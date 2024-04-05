package org.example.fineasy.models;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Transaction implements Comparable<Transaction>{
    private final StringProperty id;
    private final StringProperty type;
    private final DoubleProperty amount;
    private final StringProperty category;
    private final StringProperty comment;
    private final ObjectProperty<LocalDate> date;

    public Transaction(String id, String type, double amount, LocalDate date, String category, String comment) {
        this.id = new SimpleStringProperty(id);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.category = new SimpleStringProperty(category);
        this.comment = new SimpleStringProperty(comment);
        this.date = new SimpleObjectProperty<>(date);
    }


    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }


    @Override
    public String toString() {
        return String.format("Transaction{id='%s', type='%s', amount=%.2f, date='%s', category='%s', comment='%s'}",
                id, type, amount, date, category, comment);
    } // end toString

    public static Comparator<Transaction> getAmountComparator() {
        return Comparator.comparing(Transaction::getAmount);
    }

    public static Comparator<Transaction> getDateComparator() {
        return Comparator.comparing(Transaction::getDate);
    }

    public static Comparator<Transaction> getTypeComparator() {
        return Comparator.comparing(Transaction::getType);
    }

    public static Comparator<Transaction> getCategoryComparator() {
        return Comparator.comparing(Transaction::getCategory);
    }
    @Override
    public int compareTo(Transaction other) {
        //Compare Amount
        return Double.compare(this.getAmount(), other.getAmount());
    }
} // class Transaction


