package calculator;

import java.util.Scanner;

public class console {
	public static void main(String[] args) {
		cal a = new cal();
		Scanner sc = new Scanner(System.in);
		String exp;
		
		while( true ){
			System.out.println("������ʽ(��quit�˳�):");
			exp = sc.nextLine();
			if( exp.equals("quit") ){
				System.out.println("Thanks for use !!");
				break;
			}
			a.process( exp );	
		}
	}
}