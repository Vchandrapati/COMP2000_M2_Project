import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;

public class App {
    private Player player;
    private Storage storage;
    private JFrame frame;
    private PageManager manager;

    public App(Player p, Storage s) {
        player = p;
        storage = s;
        player.setStorageView(storage.getInventory());

        manager = new PageManager(player, storage);

        // Setup of sorting
        setupSearching((InventoryPage) manager.findPage("Player Inventory"));
        setupSearching((InventoryPage) manager.findPage("Storage"));

        // Setup of craftng
        setupCrafting((ItemCraftPage) manager.findPage("Item Crafting"), player);
        setupUncrafting((ProductPage) manager.findPage("Product Page"), player);

        // Window creation
        manager.refresh();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(manager.getJPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Task 1: Defining what each button in the UI will do.
    void setupSearching(InventoryPage page) {
        page.addSearchByButton(new SearchByButton("All", () -> {
            player.getInventory().setSearch(new AllSearchStrategy());
            player.getStorageView().setSearch(new AllSearchStrategy());
        }));

        page.addSearchByButton(new SearchByButton("Name", () -> {
            player.getInventory().setSearch(new NameSearchStrategy());
            player.getStorageView().setSearch(new NameSearchStrategy());
        }));

        page.addSearchByButton(new SearchByButton("Description", () -> {
            player.getInventory().setSearch(new DescriptionSearchStrategy());
            player.getStorageView().setSearch(new DescriptionSearchStrategy());
        }));
    }

    void setupCrafting(ItemCraftPage page, Player player) {
        page.setCraftAction((def) -> {
            if(def.isBaseItemDef()) return;

            List<String> requiredComponents = def.getComponentNames();
            Inventory playerInventory = player.getInventory();
            ArrayList<ItemInterface> components = new ArrayList<>();
            playerInventory.setSearch(new NameSearchStrategy());

            for(String componentName : requiredComponents) {
                ArrayList<ItemInterface> componentList = playerInventory.searchItems(componentName);
                if(!componentList.isEmpty()) {
                    ItemInterface component = playerInventory.remove(componentList.get(0));
                    components.add(component);
                } else {
                    if(!components.isEmpty())
                        for(ItemInterface component : components)
                            playerInventory.addOne(component);

                    components.clear();
                    throw new InsufficientItemsException(def, componentName);
                }
            }

            CompositeItem craftedItem = new CompositeItem(def);

            for(ItemInterface component : components)
                craftedItem.addComponent(component);

            playerInventory.addOne(craftedItem);
        });
    }

    void setupUncrafting(ProductPage page, Player player) {
        page.setUncraftAction((item) -> {
            Inventory playerInventory = player.getInventory();

            if(item instanceof CompositeItem compositeItem) {
                for(ItemInterface i : compositeItem.getBaseComponents()) playerInventory.addOne(i);
                playerInventory.remove(item);
            } else {
                throw new UncraftError(item);
            }
        });
    }
}
