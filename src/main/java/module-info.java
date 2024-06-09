module org.ielena.pokedex {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.orm;
    requires spring.data.jpa;
    requires java.sql;
    requires spring.beans;
    requires spring.core;
    requires jakarta.persistence;
    requires static lombok;
    requires jakarta.annotation;
    requires org.hibernate.orm.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires spring.data.commons;
    requires java.desktop;
    requires spring.security.crypto;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.web;
    requires de.jensd.fx.glyphs.fontawesome;

    opens org.ielena.pokedex to spring.core, spring.beans, spring.context, javafx.fxml;
    opens org.ielena.pokedex.controllers to javafx.fxml, spring.beans, spring.context, spring.core;
    opens org.ielena.pokedex.converters to spring.core, org.hibernate.orm.core;
    opens org.ielena.pokedex.facades.impl to spring.core;
    opens org.ielena.pokedex.models to org.hibernate.orm.core, spring.core;
    opens org.ielena.pokedex.converters.poke_api to spring.beans, spring.core, org.hibernate.orm.core;
    opens org.ielena.pokedex.poke_api to com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.annotation;
    opens org.ielena.pokedex.poke_api.side_classes to com.fasterxml.jackson.databind, com.fasterxml.jackson.core, com.fasterxml.jackson.annotation;
    opens org.ielena.pokedex.singletons to spring.beans;
    opens org.ielena.pokedex.utils to spring.beans;
    opens org.ielena.pokedex.services to spring.beans;
    opens org.ielena.pokedex.services.impl to org.hibernate.orm.core, spring.beans, spring.core;


    exports org.ielena.pokedex;
    exports org.ielena.pokedex.daos;
    exports org.ielena.pokedex.models;
    exports org.ielena.pokedex.poke_api;
    exports org.ielena.pokedex.poke_api.side_classes;
    exports org.ielena.pokedex.dtos;
    exports org.ielena.pokedex.services;
    exports org.ielena.pokedex.services.impl;
    exports org.ielena.pokedex.converters;
    exports org.ielena.pokedex.converters.poke_api;
    exports org.ielena.pokedex.facades;
    exports org.ielena.pokedex.facades.impl;
    exports org.ielena.pokedex.controllers;
    exports org.ielena.pokedex.controllers.mediator;
}