package com.spotify.models;

import java.util.List;

public class DisplayModel<T> {
    private List<T> items;
    private int totalItems;
    private int currentPage;
    private int totalPages;
    private String sortBy;
    private String sortDirection;

    public DisplayModel() {
    }

    public DisplayModel(List<T> items, int totalItems, int currentPage, int totalPages) {
        this.items = items;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    // Getters and Setters
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
} 