package com.github.gkhere.bean;

/**
 * Created by Meiji on 2016/10/23.
 */

public class BaseInfoBean {

    public static final String BASEINFO_stuId = "stuId";
    public static final String BASEINFO_stuName = "stuName";
    public static final String BASEINFO_stuPasswd = "stuPasswd";
    public static final String BASEINFO_hostUrl = "hostUrl";
    public static final String BASEINFO_codeUrl = "codeUrl";
    public static final String BASEINFO_loginUrl = "loginUrl";
    public static final String BASEINFO_userAgent = "userAgent";
    public static final String BASEINFO_searchScoreUrl = "searchScoreUrl";
    public static final String BASEINFO_searchCourseUrl = "searchCourseUrl";


    private String hostUrl = "61.142.33.204";
    private String codeUrl = "http://61.142.33.204/CheckCode.aspx";
    private String loginUrl = "http://61.142.33.204/default2.aspx";

    private String searchCourseUrl = "http://61.142.33.204/xskbcx.aspx?xh=stuId&xm=stuName&gnmkdm=N121603";//个人课表查询
    private String searchScoreUrl = "http://61.142.33.204/xscj_gc.aspx?xh=stuId&xm=stuName&gnmkdm=N121605";//学习成绩查询

    private String stuName = "";
    private String stuId = "";
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 " +
            "Safari/537.36";

    public BaseInfoBean() {
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSearchCourseUrl() {
        return searchCourseUrl;
    }

    public void setSearchCourseUrl(String searchCourseUrl) {
        this.searchCourseUrl = searchCourseUrl;
    }

    public String getSearchScoreUrl() {
        return searchScoreUrl;
    }

    public void setSearchScoreUrl(String searchScoreUrl) {
        this.searchScoreUrl = searchScoreUrl;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
