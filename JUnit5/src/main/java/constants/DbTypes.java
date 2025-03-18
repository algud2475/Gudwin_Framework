package constants;

public enum DbTypes {
    MSSQL("com.microsoft.jdbc.sqlserver.SQLServerDriver", "jdbc:sqlserver://%1$s:%2$s;databaseName=%3$s"),
    MySQL("com.mysql.jdbc.Driver", "jdbc:mysql://%1$s:%2$s/%3$s"),
    PostgreSQL("org.postgresql.Driver", "jdbc:postgresql://%1$s:%2$s/%3$s"),
    H2("org.h2.Driver", "jdbc:h2:tcp://%1$s:%2$s/mem:%3$s");

    public final String driverName;
    public final String url;

    private DbTypes(String driverName, String url) {
        this.driverName = driverName;
        this.url = url;
    }

    public static DbTypes getType(String name) {
        for (DbTypes type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid DataBase type: " + name);
    }
}
