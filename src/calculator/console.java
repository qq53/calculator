package calculator;

import java.util.Scanner;

public class console {
	public static void main(String[] args) {
		calcul a = new calcul();
		Scanner sc = new Scanner(System.in);
		String exp, result;
		
		a.println("输入表达式(输quit退出):");
		while( true ){
			exp = sc.nextLine();
			if( exp.equals("quit") ){
				a.println("Thanks for using !!\n");
				break;
			}
			if( exp.replaceAll(" ", "").length() == 0 ){
				a.println("");
				continue;
			}
			result = a.process( exp );	
			if( result != null )
				a.println(exp + " = " + result + "\n");
			a.println("");
		}
		sc.close();
	}
}
