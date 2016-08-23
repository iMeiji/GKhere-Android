package com.github.gkhere.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/8/10.
 */


public class HtmlUtils {

    public static String hostUrl = "61.142.33.204";
    public static String codeUrl = "http://61.142.33.204/CheckCode.aspx";
    public static String loginUrl = "http://61.142.33.204/default2.aspx";
    public static String searchcourseUrl = "http://61.142.33.204/xskbcx.aspx?xh=stuXh&xm=stuName&gnmkdm=N121603";
    public static String searchscoreUrl = "http://61.142.33.204/xscj_gc.aspx?xh=stuXh&xm=stuName&gnmkdm=N121605";

    public static String response;
    public static String stuName = "";
    public static String stuXh = "";
    public static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 " +
            "Safari/537.36";


    public HtmlUtils(String response) {
        this.response = response;
    }

    public String encoder(String response) {
        //http://61.142.33.204/xscj_gc.aspx?xh=2014133217&xm=李铭智&gnmkdm=N121605

        Document document = Jsoup.parse(response);
        Elements links = document.select("a[href]");
        StringBuffer buffer = new StringBuffer();
        for (Element link : links) {
            if (link.text().equals("学习成绩查询")) {
                buffer.append(link.attr("href"));
            }
        }
        //System.out.println("----------HtmlUtils buffer----------" + buffer.toString());
        String url = buffer.toString();
        // xscj_gc.aspx?xh=2014133217&xm=李铭智&gnmkdm=N121605

        // 获取学号
        int indexXh = url.indexOf("xh");
        int indexXhStart  = url.indexOf("=", indexXh);
        int indexXhEnd = url.indexOf("&",indexXhStart);
        stuXh = url.substring(indexXhStart + 1, indexXhEnd);

        // 获取姓名
        int indexXm = url.indexOf("xm");
        int indexXmStrart = url.indexOf("=", indexXm);
        int indexXmEnd = url.lastIndexOf("&");
        stuName = url.substring(indexXmStrart + 1, indexXmEnd);

        // 转换编码
        try {
            String encodeName = URLEncoder.encode(stuName, "GBK");
            url = url.replace(stuName, encodeName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("----------HtmlUtils url----------" + url);
        return url;
    }

    public String getXhandName() {
        Document document = Jsoup.parse(response);
        Element xhxm = document.getElementById("xhxm");
        String text = xhxm.text();
        return text;
    }

    public static String getStuName() {
        return stuName;
    }

    public static String getStuXh() {
        return stuXh;
    }

    /*public List<ChengjiBean> parseCJTable() {
        List<ChengjiBean> cjList = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Element dataGrid1 = document.getElementById("DataGrid1");
        Elements trs = dataGrid1.select("tbody").select("tr");
        for (int i = 0; i < trs.size(); i++) {
            ChengjiBean bean = new ChengjiBean();
            Elements tds = trs.get(i).select("td");
            for (int j = 0; j < tds.size(); j++) {
                switch (j) {
                    case 0:
                        bean.setCourseId(tds.get(j).text());
                        break;
                    case 1:
                        bean.setCourseName(tds.get(j).text());
                        break;
                    case 2:
                        bean.setCourseXz(tds.get(j).text());
                        break;
                    case 3:
                        bean.setCourseCj(tds.get(j).text());
                        break;
                    case 4:
                        bean.setCourseGs(tds.get(j).text());
                        break;
                    case 5:
                        bean.setCourseBk(tds.get(j).text());
                        break;
                    case 6:
                        bean.setCourseCx(tds.get(j).text());
                        break;
                    case 7:
                        bean.setCourseXf(tds.get(j).text());
                        break;
                    case 8:
                        bean.setCourseBj(tds.get(j).text());
                        break;
                }
            }
            cjList.add(bean);
            System.out.println(bean.toString());
        }
        return cjList;
    }*/

    /**
     * 返回成绩查询页面的年份集合
     *
     * @return
     */
    public List<String> parseSelectList() {
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
