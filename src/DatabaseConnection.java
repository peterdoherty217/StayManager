import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/staymanager";

    private static final String USER = "root";

    private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    public static Connection getConnection() {

        try {

            Connection connection =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            System.out.println("Connected to database successfully.");

            return connection;

        } catch (SQLException e) {

            System.out.println("Database connection failed.");

            e.printStackTrace();

            return null;
        }
    }
}
