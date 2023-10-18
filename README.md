# Added functionalities and changes in Expense Tracker for hw2

The major new functionality added in this iteration of the Expense Tracker app is the functionality of filtering the records. This includes filtering based on Amount and Category of a transaction.
The user when trying to filter the transaction by amount can select the filter type as Amount and then enter the range, whereas when trying to filter the transaction by category can select the filter type as Category and then enter the category value.
The amount range and the category should be valid else the application will display an error. If the filter succeeds to find any records, it highlights it in green colour.

## Filtering Implementation

The changes were required in all three components of the application, model, view and controller. The changes were as follows -

### Model

This change required making of a new function named refreshFilteredTable() which can display the results after the filter is applied. This function has a logic of colouring the filtered rows.

### View

Since new fields for filtering are added in the view, the code changes required for adding the fields and drop down is done in the view. Their getter and setter methods are also added as and when needed.
Another change in the view is that Add Transaction button is moved next to the Amount and Category fields for the ease of the user in adding a transaction.

### Controller

There is a significant change in the main() function of the ExpenseTrackerApp class as the listeners to the new buttons for applying and clearing the filter are added to it.
New buttons for applying and clearing the filter are added with their getters. The applyFilter() function that applies the filters is also added.

## Other Minor changes

### Applying Open-Close Principle

To enable modularity in the code, data encapsulation and immutability were established for the Transactions data. 

### Updated the README.MD file 

This file was updated to capture the changes made in the code.

### javadocs

Javadocs were generated and committed in the code.