package org.ielena.pokedex.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.SneakyThrows;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.PokemonInfoControllerMediator;
import org.ielena.pokedex.dtos.AbilityDto;
import org.ielena.pokedex.dtos.MoveDto;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.facades.UserFacade;
import org.ielena.pokedex.services.impl.DefaultCacheService;
import org.ielena.pokedex.singletons.SpringContextSingleton;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.ielena.pokedex.utils.Constants.View.ABILITY_ITEM_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.MOVE_ITEM_FXML_PATH;
import static org.ielena.pokedex.utils.Constants.View.TYPE_ITEM_FXML_PATH;

@Component
public class PokemonInfoController implements ViewController {

    private static final double MAX_STAT = 255.0;
    private static final String FX_BACKGROUND_COLOR = "-fx-background-color: %s;";

    @Resource
    private DefaultCacheService defaultCacheService;
    @Resource
    private UserFacade userFacade;

    @FXML
    private AnchorPane infoContainer;
    @FXML
    private ScrollPane movesContainer;
    @FXML
    private VBox statisticsContainer;
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
    @FXML
    private GridPane movesGridPane;
    @FXML
    private Button favoriteButton;
    @FXML
    private FontAwesomeIconView starIcon;


    private PokemonInfoControllerMediator mediator;
    private PokemonDto pokemon;

    public void initialize() {
        setMediator(SpringContextSingleton.getContext()
                                          .getBean(MasterController.class));
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (PokemonInfoControllerMediator) mediator;
    }

    public void setData(PokemonDto pokemonDto) {
        this.pokemon = pokemonDto;
        configurePokemonDetails();
        configurePokemonStats();
        configureContainers();
        updateFavoriteButton();
    }

    private void configurePokemonDetails() {
        idLabel.setText(String.format("#%03d  %s", pokemon.getId(), pokemon.getName()));
        pokemonImg.setImage(pokemon.getImg());
        heightLabel.setText(String.format("%.1f m", pokemon.getHeight()));
        weightLabel.setText(String.format("%.1f kg", pokemon.getWeight()));
        descriptionLabel.setText(pokemon.getDescription());
    }

    private void configurePokemonStats() {
        hpLabel.setText(String.valueOf(pokemon.getHp()));
        attackLabel.setText(String.valueOf(pokemon.getAttack()));
        defenseLabel.setText(String.valueOf(pokemon.getDefense()));
        sAttackLabel.setText(String.valueOf(pokemon.getSpecialAttack()));
        sDefenseLabel.setText(String.valueOf(pokemon.getSpecialDefense()));
        speedLabel.setText(String.valueOf(pokemon.getSpeed()));

        hpProgressBar.setProgress(pokemon.getHp() / MAX_STAT);
        attackProgressBar.setProgress(pokemon.getAttack() / MAX_STAT);
        defenseProgressBar.setProgress(pokemon.getDefense() / MAX_STAT);
        sAttackProgressBar.setProgress(pokemon.getSpecialAttack() / MAX_STAT);
        sDefenseProgressBar.setProgress(pokemon.getSpecialDefense() / MAX_STAT);
        speedProgressBar.setProgress(pokemon.getSpeed() / MAX_STAT);

        infoContainer.setStyle(String.format(FX_BACKGROUND_COLOR, pokemon.getColor()));
    }

    private void configureContainers() {
        clearContainers();
        pokemon.getTypes()
               .forEach(this::addType);
        pokemon.getAbilities()
               .forEach(this::addAbility);
        addMovesToGrid(pokemon.getMoves());
    }

    private void clearContainers() {
        abilitiesHBox.getChildren()
                     .clear();
        typesHBox.getChildren()
                 .clear();
        movesGridPane.getChildren()
                     .clear();
    }

    @SneakyThrows
    private void addType(TypeDto typeDto) {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource(TYPE_ITEM_FXML_PATH));
        AnchorPane anchorPane = fxmlLoader.load();
        TypeItemController typeItemController = fxmlLoader.getController();
        typeItemController.setPokemonType(typeDto, 18);
        typesHBox.getChildren()
                 .add(anchorPane);
    }

    @SneakyThrows
    private void addAbility(AbilityDto abilityDto) {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource(ABILITY_ITEM_FXML_PATH));
        AnchorPane anchorPane = fxmlLoader.load();
        AbilityItemController abilityItemController = fxmlLoader.getController();
        abilityItemController.setData(abilityDto, pokemon.getColor());
        abilitiesHBox.getChildren()
                     .add(anchorPane);
    }

    private void addMovesToGrid(List<MoveDto> moveDtos) {
        int row = 0;
        int col = 0;

        List<Node> nodes = moveDtos.parallelStream()
                                   .map(this::createMove)
                                   .toList();

        for (Node moveNode : nodes) {
            movesGridPane.add(moveNode, col, row);
            col = (col + 1) % 2;
            if (col == 0) row++;
        }
    }

    private Node createMove(MoveDto moveDto) {
        if (defaultCacheService.isMoveDtoNodeInCache(moveDto)) {
            return defaultCacheService.getMoveDtoNodeFromCache(moveDto);
        } else {
            Node moveNode = createMoveDtoNode(moveDto);
            defaultCacheService.addMoveDtoNodeToCache(moveDto, moveNode);
            return moveNode;
        }
    }

    @SneakyThrows
    private Node createMoveDtoNode(MoveDto moveDto) {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource(MOVE_ITEM_FXML_PATH));
        Node anchorPane = fxmlLoader.load();
        MoveItemController moveItemController = fxmlLoader.getController();
        moveItemController.setData(moveDto);
        return anchorPane;
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
        mediator.changeToMainWindow();
    }

    @FXML
    public void onStatistics() {
        statisticsContainer.setVisible(true);
        movesContainer.setVisible(false);
    }

    @FXML
    public void onMoves() {
        statisticsContainer.setVisible(false);
        movesContainer.setVisible(true);
    }

    @FXML
    private void onFavoriteButtonClicked() {
        pokemon.setFavorite(!pokemon.isFavorite());
        updateFavoriteButton();
        updateDatabaseInfo();
    }

    private void updateDatabaseInfo() {
        if (pokemon.isFavorite()) {
            userFacade.addFavoritePokemon(pokemon.getId());
        } else {
            userFacade.removeFavoritePokemon(pokemon.getId());
        }
    }

    private void updateFavoriteButton() {
        if (pokemon.isFavorite()) {
            starIcon.setIcon(FontAwesomeIcon.STAR);
            starIcon.setFill(Color.GOLD);
        } else {
            starIcon.setIcon(FontAwesomeIcon.STAR_ALT);
            starIcon.setFill(Color.BLACK);
        }
    }

    @FXML
    public void onUpdateDatabase() {
        mediator.updateDatabase();
    }

    @FXML
    public void onLogOut() {
        mediator.logOut();
    }
}