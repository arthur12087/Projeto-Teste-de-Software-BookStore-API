package resources;

public class BookToDelete {
    private String isbn;
    private String userId;

    public BookToDelete(String isbn, String userId) {
        this.isbn = isbn;
        this.userId = userId;
    }

    public BookToDelete() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
