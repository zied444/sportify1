package com.spotify.models;

public class DeleteModel {
    private Long id;
    private String entityType;
    private boolean success;
    private String message;

    public DeleteModel() {
    }

    public DeleteModel(Long id, String entityType) {
        this.id = id;
        this.entityType = entityType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
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