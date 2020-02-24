package com.company;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;


public class Habitat {
    private JPanel panel = new JPanel();
    private JTextArea area = new JTextArea();
    private JLabel label = new JLabel();
    private JLabel label2 = new JLabel();
    Window window;

    private int P1 = 50;
    private int P2 = 100;
    private int N2 = 1;
    private int N1 = 1;

    //private Graphics g;
    private CapitalFactory capitalFactory = new CapitalFactory();
    private WoodenFactory woodenFactory = new WoodenFactory();

    private boolean isRun = false;
    private int i = 0;
    private long START_TIME = 0;
    private long END_TIME = 0;
    private double WORK_TIME = 0;
    private Timer myTimer;

    private int countOfWoodenHouses = 0;
    private int countOfCapitalHouses = 0;
    private static final long serialVersionUID = 1L;


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
                tmp.SetX(r.nextInt(window.getWidth() - tmp.GetWidth() * 2) + tmp.GetWidth());
                tmp.SetY(r.nextInt(window.getHeight() - tmp.GetHeight() * 2) + tmp.GetHeight());
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

                tmp.SetX(r.nextInt(window.getWidth() - tmp.GetWidth()));
                tmp.SetY(r.nextInt(window.getHeight() - tmp.GetHeight()));
                AddHouse(tmp);
            }
        }
    }

    public void ShowOrHide() {
        if (label.isVisible()) {
            label.setVisible(false);
            label2.setVisible(false);
        } else {
            label.setVisible(true);
            label2.setVisible(true);
        }
        window.repaint();
    }

    public Habitat() {
        window = new Window(this);
        window.addWindow();
        window.setLayout(new BorderLayout());
        window.add(label,BorderLayout.NORTH);
        label.setVisible(true);
        label.setText(String.format("Время с начала работы 0"));
        label.setBounds(0,0,300,100);
        label.paint(window.getGraphics());
        WoodenHouse.SetImage();
        CapitalHouse.SetImage();
    }

    private void AddHouse(House tmp) {
        SingleVector.add(tmp);
        tmp.Draw(window.getGraphics());
        //window.add(tmp);
        i++;
    }


    public void Update(double workTime, double frameTime) {

        area.setText(String.format("Количество капитальных домов: %d\nколичество деревянных домов: %d\nвсего домов: %d",
                countOfCapitalHouses,
                countOfWoodenHouses,
                i));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setText(String.format("Время с начала работы %f", workTime + WORK_TIME));
        label2.setText(String.format("Время обновления %f", frameTime));
        // window.repaint();
    }

    public void EndSimulation() {
        myTimer.cancel();
        isRun = false;

        window.clear();
        SingleVector.getVector().clear();
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
}


