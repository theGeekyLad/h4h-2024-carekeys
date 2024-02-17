import boto3, botocore
import pymysql
import random
from pymysql import *
from werkzeug.utils import secure_filename
from boto3 import *
from os import environ
import json

AWS_BUCKET_NAME= 'one-id-resource'



def send_text_llm(text):
    message = user_data.get('text')
    print(message)
    """
    Faizan
    """
    return

def retrieve_emotion():
    return

def retrieve_summary():
    return