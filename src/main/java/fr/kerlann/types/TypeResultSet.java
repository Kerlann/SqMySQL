package fr.kerlann.types;

import fr.nico.sqript.meta.Type;
import fr.nico.sqript.structures.ScriptElement;
import fr.nico.sqript.types.ScriptType;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Type(name = "resultset",
        parsableAs = {})
public class TypeResultSet extends ScriptType< CachedRowSet > {

    @Override
    public String toString() {
        return "MySQL resultSet";
    }

    public TypeResultSet(CachedRowSet resultSet) {
        super(resultSet);
    }


}
