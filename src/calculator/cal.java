package calculator;

import java.util.Stack;

public class cal {
	private Stack<String> oprd,optr; 		//存放操作数和操作符
	private String ans;								//默认结果存放
	private String[] var;								//变量数组，打算MAP实现
	private int varbit;								//位对应索引 判断该位置VAR数组元素是否使用
	private enum priority{							//运算符优先级
		GRE, EQU, LESS
	}
	private int varcount;			//当前变量数
	private String PS1 = ">> ";
	
	public cal(){
		init();
	}
	public void init(){			//重新初始化环境
		oprd = new Stack<String>();
		optr = new Stack<String>();
		var = new String[100];
		optr.push("#");		
		varbit = 0;
		varcount = 1;
	}
	void process(String a){}		//处理表达式转成后缀
	String get(){							//返回ans值
		if( !ans.isEmpty() )
			return ans;
		return null;
	}	
	priority compare(String tr1, String tr2){}			//比较运算符优先级
	String cal(String tr, String rd){}						//计算单目运算符
	String cal(String tr, String rd1,  String rd2){}	//计算双目运算符
	boolean check(String exp){}							//检查表达式错对
	
//以下是高级后期功能
	void PS1(String format){}			//改变CONSOLE 每次命令前的提示（比如 "[root /]# "）
	Boolean save(String path){}		//提供保存变量到文件
	String load(String path){}			//加载对应变量
	int getidx(String name){}			//获取变量索引
	void changevar(int idx){}			//修改变量值
	String Multcal(String exp){}		//实现同级运算多线程计算。。。。估计不好弄
}
