package calculator;

import java.util.Scanner;

public class console {
	public static void main(String[] args) {
		cal a = new cal();
		Scanner sc = new Scanner(System.in);
		String exp, result;
		
		while( true ){
			a.println("输入表达式(输quit退出):");
			exp = sc.nextLine();
			if( exp.equals("quit") ){
				a.println("Thanks for use !!");
				break;
			}
			result = a.process( exp );	
			a.println(exp + "=" + result);
		}
	}
}