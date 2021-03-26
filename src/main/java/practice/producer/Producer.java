package practice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import practice.model.Payment;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "board";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        objectMapper = new ObjectMapper();
    }

    public void sendMessage(Payment payment) throws JsonProcessingException {

        String messageOut = objectMapper.writeValueAsString(payment);

        logger.info(String.format("Publishing payment with paymentId: %s", payment.getPaymentId()));
        this.kafkaTemplate.send(TOPIC, messageOut);
    }
}