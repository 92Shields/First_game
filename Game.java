/**
 * Main class which represent the game. Runs all methods necessary for the game
 * to play and refresh the display.
 */

package com.main;

import java.awt.*;
import java.awt.image.*;

public class Game extends Canvas implements Runnable
{
    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;

    public Game()
    {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        new Window(WIDTH, HEIGHT, "First game", this);
        hud = new HUD();

        handler.addObject(new Player(100, 100, ID.Player)); //for testing
        handler.addObject(new BasicEnemy(100, 100, ID.BasicEnemy)); //for testing

    }

    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try{
            thread.join();
            running = false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1)
            {
                tick();
                delta--;
            }
            if(running)
            {
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer +=1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick()
    {
        handler.tick();
        hud.tick();
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(g);

        hud.render(g);

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max)
    {
        if(var >= max)
        {
            return var = max;
        }
        else if(var <= min)
        {
            return var = min;
        }
        else
        {
            return var;
        }
    }

    public static void main(String args[])
    {
        new Game();
    }

}