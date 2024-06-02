package org.ielena.pokedex.services.impl;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.ielena.pokedex.services.ImageService;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AdvancedImageService implements ImageService {

    @Override
    public Image getImage(byte[] imageData) throws IOException {
        BufferedImage bufferedImage = readImage(imageData);
        return toFXImage(bufferedImage);
    }

    @Override
    public Image getResizedImage(byte[] imageData, int newWidth, int newHeight) throws IOException {
        BufferedImage originalImage = readImage(imageData);
        BufferedImage resizedImage = resizeImage(originalImage, newWidth, newHeight);
        return toFXImage(resizedImage);
    }

    @Override
    public Color getDominantColor(byte[] imageData) {
        Image image = new Image(new ByteArrayInputStream(imageData));

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        javafx.scene.image.PixelReader pixelReader = image.getPixelReader();

        double totalRed = 0;
        double totalGreen = 0;
        double totalBlue = 0;
        double totalAlpha = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = pixelReader.getColor(x, y);

                totalRed += pixelColor.getRed() * pixelColor.getOpacity();
                totalGreen += pixelColor.getGreen() * pixelColor.getOpacity();
                totalBlue += pixelColor.getBlue() * pixelColor.getOpacity();
                totalAlpha += pixelColor.getOpacity();
            }
        }

        double averageRed = totalRed / totalAlpha;
        double averageGreen = totalGreen / totalAlpha;
        double averageBlue = totalBlue / totalAlpha;

        double maxComponent = Math.max(Math.max(averageRed, averageGreen), averageBlue);
        double scaleFactor = 0.8 / maxComponent;

        averageRed *= scaleFactor;
        averageGreen *= scaleFactor;
        averageBlue *= scaleFactor;

        return Color.color(averageRed, averageGreen, averageBlue);
    }

    @Override
    public String getDominantColorHex(byte[] imageData) {
        Color dominantColor = getDominantColor(imageData);
        return colorToHex(dominantColor);
    }

    private String colorToHex(Color color) {
        int red = (int) Math.round(color.getRed() * 255);
        int green = (int) Math.round(color.getGreen() * 255);
        int blue = (int) Math.round(color.getBlue() * 255);
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    private BufferedImage readImage(byte[] imageData) throws IOException {
        try (ByteArrayInputStream imageStream = new ByteArrayInputStream(imageData)) {
            return ImageIO.read(imageStream);
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    private Image toFXImage(BufferedImage bufferedImage) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos);
            try (InputStream is = new ByteArrayInputStream(baos.toByteArray())) {
                return new Image(is);
            }
        }
    }
}