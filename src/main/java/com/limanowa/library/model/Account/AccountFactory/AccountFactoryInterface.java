/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.Account.AccountFactory;

import com.limanowa.library.model.Account.User;

/**
 *
 * @author Patryk
 */
public interface AccountFactoryInterface {
    public User createAccount(int accountType);
}
