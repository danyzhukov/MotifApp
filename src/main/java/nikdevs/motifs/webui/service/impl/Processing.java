package nikdevs.motifs.webui.service.impl;

import edu.uci.ics.jung.io.PajekNetReader;
import io.github.mnyudina.subgraph.*;
import io.github.mnyudina.util.FormatUtils;
import io.github.mnyudina.util.MySparseGraph;
import nikdevs.motifs.webui.model.AlgorithmType;
import nikdevs.motifs.webui.model.ProcessParameters;
import nikdevs.motifs.webui.model.ProcessResult;
import nikdevs.motifs.webui.service.AlgorithmProcessing;
import org.apache.commons.collections15.Factory;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Основной алгоритм взят из десктопного приложения
 */
@Service
public class Processing implements AlgorithmProcessing {

    private int numNet;
    private int numSamples;
    private int exchangesPerEdges;
    private int exchangesAttempts;
    private int size;
    private AlgorithmType algorithmType;
    private boolean useParallelism;

    private Double[] N_G;
    private Double[] SKO_G;
    private Double[][] N_GR;
    private Double[] Z_SCORE;
    private Double[] R;
    private Double[] R_;
    private Double[] COUNT_G;

    private StringBuilder out;

    @Override
    public void process(File file, ProcessParameters param, ProcessResult result) {
        resetParam();
        setParam(param);

        if (file == null || !file.exists()) {
            out.append("FIle is empty");
            writeResult(result, false);
            return;
        }

        MySparseGraph graph;
        try {
            graph = (MySparseGraph) new PajekNetReader<>(createIntegerFactory(), createIntegerFactory())
                    .load(file.getAbsolutePath(), new MySparseGraph<>());
        } catch (IOException e) {
            out.append("Problem while opening file");
            writeResult(result, false);
            return;
        }

        execAlgorithm(graph, file.getName());

        writeResult(result, true);
    }

    private void execAlgorithm(MySparseGraph graph, String fileName) {
        long tStart = System.nanoTime();

        out.append("Network name: ").append(fileName).append("\n");
        out.append(graph).append("\n");
        if (algorithmType.equals(AlgorithmType.FRAMES)) {
            out.append("\nAlgorithm: sampling");
            out.append("\nSampling parameter: ").append(numSamples);
        } else {
            out.append("\nAlgorithm: enumeration");

        }
        out.append("\nSubgraph Size: ").append(size).append("\n");

        if (numNet > 0) {
            out.append("\nGenerated: ").append(numNet).append(" random networks");
            out.append("\n   with locally constant number of bidirectional edges,");
            out.append("\n   ").append(exchangesPerEdges).append(" exchanges per edge and ").append(exchangesAttempts).append(" tries per edge.");
        } else
            out.append("\nNo random graphs were generated");

        AbstractExecutor activeCalc;
        if (algorithmType.equals(AlgorithmType.FRAMES)) {
            if (size == 3) {
                if (useParallelism)
                    activeCalc = new RandSF3p(graph, numSamples);
                else
                    activeCalc = new RandSF3(graph, numSamples);

            } else {
                if (useParallelism)
                    activeCalc = new RandSF4p(graph, numSamples);
                else
                    activeCalc = new RandSF4(graph, numSamples);
            }
        } else {
            if (size == 3) {
                if (useParallelism)
                    activeCalc = new EnumFrames3p(graph);
                else
                    activeCalc = new EnumFrames3(graph);

            } else {
                if (useParallelism)
                    activeCalc = new EnumFrames4p(graph);
                else
                    activeCalc = new EnumFrames4(graph);

            }

        }
        long t = System.nanoTime();
        activeCalc.execute();
        COUNT_G = activeCalc.counts;

        out.append("\n\nCalculating in the original network took: ")
                .append(FormatUtils.durationToHMS(System.nanoTime() - t)).append("\n");

        N_G = activeCalc.motifs;
        SKO_G = activeCalc.sigmas;

        for (int j = 0; j < activeCalc.motifs.length; j++) {
            if (N_G[j] != null) {
                R_[j] = 0.;
                R[j] = 0.;
            } else {
                N_G[j] = 0.;
                SKO_G[j] = 0.;
            }

        }

        double[] countR;
        if (size == 3)
            countR = new double[16];
        else
            countR = new double[218];

        for (int i = 0; i < numNet; i++) {
            t = System.nanoTime();

            MySparseGraph g_i = new MySparseGraph(graph);
            g_i.getRandomized(exchangesPerEdges, exchangesAttempts);

            out.append("\nRandomization ").append(i).append(" took: ")
                    .append(FormatUtils.durationToHMS(System.nanoTime() - t));

            if (algorithmType.equals(AlgorithmType.FRAMES)) {

                if (size == 3)
                    activeCalc = new RandSF3(g_i, numSamples);
                else
                    activeCalc = new RandSF4<>(g_i, numSamples);

            } else {

                if (size == 3)
                    activeCalc = new EnumFrames3(g_i);
                else
                    activeCalc = new EnumFrames4p<>(g_i);
            }
            activeCalc.execute();

            for (int j = 0; j < activeCalc.motifs.length; j++) {
                if (N_G[j] > 0.1) {

                    Double x;
                    if (activeCalc.motifs[j] != null)
                        x = activeCalc.motifs[j];
                    else
                        x = 0.;
                    N_GR[i][j] = x;

                }
                if (N_G[j] > 0.1 && N_GR[i][j] > 0.1) {
                    countR[j] = countR[j] + 1.;
                    R_[j] = R_[j] + (N_G[j] - 3. * SKO_G[j]) / (N_GR[i][j] + 3. * activeCalc.sigmas[j]);
                    R[j] = R[j] + (N_G[j]) / (N_GR[i][j]);

                }
            }
        }
        out.append("\nTotal time of a calculation: ")
                .append(FormatUtils.durationToHMS(System.nanoTime() - tStart));

        for (int j = 0; j < R.length; j++) {
            if (N_G[j] > 0.1 && countR[j] > 0) {
                R[j] = R[j] / countR[j];
                R_[j] = R_[j] / countR[j];

            } else {
                R[j] = Double.MIN_VALUE;
                R_[j] = Double.MIN_VALUE;

            }
        }
        for (int j = 0; j < N_G.length; j++) {
            double mx = 0.;
            double sx = 0.;

            for (Double[] doubles : N_GR) {
                if (N_G[j] > 0.1) {

                    mx = mx + doubles[j] / N_GR.length;
                    sx = sx + doubles[j] * (doubles[j] / (double) N_GR.length);
                }
            }
            if (mx > 0) {
                Z_SCORE[j] = (N_G[j] - mx) / Math.sqrt((sx - mx * mx));
            } else
                Z_SCORE[j] = -1. / 0.;
        }
    }

    private void resetParam() {
        this.numNet = 1;
        this.numSamples = 100000;
        this.exchangesPerEdges = 3;
        this.exchangesAttempts = 3;
        this.size = 4;
        this.algorithmType = AlgorithmType.FRAMES;
        this.useParallelism = false;

        N_GR = null;
        N_G = null;
        SKO_G = null;
        R = null;
        R_ = null;
        Z_SCORE = null;
        COUNT_G = null;

        out = new StringBuilder();
    }

    private void setParam(ProcessParameters param) {
        this.numNet = param.getNumNet();
        this.numSamples = param.getNumSample();
        this.exchangesPerEdges = param.getExchangesPerEdges();
        this.exchangesAttempts = param.getExchangesAttempts();
        this.size = param.getMotifSize();
        this.algorithmType = param.getAlgorithmType();
        this.useParallelism = param.isUseParallelism();

        if (size == 4) {
            N_GR = new Double[numNet][218];
            N_G = new Double[218];
            SKO_G = new Double[218];
            R = new Double[218];
            R_ = new Double[218];
            Z_SCORE = new Double[218];
            COUNT_G = new Double[218];
        } else if (size == 3) {
            N_GR = new Double[numNet][16];
            N_G = new Double[16];
            SKO_G = new Double[16];
            R = new Double[16];
            R_ = new Double[16];
            Z_SCORE = new Double[16];
            COUNT_G = new Double[16];
        }
    }

    private void writeResult(ProcessResult result, boolean success) {
        if (success) {
            result.setN_GR(N_GR);
            result.setN_G(N_G);
            result.setSKO_G(SKO_G);
            result.setR(R);
            result.setR_(R_);
            result.setZ_SCORE(Z_SCORE);
            result.setCOUNT_G(COUNT_G);
            result.setProcessComplete(true);
        }

        result.setResultText(out.toString());
    }

    private Factory<Integer> createIntegerFactory() {
        return new Factory<Integer>() {
            private int n = 0;

            @Override
            public Integer create() {
                return n++;
            }
        };
    }
}
