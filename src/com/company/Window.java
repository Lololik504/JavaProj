package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.String.format;

public class Window extends JFrame {
    public Habitat habit;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu runMenu = new JMenu("Run");
    private JMenu controlsMenu = new JMenu("Tools");
    private JMenuItem itemRun = new JMenuItem("Run");
    private JMenuItem itemStop = new JMenuItem("Stop");
    private JMenuItem itemHideOrShow = new JMenuItem("Hide/Show");
    public MainPanel mainPanel;
    private int winWidth = 900;
    private int winHeight = 600;

    class MainPanel extends JPanel {
        public JLabel runTimeLabel = new JLabel("Время с начала работы 0");
        public JLabel updateTimeLabel = new JLabel("Время обновления 0");
        private JLabel woodHousesLabel = new JLabel("Количество деревянных домов: 0");
        private JLabel capitalHousesLabel = new JLabel("Количество капитальных домов: 0");
        private JLabel totalHousesLabel = new JLabel("Всего домов: 0");
        List list1 = new List();
        List list2 = new List();
        JPanel panelForLabels = new JPanel();
        JPanel panelForButtons = new JPanel();
        JPanel panelLeft = new JPanel();
        JPanel panelNorthWest = new JPanel();
        JPanel panelForControls = new JPanel();
        JPanel panelForTable = new JPanel();
        JButton stopButton = new JButton("STOP");
        JButton startButton = new JButton("START");
        JButton confirmButton = new JButton("CONFIRM");
        JButton hideButton = new JButton("HIDE/SHOW");
        JCheckBox isGetInfo = new JCheckBox("Показывать информацию");
        JSlider slider = new JSlider(0, 100, 50);

        JComboBox comboBox = new JComboBox();
        JTextArea textAreaN1 = new JTextArea("500");
        JTextArea textAreaN2 = new JTextArea("500");

        public MainPanel() {
            this.setLayout(new BorderLayout());
            panelForLabels.setLayout(new BoxLayout(panelForLabels, BoxLayout.Y_AXIS));
            setOpaques();
            setLabels();
            Buttons();
            Controls();
            ComboBoxOptions();
            SliderOptions();
            panelLeft.setLayout(new BorderLayout());
            panelLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            //panelLeft.setSize(panelLeft.getWidth(), 600);
            GridBagConstraints p = new GridBagConstraints();
            {
                panelNorthWest.setLayout(new BoxLayout(panelNorthWest, BoxLayout.Y_AXIS));
                panelLeft.add(panelNorthWest, BorderLayout.NORTH);
                panelForLabels.setAlignmentX(LEFT_ALIGNMENT);
                panelNorthWest.add(panelForLabels);
                panelNorthWest.add(Box.createRigidArea(new Dimension(0, 50)));
                panelForButtons.setAlignmentX(LEFT_ALIGNMENT);
                panelNorthWest.add(panelForButtons);
                panelForControls.setAlignmentX(LEFT_ALIGNMENT);
                panelNorthWest.add(Box.createRigidArea(new Dimension(0, 20)));
                panelNorthWest.add(panelForControls);
                panelForTable.setLayout(new GridLayout(1, 2));
                panelForTable.setAlignmentX(LEFT_ALIGNMENT);
                panelForTable.add(list1);
                panelForTable.add(list2);
                panelNorthWest.add(Box.createRigidArea(new Dimension(0, 15)));
                panelNorthWest.add(panelForTable);
            }
            add(panelLeft, BorderLayout.WEST);
        }

        public void setLabels() {
            panelForLabels.add(runTimeLabel);
            panelForLabels.add(updateTimeLabel);
            panelForLabels.add(capitalHousesLabel);
            panelForLabels.add(woodHousesLabel);
            panelForLabels.add(totalHousesLabel);
        }

        private void setOpaques() {
            setOpaque(false);
            // panelForTable.setOpaque(false);
            panelForButtons.setOpaque(false);
            panelForControls.setOpaque(false);
            panelLeft.setOpaque(false);
            panelNorthWest.setOpaque(false);
        }

        private void setFocus() {
            stopButton.setFocusable(false);
            startButton.setFocusable(false);
            confirmButton.setFocusable(false);
            hideButton.setFocusable(false);
            slider.setFocusable(false);
            list1.setFocusable(false);
            list2.setFocusable(false);
            isGetInfo.setFocusable(false);

            // comboBox.setFocusable(false);
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
            hideButton.addActionListener(e -> ShowOrHideTime());
            confirmButton.addActionListener(e -> {
                habit.setP1(slider.getValue());
                try {
                    habit.setP2(Integer.parseInt((String) comboBox.getSelectedItem()));
                    habit.setN1(Integer.parseInt(textAreaN1.getText()));
                    habit.setN2(Integer.parseInt(textAreaN2.getText()));
                    System.out.println("Success");
                } catch (NumberFormatException ex) {
                    dialogError(ex);
                }
            });
        }

        private void SliderOptions() {
            slider.setName("P1");
            slider.setMajorTickSpacing(20);
            slider.setMinorTickSpacing(5);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
        }

        private void Buttons() {
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

            c.gridy = 2;
            c.gridwidth = 2;
            c.gridx = 0;
            panelForButtons.add(isGetInfo, c);

            ButtonsListeners();
        }

        private void Controls() {
            panelForControls.setLayout(new BoxLayout(panelForControls, BoxLayout.Y_AXIS));
            panelForControls.add(new JLabel("P1 (%)"));
            panelForControls.add(Box.createRigidArea(new Dimension(0, 5)));
            panelForControls.add(slider);
            panelForControls.add(Box.createRigidArea(new Dimension(0, 5)));
            panelForControls.add(new JLabel("P2 (%)"));
            panelForControls.add(Box.createRigidArea(new Dimension(0, 5)));
            panelForControls.add(comboBox);
            panelForControls.add(Box.createRigidArea(new Dimension(0, 5)));
            panelForControls.add(new JLabel("N1 (мс)"));
            textAreaN1.setBorder(BorderFactory.createLineBorder(Color.black,1));
            panelForControls.add(textAreaN1);
            panelForControls.add(Box.createRigidArea(new Dimension(0, 5)));
            panelForControls.add(new JLabel("N2 (мс)"));
            textAreaN2.setBorder(BorderFactory.createLineBorder(Color.black,1));
            panelForControls.add(textAreaN2);
        }

        private void ComboBoxOptions() {
            for (int i = 0; i <= 100; i += 5)
                comboBox.addItem(String.format("%d", i));
            comboBox.setEditable(true);
            comboBox.setSelectedItem("50");
            comboBox.setMaximumSize(new Dimension(500, 20));
        }

        public void addLabel(JLabel label) {
            panelForLabels.add(label);
        }

    }

    public void updateLabels(double frameTime) {
        mainPanel.runTimeLabel.setText(format("Время с начала работы %.2f", habit.getWORK_TIME()));
        mainPanel.updateTimeLabel.setText(format("Время обновления %.2f", frameTime));
        mainPanel.woodHousesLabel.setText(format("Количество деревянных домов: %d", WoodenHouse.getCount()));
        mainPanel.capitalHousesLabel.setText(format("Количество капитальных домов: %d", CapitalHouse.getCount()));
        mainPanel.totalHousesLabel.setText(format("Всего домов: %d", WoodenHouse.getCount() + CapitalHouse.getCount()));
    }

    private void dialogError(Exception ex) {
        JDialog dialog = new JDialog();
        dialog.setSize(new Dimension(400, 200));
        dialog.setLayout(new BorderLayout());
        JLabel error = new JLabel("ERROR: " + ex.getMessage());
        JPanel Top = new JPanel();
        JButton OKdialogButton = new JButton("OK");
        Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
        Top.add(Box.createRigidArea(new Dimension(0, 50)));
        Top.add(error);
        Top.add(Box.createRigidArea(new Dimension(0, 50)));
        OKdialogButton.setAlignmentX(0.5f);
        Top.add(OKdialogButton);
        OKdialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dialog.dispose();
            }
        });
        error.setAlignmentX(0.5f);
        dialog.add(Top, BorderLayout.NORTH);
        dialog.add(new JPanel(), BorderLayout.WEST);
        dialog.setVisible(true);
        System.out.println(ex);
    }

    public Window(Habitat habitat) {
        super("Laba 1");
        this.setLayout(new BorderLayout());
        habit = habitat;
        addWindow();
        mainPanel = new MainPanel();
        add(mainPanel);
        menuInit();
        Listeners();

        paintAll(getGraphics());
    }

    private void menuInit() {
        itemRun.setActionCommand(itemRun.getName());
        itemRun.setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_DOWN_MASK));
        itemRun.addActionListener(actionEvent -> StartSimulation());
        runMenu.add(itemRun);
        runMenu.addSeparator();

        itemStop.setActionCommand(itemStop.getName());
        itemStop.addActionListener(actionEvent -> EndSimulation());
        runMenu.add(itemStop);

        itemHideOrShow.addActionListener(actionEvent -> ShowOrHide());
        controlsMenu.add(itemHideOrShow);

        menuBar.add(runMenu);
        menuBar.add(controlsMenu);
        this.setJMenuBar(menuBar);
    }

    private void STOPSimulation() {
        mainPanel.stopButton.setEnabled(false);
        mainPanel.startButton.setEnabled(true);
        itemRun.setEnabled(true);
        itemStop.setEnabled(false);
        habit.EndSimulation();
    }

    private void EndSimulation() {
        if (!habit.isRun()) return;
        if (mainPanel.isGetInfo.isSelected()) {
            JDialog dialog = new JDialog();
            dialog.setSize(new Dimension(400, 200));
            dialog.setLayout(new BorderLayout());
            JLabel message = new JLabel("Остановить симуляцию?");
            JTextArea info = new JTextArea();
            info.setText(format("Время работы симуляции: %2f\n" +
                            "Количество деревянных домов: %d\n" +
                            "Количество капитальных домов: %d\n" +
                            "Всего домов: %d", habit.getWORK_TIME(), WoodenHouse.getCount(),
                    CapitalHouse.getCount(), WoodenHouse.getCount() + CapitalHouse.getCount()));
            info.setEditable(false);
            JPanel Top = new JPanel();
            JPanel panelForButtons = new JPanel();
            JButton OKdialogButton = new JButton("OK");
            JButton CanceldialogButton = new JButton("Отмена");
            CanceldialogButton.addActionListener(e -> {
                dialog.dispose();
                return;
            });
            OKdialogButton.addActionListener(e -> {
                STOPSimulation();
                dialog.dispose();
                return;
            });
            panelForButtons.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 0;
            c.gridx = 0;
            panelForButtons.add(OKdialogButton, c);

            c.gridy = 0;
            c.gridx = 1;
            panelForButtons.add(CanceldialogButton, c);

            Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
            Top.add(Box.createRigidArea(new Dimension(0, 10)));
            Top.add(info);
            message.setAlignmentX(0.5f);
            Top.add(message);
            Top.add(Box.createRigidArea(new Dimension(0, 20)));
            Top.add(panelForButtons);

            dialog.add(Top);
            dialog.setVisible(true);
        } else STOPSimulation();
    }

    private void StartSimulation() {
        if (habit.isRun()) return;
        mainPanel.startButton.setEnabled(false);
        mainPanel.stopButton.setEnabled(true);
        itemRun.setEnabled(false);
        itemStop.setEnabled(true);
        habit.StartSimulation();
    }

    public void ShowOrHide() {
        if (mainPanel.panelLeft.isVisible()) {
            mainPanel.panelLeft.setVisible(false);
        } else {
            mainPanel.panelLeft.setVisible(true);
        }
    }

    public void ShowOrHideTime() {
        if (mainPanel.runTimeLabel.isVisible()) {
            mainPanel.runTimeLabel.setVisible(false);
        } else {
            mainPanel.runTimeLabel.setVisible(true);
        }
    }

    public void addWindow() {
        this.setSize(new Dimension(winWidth, winHeight));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(300, 300));
        this.setVisible(true);
    }

    private void Listeners() {
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
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!mainPanel.panelLeft.contains(e.getPoint()))
                    Window.this.requestFocus();
            }
        });
    }

    public void Resized() {
        mainPanel.setSize(getWidth(), getHeight() - 40 - menuBar.getHeight());
    }
}
