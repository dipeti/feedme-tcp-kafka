package dinya.peter.feedme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

@SpringBootApplication
@EnableAsync
public class FeedmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedmeApplication.class, args);
    }

}
