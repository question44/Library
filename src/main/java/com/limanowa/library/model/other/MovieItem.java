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
public class MovieItem extends Item{
    private String director;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public void fillInformation(String name) {
        MovieItem m = repo.getInformationForMovie(name);
        this.setDirector(m.getDirector());
        this.setDescription(m.getDescription());
        this.setId(m.getId());
        this.setSubcategory(m.getSubcategory());
        this.setTitle(m.getTitle());
        this.setUser(m.getUser());
        this.setUserId(m.getUserId());
        this.setAvalibility(m.isAvalibility());
    }

    @Override
    public String getCategory() {
        return "Film";
    }

    @Override
    public String getAdditionalInfo() {
        return this.director;
    }
    
    @Override
    public boolean setOrder(OrderInfo order) {
        order.setCategoryId(2);
        return repo.addOrder(order);
    }

    @Override
    public void setAvalibility(int item, int val) {
        repo.setAvalibity(2, item, val);
    }
}
