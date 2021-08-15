package fr.kerlann;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.IOException;
import java.sql.SQLException;

@Mod(modid = "sqmysql", name = "SqMySQL", dependencies = "required-after:sqript@${version}", version = "1.0.0")
public class SqMySQL {

    private static RowSetFactory rowSetFactory;

    @Mod.EventHandler
    public static void preInit(FMLConstructionEvent event) {
        try {
            rowSetFactory = RowSetProvider.newFactory();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {

    }

    public static RowSetFactory getRowSetFactory() {
        return rowSetFactory;
    }
}
