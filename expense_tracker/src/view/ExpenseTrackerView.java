package view;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableModel;

import controller.InputValidation;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

import model.Transaction;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import javax.swing.JTable;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JComboBox<String> filterDropDown;
  private JFormattedTextField amountFilterMinField;
  private JFormattedTextField amountFilterMaxField;
  private JTextField categoryFilterField; //TODO: add validation
  private JButton applyFilterBtn; //TODO: add clear filter button
  private JButton clearFilterBtn;
  

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    String[] filters = {"Category", "Amount"}; //TODO: Add None
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);

    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    amountFilterMinField = new JFormattedTextField(format);
    amountFilterMinField.setColumns(10);
    amountFilterMaxField = new JFormattedTextField(format);
    amountFilterMaxField.setColumns(10);
    categoryFilterField = new JTextField();
    categoryFilterField = new JTextField(10);

    applyFilterBtn = new JButton("Apply Filter");
    clearFilterBtn = new JButton("Clear Filter");
    JLabel filterLabel = new JLabel("Filter:");
    filterDropDown = new JComboBox<>(filters);
    JPanel filterPanel = new JPanel();
    filterPanel.add(filterLabel);
    filterPanel.add(filterDropDown);

    filterPanel.add(amountFilterMinField).setVisible(false);
    filterPanel.add(amountFilterMaxField).setVisible(false);
    filterPanel.add(categoryFilterField).setVisible(false);
    filterPanel.add(applyFilterBtn);
    filterPanel.add(clearFilterBtn);

    filterDropDown.addItemListener(e -> {
      if(e.getStateChange() == ItemEvent.SELECTED) {
        if(filterDropDown.getSelectedItem() == "Category") {
          categoryFilterField.setVisible(true);
          amountFilterMinField.setVisible(false);
          amountFilterMaxField.setVisible(false);
        }
        if(filterDropDown.getSelectedItem() == "Amount") {
          categoryFilterField.setVisible(false);
          amountFilterMinField.setVisible(true);
          amountFilterMaxField.setVisible(true);
        }
        setVisible(true);
      }
    });

    JPanel inputFilterPanel = new JPanel();
    inputFilterPanel.setLayout(new BoxLayout(inputFilterPanel, BoxLayout.Y_AXIS));
    inputFilterPanel.add(inputPanel);
    inputFilterPanel.add(filterPanel);

    // Add panels to frame
    add(inputFilterPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);

    // Set frame properties
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()});
      }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
      transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
      // Fire table update
      transactionsTable.updateUI();

    }

    public void refreshFilteredTable(List<Transaction> transactions, List<Transaction> filteredTransactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()});
      }
      // Add total row
      Object[] totalRow = {"Total", null, null, totalCost};
      model.addRow(totalRow);

      transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
          java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

          // Highlight rows in green if they are in the filteredTransactions list
          if (row < filteredTransactions.size() && filteredTransactions.contains(transactions.get(row))) {
            cellComponent.setBackground(new Color(173, 255, 168));
          }

          return cellComponent;
        }
      });
      // Fire table update
      transactionsTable.updateUI();

  }

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public JButton getApplyFilterBtn() {
    return applyFilterBtn;
  }
  public JButton getClearFilterBtn() { return clearFilterBtn;}
  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }
}
