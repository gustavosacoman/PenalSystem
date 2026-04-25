package infrastructure;

import org.flywaydb.core.Flyway;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream input = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties not found");
            }

            PROPS.load(input);

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String URL = PROPS.getProperty("db.url");
    private static final String USER = PROPS.getProperty("db.user");
    private static final String PASSWORD = PROPS.getProperty("db.password");

    public static void applyMigrations(){
        System.out.println("Synchronizing database with flyway");

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(URL, USER, PASSWORD)
                    .load();

            flyway.migrate();

        } catch (Exception e)
        {
            System.err.println("Error Flyway: " + e.getMessage());
            throw e;
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}