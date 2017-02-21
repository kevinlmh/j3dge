package com.minghuiliu.base.engine;

/**
 * Created by kevin on 1/31/17.
 */
public abstract class Game {
    protected long window;

    public Game(long window) {
        this.window = window;
    }

    public abstract void handleInput();

    public abstract void keyCallback(long window, int key, int scancode, int action, int mods);

    public abstract void scrollCallback(long window, double xoffset, double yoffset);

    public abstract void update();

    public abstract void render();
}
