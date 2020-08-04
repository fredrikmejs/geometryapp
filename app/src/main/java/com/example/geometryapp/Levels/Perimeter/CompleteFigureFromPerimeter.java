package com.example.geometryapp.Levels.Perimeter;

import android.content.Context;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.GameStateBuilder;
import com.example.geometryapp.Interface.Level;
import com.example.geometryapp.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import static com.example.geometryapp.Enum.Categories.COMPLETEFIGUREFROMPERIMETER;

public class CompleteFigureFromPerimeter implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private String targetAnswer;
    private SelectedDot selectedDot;
    private Categories category = COMPLETEFIGUREFROMPERIMETER;
    private Rectangle rectangle = null;
    private Circle circle = null;
    private Triangle triangle = null;

    //Creates the correct gameState
    public CompleteFigureFromPerimeter(int levelNum) {
        if (levelNum == 0) {
            levelNum = randomPoint(1, 9);
        }
        if (levelNum == 1) {
            level1();
        } else if (levelNum == 2) {
            level2();
        } else if (levelNum == 3) {
            level3();
        } else if (levelNum == 4) {
            level4();
        } else if (levelNum == 5) {
            level5();
        } else if (levelNum == 6) {
            level6();
        } else if (levelNum == 7) {
            level7();
        } else if (levelNum == 8) {
            level8();
        } else if (levelNum == 9) {
            level9();
        } else {
            throw new IllegalArgumentException("Level does not exist! Level index was " + levelNum);
        }
    }


    public void level1() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        selectedDot = new SelectedDot(new Coordinate(5, 5));
        int sideLength = randomPoint(3, 7);
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + +sideLength, bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + sideLength, bottomLeft.getY())
                , bottomLeft);
        targetAnswer = "" + round(rectangle.calculatePerimeter(xScale, yScale), 2);
    }

    public void level2() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = xScale;
        selectedDot = new SelectedDot(new Coordinate(5, 5));
        int sideLength = randomPoint(3, 7);
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + +sideLength, bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + sideLength, bottomLeft.getY())
                , bottomLeft);
        targetAnswer = "" + round(rectangle.calculatePerimeter(xScale, yScale), 2);
    }

    public void level3() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        selectedDot = new SelectedDot(new Coordinate(5, 5));
        int width = randomPoint(3, 7);
        int height = randomPoint(3, 7);
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 5), randomPoint(1, 5));
        while (!isCoordinatesOnGrid(width, height, bottomLeft)) {
            bottomLeft = new Coordinate(randomPoint(1, 5), randomPoint(1, 5));
        }
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + height)
                , new Coordinate(bottomLeft.getX() + width, bottomLeft.getY() + height)
                , new Coordinate(bottomLeft.getX() + width, bottomLeft.getY())
                , bottomLeft);
        targetAnswer = "" + round(rectangle.calculatePerimeter(xScale, yScale), 2);
    }

    public void level4() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = randomPoint(2, 10);
        selectedDot = new SelectedDot(new Coordinate(5, 5));
        int width = randomPoint(3, 7);
        int height = randomPoint(3, 7);
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 5), randomPoint(1, 5));
        while (!isCoordinatesOnGrid(width, height, bottomLeft)) {
            bottomLeft = new Coordinate(randomPoint(1, 5), randomPoint(1, 5));
        }
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + height)
                , new Coordinate(bottomLeft.getX() + width, bottomLeft.getY() + height)
                , new Coordinate(bottomLeft.getX() + width, bottomLeft.getY())
                , bottomLeft);
        targetAnswer = "" + round(rectangle.calculatePerimeter(xScale, yScale), 2);
    }

    public void level5() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        selectedDot = new SelectedDot(new Coordinate(5, 5));
        circle = new Circle(new Coordinate(randomPoint(4, 6), randomPoint(4, 6)), randomPoint(1, 4));
        targetAnswer = "" + yScale * xScale * 2 * circle.getRadius() + "\uD835\uDF0B";
    }

    public void level6() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = xScale;
        selectedDot = new SelectedDot(new Coordinate(5, 5));
        circle = new Circle(new Coordinate(randomPoint(4, 6), randomPoint(4, 6)), randomPoint(1, 4));
        targetAnswer = "" + yScale * 2 * circle.getRadius() + "\uD835\uDF0B";
    }

    public void level7() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        int width = randomPoint(3, 7) * randomChangeNegativeOrPositive();
        int height = randomPoint(3, 7) * randomChangeNegativeOrPositive();
        Coordinate firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        while (width == 0 || height == 0 || !isCoordinatesOnGrid(width, 0, firstPoint) || !isCoordinatesOnGrid(0, 0, firstPoint)
                || !isCoordinatesOnGrid(0, height, firstPoint)
                || !isDistanceMoreThanInput(firstPoint
                , new Coordinate(firstPoint.getX(), firstPoint.getY() + height)
                , new Coordinate(firstPoint.getX() + width, firstPoint.getY()),5)
                || isCoordinateBetweenCoordinates(firstPoint, new Coordinate(firstPoint.getX(),
                firstPoint.getY() + height), new Coordinate(firstPoint.getX() + width, firstPoint.getY()))) {
            width = randomPoint(3, 7) * randomChangeNegativeOrPositive();
            height = randomPoint(3, 7) * randomChangeNegativeOrPositive();
            firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        }
        selectedDot = new SelectedDot(new Coordinate(firstPoint.getX(), 5));
        triangle = new Triangle(firstPoint
                , new Coordinate(firstPoint.getX(), firstPoint.getY() + height)
                , new Coordinate(firstPoint.getX() + width, firstPoint.getY()));
        targetAnswer = "" + round(triangle.calculatePerimeter(xScale, yScale), 1);
    }

    public void level8() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        Coordinate firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Coordinate secondPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Coordinate thirdPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        while (isCoordinateBetweenCoordinates(firstPoint, secondPoint, thirdPoint)
                || firstPoint.equals(secondPoint) || firstPoint.equals(thirdPoint) || secondPoint.equals(thirdPoint)
                || !isDistanceEqual(firstPoint, secondPoint, thirdPoint)
                || !isDistanceMoreThanInput(firstPoint, secondPoint, thirdPoint,4)
                || !isOneLineParallel(firstPoint,secondPoint,thirdPoint)
                || isOneCornerRightAngle(firstPoint,secondPoint,thirdPoint)) {
            firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
            secondPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
            thirdPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        }
        selectedDot = new SelectedDot(new Coordinate(secondPoint.getX(), 5));
        triangle = new Triangle(firstPoint, secondPoint, thirdPoint);
        targetAnswer = "" + round(triangle.calculatePerimeter(xScale, yScale), 1);
    }

    public void level9() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        Coordinate firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Coordinate secondPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Coordinate thirdPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        while (isCoordinateBetweenCoordinates(firstPoint, secondPoint, thirdPoint)
                || firstPoint.equals(secondPoint) || firstPoint.equals(thirdPoint) || secondPoint.equals(thirdPoint)
                || !isDistanceMoreThanInput(firstPoint, secondPoint, thirdPoint,5)
                || !isOneLineParallel(firstPoint,secondPoint,thirdPoint)
                || isOneCornerRightAngle(firstPoint,secondPoint,thirdPoint)) {
            firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
            secondPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
            thirdPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        }
        selectedDot = new SelectedDot(new Coordinate(secondPoint.getX(), 5));
        triangle = new Triangle(firstPoint, secondPoint, thirdPoint);
        targetAnswer = "" + round(triangle.calculatePerimeter(xScale, yScale), 1);
    }

    private boolean isOneCornerRightAngle(Coordinate first, Coordinate second, Coordinate third){
        if((first.getX() == second.getX() || first.getY() == second.getY()) && (first.getX() == third.getX() || first.getY() == third.getY())){
            return true;
        }
        if((third.getX() == second.getX() || third.getY() == second.getY()) && (third.getX() == second.getX() || third.getY() == second.getY())){
            return true;
        }
        if((first.getX() == third.getX() || first.getY() == third.getY()) && (first.getX() == third.getX() || first.getY() == third.getY())){
            return true;
        }
        return false;
    }

    private boolean isOneLineParallel(Coordinate first, Coordinate second, Coordinate third){
        if(first.getX() == second.getX() || first.getY() == second.getY()){
            return true;
        }
        if(second.getX() == third.getX() || second.getY() == third.getY()){
            return true;
        }
        if(first.getX() == third.getX() || first.getY() == third.getY()){
            return true;
        }
        return false;
    }

    public double calculateDistanceTwoCoordinates(Coordinate start, Coordinate end) {
        return Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) * xScale
                + (start.getY() - end.getY()) * (start.getY() - end.getY()) * yScale);
    }

    private boolean isDistanceMoreThanInput(Coordinate firstCoordinate, Coordinate secondCoordinate, Coordinate thirdCoordinate, int distance) {
        if (calculateDistanceTwoCoordinates(firstCoordinate, secondCoordinate) < distance) {
            return false;
        }
        if (calculateDistanceTwoCoordinates(firstCoordinate, thirdCoordinate) < distance) {
            return false;
        }
        if (calculateDistanceTwoCoordinates(secondCoordinate, thirdCoordinate) < distance) {
            return false;
        }
        return true;
    }

    private boolean isCoordinatesOnGrid(int width, int height, Coordinate bottomLeft) {
        if (bottomLeft.getY() + height > 10) {
            return false;
        }
        if (bottomLeft.getX() + width > 10) {
            return false;
        }
        if (bottomLeft.getY() + height < 0) {
            return false;
        }
        if (bottomLeft.getX() + width < 0) {
            return false;
        }
        return true;
    }

    private boolean isPointsMoreThanOneSquaresAway(Coordinate firstPoint, Coordinate secondPoint, Coordinate thirdPoint) {
        if ((Math.abs(firstPoint.getX() - secondPoint.getX()) > 1 || Math.abs(firstPoint.getY() - secondPoint.getY()) > 1) && (Math.abs(secondPoint.getX() - thirdPoint.getX()) > 1 ||
                Math.abs(secondPoint.getY() - thirdPoint.getY()) > 1) && (Math.abs(thirdPoint.getX() - firstPoint.getX()) > 1 || Math.abs(thirdPoint.getY() - firstPoint.getY()) > 1)) {
            return true;
        }
        return false;
    }

    private boolean isDistanceEqual(Coordinate startCoordinate, Coordinate firstCoordinate, Coordinate secondCoordinate) {
        if (Math.sqrt((startCoordinate.getX() - firstCoordinate.getX()) * (startCoordinate.getX() - firstCoordinate.getX())
                + (startCoordinate.getY() - firstCoordinate.getY()) * (startCoordinate.getY() - firstCoordinate.getY()))
                == Math.sqrt((startCoordinate.getX() - secondCoordinate.getX()) * (startCoordinate.getX() - secondCoordinate.getX())
                + (startCoordinate.getY() - secondCoordinate.getY()) * (startCoordinate.getY() - secondCoordinate.getY()))) {
            return true;
        }
        return false;
    }

    private boolean isCoordinateBetweenCoordinates(Coordinate startCoordinate, Coordinate endCoordinate, Coordinate betweenCoordinate) {
        double slopeStartEnd = 0;
        double slopeEndBetween = 0;
        try {
            slopeStartEnd = (startCoordinate.getX() - endCoordinate.getX()) / (startCoordinate.getY() - endCoordinate.getY());
        } catch (ArithmeticException ignored) {
        }
        try {
            slopeEndBetween = (betweenCoordinate.getX() - endCoordinate.getX()) / (betweenCoordinate.getY() - endCoordinate.getY());
        } catch (ArithmeticException ignored) {
        }
        if (slopeStartEnd == slopeEndBetween) {
            return true;
        }
        if (slopeStartEnd == -1 * slopeEndBetween) {
            return true;
        }
        return false;
    }

    private int randomPoint(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private int randomChangeNegativeOrPositive() {
        if (random.nextInt(1 - 0 + 1) == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setXScale(xScale)
                .setYScale(yScale)
                .setCategory(category)
                .setQuestion(context.getResources().getString(R.string.CompleteFigureFromPerimeter))
                .setSelectedDot(new SelectedDot(new Coordinate(0, 0)))
                .setRectangle(rectangle)
                .setCircle(circle)
                .setTriangle(triangle)
                .setTargetAnswer(targetAnswer)
                .setSelectedDot(selectedDot);
        return gameStateBuilder.build();
    }
}
