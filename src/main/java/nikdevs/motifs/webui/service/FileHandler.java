package nikdevs.motifs.webui.service;

import nikdevs.motifs.webui.model.ProcessResult;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Интерфейс для работы с файлами
 */
public interface FileHandler {

    File saveMultipartFile(MultipartFile multipartFile) throws IOException;
    void deleteFile(File file) throws IOException;
    File saveResultToCsv(ProcessResult result) throws IOException;
    void loadResultFromCsv(File file, ProcessResult result) throws IOException;
    File saveImg(BufferedImage image, String imgName) throws IOException;
}
