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
		trtable.put("(", 4);	
		trtable.put(")", 4);		
	}
	String process(String tmp){		//������ʽ����ֵ

		boolean f_digit;
		boolean f_change = false;
		int head = 0, tail = 0;
		
		optr.push("#");			
		exp = tmp + "#";

		if( !iscorrect() )
			return null;
		if( exp.charAt(0) <= '9' )
			f_digit = true;
		else
			f_digit = false;
		for(int i = 0; i < exp.length(); ++i){
			char c = exp.charAt(i);

			if( c == ' ' || c == '(' )
				f_change = true;
			if( !f_change && c == '#' )
				f_change = true;
			if( !f_change && c == '[' ){
				for(int j = i+1; j < exp.length(); ++j)
					if( exp.charAt(j) == ']' ){
						head = i + 1;
						tail = j;
						i = j + 2;
						break;
					}
				oprd.push( exp.substring(head, tail) );
				f_digit = true;
				tail = i;
			}
			if( !f_change && c == '^' )
				f_change = true;
			if( !f_change && f_digit && ( c < '0'	 || c >= 'A' ) )
				f_change = true;
			if( !f_change && !f_digit && c <= '9' )
				f_change = true;

			if( f_change ){
				head = tail;
				tail = i;
				if( f_digit )
					oprd.push( exp.substring(head, tail) );
				else
					optr.push( exp.substring(head, tail) );
				if( c == ' ')
					++tail;
				f_digit = !f_digit;
				f_change = false;
			}
		}
		System.out.println("OPRD:");
		while( !oprd.isEmpty() )
			System.out.println(oprd.pop());
		System.out.println("OPTR:");
		while( !optr.isEmpty() )
			System.out.println(optr.pop());
		
		return null;
	}
	void trim(){}				//ȥ����ո�
	String get(){							//����ansֵ
		if( !ans.isEmpty() )
			return ans;
		return null;
	}	
	char compare(String top, String tr){							//�Ƚ���������ȼ�
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
	private String cal(String tr, String rd){}							//���㵥Ŀ�����
	private String cal(String tr, String rd1,  String rd2){}		//����˫Ŀ�����
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
