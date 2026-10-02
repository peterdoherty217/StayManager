public class Property {
    private int propertyId;
    private String name;
    private String location;
    private double pricePerNight;

    public Property(int propertyId, String name, String location, double pricePerNight) {
        this.propertyId = propertyId;
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return propertyId + " | " + name + " | " + location +
                " | €" + pricePerNight + " per night";
    }
}

