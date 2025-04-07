import random

def modinv(a, p):
    return pow(a, p - 2, p)

def generate_keys(p, g):
    x = random.randint(2, p - 2)
    h = pow(g, x, p)
    return (p, g, h), x

def elgamal_encrypt(p, g, h, m):
    y = random.randint(1, p - 2)
    c1 = pow(g, y, p)
    s = pow(h, y, p)
    c2 = (m * s) % p
    return (c1, c2)

def elgamal_decrypt(c1, c2, x, p):
    s = pow(c1, x, p)
    s_inv = modinv(s, p)
    m = (c2 * s_inv) % p
    return m

def decryption_oracle(c1, c2, x, p):
    return elgamal_decrypt(c1, c2, x, p)

def cca_attack(c1, c2, h, g, p, oracle_function):
    z = random.randint(2, p - 2)
    c1_dash = (c1 * pow(g, z, p)) % p
    c2_dash = (c2 * pow(h, z, p)) % p
    
    print(f"[DEBUG] z = {z}")
    print(f"[DEBUG] Original ciphertext: ({c1}, {c2})")
    print(f"[DEBUG] Modified ciphertext: ({c1_dash}, {c2_dash})")
    
    m_dash = oracle_function(c1_dash, c2_dash)
    print(f"[DEBUG] Decrypted modified ciphertext: {m_dash}")
    original_m = m_dash
    return original_m

if __name__ == "__main__":
    p = 467
    g = 2
    public_key, private_key = generate_keys(p, g)
    p, g, h = public_key
    
    print(f"Public key: (p={p}, g={g}, h={h})")
    print(f"Private key: x={private_key}")
    message = 205
    c1, c2 = elgamal_encrypt(p, g, h, message)
    print(f"\nOriginal Message: {message}")
    print(f"Ciphertext: ({c1}, {c2})")
    
    def oracle(c1_dash, c2_dash):
        return decryption_oracle(c1_dash, c2_dash, private_key, p)
    
    recovered_message = cca_attack(c1, c2, h, g, p, oracle)
    print(f"\nRecovered Message by CCA: {recovered_message}")
    print(f"Attack successful: {message == recovered_message}")