package vinova.henry.com.readcontactcontentprovider.models;


public class Contact {
    private String Name;
    private String PhoneNumber;

    public Contact(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}
