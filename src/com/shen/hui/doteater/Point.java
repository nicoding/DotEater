package com.shen.hui.doteater;

/**
 * Created on 2015/3/4.
 *
 * @author js
 */
public class Point {
    public int x;
    public int y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point worldToScreen(Point screenPos, int screenW, int screenH) {
        return new Point(x - screenPos.x, screenPos.y + screenH - y);
    }

    public Point screenToWorld(Point screenPos, int screenW, int screenH) {
        return new Point(x + screenPos.x, screenPos.y + screenH - y);
    }

    public double getDistance(Point point) {
        return Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
    }

    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point sub(Point point) {
        return new Point(x - point.x, y - point.y);
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public PointD getDirection() {
        return new PointD(x / getMagnitude(), y / getMagnitude());
    }
}
