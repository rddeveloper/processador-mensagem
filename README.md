# Processador de Mensagens

Projeto criado utilizando o java e o apache kafka, cujo o objetivo é simular um consumer de processamento de mensagens.\
obs:bprimeiro execute e rode o projeto [envio-mensagem](https://github.com/rddeveloper/envio-mensagem), assim garantido que os dados esteja sendo produzidos, podendo ser consumidos pela aplicação de processamento de mensagens através do tópico de mensagem-envio e que os containers do zookeper e o kafka através do docker estejam rodando na sua máquina.

# Tecnologias

Java 11\
Apache Kafka\
Jackson Databind

# Execução
## JAVA
Com o kafka rodando na sua máquina, basta executar a classe com o método principal da classe ProcessadorMensagemService dentro do package service para que a instância e as configurações do kafka sejam criadas e consumidas, e o consumer de mensagem seja executado.

```Java
public static void main(String args []) throws InterruptedException {
  Properties properties = new Properties();
  properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
  properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
  properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MensagemDeserializer.class.getName());
  properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-processamento");
  properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
  ....
}
```
o método esta com o while(true) assim sendo lidas n mensagems com um thread sleep de 200 e simulando o processamento de mensagens em tempo real
```Java
  while(true) {
     ConsumerRecords<String, Mensagem> records = consumer.poll(Duration.ofMillis(200));
     for (ConsumerRecord<String, Mensagem> record: records) {
         Mensagem mensagem = record.value();
         Thread.sleep(200);
         System.out.println(mensagem);
     }
   }
```

