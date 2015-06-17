/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.Account.AccountFactory;

import com.limanowa.library.model.Account.AdminUser;
import com.limanowa.library.model.Account.ChildUser;
import com.limanowa.library.model.Account.OtherUser;
import com.limanowa.library.model.Account.ParentUser;
import com.limanowa.library.model.Account.User;

/**
 *
 * @author Patryk
 */
public class AccountFactory implements AccountFactoryInterface{

    @Override
    public User createAccount(int accountType) {
        User user = null;
        switch(accountType){
            case 1 : 
                user = new AdminUser();
                break;
            case 3:
                user = new ParentUser();
                break;
            case 2:
                user = new ChildUser();
                break;
            case 4:
                user = new OtherUser();
                break;
            default:
                user = null;
                break;
        }
        return user;
    }
    
}
