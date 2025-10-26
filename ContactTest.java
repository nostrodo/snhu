package contacts;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {
	String IdGeneric = "ID123";
	String fnGeneric = "John";
	String lnGeneric = "Doe";
	String pnGeneric = "1234567890";
	String addGeneric = "123 Wherever st";
	
	// for easy copy/pasting:
	// Contact contact = new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, addGeneric);
	
	// CONTACT ID# TEST
	@Test
	void contactIdShorterThan10_IsAccepted() {
		Contact contact = new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, addGeneric);
		assertEquals("ID123", contact.getContactId());
	}
	
	@Test
	void contactIdExactly10_IsAccepted() {
		String ten = "ABCDEFGHIJ";
		Contact contact = new Contact(ten, fnGeneric, lnGeneric, pnGeneric, addGeneric);
		assertEquals(ten, contact.getContactId());
	}
	
	@Test
	void contactIdLongerThan10_IsRejected() {
		assertThrows(IllegalArgumentException.class, () ->
			new Contact("ABCDEFGHIJK", fnGeneric, lnGeneric, pnGeneric, addGeneric));
	}
	
	@Test
	void contactIdNull_IsRejected() {
		assertThrows(IllegalArgumentException.class, () ->
			new Contact(null, fnGeneric, lnGeneric, pnGeneric, addGeneric));
	}
	
	 @Test
	 void contactIdIsNotUpdatable() {
		 Contact contact = new Contact("IMMUTABLE", "John", "Doe", "1234567890", "Addr");
	     contact.setFirstName("Jane");
	     contact.setLastName("Smith");
	     contact.setPhone("0987654321");
	     contact.setAddress("New Address 123");

	     assertEquals("IMMUTABLE", contact.getContactId());
	     assertEquals("Jane", contact.getFirstName());
	     assertEquals("Smith", contact.getLastName());
	     assertEquals("0987654321", contact.getPhone());
	     assertEquals("New Address 123", contact.getAddress());
	}
	  
	  // firstName tests
	  @Test
	  void firstNameShorterThan10_IsAccepted() {
		  Contact contact = new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, addGeneric);
		  assertEquals(fnGeneric, contact.getFirstName());
	  }
	  
	  @Test
	  void firstNameExactly10_IsAccepted() {
		  Contact contact = new Contact(IdGeneric, "ABCDEFGHIJ", lnGeneric, pnGeneric, addGeneric);
		  assertEquals("ABCDEFGHIJ", contact.getFirstName());
	  }
	  
	  @Test
	  void firstNameLongerThan10_IsRejected() {
		  assertThrows(IllegalArgumentException.class, () ->
		  new Contact(IdGeneric, "ABCDEFGHIJK", lnGeneric, pnGeneric, addGeneric));
	  }
	  
	  @Test
	  void firstNameNull_Isrejected() {
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, null, lnGeneric, pnGeneric, addGeneric));
	  }
	  
	  // lastName Tests
	  @Test
	  void lastNameShorterThan10_IsAccepted() {
		  Contact contact = new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, addGeneric);
		  assertEquals(lnGeneric, contact.getLastName());
	  }
	  
	  @Test
	  void lastNameExactly10_IsAccepted() {
		  String ten = "ABCDEFGHIJ";
		  Contact contact = new Contact(IdGeneric, fnGeneric, ten, pnGeneric, addGeneric);
		  assertEquals(ten, contact.getLastName());
	  }
	  
	  @Test
	  void lastNameLongerThan10_IsReject() {
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, fnGeneric, "ABCDEFGHIJK", pnGeneric, addGeneric));
	  }
	  
	  @Test
	  void lastNameNull_IsRejected() {
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, fnGeneric, null, pnGeneric, addGeneric));
	  }
	  
	  // phone number tests
	  @Test
	  void phoneExactly10digits_IsAccepted() {
		  Contact contact = new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, addGeneric);
		  assertEquals(pnGeneric, contact.getPhone());
	  }
	  
	  @Test
	  void phoneNineDigits_IsRejected() {
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, fnGeneric, lnGeneric, "123456789", addGeneric));
	  }
	  
	  @Test
	  void phoneElevenDigits_IsRejected() {
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, fnGeneric, lnGeneric, "12345678901", addGeneric));
	  }
	  
	  @Test
	  void phoneNonDigits_IsRejected() {
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, fnGeneric, lnGeneric, "12345abcde", addGeneric));
	  }
	  
	  @Test
	  void phoneNull_IsRejected() {
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, fnGeneric, lnGeneric, null, addGeneric));
	  }
	  
	  // Address tests
	  @Test
	  void addressShorterThan30_IsAccepted() {
		  Contact contact = new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, addGeneric);
		  assertEquals(addGeneric, contact.getAddress());
	  }
	  
	  @Test
	  void addressExactly30_IsAccepted() {
		  String thirty = "123456789012345678901234567890";
		  assertEquals(30, thirty.length());
		  Contact contact = new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, thirty);
		  assertEquals(thirty, contact.getAddress());
	  }
	  
	  @Test
	  void addressLongerThan30_IsRejected() {
		  String thirtyOne = "1234567890123456789012345678901";
		  assertEquals(31, thirtyOne.length());
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, thirtyOne));
	  }
	  
	  @Test
	  void addressNull_IsRejected() {
		  assertThrows(IllegalArgumentException.class, () ->
		  	new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, null));
	  }
	  
	  // quick test to ensure setters also respect boundaries
	    @Test
	    void setterBoundaries_AreEnforced() {
	        Contact contact = new Contact(IdGeneric, fnGeneric, lnGeneric, pnGeneric, addGeneric);

	        // firstName
	        contact.setFirstName("ABCDEFGHIJ");
	        assertEquals("ABCDEFGHIJ", contact.getFirstName());
	        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("ABCDEFGHIJK"));

	        // lastName
	        contact.setLastName("ABCDEFGHIJ");
	        assertEquals("ABCDEFGHIJ", contact.getLastName());
	        assertThrows(IllegalArgumentException.class, () -> contact.setLastName("ABCDEFGHIJK"));

	        // phone
	        contact.setPhone("0123456789");
	        assertEquals("0123456789", contact.getPhone());
	        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("123456789"));
	        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("12345678901"));
	        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("12345abc90"));

	        // address
	        contact.setAddress("123456789012345678901234567890");
	        assertEquals(30, contact.getAddress().length());
	        assertThrows(IllegalArgumentException.class, () -> contact.setAddress("1234567890123456789012345678901"));
	    }

}
