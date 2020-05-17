package nikdevs.motifs.webui.model;

import org.springframework.stereotype.Component;

/**
 * Результат расчета алгоритма
 */
@Component
public class ProcessResult {

    private Double[] N_G;
    private Double[] SKO_G;
    private Double[][] N_GR;
    private Double[] Z_SCORE;
    private Double[] R;
    private Double[] R_;
    private Double[] COUNT_G;

    private String resultText;

    private boolean processComplete;

    public ProcessResult() {
        clear();
    }

    public void clear() {
        N_G = null;
        SKO_G = null;
        N_GR = null;
        Z_SCORE = null;
        R = null;
        R_ = null;
        COUNT_G = null;
        resultText = "";
        processComplete = false;
    }

    public void copy(ProcessResult result) {
        this.N_G = result.getN_G();
        this.SKO_G = result.getSKO_G();
        this.N_GR = result.getN_GR();
        this.Z_SCORE = result.getZ_SCORE();
        this.R = result.getR();
        this.R_ = result.getR_();
        this.COUNT_G = result.COUNT_G;
        this.resultText = result.getResultText();
        this.processComplete = result.isProcessComplete();
    }

    public Double[] getN_G() {
        return N_G;
    }

    public void setN_G(Double[] n_G) {
        N_G = n_G;
    }

    public Double[] getSKO_G() {
        return SKO_G;
    }

    public void setSKO_G(Double[] SKO_G) {
        this.SKO_G = SKO_G;
    }

    public Double[][] getN_GR() {
        return N_GR;
    }

    public void setN_GR(Double[][] n_GR) {
        N_GR = n_GR;
    }

    public Double[] getZ_SCORE() {
        return Z_SCORE;
    }

    public void setZ_SCORE(Double[] z_SCORE) {
        Z_SCORE = z_SCORE;
    }

    public Double[] getR() {
        return R;
    }

    public void setR(Double[] r) {
        R = r;
    }

    public Double[] getR_() {
        return R_;
    }

    public void setR_(Double[] r_) {
        R_ = r_;
    }

    public Double[] getCOUNT_G() {
        return COUNT_G;
    }

    public void setCOUNT_G(Double[] COUNT_G) {
        this.COUNT_G = COUNT_G;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public boolean isProcessComplete() {
        return processComplete;
    }

    public void setProcessComplete(boolean processComplete) {
        this.processComplete = processComplete;
    }
}
