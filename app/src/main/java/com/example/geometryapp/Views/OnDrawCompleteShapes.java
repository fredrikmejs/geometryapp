package com.example.geometryapp.Views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.CoordinateSystem;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.ShapeFourCorners;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.Enum.ShapeType;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Interface.CanvasDraw;
import com.example.geometryapp.R;

import static com.example.geometryapp.Views.Canvas.RoundedRect;

public class OnDrawCompleteShapes extends View implements CanvasDraw {
    private CoordinateSystem coordinateSystem;// Contains coordinate system coordinates and their real coordinates on the screen.
    private Paint paintSymmetryPoint;
    private Paint paintWhiteText;
    private Paint paintLine;
    private Paint paintCoordinateSelectedDot;
    private Paint paintSymmetryLine;
    private android.graphics.Canvas canvas;
    private GameState gameState;
    private Paint paintFillShape;

    public OnDrawCompleteShapes(Context context, android.graphics.Canvas canvas, GameState gameState, CoordinateSystem coordinateSystem, Paint paintSymmetryLine, Paint paintCooridnateSelecetedDot,
                        Paint paintWhiteText, Paint paintLine, Paint paintSymmetryPoint, Paint paintFillShape) {
        super(context);
        this.canvas = canvas;
        this.gameState = gameState;
        this.coordinateSystem = coordinateSystem;
        this.paintSymmetryLine = paintSymmetryLine;
        this.paintCoordinateSelectedDot = paintCooridnateSelecetedDot;
        this.paintLine = paintLine;
        this.paintWhiteText = paintWhiteText;
        this.paintSymmetryPoint = paintSymmetryPoint;
        this.paintFillShape = paintFillShape;
    }

    @Override
    public void drawRectangle(Rectangle rectangle){
        gameState.getRectangle().setTopLeft(new Coordinate(gameState.getRectangle().getTopLeft().getX()
                , gameState.getSelectedDot().getCoordinate().getY()));
        gameState.getRectangle().setTopRight(new Coordinate(gameState.getRectangle().getBottomRight().getX()
                , gameState.getSelectedDot().getCoordinate().getY()));
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
        int realXStartLine = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first;
        int realYStartLine = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second;
        int realXEndLine = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first;
        int realYEndLine = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).second;
        canvas.drawLine(realXStartLine, realYStartLine, realXEndLine, realYEndLine, paintSymmetryLine);

        realXStartLine = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
        realYStartLine = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;
        realXEndLine = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
        realYEndLine = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;
        canvas.drawLine(realXStartLine, realYStartLine, realXEndLine, realYEndLine, paintSymmetryLine);

        int pointXTopL = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
        int pointYTopL = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;
        int pointXTopR = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
        int pointYTopR = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;
        int point1x = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
        int point1y = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;
        int point2x = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
        int point2y = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;
        int point3x = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first;
        int point3y = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).second;
        int point4x = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first;
        int point4y = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second;
        int xPos = (point1x + point4x) /2;
        int yPos = (point1y + point4y)/2;

        if (gameState.isAnsweredCorrectly()){
            paintLine.setColor(getResources().getColor(R.color.LightGreen));
        } else {
            paintLine.setColor(paintCoordinateSelectedDot.getColor());
        }
        canvas.drawCircle(xPos-15, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintLine);
        canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getYScale()), xPos-15, yPos, paintWhiteText);

        xPos = (point4x + point3x)/2;
        yPos = (point4y + point3y)/2;
        canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintLine);
        canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);

        xPos = (point2x + point3x) /2;
        yPos = (point2y + point3y)/2;
        canvas.drawCircle(xPos+15, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintLine);
        canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getYScale()), xPos+15, yPos, paintWhiteText);

        //TODO maybe close top
        canvas.drawLine(realXStartLine,realYStartLine,pointXTopL,pointYTopL,paintSymmetryLine);
        canvas.drawLine(realXEndLine,realYEndLine,pointXTopR,pointYTopR,paintSymmetryLine);

        if (gameState.isAnsweredCorrectly()) {
            //Draw math
            xPos = (coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomLeft()).first + coordinateSystem.getCanvasRealCoordinate(rectangle.getTopRight()).first) / 2;
            yPos = (coordinateSystem.getCanvasRealCoordinate(rectangle.getBottomLeft()).second + coordinateSystem.getCanvasRealCoordinate(rectangle.getTopRight()).second) / 2;

            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintLine);
            canvas.drawText("A = " + rectangle.getHeight(gameState.getYScale()) + "×"
                    + rectangle.getWidth(gameState.getXScale()), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
            canvas.drawText("  = " + rectangle.calculateArea(gameState.getXScale(), gameState.getYScale()),
                    xPos, yPos, paintWhiteText);
        }
    }

    @Override
    public void drawCircle(Circle circle) {

    }

    @Override
    public void drawTriangle(Triangle triangle) {

    }

    @Override
    public void drawRectangle() {

    }
    @Override
    public void drawCircle(){
        int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;
        int xDifference = Math.abs(gameState.getCircle().getCenter().getY() - gameState.getSelectedDot().getCoordinate().getY());
        int yDifference = Math.abs(gameState.getCircle().getCenter().getX() - gameState.getSelectedDot().getCoordinate().getX());
        gameState.getCircle().setRadius(Math.max(xDifference, yDifference));
        canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintFillShape);
        canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintFillShape);

        canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintSymmetryPoint);
        canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);

        int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getDrawCircle()).first;
        int yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getDrawCircle()).second;

        canvas.drawLine(realXStart,realYStart,xPos,realYStart, paintSymmetryLine);
        canvas.drawCircle(xPos+10,yPos, paintCoordinateSelectedDot.getStrokeWidth(),paintCoordinateSelectedDot);
        canvas.drawText("" + gameState.getCircle().getRadius()*gameState.getXScale(),xPos+19,yPos,paintWhiteText);
    }

    @Override
    public void drawTriangle(){
        gameState.getTriangle().setSecondPoint(gameState.getSelectedDot().getCoordinate());
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
        int realXStartLine = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
        int realYStartLine = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
        int realXEndLine = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
        int realYEndLine = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
        canvas.drawLine(realXStartLine, realYStartLine, realXEndLine, realYEndLine, paintSymmetryLine);

        int point1x = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
        int point1y = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;

        int point2x = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
        int point2y = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;

        int point3x = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
        int point3y = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;

        canvas.drawLine(realXStartLine,realYStartLine,point2x,point2y,paintSymmetryLine);
        canvas.drawLine(realXEndLine,realYEndLine,point2x,point2y,paintSymmetryLine);

        int xPos = (point1x + point2x)/2;
        int yPos = (point1y + point2y)/2;
        Coordinate coordinate1 = gameState.getTriangle().getFirstPoint();
        Coordinate coordinate2 = gameState.getTriangle().getSecondPoint();
        canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
        canvas.drawText("" + gameState.getTriangle().calculateDistanceTwoCoordinates(coordinate1,coordinate2,gameState.getXScale(),gameState.getYScale(),
                true),xPos,yPos,paintWhiteText);

        xPos = (point1x + point3x)/2;
        yPos = (point1y + point3y)/2;
        coordinate1 = gameState.getTriangle().getFirstPoint();
        coordinate2 = gameState.getTriangle().getThirdPoint();
        canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
        canvas.drawText("" + gameState.getTriangle().calculateDistanceTwoCoordinates(coordinate1,coordinate2,gameState.getXScale(),gameState.getYScale()
                ,true),xPos,yPos,paintWhiteText);

        xPos = (point3x + point2x)/2;
        yPos = (point3y + point2y)/2;
        coordinate1 = gameState.getTriangle().getThirdPoint();
        coordinate2 = gameState.getTriangle().getSecondPoint();
        canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
        canvas.drawText("" + gameState.getTriangle().calculateDistanceTwoCoordinates(coordinate1,coordinate2,gameState.getXScale(),gameState.getYScale()
                ,true),xPos,yPos,paintWhiteText);

        if(gameState.isAnsweredCorrectly()){
            xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
            yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
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
        Path path = new Path();
        int realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).first;
        int realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
        path.moveTo(realXEnd, realYEnd);
        realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
        realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).second;
        path.lineTo(realXEnd, realYEnd);

        try {
            gameState.getShapeFourCorners().setBottomRight(gameState.getSelectedDot().getCoordinate());
            gameState.getShapeFourCorners().setBottomLeft(gameState.getSelectedDot().getPreviousCoordinates()
                    .get(gameState.getSelectedDot().getPreviousCoordinates().size() - 2));
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).second;
            path.lineTo(realXEnd, realYEnd);
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
            path.lineTo(realXEnd, realYEnd);
        } catch (Exception ignored) {
        }
        path.close();
        canvas.drawPath(path, paintFillShape);
        int realXStartLine = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).first;
        int realYStartLine = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;
        int realXEndLine = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
        int realYEndLine = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).second;
        canvas.drawLine(realXStartLine, realYStartLine, realXEndLine, realYEndLine, paintSymmetryLine);
        if ((gameState.isAnsweredCorrectly()) && gameState.getShapeFourCorners().getShapeType() == ShapeType.KITE) {
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
        if ((gameState.isAnsweredCorrectly()) && gameState.getShapeFourCorners().getShapeType() == ShapeType.PARALLELOGRAM) {
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
        if ((gameState.isAnsweredCorrectly()) && gameState.getShapeFourCorners().getShapeType() == ShapeType.TRAPEZOID) {
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

    @Override
    public void drawPointShape(ShapeFourCorners shapeFourCorners) {

    }
}
