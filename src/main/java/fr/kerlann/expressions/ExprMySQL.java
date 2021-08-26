package fr.kerlann.expressions;

import fr.kerlann.SqMySQL;
import fr.kerlann.types.TypeMySQL;
import fr.kerlann.types.TypeResultSet;
import fr.nico.sqript.ScriptManager;
import fr.nico.sqript.expressions.ScriptExpression;
import fr.nico.sqript.meta.Expression;
import fr.nico.sqript.meta.Feature;
import fr.nico.sqript.structures.ScriptContext;
import fr.nico.sqript.types.ScriptType;
import fr.nico.sqript.types.TypeArray;
import fr.nico.sqript.types.TypeNull;
import fr.nico.sqript.types.primitive.TypeBoolean;
import fr.nico.sqript.types.primitive.TypeString;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;

@Expression(name = "MySQL expressions",
        features = {
                @Feature(name = "Initialize database", description = "Iinitialize database", examples = "set ${MySQL} to the database \"jdbc:mysql://localhost:3306/test?user=root&password=\"", pattern = "the database {string}", type = "mysql"),
                @Feature(name = "ResultSet database", description = "Retrieve information from the sql query", examples = "set {result} to ${MySQL} result of query \"SELECT * FROM `user` WHERE `player_name` = '\"+{username}+\"'\"", pattern = "{mysql} result of query {string}", type = "resultset"),
                @Feature(name = "Value String ResultSet", description = "get value string", examples = "set {playerName_array} to ${MySQL} string \"player_name\" in {result}", pattern = "{mysql} string {string} in {resultset}", type = "array"),
                @Feature(name = "Value Boolean ResultSet", description = "get value boolean", examples = "set {playerName_array} to ${MySQL} boolean \"isHand\" in {result}", pattern = "{mysql} boolean {string} in {resultset}", type = "array"),

        }
)
public class ExprMySQL extends ScriptExpression {

    @Override
    public ScriptType get(ScriptContext context, ScriptType[] parameters) {

        switch(getMatchedIndex()){
            case 0:
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection((String) parameters[0].getObject());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(connection != null) {
                    ScriptManager.log.info("MYSQL Database Connected successfully");
                    return new TypeMySQL(connection);
                }else {
                    ScriptManager.log.fatal("MYSQL Can't connect to Database");
                    return new TypeNull();
                }
            case 1:
                Connection conn = (Connection) parameters[0].getObject();
                String format = (String) parameters[1].getObject();
                try (Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(format)) {
                    if (resultSet.isBeforeFirst()) {
                        CachedRowSet crs = SqMySQL.getRowSetFactory().createCachedRowSet();
                        crs.populate(resultSet);
                        return new TypeResultSet(crs);
                    } else {
                        return new TypeNull();
                    }
                } catch (SQLException ex) {
                    ScriptManager.log.error("Error in " + getLine().getScriptInstance().getName() + "- Line "+getLine().getLineNumber(), ex);
                    return new TypeNull();
                }
            case 2:
                conn = (Connection) parameters[0].getObject();
                CachedRowSet resultSet = (CachedRowSet) parameters[2].getObject();
                ArrayList< TypeString > typeLists = new ArrayList<>();
                try {
                    while (resultSet.next()) {
                        typeLists.add(new TypeString(resultSet.getString((String) parameters[1].getObject())));
                    }
                    resultSet.release();
                    return new TypeArray(typeLists);
                } catch (SQLException ex) {
                    ScriptManager.log.error("Error in " + getLine().getScriptInstance().getName() + "- Line " + getLine().getLineNumber(), ex);
                    return new TypeNull();
                }
            case 3:
                conn = (Connection) parameters[0].getObject();
                resultSet = (CachedRowSet) parameters[2].getObject();
                ArrayList< TypeBoolean > typeBooleanLists = new ArrayList<>();
                try {
                    while (resultSet.next()) {
                        typeBooleanLists.add(new TypeBoolean(resultSet.getBoolean((String) parameters[1].getObject())));
                    }
                    resultSet.release();
                    return new TypeArray(typeBooleanLists);
                } catch (SQLException ex) {
                    ScriptManager.log.error("Error in " + getLine().getScriptInstance().getName() + "- Line " + getLine().getLineNumber(), ex);
                    return new TypeNull();
                }
        }
        return null;
    }

    @Override
    public boolean set(ScriptContext context, ScriptType to, ScriptType[] parameters) {
        switch(getMatchedIndex()){
            case 0:
                return false;
        }
        return false;
    }

}
