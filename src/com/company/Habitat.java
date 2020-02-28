package com.company;

import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;

import static java.lang.String.*;

public class Habitat {
    public void setP1(int value) {
        P1 = value;
    }

    public void setN2(int n2) {
        N2 = n2;
    }

    public void setN1(int n1) {
        N1 = n1;
    }

    public void setP2(int value) {
        P2 = value;
    }

    class PanelForImages extends JPanel {
        public PanelForImages(){
            super();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            SingleVector.getSingleVector().drawHouses(g);
        }
    }

    PanelForImages panelForImages = new PanelForImages();

    private Window window;

    private int P1 = 5;
    private int P2 = 10;
    private int N2 = 100;
    private int N1 = 100;

    private CapitalFactory capitalFactory = new CapitalFactory();
    private WoodenFactory woodenFactory = new WoodenFactory();

    private boolean isRun = false;
    private int i = 0;
    private long START_TIME = 0;
    private long END_TIME = 0;
    private long LAST_CAP_TIME = 0;
    private long LAST_WOOD_TIME = 0;
    private double WORK_TIME = 0;
    private Timer myTimer;

    private static final long serialVersionUID = 1L;

    public void StartSimulation() {
        isRun = true;
        myTimer = new Timer();
        myTimer.schedule(new Updater(), 0, 20);
    }

    public Habitat(int P1, int P2, int T1, int T2) {
        this.P1 = P1;
        this.P2 = P2;
        N1 = T1;
        N2 = T2;
        window = new Window(this);
        window.setLayout(new BorderLayout());

        panelForImages.setFocusable(false);
        panelForImages.setVisible(true);

        window.mainPanel.add(panelForImages,BorderLayout.CENTER);
        //window.add(panelForImages,BorderLayout.CENTER);
        //window.add(runTimeLabel);
        window.validate();
        window.repaint();
    }

    private void AddHouse(House tmp) {
        SingleVector.getSingleVector().add(tmp);
        i++;
    }

    public void Update(double workTime, double frameTime) {
        window.repaint();
        WORK_TIME = workTime;
        window.updateLabels(frameTime);
    }

    public void EndSimulation() {
        myTimer.cancel();
        isRun = false;
        window.mainPanel.list1.removeAll();
        window.mainPanel.list2.removeAll();
        SingleVector.getSingleVector().clear();
        window.repaint();
        WoodenHouse.clearCount();
        CapitalHouse.clearCount();
        WORK_TIME = 0;
        i = 0;
    }

    public double getWORK_TIME() {
        return WORK_TIME;
    }

    public void PauseSimulation() {
        myTimer.cancel();
        isRun = false;
        WORK_TIME += ((double) (END_TIME - START_TIME)) / 1000;
    }

    public boolean isRun() {
        return isRun;
    }

    public class Updater extends TimerTask {
        private boolean m_firstRun = true;

        @Override
        public void run() {
            if (m_firstRun) {
                START_TIME = System.currentTimeMillis();
                END_TIME = START_TIME;
                m_firstRun = false;
            }

            Random r = new Random();
            long currentTime = System.currentTimeMillis();
            double elapsed = (currentTime - START_TIME) / 1000.0;
            if (currentTime - LAST_CAP_TIME > N1) {
                LAST_CAP_TIME = currentTime;
                House tmp = capitalFactory.Generate(P1);
                if (tmp != null) {
                    CapitalHouse.incrementCount();
                    tmp.SetX(r.nextInt(window.getWidth() - tmp.GetWidth() * 2));
                    tmp.SetY(r.nextInt(window.getHeight() - tmp.GetHeight() * 2));
                    window.mainPanel.list2.add(String.valueOf(elapsed));
                    AddHouse(tmp);
                }
            }

            if (currentTime - LAST_WOOD_TIME > N2) {
                LAST_WOOD_TIME = currentTime;
                House tmp = woodenFactory.Generate(P2);
                if (tmp != null) {
                    WoodenHouse.incrementCount();
                    tmp.SetX(r.nextInt(window.getWidth() - tmp.GetWidth() * 2));
                    tmp.SetY(r.nextInt(window.getHeight() - tmp.GetHeight() * 2));
                    window.mainPanel.list1.add(String.valueOf(elapsed));
                    AddHouse(tmp);
                }
            }
            // Время, прошедшее от начала, в секундах
            // Время, прошедшее с последнего обновления, в секундах
            double frameTime = (currentTime - END_TIME) / 1000.0;
            // Вызываем обновление
            Update(elapsed, frameTime);
            END_TIME = currentTime;
        }
    }
}