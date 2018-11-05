package networkanalyzer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"networkanalyzer.rest"})
public class NetworkAnalyzerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NetworkAnalyzerApplication.class, args);
    }

}
