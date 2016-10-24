package com.github.gkhere.utils;


import com.github.gkhere.bean.CourseBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/8/10.
 */

public class PareseCourseUtils {

    private static List<String> preList = new ArrayList<>();
    private static List<CourseBean> courseList = new ArrayList<>();

    public static List<CourseBean> getCourse(String response) {

        try {
            Document document = Jsoup.parse(response);
            Element table1 = document.getElementById("Table1");
            // 拿到tbody
            Element tbody = table1.select("tbody").get(0);
            // 去除前面两行 ，时间和早晨
            tbody.child(0).remove();
            tbody.child(0).remove();
            // 去除上午，下午，晚上
            tbody.child(0).child(0).remove();
            tbody.child(4).child(0).remove();
            tbody.child(8).child(0).remove();
            // 去除无用行之后，还剩余
            Elements trs = tbody.select("tr");
            int rowNum = trs.size(); //10
            // 用map储存数据
            for (int i = 0; i < rowNum; i++) {
                if (i % 2 == 0) {
                    // 拿到每一行
                    Element tr = trs.get(i);
                    // 一星期7天
                    for (int j = 0; j < 7; j++) {
                        Element td = tr.child(j);
                        if (td.hasAttr("rowspan")) {
                            String content = td.text();
                            System.out.println("所有课表 ：" + content);
                            preList.add(content);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析课表错误");
        }

        return SplitCourse();
    }

    public static String setWeek(String text) {
        switch (text) {
            case "周一":
                text = "1";
                break;
            case "周二":
                text = "2";

                break;
            case "周三":
                text = "3";

                break;
            case "周四":
                text = "4";

                break;
            case "周五":
                text = "5";

                break;
            case "周六":
                text = "6";

                break;
            case "周日":
                text = "7";
                break;
        }
        return text;
    }

    private static List<CourseBean> SplitCourse() {
        for (int i = 0; i < preList.size(); i++) {

            //System.out.println("所有课程 : " + preList.get(i));
            String content = preList.get(i);
            String[] splits = content.split(" ");
            //            System.out.println(courses.length);
            if (splits.length == 4) {
                // 嵌入式系统基础 周二第1,2节{第1-15周} 彭玲 工一-204
                try {
                    CourseBean bean = new CourseBean();
                    String course = splits[0];
                    String day = splits[1].substring(0, 2);
                    int di = splits[1].indexOf("第");
                    int jie = splits[1].indexOf("节") + 1;
                    String timeinfo = splits[1].substring(di, jie).replace(",", "-");
                    int lastdi = splits[1].lastIndexOf("第");
                    //int lastjie = splits[1].indexOf("周") + 1;
                    String week = splits[1].substring(lastdi, splits[1].length() - 1);
                    String teacher = splits[2];
                    String location = splits[3];
                    String extra = "";
                    bean.setCourse(course);
                    bean.setDay(day);
                    bean.setTimeinfo(timeinfo);
                    bean.setWeek(week);
                    bean.setTeacher(teacher);
                    bean.setLocation(location);
                    bean.setExtra(extra);
                    courseList.add(bean);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    throw new RuntimeException("封装课表错误 - 1");
                }
            } else if (splits.length == 5) {
                // 形势与政策 周五第5,6节{第6-6周} 侯东栋 3-303 (调0177)
                try {
                    CourseBean bean = new CourseBean();
                    String course = splits[0];
                    String day = splits[1].substring(0, 2);
                    int di = splits[1].indexOf("第");
                    int jie = splits[1].indexOf("节") + 1;
                    String timeinfo = splits[1].substring(di, jie).replace(",", "-");
                    int lastdi = splits[1].lastIndexOf("第");
                    String week = splits[1].substring(lastdi, splits[1].length() - 1);
                    String teacher = splits[2];
                    String location = splits[3];
                    String extra = splits[4];
                    bean.setCourse(course);
                    bean.setDay(day);
                    bean.setTimeinfo(timeinfo);
                    bean.setWeek(week);
                    bean.setTeacher(teacher);
                    bean.setLocation(location);
                    bean.setExtra(extra);
                    courseList.add(bean);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    throw new RuntimeException("封装课表错误 - 2");
                }
            } else if (splits.length == 8) {
                // 大学英语 周一第1,2节{第1-16周} 刘颖 4-501 C#课程设计 周一第1,2节{第16-16周} 陈刚 9-602
                try {
                    CourseBean bean = new CourseBean();
                    String course = splits[0];
                    String day = splits[1].substring(0, 2);
                    int di = splits[1].indexOf("第");
                    int jie = splits[1].indexOf("节") + 1;
                    String timeinfo = splits[1].substring(di, jie).replace(",", "-");
                    int lastdi = splits[1].lastIndexOf("第");
                    String week = splits[1].substring(lastdi, splits[1].length() - 1);
                    String teacher = splits[2];
                    String location = splits[3];
                    String extra = "";
                    bean.setCourse(course);
                    bean.setDay(day);
                    bean.setTimeinfo(timeinfo);
                    bean.setWeek(week);
                    bean.setTeacher(teacher);
                    bean.setLocation(location);
                    bean.setExtra(extra);
                    courseList.add(bean);

                    CourseBean bean2 = new CourseBean();
                    String course2 = splits[5];
                    String day2 = splits[6].substring(0, 2);
                    int di2 = splits[1].indexOf("第");
                    int jie2 = splits[1].indexOf("节") + 1;
                    String timeinfo2 = splits[6].substring(di2, jie2).replace(",", "-");
                    int lastdi2 = splits[1].lastIndexOf("第");
                    String week2 = splits[6].substring(lastdi2, splits[6].length() - 1);
                    String teacher2 = splits[7];
                    String location2 = splits[8];
                    String extra2 = "";
                    bean2.setCourse(course2);
                    bean2.setDay(day2);
                    bean2.setTimeinfo(timeinfo2);
                    bean2.setWeek(week2);
                    bean2.setTeacher(teacher2);
                    bean2.setLocation(location2);
                    bean2.setExtra(extra2);
                    courseList.add(bean2);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    throw new RuntimeException("封装课表错误 - 3");
                }
            } else if (splits.length == 9) {

                if (splits[4].contains("调")) {
                    // 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 (调0334) C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602
                    try {
                        CourseBean bean = new CourseBean();
                        String course = splits[0];
                        String day = splits[1].substring(0, 2);
                        int di = splits[1].indexOf("第");
                        int jie = splits[1].indexOf("节") + 1;
                        String timeinfo = splits[1].substring(di, jie).replace(",", "-");
                        int lastdi = splits[1].lastIndexOf("第");
                        String week = splits[1].substring(lastdi, splits[1].length() - 1);
                        String teacher = splits[2];
                        String location = splits[3];
                        String extra = splits[4];
                        bean.setCourse(course);
                        bean.setDay(day);
                        bean.setTimeinfo(timeinfo);
                        bean.setWeek(week);
                        bean.setTeacher(teacher);
                        bean.setLocation(location);
                        bean.setExtra(extra);
                        courseList.add(bean);

                        CourseBean bean2 = new CourseBean();
                        String course2 = splits[5];
                        String day2 = splits[6].substring(0, 2);
                        int di2 = splits[1].indexOf("第");
                        int jie2 = splits[1].indexOf("节") + 1;
                        String timeinfo2 = splits[6].substring(di2, jie2).replace(",", "-");
                        int lastdi2 = splits[6].lastIndexOf("第");
                        String week2 = splits[6].substring(lastdi2, splits[6].length() - 1);
                        String teacher2 = splits[7];
                        String location2 = splits[8];
                        String extra2 = "";
                        bean2.setCourse(course2);
                        bean2.setDay(day2);
                        bean2.setTimeinfo(timeinfo2);
                        bean2.setWeek(week2);
                        bean2.setTeacher(teacher2);
                        bean2.setLocation(location2);
                        bean2.setExtra(extra2);
                        courseList.add(bean2);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        throw new RuntimeException("封装课表错误 - 3");
                    }
                } else if (splits[splits.length - 1].contains("调")) {
                    // C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 (调0334)
                    try {
                        CourseBean bean = new CourseBean();
                        String course = splits[0];
                        String day = splits[1].substring(0, 2);
                        int di = splits[1].indexOf("第");
                        int jie = splits[1].indexOf("节") + 1;
                        String timeinfo = splits[1].substring(di, jie).replace(",", "-");
                        int lastdi = splits[1].lastIndexOf("第");
                        String week = splits[1].substring(lastdi, splits[1].length() - 1);
                        String teacher = splits[2];
                        String location = splits[3];
                        String extra = "";
                        bean.setCourse(course);
                        bean.setDay(day);
                        bean.setTimeinfo(timeinfo);
                        bean.setWeek(week);
                        bean.setTeacher(teacher);
                        bean.setLocation(location);
                        bean.setExtra(extra);
                        courseList.add(bean);

                        CourseBean bean2 = new CourseBean();
                        String course2 = splits[4];
                        String day2 = splits[5].substring(0, 2);
                        int di2 = splits[1].indexOf("第");
                        int jie2 = splits[1].indexOf("节") + 1;
                        String timeinfo2 = splits[5].substring(di2, jie2).replace(",", "-");
                        int lastdi2 = splits[1].lastIndexOf("第");
                        String week2 = splits[5].substring(lastdi2, splits[5].length() - 1);
                        String teacher2 = splits[6];
                        String location2 = splits[7];
                        String extra2 = splits[7];
                        bean2.setCourse(course2);
                        bean2.setDay(day2);
                        bean2.setTimeinfo(timeinfo2);
                        bean2.setWeek(week2);
                        bean2.setTeacher(teacher2);
                        bean2.setLocation(location2);
                        bean2.setExtra(extra2);
                        courseList.add(bean2);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        throw new RuntimeException("封装课表错误 - 3");
                    }
                }

            } else if (splits.length == 10) {
                // 形势与政策 周一第1,2节{第2-4周|双周} 侯东栋 3-402 (调0177) 形势与政策 周一第1,2节{第8-14周|双周} 侯东栋 3-402 (调0177)
                try {
                    CourseBean bean = new CourseBean();
                    String course = splits[0];
                    String day = splits[1].substring(0, 2);
                    int di = splits[1].indexOf("第");
                    int jie = splits[1].indexOf("节") + 1;
                    String timeinfo = splits[1].substring(di, jie).replace(",", "-");
                    int lastdi = splits[1].lastIndexOf("第");
                    String week = splits[1].substring(lastdi, splits[1].length() - 1);
                    String teacher = splits[2];
                    String location = splits[3];
                    String extra = splits[4];
                    bean.setCourse(course);
                    bean.setDay(day);
                    bean.setTimeinfo(timeinfo);
                    bean.setWeek(week);
                    bean.setTeacher(teacher);
                    bean.setLocation(location);
                    bean.setExtra(extra);
                    courseList.add(bean);

                    CourseBean bean2 = new CourseBean();
                    String course2 = splits[5];
                    String day2 = splits[6].substring(0, 2);
                    int di2 = splits[1].indexOf("第");
                    int jie2 = splits[1].indexOf("节") + 1;
                    String timeinfo2 = splits[6].substring(di2, jie2).replace(",", "-");
                    int lastdi2 = splits[1].lastIndexOf("第");
                    String week2 = splits[6].substring(lastdi2, splits[6].length() - 1);
                    String teacher2 = splits[7];
                    String location2 = splits[8];
                    String extra2 = splits[9];
                    bean2.setCourse(course2);
                    bean2.setDay(day2);
                    bean2.setTimeinfo(timeinfo2);
                    bean2.setWeek(week2);
                    bean2.setTeacher(teacher2);
                    bean2.setLocation(location2);
                    bean2.setExtra(extra2);
                    courseList.add(bean2);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    throw new RuntimeException("封装课表错误 - 3");
                }
            } else if (splits.length == 14) {
                // 线性代数 周四第3,4节{第1-7周} 易宗向 8-403 (调0334) 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 (调0334) C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602
                if (splits[4].contains("调")) {
                    try {
                        CourseBean bean = new CourseBean();
                        String course = splits[0];
                        String day = splits[1].substring(0, 2);
                        int di = splits[1].indexOf("第");
                        int jie = splits[1].indexOf("节") + 1;
                        String timeinfo = splits[1].substring(di, jie).replace(",", "-");
                        int lastdi = splits[1].lastIndexOf("第");
                        String week = splits[1].substring(lastdi, splits[1].length() - 1);
                        String teacher = splits[2];
                        String location = splits[3];
                        String extra = splits[4];
                        bean.setCourse(course);
                        bean.setDay(day);
                        bean.setTimeinfo(timeinfo);
                        bean.setWeek(week);
                        bean.setTeacher(teacher);
                        bean.setLocation(location);
                        bean.setExtra(extra);
                        courseList.add(bean);

                        CourseBean bean2 = new CourseBean();
                        String course2 = splits[5];
                        String day2 = splits[6].substring(0, 2);
                        int di2 = splits[1].indexOf("第");
                        int jie2 = splits[1].indexOf("节") + 1;
                        String timeinfo2 = splits[6].substring(di2, jie2).replace(",", "-");
                        int lastdi2 = splits[6].lastIndexOf("第");
                        String week2 = splits[6].substring(lastdi2, splits[6].length() - 1);
                        String teacher2 = splits[7];
                        String location2 = splits[8];
                        String extra2 = "";
                        bean2.setCourse(course2);
                        bean2.setDay(day2);
                        bean2.setTimeinfo(timeinfo2);
                        bean2.setWeek(week2);
                        bean2.setTeacher(teacher2);
                        bean2.setLocation(location2);
                        bean2.setExtra(extra2);
                        courseList.add(bean2);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        throw new RuntimeException("封装课表错误 - 3");
                    }
                }
            }
        }

        for (CourseBean list : courseList) {
            System.out.println(list);
        }

        return courseList;
    }
}