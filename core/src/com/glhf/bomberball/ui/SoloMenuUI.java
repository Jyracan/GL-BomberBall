package com.glhf.bomberball.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.glhf.bomberball.Graphics;
import com.glhf.bomberball.screens.InfiniteMenuScreen;
import com.glhf.bomberball.screens.MainMenuScreen;
import com.glhf.bomberball.screens.ScreenChangeListener;
import com.glhf.bomberball.screens.StoryMenuScreen;
import com.glhf.bomberball.ui.SoloMenuUI;

public class SoloMenuUI extends Table {

    private TextButton infinite_button, story_button, back_button;

    public SoloMenuUI(){
        super();

        this.setFillParent(true);
        this.padLeft(Value.percentWidth(0.25f));
        this.padRight(Value.percentWidth(0.25f));
        this.padTop(Value.percentHeight(0.1f));
        this.padBottom(Value.percentHeight(0.1f));

        initializeButtons();
    }

    private void initializeButtons() {

        story_button = new TextButton("Story Mode", Graphics.GUI.getSkin());
        story_button.addListener(new ScreenChangeListener(StoryMenuScreen.class));
        this.add(story_button).row();

        infinite_button = new TextButton("Infinite Mode", Graphics.GUI.getSkin());
        infinite_button.addListener(new ScreenChangeListener(InfiniteMenuScreen.class));
        this.add(infinite_button).row();

        back_button = new TextButton("Back to main menu",Graphics.GUI.getSkin());
        back_button.getLabel().setFontScale(0.8f,0.8f);
        back_button.addListener(new ScreenChangeListener(MainMenuScreen.class));
        this.add(back_button).row();
    }

}
