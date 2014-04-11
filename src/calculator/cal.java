package calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

public class cal {
	private Stack<String> optr, oprd; 		//存放操作符,操作数
	private String ans;								//默认结果存放
	private Map<String, Integer> trtable;		//存运算符优先级，顺便可以判断是否支持
	private String PS1;
	private Map<String, String> vartable;		//存运算符优先级，顺便可以判断是否支持
	private String exp;									//本次完整的表达式
	private String basetr = "+-*/%^(),[]#";
	private function func;
	private String pwd;
	private Map<String, Integer> varnumtable;
	private String operator1 = ".,+-*/^%";      //判断字符是否为运算符
	private String operator2 = " ,(^%*/[";        //优化去掉多余的运算符
	private String operator3 = ".0123456789"; //判断字符是否代表数字
	
	private boolean isExist(String path){
		File f = new File(path);
		if( f.exists() )
			return true;
		return false;
	}
	cal(){
		init();
	}
	public void println(String str){
		System.out.print(PS1 + str);
	}
	public void init(){					//重新初始化环境
		oprd = new Stack<String>();
		optr = new Stack<String>();

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
		trtable.put("root", 3);
		trtable.put("avg", 3);
		trtable.put("sum", 3);
		trtable.put("var", 3);
		trtable.put("varp", 3);
		trtable.put("stdev", 3);
		trtable.put("stdevp", 3);	
		
		func = new function();
		ans = "0";
		
		pwd  = System.getProperty("user.dir");
		PS1 = pwd.substring( pwd.lastIndexOf("\\") + 1 );
		PS1 = "[ " + PS1 + " ] ";
		
		vartable = new  HashMap<String, String>();
		vartable.put("ans", ans);		

		varnumtable = new HashMap<String, Integer>();
		varnumtable.put("mod", 2);
		varnumtable.put("sin", 1);
		varnumtable.put("cos", 1);
		varnumtable.put("tan", 1);
		varnumtable.put("arcsin", 1);
		varnumtable.put("arccos", 1);
		varnumtable.put("arctan", 1);
		varnumtable.put("sinh", 1);
		varnumtable.put("cosh", 1);
		varnumtable.put("tanh", 1);
		varnumtable.put("log", 1);
		varnumtable.put("lg", 1);
		varnumtable.put("ln", 1);
		varnumtable.put("pow", 2);
		varnumtable.put("exp", 1);
		varnumtable.put("fact", 1);
		varnumtable.put("sqrt", 1);
		varnumtable.put("cuberoot", 1);
		varnumtable.put("root", 2);
		varnumtable.put("avg", 0);
		varnumtable.put("sum", 0);
		varnumtable.put("var", 0);
		varnumtable.put("varp", 0);
		varnumtable.put("stdev", 0);
		varnumtable.put("stdevp", 0);
	}
	private boolean preprocess() {			//预处理 完成变量替换 或 命令
		String tmp = exp.substring(0, exp.length() - 1 );
		tmp = tmp.replaceAll(" {2,}", " ");
		boolean ret = false;
		
		if( tmp.indexOf("=") > 0 ){
			ret = true;
			String var = "", varexp = "";
			int count = 0;
			boolean f_print = true;
			for(String i : tmp.split("=") ){
				if( count == 2 )
					break;
				if( count == 1 )
					varexp = i;
				else
					var = i;
				++count;
			}
			if( var.equals("") || varexp.equals("") )
				System.out.println("=号使用错误");
			else{
				if( trtable.get(var) != null || var.equals("ans") )
					System.out.println("变量名不可用 !!");
				else{
					if( varexp.indexOf(";") > 0 )
						f_print = false;
					if( !f_print )
						varexp = varexp.substring(0, varexp.length()-1 );
					exp = varexp + "#";
					optimize();
					if( iscorrect() ) {
						System.out.println("优化后的表达式为:" + exp);
						varexp = calcul();
						vartable.put(var, varexp);
						if( f_print )
							System.out.println( var + " = " + vartable.get(var) );
					}
					else {
						System.out.println("表达式错误");
					}
				}
			}	
		}
		
		if( tmp.length() >= 2 ){
			switch( tmp.substring(0,2) ){
			case "ls":
				ret = true;
				File f = new File(pwd);
				File list[] = f.listFiles();
				for(int i = 0; i < list.length; ){
					System.out.printf("%-16s", list[i].getName());
					if( ++i%4 == 0 )
						System.out.print("\n");
				}
				System.out.print("\n");
				break;
			case "cd":
				ret = true;
				if( tmp.indexOf(":") > 0 ){
					if( isExist(tmp.substring(3)) )
						pwd = tmp.substring(3);
					break;
				}
				String tpwd = pwd;
				String s = tmp.substring(3);
				if( s.charAt(s.length()-1) != '\\' )	//末尾添加\
					s += "\\";
				while( s.indexOf("\\") > 0 ){
					switch( s.substring(0, s.indexOf("\\")) ){
					case ".":
						break;
					case "..":
						if( tpwd.lastIndexOf("\\") > 0 )
							tpwd = tpwd.substring(0, tpwd.lastIndexOf("\\"));
						break;
					default:
						if( s.substring(0, s.indexOf("\\")).indexOf(".") >= 0 ) // 2个以上.不处理
							break;
						tpwd += "\\" + s.substring(0, s.indexOf("\\"));
						break;
					}
					s = s.substring(s.indexOf("\\")+1);
				}
				if( !isExist(tpwd) ){
					System.out.println("目录不存在 !!");
					break;
				}
				pwd = tpwd;
				PS1 = pwd.substring( pwd.lastIndexOf("\\") + 1 );
				PS1 = "[ " + PS1 + " ] ";
				break;
			case "rm":
				ret = true;
				String var = tmp.substring(3);
				String dpath = pwd, s1 = "";
				if( var.indexOf(":") > 0 ){
					if( isExist(var) )
						dpath = var;
					else{
						System.out.println("文件路径错误 !!");
						break;
					}
				}else
					s1 = var;
				if( s1.charAt(s1.length()-1) != '\\' )
					s1 += "\\";
				while( s1.indexOf("\\") > 0 ){
					switch( s1.substring(0, s1.indexOf("\\")) ){
					case ".":
						break;
					case "..":
						if( dpath.lastIndexOf("\\") > 0 )
							dpath = dpath.substring(0, dpath.lastIndexOf("\\"));
						break;
					default:
						String tmp2 = s1.substring(0, s1.indexOf("\\"));
						int i = tmp2.indexOf(".");
						if( i > 0 && tmp2.charAt(i-1) == '.' )
							break;
						dpath += "\\" + s1.substring(0, s1.indexOf("\\"));
						break;
					}
					s1 = s1.substring(s1.indexOf("\\")+1);
				}
				File df = new File(dpath);
				if( !df.exists() ){
					System.out.println("文件不存在 !!");
					break;
				}
				if( df.isDirectory() || !df.isFile() ){
					System.out.println("文件类型错误 !!");
					break;
				}
				if( !df.delete() )
					System.out.println("删除文件错误 !!");
				break;
			}
		}
		
		if( tmp.length() >= 7 && tmp.substring(0, 3).equals("PS1") ){		//因为已经转成小写了
			PS1( tmp.substring( 5, tmp.length()-2 ) );
			ret = true;
		}
			
		if( tmp.length() >= 4 ){
			switch(  tmp.substring(0, 4) ){
			case "show":
				ret = true;
				Set set = vartable.entrySet() ;
				Iterator it = vartable.entrySet().iterator();
				System.out.println("变量表:");
				while(it.hasNext()){
					Entry entry = (Entry)it.next();
					System.out.println(entry.toString());
				} 
				break;
			case "save":
				ret = true;
				String[] vararr = tmp.split(" ");
				if( vararr.length < 3 )
					break;
				String spath = vararr[1];
				String var = tmp.substring(4+1+spath.length()+1);
				save(spath, var);
				break;
			case "load":
				ret = true;
				String lpath = tmp.split(" ")[1];
				load(lpath);
				break;
			}
		}
		
		return ret;
	}

	boolean iscorrect() {				//检测表达式
		exp = "     " + exp + "     ";
		if( exp.matches(".*\\.\\w+\\..*") ){
			System.out.println(".号错误 !!");
			return false;
		}
		if( exp.matches(".*[^\\d]+\\.[^\\d]+.*") ){
			System.out.println(".号错误 !!");
			return false;
		}
		if( exp.matches(".*[\\(\\[]+[\\)\\]]+.*") ){
			System.out.println("参数不能为空 !!");
			return false;
		}
		if( exp.matches(".*[a-z]+.*") && !exp.matches(".*[a-z]+\\(.*\\).*") ){
			System.out.println("函数需要参数 !!");
			return false;
		}		
		if( exp.matches("[^\\(]+,[^\\)]+") ){
			System.out.println("逗号错误 !!");
			return false;
		}	
		if( exp.matches(".*[^\\)\\d]+[+-/%^\\*]+.*") ){
			System.out.println("数不正确 !!");
			return false;
		}		
		if( exp.matches(".*[+-/%^\\*]+[^a-z\\d]+.*") ){
			System.out.println("数不正确 !!");
			return false;
		}		
		exp = exp.trim();
		Set set = varnumtable.entrySet() ;
		Iterator it = varnumtable.entrySet().iterator();
		String etmp = exp;
		while(it.hasNext()){
			Entry entry = (Entry)it.next();
			etmp = etmp.replaceAll((String)entry.getKey(), "");
		}
		if( etmp.matches(".*[a-z]+.*") ){
			System.out.println("函数名错误 !!");
			return false;
		}
		int checknumber = 0;
		for(int i = 0; i < exp.lastIndexOf("#"); ++i) {
			switch( exp.charAt(i) ){
			case '(':
				++checknumber;
				break;
			case ')':
				--checknumber;
				break;
			case '[':
				++checknumber;
				break;
			case ']':
				--checknumber;
				break;
			}		
		}
		
		if( checknumber != 0 )	{
			System.out.println("括号个数不合法 !!");
			return false;
		}		
		
		return true;
	}
	void optimize() { 			//优化表达式
		exp = exp.replaceAll(" " , ""); 	//去空格
		exp = "     " + exp + "     ";	//防止越界
		exp.toLowerCase(); 		//大写转小写
		for(int i = 4; i < exp.lastIndexOf("#"); ++i ) {  //“.“前补0
			if( exp.charAt(i+1) == '.' && operator3.indexOf(exp.substring(i, i+1)) < 0 ){
				exp = exp.substring(0, i + 1) + "0" + exp.substring(i + 1);	
				continue;
			}	//	mod -> %
			if(exp.substring(i, i + 3).equals("mod") && exp.charAt(i + 3) != '(') {	
				exp = exp.substring(0, i) + "%" + exp.substring(i+3);
				continue;
			}	//	log10 -> lg
			if(exp.substring(i, i + 5).equals("log10")) {
				exp = exp.substring(0, i) + "lg" + exp.substring(i+5);
				continue;
			}
			//	1(2) -> 1*(2)
			if(exp.charAt(i+1) == '(' && operator3.indexOf(exp.substring(i, i+1)) > 0) {
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
						i += 3;
			}
			//	)(加*号
			if(exp.charAt(i) == ')' && exp.charAt(i+1) == '(') {
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
				continue;
			}	//	 )9 -> )*9
			if(exp.charAt(i) == ')' && operator3.indexOf(exp.substring(i+1, i+2)) > 0) {
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
				continue;
			}	//	)sin(1) -> )*sin(1)
			if(exp.charAt(i) == ')' && exp.charAt(i+1) >= 'a' && exp.charAt(i+1) <= 'z') {
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
				continue;
			} 	//	2sin -> 2*sin
			if(operator3.indexOf(exp.substring(i, i+1)) > 0 && (exp.charAt(i+1) >= 'a' && exp.charAt(i+1) <= 'z')) {				
				exp = exp.substring(0, i+1) + "*" + exp.substring(i+1);
				continue;
			}
			if( exp.substring(i,i+2).equals("++") ) {
				exp = exp.substring(0, i+1) + exp.substring(i+2);
				continue;
			}
			if( exp.substring(i,i+2).equals("+-") ) { 
				exp = exp.substring(0, i) + exp.substring(i+1);
				continue;
			}
			if( exp.substring(i,i+2).equals("--") ) { 
				exp = exp.substring(0, i) + "+" + exp.substring(i+2);
				continue;
			}
			if( exp.substring(i,i+2).equals("-+") ) {
				exp = exp.substring(0, i+1) + exp.substring(i+2);
				continue;
			}
			if(operator2.indexOf(exp.substring(i, i+1)) >= 0) {
				if((exp.charAt(i+1) == '+' || exp.charAt(i+1) == '-') && operator3.indexOf(exp.substring(i+2, i+3)) >= 0) {
					int k;
					for(k = i+3; operator3.indexOf(exp.substring(k, k+1)) > 0; k++);
					exp = exp.substring(0, i+1) + "(0" + exp.substring(i+1, k) + ")" + exp.substring(k);
					continue;
				}
				if((exp.charAt(i+1) == '+' || exp.charAt(i+1) == '-' ) && 
						(exp.charAt(i+2) == '(' || (exp.charAt(i+2) >= 'a' && exp.charAt(i+2) <= 'z'))) {
					int p;
					int n = 1;
					for(p = i+2; exp.charAt(p) != '('; p++);
					for(p += 1 ; n != 0 && p < exp.lastIndexOf("#"); p++) {
						if(exp.charAt(p) == '(')
							n++;
						else if(exp.charAt(p) == ')')
							n--;
					}
					exp = exp.substring(0, i+1) + "(0" + exp.substring(i+1, p) + ")" + exp.substring(p);
					continue;
				}
			}
			if(exp.charAt(i) == ',') {
				if(exp.charAt(i-1) == '[' || exp.charAt(i-1) == '(') {
					exp = exp.substring(0, i) + "0" + exp.substring(i);
					continue;
				}
				else if(exp.charAt(i+1) == ']' || exp.charAt(i+1) == ')' || exp.charAt(i+1) == ',') {
					exp = exp.substring(0, i+1) + "0" + exp.substring(i+1);
					continue;
				}
			}
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
							continue;
						}
					}
					else if(operator3.indexOf(exp.substring(i-1, i)) >= 0) {
						int k;
						for(k = i-1; operator3.indexOf(exp.substring(k-1, k)) > 0 && exp.charAt(k) != ' '; k--);
						exp = exp.substring(0, k) + exp.substring(i+1, i+6) + exp.substring(k, i) + "," + exp.substring(i+6);
					}
				}
			}
		}
		exp = exp.trim();
		System.out.println(exp);
	}
	
	public String process(String tmp){		//处理表达式返回值
		
		exp = tmp + "#";
		
		if( preprocess() )
			return null;
		
		optimize();
		if( iscorrect() ) 
			System.out.println("优化后的表达式为:" + exp);
		else 
			return null;		
		
		return calcul();
	}
	
	private String calcul(){			//计算表达式返回值
		String var1 = "", var2 = "";
		String ctop;
		String c;
		int varsum = 1;
		
		oprd.clear();
		optr.clear();
		optr.push("#");			
		
		c = GetNextTr();
		while( !c.equals("#") || !optr.peek().equals("#") ){
			ctop = optr.peek();
			if( trtable.get(c) == null && basetr.indexOf(c) < 0 ){
				System.out.println(c + " 函数或操作符错误 !!");
				return null;
			}
			switch ( compare( ctop, c ) ) {
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
					if( varsum > 2) {
						int i = varsum;
						while( i-- > 0 )
							var1 = oprd.pop() + "," + var1;
						ans = func.cal(ctop, var1, varsum);
						varsum = 1;
					} else if (varsum == 2) {
						var2 = oprd.pop();
						var1 = oprd.pop();
						ans = cal(ctop, var1, var2);
					} else {
						var1 = oprd.pop();
						ans = func.cal(ctop, var1);		
					}
					varsum = 1;
				} else {
					var2 = oprd.pop();
					var1 = oprd.pop();
					Double i = new Double(var2);
					if( i.doubleValue() - 0 <= 0.00000001 ){
						System.out.println("除数不可为0 !!");
						return null;
					}
					ans = cal(ctop, var1, var2);
				}
				if( ans == null )
					return null;
				var1 = "";
				var2 = "";
				oprd.push(ans);
				break;
			}
		}
	/*	System.out.println("OPRD:");
		while( !oprd.isEmpty() )
			System.out.println(oprd.pop());
		System.out.println("OPTR:");
		while( !optr.isEmpty() )
			System.out.println(optr.pop());*/
		ans = oprd.pop();
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
		if( c == ',' ){		
			if( c1 == ']' || c1 == ')' )
				return '=';
			return '<';
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
			if( c == '(' )
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
			if( c == '[' )
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
			switch( tr ){
			case "+":
				ans = brd1.add(brd2).toString();
				break;
			case "-":
				ans = brd1.add(brd2.negate()).toString();
				break;
			case "*":
				ans = brd1.multiply(brd2).toString();
				break;
			case "/":
				ans = brd1.divide(brd2, 2, RoundingMode.DOWN).toString();
				break;
			case "%":
				ans = brd1.remainder(brd2).toString();
				break;
			case "^":
				ans = brd1.pow(brd2.intValue()).toString();
				break;
			}
		}else 
			ans = func.cal(tr, rd1, rd2);
		return ans;
	}
	private String GetNextTr(){
		int i = 0;
		String c;
		String ret = "";

		while( true ){
			c = exp.substring(i, i+1);
			if( basetr.indexOf(c) >= 0 ){
				if( i > 0 ){
					if( exp.charAt( i-1 ) >= 'a' )
						ret = exp.substring(0, i);
					else{
						oprd.push( exp.substring(0, i) );
						ret = c;
						++i;
					}
				}else{
					ret = c;
					++i;
				}
				exp = exp.substring(i);
				i = 0;		
				if( !ret.equals("") )
					break;
			}else
				++i;
		}			
		return ret;
	}
	public void PS1(String format){			//改变CONSOLE 每次命令前的提示（比如 "[root /]# "）
		PS1 = format + " ";
	}
	public void save(String file, String var){			//提供保存变量到文件 
		String path = pwd + "\\" + file;
		try {
			FileWriter fw = new FileWriter(path);
			
			int i = 0;
			String[] tmp = var.split(" ");
			while(i < tmp.length){
				fw.write( tmp[i] + " " + vartable.get(tmp[i]) + "\r\n" );
				++i;
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("写文件错误 !!");
		}
	}
	public void load(String path) {				//加载文件变量
		if( path.indexOf(".") > 0 || path.indexOf("\\") <= 0 )
			path = pwd + "\\" + path;
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String tmp;
			while( (tmp=br.readLine()) != null )
				vartable.put(tmp.split(" ")[0], tmp.split(" ")[1]);
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("读文件错误 !!");
		} catch (IOException e) {
			System.out.println("读文件错误 !!");		
		}
	}
}