package com.luck.common.utils;

//import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串操作功能类. <p/> 最后更新：2003-09-01
 * 
 */
public class StringUtil {

	/**
	 * 检验是否非空字符串
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtil.isNull(str).equals("");
	}

	
	public static String fillTemplet(String template, Map<String, Object> sendData) {
		// 模板中的'是非法字符，会导致无法提交，所以页面上用`代替
		template = template.replace('`', '\'');
		try {
			return FreemarkerUtil.renderTemplate(template, sendData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 检验是否为空或空字符串
	 *
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return StringUtil.isNull(str).equals("");
	}

	/**
	 * String to int
	 *
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		if (StringUtil.isBlank(str))
			return 0;
		int ret = 0;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * 字符串替换函数,String的replace函数不能处理'|'符号
	 * 
	 * @param strSource 被替换的源字符串
	 * @param strFrom 要查找并替换的子字符串
	 * @param strTo 要替换为的子字符串
	 * @return 替换完成的字符串
	 */
	public static String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null)
			strSource = "";
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;
		if (strFrom.equals("")) {
			return strSource;
		}
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;
		return strDest;
	}

	/**
	 * 将普通字符串格式化成数据库认可的字符串格式
	 * 
	 * @param input 要格式化的字符串
	 * @return 合法的数据库字符串
	 */
	public static String toSql(String input) {
		if (isEmpty(input)) {
			return "";
		} else {
			return input.replaceAll("'", "''");
		}
	}

	/**
	 * 截取字符串左侧指定长度的字符串
	 * 
	 * @param input 输入字符串
	 * @param count 截取长度
	 * @return 截取字符串
	 */
	public static String left(String input, int count) {
		if (isEmpty(input)) {
			return "";
		}
		count = (count > input.length()) ? input.length() : count;
		return input.substring(0, count);
	}

	/**
	 * 截取字符串右侧指定长度的字符串
	 * 
	 * @param input 输入字符串
	 * @param count 截取长度
	 * @return 截取字符串
	 */
	public static String right(String input, int count) {
		if (isEmpty(input)) {
			return "";
		}
		count = (count > input.length()) ? input.length() : count;
		return input.substring(input.length() - count, input.length());
	}

	/**
	 * 从指定位置开始截取指定长度的字符串
	 * 
	 * @param input 输入字符串
	 * @param index 截取位置，左侧第一个字符索引值是1
	 * @param count 截取长度
	 * @return 截取字符串
	 */
	public static String middle(String input, int index, int count) {
		if (isEmpty(input)) {
			return "";
		}
		count = (count > input.length() - index + 1) ? input.length() - index
				+ 1 : count;
		return input.substring(index - 1, index + count - 1);
	}

	/**
	 * Unicode转换成GBK字符集
	 * 
	 * @param input 待转换字符串
	 * @return 转换完成字符串
	 */
	public static String UnicodeToGB(String input)
			throws UnsupportedEncodingException {
		if (isEmpty(input)) {
			return "";
		} else {
			String s1;
			s1 = new String(input.getBytes("ISO8859_1"), "GBK");
			return s1;
		}
	}

	/**
	 * GBK转换成Unicode字符集
	 * 
	 * @param input 待转换字符串
	 * @return 转换完成字符串
	 */
	public static String GBToUnicode(String input)
			throws UnsupportedEncodingException {
		if (isEmpty(input)) {
			return "";
		} else {
			String s1;
			s1 = new String(input.getBytes("GBK"), "ISO8859_1");
			return s1;
		}
	}

	/**
	 * 分隔字符串成数组. <p/> 使用StringTokenizer，String的split函数不能处理'|'符号
	 * 
	 * @param input 输入字符串
	 * @param delim 分隔符
	 * @return 分隔后数组
	 */
	public static String[] splitString(String input, String delim) {
		if (isEmpty(input)) {
			return new String[0];
		}
		ArrayList<String> al = new ArrayList<String>();
		for (StringTokenizer stringtokenizer = new StringTokenizer(input, delim); stringtokenizer
				.hasMoreTokens(); al.add(stringtokenizer.nextToken())) {
		}
		String result[] = new String[al.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (String) al.get(i);
		}
		return result;
	}

	/**
	 * 判断字符串数组中是否包含某字符串元素
	 * 
	 * @param substring 某字符串
	 * @param source 源字符串数组
	 * @return 包含则返回true，否则返回false
	 */
	public static boolean isIn(String substring, String[] source) {
		if (source == null || source.length == 0) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断字符是否为空
	 * 
	 * @param input 某字符串
	 * @return 包含则返回true，否则返回false
	 */
	public static boolean isEmpty(String input) {
		return input == null || input.length() == 0;
	}

	/**
	 * 获得0-9的随机数
	 * 
	 * @param length
	 * @return String
	 */
	public static String getRandomNumber(int length) {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < length; i++) {
			buffer.append(random.nextInt(10));
		}
		return buffer.toString();
	}

	/**
	 * 获得0-9,a-z,A-Z范围的随机数
	 *
	 * @param length 随机数长度
	 * @return String
	 */

	public static String getRandomChar(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(62)]);
		}
		return buffer.toString();
	}

	/**
	 * 获得0-F范围的随机数
	 *
	 * @param length 随机数长度
	 * @return String
	 */

	public static String getColorRandomChar(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(16)]);
		}
		return buffer.toString();
	}

	/**
	 * 过滤Html
	 *
	 * @param input
	 */
	public static String filterHTML(String input) {
		StringBuffer filtered = new StringBuffer();
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '&':
				filtered.append("&amp;");
				break;
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&#034;");
				break;
			case '\'':
				filtered.append("&#039;");
				break;
			default:
				filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	/**
	 * 替换html符号
	 * @param str
	 * @return
	 */
	public static String toHtml(String str) {
		if (str == null || "".equals(str))
			return "";
		str = StringUtil.replace(str, "&amp;", "&");
		str = StringUtil.replace(str, "&lt;", "<");
		str = StringUtil.replace(str, "&gt;", ">");
		str = StringUtil.replace(str, "&#034;", "\"");
		str = StringUtil.replace(str, "&#039;", "\'");
		return str;
	}

	/**
	 * 替换字符
	 *
	 * @param str
	 * @param regex
	 * @param replacement
	 */
	public static String replaceAll(String str, String regex, String replacement) {
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return str;
		}
		if (regex == null || regex.compareTo("null") == 0) {
			return str;
		}
		if (replacement == null || replacement.compareTo("null") == 0) {
			return str;
		}
		try {
			int iIndex, iFromIndex;
			String stmp = new String();
			int iLen = regex.length();
			iFromIndex = 0;
			iIndex = str.indexOf(regex, iFromIndex);
			stmp = "";
			while (iIndex >= 0) {
				stmp = stmp + str.substring(iFromIndex, iIndex) + replacement;
				str = str.substring(iIndex + iLen);
				iIndex = str.indexOf(regex, iFromIndex);
			}
			stmp = stmp + str;
			return stmp;
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * 检测字符串长度
	 *
	 * @param str
	 * @return
	 */
	static public int length(String str) {
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return 0;
		}
		int enLen = 0;
		int chLen = 0;
		char ch = ' ';
		Character CH = new Character(' ');
		int iValue = 0;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			CH = new Character(ch);
			iValue = CH.charValue();
			if (iValue < 128) {
				enLen++;
			} else {
				chLen++;
			}
		}
		return (enLen + chLen * 2);
	}

	/**
	 * 提取字符
	 *
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 */
	public static String substring(String str, int beginIndex, int endIndex) {
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return "";
		}
		String rtsValue = null;
		int enLen = 0;
		int chLen = 0;
		char ch = ' ';
		Character CH = new Character(' ');
		int iValue = 0;
		int iLength = 0;
		int realBegin = 0;
		int realEnd = 0;
		int i = 0;
		while (iLength < beginIndex) {
			ch = str.charAt(i);
			CH = new Character(ch);
			iValue = CH.charValue();
			if (iValue < 128) {
				enLen++;
			} else {
				chLen++;
			}
			iLength = enLen + chLen * 2;
			i++;
		}
		realBegin = enLen + chLen;
		i = realBegin;
		while (iLength < endIndex) {
			ch = str.charAt(i);
			CH = new Character(ch);
			iValue = CH.charValue();
			if (iValue < 128) {
				enLen++;
			} else {
				chLen++;
			}
			iLength = enLen + chLen * 2;
			i++;
		}
		realEnd = enLen + chLen;
		rtsValue = str.substring(realBegin, realEnd);
		return rtsValue;
	}

	/**
	 * 如果数组str中有一个参数为空或null，则返回true
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNullEx(String str[]) {
		if (str == null)
			return true;
		for (int i = 0; i < str.length; i++) {
			if (isEmpty(str[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 如果数组str中所有参数为空或null，则返回true
	 *
	 * @param str
	 * @return
	 */
	public static boolean isAllNull(String str[]){
		if (str == null)
			return true;
		for (int i = 0; i < str.length; i++) {
			if (!isEmpty(str[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 在数字前面补零. <p/> 在数值前面补零，整个字符串达到固定长度，主要用于银行帐号，单据编号等
	 *
	 * @param num 转换的数值
	 * @param length 使整个串达到的长度
	 * @return 数值前面补零的固定长度的字符串
	 */
	public static String appendZero(int num, int length) {
		String tmpString = String.valueOf(num);
		for (int i = tmpString.length(); i < length; i++) {
			tmpString = "0" + tmpString;
		}
		return tmpString;
	}
	/**
	 *  在字符前补充字符
	 * @param num 原始字符串
	 * @param length 总的长度
	 * @param szVal 需要补充的字符
	 * @return 补充后的字符
	 */
	public static String appendChar(String num, int length, char szVal){
		if(num == null)
			return null;

		for (int i = num.length(); i < length; i++) {
			num = szVal + num;
		}

		return num;
	}
	/**
	 *  在字符后补充字符
	 * @param num 原始字符串
	 * @param length 总的长度
	 * @param szVal 需要补充的字符
	 * @return 补充后的字符
	 */
	public static String appendCharLast(String num, int length, char szVal){
		if(num == null)
			return null;

		for (int i = num.length(); i < length; i++) {
			num = num + szVal;
		}

		return num;
	}

	/**
	 * 根据工程师类型(默认为0), 组编号, 工程师工号,算出工程师id 工程师类型放在最位,后16位放工程师工号,中间15位放组编号
	 * @param type
	 * @param groupno
	 * @param muserno
	 * @return
	 */
	public static int getmuserId(Integer type, Integer groupno, Integer muserno) {
		int muserid = 0;
		muserid = muserid | (type & 1) << 31;
		muserid = muserid | (groupno & 0x7FFF) << 16;
		muserid = muserid | (muserno & 0xFFFF);
		return muserid;
	}

	/**
	 * 测试方法
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		boolean isOk = isNumeric("1231.1");
		if(isOk){
			System.out.println("true");
		}else{
			System.out.println("false");
		}

	}

	/**
	 * 字符串空格验证，如果input前后有空格,自动处理
	 *
	 * @param input
	 * @return
	 */
	public static String trimStr(String input) {
		if (input != null) {
			return input.trim();
		}
		return "";
	}

	/**
	 * 获得服务卡密码
	 *
	 * @param length 随机数长度
	 * @return String
	 */

	public static String getRandomCardPwd(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
				'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(34)]);
		}
		return buffer.toString();
	}

	/**
	 * 获取指定长度的随机数字串
	 *
	 * @param length 随机数长度
	 * @return String
	 */

	public static String getRandomNumeric(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(10)]);
		}
		return buffer.toString();
	}

	/**
	 * 用JAVA自带函数 判断是否为数字
	 *
	 * @param str
	 * @return false:不是数字 true:是数字
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 用正则表达式 判断是否为数字
	 *
	 * @param str
	 * @return false:不是数字 true:是数字
	 */
	public static boolean isNumericByMatch(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 用ascii码 判断是否为数字
	 *
	 * @param str
	 * @return false:不是数字 true:是数字
	 */
	public static boolean isNumericByASCII(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 获取用户IP地址
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request){
		String ip = request.getRemoteAddr();
		//ClientLog.levelLog("GET_IP", "getRemoteAddr_IP="+ip);
		return ip.trim();
	}

	/**
	 * 将IP地址拼成LONG型字符串
	 * @param userip
	 * @return
	 */
	public static Long IpConvertToLong(String userip) throws Exception{
		String[] ip = StringUtil.splitString(userip, ".");
		userip = "";
		for(int i = 0; i<ip.length; i++){
			ip[i] = StringUtil.appendZero(Integer.parseInt(ip[i]), 3);
			userip +=ip[i];
		}
		return Long.parseLong(userip);
	}

	/**
	 * 随机色彩
	 *
	 * @param length 随机数长度
	 * @return String
	 */

	public static String getRandomColor(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(16)]);
		}
		return buffer.toString();
	}
	/**
	 * 将String 转化为byte数据
	 * @param inputStr
	 * @return
	 */
	public static byte[] stringToBytes(String inputStr){
		if(inputStr == null){
			return null;
		}
		int len = inputStr.length();
		
		byte b[] = new byte[len];
		for(int i = 0; i < len; i++){
			b[i] = (byte)inputStr.charAt(i);
		}

		return b;
	}
	
	
	/**
	 * 将对象转为字符串
	 * 
	 * @param o
	 * @return
	 */
	public static String isNull(Object o) {
		if (o == null) {
			return "";
		}
		String str = "";
		if (o instanceof String) {
			str = (String) o;
		} else {
			str = o.toString();
		}
		return str.trim();
	}
	/**
	 * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
	 * 
	 * @param str
	 * @return
	 */
	public static String isNullReturnStr(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static List<String> stringMatch(String string,String regex){
		List<String> msList = new ArrayList<>();
 		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(string);
		while (matcher.find()) {
			msList.add(matcher.group());
		}
		return msList;
	}
	
	/**
	 * 使用特殊符号屏蔽字符串
	 * @param targetValue 需要屏蔽的字符串
	 * @param sign 屏蔽的符号
	 * @param startIndex 屏蔽的起始位置
	 * @param endIndex 屏蔽的结束位置
	 * @return
	 */
	public static String dealStringWithSign(String targetValue, String sign, int startIndex, int endIndex) {
		if(StringUtils.isNotBlank(targetValue)) {
			if(targetValue.length()-1 < startIndex || targetValue.length() < endIndex) {//不符合截取规则的直接返回原字符串
				return targetValue;
			}
			String targetStr = targetValue.substring(startIndex, endIndex);
			String signStr = "";
			for(int i=0; i<targetStr.length(); i++) {
				signStr+=sign;
			}
			targetStr = targetValue.substring(0,startIndex)+signStr+targetValue.substring(endIndex,targetValue.length());
			return targetStr;
		}else {
			return null;
		}
	}
	
	/**
	 * 需要屏蔽的字符串是一个集合，以逗号分隔并以字符串形式存在
	 * @param targetValue 逗号分隔的字符串
	 * @param sign 屏蔽标识符
	 * @param startIndex 屏蔽的开始位置
	 * @param endIndex 尾部保留位数
	 * @return
	 */
	public static String dealArrayWithSign(String targetValue, String sign, int startIndex, int endIndex) {
		StringBuilder sb = new StringBuilder();
		String[] targetValueArray = targetValue.split(",");
		String dealStr;
		for (String targetVal : targetValueArray) {
			dealStr = StringUtil.dealStringWithSign(targetVal, sign, startIndex, targetVal.length()-endIndex);
			if(StringUtils.isNotBlank(dealStr)) {
				sb.append(dealStr).append(",");
			}
		}
		if(sb.length()>0) {
			return sb.substring(0, sb.length()-1);
		}
		return null;
	}

	/**
	 * String to Long
	 * 
	 * @param str
	 * @return
	 */
	public static long toLong(String str) {
		if (StringUtil.isBlank(str))
			return 0L;
		long ret = 0;
		try {
			ret = Long.parseLong(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	
	/**
	 * 根据身份证Id获取性别
	 * @param cardId
	 * @return
	 */
	public static int getSex(String cardId) {
		int sexNum = 0;
		//15位的最后一位代表性别，18位的第17位代表性别，奇数为男，偶数为女
		if (cardId.length() == 15) {
			sexNum = cardId.charAt(cardId.length() - 1);
		} else {
			sexNum = cardId.charAt(cardId.length() - 2);
		}
		
		if (sexNum % 2 == 1) {
			return 1;
		} else {
			return 0;
		}
	}



	public static boolean isBlank(Object o) {
		return StringUtil.isNull(o).equals("");
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstCharUpperCase(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();
	}
    public static String objToString(Object obj) {
		return (obj == null|| ""== obj) ? null : obj.toString();
    }


	/**
	 * 入参String转化成boolean，如何为空，默认false
	 * @param obj
	 * @return
	 */
	public static boolean toBoolean(Object obj){
		if (obj == null|| ""== obj){
			return false;
		}
        if ("true".equals(obj.toString())){
			return true;
		}
		return false;
	}

	/**
	 * 匹配汉字
	 * @param str
	 * @return
	 */
	public static boolean formatCN(String str){
		String regEx = "[\\u4e00-\\u9fa5]+";

		Pattern p = Pattern.compile(regEx);

		Matcher m = p.matcher(str);

        if(m.matches()){
             return true;
		}
		return false;
	}


}
