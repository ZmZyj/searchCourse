package com.miaozhen.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;

/** 
* 数学计算相关方法
* 
* @author zhangmin@miaozhen.com
* @date 2014-12-18 下午4:33:03 
* 
*/ 
public class MathUtil {


	// 默认除法运算精度
	private static final int DEFAULT_DIV_SCALE = 4;
	
	// 百分比除法运算精度
	private static final int PERCENT_DIV_SCALE = 2;
	
	/** 
	* 获取百分比运算结果，结果保留到整数位
	* 
	* @param v1
	* @param v2
	* @return
	*/ 
	public static String getPercent(double v1, double v2) {
		if (0.0 == v2){
			return "100%";
		}
		double result = divide(v1, v2, PERCENT_DIV_SCALE) * 100;
		String resultStr = String.valueOf(result);
		if(resultStr.length() - resultStr.lastIndexOf(".") > 1){
			resultStr = resultStr.substring(0, resultStr.lastIndexOf("."));
		}
		return resultStr + "%";
	}
	
	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的商
	 */

	public static double divide(double v1, double v2){
		return divide(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @param scale
	 *            表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 */

	public static double divide(double v1, double v2, int scale){
		if (scale < 0) {
	          throw new IllegalArgumentException(
	              "The scale must be a positive integer or zero");
	      }
	      BigDecimal b1 = new BigDecimal(Double.toString(v1));
	      BigDecimal b2 = new BigDecimal(Double.toString(v2));
	      return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @param scale
	 *            表示需要精确到小数点以后几位
	 * 
	 * @param round_mode
	 *            表示用户指定的舍入模式
	 * 
	 * @return 两个参数的商
	 */

	public static double divide(double v1, double v2, int scale, int round_mode) {
		if (scale < 0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, round_mode).doubleValue();

	}

	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的商，以字符串格式返回
	 */
	public static String divide(String v1, String v2){
		return divide(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @param scale
	 *            表示需要精确到小数点以后几位
	 * 
	 * @return 两个参数的商，以字符串格式返回
	 */

	public static String divide(String v1, String v2, int scale){
		return divide(v1, v2, DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @param scale
	 *            表示需要精确到小数点以后几位
	 * 
	 * @param round_mode
	 *            表示用户指定的舍入模式
	 * 
	 * @return 两个参数的商，以字符串格式返回
	 */

	public static String divide(String v1, String v2, int scale, int round_mode){
		if (scale < 0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, round_mode).toString();

	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale){
		return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @param round_mode
	 *            指定的舍入模式
	 * 
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale, int round_mode){
		if (scale < 0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		return b.setScale(scale, round_mode).doubleValue();
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果，以字符串格式返回
	 */
	public static String round(String v, int scale){
		return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @param round_mode
	 *            指定的舍入模式
	 * 
	 * @return 四舍五入后的结果，以字符串格式返回
	 */
	public static String round(String v, int scale, int round_mode){
		if (scale < 0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		return b.setScale(scale, round_mode).toString();
	}
	
	/**
	 * 得到n位长度的随机数
	 * @param n 随机数的长度
	 * @return 返回  n位的随机整数
	 */
	public static int getRandomNumber(int n){
		int temp = 0;
		int min = (int) Math.pow(10, n-1);
		int max = (int) Math.pow(10, n);
		Random rand = new Random();
		
		while(true){
			temp = rand.nextInt(max);
			if(temp >= min)
				break;
		}
		
		return temp;
	}
	
	/** 
	* 对数据进行千分位的格式化
	* 
	* @param num
	* @return
	*/ 
	public static String format(Integer num){
		return NumberFormat.getNumberInstance().format(num);
	}
	
	public static Integer nullToZeroNum(Integer num){
		if(null == num){
			return 0;
		}
		return num;
	}

}
