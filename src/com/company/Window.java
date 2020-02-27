package com.company;

import javafx.scene.layout.GridPane;

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
        JPanel panelForControls = new JPanel();
        JButton stopButton = new JButton("STOP");
        JButton startButton = new JButton("START");
        JButton confirmButton = new JButton("CONFIRM");
        JButton hideButton = new JButton("HIDE");
        JSlider slider = new JSlider(0, 100, 50);
        JComboBox comboBox = new JComboBox();

        private void setFocus() {
            stopButton.setFocusable(false);
            startButton.setFocusable(false);
            confirmButton.setFocusable(false);
            hideButton.setFocusable(false);
            slider.setFocusable(false);
            comboBox.setFocusable(false);
        }

        private void ButtonsListeners() {
            stopButton.addActionListener(e -> {
                if (habit.isRun())
                    EndSimulation();
            });
            startButton.addActionListener(e -> {
                if (!habit.isRun()) {
                    StartSimulation();
                }
            });
            hideButton.addActionListener(e -> {
                ShowOrHide();
            });
            confirmButton.addActionListener(e -> {
                habit.setP1(slider.getValue());
                //System.out.println(comboBox.getSelectedItem());
                Object object = new Object();
                habit.setP2(Integer.parseInt((String) comboBox.getSelectedItem()));
            });
        }



        private void SliderOptions() {
            slider.setName("P1");
            slider.setMajorTickSpacing(20);
            slider.setMinorTickSpacing(5);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
        }

        public MainPanel() {
            super();
            setOpaque(false);
            panelForLabels.setOpaque(false);
            panelForButtons.setOpaque(false);
            panelForControls.setOpaque(false);
            PANEL.setOpaque(false);
            setFocus();
            this.setLayout(new BorderLayout());
            panelForLabels.setLayout(new BoxLayout(panelForLabels, BoxLayout.Y_AXIS));
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
            gbl.setConstraints(hideButton, c);
            panelForButtons.add(hideButton);

            c.gridy = 1;
            c.gridx = 1;
            gbl.setConstraints(confirmButton, c);
            panelForButtons.add(confirmButton);
            ButtonsListeners();

            panelForControls.setLayout(new BoxLayout(panelForControls, BoxLayout.Y_AXIS));
            panelForControls.add(new JLabel("P1"));
            panelForControls.add(slider);
            panelForControls.add(Box.createRigidArea(new Dimension(0,20)));
            panelForControls.add(new JLabel("P2"));
            panelForControls.add(comboBox);
            panelForControls.add(Box.createRigidArea(new Dimension(0,5)));
            ComboBoxOptions();
            SliderOptions();

            PANEL.setLayout(new BorderLayout());
            GridBagConstraints p = new GridBagConstraints();
            {
                JPanel NORTHWEST = new JPanel();
                NORTHWEST.setLayout(new GridBagLayout());
                PANEL.add(NORTHWEST,BorderLayout.NORTH);

                p.gridx = 0;
                p.gridy = GridBagConstraints.RELATIVE;
                NORTHWEST.add(panelForLabels,p);
                p.ipady = 150;
                NORTHWEST.add(panelForButtons,p);
                p.ipady = 150;
                NORTHWEST.add(panelForControls,p);
            }


            add(PANEL, BorderLayout.WEST);

        }

        private void ComboBoxOptions() {
            for (int i = 0; i <= 100; i+=5)
            comboBox.addItem(String.format("%d",i));
            comboBox.setEditable(true);
            comboBox.setSelectedItem("50");
            comboBox.setMaximumSize(new Dimension(500,20));
        }


        public void addLabel(JLabel label) {
            panelForLabels.add(label);
        }
    }

    private void EndSimulation (){
        mainPanel.stopButton.setEnabled(false);
        mainPanel.startButton.setEnabled(true);
        habit.EndSimulation();
    }

    private void StartSimulation(){
        mainPanel.startButton.setEnabled(false);
        mainPanel.stopButton.setEnabled(true);
        habit.StartSimulation();
    }

    public Window(Habitat habitat) {
        super("Laba 1");
        this.setLayout(new BorderLayout());
        habit = habitat;
        addWindow();
        mainPanel = new MainPanel();
        add(mainPanel);
        AddWindowListener();
        paintAll(getGraphics());
    }

    public void ShowOrHide() {
        if (mainPanel.PANEL.isVisible()) {
            mainPanel.PANEL.setVisible(false);
        } else {
            mainPanel.PANEL.setVisible(true);
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
                            EndSimulation();
                        break;
                    case KeyEvent.VK_B:
                        System.out.println("B is Pressed");
                        if (!habit.isRun()) {
                            StartSimulation();
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
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Resized();
            }
        });
    }

    public void Resized() {
        mainPanel.setSize(getWidth(), getHeight());
    }
}
