package contacts;

import java.util.HashMap;
import java.util.Map;

public class ContactService {

	private final Map<String, Contact> contacts = new HashMap<>();
	
	// add contact with ID#, ensure it is unique
	public void addContact(Contact contact) {
		// null check
		if (contact == null) throw new IllegalArgumentException("contact cannot be null");
		
		// check if ID is unique
		String id = contact.getContactId();
		if (contacts.containsKey(id)) {
			throw new IllegalArgumentException("Contact ID already exists; " + id);
		}
		
		// add the contact once checks have passed
		contacts.put(id,  contact);
	}
	
	// delete contact by ID#
	public void deleteContact(String contactId) {
		// validates and checks ID exists
		getExisting(contactId);
		
		// deletes the contact once checks have passed
		contacts.remove(contactId);
	}

	// updating fields by ID#
	public void updateFirstName(String contactId, String firstName) {
		getExisting(contactId).setFirstName(firstName);
	}
	
	public void updateLastName(String contactId, String lastName) {
		getExisting(contactId).setLastName(lastName);
	}
	
	public void updatePhone(String contactId, String phone) {
		getExisting(contactId).setPhone(phone);
	}
	
	public void updateAddress(String contactId, String address) {
		getExisting(contactId).setAddress(address);
	}
	
	
	// Helper methods
	private Contact getExisting(String contactId) {
		// null check
		if (contactId == null) throw new IllegalArgumentException("contactId cannot be null");
		Contact contact = contacts.get(contactId);
		
		//existence check
		if (contact == null) throw new IllegalArgumentException("No contact with ID: " + contactId);
		return contact;
	}
	
	// getters
	public Contact getContact (String contactId) { return contacts.get(contactId);	}
	
}
