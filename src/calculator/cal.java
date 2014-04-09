package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class cal {
	private Stack<String> oprd,optr; 		//存放操作数和操作符
	private String ans;								//默认结果存放
	private String[] var;								//变量数组，打算MAP实现
	private int varbit;								//位对应索引 判断该位置VAR数组元素是否使用
	private int varcount;							//当前变量数
	private String PS1 = ">> ";
	private Map<String, Integer> trtable;		//存运算符优先级，顺便可以判断是否支持
	private Map<String, Integer> vartable;
	private String exp;
	private String operator1 = ".,+-*/^%";      //判断字符是否为运算符
	private String operator2 = " ,(^%*/[";        //优化去掉多余的运算符
	private String operator3 = ".0123456789"; //判断字符是否代表数字
	
	public cal() {
		init();	
	}
	public void init() {			//重新初始化环境
		oprd = new Stack<String>();
		optr = new Stack<String>();
		var = new String[100];
		varbit = 0;
		varcount = 1;
		trtable = new  HashMap<String, Integer>();
		vartable = new HashMap<String, Integer>();
		trtable.put("#", 0);				vartable.put("mod", 2);
		trtable.put("+", 1);				vartable.put("sin", 1);
		trtable.put("-", 1);				vartable.put("cos", 1);
		trtable.put("*", 2);				vartable.put("tan", 1);
		trtable.put("/", 2);				vartable.put("arcsin", 1);
		trtable.put("^", 3);				vartable.put("arccos", 1);
		trtable.put("%", 3);			vartable.put("arctan", 1);
		trtable.put("mod", 3);		vartable.put("sinh", 1);
		trtable.put("sin", 3);			vartable.put("cosh", 1);
		trtable.put("cos", 3);			vartable.put("tanh", 1);
		trtable.put("tan", 3);			vartable.put("log", 1);
		trtable.put("arcsin", 3);		vartable.put("lg", 1);
		trtable.put("arccos", 3);		vartable.put("ln", 1);
		trtable.put("arctan", 3);		vartable.put("pow", 2);
		trtable.put("sinh", 3);			vartable.put("exp", 1);
		trtable.put("cosh", 3);		vartable.put("fact", 1);
		trtable.put("tanh", 3);		vartable.put("sqrt", 1);
		trtable.put("log", 3);			vartable.put("cuberoot", 1);
		trtable.put("lg", 3);			vartable.put("root", 2);
		trtable.put("ln", 3);			vartable.put("avg", 0);
		trtable.put("pow", 3);			vartable.put("sum", 0);
		trtable.put("exp", 3);			vartable.put("var", 0);
		trtable.put("fact", 3);			vartable.put("varp", 0);
		trtable.put("sqrt", 3);			vartable.put("stdev", 0);
		trtable.put("cuberoot", 3);	vartable.put("stdevp", 0);
		trtable.put("root", 3);
		trtable.put("avg", 3);
		trtable.put("sum", 3);
		trtable.put("var", 3);
		trtable.put("varp", 3);
		trtable.put("stdev", 3);
		trtable.put("stdevp", 3);
		trtable.put("(", 4);	
		trtable.put(")", 4);			
	}
	public String process(String tmp) {		//处理表达式返回值
		//.push("#");
		exp = tmp;
		optimize();
		if(!iscorrect()) {
			//System.out.println("优化后的表达式为:" + exp);
			return exp;
		}
		else {
			System.out.println("表达式错误");
		}
		return null;
	}
	boolean iscorrect() {				//检测表达式
		int checknumber1 = 0;
		int checknumber2 = 0;
		for(int i = 0; exp.charAt(i) != '#'; i++) {
			if(exp.charAt(i) == '.') {
				checknumber2++;
				for(int j = i + 1; exp.charAt(j) != '#'; j++) {
					if(exp.charAt(j) == '.') {
						checknumber2++;
					}
					if(operator3.indexOf(exp.substring(j, j+1)) < 0 || exp.charAt(j+1) == '#') {
						checknumber2--;
						break;		
					}
				}
				if(checknumber2 > 0) {
					return true;
				}
			}
				checknumber2 = 0;
		}
		checknumber2 = 0;
		for(int i = 0; exp.charAt(i) != '#'; i++) {
			if(exp.charAt(i) == '(') {
				checknumber1++;
			}
			if(exp.charAt(i) == ')') {
				checknumber1--;
			}
			if(exp.charAt(i) == '[') {
				checknumber2++;
			}
			if(exp.charAt(i) == ']') {
				checknumber2--;
			}
			if(exp.substring(i, i+2).equals("()")) {
				System.out.println("括号内语句不能为空");
				return true;
			}
			if(exp.substring(i, i+2).equals("[]")) {
				System.out.println("统计表达式不能为空");
				return  true;
			}
			if(exp.charAt(i) == ',' && exp.charAt(i+1) == ',') {
				System.out.println("统计表达式不能为空");
				return true;
			}
			if(operator1.indexOf(exp.substring(i, i+1)) >= 0 && operator1.indexOf(exp.substring(i+1, i+2)) >= 0) {
				System.out.println("操作符个数不合法");
				return true;
			}
			if(exp.charAt(i) == '.' && (operator3.indexOf(exp.substring(i-1, i)) < 0 || operator3.indexOf(exp.substring(i+1, i+2)) < 0)) {
				System.out.println(".前后有错误");
				return true;
			}
		}
		for(int i = 0; exp.charAt(i) != '#'; i++) {
			if(exp.charAt(i) >= 'a' && exp.charAt(i) <= 'z') {
				int j;
				int cont1 = 0;
				int cont2 = 0;
				for(j = i + 1; exp.charAt(j) >= 'a' && exp.charAt(j) <= 'z'; j++);
				if(exp.charAt(j) != '(') {
					System.out.println("函数参数要加（）");
					return true;
				}
				else if(exp.charAt(j) == '(') {
					if(vartable.get(exp.substring(i, j)) == null) {
						System.out.println("函数名错误");
						return true;
					}
					else {
						int p, k, m;
						int n = 1;
						for(p = j+1; n != 0 && exp.charAt(p) != '#'; p++) {
							if(exp.charAt(p) == '(')
								n++;
							else if(exp.charAt(p) == ')')
								n--;
							else if(exp.charAt(p) == ',')
								cont1++;
						}
						for(k = i; k < p; ) {
							for(m = k; exp.charAt(m) != '#' && exp.charAt(m) >= 'a' && exp.charAt(m) <= 'z'; m++);
							if(vartable.get(exp.substring(k, m)) == null)
							{
								k++;
								continue;
							}
							else if(vartable.get(exp.substring(k, m)) > 1)
								cont2++;
							k = m;
						}
						if(cont1 != cont2)
							return true;
					}
				}
				i = j;
			}				
		}
		if(checknumber1 > 0 || checknumber2 > 0)	{
			System.out.println("括号个数不合法");
			return true;
		}
		return false;
	}
	void optimize() { 			//优化表达式
		exp = exp.replaceAll("\\s*" , ""); //去空格
		exp = "       " + exp + "#            ";
		exp.toLowerCase(); 		//大写转小写
		for(int i = 0; exp.charAt(i) != '#'; i++) {  //“.“前补0
			if(operator3.indexOf(exp.substring(i, i+1)) <= 0 && exp.charAt(i + 1) == '.') {
				exp = exp.substring(0, i + 1) + "0" + exp.substring(i + 1);	
			}
		}
		for(int i = 0; exp.charAt(i) != '#'; i++) { //mod操作符转化为%; log10换成lg
			if(exp.charAt(i) == 'm') {
				if(exp.substring(i, i + 3).equals("mod") && exp.charAt(i + 3) != '(') {
					exp = exp.substring(0, i) + "%" + exp.substring(i+3);
				} 
			}
			else if(exp.charAt(i) == 'l') {
				if(exp.substring(i, i + 5).equals("log10")) {
					exp = exp.substring(0, i) + "lg" + exp.substring(i+5);				
				}
			}
		} 
		for(int i = 0; exp.charAt(i) != '#'; i++) { //（）前后添加符号
			if(exp.charAt(i) == ')' && exp.charAt(i+1) == '(') {
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
				i += 2;
			}
			else if(exp.charAt(i) == ')' && operator3.indexOf(exp.substring(i+1, i+2)) > 0) {
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
				i += 2;
			}
			else if(exp.charAt(i) == ')' && exp.charAt(i+1) >= 'a' && exp.charAt(i+1) <= 'z') {
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
				i += 2;
			} 
			else if(exp.charAt(i) == '(' && operator3.indexOf(exp.substring(i-1, i)) > 0) {
				exp = exp.substring(0, i) + "*" + exp.substring(i);
			}
			else if(operator3.indexOf(exp.substring(i, i+1)) > 0 && exp.charAt(i+1) >= 'a' && exp.charAt(i+1) <= 'z') {
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
				i += 2;
			}
		}
		for(int i = 0; exp.charAt(i) != '#'; i++) { //++ +- -+ --的处理
			if(exp.charAt(i) == '+') {
				if(exp.charAt(i+1) == '+') {
					exp = exp.substring(0, i+1) + exp.substring(i+2);
				}
				else if(exp.charAt(i+1) == '-') {
					exp = exp.substring(0, i) + exp.substring(i+1);
				}
			}
			else if (exp.charAt(i) == '-') {
				if(exp.charAt(i+1) == '-') {
					exp = exp.substring(0, i) + "+" + exp.substring(i+2);
				}
				else if(exp.charAt(i+1) == '+') {
					exp = exp.substring(0, i+1) + exp.substring(i+2);
				}
			}
		}
		for(int i = 0; exp.charAt(i) != '#'; i++) { //- +前面补0
			if(operator2.indexOf(exp.substring(i, i+1)) >= 0) {
				if((exp.charAt(i+1) == '+' || exp.charAt(i+1) == '-') && operator3.indexOf(exp.substring(i+2, i+3)) >= 0) {
					int k;
					for(k = i+3; operator3.indexOf(exp.substring(k, k+1)) >= 0; k++);
					exp = exp.substring(0, i+1) + "(0" + exp.substring(i+1, k) + ")" + exp.substring(k);
				}
				else if((exp.charAt(i+1) == '+' || exp.charAt(i+1) == '-' ) && 
						(exp.charAt(i+2) == '(' || (exp.charAt(i+2) >= 'a' && exp.charAt(i+2) <= 'z'))) {
					int p;
					int n = 1;
					for(p = i+2; exp.charAt(p) != '('; p++);
					for(p += 1 ; n != 0 && exp.charAt(p) != '#'; p++) {
						if(exp.charAt(p) == '(')
							n++;
						else if(exp.charAt(p) == ')')
							n--;
					}
					exp = exp.substring(0, i+1) + "(0" + exp.substring(i+1, p) + ")" + exp.substring(p);
				}
		/*		else if((exp.charAt(i+1) == '+' || exp.charAt(i+1) == '-')
						&& exp.charAt(i+2) >= 'a' 
						&& exp.charAt(i+2) <= 'z' 
						&& exp.charAt(i+3) == '(') {
					int k;
					int n = 1;
					for(k = i+3; n != 0; k++) {
						if(exp.charAt(k) == '(')
							n++;
						else if(exp.charAt(k) == ')')
							n--;
					}
					exp = exp.substring(0, i+1) + "(0" + exp.substring(i+1, k) + ")" +exp.substring(k);
				}*/
			}
		}
		for(int i = 0; exp.charAt(i) != '#'; i++ ) { //,前后补数字
			if(exp.charAt(i) == ',') {
				if(exp.charAt(i-1) == '[' || exp.charAt(i-1) == '(') {
					exp = exp.substring(0, i) + "0" + exp.substring(i);
				}
				else if(exp.charAt(i+1) == ']' || exp.charAt(i+1) == ')' || exp.charAt(i+1) == ',') {
					exp = exp.substring(0, i+1) + "0" + exp.substring(i+1);
				}
			}
		}
		System.out.println(exp);
		for(int i = 0; exp.charAt(i) != '#'; i++) {
			if(exp.charAt(i) == '*' && exp.charAt(i+1) == 'r') {
				if(exp.substring(i+1, i+5).equals("root")) {
					if(exp.charAt(i-1) == ')') {
						int n = 1;
						int p;
						for(p = i-1; n != 0 && exp.charAt(p) != ' '; p--) {
							if(exp.charAt(p) == ')')
								n++;
							else if(exp.charAt(p) == '(')
								n--;
						}
						if(exp.charAt(p) < 'a' || exp.charAt(p) > 'z') {
							exp = exp.substring(0, p+1) + exp.substring(i+1, i+6) + exp.substring(p+1, i) + "," + exp.substring(i+6);
						}
					}
					else if(operator3.indexOf(exp.substring(i-1, i)) > 0) {
						int k;
						for(k = i-1; operator3.indexOf(exp.substring(k-1, k)) > 0 && exp.charAt(k) != ' '; k--);
						exp = exp.substring(0, k) + exp.substring(i+1, i+6) + exp.substring(k, i) + "," + exp.substring(i+6);
					}
				}
			}
		}
		exp = exp.replaceAll("\\s*" , ""); //去空格
		System.out.println(exp);
	}
	String get() {							//返回ans值
		if( !ans.isEmpty() )
			return ans;
		return null;
	}	
	char compare(String top, String tr) {							//比较运算符优先级
		int i1 = trtable.get(top);
		int i2 = trtable.get(tr);
		if( i1 == i2){
			if(top.charAt(0) == '#')
				return '=';
			if(top.charAt(0) == '('){
				if(tr.charAt(0) == '(')
					return '<';
				if(tr.charAt(0) == ')')
					return '=';
			}
			return '>';
		}else
			return i1 > i2?'>':'<';
	}
	//private String cal(String tr, String rd) {}							//计算单目运算符
//	private String cal(String tr, String rd1,  String rd2) {}		//计算双目运算符
//	private boolean iscorrect(String exp) {}							//检查表达式错对
	
//以下是高级后期功能
	void PS1(String format) {}			//改变CONSOLE 每次命令前的提示（比如 "[root /]# "）
	Boolean save(String path) {}			//提供保存变量到文件
	String load(String path) {}				//加载对应变量
	int getidx(String name) {}				//获取变量索引
	void changevar(int idx) {}				//修改变量值
	String Multcal(String exp) {}			//实现同级运算多线程计算。。。。估计不好弄
}
