package nikdevs.motifs.webui.repository.impl;

import nikdevs.motifs.webui.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реализация ImageRepository
 */
@Service
public class ImageRepositoryImpl implements ImageRepository {

    private Map<String, File> images;

    public ImageRepositoryImpl() {
        images = new ConcurrentHashMap<>();

        //При запуске приложения ищем существующие изображения
        searchImgFromDir();
    }

    /**
     * Поиск изображений в дирректории для изображений
     */
    private void searchImgFromDir() {
        File imgDir = new File(IMG_DIR);
        if (!imgDir.exists())
            imgDir.mkdir();

        File[] imagesList = imgDir.listFiles();
        if (imagesList != null) {
            for (File file : imagesList) {
                images.put(file.getName(), file);
            }
        }
    }

    public void addImg(File img) {
        images.put(img.getName(), img);
    }

    public File getImg(String name) {
        if (images.containsKey(name))
            return images.get(name);

        return null;
    }

    public boolean contains(String name) {
        return images.containsKey(name);
    }
}
