package com.bb.game.minigames;

import static com.bb.game.utils.Difficulty.HARD;
import static com.bb.game.utils.Difficulty.MEDIUM;

import com.bb.game.utils.Difficulty;

public class MiniGameFactory {
    public enum Type{
        MEMORY, SEQUENCE, AIM, CHIMP
    }

    public static MiniGameGraphics createMiniGame(Type type, Difficulty difficulty){
        MiniGameGraphics miniGame;
        switch (type){
            case MEMORY:
                miniGame = createMemory(difficulty);
                break;
            case SEQUENCE:
                miniGame = createSequence(difficulty);
                break;
            case AIM:
                miniGame = createAim(difficulty);
                break;
            case CHIMP:
                miniGame = createChimp(difficulty);
                break;
            default:
                throw new IllegalStateException();
        }
        return miniGame;
    }

    private static MiniGameGraphics createChimp(Difficulty difficulty) {
        //substituir depois
        return new ChimpGraphics(HARD);
    }

    private static MiniGameGraphics createAim(Difficulty difficulty) {
        //substituir depois
        return new ChimpGraphics(HARD);
    }

    private static MiniGameGraphics createSequence(Difficulty difficulty) {
        //substituir depois
        return new ChimpGraphics(HARD);
    }

    private static MiniGameGraphics createMemory(Difficulty difficulty) {
        return new ChimpGraphics(HARD);
    }
}
