package com.github.gkhere.bean;

/**
 * Created by Meiji on 2016/8/20.
 */
public class ScoreBean {

    /**
     * <td>学年</td>
     * <td>学期</td>
     * <td>课程代码</td>
     * <td>课程名称</td>
     * <td>课程性质</td>
     * <td>课程归属</td>
     * <td>学分</td>
     * <td>绩点</td>
     * <td>成绩</td>
     * <td>辅修标记</td>
     * <td>补考成绩</td>
     * <td>重修成绩</td>
     * <td>学院名称</td>
     * <td>备注</td>
     * <td>重修标记</td>
     */

    private String scoreYear;   //学年
    private String scoreSemester;   //学期
    private String scoreId; //课程代码
    private String scoreName; //课程名称
    private String scoreKind;   //课程性质
    private String scoreHome;   //课程归属
    private String scoreCredit; //学分
    private String scorePoint;  //绩点
    private String score;   //成绩
    private String scoreMark;   //辅修标记
    private String scoreresults;    //补考成绩
    private String scoreRebuiltResults; //重修成绩
    private String scoreCollege;    //学院名称
    private String scoreRemark; //备注
    private String scoreRebuiltMark;    //重修标记

    public String getScoreYear() {
        return scoreYear;
    }

    public void setScoreYear(String scoreYear) {
        this.scoreYear = scoreYear;
    }

    public String getScoreSemester() {
        return scoreSemester;
    }

    public void setScoreSemester(String scoreSemester) {
        this.scoreSemester = scoreSemester;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public String getScoreKind() {
        return scoreKind;
    }

    public void setScoreKind(String scoreKind) {
        this.scoreKind = scoreKind;
    }

    public String getScoreHome() {
        return scoreHome;
    }

    public void setScoreHome(String scoreHome) {
        this.scoreHome = scoreHome;
    }

    public String getScoreCredit() {
        return scoreCredit;
    }

    public void setScoreCredit(String scoreCredit) {
        this.scoreCredit = scoreCredit;
    }

    public String getScorePoint() {
        return scorePoint;
    }

    public void setScorePoint(String scorePoint) {
        this.scorePoint = scorePoint;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreMark() {
        return scoreMark;
    }

    public void setScoreMark(String scoreMark) {
        this.scoreMark = scoreMark;
    }

    public String getScoreresults() {
        return scoreresults;
    }

    public void setScoreresults(String scoreresults) {
        this.scoreresults = scoreresults;
    }

    public String getScoreRebuiltResults() {
        return scoreRebuiltResults;
    }

    public void setScoreRebuiltResults(String scoreRebuiltResults) {
        this.scoreRebuiltResults = scoreRebuiltResults;
    }

    public String getScoreCollege() {
        return scoreCollege;
    }

    public void setScoreCollege(String scoreCollege) {
        this.scoreCollege = scoreCollege;
    }

    public String getScoreRemark() {
        return scoreRemark;
    }

    public void setScoreRemark(String scoreRemark) {
        this.scoreRemark = scoreRemark;
    }

    public String getScoreRebuiltMark() {
        return scoreRebuiltMark;
    }

    public void setScoreRebuiltMark(String scoreRebuiltMark) {
        this.scoreRebuiltMark = scoreRebuiltMark;
    }

    @Override
    public String toString() {
        return "ScoreBean" + getScoreYear() + " - " + getScoreSemester() + " - " +
                getScoreId() + " - " + getScoreName() + " - " + getScoreKind() + "" +
                " - " + getScoreHome() + " - " + getScoreCredit() + " - " +
                getScorePoint() + " - " + getScore() + " - " + getScoreMark() + " " +
                "- " + getScoreresults() + " - " + getScoreRebuiltResults() + " - " +
                "" + getScoreCollege() + " - " + getScoreRemark() + " - " + getScoreRebuiltMark();
    }
}
