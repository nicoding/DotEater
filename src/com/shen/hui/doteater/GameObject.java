package com.shen.hui.doteater;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created on 2015/3/2.
 *
 * @author js
 */
class GameObject {
    public Point position = new Point();
    public PointD velocity = new PointD();

    public int radius;
    public double mass;
    public double direction;

    public GameObject() {
        position.set(0, 0);
        velocity.set(0, 0);
        radius = 0;
        mass = 0;

        direction = 0;
    }

    public GameObject(int x, int y, double m) {
        position.set(x, y);
        velocity.set(0, 0);
        radius = 0;
        mass = m;

        direction = 0;
    }

    public boolean isCollsionWith(GameObject gameObject) {
        double distance = Math.sqrt((position.x - gameObject.position.x) * (position.x - gameObject.position.x)
                + (position.y - gameObject.position.y) * (position.y - gameObject.position.y));
        return distance < (radius + gameObject.radius);
    }

    public void draw(Canvas canvas, Paint paint, Point screenPos) {
        Point pos = position.worldToScreen(screenPos, canvas.getWidth(), canvas.getHeight());
        canvas.drawText("GameObject", pos.x, pos.y, paint);
    }

    public void updateVelocity(double accX, double accY, long deltaTime) {
        velocity.x += accX * deltaTime / 1000;
        velocity.y += accY * deltaTime / 1000;
        direction = Math.atan2(velocity.y, velocity.x);
    }

    public void setVelocity(double velX, double velY) {
        velocity.x = velX;
        velocity.y = velY;
        direction = Math.atan2(velocity.y, velocity.x);
    }

    public void updatePosition(long deltaTime) {
        position.x += velocity.x * deltaTime / 1000;
        position.y += velocity.y * deltaTime / 1000;
    }

    public void setPosition(int posX, int posY) {
        this.position.x = posX;
        this.position.y = posY;
    }

    public PointD getAcceleration(GameObject gameObject) {
        double distance = position.getDistance(gameObject.position);
        double magnitude = 1000 * mass * gameObject.mass / distance;
        PointD acc = gameObject.position.sub(position).getDirection();
        acc.x = acc.x * magnitude;
        acc.y = acc.y * magnitude;
        return acc;
    }
}
