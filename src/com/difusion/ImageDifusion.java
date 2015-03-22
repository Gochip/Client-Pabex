package com.difusion;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Parisi Germán
 * @version
 */
public class ImageDifusion extends Diffusion implements Serializable {

    private byte image[];
    
    public ImageDifusion(String idGroup) {
        super(idGroup);
    }

    public void setImage(String url) {
        try {
            File file = new File(url);
            BufferedImage buffer = ImageIO.read(file);
            ByteArrayOutputStream imageByte = new ByteArrayOutputStream();
            ImageIO.write(buffer, getExtension(url), imageByte);
            image = imageByte.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(ImageDifusion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getExtension(String url) {
        int ind = url.lastIndexOf('.');
        return url.substring(ind + 1, url.length());
    }

    public File getImage(String url) {
        File file = new File(url);
        try {
            if (file.exists()) {
                return null;
            }
            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayInputStream imageByte = new ByteArrayInputStream(image);
            BufferedImage im = ImageIO.read(imageByte);
            ImageIO.write(im, getExtension(url), fos);
        } catch (IOException ex) {
            Logger.getLogger(ImageDifusion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

//    public static void main(String args[]) {
//        ImageDifusion id = new ImageDifusion("000");
//        id.setImage("C:\\c.jpg");
//        id.getImage("c1.jpg");
//    }


}
