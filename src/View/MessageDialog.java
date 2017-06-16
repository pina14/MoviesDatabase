package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MessageDialog extends JDialog {
    private JPanel rootPanel;
    private JButton buttonOK;
    private JLabel messageLabel;

    //TODO resolve size problem
    public MessageDialog() {
        setContentPane(rootPanel);
        setModalityType(DEFAULT_MODALITY_TYPE);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onCancel() on ESCAPE
        rootPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setPreferredSize(new Dimension(480, 100));
        pack();
    }

    public void setMessage(String message){
        messageLabel.setText(message);
    }

    public void setTimer(int time){
        Timer timer = new Timer(time, e -> {
            setVisible(false);
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void showDialog(){
        this.setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
