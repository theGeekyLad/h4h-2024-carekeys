import pymysql
import random
from pymysql import *
from werkzeug.utils import secure_filename
from boto3 import *
from os import environ
import json
from Crypto.Cipher import AES
import base64
import openai




def connect_mysql():
    db = pymysql.connect(host = MYSQL_HOST, 
                         user= MYSQL_USER, 
                         password = MYSQL_PSD, 
                         database = MYSQL_DB)
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
    print(key_logger)
    openai.api_key = OPEN_AI_KEY
    try:
        response = openai.ChatCompletion.create(
            model=MODEL_ID,
            messages=[{"role": "system", 
                       "content": "You are a support agent give the output in json format with key type and summary. Type can be one of the following: self_harm, violence, depression or sexual_misconduct_prevention. Identify the most prominent type and then generate a summary focused on the selected type based on the provided text inputs."},
                      {"role": "user",
                        "content": key_logger}],
            temperature=0  # Adjust temperature if needed
        )
        message = json.loads(response.choices[0].message["content"])
    except Exception as e:
        print("ërror:'{0}'".format(str(e)))
        return {'error':str(e)}
    return message

def insert_query_data(response,message_date):
    #functions to create insert query
    query = ("""INSERT INTO {0}.{1} (user, depression_intensity, violence_intensity, self_harm_intensity, sexual_misconduct_intensity, summary, message_timestamp, updated_timestamp) \
         VALUES ("{2}", {3}, {4}, {5}, {6}, "{7}", "{8}", NOW())"""
         .format(SCHEMA, 
                 TABLE_ANGEL, 
                 USERNAME, 
                 response['depression_intensity'].split('/')[0], 
                 response['violence_intensity'].split('/')[0], 
                 response['self_harm_intensity'].split('/')[0], 
                 response['sexual_misconduct_intensity'].split('/')[0], 
                 response['summary'],
                 message_date))
    print(query)
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

def send_text(text_json):
    message_date = text_json.get('date')
    message_array = text_json.get('text')
    print(str(message_array))
    decrypted_message_array = decrypt_message(message_array,ENCRYPT_KEY)
    #sending message to llm
    print(str(decrypted_message_array))
    response = send_llm('.'.join(message_array))
    print(str(response))
    insert_query_data(response,message_date)
    return True

def retrieve_emotion():
    query = ("""select category from {0}.{1}  where USERNAME = '{2}'
                    ORDER BY updated_timestamp DESC
                    LIMIT 1;
                """.format(SCHEMA,TABLE_ANGEL,USERNAME))
    data = get_info_mysql(query)
    return data[0][1]

def retrieve_bar_graph_info():
    query = """
                SELECT 
                    DATE(message_timestamp) AS date,
                    AVG(depression_intensity) AS depression_intensity,
                    AVG(violence_intensity) AS violence_intensity,
                    AVG(self_harm_intensity) AS self_harm_intensity,
                    AVG(sexual_misconduct_intensity) AS sexual_misconduct_intensity
                FROM 
                    {0}.{1}
                WHERE 
                    message_timestamp >= CURDATE() - INTERVAL 25 DAY and user = '{2}'
                GROUP BY 
                    DATE(message_timestamp)
                ORDER BY 
                    date DESC;
            """.format(SCHEMA,TABLE_ANGEL,USERNAME)
    return  get_info_mysql(query)


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
    query = ("""select {1}.* from {0}.{1}  where USERNAME = '{2}'
                    ORDER BY updated_timestamp DESC
                    LIMIT 5;
                """.format(SCHEMA,TABLE_ANGEL,USERNAME))
    return get_info_mysql(query)