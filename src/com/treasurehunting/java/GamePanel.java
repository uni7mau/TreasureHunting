package com.treasurehunting.java;

import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {

    public static int oldFrameCount;
    public static int oldTickCount;
    public static int tickCount;

    private Thread thread;
    private boolean running = false;

    //sử dụng bufferstrategy cải thiện hiệu suất vẽ và giảm thiểu hiện tượng nhấp nháy
    private BufferStrategy bs;
    private BufferedImage img;
    private Graphics2D g2d;

    private MouseHandler mouse;
    private KeyHandler key;

    private GameSceneManager gsm;

    public GamePanel(BufferStrategy bs, int width, int height) {
        this.bs = bs;
        setPreferredSize(new Dimension(width, height)); //Khi pack(); thì window sẽ ưu tiên resize về kích thước này
        setFocusable(true); //Nhận sự kiện bàn phím
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init() {
        running = true;

        img = new BufferedImage(GameSettings.GAME_WIDTH, GameSettings.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D)img.getGraphics();

        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        gsm = new GameSceneManager();
    }

    public void update(double time) {
        gsm.update(time);
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        gsm.input(mouse, key);
    }

    public void render() {
        if (g2d != null) {
            gsm.render(g2d);
        }
    }

    public void draw() {
        do {
            Graphics g = bs.getDrawGraphics();
            //drawing x = 0, y = 0 bị trống 1 khoảng không rõ lý do
            g.drawImage(img, 3, 26, GameSettings.GAME_WIDTH + 10, GameSettings.GAME_HEIGHT + 10, null);

            g.dispose();
            bs.show();
        } while (bs.contentsLost());
    }

    @Override
    public void run() {
        init();

        final double GAME_HERTZ = GameSettings.GAME_HERTZ;
        final double TBU = 1000000000 / GAME_HERTZ; //Time between updates 1/64 = 0.015625 or 15_625_000

        // Có 2 loại set cho MUBR là 1 hoặc 3, nếu để là 1 thì có vài trường hợp gây input lag
        // và chỉ set cao hơn khi FPS thấp hơn HERTZ
        // Trường hợp hiện tại game này chưa cần tăng lên
        final int MUBR = 1; //must update before render
        double lastUpdateTime = System.nanoTime();
        final double TTBR = (double) 1000000000 / GameSettings.GAME_MAX_FPS; //Time to be rendered

        int frameCount = 0;
        oldFrameCount = 0;
        tickCount = 0;
        oldTickCount = 0;

        int lastSecondTime = (int)(lastUpdateTime / 1000000000);

        double nextRenderTime = System.nanoTime();
        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;

            input(mouse, key);

            while (now - lastUpdateTime > TBU && updateCount < MUBR) {
                update(now);
                lastUpdateTime += TBU;
                updateCount++;
                tickCount++;
            }

            if (now >= nextRenderTime) {
                render();
                draw();
                frameCount++;
                nextRenderTime = now + TTBR;
            }

            int thisSecond = (int)(lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) { // chênh 1s
                oldFrameCount = frameCount;
                oldTickCount = tickCount;

                frameCount = 0;
                tickCount = 0;
                lastSecondTime = thisSecond;
            }

            while (System.nanoTime() < nextRenderTime && System.nanoTime() - lastUpdateTime < TBU) {
                Thread.yield(); //Nhường cpu cho tiến trình khác

                try {
                    Thread.sleep(1); //Tạm dừng luồng trong 1 milli sec
                } catch (Exception e) {
                    System.out.println("ERROR: yielding thread");
                }
            }
        }
    }

}