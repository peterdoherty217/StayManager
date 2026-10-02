import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {

    private String bookingId;
    private Guest guest;
    private Property property;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public Booking(
            String bookingId,
            Guest guest,
            Property property,
            LocalDate checkIn,
            LocalDate checkOut) {

        this.bookingId = bookingId;
        this.guest = guest;
        this.property = property;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Property getProperty() {
        return property;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public double calculateTotalPrice() {
        return getNumberOfNights() * property.getPricePerNight();
    }

    @Override
    public String toString() {
        return "Booking #" + bookingId +
                "\nGuest: " + guest.getFullName() +
                "\nProperty: " + property.getName() +
                "\nCheck-in: " + checkIn +
                "\nCheck-out: " + checkOut +
                "\nNights: " + getNumberOfNights() +
                "\nTotal: €" + calculateTotalPrice();
    }
}
