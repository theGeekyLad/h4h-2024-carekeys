import pymysql
import random
from pymysql import *
from werkzeug.utils import secure_filename
from boto3 import *
from os import environ
import json
from Crypto.Cipher import AES
import base64


USERNAME = 'Rahul'
ENCRYPT_KEY = ''
MYSQL_HOST = ''
MYSQL_USER = ''
MYSQL_PSD = ''
MYSQL_DB = ''
SCHEMA = 'care_keys'
TABLE_ANGEL = 'angel_watch'

def connect_mysql():
    db = pymysql.connect(host = MYSQL_HOST, user= MYSQL_USER, password = MYSQL_PSD, database = MYSQL_DB)
    return db

def decrypt_message(encrypted_array,key):
    #DECRYPT MESSAGE
    decrypted_array = []
    aes_key = key.encode('utf-8')[:16]
    cipher = AES.new(aes_key, AES.MODE_ECB)
    for encrypted_str in encrypted_array:
        encrypted_data = base64.b64decode(encrypted_str)
        decrypted = cipher.decrypt(encrypted_data)
        decrypted_array.append(decrypted.rstrip(b"\0").decode('utf-8'))
    return decrypted_array

def send_llm(key_logger):
    #faizan
    time = ''
    category = ''
    summary = ''
    return time,category,summary

def insert_query_data(time,category,summary):
    #functions to create insert query
    query = ("INSERT INTO {0}.{1} (id, user_name,category, summary, message_recieieved_timestamp , updated_timestamp) \
            VALUES ('{2}','{3}','{4}','{5}',NOW());"
             .format(SCHEMA,TABLE_ANGEL,USERNAME, category,summary,time))
    return set_upsert_mysql(query)

def set_upsert_mysql(query):
    #function to insert and update to RDS
    try:
        print('Initiating set_upsert_rds function')
        db = connect_mysql()
        cursor = db.cursor()
        cursor.execute(query)
    except Exception as e:
        message = "Error running query:{0} Generated error: {1}".format(query,str(e))
        print(message)
        return False
    else:
        db.commit()
        db.close()
        message = "Query: {0} ran successfully!".format(query)
    print(message)
    return True 

def send_text(text):
    message_array = text.get('message_array')
    decrypted_message_array = decrypt_message(message_array,ENCRYPT_KEY)
    #sending message to llm
    time,category,summary = send_llm(decrypted_message_array)
    insert_query_data(time,category,summary)
    return

def send_db_response():

    return

def retrieve_emotion():
    return

def get_info_mysql(query):
    try:
        db = connect_mysql()
        print('Initiating get_info_rds function')
        data = {}
        cursor = db.cursor()
        cursor.execute(query)
        data = cursor.fetchall()
        print("data {}".format(str(data)))
    except Exception as e:
        message = "Error running query:{0} Generated error: {1}".format(query,str(e))
    else:
        db.close()
        message = "Query: {0} ran successfully!".format(query)
    print(message)
    return data

def retrieve_summary():
    query = ("""select {1}.* from {0}.{1} SELECT * FROM your_table_name where USERNAME = '{2}'
                    ORDER BY updated_timestamp DESC
                    LIMIT 5;
                """.format(SCHEMA,TABLE_ANGEL,USERNAME))
    return get_info_mysql(query)