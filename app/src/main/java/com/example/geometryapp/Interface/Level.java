package com.example.geometryapp.Interface;

import android.content.Context;

import com.example.geometryapp.GameState;

public interface Level {
    //Each level must have function which returns levels default gameState
    GameState getDefaultLevelState(Context context);
}
