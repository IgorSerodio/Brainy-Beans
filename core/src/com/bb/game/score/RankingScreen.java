package com.bb.game.score;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bb.game.BrainyBeansGraphics;
import com.bb.game.menu.Button;
import com.bb.game.menu.Menu;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Fonts;

public class RankingScreen extends BrainyBeansGraphics {
    private Game game;

    private static final Texture backgroundTexture = new Texture("animations\\background.png");
    private static final Texture effectsTexture = new Texture("animations\\effects.png");
    private static final float TABLE_SCALE = 2.5f;

    public RankingScreen(Game game){
        this.game = game;
        setUpStage();
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

        boolean empty = true;

        Preferences ranking = Gdx.app.getPreferences("ranking");
        Table table = new Table();
        table.setFillParent(true);
        String line;

        for(Integer i = 1; i<11; i++){
            line = ranking.getString(i.toString(), null);

            if (line == null)
                break;

            empty = false;
            String[] fields = line.split(",");
            Label playerName = new Label(i.toString()+ ". " + fields[0] + ": ", new Label.LabelStyle(Fonts.COMIC_NEUE, Color.YELLOW));
            playerName.setFontScale(TABLE_SCALE);
            table.add(playerName).minWidth(ScoreScreen.NAME_LIMIT*50);
            Label score = new Label(fields[1], new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
            score.setFontScale(TABLE_SCALE);
            table.add(score);

            if(i<10)
                table.row();
        }

        if(empty) {
            Label text = new Label("No score saved.", new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
            text.setFontScale(5f);
            text.setPosition(Constants.WORLD_WIDTH / 2f - text.getFontScaleX() * text.getWidth() / 2f, Constants.WORLD_HEIGHT / 2f - text.getFontScaleY() * text.getHeight() / 2f);
            getStage().addActor(text);
        } else {
            Label rankingLabel = new Label("Ranking", new Label.LabelStyle(Fonts.COMIC_NEUE, Color.BLACK));
            rankingLabel.setFontScale(3f);
            rankingLabel.setPosition(Constants.WORLD_WIDTH/2f - rankingLabel.getFontScaleX() * rankingLabel.getWidth()/2f, Constants.WORLD_HEIGHT*0.9f);
            getStage().addActor(rankingLabel);
            getStage().addActor(table);
        }

        Button returnButton = new Button("return", Constants.WORLD_WIDTH * 0.8f, Constants.WORLD_HEIGHT * 0.05f, Constants.WORLD_WIDTH * 0.18f, Constants.WORLD_HEIGHT * 0.09f);
        getStage().addActor(returnButton);

        getStage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if(event.getTarget() instanceof Button)
                    game.setScreen(new Menu(game));
            }
        });
    }
}
