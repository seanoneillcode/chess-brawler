package com.lovely.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lovely.game.Constants.GAME_MENU;
import static com.lovely.game.Constants.GAME_PLAYING;
import static com.lovely.game.LoadingManager.*;

public class StoryManager {

    String leftPortrait;
    String rightPortrait;

    int storyLevel;

    List<String> leftPortraits = Arrays.asList(PORTRAIT_1, PORTRAIT_1, PORTRAIT_1, PORTRAIT_1);
    List<String> rightPortraits = Arrays.asList(PORTRAIT_2, PORTRAIT_3, PORTRAIT_4, PORTRAIT_1);

    List<List<String>> lines;
    public boolean isCurrentActorLeft;
    private int currentLine;
    private float timer;

    StoryManager() {
        storyLevel = 0;
        lines = new ArrayList<>();
        lines.add(Arrays.asList(
                "I'm here for your head,\nThe price a rebel pays",
                "Will you not hear our \ndemands? Our suffering?",
                "Would a hunter listen to\na fox before the kill?",
                "You are wretched dog!"
        ));
        lines.add(Arrays.asList(
                "I'm here for the crown\nAnd your head",
                "What are you doing?\nI am your king",
                "I will be the king\nNo-one is stronger",
                "You will have no honor\n",
                "I will have my head"
        ));
        lines.add(Arrays.asList(
                "Are you here for the crown?",
                "I'm am her for revenge\nFor my father",
                "Then you will join him,\nIn death",
                "We shall see"
        ));
        lines.add(Arrays.asList(
                "You have won the battle \nbut at what cost...",
                "The kingdom is a place \nof plague and villany",
                "Your legacy is of murder,\nof death",
                "THE END"
        ));
    }

    void loadCurrentStoryLevel() {
        leftPortrait = leftPortraits.get(storyLevel);
        rightPortrait = rightPortraits.get(storyLevel);
        isCurrentActorLeft = true;
        currentLine = 0;
        timer = 1.0f;
    }

    String getLine() {
        if (timer > 0) {
            return "";
        }
        return lines.get(storyLevel).get(currentLine);
    }

    boolean isReady() {
        return timer > 0;
    }

    boolean isLastStory() {
        return storyLevel == lines.size() - 1;
    }

    void update(ChessBrawler chessBrawler) {
        if (!chessBrawler.isStoryMode) {
            return;
        }
        timer = timer - Gdx.graphics.getDeltaTime();
        if (timer < 0 && chessBrawler.inputManager.justClicked) {
            currentLine++;
            isCurrentActorLeft = !isCurrentActorLeft;
            if (currentLine == lines.get(storyLevel).size()) {
                if (isLastStory()) {
                    storyLevel = 0;
                    chessBrawler.changeScreen(GAME_MENU);
                } else {
                    chessBrawler.changeScreen(GAME_PLAYING);
                }
            }
        }
    }

    public void nextBattle(ChessBrawler chessBrawler) {
        storyLevel = storyLevel + 1;
        chessBrawler.aiPlayer.aiLevel++;
        if (storyLevel > lines.size() - 1) {
            storyLevel = 0;
            chessBrawler.changeScreen(GAME_MENU);
        }
    }
}
