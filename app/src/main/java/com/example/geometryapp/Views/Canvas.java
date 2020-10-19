package com.example.geometryapp.Views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.example.geometryapp.CoordinateSystem;
import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Line;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.DrawObjects.ShapeFourCorners;
import com.example.geometryapp.DrawObjects.SymmetryLine;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.Enum.ShapeType;
import com.example.geometryapp.R;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Singleton;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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

    private void onDrawScale(android.graphics.Canvas canvas) {
        //Draws coordinate system scale arrows and scale numbers
        if (gameState.getCategory() != Categories.FINDPOINTWITHLINESYMMETRY && gameState.getCategory() != Categories.FINDPOINTWITHPOINTSYMMETRY) {
            onDrawScaleYArrow(canvas);
            onDrawScaleXArrow(canvas);
        }
    }

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
        canvas.drawCircle(realCoordinateX, realCoordinateY, paintCoordinateTargetDot.getStrokeWidth() / 2, paintCoordinateTargetDot);
    }

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

    private void onDrawCorrectAnswer(android.graphics.Canvas canvas) throws NullPointerException {
        //Draws the correct answer. For example in category find figure of symmetry through point of symmetry, correct answer is drawn after 3 failed attemps

        if (gameState.getCoordinateCorrectAnswer() != null && (gameState.getAttempt() < 3 && !gameState.isAnsweredCorrectly())){
            Singleton singleton = Singleton.getInstance();
            int coordinateSystemX = singleton.getXCoordinate();
            int coordinateSystemY = singleton.getYCoordinate();
            Pair<Integer, Integer> realCoordinate = coordinateSystem.getCanvasRealCoordinate(new Coordinate(coordinateSystemX, coordinateSystemY));
            int realCoordinateX = realCoordinate.first;
            int realCoordinateY = realCoordinate.second;
            paintCorrectAnswer.setColor(getResources().getColor(R.color.Red));
            double xSelected = gameState.getOrigin().getX() + (double) gameState.getSelectedDot().getCoordinate().getX() / gameState.getXScale();
            double ySelected = gameState.getOrigin().getY() + (double) gameState.getSelectedDot().getCoordinate().getY() / gameState.getYScale();
            if (singleton.getCategoryindex() == 3 || singleton.getCategoryindex() == 5) {
                if (xSelected == coordinateSystemX && ySelected == coordinateSystemY){
                    canvas.drawCircle(realCoordinateX, realCoordinateY, paintCorrectAnswer.getStrokeWidth() / 2, paintCorrectAnswer);
                }
            } else {
                canvas.drawCircle(realCoordinateX, realCoordinateY, paintCorrectAnswer.getStrokeWidth() / 2, paintCorrectAnswer);
            }
        }

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

    private void onDrawSymmetryFigures(android.graphics.Canvas canvas) {
        //Draws the symmetry figure
        if (gameState.getCategory() == Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY) {
            if(gameState.isAnsweredCorrectly()){
                paintSymmetryLine.setColor(getResources().getColor(R.color.LightGreen));
            }
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

    // TODO: 5.7.2020 This method is huge and should be dived into multiple small methods
    private void onDrawEmptyShape(android.graphics.Canvas canvas) {
        //Draws an empty shape. This used in perimeter exercises
        if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3 && gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE ) {
            paintSymmetryLine.setColor(getResources().getColor(R.color.LightGreen));
            paintSymmetryPoint.setColor(getResources().getColor(R.color.LightGreen));
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.LightGreen));
        }
        //Draws rectangle
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
                    canvas.drawText("P = 4 × "  + gameState.getRectangle().getWidth(gameState.getXScale()) , x, y, paintWhiteText);
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
        //Draws circle
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
        //Draws triangle
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


                Coordinate dist1 = gameState.getTriangle().getFirstPoint();
                Coordinate dist2 = gameState.getTriangle().getSecondPoint();
                Coordinate dist3 = gameState.getTriangle().getThirdPoint();



               /*
                double distance1_2 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        dist1, dist2, gameState.getXScale(), gameState.getYScale(), true);

                double distance1_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        dist1, dist3, gameState.getXScale(), gameState.getYScale(), true);

                double distance_2_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        dist2, dist3, gameState.getXScale(), gameState.getYScale(), true);

                if (distance1_2 == distance1_3){
                    realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                    realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                    realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                    realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;

                    xPos = (realXStart + realXEnd) / 2;
                    yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
                } else if(distance1_2 == distance_2_3){

                    realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                    realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                    realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                    realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;

                    xPos = (realXStart + realXEnd) / 2;
                    yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);
                } else if(distance1_3 == distance_2_3){

                    realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                    realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;
                    realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                    realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;

                    xPos = (realXStart + realXEnd) / 2;
                    yPos = (realYStart + realYEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

                }

                */



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

                double distance1_2 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getSecondPoint(),
                        gameState.getXScale(), gameState.getYScale(), true);

                double distance1_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true);

                double distance_2_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true);

                if (!(distance1_2 == distance_2_3 || distance_2_3 == distance1_3)) {

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

                double distance1_2 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getSecondPoint(),
                        gameState.getXScale(), gameState.getYScale(), true);

                double distance1_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getFirstPoint(), gameState.getTriangle().getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true);

                double distance_2_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getThirdPoint()
                        , gameState.getXScale(), gameState.getYScale(), true);

                if (!(distance1_2 == distance_2_3 || distance_2_3 == distance1_3 || distance1_2 == distance1_3)) {

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

                Coordinate point1 = gameState.getTriangle().getFirstPoint();
                Coordinate point2 = gameState.getTriangle().getSecondPoint();
                Coordinate point3 = gameState.getTriangle().getThirdPoint();
                int xScale = gameState.getXScale();
                int yScale = gameState.getYScale();
                double dist12 = gameState.getTriangle().calculateDistanceTwoCoordinates(point1,point3,xScale,yScale,true);
                double distance1 = gameState.getTriangle().calculateDistanceTwoCoordinates(point1,point2,xScale,yScale,true);
                double distance2 = gameState.getTriangle().calculateDistanceTwoCoordinates(point1,point3,xScale,yScale,true);
                double distance3 = gameState.getTriangle().calculateDistanceTwoCoordinates(point2,point3,xScale,yScale,true);


                if (!(dist12 %1 == 0)){
                    xEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                    yEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;

                    xPos = (xStart + xEnd) / 2;
                    yPos = (yStart + yEnd) / 2 - (int) ((paintWhiteText.descent() + paintWhiteText.ascent()) / 2);

                }

                String distance1String = String.valueOf(distance1).replace(".0","");
                String distance2String = String.valueOf(distance2).replace(".0","");
                String distance3String = String.valueOf(distance3).replace(".0","");
                double permeter =  distance1 + distance2 + distance3;

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

    private void onDrawCorrectEmptyShape(android.graphics.Canvas canvas, Rectangle rectangle, Circle circle, Triangle triangle) {
        //This is used when drawn the correct shape after 3 attempt in category 9. Comple figure from perimeter.
        paintSymmetryLine.setColor(getResources().getColor(R.color.LightGreen));
        paintSymmetryPoint.setColor(getResources().getColor(R.color.LightGreen));
        paintCompleteFigure.setColor(getResources().getColor(R.color.LightGreen));
        paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.LightGreen));
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && rectangle != null) {
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
        //Draws circle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && circle != null) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(circle.getCenter()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(circle.getCenter()).second;
            canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * circle.getRadius() / 10, paintSymmetryPoint);
            canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);
        }
        //Draws triangle

        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && triangle != null) {
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

    //Correct answer shapes are saved into these objects. So that users drawn shape does not affect them
    // TODO: 5.7.2020 Canvas class should not have variables like these. They belong to the gameState. Redesining categories 9-11 for drawing shapes could be a good idea.
    boolean firstTime = true;
    Rectangle rectangle = null;
    Circle circle = null;
    Triangle triangle = null;
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
        //Draws rectangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && gameState.getRectangle() != null) {

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
        //Draws circle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && gameState.getCircle() != null) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;
            int xDifference = Math.abs(gameState.getCircle().getCenter().getY() - gameState.getSelectedDot().getCoordinate().getY());
            int yDifference = Math.abs(gameState.getCircle().getCenter().getX() - gameState.getSelectedDot().getCoordinate().getX());
            gameState.getCircle().setRadius(Math.max(xDifference, yDifference));
            canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintSymmetryPoint);
            canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);
        }
        //Draws triangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER && gameState.getTriangle() != null) {
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
        //Draws rectangle
        if (gameState.getCategory() == Categories.FINDAREAFROMFIGURE && gameState.getRectangle() != null) {
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
        }  //Draws circle
        else if (gameState.getCategory() == Categories.FINDAREAFROMFIGURE && gameState.getCircle() != null) {
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;
            //sets the color of the outer circle
            canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintFillShape);
            canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintFillShape);

            canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintSymmetryPoint);
            canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintSymmetryPoint);

            int xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getDrawCircle()).first;
            int yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getDrawCircle()).second;


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

        }//Draws triangle
        else if (gameState.getCategory() == Categories.FINDAREAFROMFIGURE && gameState.getTriangle() != null) {

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

            int xPos = (point1x + point2x)/2;
            int yPos = (point1y + point2y)/2;

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
                double a = 0;

                if (dist1_2 == dist1_3) {

                    realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                    realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
                    realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                    realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;

                    a = Math.abs(gameState.getTriangle().getSecondPoint().getX() - gameState.getTriangle().getThirdPoint().getX());

                } else if (dist1_2 == dist2_3) {

                    realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                    realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).second;
                    realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                    realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;

                    a = Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX());

                } else if (dist1_3 == dist2_3) {

                    realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).first;
                    realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
                    realXEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                    realYEnd = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;

                    a = Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getSecondPoint().getX());
                }
                canvas.drawCircle((realXStart + realXEnd) / 2, (realYStart + realYEnd) / 2, paintCoordinateSelectedDot.getStrokeWidth(), paintCoordinateSelectedDot);

                canvas.drawText("" + a, (realXStart + realXEnd) / 2
                        , (realYStart + realYEnd) / 2 - (int) (paintWhiteText.descent() + paintWhiteText.ascent()) / 2, paintWhiteText);


            }

                if (gameState.isAnsweredCorrectly() || gameState.getAttempt() == 3) {


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


                            double b = Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX());
                            double c = Math.abs(gameState.getTriangle().getFirstPoint().getY() - gameState.getTriangle().getSecondPoint().getY());
                            xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).first;
                            yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getFirstPoint()).second;

                            canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10 - 15, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10 + 15
                                    , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                                    , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);

                            canvas.drawText("A = (" + b + "×" + c + ")/2"
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
                        canvas.drawText("= " + gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale()),
                                xPos, yPos, paintWhiteText);
                    } else {
                        String b = ("" + Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX())).replace(".0", "");
                        String c = ("" + Math.abs(firstPoint.getY() - secondPoint.getY())).replace(".0", "");

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
                        canvas.drawText("= " + gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale()),
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

                double distance_2_3 = gameState.getTriangle().calculateDistanceTwoCoordinates(
                        gameState.getTriangle().getSecondPoint(), gameState.getTriangle().getThirdPoint()
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
        }//Draws any for point shape
        else if (gameState.getCategory() == Categories.FINDAREAFROMFIGURE && gameState.getShapeFourCorners() != null) {
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
                canvas.drawText("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale()),
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
                canvas.drawText(("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale())),
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
                canvas.drawText("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale()),
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
        //Draws rectangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getRectangle() != null) {
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

            //TODO maybe switch the color of the text
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
        //Draws circle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getCircle() != null) {
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
        //Draws triangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getTriangle() != null) {
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


            if (gameState.isAnsweredCorrectly()) {
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
                xPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getThirdPoint()).first;
                yPos = coordinateSystem.getCanvasRealCoordinate(gameState.getTriangle().getSecondPoint()).second;
                canvas.drawPath(RoundedRect(xPos - canvas.getWidth() / 10, yPos - (paintWhiteText.descent() - paintWhiteText.ascent()) * 2.5F, xPos + canvas.getWidth() / 10
                        , yPos + (paintWhiteText.descent() - paintWhiteText.ascent()), canvas.getWidth() / 40
                        , canvas.getWidth() / 40, false), paintCoordinateSelectedDot);
                canvas.drawText("A = (" + Math.abs(gameState.getTriangle().getFirstPoint().getX() - gameState.getTriangle().getThirdPoint().getX()) + "×"
                                + Math.abs(gameState.getTriangle().getFirstPoint().getY() - gameState.getTriangle().getSecondPoint().getY()) + ")/2"
                        , xPos, yPos + (paintWhiteText.descent() - paintWhiteText.ascent()) * -1f, paintWhiteText);
                canvas.drawText("  = " + gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale()),
                        xPos, yPos, paintWhiteText);
            }
        }
        //Draws any four point shape
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getShapeFourCorners() != null) {
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
                canvas.drawText("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale()),
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
                canvas.drawText(("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale())),
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
                canvas.drawText("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale()),
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
        if (!gameState.isAnsweredCorrectly() && gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getAttempt() == 3) {
            onDrawCorrectShapes(canvas, rectangle, circle, triangle, shapeFourCorners);
        }
    }

    private void onDrawCorrectShapes(android.graphics.Canvas canvas, Rectangle rectangle, Circle circle, Triangle triangle, ShapeFourCorners shapeFourCorners) {
        //Draws the correct answer shape. For example in area levels after 3 attempts, correct answer shape is drawn.
        paintFillShape.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
        paintSymmetryLine.setColor(getResources().getColor(R.color.shapeColorCorrectAlpha50));
        paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.LightGreen));
        //Draws rectangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getRectangle() != null) {
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
        //Draws circle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getCircle() != null) {
            gameState.setCircle(circle);
            int realXStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).first;
            int realYStart = coordinateSystem.getCanvasRealCoordinate(gameState.getCircle().getCenter()).second;
            canvas.drawCircle(realXStart, realYStart, (canvas.getWidth() - 50) * gameState.getCircle().getRadius() / 10, paintFillShape);
            canvas.drawCircle(realXStart, realYStart, paintSymmetryPoint.getStrokeWidth(), paintFillShape);
        }
        //Draws triangle
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getTriangle() != null) {
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
                canvas.drawText("  = " + gameState.getTriangle().calculateArea(gameState.getXScale(), gameState.getYScale()),
                        xPos, yPos, paintWhiteText);
            }
        }
        //Draws any for point shape
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA && gameState.getShapeFourCorners() != null) {
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
                canvas.drawText("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale()),
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
                canvas.drawText(("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale())),
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
                canvas.drawText("  = " + gameState.getShapeFourCorners().calculateArea(gameState.getXScale(), gameState.getYScale()),
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
        } if ((gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY)
                && !gameState.isAnsweredCorrectly() ) {
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.Purple));
        } else {
            paintCoordinateSelectedDot.setColor(getResources().getColor(R.color.Red));
            paintLineFigureAnswer.setColor(getResources().getColor(R.color.Red));
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
