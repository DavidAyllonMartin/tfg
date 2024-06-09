package org.ielena.pokedex.services.impl;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FadeTransitionService {

    private final Map<Node, FadeTransition> fadeTransitions;

    public FadeTransitionService() {
        fadeTransitions = new HashMap<>();
    }

    public void applyFadeTransition(Node node, Duration duration) {
        if (fadeTransitions.containsKey(node)) {
            fadeTransitions.get(node)
                           .stop();
        } else {
            fadeTransitions.put(node, createFadeTransition(node, duration));
        }

        node.setVisible(true);
        node.setOpacity(1.0);
        fadeTransitions.get(node)
                       .playFromStart();
    }

    private FadeTransition createFadeTransition(Node node, Duration duration) {
        FadeTransition fadeOut = new FadeTransition(duration, node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> node.setVisible(false));
        return fadeOut;
    }
}