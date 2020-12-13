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
    private Color bg = new Color(178, 213, 224);
    private BufferedImage image;
    private MyImageObj pbnImage = new MyImageObj(), usrImg = new MyImageObj();

    public canvas() {
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

    public void imageDisplay() {
        GridLayout gl = new GridLayout(1, 2);
        gl.setHgap(200);
        imageBoard.setBackground(bg);
        imageBoard.setSize((int) screenSize.getWidth(), usrImg.getHeight());
        imageBoard.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        imageBoard.setLayout(gl);
        // add images to panel here

        imageBoard.add(usrImg);
        imageBoard.add(pbnImage);
    }

    public void setControls() {
        GridLayout gl = new GridLayout(2, 1);
        gl.setVgap(15);
        controls.setLayout(gl);
        controls.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));
        controls.setSize((int) screenSize.getWidth(), 50);
        controls.setBackground(bg);
        JSlider numValues = new JSlider();
        numValues.setMinimum(2);
        numValues.setMaximum(24);
        numValues.setValue(13);
        numValues.setPaintLabels(true);
        numValues.setPaintTicks(true);
        numValues.setMajorTickSpacing(1);
        numValues.setBackground(bg);
        controls.add(numValues);
        JButton start = new JButton("Paint by Numbers");
        JButton blur = new JButton("Blur");
        JButton sharpen = new JButton("Sharpen");
        JButton grayScale = new JButton("Grayscale");
        JButton detectEdges = new JButton("Edge Detection");
        JButton posterize = new JButton("Posterize");
        JButton threshold = new JButton("Threshold");
        JButton randomThreshold = new JButton("Random Threshold");
        JButton invertColors = new JButton("Invert Colors");
        JButton reset = new JButton("Reset");
        JButton export = new JButton("Export");

        JPanel buttons = new JPanel();
        buttons.setBackground(bg);
        buttons.add(start);
        buttons.add(blur);
        buttons.add(sharpen);
        buttons.add(grayScale);
        buttons.add(detectEdges);
        buttons.add(posterize);
        buttons.add(threshold);
        buttons.add(randomThreshold);
        buttons.add(invertColors);
        buttons.add(reset);
        buttons.add(export);
        controls.add(buttons);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pbnImage.reset();
                pbnImage.setColors(numValues.getValue());
                pbnImage.thresholdImage(numValues.getValue());
                //pbnImage.blurImage();
                pbnImage.detectEdges();
                pbnImage.grayscaleImage();
                pbnImage.invertImage();
                pbnImage.setRenderColors();
                //pbnImage.defineLines();
                //pbnImage.sharpen();
                repaint();
            }
        });

        blur.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               pbnImage.blurImage();
               pbnImage.setColors(numValues.getValue());
               pbnImage.setRenderColors();
               repaint();
           }
       });
        sharpen.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pbnImage.sharpen();
              pbnImage.setColors(numValues.getValue());
              pbnImage.setRenderColors();
              repaint();
          }
      });
        grayScale.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pbnImage.grayscaleImage();
            pbnImage.setColors(numValues.getValue());
            pbnImage.setRenderColors();
            repaint();
        }
      });
        detectEdges.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pbnImage.detectEdges();
              pbnImage.setColors(numValues.getValue());
              pbnImage.setRenderColors();
              repaint();
          }
      });
        posterize.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pbnImage.posterize(numValues.getValue());
            pbnImage.setColors(numValues.getValue());
            pbnImage.setRenderColors();
            repaint();
        }
    });
        threshold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pbnImage.setColors(numValues.getValue());
                pbnImage.setRenderColors();
                pbnImage.thresholdImage(numValues.getValue());
                repaint();
            }
        });
        randomThreshold.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              pbnImage.randomThreshHold(numValues.getValue());
              pbnImage.setColors(numValues.getValue());
              pbnImage.setRenderColors();
              repaint();
          }
      });
        invertColors.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           pbnImage.invertImage();
           pbnImage.setColors(numValues.getValue());
           pbnImage.setRenderColors();
           repaint();
       }
   });
        reset.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pbnImage.reset();
            pbnImage.setColors(numValues.getValue());
            pbnImage.setRenderColors();
            repaint();
        }
    });
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pbnImage.export();
            }
        });
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
                            pbnImage.setImage(image);

                            // resize the image
                            if (usrImg.getImage().getHeight() < usrImg.getImage().getWidth()) {
                                //if (usrImg.getImage().getWidth() < (screenSize.getWidth() / 2) - 100 || usrImg.getImage().getWidth() > (screenSize.getWidth() / 2) - 100) {
                                    double ratio = (screenSize.getWidth() / 2 - 100) / usrImg.getImage().getWidth();
                                    BufferedImage resizedImage = new BufferedImage((int) screenSize.getWidth() / 2 - 100, (int) (usrImg.getImage().getHeight() * ratio), usrImg.getImage().getType());
                                    Graphics2D g2d = (Graphics2D) resizedImage.getGraphics();
                                    g2d.drawImage(image, 0, 0, (int) screenSize.getWidth() / 2 - 100, (int) (usrImg.getImage().getHeight() * ratio), null);
                                    g2d.dispose();
                                    usrImg.setImage(resizedImage);

                                    resizedImage = new BufferedImage(usrImg.getImage().getWidth(), usrImg.getImage().getHeight(), usrImg.getImage().getType());
                                    g2d = (Graphics2D) resizedImage.getGraphics();
                                    g2d.drawImage(image, 0, 0, usrImg.getImage().getWidth(), usrImg.getImage().getHeight(), null);
                                    g2d.dispose();

                                    pbnImage.setImage(resizedImage);
                                    pbnImage.setColors(13);
                                    pbnImage.setRenderColors();
                                //}
                            }
                            else {
                                double ratio = (screenSize.getHeight() - 220) / usrImg.getImage().getHeight();
                                BufferedImage resizedImage = new BufferedImage((int) (usrImg.getImage().getWidth() * ratio), (int) (screenSize.getHeight() - 220), usrImg.getImage().getType());
                                Graphics2D g2d = (Graphics2D) resizedImage.getGraphics();
                                g2d.drawImage(image, 0, 0, (int) (usrImg.getImage().getWidth() * ratio), (int) (screenSize.getHeight() - 220), null);
                                g2d.dispose();
                                usrImg.setImage(resizedImage);

                                resizedImage = new BufferedImage(usrImg.getImage().getWidth(), usrImg.getImage().getHeight(), usrImg.getImage().getType());
                                g2d = (Graphics2D) resizedImage.getGraphics();
                                g2d.drawImage(image, 0, 0, usrImg.getImage().getWidth(), usrImg.getImage().getHeight(), null);
                                g2d.dispose();

                                pbnImage.setImage(resizedImage);
                                pbnImage.setColors(13);
                                pbnImage.setRenderColors();
                            }

                            usrImg.repaint();
                            pbnImage.repaint();
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
