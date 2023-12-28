public class InsufficientItemsException extends Exception {
    public InsufficientItemsException(String errorMessage) {
        super(errorMessage);
    }

    public InsufficientItemsException(ItemDefinition item, String missingItem) {
        super(String.format(
                "Could not craft %s: missing %s",
                item.getName(), missingItem
        ));
    }
}
