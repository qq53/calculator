package calculator;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Stack;
import java.math.*;

public class console {
	public static void main(String[] args) {
		cal a = new cal();
		Scanner sc = new Scanner(System.in);
		String exp;
		
		while( true ){
			System.out.println("������ʽ:");
			exp = sc.nextLine();
			if( exp.equals("quit") ){
				System.out.println("Thanks for use !!");
				break;
			}
			a.process( exp );	
		}
	}
}