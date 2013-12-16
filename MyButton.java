
package labb4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyButton extends JButton implements ActionListener {

    private boolean isClicked = false;
    private String clicked;
    private String unClicked;
    private Color col1;
    private Color col2;

    
    public void toggleState() {
        if (isClicked == false) {
            isClicked = true;
            this.setText(clicked);
            this.setBackground(col2);
        } else {
            isClicked = false;
            this.setText(unClicked);
            this.setBackground(col1);
        }
    }

    public boolean getState() {
        return isClicked;
    }

    public MyButton(Color col1, Color col2, String unClicked, String clicked) {
        this.col1 = col1;
        this.col2 = col2;
        this.clicked = clicked;
        this.unClicked = unClicked;
        this.setText(unClicked);
        this.setBackground(col1);
    }

    public MyButton() {
        this(Color.RED, Color.GREEN, "Loggar inte", "Loggar");
    }

    public void actionPerformed(ActionEvent e) {
        toggleState();
    }
}
