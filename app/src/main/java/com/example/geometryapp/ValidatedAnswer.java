package com.example.geometryapp;

import com.example.geometryapp.DrawObjects.LineFigure;
import com.example.geometryapp.DrawObjects.SymmetryLine;

public class ValidatedAnswer {
    private boolean answerIsCorrect;//Was the users answer correct?
    private boolean yIsCorrect;//Was the y coordinate correct?
    private boolean xIsCorrect;//Was the x coordinate correct?
    private Coordinate correctAnswer;//This is the correct answer. If user answers wrong this can be drawn into the screen
    private LineFigure symmetryLineFigureAnswer;//This is the correct answer. If user answers wrong this can be drawn into the screen

    //Object which tells, was the answer right and which parts where correct.
    public ValidatedAnswer(boolean answerIsCorrect, boolean yIsCorrect, boolean xIsCorrect) {
        this.answerIsCorrect = answerIsCorrect;
        this.yIsCorrect = yIsCorrect;
        this.xIsCorrect = xIsCorrect;
        this.correctAnswer = null;
        this.symmetryLineFigureAnswer = null;
    }

    public boolean isAnswerCorrect() {
        return answerIsCorrect;
    }

    public void setIsAnswerCorrect(boolean answerIsCorrect) {
        this.answerIsCorrect = answerIsCorrect;
    }

    public boolean isYCorrect() {
        return yIsCorrect;
    }

    public void setIsYCorrect(boolean yIsCorrect) {
        this.yIsCorrect = yIsCorrect;
    }

    public boolean isXCorrect() {
        return xIsCorrect;
    }

    public void setIsXCorrect(boolean xIsCorrect) {
        this.xIsCorrect = xIsCorrect;
    }

    public Coordinate getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Coordinate correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public LineFigure getSymmetryLineAnswer() {
        return symmetryLineFigureAnswer;
    }

    public void LineFigure(LineFigure symmetryLineAnswer) {
        this.symmetryLineFigureAnswer = symmetryLineAnswer;
    }
}
