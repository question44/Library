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
public class AlbumItem extends Item {
    private String band;
    
    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    @Override
    public void fillInformation(String name) {
        AlbumItem a = repo.getInformationForAlbum(name);
        this.setBand(a.getBand());
        this.setDescription(a.getDescription());
        this.setId(a.getId());
        this.setSubcategory(a.getSubcategory());
        this.setTitle(a.getTitle());
        this.setUser(a.getUser());
        this.setUserId(a.getUserId());
        this.setAvalibility(a.isAvalibility());
    }

    @Override
    public String getCategory() {
        return "Muzyka";
    }

    @Override
    public String getAdditionalInfo() {
        return this.band;
    }

    @Override
    public boolean setOrder(OrderInfo order) {
        order.setCategoryId(3);
        return repo.addOrder(order);
    }

    @Override
    public void setAvalibility(int item, int val) {
        repo.setAvalibity(3, item, val);
    }
}
