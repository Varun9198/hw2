package controller.filter;

import model.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CategoryFilter implements TransactionFilter {

    private final String[] validWords = {"food", "travel", "bills", "entertainment", "other"};
    @Override
    public List<Transaction> filter(List<Transaction> allTransactions, List<Object> filterObject) {
        String filterCategory = filterObject.get(0).toString();
        return allTransactions
                .stream()
                .filter(t -> filterCategory.equals(t.getCategory()))
                .collect(Collectors.toList());
    }

}
