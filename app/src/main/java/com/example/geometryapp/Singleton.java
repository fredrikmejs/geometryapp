package com.example.geometryapp;

public class Singleton {

    private static final Singleton ourInstance = new Singleton();
    private int randomNum;

private Singleton(){
}

public static Singleton getInstance(){return ourInstance;}


public void setRandomNum(int randomNum){
    this.randomNum = randomNum;
}
public int getRandomNum(){
    return randomNum;
}
}
