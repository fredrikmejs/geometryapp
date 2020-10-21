package com.example.geometryapp.Views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.example.geometryapp.CoordinateSystem;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.ShapeFourCorners;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.Enum.ShapeType;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.CanvasDraw;

import static com.example.geometryapp.Views.Canvas.RoundedRect;

public class OnDrawCorrectShapes extends View implements CanvasDraw {
    private CoordinateSystem coordinateSystem;// Contains coordinate system coordinates and their real coordinates on the screen.
    private Paint paintSymmetryPoint;
    private Paint paintWhiteText;
    private Paint paintLine;
    private Paint paintCoordinateSelectedDot;
    private Paint paintSymmetryLine;
    private android.graphics.Canvas canvas;
    private GameState gameState;
    private Paint paintFillShape;

    public OnDrawCorrectShapes(Context context, android.graphics.Canvas canvas, GameState gameState, CoordinateSystem coordinateSystem, Paint paintSymmetryLine, Paint paintCoordinateSelectedDot,
                                Paint paintWhiteText, Paint paintLine, Paint paintSymmetryPoint, Paint paintFillShape) {
        super(context);
        this.canvas = canvas;
        this.gameState = gameState;
        this.coordinateSystem = coordinateSystem;
        this.paintSymmetryLine = paintSymmetryLine;
        this.paintCoordinateSelectedDot = paintCoordinateSelectedDot;
        this.paintLine = paintLine;
        this.paintWhiteText = paintWhiteText;
        this.paintSymmetryPoint = paintSymmetryPoint;
        this.paintFillShape = paintFillShape;
    }

    @Override
    public void drawRectangle() {

    }

    @Override
    public void drawCircle() {

    }

    @Override
    public void drawTriangle() {

    }

    @Override
    public void drawRectangle(Rectangle rectangle) {
        gameState.setRectangle(rectangle);
        Path path = new Path();
        int realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
        int realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;
        path.moveTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;
        path.lineTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).second;
        path.lineTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second;
        path.lineTo(realXEnd, realYEnd);
        path.close();
        canvas.drawPath(path, paintFillShape);
        if (!gameState.isAnsweredCorrectly() && gameState.getAttempt() == 3) {
            //Draw math
            int xPos = (coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first
                    + coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first) / 2;
            int yPos = (coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second
                    + coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second) / 2;
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText("l = " + gameState.getRectangle().getHeight(gameState.getYScale()) + "×"
                    + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
            canvas.drawText("  = " + gameState.getRectangle().calculateArea(gameState.getXScale(), gameState.getYScale()),
                    xPos, yPos, paintWhiteText);
        }
    }

    @Override
    public void drawCircle(Circle circle) {
        gameState.setCircle(circle);
        int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;
        canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintFillShape);
        canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintFillShape);
    }

    @Override
    public void drawTriangle(Triangle triangle) {
        gameState.setTriangle(triangle);
        Path path = new Path();
        int realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
        int realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
        path.moveTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
        path.lineTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
        path.lineTo(realXEnd, realYEnd);
        path.close();
        canvas.drawPath(path, paintFillShape);
        if (gameState.getAttempt() == 3) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText(("" + Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX())).replace(".0", ""), (realXStart + realXEnd) / 2
                    , (realYStart + realYEnd) / 2 - (int) (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
            realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
            realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText(("" + Math.abs(gameState.getTriangle().getFirstPoint().getY() - gameState.getTriangle().getSecondPoint().getY())).replace(".0", ""), (realXStart + realXEnd) / 2,
                    (realYStart + realYEnd) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
            //Draw math
            int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
            int yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText("A = (" + Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX()) + "×"
                            + Math.abs(gameState.getTriangle().getFirstPoint().getY() - gameState.getTriangle().getSecondPoint().getY()) + ")/2"
                    , xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
            canvas.drawText("  = " + gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()),
                    xPos, yPos, paintWhiteText);
        }
    }

    @Override
    public void drawPointShape(){

    }

    @Override
    public void drawPointShape(ShapeFourCorners shapeFourCorners) {
        gameState.setShapeFourCorners(shapeFourCorners);
        Path path = new Path();
        int realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).first;
        int realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
        path.moveTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).second;
        path.lineTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).second;
        path.lineTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
        path.lineTo(realXEnd, realYEnd);
        path.close();
        canvas.drawPath(path, paintFillShape);
        if ((gameState.getAttempt() == 3) && gameState.getShapeFourCorners().getShapeType() == ShapeType.KITE) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
            int realXStartSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).first;
            int realYStartSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).second;
            int realXEndSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).first;
            int realYEndSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
            canvas.drawLine(realXStartSecond, realYStartSecond, realXEndSecond, realYEndSecond, paintSymmetryLine);
            canvas.drawCircle((realXStartSecond + realXEndSecond) / 2, (realYStartSecond + realYEndSecond) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            //Draw math
            int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
            int yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText(("A = (" + Math.abs(gameState.getShapeFourCorners().getBottomLeft().getX() - gameState.getShapeFourCorners().getTopRight().getX()))
                            .replace(".0", "")+ "×"
                            + ("" + gameState.getShapeFourCorners().calculateDistanceTwoCoordinates(
                    gameState.getShapeFourCorners().getBottomRight(), gameState.getShapeFourCorners().getTopLeft()
                    , gameState.getXScale(), gameState.getYScale(), true)).replace(".0", "") + ")/2"
                    , xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
            canvas.drawText("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()),
                    xPos, yPos, paintWhiteText);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText(("" + gameState.getShapeFourCorners().calculateDistanceTwoCoordinates(
                    gameState.getShapeFourCorners().getBottomRight(), gameState.getShapeFourCorners().getTopLeft()
                    , gameState.getXScale(), gameState.getYScale(), true)).replace(".0", ""), (realXStartSecond + realXEndSecond) / 2,
                    (realYStartSecond + realYEndSecond) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
            canvas.drawText(("" + gameState.getShapeFourCorners().calculateDistanceTwoCoordinates(
                    gameState.getShapeFourCorners().getTopRight(), gameState.getShapeFourCorners().getBottomLeft()
                    , gameState.getXScale(), gameState.getYScale(), true)).replace(".0", ""), (realXStart + realXEnd) / 2,
                    (realYStart + realYEnd) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
        }
        if ((gameState.getAttempt() == 3) && gameState.getShapeFourCorners().getShapeType() == ShapeType.PARALLELOGRAM) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
            int realXStartSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            int realYStartSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
            int realXEndSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).first;
            int realYEndSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
            canvas.drawLine(realXStartSecond, realYStartSecond, realXEndSecond, realYEndSecond, paintSymmetryLine);
            canvas.drawCircle((realXStartSecond + realXEndSecond) / 2, (realYStartSecond + realYEndSecond) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            //Draw math
            int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
            int yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).second;
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText(("A = " + Math.abs(gameState.getShapeFourCorners().getTopLeft().getY() - gameState.getShapeFourCorners().getBottomLeft().getY()))
                            .replace(".0", "")+ "×"
                            + ("" + Math.abs(gameState.getShapeFourCorners().getBottomLeft().getX() - gameState.getShapeFourCorners().getBottomRight().getX()))
                            .replace(".0", "")
                    , xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
            canvas.drawText(("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory())),
                    xPos, yPos, paintWhiteText);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText(("" + Math.abs(gameState.getShapeFourCorners().getTopLeft().getY() - gameState.getShapeFourCorners().getBottomLeft().getY()))
                            .replace(".0", ""), (realXStart + realXEnd) / 2,
                    (realYStart + realYEnd) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
            canvas.drawText(("" + Math.abs(gameState.getShapeFourCorners().getBottomLeft().getX() - gameState.getShapeFourCorners().getBottomRight().getX()))
                            .replace(".0", ""), (realXStartSecond + realXEndSecond) / 2,
                    (realYStartSecond + realYEndSecond) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
        }
        if ((gameState.getAttempt() == 3) && gameState.getShapeFourCorners().getShapeType() == ShapeType.TRAPEZOID) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
            int realXStartSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).first;
            int realYStartSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).second;
            int realXEndSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            int realYEndSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
            canvas.drawLine(realXStartSecond, realYStartSecond, realXEndSecond, realYEndSecond, paintSymmetryLine);
            canvas.drawCircle((realXStartSecond + realXEndSecond) / 2, (realYStartSecond + realYEndSecond) / 2
                    , paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            int realXStartThird = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).first;
            int realYStartThird = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
            int realXEndThird = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
            int realYEndThird = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).second;
            canvas.drawLine(realXStartThird, realYStartThird, realXEndThird, realYEndThird, paintSymmetryLine);
            canvas.drawCircle((realXStartThird + realXEndThird) / 2, (realYStartThird + realYEndThird) / 2
                    , paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            //Draw math
            int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
            int yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText(("A = "+Math.abs(gameState.getShapeFourCorners().getBottomLeft().getY()-gameState.getShapeFourCorners().getTopLeft().getY())
                            +"×(" + Math.abs(gameState.getShapeFourCorners().getBottomLeft().getX()-gameState.getShapeFourCorners().getBottomRight().getX()))
                            .replace(".0", "")+ "+"
                            + ("" + Math.abs(gameState.getShapeFourCorners().getTopLeft().getX()-gameState.getShapeFourCorners().getTopRight().getX())).replace(".0", "") + ")/2"
                    , xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
            canvas.drawText("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()),
                    xPos, yPos, paintWhiteText);
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText(("" + Math.abs(gameState.getShapeFourCorners().getBottomLeft().getX()-gameState.getShapeFourCorners().getBottomRight().getX()))
                            .replace(".0", ""), (realXStartSecond + realXEndSecond) / 2,
                    (realYStartSecond + realYEndSecond) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
            canvas.drawText(("" + Math.abs(gameState.getShapeFourCorners().getBottomLeft().getY()-gameState.getShapeFourCorners().getTopLeft().getY()))
                            .replace(".0", ""), (realXStart + realXEnd) / 2,
                    (realYStart + realYEnd) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
            canvas.drawText(("" + Math.abs(gameState.getShapeFourCorners().getTopLeft().getX()-gameState.getShapeFourCorners().getTopRight().getX()))
                            .replace(".0", ""), (realXStartThird + realXEndThird) / 2,
                    (realYStartThird + realYEndThird) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
        }
    }
}
