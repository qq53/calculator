package calculator;

import java.util.Stack;

public class cal {
	private Stack<String> oprd,optr; 		//��Ų������Ͳ�����
	private String ans;								//Ĭ�Ͻ�����
	private String[] var;								//�������飬����MAPʵ��
	private int varbit;								//λ��Ӧ���� �жϸ�λ��VAR����Ԫ���Ƿ�ʹ��
	private enum priority{							//��������ȼ�
		GRE, EQU, LESS
	}
	private int varcount;			//��ǰ������
	private String PS1 = ">> ";
	
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
	}
	void process(String a){}		//������ʽת�ɺ�׺
	String get(){							//����ansֵ
		if( !ans.isEmpty() )
			return ans;
		return null;
	}	
	priority compare(String tr1, String tr2){}			//�Ƚ���������ȼ�
	String cal(String tr, String rd){}						//���㵥Ŀ�����
	String cal(String tr, String rd1,  String rd2){}	//����˫Ŀ�����
	boolean check(String exp){}							//�����ʽ���
	
//�����Ǹ߼����ڹ���
	void PS1(String format){}			//�ı�CONSOLE ÿ������ǰ����ʾ������ "[root /]# "��
	Boolean save(String path){}		//�ṩ����������ļ�
	String load(String path){}			//���ض�Ӧ����
	int getidx(String name){}			//��ȡ��������
	void changevar(int idx){}			//�޸ı���ֵ
	String Multcal(String exp){}		//ʵ��ͬ��������̼߳��㡣���������Ʋ���Ū
}
