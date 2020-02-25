package com.company;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        JButton stopButton = new JButton();
        JButton startButton = new JButton();
        JButton confirmButton = new JButton();
        public void MainPanel() {

        }
    }

    public void addWindow() {
        this.setSize(new Dimension(winWidth, winHeight));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(300, 300));
        this.setVisible(true);
    }

    public void setHabitat(Habitat habitat) {
        habit = habitat;
    }

    @Override
    public synchronized void paint(Graphics g) {
        super.paint(g);
        Image buff;
        buff = createImage(getWidth(),getHeight());
        Graphics BufferGraphics = buff.getGraphics();
        SingleVector.getSingleVector().drawHouses(BufferGraphics);
        g.drawImage(buff,0,0,null);
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
