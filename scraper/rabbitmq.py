import json
import pika

from config import RABBITMQ_HOST, RABBITMQ_PORT, RABBITMQ_USER, RABBITMQ_PASSWORD, RABBITMQ_QUEUE

def publish(data: dict):
    creds = pika.PlainCredentials(RABBITMQ_USER, RABBITMQ_PASSWORD)

    params = pika.ConnectionParameters(host=RABBITMQ_HOST, port=RABBITMQ_PORT, credentials=creds)
    connection = pika.BlockingConnection(params)
    channel = connection.channel()

    print('RabbitMQ verbunden. Channel geöffnet:', channel.channel_number)

    # Queue
    channel.queue_declare(queue=RABBITMQ_QUEUE, durable=True)

    print(f'Queue "{RABBITMQ_QUEUE}" deklariert')

    channel.basic_publish(
        exchange='',
        routing_key=RABBITMQ_QUEUE,
        body=json.dumps(data),
        properties=pika.BasicProperties(
            delivery_mode=2,
            content_type='application/json',
        )
    )
    connection.close()
    print(f'Gesendet:', data)