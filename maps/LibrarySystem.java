package maps;

import java.util.*;

public class LibrarySystem {
        private Map<String, Book> books;
        private Set<String> availableBooks;

        public LibrarySystem() {
            this.books = new HashMap<>();
            this.availableBooks = new HashSet<>();
        }

        public boolean addBook(String bookId, String title) {
            if (books.containsKey(bookId)) {
                return false;
            }
            books.put(bookId, new Book(bookId, title));
            availableBooks.add(bookId);
            return true;
        }

        public List<String> listBooks() {
            List<String> titles = new ArrayList<>();
            for (String bookId : availableBooks) {
                titles.add(books.get(bookId).getTitle());
            }
            return titles;
        }

        public boolean borrowBook(String bookId) {
            if (!books.containsKey(bookId) || !availableBooks.contains(bookId)) {
                return false;
            }
            Book book = books.get(bookId);
            book.setStatus("borrowed");
            availableBooks.remove(bookId);
            book.addHistory("borrowed");
            return true;
        }

        public boolean returnBook(String bookId) {
            if (!books.containsKey(bookId) || "available".equals(books.get(bookId).getStatus())) {
                return false;
            }
            Book book = books.get(bookId);
            book.setStatus("available");
            availableBooks.add(bookId);
            book.addHistory("returned");
            return true;
        }

        public boolean reserveBook(String bookId) throws Exception{

            Book book = books.get(bookId);

            if(book == null) throw new Exception(" The following Book does not exist ".concat(bookId));

            if(book.getStatus().equals("borrowed") && !book.isReserved() ){
                book.setReserved(Boolean.TRUE);
                book.addHistory("reserved");
                return true;
            }
            
            return false;
        }


        public List<String> getBookHistory(String bookId) throws Exception{
            Book book = books.get(bookId);

            if(book == null) 
                throw new Exception(" The following Book does not exist ".concat(bookId));

            return book.getHistory();
            
        }

        

        public static void main(String[] args) {
            
        }
}

