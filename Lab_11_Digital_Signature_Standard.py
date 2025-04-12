import hashlib
import random
p = 23         
q = 11        
g = 4          

def generate_keys():
    """
    Generates a pair of DSA keys.
    
    Returns:
        x (int): The private key, a random integer in the range [1, q-1].
        y (int): The public key computed as y = g^x mod p.
    """
    x = random.randint(1, q - 1)  
    y = pow(g, x, p)               
    return x, y

def sign_message(message, x):
    """
    Signs a message using the DSA private key.
    
    Args:
        message (str): The message to sign.
        x (int): The private key.
    
    Returns:
        (r, s) (tuple): The DSA signature components.
    """

    sha1 = hashlib.sha1()
    sha1.update(message.encode('utf-8'))
    H = int(sha1.hexdigest(), 16) % q

    while True:
        k = random.randint(1, q - 1)          
        r = pow(g, k, p) % q               
        if r == 0:
            continue                      
        try:
            k_inv = pow(k, -1, q)             
        except ValueError:
            continue                          
        s = (k_inv * (H + x * r)) % q
        if s != 0:
            break
    return (r, s)

def verify_signature(message, signature, y):
    """
    Verifies a DSA signature.
    
    Args:
        message (str): The original message.
        signature (tuple): The signature (r, s) to verify.
        y (int): The public key.
    
    Returns:
        bool: True if the signature is valid; False otherwise.
    """
    r, s = signature

    if not (0 < r < q) or not (0 < s < q):
        return False

    sha1 = hashlib.sha1()
    sha1.update(message.encode('utf-8'))
    H = int(sha1.hexdigest(), 16) % q

    try:
        w = pow(s, -1, q)      
    except ValueError:
        return False

    u1 = (H * w) % q
    u2 = (r * w) % q

    v = (pow(g, u1, p) * pow(y, u2, p)) % p
    v = v % q
    return v == r

def main():
    message = "The quick brown fox jumps over the lazy dog"
    print("Message:", message)

    x, y = generate_keys()
    print("Private key (x):", x)
    print("Public key (y):", y)

    signature = sign_message(message, x)
    print("Signature (r, s):", signature)

    if verify_signature(message, signature, y):
        print("Signature verification: SUCCESS")
    else:
        print("Signature verification: FAILURE")

if __name__ == '__main__':
    main()
