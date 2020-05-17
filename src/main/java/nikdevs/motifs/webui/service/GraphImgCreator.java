package nikdevs.motifs.webui.service;

import java.io.IOException;

/**
 * Интерфейс для создания изображений графов
 */
public interface GraphImgCreator {

    String[] create3IdImages() throws IOException;
    String[] create4IdImages() throws IOException;
}
