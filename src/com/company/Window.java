package com.company;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class Window extends JFrame {
    public Habitat habit;
    public MainPanel mainPanel = new MainPanel();
    private int winWidth = 900;
    private int winHeight = 600;

    public Window(Habitat habitat) {
        super("Laba 1");

        habit = habitat;
        addWindow();
        AddWindowListener();
    }

    class MainPanel extends JPanel {
        JPanel panelForLabels = new JPanel();
        JPanel panelForButtons = new JPanel();
        JButton stopButton = new JButton("STOP");
        JButton startButton = new JButton("START");
        JButton confirmButton = new JButton("CONFIRM");

        public MainPanel() {
            panelForLabels.setLayout(new BoxLayout(panelForLabels, BoxLayout.Y_AXIS));
            GridBagLayout gbl = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            panelForButtons.setLayout(gbl);
            c.anchor = GridBagConstraints.SOUTHEAST;
            c.fill   = GridBagConstraints.NONE;
            c.gridheight = 1;
            c.gridwidth  = 1;
            c.gridx = GridBagConstraints.RELATIVE;
            c.gridy = GridBagConstraints.RELATIVE;
            c.insets = new Insets(10, 10, 0, 0);
            gbl.setConstraints(panelForLabels,c);
            panelForButtons.add(stopButton);
            panelForButtons.add(startButton);
            panelForButtons.add(confirmButton);
            this.add(panelForLabels);
            this.add(panelForButtons);
        }
    }

    public void addWindow() {
        this.setSize(new Dimension(winWidth, winHeight));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(300, 300));
        this.setVisible(true);
        this.add(mainPanel,BorderLayout.WEST);
        mainPanel.setFocusable(false);
    }

    public void setHabitat(Habitat habitat) {
        habit = habitat;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        Graphics2D g2 = (Graphics2D)getContentPane().getGraphics();
//        BufferedImage bufferedImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2Buf = bufferedImage.createGraphics();
//        SingleVector.getSingleVector().drawHouses(g2Buf);
//        g2.drawImage(bufferedImage,0,0,this);
    }

    private void AddWindowListener() {
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_E:
                        System.out.println("E is Pressed");
                        if (habit.isRun())
                            habit.EndSimulation();
                        break;
                    case KeyEvent.VK_B:
                        System.out.println("B is Pressed");
                        if (!habit.isRun()) {
                            habit.StartSimulation();
                        }
                        break;
                    case KeyEvent.VK_P:
                        System.out.println("P is Pressed");
                        if (habit.isRun()) {
                            habit.PauseSimulation();
                        }
                        break;
                    case KeyEvent.VK_T:
                        habit.ShowOrHide();
                        System.out.println("T is Pressed");
                        break;
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                    default:
                        System.out.println(e.getKeyChar() + " is Pressed");
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
