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

    private static List<CourseBean> courseList = new ArrayList<>();

    public static List<CourseBean> getCourse(String response) {
        Document document = Jsoup.parse(response);
        Element table1 = document.getElementById("Table1");
        //拿到tbody
        Element tbody = table1.select("tbody").get(0);
        //去除前面两行 ，时间和早晨
        tbody.child(0).remove();
        tbody.child(0).remove();
        //去除上午，下午，晚上
        tbody.child(0).child(0).remove();
        tbody.child(4).child(0).remove();
        tbody.child(8).child(0).remove();
        //去除无用行之后，还剩余
        Elements trs = tbody.select("tr");
        int rowNum = trs.size(); //10
        //用map储存数据
        for (int i = 0; i < rowNum; i++) {
            if (i % 2 == 0) {
                //拿到每一行
                Element tr = trs.get(i);
                //一星期7天
                for (int j = 0; j < 7 ; j++) {
                    Element colum = tr.child(j);
                    if (colum.hasAttr("rowspan")) {
                        CourseBean course = new CourseBean();
                        String text = colum.text();
                        System.out.println("所有课表 ：" + text);
                        String[] strings = text.split(" ");
                        /**
                         * 有三张情况
                         * 一.线性代数 周四第3,4节{第1-7周} 易宗向 8-403 (调0334) 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 (调0334) C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602
                         * 二.大学英语 周一第1,2节{第1-16周} 刘颖 4-501 C#课程设计 周一第1,2节{第16-16周} 陈刚 9-602
                         * 三.C#课程设计 周二第3,4节{第16-16周} 陈刚 9-602
                         */
                        if (strings.length > 9) {
                            course.setCourseName(strings[0]);
                            String timeInfos = strings[1];
                            String time = timeInfos.substring(0, 2);
                            course.setCourseTime(setWeek(time));
                            course.setCourseTimeDetail(timeInfos.substring(2,
                                    timeInfos.length()).replace(",", "-"));
                            course.setCourseTeacher(strings[2]);
                            course.setCourseLocation(strings[3]);
                            if (strings[4].contains("调")) {
                                course.setCourseInfo(strings[4]);
                            }
                            courseList.add(course);

                            CourseBean course2 = new CourseBean();
                            course2.setCourseName(strings[5]);
                            String timeInfos2 = strings[6];
                            String time2 = timeInfos2.substring(0, 2);
                            course2.setCourseTime(setWeek(time2));
                            course2.setCourseTimeDetail(timeInfos2.substring(2,
                                    timeInfos2.length()).replace(",", "-"));
                            course2.setCourseTeacher(strings[7]);
                            course2.setCourseLocation(strings[8]);
                            if (strings[9].contains("调")) {
                                course2.setCourseInfo(strings[9]);
                            }
                            courseList.add(course2);

                            CourseBean course3 = new CourseBean();
                            course3.setCourseName(strings[10]);
                            String timeInfos3 = strings[11];
                            String time3 = timeInfos3.substring(0, 2);
                            course3.setCourseTime(setWeek(time3));
                            course3.setCourseTimeDetail(timeInfos3.substring(2,
                                    timeInfos3.length()).replace(",", "-"));
                            course3.setCourseTeacher(strings[12]);
                            course3.setCourseLocation(strings[13]);
                            course3.setCourseInfo("");
                            courseList.add(course3);
                        }
                        else if (strings.length > 4 && strings.length <= 8 ) {
                            addCourse(course, strings, 0);
                            CourseBean course2 = new CourseBean();
                            addCourse(course2, strings, 4);
                        } else if (strings.length == 4) {
                            addCourse(course, strings, 0);
                        }

                    }
                }
            }
        }

        for (CourseBean bean : courseList) {
            System.out.println(bean.toString());
        }
        return courseList;
    }

    private static void addCourse(CourseBean course, String[] strings,int count) {
        course.setCourseName(strings[count]);
        String timeInfos = strings[++count];
        String time = timeInfos.substring(0, 2);
        course.setCourseTime(setWeek(time));
        course.setCourseTimeDetail(timeInfos.substring(2,
                timeInfos.length()).replace(",", "-"));
        course.setCourseTeacher(strings[++count]);
        course.setCourseLocation(strings[++count]);
        course.setCourseInfo("");
        courseList.add(course);
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
}