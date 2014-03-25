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
	
	public cal(){
		init();
	}
	public void init(){			//重新初始化环境
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
		trtable.put("log10", 3);
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
		trtable.put("(", 4);	
		trtable.put(")", 4);			
	}
	String process(){		//处理表达式返回值
		optr.push("#");	
		sy
	}
	void trim(){}				//去多余空格
	String get(){							//返回ans值
		if( !ans.isEmpty() )
			return ans;
		return null;
	}	
	char compare(String top, String tr){							//比较运算符优先级
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
	private String cal(String tr, String rd){}							//计算单目运算符
	private String cal(String tr, String rd1,  String rd2){}		//计算双目运算符
	private boolean iscorrect(String exp){}							//检查表达式错对
	
//以下是高级后期功能
	void PS1(String format){}			//改变CONSOLE 每次命令前的提示（比如 "[root /]# "）
	Boolean save(String path){}			//提供保存变量到文件
	String load(String path){}				//加载对应变量
	int getidx(String name){}				//获取变量索引
	void changevar(int idx){}				//修改变量值
	String Multcal(String exp){}			//实现同级运算多线程计算。。。。估计不好弄
}
