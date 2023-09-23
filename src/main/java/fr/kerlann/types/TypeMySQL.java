package fr.kerlann.types;

import fr.nico.sqript.meta.Type;
import fr.nico.sqript.structures.ScriptElement;
import fr.nico.sqript.types.ScriptType;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.sql.Connection;
import java.sql.SQLException;

@Type(name = "mysql",
        parsableAs = {})
public class TypeMySQL extends ScriptType< Connection > {

    @Override
    public String toString() {
        return "MySQL Connection";
    }

    public TypeMySQL(Connection sender) {
        super(sender);
    }


}
