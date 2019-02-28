package dinya.peter.feedme;

import dinya.peter.feedme.messaging.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
public class FeedmeApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(FeedmeApplication.class, args);
    }

    @Autowired
    private MessageProcessor messageProcessor;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        messageProcessor.startMessageProcessing();
    }
}
