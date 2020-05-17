package nikdevs.motifs.webui.service.impl;

import nikdevs.motifs.webui.model.ProcessResult;
import nikdevs.motifs.webui.service.FileHandler;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.UUID;

/**
 * Реализация FileHandler
 */
@Service
public class FileHandlerImpl implements FileHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Дирректория для сохранения временных файлов
     * */
    private static final String ROOT = "temp";

    /**
     * Сохраненяет файл на сервере и возвращает на него ссылку
     */
    public File saveMultipartFile(MultipartFile multipartFile) throws IOException {
        File rootDir = createRootDir();
        File file = new File(rootDir.getAbsolutePath() + "/" + multipartFile.getOriginalFilename());

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
        stream.write(multipartFile.getBytes());
        stream.close();

        return file;
    }

    /**
     * Удаление файла с сервера
     */
    public void deleteFile(File file) throws IOException {
        if (file.isDirectory())
            FileUtils.deleteDirectory(file);
        else
            Files.deleteIfExists(file.toPath());
    }

    /**
     * Сохраняет результат алгоритма на сервере и возвращает на него ссылку
     */
    public File saveResultToCsv(ProcessResult result) throws IOException {
        File rootDir = createRootDir();
        File file = new File(rootDir.getAbsolutePath() + "/result.csv");

        FileWriter csv = new FileWriter(file);

        for (String line : result.getResultText().split("\\n"))
            csv.append(line).append("\n");

        csv.append("-----\n");

        for (int index = 0; index < result.getN_G().length; index++) {
            csv.append(result.getN_G()[index].toString()).append(";");
            csv.append(result.getSKO_G()[index].toString()).append(";");
            csv.append(result.getR()[index].toString()).append(";");
            csv.append(result.getR_()[index].toString()).append(";");
            csv.append(result.getZ_SCORE()[index].toString()).append(";");
            csv.append(result.getCOUNT_G()[index].toString());
            if (index != result.getN_G().length - 1) {
                csv.append("\n");
            }
        }
        csv.close();

        return file;
    }

    /**
     * Запуск чтения результата алгоритма из csv файла
     */
    public void loadResultFromCsv(File file, ProcessResult result) throws IOException {
        StringBuilder out = new StringBuilder();

        if (readCsvFile(file, out, result)) {
            result.setProcessComplete(true);
        }
        result.setResultText(out.toString());
    }

    /**
     * Чтение результата алгоритма из csv файла
     */
    private boolean readCsvFile(File file, StringBuilder out, ProcessResult result) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        int n = 0;

        String firstLine = sc.nextLine();
        if (firstLine.startsWith("Network name")) {
            out.append(firstLine).append("\n");
        } else
            return false;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split(": ");
            if (words[0].equals("Subgraph Size")) {
                int x = Integer.parseInt(words[1]);
                if (x == 4)
                    n = 218;
                else
                    n = 16;
            }
            if (line.equals("-----"))
                break;
            out.append(line).append("\n");
        }
        if (n != 218 && n != 16) {
            out.append("Wrong file");
            return false;
        }

        result.setN_G(new Double[n]);
        result.setSKO_G(new Double[n]);
        result.setZ_SCORE(new Double[n]);
        result.setR(new Double[n]);
        result.setR_(new Double[n]);
        result.setZ_SCORE(new Double[n]);
        result.setCOUNT_G(new Double[n]);

        int i = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] str = line.split(";");
            result.getN_G()[i] = Double.parseDouble(str[0]);
            result.getSKO_G()[i] = Double.parseDouble(str[1]);
            result.getR()[i] = Double.parseDouble(str[2]);
            result.getR_()[i] = Double.parseDouble(str[3]);
            result.getZ_SCORE()[i] = Double.parseDouble(str[4]);
            result.getCOUNT_G()[i] = Double.parseDouble(str[5]);
            i++;
        }

        sc.close();

        return true;
    }

    /**
     * Сохраняет image под именем imgName и возвращает на него ссылку
     */
    public synchronized File saveImg(BufferedImage image, String imgName) throws IOException {
        File img = new File(imgName);
        if (!img.getParentFile().exists()) {
            img.getParentFile().mkdirs();
        }

        try {
            ImageIO.write(image,"png",img);
        } catch (IOException e) {
            logger.error("Save image to {} error: {}", img.getAbsolutePath(), e.getMessage());
            throw e;
        }
        return img;
    }

    /**
     * Создает уникальную дирректорию и возвращает на нее ссылку
     */
    private synchronized File createRootDir() {
        File rootDir = new File(ROOT + "/" + UUID.randomUUID());
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }

        return rootDir;
    }
}
