package nikdevs.motifs.webui.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для склеивания изображений
 */
public interface ImageOverlay {

    BufferedImage overlay(List<File> images) throws IOException;
}
