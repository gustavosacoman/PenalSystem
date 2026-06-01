package application.dtos.Prisoner;

public class CompleteAddressDto {
    public String zipCode;
    public String street;
    public String city;
    public String state;

    public CompleteAddressDto(
            String zipCode,
            String street,
            String city,
            String state) {

        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
        this.state = state;
    }
}
