# Book Vending Machine Project

This project implements a system for printing literary classics with expired copyrights (Project Gutenberg) and selling them through vending machines. It consists of three main components: **Books**, **Vending Machines**, and a **Printing Press**.

## Overview 

1. **Book**: Represents an individual book with attributes such as title, author, content, and edition.
2. **VendingMachine**: Represents a vending machine that stores books and sells them to customers based on location and pricing.
3. **Press**: Represents a printing press that prints books in batches and manages their storage until sold.

## File Descriptions

### 1. Book.java
- **Description**: Represents an individual book with attributes for title, author, content, and edition.
- **Key Features**:
  - Accessor methods: `getTitle()`, `getAuthor()`, `getContent()`, `getEdition()`.
  - Method `getPages()` to calculate the number of pages based on content length.
  - Overridden `toString()` method for formatted output.

### 2. VendingMachine.java
- **Description**: Represents a vending machine that holds books and manages sales.
- **Key Features**:
  - Stores books in a list and manages pricing based on location factor.
  - Methods to insert coins, cancel sales, restock books, empty the safe, and get the catalogue of books.
  - Exception handling for invalid operations (e.g., invalid coins, invalid password).

### 3. Press.java
- **Description**: This class provides methods to print books in batches. Books are to be stored until disributed to vending machines.
- **Key Features**:
  - Maintains a shelf for storing books and tracks editions.
  - Methods to request books, print new books from files, and get a catalogue of available books.
  - Reads book information from text files using regular expressions.

### 4. InvalidPasswordException.java
- **Description**: Custom exception class to handle invalid password scenarios during restocking and accessing the safe.
- **Key Features**:
  - Extends `RuntimeException` to provide unchecked exception handling.

### 5. CassetteException.java
- **Description**: Custom exception class for handling cases where there are insufficient funds in the cassette to purchase a book.
- **Key Features**:
  - Extends `RuntimeException` for flexible error handling during book purchases.

### 6. Main.java (Optional)
- **Description**: Contains the main method to test and run the functionality of the vending machine, printing press, and book handling. A file for sample Books is provided in the repositry for testing.

