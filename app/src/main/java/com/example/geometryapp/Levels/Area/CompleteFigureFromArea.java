package com.example.geometryapp.Levels.Area;

import android.content.Context;

import com.example.geometryapp.Coordinate;
import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.DrawObjects.ShapeFourCorners;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.Enum.ShapeType;
import com.example.geometryapp.GameState;
import com.example.geometryapp.GameStateBuilder;
import com.example.geometryapp.Interface.Level;
import com.example.geometryapp.R;
import com.example.geometryapp.Singleton;

import java.util.Random;

import static com.example.geometryapp.Enum.Categories.COMPLETEFIGUREFROMAREA;

/**
 * This class creates the levels for find area from figure category
 */
public class CompleteFigureFromArea implements Level {

    private static Random random = new Random();
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private Categories category = COMPLETEFIGUREFROMAREA;
    private Rectangle rectangle = null;
    private Circle circle = null;
    private Triangle triangle = null;
    private ShapeFourCorners shapeFourCorners = null;
    private String targetAnswer;

    //Creates the correct gameState
    public CompleteFigureFromArea(int levelNum) {
        if (levelNum == 0) {
            levelNum = randomPoint(1, 11);
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
        } else if (levelNum == 10) {
            level10();
        } else if (levelNum == 11) {
            level11();
        } else {
            throw new IllegalArgumentException("Level does not exist! Level index was " + levelNum);
        }
    }

    /**
     * Creates level 1
     */

    public void level1() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        int sideLength = randomPoint(3, 7);
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + +sideLength, bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + sideLength, bottomLeft.getY())
                , bottomLeft);
        targetAnswer = "" + rectangle.calculateArea(xScale, yScale);
    }

    /**
     * Creates level 2
     */

    public void level2() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = xScale;
        int sideLength = randomPoint(3, 7);
        Coordinate bottomLeft = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        rectangle = new Rectangle(new Coordinate(bottomLeft.getX(), bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + +sideLength, bottomLeft.getY() + sideLength)
                , new Coordinate(bottomLeft.getX() + sideLength, bottomLeft.getY())
                , bottomLeft);
        targetAnswer = "" + rectangle.calculateArea(xScale, yScale);
    }

    /**
     * Creates level 3
     */
    public void level3() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
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
        targetAnswer = "" + rectangle.calculateArea(xScale, yScale);
    }

    /**
     * Creates level 4
     */
    public void level4() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = randomPoint(2, 10);
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
        targetAnswer = "" + rectangle.calculateArea(xScale, yScale);
    }

    /**
     * Creates level 5
     */
    public void level5() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        circle = new Circle(new Coordinate(randomPoint(4, 6), randomPoint(4, 6)), randomPoint(1, 4));
        targetAnswer = "" + circle.getRadius()*circle.getRadius()* xScale * xScale + "\uD835\uDF0B";
    }

    /**
     * Creates level 6
     */
    public void level6() {
        origin = new Coordinate(0, 0);
        xScale = randomPoint(2, 10);
        yScale = xScale;
        circle = new Circle(new Coordinate(randomPoint(4, 6), randomPoint(4, 6)), randomPoint(1, 4));
        targetAnswer = "" + circle.getRadius()*circle.getRadius()* xScale * xScale + "\uD835\uDF0B";
    }

    /**
     * Creates level 7
     */
    public void level7() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        int width = randomPoint(3, 7) * randomChangeNegativeOrPositive();
        int height = randomPoint(3, 7) * randomChangeNegativeOrPositive();
        Coordinate firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        while (width == 0 || height == 0 || !isCoordinatesOnGrid(width, 0, firstPoint) || !isCoordinatesOnGrid(0, 0, firstPoint)
                || !isCoordinatesOnGrid(0, height, firstPoint)
                || isCoordinateBetweenCoordinates(firstPoint, new Coordinate(firstPoint.getX()
                , firstPoint.getY() + height), new Coordinate(firstPoint.getX() + width, firstPoint.getY()))) {
            width = randomPoint(3, 7) * randomChangeNegativeOrPositive();
            height = randomPoint(3, 7) * randomChangeNegativeOrPositive();
            firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        }
        triangle = new Triangle(firstPoint
                , new Coordinate(firstPoint.getX(), firstPoint.getY() + height)
                , new Coordinate(firstPoint.getX() + width, firstPoint.getY()));
        targetAnswer = "" + triangle.calculateArea(xScale, yScale, 7, COMPLETEFIGUREFROMAREA);
    }

    /**
     * Creates level 8
     */
    public void level8() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        Coordinate firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Coordinate secondPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
        Coordinate thirdPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));

        int randomNum = randomPoint(1,2);
        Singleton singleton = Singleton.getInstance();
        singleton.setRandomNum(randomNum);
        //Creates special type of triangles
        if (randomNum == 1) {
            while (isCoordinateBetweenCoordinates(firstPoint, secondPoint, thirdPoint)
                    || firstPoint.equals(secondPoint) || firstPoint.equals(thirdPoint) || secondPoint.equals(thirdPoint)
                    || !isDistanceEqual(firstPoint, secondPoint, thirdPoint)
                    || !isDistanceMoreThanInput(firstPoint, secondPoint, thirdPoint, 3)
                    || !isOneLineParallel(firstPoint, secondPoint, thirdPoint)
                    || isOneCornerRightAngle(firstPoint, secondPoint, thirdPoint)) {
                firstPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
                secondPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
                thirdPoint = new Coordinate(randomPoint(1, 9), randomPoint(1, 9));
            }
        } //Creates isolate triangles
        else {
            int a;
            while (!checkGrid(firstPoint, secondPoint, thirdPoint)) {
                firstPoint = new Coordinate(randomPoint(2, 6), randomPoint(1, 5));
                secondPoint = new Coordinate(firstPoint.getX() + randomPoint(3, 6), firstPoint.getY());
                a = (secondPoint.getX() + firstPoint.getX()) / 2;
                thirdPoint = new Coordinate(a, firstPoint.getX() + randomPoint(3, 7));
            }
        }
        triangle = new Triangle(firstPoint, secondPoint, thirdPoint);
        targetAnswer = "" + triangle.calculateArea(xScale, yScale,8, COMPLETEFIGUREFROMAREA);
    }

    /**
     * Creates level 9
     */
    public void level9() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        Coordinate firstPoint = new Coordinate(randomPoint(4, 6), randomPoint(6, 9));
        Coordinate secondPoint = new Coordinate(randomPoint(6, 9), randomPoint(3, 6));
        Coordinate thirdPoint = new Coordinate(randomPoint(4, 6), randomPoint(1, 3));
        Coordinate fourthPoint = new Coordinate(randomPoint(1, 3), randomPoint(3, 6));
        while (!isShapeKite(firstPoint, secondPoint, thirdPoint, fourthPoint)) {
            firstPoint = new Coordinate(randomPoint(4, 6), randomPoint(6, 9));
            secondPoint = new Coordinate(randomPoint(6, 9), randomPoint(3, 6));
            thirdPoint = new Coordinate(randomPoint(4, 6), randomPoint(1, 3));
            fourthPoint = new Coordinate(randomPoint(1, 3), randomPoint(3, 6));
        }
        shapeFourCorners = new ShapeFourCorners(firstPoint, secondPoint, thirdPoint, fourthPoint, ShapeType.KITE);
        targetAnswer = "" + shapeFourCorners.calculateArea(xScale, yScale,9, COMPLETEFIGUREFROMAREA);
    }

    /**
     * Creates level 10
     */
    public void level10() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        Coordinate firstPoint = new Coordinate(randomPoint(1, 3), randomPoint(5, 9));
        Coordinate secondPoint = new Coordinate(randomPoint(5, 9), randomPoint(5, 9));
        Coordinate thirdPoint = new Coordinate(randomPoint(5, 9), randomPoint(1, 3));
        Coordinate fourthPoint = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        while (!isShapeParallelogram(firstPoint, secondPoint, thirdPoint, fourthPoint)) {
            firstPoint = new Coordinate(randomPoint(1, 3), randomPoint(5, 9));
            secondPoint = new Coordinate(randomPoint(5, 9), randomPoint(5, 9));
            thirdPoint = new Coordinate(randomPoint(5, 9), randomPoint(1, 3));
            fourthPoint = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        }
        shapeFourCorners = new ShapeFourCorners(firstPoint, secondPoint, thirdPoint, fourthPoint, ShapeType.PARALLELOGRAM);
        targetAnswer = "" + shapeFourCorners.calculateArea(xScale, yScale, 10,COMPLETEFIGUREFROMAREA);
    }

    /**
     * Creates level 11
     */
    public void level11() {
        origin = new Coordinate(0, 0);
        xScale = 1;
        yScale = 1;
        Coordinate firstPoint = new Coordinate(randomPoint(1, 3), randomPoint(5, 9));
        Coordinate secondPoint = new Coordinate(randomPoint(5, 9), randomPoint(5, 9));
        Coordinate thirdPoint = new Coordinate(randomPoint(5, 9), randomPoint(1, 3));
        Coordinate fourthPoint = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        while (!isShapeTrapezoid(firstPoint, secondPoint, thirdPoint, fourthPoint)) {
            firstPoint = new Coordinate(randomPoint(1, 3), randomPoint(5, 9));
            secondPoint = new Coordinate(randomPoint(5, 9), randomPoint(5, 9));
            thirdPoint = new Coordinate(randomPoint(5, 9), randomPoint(1, 3));
            fourthPoint = new Coordinate(randomPoint(1, 3), randomPoint(1, 3));
        }
        shapeFourCorners = new ShapeFourCorners(firstPoint, secondPoint, thirdPoint, fourthPoint, ShapeType.TRAPEZOID);
        targetAnswer = "" + shapeFourCorners.calculateArea(xScale, yScale, 11,COMPLETEFIGUREFROMAREA);
    }

    //Checks if the coordinate is on the grid
    private boolean isCoordinatesOnGrid(int width, int height, Coordinate bottomLeft) {
        if (bottomLeft.getY() + height >= 10) {
            return false;
        }
        if (bottomLeft.getX() + width >= 10) {
            return false;
        }
        if (bottomLeft.getY() + height <= 0) {
            return false;
        }
        if (bottomLeft.getX() + width <= 0) {
            return false;
        }
        return true;
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
    private boolean isShapeKite(Coordinate firstPoint, Coordinate secondPoint, Coordinate thirdPoint, Coordinate fourthPoint) {
        return firstPoint.getX() == thirdPoint.getX() && secondPoint.getY() == fourthPoint.getY()
                && firstPoint.getX() < secondPoint.getX() && firstPoint.getX() > fourthPoint.getX()
                && thirdPoint.getX() < secondPoint.getX() && thirdPoint.getX() > fourthPoint.getX()
                && secondPoint.getY() < firstPoint.getY() && secondPoint.getY() > thirdPoint.getY()
                && fourthPoint.getY() < firstPoint.getY() && fourthPoint.getY() > thirdPoint.getY()
                && (double) firstPoint.getX() == Math.abs((double) (secondPoint.getX() + fourthPoint.getX()) / 2);
    }

    private boolean isShapeParallelogram(Coordinate firstPoint, Coordinate secondPoint, Coordinate thirdPoint, Coordinate fourthPoint) {
        if (firstPoint.getY() == secondPoint.getY() && thirdPoint.getY() == fourthPoint.getY() && firstPoint.getY() != thirdPoint.getY()
                && firstPoint.getX() != fourthPoint.getX()
                && firstPoint.getX() - secondPoint.getX() == fourthPoint.getX() - thirdPoint.getX()
                && firstPoint.getX() != secondPoint.getX() && thirdPoint.getX() != fourthPoint.getX()) {
            return true;
        } else if (firstPoint.getX() == secondPoint.getX() && thirdPoint.getX() == fourthPoint.getX() && firstPoint.getX() != thirdPoint.getX()
                && firstPoint.getY() != fourthPoint.getY()
                && firstPoint.getY() - secondPoint.getY() == fourthPoint.getY() - thirdPoint.getY()
                && firstPoint.getY() != secondPoint.getY() && thirdPoint.getY() != fourthPoint.getY()) {
            return true;
        }
        return false;
    }

    private boolean isShapeTrapezoid(Coordinate firstPoint, Coordinate secondPoint, Coordinate thirdPoint, Coordinate fourthPoint) {
        if (firstPoint.getY() == secondPoint.getY() && thirdPoint.getY() == fourthPoint.getY() && firstPoint.getY() != thirdPoint.getY()
                && firstPoint.getX() != fourthPoint.getX()
                && firstPoint.getX() < secondPoint.getX() && fourthPoint.getX() < thirdPoint.getX()
                && firstPoint.getX() != secondPoint.getX() && thirdPoint.getX() != fourthPoint.getX()
                && calculateDistanceTwoCoordinates(firstPoint,secondPoint) != calculateDistanceTwoCoordinates(thirdPoint,fourthPoint)) {
            return true;
        } else if (firstPoint.getX() == secondPoint.getX() && thirdPoint.getX() == fourthPoint.getX() && firstPoint.getX() != thirdPoint.getX()
                && firstPoint.getY() != fourthPoint.getY()
                && firstPoint.getY() < secondPoint.getY() && fourthPoint.getY() < thirdPoint.getY()
                && firstPoint.getY() != secondPoint.getY() && thirdPoint.getY() != fourthPoint.getY()
                && calculateDistanceTwoCoordinates(firstPoint,secondPoint) != calculateDistanceTwoCoordinates(thirdPoint,fourthPoint)) {
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

    @Override
    public GameState getDefaultLevelState(Context context) {
        GameStateBuilder gameStateBuilder = new GameStateBuilder();
        gameStateBuilder.setOrigin(origin)
                .setXScale(xScale)
                .setYScale(yScale)
                .setCategory(category)
                .setQuestion(context.getResources().getString(R.string.FindAreaFromFigure))
                .setSelectedDot(new SelectedDot(new Coordinate(5, 5)))
                .setRectangle(rectangle)
                .setCircle(circle)
                .setTriangle(triangle)
                .setShapeFourCorners(shapeFourCorners)
                .setTargetAnswer(targetAnswer);
        return gameStateBuilder.build();
    }

    /**
     * Made for isolate trianglees to check if they are acceptable
     * @param first coordinate for the first point
     * @param second coordinate for the second point
     * @param third coordinate for the third point
     * @return true/false depending on if the triangle is okay or not
     */
    private boolean checkGrid(Coordinate first, Coordinate second, Coordinate third) {
        int a = second.getX() - first.getX();
        if (!(a % 2 == 0)) {
            return false;
        }
        if(first.getY() != second.getY()){
            return false;
        }
        if (first.getX() > 10 || first.getX() < 0) {
            return false;
        }
        if (second.getX() > 10 || second.getX() < 0) {
            return false;
        }
        if (third.getY() > 10 || third.getY() < 0) {
            return false;
        }
        int b = third.getY() - first.getY();
        int c = first.getY() - third.getY();
        if (!(b >= 3 || c >= 3)) {
            return false;
        }
        int d =  ( second.getX() + first.getX()) /2;
        return third.getX() == d;
    }

}
