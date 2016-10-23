package com.github.gkhere.utils;

import com.github.gkhere.bean.ScoreBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/8/20.
 */
public class ParseScoreUtils {

    private String response;

    public ParseScoreUtils(String response) {
        this.response = response;
    }


    public List<ScoreBean> parseScore() {
        List<ScoreBean> scoreList = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Element dataGrid1 = document.getElementById("Datagrid1");
        try {
            Elements trs = dataGrid1.select("tr");
            //trs.get(0).select("td").remove();
            for (int i = 1; i < trs.size(); i++) {
                ScoreBean bean = new ScoreBean();
                Elements tds = trs.get(i).select("td");
                for (int j = 0; j < tds.size(); j++) {
                    switch (j) {
                        case 0:
                            bean.setScoreYear(tds.get(j).text());
                            break;
                        case 1:
                            bean.setScoreSemester(tds.get(j).text());
                            break;
                        case 2:
                            bean.setScoreId(tds.get(j).text());
                            break;
                        case 3:
                            bean.setScoreName(tds.get(j).text());
                            break;
                        case 4:
                            bean.setScoreKind(tds.get(j).text());
                            break;
                        case 5:
                            bean.setScoreHome(tds.get(j).text());
                            break;
                        case 6:
                            bean.setScoreCredit(tds.get(j).text());
                            break;
                        case 7:
                            bean.setScorePoint(tds.get(j).text());
                            break;
                        case 8:
                            bean.setScore(tds.get(j).text());
                            break;
                        case 9:
                            bean.setScoreMark(tds.get(j).text());
                            break;
                        case 10:
                            bean.setScoreresults(tds.get(j).text());
                            break;
                        case 11:
                            bean.setScoreRebuiltResults(tds.get(j).text());
                            break;
                        case 12:
                            bean.setScoreCollege(tds.get(j).text());
                            break;
                        case 13:
                            bean.setScoreRemark(tds.get(j).text());
                            break;
                        case 14:
                            bean.setScoreRebuiltMark(tds.get(j).text());
                            break;
                    }
                }
                scoreList.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (ScoreBean bean:scoreList) {
            System.out.println(bean.toString());
        }
        return scoreList;
    }

    /**
     * 返回成绩查询页面的年份集合
     *
     * @return
     */
    public List<String> parseSelectYearList() {
        Document document = Jsoup.parse(response);
        Element select = document.getElementById("ddlXN");
        Elements options = select.select("option");
        List<String> tempList = new ArrayList<>();
        List<String> yearList = new ArrayList<>();
        for (Element option : options) {
            tempList.add(option.text());
        }
        for (int j = tempList.size() - 1; j > 0; j--) {
            yearList.add(tempList.get(j));
        }
        return yearList;
    }

}
