package org.ielena.pokedex.controllers;

import org.ielena.pokedex.controllers.mediator.Mediator;

public interface ViewController {

    void setMediator(Mediator mediator);
}
