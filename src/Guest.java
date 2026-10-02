public class Guest {

    private int guestId;
    private String firstName;
    private String lastName;
    private String email;

    public Guest(int guestId, String firstName, String lastName, String email) {
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGuestId() {
        return guestId;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return guestId + " | " + getFullName() + " | " + email;
    }

}
