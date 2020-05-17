package nikdevs.motifs.webui.model;

import org.springframework.stereotype.Component;

/**
 * Входящие параметры для расчета алгоритма
 */
@Component
public class ProcessParameters {

    private int motifSize;
    private int numSample;
    private AlgorithmType algorithmType;
    private boolean useParallelism;
    private int exchangesPerEdges;
    private int exchangesAttempts;
    private int numNet;

    public ProcessParameters() {
        clear();
    }

    public void clear() {
        this.motifSize = 4;
        this.numSample = 100000;
        this.algorithmType = AlgorithmType.FRAMES;
        this.useParallelism = false;
        this.exchangesPerEdges = 3;
        this.exchangesAttempts = 3;
        this.numNet = 1;
    }

    public void copy(ProcessParameters parameters) {
        this.motifSize = parameters.getMotifSize();
        this.numSample = parameters.getNumSample();
        this.algorithmType = parameters.getAlgorithmType();
        this.useParallelism = parameters.isUseParallelism();
        this.exchangesPerEdges = parameters.getExchangesPerEdges();
        this.exchangesAttempts = parameters.getExchangesAttempts();
        this.numNet = parameters.getNumNet();
    }

    public int getMotifSize() {
        return motifSize;
    }

    public void setMotifSize(int motifSize) {
        this.motifSize = motifSize;
    }

    public int getNumSample() {
        return numSample;
    }

    public void setNumSample(int numSample) {
        this.numSample = numSample;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    public boolean isUseParallelism() {
        return useParallelism;
    }

    public void setUseParallelism(boolean useParallelism) {
        this.useParallelism = useParallelism;
    }

    public int getExchangesPerEdges() {
        return exchangesPerEdges;
    }

    public void setExchangesPerEdges(int exchangesPerEdges) {
        this.exchangesPerEdges = exchangesPerEdges;
    }

    public int getExchangesAttempts() {
        return exchangesAttempts;
    }

    public void setExchangesAttempts(int exchangesAttempts) {
        this.exchangesAttempts = exchangesAttempts;
    }

    public int getNumNet() {
        return numNet;
    }

    public void setNumNet(int numNet) {
        this.numNet = numNet;
    }
}
