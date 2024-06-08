package org.ielena.pokedex.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "app_user")
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_favorite_pokemons",
            inverseJoinColumns = {@JoinColumn(name = "pokemon_id", referencedColumnName = "pokemon_id")},
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")}
    )
    private Set<PokemonBasicInfoModel> favorites = new HashSet<>();

    public void addFavorite(PokemonBasicInfoModel pokemonModel){
        favorites.add(pokemonModel);
    }

    public void deleteFavorite(PokemonBasicInfoModel pokemonModel){
        favorites.remove(pokemonModel);
    }
}

