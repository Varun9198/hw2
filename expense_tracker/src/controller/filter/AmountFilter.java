package controller.filter;

import model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilter implements TransactionFilter {

    @Override
    public List<Transaction> filter(List<Transaction> allTransactions, List<Object> filterObject) {
        double min = new Double(filterObject.get(0).toString());
        double max = new Double(filterObject.get(1).toString());
        return allTransactions
                .stream()
                .filter(t -> t.getAmount() >= min && t.getAmount() <= max)
                .collect(Collectors.toList());
    }
}
