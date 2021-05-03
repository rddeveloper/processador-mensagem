package service;

import deserializer.MensagemDeserializer;
import model.Mensagem;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ProcessadorMensagemService {

    public static void main(String args[]) throws InterruptedException {
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MensagemDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-processamento");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try(KafkaConsumer<String, Mensagem> consumer = new KafkaConsumer<String, Mensagem>(properties)) {
            consumer.subscribe(Arrays.asList("mensagem-envio"));
            while(true) {
               ConsumerRecords<String, Mensagem> records = consumer.poll(Duration.ofMillis(200));
               for (ConsumerRecord<String, Mensagem> record: records) {
                   Mensagem mensagem = record.value();
                   Thread.sleep(200);
                   System.out.println(mensagem);
               }
            }
        }


    }
}
