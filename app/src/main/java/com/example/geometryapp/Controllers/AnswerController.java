package com.example.geometryapp.Controllers;

import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.LevelAnswer;
import com.example.geometryapp.LevelAnswers.AreaAnswers.AnswerCompleteFigureFromArea;
import com.example.geometryapp.LevelAnswers.AreaAnswers.AnswerFindAreaFromFigure;
import com.example.geometryapp.LevelAnswers.CoordinatesAnswers.AnswerCoordinateFromPoint;
import com.example.geometryapp.LevelAnswers.CoordinatesAnswers.AnswerPointFromCoordinate;
import com.example.geometryapp.LevelAnswers.PerimeterAnswers.AnswerCompleteFigureFromPerimeter;
import com.example.geometryapp.LevelAnswers.PerimeterAnswers.AnswerFindThePerimeterOfAFigure;
import com.example.geometryapp.LevelAnswers.SymmetryAnswers.AnswerFindCoordinateWithLineSymmetry;
import com.example.geometryapp.LevelAnswers.SymmetryAnswers.AnswerFindCoordinateWithPointSymmetry;
import com.example.geometryapp.LevelAnswers.SymmetryAnswers.AnswerFindFigureOfSymmetryThroughPointOfSymmetry;
import com.example.geometryapp.LevelAnswers.SymmetryAnswers.AnswerFindPointWithLineSymmetry;
import com.example.geometryapp.LevelAnswers.SymmetryAnswers.AnswerFindPointWithPointSymmetry;
import com.example.geometryapp.ValidatedAnswer;

/**
 * This class checks where to find the answer for the given problem
 */
public class AnswerController {

    //Returns validatedAnswer from the correct category.
    public ValidatedAnswer getAnswer(GameState gameState, int categoryIndex, int levelNum) {
        if (categoryIndex == 1) {
            LevelAnswer answerPointFromCoordinate = new AnswerPointFromCoordinate();
            return answerPointFromCoordinate.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 2) {
            LevelAnswer answerPointFromCoordinate = new AnswerCoordinateFromPoint();
            return answerPointFromCoordinate.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 3) {
            LevelAnswer answerFindPointWithLineSymmetry = new AnswerFindPointWithLineSymmetry();
            return answerFindPointWithLineSymmetry.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 4) {
            LevelAnswer answerFindCoordinateWithLineSymmetry = new AnswerFindCoordinateWithLineSymmetry();
            return answerFindCoordinateWithLineSymmetry.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 5) {
            LevelAnswer answerFindPointWithPointSymmetry = new AnswerFindPointWithPointSymmetry();
            return answerFindPointWithPointSymmetry.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 6) {
            LevelAnswer answerFindCoordinateWithPointSymmetry = new AnswerFindCoordinateWithPointSymmetry();
            return answerFindCoordinateWithPointSymmetry.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 7) {
            LevelAnswer answerFindFigureOfSymmetryThroughPointOfSymmetry = new AnswerFindFigureOfSymmetryThroughPointOfSymmetry();
            return answerFindFigureOfSymmetryThroughPointOfSymmetry.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 8) {
            LevelAnswer answerFindThePerimeterOfAFigure = new AnswerFindThePerimeterOfAFigure();
            return answerFindThePerimeterOfAFigure.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 9) {
            LevelAnswer answerCompleteFigureFromPerimeter = new AnswerCompleteFigureFromPerimeter();
            return answerCompleteFigureFromPerimeter.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 10) {
            LevelAnswer answerFindAreaFromFigure = new AnswerFindAreaFromFigure();
            return answerFindAreaFromFigure.isAnswerCorrect(gameState, levelNum);
        } else if (categoryIndex == 11) {
            LevelAnswer answerCompleteFigureFromArea = new AnswerCompleteFigureFromArea();
            return answerCompleteFigureFromArea.isAnswerCorrect(gameState, levelNum);
        }
        throw new IllegalArgumentException("Category or level was not found. Category index was: " + categoryIndex + ". Level index was: " + levelNum);
    }
}
