package nikdevs.motifs.webui.controller;

import nikdevs.motifs.webui.model.AlgorithmType;
import nikdevs.motifs.webui.model.ProcessParameters;
import nikdevs.motifs.webui.model.ProcessResult;
import nikdevs.motifs.webui.service.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Index page controller
 */
@Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private AlgorithmProcessing processing;
    private ProcessParameters parameters;
    private ProcessResult result;
    private FileHandler fileHandler;

    @Autowired
    public MainController(AlgorithmProcessing processing, ProcessParameters parameters, ProcessResult result, FileHandler fileHandler) {
        this.processing = processing;
        this.parameters = parameters;
        this.result = result;
        this.fileHandler = fileHandler;
    }

    /**
     * Возвращает страницу index.html по запросу на "/"
     */
    @GetMapping("/")
    public String indexPage(Model model, HttpServletRequest request) {
        logger.info("Get index page");
        model.addAttribute("indexForm", parameters);
        model.addAttribute("algorithmTypes", AlgorithmType.values());
        model.addAttribute("algorithmTypeFrames", AlgorithmType.FRAMES);
        model.addAttribute("result", result);
        //Сохраняем результат алгоритма в сессию
        request.getSession().setAttribute("result", result);
        return "index";
    }

    /**
     * Сохранение результата алгоритма в файл csv по кнопке Save
     */
    @GetMapping(value = "/saveResult", produces = "application/csv")
    public void sendResultToClient(HttpServletResponse response) {
        logger.info("Start save result");

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"result.csv\"");

        try {
            File file = fileHandler.saveResultToCsv(result);

            OutputStream outputStream = response.getOutputStream();

            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, outputStream);

            fileInputStream.close();
            outputStream.close();

            fileHandler.deleteFile(file.getParentFile());
        } catch (IOException e) {
            logger.info("File load to client error: {}", e.getMessage());
        }

        logger.info("End save result");
    }

    /**
     * Запуск алгоритма по кнопке Start
     */
    @PostMapping(value = "/", params = "start")
    public String startProcess(ProcessParameters param,
                             @RequestParam(value = "useParallelism", required = false) boolean useParallelism,
                             StandardMultipartHttpServletRequest request) {
        logger.info("Start motif analyse");

        param.setUseParallelism(useParallelism);
        parameters.copy(param);
        result.clear();

        File file = saveMultipartFileToServer(request, "fileNet");

        if (file != null && file.exists()) {
            processing.process(file, parameters, result);
            deleteFileFromServer(file.getParentFile());
        }

        logger.info("End motif analyse");

        return "redirect:/";
    }

    /**
     * Получение результата алгоритма из файла по кнопке Load
     */
    @PostMapping(value = "/", params = "loadResult")
    public String loadResultFromClient(StandardMultipartHttpServletRequest request) {
        logger.info("Start load result from client");

        result.clear();

        File file = saveMultipartFileToServer(request, "fileLoad");

        if (file != null && file.exists()) {
            try {
                fileHandler.loadResultFromCsv(file, result);
                logger.error("Read result data from file successful");
            } catch (IOException e) {
                result.setResultText("File read error");
                logger.error("Read result data from file error: {}", e.getMessage());
            }
            deleteFileFromServer(file.getParentFile());
        }

        logger.info("End load result from client");

        return "redirect:/";
    }

    /**
     * Сохранение полученного от клиента файла на сервер
     */
    private File saveMultipartFileToServer(StandardMultipartHttpServletRequest request, String elementName) {
        File file = null;
        if (!request.getFileMap().isEmpty()) {
            MultipartFile multipartFile = request.getFileMap().get(elementName);
            if (!multipartFile.isEmpty()) {
                try {
                    file = fileHandler.saveMultipartFile(multipartFile);
                    logger.info("File save on server successful to {}", file.getAbsolutePath());
                } catch (IOException e) {
                    logger.error("Save file on server error: {}", e.getMessage());
                }
            }
        }

        return file;
    }

    /**
     * Удаление файла на сервере
     */
    private void deleteFileFromServer(File file) {
        try {
            fileHandler.deleteFile(file);
            logger.info("Delete file from server successful");
        } catch (IOException e) {
            logger.error("Delete file from server error: {}", e.getMessage());
        }
    }
}
