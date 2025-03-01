package controller;

import controller.filter.AmountFilter;
import controller.filter.CategoryFilter;
import controller.filter.TransactionFilter;
import view.ExpenseTrackerView;

import java.util.List;



import model.ExpenseTrackerModel;
import model.Transaction;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private final TransactionFilter categoryFilter;
  private final TransactionFilter amountFilter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
    this.categoryFilter = new CategoryFilter();
    this.amountFilter = new AmountFilter();

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }
  
  // Other controller methods
  public void applyFilter(String filter, List<Object> filterObject){
    // Get transactions from model
    List<Transaction> allTransactions = model.getTransactions();
    List<Transaction> filteredTransactions;
    switch(filter) {
      case "Category":
        filteredTransactions = categoryFilter.filter(allTransactions, filterObject);
        break;
      case "Amount":
        filteredTransactions = amountFilter.filter(allTransactions, filterObject);
        break;
      default:
        filteredTransactions = allTransactions;
    }
    // Pass to view
    view.refreshFilteredTable(allTransactions, filteredTransactions);
  }
}