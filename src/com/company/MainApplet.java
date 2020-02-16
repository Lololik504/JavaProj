package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainApplet extends JApplet {
    private Timer m_timer = new Timer();
    private boolean m_runViaFrame = false;
    private double m_time = 0;

    private class Updater extends TimerTask {
        private MainApplet m_applet = null;
        // Первый ли запуск метода run()?
        private boolean m_firstRun = true;
        // Время начала
        private long m_startTime = 0;
        // Время последнего обновления
        private long m_lastTime = 0;

        public Updater(MainApplet applet)         {
            m_applet = applet;
        }
        @Override
        public void run()     {
            if (m_firstRun)       {
                m_startTime = System.currentTimeMillis();
                m_lastTime = m_startTime;
                m_firstRun = false;
            }
            long currentTime = System.currentTimeMillis();
            // Время, прошедшее от начала, в секундах
            double elapsed = (currentTime - m_startTime) / 1000.0;
            // Время, прошедшее с последнего обновления, в секундах
            double frameTime = (m_lastTime - m_startTime) / 1000.0;
            // Вызываем обновление
            m_applet.Update(elapsed, frameTime);
            m_lastTime = currentTime;
        }
    }
    public MainApplet()     {     Init();    }
    public MainApplet(boolean viaFrame)     {
        m_runViaFrame = viaFrame;
        Init();
    }
    private void Init()     {
        // таймер будет вызываться каждые 100 мс
        m_timer.schedule(new Updater(this), 0, 100);
    }
    public void Update(double elapsedTime, double frameTime)     {
        m_time = elapsedTime;
        this.repaint();
    }
    @Override
    public void paint(Graphics g)      {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        String str = "Time = " + Double.toString(m_time);
        g.drawString(str, 15, 15);
    }

}
