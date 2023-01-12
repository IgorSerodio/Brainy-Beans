package com.bb.game.score;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bb.game.BrainyBeansGraphics;
import com.bb.game.menu.Menu;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Fonts;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreScreen extends BrainyBeansGraphics {
    public static final int NAME_LIMIT = 10;
    private Game game;
    private Integer score;
    private boolean keyBoardVisibility;

    private static final Texture backgroundTexture = new Texture("animations\\background.png");
    private static final Texture effectsTexture = new Texture("animations\\effects.png");

    public ScoreScreen(int score, Game game){
        this.game = game;
        this.score = score;
        setUpStage();
    }

    private void submitScore(String playerName, Integer newScore) {
        ArrayList<String> scoreList = getScores();
        if(!scoreUpdate(scoreList, playerName, newScore))
            scoreList.add(playerName + "," + newScore);
        Collections.sort(scoreList, new ScoreStringComparator());
        saveScores(scoreList);
    }

    private boolean scoreUpdate(ArrayList<String> scoreList, String playerName, Integer newScore) {
        for(int i = 0; i<scoreList.size(); i++){
            if(scoreList.get(i) != null && playerName.equals(scoreList.get(i).split(",")[0])){
                if(newScore>Integer.valueOf(scoreList.get(i).split(",")[1]))
                    scoreList.set(i, playerName + "," + newScore);
                return true;
            }
        }
        return false;
    }

    private void saveScores(ArrayList<String> scoreList) {
        Preferences ranking = Gdx.app.getPreferences("Ranking");
        for(Integer i = 1; i<11; i++){
            System.out.println(scoreList.get(i-1));
            if(scoreList.get(i-1) != null){
                ranking.putString(i.toString(), scoreList.get(i-1));
            }
        }
        ranking.flush();
    }

    private ArrayList<String> getScores(){
        ArrayList<String> scores = new ArrayList<>();
        Preferences ranking = Gdx.app.getPreferences("Ranking");
        for(Integer i = 1; i<11; i++){
            scores.add(ranking.getString(i.toString(), null));
        }
        return scores;
    }

    private void setUpStage() {
        Image background = new Image(backgroundTexture);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);

        Image effects = new Image(effectsTexture);
        effects.setPosition(Constants.WORLD_WIDTH * -0.1f, Constants.WORLD_WIDTH * -0.45f);
        effects.setOrigin(effects.getWidth()/2f, effects.getHeight()/2f);
        effects.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(30f, 1)));
        getStage().addActor(effects);

        Label text = new Label("Total Score: " + score.toString(), new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
        text.setFontScale(5f);
        text.setPosition(Constants.WORLD_WIDTH/2f - text.getFontScaleX()*text.getWidth()/2f, Constants.WORLD_HEIGHT/2f + text.getFontScaleY()*text.getHeight());
        getStage().addActor(text);

        Label playerName = new Label("Player Name: ", new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
        playerName.setFontScale(5f);
        playerName.setPosition(text.getX(), Constants.WORLD_HEIGHT/2f);
        getStage().addActor(playerName);

        Label input = new Label(" ", new Label.LabelStyle(Fonts.COMIC_NEUE, Color.YELLOW));
        input.setFontScale(5f);
        input.setPosition(Constants.WORLD_WIDTH/2f, Constants.WORLD_HEIGHT/2f - text.getFontScaleY()*text.getHeight());

        float step = (input.getWidth()* input.getFontScaleX())/1.5f;
        input.setText("");

        getStage().addActor(input);

        getStage().addListener(new InputListener(){

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                String string = Character.valueOf(character).toString();
                String name = input.getText().toString();
                if(string.matches("^[a-zA-Z0-9]$") && name.length()<NAME_LIMIT){
                    input.setText(name + string);
                    input.setX(input.getX()-step);
                } else if(character == '\b' && input.getText().length > 0){
                    input.setText(name.replaceAll(".$", ""));
                    input.setX(input.getX()+step);
                } else if(character == '\n') {
                    submitScore(name, score);
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    game.setScreen(new Menu(game));
                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            this.keyBoardVisibility = !this.keyBoardVisibility;
            Gdx.input.setOnscreenKeyboardVisible(this.keyBoardVisibility);
        }
        super.render(delta);
    }
}
