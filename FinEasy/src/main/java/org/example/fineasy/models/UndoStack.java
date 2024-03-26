package org.example.fineasy.models;

import java.util.Stack;

public class UndoStack {
    private Stack<Transaction> transactions;

    public UndoStack() {
        this.transactions = new Stack<>();
    }

    // 添加、撤销操作等方法
}

