# Task 1
Behavioural pattern - Options: *Strategy* or *Observer* pattern.
You chose: Strategy

## Itemise changes made (which class files were modified)
1. Inventory.java
2. SearchStrategy.java
3. NameSearchStrategy.java
4. AllSearchStrategy.java
5. DescriptionSearchStrategy.java
6. App.java

# Task 2
Structural pattern - *Composite* pattern.

## Itemise changes made (which class files were modified)
1. CompositeItem.java
2. Item.java
3. ItemDefinition.java
4. App.java

# Task 3

## Itemised changes or new files
1. App.java
2. UncraftError.java
3. InventoryIterator.java
4. Inventory.java
5. CompositeItem.java
6. ItemDefinition.java
7. Reader.java

## What was changed
1. App.java crafting method now calls uncraft error if an item cant be un-crafted for any reason
2. Addition of iterator pattern in inventory
3. ItemDefinition's create function now creates items as a composite item if it has components
4. Read player class now checks if player inventory full
5. adjustStartingInventory fixes overweight inventory

## Why it was changed
1. To create more clarity in error handling and the issue more clear
2. Consistency in error handling, in line with other custom errors
3. Improved debugging
4. To allow for a better implementation of inventory function and provide better code maintainability
5. To ensure any items that are pulled from the config are correctly created and are displayed correctly and can be dismantled if able
6. To ensure that the players starting inventory can no longer be overweight and sends items to storage automatically if it is

## The benefits of the change
1. The new exception provides a clear and concise method letting the player know why the un-crafting procedure failed
2. It improves UX if they are aware of what is wrong
3. Makes the code easier to understand and maintain since any calls to the function will allow the dev to understand what the code is doing
4. Pattern allows for better access to inventory and a more unified approach to iterating through inventory if any future methods require it creating better maintainability
5. pattern allows for better data encapsulation within inventory
6. The bug was that when a craftable item was created from the config file the instantiation didnt differentiate between whether or not it was a composite or regular item meaning if for example the config file was a save file it would mean if an item was crafted and saved, the next time the file is called the item would be created as a regular item and as such wouldnt have the ability to uncraft, fixing this bug means that composite items in the config file or in any future systems will be created and entered correctly.
7. The change now fixed a bug where the player could start with more items in their starting inventory if they were listed in the config file, this change ensures that the player weight restriction is adhered to and sends any items that may cause an overweight issue to the storage automatically for the player to retrieve if needed.