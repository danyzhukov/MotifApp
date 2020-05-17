package nikdevs.motifs.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MfsWebUiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MfsWebUiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MfsWebUiApplication.class);
    }
}

//@SpringBootApplication
//public class MfsWebUiApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(MfsWebUiApplication.class, args);
//    }
//
//}
