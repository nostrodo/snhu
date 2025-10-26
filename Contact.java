package contacts;

public class Contact {
	
	private final String contactId;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	
	// default constructor
	public Contact(String contactId, String firstName, String lastName, String phone, String address) {
		
		validateNotNull(contactId, "contactId");
		validateMaxLength(contactId, 10, "contactId");
		
		validateNotNull(firstName, "firstName");
		validateMaxLength(firstName, 10, "firstName");
		
		validateNotNull(lastName, "lastName");
		validateMaxLength(lastName, 10, "lastName");
		
		validateNotNull(phone, "phone");
		validatePhone(phone);
		
		validateNotNull(address, "address");
		validateMaxLength(address, 30, "address");
		
		this.contactId = contactId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		
	}
	
	// getters
	public String getContactId()	{ return contactId; }
	public String getFirstName()	{ return firstName; }
	public String getLastName()		{ return lastName; }
	public String getPhone()		{ return phone; }
	public String getAddress()		{ return address; }
	
	// setters
	public void setFirstName(String firstName) {
		validateNotNull(firstName, "firstName");
		validateMaxLength(firstName, 10, "firstName");
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		validateNotNull(lastName, "lastName");
		validateMaxLength(lastName, 10, "lastName");
		this.lastName = lastName;
	}
	
	public void setPhone(String phone) {
		validateNotNull(phone, "phone");
		validatePhone(phone);
		this.phone = phone;
	}
	
	public void setAddress(String address) {
		validateNotNull(address, "address");
		validateMaxLength(address, 30, "address");
		this.address = address;
	}
	
	// validation methods
	private static void validateNotNull(String value, String field) {
		if (value == null) throw new IllegalArgumentException(field + " cannot be null");
	}
	
	private static void validateMaxLength(String value, int max, String field) {
		if (value.length() > max) {
			throw new IllegalArgumentException(field + " length must be <= " + max);
		}
	}
	
	private static void validatePhone(String phone) {
		if (!phone.matches("\\d{10}")) {
			throw new IllegalArgumentException("phone must be 10 digits");
		}
	}	
}