package nikdevs.motifs.webui.controller;

import nikdevs.motifs.webui.model.*;
import nikdevs.motifs.webui.service.GraphImgCreator;
import nikdevs.motifs.webui.service.Rounder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Result page controller
 */
@Controller
public class ResultController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Rounder rounder;
    private ResultFilter filter;
    private GraphImgCreator graphImgCreatorImpl;

    @Autowired
    public ResultController(Rounder rounder, ResultFilter filter, GraphImgCreator graphImgCreatorImpl) {
        this.rounder = rounder;
        this.filter = filter;
        this.graphImgCreatorImpl = graphImgCreatorImpl;
    }

    /**
     * Возвращает страницу result.html по запросу на "/result"
     */
    @GetMapping("/result")
    public String resultPage(Model model, HttpServletRequest request) {
        logger.info("Get result page");

        //Получаем результат алгоритма из сессии
        ProcessResult result = (ProcessResult) request.getSession().getAttribute("result");
        //Подготавливает строки результата с учетом параметров фильтра
        List<ResultRow> rows = getResultRows(result);

        model.addAttribute("filter", filter);
        model.addAttribute("rows", rows);
        model.addAttribute("filterTypes", FilterType.values());

        return "result";
    }

    /**
     * Сохраняет параметры фильтра по кнопке Filter
     */
    @PostMapping("/result")
    public String resultFilter(ResultFilter resultFilter) {
        logger.info("Start filter results");

        filter.copy(resultFilter);

        logger.info("End filter result");

        return "redirect:result";
    }

    /**
     * Возвращает список строк результата с учетом фильтра
     */
    private List<ResultRow> getResultRows(ProcessResult result) {
        int size = result.getN_G().length;
        List<ResultRow> rows = new ArrayList<>(size);

        // Получаем массив изображений графов
        String[] graphPicturesName = null;
        try {
            if (size == 16)
                graphPicturesName = graphImgCreatorImpl.create3IdImages();
            else
                graphPicturesName = graphImgCreatorImpl.create4IdImages();
        } catch (IOException e) {
            logger.error("Error with load graph pictures: {}", e.getMessage());
        }

        int roundScale = 3; //До скольки знаков округлять числа для вывода на страницу
        for (int i = 0; i < result.getN_G().length; i++) {
            if ((filter.getFilterType().equals(FilterType.Z_SCORE) && filter.getZ_SCORE() > result.getZ_SCORE()[i]) ||
                    (filter.getFilterType().equals(FilterType.R_) && filter.getR_() > result.getR_()[i]) ||
                    (filter.getFilterType().equals(FilterType.MOTIFS) && filter.getMotifs() > result.getCOUNT_G()[i])) {
                continue;
            }
            ResultRow row = new ResultRow();
            if (graphPicturesName != null && graphPicturesName.length > i)
                row.setPicture(graphPicturesName[i]);
            row.setN_G(rounder.round(result.getN_G()[i], roundScale));
            row.setSKO_G(rounder.round(result.getSKO_G()[i], roundScale));
            row.setR(rounder.round(result.getR()[i], roundScale));
            row.setR_(rounder.round(result.getR_()[i], roundScale));
            row.setZ_SCORE(result.getZ_SCORE()[i] < 0 ? "-" : "");
            row.setCOUNT_G(rounder.round(result.getCOUNT_G()[i], roundScale));
            rows.add(row);
        }

        return rows;
    }
}
