package gui;

import engine.Engine;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapFrame extends JFrame {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread steppingThread;

    public MapFrame(Engine engine) {
        super();
        setTitle("Cell-inator: run " + engine.engineName());

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                running.set(false);
                try{
                    steppingThread.interrupt();
                } catch (NullPointerException ignored){

                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                running.set(false);
                try{
                    steppingThread.interrupt();
                } catch (NullPointerException ignored){

                }
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new MapPanel(engine));
        add(new JSeparator());
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
        JButton startButton = new JButton("Start");
        startButton.addActionListener(action->{
            running.set(true);
            steppingThread = new Thread(() -> {
                try{
                        while (running.get()) {
                            Thread.sleep(333);
                            engine.step();
                            repaint();
                        }
                } catch (InterruptedException ignored){
                    // do nothing
                } finally {
                    running.set(false);
                }
            });
            steppingThread.setDaemon(true);
            steppingThread.start();
        });
        buttons.add(startButton);
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(action->{
            running.set(false);
            try {
                steppingThread.interrupt();
            } catch (NullPointerException ignored){

            }
        });
        buttons.add(stopButton);
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(action->{
            engine.step();
            repaint();
        });
        buttons.add(stepButton);
        buttons.add(new JButton("Save"));
        add(buttons);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}
