package fr.kerlann.actions;

import fr.nico.sqript.actions.ScriptAction;
import fr.nico.sqript.meta.Feature;
import fr.nico.sqript.network.ScriptNetworkManager;
import fr.nico.sqript.types.TypePlayer;
import fr.nico.sqript.compiling.ScriptException;
import fr.nico.sqript.meta.Action;
import fr.nico.sqript.structures.ScriptContext;
import fr.nico.sqript.types.ScriptType;
import fr.nico.sqript.types.primitive.TypeString;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@Action(name = "MySQL Actions",
        features = {
                @Feature(name = "Mysql execute", description = "Executing a Sql query", examples = "mysql execute \"INSERT INTO `user`(`player_name`, `coins`, `isHand`) VALUES ('%{username}%',%{coins}%,%{isHand}%)\" in ${MySQL}", pattern = "mysql execute {string} in {mysql}"),
        }
)
public class ActMySQL extends ScriptAction {

    @Override
    public void execute(ScriptContext context) throws ScriptException {
        switch (getMatchedIndex()){
            case 0:
                Connection conn = (Connection) getParameter(2, context);
                try {
                    Statement st = conn.createStatement();
                    System.out.println((String)getParameter(1, context));
                    st.executeUpdate((String)getParameter(1, context));
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
                return;

        }

    }
}