package org.example.fineasy.entity;

import org.example.fineasy.entity.OperationType;
import org.example.fineasy.models.Transaction;


/**
 * It is a simply encapsulate data, the traditional class is convert to a record
 * method like equals(), hashCode(), and toString() is automatically implemented
 * @param operationType The type of user operation
 * @param transaction The transaction object that the operation performed on
 */
public record OperationRecord(OperationType operationType, Integer transactionPosition, Transaction transaction) { }
