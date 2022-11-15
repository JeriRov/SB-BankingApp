package com.ipz.mba.exceptions;

public class TransactionFailedException extends Exception {
    public TransactionFailedException(String message) {
        super(message);
    }
}
