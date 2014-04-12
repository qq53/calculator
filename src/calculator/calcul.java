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
import java.util.Stack;

public class calcul {
	private Stack<String> optr, oprd; 		//��Ų�����,������
	private String ans;								//Ĭ�Ͻ�����
	private Map<String, Integer> trtable;		//����������ȼ���˳������ж��Ƿ�֧��
	private String PS1;
	private Map<String, String> vartable;		//����������ȼ���˳������ж��Ƿ�֧��
	private String exp;									//���������ı��ʽ
	private String basetr = "+-*/%^(),[]#";	//�ָ�֮ǰ������/��
	private function func;
	private String pwd;
	private String operator2 = ",(^%*/[";        //�Ż�ȥ������������
	private String operator3 = ".0123456789"; //�ж��ַ��Ƿ��������
	
	private String deal_path(String base, String s){
		s = s.replaceAll(" ", "").replaceAll("/s", " ");
		if( s.charAt(s.length()-1) != '\\' )	//ĩβ���\
			s += "\\";
		if( s.indexOf(":") > 0 )
			if( isExist(s) )
				return s;
		String tmp = base;
		while( s.indexOf("\\") > 0 ){
			switch( s.substring(0, s.indexOf("\\")) ){
			case ".":
				break;
			case "..":
				if( tmp.lastIndexOf("\\") > 0 )
					tmp = tmp.substring(0, tmp.lastIndexOf("\\"));
				break;
			default:
				if( s.substring(0, s.indexOf("\\")).indexOf(".") >= 0 ) // 2������.������
					break;
				tmp += "\\" + s.substring(0, s.indexOf("\\"));
				break;
			}
			s = s.substring(s.indexOf("\\")+1);
		}
		return tmp;
	}
	
	private boolean isExist(String path){
		File f = new File(path);
		if( f.exists() )
			return true;
		return false;
	}
	calcul(){
		init();
	}
	public void println(String str){
		System.out.print(PS1 + str);
	}
	public void init(){					//���³�ʼ������
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
	}
	private boolean preprocess() {			//Ԥ���� ��ɱ����滻 �� ����
		String tmp = exp.substring(0, exp.length() - 1 ).trim();
		tmp = tmp.replaceAll(" {2,}", " ");
		boolean ret = false;
		Iterator<Entry<String, String>> it;
		Entry<String, String> entry;
		
		if( tmp.indexOf("=") != tmp.lastIndexOf("=") ){
			System.out.println("=�ų���2�� !!");
			return true;
		}
		//�����滻
		tmp = "     " + tmp + "     ";
		int pos = tmp.lastIndexOf("=");
		if( pos < 0 )
			pos = 0;
		it = vartable.entrySet().iterator();
		while(it.hasNext()){
			entry = (Entry<String, String>)it.next();
			int i = tmp.indexOf(entry.getKey());
			if( i > pos ){
				int len  = entry.getKey().length();
				int j = ( tmp.charAt(i) + tmp.charAt( i + len) )>>1;
				if(  j < 'a' || j > 'z' )
					tmp = tmp.substring(0, i) + entry.getValue() + tmp.substring(i+len);
			}
		} 
		tmp = tmp.trim();		
		exp = tmp + "#";		//�滻���
		
		if( tmp.indexOf("=") > 0 ){
			tmp = tmp.replaceAll(" ", "");	//=�ű��ʽ ����Ҫ�ո�
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
				System.out.println("=��ʹ�ô���");
			else{
				if( trtable.get(var) != null || var.equals("ans") || var.matches(".*\\d+.*") )
					System.out.println("������������ !!");
				else{
					if( varexp.indexOf(";") > 0 )
						f_print = false;
					if( !f_print )
						varexp = varexp.substring(0, varexp.length()-1 );
					exp = varexp + "#";
					optimize();
					if( iscorrect() ) {
						varexp = cal();
						vartable.put(var, varexp);
						if( f_print )
							System.out.println( var + " = " + vartable.get(var) );
					}else
						return true;
				}
			}	
		}
		
		if( tmp.length() >= 2 ){
			switch( tmp.substring(0,2) ){
			case "ls":
				ret = true;
				File f = new File(pwd + "\\");
				File list[] = f.listFiles();
				int i;
				for(i = 0; i < list.length; ){
					System.out.printf("%-15s ", list[i].getName());
					if( ++i%4 == 0 )
						System.out.print("\n");
				}
				if( i%4 != 0)
					System.out.print("\n");
				break;
			case "cd":
				ret = true;
				if( tmp.charAt(2) != ' ' && tmp.length() < 4 ){
					System.out.println("cd��������!! ");
					return true;
				}
				String s = tmp.substring(3);
				String tpwd = deal_path(pwd, s);
				if( !isExist(tpwd) ){
					System.out.println("Ŀ¼������ !!");
					break;
				}
				pwd = tpwd;
				if( pwd.lastIndexOf("\\") > 0 )
					PS1 = pwd.substring( pwd.lastIndexOf("\\") + 1 );
				else
					PS1 = pwd.substring(0, 1);
				PS1 = "[ " + PS1 + " ] ";
				break;
			case "rm":
				ret = true;
				if( tmp.charAt(2) != ' ' ){
					System.out.println("��Ҫ���� !!");
					return true;
				}
				String[] vararr = tmp.substring(3).split(" ");
				for(i = 0; i < vararr.length; ++i){
					String dpath = deal_path(pwd, vararr[i]);
					File df = new File(dpath);
					if( !df.exists() ){
						System.out.println("�ļ������� !!");
						break;
					}
					if( df.isDirectory() || !df.isFile() ){
						System.out.println("�ļ����ʹ��� !!");
						break;
					}
					if( !df.delete() ){
						System.out.println("ɾ���ļ����� !!");
						break;
					}
				}
				break;
			}
		}
		
		if( tmp.length() >= 7 && tmp.substring(0, 3).equals("PS1") ){		//��Ϊ�Ѿ�ת��Сд��
			PS1( tmp.substring( 5, tmp.length()-2 ) );
			ret = true;
		}
			
		if( tmp.length() >= 4 ){
			switch(  tmp.substring(0, 4) ){
			case "show":
				ret = true;
				it = vartable.entrySet().iterator();
				System.out.println("������:");
				while(it.hasNext()){
					entry = (Entry<String, String>)it.next();
					System.out.println(entry.toString());
				} 
				break;
			case "save":
				ret = true;
				String[] vararr = tmp.split(" ");
				if( vararr.length < 3 )
					break;
				String spath = vararr[1].replaceAll("/s", " ");
				String var = tmp.substring(4+1+spath.length()+1).replaceAll("/s", " ");
				save(spath, var);
				break;
			case "load":
				ret = true;
				String lpath = tmp.split(" ")[1].replaceAll("/s", " ");
				load(lpath);
				break;
			}
		}
		
		return ret;
	}

	boolean iscorrect() {				//�����ʽ
		exp = "     " + exp + "     ";
		if( exp.matches(".*\\.\\w+\\..*") ){
			System.out.println(".�Ŵ��� !!");
			return false;
		}
		if( exp.matches(".*[^\\d]+\\.[^\\d]+.*") ){
			System.out.println(".�Ŵ��� !!");
			return false;
		}
		if( exp.matches(".*[\\(\\[]+[\\)\\]]+.*") ){
			System.out.println("��������Ϊ�� !!");
			return false;
		}
		if( exp.matches(".*[a-z]+.*") && !exp.matches(".*[a-z]+\\(.*\\).*") ){
			System.out.println("������Ҫ���� !!");
			return false;
		}		
		if( exp.matches("[^\\(]+,[^\\)]+") ){
			System.out.println("���Ŵ��� !!");
			return false;
		}	
		if( exp.matches(".*[^\\)\\d]+[+-/%^\\*]+.*") ){
			System.out.println("������ȷ1 !!");
			return false;
		}		
		if( exp.matches(".*[+-/%^\\*]+[^a-z\\d]+.*") ){
			System.out.println("������ȷ2 !!");
			return false;
		}		
		exp = exp.trim();
		Iterator<Entry<String, Integer>> it = trtable.entrySet().iterator();
		String etmp = exp;
		etmp = etmp.replaceAll("cuberoot", "");
		while(it.hasNext()){
			Entry<String, Integer> entry = (Entry<String, Integer>)it.next();
			if( ((String)entry.getKey()).length() > 1 )
				etmp = etmp.replaceAll((String)entry.getKey(), "");
		}
		if( etmp.matches(".*[a-z]+.*") ){
			System.out.println("���������� !!");
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
			System.out.println("���Ÿ������Ϸ� !!");
			return false;
		}		
		etmp = exp + "     ";
		int cfunc = 0;	//��������ͳ�ƺ�����,��
		int pos1 = 0, pos2 = 0; //������¼ͳ�ƺ���ʼĩ
		for(int i = 0; i < etmp.lastIndexOf("#"); ++i) {
			String tmp = etmp.substring(i, i+3);
			if( cfunc !=0 ){
				if( etmp.charAt(i) == '(' || etmp.charAt(i) == '[' ){
					++cfunc;
				}
				else if( etmp.charAt(i) == ')' || etmp.charAt(i) == ']' ){
					--cfunc;		
					if( cfunc == 1 ){
						if( etmp.charAt(i-1) != ']' ){
							System.out.println("ͳ�ƺ�����Ҫ�������� !!");
							return false;
						}
						--cfunc;
						pos2 = i;
					}
				}
			}
			if( tmp.equals("avg") || tmp.equals("sum") || tmp.equals("var") || tmp.equals("std") ){
				pos1 = i;
				cfunc = 1;
				i += 2;
			}
			if( cfunc == 0 ){
				if( pos1+pos2 != 0 ){
					etmp = etmp.substring(0, pos1) + etmp.substring(pos2+1);
					i = pos1;
					pos1 = pos2 = 0;
				}
				if( tmp.equals("mod") || tmp.equals("pow") || (tmp.equals("roo") && etmp.charAt(i-1) != 'e' ) ){
					++checknumber;
					i += 2;
				}
				if( etmp.charAt(i) == ',' )
					--checknumber;
			}
		}				
		if( checknumber != 0 ){
			System.out.println("���������������� !!");
			return false;
		}
		
		return true;
	}
	void optimize() { 			//�Ż����ʽ
		exp = exp.replaceAll(" " , ""); 	//ȥ�ո�
		exp = "     " + exp + "     ";	//��ֹԽ��
		exp.toLowerCase(); 		//��дתСд
		for(int i = 4; i < exp.lastIndexOf("#"); ++i ) {  //��.��ǰ��0
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
			//	)(��*��
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
//		System.out.println(exp);
	}
	
	public String process(String tmp){		//������ʽ����ֵ
		
		exp = tmp + "#";
		
		if( preprocess() )
			return null;
		
		optimize();
		System.out.println("�Ż���ı��ʽΪ:" + exp);
		if( !iscorrect() ) 
			return null;
		
		return cal();
	}
	
	private String cal(){			//������ʽ����ֵ
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
					if( ctop.equals("/") && i.doubleValue() - 0 <= 0.00000001 ){
						System.out.println("��������Ϊ0 !!");
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
		ans = oprd.pop();
		return ans;
	}
	private char compare(String top, String tr){							//�Ƚ���������ȼ�
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
	private String cal(String tr, String rd1,  String rd2){		//����˫Ŀ�����,�򵥵ı��ദ�����ӵĽ���function��
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
	public void PS1(String format){			//�ı�CONSOLE ÿ������ǰ����ʾ������ "[root /]# "��
		PS1 = format + " ";
	}
	public void save(String file, String var){			//�ṩ����������ļ� 
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
			System.out.println("д�ļ����� !!");
		}
	}
	public void load(String path) {				//�����ļ�����
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
			System.out.println("���ļ����� !!");
		} catch (IOException e) {
			System.out.println("���ļ����� !!");		
		}
	}
}