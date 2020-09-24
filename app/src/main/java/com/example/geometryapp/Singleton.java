package com.example.geometryapp;

public class Singleton {

    private static final Singleton ourInstance = new Singleton();
    private int randomNum;
    private int x;
    private int y;
    private int L2X;
    private int L2Y;

private Singleton(){
}

public static Singleton getInstance(){return ourInstance;}


public void setRandomNum(int randomNum){
    this.randomNum = randomNum;
}
public int getRandomNum(){
    return randomNum;
}

public void setXCoordinate(int x){this.x = x;}
public int getXCoordinate(){return x;}

public void setYCoordinate(int y){this.y = y;}
public int getYCoordinate(){return y;}

    public int getL2X() {
        return L2X;
    }

    public void setL2X(int l2X) {
        L2X = l2X;
    }

    public int getL2Y() {
        return L2Y;
    }

    public void setL2Y(int l2Y) {
        L2Y = l2Y;
    }
}
