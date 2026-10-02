import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseManager {

    public static void saveGuest(Guest guest) {

        String sql = """
                INSERT INTO Guest
                (guest_id, first_name, last_name, email)
                VALUES (?, ?, ?, ?)
                """;

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, guest.getGuestId());
            statement.setString(2, guest.getFirstName());
            statement.setString(3, guest.getLastName());
            statement.setString(4, guest.getEmail());

            statement.executeUpdate();

            System.out.println("Guest saved to database.");

        } catch (SQLException e) {

            System.out.println("Could not save guest.");
            e.printStackTrace();
        }
    }

    public static boolean guestExists(int guestId) {

        String sql = "SELECT guest_id FROM Guest WHERE guest_id = ?";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, guestId);

            return statement.executeQuery().next();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    public static void saveBooking(Booking booking) {

        String sql = """
                INSERT INTO Booking
                (booking_id, guest_id, property_id, check_in, check_out)
                VALUES (?, ?, ?, ?, ?)
                """;

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(
                    1,
                    booking.getBookingId()
            );

            statement.setInt(
                    2,
                    booking.getGuest().getGuestId()
            );

            statement.setInt(
                    3,
                    booking.getProperty().getPropertyId()
            );

            statement.setDate(
                    4,
                    java.sql.Date.valueOf(
                            booking.getCheckIn()
                    )
            );

            statement.setDate(
                    5,
                    java.sql.Date.valueOf(
                            booking.getCheckOut()
                    )
            );

            statement.executeUpdate();

            System.out.println("Booking saved to database.");

        } catch (SQLException e) {

            System.out.println("Could not save booking.");
            e.printStackTrace();
        }
    }
    public static void displayBookings() {

        String sql = "SELECT * FROM Booking";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet results = statement.executeQuery();

            System.out.println();
            System.out.println("===== SAVED BOOKINGS =====");

            boolean found = false;

            while (results.next()) {

                found = true;

                System.out.println("-------------------------");

                System.out.println(
                        "Booking ID: " +
                                results.getString("booking_id")
                );

                System.out.println(
                        "Guest ID: " +
                                results.getInt("guest_id")
                );

                System.out.println(
                        "Property ID: " +
                                results.getInt("property_id")
                );

                System.out.println(
                        "Check-in: " +
                                results.getDate("check_in")
                );

                System.out.println(
                        "Check-out: " +
                                results.getDate("check_out")
                );
            }

            if (!found) {
                System.out.println("No bookings found in database.");
            }

        } catch (SQLException e) {

            System.out.println("Could not load bookings.");
            e.printStackTrace();
        }
    }
    public static void cancelBooking(String bookingId) {

        String sql = """
            DELETE FROM Booking
            WHERE booking_id = ?
            """;

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, bookingId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Booking cancelled successfully.");
            } else {
                System.out.println("Booking ID not found.");
            }

        } catch (SQLException e) {

            System.out.println("Could not cancel booking.");
            e.printStackTrace();
        }
    }
    public static void searchBooking(String bookingId) {

        String sql = "SELECT * FROM Booking WHERE booking_id = ?";

        try {

            Connection connection = DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, bookingId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                System.out.println();
                System.out.println("===== BOOKING FOUND =====");

                System.out.println(
                        "Booking ID: " +
                                result.getString("booking_id")
                );

                System.out.println(
                        "Guest ID: " +
                                result.getInt("guest_id")
                );

                System.out.println(
                        "Property ID: " +
                                result.getInt("property_id")
                );

                System.out.println(
                        "Check-in: " +
                                result.getDate("check_in")
                );

                System.out.println(
                        "Check-out: " +
                                result.getDate("check_out")
                );

            } else {

                System.out.println("Booking not found.");
            }

        } catch (SQLException e) {

            System.out.println("Could not search for booking.");
            e.printStackTrace();
        }
    }
    public static void displayProperties() {

        String sql = "SELECT * FROM Property";

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet results =
                    statement.executeQuery();

            System.out.println();
            System.out.println("===== PROPERTIES =====");

            boolean found = false;

            while (results.next()) {

                found = true;

                System.out.println(
                        results.getInt("property_id")
                                + " | "
                                + results.getString("name")
                                + " | "
                                + results.getString("location")
                                + " | €"
                                + results.getDouble("price_per_night")
                                + " per night"
                );
            }

            if (!found) {
                System.out.println("No properties found.");
            }

        } catch (SQLException e) {

            System.out.println("Could not load properties.");
            e.printStackTrace();
        }
    }
    public static Property getProperty(int propertyId) {

        String sql =
                "SELECT * FROM Property WHERE property_id = ?";

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, propertyId);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                return new Property(
                        result.getInt("property_id"),
                        result.getString("name"),
                        result.getString("location"),
                        result.getDouble("price_per_night")
                );
            }

        } catch (SQLException e) {

            System.out.println("Could not load property.");
            e.printStackTrace();
        }

        return null;
    }
    public static void revenueReport() {

        String sql = """
            SELECT
                COUNT(*) AS total_bookings,
                COALESCE(
                    SUM(
                        DATEDIFF(
                            Booking.check_out,
                            Booking.check_in
                        )
                        *
                        Property.price_per_night
                    ),
                    0
                ) AS total_revenue
            FROM Booking
            JOIN Property
                ON Booking.property_id = Property.property_id
            """;

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                int totalBookings =
                        result.getInt("total_bookings");

                double totalRevenue =
                        result.getDouble("total_revenue");

                System.out.println();
                System.out.println("===== REVENUE REPORT =====");
                System.out.println(
                        "Total bookings: " + totalBookings
                );

                System.out.printf(
                        "Total revenue: €%.2f%n",
                        totalRevenue
                );

                System.out.println("==========================");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Could not generate revenue report."
            );

            e.printStackTrace();
        }
    }
    public static void saveProperty(Property property) {

        String sql = """
            INSERT INTO Property
            (property_id, name, location, price_per_night)
            VALUES (?, ?, ?, ?)
            """;

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    property.getPropertyId()
            );

            statement.setString(
                    2,
                    property.getName()
            );

            statement.setString(
                    3,
                    property.getLocation()
            );

            statement.setDouble(
                    4,
                    property.getPricePerNight()
            );

            statement.executeUpdate();

            System.out.println(
                    "Property added successfully."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Could not add property."
            );

            e.printStackTrace();
        }
    }
}
