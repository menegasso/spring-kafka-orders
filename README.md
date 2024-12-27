# Sistema de Pedidos com Kafka

Este projeto é uma aplicação Spring Boot que simula um sistema de pedidos, utilizando **Apache Kafka em KRaft Mode** (sem Zookeeper) para o processamento assíncrono de pedidos em tempo real. A aplicação foi construída para demonstrar como o Kafka pode ser utilizado em sistemas distribuídos para enviar, consumir e processar pedidos de forma eficiente e escalável.

## Tecnologias Utilizadas

- **Spring Boot**: Framework Java para criação de aplicações web e APIs.
- **Apache Kafka (KRaft Mode)**: Plataforma de streaming distribuído para enviar, receber e processar dados em tempo real, sem a necessidade do Zookeeper.
- **Gradle**: Gerenciador de dependências e automação de builds.

## Funcionalidades do Sistema de Pedidos

- **Recebimento de Pedidos**: O sistema recebe pedidos através de uma API REST.
- **Produção de Mensagens**: Quando um pedido é recebido, uma mensagem é enviada para o Kafka, simulando o processamento assíncrono do pedido.
- **Processamento de Pedidos**: O consumidor Kafka recebe as mensagens do Kafka e processa os pedidos de forma assíncrona, simulando a execução de tarefas como validação de estoque, cobrança e envio de confirmação ao cliente.
- **Monitoramento de Processamento**: O sistema pode ser monitorado por logs e endpoints para visualizar o estado de processamento dos pedidos.

## Arquitetura do Sistema

1. **API de Recebimento de Pedidos**: 
   - A aplicação oferece um endpoint REST onde novos pedidos são recebidos.
   - Cada pedido gerado é enviado para um tópico Kafka.

2. **Produtor Kafka**: 
   - Um componente que envia mensagens para o Kafka. As mensagens representam pedidos que estão aguardando processamento.

3. **Consumidor Kafka**: 
   - Um componente que consome as mensagens do Kafka e simula o processamento de pedidos, como validação de estoque, cobrança, etc.

4. **Kafka**: 
   - O Kafka é utilizado como o middleware de mensageria para garantir a comunicação assíncrona entre os produtores e consumidores de pedidos.

## Como Executar Localmente

### Pré-requisitos

1. **Apache Kafka em KRaft Mode** deve estar instalado e em execução localmente. Caso não tenha o Kafka instalado, siga os passos abaixo:

   1. Baixe e extraia o [Apache Kafka](https://kafka.apache.org/downloads).
   2. No diretório do Kafka, crie um diretório para os logs do Kafka:

      ```bash
      mkdir -p /tmp/kraft
      ```

   3. Em seguida, inicie o Kafka em KRaft Mode (sem Zookeeper):

      ```bash
      bin/kafka-server-start.sh config/kraft/server.properties
      ```

      **Importante**: O arquivo `server.properties` já está configurado para rodar no KRaft Mode. No KRaft Mode, não é necessário configurar o Zookeeper, e o Kafka gerencia os metadados de forma autônoma.

2. **Java 11 ou superior** deve estar instalado.

3. **Gradle** instalado, caso não tenha, siga a [documentação oficial do Gradle](https://gradle.org/install/).

### Rodando a Aplicação

1. Clone o repositório:

   ```bash
   git clone https://github.com/menegasso/spring-kafka-orders.git
   cd spring-kafka-orders
   ```

2. Configure o `application.properties` para usar o endereço do seu broker Kafka local:

    ```bash
    spring.kafka.bootstrap-servers=localhost:9092
    spring.kafka.consumer.group-id=pedido-processamento
    spring.kafka.consumer.auto-offset-reset=earliest
    spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
    spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
    spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
    spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
    ```

3. Execute a aplicação com Gradle:
    ```bash
    ./gradlew bootRun
    ```

4. A aplicação estará rodando no http://localhost:8080.


### Testando a Aplicação
1. Envio de Pedidos: Use uma ferramenta como o Postman ou cURL para enviar uma requisição POST para o endpoint /orders:

Exemplo de requisição:
    ```
    curl -X POST http://localhost:8080/api/pedidos -H "Content-Type: application/json" -d '{"produto": "Produto XYZ", "quantidade": 10}'
    ```

2. Processamento de Pedidos: Após o envio do pedido, ele será enviado para um tópico Kafka e o consumidor começará a processá-lo, realizando ações como validação de estoque e outras operações simulada.

### Estrutura do Projeto
* src/main/java: Código-fonte da aplicação, incluindo configurações Kafka e lógica de negócios.

    * PedidoProducer: Responsável por enviar mensagens de pedidos para o Kafka.
    * PedidoConsumer: Responsável por consumir mensagens de pedidos e simular o processamento.
    * KafkaConfig: Configurações do Kafka para o produtor e consumidor.
    * PedidoController: Controlador responsável pela API de recebimento de pedidos.
    * src/main/resources: Arquivos de configuração, como o application.properties.

* build.gradle: Arquivo de configuração do Gradle.

### Contribuindo
1. Faça um fork deste repositório.
2. Crie uma branch para a sua feature (git checkout -b minha-feature).
3. Faça as alterações necessárias e envie um pull request.

### Contato
Se você tiver dúvidas ou sugestões, sinta-se à vontade para abrir uma issue no GitHub ou me contactar diretamente.

