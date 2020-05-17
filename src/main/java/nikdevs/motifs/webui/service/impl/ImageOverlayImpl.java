package nikdevs.motifs.webui.service.impl;

import nikdevs.motifs.webui.service.ImageOverlay;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Реализация ImageOverlay
 */
@Service
public class ImageOverlayImpl implements ImageOverlay {

    /**
     * Создает одно изображение путем склеивания нескольких
     */
    public BufferedImage overlay(List<File> images) throws IOException {
        BufferedImage pic = new BufferedImage(62, 60, BufferedImage.TYPE_INT_ARGB);
        for (File img : images) {
            pic.getGraphics().drawImage(ImageIO.read(img), 0, 0, null);
        }

        return pic;
    }
}
