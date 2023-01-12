package com.bb.game.score;

import java.util.Comparator;

public class ScoreStringComparator implements Comparator<String> {
    @Override
    public int compare(String string1, String string2) {
        if(string1 == null && string2 != null){
            return 1;
        } else if(string1 != null && string2 == null){
            return -1;
        } else if(string1 == null && string2 == null){
            return 0;
        }
        Integer score1 = Integer.valueOf(string1.split(",")[1]);
        Integer score2 = Integer.valueOf(string2.split(",")[1]);

        return  score2.compareTo(score1);
    }
}
