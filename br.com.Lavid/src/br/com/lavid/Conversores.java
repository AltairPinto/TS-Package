/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lavid;

/**
 *
 * @author Altair
 */
abstract class Conversores {
  
    public static String inteiroParaBinario(int i) { 
            int num = i;
            StringBuilder buf1 = new StringBuilder();
            StringBuilder buf2 = new StringBuilder();
                while (num != 0) {
                    int digit = num % 2;
                    buf1.append(digit);
                    num = num / 2;
                }
                String binary = buf1.reverse().toString();
            int length = binary.length();
		if (length < 8) {
			while (8 - length > 0) {
				buf2.append("0");
				length++;
			}
		}
		String bin = buf2.toString() + binary;
		return bin;
	}
    
    public static String binarioParaDecimal(String s) {
            int degree = 1;
            int n = 0;
		for (int k = s.length() - 1; k >= 0; k--) {
			n += degree * (s.charAt(k) - '0');
			degree *= 2;
		}
		return n + "";
	}
    
    public static String Diretorio = "src/video/video.ts"; //Local do video
}
