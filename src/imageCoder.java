import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Chris on 8/21/2016.
 */
public class imageCoder {
    //creates the global bufferedImage
    BufferedImage img = null;

    //constructor
    public imageCoder(String add)
    {
        //tries to read image file
        try{
            img = ImageIO.read(new File(add));

        }
        catch(Exception e)
        {

        }
    }

    //access method and raw method to write text to an image
    public void setText(String text)
    {
        write(text);
    }
    private void write(String text)
    {
        //declare variables
        int maxX, maxY, pix;
        int red, blue, green;
        int place=0, count=3;
        char let;

        //get image dimensions
        maxX = img.getWidth();
        maxY = img.getHeight();

        //cleans image
        clean();

        //nested loops to write up to every pixel
        outerloop:
        for(int y=0;y<maxY;y++)
        {
            for(int x=0;x<maxX;x++)
            {
                //get pixel data
                pix = img.getRGB(x,y);
                blue = (pix & 0xff);
                green = (pix & 0xff00) >> 8;
                red = (pix & 0xff0000) >> 16;

                //wipe last 2 bits
                blue=blue -(blue%4);
                red=red -(red%4);
                green=green -(green%4);

                //get current letter to print
                let = text.charAt(place);

                //print bits to red
                red = (red)+((let>>(count*2))%4);
                if(count--<0) {
                    count = 3;
                    place++;
                    //if complete
                    if(place>=text.length()) {
                        img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));
                        break outerloop;
                    }
                    let = text.charAt(place);
                }

                //print bits to green
                green = (green)+((let>>(count*2))%4);
                if(count--<0) {
                    count = 3;
                    place++;
                    //if complete
                    if(place>=text.length()) {
                        img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));
                        break outerloop;
                    }
                    let = text.charAt(place);
                }


                //print bits to blue
                blue = (blue)+((let>>(count*2))%4);
                if(count--<0) {
                    count = 3;
                    place++;
                    //if complete
                    if(place>=text.length()) {
                        img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));
                        break outerloop;
                    }

                }
                //write pixel to image
                img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));


            }
        }
    }//end of write


    //access and raw methods to get text from an image
    public String getText()
    {
        return read();
    }
    private String read()
    {
        //declare variables
        String text = "";
        int maxX, maxY, pix;
        int red, blue, green;
        int count=3;
        char let=0;

        //get image dimensions
        maxX = img.getWidth();
        maxY = img.getHeight();

        //nested reading loops
        outerloop:
        for(int y=0;y<maxY;y++)
        {
            for (int x = 0; x < maxX; x++)
            {
                //get pixel data
                pix = img.getRGB(x,y);
                blue = (pix & 0xff);
                green = (pix & 0xff00) >> 8;
                red = (pix & 0xff0000) >> 16;


                //extract from red
                red = red%4;
                let+=red<<(count*2);
                if(count--<0)
                {
                    if(let==0)
                        break outerloop;
                    text = text+let;
                    let=0;
                    count=3;
                }

                //extract from green
                green = green%4;
                let+= green<<(count*2);
                if(count--<0)
                {
                    if(let==0)
                        break outerloop;
                    text = text+let;
                    let=0;
                    count=3;
                }

                //extract from blue
                blue = blue%4;
                let+= blue<<(count*2);
                if(count--<0)
                {
                    if(let==0)
                        break outerloop;
                    text = text+let;
                    let=0;
                    count=3;
                }

            }
        }


        return text;
    }//end of read

    //sets image to pure red
    public void redify() {
        int maxX, maxY;
        maxX = img.getWidth();
        maxY = img.getHeight();
        outerloop:
        for (int y = 0; y < maxY; y++)
        {
            for (int x = 0; x < maxX; x++)
            {
                img.setRGB(x, y, 255 << 16);
            }
        }
    }

    //erases last 2 bit of image
    public void clean(){
        int maxX, maxY, pix;
        int red, blue, green;
        maxX = img.getWidth();
        maxY = img.getHeight();
        for(int y=0;y<maxY;y++) {
            for (int x = 0; x < maxX; x++) {
                pix = img.getRGB(x,y);
                blue = (pix & 0xff);
                green = (pix & 0xff00) >> 8;
                red = (pix & 0xff0000) >> 16;
                blue=blue -(blue%4);
                red=red -(red%4);
                green=green -(green%4);
                img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));
            }
        }
    }
}//end of imageCoder
