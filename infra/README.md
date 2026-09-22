# Infraestructura local

Los compose incluidos son una topologia local reducida para desarrollo. En AWS se deben separar por EC2 y ampliar a los nodos definidos por la pauta.

- RabbitMQ: `docker compose -f mq-compose.yml up -d`
- Kafka/Zookeeper: `docker compose -f kafka-compose.yml up -d`
