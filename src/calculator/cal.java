package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class cal {
	private Stack<String> oprd,optr; 		//��Ų������Ͳ�����
	private String ans;								//Ĭ�Ͻ�����
	private String[] var;								//�������飬����MAPʵ��
	private int varbit;								//λ��Ӧ���� �жϸ�λ��VAR����Ԫ���Ƿ�ʹ��
	private int varcount;							//��ǰ������
	private String PS1 = ">> ";
	private Map<String, Integer> trtable;		//����������ȼ���˳������ж��Ƿ�֧��
	private String exp;
	private String basetr = "+-*/%^()#,[]";
	
	public cal(){
		init();
	}
	public void init(){			//���³�ʼ������
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
	}
	String process(String tmp){			//������ʽ����ֵ
		int head = 0, tail = 0;
		String c;
		
		optr.push("#");			
		exp = tmp + "#";

		if( !iscorrect() )
			return null;
		for(int i = 0; i < exp.length(); ++i){
			c = exp.substring(i, i+1);
			
			if( basetr.indexOf(c) >= 0 ){
				head = tail;
				tail = i;		
				if( exp.charAt(head) >= 'a' )
					optr.push( exp.substring(head, tail) );
				else if ( head != tail )
					oprd.push( exp.substring(head, tail) );
				optr.push(c);
				++tail;
			}
		}
		optr.pop();
	/*	System.out.println("OPRD:");
		while( !oprd.isEmpty() )
			System.out.println(oprd.pop());
		System.out.println("OPTR:");
		while( !optr.isEmpty() )
			System.out.println(optr.pop());*/
		
		c = optr.pop();
		String tmp2, var1 ="", var2 ="", ctop;
		boolean f_vector = false;
		while( !optr.isEmpty() ){
			ctop = optr.peek();
			if(ctop == "]"){
				optr.pop();
				f_vector = true;
			}
			if( ctop == "[" ){
				optr.pop();
				var1 += oprd.pop() + ",";
				f_vector = false;
			}
			if(c == ","){
				optr.pop();
				if( f_vector )
					var1 += oprd.pop() + ",";
				else
					var2 = oprd.pop();
			}
			switch( compare( ctop, c ) ){
			case '<':
				tmp2 = c;
				c = optr.pop();
				optr.push(tmp2);
				continue;
			case '=':
				optr.pop();
				break;
			case '>':
				if( ctop.length() > 1 ){
					var1 = oprd.pop();
					if( var.length > 0 )
						tmp2 = cal(ctop, var1, var2);
					else
						tmp2 = cal(ctop, var1);
					oprd.push(tmp2);
				}else
					tmp2 = cal(ctop, oprd.pop(), oprd.pop());
				oprd.push(tmp2);
				break;
			}
			c = optr.pop();
		}
		
		return null;
	}
	String get(){		//����ansֵ
		if( !ans.isEmpty() )
			return ans;
		return null;
	}	
	char compare(String top, String tr){							//�Ƚ���������ȼ�
		char c = top.charAt(0);
		char c1 = tr.charAt(0);
		
		if ( c == '(' ){
			if( c1 == ')' )
				return '=';
			return '<';
		}
		if( c == ')' )
			return '>';
		if ( c1 == ')' ){
			if( c == '(' )
				return '=';
			return '>';
		}
		if( c1 == '(' )
			return '<';
		if ( c == '[' ){
			if( c1 == ']' )
				return '=';
			return '<';
		}
		if( c == ']' )
			return '>';	
		if ( c1 == ']' ){
			if( c == '[' )
				return '=';
			return '>';
		}
		if( c1 == '[' )
			return '<';	
		
		int i1 = trtable.get(top);
		int i2 = trtable.get(tr);
		if( i1 == i2){
			if(c == '#')
				return '=';
			return '>';
		}else
			return i1 > i2?'>':'<';
	}
	private String cal(String tr, String rd){}							//���㵥Ŀ�����
	private String cal(String tr, String rd2,  String rd1){		//����˫Ŀ�����
		System.out.println(rd1 + tr + rd2);
		return null;
	}
	private boolean iscorrect(){											//�����ʽ���
		
		return true;
	}
	
//�����Ǹ߼����ڹ���
	void PS1(String format){}			//�ı�CONSOLE ÿ������ǰ����ʾ������ "[root /]# "��
	Boolean save(String path){}			//�ṩ����������ļ�
	String load(String path){}				//���ض�Ӧ����
	int getidx(String name){}				//��ȡ��������
	void changevar(int idx){}				//�޸ı���ֵ
	String Multcal(String exp){}			//ʵ��ͬ��������̼߳��㡣���������Ʋ���Ū
}
