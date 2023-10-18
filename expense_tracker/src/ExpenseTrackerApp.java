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
        boolean filterConditionsValid = false;
        switch (filterType) {
          case "Category":
            Object category = view.getCategoryFilterField().getText();
            if(InputValidation.isValidCategory(category.toString())) {
              filterConditionsValid = true;
              filterObjects.add(category);
            }
            break;
          case "Amount":
            double min = Double.parseDouble(view.getAmountFilterMinField().getText());
            double max = Double.parseDouble(view.getAmountFilterMaxField().getText());
            if(InputValidation.isValidAmount(min) && InputValidation.isValidAmount(max) && min <= max){
              filterConditionsValid = true;
              filterObjects.add(min);
              filterObjects.add(max);
            }
            break;
        }
        if(filterConditionsValid) {
          controller.applyFilter(filterType, filterObjects);
        } else {
          JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
          view.toFront();
          controller.refresh();
        }
    });

    view.getClearFilterBtn().addActionListener(e -> {
        controller.refresh();
    });
  }

}