# Infraestructura local

Los compose incluidos son una topologia local reducida para desarrollo. En AWS se deben separar por EC2 y ampliar a los nodos definidos por la pauta.

- RabbitMQ: `docker compose -f mq-compose.yml up -d`
- Kafka/Zookeeper: `docker compose -f kafka-compose.yml up -d`
- Microservicios y BFF: `docker compose -f apps-compose.yml up --build`

El BFF queda disponible en `http://localhost:8080`. El frontend debe usar `VITE_API_URL=http://localhost:8080`.
