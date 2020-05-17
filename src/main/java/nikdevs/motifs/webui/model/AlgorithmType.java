package nikdevs.motifs.webui.model;

/**
 * Типы расчета алгоритма
 */
public enum AlgorithmType {

    FULL("Full Enumeration"), FRAMES("Sampling Frames");

    private String name;

    AlgorithmType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
