package com.example.geometryapp.Interface;

import com.example.geometryapp.GameState;
import com.example.geometryapp.ValidatedAnswer;

public interface LevelAnswer {
    //Each levelAnswer much have a function that returns Validated answer which tells if users answer was correct
    ValidatedAnswer isAnswerCorrect(GameState gameState, int levelIndex);
}
