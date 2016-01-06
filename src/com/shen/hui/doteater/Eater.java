package com.shen.hui.doteater;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created on 2015/3/2.
 *
 * @author js
 */
public class Eater extends GameObject {

    public Bitmap eaterPic;
    public int width, height;

    public Eater(Bitmap bitmap, int posX, int posY, float mass) {
        super(posX, posY, mass);
        eaterPic = bitmap;
        width = eaterPic.getWidth();
        height = eaterPic.getHeight();
        radius = (eaterPic.getHeight() + eaterPic.getWidth()) / 2;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Point screenPos) {
        Point pos = position.worldToScreen(screenPos, canvas.getWidth(), canvas.getHeight());
        Matrix matrix = new Matrix();
        matrix.postTranslate(pos.x - width / 2, pos.y - height / 2);
        matrix.postRotate((float) (-direction / Math.PI * 180 + 90), pos.x, pos.y);
        canvas.drawBitmap(eaterPic, matrix, paint);
    }
}
