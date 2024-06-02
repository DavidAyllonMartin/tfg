package org.ielena.pokedex.singletons;

import org.ielena.pokedex.controllers.MasterController;

public class MasterControllerSingleton {

    private static volatile MasterController instance;

    private MasterControllerSingleton() {
    }

    public static MasterController getInstance() {
        if (instance == null) {
            synchronized (MasterController.class) {
                if (instance == null) {
                    instance = new MasterController();
                }
            }
        }
        return instance;
    }
}