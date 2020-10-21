package com.example.geometryapp;

import android.util.Pair;

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

/**
 * Explains what a gamestate is and about the current game.
 */
public class GameState {

    //This class contains all of the information that level has.
    //For example origin position, scales, level question and shapes that are drawn at the screen.
    private Coordinate origin;
    private int xScale;
    private int yScale;
    private TargetDot targetDot;
    private SelectedDot selectedDot;
    private ShapeFourCorners shapeFourCorners;
    private Categories category;
    private String question;
    private Coordinate targetPoint;
    private String targetAnswer;//Answer that is displayed to the user. For example area of the rectangle
    private Pair<String, String> typedCoordinateAnswer;//Answer that is displayed to the user. For coordinate of a point
    private String typedValueAnswer;//Answer which user has typed. For example area of a circle
    private SymmetryLine symmetryLine;
    private Coordinate coordinateCorrectAnswer;//Coordinate of the correct answer
    private LineFigure symmetryFigure;//This is the figure user is trying to replicate
    private LineFigure drawnSymmetryFigure;//This is the figure user has drawn by selecting points
    private LineFigure lineFigureCorrectAnswer;//Correct answer to
    private boolean answeredCorrectly;
    private int attempt;
    private Coordinate symmetryPoint;
    private Rectangle rectangle;
    private Circle circle;
    private Triangle triangle;
    private boolean englishNumbers = true;
    private int level;

    public GameState(Coordinate origin, int xScale, int yScale, TargetDot targetDot, SelectedDot selectedDot
            , ShapeFourCorners shapeFourCorners
            , Categories category, String question, Coordinate targetPoint
            , SymmetryLine symmetryLine, int attempt
            , Coordinate symmetryPoint, LineFigure symmetryFigure, LineFigure drawnSymmetryFigure
            , Rectangle rectangle, Circle circle, Triangle triangle, String targetAnswer) {
        this.origin = origin;
        this.xScale = xScale;
        this.yScale = yScale;
        this.targetDot = targetDot;
        this.selectedDot = selectedDot;
        this.shapeFourCorners = shapeFourCorners;
        this.category = category;
        this.question = question;
        this.targetPoint = targetPoint;
        this.symmetryLine = symmetryLine;
        this.attempt = attempt;
        this.symmetryPoint = symmetryPoint;
        this.symmetryFigure = symmetryFigure;
        this.drawnSymmetryFigure = drawnSymmetryFigure;
        this.rectangle = rectangle;
        this.circle = circle;
        this.triangle = triangle;
        this.answeredCorrectly = false;
        this.targetAnswer = targetAnswer;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public Triangle getTriangle() {
        return triangle;
    }

    public void setTriangle(Triangle triangle) {
        this.triangle = triangle;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Coordinate getOrigin() {
        return origin;
    }

    public void setOrigin(Coordinate origin) {
        this.origin = origin;
    }

    public int getXScale() {
        return xScale;
    }

    public void setXScale(int xScale) {
        this.xScale = xScale;
    }

    public int getYScale() {
        return yScale;
    }

    public void setYScale(int yScale) {
        this.yScale = yScale;
    }

    public TargetDot getTargetDot() {
        return targetDot;
    }

    public ShapeFourCorners getShapeFourCorners() {
        return shapeFourCorners;
    }

    public void setShapeFourCorners(ShapeFourCorners shapeFourCorners) {
        this.shapeFourCorners = shapeFourCorners;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public SelectedDot getSelectedDot() {
        return selectedDot;
    }

    public void setSelectedDot(SelectedDot selectedDot) {
        this.selectedDot = selectedDot;
    }

    public void setSelectedDotCoordinate(Coordinate coordinate) {
        selectedDot.setCoordinate(coordinate);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Coordinate getTargetPoint() {
        return targetPoint;
    }


    public Pair<String, String> getTypedCoordinateAnswer() {
        return typedCoordinateAnswer;
    }

    public void setTypedCoordinateAnswer(Pair<String, String> typedCoordinateAnswer) {
        this.typedCoordinateAnswer = typedCoordinateAnswer;
    }

    public SymmetryLine getSymmetryLine() {
        return symmetryLine;
    }

    public Coordinate getCoordinateCorrectAnswer() {
        return coordinateCorrectAnswer;
    }

    public void setCoordinateCorrectAnswer(Coordinate coordinateCorrectAnswer) {
        this.coordinateCorrectAnswer = coordinateCorrectAnswer;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public Coordinate getSymmetryPoint() {
        return symmetryPoint;
    }

    public LineFigure getSymmetryFigure() {
        return symmetryFigure;
    }

    public LineFigure getLineFigureCorrectAnswer() {
        return lineFigureCorrectAnswer;
    }

    public void setLineFigureCorrectAnswer(LineFigure lineFigureCorrectAnswer) {
        this.lineFigureCorrectAnswer = lineFigureCorrectAnswer;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public String getTypedValueAnswer() {
        return typedValueAnswer;
    }

    public void setTypedValueAnswer(String typedValueAnswer) {
        this.typedValueAnswer = typedValueAnswer;
    }

    public String getTargetAnswer() {
        return targetAnswer;
    }

    public void setAnsweredCorrectly(boolean isCorrect){
        answeredCorrectly = isCorrect;
    }

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public boolean isEnglishNumbers() {
        return englishNumbers;
    }

    public void setEnglishNumbers(boolean englishNumbers) {
        this.englishNumbers = englishNumbers;
    }
}
