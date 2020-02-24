package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class Window extends JFrame {
    Habitat habitat;
    private int winWidth = 900;
    private int winHeight = 600;


    public Window(Habitat habitat) {
        super("Laba 1");
        this.habitat = habitat;
        AddWindowListener();
    }

    public void addWindow() {
        this.setSize(new Dimension(winWidth, winHeight));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // this.setPreferredSize(new Dimension(winWidth, winHeight));
        this.setMinimumSize(new Dimension(300, 300));
        this.setVisible(true);
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    @Override
    public synchronized void paint(Graphics g) {
        //super.paint(g);
        //this.repaint(g);
        Vector<House> houses = habitat.getHouses();
        if (houses != null) {
            for (House house : houses) {
                this.add(house);
                house.setVisible(true);
                //house.repaint();
            }
        }
    }

    public void clear(Vector<House> houses) {
        for (House house : houses) {
            house.setVisible(false);
            this.remove(house);
        }
    }

    private void AddWindowListener() {
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_E:
                        System.out.println("E is Pressed");
                        if (habitat.isRun())
                            habitat.EndSimulation();
                        break;
                    case KeyEvent.VK_B:
                        System.out.println("B is Pressed");
                        if (!habitat.isRun()) {
                            habitat.StartSimulation();
                        }
                        break;
                    case KeyEvent.VK_P:
                        System.out.println("P is Pressed");
                        if (habitat.isRun()) {
                            habitat.PauseSimulation();
                        }
                        break;
                    case KeyEvent.VK_T:
                        habitat.ShowOrHide();
                        System.out.println("T is Pressed");
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
