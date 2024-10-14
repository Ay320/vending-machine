import java.util.ArrayList;
import java.util.List;
/**
 * This class represents a vending machine that sells books to customers.
 * @author Aiham Ahmed
 */
public class VendingMachine{
    private List<Book> shelf;
    private double locationFactor;
    private int cassette;   // sum of coins inserted
    private int safe;
    private String password;
    
    /**
     * Constructs a VendingMachine object with the specified location factor and password.
     * @param locationFactor The factor used to calculate book prices based on location.
     * @param password   The password required by seller for certain operations.
     */
    public VendingMachine(double locationFactor, String password) { 
        this.locationFactor = locationFactor;
        this.password = password;
        this.cassette = 0;
        this.safe = 0;
        this.shelf = new ArrayList<>();
     }

     /**
     * Retrieves the total value of coins inserted into the vending machine.
     *
     * @return Total value of coins inserted.
     */
     public int getCassette(){
        return this.cassette;
     }

     /**
     * Inserts a coin into the vending machine.
     * Coins are added to the cassette.
     *
     * @param coin The value of the coin to be inserted.
     * @throws IllegalArgumentException If the coin denomination is not acceptable.
     */
     public void insertCoin(int coin){  
        switch (coin) {
            case 1:
            case 2:
            case 5:
            case 10:
            case 20:
            case 50:
            case 100:
            case 200:
            // If the coin value is acceptable, add it to the cassette
            cassette += coin;
            break;

            default:
            // If not acceptable, throw an IllegalArgumentException
            throw new IllegalArgumentException("Invalid coin denomination. Acceptable values are 1, 2, 5, 10, 20, 50, 100, and 200.");
    }
     }
     /**
     * Cancels the transaction and returns the total value of coins inserted.
     *
     * @return Total value of coins inserted before cancellation.
     */
     public int cancel(){
        int formerCassette = this.cassette;
        this.cassette = 0;
        return formerCassette;   // before cancelling. 
     }


     /**
     * Restocks the vending machine with a new stock of books.
     *
     * @param books    The list of books to be restocked.
     * @param password The password required to restock the vending machine.
     * @throws InvalidPasswordException If the provided password is incorrect.
     */
     public void restock(List<Book> books, String password){
        if (this.password.equals(password)){         
            shelf.addAll(books);
        }
        else{
            throw new InvalidPasswordException("Invalid password !!");
        }
     }

     /**
     * Empties the safe and returns the total revenue stored.
     *
     * @param password The password required to empty the safe.
     * @return Total revenue stored in the safe before emptying.
     * @throws InvalidPasswordException If the provided password is incorrect.
     */
     public int emptySafe(String password){
        int formerSafe=0;
        if(this.password.equals(password)){
            formerSafe = this.safe;
            this.safe = 0;
        }
        else{
            throw new InvalidPasswordException("Invalid password !!");
        }
      return formerSafe;
     }

     /**
     * Retrieves the catalogue of books available for sale.
     *
     * @return The list of book titles in the vending machine's shelf.
     */
     public List<String> getCatalogue(){
        List<String> catalogue = new ArrayList<>();

        for (Book book : shelf) {
            catalogue.add(book.toString());
        }
        return catalogue;
     }

     
     /**
     * Retrieves the price of a book at the specified index in the vending machine's shelf.
     *
     * @param index The index of the book in the vending machine's shelf.
     * @return The price of the book.
     * @throws IndexOutOfBoundsException If the specified index is out of bounds.
     */
     public int getPrice(int index){   
        if (index < 0 || index >= shelf.size()) {
            throw new IndexOutOfBoundsException("Invalid book index.");    //exit the method
        }

        Book currentBook = shelf.get(index);
        int pages = currentBook.getPages();
        int price = (int) Math.ceil(pages*locationFactor);
        return price;
     }


      /**
     * Allows a customer to buy a book from the vending machine.
     *
     * @param index The index of the book to be bought in the vending machine's shelf.
     * @return The book that was bought.
     * @throws IndexOutOfBoundsException If the specified index is out of bounds.
     * @throws CassetteException         If there are insufficient funds in the cassette to buy the book.
     */
     public Book buyBook(int index){   // exception
        if (index < 0 || index >= shelf.size()) {
            throw new IndexOutOfBoundsException("Invalid book index.");
        }

        Book bookToBuy = shelf.get(index);
        int price = getPrice(index);

        if (price > cassette) {
            throw new CassetteException("Insufficient funds in cassette.");
        }

        // if price is <= cassette:
        shelf.remove(index);
        cassette -= price;
        safe += price;

        return bookToBuy;
     }

     /*public static void main(String[] args) {
        VendingMachine s = new VendingMachine(1, "12345");
        List<Book> books = new ArrayList<Book>();
        books.add(new Book("book1", "author1", "content", 6));
        books.add(new Book("book2", "author2", "content", 5));
        books.add(new Book("book3", "author3", "content", 4)); 
        books.add(new Book("book4", "author4", "content", 3)); 
        books.add(new Book("book5", "author5", "content", 2));
        books.add(new Book("book6", "author6", "content", 1));  
        s.restock(books, "12345");
        System.out.println(s.getCatalogue());
        System.out.println(s.getPrice(0));
     } */
}