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

    public abstract void update();

    public abstract void render();
}
