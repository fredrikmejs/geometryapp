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
import com.example.geometryapp.GameState;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.Interface.CanvasDraw;
import com.example.geometryapp.Singleton;

import static com.example.geometryapp.Views.Canvas.RoundedRect;


public class DrawEmtpyShape extends View implements CanvasDraw {

    private CoordinateSystem coordinateSystem;// Contains coordinate system coordinates and their real coordinates on the screen.
    private Paint paintSymmetryPoint;
    private Paint paintWhiteText;
    private Paint paintLine;
    private Paint paintCoordinateSelectedDot;
    private Paint paintSymmetryLine;
    private android.graphics.Canvas canvas;
    private GameState gameState;

    public DrawEmtpyShape(Context context, android.graphics.Canvas canvas, GameState gameState, CoordinateSystem coordinateSystem, Paint paintSymmetryLine, Paint paintCooridnateSelecetedDot,
                          Paint paintWhiteText, Paint paintLine, Paint paintSymmetryPoint) {
        super(context);
        this.canvas = canvas;
        this.gameState = gameState;
        this.coordinateSystem = coordinateSystem;
        this.paintSymmetryLine = paintSymmetryLine;
        this.paintCoordinateSelectedDot = paintCooridnateSelecetedDot;
        this.paintLine = paintLine;
        this.paintWhiteText = paintWhiteText;
        this.paintSymmetryPoint = paintSymmetryPoint;
    }

    /**
     * Draws rectangle for DrawEmptyShape()
     *
     */
    @Override
    public void drawRectangle() {
        if (gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE && gameState.getRectangle() != null) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second;
            int realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
            int realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);

            //Draws correct answer point
            if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
                int xPos = (realXStart + realXEnd) / 2;
                int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getYScale()), xPos, yPos, paintWhiteText);


                int x = (coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first +
                        coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first) / 2;

                int y = (coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second +
                        coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second) / 2;

                canvas.drawPath(RoundedRect(x - canvas.getWidth() / 8, y - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2 + 25, x + canvas.getWidth() / 8
                        , y + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f + 3, canvas.getWidth() / 30
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                if (!(gameState.getRectangle().getWidth(gameState.getXScale()) == gameState.getRectangle().getHeight(gameState.getYScale()))) {
                    canvas.drawText("P = 2×( " + gameState.getRectangle().getWidth(gameState.getXScale()) + " + " + gameState.getRectangle().getHeight(gameState.getYScale())
                            + ")", x, y, paintWhiteText);
                } else {
                    canvas.drawText("P = 4 × " + gameState.getRectangle().getWidth(gameState.getXScale()), x, y, paintWhiteText);
                }
                canvas.drawText("= " + gameState.getRectangle().calculatePerimeter(gameState.getXScale(), gameState.getYScale()), x, y + 50, paintWhiteText);
            }
            realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
            realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);


            if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
                int xPos = (realXStart + realXEnd) / 2;
                int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);
            }


            realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
            realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);

            if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
                int xPos = (realXStart + realXEnd) / 2;
                int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getYScale()), xPos, yPos, paintWhiteText);
            }

            realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first;
            realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);


            if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
                int xPos = (realXStart + realXEnd) / 2;
                int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);
            }
        }
    }

    /**
     * Draws circle for DrawEmptyShape()
     */
    @Override
    public void drawCircle() {
        if (gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE && gameState.getCircle() != null) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;
            canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintSymmetryPoint);
            canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);

            if(gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3){
                int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getDrawCircle()).first;
                int yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getDrawCircle()).second;

                int xPos1 = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
                int yPos1 = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;

                canvas.drawLine(realXStart,realYStart,xPos,realYStart, paintSymmetryLine);

                canvas.drawCircle(xPos,yPos, paintCoordinateSelectedDot.getStrokeWidth(),paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getCircle().getRadius()*gameState.getXScale(),xPos,yPos,paintWhiteText);


                if(gameState.getCircle().getRadius() != 1) {
                    canvas.drawPath(RoundedRect(xPos1 - canvas.getWidth() / 9,
                            yPos1 - (paintWhiteText.descent() - paintWhiteText.ascent()) * 1+10,
                            xPos1 + canvas.getWidth() / 9
                            , yPos1 + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f+2,
                            canvas.getWidth() / 40
                            , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                    canvas.drawText("P = 2 x \uD835\uDF0B x " + gameState.getCircle().getRadius()*gameState.getXScale(), xPos1, yPos1, paintWhiteText);

                    canvas.drawText("= " + gameState.getCircle().getRadius()*2*gameState.getXScale()+" x \uD835\uDF0B",xPos1,yPos1+50,paintWhiteText);

                }
            }
        }
    }


    /**
     * This class is complex and is build around figuring out which side it should draw the length on.
     * Draws triangle for DrawEmptyShape()
     */
    @Override
    public void drawTriangle(){
        if (gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE && gameState.getTriangle() != null) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
            int realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
            int realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
            Singleton singleton = Singleton.getInstance();
            int num = singleton.getRandomNum();
            int xPos = (realXStart + realXEnd) / 2;
            int yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

            double distance1_2 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getSecondPoint(),
                    gameState.getXScale(), gameState.getYScale(), true);

            double distance1_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getThirdPoint()
                    , gameState.getXScale(), gameState.getYScale(), true);

            double distance2_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getThirdPoint()
                    , gameState.getXScale(), gameState.getYScale(), true);

            if (gameState.getLevel() == 8 && singleton.getRandomNum() == 2){
                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;

                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            }

            if(gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3){
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

                if (!(distance1_2 == distance2_3 || distance2_3 == distance1_3)) {

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
                } else if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
                    canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                    canvas.drawText(("" + gameState.getTriangle().calculateDistanceTwoCoordinates(
                            gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getThirdPoint()
                            , gameState.getXScale(), gameState.getYScale(), true)).replace(".0", ""), xPos, yPos, paintWhiteText);
                }
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

                if (!(distance1_2 == distance2_3 || distance2_3 == distance1_3 || distance1_2 == distance1_3)) {

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
                }
            }else if(gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3){
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText(("" + gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true)).replace(".0",""), xPos, yPos, paintWhiteText);
            }

            if (gameState.getAttempt() == 3 || gameState.isAnsweredCorrectly()){
                int xStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                int yStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                int xEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                int yEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
                xPos = (xStart + xEnd) / 2;
                yPos = (yStart + yEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

                if (!(distance1_3 %1 == 0)){
                    xEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                    yEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;

                    xPos = (xStart + xEnd) / 2;
                    yPos = (yStart + yEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
                }

                String distance1String = String.valueOf(distance1_2).replace(".0","");
                String distance2String = String.valueOf(distance1_3).replace(".0","");
                String distance3String = String.valueOf(distance2_3).replace(".0","");
                double permeter =  distance1_2 + distance1_3 + distance2_3;

                //Draws math
                canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10 -25, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10 + 15
                        , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                canvas.drawText("P = " + distance1String + " + " + distance2String + " + " +  distance3String, xPos,
                        yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);
                canvas.drawText(" = " + permeter, xPos
                        , yPos, paintWhiteText);
            }
            if (num == 2){
                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;

                xPos = (realXStart + realXEnd) / 2;
                yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
                //Draws math
                canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                        , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                canvas.drawText("l = √(" + Math.abs(gameState.getTriangle().getThirdPoint().getX() - gameState.getTriangle().getFirstPoint().getX()) + "² + "
                                + Math.abs(gameState.getTriangle().getThirdPoint().getY() - gameState.getTriangle().getFirstPoint().getY()) + "²)", xPos,
                        yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);

                canvas.drawText(" = √(" + (Math.abs(gameState.getTriangle().getThirdPoint().getX() - gameState.getTriangle().getFirstPoint().getX())
                                * Math.abs(gameState.getTriangle().getThirdPoint().getX() - gameState.getTriangle().getFirstPoint().getX())
                                + Math.abs(gameState.getTriangle().getThirdPoint().getY() - gameState.getTriangle().getFirstPoint().getY())
                                * Math.abs(gameState.getTriangle().getThirdPoint().getY() - gameState.getTriangle().getFirstPoint().getY())) + ")", xPos
                        , yPos, paintWhiteText);
                canvas.drawText(" = " + gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getThirdPoint(), gameState.getTriangle().getFirstPoint()
                        , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), paintWhiteText);

            }
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
