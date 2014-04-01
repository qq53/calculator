package calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class cal {
	private Stack<String> optr, oprd; 		//存放操作符,操作数
	private String ans;								//默认结果存放
	private String[] var;								//变量数组，打算MAP实现
	private int varbit;								//位对应索引 判断该位置VAR数组元素是否使用
	private int varcount;							//当前变量数
	private String PS1 = ">> ";
	private Map<String, Integer> trtable;		//存运算符优先级，顺便可以判断是否支持
	private Map<String, Integer> vartable;		//存运算符优先级，顺便可以判断是否支持	
	private String exp;									//本次完整的表达式
	private String basetr = "+-*/%^(),[]#";
	private String exp2;									//待处理的字符串
	private function func;
	
	cal(){
		init();
	}
	
	public void init(){					//重新初始化环境
		oprd = new Stack<String>();
		optr = new Stack<String>();
		var = new String[100];
		varbit = 0;
		varcount = 1;
		trtable = new  HashMap<String, Integer>();
		trtable.put("#", 0); 
		trtable.put("+", 1);
		trtable.put("-", 1); 
		trtable.put("*", 2);	
		trtable.put("/", 2); 
		trtable.put("^", 3);	
		trtable.put("%", 3);	
		trtable.put("mod", 3);
		trtable.put("sin", 3);
		trtable.put("cos", 3);
		trtable.put("tan", 3);
		trtable.put("arcsin", 3);
		trtable.put("arccos", 3);
		trtable.put("arctan", 3);
		trtable.put("sinh", 3);
		trtable.put("cosh", 3);
		trtable.put("tanh", 3);
		trtable.put("log", 3);
		trtable.put("lg", 3);
		trtable.put("ln", 3);
		trtable.put("pow", 3);
		trtable.put("exp", 3);
		trtable.put("fact", 3);
		trtable.put("sqrt", 3);
		trtable.put("cuberoot", 3);
		trtable.put("yroot", 3);
		trtable.put("avg", 3);
		trtable.put("sum", 3);
		trtable.put("var", 3);
		trtable.put("varp", 3);
		trtable.put("stdev", 3);
		trtable.put("stdevp", 3);	
		
		vartable = new  HashMap<String, Integer>();
		vartable.put("mod", 2);
		vartable.put("sin", 1);
		vartable.put("cos", 1);
		vartable.put("tan", 1);
		vartable.put("arcsin", 1);
		vartable.put("arccos", 1);
		vartable.put("arctan", 1);
		vartable.put("sinh", 1);
		vartable.put("cosh", 1);
		vartable.put("tanh", 1);
		vartable.put("log", 2);
		vartable.put("lg", 1);
		vartable.put("ln", 1);
		vartable.put("pow", 2);
		vartable.put("exp", 1);
		vartable.put("fact", 1);
		vartable.put("sqrt", 1);
		vartable.put("cuberoot", 1);
		vartable.put("yroot", 1);
		
		func = new function();
		ans = "0";
	}

	public String process(String tmp){			//处理表达式返回值
		String var1 = "", var2 = "";
		String ctop, tmp2;
		String c;
		int varsum = 1;
		
		oprd.clear();
		optr.clear();
		optr.push("#");			
		exp = tmp + "#";
		exp2 = exp;

		if( !iscorrect() )
			return null;

		c = GetNextTr();
		while( !c.equals("#") || !optr.peek().equals("#") ){
			ctop = optr.peek();
			if( trtable.get(c) == null && basetr.indexOf(c) < 0 ){
				System.out.println("函数或操作符错误 !!");
				return null;
			}
			switch ( compare( ctop, c ) ) {
			case ',':
				c = GetNextTr();
				break;
			case '<':
				optr.push(c);
				c = GetNextTr();
				break;
			case '=':
				if( optr.peek().equals(",") ){
					++varsum;
					optr.pop();
					break;
				}
				optr.pop();
				c = GetNextTr();
				break;
			case '>':
				ctop = optr.pop();
				if (ctop.length() > 1) {			
					//varsum = vartable.get(ctop);
					if( varsum > 2) {
						while( varsum-- > 0 )
							var1 = oprd.pop() + "," + var1;
						var1 = var1.substring(0, var1.length()-1);
						tmp2 = func.cal(ctop, var1, varsum);
						varsum = 1;
					} else if (varsum == 2) {
						var2 = oprd.pop();
						var1 = oprd.pop();
						tmp2 = cal(ctop, var1, var2);
					} else {
						var1 = oprd.pop();
						tmp2 = func.cal(ctop, var1);		
					}
					varsum = 1;
				} else {
					var2 = oprd.pop();
					var1 = oprd.pop();
					tmp2 = cal(ctop, var1, var2);
				}
				var1 = "";
				var2 = "";
				oprd.push(tmp2);
				break;
			}
		}
	/*	System.out.println("OPRD:");
		while( !oprd.isEmpty() )
			System.out.println(oprd.pop());
		System.out.println("OPTR:");
		while( !optr.isEmpty() )
			System.out.println(optr.pop());*/
		
		return oprd.pop();
	}
	private String get(){		//返回ans值
		return ans;
	}	
	private char compare(String top, String tr){							//比较运算符优先级
		char c = top.charAt(0);
		char c1 = tr.charAt(0);

		if( c1 == ',' ){		
			if( c == '(' || c == '[' || c == ',' )
				return '<';
			return '>';
		}
		if ( c == '(' ){
			if( c1 == ')' )
				return '=';
			return '<';
		}
		if( c == ')' )
			return '>';
		if( c1 == '(' )
			return '<';
		if ( c1 == ')' ){
			if( c == '(' || c == ',')
				return '=';
			return '>';
		}
		if ( c == '[' ){
			if( c1 == ']' )
				return '=';
			return '<';
		}
		if( c == ']' )
			return '>';
		if( c1 == '[' )
			return '<';	
		if ( c1 == ']' ){
			if( c == '[' || c == ',' )
				return '=';
			return '>';
		}
		
		int i1 = trtable.get(top);
		int i2 = trtable.get(tr);
		if( i1 == i2){
			if( c == '#' )
				return '=';
			return '>';
		}
		else
			return i1 > i2?'>':'<';
	}

	private String cal(String tr, String rd1,  String rd2){		//计算双目运算符,简单的本类处理，复杂的交给function类
		BigDecimal brd1 = new BigDecimal(rd1);
		BigDecimal brd2 = new BigDecimal(rd2);		

		if( tr.length() == 1 ){
			switch( basetr.indexOf(tr) ){
			case 0:
				ans = brd1.add(brd2).toString();
				break;
			case 1:
				ans = brd1.add(brd2.negate()).toString();
				break;
			case 2:
				ans = brd1.multiply(brd2).toString();
				break;
			case 3:
				ans = brd1.divide(brd2, 2, RoundingMode.DOWN).toString();
				break;
			case 4:
				ans = brd1.remainder(brd2).toString();
				break;
			case 5:
				ans = brd1.pow(brd2.intValue()).toString();
				break;
			}
		}else 
			ans = func.cal(tr, rd1, rd2);
		return ans;
	}
	private boolean iscorrect(){											//检查表达式错对
		return true;
	}
	private String GetNextTr(){
		int i = 0;
		String c;
		String ret = "";

		while( true ){
			c = exp2.substring(i, i+1);
			if( basetr.indexOf(c) >= 0 ){
				if( i > 0 ){
					if( exp2.charAt( i-1 ) >= 'a' )
						ret = exp2.substring(0, i);
					else{
						oprd.push( exp2.substring(0, i) );
						ret = c;
						++i;
					}
				}else{
					ret = c;
					++i;
				}
				exp2 = exp2.substring(i);
				i = 0;		
				if( !ret.equals("") )
					break;
			}else
				++i;
		}
		
		return ret;
}
//以下是高级后期功能
	public void PS1(String format){}			//改变CONSOLE 每次命令前的提示（比如 "[root /]# "）
	public boolean save(String path){}			//提供保存变量到文件
	public String load(String path){}				//加载对应变量
	private int getidx(String name){}				//获取变量索引
	private void changevar(int idx){}				//修改变量值
	private String Multcal(String exp){}			//实现同级运算多线程计算。。。。估计不好弄
}
