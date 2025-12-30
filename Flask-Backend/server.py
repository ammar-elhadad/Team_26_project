from flask import Flask, jsonify, request, render_template
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
import os
import sqlite3

app = Flask(__name__)


key = os.urandom(16)
iv =  os.urandom(16)

FLAG = "PwnD{Y0u_4r3_4_V1P_m3mb3r!}"

# Tiket format = username=ahmed_reda&IsVip=1

def init_db():
    """Initialize the SQLite database from schema.sql and insert initial feedbacks"""
    DB_PATH = os.path.join(os.path.dirname(__file__), 'feedbacks.db')

    with sqlite3.connect(DB_PATH) as conn:
        cursor = conn.cursor()
        
        # Create table
        cursor.executescript("""
            DROP TABLE IF EXISTS feedbacks;

            CREATE TABLE feedbacks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                feedback TEXT NOT NULL, 
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """)
        
        # Insert initial values
        initial_feedbacks = [
            (1, "the best application ive ever used!!", "2025-12-29 22:30:32"),
            (2, "after using pwnDroid i bacame aware of android security features, thanks🔻", "2025-12-29 22:31:44"),
            (3, "I really loved the application and the labs, i recommend it to everybody who wants to start Andriod security!!", "2025-12-29 22:31:50"),
            (4, "PwnD{Thanks_for_ur_feedbacks❤}", "2025-12-29 22:36:55"),
            (5, "Keep up the good work, add more vulnerabilities please!!", "2026-01-29 22:36:59")
        ]
        cursor.executemany(
            "INSERT INTO feedbacks (id, feedback, created_at) VALUES (?, ?, ?)",
            initial_feedbacks
        )
        conn.commit()
    print("Database initialized successfully with sample feedbacks!")


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


@app.route('/feedback/add', methods=['POST'])
def add_feedback():
    feedback = request.form.get('feedback', '')
    if not feedback:
        return jsonify({"error": "Feedback cannot be empty."}), 400
    with sqlite3.connect('feedbacks.db') as conn:
        cursor = conn.cursor()
        cursor.execute("INSERT INTO feedbacks (feedback) VALUES (?)", (feedback,))
        conn.commit()
    return jsonify({"message": "Feedback added successfully"}), 200


@app.route('/feedbacks', methods=['GET'])
def get_feedbacks():
    with sqlite3.connect('feedbacks.db') as conn:
        conn.row_factory = sqlite3.Row
        cursor = conn.cursor()
        cursor.execute("SELECT id, feedback, created_at FROM feedbacks")
        rows = cursor.fetchall()
        feedbacks = [dict(row) for row in rows]
    return jsonify(feedbacks), 200


if __name__ == '__main__':
    init_db()
    app.run(debug=True,host='0.0.0.0',port= 4444)