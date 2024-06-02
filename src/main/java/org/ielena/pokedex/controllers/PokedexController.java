package org.ielena.pokedex.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lombok.SneakyThrows;
import org.ielena.pokedex.ProjectJavaFxApp;
import org.ielena.pokedex.controllers.mediator.Mediator;
import org.ielena.pokedex.controllers.mediator.PokedexControllerMediator;
import org.ielena.pokedex.dtos.PokemonDto;
import org.ielena.pokedex.dtos.TypeDto;
import org.ielena.pokedex.facades.PokemonFacade;
import org.ielena.pokedex.facades.TypeFacade;
import org.ielena.pokedex.services.DatabaseUpdateService;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;

@Component
public class PokedexController implements ViewController{

    @Resource
    private PokemonFacade pokemonFacade;

    @Resource
    private TypeFacade typeFacade;

    @Resource
    private DatabaseUpdateService databaseUpdateService;

    @FXML
    private TextField searchField;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label pageLabel;

    @FXML
    private ComboBox<Integer> pageComboBox;

    @FXML
    private ComboBox<TypeDto> typeComboBox;

    private IntFunction<Page<PokemonDto>> load;

    private Page<PokemonDto> currentPage;

    private PokedexControllerMediator mediator;

    private static final int ITEMS_PER_PAGE = 9;

    public void initialize() {
        loadTypes();
        loadPokemons(0);
        configureSearchField();
        setMediator(MasterControllerSingleton.getInstance());
    }

    private void loadTypes() {
        List<TypeDto> types = typeFacade.findAll();
        typeComboBox.setItems(FXCollections.observableArrayList(new TypeDto()));
        typeComboBox.getItems()
                    .addAll(types);
    }

    private void configureSearchField() {
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onSearch();
            }
        });
    }

    @FXML
    private void onSearch() {
        String query = searchField.getText()
                                  .trim();
        TypeDto selectedType = typeComboBox.getSelectionModel()
                                           .getSelectedItem();
        boolean isQueryNotEmpty = !query.isEmpty();
        boolean isTypeSelected = selectedType != null && selectedType.getId() != null;

        if (isQueryNotEmpty && isTypeSelected) {
            loadPokemonsByNameAndType(query, selectedType, 0);
        } else if (isTypeSelected) {
            loadPokemonsByType(selectedType, 0);
        } else if (isQueryNotEmpty) {
            loadPokemonsByName(query, 0);
        } else {
            loadPokemons(0);
        }
    }

    @FXML
    private void onPrevious() {
        navigateToPage(currentPage.getNumber() - 1);
    }

    @FXML
    private void onNext() {
        navigateToPage(currentPage.getNumber() + 1);
    }

    @FXML
    private void onGoToPage() {
        Optional.ofNullable(pageComboBox.getSelectionModel()
                                        .getSelectedItem())
                .ifPresent(page -> navigateToPage(page - 1));
    }

    @FXML
    private void onShowFavorites() {
        // Implement logic to show favorite Pokemons here
    }

    private void loadPokemons(int page) {
        load = pageNum -> pokemonFacade.findAll(PageRequest.of(pageNum, ITEMS_PER_PAGE));
        executeLoad(page);
    }

    private void loadPokemonsByName(String name, int page) {
        load = pageNum -> pokemonFacade.findByName(name, PageRequest.of(pageNum, ITEMS_PER_PAGE));
        executeLoad(page);
    }

    private void loadPokemonsByType(TypeDto type, int page) {
        load = pageNum -> pokemonFacade.findByType(type, PageRequest.of(pageNum, ITEMS_PER_PAGE));
        executeLoad(page);
    }

    private void loadPokemonsByNameAndType(String name, TypeDto type, int page) {
        load = pageNum -> pokemonFacade.findByNameAndType(name, type, PageRequest.of(pageNum, ITEMS_PER_PAGE));
        executeLoad(page);
    }

    private void navigateToPage(int page) {
        if (currentPage != null && page >= 0 && page < currentPage.getTotalPages()) {
            executeLoad(page);
        }
    }

    private void executeLoad(int page) {
        currentPage = load.apply(page);
        updateGrid();
        updatePaginationControls();
    }

    private void updatePaginationControls() {
        if (currentPage == null) {
            return;
        }

        updatePaginationComboBox();
        updatePaginationButtons();
        pageLabel.setText("Page " + (currentPage.getNumber() + 1) + "/" + currentPage.getTotalPages());
    }

    private void updatePaginationComboBox() {
        pageComboBox.setOnAction(null);
        List<Integer> pageNumbers = FXCollections.observableArrayList();
        for (int i = 1; i <= currentPage.getTotalPages(); i++) {
            pageNumbers.add(i);
        }
        pageComboBox.setItems(FXCollections.observableArrayList(pageNumbers));
        pageComboBox.setValue(currentPage.getNumber() + 1);
        pageComboBox.setOnAction(event -> onGoToPage());
    }

    private void updatePaginationButtons() {
        prevButton.setDisable(!currentPage.hasPrevious());
        nextButton.setDisable(!currentPage.hasNext());
    }

    @SneakyThrows
    private void updateGrid() {
        gridPane.getChildren()
                .clear();
        List<PokemonDto> pokemonList = currentPage.getContent();
        for (int i = 0; i < pokemonList.size(); i++) {
            PokemonDto pokemon = pokemonList.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource("views/pokemon-item.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            PokemonItemController itemController = fxmlLoader.getController();
            itemController.setData(pokemon);
            int col = i % 3;
            int row = i / 3;
            gridPane.add(anchorPane, col, row);
        }
    }

    @FXML
    private void updateDatabase() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    databaseUpdateService.updateDatabase(251, 0);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void succeeded() {
                // Aquí puedes actualizar la UI de JavaFX si es necesario
                System.out.println("Database update completed successfully.");
            }

            @Override
            protected void failed() {
                // Aquí puedes manejar errores si es necesario
                System.err.println("Database update failed.");
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = (PokedexControllerMediator) mediator;
    }
}