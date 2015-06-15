/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.Database.Injection;

import com.google.inject.AbstractModule;
import com.limanowa.library.model.Database.DBRepo;
import com.limanowa.library.model.Database.DBRepoInterface;

/**
 *
 * @author Patryk
 */
public class DependencyInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(DBRepoInterface.class).to(DBRepo.class);
    }
    
}
