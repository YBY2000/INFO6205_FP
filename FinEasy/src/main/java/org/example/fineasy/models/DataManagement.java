package org.example.fineasy.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.example.fineasy.entity.OperationRecord;
import org.example.fineasy.utils.ShowDialog;
import org.example.fineasy.utils.TransactionNotFoundException;

import java.time.LocalDate;


import static org.example.fineasy.entity.OperationType.ADD;
import static org.example.fineasy.entity.OperationType.DELETE;

/**
 * The class to manage all data in the system
 * It is the singleton that prevent from multiple access
 * Add transaction: call addTransaction(Transaction transaction), add record to list and BST
 * Delete transaction: call deleteTransaction(int transactionId), delete record from list
 * Modify transaction: TODO: use modifyTransaction(String transactionId, Transaction newTransactionData) to update transaction, (only detail data change, has no influence to BST)
 * Undo operation: call undoLastAction(), undo the latest operation
 * search transaction by id: call searchTransactionById(String id) to use BST for efficient search
 */
public class DataManagement {
    private static DataManagement instance; // Static instance variable

//    private final LinkedBag<Transaction> transactionList;
    private final LinkedList<Transaction> transactionList;
    private final UndoStack<OperationRecord> undoStack;
    private final BinarySearchTree<Transaction> bst;
    private final ObservableList<Transaction> transactionsObservable = FXCollections.observableArrayList();

    // Private constructor
    private DataManagement() {
        this.transactionList = new LinkedList<>();
        this.undoStack = new UndoStack<>();
        this.bst = new BinarySearchTree<>();
    } // end constructor

    // Public static method to get the instance
    public static DataManagement getInstance() {
        if (instance == null) {
            instance = new DataManagement();
            instance.addSampleTransactions();
        }
        return instance;
    } // end get Instance


    /**
     * Adds 10 sample transactions for testing or demonstration purposes.
     */
    public void addSampleTransactions() {
        addTransaction(new Transaction(1, "Expense", 50.0, LocalDate.now(), "Food", "Lunch with friends"));
        addTransaction(new Transaction(2, "Income", 1500.0, LocalDate.now(), "Education", "Monthly salary"));
        addTransaction(new Transaction(3, "Expense", 300.0, LocalDate.now(), "Food", "New headphones"));
        addTransaction(new Transaction(4, "Expense", 120.0, LocalDate.now(), "Education", "Weekly groceries"));
        addTransaction(new Transaction(5, "Income", 200.0, LocalDate.now(), "Education", "Freelance project"));
        addTransaction(new Transaction(6, "Expense", 60.0, LocalDate.now(), "Entertainment", "Cinema tickets"));
        addTransaction(new Transaction(7, "Expense", 400.0, LocalDate.now(), "Daily", "Weekend trip"));
        addTransaction(new Transaction(8, "Income", 250.0, LocalDate.now(), "Gift", "Birthday money"));
        addTransaction(new Transaction(9, "Expense", 100.0, LocalDate.now(), "Daily", "Pharmacy"));
        addTransaction(new Transaction(10, "Expense", 90.0, LocalDate.now(), "Transportation", "Programming books"));
    }

    /**
     * Returns an observable list of transactions for UI binding.
     * @return ObservableList of Transaction objects.
     */
    public ObservableList<Transaction> getTransactionsObservable() {
        return transactionsObservable;
    } // end getTransactionsObservable


    /**
     * Adds a new transaction to the system,
     * including the linked list bag, observable list for UI, binary search tree for efficient search, and undo stack for operation reversal.
     * also add to observable list, BST, and undo stack
     *
     * @param transaction The Transaction object to add
     */
    public void addTransaction(Transaction transaction) {
        undoStack.push(new OperationRecord(ADD, 0, transaction));
        transactionList.add(transaction);
        transactionsObservable.add(transaction);
        bst.insert(transaction);
    } // end addTransaction


    /**
     * Deletes a transaction by its ID. If the transaction is found, it removes the transaction from the system,
     * including the linked list, observable list for UI, and undo stack for operation reversal.
     * The transaction record is also deleted from the binary search tree for efficient search.
     *
     * @param transactionId The ID of the transaction to delete.
     * @throws TransactionNotFoundException If the transaction with the specified ID cannot be found.
     */
    public void deleteTransaction(int transactionId) throws TransactionNotFoundException
    {
        Transaction transactionToDelete = searchTransactionById(transactionId);
        if (transactionToDelete != null) {
            boolean isDeleted = bst.delete(transactionToDelete.getId());
            if (isDeleted) {
                int targetPosition = transactionList.getPosition(transactionToDelete);
                transactionsObservable.remove(transactionToDelete);
                transactionList.remove(targetPosition);
                undoStack.push(new OperationRecord(DELETE, targetPosition, transactionToDelete));
            } else {
                throw new TransactionNotFoundException("Failed to delete the transaction from BST.");
            }
        } else {
            String errMsg = "Transaction with ID " + transactionId + " not found.";
            ShowDialog.showAlert("Error", errMsg, Alert.AlertType.ERROR);
            throw new TransactionNotFoundException(errMsg);
        }
    } // end deleteTransaction


    /**
     * Undoes the last user operation, either adding or deleting a transaction, depending on what the last operation was.
     * @throws TransactionNotFoundException If there's an issue reverting the last operation, such as not finding the transaction to undo delete.
     */
    public void undoLastAction() throws TransactionNotFoundException {
        if (!undoStack.isEmpty()) {
            OperationRecord lastOperation = undoStack.pop();
            Transaction transaction = lastOperation.transaction();
            switch (lastOperation.operationType()) {
                case ADD -> {
                    // the latest operation is ADD, then delete the latest added transaction
                    transactionList.remove(transactionList.getPosition(transaction));
                    transactionsObservable.remove(transaction);
                    bst.delete(transaction.getId());
                }
                case DELETE -> {
                    // the latest operation is DELETE, then add the latest deleted transaction
                    transactionList.add(lastOperation.transactionPosition(), transaction);
                    transactionsObservable.add(transaction);
                    bst.insert(transaction);
                }
                // TODO: Handle EDIT case as needed
            }
        } else {
            String errMsg = "No operations to undo.";
            throw new TransactionNotFoundException(errMsg);
        } // end if(!undoStack.isEmpty()) - else
    } // end undoLastAction


    // use BST to search
    public Transaction searchTransactionById(int id) {
        return bst.search(id);
    } // end searchTransactionById

    // Retrieves the single instance of BinarySearchTree used for managing transactions within the application.
    public BinarySearchTree<Transaction> getBinarySearchTree() {
        return this.bst;
    }
} // end DataManagement
