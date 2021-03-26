package practice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practice.model.Payment;
import practice.producer.Producer;

import java.util.Random;

@RestController
@RequestMapping(value = "/")
public class KafkaController {

    private final Producer producer;
    private final ObjectMapper objectMapper;
    private final Random random;
    private final String digitSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    public KafkaController(Producer producer) {
        this.producer = producer;
        objectMapper = new ObjectMapper();
        random = new Random();
    }

    @PostMapping(value = "publish")
    public String sendMessageToKafkaTopic(@RequestBody String messageIn) throws JsonProcessingException {

        Payment payment = objectMapper.readValue(messageIn, Payment.class);
        payment.setPaymentId(paymentIdGenerator());

        producer.sendMessage(payment);
        return "published";
    }

    @GetMapping("isOn")
    public String isOn() {
        return "ON";
    }

    private String paymentIdGenerator() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<27;i++){
            if (i==6 || i==13 || i==20){
                sb.append("-");
            }
            else {
                sb.append(digitSet.charAt(random.nextInt(digitSet.length())));
            }
        }
        return sb.toString();
    }


}
