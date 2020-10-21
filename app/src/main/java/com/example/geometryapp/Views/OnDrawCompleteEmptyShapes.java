package com.example.geometryapp.Views;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.CoordinateSystem;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.ShapeFourCorners;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.CanvasDraw;
import com.example.geometryapp.R;

import static com.example.geometryapp.Views.Canvas.RoundedRect;

public class OnDrawCompleteEmptyShapes extends View implements CanvasDraw {

    private CoordinateSystem coordinateSystem;// Contains coordinate system coordinates and their real coordinates on the screen.
    private Paint paintSymmetryPoint;
    private Paint paintWhiteText;
    private Paint paintLine;
    private Paint paintCoordinateSelectedDot;
    private Paint paintSymmetryLine;
    private android.graphics.Canvas canvas;
    private GameState gameState;
    private Paint paintCompleteFigure;

    public OnDrawCompleteEmptyShapes(Context context, android.graphics.Canvas canvas, GameState gameState, CoordinateSystem coordinateSystem, Paint paintSymmetryLine, Paint paintCooridnateSelecetedDot,
                                 Paint paintWhiteText, Paint paintLine, Paint paintSymmetryPoint, Paint paintCompleteFigure) {
        super(context);
        this.canvas = canvas;
        this.gameState = gameState;
        this.coordinateSystem = coordinateSystem;
        this.paintSymmetryLine = paintSymmetryLine;
        this.paintCoordinateSelectedDot = paintCooridnateSelecetedDot;
        this.paintLine = paintLine;
        this.paintWhiteText = paintWhiteText;
        this.paintSymmetryPoint = paintSymmetryPoint;
        this.paintCompleteFigure = paintCompleteFigure;
    }

    /**
     * Draws Rectangle for OnDrawCompleteEmptyShape()
     */
    @Override
    public void drawRectangle(){
        gameState.getRectangle().setTopLeft(new Coordinate(gameState.getRectangle().getTopLeft().getX()
                , gameState.getSelectedDot().getCoordinate().getY()));
        gameState.getRectangle().setTopRight(new Coordinate(gameState.getRectangle().getBottomRight().getX()
                , gameState.getSelectedDot().getCoordinate().getY()));

        int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second;
        int realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
        int realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;

        paintLine.setColor(getResources().getColor(R.color.SymmetryLine));
        paintLine.setStrokeWidth(8);

        if (gameState.isAnsweredCorrectly()){
            paintLine.setColor(getResources().getColor(R.color.LightGreen));
        }

        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintLine);

        //Draws correct answer point
        if (gameState.isAnsweredCorrectly()) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintLine);
            canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getYScale()), xPos, yPos, paintWhiteText);
        }
        realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;

        //TOP line
        paintLine.setStrokeWidth(5);
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintLine);

        if (gameState.isAnsweredCorrectly()) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintLine);
            canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);
        }
        realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).second;

        paintLine.setStrokeWidth(8);
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintLine);
        if (gameState.isAnsweredCorrectly()) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintLine);
            canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getYScale()), xPos, yPos, paintWhiteText);
        }
        realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second;

        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintLine);
        if (gameState.isAnsweredCorrectly()) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintLine);
            canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);
        }
    }

    /**
     * Draws circle for OnDrawCompleteEmptyShape()
     */
    @Override
    public void drawCircle(){
        int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;
        int xDifference = Math.abs(gameState.getCircle().getCenter().getY() - gameState.getSelectedDot().getCoordinate().getY());
        int yDifference = Math.abs(gameState.getCircle().getCenter().getX() - gameState.getSelectedDot().getCoordinate().getX());
        gameState.getCircle().setRadius(Math.max(xDifference, yDifference));
        canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintSymmetryPoint);
        canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);
    }

    /**
     * Draws triangle for OnDrawCompleteEmptyShape()
     */
    @Override
    public void drawTriangle(){
        gameState.getTriangle().setSecondPoint(new Coordinate(gameState.getSelectedDot().getCoordinate().getX(), gameState.getSelectedDot().getCoordinate().getY()));
        int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
        int realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
        int realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
        int xPos = (realXStart + realXEnd) / 2;
        int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

        if (gameState.getTriangle().getSecondPoint().getX() != gameState.getTriangle().getFirstPoint().getX() &&
                gameState.getTriangle().getSecondPoint().getY() != gameState.getTriangle().getFirstPoint().getY()) {
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText("l = √(" + Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getFirstPoint().getX()) + "² + "
                            + Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getFirstPoint().getY()) + "²)", xPos,
                    yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);
            canvas.drawText(" = √(" + (Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getFirstPoint().getX())
                            * Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getFirstPoint().getX())
                            + Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getFirstPoint().getY())
                            * Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getFirstPoint().getY())) + ")", xPos
                    , yPos, paintWhiteText);
            canvas.drawText(" = " + gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getFirstPoint()
                    , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), paintWhiteText);
        }else if(gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3){
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText(("" + gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getFirstPoint()
                    , gameState.getXScale(), gameState.getYScale(), true)).replace(".0",""), xPos, yPos, paintWhiteText);
        }

        realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
        xPos = (realXStart + realXEnd) / 2;
        yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
        if ((gameState.getTriangle().getSecondPoint().getX() != gameState.getTriangle().getThirdPoint().getX() &&
                gameState.getTriangle().getSecondPoint().getY() != gameState.getTriangle().getThirdPoint().getY())) {
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText("l = √(" + Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getThirdPoint().getX()) + "² + "
                            + Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getThirdPoint().getY()) + "²)", xPos,
                    yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);
            canvas.drawText(" = √(" + (Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getThirdPoint().getX())
                            * Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getThirdPoint().getX())
                            + Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getThirdPoint().getY())
                            * Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getThirdPoint().getY())) + ")", xPos
                    , yPos, paintWhiteText);
            canvas.drawText(" = " + gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getThirdPoint()
                    , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), paintWhiteText);
        }else if(gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3){
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText(("" + gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getThirdPoint()
                    , gameState.getXScale(), gameState.getYScale(), true)).replace(".0",""), xPos, yPos, paintWhiteText);
        }

        realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
        xPos = (realXStart + realXEnd) / 2;
        yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

        if ((gameState.getTriangle().getFirstPoint().getX() != gameState.getTriangle().getThirdPoint().getX() &&
                gameState.getTriangle().getFirstPoint().getY() != gameState.getTriangle().getThirdPoint().getY())) {
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText("l = √(" + Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX()) + "² + "
                            + Math.abs(gameState.getTriangle().getFirstPoint().getY() - gameState.getTriangle().getThirdPoint().getY()) + "²)", xPos,
                    yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);
            canvas.drawText(" = √(" + (Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX())
                            * Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX())
                            + Math.abs(gameState.getTriangle().getFirstPoint().getY() - gameState.getTriangle().getThirdPoint().getY())
                            * Math.abs(gameState.getTriangle().getFirstPoint().getY() - gameState.getTriangle().getThirdPoint().getY())) + ")", xPos
                    , yPos, paintWhiteText);
            canvas.drawText(" = " + gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getThirdPoint()
                    , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), paintWhiteText);
        }else if(gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3){
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText(("" + gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getThirdPoint()
                    , gameState.getXScale(), gameState.getYScale(), true)).replace(".0",""), xPos, yPos, paintWhiteText);
        }
    }

    @Override
    public void drawRectangle(Rectangle rectangle) {

    }

    @Override
    public void drawCircle(Circle circle) {

    }

    @Override
    public void drawTriangle(Triangle triangle) {

    }

    @Override
    public void drawPointShape() {

    }

    @Override
    public void drawPointShape(ShapeFourCorners shapeFourCorners) {

    }
}
