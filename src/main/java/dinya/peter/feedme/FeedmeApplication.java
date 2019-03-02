package dinya.peter.feedme;

import dinya.peter.feedme.messaging.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeedmeApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(FeedmeApplication.class, args);
    }

    @Autowired
    private MessageProcessor messageProcessor;

    @Override
    public void run(ApplicationArguments args) {
        new Thread(() -> {
            try {
                /* Give time to Kafka to create the topic*/
                Thread.sleep(10000);
                messageProcessor.startMessageProcessing();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
