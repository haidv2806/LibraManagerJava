ó bảopackage com.javateam.libramanagerjava;

public class User {
    private String fullName;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String address;
    private String hashedPassword;

    public User(String fullName, String dateOfBirth, String phone, String email, String address, String hashedPassword) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hashedPassword = hashedPassword;
    }

    public String getFullName() { return fullName; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getHashedPassword() { return hashedPassword; }
}