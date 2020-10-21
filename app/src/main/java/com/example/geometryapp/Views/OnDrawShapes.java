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
import com.example.geometryapp.Singleton;
import static com.example.geometryapp.Views.Canvas.RoundedRect;

public class OnDrawShapes extends View implements CanvasDraw {
    private CoordinateSystem coordinateSystem;// Contains coordinate system coordinates and their real coordinates on the screen.
    private Paint paintSymmetryPoint;
    private Paint paintWhiteText;
    private Paint paintLine;
    private Paint paintCoordinateSelectedDot;
    private Paint paintSymmetryLine;
    private android.graphics.Canvas canvas;
    private GameState gameState;
    private Paint paintCompleteFigure;
    private Paint paintFillShape;

    public OnDrawShapes(Context context, android.graphics.Canvas canvas, GameState gameState, CoordinateSystem coordinateSystem, Paint paintSymmetryLine, Paint paintCooridnateSelecetedDot,
                                     Paint paintWhiteText, Paint paintLine, Paint paintSymmetryPoint, Paint paintCompleteFigure, Paint paintFillShape) {
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
        this.paintFillShape = paintFillShape;
    }

    /**
     * Draws rectangles for OnDrawShapes()
     */
    @Override
    public void drawRectangle(){
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

        int point1x = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).first;
        int point1y = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopLeft()).second;

        int point2x = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first;
        int point2y = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second;

        int point3x = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).first;
        int point3y = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomRight()).second;

        int point4x = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first;
        int point4y = coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second;

        paintLine.setColor(getResources().getColor(R.color.SymmetryLine));
        paintLine.setStrokeWidth(10);
        canvas.drawLine(point1x,point1y,point2x,point2y,paintLine);
        canvas.drawLine(point1x,point1y,point4x,point4y,paintLine);
        canvas.drawLine(point2x,point2y,point3x,point3y,paintLine);
        canvas.drawLine(point4x,point4y,point3x,point3y,paintLine);

        int xPos;
        int yPos;


/*
                //CAN DRAW text on the lines
                canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);

                xPos = (point1x + point4x) /2;
                yPos = (point1y + point4y)/2;
                canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getXScale()), xPos, yPos, paintWhiteText);

                xPos = (point4x + point3x)/2;
                yPos = (point4y + point3y)/2;
                canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);

                xPos = (point2x + point3x) /2;
                yPos = (point2y + point3y)/2;
                canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getXScale()), xPos, yPos, paintWhiteText);
 */

        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            //Draw math
            xPos = (coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).first
                    + coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).first) / 2;
            yPos = (coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getBottomLeft()).second
                    + coordinateSystem.getCanvasRealCoordinate(gameState.getRectangle().getTopRight()).second) / 2;
            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
            canvas.drawText("A = " + gameState.getRectangle().getHeight(gameState.getYScale()) + "×"
                    + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
            canvas.drawText("  = " + gameState.getRectangle().calculateArea(gameState.getXScale(), gameState.getYScale()),
                    xPos, yPos, paintWhiteText);

            if (gameState.isAnsweredCorrectly()){
                paintLine.setColor(getResources().getColor(R.color.LightGreen));

            } else {
                paintLine.setColor(getResources().getColor(R.color.Red));
            }

            paintLine.setStrokeWidth(10);

            canvas.drawLine(point1x,point1y,point2x,point2y,paintLine);
            canvas.drawLine(point1x,point1y,point4x,point4y,paintLine);
            canvas.drawLine(point2x,point2y,point3x,point3y,paintLine);
            canvas.drawLine(point4x,point4y,point3x,point3y,paintLine);

            xPos = (point1x + point2x) / 2;
            yPos = (point1y + point2y) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

            canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);

            xPos = (point1x + point4x) /2;
            yPos = (point1y + point4y)/2;
            canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getYScale()), xPos, yPos, paintWhiteText);

            xPos = (point4x + point3x)/2;
            yPos = (point4y + point3y)/2;
            canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + gameState.getRectangle().getWidth(gameState.getXScale()), xPos, yPos, paintWhiteText);

            xPos = (point2x + point3x) /2;
            yPos = (point2y + point3y)/2;
            canvas.drawCircle(xPos, yPos, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + gameState.getRectangle().getHeight(gameState.getYScale()), xPos, yPos, paintWhiteText);


        }
    }

    /**
     * Draws circles for OnDrawShapes()
     */
    @Override
    public void drawCircle(){
        int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
        int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;
        //sets the color of the outer circle
        canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintFillShape);
        canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintFillShape);

        canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintSymmetryPoint);
        canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);

        int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getDrawCircle()).first;
        canvas.drawLine(realXStart,realYStart,xPos,realYStart, paintSymmetryLine);

        //Writes radius of the circle
            /*
            canvas.drawCircle(xPos,yPos, paintCoordinateSelectedDot.getStrokeWidth(),paintCoordinateSelectedDot);
            canvas.drawText("" + gameState.getCircle().getRadius()*gameState.getXScale(),xPos,yPos,paintWhiteText);
            */

        if(gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3){

            if (gameState.isAnsweredCorrectly()){
                paintSymmetryPoint.setColor(getResources().getColor(R.color.LightGreen));
            } else {
                paintSymmetryPoint.setColor(getResources().getColor(R.color.Red));
            }
            canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintSymmetryPoint);
            canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);

            int xPos1 = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
            int yPos1 = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;

            canvas.drawPath(RoundedRect(xPos1 - canvas.getWidth() / 9,
                    yPos1 - (paintWhiteText.descent() - paintWhiteText.ascent()) * 1+10,
                    xPos1 + canvas.getWidth() / 9
                    , yPos1 + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f+2,
                    canvas.getWidth() / 40
                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

            if (gameState.getCircle().getRadius() != 1){

                canvas.drawText("P = " + gameState.getCircle().getRadius()*gameState.getXScale() + "×" +  gameState.getCircle().getRadius()*gameState.getXScale() + "×\uD835\uDF0B", xPos1, yPos1, paintWhiteText);
                canvas.drawText("= " + gameState.getCircle().calculateArea(gameState.getXScale())*gameState.getXScale() ,xPos1,yPos1+50,paintWhiteText);
            } else {
                canvas.drawText("P = " + gameState.getCircle().getRadius()*gameState.getXScale() + "×" +  gameState.getCircle().getRadius() + "×\uD835\uDF0B" + gameState.getCircle().getRadius()*gameState.getXScale(), xPos1, yPos1, paintWhiteText);
                canvas.drawText("= \uD835\uDF0B" ,xPos1,yPos1+50,paintWhiteText);
            }
        }
    }

    /**
     * Draws triangles for OnDrawShapes()
     */
    @Override
    public void drawTriangle(){

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

        int point1x = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
        int point1y = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;

        int point2x = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
        int point2y = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;

        int point3x = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
        int point3y = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;

        paintLine.setColor(getResources().getColor(R.color.SymmetryLine));
        paintLine.setStrokeWidth(5);
        canvas.drawLine(point1x,point1y,point2x,point2y,paintLine);
        canvas.drawLine(point1x,point1y,point3x,point3y,paintLine);
        canvas.drawLine(point2x,point2y,point3x,point3y,paintLine);

        //draws distance between two points
        int xPos;
        int yPos;
        Singleton singleton = Singleton.getInstance();
        Coordinate firstPoint = gameState.getTriangle().getFirstPoint();
        Coordinate secondPoint = gameState.getTriangle().getSecondPoint();
        Coordinate thirdPoint = gameState.getTriangle().getThirdPoint();
        int xScale = gameState.getXScale();
        int yScale = gameState.getYScale();
        int realXStart = 0;
        int realYStart = 0;
        double dist1_2 = gameState.getTriangle().calculateDistanceTwoCoordinates(firstPoint,secondPoint,xScale,yScale,true);
        double dist1_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(firstPoint,thirdPoint,xScale,yScale,true);
        double dist2_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(secondPoint,thirdPoint,xScale,yScale,true);


        int randomNum = singleton.getRandomNum();
        if (gameState.isAnsweredCorrectly()){
            paintLine.setColor(getResources().getColor(R.color.LightGreen));

        } else {
            paintLine.setColor(getResources().getColor(R.color.Red));
        }
        paintLine.setStrokeWidth(5);

        canvas.drawLine(point1x,point1y,point2x,point2y,paintLine);
        canvas.drawLine(point1x,point1y,point3x,point3y,paintLine);
        canvas.drawLine(point2x,point2y,point3x,point3y,paintLine);

        if (randomNum == 1) {
            //a is a side of the triangle
            double a = 0;

            if (dist1_2 == dist1_3) {
                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
                a = dist2_3;
            } else if (dist1_2 == dist2_3) {
                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                a = dist1_3;
            } else if (dist1_3 == dist2_3) {
                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                a = dist1_2;
            }
            canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
            canvas.drawText("" + a, (realXStart + realXEnd) / 2
                    , (realYStart + realYEnd) / 2 - (int) (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);
        }

        //If correct or all attempts are used draws math
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {
            //special case because if triangle cases
            if (gameState.getLevel() == 8) {
                if (randomNum == 1) {
                    realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                    realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                    realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                    realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
                    canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
                    canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                    canvas.drawText(("" + dist1_2).replace(".0", ""), (realXStart + realXEnd) / 2,
                            (realYStart + realYEnd) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);

                    xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                    yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;

                    canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10 - 15, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10 + 15
                            , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                            , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                    canvas.drawText("A = (" + dist1_3 + "×" + dist2_3 + ")/2"
                            , xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);

                } else {
                    String b = ("" + Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getSecondPoint().getX())).replace(".0", "");
                    String c = ("" + Math.abs(gameState.getTriangle().getThirdPoint().getY() - gameState.getTriangle().getSecondPoint().getY())).replace(".0", "");
                    xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                    yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;

                    canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10 - 15, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10 + 15
                            , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                            , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                    canvas.drawText("A = (" + b + "×" + c + ")/2"
                            , xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
                }
                canvas.drawText("= " + gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()),
                        xPos, yPos, paintWhiteText);
            } else {
                String b = ("" + dist1_3).replace(".0", "");
                String c = ("" + dist1_2).replace(".0", "");
                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;

                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText(c, (realXStart + realXEnd) / 2,
                        (realYStart + realYEnd) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);

                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;

                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);
                canvas.drawText(b, (realXStart + realXEnd) / 2,
                        (realYStart + realYEnd) / 2 - (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);

                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
                xPos = (realXStart + realXEnd)/2;
                yPos = (realYStart + realYEnd)/2;

                canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10 - 15, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10 + 15
                        , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                canvas.drawText("A = (" + b + "×" + c + ")/2"
                        , xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
                canvas.drawText("= " + gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale(),gameState.getLevel(),gameState.getCategory()),
                        xPos, yPos, paintWhiteText);
            }
        }

        int num = singleton.getRandomNum();
        if (gameState.getAttempt() < 3) {
            double distance1_2 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getSecondPoint(),
                    gameState.getXScale(), gameState.getYScale(), true);

            double distance1_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                    gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getThirdPoint()
                    , gameState.getXScale(), gameState.getYScale(), true);

            if (!(distance1_2 % 1 == 0)) {
                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
                xPos = (realXStart + realXEnd) / 2;
                yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

                canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2, xPos + canvas.getWidth() / 10
                        , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * 1.5f, canvas.getWidth() / 40
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                canvas.drawText("l = √(" + Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getFirstPoint().getX()) + "² + "
                                + Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getFirstPoint().getY()) + "²)", xPos,
                        yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1, paintWhiteText);

                canvas.drawText(" = √(" + (Math.abs(gameState.getTriangle().getThirdPoint().getX() - gameState.getTriangle().getFirstPoint().getX())
                                * Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getFirstPoint().getX())
                                + Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getFirstPoint().getY())
                                * Math.abs(gameState.getTriangle().getSecondPoint().getY() - gameState.getTriangle().getFirstPoint().getY())) + ")", xPos
                        , yPos, paintWhiteText);
                canvas.drawText(" = " + gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getFirstPoint()
                        , gameState.getXScale(), gameState.getYScale(), true), xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), paintWhiteText);

            } else if(!(distance1_3 %1 == 0) && distance1_3 != distance1_2 && num  != 2){
                realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
                xPos = (realXStart + realXEnd) / 2;
                yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

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
        //Draws math
        if (num == 2){
            realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
            realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
            xPos = (realXStart + realXEnd) / 2;
            yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

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

    @Override
    public void drawRectangle(Rectangle rectangle) {

    }

    @Override
    public void drawCircle(Circle circle) {

    }

    @Override
    public void drawTriangle(Triangle triangle) {

    }

    /**
     * Draws point shapes for OnDrawShapes()
     */
    public void drawPointShape(){
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

        int point1x = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).first;
        int point1y = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;

        int point2x = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
        int point2y = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).second;

        int point3x = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).first;
        int point3y = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).second;

        int point4x = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
        int point4y = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;

        paintLine.setColor(getResources().getColor(R.color.SymmetryLine));
        paintLine.setStrokeWidth(5);
        canvas.drawLine(point1x,point1y,point2x,point2y,paintLine);
        canvas.drawLine(point1x,point1y,point4x,point4y,paintLine);
        canvas.drawLine(point2x,point2y,point3x,point3y,paintLine);
        canvas.drawLine(point4x,point4y,point3x,point3y,paintLine);

        if ((gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) && gameState.getShapeFourCorners().getShapeType() == ShapeType.KITE) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomLeft()).second;
            realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).first;
            realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopRight()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
            int realXStartSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).first;
            int realYStartSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getBottomRight()).second;
            int realXEndSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).first;
            int realYEndSecond = coordinateSystem.getCanvasRealCoordinate(gameState.getShapeFourCorners().getTopLeft()).second;

            if(gameState.isAnsweredCorrectly()){
                paintLine.setColor(getResources().getColor(R.color.LightGreen));
            } else {
                paintLine.setColor(getResources().getColor(R.color.Red));
            }
            paintLine.setStrokeWidth(5);
            canvas.drawLine(point1x,point1y,point2x,point2y,paintLine);
            canvas.drawLine(point1x,point1y,point4x,point4y,paintLine);
            canvas.drawLine(point2x,point2y,point3x,point3y,paintLine);
            canvas.drawLine(point4x,point4y,point3x,point3y,paintLine);

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

        if ((gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) && gameState.getShapeFourCorners().getShapeType() == ShapeType.PARALLELOGRAM) {
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

            if (gameState.isAnsweredCorrectly()){
                paintLine.setColor(getResources().getColor(R.color.LightGreen));

            } else {
                paintLine.setColor(getResources().getColor(R.color.Red));
            }
            paintLine.setStrokeWidth(5);
            canvas.drawLine(point1x,point1y,point2x,point2y,paintLine);
            canvas.drawLine(point1x,point1y,point4x,point4y,paintLine);
            canvas.drawLine(point2x,point2y,point3x,point3y,paintLine);
            canvas.drawLine(point4x,point4y,point3x,point3y,paintLine);

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
        if ((gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) && gameState.getShapeFourCorners().getShapeType() == ShapeType.TRAPEZOID) {
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

            if (gameState.isAnsweredCorrectly()){
                paintLine.setColor(getResources().getColor(R.color.LightGreen));

            } else {
                paintLine.setColor(getResources().getColor(R.color.Red));
            }
            paintLine.setStrokeWidth(5);
            canvas.drawLine(point1x,point1y,point2x,point2y,paintLine);
            canvas.drawLine(point1x,point1y,point4x,point4y,paintLine);
            canvas.drawLine(point2x,point2y,point3x,point3y,paintLine);
            canvas.drawLine(point4x,point4y,point3x,point3y,paintLine);

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
