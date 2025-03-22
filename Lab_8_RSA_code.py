def gcd(a,b):
    while b!=0:
        a,b=b,a%b
    return a

def modinv(e,phi):
    for d in range(2,phi):
        if(e*d)%phi==1:
            return d
    return -1


def generateKeys():
    p=7393
    q=1087
    n=p*q
    phi=(p-1)*(q-1)
    e=0
    for e in range(2,phi):
        if gcd(e,phi)==1:
            break
    d=modinv(e,phi)
    return e,d,n

if __name__=="__main__":
    e,d,n=generateKeys()
    print(f"Public Key (e, n): ({e}, {n})")
    print(f"Private Key (d, n): ({d}, {n})")
    message=123456
    print("Original: ",message)
    encrypted=pow(message,e,n)
    print("Encrypted: ",encrypted)
    decrypted=pow(encrypted,d,n)
    print("Decrypted: ",decrypted)