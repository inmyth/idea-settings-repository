package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyGenerator {
    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");
    private static final String OUT_DIR = PROJECT_DIR + "/app/src/main/java";

    public static void main(String[] args) throws Exception{

        Schema schema = new Schema(1, "mobileyed.hanatoya.jp");
        // Call the addTables method which appends our Objects definition to the schema
        Entity cam = schema.addEntity("Cam");
        cam.addIdProperty().primaryKey().autoincrement();
        cam.addStringProperty("type").notNull();
        cam.addStringProperty("name").notNull();
        cam.addStringProperty("host").notNull();
        cam.addStringProperty("protocol").notNull();
        cam.addIntProperty("port").notNull();
        cam.addStringProperty("username");
        cam.addStringProperty("password");
        cam.addIntProperty("status");
        cam.addByteArrayProperty("snap");
        new DaoGenerator().generateAll(schema, OUT_DIR);

    }
}
