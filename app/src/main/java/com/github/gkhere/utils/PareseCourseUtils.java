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

    /**
     * 解析网页 从中获取课表
     *
     * @param response
     * @return
     */
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
                            System.out.println("解析课表 ：" + content);
                            preList.add(content);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析课表错误");
        }

        // 整理课表
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

    /**
     * 整理课表 并封装好
     * @return
     */
    private static List<CourseBean> SplitCourse() {

        List<String[]> list = new ArrayList<>();

        for (String aPreList : preList) {
            //System.out.println("所有课程 : " + aPreList);
            String[] splits = aPreList.split(" ");

            if (splits.length == 4) {
                // 形势与政策 周一第1,2节{第2-4周|双周} 侯东栋 3-402
                String[] split = new String[4];
                System.arraycopy(splits, 0, split, 0, splits.length);
                list.add(split);

            } else if (splits.length == 5 && splits[4].contains("调")) {
                // 形势与政策 周五第5,6节{第6-6周} 侯东栋 3-303 (调0334)
                String[] split = new String[5];
                System.arraycopy(splits, 0, split, 0, splits.length);
                list.add(split);

            } else if (splits.length == 8) {
                // 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602
                String[] split = new String[4];
                System.arraycopy(splits, 0, split, 0, 4);
                list.add(split);

                String[] split2 = new String[4];
                System.arraycopy(splits, 4, split2, 0, 4);
                list.add(split2);

            } else if (splits.length == 9 && splits[4].contains("调")) {
                // 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 (调0334) C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602
                String[] split = new String[5];
                System.arraycopy(splits, 0, split, 0, 5);
                list.add(split);

                String[] split2 = new String[4];
                System.arraycopy(splits, 5, split2, 0, 4);
                list.add(split2);

            } else if (splits.length == 9 && splits[8].contains("调")) {
                // C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 (调0334)
                String[] split = new String[4];
                System.arraycopy(splits, 0, split, 0, 4);
                list.add(split);

                String[] split2 = new String[5];
                System.arraycopy(splits, 4, split2, 0, 5);
                list.add(split2);

            } else if (splits.length == 10 && splits[4].contains("调") && splits[9].contains("调")) {
                // 形势与政策 周一第1,2节{第2-4周|双周} 侯东栋 3-402 (调0177) 形势与政策 周一第1,2节{第8-14周|双周} 侯东栋 3-402 (调0177)
                String[] split = new String[5];
                System.arraycopy(splits, 0, split, 0, 5);
                list.add(split);

                String[] split2 = new String[5];
                System.arraycopy(splits, 5, split2, 0, 5);
                list.add(split2);

            } else if (splits.length == 14 && splits[4].contains("调") && splits[9].contains("调")) {
                // 线性代数 周四第3,4节{第1-7周} 易宗向 8-403 (调0334) 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 (调0334) C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602
                String[] split = new String[5];
                System.arraycopy(splits, 0, split, 0, 5);
                list.add(split);

                String[] split2 = new String[5];
                System.arraycopy(splits, 5, split2, 0, 5);
                list.add(split2);

                String[] split3 = new String[4];
                System.arraycopy(splits, 10, split3, 0, 4);
                list.add(split3);

            } else if (splits.length == 14 && splits[4].contains("调") && splits[13].contains("调")) {
                // 线性代数 周四第3,4节{第1-7周} 易宗向 8-403 (调0334) 线性代数 周四第3,4节{第9-14周} 易宗向 8-403  C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602 (调0334)
                String[] split = new String[5];
                System.arraycopy(splits, 0, split, 0, 5);
                list.add(split);

                String[] split2 = new String[4];
                System.arraycopy(splits, 5, split2, 0, 4);
                list.add(split2);

                String[] split3 = new String[5];
                System.arraycopy(splits, 9, split3, 0, 5);
                list.add(split3);

            } else if (splits.length == 14 && splits[8].contains("调") && splits[13].contains("调")) {
                // 线性代数 周四第3,4节{第1-7周} 易宗向 8-403 线性代数 周四第3,4节{第9-14周} 易宗向 8-403 (调0334) C#课程设计 周四第3,4节{第16-16周} 陈刚 9-602 (调0334)
                String[] split = new String[4];
                System.arraycopy(splits, 0, split, 0, 4);
                list.add(split);

                String[] split2 = new String[5];
                System.arraycopy(splits, 4, split2, 0, 5);
                list.add(split2);

                String[] split3 = new String[5];
                System.arraycopy(splits, 9, split3, 0, 5);
                list.add(split3);
            }
        }

        // 封装CourseBean
        for (String[] strings : list) {
            try {
                CourseBean bean = new CourseBean();
                String course = strings[0];
                String day = strings[1].substring(0, 2);
                int di = strings[1].indexOf("第");
                int jie = strings[1].indexOf("节") + 1;
                String timeinfo = strings[1].substring(di, jie).replace(",", "-");
                int lastdi = strings[1].lastIndexOf("第");
                //int lastjie = splits[1].indexOf("周") + 1;
                String week = strings[1].substring(lastdi, strings[1].length() - 1);
                String teacher = strings[2];
                String location = strings[3];
                String extra = "";
                if (strings.length == 5) {
                    extra = strings[4];
                }
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
        }

        for (CourseBean bean : courseList) {
            System.out.println("封装课表 : " + bean);
        }

        return courseList;
    }
}