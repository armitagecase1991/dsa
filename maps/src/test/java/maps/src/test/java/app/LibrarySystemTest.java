package maps.src.test.java.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dsa.maps.app.LibrarySystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class LibrarySystemTest {
    private LibrarySystem library;

    @BeforeEach
    public void setup() {
        library = new LibrarySystem();
    }

    @Test
    public void testAddBook_Success() {
        boolean result = library.addBook("B1", "Java Programming");
        assertTrue(result, "Book should be added successfully");
    }

    @Test
    public void testAddBook_Failure_DuplicateBookId() {
        library.addBook("B1", "Java Programming");
        boolean result = library.addBook("B1", "Another Book");
        assertFalse(result, "Adding a book with a duplicate ID should fail");
    }

    @Test
    public void testListBooks() {
        library.addBook("B1", "Java Programming");
        library.addBook("B2", "Python Basics");
        List<String> books = library.listBooks();
        assertEquals(2, books.size(), "List should contain two books");
        assertTrue(books.contains("Java Programming"));
        assertTrue(books.contains("Python Basics"));
    }

    @Test
    public void testBorrowBook_Success() {
        library.addBook("B1", "Java Programming");
        boolean result = library.borrowBook("B1");
        assertTrue(result, "Book should be borrowed successfully");
        List<String> books = library.listBooks();
        assertFalse(books.contains("Java Programming"), "Borrowed book should no longer be in available books list");
    }

    @Test
    public void testBorrowBook_Failure_BookNotFound() {
        boolean result = library.borrowBook("B1");
        assertFalse(result, "Borrowing a non-existent book should fail");
    }

    @Test
    public void testBorrowBook_Failure_AlreadyBorrowed() {
        library.addBook("B1", "Java Programming");
        library.borrowBook("B1");
        boolean result = library.borrowBook("B1");
        assertFalse(result, "Borrowing an already borrowed book should fail");
    }

    @Test
    public void testReturnBook_Success() {
        library.addBook("B1", "Java Programming");
        library.borrowBook("B1");
        boolean result = library.returnBook("B1");
        assertTrue(result, "Returning a borrowed book should succeed");
        List<String> books = library.listBooks();
        assertTrue(books.contains("Java Programming"), "Returned book should be available again");
    }

    @Test
    public void testReturnBook_Failure_BookNotFound() {
        boolean result = library.returnBook("B1");
        assertFalse(result, "Returning a non-existent book should fail");
    }

    @Test
    public void testReturnBook_Failure_AlreadyAvailable() {
        library.addBook("B1", "Java Programming");
        boolean result = library.returnBook("B1");
        assertFalse(result, "Returning a book that is already available should fail");
    }

    @Test
    public void testReserveBook_Success() throws Exception {
        library.addBook("B1", "Java Programming");
        library.borrowBook("B1");
        boolean result = library.reserveBook("B1");
        assertTrue(result, "Reserving a borrowed book should succeed");
    }

    @Test
    public void testReserveBook_Failure_BookNotFound() {
        Exception exception = assertThrows(Exception.class, () -> library.reserveBook("B1"));
        assertEquals(" The following Book does not exist B1", exception.getMessage());
    }

    @Test
    public void testReserveBook_Failure_AlreadyReserved() throws Exception {
        library.addBook("B1", "Java Programming");
        library.borrowBook("B1");
        library.reserveBook("B1");
        boolean result = library.reserveBook("B1");
        assertFalse(result, "Reserving an already reserved book should fail");
    }

    @Test
    public void testGetBookHistory_Success() throws Exception {
        library.addBook("B1", "Java Programming");
        library.borrowBook("B1");
        library.returnBook("B1");
        List<String> history = library.getBookHistory("B1");
        assertEquals(2, history.size(), "Book history should contain two events");
        assertTrue(history.contains("borrowed"));
        assertTrue(history.contains("returned"));
    }

    @Test
    public void testGetBookHistory_Failure_BookNotFound() {
        Exception exception = assertThrows(Exception.class, () -> library.getBookHistory("B1"));
        assertEquals(" The following Book does not exist B1", exception.getMessage());
    }

}
