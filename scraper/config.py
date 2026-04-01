import os
from dotenv import load_dotenv, find_dotenv

load_dotenv(find_dotenv())

# Rabbitmq
RABBITMQ_HOST = os.getenv('RABBITMQ_HOST', 'localhost')
RABBITMQ_PORT = int(os.getenv('RABBITMQ_PORT', '5672'))
RABBITMQ_USER = os.getenv('RABBITMQ_DEFAULT_USER', 'user')
RABBITMQ_PASSWORD = os.getenv('RABBITMQ_DEFAULT_PASS', 'password')
RABBITMQ_QUEUE = os.getenv('RABBITMQ_QUEUE')

# Scraper
USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36"
KEYWORD='applikationsentwickler'
MAX_PAGES = 10
REGIONS = {
    'st.gallen': 17,
    'winterthur': 18,
    'thurgau': 21,
    'schaffhausen': 25
}