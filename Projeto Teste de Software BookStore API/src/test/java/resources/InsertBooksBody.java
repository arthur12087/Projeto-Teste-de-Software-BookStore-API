package resources;

import java.util.ArrayList;
import java.util.List;

public class InsertBooksBody {
    private String userId;
    private ArrayList collectionOfIsbns;

    public InsertBooksBody(String userId, ArrayList collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public InsertBooksBody() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(ArrayList collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }
}
