package org.ielena.pokedex.singletons;/*
package org.ielena.pokedex.singletons;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ielena.pokedex.converters.poke_api.AbilityToAbilityModelConverter;
import org.ielena.pokedex.converters.poke_api.MoveToMoveModelConverter;
import org.ielena.pokedex.converters.poke_api.TypeToTypeModelConverter;
import org.ielena.pokedex.models.AbilityModel;
import org.ielena.pokedex.models.MoveModel;
import org.ielena.pokedex.models.PokemonModel;
import org.ielena.pokedex.models.TypeModel;
import org.ielena.pokedex.poke_api.Ability;
import org.ielena.pokedex.poke_api.Move;
import org.ielena.pokedex.poke_api.Pokemon;
import org.ielena.pokedex.poke_api.Type;
import org.ielena.pokedex.poke_api.side_classes.NamedAPIResource;
import org.ielena.pokedex.poke_api.side_classes.PokeAPIResponse;
import org.ielena.pokedex.poke_api.side_classes.PokemonAbility;
import org.ielena.pokedex.poke_api.side_classes.PokemonMove;
import org.ielena.pokedex.poke_api.side_classes.PokemonType;
import org.ielena.pokedex.services.JsonManager;
import org.ielena.pokedex.services.impl.DefaultJsonManager;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DDBB {

    public static final Map<Long, PokemonModel> pokemons = new ConcurrentHashMap<>();
    private static final JsonManager jsonManager = new DefaultJsonManager();
    private static final Converter<Move, MoveModel> moveConverter = new MoveToMoveModelConverter();
    private static final Converter<Type, TypeModel> typeConverter = new TypeToTypeModelConverter();
    private static final Converter<Ability, AbilityModel> abilityConverter = new AbilityToAbilityModelConverter();
    public static final Map<String, MoveModel> moves = new ConcurrentHashMap<>();
    public static final Map<String, AbilityModel> abilities = new ConcurrentHashMap<>();
    public static final Map<String, TypeModel> types = new ConcurrentHashMap<>();
    private static final String MOVE_API_URL = "https://pokeapi.co/api/v2/move?limit=100000&offset=0";
    private static final String ABILITY_API_URL = "https://pokeapi.co/api/v2/ability?limit=100000&offset=0";
    private static final String TYPE_API_URL = "https://pokeapi.co/api/v2/type?limit=100000&offset=0";
    private static final String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon?limit=100000&offset=0";

    public static void main(String[] args) {
        loadAllItems();
    }

    public static void loadAllItems() {
        loadData(MOVE_API_URL, Move.class, moveConverter, moves);
        loadData(ABILITY_API_URL, Ability.class, abilityConverter, abilities);
        loadData(TYPE_API_URL, Type.class, typeConverter, types);
        loadPokemonData();
    }

    private static <T, M> void loadData(String apiUrl, Class<T> apiClass, Converter<T, M> converter, Map<String, M> storage) {
        try {
            String json = jsonManager.fetchJsonFromUrlSync(apiUrl);
            PokeAPIResponse response = new ObjectMapper().readValue(json, PokeAPIResponse.class);

            response.getResults()
                    .parallelStream()
                    .map(NamedAPIResource::getUrl)
                    .forEach(url -> {
                        try {
                            String itemJson = jsonManager.fetchJsonFromUrlSync(url);
                            T apiItem = new ObjectMapper().readValue(itemJson, apiClass);
                            M model = converter.convert(apiItem);
                            storage.put(apiItem.getClass()
                                               .getMethod("getName")
                                               .invoke(apiItem)
                                               .toString(), model);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadPokemonData() {
        try {
            String json = jsonManager.fetchJsonFromUrlSync(POKEMON_API_URL);
            PokeAPIResponse response = new ObjectMapper().readValue(json, PokeAPIResponse.class);

            response.getResults()
                    .parallelStream()
                    .map(NamedAPIResource::getUrl)
                    .forEach(url -> {
                        try {
                            String pokemonJson = jsonManager.fetchJsonFromUrlSync(url);
                            Pokemon pokemon = new ObjectMapper().readValue(pokemonJson, Pokemon.class);

                            List<MoveModel> pokemonMoves = pokemon.getMoves()
                                                                  .stream()
                                                                  .map(PokemonMove::getMove)
                                                                  .map(a -> moves.get(a.getName()))
                                                                  .collect(Collectors.toList());

                            List<AbilityModel> pokemonAbilities = pokemon.getAbilities()
                                                                         .stream()
                                                                         .map(PokemonAbility::getAbility)
                                                                         .map(a -> abilities.get(a.getName()))
                                                                         .collect(Collectors.toList());

                            List<TypeModel> pokemonTypes = pokemon.getTypes()
                                                                  .stream()
                                                                  .map(PokemonType::getType)
                                                                  .map(a -> types.get(a.getName()))
                                                                  .collect(Collectors.toList());

                            PokemonModel pokemonModel = buildPokemonModel(pokemon, pokemonMoves, pokemonAbilities, pokemonTypes);

                            pokemons.put(pokemonModel.getId(), pokemonModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PokemonModel buildPokemonModel(Pokemon pokemon, List<MoveModel> moves, List<AbilityModel> abilities, List<TypeModel> types) {
        return PokemonModel.builder()
                           .id(pokemon.getId())
                           .name(pokemon.getName())
                           .baseExperience(pokemon.getBaseExperience())
                           .imgUrl(pokemon.getSprites()
                                          .getOther()
                                          .getOfficialArtwork()
                                          .getFrontDefault())
                           .hp(pokemon.getStats()
                                      .get(0)
                                      .getBaseStat())
                           .attack(pokemon.getStats()
                                          .get(1)
                                          .getBaseStat())
                           .defense(pokemon.getStats()
                                           .get(2)
                                           .getBaseStat())
                           .speed(pokemon.getStats()
                                         .get(5)
                                         .getBaseStat())
                           .specialAttack(pokemon.getStats()
                                                 .get(3)
                                                 .getBaseStat())
                           .specialDefense(pokemon.getStats()
                                                  .get(4)
                                                  .getBaseStat())
                           .types(types)
                           .abilities(abilities)
                           .moves(moves)
                           .cryUrl(pokemon.getCries()
                                          .getLatest())
                           .height(pokemon.getHeight())
                           .weight(pokemon.getWeight())
                           .isDefault(pokemon.getIsDefault())
                           .build();
    }
}*/
