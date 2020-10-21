package com.example.geometryapp.Interface;

import com.example.geometryapp.DrawObjects.Circle;
import com.example.geometryapp.DrawObjects.Rectangle;
import com.example.geometryapp.DrawObjects.ShapeFourCorners;
import com.example.geometryapp.DrawObjects.Triangle;

public interface CanvasDraw {

    void drawRectangle();

    void drawCircle();

    void drawTriangle();

    void drawRectangle(Rectangle rectangle);

    void drawCircle(Circle circle);

    void drawTriangle(Triangle triangle);

    void drawPointShape();

    void drawPointShape(ShapeFourCorners shapeFourCorners);
}
