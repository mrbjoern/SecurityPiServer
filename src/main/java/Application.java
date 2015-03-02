import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mrbjoern on 3/2/15.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
}
