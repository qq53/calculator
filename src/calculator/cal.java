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
	String process(){		//������ʽ����ֵ
		optr.push("#");	
		sy
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
	private boolean iscorrect(String exp){}							//�����ʽ���
	
//�����Ǹ߼����ڹ���
	void PS1(String format){}			//�ı�CONSOLE ÿ������ǰ����ʾ������ "[root /]# "��
	Boolean save(String path){}			//�ṩ����������ļ�
	String load(String path){}				//���ض�Ӧ����
	int getidx(String name){}				//��ȡ��������
	void changevar(int idx){}				//�޸ı���ֵ
	String Multcal(String exp){}			//ʵ��ͬ��������̼߳��㡣���������Ʋ���Ū
}
