package labb4;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Test {

	public static String encrypt(String text, int s) 
    { 
        StringBuffer result= new StringBuffer(); 
  
        for (int i=0; i<text.length(); i++) 
        { 
        	if((int)text.charAt(i) < 64) {
        		result.append(text.charAt(i));
        	} else if (Character.isUpperCase(text.charAt(i))) 
            { 
                char ch = (char)(((int)text.charAt(i) + s - 65) % 26 + 65); 
                result.append(ch); 
            } 
            else
            { 
                char ch = (char)(((int)text.charAt(i) + s - 97) % 26 + 97); 
                result.append(ch); 
            } 
        } 
        return result.toString(); 
    }
	
	public static String decrypt(String text, int s) {
		return encrypt(text, 26 - s);
	}
	
	private static String readInput(BufferedReader in) {
		StringBuilder input = new StringBuilder();
		int readByte;
		try {
			while((readByte = in.read()) != -1) {
				input.append((char) readByte);
				if(!in.ready()) {
					break;
				}
			}
		} catch(IOException e) {
			System.err.println(e);
			
		}
		return input.toString();
	}
	
	public static void delay() throws InterruptedException {
		Thread.sleep(2000);
	}
	
	public static void main(String[] args) {
		//System.out.println(decrypt(System.getenv("userHash"), 10)); //decrypt 10
		String encodedUser = Base64.getEncoder().withoutPadding().encodeToString(Test.decrypt(System.getenv("userHash"), 10).getBytes(StandardCharsets.UTF_8));
		String encodedPass = Base64.getEncoder().withoutPadding().encodeToString(Test.decrypt(System.getenv("mailHash"), 10).getBytes(StandardCharsets.UTF_8));
		System.out.println(encodedUser);
		System.out.println(encodedPass);
	}

}
