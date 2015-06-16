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
public class BookItem extends Item{
    private String author;
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void fillInformation(String name) {
        BookItem b = repo.getInformationForBook(name);
        this.setAuthor(b.getAuthor());
        this.setDescription(b.getDescription());
        this.setId(b.getId());
        this.setSubcategory(b.getSubcategory());
        this.setTitle(b.getTitle());
        this.setUser(b.getUser());
        this.setUserId(b.getUserId());
        this.setAvalibility(b.isAvalibility());
    }

    @Override
    public String getCategory() {
        return "Ksiazki";
    }

    @Override
    public String getAdditionalInfo() {
        return this.author;
    }
    
    @Override
    public boolean setOrder(OrderInfo order) {
        order.setCategoryId(1);
        return repo.addOrder(order);
    }

    @Override
    public void setAvalibility(int item, int val) {
        repo.setAvalibity(1, item, val);
    }

    @Override
    public void addItem(Item item, String var) {
        BookItem it = new BookItem();
        
        it.setAvalibility(item.isAvalibility());
        it.setSubcategoryId(item.getSubcategoryId());
        it.setTitle(item.getTitle());
        it.setAuthor(var);
        it.setDescription(item.getDescription());
        it.setUserId(item.getUserId());
        
        repo.AddBook(it);
    }

    @Override
    public int getLastId() {
        return repo.getLastId("Books","bookId");
    }
}
