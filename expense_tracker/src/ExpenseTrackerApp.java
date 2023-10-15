import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Transaction;
import controller.InputValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

    view.getApplyFilterBtn().addActionListener(e -> {
        List<Object> filterObjects = new ArrayList<>();
        String filterType = Objects.requireNonNull(view.getFilterDropDown().getSelectedItem()).toString();
        switch (filterType) {
          case "Category":
            filterObjects.add(view.getCategoryFilterField().getText());
            break;
          case "Amount":
            filterObjects.add(view.getAmountFilterMinField().getText());
            filterObjects.add(view.getAmountFilterMaxField().getText());
            break;
        }
        controller.applyFilter(filterType, filterObjects);
    });

    view.getClearFilterBtn().addActionListener(e -> {
        controller.refresh();
    });
  }

}