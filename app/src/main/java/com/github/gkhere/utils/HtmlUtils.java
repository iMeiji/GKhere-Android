package com.github.gkhere.utils;

import com.github.gkhere.bean.BaseInfoBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/8/10.
 */

public class HtmlUtils {

    public static String response;

    public static BaseInfoBean encoder(BaseInfoBean baseInfoBean) {

        if (!"".equals(response)) {
            Document document = Jsoup.parse(response);
            Elements links = document.select("a[href]");
            StringBuilder buffer = new StringBuilder();
            for (Element link : links) {
                if (link.text().equals("学习成绩查询")) {
                    buffer.append(link.attr("href"));
                }
            }
            String url = buffer.toString();
            // xscj_gc.aspx?xh=2014133217&xm=哈哈哈&gnmkdm=N121605

            // 获取学号
            int indexXh = url.indexOf("xh");
            int indexXhStart = url.indexOf("=", indexXh);
            int indexXhEnd = url.indexOf("&", indexXhStart);
            String stuId = url.substring(indexXhStart + 1, indexXhEnd);


            // 获取姓名
            int indexXm = url.indexOf("xm");
            int indexXmStrart = url.indexOf("=", indexXm);
            int indexXmEnd = url.lastIndexOf("&");
            String stuName = url.substring(indexXmStrart + 1, indexXmEnd);

            // 获取课表查询Url
            String searchCourseUrl = baseInfoBean.getSearchCourseUrl();
            searchCourseUrl = searchCourseUrl
                    .replace("stuId", stuId)
                    .replace("stuName", TextEncoderUtils.encoding(stuName));

            // 获取成绩查询Url
            String searchScoreUrl = baseInfoBean.getSearchScoreUrl();
            searchScoreUrl = searchScoreUrl
                    .replace("stuId", stuId)
                    .replace("stuName", TextEncoderUtils.encoding(stuName));

            // 保存数据到对象中
            baseInfoBean.setStuId(stuId);
            baseInfoBean.setStuName(stuName);
            baseInfoBean.setSearchCourseUrl(searchCourseUrl);
            baseInfoBean.setSearchScoreUrl(searchScoreUrl);

           /* // 转换编码
            try {
                String encodeName = URLEncoder.encode(stuName, "GBK");
                url = url.replace(stuName, encodeName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("----------HtmlUtils url----------" + url);*/
        }
        return baseInfoBean;
    }


    /*// 返回个人课表查询Url
    public static String getsearchCourseUrl() {
        searchCourseUrl = searchCourseUrl
                .replace("stuId", stuId)
                .replace("stuName", TextEncoderUtils.encoding(stuName));
        return searchCourseUrl;
    }

    // 返回学习成绩查询Url
    public static String getsearchScoreUrl() {
        searchScoreUrl = searchScoreUrl
                .replace("stuId", stuId)
                .replace("stuName", TextEncoderUtils.encoding(stuName));
        return searchScoreUrl;
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
