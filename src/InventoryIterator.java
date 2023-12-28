import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class InventoryIterator implements Iterator<ItemInterface> {
    private final ArrayList<ItemInterface> stock;
    private int position = 0;

    InventoryIterator(ArrayList<ItemInterface> stock) {
        this.stock = stock;
    }

    @Override
    public boolean hasNext() {
        return position < stock.size();
    }
    @Override
    public ItemInterface next() {
        if(hasNext()) return stock.get(position++);

        throw new NoSuchElementException("No more items in Inventory");
    }
}
