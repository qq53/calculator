package calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class function {
	public String cal(String name, String rd1, String rd2){	//双变量函数
		//System.out.println(rd1 + name + rd2);
		BigDecimal brd1 = new BigDecimal(rd1);
		BigDecimal brd2 = new BigDecimal(rd2);	
		Double d1, d2;	
		switch( name ){
		case "log":
			d1 = new Double(brd1.doubleValue());
			d2 = new Double(brd2.doubleValue());			
			d1 = Math.log(d1);
			d2 = Math.log(d2);
			d1 /= d2;
			brd1 = new BigDecimal(d1);		
			break;
		case "pow":
			if( brd2.toString().indexOf(".") > 0){			
				d1 = new Double(brd1.doubleValue());
				d2 = new Double(brd2.doubleValue());	
				d1 = Math.pow(d1, d2);
				brd1 = new BigDecimal(d1);	
			}
			else
				brd1 = brd1.pow( brd2.intValue() );
			break;
		case "mod":
			brd1 = brd1.remainder(brd2);
			break;
		case "root":
			BigDecimal bone = new BigDecimal("1");
			bone = bone.divide(brd2, 2, RoundingMode.DOWN);
			brd1 = new BigDecimal( cal("pow", rd1, bone.toString()) );
			break;
		}
		return brd1.toString();
	}
	public String cal(String name, String rd){	//处理单变量
		//System.out.println(name + " " + rd);
		Double d = new Double(rd);
		String ret = "";
		BigDecimal bd1, bd2, bn1;
		switch( name ){
		case "sin":
			d = Math.sin(d);
			break;
		case "cos":
			d = Math.cos(d);
			break;
		case "tan":
			d = Math.tan(d);
			break;
		case "arcsin":
			d = Math.asin(d);
			break;
		case "arccos":
			d = Math.acos(d);
			break;
		case "arctan":
			d = Math.atan(d);
			break;
		case "sinh":
			d = Math.sinh(d);
			break;
		case "cosh":
			d = Math.cosh(d);
			break;
		case "tanh":
			d = Math.tanh(d);
			break;
		case "lg":
			d = Math.log10(d);
			break;
		case "ln":
			d = Math.log(d);
			break;
		case "exp":
			d = Math.exp(d);
			break;
		case "fact":
			bd1 = new BigDecimal(rd);
			bd2 = new BigDecimal(rd);			
			bn1 = new BigDecimal("-1");			
			Integer i = new Integer(rd);
			while( --i > 1 ){
				bd2 = bd2.add(bn1);
				bd1 = bd1.multiply(bd2);			
			}
			ret = bd1.toString();
			break;
		case "sqrt":
			d = Math.sqrt(d);
			break;
		case "cuberoot":
			double d1 = 1.0/3;
			d = Math.pow(d, d1);
			break;
		}
		if( ret.length() < 1 )
			ret = d.toString();
		return ret;
	}	
	public String cal(String name, String rd, int varsum){		//处理向量函数
		//System.out.println(name + "[" + rd + "]");	
		BigDecimal bd1 = new BigDecimal("0");
		BigDecimal bd2 = new BigDecimal("0");
		int i;
		switch( name ){
		case "sum":
			for(i = 1; i < rd.length(); ++i){
				if( rd.charAt(i) == ',' ){
						bd2 = new BigDecimal(rd.substring(0, i));
						rd = rd.substring(i+1);
						i = 0;
						bd1 = bd1.add(bd2);
				}
			}
			break;
		case "avg":
			bd1 = new BigDecimal(cal("sum", rd, varsum));
			bd2 = new BigDecimal(varsum);
			bd1 = bd1.divide(bd2);
			break;
		case "sumavg":
			BigDecimal bavg = new BigDecimal( cal("avg",rd,varsum) );		
			bavg = bavg.negate();
			for(i = 1; i < rd.length(); ++i){
				if( rd.charAt(i) == ',' ){
						bd2 = new BigDecimal(rd.substring(0, i));
						rd = rd.substring(i+1);
						i = 0;
						bd2 = bd2.add(bavg);
						bd2 = bd2.pow(2);
						bd1 = bd1.add(bd2);
				}
			}
			break;	
		case "var":
			bd1 = new BigDecimal( cal("sumavg",rd,varsum) );
			bd2 = new BigDecimal( varsum );	
			BigDecimal bone = new BigDecimal("-1");
			bd2 = bd2.add(bone);
			bd1 = bd1.divide(bd2, 2, RoundingMode.DOWN);
			break;	
		case "varp":
			bd1 = new BigDecimal( cal("sumavg",rd,varsum) );
			bd2 = new BigDecimal( varsum );	
			bd1 = bd1.divide(bd2, 2, RoundingMode.DOWN);
			break;	
		case "stdev":
			bd1 = new BigDecimal( cal("var",rd,varsum) );
			bd1 = new BigDecimal( Math.sqrt(bd1.doubleValue()) );
			break;	
		case "stdevp":
			bd1 = new BigDecimal( cal("varp",rd,varsum) );
			bd1 = new BigDecimal( Math.sqrt(bd1.doubleValue()) );
			break;
		}
		return bd1.toString();
	}
}