package calculator;

import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Stack;
import java.math.*;

public class console {
	public static void main(String[] args) {
		cal a = new cal();
		Scanner sc = new Scanner(System.in);
		String exp, result;
		while(true) {
			System.out.println("输入表达式（按quit退出）");
			exp = sc.nextLine();
			if(exp.equals("quit")) {
				System.out.println("Thanks for using!!");
				break;
			}
			result = a.process(exp);
			System.out.println(result);
		}
	}
}