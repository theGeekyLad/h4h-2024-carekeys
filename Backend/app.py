#Developer : Anandu Sreekumar
#
#
from util.function import *
from flask_cors import CORS
from flask import Flask, request, jsonify, request


app = Flask(__name__)

CORS(app)



@app.route("/test_api_connection", methods=['POST'])
def test_api_connection():
    return jsonify({ 'data': request.json,'message': 'tetsing in progress!'}), 200

@app.route("/text", methods=['POST'])
def send_text(): 
    if send_text_llm(request.json):
        print("Recieveing message")
        return jsonify({'message': 'Message !'}), 200
    else:
        return jsonify({'message': 'message retrival failed!'}), 400

@app.route("/")
def test():
    return "<h1>Test one-d connection successful</h1>"


if __name__ == "__main__":
    print('Starting one-d server!')
    app.run(host='0.0.0.0',port=5000)
