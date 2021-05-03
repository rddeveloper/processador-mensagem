# Processador de Mensagens

Projeto criado utilizando o java e o apache kafka, cujo o objetivo é simular um cosumer de processamento de mensagens.

# Tecnologias

Java 11\
Apache Kafka\
Jackson Databind

# Execução

## Docker-compose

Primeiramente execute o arquivo docker compose na sua máquina e garanta que o zookeeper e o kafka estejam sendo executados, dando o comando 

```docker
sudo docker-compose up
```
o arquivo se encontra no diretorio /docker.

para verificar se as instâncias dos containers fora executados e estão levantadas, execute o comando

```docker
sudo docker ps -a
```

## Criação do Tópico

Execute o comando para criação do tópico na sua máquina

```docker
sudo docker-compose exec kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic mensagem-envio
```

Confirme se o tópico foi criado com sucesso

```docker
sudo docker-compose exec kafka kafka-topics.sh --bootstrap-server localhost:9092 --list
```
## JAVA
Com o kafka rodando na sua máquina, basta executar a classe com o método principal da classe ProcessadorMensagemService dentro do package service para que a instancia e as configurações do kafka sejam criadas e consumidas, e o consumer de mensagem seja executado.

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

## Dicas Extras
primeiro execute e rode o projeto [envio-mensagem](https://github.com/rddeveloper/envio-mensagem), assim garantido que os dados estejam sendo produzidos, podendo serem consumidos pela aplicação de processamento de mensagens.
Caso queira dar um stop nos containers rodando do docker na sua máquina, execute:

```docker
sudo docker stop $(sudo docker ps -aq)
```
