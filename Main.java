import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Test the Press class
        Press press = new Press("C:\\Users\\wwii4\\Desktop\\javatest\\Ass\\Ass3\\Books", 5);
        List<String> catalogue = press.getCatalogue();
        System.out.println("Catalogue: " + catalogue);

        // Test the request method of Press class
        List<Book> requestedBooks = press.request("11-0.txt", 3);
        System.out.println("Requested Books: " + requestedBooks);
         requestedBooks = press.request("12-0.txt", 3);
        System.out.println("Requested Books: " + requestedBooks);

        // Test the VendingMachine class
        VendingMachine vendingMachine = new VendingMachine(1.5, "password123");
        List<Book> books = new ArrayList<>();
        books.add(new Book("book1", "author1", "content1", 1));
        books.add(new Book("book2", "author2", "content2", 2));
        vendingMachine.restock(books, "password123");

        // Insert coins into the cassette
        vendingMachine.insertCoin(5); 
        vendingMachine.insertCoin(10); 


        // Test the getCatalogue method of VendingMachine class
        List<String> vendingMachineCatalogue = vendingMachine.getCatalogue();
        System.out.println("Vending Machine Catalogue: " + vendingMachineCatalogue);

        // Test the getPrice method of VendingMachine class
        int price = vendingMachine.getPrice(0);
        System.out.println("Price of book at index 0: " + price);

        // Test the buyBook method of VendingMachine class
        Book purchasedBook = vendingMachine.buyBook(0);
        System.out.println("Purchased Book: " + purchasedBook);
    }
}
