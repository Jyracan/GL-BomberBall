package com.glhf.bomberball;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Character {
    private int number_bomb_remaining;
    @Override
    public boolean collide(Character e) {
        return false;
    }

    @Override
    public Texture getTexture() {
        return null;
    }
}
