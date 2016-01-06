package com.shen.hui.doteater;

/**
 * Created on 2015/3/4.
 *
 * @author js
 */
public class PointD {
    public double x;
    public double y;

    public PointD() {
        x = 0;
        y = 0;
    }

    public PointD(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PointD add(PointD pointD) {
        return new PointD(x + pointD.x, y + pointD.y);
    }

    public PointD sub(PointD pointD) {
        return new PointD(x - pointD.x, y - pointD.y);
    }
}
