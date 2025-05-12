package com.spotify.models;

public class UpdateModel<T> {
    private Long id;
    private T oldData;
    private T newData;
    private boolean success;
    private String message;

    public UpdateModel() {
    }

    public UpdateModel(Long id, T oldData, T newData) {
        this.id = id;
        this.oldData = oldData;
        this.newData = newData;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public T getOldData() {
        return oldData;
    }

    public void setOldData(T oldData) {
        this.oldData = oldData;
    }

    public T getNewData() {
        return newData;
    }

    public void setNewData(T newData) {
        this.newData = newData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 