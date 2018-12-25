package com.glhf.bomberball.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.glhf.bomberball.Bomberball;
import com.glhf.bomberball.Graphics;
import com.glhf.bomberball.maze.MazeDrawer;
import com.glhf.bomberball.screens.*;

public class StoryMenuUI extends Table {

    private TextButton back_button, next_level_button, previous_level_button,play_button;
    private Label label;
    private Table level_selection;
    private MazeDrawer level_preview;
    private StoryMenuScreen screen;
    private Table buttons;
    private TextButton[] levels;

    public StoryMenuUI(StoryMenuScreen screen){

        super();
        this.screen = screen;
        this.setFillParent(true);
        level_selection = new Table();
        addButtons();
        level_preview = new MazeDrawer(screen.maze, 0.25f, 0.75f,  0.5f, 0.88f, MazeDrawer.Fit.BEST);
        this.add(level_preview);
        unlockLevel(2);
    }

    /**
     * initializes and adds all the buttons to the table. Also adds listeners to buttons if necessary
     */
    private void addButtons() {

        // Title

        label = new Label("Level Selection",Graphics.GUI.getSkin(),"Title");
        label.setAlignment(Align.center);
        label.setFontScale(1.7f,1.7f);
        this.add(label).row();

        // Buttons to select the level

        previous_level_button = new TextButton("<",Graphics.GUI.getSkin());
        previous_level_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.previousMaze();
                level_preview.setMaze(screen.maze);
            }
        });
        level_selection.add(previous_level_button);

        next_level_button = new TextButton(">",Graphics.GUI.getSkin());
        next_level_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screen.nextMaze();
                level_preview.setMaze(screen.maze);
            }
        });
        level_selection.add(next_level_button).spaceLeft(Value.percentHeight(8f));

        this.add(level_selection).align(Align.center).grow().row();

        buttons = new Table();

        // Available levels

        int nb_levels = screen.getMazeCount();
        levels = new TextButton[nb_levels];
        HorizontalGroup horizontal = new HorizontalGroup();

        for(int i =1;i<nb_levels;i++)
        {
            if(i>1) // locks all the levels except the first one
            {
                levels[i-1] = new TextButton(Integer.toString(i),Graphics.GUI.getSkin(),"locked level");
                horizontal.addActor(levels[i-1]);
                horizontal.space(25);
            }
            if(i==1) // the first level is available to play
            {
                levels[i-1] = new TextButton(Integer.toString(i),Graphics.GUI.getSkin());
                levels[i-1].addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        screen.getMaze(0);
                        level_preview.setMaze(screen.maze);
                    }
                });
                horizontal.addActor(levels[i-1]);
                horizontal.space(25);
            }
        }
        levels[nb_levels-1] =  new TextButton(Integer.toString(nb_levels),Graphics.GUI.getSkin(),"locked level");
        horizontal.addActor(levels[nb_levels-1]);
        buttons.add(horizontal).spaceBottom(Value.percentHeight(0.5f)).row();

        // Play and quit buttons

        play_button = new TextButton("Play Selected Level",Graphics.GUI.getSkin());
        play_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Bomberball.changeScreen(new GameMultiScreen(screen.maze,screen.getMazeId()));
            }
        });
        buttons.add(play_button).row();

        back_button = new TextButton("Back to main menu",Graphics.GUI.getSkin());
        back_button.addListener(new ScreenChangeListener(MainMenuScreen.class));
        back_button.getLabel().setFontScale(0.8f,0.8f);
        buttons.add(back_button).spaceTop(Value.percentHeight(0.9f));
        this.add(buttons);
    }

    /**
     * change the texture and allows preview of the level when the button is clicked
     * @param i
     */
    public void unlockLevel(int i)
    {
        //TODO remettre l'apparence par défaut du bouton
        levels[i-1].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    screen.getMaze(i-1);
                    level_preview.setMaze(screen.maze);
            }
        });
    }

}


