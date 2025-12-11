import requests
from binascii import unhexlify, hexlify

HOST = "http://localhost:4444"
BLOCK = 16

def get_ticket(username):
    r = requests.post(f"{HOST}/Get-ticket", data={"username": username, "age": 16})
    return r.json()["Ticket-Code"]

def find_vip_zero_index(plaintext: bytes):
    return plaintext.index(b"IsVip=0") + len("IsVip=")

def forge(cipher_hex, zero_pos):
    cipher = bytearray(unhexlify(cipher_hex))
    block_index = zero_pos // BLOCK           
    offset = zero_pos % BLOCK                 
    prev_byte_index = block_index * BLOCK + offset - BLOCK
    cipher[prev_byte_index] ^= 0x01

    return hexlify(cipher).decode()

username = input("Enter username: ")
ticket = get_ticket(username)
cipher_bytes = unhexlify(ticket)
plaintext = f"username={username}&IsVip=0".encode()

zero_index = find_vip_zero_index(plaintext)

forged_hex = forge(ticket, zero_index)

r = requests.post(f"{HOST}/Vip-check", data={"Ticket-code": forged_hex})
print("Server Response:", r.text)

print("Original Ticket :", ticket)
print("Forged Ticket   :", forged_hex)
print("Byte flipped at :", zero_index)
