package nikdevs.motifs.webui.controller;

import nikdevs.motifs.webui.repository.ImageRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Image controller
 */
@Controller
public class ImageController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Возвращает картинку из репозитория по имени картинки
     */
    @GetMapping("/pictures/{name}")
    public void getPicture(@PathVariable("name") String name, HttpServletResponse response) {
        File picture = imageRepository.getImg(name);
        try {
            OutputStream outputStream = response.getOutputStream();

            FileInputStream fileInputStream = new FileInputStream(picture);

            IOUtils.copy(fileInputStream, outputStream);

            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            logger.error("Error getting picture: {}", e.getMessage());
        }
    }
}
