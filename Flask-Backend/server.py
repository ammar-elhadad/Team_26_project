from flask import Flask, jsonify, request, render_template
import zipfile, io
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
import os

app = Flask(__name__)


key = os.urandom(16)
iv =  os.urandom(16)

FLAG = "PwnD{Y0u_4r3_4_V1P_m3mb3r!}"

# Tiket format = username=ahmed_reda&IsVip=1

@app.route('/Get-ticket', methods=['POST'])
def encrypt():
    username = request.form.get('username', '')
    if "IsVip" in username:
        return jsonify({"error": "Nice try tho!"}), 400
    age = request.form.get('age', '')
    is_vip = 0

    plaintext = f"username={username}&IsVip={is_vip}".encode()
    print(plaintext, "lenth:", len(plaintext))
    cipher = AES.new(key,AES.MODE_CBC,iv)
    ciphertext = cipher.encrypt(pad(plaintext, AES.block_size))
    ticket_code = ciphertext.hex()

    response = {"Ticket-Code": ticket_code}
    return jsonify(response),200

@app.route('/Vip-check', methods=['POST'])
def check_vip():
    ticket_code = request.form.get('Ticket-code', '')
    try:
        ciphertext = bytes.fromhex(ticket_code)
        cipher = AES.new(key, AES.MODE_CBC, iv)
        decrypted = cipher.decrypt(ciphertext)
        decrypted_str = unpad(decrypted, AES.block_size).decode(errors='ignore')
        print(f"decrypted_str: {decrypted_str}")
        is_vip = decrypted_str.split('IsVip=')[-1][0]
        print(f"is_vip: {is_vip}")
        if 'IsVip=1' in decrypted_str:
            return jsonify({"flag": FLAG}), 200
        else:
            return jsonify({"error": "Access denied. VIP status required."}), 403
    except Exception as e:
        return jsonify({"error": "Invalid ticket code."}), 400

@app.route('/support', methods=['GET'])
def support():
    return render_template('support.html')


@app.route('/insecure-webview', methods=['GET'])
def insecure_webview():
    return render_template('webview.html')

if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0',port= 4444)