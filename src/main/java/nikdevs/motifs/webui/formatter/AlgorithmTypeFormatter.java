package nikdevs.motifs.webui.formatter;

import nikdevs.motifs.webui.model.AlgorithmType;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Formatter ENUM AlgorithmType
 */
@Component
public class AlgorithmTypeFormatter implements Formatter<AlgorithmType> {

    @Override
    public AlgorithmType parse(String s, Locale locale) throws ParseException {
        for (AlgorithmType at : AlgorithmType.values()) {
            if (at.toString().equals(s))
                return at;
        }
        return null;
    }

    @Override
    public String print(AlgorithmType algorithmType, Locale locale) {
        return algorithmType.toString();
    }
}
