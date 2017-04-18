package com.minghuiliu.base.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by kevin on 4/17/17.
 */
public class Bitmap {
    private int width;
    private int height;
    private int[] pixels;

    public Bitmap(String filename) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("./res/bitmaps/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            width = image.getWidth();
            height = image.getHeight();
        }

        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }

    public Bitmap flipX() {
        int[] temp = new int[pixels.length];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                temp[j + i * width] = pixels[(width - j - 1) + i * width];
            }
        }

        this.pixels = temp;
        return this;
    }

    public Bitmap flipY() {
        int[] temp = new int[pixels.length];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                temp[j + i * width] = pixels[j + (height - i - 1)  * width];
            }
        }

        pixels = temp;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int getPixel(int x, int y) {
        return pixels[x + y * width];
    }

    public void setPixel(int x, int y, int value) {
        pixels[x + y * width] = value;
    }
}
