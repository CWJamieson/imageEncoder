import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Chris on 8/21/2016.
 */
public class imageCoder {
    BufferedImage img = null;
    public imageCoder(String add)
    {
        try{
            img = ImageIO.read(new File(add));

        }
        catch(Exception e)
        {

        }
    }

    public void setText(String text)
    {
        write(text);
    }
    private void write(String text)
    {
        int maxX, maxY, pix;
        int red, blue, green;
        int place=0, count=3;
        char let;
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
        outerloop:
        for(int y=0;y<maxY;y++)
        {
            for(int x=0;x<maxX;x++)
            {
                pix = img.getRGB(x,y);
                blue = (pix & 0xff);
                green = (pix & 0xff00) >> 8;
                red = (pix & 0xff0000) >> 16;
                blue=blue -(blue%4);
                red=red -(red%4);
                green=green -(green%4);
                let = text.charAt(place);

                red = (red)+((let>>(count*2))%4);
                if(count--<0) {
                    count = 3;
                    place++;
                    if(place>=text.length()) {
                        img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));
                        break outerloop;
                    }
                    let = text.charAt(place);
                }

                green = (green)+((let>>(count*2))%4);
                if(count--<0) {
                    count = 3;
                    place++;
                    if(place>=text.length()) {
                        img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));
                        break outerloop;
                    }
                    let = text.charAt(place);
                }

                blue = (blue)+((let>>(count*2))%4);
                if(count--<0) {
                    count = 3;
                    place++;
                    if(place>=text.length()) {
                        img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));
                        break outerloop;
                    }

                }
                img.setRGB(x,y,((red<<16)+(green<<8)+(blue)));


            }
        }
    }

    public String getText()
    {
        return read();
    }
    private String read()
    {
        String text = "";

        int maxX, maxY, pix;
        int red, blue, green;
        int count=3;
        char let=0;
        maxX = img.getWidth();
        maxY = img.getHeight();
        outerloop:
        for(int y=0;y<maxY;y++)
        {
            for (int x = 0; x < maxX; x++)
            {
                pix = img.getRGB(x,y);
                blue = (pix & 0xff);
                green = (pix & 0xff00) >> 8;
                red = (pix & 0xff0000) >> 16;



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
    }
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
}
