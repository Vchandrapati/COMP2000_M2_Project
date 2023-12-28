import java.util.*;

public class ItemDefinition {
    private String name;
    private String description;
    private String[] componentNames;
    private boolean isBaseItem;
    private Optional<Double> weight;

    public ItemDefinition(String n, String desc, Optional<Double> weightIfBase, String[] components) {
        name = n;
        description = desc;
        componentNames = components;
        isBaseItem = weightIfBase.isPresent();
        weight = weightIfBase;

        // This may be helpful for the compsite pattern to find the appropriate item definitions
        ItemDictionary dict = ItemDictionary.get();
    }

    /**
     * Create an instance of the item described by this ItemDefinition.
     * If the Item is made up of other items, then each sub-item should also be created.
     * @return An Item instance described by the ItemDefinition
     */
    public ItemInterface create() {
        ItemDictionary dict = ItemDictionary.get();
        if(componentNames.length == 0)
            return new Item(this);
        else {
            CompositeItem compositeItem = new CompositeItem(this);
            List<String> componentNames = this.getComponentNames();

            for(String component : componentNames)
                dict.defByName(component).ifPresent(def -> compositeItem.addComponent(new Item(def)));

            return compositeItem;
        }
    }

    // ItemDefinition might "craft" and return an item, using items from some source inventory.
    // You might use the Milestone 1 Basket transaction code as a guide

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Format: {ITEM 1}, {ITEM 2}, ...
     * @return a String of sub-item/component names in the above format
     */
    public String componentsString() {
        String output = "";
        for (String componentName : componentNames) {
            output += componentName + ", ";
        }
        return output;
    }

    public List<String> getComponentNames() {
        return Collections.unmodifiableList(Arrays.asList(componentNames));
    }

    public boolean isBaseItemDef() {
        return isBaseItem;
    }

    public Optional<Double> getWeight() {
        return weight;
    }

    public boolean equals(Item other) {
        return isOf(other.getDefinition());
    }

	public boolean isOf(ItemDefinition def) {
		return getName().equals(def.getName());
	}

}
