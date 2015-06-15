/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.ItemFactory;

import com.limanowa.library.model.other.Item;

/**
 *
 * @author Patryk
 */
public interface ItemFactoryInterface {
    public Item createItem(int itemType);
}
