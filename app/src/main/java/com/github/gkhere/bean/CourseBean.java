package com.github.gkhere.bean;

/**
 * Created by Meiji on 2016/8/11.
 */
public class CourseBean {
    private String courseName;
    private String courseTime;
    private String courseTimeDetail;
    private String courseTeacher;
    private String courseLocation;
    private String courseInfo;

    public String getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

    public void setCourseLocation(String courseLocation) {
        this.courseLocation = courseLocation;
    }

    public String getCourseTimeDetail() {
        return courseTimeDetail;
    }

    public void setCourseTimeDetail(String courseTimeDetail) {
        this.courseTimeDetail = courseTimeDetail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    @Override
    public String toString() {
        return getCourseName() + "-" + getCourseTime() + "-" + getCourseTimeDetail
                () + "-" + getCourseTeacher() + "-" + getCourseLocation() + "-" +
                getCourseInfo();
    }
}
