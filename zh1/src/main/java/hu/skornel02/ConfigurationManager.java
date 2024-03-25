package hu.skornel02;

public class ConfigurationManager {
    private static String connectionUrl = "jdbc:sqlite:/home.local/valaki/IdeaProjects/zhzhzh/verysusdatabase.sqlite";

    public static String getConnectionUrl(){
        return connectionUrl;
    }
}
