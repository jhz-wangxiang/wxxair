package com.efrobot.weixin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;



public class StringUtil {
	private static char[] couponCode={
		    'A', 'B', 'C', 'D', 'E',
		    'F', 'G', 'H', 'J', 'K',
		    'L', 'M', 'N', 'P', 'Q', 
		    'R', 'S', 'T', 'U', 'V',
		    'W', 'X', 'Y', 'Z', 
		    '0', '1', '2', '3', '4',
		    '5', '6', '7', '8', '9' }; 
	/** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param byteArray 
     * @return 
     */  
	public static String byteToStr(byte[] byteArray) {  
        String strDigest = "";  
        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  
        return strDigest;  
    }  
  
    /** 
     * 将字节转换为十六进制字符串 
     *  
     * @param mByte 
     * @return 
     */  
	public static String byteToHexStr(byte mByte) {  
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
  
        String s = new String(tempArr);  
        return s;  
    }  
	/**
	 * 获取10元投票激活码
	 * TemplateUtil.getRegisterMobileCode()<BR>
	 * <P>Author :  yangjj </P>  
	 * <P>Date : 2015-11-17 </P>
	 * @return
	 */
	public static String getCouponCode(){
		StringBuilder sb = new StringBuilder();
		Random random=new Random(); 
		for(int i=0; i<8; i++){
			sb.append(couponCode[random.nextInt(34)]);
		}
		return sb.toString();
	}
	/**
	 * 
	 * @Description
	 *
	 * @param date
	 *            鏃ユ湡
	 * @param format
	 *            鏃ユ湡鏍煎紡(鍙互浣跨敤DateUtil.DateStyle鎻愪緵鐨勬灇涓惧��),濡傛灉浼爊ull鍊兼垨鑰�""鍒欓粯璁yyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 */
	public static String date2String(Date date, String format) throws Exception {
		SimpleDateFormat sdf;
		if (StringUtils.isEmpty(format)) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdf = new SimpleDateFormat(format);
		}
		return sdf.format(date);
	}
	/**
	 * 获取TimeStamp
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		Random rand = new Random();
		return "wx"+System.currentTimeMillis()+""+rand.nextInt(1000000);
	}
	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getRobotNonceStr() {
		Random rand = new Random();
		return "cz"+System.currentTimeMillis()+""+rand.nextInt(1000000);
	}
	/**
	 * 判断字符串都是数字
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
    /**
     * 检测是否有emoji字符
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if ( "".equals(source) && null == source ) {
            return false;
        }
        
        int len = source.length();
        
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            
            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }
        
        return false;
    }


    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || 
                (codePoint == 0x9) ||                            
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
    
    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;
        
        int len = source.length();
        
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                
                buf.append(codePoint);
            } else {
            }
        }
        
        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
        
    }
    /**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String getSHA1(String token, String timestamp, String nonce, String open_id){
		StringBuffer hexstr = new StringBuffer();
		try {
			String[] array = new String[] { token, timestamp, nonce, open_id };
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < 4; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
		} catch (Exception e) {
			e.printStackTrace();//return "sha加密生成签名失败";
		}
		return hexstr.toString();
	}
	/** 
     * 验证签名 
     *  
     * @param signature 
     * @param timestamp 
     * @param nonce 
     * @return 
     */  
    public static boolean checkSignature(String signature, String timestamp, String nonce) {  
        String[] arr = new String[] { WXKeys.WX_TOKEN, timestamp, nonce };  
        // 将token、timestamp、nonce三个参数进行字典序排序  
        Arrays.sort(arr);  
        StringBuilder content = new StringBuilder();  
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);  
        }
        MessageDigest md = null;  	
        String tmpStr = null;  
  
        try {  
            md = MessageDigest.getInstance("SHA-1");  
            // 将三个参数字符串拼接成一个字符串进行sha1加密  
            byte[] digest = md.digest(content.toString().getBytes());  
            tmpStr = byteToStr(digest);  
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  
        }  
  
        content = null;  
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信  
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;  
    }
}
