package com.toyota.rentalcar.dev.commons.exceptions;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String msg){
        super(msg);
    }

    public FileStorageException(String msg, Throwable cause){
        super(msg, cause);
    }
}
