package com.company;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

public class Habitat {
    private JPanel panel = new JPanel();
    Window window;

    //private Graphics g;
    private CapitalFactory capitalFactory = new CapitalFactory();
    private WoodenFactory woodenFactory = new WoodenFactory();

    private static final long serialVersionUID = 1L;
    private JLabel label = null;//Время с начала работы
    private JLabel label2 = null;//Время обновления
    private JTextArea area = new JTextArea();

    private Timer myTimer;
    private int N1 = 1;
    private int N2 = 1;
    private int P1, P2;
    private long START_TIME = 0;
    private long END_TIME = 0;
    private double WORK_TIME = 0;
    private int winWidth = 900;
    private int winHeight = 600;
    private boolean isRun = false;
    //Массив обьектов
    private int i = 0;
    private int countOfWoodenHouses = 0;
    private int countOfCapitalHouses = 0;

    //P1 - вероятность появления капитального дома, P2 - деревянного

    public Vector<House> houses = new Vector<House>();

    public Vector<House> getHouses() {
        return houses;
    }

    public void StartSimulation() {
        isRun = true;
        myTimer = new Timer();
        myTimer.schedule(new Updater(), 0, 50);
        myTimer.schedule(new UpdaterForCapitalHouse(), 0, N1 * 1000);
        myTimer.schedule(new UpdaterForWoodenHouse(), 0, N2 * 1000);
    }

    private class UpdaterForCapitalHouse extends TimerTask {
        @Override
        public void run() {
            Random r = new Random();
            House tmp = capitalFactory.Generate(P1);
            if (tmp != null) {
                countOfCapitalHouses++;
                tmp.SetX(r.nextInt(winWidth - tmp.GetWidth() * 2) + tmp.GetWidth());
                tmp.SetY(r.nextInt(winHeight - tmp.GetHeight() * 2) + tmp.GetHeight());
                AddHouse(tmp);
            }
        }
    }

    private class UpdaterForWoodenHouse extends TimerTask {
        @Override
        public void run() {
            Random r = new Random();
            House tmp = woodenFactory.Generate(P2);
            if (tmp != null) {
                countOfWoodenHouses++;
                tmp.SetX(r.nextInt(winWidth - tmp.GetWidth()));
                tmp.SetY(r.nextInt(winHeight - tmp.GetHeight()));
                AddHouse(tmp);
            }
        }
    }

    public void ShowOrHide() {
        if (panel.isVisible()) {
            panel.setVisible(false);
            area.setVisible(false);
        } else {
            panel.setVisible(true);
            area.setVisible(true);
        }
    }

    public Habitat() {
        window = new Window(this);
        window.addWindow();
        //g = window.getGraphics();
        P1 = 100;
        P2 = 100;
        N1 = N2 = 1;
        WoodenHouse.SetImage();
        CapitalHouse.SetImage();
        {
            label = new JLabel(String.format("Время с начала 0"));
            label2 = new JLabel(String.format("Время обновления 0"));
        }
        area.setBounds(0, window.getHeight() - 100, 250, 100);
        area.setBackground(Color.WHITE);
        area.setFocusable(false);
        area.setVisible(true);
        window.add(area);
        AddComponentsToLeftPanel();
        window.add(panel);
        //Window.add(new Picture());
    }

    private void AddHouse(House tmp) {
        houses.add(tmp);
        //window.add(tmp);
        i++;
    }


    public void Update(double workTime, double frameTime) {
        winHeight = window.getHeight();
        winWidth = window.getWidth();
        //this.paintAll(g);
        area.setText(String.format("Количество капитальных домов: %d\nколичество деревянных домов: %d\nвсего домов: %d",
                countOfCapitalHouses,
                countOfWoodenHouses,
                i));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setText(String.format("Время с начала работы %f", workTime + WORK_TIME));
        label2.setText(String.format("Время обновления %f", frameTime));
        window.paint(window.getGraphics());
        //window.paint(houses);
    }

    public void EndSimulation() {
        myTimer.cancel();
        isRun = false;
        window.clear(houses);
        houses.clear();
        countOfWoodenHouses = 0;
        countOfCapitalHouses = 0;
        WORK_TIME = 0;
        i = 0;
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

        // Первый ли запуск метода run()?
        private boolean m_firstRun = true;

        @Override
        public void run() {
            if (m_firstRun) {
                START_TIME = System.currentTimeMillis();
                END_TIME = START_TIME;
                m_firstRun = false;
            }
            long currentTime = System.currentTimeMillis();
            // Время, прошедшее от начала, в секундах
            double elapsed = (currentTime - START_TIME) / 1000.0;
            // Время, прошедшее с последнего обновления, в секундах
            double frameTime = (currentTime - END_TIME) / 1000.0;
            // Вызываем обновление
            Update(elapsed, frameTime);
            END_TIME = currentTime;
        }

    }

    private void AddComponentsToLeftPanel() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        panel.add(label2);
    }
}
