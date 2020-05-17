package nikdevs.motifs.webui.service.impl;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import io.github.mnyudina.util.CreateKoefMass;
import nikdevs.motifs.webui.repository.ImageRepository;
import nikdevs.motifs.webui.service.FileHandler;
import nikdevs.motifs.webui.service.GraphImgCreator;
import nikdevs.motifs.webui.service.ImageOverlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Реализация GraphImgCreator
 * Генератор изображений графов
 * Логика взята из десктопного приложения
 */
@Service
public class GraphImgCreatorImpl implements GraphImgCreator {

    private static final String EXT = ".png";

    private static final String ARROW_3_ID_0_1 = "01";
    private static final String ARROW_3_ID_1_0 = "10";
    private static final String ARROW_3_ID_1_2 = "12";
    private static final String ARROW_3_ID_2_1 = "21";
    private static final String ARROW_3_ID_0_2 = "02";
    private static final String ARROW_3_ID_2_0 = "20";

    private static final String ARROW_4_ID_0_1 = "01";
    private static final String ARROW_4_ID_0_2 = "02";
    private static final String ARROW_4_ID_0_3 = "03";
    private static final String ARROW_4_ID_1_0 = "10";
    private static final String ARROW_4_ID_1_2 = "12";
    private static final String ARROW_4_ID_1_3 = "13";
    private static final String ARROW_4_ID_2_0 = "20";
    private static final String ARROW_4_ID_2_1 = "21";
    private static final String ARROW_4_ID_2_3 = "23";
    private static final String ARROW_4_ID_3_0 = "30";
    private static final String ARROW_4_ID_3_1 = "31";
    private static final String ARROW_4_ID_3_2 = "32";

    private ResourceLoader resourceLoader;
    private ImageOverlay imageOverlay;
    private ImageRepository imageRepository;
    private FileHandler fileHandler;

    @Autowired
    public GraphImgCreatorImpl(ResourceLoader resourceLoader, ImageOverlay imageOverlay,
                               ImageRepository imageRepository, FileHandler fileHandler) {
        this.resourceLoader = resourceLoader;
        this.imageOverlay = imageOverlay;
        this.imageRepository = imageRepository;
        this.fileHandler = fileHandler;
    }

    public String[] create3IdImages() throws IOException {
        String[] pictures = new String[16];
        Integer v1 = 1;
        Integer v2 = 2;
        Integer v3 = 3;
        for (int i = 0; i < 4; i++) {
            for (int ii = 0; ii < 4; ii++) {
                for (int iii = 0; iii < 4; iii++) {
                    Graph<Integer, Integer> graph = new SparseGraph<>();
                    graph.addVertex(v1);
                    graph.addVertex(v2);
                    graph.addVertex(v3);
                    if (i == 1)
                        graph.addEdge(1, v1, v2, EdgeType.DIRECTED);
                    else if (i == 2)
                        graph.addEdge(1, v2, v1, EdgeType.DIRECTED);
                    else if (i == 3)
                        graph.addEdge(1, v1, v2, EdgeType.UNDIRECTED);

                    if (ii == 1)
                        graph.addEdge(2, v1, v3, EdgeType.DIRECTED);
                    else if (ii == 2)
                        graph.addEdge(2, v3, v1, EdgeType.DIRECTED);
                    else if (ii == 3)
                        graph.addEdge(2, v1, v3, EdgeType.UNDIRECTED);

                    if (iii == 1)
                        graph.addEdge(3, v3, v2, EdgeType.DIRECTED);
                    else if (iii == 2)
                        graph.addEdge(3, v2, v3, EdgeType.DIRECTED);
                    else if (iii == 3)
                        graph.addEdge(3, v3, v2, EdgeType.UNDIRECTED);

                    Integer[] vert = new Integer[4];
                    vert[0] = v1;
                    vert[1] = v2;
                    vert[2] = v3;
                    int code = 0;
                    ArrayList<Pair<Integer>> pairsList = new ArrayList<>();

                    for (int k = 0; k < vert.length - 1; k++) {
                        for (int j = k + 1; j < vert.length; j++) {
                            Integer o1 = graph.findEdge(vert[k], vert[j]);
                            if (o1 != null) {
                                code |= CreateKoefMass.igraph_i_isoclass_3_idx[3 * k + j];
                                pairsList.add(new Pair<>(k, j));
                            }
                            Integer o2 = graph.findEdge(vert[j], vert[k]);
                            if (o2 != null) {
                                code |= CreateKoefMass.igraph_i_isoclass_3_idx[3 * j + k];
                                pairsList.add(new Pair<>(j, k));
                            }
                        }
                    }
                    int iso = CreateKoefMass.igraph_i_isoclass2_3[code];
                    if (pictures[iso] == null) {
                        pictures[iso] = create3IdPicture(pairsList);
                    }
                }
            }
        }

        return pictures;
    }

    public String[] create4IdImages() throws IOException {
        String[] pictures = new String[218];
        Integer v1 = 1;
        Integer v2 = 2;
        Integer v3 = 3;
        Integer v4 = 4;

        for (int i = 0; i < 4; i++) {
            for (int ii = 0; ii < 4; ii++) {
                for (int iii = 0; iii < 4; iii++) {
                    for (int iiii = 0; iiii < 4; iiii++) {
                        for (int iiiii = 0; iiiii < 4; iiiii++) {
                            for (int iiiiii = 0; iiiiii < 4; iiiiii++) {
                                Graph<Integer, Integer> graph = new SparseGraph<>();
                                graph.addVertex(v1);
                                graph.addVertex(v2);
                                graph.addVertex(v3);
                                graph.addVertex(v4);
                                if (i == 1)
                                    graph.addEdge(1, v1, v2, EdgeType.DIRECTED);
                                else if (i == 2)
                                    graph.addEdge(1, v2, v1, EdgeType.DIRECTED);
                                else if (i == 3)
                                    graph.addEdge(1, v1, v2, EdgeType.UNDIRECTED);
                                if (ii == 1)
                                    graph.addEdge(2, v1, v3, EdgeType.DIRECTED);
                                else if (ii == 2)
                                    graph.addEdge(2, v3, v1, EdgeType.DIRECTED);
                                else if (ii == 3)
                                    graph.addEdge(2, v1, v3, EdgeType.UNDIRECTED);
                                if (iii == 1)
                                    graph.addEdge(3, v3, v4, EdgeType.DIRECTED);
                                else if (iii == 2)
                                    graph.addEdge(3, v4, v3, EdgeType.DIRECTED);
                                else if (iii == 3)
                                    graph.addEdge(3, v3, v4, EdgeType.UNDIRECTED);
                                if (iiii == 1)
                                    graph.addEdge(4, v4, v2, EdgeType.DIRECTED);
                                else if (iiii == 2)
                                    graph.addEdge(4, v2, v4, EdgeType.DIRECTED);
                                else if (iiii == 3)
                                    graph.addEdge(4, v4, v2, EdgeType.UNDIRECTED);
                                if (iiiii == 1)
                                    graph.addEdge(5, v3, v2, EdgeType.DIRECTED);
                                else if (iiiii == 2)
                                    graph.addEdge(5, v2, v3, EdgeType.DIRECTED);
                                else if (iiiii == 3)
                                    graph.addEdge(5, v3, v2, EdgeType.UNDIRECTED);
                                if (iiiiii == 1)
                                    graph.addEdge(6, v1, v4, EdgeType.DIRECTED);
                                else if (iiiiii == 2)
                                    graph.addEdge(6, v4, v1, EdgeType.DIRECTED);
                                else if (iiiiii == 3)
                                    graph.addEdge(6, v1, v4, EdgeType.UNDIRECTED);

                                Integer[] vert = new Integer[4];
                                vert[0] = v1;
                                vert[1] = v2;
                                vert[2] = v3;
                                vert[3] = v4;
                                int code = 0;
                                ArrayList<Pair<Integer>> pairsList = new ArrayList<>();

                                for (int k = 0; k < vert.length - 1; k++) {
                                    for (int j = k + 1; j < vert.length; j++) {
                                        Integer o1 = graph.findEdge(vert[k], vert[j]);
                                        if (o1 != null) {
                                            code |= CreateKoefMass.arr_idx[4 * k + j];
                                            pairsList.add(new Pair<>(k, j));
                                        }
                                        Integer o2 = graph.findEdge(vert[j], vert[k]);
                                        if (o2 != null) {
                                            code |= CreateKoefMass.arr_idx[4 * j + k];
                                            pairsList.add(new Pair<>(j, k));

                                        }
                                    }
                                }
                                int iso = CreateKoefMass.arrcode[code];
                                if (pictures[iso] == null) {
                                    pictures[iso] = create4IdPicture(pairsList);
                                }
                            }
                        }
                    }
                }
            }
        }

        return pictures;
    }

    private String create3IdPicture(ArrayList<Pair<Integer>> pairsList) throws IOException {
        String path = "classpath:static/arrows/3Id/";
        StringBuilder imgName = new StringBuilder("3Id_");

        List<File> arrows = new ArrayList<>();
        for (Pair<Integer> pair : pairsList) {
            int i = pair.getFirst();
            int j = pair.getSecond();

            if (i == 0 && j == 1) {
                arrows.add(resourceLoader.getResource(path + ARROW_3_ID_0_1 + EXT).getFile());
                imgName.append(ARROW_3_ID_0_1);
            } else if (i == 1 && j == 0) {
                arrows.add(resourceLoader.getResource(path + ARROW_3_ID_1_0 + EXT).getFile());
                imgName.append(ARROW_3_ID_1_0);
            } else if (i == 1 && j == 2) {
                arrows.add(resourceLoader.getResource(path + ARROW_3_ID_1_2 + EXT).getFile());
                imgName.append(ARROW_3_ID_1_2);
            } else if (i == 2 && j == 1) {
                arrows.add(resourceLoader.getResource(path + ARROW_3_ID_2_1 + EXT).getFile());
                imgName.append(ARROW_3_ID_2_1);
            } else if (i == 0 && j == 2) {
                arrows.add(resourceLoader.getResource(path + ARROW_3_ID_0_2 + EXT).getFile());
                imgName.append(ARROW_3_ID_0_2);
            } else if (i == 2 && j == 0) {
                arrows.add(resourceLoader.getResource(path + ARROW_3_ID_2_0 + EXT).getFile());
                imgName.append(ARROW_3_ID_2_0);
            }
        }

        //Получаем имя изображения
        String imgNameStr = imgName.toString() + EXT;
        //Если в репозитории такого изображения нету, то создаем его и помещаем в репозиторий
        if (!imageRepository.contains(imgNameStr)) {
            File imgFile = fileHandler.saveImg(imageOverlay.overlay(arrows), ImageRepository.IMG_DIR + imgNameStr);
            imageRepository.addImg(imgFile);
        }

        return imgNameStr;
    }

    private String create4IdPicture(ArrayList<Pair<Integer>> pairsList) throws IOException {
        String path = "classpath:static/arrows/4Id/";
        StringBuilder imgName = new StringBuilder("4Id_");

        List<File> arrows = new ArrayList<>();
        for (Pair<Integer> pair : pairsList) {
            int i = pair.getFirst();
            int j = pair.getSecond();

            if (i == 0 && j == 1) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_0_1 + EXT).getFile());
                imgName.append(ARROW_4_ID_0_1);
            } else if (i == 0 && j == 2) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_0_2 + EXT).getFile());
                imgName.append(ARROW_4_ID_0_2);
            } else if (i == 0 && j == 3) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_0_3 + EXT).getFile());
                imgName.append(ARROW_4_ID_0_3);
            } else if (i == 1 && j == 0) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_1_0 + EXT).getFile());
                imgName.append(ARROW_4_ID_1_0);
            } else if (i == 1 && j == 2) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_1_2 + EXT).getFile());
                imgName.append(ARROW_4_ID_1_2);
            } else if (i == 1 && j == 3) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_1_3 + EXT).getFile());
                imgName.append(ARROW_4_ID_1_3);
            } else if (i == 2 && j == 0) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_2_0 + EXT).getFile());
                imgName.append(ARROW_4_ID_2_0);
            } else if (i == 2 && j == 1) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_2_1 + EXT).getFile());
                imgName.append(ARROW_4_ID_2_1);
            } else if (i == 2 && j == 3) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_2_3 + EXT).getFile());
                imgName.append(ARROW_4_ID_2_3);
            } else if (i == 3 && j == 0) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_3_0 + EXT).getFile());
                imgName.append(ARROW_4_ID_3_0);
            } else if (i == 3 && j == 1) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_3_1 + EXT).getFile());
                imgName.append(ARROW_4_ID_3_1);
            } else if (i == 3 && j == 2) {
                arrows.add(resourceLoader.getResource(path + ARROW_4_ID_3_2 + EXT).getFile());
                imgName.append(ARROW_4_ID_3_2);
            }
        }

        //Получаем имя изображения
        String imgNameStr = imgName.toString() + EXT;
        //Если в репозитории такого изображения нету, то создаем его и помещаем в репозиторий
        if (!imageRepository.contains(imgNameStr)) {
            File imgFile = fileHandler.saveImg(imageOverlay.overlay(arrows), ImageRepository.IMG_DIR + imgNameStr);
            imageRepository.addImg(imgFile);
        }

        return imgNameStr;
    }
}
