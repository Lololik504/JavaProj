package com.company;

import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;

public class Habitat {
    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private JTextArea area = new JTextArea();
    private JLabel label = new JLabel();
    private JLabel label2 = new JLabel();
    private Window window;

    private int P1 = 5;
    private int P2 = 10;
    private int N2 = 100;
    private int N1 = 100;

    //private Graphics g;
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

    private int countOfWoodenHouses = 0;
    private int countOfCapitalHouses = 0;
    private static final long serialVersionUID = 1L;

    public void StartSimulation() {
        isRun = true;
        myTimer = new Timer();
        myTimer.schedule(new Updater(), 0, 100);
    }

    public void ShowOrHide() {
        if (panel.isVisible()) {
            panel.setVisible(false);
            panel2.setVisible(false);
        } else {
            panel.setVisible(true);
            panel2.setVisible(true);
        }
        //window.repaint(panel.getX(), panel.getY(), panel.getWidth(), panel.getWidth());
    }

    public Habitat(int P1, int P2, int T1, int T2) {
        this.P1 = P1;
        this.P2 = P2;
        N1 = T1;
        N2 = T2;
        window = new Window(this);
        window.setLayout(new BorderLayout());

        WoodenHouse.SetImage();
        CapitalHouse.SetImage();

        area.setFocusable(false);
        area.setVisible(true);

        label.setVisible(true);
        label2.setVisible(true);

        label.setText("Время с начала работы 0");
        label2.setText("Время обновления 0");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel2.setLayout(new BorderLayout());

        panel.add(label);
        panel.add(label2);

        area.setText(String.format("Количество капитальных домов: %d\nКоличество деревянных домов: %d\nВсего домов: %d",
                countOfCapitalHouses,
                countOfWoodenHouses,
                i));
        panel2.add(area, BorderLayout.NORTH);
        //panel.setBounds(0, 0, 300, 30);
        //panel2.setBounds(0, window.getHeight() - area.getHeight(), 250, area.getHeight());
        window.add(panel, BorderLayout.NORTH);
        window.add(panel2, BorderLayout.SOUTH);
        window.validate();
    }

    private void AddHouse(House tmp) {
        SingleVector.getSingleVector().add(tmp);
        //tmp.Draw(window.getContentPane().getGraphics());
       // window.repaint();
        i++;
    }


    public void Update(double workTime, double frameTime) {

        //panel2.setBounds(0, window.getHeight() - area.getHeight() - 40, 250, area.getHeight());
        area.setText(String.format("Количество капитальных домов: %d\nколичество деревянных домов: %d\nвсего домов: %d",
                countOfCapitalHouses,
                countOfWoodenHouses,
                i));
        label.setText(String.format("Время с начала работы %.2f", workTime + WORK_TIME));
        label2.setText(String.format("Время обновления %.2f", frameTime));

        window.repaint();
    }

    public void EndSimulation() {
        myTimer.cancel();
        isRun = false;
        SingleVector.getSingleVector().clear();
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
            if (currentTime - LAST_CAP_TIME > N1) {
                LAST_CAP_TIME = currentTime;
                House tmp = capitalFactory.Generate(P1);
                if (tmp != null) {
                    countOfCapitalHouses++;
                    tmp.SetX(r.nextInt(window.getWidth() - tmp.GetWidth() * 2) + tmp.GetWidth());
                    tmp.SetY(r.nextInt(window.getHeight() - tmp.GetHeight() * 2) + tmp.GetHeight());
                    AddHouse(tmp);
                }
            }

            if (currentTime - LAST_WOOD_TIME > N2) {
                LAST_WOOD_TIME = currentTime;
                House tmp = woodenFactory.Generate(P2);
                if (tmp != null) {
                    countOfCapitalHouses++;
                    tmp.SetX(r.nextInt(window.getWidth() - tmp.GetWidth() * 2) + tmp.GetWidth());
                    tmp.SetY(r.nextInt(window.getHeight() - tmp.GetHeight() * 2) + tmp.GetHeight());
                    AddHouse(tmp);
                }
            }
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