import java.util.ArrayList;

public interface SearchStrategy {
    ArrayList<ItemInterface> search(ArrayList<ItemInterface> result, String term);
}
