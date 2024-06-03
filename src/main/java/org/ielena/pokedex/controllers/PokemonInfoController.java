package org.ielena.pokedex.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.PokemonInfoControllerMediator;
import org.ielena.pokedex.dtos.AbilityDto;
import org.ielena.pokedex.dtos.MoveDto;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.ielena.pokedex.singletons.SpringContextSingleton;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PokemonInfoController implements ViewController{

    private static final double MAX_STAT = 200.0;
    public AnchorPane infoContainer;
    public ScrollPane movesContainer;
    public VBox movesVBox;
    public VBox statisticsContainer;

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

        pokemonDto.getTypes().forEach(this::addType);
        pokemonDto.getAbilities().forEach(this::addAbility);
        movesVBox.getChildren().addAll(pokemonDto.getMoves().parallelStream().map(this::createMove).toList());

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

    @SneakyThrows
    private void addType(TypeDto typeDto) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/type-container.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        TypeContainerController typeContainerController = fxmlLoader.getController();
        typeContainerController.setPokemonType(typeDto, 18);
        typesHBox.getChildren().add(anchorPane);
    }

    @SneakyThrows
    private void addAbility(AbilityDto abilityDto) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/ability-item.fxml"));
//        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        AnchorPane anchorPane = fxmlLoader.load();
        AbilityItemController abilityItemController = fxmlLoader.getController();
        abilityItemController.setData(abilityDto);
        abilitiesHBox.getChildren().add(anchorPane);
    }

    @SneakyThrows
    private Node createMove(MoveDto moveDto) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/move-item.fxml"));
//        fxmlLoader.setControllerFactory(SpringContextSingleton.getContext()::getBean);
        Node anchorPane = fxmlLoader.load();
        MoveItemController moveItemController = fxmlLoader.getController();
        moveItemController.setData(moveDto);
        return anchorPane;
    }

    public void onBack(ActionEvent actionEvent) {
        mediator.changeToMainWindow();
    }

    public void onStatistics() {
        statisticsContainer.setVisible(true);
        movesContainer.setVisible(false);
    }

    public void onMoves() {
        statisticsContainer.setVisible(false);
        movesContainer.setVisible(true);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (PokemonInfoControllerMediator) mediator;
    }
}
