module helloworld.helloworld {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens helloworld.helloworld to javafx.fxml;
    exports helloworld.helloworld;
    exports helloworld.helloworld.controller;
    exports helloworld.helloworld.model;
    exports helloworld.helloworld.view;
    exports helloworld.helloworld.service;
    exports helloworld.helloworld.database;
    exports helloworld.helloworld.dao;
    exports helloworld.helloworld.experiment;
}