package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class cal {
	private Stack<String> oprd,optr; 		//��Ų������Ͳ�����
	private String ans;								//Ĭ�Ͻ�����
	private String[] var;								//�������飬����MAPʵ��
	private int varbit;								//λ��Ӧ���� �жϸ�λ��VAR����Ԫ���Ƿ�ʹ��
	private enum priority{							//��������ȼ�
		GREAT, EQU, LESS
	}
	private int varcount;			//��ǰ������
	private String PS1 = ">> ";
	private Map<String, Integer> trtable;		//����������ȼ���˳������ж��Ƿ�֧��
	
	public cal(){
		init();
	}
	public void init(){			//���³�ʼ������
		oprd = new Stack<String>();
		optr = new Stack<String>();
		var = new String[100];
		optr.push("#");		
		varbit = 0;
		varcount = 1;
		trtable = new  HashMap<String, Integer>();
		trtable.put("+", 0);
		trtable.put("-", 0);		
		trtable.put("*", 1);	
		trtable.put("/", 1);	
		trtable.put("^", 2);	
		trtable.put("mod", 2);
		trtable.put("sin", 2);
		trtable.put("cos", 2);
		trtable.put("tan", 2);
		trtable.put("arcsin", 2);
		trtable.put("arccos", 2);
		trtable.put("arctan", 2);
		trtable.put("sinh", 2);
		trtable.put("cosh", 2);
		trtable.put("tanh", 2);
		trtable.put("log", 2);
		trtable.put("log10", 2);
		trtable.put("ln", 2);
		trtable.put("pow", 2);
		trtable.put("exp", 2);
		trtable.put("fact", 2);
		trtable.put("sqrt", 2);
		trtable.put("cuberoot", 2);
		trtable.put("yroot", 2);
		trtable.put("avg", 2);
		trtable.put("sum", 2);
		trtable.put("var", 2);
		trtable.put("varp", 2);
		trtable.put("stdev", 2);
		trtable.put("stdevp", 2);
		trtable.put("(", 3);	
		trtable.put(")", 3);	
		trtable.put("#", 4);			
	}
	String process(String a){}		//������ʽ����ֵ
	String get(){							//����ansֵ
		if( !ans.isEmpty() )
			return ans;
		return null;
	}	
	priority compare(String tr1, String tr2){}							//�Ƚ���������ȼ�
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
