from Crypto.Cipher import DES
from Crypto.Util.Padding import pad, unpad

def get_des_key(key_int):
    key = key_int.to_bytes(8, byteorder='big')
    return key

plaintext = b"Rohith12"
true_key = get_des_key(0x1456)
cipher = DES.new(true_key, DES.MODE_ECB)
ciphertext = cipher.encrypt(pad(plaintext, DES.block_size))

print("Starting brute-force attack...")
for key_guess in range(0x0000, 0xFFFF + 1):
    guess_key = get_des_key(key_guess)
    try:
        guess_cipher = DES.new(guess_key, DES.MODE_ECB)
        decrypted = unpad(guess_cipher.decrypt(ciphertext), DES.block_size)
        print(f"Key tried: {hex(key_guess)}")
        if decrypted == plaintext:
            print(f"[+] Key found: {hex(key_guess)}")
            break
    except (ValueError, KeyError):
        continue

print(f"Original plaintext: {plaintext}")
print(f"Recovered plaintext: {decrypted}")
