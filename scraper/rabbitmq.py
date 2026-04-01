import pika

connection = pika.BlockingConnection(
    pika.ConnectionParameters('localhost')
)

channel = connection.channel()

# Queue
channel.queue_declare(queue='jobs', durable=True)