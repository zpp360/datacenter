package com.shuheng.datacenter.utils;


import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    /** 整数  */
    private static final String  V_INTEGER="^-?[1-9]\\d*$";

    /**  正整数 */
    private static final String V_Z_INDEX="^[1-9]\\d*$";

    /**负整数 */
    private static final String V_NEGATIVE_INTEGER="^-[1-9]\\d*$";

    /** 数字 */
    private static final String V_NUMBER="^([+-]?)\\d*\\.?\\d+$";

    /**正数 */
    private static final String V_POSITIVE_NUMBER="^[1-9]\\d*|0$";

    /** 负数 */
    private static final String V_NEGATINE_NUMBER="^-[1-9]\\d*|0$";

    /** 浮点数 */
    private static final String V_FLOAT="^([+-]?)\\d*\\.\\d+$";

    /** 正浮点数 */
    private static final String V_POSTTIVE_FLOAT="^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";

    /** 负浮点数 */
    private static final String V_NEGATIVE_FLOAT="^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$";

    /** 非负浮点数（正浮点数 + 0） */
    private static final String V_UNPOSITIVE_FLOAT="^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";

    /** 非正浮点数（负浮点数 + 0） */
    private static final String V_UN_NEGATIVE_FLOAT="^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$";

    /** 邮件 */
    private static final String V_EMAIL="^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    /** 颜色 */
    private static final String V_COLOR="^[a-fA-F0-9]{6}$";

    /** url */
    private static final String V_URL="^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";

    /** 仅中文 */
    private static final String V_CHINESE="^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";

    /** 仅ACSII字符 */
    private static final String V_ASCII="^[\\x00-\\xFF]+$";

    /** 邮编 */
    private static final String V_ZIPCODE="^\\d{6}$";

    /** 手机 */
    private static final String V_MOBILE="^(1)[0-9]{10}$";

    /** ip地址 */
    private static final String V_IP4="^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";

    /** 非空 */
    private static final String V_NOTEMPTY="^\\S+$";

    /** 图片  */
    private static final String V_PICTURE="(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";

    /**  压缩文件  */
    private static final String V_RAR="(.*)\\.(rar|zip|7zip|tgz)$";

    /** 日期 */
    private static final String V_DATE="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

    /** QQ号码  */
    private static final String V_QQ_NUMBER="^[1-9]*[1-9][0-9]*$";

    /** 电话号码的函数(包括验证国内区号,国际区号,分机号) */
    private static final String V_TEL="^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";

    /** 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串 */
    private static final String V_USERNAME="^\\w+$";

    /** 字母 */
    private static final String V_LETTER="^[A-Za-z]+$";

    /** 大写字母  */
    private static final String V_LETTER_U="^[A-Z]+$";

    /** 小写字母 */
    private static final String V_LETTER_I="^[a-z]+$";

    /** 身份证  */
    private static final String V_IDCARD ="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$|^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";

    /**验证密码(数字和英文同时存在)*/
    private static final String V_PASSWORD_REG="[A-Za-z]+[0-9]";

    /**验证密码长度(6-18位)*/
    private static final String V_PASSWORD_LENGTH="^\\d{6,18}$";

    /**验证两位数*/
    private static final String V_TWO＿POINT="^[0-9]+(.[0-9]{2})?$";

    /**验证一个月的31天*/
    private static final String V_31DAYS="^((0?[1-9])|((1|2)[0-9])|30|31)$";
    /**验证金额 小数点后2位*/
    private static final String V_DIGITS="^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
    /**版本号*/
    private static final String V_VERSION = "^\\d+(\\.\\d+){0,2}$";

    private static final String V_NORMAL_NAME = "^[\u4e00-\u9fa5|()|0-9|、]{0,}$";

    public ValidateUtils(){}


    /**
     * 验证是不是整数
     * @param value 要验证的字符串 要验证的字符串
     * @return  如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Integer(String value){
        return match(V_INTEGER,value);
    }

    /**
     * 验证是不是正整数
     * @param value 要验证的字符串
     * @return  如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Z_index(String value){
        return match(V_Z_INDEX,value);
    }

    /**
     * 验证是不是负整数
     * @param value 要验证的字符串
     * @return  如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Negative_integer(String value){
        return match(V_NEGATIVE_INTEGER,value);
    }

    /**
     * 验证是不是数字
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Number(String value){
        return match(V_NUMBER,value);
    }

    /**
     * 验证是不是正数
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean PositiveNumber(String value){
        return match(V_POSITIVE_NUMBER,value);
    }

    /**
     * 验证是不是负数
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean NegatineNumber(String value){
        return match(V_NEGATINE_NUMBER,value);
    }

    /**
     * 验证一个月的31天
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Is31Days(String value){
        return match(V_31DAYS,value);
    }

    /**
     * 验证是不是ASCII
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean ASCII(String value){
        return match(V_ASCII,value);
    }


    /**
     * 验证是不是中文
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Chinese(String value){
        return match(V_CHINESE,value);
    }



    /**
     * 验证是不是颜色
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Color(String value){
        return match(V_COLOR,value);
    }



    /**
     * 验证是不是日期
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Date(String value){
        return match(V_DATE,value);
    }

    /**
     * 验证是不是邮箱地址
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Email(String value){
        return match(V_EMAIL,value);
    }

    /**
     * 验证是不是浮点数
     * @param value 要验证的字符串
     * @return  如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Float(String value){
        return match(V_FLOAT,value);
    }

    /**
     * 验证是不是正确的身份证号码
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IDcard(String value){
        return match(V_IDCARD,value);
    }

    /**
     * 验证是不是正确的IP地址
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IP4(String value){
        return match(V_IP4,value);
    }

    /**
     * 验证是不是字母
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Letter(String value){
        return match(V_LETTER,value);
    }

    /**
     * 验证是不是小写字母
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Letter_i(String value){
        return match(V_LETTER_I,value);
    }


    /**
     * 验证是不是大写字母
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Letter_u(String value){
        return match(V_LETTER_U,value);
    }


    /**
     * 验证是不是手机号码
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Mobile(String value){
        return match(V_MOBILE,value);
    }

    /**
     * 验证是不是负浮点数
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Negative_float(String value){
        return match(V_NEGATIVE_FLOAT,value);
    }

    /**
     * 验证非空
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Notempty(String value){
        return StringUtils.isNotEmpty(value);
    }

    /**
     * 验证密码的长度(6~18位)
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Number_length(String value){
        return match(V_PASSWORD_LENGTH,value);
    }

    /**
     * 验证密码(数字和英文同时存在)
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Password_reg(String value){
        return match(V_PASSWORD_REG,value);
    }

    /**
     * 验证图片
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Picture(String value){
        return match(V_PICTURE,value);
    }

    /**
     * 验证正浮点数
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Posttive_float(String value){
        return match(V_POSTTIVE_FLOAT,value);
    }

    /**
     * 验证QQ号码
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean QQnumber(String value){
        return match(V_QQ_NUMBER,value);
    }

    /**
     * 验证压缩文件
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Rar(String value){
        return match(V_RAR,value);
    }

    /**
     * 验证电话
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Tel(String value){
        return match(V_TEL,value);
    }

    /**
     * 验证两位小数
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Two_point(String value){
        return match(V_TWO＿POINT,value);
    }

    /**
     * 验证非正浮点数
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Un_negative_float(String value){
        return match(V_UN_NEGATIVE_FLOAT,value);
    }

    /**
     * 验证非负浮点数
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Unpositive_float(String value){
        return match(V_UNPOSITIVE_FLOAT,value);
    }

    /**
     * 验证URL
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Url(String value){
        return match(V_URL,value);
    }

    /**
     * 验证用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean UserName(String value){
        return match(V_USERNAME,value);
    }

    /**
     * 验证邮编
     * @param value 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Zipcode(String value){
        return match(V_ZIPCODE,value);
    }
    /**
     * 限制字符长度必须在min到max之间
     * @param value
     * @param length
     * @return
     */
    public static boolean Size(String value,String length){
        String[] lengths=length.split(",");
        int min=0;
        int max=0;
        if(lengths.length>1){
            min=Integer.parseInt(lengths[0]);
            max=Integer.parseInt(lengths[1]);
        }else{
            max=Integer.parseInt(lengths[0]);
        }
        if(min<=value.length() && value.length()<=max){
            return true;
        }
        return false;
    }
    /**
     *日期比较
     * @param date1
     * @param date2
     * @return
     */
    public static int compare_date(String date1,String date2){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ymd=new SimpleDateFormat("yyyy/MM/dd");
        Date dt1=null;
        Date dt2=null;
        try {
            if(date1.contains("/")){
                dt1 =ymd.parse(date1);
            }else{
                dt1= sdf.parse(date1);//把时间格式化
            }
            if(date2.contains("/")){
                dt2 =ymd.parse(date2);
            }else{
                dt2= sdf.parse(date2);//把时间格式化
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dt1.getTime()<dt2.getTime()){
            return -1;
        }else if(dt1.getTime()>dt2.getTime()){
            return 1;
        }
        return 0;
    }
    /**
     *日期比较（小于）
     * @param value
     * @param endDate
     * @return
     */
    public static boolean LessCompareDate(String value,String endDate){
        return compare_date(value,endDate)<=0? true:false;
    }
    /**
     *日期比较（大于）
     * @param value
     * @param strartDate
     * @return
     */
    public static boolean greaterCompareDate(String value,String strartDate){
        return compare_date(strartDate,value)>=0? true:false;
    }
    /**
     * 下拉列表验证
     * @param value
     * @param format
     * @return
     */
    public static boolean Select(String value,String format){
        String[] formats=format.split(";");
        List list = new ArrayList();
        for(String s: formats){
            list.add(s.split(":")[1]);
        }
        return list.contains(value);
    }
    /**
     * 验证金额 小数点后2位
     * @param value
     * @return
     */
    public static boolean Digits(String value){
        return match(V_DIGITS,value);
    }
    /**
     * 匹配版本号
     * @param str
     * @return
     */
    public static boolean isVersion(String str){
        return match(V_VERSION,str);
    }

    /**
     * 正常名字，单位名字，用户名，团队名称等
     * @param str
     * @return
     */
    public static boolean isNormalName(String str){
        return match(V_NORMAL_NAME,str);
    }
    /**
     * 传入正则表达式
     * @param str
     * @param regex
     * @return
     */
    public  static boolean regex(String str,String regex){
        return match(regex,str);
    }
    /**
     * @param regex 正则表达式字符串
     * @param str 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}

