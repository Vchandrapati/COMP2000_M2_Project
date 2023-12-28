public class UncraftError extends Exception {
    public UncraftError(String errorMessage) {
        super(errorMessage);
    }

    public UncraftError(Item item) {
        super(String.format(
                "%s could not be broken down as it is not a craftable item",
                item.getName()
        ));
    }
}
