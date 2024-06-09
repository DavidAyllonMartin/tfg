package org.ielena.pokedex.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
import org.ielena.pokedex.services.impl.DefaultCacheService;
import org.ielena.pokedex.singletons.MasterControllerSingleton;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;

import static org.ielena.pokedex.utils.Constants.View.POKEMON_ITEM_FXML_PATH;

@Component
public class PokedexController implements ViewController {

    private static final int ITEMS_PER_PAGE = 15;

    @Resource
    private PokemonFacade pokemonFacade;
    @Resource
    private TypeFacade typeFacade;
    @Resource
    private DefaultCacheService defaultCacheService;
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
    private Integer currentPageNumber;
    private PokedexControllerMediator mediator;

    public void initialize() {
        loadTypes();
        currentPageNumber = 0;
        load = pageNum -> pokemonFacade.findAll(PageRequest.of(pageNum, ITEMS_PER_PAGE));
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
            loadPokemonsByNameAndType(query, selectedType);
        } else if (isTypeSelected) {
            loadPokemonsByType(selectedType);
        } else if (isQueryNotEmpty) {
            loadPokemonsByName(query);
        } else {
            loadPokemons();
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
        load = pageNum -> pokemonFacade.findUserFavorites(PageRequest.of(pageNum, ITEMS_PER_PAGE));
        currentPageNumber = 0;
        executeLoad();
    }

    private void loadPokemons() {
        load = pageNum -> pokemonFacade.findAll(PageRequest.of(pageNum, ITEMS_PER_PAGE));
        currentPageNumber = 0;
        executeLoad();
    }

    private void loadPokemonsByName(String name) {
        load = pageNum -> pokemonFacade.findByName(name, PageRequest.of(pageNum, ITEMS_PER_PAGE));
        currentPageNumber = 0;
        executeLoad();
    }

    private void loadPokemonsByType(TypeDto type) {
        load = pageNum -> pokemonFacade.findByType(type, PageRequest.of(pageNum, ITEMS_PER_PAGE));
        currentPageNumber = 0;
        executeLoad();
    }

    private void loadPokemonsByNameAndType(String name, TypeDto type) {
        load = pageNum -> pokemonFacade.findByNameAndType(name, type, PageRequest.of(pageNum, ITEMS_PER_PAGE));
        currentPageNumber = 0;
        executeLoad();
    }

    private void navigateToPage(int page) {
        if (currentPage != null && page >= 0 && page < currentPage.getTotalPages()) {
            currentPageNumber = page;
            executeLoad();
        }
    }

    public void executeLoad() {
        currentPage = load.apply(currentPageNumber);
        updateGrid();
        updatePaginationControls();
    }

    private void updatePaginationControls() {
        if (currentPage == null) {
            return;
        }

        updatePaginationComboBox();
        updatePaginationButtons();
        if (currentPage.getTotalPages() != 0) {
            pageLabel.setText((currentPage.getNumber() + 1) + "/" + currentPage.getTotalPages());
        } else {
            pageLabel.setText("0/0");
        }
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
        List<Node> pokemonNodeList = currentPage.getContent()
                                                .stream()
                                                .map(this::createPokemon)
                                                .toList();

        for (int i = 0; i < pokemonNodeList.size(); i++) {
            Node pokemonNode = pokemonNodeList.get(i);
            int col = i % 5;
            int row = i / 5;
            gridPane.add(pokemonNode, col, row);
        }
    }

    private Node createPokemon(PokemonDto pokemonDto) {
        if (defaultCacheService.isPokemonDtoNodeInCache(pokemonDto)) {
            return defaultCacheService.getPokemonDtoNodeFromCache(pokemonDto);
        } else {
            Node pokemonNode = createPokemonNode(pokemonDto);
            defaultCacheService.addPokemonDtoNodeToCache(pokemonDto, pokemonNode);
            return pokemonNode;
        }
    }

    @SneakyThrows
    private Node createPokemonNode(PokemonDto pokemon) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ProjectJavaFxApp.class.getResource(POKEMON_ITEM_FXML_PATH));
        Node node = fxmlLoader.load();
        PokemonItemController itemController = fxmlLoader.getController();
        itemController.setData(pokemon);
        return node;
    }

    @FXML
    private void updateDatabase() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    databaseUpdateService.updateDatabase(904, 0);
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