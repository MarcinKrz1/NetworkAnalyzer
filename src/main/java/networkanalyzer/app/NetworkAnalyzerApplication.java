package networkanalyzer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * klasa odpowiadajaca za start aplikacji  na porcie 8080
 *
 */


@SpringBootApplication(scanBasePackages = {"networkanalyzer.rest"})
public class NetworkAnalyzerApplication {

    /**
     * metoda odpowiadająca za start aplikacji
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(NetworkAnalyzerApplication.class, args);
    }
}