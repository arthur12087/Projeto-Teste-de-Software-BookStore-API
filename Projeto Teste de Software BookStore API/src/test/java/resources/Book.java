package resources;

public class Book {
    private String isbn;

    public Book(String isbn) {
        this.isbn = isbn;
    }

    public Book() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
