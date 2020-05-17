package nikdevs.motifs.webui.model;

/**
 * Строка результата алгоритма
 */
public class ResultRow {

    private String picture;
    private double N_G;
    private double SKO_G;
    private double R;
    private double R_;
    private String Z_SCORE;
    private double COUNT_G;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getN_G() {
        return N_G;
    }

    public void setN_G(double n_G) {
        N_G = n_G;
    }

    public double getSKO_G() {
        return SKO_G;
    }

    public void setSKO_G(double SKO_G) {
        this.SKO_G = SKO_G;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    public double getR_() {
        return R_;
    }

    public void setR_(double r_) {
        R_ = r_;
    }

    public String getZ_SCORE() {
        return Z_SCORE;
    }

    public void setZ_SCORE(String z_SCORE) {
        Z_SCORE = z_SCORE;
    }

    public double getCOUNT_G() {
        return COUNT_G;
    }

    public void setCOUNT_G(double COUNT_G) {
        this.COUNT_G = COUNT_G;
    }
}
