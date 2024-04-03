package org.example.fineasy.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.fineasy.Utils.TransactionNotFoundException;

public class DataManagement {
    private LinkedBag<Transaction> transactions;
    private UndoStack<OperationRecord> undoStack;
    private BinarySearchTree bst; // Binary Search实例
    @FXML
    private TableView<Transaction> transactionTable;

    //添加数据监听和获取功能
    private final ObservableList<Transaction> transactionsObservable = FXCollections.observableArrayList();

    public ObservableList<Transaction> getTransactionsObservable() {
        return transactionsObservable;
    }

    public void updateTransactionsView() {
        transactionTable.setItems(DataManagementSingleton.getInstance().getTransactionsObservable());
    }


    public DataManagement() {
        this.transactions = new LinkedBag<>();
        this.undoStack = new UndoStack<>();
        this.bst = new BinarySearchTree(); // 初始化BST
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transactionsObservable.add(transaction); // Add to observable list
        bst.insert(transaction); // Insert into BST
//        undoStack.push(() -> {
//            transactions.remove(transaction);
//            transactionsObservable.remove(transaction); // Remove from observable list
//        });
        // TODO: operation_type写成enum, 1:add, 2: delete, 3:edit, 4:reorder
        undoStack.push(new OperationRecord(OperationRecord.OperationType.ADD, transaction));
    }


//    public void deleteTransaction(String transactionId) throws TransactionNotFoundException {
//        Transaction transaction = searchTransactionById(transactionId);
//        if (transaction == null) {
//            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found.");
//        }
//        transactions.remove(transaction);
//        // TODO: operation_type写成enum, 1:add, 2: delete, 3:edit, 4:reorder
//        undoStack.push(new OperationRecord(2, transaction));
////        undoStack.push(() -> transactions.add(transaction));
//        // 注意：在实际应用中，你可能还需要处理BST中的删除
//    }
public void deleteTransaction(String transactionId) throws TransactionNotFoundException {
    Transaction transactionToDelete = null;
    for (Transaction transaction : transactionsObservable) {
        if (transaction.getId().equals(transactionId)) {
            transactionToDelete = transaction;
            break;
        }
    }
    if (transactionToDelete != null) {
        transactionsObservable.remove(transactionToDelete);
        // Push an undo operation for deletion
        undoStack.push(new OperationRecord(OperationRecord.OperationType.DELETE, transactionToDelete));
    } else {
        throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found.");
    }
}

    public void undoLastAction() throws TransactionNotFoundException {
        if (!undoStack.isEmpty()) {
            OperationRecord lastOperation = undoStack.pop();
            switch (lastOperation.getOperationType()) {
                case ADD:
                    // Undo the addition by removing the transaction
                    transactions.remove(lastOperation.getTransaction());
                    transactionsObservable.remove(lastOperation.getTransaction());
                    // Optionally, handle BST removal here
                    break;
                case DELETE:
                    // Undo the deletion by re-adding the transaction
                    transactions.add(lastOperation.getTransaction());
                    transactionsObservable.add(lastOperation.getTransaction());
                    // Optionally, re-insert into BST here
                    break;
                case EDIT:
                    // Undo editing (requires storing previous state in OperationRecord)
                    break;
            }
        }
    }


//    public void modifyTransaction(String transactionId, Transaction newTransactionData) throws TransactionNotFoundException {
//        boolean found = false;
//        for (int i = 0; i < transactions.size(); i++) {
//            if (transactions.get(i).getId().equals(transactionId)) {
//                found = true;
//                Transaction oldTransaction = transactions.get(i);
//                bst.delete(oldTransaction); // 先删除旧节点
//                bst.insert(newTransactionData); // 再插入新节点
//                transactions.set(i, newTransactionData);
//                undoStack.push(() -> {
//                    transactions.set(transactions.indexOf(newTransactionData), oldTransaction);
//                    bst.delete(newTransactionData); // 撤销时也要维护BST的一致性
//                    bst.insert(oldTransaction);
//                });
//                break;
//            }
//        }
//        if (!found) {
//            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found.");
//        }
//    }



//    public void undoLastAction() {
//        if (!undoStack.isEmpty()) {
//            undoStack.pop().run();
//        }
//    }

//    private Transaction findTransactionById(String id) {
//        // TODO:
//        return transactions.stream()
//                .filter(transaction -> transaction.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }


    // 打印所有交易记录，方便调试
//    public void printTransactions() {
//        transactions.forEach(System.out::println);
//    }

    // 使用BST进行搜索
//    public Transaction searchTransactionById(String id) {
//        return bst.search(id);
//    }


//    public void sortTransactionsByDate() {
//        DateSortingStrategy sortingStrategy = new DateSortingStrategy();
//        sortingStrategy.sort(transactions);
//    }
}

//添加交易记录: 调用 addTransaction(Transaction transaction) 方法，它将交易记录添加到列表和BST中。
//删除交易记录: 调用 deleteTransaction(String transactionId) 方法，它将从列表中删除交易记录。为简化示例，我们没有在BST中实现删除功能。
//修改交易记录: 调用 modifyTransaction(String transactionId, Transaction newTransactionData) 方法，它将更新列表中的交易记录。修改操作不会影响BST，因为ID不会改变。
//撤销上一操作: 调用 undoLastAction() 方法，它将撤销上一次添加或删除操作。
//按ID搜索交易记录: 使用 searchTransactionById(String id) 方法，它将通过BST进行高效搜索。