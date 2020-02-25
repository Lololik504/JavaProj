package com.company;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class Window extends JFrame {
    public Habitat habit;
    public MainPanel mainPanel;
    private int winWidth = 900;
    private int winHeight = 600;

    class MainPanel extends JPanel {
        JPanel panelForLabels = new JPanel();
        JPanel panelForButtons = new JPanel();
        JPanel PANEL = new JPanel();
        JButton stopButton = new JButton("STOP");
        JButton startButton = new JButton("START");
        JButton confirmButton = new JButton("CONFIRM");
        JButton HideButton = new JButton("HIDE");

        public MainPanel() {
            super();
            this.setLayout(new BorderLayout());
            panelForLabels.setLayout(new BoxLayout(panelForLabels, BoxLayout.Y_AXIS));
            //panelForLabels.setSize(300,200);
            GridBagLayout gbl = new GridBagLayout();
            panelForButtons.setLayout(gbl);
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.CENTER;
            c.gridy = 0;
            c.gridx = 0;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(5, 5, 5, 5);
            gbl.setConstraints(stopButton, c);
            panelForButtons.add(stopButton);

            c.gridy = 0;
            c.gridx = 1;
            gbl.setConstraints(startButton, c);
            panelForButtons.add(startButton);

            c.gridy = 1;
            c.gridx = 0;
            gbl.setConstraints(HideButton, c);
            panelForButtons.add(HideButton);

            c.gridy = 1;
            c.gridx = 1;
            gbl.setConstraints(confirmButton, c);
            panelForButtons.add(confirmButton);
            stopButton.addActionListener(e -> {
                if (habit.isRun())
                    habit.EndSimulation();
            });
            startButton.addActionListener(e -> {
                if (!habit.isRun()) {
                    habit.StartSimulation();
                }
            });
            PANEL.setLayout(new GridLayout(3,1));
            PANEL.add(panelForLabels);
            PANEL.add(panelForButtons);
            add(PANEL,BorderLayout.WEST);
        }

        public void addLabel(JLabel label) {
            panelForLabels.add(label);
        }
    }

    public Window(Habitat habitat) {
        super("Laba 1");


        this.setLayout(new BorderLayout());
        habit = habitat;
        addWindow();
        mainPanel = new MainPanel();
        getContentPane().add(mainPanel);
        AddWindowListener();
        paintAll(getGraphics());
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//    }


    public void ShowOrHide() {
        if (mainPanel.isVisible()) {
            mainPanel.setVisible(false);
        } else {
            mainPanel.setVisible(true);
        }

    }

    public void addWindow() {
        this.setSize(new Dimension(winWidth, winHeight));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(300, 300));
        this.setVisible(true);
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
                        ShowOrHide();
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
