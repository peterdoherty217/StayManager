import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DatabaseConnection.getConnection();

        Scanner scanner = new Scanner(System.in);

        BookingManager manager = new BookingManager();

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("=========================");
            System.out.println("       STAYMANAGER");
            System.out.println("=========================");
            System.out.println("1. Create Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Search Booking");
            System.out.println("5. Add Property");
            System.out.println("6. Revenue Report");
            System.out.println("0. Exit");

            int choice = readInt(
                    scanner,
                    "Choose an option: "
            );

            switch (choice) {

                // =====================================
                // CREATE BOOKING
                // =====================================

                case 1: {

                    int guestId = readInt(
                            scanner,
                            "Enter guest ID: "
                    );

                    String firstName = readText(
                            scanner,
                            "Enter first name: "
                    );

                    String lastName = readText(
                            scanner,
                            "Enter last name: "
                    );

                    String email = readText(
                            scanner,
                            "Enter email: "
                    );

                    Guest guest = new Guest(
                            guestId,
                            firstName,
                            lastName,
                            email
                    );

                    String bookingId = readText(
                            scanner,
                            "Enter booking ID: "
                    );

                    // Show properties stored in MySQL
                    DatabaseManager.displayProperties();

                    int propertyId = readInt(
                            scanner,
                            "Enter property ID: "
                    );

                    Property selectedProperty =
                            DatabaseManager.getProperty(
                                    propertyId
                            );

                    if (selectedProperty == null) {

                        System.out.println(
                                "Property not found."
                        );

                        break;
                    }

                    LocalDate checkIn =
                            readDate(
                                    scanner,
                                    "Enter check-in date"
                            );

                    LocalDate checkOut =
                            readDate(
                                    scanner,
                                    "Enter check-out date"
                            );

                    while (!checkOut.isAfter(checkIn)) {

                        System.out.println(
                                "Check-out must be after check-in."
                        );

                        checkOut = readDate(
                                scanner,
                                "Enter check-out date"
                        );
                    }

                    Booking booking = new Booking(
                            bookingId,
                            guest,
                            selectedProperty,
                            checkIn,
                            checkOut
                    );

                    if (manager.addBooking(booking)) {

                        if (!DatabaseManager.guestExists(
                                guest.getGuestId())) {

                            DatabaseManager.saveGuest(
                                    guest
                            );

                        } else {

                            System.out.println(
                                    "Guest already exists in database."
                            );
                        }

                        DatabaseManager.saveBooking(
                                booking
                        );

                        System.out.println(
                                "Booking successfully created."
                        );

                        System.out.printf(
                                "Total price: €%.2f%n",
                                booking.calculateTotalPrice()
                        );

                    } else {

                        System.out.println(
                                "Property unavailable for those dates."
                        );
                    }

                    break;
                }


                // =====================================
                // VIEW BOOKINGS
                // =====================================

                case 2: {

                    DatabaseManager.displayBookings();

                    break;
                }


                // =====================================
                // CANCEL BOOKING
                // =====================================

                case 3: {

                    String cancelId = readText(
                            scanner,
                            "Enter booking ID to cancel: "
                    );

                    DatabaseManager.cancelBooking(
                            cancelId
                    );

                    break;
                }


                // =====================================
                // SEARCH BOOKING
                // =====================================

                case 4: {

                    String searchId = readText(
                            scanner,
                            "Enter booking ID: "
                    );

                    DatabaseManager.searchBooking(
                            searchId
                    );

                    break;
                }


                // =====================================
                // ADD PROPERTY
                // =====================================

                case 5: {

                    int propertyId = readInt(
                            scanner,
                            "Enter property ID: "
                    );

                    String propertyName = readText(
                            scanner,
                            "Enter property name: "
                    );

                    String location = readText(
                            scanner,
                            "Enter location: "
                    );

                    double pricePerNight = readDouble(
                            scanner,
                            "Enter price per night: €"
                    );

                    Property property =
                            new Property(
                                    propertyId,
                                    propertyName,
                                    location,
                                    pricePerNight
                            );

                    DatabaseManager.saveProperty(
                            property
                    );

                    break;
                }


                // =====================================
                // REVENUE REPORT
                // =====================================

                case 6: {

                    DatabaseManager.revenueReport();

                    break;
                }


                // =====================================
                // EXIT
                // =====================================

                case 0: {

                    running = false;

                    System.out.println(
                            "Goodbye."
                    );

                    break;
                }


                default: {

                    System.out.println(
                            "Invalid option."
                    );
                }
            }
        }

        scanner.close();
    }


    // =====================================
    // READ INTEGER
    // =====================================

    public static int readInt(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    // =====================================
    // READ DOUBLE
    // =====================================

    public static double readDouble(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            try {

                double number =
                        Double.parseDouble(input);

                if (number < 0) {

                    System.out.println(
                            "Amount cannot be negative."
                    );

                    continue;
                }

                return number;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid amount."
                );
            }
        }
    }


    // =====================================
    // READ TEXT
    // =====================================

    public static String readText(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            if (!input.isBlank()) {

                return input;
            }

            System.out.println(
                    "This field cannot be blank."
            );
        }
    }


    // =====================================
    // READ DATE
    // =====================================

    public static LocalDate readDate(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(
                    message +
                            " (YYYY-MM-DD): "
            );

            String input =
                    scanner.nextLine().trim();

            try {

                return LocalDate.parse(input);

            } catch (Exception e) {

                System.out.println(
                        "Invalid date. Example: 2026-12-15"
                );
            }
        }
    }
}