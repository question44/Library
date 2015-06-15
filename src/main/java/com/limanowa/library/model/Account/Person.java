/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.Account;

import com.limanowa.library.model.Account.User;

/**
 *
 * @author Patryk
 */
public class Person extends User{
    private int accountType;

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
    
}
