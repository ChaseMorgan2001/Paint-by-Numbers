import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

public class MyImageObj extends JLabel {
    private BufferedImage bim = null, og = null;

    private Color[] colors = {
            Color.black,
            Color.white,
            Color.gray,
            Color.lightGray,
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.cyan,
            Color.DARK_GRAY,
            Color.magenta,
            Color.pink
    };

    //private ArrayList<Color> colors = new ArrayList<Color>();

    MyImageObj(){

    }

    public void setImage(BufferedImage img){
        if(img == null){
            return;
        }
        bim = img;

        og = new BufferedImage(bim.getWidth(),bim.getHeight(),bim.getType());
        Graphics2D g2d = (Graphics2D) og.getGraphics();
        g2d.drawImage(bim,0,0,bim.getWidth(), bim.getHeight(), null);
        g2d.dispose();

        this.repaint();
    }

    public BufferedImage getImage(){
        return bim;
    }

    public void findColorSpace(){

        for(int y = 0; y < bim.getHeight(); y++){
            for(int x = 0; x < bim.getWidth(); x++){
                boolean insert = true;
                int pixel = bim.getRGB(x,y);
                Color clr = new Color(pixel,true);
                int red = clr.getRed(), blue = clr.getBlue(), green = clr.getGreen();
                for( int n = 0; n < colors.length;n++){
                    if(colors[n] == clr){
                        insert = false;
                    }
                }
                if(insert){
                    //colors.add(clr);
                }
            }
        }
    }

    public void blurImage(){
        float[] matrix = {
                0.111f, 0.111f, 0.111f,
                0.111f, 0.111f, 0.111f,
                0.111f, 0.111f, 0.111f
        };
        float[] gauss = {

                0.024879f,	0.107973f,	0.024879f,
                0.107973f,	0.468592f,	0.107973f,
                0.024879f,	0.107973f,	0.024879f
        };
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,gauss));
        BufferedImage temp = new BufferedImage(bim.getWidth(), bim.getHeight(), bim.getType());
        op.filter(bim,temp);
        bim = temp;

        repaint();
    }

    public void detectEdges(){
        float[] edge = {
                0f, -2f, 0f,
                -2f, 8f, -2f,
                0f, -2f, 0f
        };
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,edge));
        BufferedImage temp = new BufferedImage(bim.getWidth(), bim.getHeight(), bim.getType());
        op.filter(bim,temp);
        bim = temp;

        repaint();
    }

    public void thresholdImage(int i){
        //findColorSpace();
        int colorIndex = 0;
        for(int y = 0; y < bim.getHeight(); y++){
            for(int x = 0; x < bim.getWidth(); x++){
                double min = 99999;
                int pixel = bim.getRGB(x,y);
                Color clr = new Color(pixel,true);
                int red = clr.getRed(), blue = clr.getBlue(), green = clr.getGreen();
                for( int n = 0; n < Math.min(i, colors.length);n++){
                    int j = (colors[n].getRed() - red), k = (colors[n].getBlue() - blue), m = (colors[n].getGreen() - green);
                    double distance = Math.sqrt((j*j)+(k*k)+(m*m));
                    if(distance < min){
                        min = distance;
                        colorIndex = n;
                    }
                }
                bim.setRGB(x,y,colors[colorIndex].getRGB());
                //repaint();
            }
        }
        //System.out.println("Done");
        repaint();
    }

    public void invertImage() {
        for (int x = 0; x < bim.getWidth(); x++) {
            for (int y = 0; y < bim.getHeight(); y++) {
                int rgba = bim.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                bim.setRGB(x, y, col.getRGB());
            }
        }
    }
    public void reset(){
        setImage(og);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D big = (Graphics2D) g;
        g.clearRect(0,0,getWidth(),getHeight());

        big.drawImage(bim,0,0,this);
    }
}

class colorFreq {
    Color c;
    int count = 0;
    colorFreq(Color clr){
        c = clr;
        count++;
    }
    public void incrCount(){
        count++;
    }
}
