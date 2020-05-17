package nikdevs.motifs.webui.service;

import nikdevs.motifs.webui.model.ProcessParameters;
import nikdevs.motifs.webui.model.ProcessResult;

import java.io.File;

/**
 * Интерфейс для выполнения алгоритма
 */
public interface AlgorithmProcessing {

    void process(File file, ProcessParameters param, ProcessResult result);
}
