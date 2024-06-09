package org.ielena.pokedex.controllers;

import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.RegisterControllerMediator;
import org.ielena.pokedex.models.UserModel;
import org.ielena.pokedex.services.UserService;
import org.ielena.pokedex.services.impl.FadeTransitionService;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.springframework.stereotype.Component;

@Component
public class RegisterController implements ViewController {

    @Resource
    private UserService userService;
    @Resource
    private FadeTransitionService fadeTransitionService;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label messageLabel;

    private RegisterControllerMediator mediator;

    public void initialize() {
        setMediator(MasterControllerSingleton.getInstance());
    }

    @FXML
    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Username and password are required.", Color.RED);
        } else if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match", Color.RED);
        } else if (userService.findByUsername(username) != null) {
            showMessage("Username already exists.", Color.RED);
        } else {
            UserModel newUser = new UserModel();
            newUser.setUsername(username);
            newUser.setPassword(password);
            userService.register(newUser);
            showMessage("Registration successful!", Color.GREEN);
            clearFields();
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setTextFill(color);
        fadeTransitionService.applyFadeTransition(messageLabel, Duration.seconds(7));
    }

    @FXML
    public void backToLogin() {
        mediator.changeToLoginWindow();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (RegisterControllerMediator) mediator;
    }
}