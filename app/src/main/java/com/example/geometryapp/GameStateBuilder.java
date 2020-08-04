package com.example.geometryapp;

import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.Triangle;
import com.example.geometryapp.DrawObjects.Line;
import com.example.geometryapp.DrawObjects.LineFigure;
import com.example.geometryapp.DrawObjects.SelectedDot;
import com.example.geometryapp.DrawObjects.ShapeFourCorners;
import com.example.geometryapp.DrawObjects.SymmetryLine;
import com.example.geometryapp.DrawObjects.TargetDot;
import com.example.geometryapp.Enum.Categories;

import java.util.ArrayList;

public class GameStateBuilder {

    //Builder design pattern. Makes creating gameStates easier.

    private Coordinate origin;
    private int xScale;
    private int yScale;
    private TargetDot targetDot;
    private SelectedDot selectedDot;
    private ShapeFourCorners shapeFourCorners;
    private Categories category;
    private String question;
    private Coordinate targetPoint;
    private SymmetryLine symmetryLine;
    private Coordinate symmetryPoint;
    private LineFigure symmetryFigure;
    private LineFigure drawnSymmetryFigure;
    private Rectangle rectangle;
    private Circle circle;
    private Triangle triangle;
    private String targetAnswer;

    public GameStateBuilder setSymmetryLine(SymmetryLine symmetryLine) {
        this.symmetryLine = symmetryLine;
        return this;
    }

    public GameStateBuilder setOrigin(Coordinate origin) {
        this.origin = origin;
        return this;
    }

    public GameStateBuilder setXScale(int xScale) {
        this.xScale = xScale;
        return this;
    }

    public GameStateBuilder setYScale(int yScale) {
        this.yScale = yScale;
        return this;
    }

    public GameStateBuilder setTargetDot(TargetDot targetDot) {
        this.targetDot = targetDot;
        return this;
    }

    public GameStateBuilder setSelectedDot(SelectedDot selectedDot) {
        this.selectedDot = selectedDot;
        return this;
    }

    public GameStateBuilder setShapeFourCorners(ShapeFourCorners shapeFourCorners) {
        this.shapeFourCorners = shapeFourCorners;
        return this;
    }

    public GameStateBuilder setCategory(Categories category) {
        this.category = category;
        return this;
    }

    public GameStateBuilder setQuestion(String question) {
        this.question = question;
        return this;
    }

    public GameStateBuilder setTargetPoint(Coordinate targetPoint) {
        this.targetPoint = targetPoint;
        return this;
    }

    public GameStateBuilder setSymmetryPoint(Coordinate symmetryPoint) {
        this.symmetryPoint = symmetryPoint;
        return this;
    }

    public GameStateBuilder setSymmetryFigure(LineFigure lineFigure) {
        this.symmetryFigure = lineFigure;
        return this;
    }

    public GameStateBuilder setDrawnSymmetryFigure(LineFigure lineFigure) {
        this.drawnSymmetryFigure = lineFigure;
        return this;
    }

    public GameStateBuilder setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
        return this;
    }

    public GameStateBuilder setCircle(Circle circle) {
        this.circle = circle;
        return this;
    }

    public GameStateBuilder setTriangle(Triangle triangle) {
        this.triangle = triangle;
        return this;
    }

    public GameStateBuilder setTargetAnswer(String targetAnswer) {
        this.targetAnswer = targetAnswer;
        return this;
    }


    public GameState build() {
        return new GameState(origin, xScale, yScale, targetDot, selectedDot, shapeFourCorners
                , category, question, targetPoint, symmetryLine, 0, symmetryPoint
                , symmetryFigure, drawnSymmetryFigure, rectangle, circle, triangle, targetAnswer);
    }
}
