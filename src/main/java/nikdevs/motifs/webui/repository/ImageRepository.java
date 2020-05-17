package nikdevs.motifs.webui.repository;

import java.io.File;

/**
 * Интерфейс для репозитория изображений
 * Содержит ссылки на изображения графов
 */
public interface ImageRepository {

    String IMG_DIR = "images/graphs/"; //Путь для сохранения изображений графов

    void addImg(File img);
    File getImg(String name);
    boolean contains(String name);
}
