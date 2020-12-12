import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class canvas extends JFrame {
    Container c = getContentPane();
    JPanel controls = new JPanel();
    JPanel imageBoard = new JPanel();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Color bg = new Color(178, 213,224);
    BufferedImage usrImg, pbnImg;

    public canvas(){
        setSize(screenSize);
        c.setBackground(bg);
        setVisible(true);
        c.add(controls, BorderLayout.SOUTH);
        c.add(imageBoard, BorderLayout.NORTH);
        setControls();
        imageDisplay(usrImg, pbnImg);
    }
    public void imageDisplay(BufferedImage userImg, BufferedImage pbnImg){
        GridLayout gl = new GridLayout(1,2);
        gl.setHgap(200);
        imageBoard.setBackground(bg);
        imageBoard.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight() - 50);
        imageBoard.setBorder(BorderFactory.createEmptyBorder(15,50,15,50));
        imageBoard.setLayout(gl);

        // add images to panel here
    }
    public void setControls(){
        GridLayout gl = new GridLayout(2, 1);
        gl.setVgap(15);
        controls.setLayout(gl);
        controls.setBorder(BorderFactory.createEmptyBorder(15,50,15,50));
        controls.setSize((int) screenSize.getWidth(), 50);
        controls.setBackground(bg);
        JSlider numValues = new JSlider();
        numValues.setMinimum(2);
        numValues.setMaximum(15);
        numValues.setValue(8);
        numValues.setPaintLabels(true);
        numValues.setPaintTicks(true);
        numValues.setMajorTickSpacing(1);
        numValues.setBackground(bg);
        controls.add(numValues);
        JButton start = new JButton("Start");
        JPanel buttons = new JPanel();
        buttons.setBackground(bg);
        buttons.add(start);
        controls.add(buttons);
    }
    public static void main(String[] args){
        canvas c = new canvas();
        c.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}
