package nikdevs.motifs.webui.config;

import nikdevs.motifs.webui.formatter.AlgorithmTypeFormatter;
import nikdevs.motifs.webui.formatter.FilterTypeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration Spring MVC
 * Подключение форматтеров для классов типа ENUM для корректной работы с ними на страницах
 */
public class WebMvcConfig implements WebMvcConfigurer {

    private AlgorithmTypeFormatter algorithmTypeFormatter;
    private FilterTypeFormatter filterTypeFormatter;

    @Autowired
    public WebMvcConfig(AlgorithmTypeFormatter algorithmTypeFormatter, FilterTypeFormatter filterTypeFormatter) {
        this.algorithmTypeFormatter = algorithmTypeFormatter;
        this.filterTypeFormatter = filterTypeFormatter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(algorithmTypeFormatter);
        registry.addFormatter(filterTypeFormatter);
    }
}
