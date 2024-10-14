/**
 * This class represents a book with Title, Author, Edition, and Content.
 * @author Aiham Ahmed
 */
public class Book{
    private String title;
    private String author;
    private String content;
    private int edition;


    /**
     * Constructs a Book object with the specified title, author, content, and edition.
     *
     * @param t The title of the book.
     * @param a The author of the book.
     * @param c The content of the book.
     * @param e The edition number of the book.
     */
    public Book(String t, String a, String c, int e){
        this.title = t;
        this.author = a;
        this.content = c;
        this.edition = e;
    }

    /**
     * A method to get the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle(){
        return this.title;
    }
    /**
     * A method to get the author of the book.
     *
     * @return The author of the book.
     */
    public String getAuthor(){
        return this.author;
    }
    /**
     * A method to get the content of the book.
     *
     * @return The content of the book.
     */
    public String getContent(){
        return this.content;
    }
    /**
     * A method to get the edition number of the book.
     *
     * @return The edition number of the book.
     */
    public int getEdition(){
        return this.edition;
    }

    
    /**
     * Calculates the number of pages in the book based on its content.
     * Each page have 750 chars.
     * @return The number of pages in the book.
     */
    public int getPages(){
        int chars=0;
        for (int i=0; i<this.content.length(); i++){
            if (content.charAt(i) != ' ') {
                chars++;
        }
    }
    int pages = (int) Math.ceil((double)chars / 750);  // rounded up.
    return pages;
   }

    /**
     * Returns a string representation of the book.
     *
     * @return A string containing the title, author, and edition of the book.
     */
   public String toString(){
    String output = "Title: " + title + "\n" +
                    "Author: " + author + "\n" +
                    "Edition: " + edition + "\n";
    return output;
  }
}
