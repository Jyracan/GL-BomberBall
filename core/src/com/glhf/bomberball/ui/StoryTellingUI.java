package com.glhf.bomberball.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.glhf.bomberball.Bomberball;
import com.glhf.bomberball.Graphics;
import com.glhf.bomberball.Graphics.Anims;
import com.glhf.bomberball.Graphics.GUI;
import com.glhf.bomberball.Translator;
import com.glhf.bomberball.audio.AudioButton;
import com.glhf.bomberball.config.AppConfig;
import com.glhf.bomberball.screens.SoloMenuScreen;
import com.glhf.bomberball.screens.StoryMenuScreen;
import com.glhf.bomberball.screens.StoryTellingScreen;
import com.glhf.bomberball.utils.ScreenChangeListener;

public class StoryTellingUI extends Table {
    private final Label label;
    private int chapter = 1;
    private StoryTellingScreen screen;

    public StoryTellingUI(StoryTellingScreen screen) {
        this.screen = screen;
        setFillParent(true);
        label = new Label(Translator.translate("story"+chapter), Graphics.GUI.getSkin(), "small");
        label.setAlignment(Align.center);
        add(label).grow();

        Skin skin = GUI.getSkin();
        Table characters = new Table();
        characters.setFillParent(true);
        characters.align(Align.top);
        characters.padTop(10);
        characters.add(new CharacterUI("knight_m", "Le héros", "Maximilien"))
                .height(Value.percentHeight(0.3f, this));
        characters.add(new CharacterUI("black_knight", "Le méchant", "Plétor"))
                .height(Value.percentHeight(0.3f, this));
        characters.add(new CharacterUI("wizzard_m", "Le magicien", "Bernard"))
                .height(Value.percentHeight(0.3f, this));
        characters.add(new CharacterUI("elf_f", "La princesse", "Bianca"))
                .height(Value.percentHeight(0.3f, this));
        addActor(characters);

        Table buttons = new Table();
        buttons.setFillParent(true);
        buttons.align(Align.bottom);
        TextButton skip = new AudioButton(Translator.translate("Skip"), skin);
        skip.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.endStory();
            }
        });
        skip.align(Align.bottomLeft);
        buttons.add(skip);
        buttons.add().growX();
        TextButton next_chapter = new AudioButton(Translator.translate("Next chapter"), skin);
        next_chapter.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.continueStory();
            }
        });
        next_chapter.align(Align.bottomRight);
        buttons.add(next_chapter);
        addActor(buttons);
    }

    public void continueStory() {
        label.setText(Translator.translate("story"+screen.chapter));
    }

    private class CharacterUI extends Table{
        public CharacterUI(String skin_name, String titre, String nom) {
            Skin skin = GUI.getSkin();
            Image image = new Image(Anims.get(skin_name + "/idle").first());
            image.setScaling(Scaling.fit);
            image.setAlign(Align.center);

            add(image).grow().height(100).row();
            add(new Label(titre, skin)).row();
            add(new Label(nom, skin)).row();
        }
    }
}
