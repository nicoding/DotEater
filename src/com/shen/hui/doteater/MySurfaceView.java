package com.shen.hui.doteater;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created on 2015/3/2.
 *
 * @author js
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread gameThread;
    private int screenW, screenH;
    private Canvas canvas;
    private boolean runFlag;
    private long startTime, endTime;
    private Point screenPos = new Point();

    private Eater eater;

    private Point bh = new Point();

    public MySurfaceView(Context context) {
        super(context);

        runFlag = false;

        sfh = this.getHolder();
        sfh.addCallback(this);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);

        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void run() {
        while (runFlag) {
            if (startTime == 0)
                startTime = System.currentTimeMillis();
            endTime = System.currentTimeMillis();
            gameDraw();
            gameLogic(endTime - startTime);
            try {
                if (endTime - startTime < 33) {
                    Thread.sleep(33 - (endTime - startTime));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startTime = endTime;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        initGame();
        gameThread = new Thread(this);
        runFlag = true;
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenW = this.getWidth();
        screenH = this.getHeight();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        runFlag = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //eater.setVelocity(x-screenW/2, y-screenH/2);
                bh.set((int) event.getX(), (int) event.getY());
                bh = bh.screenToWorld(screenPos, screenW, screenH);
                break;
        }
        return true;
    }

    private void initGame() {
        eater = new Eater(BitmapFactory.decodeResource(getResources(), R.drawable.eater),
                screenW / 2, screenH / 2, 1);
    }

    private void gameDraw() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.BLACK);
                eater.draw(canvas, paint, screenPos);
                Point tmp = bh.worldToScreen(screenPos, screenW, screenH);
                canvas.drawCircle(tmp.x, tmp.y, 10, paint);
            }
        } catch (Exception e) {

        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    private void gameLogic(long deltaTime) {
        double accX = bh.x - eater.position.x;
        double accY = bh.y - eater.position.y;
        double tmp = Math.sqrt(accX * accX + accY * accY) / 50;
        accX = accX / tmp;
        accY = accY / tmp;
        eater.updateVelocity(accX, accY, deltaTime);
        eater.updatePosition(deltaTime);
        screenPos.set(0, eater.position.y - screenH / 2);
    }

}
