package resources;

import java.util.ArrayList;
import java.util.List;

public class ListBooks {
    private ArrayList books;

    public ListBooks(ArrayList books) {
        this.books = books;
    }

    public ListBooks() {
    }

    public ArrayList getBooks() {
        return books;
    }

    public void setBooks(ArrayList books) {
        this.books = books;
    }
}
