/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limanowa.library.model.Database.Injection;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 *
 * @author Patryk
 */
public final class InjectorInstance {
    Injector instance;
    public InjectorInstance(){
        instance = null;
    }
    
    public Injector getInstance() {
        if(instance == null){
            instance = Guice.createInjector(new DependencyInjector());
            return instance;
        }
        else{
            System.out.println("Instacja obiektu zostala juz utowrzona");
        }
        return instance;
    }
}
