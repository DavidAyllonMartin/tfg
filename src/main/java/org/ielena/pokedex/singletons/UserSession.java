package org.ielena.pokedex.singletons;

import lombok.Getter;
import lombok.Setter;
import org.ielena.pokedex.models.UserModel;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserSession {
    private UserModel activeUser;

    public void clear() {
        this.activeUser = null;
    }
}
