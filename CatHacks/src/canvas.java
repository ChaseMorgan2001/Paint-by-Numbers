import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class canvas extends JFrame {
    private Container c = getContentPane();
    private JPanel controls = new JPanel();
    private JPanel imageBoard = new JPanel();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Color bg = new Color(178, 213,224);
    private BufferedImage image;
    private MyImageObj pbnImage = new MyImageObj(), usrImg = new MyImageObj();

    public canvas(){
        super("Paint by number");
        buildMenus();

        setSize(screenSize);
        c.setBackground(bg);
        setVisible(true);
        c.add(controls, BorderLayout.SOUTH);
        c.add(imageBoard, BorderLayout.CENTER);
        setControls();
        imageDisplay();
    }
    public void imageDisplay(){
        GridLayout gl = new GridLayout(1,2);
        gl.setHgap(200);
        imageBoard.setBackground(bg);
        imageBoard.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight() - 50);
        imageBoard.setBorder(BorderFactory.createEmptyBorder(15,50,15,50));
        imageBoard.setLayout(gl);

        // add images to panel here
        imageBoard.add(usrImg);
        imageBoard.add(pbnImage);
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

    public void buildMenus(){
        final JFileChooser fc = new JFileChooser(".");
        JMenuBar bar = new JMenuBar();
        this.setJMenuBar (bar);
        JMenu fileMenu = new JMenu ("File");
        JMenuItem sourceOpen = new JMenuItem ("Source Image");
        JMenuItem fileexit = new JMenuItem ("Exit");

        sourceOpen.addActionListener(
                new ActionListener() {
                    public void actionPerformed (ActionEvent e) {
                        int returnVal = fc.showOpenDialog(canvas.this);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = fc.getSelectedFile();
                            try {
                                image = ImageIO.read(file);
                            } catch (IOException e1){}

                            usrImg.setImage(image);

                            // resize the image

                            usrImg.repaint();
                        }
                    }
                }
        );
        fileexit.addActionListener(
                new ActionListener () {
                    public void actionPerformed (ActionEvent e) {
                        System.exit(0);
                    }
                }
        );

        fileMenu.add(sourceOpen);
        fileMenu.add(fileexit);
        bar.add(fileMenu);
    }
    public static void main(String[] args){
        canvas c = new canvas();
        c.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}
