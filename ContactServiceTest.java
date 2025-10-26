package contacts;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {

    // ---------- add (unique ID) ----------
    @Test
    void addUniqueContact_Succeeds() {
        ContactService svc = new ContactService();
        Contact a = new Contact("A1", "Amy", "Adams", "1112223333", "One Way 1");
        svc.addContact(a);

        Contact stored = svc.getContact("A1");
        assertNotNull(stored);
        assertEquals("A1", stored.getContactId());
        assertEquals("Amy", stored.getFirstName());
        assertEquals("Adams", stored.getLastName());
        assertEquals("1112223333", stored.getPhone());
        assertEquals("One Way 1", stored.getAddress());
    }

    @Test
    void addDuplicateId_IsRejected() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("A1", "Amy", "Adams", "1112223333", "One Way 1"));
        assertThrows(IllegalArgumentException.class, () ->
            svc.addContact(new Contact("A1", "Alan", "Archer", "9998887777", "Dup Street")));
    }

    @Test
    void addNullContact_IsRejected() {
        ContactService svc = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> svc.addContact(null));
    }

    // ---------- delete by ID ----------
    @Test
    void deleteExistingContact_Succeeds() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("B2", "Bob", "Baker", "2223334444", "Two Way 2"));

        svc.deleteContact("B2");
        assertNull(svc.getContact("B2"));
    }

    @Test
    void deleteNonExistingContact_IsRejected() {
        ContactService svc = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> svc.deleteContact("NOPE"));
    }

    @Test
    void deleteNullId_IsRejected() {
        ContactService svc = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> svc.deleteContact(null));
    }

    // ---------- updates: boundary tests per field ----------
    @Test
    void updateFirstName_Boundaries() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("C3", "Cat", "Clark", "3334445555", "Three Way 3"));

        svc.updateFirstName("C3", "ABCDEFGHIJ"); // 10 ok
        assertEquals("ABCDEFGHIJ", svc.getContact("C3").getFirstName());

        assertThrows(IllegalArgumentException.class, () -> svc.updateFirstName("C3", "ABCDEFGHIJK"));
        assertThrows(IllegalArgumentException.class, () -> svc.updateFirstName("NOPE", "Ana"));
    }

    @Test
    void updateLastName_Boundaries() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("D4", "Dan", "Dove", "4445556666", "Four Way 4"));

        svc.updateLastName("D4", "ABCDEFGHIJ"); // 10 ok
        assertEquals("ABCDEFGHIJ", svc.getContact("D4").getLastName());

        assertThrows(IllegalArgumentException.class, () -> svc.updateLastName("D4", "ABCDEFGHIJK"));
        assertThrows(IllegalArgumentException.class, () -> svc.updateLastName("NOPE", "Li"));
    }

    @Test
    void updatePhone_Boundaries() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("E5", "Eli", "Edge", "5556667777", "Five Way 5"));

        svc.updatePhone("E5", "0123456789"); // exactly 10
        assertEquals("0123456789", svc.getContact("E5").getPhone());

        assertThrows(IllegalArgumentException.class, () -> svc.updatePhone("E5", "123456789"));   // 9
        assertThrows(IllegalArgumentException.class, () -> svc.updatePhone("E5", "12345678901")); // 11
        assertThrows(IllegalArgumentException.class, () -> svc.updatePhone("E5", "12345abc90"));  // non-digits
        assertThrows(IllegalArgumentException.class, () -> svc.updatePhone("NOPE", "0123456789"));
    }

    @Test
    void updateAddress_Boundaries() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("F6", "Fay", "Frost", "6667778888", "Six Way 6"));

        String thirty = "123456789012345678901234567890"; // 30
        svc.updateAddress("F6", thirty);
        assertEquals(thirty, svc.getContact("F6").getAddress());

        assertThrows(IllegalArgumentException.class, () ->
            svc.updateAddress("F6", "1234567890123456789012345678901")); // 31
        assertThrows(IllegalArgumentException.class, () ->
            svc.updateAddress("NOPE", "Some Addr"));
    }

    @Test
    void updateWithNullId_IsRejected() {
        ContactService svc = new ContactService();
        svc.addContact(new Contact("G7", "Gus", "Gale", "7778889999", "Seven Way 7"));
        assertThrows(IllegalArgumentException.class, () -> svc.updateFirstName(null, "Ana"));
        assertThrows(IllegalArgumentException.class, () -> svc.updateLastName(null, "Li"));
        assertThrows(IllegalArgumentException.class, () -> svc.updatePhone(null, "0123456789"));
        assertThrows(IllegalArgumentException.class, () -> svc.updateAddress(null, "Addr"));
    }
}
