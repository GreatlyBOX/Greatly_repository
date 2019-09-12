package com.n22.shiro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @功能：时间处理公共接
 */
public class DateUtil {
    /**
     * 分钟差
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
//        // 计算差多少天
//        long day = diff / nd;
//        // 计算差多少小时
//        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return min;
    }
	/**
	 * string转化成date
	 * 
	 * @throws ParseException
	 */
	public static Date formatStrToDate(String object) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sd.parse(object);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @功能：date转化成string
	 */
	public static String formatDateToStrDateTime(Date myDate) {
		String formart = "yyyy-MM-dd HH:mm:ss";
		if (null == myDate) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(formart);
		String strDate = formatter.format(myDate);
		return strDate;
	}
    /**
     * 由过去的某一时间,计算距离指定的时间
     * */
    public static String CalculateTime(Date time){
        long nowTime=System.currentTimeMillis();  //获取当前时间的毫秒数
        String msg = null;
        int fla=0;
        long reset=time.getTime();   //获取指定时间的毫秒数
        long dateDiff=reset-nowTime;
        if(dateDiff<0){
            dateDiff=Math.abs(dateDiff);
            fla++;
        }
        long dateTemp1=dateDiff/1000; //秒
        long dateTemp2=dateTemp1/60; //分钟
        long dateTemp3=dateTemp2/60; //小时
        long dateTemp4=dateTemp3/24; //天数
        if(dateTemp3!=0&&dateTemp4==0){
            dateTemp4=1L;
        }

        if(dateTemp4>0){
            msg = dateTemp4+"";
        }else if(dateTemp3>0){
            msg = 0+"";
        }else if(dateTemp2>0){
            msg = 0+"";
        }else if(dateTemp1>0){
            msg = "0";
        }
        if(fla>0){
            msg="0"+(dateTemp4+1L);
        }
        return msg;
    }

    /**
     * 由过去的某一时间,计算距离当前的时间
     * */
    public static String CalculateTime(Date time,Date endtime){
        long nowTime=endtime.getTime();  //获取当前时间的毫秒数
        String msg = "0";
        int fla=0;
        long reset=time.getTime();   //获取指定时间的毫秒数
        long dateDiff=nowTime-reset;
        if(dateDiff<0){
            dateDiff=Math.abs(dateDiff);
            fla++;
        }
        long dateTemp1=dateDiff/1000; //秒
        long dateTemp2=dateTemp1/60; //分钟
        long dateTemp3=dateTemp2/60; //小时
        long dateTemp4=dateTemp3/24; //天数

        if(dateTemp4>0){
            if(dateTemp3-(dateTemp4*24)>0){
                dateTemp4+=1;
            };
            msg = dateTemp4+"";
        }else if(dateTemp3>0){
            msg = 0+"";
        }else if(dateTemp2>0){
            msg = 0+"";
        }else if(dateTemp1>0){
            msg = "0";
        }
        if(fla>0){
            msg="-"+(dateTemp4+1L);
        }
        return msg;
    }

	/**
	 * @功能：date转化成string
	 */
	public static String formatDateToStrDateTime2(Date myDate) {
		String formart = "yyyyMMdd";
		if (null == myDate) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(formart);
		String strDate = formatter.format(myDate);
		return strDate;
	}

	/**
	 * @功能：date转化成string
	 */
	public static String formatDateToStrDateTime1(Date myDate) {
		String formart = "yyyy-MM";
		if (null == myDate) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(formart);
		String strDate = formatter.format(myDate);
		return strDate;
	}

	/**
	 * @功能：date转化成string
	 */
	public static String formatDateToStrDateNoTime(Date myDate) {
		String formart = "yyyy-MM-dd";
		if (null == myDate) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(formart);
		String strDate = formatter.format(myDate);
		return strDate;
	}

	/**
	 * @功能：date转化成string
	 */
	public static String formatDateToStrTime(Date myDate) {
		String formart = "HH:mm:ss";
		if (null == myDate) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(formart);
		String strDate = formatter.format(myDate);
		return strDate;
	}

	/**
	 * @功能：string转化成date
	 * @参数：myDate->string格式时间 ,format->时间格式
	 */
	public static Date formatStringToDate(String myDate, String format) {
		if (myDate == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(myDate);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 功能：获得指定的时间 默认格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getCalendarDate(String date, String formart, int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.formatStringToDate(date, formart));
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, day);
		return DateUtil.formatDateToStr(cal.getTime(), formart);
	}

	/**
	 * 功能：获得指定的时间 默认格式 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getCalendarDate(Date date, int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 功能：获得指定的时间 默认格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getCalendarDate(String date, String formart, int year, int month, int day, int hour,
			int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.formatStringToDate(date, formart));
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, day);
		cal.add(Calendar.HOUR, hour);
		cal.add(Calendar.MINUTE, minute);
		cal.add(Calendar.SECOND, second);
		return DateUtil.formatDateToStr(cal.getTime(), formart);
	}

	/**
	 * @功能：date转化成string
	 */
	public static String formatDateToStr(Date myDate, String format) {
		if (null == myDate) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String strDate = formatter.format(myDate);
		return strDate;
	}

	/**
	 * 判断商机是否提醒
	 * 
	 * @param starttime
	 * @param endtime
	 * @param birthday
	 * @return
	 */
	public static boolean panduan(Date starttime, Date endtime) {
		boolean boole = true;
		Date currentDate = new Date();
		int start_year = Integer.parseInt(formatDateToStr(starttime, "yyyy"));
		int start_month = Integer.parseInt(formatDateToStr(starttime, "MM"));
		int start_day = Integer.parseInt(formatDateToStr(starttime, "dd"));
		int end_year = Integer.parseInt(formatDateToStr(endtime, "yyyy"));
		int end_month = Integer.parseInt(formatDateToStr(endtime, "MM"));
		int end_day = Integer.parseInt(formatDateToStr(endtime, "dd"));
		int current_year = Integer.parseInt(formatDateToStr(currentDate, "yyyy"));
		int current_month = Integer.parseInt(formatDateToStr(currentDate, "MM"));
		int current_day = Integer.parseInt(formatDateToStr(currentDate, "dd"));

		if (start_month == end_month) {
			if (start_day > current_day || end_day < current_day || start_month != current_month) {
				boole = false;
			}
		} else if ((start_month == current_month && start_day > current_day)
				|| (end_month == current_month && end_month < current_day)) {
			boole = false;
		}
		if (start_year == end_year) {
			if (start_month > current_month || end_month < current_month) {
				boole = false;
			}
		} else if (start_year < end_year) {
			if (start_year == current_year) {
				if (start_month > current_month || end_month > current_month) {
					boole = false;
				}
			} else if (end_year == current_year) {
				if (start_month > current_month || end_month < current_month) {
					boole = false;
				}
			}
		}
		return boole;
	}

	/**
	 * 根据出生日期获得离生日天数
	 */
	public static int getDaysFromBirthday(Date birthday) {
		if (birthday != null) {
			Date currentDate = new Date();
			int year_current = Integer.parseInt(formatDateToStr(currentDate, "yyyy"));
			int month_current = Integer.parseInt(formatDateToStr(currentDate, "MM"));
			int day_current = Integer.parseInt(formatDateToStr(currentDate, "dd"));
			currentDate = DateUtil.formatStringToDate(year_current + "-" + month_current + "-" + day_current,
					"yyyy-M-d");

			int birth_month = Integer.parseInt(formatDateToStr(birthday, "MM"));
			int birth_day = Integer.parseInt(formatDateToStr(birthday, "dd"));

			if (birthday.compareTo(currentDate) >= 0) {
				return 0;
			}
			Date next_birthDate = null;
			if (birth_month < month_current || (birth_month == month_current && birth_day < day_current)) {
				next_birthDate = DateUtil.formatStringToDate((year_current + 1) + "-" + birth_month + "-" + birth_day,
						"yyyy-M-d");
			} else {
				next_birthDate = DateUtil.formatStringToDate(year_current + "-" + birth_month + "-" + birth_day,
						"yyyy-M-d");
			}
			int days = 0;
			while (currentDate.compareTo(next_birthDate) < 0) {
				currentDate = getCalendarDate(currentDate, 0, 0, 1);
				days++;
			}
			return days;
		}
		return 0;
	}

	/**
	 * 根据出生日期获得年龄
	 */
	public static String getAge(Date birthday) {
		String age = "";
		if (birthday != null) {
			Date currentDate = new Date();
			int year_current = Integer.parseInt(formatDateToStr(currentDate, "yyyy"));
			String day_current = formatDateToStr(currentDate, "MMdd");

			int year = Integer.parseInt(formatDateToStr(birthday, "yyyy"));
			String day = formatDateToStr(birthday, "MMdd");

			if (day.compareTo(day_current) <= 0) {
				age = "" + (year_current - year);
			} else {
				age = "" + (year_current - year - 1);
			}
		}
		return age;
	}

	/**
	 * 获取一个月的第一天
	 * 
	 * @param args
	 */
	public static String getFirstDay(int year, int month, int day) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, day);
		String date = format.format(cal.getTime());
		return date;
	}
	
	
	/**
	 * 获取月
	 * 
	 * @param args
	 */
	public static String getCalendarMonth() throws ParseException{
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.MONTH)+1);
	}
	
	/**
	 * 获取天
	 * 
	 * @param args
	 */
	public static String getCalendarDay() throws ParseException{
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.DATE));
	}

	/**
	 * @功能：获得季度
	 */
	public static int getQuarterOfYear(Date date) {
		int month = Integer.parseInt(formatDateToStr(date, "MM"));
		return (int) Math.ceil(month / 3.0);
	}

	/**
	 * @功能：获得当前月份的第一天
	 */
	public static String getFirstDayOfMonth(Date date) {
		return formatDateToStr(date, "yyyy-MM") + "-01";
	}

	/**
	 * @功能：获得当前季度的第一天
	 */
	public static String getFirstDayOfQuarter(Date date) {
		int month = Integer.parseInt(formatDateToStr(date, "MM"));
		int quarter = (int) Math.ceil(month / 3.0);
		int month_f = (quarter - 1) * 3 + 1;
		String month_f_str = "";
		if (month_f < 10) {
			month_f_str = "0" + month_f;
		} else {
			month_f_str = month_f + "";
		}
		return formatDateToStr(date, "yyyy") + month_f_str + "01";
	}

	/**
	 * @功能：获得当前年份的第一天
	 */
	public static String getFirstDayOfYear(Date date) {
		return formatDateToStr(date, "yyyy") + "0101" + "0000";
	}

	/**
	 * 获得当前季度第一天（yyyy-MM-dd）
	 * 
	 * @param date
	 * @return
	 */
	public static String getTheFirstDayOfQuarter(Date date) {
		int month = Integer.parseInt(formatDateToStr(date, "MM"));
		int quarter = (int) Math.ceil(month / 3.0);
		int month_f = (quarter - 1) * 3 + 1;
		String month_f_str = "";
		if (month_f < 10) {
			month_f_str = "0" + month_f;
		} else {
			month_f_str = month_f + "";
		}
		return formatDateToStr(date, "yyyy") + "-" + month_f_str + "-01";
	}

	/**
	 * 获得当前月份第一天（yyyy-MM-dd）
	 * 
	 * @param date
	 * @return
	 */
	public static String getTheFirstDayOfMonth(Date date) {
		return formatDateToStr(date, "yyyy-MM") + "-01";
	}

	/**
	 * 获得当前年份的第一天（yyyy-MM-dd）
	 * 
	 * @param date
	 * @return
	 */
	public static String getTheFirstDayOfYear(Date date) {
		return formatDateToStr(date, "yyyy") + "-01-01";
	}

	/**
	 * 一个指定日期加上指定天数得到新日期
	 * 
	 * @param str
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static String subDate(String str, long day) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		Date date = dateFormat.parse(str); // 指定日期
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time = time - day; // 相加得到新的毫秒数
		return dateFormat.format(new Date(time)).toString(); // 将毫秒数转换成日期
	}

	/**
	 * 获取当月第一天
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static String getFirstDayByMonth(String str) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		Date date = dateFormat.parse(str); // 指定日期
		return formatDateToStr(date, "yyyy-MM") + "-01";
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static String getLastDayByMonth(String str) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
		Date date = dateFormat.parse(str); // 指定日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return formatDateToStr(date, "yyyy-MM") + "-" + actualMaximum;
	}
	
	/**
	 * 系统当前日期
	 * 日期格式为"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static Date getCurrentDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		try {
			Date date = sdf.parse(time);
            return date;
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 获取前一天
	 * @return
	 */
	public static String beforeDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Date date=new Date();  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
        String time1 = sdf.format(date);
        return time1.trim();
	}
	
	/**
	 * 获取当前日期的前几天
	 * @return
	 */
	public static String beforeSomeDate(int days){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Date date=new Date();  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -days);  
        date = calendar.getTime();  
        String time1 = sdf.format(date);
        return time1.trim();
	}
	/**
	   * 获取现在时间
	   * 
	   * @return返回短时间格式 yyyy年MM月dd日
	   */
	public static String getHanHuaDate(Date str){
				String strDate= DateUtil.formatDateToStr(str, "yyyy-MM-dd");
				String year=strDate.substring(0,4);
				String month=strDate.substring(5,7);
				String day=strDate.substring(8,10);
				String date=year+"年"+month+"月"+day+"日";
			return date; 
	} 
	
	/**
	   * 获取现在时间
	   * 
	   * @return返回短时间格式 MM月dd日
	   */
	public static String getDateHanhua(Date str){
				String strDate= DateUtil.formatDateToStr(str, "MM-dd");
				String month=strDate.substring(0,2);
				String day=strDate.substring(3,5);
				String date=month+"月"+day+"日";
			return date; 
	} 
	public static void main(String[] args) throws ParseException {
//	    Date newDate = new Date();// 指定日期加上20天
//        newDate=formatStrToDate(formatDateToStrDateNoTime(newDate)+" 00:00:00");
//        Calendar now =Calendar.getInstance();
//        now.setTime(newDate);
//        now.set(Calendar.DATE,now.get(Calendar.DATE)+5);
//        String time= DateUtil.CalculateTime(now.getTime()) ;
//		System.out.println(time);
//		System.out.println(DateUtil.formatDateToStrDateNoTime(now.getTime()));



	}
    public static int getDaysOfTheMonth(Date date){//获取当月天数
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date); // 要计算你想要的月份，改变这里即可
        int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    public static void setColour(Date date,Date startTime,Date endTime,Map<String,Map<String,String>> map,String type){//获取当月天数
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        int cc=Integer.parseInt(CalculateTime(startTime,endTime));
        int dd=0;
        for(int o=0;o<=cc;o++){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            if(cc>0){
                calendar.add(Calendar.DAY_OF_MONTH,+o);
            }else {
                calendar.add(Calendar.DAY_OF_MONTH, -o);
            }
            date = calendar.getTime();
            String time1 = sdf.format(date);
            Map<String,String> map1=map.get(time1);
            if(map1==null){
                continue;
            }
            //根据类型判断
            if("node".equals(type)){
                map1.put("node","1");
            }else if("plan".equals(type)){
                map1.put("plan","1");
            }else if("meeting".equals(type)){
                map1.put("meeting","1");
            }else if("train".equals(type)){
                map1.put("train","1");
            }
        }
    }

    public static Map getColourMap(Date date,String strDate){//获取当月天数
        int count=getDaysOfTheMonth(date);
        Map<String,Map<String,String>> map=new HashMap<>();

        for(int i=1;i<=count;i++){
            String time=String.valueOf(i);
            Map<String,String>  colour=new HashMap<>();
            colour.put("node","0");
            colour.put("plan","0");
            colour.put("meeting","0");
            colour.put("train","0");
            if(time.length()==1){
                time="0"+time;
            }
            map.put(strDate+"-"+time+"",colour);
        }
        return map;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        }
//        else if(date.before(end)){
//            return true;
//        }
        else {
            return false;
        }
    }
}
