package controller.filter;

import model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilter implements TransactionFilter {

    @Override
    public List<Transaction> filter(List<Transaction> allTransactions, List<Object> filterObject) {
        return allTransactions
                .stream()
                .filter(t -> t.getAmount() >= (Integer) filterObject.get(0) && t.getAmount() <= (Integer) filterObject.get(1))
                .collect(Collectors.toList());
    }
}
