package org.ielena.pokedex.services.impl;

import lombok.Getter;
import lombok.Setter;
import org.ielena.pokedex.services.UserSessionService;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DefaultUserSessionService implements UserSessionService {

    private Long userId;

    public void clear() {
        this.userId = null;
    }
}