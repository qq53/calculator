package calculator;

import java.io.IOException;
import java.util.Scanner;

public class console {
	public static void main(String[] args) {
		cal a = new cal();
		Scanner sc = new Scanner(System.in);
		String exp, result;
		
		a.println("������ʽ(��quit�˳�):");
		while( true ){
			exp = sc.nextLine();
			if( exp.equals("quit") ){
				a.println("Thanks for use !!\n");
				break;
			}
			if( exp.length() == 0 )
				continue;
			result = a.process( exp );	
			if( result != null )
				a.println(exp + " = " + result + "\n");
			a.println("");
		}
		sc.close();
	}
}
