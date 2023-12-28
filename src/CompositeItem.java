import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositeItem extends Item {
    ArrayList<ItemInterface> baseComponents;

    public CompositeItem(ItemDefinition def) {
        super(def);
        baseComponents = new ArrayList<>();
    }

    @Override
    public double getWeight() {
        double weight = 0;
        for(ItemInterface i : baseComponents)
            weight += i.getWeight();

        return weight;
    }

    public List<ItemInterface> getBaseComponents() {
        return Collections.unmodifiableList(baseComponents);
    }

    @Override
    public String getCompositionDescription() {
        StringBuilder desc = new StringBuilder();
        for(ItemInterface component : baseComponents) {
            desc.append(component.getName()).append(' ');
        }

        return desc.toString();
    }

    public void addComponent(ItemInterface component) {
        baseComponents.add(component);
    }

    public void removeComponent(ItemInterface component) {
        baseComponents.remove(component);
    }
}
