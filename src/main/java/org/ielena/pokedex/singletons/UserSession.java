package org.ielena.pokedex.singletons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserSession {

    private Long userId;

    public void clear() {
        this.userId = null;
    }
}
