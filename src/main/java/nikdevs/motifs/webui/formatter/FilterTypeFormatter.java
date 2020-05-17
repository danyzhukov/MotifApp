package nikdevs.motifs.webui.formatter;

import nikdevs.motifs.webui.model.FilterType;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Formatter ENUM FilterType
 */
@Component
public class FilterTypeFormatter implements Formatter<FilterType> {

    @Override
    public FilterType parse(String s, Locale locale) throws ParseException {
        for (FilterType at : FilterType.values()) {
            if (at.toString().equals(s))
                return at;
        }
        return null;
    }

    @Override
    public String print(FilterType filterType, Locale locale) {
        return filterType.toString();
    }
}
