package com.example.geometryapp.Levels.Perimeter;

import android.content.Context;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.GameStateBuilder;
import com.example.geometryapp.Interface.Level;
import com.example.geometryapp.R;

import java.util.Random;

import static com.example.geometryapp.Enum.Categories.FINDTHEPERIMETEROFAFIGURE;
import static com.example.geometryapp.Levels.Perimeter.CompleteFigureFromPerimeter.round;

public class FindThePerimeterOfAFigure implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private Categories category = FINDTHEPERIMETEROFAFIGURE;
    private Rectangle rectangle = null;
    private Circle circle = null;
    private Triangle triangle = null;

    //Creates the correct gameState
    public FindThePerimeterOfAFigure(int levelNum) {
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
        int sideLength = randomPoint(3, 6);
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + +sideLength, bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + sideLength, bottomLeft.getY())
                , bottomLeft);
    }

    public void level2() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = xScale;
        int sideLength = randomPoint(3, 6);
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + +sideLength, bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + sideLength, bottomLeft.getY())
                , bottomLeft);
    }

    public void level3() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        boolean rect = true;
        int width = 0;
        int height = 0;
        while(rect) {
            width = randomPoint(3, 5);
            height = randomPoint(3, 5);

            if (width != height){
                rect = false;
            }
        }
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 4), randomPoint(1, 4));
        while (!isCoordinatesOnGrid(width, height, bottomLeft)) {
            bottomLeft = new Coordinate(randomPoint(1, 4), randomPoint(1, 4));
        }
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + height)
                , new Coordinate(bottomLeft.getX() + width, bottomLeft.getY() + height)
                , new Coordinate(bottomLeft.getX() + width, bottomLeft.getY())
                , bottomLeft);
    }

    public void level4() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = randomPoint(2, 10);
        int width = 0;
        int height = 0;
        boolean rect = true;
        while (rect){
            width = randomPoint(3, 5);
            height = randomPoint(3, 5);
            if (width != height){
                rect = false;
            }
        }

        Coordinate bottomLeft = new Coordinate(randomPoint(1, 4), randomPoint(1, 4));
        while (!isCoordinatesOnGrid(width, height, bottomLeft)) {
            bottomLeft = new Coordinate(randomPoint(1, 4), randomPoint(1, 4));
        }
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + height)
                , new Coordinate(bottomLeft.getX() + width, bottomLeft.getY() + height)
                , new Coordinate(bottomLeft.getX() + width, bottomLeft.getY())
                , bottomLeft);
    }

    public void level5() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        circle = new Circle(new Coordinate(randomPoint(4, 6), randomPoint(4, 6)), randomPoint(1, 4));
    }

    public void level6() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = xScale;
        circle = new Circle(new Coordinate(randomPoint(4, 6), randomPoint(4, 6)), randomPoint(1, 4));
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
        triangle = new Triangle(firstPoint
                , new Coordinate(firstPoint.getX(), firstPoint.getY() + height)
                , new Coordinate(firstPoint.getX() + width, firstPoint.getY()));
    }

    public void level8() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        Coordinate firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Coordinate secondPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Coordinate thirdPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));

        int a = randomPoint(0,1);
        if (a == 0){
            secondPoint.setX(firstPoint.getX() + randomPoint(-4,4   ));
            secondPoint.setY(firstPoint.getY());
            thirdPoint.setX((firstPoint.getX()+ secondPoint.getX())/2);
            thirdPoint.setY(firstPoint.getY() + randomPoint(-4,4));
        }else {
            secondPoint.setX(firstPoint.getX());
            secondPoint.setY(firstPoint.getY() + randomPoint(-4,-4));
            thirdPoint.setY((firstPoint.getY() + secondPoint.getY())/2);
            thirdPoint.setX(firstPoint.getX() + randomPoint(-4,4));
        }

        int i = 0;
        while (isCoordinateBetweenCoordinates(firstPoint, secondPoint, thirdPoint)
                || firstPoint.equals(secondPoint) || firstPoint.equals(thirdPoint) || secondPoint.equals(thirdPoint)
                || !isDistanceEqual(firstPoint, secondPoint, thirdPoint)
                || !isDistanceMoreThanInput(firstPoint, secondPoint, thirdPoint,3)
                || !isOneLineParallel(firstPoint,secondPoint,thirdPoint)
                || isOneCornerRightAngle(firstPoint,secondPoint,thirdPoint)

        ) {

            firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
            secondPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
            thirdPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
            i++;
        }

        System.out.println(i);


        triangle = new Triangle(firstPoint, secondPoint, thirdPoint);
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
        triangle = new Triangle(firstPoint, secondPoint, thirdPoint);
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

    public double calculateDistanceTwoCoordinates(Coordinate start, Coordinate end) {
        return Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) * xScale
                + (start.getY() - end.getY()) * (start.getY() - end.getY()) * yScale);
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

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setXScale(xScale)
                .setYScale(yScale)
                .setCategory(category)
                .setQuestion(context.getResources().getString(R.string.FindThePerimeterOfAFigure))
                .setSelectedDot(new SelectedDot(new Coordinate(0, 0)))
                .setRectangle(rectangle)
                .setCircle(circle)
                .setTriangle(triangle);
        return gameStateBuilder.build();
    }
}
