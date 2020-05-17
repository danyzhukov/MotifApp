package nikdevs.motifs.webui.model;

import org.springframework.stereotype.Component;

/**
 * Параметры фильтра результата алгоритма
 */
@Component
public class ResultFilter {

    private FilterType filterType;

    private double Z_SCORE;
    private double R_;
    private int motifs;

    public void clear() {
        filterType = FilterType.MOTIFS;
        Z_SCORE = 2;
        R_ = 1.1;
        motifs = 1;
    }

    public void copy(ResultFilter filter) {
        this.filterType = filter.filterType;
        this.Z_SCORE = filter.Z_SCORE;
        this.R_ = filter.R_;
        this.motifs = filter.motifs;
    }

    public ResultFilter() {
        clear();
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public double getZ_SCORE() {
        return Z_SCORE;
    }

    public void setZ_SCORE(double z_SCORE) {
        Z_SCORE = z_SCORE;
    }

    public double getR_() {
        return R_;
    }

    public void setR_(double r_) {
        R_ = r_;
    }

    public int getMotifs() {
        return motifs;
    }

    public void setMotifs(int motifs) {
        this.motifs = motifs;
    }
}
