package calculator;

import java.util.Scanner;

public class console {
	public static void main(String[] args) {
		cal a = new cal();
		Scanner sc = new Scanner(System.in);
		String exp, result;
		
		a.println("������ʽ(��quit�˳�):");
		while( true ){
			exp = sc.nextLine();
			a.println("");
			if( exp.equals("quit") ){
				a.println("Thanks for use !!");
				break;
			}
			if( exp.length() == 0 )
				continue;
			result = a.process( exp );	
			a.println(exp + "=" + result);
		}
		sc.close();
	}
}