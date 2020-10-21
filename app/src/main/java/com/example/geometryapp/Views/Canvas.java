package com.example.geometryapp.Views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.example.geometryapp.CoordinateSystem;
import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Line;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.ShapeFourCorners;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.Enum.ShapeType;
import com.example.geometryapp.R;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Singleton;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * This class create the figures and the coordinate system
 */
public class Canvas extends View {

    //THIS CANVAS DRAWS THE COORDINATE SYSTEM AND EVERYTHING IN IT
    // TODO: 5.7.2020 This class is really big and it would be good idea to dived it to multiple classes

    private CoordinateSystem coordinateSystem;// Contains coordinate system coordinates and their real coordinates on the screen.
    private Paint paintCoordinateSmallDot;
    private Paint paintCoordinateBigDot;
    private Paint paintCoordinateSelectedDot;
    private Paint paintCoordinateTargetDot;
    private Paint paintYellowArrow;
    private Paint paintOrangeArrow;
    private Paint paintYellowLine;
    private Paint paintOrangeLine;
    private Paint paintCoordinateLine;
    private Paint paintCoordinateLineThick;
    public Paint paintSelectedCoordinateLineY;
    public Paint paintSelectedCoordinateLineX;
    private Paint paintText;
    private Paint paintSymmetryLine;
    private Paint paintCorrectAnswer;
    private Paint paintSymmetryPoint;
    private Paint paintLineFigureAnswer;
    private Paint paintCorrectLineFigure;
    private Paint paintWhiteText;
    private Paint paintCompleteFigure;
    private Paint paintFillShape;
    private Paint paintThinHelpLine;
    private Boolean paintValuesAdded = false;
    private Paint paintLine;

    private GameState gameState;

    public Canvas(Context context, GameState gameState) {
        super(context);
        paintCoordinateSmallDot = new Paint();
        paintCoordinateBigDot = new Paint();
        paintCoordinateSelectedDot = new Paint();
        paintCoordinateTargetDot = new Paint();
        paintYellowArrow = new Paint();
        paintOrangeArrow = new Paint();
        paintYellowLine = new Paint();
        paintOrangeLine = new Paint();
        paintCoordinateLine = new Paint();
        paintCoordinateLineThick = new Paint();
        paintSelectedCoordinateLineX = new Paint();
        paintSelectedCoordinateLineY = new Paint();
        paintSymmetryLine = new Paint();
        paintSymmetryPoint = new Paint();
        paintText = new Paint();
        coordinateSystem = new CoordinateSystem();
        paintCorrectAnswer = new Paint();
        paintLineFigureAnswer = new Paint();
        paintCorrectLineFigure = new Paint();
        paintWhiteText = new Paint();
        paintCompleteFigure = new Paint();
        paintFillShape = new Paint();
        paintThinHelpLine = new Paint();
        paintLine = new Paint();
        this.gameState = gameState;
    }

    @Override
    protected void onDraw(final android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (!paintValuesAdded) {
            paintValuesAdded = true;
            setPaintValues(canvas);
        }
        //121 = 11 * 11, is amount of points in coordinate system.
        //Coordinate system is created only ones because Xiaomi MIUI is creating problems when redrawing canvas.
        if (coordinateSystem.getCoordinatePairHashMap().size() != 121) {
            onCreateCoordinateSystem(canvas);
        }
        //This is the drawing order.
        onDrawCoordinateSystemLines(canvas);
        onDrawCoordinateOfTheCorrectOrWrongAnswer(canvas);
        onDrawScale(canvas);
        onDrawSymmetryLine(canvas);
        onDrawSymmetryFigures(canvas);
        onDrawCompleteEmptyShapes(canvas);
        onDrawCompleteShapes(canvas);
        onDrawOwnSymmetryFigures(canvas);
        onDrawEmptyShape(canvas);
        onDrawShapes(canvas);
        onDrawSelectedDot(canvas);
        onDrawTargetDot(canvas);
        onDrawSymmetryPoint(canvas);
        onDrawCorrectAnswer(canvas);
    }

    /**
     * Creates the coordinate system
     * @param canvas
     */
    private void onCreateCoordinateSystem(android.graphics.Canvas canvas) {
        //Saves real coordinates and coordinates system coordinates to coordinateSystem object
        int canvasHeight = canvas.getHeight() - 50;
        int canvasWidth = canvas.getWidth() - 50;
        for (int x = 0; x < 11; x += 1) {
            for (int y = 0; y < 11; y += 1) {
                int realX = Math.round(canvasWidth * x / 10 + 25);//real coordinates on the screen not coordinate system coordinates
                int realY = Math.round(canvasHeight * y / 10 + 25);//real coordinates on the screen not coordinate system coordinates
                coordinateSystem.addCoordinate(new Coordinate(x, y), realX, realY);
            }
        }
    }

    /**
     * Creates the scaling of the coordinate system
     * @param canvas
     */
    private void onDrawScale(android.graphics.Canvas canvas) {
        //Draws coordinate system scale arrows and scale numbers
        if (gameState.getCategory() != Categories.FINDPOINTWITHLINESYMMETRY && gameState.getCategory() != Categories.FINDPOINTWITHPOINTSYMMETRY) {
            onDrawScaleYArrow(canvas);
            onDrawScaleXArrow(canvas);
        }
    }

    /**
     * Draws the symmetry point for the symmetry category
     * @param canvas
     */
    private void onDrawSymmetryPoint(android.graphics.Canvas canvas) {
        if (gameState.getCategory() == Categories.FINDPOINTWITHPOINTSYMMETRY
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY
                || gameState.getCategory() == Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY) {
            int realX = coordinateSystem.getCanvasRealCoordinate(gameState.getSymmetryPoint()).first;
            int realY = coordinateSystem.getCanvasRealCoordinate(gameState.getSymmetryPoint()).second;
            canvas.drawCircle(realX, realY, paintSymmetryPoint.getStrokeWidth() * 2.5f, paintSymmetryPoint);
            canvas.drawCircle(realX, realY, paintSymmetryPoint.getStrokeWidth() / 2, paintSymmetryPoint);
        }
    }

    /**
     * Draws the line of the coordinate system
     * @param canvas
     */
    private void onDrawCoordinateSystemLines(android.graphics.Canvas canvas) {
        //Draws coordinate system horizontal lines
        for (int x = 0; x < 11; x++) {
            int fromX = coordinateSystem.getCanvasRealCoordinate(new Coordinate(x, 0)).first;
            int fromY = coordinateSystem.getCanvasRealCoordinate(new Coordinate(x, 0)).second;
            int toX = coordinateSystem.getCanvasRealCoordinate(new Coordinate(x, 10)).first;
            int toY = coordinateSystem.getCanvasRealCoordinate(new Coordinate(x, 10)).second;
            if (x == gameState.getSelectedDot().getCoordinate().getX()
                    && (gameState.getCategory() == Categories.POINTFROMCOORDINATES)) {
                canvas.drawLine(fromX, fromY, toX, toY, paintSelectedCoordinateLineX);
            } else if ((x - gameState.getOrigin().getX()) % 5 == 0) {
                canvas.drawLine(fromX, fromY, toX, toY, paintCoordinateLineThick);
            } else {
                canvas.drawLine(fromX, fromY, toX, toY, paintCoordinateLine);
            }
        }
        //Draws coordinate system vertical lines
        for (int y = 0; y < 11; y++) {
            int fromX = coordinateSystem.getCanvasRealCoordinate(new Coordinate(0, y)).first;
            int fromY = coordinateSystem.getCanvasRealCoordinate(new Coordinate(0, y)).second;
            int toX = coordinateSystem.getCanvasRealCoordinate(new Coordinate(10, y)).first;
            int toY = coordinateSystem.getCanvasRealCoordinate(new Coordinate(10, y)).second;
            if (y == gameState.getSelectedDot().getCoordinate().getY()
                    && (gameState.getCategory() == Categories.POINTFROMCOORDINATES)) {
                canvas.drawLine(fromX, fromY, toX, toY, paintSelectedCoordinateLineY);
            } else if ((y - gameState.getOrigin().getY()) % 5 == 0) {
                canvas.drawLine(fromX, fromY, toX, toY, paintCoordinateLineThick);
            } else {
                canvas.drawLine(fromX, fromY, toX, toY, paintCoordinateLine);
            }
        }
    }

    /**
     * Draws coordinate for answer in category 1
     * @param canvas
     */
    private void onDrawCoordinateOfTheCorrectOrWrongAnswer(android.graphics.Canvas canvas) {
        //Draws the y and x coordinate of the selected answer. This only used in Category 1. Point From Coordinates.
        if (gameState.getCategory() == Categories.POINTFROMCOORDINATES) {
            int xPosXAnswer = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getSelectedDot().getCoordinate().getX()
                    , gameState.getOrigin().getY())).first;
            int yPosXAnswer = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getSelectedDot().getCoordinate().getX()
                    , gameState.getOrigin().getY())).second;
            if (paintSelectedCoordinateLineX.getColor() == getResources().getColor(R.color.Red)) {
                paintText.setColor(getResources().getColor(R.color.Red));
                canvas.drawText("" + gameState.getXScale() * (gameState.getSelectedDot().getCoordinate().getX() - gameState.getOrigin().getX()),
                        xPosXAnswer + +canvas.getWidth() / 70, yPosXAnswer - canvas.getHeight() / 70, paintText);
            } else if (paintSelectedCoordinateLineX.getColor() == getResources().getColor(R.color.LightGreen)) {
                paintText.setColor(getResources().getColor(R.color.LightGreen));
                canvas.drawText("" + gameState.getXScale() * (gameState.getSelectedDot().getCoordinate().getX() - gameState.getOrigin().getX()),
                        xPosXAnswer + canvas.getWidth() / 70, yPosXAnswer - canvas.getHeight() / 70, paintText);
            }
            int xPosYAnswer = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX(),
                    gameState.getSelectedDot().getCoordinate().getY())).first;
            int yPosYAnswer = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX(),
                    gameState.getSelectedDot().getCoordinate().getY())).second;
            if (paintSelectedCoordinateLineY.getColor() == getResources().getColor(R.color.Red)) {
                paintText.setColor(getResources().getColor(R.color.Red));
                canvas.drawText("" + gameState.getYScale() * (gameState.getSelectedDot().getCoordinate().getY() - gameState.getOrigin().getY()),
                        xPosYAnswer + canvas.getWidth() / 70, yPosYAnswer - canvas.getHeight() / 70, paintText);
            } else if (paintSelectedCoordinateLineY.getColor() == getResources().getColor(R.color.LightGreen)) {
                paintText.setColor(getResources().getColor(R.color.LightGreen));
                canvas.drawText("" + gameState.getYScale() * (gameState.getSelectedDot().getCoordinate().getY() - gameState.getOrigin().getY()),
                        xPosYAnswer + canvas.getWidth() / 70, yPosYAnswer - canvas.getHeight() / 70, paintText);
            }
        }
    }

    /**
     * Draws the selected dot
     * @param canvas
     */
    private void onDrawSelectedDot(android.graphics.Canvas canvas) {
        //Draws a dot to the selected point
        if (gameState.getCategory() == Categories.POINTFROMCOORDINATES
                || gameState.getCategory() == Categories.FINDPOINTWITHPOINTSYMMETRY
                || gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER
                || gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA) {
            int coordinateSystemX = gameState.getSelectedDot().getCoordinate().getX();
            int coordinateSystemY = gameState.getSelectedDot().getCoordinate().getY();
            Pair<Integer, Integer> realCoordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(coordinateSystemX, coordinateSystemY));
            int realX = realCoordinate.first;
            int realY = realCoordinate.second;
            canvas.drawCircle(realX, realY, paintCoordinateSelectedDot.getStrokeWidth() / 2, paintCoordinateSelectedDot);
        }
        if (!gameState.isAnsweredCorrectly() && (gameState.getCategory() == Categories.FINDPOINTWITHLINESYMMETRY
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY)) {
            int coordinateSystemX = gameState.getSelectedDot().getCoordinate().getX();
            int coordinateSystemY = gameState.getSelectedDot().getCoordinate().getY();
            Pair<Integer, Integer> realCoordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(coordinateSystemX, coordinateSystemY));
            int realX = realCoordinate.first;
            int realY = realCoordinate.second;
            canvas.drawCircle(realX, realY, paintCoordinateSelectedDot.getStrokeWidth() / 2, paintCoordinateSelectedDot);
        }
    }

    /**
     * Draws the target dot for categories where you have one.
     * @param canvas
     */
    private void onDrawTargetDot(android.graphics.Canvas canvas) {
        //Draws the target dot.
        if (gameState.getTargetDot() == null) {
            return;
        }
        int coordinateSystemX = gameState.getTargetDot().getCoordinate().getX();
        int coordinateSystemY = gameState.getTargetDot().getCoordinate().getY();
        Pair<Integer, Integer> realCoordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(coordinateSystemX, coordinateSystemY));
        int realCoordinateX = realCoordinate.first;
        int realCoordinateY = realCoordinate.second;

        if(gameState.isAnsweredCorrectly()){
            paintCoordinateTargetDot.setColor(getResources().getColor(R.color.LightGreen));
        }

        canvas.drawCircle(realCoordinateX, realCoordinateY, paintCoordinateTargetDot.getStrokeWidth() / 2, paintCoordinateTargetDot);
    }

    /**
     * Draws arrow for Y scaling
     * @param canvas
     */
    private void onDrawScaleYArrow(android.graphics.Canvas canvas) {
        //Draws the vertical scale arrow and text that indicates the scale
        float angle, anglerad, radius, lineangle;

        //values to change for other appearance *CHANGE THESE FOR OTHER SIZE ARROWHEADS*
        radius = canvas.getHeight() / 30;
        angle = 45;

        int fromX = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX(), 0)).first;
        int fromY = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX(), 0)).second;
        int toX = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX(), 10)).first;
        int toY = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX(), 10)).second;

        //angle calculations
        anglerad = (float) (3.141 * angle / 180.0f);
        lineangle = (float) (atan2(toY - fromY, toX - fromX));

        //line
        canvas.drawLine(fromX, fromY, toX, toY + canvas.getHeight() / 80, paintYellowLine);

        //triangle
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(toX, toY);
        path.lineTo((float) (toX - radius * cos(lineangle - (anglerad / 2.0))),
                (float) (toY - radius * sin(lineangle - (anglerad / 2.0))));
        path.lineTo((float) (toX - radius * cos(lineangle + (anglerad / 2.0))),
                (float) (toY - radius * sin(lineangle + (anglerad / 2.0))));
        path.close();
        canvas.drawPath(path, paintYellowLine);

        //Small line to indicate scale
        Pair<Integer, Integer> coordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX(), gameState.getOrigin().getY() + 1));
        canvas.drawLine(coordinate.first + canvas.getWidth() / 60, coordinate.second,
                coordinate.first - canvas.getWidth() / 60, coordinate.second, paintYellowLine);

        //text
        int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getOrigin()).first + canvas.getWidth() / 20 + (int) ((paintText.descent() + paintText.ascent()) / 2);
        int yPos = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX(), gameState.getOrigin().getY() + 1)).second
                - (int) ((paintText.descent() + paintText.ascent()) / 2);
        paintText.setColor(getResources().getColor(R.color.ArrowYellow));
        canvas.drawText("" + gameState.getYScale(), xPos, yPos, paintText);
    }

    /**
     * Draws scaling for the X arrow
     * @param canvas
     */
    private void onDrawScaleXArrow(android.graphics.Canvas canvas) {
        //Draws the vertical scale arrow and text that indicates the scale
        float angle, anglerad, radius, lineangle;

        //values to change for other appearance *CHA
        // NGE THESE FOR OTHER SIZE ARROWHEADS*
        radius = canvas.getHeight() / 30;
        angle = 45;

        int fromX = coordinateSystem.getCanvasRealCoordinate(new Coordinate(0, gameState.getOrigin().getY())).first;
        int fromY = coordinateSystem.getCanvasRealCoordinate(new Coordinate(0, gameState.getOrigin().getY())).second;
        int toX = coordinateSystem.getCanvasRealCoordinate(new Coordinate(10, gameState.getOrigin().getY())).first;
        int toY = coordinateSystem.getCanvasRealCoordinate(new Coordinate(10, gameState.getOrigin().getY())).second;

        //some angle calculations
        anglerad = (float) (3.141 * angle / 180.0f);
        lineangle = (float) (atan2(toY - fromY, toX - fromX));

        //tha line
        canvas.drawLine(fromX, fromY, toX, toY, paintOrangeLine);

        //tha triangle
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(toX, toY);
        path.lineTo((float) (toX - radius * cos(lineangle - (anglerad / 2.0))),
                (float) (toY - radius * sin(lineangle - (anglerad / 2.0))));
        path.lineTo((float) (toX - radius * cos(lineangle + (anglerad / 2.0))),
                (float) (toY - radius * sin(lineangle + (anglerad / 2.0))));
        path.close();
        canvas.drawPath(path, paintOrangeArrow);canvas.drawPath(path, paintOrangeArrow);

        //Small line to indicate scale
        Pair<Integer, Integer> coordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX() + 1, gameState.getOrigin().getY()));
        canvas.drawLine(coordinate.first, coordinate.second + canvas.getHeight() / 60,
                coordinate.first, coordinate.second - canvas.getHeight() / 60, paintOrangeLine);

        //Text
        int xPos = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getOrigin().getX() + 1, gameState.getOrigin().getY())).first
                + (int) ((paintText.descent() + paintText.ascent()) / 2);
        int yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getOrigin()).second
                - canvas.getHeight() / 20 - (int) ((paintText.descent() + paintText.ascent()) / 2);
        paintText.setColor(getResources().getColor(R.color.ArrowOrange));
        canvas.drawText("" + gameState.getXScale(), xPos, yPos, paintText);
    }

    /**
     * Draws symmetry line
     * @param canvas
     */
    private void onDrawSymmetryLine(android.graphics.Canvas canvas) {
        //Draws a symmetry line
        if (gameState.getCategory() == Categories.FINDPOINTWITHLINESYMMETRY || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getSymmetryLine().getStartCoordinate().getX()
                    , gameState.getSymmetryLine().getStartCoordinate().getY())).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getSymmetryLine().getStartCoordinate().getX()
                    , gameState.getSymmetryLine().getStartCoordinate().getY())).second;
            int realXEnd = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getSymmetryLine().getEndCoordinate().getX()
                    , gameState.getSymmetryLine().getEndCoordinate().getY())).first;
            int realYEnd = coordinateSystem.getCanvasRealCoordinate(new Coordinate(gameState.getSymmetryLine().getEndCoordinate().getX()
                    , gameState.getSymmetryLine().getEndCoordinate().getY())).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
        }
    }

    /**
     * Draws the correct answer for the question
     * @param canvas
     * @throws NullPointerException
     */
    private void onDrawCorrectAnswer(android.graphics.Canvas canvas) throws NullPointerException {
        //Draws the correct answer. For example in category find figure of symmetry through point of symmetry, correct answer is drawn after 3 failed attemps

        //Draws a wrong answer while there is attempts reminding
        if (gameState.getCoordinateCorrectAnswer() != null && (gameState.getAttempt() < 3 && !gameState.isAnsweredCorrectly()) && gameState.getCategory() != Categories.POINTFROMCOORDINATES){
            Singleton singleton = Singleton.getInstance();
            int coordinateSystemX = singleton.getXCoordinate();
            int coordinateSystemY = singleton.getYCoordinate();
            Pair<Integer, Integer> realCoordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(coordinateSystemX, coordinateSystemY));
            int realCoordinateX = realCoordinate.first;
            int realCoordinateY = realCoordinate.second;
            paintCorrectAnswer.setColor(getResources().getColor(R.color.Red));
            double xSelected = gameState.getOrigin().getX() + (double) gameState.getSelectedDot().getCoordinate().getX() / gameState.getXScale();
            double ySelected = gameState.getOrigin().getY() + (double) gameState.getSelectedDot().getCoordinate().getY() / gameState.getYScale();
            if (singleton.getCategoryIndex() == 3 || singleton.getCategoryIndex() == 5) {
                if (xSelected == coordinateSystemX && ySelected == coordinateSystemY){
                    canvas.drawCircle(realCoordinateX, realCoordinateY, paintCorrectAnswer.getStrokeWidth() / 2, paintCorrectAnswer);
                }
            } else {
             canvas.drawCircle(realCoordinateX, realCoordinateY, paintCorrectAnswer.getStrokeWidth() / 2, paintCorrectAnswer);
            }
        }

        //Draws the correct answer if the game is over or correct
        if ((gameState.getAttempt() == 3 || gameState.isAnsweredCorrectly()) &&
                !(gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER || gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA ||
                        gameState.getCategory() == Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY || gameState.getCategory() == Categories.FINDAREAFROMFIGURE)) {
            Singleton singleton = Singleton.getInstance();

            if (singleton.getXCoordinate() != -1 && singleton.getYCoordinate() != -1) {
                int coordinateSystemX = singleton.getXCoordinate();
                int coordinateSystemY = singleton.getYCoordinate();
                Pair<Integer, Integer> realCoordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(coordinateSystemX, coordinateSystemY));

                int realCoordinateX = realCoordinate.first;
                int realCoordinateY = realCoordinate.second;
                paintCorrectAnswer.setColor(getResources().getColor(R.color.LightGreen));
                canvas.drawCircle(realCoordinateX, realCoordinateY, paintCorrectAnswer.getStrokeWidth() / 2, paintCorrectAnswer);
            }
        }
        //Draws the answer for FIND FIGURE OF SYMMETRY THROUGH POINT OF SYMMETRY
        if (gameState.getCategory() == Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY && gameState.getAttempt() >= 3) {
            Line line = gameState.getLineFigureCorrectAnswer().getLine(0);
            int realXStart = coordinateSystem.getCanvasRealCoordinate(line.getStartCoordinate()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(line.getStartCoordinate()).second;
            int realXEnd = coordinateSystem.getCanvasRealCoordinate(line.getEndCoordinate()).first;
            int realYEnd = coordinateSystem.getCanvasRealCoordinate(line.getEndCoordinate()).second;
            canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintCorrectLineFigure);
            Line line2 = gameState.getLineFigureCorrectAnswer().getLine(1);
            int realXStart2 = coordinateSystem.getCanvasRealCoordinate(line2.getStartCoordinate()).first;
            int realYStart2 = coordinateSystem.getCanvasRealCoordinate(line2.getStartCoordinate()).second;
            int realXEnd2 = coordinateSystem.getCanvasRealCoordinate(line2.getEndCoordinate()).first;
            int realYEnd2 = coordinateSystem.getCanvasRealCoordinate(line2.getEndCoordinate()).second;
            canvas.drawLine(realXStart2, realYStart2, realXEnd2, realYEnd2, paintCorrectLineFigure);
        }
    }

    /**
     * Draws symmetry figures (category 7)
     * @param canvas
     */
    private void onDrawSymmetryFigures(android.graphics.Canvas canvas) {
        //Draws the symmetry figure
        if (gameState.getCategory() == Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY) {
            for (int i = 0; i < gameState.getSymmetryFigure().getLines().size(); i++) {
                Line line = gameState.getSymmetryFigure().getLine(i);
                int realXStart = coordinateSystem.getCanvasRealCoordinate(line.getStartCoordinate()).first;
                int realYStart = coordinateSystem.getCanvasRealCoordinate(line.getStartCoordinate()).second;
                int realXEnd = coordinateSystem.getCanvasRealCoordinate(line.getEndCoordinate()).first;
                int realYEnd = coordinateSystem.getCanvasRealCoordinate(line.getEndCoordinate()).second;
                canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintSymmetryLine);
            }
        }
    }

    /**
     * Draws symmetry figures (category 7)
     * @param canvas
     */
    private void onDrawOwnSymmetryFigures(android.graphics.Canvas canvas) {
        //Draws the users own drawn symmetry figure
        if (gameState.isAnsweredCorrectly()){
            paintLine.setColor(getResources().getColor(R.color.LightGreen));
        } else if (!gameState.isAnsweredCorrectly()){
            paintLine.setColor(getResources().getColor(R.color.Red));
        } else {
            paintLine.setColor(paintLineFigureAnswer.getColor());
        }
        paintLine.setStrokeWidth(10);
        if (gameState.getCategory() == Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY) {
            if (gameState.getSelectedDot().getPreviousCoordinates().size() > 1) {
                int coordinateSystemX = gameState.getSelectedDot().getCoordinate().getX();
                int coordinateSystemY = gameState.getSelectedDot().getCoordinate().getY();
                Pair<Integer, Integer> realCoordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(coordinateSystemX, coordinateSystemY));
                int realX = realCoordinate.first;
                int realY = realCoordinate.second;
                canvas.drawCircle(realX, realY, paintCoordinateSelectedDot.getStrokeWidth() / 2, paintLine);
            }
            for (int i = gameState.getSelectedDot().getPreviousCoordinates().size() - 1; i > 1; i--) {
                Line line = new Line(gameState.getSelectedDot().getPreviousCoordinates().get(i)
                        , gameState.getSelectedDot().getPreviousCoordinates().get(i - 1));
                int realXStart = coordinateSystem.getCanvasRealCoordinate(line.getStartCoordinate()).first;
                int realYStart = coordinateSystem.getCanvasRealCoordinate(line.getStartCoordinate()).second;
                int realXEnd = coordinateSystem.getCanvasRealCoordinate(line.getEndCoordinate()).first;
                int realYEnd = coordinateSystem.getCanvasRealCoordinate(line.getEndCoordinate()).second;
                canvas.drawLine(realXStart, realYStart, realXEnd, realYEnd, paintLine);
            }
        }
    }

    private void onDrawEmptyShape(android.graphics.Canvas canvas) {
        //Draws an empty shape. This used in perimeter exercises
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3 && gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE ) {
            paintSymmetryLine.setColor(getResources().getColor(R.color.LightGreen));
            paintSymmetryPoint.setColor(getResources().getColor(R.color.LightGreen));
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.LightGreen));
        }

        DrawEmtpyShape drawEmtpyShape = new DrawEmtpyShape(getContext(),canvas,gameState,coordinateSystem,paintSymmetryLine,paintCoordinateSelectedDot,
                paintWhiteText,paintLine,paintSymmetryPoint);
        //Draws rectangle
        drawEmtpyShape.drawRectangle();

        //Draws circle
        drawEmtpyShape.drawCircle();


        //Draws triangle
       drawEmtpyShape.drawTriangle();
    }

    private void onDrawCorrectEmptyShape(android.graphics.Canvas canvas, Rectangle rectangle, Circle circle, Triangle triangle) {
        //This is used when drawn the correct shape after 3 attempt in category 9. complete figure from perimeter.
        paintSymmetryLine.setColor(getResources().getColor(R.color.LightGreen));
        paintSymmetryPoint.setColor(getResources().getColor(R.color.LightGreen));
        paintCompleteFigure.setColor(getResources().getColor(R.color.LightGreen));
        paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.LightGreen));
        DrawCorrectEmptyShape drawCorrectEmptyShape = new DrawCorrectEmptyShape(getContext(),canvas,gameState,coordinateSystem,paintSymmetryLine,
                paintCoordinateSelectedDot,paintWhiteText,paintLine,paintSymmetryPoint,paintCompleteFigure);
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && rectangle != null) {
            drawCorrectEmptyShape.drawRectangle(rectangle);
        }
        //Draws circle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && circle != null) {
             drawCorrectEmptyShape.drawCircle(circle);
         }
        //Draws triangle

        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && triangle != null) {
            drawCorrectEmptyShape.drawTriangle(triangle);
        }
    }

    //Correct answer shapes are saved into these objects. So that users drawn shape does not affect them
    // TODO: 5.7.2020 Canvas class should not have variables like these. They belong to the gameState. Redesining categories 9-11 for drawing shapes could be a good idea.
    boolean firstTime = true;
    Rectangle rectangle = null;
    Circle circle = null;
    Triangle triangle;  // TODO: 5.7.2020 This method is huge and should be dived into multiple small methods= null;
    ShapeFourCorners shapeFourCorners = null;

    private void onDrawCompleteEmptyShapes(android.graphics.Canvas canvas) {
        //Draws the users drawn empty shape. For example triangle in category 9. Comple figure from perimeter.
        if (firstTime && gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER) {
            firstTime = false;
            if (gameState.getRectangle() != null) {
                rectangle = new Rectangle(gameState.getRectangle().getTopLeft(), gameState.getRectangle().getTopRight()
                        , gameState.getRectangle().getBottomRight(), gameState.getRectangle().getBottomLeft());
            }
            if (gameState.getCircle() != null) {
                circle = new Circle(gameState.getCircle().getCenter(), gameState.getCircle().getRadius());
            }
            if (gameState.getTriangle() != null) {
                triangle = new Triangle(gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getSecondPoint()
                        , gameState.getTriangle().getThirdPoint());
            }
        }
        if (gameState.isAnsweredCorrectly() && gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER) {
            paintSymmetryLine.setColor(getResources().getColor(R.color.LightGreen));
            paintSymmetryPoint.setColor(getResources().getColor(R.color.LightGreen));
            paintCompleteFigure.setColor(getResources().getColor(R.color.LightGreen));
        } else if (!gameState.isAnsweredCorrectly() && gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && gameState.getAttempt() == 3) {
            paintSymmetryLine.setColor(getResources().getColor(R.color.Red));
            paintSymmetryPoint.setColor(getResources().getColor(R.color.Red));
            paintCompleteFigure.setColor(getResources().getColor(R.color.Red));
        }
        OnDrawCompleteEmptyShapes onDrawCompleteEmptyShapes = new OnDrawCompleteEmptyShapes(getContext(),canvas,gameState,coordinateSystem,
                paintSymmetryLine,paintCoordinateSelectedDot,paintWhiteText,paintLine,paintSymmetryPoint,paintCompleteFigure);
        //Draws rectangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && gameState.getRectangle() != null) {
            onDrawCompleteEmptyShapes.drawRectangle();
        }
        //Draws circle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && gameState.getCircle() != null) {
            onDrawCompleteEmptyShapes.drawCircle();
        }
        //Draws triangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && gameState.getTriangle() != null) {
            onDrawCompleteEmptyShapes.drawTriangle();
        }


        if (!gameState.isAnsweredCorrectly() && gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && gameState.getAttempt() == 3) {
            onDrawCorrectEmptyShape(canvas, rectangle, circle, triangle);
        }
    }

    private void onDrawShapes(android.graphics.Canvas canvas) {
        //Draws a shape that has fill color. For example for the area levels.
        if (gameState.isAnsweredCorrectly() && gameState.getCategory() == Categories.FINDAREAFROMFIGURE) {
            paintFillShape.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
            paintSymmetryLine.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
        }

        OnDrawShapes onDrawShapes = new OnDrawShapes(getContext(),canvas,gameState,coordinateSystem,
                paintSymmetryLine,paintCoordinateSelectedDot,paintWhiteText,paintLine,paintSymmetryPoint,paintCompleteFigure,paintFillShape);
        //Draws rectangle
        if (gameState.getCategory() == Categories.FINDAREAFROMFIGURE && gameState.getRectangle() != null) {
            onDrawShapes.drawRectangle();
        }  //Draws circle
        else if (gameState.getCategory() == Categories.FINDAREAFROMFIGURE && gameState.getCircle() != null) {
            onDrawShapes.drawCircle();
        }//Draws triangle
        else if (gameState.getCategory() == Categories.FINDAREAFROMFIGURE && gameState.getTriangle() != null) {
            onDrawShapes.drawTriangle();
        }//Draws any for point shape
        else if (gameState.getCategory() == Categories.FINDAREAFROMFIGURE && gameState.getShapeFourCorners() != null) {
            onDrawShapes.drawPointShape();
        }
    }

    private void onDrawCompleteShapes(android.graphics.Canvas canvas) {
        //Draws the shape according to users selected points. Used in area levels.
        if (firstTime && gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA) {
            firstTime = false;
            rectangle = null;
            circle = null;
            triangle = null;
            shapeFourCorners = null;
            if (gameState.getRectangle() != null) {
                rectangle = new Rectangle(gameState.getRectangle().getTopLeft(), gameState.getRectangle().getTopRight()
                        , gameState.getRectangle().getBottomRight(), gameState.getRectangle().getBottomLeft());
            }
            if (gameState.getCircle() != null) {
                circle = new Circle(gameState.getCircle().getCenter(), gameState.getCircle().getRadius());
            }
            if (gameState.getTriangle() != null) {
                triangle = new Triangle(gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getSecondPoint()
                        , gameState.getTriangle().getThirdPoint());
            }
            if (gameState.getShapeFourCorners() != null) {
                shapeFourCorners = new ShapeFourCorners(gameState.getShapeFourCorners().getTopLeft(), gameState.getShapeFourCorners().getTopRight()
                        , gameState.getShapeFourCorners().getBottomRight(), gameState.getShapeFourCorners().getBottomLeft(), gameState.getShapeFourCorners().getShapeType());
            }
        }
        if (gameState.isAnsweredCorrectly() && gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA) {
            paintSymmetryLine.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
            paintSymmetryPoint.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
            paintCompleteFigure.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
            paintFillShape.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
        } else if (!gameState.isAnsweredCorrectly() && gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getAttempt() == 3) {
            paintSymmetryLine.setColor(getResources().getColor(R.color.shapeColorWrongAlpha50));
            paintSymmetryPoint.setColor(getResources().getColor(R.color.shapeColorWrongAlpha50));
            paintCompleteFigure.setColor(getResources().getColor(R.color.shapeColorWrongAlpha50));
        }
        OnDrawCompleteShapes onDrawCompleteShapes = new OnDrawCompleteShapes(getContext(),canvas,gameState,coordinateSystem,
                paintSymmetryLine,paintCoordinateSelectedDot,paintWhiteText,paintLine,paintSymmetryPoint,paintFillShape);
        //Draws rectangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getRectangle() != null) {
            onDrawCompleteShapes.drawRectangle(rectangle);
        }
        //Draws circle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getCircle() != null) {
            onDrawCompleteShapes.drawCircle();
        }
        //Draws triangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getTriangle() != null) {
            onDrawCompleteShapes.drawTriangle();
        }

        //Draws any four point shape
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getShapeFourCorners() != null) {
            onDrawCompleteShapes.drawPointShape();
        }
        if (!gameState.isAnsweredCorrectly() && gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getAttempt() == 3) {
            onDrawCorrectShapes(canvas, rectangle, circle, triangle, shapeFourCorners);
        }
    }

    private void onDrawCorrectShapes(android.graphics.Canvas canvas, Rectangle rectangle, Circle circle, Triangle triangle, ShapeFourCorners shapeFourCorners) {
        //Draws the correct answer shape. For example in area levels after 3 attempts, correct answer shape is drawn.
        paintFillShape.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
        paintSymmetryLine.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
        paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.LightGreen));

        OnDrawCorrectShapes onDrawCorrectShapes = new OnDrawCorrectShapes(getContext(),canvas,gameState,coordinateSystem,paintSymmetryLine,paintCoordinateSelectedDot,paintWhiteText,
                paintLine,paintSymmetryPoint,paintFillShape);

        //Draws rectangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getRectangle() != null) {
            onDrawCorrectShapes.drawRectangle();
        }
        //Draws circle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getCircle() != null) {
            onDrawCorrectShapes.drawCircle();
        }
        //Draws triangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getTriangle() != null) {
            onDrawCorrectShapes.drawTriangle();
        }
        //Draws any for point shape
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getShapeFourCorners() != null) {
            onDrawCorrectShapes.drawPointShape();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //This method is called when user touches the coordinate system.
        //Only in the categories underneath users touch affects the coordinate system
        if (!gameState.isAnsweredCorrectly() && gameState.getAttempt() != 3) {
            if (gameState.getCategory() == Categories.POINTFROMCOORDINATES
                    || gameState.getCategory() == Categories.FINDPOINTWITHLINESYMMETRY
                    || gameState.getCategory() == Categories.FINDPOINTWITHPOINTSYMMETRY
                    || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                    || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY
                    || gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER
                    || gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA) {
                Coordinate coordinateSystemCoordinate = coordinateSystem.getClosestCoordinateSystemCoordinate((int) event.getX(), (int) event.getY());
                if (gameState.getSelectedDot().getCoordinate().getX() != coordinateSystemCoordinate.getX()
                        || gameState.getSelectedDot().getCoordinate().getY() != coordinateSystemCoordinate.getY()) {
                    gameState.setSelectedDotCoordinate(new Coordinate(coordinateSystemCoordinate.getX(), coordinateSystemCoordinate.getY()));
                }
            }
            if (gameState.getCategory() == Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY) {
                Coordinate coordinateSystemCoordinate = coordinateSystem.getClosestCoordinateSystemCoordinate((int) event.getX(), (int) event.getY());
                if ((gameState.getSelectedDot().getCoordinate().getX() != coordinateSystemCoordinate.getX()
                        || gameState.getSelectedDot().getCoordinate().getY() != coordinateSystemCoordinate.getY()) && gameState.getSelectedDot().getPreviousCoordinates().size() < 4) {
                    gameState.setSelectedDotCoordinate(new Coordinate(coordinateSystemCoordinate.getX(), coordinateSystemCoordinate.getY()));
                }
            }
            invalidate();
            setCoordinateXAndYColorWhite();
        }

        return super.onTouchEvent(event);
    }

    public void setCoordinateXAndYColor(boolean XGreen, boolean YGreen) {
        //Changes colors of the coordinate lines and selected dots. When answer is rigth this function is called.
        if (XGreen) {
            paintSelectedCoordinateLineX.setColor(getResources().getColor(R.color.LightGreen));
        } else {
            paintSelectedCoordinateLineX.setColor(getResources().getColor(R.color.Red));
        }
        if (YGreen) {
            paintSelectedCoordinateLineY.setColor(getResources().getColor(R.color.LightGreen));
        } else {
            paintSelectedCoordinateLineY.setColor(getResources().getColor(R.color.Red));
        }
        if (XGreen && YGreen) {
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.LightGreen));
            paintLineFigureAnswer.setColor(getResources().getColor(R.color.LightGreen));
        } else {
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.Red));
            paintLineFigureAnswer.setColor(getResources().getColor(R.color.Red));
        }
        if ((gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY)
                && !gameState.isAnsweredCorrectly() ) {
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.Purple));
        }
        invalidate();

    }

    // This method removes the red and green coordinate points when pressing a new place on the coordinate system
    public void setCoordinateXAndYColorWhite() {
        if (paintSelectedCoordinateLineX.getColor() !=  getResources().getColor(R.color.CoordinateSystemLine)) {
            paintSelectedCoordinateLineX.setColor(getResources().getColor(R.color.CoordinateSystemLine));
        }

        if (paintSelectedCoordinateLineY.getColor() !=  getResources().getColor(R.color.CoordinateSystemLine)) {
            paintSelectedCoordinateLineY.setColor(getResources().getColor(R.color.CoordinateSystemLine));
        }
    }

    public void setPaintValues(android.graphics.Canvas canvas) {
        //Paint values are set in here
        paintCoordinateSmallDot.setColor(getResources().getColor(R.color.CoordinateDot));
        paintCoordinateSmallDot.setStrokeWidth(canvas.getWidth() / 80);
        paintCoordinateBigDot.setColor(getResources().getColor(R.color.CoordinateDot));
        paintCoordinateBigDot.setStrokeWidth(canvas.getWidth() / 40);
        paintSelectedCoordinateLineY.setStrokeWidth(canvas.getWidth() / 500);
        paintSelectedCoordinateLineX.setStrokeWidth(canvas.getWidth() / 500);
        paintLineFigureAnswer.setStrokeWidth(canvas.getWidth() / 100);
        paintCompleteFigure.setStrokeWidth(canvas.getWidth() / 100);
        if (gameState.getSelectedDot().isPurpleColorOn()) {
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.Purple));
            paintSelectedCoordinateLineY.setColor(getResources().getColor(R.color.Purple));
            paintSelectedCoordinateLineX.setColor(getResources().getColor(R.color.Purple));
            paintLineFigureAnswer.setColor(getResources().getColor(R.color.Purple));
            paintCompleteFigure.setColor(getResources().getColor(R.color.Purple));
        } else {
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.Teal));
            paintSelectedCoordinateLineX.setColor(getResources().getColor(R.color.Teal));
            paintSelectedCoordinateLineY.setColor(getResources().getColor(R.color.Teal));
            paintLineFigureAnswer.setColor(getResources().getColor(R.color.Teal));
            paintCompleteFigure.setColor(getResources().getColor(R.color.Teal));
        }
        paintCoordinateSelectedDot.setStrokeWidth(canvas.getWidth() / 30);
        paintCoordinateTargetDot.setColor(getResources().getColor(R.color.TargetDot));
        paintCoordinateTargetDot.setStrokeWidth(canvas.getWidth() / 30);
        paintYellowArrow.setColor(getResources().getColor(R.color.ArrowYellow));
        paintYellowArrow.setStrokeWidth(canvas.getWidth() / 40);
        paintYellowLine.setColor(getResources().getColor(R.color.ArrowYellow));
        paintYellowLine.setStrokeWidth(canvas.getWidth() / 200);
        paintOrangeArrow.setColor(getResources().getColor(R.color.ArrowOrange));
        paintOrangeArrow.setStrokeWidth(canvas.getWidth() / 40);
        paintOrangeLine.setColor(getResources().getColor(R.color.ArrowOrange));
        paintOrangeLine.setStrokeWidth(canvas.getWidth() / 200);
        paintCoordinateLine.setColor(getResources().getColor(R.color.CoordinateSystemLine));
        paintCoordinateLine.setStrokeWidth(canvas.getWidth() / 500);
        paintCoordinateLineThick.setColor(getResources().getColor(R.color.CoordinateSystemLine));
        paintCoordinateLineThick.setStrokeWidth(canvas.getWidth() / 200);
        paintSymmetryLine.setColor(getResources().getColor(R.color.SymmetryLine));
        paintSymmetryLine.setStrokeWidth(canvas.getWidth() / 100);
        paintThinHelpLine.setColor(getResources().getColor(R.color.SymmetryLine));
        paintThinHelpLine.setStrokeWidth(canvas.getWidth() / 200);
        paintWhiteText.setColor(getResources().getColor(R.color.white));
        paintWhiteText.setTextSize(30);
        paintWhiteText.setTextAlign(Paint.Align.CENTER);
        paintText.setColor(getResources().getColor(R.color.TextScale));
        paintText.setTextSize(40);
        if (!gameState.isEnglishNumbers()) {
            paintWhiteText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            paintText.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        } else {
            paintWhiteText.setTypeface(ResourcesCompat.getFont(getContext(), R.font.bzar));
            paintText.setTypeface(ResourcesCompat.getFont(getContext(), R.font.bzar));
        }
        paintCorrectAnswer.setColor(getResources().getColor(R.color.LightGreen));
        paintCorrectAnswer.setStrokeWidth(canvas.getWidth() / 30);
        paintSymmetryPoint.setColor(getResources().getColor(R.color.SymmetryLine));
        paintSymmetryPoint.setStrokeWidth(canvas.getWidth() / 100);
        paintSymmetryPoint.setStyle(Paint.Style.STROKE);
        paintCorrectLineFigure.setStrokeWidth(getWidth() / 100);
        paintCorrectLineFigure.setColor(getResources().getColor(R.color.LightGreen));
        paintFillShape.setStyle(Paint.Style.FILL);
        paintFillShape.setColor(getResources().getColor(R.color.shapeColorAlpha50));
    }

    public GameState getGameState() {
        return gameState;
    }

    // TODO: 5.7.2020 Canvas class is already huge. It could be good idea to create different class for drawing shapes like rounded rect
    static public Path RoundedRect(float left, float top, float right, float bottom, float rx, float ry, boolean conformToOriginalPost) {
        //Creates a path for rounded rect
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        path.rLineTo(-widthMinusCorners, 0);
        path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        path.rLineTo(0, heightMinusCorners);

        if (conformToOriginalPost) {
            path.rLineTo(0, ry);
            path.rLineTo(width, 0);
            path.rLineTo(0, -ry);
        } else {
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
            path.rLineTo(widthMinusCorners, 0);
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.
        return path;
    }
}
