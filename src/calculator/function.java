package calculator;

import java.math.BigDecimal;

public class function {
	public String cal(String name, String rd1, String rd2){	//双变量函数
		System.out.println(rd1 + name + rd2);
		return "1";
	}
	public String cal(String name, String rd){	//处理单变量
		System.out.println(name + " " + rd);

		return "1";
	}	
	public String cal(String name, String rd, int varsum){		//处理向量函数
		System.out.println(name + "[" + rd + "]");	
		
		return "1";
	}
	private String sin(BigDecimal rd){}
	private String cos(BigDecimal rd){}
	private String tan(BigDecimal rd){}
	
	private String arcsin(BigDecimal rd){}
	private String arccos(BigDecimal rd){}
	private String arctan(BigDecimal rd){}
	
	private String sinh(BigDecimal rd){}
	private String cosh(BigDecimal rd){}
	private String tanh(BigDecimal rd){}
	
	private String log(BigDecimal x, BigDecimal base){}
	private String lg(BigDecimal rd){}
	private String ln(BigDecimal rd){}

	private String power(BigDecimal x, BigDecimal nPower){}
	private String sqrt(BigDecimal rd){}
	private String cuberoot(BigDecimal rd){}
	private String yroot(BigDecimal rd, BigDecimal n){}
	private String exp(BigDecimal rd){}
	private String fact(BigDecimal rd){}
	private String avg(BigDecimal rd){}
	private String sum(BigDecimal rd){}
	private String var(BigDecimal rd){}
	private String varp(BigDecimal rd){}
	private String stdev(BigDecimal rd){}
	private String stdevp(BigDecimal rd){}
}