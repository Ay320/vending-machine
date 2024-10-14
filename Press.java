import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SecureDirectoryStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * This class provides methods to print books in batches.
 * Books are to be stored until disributed to vending machines.
 * @author Aiham Ahmed
 */
public class Press{
    private Map<String , List<Book>> shelf; // for each book, there are many copies of it. String is book id.
    private int shelfSize;   // max num of copies for each book
    private Map<String, Integer> edition; // Map to store the edition of each book
    private String pathDir;  // to store path for later use.
     
    /**
     * Constructor with specified directory and a shelf size.
     * 
     * @param pathToBooKDir The path to the directory containing book files.
     * @param shelfSize The maximum number of copies for each book.
     */
    public Press(String pathToBooKDir, int shelfSize) { 
        this.shelfSize = shelfSize;
        shelf = new HashMap<>();
        edition = new HashMap<>();
        pathDir = pathToBooKDir;

        // Look up all possible book IDs in the directory:
        try{
        File directory = new File(pathToBooKDir);
        if (!directory.exists() || !directory.isDirectory()) {
            return; // If directory doesn't exist or is not a directory, return
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return; // If directory is empty, return
        }

        for (File file : files) {
            String bookID = file.getName();
            edition.put(bookID, 0); // Set initial edition to 0 for each book
            shelf.put(bookID, new ArrayList<>()); // Initialize shelf for each book as empty list
            }
        
        } catch(Exception e){
            System.out.println(e.getMessage());  // print any exception if occured.
        }
        
        }

        /**
         * Prints a book with the given bookID and edition number.
         * If print is called, all books will have the same edition.
         * @param bookID The ID of the book to print (.txt)
         * @param edition The edition number of the book to print.
         * @return The printed Book object.
         */
        protected Book print(String bookID, int edition) {
            //String filePath = "C:\\Users\\wwii4\\Desktop\\javatest\\Ass\\Ass3\\Books\\" + bookID;  // not sure
            String filePath = pathDir +File.separator+ bookID;
            

            try {
            // Read the entire content of the file into a string using UTF-8 encoding
            BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
            StringBuilder fileContent = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            reader.close();


            // Find the start of the book content
            int startOfContentIndex = fileContent.indexOf("*** START OF");
            if (startOfContentIndex == -1) { // if not found
                throw new IOException("Invalid content format in file: " + bookID);
            }

            // Extract title and author from the header
            String header = fileContent.substring(0, startOfContentIndex);
            String title = extractValue(header, "Title:");
            String author = extractValue(header, "Author:");

            // Extract book content
            String content = fileContent.substring(startOfContentIndex);

            // Create and return the Book object
            return new Book(title, author, content, edition);

            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        // Helper method to extract value from header using regular expressions
    /**
     * Helper method to extract the value associated with the given key from the header using regular expressions.
     * This method is used to find the Author, and Title of the book.
     *
     * @param header initiale pages of the book that containes info SA Author,Title...
     * @param key    The key to search for in the header string.
     * @return The Author, Title of the book.
     */
    private String extractValue(String header, String key) {
        String regex = key + "\\s*(.+)";   // regular expression.
        String value = "";  // will hold the extracted value from the header.

        // Use regex to find the value associated with the key
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(header);

        if (matcher.find()) {
            value = matcher.group(1).trim();
        }
        return value;
    }
 
    /**
     * Retrieves the list of book IDs available in the press.
     *
     * @return list of book IDs.
     */
    public List<String> getCatalogue() {
        return new ArrayList<>(edition.keySet());
    }


     /**
     * Requests a certain number of copies of a book with the given bookID.
     *
     * @param bookID The ID of the book to request.
     * @param amount The number of copies to take.
     * @return The list of requested books. Could be newly printed or from the shelf.
     */
    public List<Book> request(String bookID, int amount) {
        List<Book> requestedBooks = new ArrayList<>();  // empty list

        try {
            // Check if the bookID exists in the edition map
            if (!edition.containsKey(bookID)) {
                throw new IllegalArgumentException("Book with ID " + bookID + " is not in print.");
            }

            // Determine the edition number of the requested book
            int bookEdition = edition.get(bookID);

            // Retrieve books from the shelf if available.
            List<Book> shelfBooks = shelf.getOrDefault(bookID, new ArrayList<>());
            //int booksInStock = shelf.get(bookID).size();
            //System.out.println(shelfBooks.size()); =0
            int booksInStock = shelfBooks.size();
            int booksToRetrieve = Math.min(amount, booksInStock);
            for (int i = 0; i < booksToRetrieve; i++) {
                //requestedBooks.add(shelf.get(bookID).remove(0));
                requestedBooks.add(shelfBooks.remove(0));
                //System.out.println("check");
            }

            // Check if additional books need to be printed
            int remainingAmount = amount - requestedBooks.size();
            //System.out.println(remainingAmount);
            if (remainingAmount > 0) {
                for (int i = 0; i < remainingAmount; i++) {
                    Book printedBook = print(bookID, bookEdition + 1);
                    if (printedBook != null) {
                        requestedBooks.add(printedBook);
                    } else {
                        break; // Stop printing if an error occurs
                    }
                }
            }

            // Update edition number if new books were printed
            if (requestedBooks.size() > booksToRetrieve) {
                //System.out.println(bookEdition);
                bookEdition++;
                edition.put(bookID, bookEdition );
                //System.out.println(bookEdition);
            }

            // Restock the shelf if needed
            if (shelf.get(bookID).isEmpty()) {
                bookEdition++;
                restockShelf(bookID, bookEdition );
            }
            //System.out.println("%"+shelf.get(bookID));
            
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return requestedBooks;
    }


    /**
     * Restocks the shelf if empty with books of the specified edition.
     * This is a helper method used in the Reaquest method.
     *
     * @param bookID  The ID of the book to restock.
     * @param edition The edition number of the book to restock.
     */
    private void restockShelf(String bookID, int edition) {
        List<Book> printedBooks = new ArrayList<>();
        for (int i = 0; i < shelfSize; i++) {
            Book printedBook = print(bookID, edition);
            if (printedBook != null) {
                printedBooks.add(printedBook);
            } else {
                break; // Stop printing if an error occurs
            }
        }
        shelf.put(bookID, printedBooks);
    }

}

