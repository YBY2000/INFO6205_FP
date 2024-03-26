package org.example.fineasy.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Transaction {
    private String id; // 唯一标识符，便于查找和操作
    private String type; // 如收入或支出
    private double amount;
    private LocalDate date; // 使用简单的字符串表示日期，便于演示
    private String category;
    private String comment;

    public Transaction(String id, String type, double amount, LocalDate date, String category, String comment) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.comment = comment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    // 用于打印交易信息，方便调试
    @Override
    public String toString() {
        return String.format("Transaction{id='%s', type='%s', amount=%.2f, date='%s', category='%s', comment='%s'}",
                id, type, amount, date, category, comment);
    }
}


