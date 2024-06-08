package org.ielena.pokedex.controllers;

import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.ielena.pokedex.controllers.mediator.LoginControllerMediator;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.models.UserModel;
import org.ielena.pokedex.services.impl.FadeTransitionService;
import org.ielena.pokedex.services.UserService;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.ielena.pokedex.singletons.UserSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginController implements ViewController {

    private static final Integer FADE_OUT_SECONDS = 5;

    @Resource
    private UserService userService;
    @Resource
    private UserSession userSession;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private FadeTransitionService fadeTransitionService;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessage;

    private LoginControllerMediator mediator;

    public void initialize() {
        setMediator(MasterControllerSingleton.getInstance());
    }

    @FXML
    public void handleLogin() {
        errorMessage.setVisible(false);

        String username = usernameField.getText();
        String password = passwordField.getText();

        UserModel user = userService.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            userSession.setUserId(user.getId());
            mediator.changeToMainWindow();
        } else {
            errorMessage.setText("Wrong username or password");
            fadeTransitionService.applyFadeTransition(errorMessage, Duration.seconds(FADE_OUT_SECONDS));
        }
    }

    @FXML
    public void goToRegister(){
        mediator.changeToRegisterWindow();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (LoginControllerMediator) mediator;
    }
}