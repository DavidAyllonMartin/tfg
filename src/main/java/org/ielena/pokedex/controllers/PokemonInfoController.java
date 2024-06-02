package org.ielena.pokedex.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.PokemonInfoControllerMediator;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.springframework.stereotype.Component;

@Component
public class PokemonInfoController implements ViewController{

    private static final double MAX_STAT = 200.0;
    public AnchorPane infoContainer;

    @FXML
    private ImageView pokemonImg;
    
    @FXML
    private Label idLabel;

    @FXML
    private HBox typesHBox;

    @FXML
    private Label heightLabel;
    @FXML
    private Label weightLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label hpLabel;

    @FXML
    private Label attackLabel;

    @FXML
    private Label defenseLabel;

    @FXML
    private Label sAttackLabel;

    @FXML
    private Label sDefenseLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private ProgressBar hpProgressBar;

    @FXML
    private ProgressBar attackProgressBar;

    @FXML
    private ProgressBar defenseProgressBar;

    @FXML
    private ProgressBar sAttackProgressBar;

    @FXML
    private ProgressBar sDefenseProgressBar;

    @FXML
    private ProgressBar speedProgressBar;

    @FXML
    private HBox abilitiesHBox;

    private PokemonInfoControllerMediator mediator;

    public void initialize(){
        setMediator(MasterControllerSingleton.getInstance());
    }

    public void setData(PokemonDto pokemonDto) {
        idLabel.setText(String.format("#%03d  %s", pokemonDto.getId(), pokemonDto.getName()));

        pokemonImg.setImage(pokemonDto.getImg());

        heightLabel.setText(String.format("%.1f m", pokemonDto.getHeight()));
        weightLabel.setText(String.format("%.1f kg", pokemonDto.getWeight()));
        descriptionLabel.setText(pokemonDto.getDescription());

        hpLabel.setText(String.valueOf(pokemonDto.getHp()));
        attackLabel.setText(String.valueOf(pokemonDto.getAttack()));
        defenseLabel.setText(String.valueOf(pokemonDto.getDefense()));
        sAttackLabel.setText(String.valueOf(pokemonDto.getSpecialAttack()));
        sDefenseLabel.setText(String.valueOf(pokemonDto.getSpecialDefense()));
        speedLabel.setText(String.valueOf(pokemonDto.getSpeed()));


        hpProgressBar.setProgress(pokemonDto.getHp() / MAX_STAT);
        attackProgressBar.setProgress(pokemonDto.getAttack() / MAX_STAT);
        defenseProgressBar.setProgress(pokemonDto.getDefense() / MAX_STAT);
        sAttackProgressBar.setProgress(pokemonDto.getSpecialAttack() / MAX_STAT);
        sDefenseProgressBar.setProgress(pokemonDto.getSpecialDefense() / MAX_STAT);
        speedProgressBar.setProgress(pokemonDto.getSpeed() / MAX_STAT);

        infoContainer.setStyle(String.format("-fx-background-color: %s;", pokemonDto.getColor()));
    }

    public void onBack(ActionEvent actionEvent) {
        mediator.changeToMainWindow();
    }

    public void onStatistics(ActionEvent actionEvent) {

    }

    public void onMoves(ActionEvent actionEvent) {

    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (PokemonInfoControllerMediator) mediator;
    }
}
