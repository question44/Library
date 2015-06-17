/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.other;

import com.google.inject.Injector;
import com.limanowa.library.model.Database.DBRepoInterface;
import com.limanowa.library.model.Database.Injection.InjectorInstance;

/**
 *
 * @author Patryk
 */
public abstract class Item {
    protected int id;
    protected String subcategory;
    protected int subcategoryId;
    protected String title;
    protected String description;
    protected String user;
    protected int userId;
    protected int itemId;
    protected int avalibility;
    protected Injector injector = null;
    
    
    protected DBRepoInterface repo = null;
    
    public Item(){
        injector = new InjectorInstance().getInstance();
        repo = injector.getInstance(DBRepoInterface.class);
        repo.ConnectDB();
    }
    
    public abstract boolean setOrder(OrderInfo order);
    public abstract String getCategory();
    public abstract String getAdditionalInfo();
    public abstract void fillInformation(String name);
    public abstract void setAvalibility(int item, int val);
    public abstract void addItem(Item item, String var);
    public abstract int getLastId();

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }
    
    public int isAvalibility() {
        return avalibility;
    }

    public void setAvalibility(int avalibility) {
        this.avalibility = avalibility;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setUserId(int userId){
        this.userId = userId;
    }
    
    public int getUserId(){
        return this.userId;
    }
    
    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}
