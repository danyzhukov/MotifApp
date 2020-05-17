package nikdevs.motifs.webui.model;

/**
 * Типы фильтров результата
 */
public enum FilterType {

    Z_SCORE("Z-score bigger then"), R_("R' bigger then"), MOTIFS("Motifs found at least");

    private String name;

    FilterType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
