package resources;

import java.util.List;

public class UserResponse {
    private String userID;
    private String username;
    private List books;
    private String password;


    public UserResponse(String userID, String username, List books) {
        this.userID = userID;
        this.username = username;
        this.books = books;
    }

    public UserResponse() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List getBooks() {
        return books;
    }

    public void setBooks(List books) {
        this.books = books;
    }
}
