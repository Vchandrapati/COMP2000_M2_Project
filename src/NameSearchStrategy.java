import java.util.ArrayList;

public class NameSearchStrategy implements SearchStrategy {
    @Override
    public ArrayList<ItemInterface> search(ArrayList<ItemInterface> result, String term) {
        for (int i = 0; i < result.size(); i++) {
            ItemInterface curItem = result.get(i);
            if (!curItem.getName().contains(term)) {
                result.remove(i);
                i--;  // Go back to revisit current index on next run of loop
            }
        }

        return result;
    }
}
