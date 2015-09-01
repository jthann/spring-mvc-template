package com.xxx.util;


import com.xxx.util.exception.TypeCastException;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author hanjuntao
 */

public class TypeCastUtils {

    public static Boolean convert2Boolean(Object obj) {
        return (Boolean) convert(obj, "Boolean", null);
    }

    public static Integer convert2Integer(Object obj) {
        return (Integer) convert(obj, "Integer", null);
    }

    public static String convert2String(Object obj)  {
        return (String) convert(obj, "String", null);
    }

    public static String convert2String(Object obj, String defaultValue) {
        Object s = convert(obj, "String", null);
        if (s != null)
            return (String) s;
        else
            return defaultValue;
    }

    public static Long convert2Long(Object obj) throws TypeCastException {
        return (Long) convert(obj, "Long", null);
    }

    public static Double convert2Double(Object obj) throws TypeCastException {
        return (Double) convert(obj, "Double", null);
    }

    public static BigDecimal convert2BigDecimal(Object obj, int scale) throws TypeCastException {
        return ((BigDecimal) convert(obj, "BigDecimal", null)).setScale(scale, 5);
    }

    public static Date convert2SqlDate(Object obj, String format) throws TypeCastException {
        return (Date) convert(obj, "Date", format);
    }

    public static Timestamp convert2Timestamp(Object obj, String format) throws TypeCastException {
        return (Timestamp) convert(obj, "Timestamp", format);
    }

    /**
     * 转换核心实现方法
     * @param originObj     原对象值
     * @param type    转换的类型
     * @param format  转换格式
     * @return Object  返回值，客户端强制类型转换
     * @throws com.jd.ept.m.dto.helper.exception.TypeCastException
     */
    public static Object convert(Object originObj, String type, String format) throws TypeCastException {
        Locale locale = new Locale("zh", "CN", "");
        if (originObj == null)
            return null;
        if (originObj.getClass().getName().equals(type))
            return originObj;
        if ("Object".equals(type) || "java.lang.Object".equals(type))
            return originObj;

        if (originObj instanceof String)
            return string2Obj((String)originObj, type, format, locale);

        if (originObj instanceof Integer)
            return integer2Obj((Integer)originObj, type, locale);

        if (originObj instanceof Long)
            return long2Obj((Long)originObj, type, locale);

        if (originObj instanceof Double)
            return double2Obj((Double)originObj, type, locale);

        if (originObj instanceof Date)
            return date2Obj((Date)originObj, type, format);

        if (originObj instanceof Boolean)
            return boolean2Obj((Boolean) originObj, type);

        if (originObj instanceof Timestamp)
            return timestamp2Obj((Timestamp)originObj, type, format);

        if (originObj instanceof Float)
            return float2Obj((Float)originObj, type, locale);

        if (originObj instanceof BigDecimal)
            return bigDecimal2Obj((BigDecimal)originObj, type, locale);

        if ("String".equals(type) || "java.lang.String".equals(type))
            return originObj.toString();
        else
            throw new TypeCastException("Conversion from " + originObj.getClass().getName() + " to "
                    + type + " not currently supported");
    }

    private static Object string2Obj(String str, String type, String format, Locale locale) {
        if ("String".equals(type) || "java.lang.String".equals(type))
            return str;
        if (str.length() == 0)
            return null;
        if ("Boolean".equals(type) || "java.lang.Boolean".equals(type)) {
            Boolean value = null;
            if (str.equalsIgnoreCase("TRUE"))
                value = new Boolean(true);
            else
                value = new Boolean(false);
            return value;
        }
        if ("Double".equals(type) || "java.lang.Double".equals(type))
            try {
                Number tempNum = getNf(locale).parse(str);
                return new Double(tempNum.doubleValue());
            } catch (ParseException e) {
                throw new TypeCastException("Could not convert " + str + " to " + type + ": ",
                        e);
            }
        if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
            try {
                BigDecimal retBig = new BigDecimal(str);
                int iscale = str.indexOf(".");
                int keylen = str.length();
                if (iscale > -1) {
                    iscale = keylen - (iscale + 1);
                    return retBig.setScale(iscale, 5);
                } else {
                    return retBig.setScale(0, 5);
                }
            } catch (Exception e) {
                throw new TypeCastException("Could not convert " + str + " to " + type + ": ",
                        e);
            }
        if ("Float".equals(type) || "java.lang.Float".equals(type))
            try {
                Number tempNum = getNf(locale).parse(str);
                return new Float(tempNum.floatValue());
            } catch (ParseException e) {
                throw new TypeCastException("Could not convert " + str + " to " + type + ": ",
                        e);
            }
        if ("Long".equals(type) || "java.lang.Long".equals(type))
            try {
                NumberFormat nf = getNf(locale);
                nf.setMaximumFractionDigits(0);
                Number tempNum = nf.parse(str);
                return new Long(tempNum.longValue());
            } catch (ParseException e) {
                throw new TypeCastException("Could not convert " + str + " to " + type + ": ",
                        e);
            }
        if ("Integer".equals(type) || "java.lang.Integer".equals(type))
            try {
                NumberFormat nf = getNf(locale);
                nf.setMaximumFractionDigits(0);
                Number tempNum = nf.parse(str);
                return new Integer(tempNum.intValue());
            } catch (ParseException e) {
                throw new TypeCastException("Could not convert " + str + " to " + type + ": ",
                        e);
            }
        if ("Date".equals(type) || "java.sql.Date".equals(type)) {
            if (format == null || format.length() == 0)
                try {
                    return Date.valueOf(str);
                } catch (Exception e) {
                    try {
                        DateFormat df = null;
                        if (locale != null)
                            df = DateFormat.getDateInstance(3, locale);
                        else
                            df = DateFormat.getDateInstance(3);
                        java.util.Date fieldDate = df.parse(str);
                        return new Date(fieldDate.getTime());
                    } catch (ParseException e1) {
                        throw new TypeCastException("Could not convert " + str + " to " + type
                                + ": ", e);
                    }
                }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                java.util.Date fieldDate = sdf.parse(str);
                return new Date(fieldDate.getTime());
            } catch (ParseException e) {
                throw new TypeCastException("Could not convert " + str + " to " + type + ": ",
                        e);
            }
        }
        if ("Timestamp".equals(type) || "java.sql.Timestamp".equals(type)) {
            if (str.length() == 10)
                str = str + " 00:00:00";
            if (format == null || format.length() == 0)
                try {
                    return Timestamp.valueOf(str);
                } catch (Exception e) {
                    try {
                        DateFormat df = null;
                        if (locale != null)
                            df = DateFormat.getDateTimeInstance(3, 3, locale);
                        else
                            df = DateFormat.getDateTimeInstance(3, 3);
                        java.util.Date fieldDate = df.parse(str);
                        return new Timestamp(fieldDate.getTime());
                    } catch (ParseException e1) {
                        throw new TypeCastException("Could not convert " + str + " to " + type
                                + ": ", e);
                    }
                }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                java.util.Date fieldDate = sdf.parse(str);
                return new Timestamp(fieldDate.getTime());
            } catch (ParseException e) {
                throw new TypeCastException("Could not convert " + str + " to " + type + ": ",
                        e);
            }
        } else {
            throw new TypeCastException("Conversion from String to " + type
                    + " not currently supported");
        }
    }

    private static Object integer2Obj(Integer integerObj, String type, Locale locale) {

        if ("String".equals(type) || "java.lang.String".equals(type))
            return getNf(locale).format(integerObj.longValue());
        if ("Double".equals(type) || "java.lang.Double".equals(type))
            return new Double(integerObj.doubleValue());
        if ("Float".equals(type) || "java.lang.Float".equals(type))
            return new Float(integerObj.floatValue());
        if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type)) {
            String str = integerObj.toString();
            BigDecimal retBig = new BigDecimal(integerObj.doubleValue());
            int iscale = str.indexOf(".");
            int keylen = str.length();
            if (iscale > -1) {
                iscale = keylen - (iscale + 1);
                return retBig.setScale(iscale, 5);
            } else {
                return retBig.setScale(0, 5);
            }
        }
        if ("Long".equals(type) || "java.lang.Long".equals(type))
            return new Long(integerObj.longValue());
        if ("Integer".equals(type) || "java.lang.Integer".equals(type))
            return integerObj;
        else
            throw new TypeCastException("Conversion from Integer to " + type
                    + " not currently supported");
    }

    private static Object long2Obj(Long longObj, String type, Locale locale) {
        if ("String".equals(type) || "java.lang.String".equals(type))
            return getNf(locale).format(longObj.longValue());
        if ("Double".equals(type) || "java.lang.Double".equals(type))
            return new Double(longObj.doubleValue());
        if ("Float".equals(type) || "java.lang.Float".equals(type))
            return new Float(longObj.floatValue());
        if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
            return new BigDecimal(longObj.toString());
        if ("Long".equals(type) || "java.lang.Long".equals(type))
            return longObj;
        if ("Integer".equals(type) || "java.lang.Integer".equals(type))
            return new Integer(longObj.intValue());
        else
            throw new TypeCastException("Conversion from Long to " + type
                    + " not currently supported");
    }

    private static Object double2Obj(Double dblObj, String type, Locale locale) {
        if ("String".equals(type) || "java.lang.String".equals(type))
            return getNf(locale).format(dblObj.doubleValue());
        if ("Double".equals(type) || "java.lang.Double".equals(type))
            return dblObj;
        if ("Float".equals(type) || "java.lang.Float".equals(type))
            return new Float(dblObj.floatValue());
        if ("Long".equals(type) || "java.lang.Long".equals(type))
            return new Long(Math.round(dblObj.doubleValue()));
        if ("Integer".equals(type) || "java.lang.Integer".equals(type))
            return new Integer((int) Math.round(dblObj.doubleValue()));
        if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
            return new BigDecimal(dblObj.toString());
        else
            throw new TypeCastException("Conversion from Double to " + type
                    + " not currently supported");
    }

    private static Object date2Obj(Date dateObj, String type, String format) {
        if ("String".equals(type) || "java.lang.String".equals(type))
            if (format == null || format.length() == 0) {
                return dateObj.toString();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(new java.util.Date(dateObj.getTime()));
            }
        if ("Date".equals(type) || "java.sql.Date".equals(type))
            return dateObj;
        if ("Time".equals(type) || "java.sql.Time".equals(type))
            throw new TypeCastException("Conversion from Date to " + type
                    + " not currently supported");
        if ("Timestamp".equals(type) || "java.sql.Timestamp".equals(type))
            return new Timestamp(dateObj.getTime());
        else
            throw new TypeCastException("Conversion from Date to " + type
                    + " not currently supported");
    }

    private static Object boolean2Obj(Boolean boolObj, String type) {
        if ("Boolean".equals(type) || "java.lang.Boolean".equals(type))
            return boolObj;
        if ("String".equals(type) || "java.lang.String".equals(type))
            return boolObj.toString();
        if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
            if (boolObj.booleanValue())
                return new Integer(1);
            else
                return new Integer(0);
        } else {
            throw new TypeCastException("Conversion from Boolean to " + type
                    + " not currently supported");
        }
    }

    private static Object timestamp2Obj(Timestamp timestampObj, String type, String format) {
        if ("String".equals(type) || "java.lang.String".equals(type))
            if (format == null || format.length() == 0) {
                return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(timestampObj).toString();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(new java.util.Date(timestampObj.getTime()));
            }
        if ("Date".equals(type) || "java.sql.Date".equals(type))
            return new Date(timestampObj.getTime());
        if ("Time".equals(type) || "java.sql.Time".equals(type))
            return new Time(timestampObj.getTime());
        if ("Timestamp".equals(type) || "java.sql.Timestamp".equals(type))
            return timestampObj;
        else
            throw new TypeCastException("Conversion from Timestamp to type "
                    + " not currently supported");
    }
    private static Object float2Obj(Float floatObj, String type, Locale locale) {
        if ("String".equals(type))
            return getNf(locale).format(floatObj.doubleValue());
        if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
            return new BigDecimal(floatObj.doubleValue());
        if ("Double".equals(type))
            return new Double(floatObj.doubleValue());
        if ("Float".equals(type))
            return floatObj;
        if ("Long".equals(type))
            return new Long(Math.round(floatObj.doubleValue()));
        if ("Integer".equals(type))
            return new Integer((int) Math.round(floatObj.doubleValue()));
        else
            throw new TypeCastException("Conversion from Float to " + type
                    + " not currently supported");
    }


    private static Object bigDecimal2Obj(BigDecimal bigDecimalObj, String type, Locale locale) {
        if ("String".equals(type))
            return getNf(locale).format(bigDecimalObj.doubleValue());
        if ("BigDecimal".equals(type) || "java.math.BigDecimal".equals(type))
            return bigDecimalObj;
        if ("Double".equals(type))
            return new Double(bigDecimalObj.doubleValue());
        if ("Float".equals(type))
            return new Float(bigDecimalObj.floatValue());
        if ("Long".equals(type))
            return new Long(Math.round(bigDecimalObj.doubleValue()));
        if ("Integer".equals(type))
            return new Integer((int) Math.round(bigDecimalObj.doubleValue()));
        else
            throw new TypeCastException("Conversion from BigDecimal to " + type
                    + " not currently supported");
    }

    private static NumberFormat getNf(Locale locale) {
        NumberFormat nf = null;
        if (locale == null)
            nf = NumberFormat.getNumberInstance();
        else
            nf = NumberFormat.getNumberInstance(locale);
        nf.setGroupingUsed(false);
        return nf;
    }

}
