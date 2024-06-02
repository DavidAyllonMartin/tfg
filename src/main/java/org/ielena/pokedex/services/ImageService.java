package org.ielena.pokedex.services;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.IOException;

public interface ImageService {

    Image getImage(byte[] imageData) throws IOException;
    Image getResizedImage(byte[] imageData, int newWidth, int newHeight) throws IOException;

    Color getDominantColor(byte[] imageData);

    String getDominantColorHex(byte[] imageData);
}
