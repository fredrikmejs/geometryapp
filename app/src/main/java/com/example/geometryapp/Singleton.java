package com.example.geometryapp;

public class Singleton {

    private static final Singleton ourInstance = new Singleton();
    private int randomNum;
    private int x;
    private int y;

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


}
