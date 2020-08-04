package com.example.geometryapp.DrawObjects;

import com.example.geometryapp.Coordinate;

import java.util.ArrayList;

public class LineFigure {
    private ArrayList<Line> lines;
    private int maxAmountOfLines;//Max limit of lines. For example this set to 2 in line figure exercise

    //Collection of lines. For example used in category 7. Find point with point symmetry.
    public LineFigure(int maxAmountOfLines) {
        this.maxAmountOfLines = maxAmountOfLines;
        this.lines = new ArrayList<>();
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public int getMaxAmountOfLines() {
        return maxAmountOfLines;
    }

    public void setMaxAmountOfLines(int maxAmountOfLines) {
        this.maxAmountOfLines = maxAmountOfLines;
    }

    public void removeFirstLine(){
        if(lines.size()!=0){
            lines.remove(0);
        }
    }

    public void addLine(Line line){
        lines.add(line);
        if(lines.size()>maxAmountOfLines){
            lines.remove(0);
        }
    }

    public Line getLine(int index){
        if(lines.size()<index){
            return null;
        }
        return lines.get(index);
    }

}
