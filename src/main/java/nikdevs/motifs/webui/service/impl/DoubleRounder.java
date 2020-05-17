package nikdevs.motifs.webui.service.impl;

import nikdevs.motifs.webui.service.Rounder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Реализация Rounder
 */
@Service
public class DoubleRounder implements Rounder {

    /**
     * Округляет val до scale знаков после запятой
     */
    public double round(double val, int scale) {
        return new BigDecimal(val).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }
}
