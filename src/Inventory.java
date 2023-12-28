import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class Inventory implements Iterable<ItemInterface> {
    private final ArrayList<ItemInterface> stock;
    private SearchStrategy searchBy;

    public Inventory() {
        stock = new ArrayList<>();
        searchBy = new AllSearchStrategy();
    }

    public Inventory(ArrayList<ItemInterface> startingStock) {
        stock = startingStock;
        searchBy = new AllSearchStrategy();
    }

    /**
     * Removes and returns the first Item instance that matches the
     * provided 'itemDefinition'.
     * Throws an ItemNotAvailableException if the `item` is not present in the inventory.
     * @param itemDefinition
     * @return Item instance matching `item` parameter definition
     * @throws ItemNotAvailableException
     */
    public ItemInterface removeOne(ItemDefinition itemDefinition) throws ItemNotAvailableException {
        Optional<Integer> removeFromIdx = indexOfItemByName(itemDefinition);
        if (removeFromIdx.isEmpty()) {
            throw new ItemNotAvailableException(itemDefinition);
        }

        return stock.remove((int) removeFromIdx.get());
    }

    public ItemInterface remove(ItemInterface item) throws ItemNotAvailableException {
        // Check if the provided item exists in the players inventory
        Optional<Integer> removeFromIdx = Optional.empty();
        for (int i = 0; i < stock.size(); i++) {
            if (stock.get(i) == item) {
                removeFromIdx = Optional.of(i);
                break;
            }
        }
        if (removeFromIdx.isEmpty()) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        return stock.remove(removeFromIdx.get().intValue());
    }

    /**
     * Adds an Item instance to the inventories stock.
     * Sort is called using the current/existing sort strategy.
     * @param item - actual instance
     */
    public void addOne(ItemInterface item) {
        stock.add(item);
    }

    /**
     * Search for `item` in the inventory stock.
     * @param item definition
     * @return index of `item` or empty optional if `item` not in stock
     */
    private Optional<Integer> indexOfItemByName(ItemDefinition item) {
        int index = 0;
        for (ItemInterface cur : this) {
            if (cur.getName().equals(item.getName()))
                return Optional.of(index);

            index++;
        }
        return Optional.empty();
    }

    public void setSearch(SearchStrategy strategy) {
        this.searchBy = strategy;
    }

    /**
     * Search for items using the current search criteria in the Inventory.
     * An instance copy is made, such that the items that the inventory is not
     * lost when removed from the resulting ArrayList.
     * @param searchTerm - Text from the UIs textfield
     * @return a filtered instance copy of the items arraylist
     */
    public ArrayList<ItemInterface> searchItems(String searchTerm) {
        String term = searchTerm.toLowerCase();
        ArrayList<ItemInterface> result = new ArrayList<>(stock);  // ArrayList copy
        return searchBy.search(result, term);
    }

    public int qtyOf(ItemDefinition def) {
        int qty = 0;
        for (ItemInterface item : this) {
            if (item.getName().equals(def.getName())) {
                qty++;
            }
        }
        return qty;
    }

    @Override
    public String toString() {
        String str = "";
        for (ItemInterface item : this) {
            str += item.toString() + "\n\n";
        }
        return str;
    }

    @Override
    public InventoryIterator iterator() {
        return new InventoryIterator(stock);
    }

    public boolean isEmpty() {
        return stock.isEmpty();
    }
}
