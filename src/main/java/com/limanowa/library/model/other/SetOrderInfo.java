/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.other;

/**
 *
 * @author Patryk
 */
public class SetOrderInfo {
    private int serorderId;
    private int categoryId;
    private int itemId;
    private String username;
    private String title;
    private String category;
    private int orderId;
    private int ownerId;

    public SetOrderInfo(){
        
    }
    
    public SetOrderInfo(int serorderId, int categoryId, int itemId, String username, String title, String category, int orderId, int ownerId) {
        this.serorderId = serorderId;
        this.categoryId = categoryId;
        this.itemId = itemId;
        this.username = username;
        this.title = title;
        this.category = category;
        this.orderId = orderId;
        this.ownerId = ownerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSerorderId() {
        return serorderId;
    }

    public void setSerorderId(int serorderId) {
        this.serorderId = serorderId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(String username) {
        this.username = username;
    }
    
}
