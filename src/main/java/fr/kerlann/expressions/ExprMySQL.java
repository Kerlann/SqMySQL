package fr.kerlann.expressions;

import fr.kerlann.SqMySQL;
import fr.kerlann.types.TypeMySQL;
import fr.kerlann.types.TypeResultSet;
import fr.nico.sqript.ScriptManager;
import fr.nico.sqript.expressions.ScriptExpression;
import fr.nico.sqript.meta.Expression;
import fr.nico.sqript.structures.ScriptContext;
import fr.nico.sqript.types.ScriptType;
import fr.nico.sqript.types.TypeArray;
import fr.nico.sqript.types.TypeNull;
import fr.nico.sqript.types.primitive.TypeString;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;

@Expression(name = "MySQL Expressions",
        description = "Get informations about the system",
        examples = "execution side",
        patterns = {
                "the database {string}",
                "{mysql} result of query {string}",
                "{mysql} string {string} in {resultset}",
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
                    ScriptManager.log.error("Error in " + getLine().scriptInstance.getName() + "- Line "+getLine().number, ex);
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
                    ScriptManager.log.error("Error in " + getLine().scriptInstance.getName() + "- Line " + getLine().number, ex);
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
