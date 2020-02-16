package com.company;

import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;

public class Habitat extends JFrame {
    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private Graphics g;
    private ConcreteFactory Generator;

    private static final long serialVersionUID = 1L;
    private JScrollBar scrollBarP1 = new JScrollBar();
    private JScrollBar scrollBarP2 = new JScrollBar();
    private JLabel label = null;//Время с начала работы
    private JLabel label2 = null;//Время обновления
    private JLabel label3 = null;//P1
    private JLabel label4 = null;//P2
    private JLabel label5 = null;//Информация о домах

    private Timer myTimer;
    private int winWidth = 900;
    private int winHeight = 600;
    //Массив обьектов
    private int i = 0;
    private int countOfWoodenHouses = 0;
    private int countOfCapitalHouses = 0;
    //P1 - вероятность появления капитального дома, P2 - деревянного
    private int P1, P2;
    public ArrayList<House> houses = new ArrayList<House>();


    public Habitat() {
        super("Laba 1");
        { label = new JLabel(String.format("Время с начала 0"));
        label2 = new JLabel(String.format("Время обновления 0"));
        label3 = new JLabel(String.format("%d", scrollBarP1.getValue()));
        label4 = new JLabel(String.format("%d", scrollBarP2.getValue()));
        label5 = new JLabel(String.format("Количество капитальных домов: %d, количество деревянных домов: %d, всего домов: %d",
                countOfCapitalHouses,
                countOfWoodenHouses,
                i));}
        P1 = 40;
        P2 = 56;
        Generator = new ConcreteFactory();
        addWindow();
        myTimer = new Timer();
        myTimer.schedule(new Updater(), 0, 100);
        myTimer.schedule(new UpdaterForHouse(), 0, 1000);


        //Создание и запуск тиков таймера

        AddWindowListener();
        AddPanelsToWindow();
        AddComponentsToLeftPanel();
        //Window.add(new Picture());
        g = panel.getGraphics();

    }

    private void AddHouse(House tmp) {
        houses.add(i, tmp);
        i++;
    }

    private void CheckHouse() {
        House tmp = Generator.Generate(P1);
        if (tmp != null) {
            AddHouse(tmp);
            countOfCapitalHouses++;
        }
        tmp = Generator.Generate(P2);
        if (tmp != null) {
            countOfWoodenHouses++;
            AddHouse(tmp);
        }
    }

    public void Update(double workTime, double frameTime) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setText(String.format("Время с начала работы %f", workTime));
        label2.setText(String.format("Время обновления %f", frameTime));
        label3.setText(String.format("P1: %d", scrollBarP1.getValue()));
        label4.setText(String.format("P2: %d", scrollBarP2.getValue()));
        P1 = scrollBarP1.getValue();
        P2 = scrollBarP2.getValue();
        label5.setText(String.format("Количество капитальных домов: %d, количество деревянных домов: %d, всего домов: %d",
                countOfCapitalHouses,
                countOfWoodenHouses,
                i));
    }

    private void addWindow() {
        // Border border = LineBorder.createGrayLineBorder();
        //Создание окна с заголовком
        this.setSize(new Dimension(winWidth, winHeight));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(winWidth, winHeight));
        this.setMinimumSize(new Dimension(200, 200));
        this.setVisible(true);
    }

    static class Picture extends JComponent {

        protected void paintComponent(Graphics g) {
            double SIZE = 0.2;
            Graphics2D g2 = (Graphics2D) g;
            String filename = "src/pictures/wooden/1.jpg";
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("src/pictures/capital/1.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedImage imageToChange = new BufferedImage((int) (image.getWidth() * SIZE), (int) (image.getHeight() * SIZE), image.getType());
            AffineTransform affineTransform = AffineTransform.getScaleInstance(SIZE, SIZE);
            BufferedImageOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BICUBIC);
            op.filter(image, imageToChange);
            g2.setBackground(Color.WHITE);
            g2.drawImage(imageToChange, 0, 0, null);
        }

    }

    private class Updater extends TimerTask {
        // Первый ли запуск метода run()?
        private boolean m_firstRun = true;
        // Время начала
        private long m_startTime = 0;
        // Время последнего обновления
        private long m_lastTime = 0;

        @Override
        public void run() {
            if (m_firstRun) {
                m_startTime = System.currentTimeMillis();
                m_lastTime = m_startTime;
                m_firstRun = false;
            }
            long currentTime = System.currentTimeMillis();
            // Время, прошедшее от начала, в секундах
            double elapsed = (currentTime - m_startTime) / 1000.0;
            // Время, прошедшее с последнего обновления, в секундах
            double frameTime = (currentTime - m_lastTime) / 1000.0;
            // Вызываем обновление
            Update(elapsed, frameTime);
            m_lastTime = currentTime;
        }


    }

    private class UpdaterForHouse extends TimerTask {
        @Override
        public void run() {

            CheckHouse();
        }
    }

    private void AddComponentsToLeftPanel() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel.add(label);
        panel.add(label2);
        scrollBarP1.setMaximum(110);
        scrollBarP2.setMaximum(110);
        panel2.add(scrollBarP1);
        panel2.add(label3);
        panel2.add(scrollBarP2);
        panel2.add(label4);
        panel.add(label5);

    }

    private void AddPanelsToWindow() {
        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.WEST);
        this.add(panel2,BorderLayout.EAST);
    }

    private void AddWindowListener() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_E:

                        System.out.println("E is Pressed");
                        break;
                    case KeyEvent.VK_B:

                        System.out.println("B is Pressed");
                        break;
                    case KeyEvent.VK_T:
                        if (panel.isVisible())
                            panel.setVisible(false);
                        else panel.setVisible(true);
                        System.out.println("T is Pressed");
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_E:
                        System.out.println("E is Released");
                        break;
                    case KeyEvent.VK_B:
                        System.out.println("B is Released");
                        break;
                }
            }
        });
    }


}
