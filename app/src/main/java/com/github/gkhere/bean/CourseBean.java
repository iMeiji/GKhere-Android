package com.github.gkhere.bean;

/**
 * Created by Meiji on 2016/8/11.
 */
public class CourseBean {

    // 形势与政策-周一-第1-2节-第2-4周|双周-侯东栋-3-402-(调0177)

    public static final String COURSEBEAN_course = "course";
    public static final String COURSEBEAN_day = "day";
    public static final String COURSEBEAN_timeinfo = "timeinfo";
    public static final String COURSEBEAN_week = "week";
    public static final String COURSEBEAN_teacher = "teacher";
    public static final String COURSEBEAN_location = "location";
    public static final String COURSEBEAN_extra = "extra";

    private String course;
    private String day;
    private String timeinfo;
    private String week;
    private String teacher;
    private String location;
    private String extra;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeinfo() {
        return timeinfo;
    }

    public void setTimeinfo(String timeinfo) {
        this.timeinfo = timeinfo;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    @Override
    public String toString() {
        return "CourseBean : " + getCourse() + "-" + getDay() + "-" + getTimeinfo() + "-" + getWeek() + "-" + getTeacher() + "-" + getLocation() + "-" + getExtra();
    }
}
