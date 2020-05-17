package nikdevs.motifs.webui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Tutorial page controller
 */
@Controller
public class TutorialController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/tutorial")
    public String tutorialPage() {
        logger.info("Get tutorial page");
        return "tutorial";
    }
}
