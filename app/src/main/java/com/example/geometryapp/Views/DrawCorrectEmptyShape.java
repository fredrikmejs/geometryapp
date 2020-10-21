package com.example.geometryapp.Views;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

import com.example.geometryapp.CoordinateSystem;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.GameState;

import static com.example.geometryapp.Views.Canvas.RoundedRect;

public class DrawCorrectEmptyShape extends View {

    private CoordinateSystem coordinateSystem;// Contains coordinate system coordinates and their real coordinates on the screen.
    private Paint paintSymmetryPoint;
    private Paint paintWhiteText;
    private Paint paintLine;
    private Paint paintCoordinateSelectedDot;
    private Paint paintSymmetryLine;
    private android.graphics.Canvas canvas;
    private GameState gameState;
    private Paint paintCompleteFigure;

    public DrawCorrectEmptyShape(Context context, android.graphics.Canvas canvas, GameState gameState, CoordinateSystem coordinateSystem, Paint paintSymmetryLine, Paint paintCooridnateSelecetedDot,
                          Paint paintWhiteText, Paint paintLine, Paint paintSymmetryPoint,Paint paintCompleteFigure) {
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
     * Draws rectangle for DrawCorrectEmptyShape()
     * @param rectangle
     */
    public void drawRecangle(Rectangle rectangle){
        int realXStart = coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomLeft()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomLeft()).second;
        int realXEnd = coordinateSystem.getCanvasRealCoordinate(rectangle.getTopLeft()).first;
        int realYEnd = coordinateSystem.getCanvasRealCoordinate(rectangle.getTopLeft()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintCompleteFigure);
        //Draws correct answer point
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + rectangle.getHeight(gameState.getYScale()), xPos, yPos, paintWhiteText);
        }
        realXStart = coordinateSystem.getCanvasRealCoordinate(rectangle.getTopLeft()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(rectangle.getTopLeft()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(rectangle.getTopRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(rectangle.getTopRight()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintCompleteFigure);
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + rectangle.getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);
        }
        realXStart = coordinateSystem.getCanvasRealCoordinate(rectangle.getTopRight()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(rectangle.getTopRight()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomRight()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintCompleteFigure);
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + rectangle.getHeight(gameState.getYScale()), xPos, yPos, paintWhiteText);
        }
        realXStart = coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomRight()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomRight()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomLeft()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomLeft()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + rectangle.getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);
        }
    }

    /**
     * Draws circle for DrawCorrectEmptyShape()
     * @param circle
     */
    public void drawCircle(Circle circle){
        int realXStart = coordinateSystem.getCanvasRealCoordinate(circle.getCenter()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(circle.getCenter()).second;
        canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * circle.getRadius() / 10, paintSymmetryPoint);
        canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);

    }

    /**
     * Draws triangle for DrawCorrectEmptyShape()
     * @param triangle
     */
    public void drawTriangle(Triangle triangle){
        int realXStart = coordinateSystem.getCanvasRealCoordinate(triangle.getFirstPoint()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(triangle.getFirstPoint()).second;
        int realXEnd = coordinateSystem.getCanvasRealCoordinate(triangle.getSecondPoint()).first;
        int realYEnd = coordinateSystem.getCanvasRealCoordinate(triangle.getSecondPoint()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintCompleteFigure);
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            if (triangle.getSecondPoint().getX() != triangle.getFirstPoint().getX() &&
                    triangle.getSecondPoint().getY() != triangle.getFirstPoint().getY()) {
                canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                        , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
                canvas.drawText("l = √(" + Math.abs(triangle.getSecondPoint().getX() - triangle.getFirstPoint().getX()) + "² + "
                                + Math.abs(triangle.getSecondPoint().getY() - triangle.getFirstPoint().getY()) + "²)", xPos,
                        yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);
                canvas.drawText(" = √(" + (Math.abs(triangle.getSecondPoint().getX() - triangle.getFirstPoint().getX())
                                * Math.abs(triangle.getSecondPoint().getX() - triangle.getFirstPoint().getX())
                                + Math.abs(triangle.getSecondPoint().getY() - triangle.getFirstPoint().getY())
                                * Math.abs(triangle.getSecondPoint().getY() - triangle.getFirstPoint().getY())) + ")", xPos
                        , yPos, paintWhiteText);
                canvas.drawText(" = " + triangle.calculateDistanceTwoCoordinates(
                        triangle.getSecondPoint(), triangle.getFirstPoint()
                        , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), paintWhiteText);

            } else {
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + triangle.calculateDistanceTwoCoordinates(
                        triangle.getSecondPoint(), triangle.getFirstPoint()
                        , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos, paintWhiteText);
            }
        }
        realXStart = coordinateSystem.getCanvasRealCoordinate(triangle.getSecondPoint()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(triangle.getSecondPoint()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(triangle.getThirdPoint()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(triangle.getThirdPoint()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintCompleteFigure);

        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            if (triangle.getSecondPoint().getX() == triangle.getThirdPoint().getX() ||
                    triangle.getSecondPoint().getY() == triangle.getThirdPoint().getY()) {
                canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                        , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
                canvas.drawText("l = √(" + Math.abs(triangle.getSecondPoint().getX() - triangle.getThirdPoint().getX()) + "² + "
                                + Math.abs(triangle.getSecondPoint().getY() - triangle.getThirdPoint().getY()) + "²)", xPos,
                        yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);
                canvas.drawText(" = √(" + (Math.abs(triangle.getSecondPoint().getX() - triangle.getThirdPoint().getX())
                                * Math.abs(triangle.getSecondPoint().getX() - triangle.getThirdPoint().getX())
                                + Math.abs(triangle.getSecondPoint().getY() - triangle.getThirdPoint().getY())
                                * Math.abs(triangle.getSecondPoint().getY() - triangle.getThirdPoint().getY())) + ")", xPos
                        , yPos, paintWhiteText);
                canvas.drawText(" = " + triangle.calculateDistanceTwoCoordinates(
                        triangle.getSecondPoint(), triangle.getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), paintWhiteText);
            } else {
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + triangle.calculateDistanceTwoCoordinates(
                        triangle.getSecondPoint(), triangle.getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos, paintWhiteText);
            }
        }

        realXStart = coordinateSystem.getCanvasRealCoordinate(triangle.getThirdPoint()).first;
        realYStart = coordinateSystem.getCanvasRealCoordinate(triangle.getThirdPoint()).second;
        realXEnd = coordinateSystem.getCanvasRealCoordinate(triangle.getFirstPoint()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(triangle.getFirstPoint()).second;
        canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
            if (triangle.getFirstPoint().getX() == triangle.getThirdPoint().getX() ||
                    triangle.getFirstPoint().getY() == triangle.getThirdPoint().getY()) {
                canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                        , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
                canvas.drawText("l = √(" + Math.abs(triangle.getFirstPoint().getX() - triangle.getThirdPoint().getX()) + "² + "
                                + Math.abs(triangle.getFirstPoint().getY() - triangle.getThirdPoint().getY()) + "²)", xPos,
                        yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);
                canvas.drawText(" = √(" + (Math.abs(triangle.getFirstPoint().getX() - triangle.getThirdPoint().getX())
                                * Math.abs(triangle.getFirstPoint().getX() - triangle.getThirdPoint().getX())
                                + Math.abs(triangle.getFirstPoint().getY() - triangle.getThirdPoint().getY())
                                * Math.abs(triangle.getFirstPoint().getY() - triangle.getThirdPoint().getY())) + ")", xPos
                        , yPos, paintWhiteText);
                canvas.drawText(" = " + triangle.calculateDistanceTwoCoordinates(
                        triangle.getFirstPoint(), triangle.getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), paintWhiteText);
            } else {
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + triangle.calculateDistanceTwoCoordinates(
                        triangle.getFirstPoint(), gameState.getTriangle().getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos, paintWhiteText);
            }
        }
    }
}


