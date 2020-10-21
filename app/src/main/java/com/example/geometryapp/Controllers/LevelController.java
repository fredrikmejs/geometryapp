package com.example.geometryapp.Controllers;

import android.content.Context;

import com.example.geometryapp.Levels.Area.CompleteFigureFromArea;
import com.example.geometryapp.Levels.Area.FindAreaFromFigure;
import com.example.geometryapp.Levels.Coordinates.CoordinatesFromPoint;
import com.example.geometryapp.Levels.Coordinates.PointFromCoordinates;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Levels.Perimeter.CompleteFigureFromPerimeter;
import com.example.geometryapp.Levels.Perimeter.FindThePerimeterOfAFigure;
import com.example.geometryapp.Levels.Symmetry.FindCoordinateWithPointSymmetry;
import com.example.geometryapp.Levels.Symmetry.FindFigureOfSymmetryThroughPointOfSymmetry;
import com.example.geometryapp.Levels.Symmetry.FindPointWithLineSymmetry;
import com.example.geometryapp.Levels.Symmetry.FindCoordinateWithLineSymmetry;
import com.example.geometryapp.Levels.Symmetry.FindPointWithPointSymmetry;

public class LevelController {
    private int categoryIndex;
    private int levelIndex;

    // ALL CATEGORIES
    // 1 - Point from coordinates
    // 2 - Coordinates from points
    // 3 - line symmetry - point - find point
    // 4 - line symmetry - point - find coordinates
    // 5 - point symmetry - point - find point
    // 6 - point symmetry - point - find coordinates
    // 7 - point symmetry- figure - find coordinates
    // 8 - Find the perimeter of a figure
    // 9 - Draw figure from perimeter
    // 10 - Find the area of a figure
    // 11 - Draw figure from area

    //Returns the correct gameState object according to the category index and level index.
    public GameState getLevel(int categoryIndex, int levelIndex, Context context, GameState gameState) {
        this.categoryIndex = categoryIndex;
        this.levelIndex = levelIndex;
        if (categoryIndex == 1) {
            return (new PointFromCoordinates(levelIndex)).getDefaultLevelState(context);
        } else if (categoryIndex == 2) {
            return (new CoordinatesFromPoint(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 3) {
            return (new FindPointWithLineSymmetry(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 4) {
            return (new FindCoordinateWithLineSymmetry(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 5) {
            return (new FindPointWithPointSymmetry(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 6) {
            return (new FindCoordinateWithPointSymmetry(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 7) {
            return (new FindFigureOfSymmetryThroughPointOfSymmetry(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 8) {
            return (new FindThePerimeterOfAFigure(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 9) {
            return (new CompleteFigureFromPerimeter(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 10) {
            return (new FindAreaFromFigure(levelIndex).getDefaultLevelState(context));
        } else if (categoryIndex == 11) {
            return (new CompleteFigureFromArea(levelIndex, gameState).getDefaultLevelState(context));
        }
        throw new IllegalArgumentException("Category or level not found. Category was " + categoryIndex + ", level was " + levelIndex);
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public int getLevelIndex() {
        return levelIndex;
    }
}
