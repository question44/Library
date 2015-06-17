/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.ItemFactory;

import com.limanowa.library.model.other.AlbumItem;
import com.limanowa.library.model.other.BookItem;
import com.limanowa.library.model.other.Item;
import com.limanowa.library.model.other.MovieItem;

/**
 *
 * @author Patryk
 */
public class ItemFactory implements ItemFactoryInterface{
@Override
    public Item createItem(int itemType) {
        Item item;
        switch(itemType){
            case 1:
                item = new BookItem();
                break;
            case 2:
                item = new MovieItem();
                break;
            case 3:
                item = new AlbumItem();
                break;
            default:
                item = null;
                break;
        }
        return item;
    }
    
}
