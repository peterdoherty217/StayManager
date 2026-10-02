import java.time.LocalDate;
import java.util.ArrayList;

public class BookingManager {

    private ArrayList<Booking> bookings;

    public BookingManager() {
        bookings = new ArrayList<>();
    }

    public boolean isPropertyAvailable(
            Property property,
            LocalDate checkIn,
            LocalDate checkOut) {

        for (Booking booking : bookings) {

            if (booking.getProperty().getPropertyId()
                    == property.getPropertyId()) {

                boolean datesOverlap =
                        checkIn.isBefore(booking.getCheckOut())
                                &&
                                checkOut.isAfter(booking.getCheckIn());

                if (datesOverlap) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean addBooking(Booking booking) {

        if (isPropertyAvailable(
                booking.getProperty(),
                booking.getCheckIn(),
                booking.getCheckOut())) {

            bookings.add(booking);
            return true;
        }

        return false;
    }

    public void displayBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println("------------------------");
            System.out.println(booking);
        }
    }
}