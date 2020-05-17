package nikdevs.motifs.webui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Contacts page controller
 */
@Controller
public class ContactsController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/contacts")
    public String contactsPage() {
        logger.info("Get contacts page");
        return "contacts";
    }
}
