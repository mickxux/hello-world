import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {
    public static void main(String[] str) {
        try {
            List<PeriodBean> periodList = new ArrayList<PeriodBean>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate = sdf.parse("20101026");
            Date endDate = sdf.parse("20120427");
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            List<PeriodBean> quarterList = new Utils().getQuarterListFor31(periodList, start, end);
            for (PeriodBean bean : quarterList) {
                System.out.println(bean.toString());
            }
        } catch (Exception e) {
        }
    }

    /**
     * 按年
     *
     * @param periodList 装期间的list
     * @param startDate  开始时间
     * @param endDate    结束时间
     *                   类型
     * @return
     * @author 徐兴
     */
    public List<Utils.PeriodBean> computePeriodHandler(List<PeriodBean> periodList, Calendar startDate, Calendar endDate) {

        int startYear = startDate.get(Calendar.YEAR);

        int endYear = endDate.get(Calendar.YEAR);

        Calendar firstPeriod = Calendar.getInstance();
        firstPeriod.set(startYear, 11, 20);// 开始年12月20日

        Calendar firstPeriod3 = Calendar.getInstance();
        firstPeriod3.set(startYear + 1, 11, 20);// 开始年12月20日

        Utils.PeriodBean bean = null;
        // 按年来计算
        if ((startDate.compareTo(firstPeriod) <= 0 && endDate.compareTo(firstPeriod) <= 0) || (startDate.compareTo(firstPeriod) > 0 && startYear == endYear)) {
            // 开始结束为同一期
            bean = new Utils.PeriodBean(startDate.getTime(), endDate.getTime());
            periodList.add(bean);
            return periodList;
        } else if (startDate.compareTo(firstPeriod) <= 0 && endDate.compareTo(firstPeriod) > 0 && startYear == endYear) {
            // 同年两期
            bean = new Utils.PeriodBean(startDate.getTime(), firstPeriod.getTime());
            periodList.add(bean);

            Calendar firstPeriod2 = (Calendar) firstPeriod.clone();
            firstPeriod2.add(Calendar.DAY_OF_MONTH, 1);
            Utils.PeriodBean bean2 = new Utils.PeriodBean(firstPeriod2.getTime(), endDate.getTime());
            periodList.add(bean2);
            return periodList;
        } else if (startDate.compareTo(firstPeriod) > 0 && startYear < endYear && endDate.compareTo(firstPeriod3) <= 0) {
            // start 2019-12-21 end 2020-10-11
            // 开始结束为同一期
            bean = new Utils.PeriodBean(startDate.getTime(), endDate.getTime());
            periodList.add(bean);
            return periodList;
        } else {
            // 跨年两期、多期
            Calendar firstPeriod2 = (Calendar) firstPeriod.clone();
            if (startDate.compareTo(firstPeriod) <= 0) {
                bean = new Utils.PeriodBean(startDate.getTime(), firstPeriod.getTime());
            } else {
                bean = new Utils.PeriodBean(startDate.getTime(), firstPeriod3.getTime());
                firstPeriod2 = (Calendar) firstPeriod3.clone();
            }
            periodList.add(bean);

            firstPeriod2.add(Calendar.DAY_OF_MONTH, 1);
            return computePeriodHandler(periodList, firstPeriod2, endDate);
        }
    }

    /**
     * 按季度计算
     *
     * @param beanList
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Utils.PeriodBean> getQuarterList(List<Utils.PeriodBean> beanList, Calendar startDate, Calendar endDate) {
        int startYear = startDate.get(Calendar.YEAR); // 开始年
        Utils.PeriodBean beanDate = null;

        Calendar oneEnd = Calendar.getInstance();
        oneEnd.set(startYear, 2, 20);// 第一期结束时间

        Calendar twoEnd = Calendar.getInstance();
        twoEnd.set(startYear, 5, 20);// 第二期结束时间

        Calendar threeEnd = Calendar.getInstance();
        threeEnd.set(startYear, 8, 20);// 第三期结束时间

        Calendar fourEnd = Calendar.getInstance();
        fourEnd.set(startYear, 11, 20);// 第四期结束时间

        Calendar fiveEnd = Calendar.getInstance();
        fiveEnd.set(startYear+1, 2, 20);// 第五期结束时间

        Calendar firstYear = Calendar.getInstance();
        if (startDate.compareTo(endDate) < 0) {
            //计算第一期
            if (startDate.compareTo(oneEnd) < 0 && endDate.compareTo(oneEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), oneEnd.getTime());
                beanList.add(beanDate);
                oneEnd.add(Calendar.DAY_OF_MONTH, 1);
                return getQuarterList(beanList, oneEnd, endDate);
            }
            //计算第二期
            else if (startDate.compareTo(twoEnd) < 0 && endDate.compareTo(twoEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), twoEnd.getTime());
                beanList.add(beanDate);
                twoEnd.add(Calendar.DAY_OF_MONTH, 1);
                return getQuarterList(beanList, twoEnd, endDate);
            }
            //计算第三期
            else if (startDate.compareTo(threeEnd) < 0 && endDate.compareTo(threeEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), threeEnd.getTime());
                beanList.add(beanDate);
                threeEnd.add(Calendar.DAY_OF_MONTH, 1);
                return getQuarterList(beanList, threeEnd, endDate);
            }
            //计算第四期
            else if (startDate.compareTo(fourEnd) < 0 && endDate.compareTo(fourEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), fourEnd.getTime());
                beanList.add(beanDate);
                fourEnd.add(Calendar.DAY_OF_MONTH, 1);
                return getQuarterList(beanList, fourEnd, endDate);
            }
            //计算第五期
            else if (startDate.compareTo(fiveEnd) < 0 && endDate.compareTo(fiveEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), fiveEnd.getTime());
                beanList.add(beanDate);
                fiveEnd.add(Calendar.DAY_OF_MONTH,1);
                return getQuarterList(beanList, fiveEnd, endDate);
            }
            else {
                beanDate = new Utils.PeriodBean(startDate.getTime(), endDate.getTime());
                beanList.add(beanDate);
            }
        }
        return beanList;
    }

    /**
     * 按月计算
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     * @author 徐兴
     */
    public List<Utils.PeriodBean> getMonthList(List<Utils.PeriodBean> beanList, Calendar startDate, Calendar endDate) {

        int startYear = startDate.get(Calendar.YEAR); // 开始年
        int startMonth = startDate.get(Calendar.MONTH);// 开始月

        int endYear = endDate.get(Calendar.YEAR);// 结束年
        int endMonth = endDate.get(Calendar.MONTH);// 结束月

        Calendar firstPeriod = Calendar.getInstance();
        firstPeriod.set(startYear, startMonth, 20);// 开始时间的 初始月天数为 20号

        Calendar lasetPeriod = Calendar.getInstance();
        lasetPeriod.set(endYear, endMonth, 20);// 结束时间的 初始月天数为 20号

        Calendar yearMonthPeriod = Calendar.getInstance(); // 最后一个月为12月20号
        yearMonthPeriod.set(startYear, 11, 20);// 开始时间的 初始月天数为 20号

        Calendar yearMonthNOPeriod = Calendar.getInstance();// 开始时间的后一期
        yearMonthNOPeriod.set(startYear, startMonth + 1, 20);// 开始时间的 初始月天数为 20号

        Calendar thisCl = Calendar.getInstance();
        thisCl.set(endYear + 1, 0, 20);

        Calendar thisC = Calendar.getInstance();
        thisC.set(startYear + 1, 0, 20);
        Utils.PeriodBean beanDate = null;
        // 同年同月
        if (startYear == endYear && startMonth == endMonth) {
            // 1期
            if (((startDate.compareTo(firstPeriod) <= 0 && endDate.compareTo(firstPeriod) <= 0) || (startDate.compareTo(firstPeriod) > 0 && endDate.compareTo(yearMonthNOPeriod) <= 0)) && endDate.compareTo(yearMonthPeriod) <= 0 && startDate.compareTo(yearMonthPeriod) <= 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), endDate.getTime());
                beanList.add(beanDate);
                return beanList;
            }
            // 2期
            else {
                // 如果同一年 但是是最后一期 12-20 之后
                if (startYear == endYear && startDate.compareTo(yearMonthPeriod) > 0 && endDate.compareTo(thisCl) <= 0) {
                    Calendar lastEndDate = (Calendar) endDate.clone();
                    lastEndDate.set(startYear + 1, 0, 20);
                    beanDate = new Utils.PeriodBean(startDate.getTime(), endDate.getTime());
                    beanList.add(beanDate);
                    return beanList;
                } else {
                    Calendar nextPeriod = (Calendar) firstPeriod.clone();
                    if (startDate.compareTo(firstPeriod) <= 0 && startDate.compareTo(yearMonthPeriod) <= 0) {
                        beanDate = new Utils.PeriodBean(startDate.getTime(), firstPeriod.getTime());
                    } else {
                        beanDate = new PeriodBean(startDate.getTime(), yearMonthPeriod.getTime());
                        nextPeriod = (Calendar) yearMonthPeriod.clone();
                    }
                    beanList.add(beanDate);
                    nextPeriod.add(Calendar.DAY_OF_MONTH, 1);
                    return getMonthList(beanList, nextPeriod, endDate);
                }
            }
        }
        // 同年不同月
        else
            // 1.一期
//            if (startDate.compareTo(yearMonthPeriod) > 0 && endDate.compareTo(thisC) <= 0){
            if ((startDate.compareTo(yearMonthPeriod) > 0 && endDate.compareTo(thisC) <= 0) || (startDate.compareTo(firstPeriod) > 0 && endDate.compareTo(lasetPeriod) <= 0 && endDate.compareTo(yearMonthPeriod) <= 0 && endMonth - startMonth <= 1)) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), endDate.getTime());
                beanList.add(beanDate);
                return beanList;
            }
            // 2.多期
            else {
                Calendar firestEndPeriod = (Calendar) firstPeriod.clone();
                // 1.开始日期小于等于开始日期规定的日期 2010-10-12 2010-10-20
                if (startDate.compareTo(firstPeriod) <= 0 && startDate.compareTo(yearMonthPeriod) <= 0) {
                    beanDate = new Utils.PeriodBean(startDate.getTime(), firstPeriod.getTime());
                }
                // 开始日期大于开始日期规定的日期 2010-10-22 2010-10-20
                else {
                    beanDate = new Utils.PeriodBean(startDate.getTime(), yearMonthNOPeriod.getTime());
                    firestEndPeriod = (Calendar) yearMonthNOPeriod.clone();
                }
                beanList.add(beanDate);
                firestEndPeriod.add(Calendar.DAY_OF_MONTH, 1);
                return getMonthList(beanList, firestEndPeriod, endDate);
            }
    }

    /**
     * 按月结算 每年12-31 为结束日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Utils.PeriodBean> getMonthPeriod(List<Utils.PeriodBean> beanList, Calendar startDate, Calendar endDate) {
        int startYear = startDate.get(Calendar.YEAR); // 开始年
        int startMonth = startDate.get(Calendar.MONTH);// 开始月

        int endYear = endDate.get(Calendar.YEAR);// 结束年
        int endMonth = endDate.get(Calendar.MONTH);// 结束月

        Calendar firstPeriod = Calendar.getInstance();
        firstPeriod.set(startYear, startMonth, 20);// 开始时间的 初始月天数为 20号

        Calendar lasetPeriod = Calendar.getInstance();//结束时间的 初始天数为20号
        lasetPeriod.set(endYear, endMonth, 20);

        Calendar yearPeriod = Calendar.getInstance();//开始时间的年份末尾
        yearPeriod.set(startYear, 11, 31);

        Calendar yearMonthPeriod = Calendar.getInstance(); // 最后一个月为12月20号
        yearMonthPeriod.set(startYear, 11, 20);

        Calendar yearMonthNOPeriod = Calendar.getInstance();// 开始时间的后一期
        yearMonthNOPeriod.set(startYear, startMonth + 1, 20);

        Utils.PeriodBean beanDate = null;
        //一期
        if (((startDate.compareTo(firstPeriod) <= 0 && endDate.compareTo(firstPeriod) <= 0) || (startDate.compareTo(firstPeriod) > 0 && endDate.compareTo(yearMonthNOPeriod) <= 0) || (startDate.compareTo(
                yearPeriod) <= 0 && endDate.compareTo(yearPeriod) <= 0 && startDate.compareTo(yearMonthPeriod) >= 0)) &&
                (endDate.compareTo(yearPeriod) <= 0 && startDate.compareTo(yearPeriod) <= 0)) {
            beanDate = new Utils.PeriodBean(startDate.getTime(), endDate.getTime());
            beanList.add(beanDate);
            return beanList;
        } else {
            Calendar firestEndPeriod = (Calendar) firstPeriod.clone();
            if ((startDate.compareTo(yearMonthPeriod) >= 0 && startDate.compareTo(yearPeriod) <= 0 && endDate.compareTo(yearPeriod) > 0)) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), yearPeriod.getTime());
                beanList.add(beanDate);
                firestEndPeriod = (Calendar) yearMonthNOPeriod.clone();
                firestEndPeriod.set(startYear + 1, 0, 1);
                return getMonthPeriod(beanList, firestEndPeriod, endDate);
            } else if ((startDate.compareTo(firstPeriod) <= 0 && startDate.compareTo(yearMonthPeriod) <= 0)) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), firstPeriod.getTime());
            }
            // 开始日期大于开始日期规定的日期 2010-10-22 2010-11-20
            else {
                beanDate = new Utils.PeriodBean(startDate.getTime(), yearMonthNOPeriod.getTime());
                firestEndPeriod = (Calendar) yearMonthNOPeriod.clone();
            }
            beanList.add(beanDate);
            firestEndPeriod.add(Calendar.DAY_OF_MONTH, 1);
            return getMonthPeriod(beanList, firestEndPeriod, endDate);
        }
    }

    /**
     * 计提利息按季度的31计算
     *
     * @param beanList
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Utils.PeriodBean> getQuarterListFor31(List<Utils.PeriodBean> beanList, Calendar startDate, Calendar endDate) {
        int startYear = startDate.get(Calendar.YEAR); // 开始年
        Utils.PeriodBean beanDate = null;

        Calendar oneEnd = Calendar.getInstance();
        oneEnd.set(startYear, 2, 20);// 第一期结束时间

        Calendar twoEnd = Calendar.getInstance();
        twoEnd.set(startYear, 5, 20);// 第二期结束时间

        Calendar threeEnd = Calendar.getInstance();
        threeEnd.set(startYear, 8, 20);// 第三期结束时间

        Calendar fourEnd = Calendar.getInstance();
        fourEnd.set(startYear, 11, 20);// 第四期结束时间

        Calendar fiveEnd = Calendar.getInstance();
        fiveEnd.set(startYear, 11, 31);// 第五期结束时间

        Calendar firstYear = Calendar.getInstance();
        if (startDate.compareTo(endDate) < 0) {
            //计算第一期
            if (startDate.compareTo(oneEnd) < 0 && endDate.compareTo(oneEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), oneEnd.getTime());
                beanList.add(beanDate);
                oneEnd.add(Calendar.DAY_OF_MONTH, 1);
                return getQuarterListFor31(beanList, oneEnd, endDate);
            }
            //计算第二期
            else if (startDate.compareTo(twoEnd) < 0 && endDate.compareTo(twoEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), twoEnd.getTime());
                beanList.add(beanDate);
                twoEnd.add(Calendar.DAY_OF_MONTH, 1);
                return getQuarterListFor31(beanList, twoEnd, endDate);
            }
            //计算第三期
            else if (startDate.compareTo(threeEnd) < 0 && endDate.compareTo(threeEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), threeEnd.getTime());
                beanList.add(beanDate);
                threeEnd.add(Calendar.DAY_OF_MONTH, 1);
                return getQuarterListFor31(beanList, threeEnd, endDate);
            }
            //计算第四期
            else if (startDate.compareTo(fourEnd) < 0 && endDate.compareTo(fourEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), fourEnd.getTime());
                beanList.add(beanDate);
                fourEnd.add(Calendar.DAY_OF_MONTH, 1);
                return getQuarterListFor31(beanList, fourEnd, endDate);
            }
            //计算第五期
            else if (startDate.compareTo(fiveEnd) < 0 && endDate.compareTo(fiveEnd) > 0) {
                beanDate = new Utils.PeriodBean(startDate.getTime(), fiveEnd.getTime());
                beanList.add(beanDate);
                firstYear.set(startYear + 1, 0, 1);
                return getQuarterListFor31(beanList, firstYear, endDate);
            } else {
                beanDate = new Utils.PeriodBean(startDate.getTime(), endDate.getTime());
                beanList.add(beanDate);
            }
        }
        return beanList;
    }

    /**
     * 期间
     *
     * @author 徐兴
     */
    public static class PeriodBean {

        Date startDate;

        Date endDate;

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public PeriodBean(Date startDate, Date endDate) {
            super();
            this.endDate = endDate;
            this.startDate = startDate;
        }

        @Override
        public String toString() {
            return "startDate: " + DateUtil.formatDate(startDate) + ", endDate:" + DateUtil.formatDate(endDate);
        }
    }
}
