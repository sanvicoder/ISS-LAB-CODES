import java.util.Scanner; 
public class Lab_4_RailFence_Cipher {

    public static void main(String[] args) {
       
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the text :- ");
        String text = sc.nextLine();

        System.out.print("Enter the key value:- ");
        int key = sc.nextInt();

        encryption(text,key);
    }

    private static void encryption(String text, int key) {
    
        String encrypted_text = "";
        boolean direction =  false;
        int column = text.length();
        int j =0;
        char[][] a = new char[key][column];
        for(int i=0;i<column;i++)
        {
            if( j==0 || j == key-1)
            {
                direction = !direction;
            }
            a[j][i] = text.charAt(i);
            if(direction)
            {
                j++;
            }
            else
            {
                j--;
            }
        }

        for(int i = 0; i< key; i++)
        {
            for(int k=0;k<column;k++)
            {
                if(a[i][k]!=0)
                {
                    encrypted_text+=a[i][k];
                }
            }
        }
        System.out.println("Encrypted text = " + encrypted_text);


        decryption(encrypted_text,key);
    }


    private static void decryption(String encrypted_text, int key) {
       
        String decrypted_text = "";

     
        boolean direction =  false;

     
        int column = encrypted_text.length();


        int j =0;


        char[][] a = new char[key][column];


        for(int i=0;i<column;i++)
        {
        
            if( j==0 || j == key-1)
            {
                direction = !direction;
            }
  
            a[j][i] = '*';
      
            if(direction)
            {
                j++;
            }
            else
            {
                j--;
            }
        }


        direction = false;

        int index = 0;


        for(int i = 0; i< key; i++)
        {
            for(int k =0;k<column;k++)
            {
                if(a[i][k] == '*' && index<column)
                {
                    a[i][k] = encrypted_text.charAt(index++);
                }
            }
        }

        j=0;
        for(int i=0;i<column;i++)
        {
            if( j==0 || j == key-1)
            {
                direction = !direction;
            }
            decrypted_text+=a[j][i];
            if(direction)
            {
                j++;
            }
            else
            {
                j--;
            }
        }

        System.out.println("Decrypted text :- " + decrypted_text);
    }
}