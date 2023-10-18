package controller.filter;

import model.Transaction;

import java.util.List;

public interface TransactionFilter {

    List<Transaction> filter(List<Transaction> allTransactions, List<Object> filterObject);
}
