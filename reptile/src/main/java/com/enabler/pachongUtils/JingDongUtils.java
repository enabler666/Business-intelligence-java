package com.enabler.pachongUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class JingDongUtils {

    /**
     * 可以爬取固定物品的名称和价格只有第一页
     *
     * @throws IOException
     */
    public void getPageOne() throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("固态硬盘价格列表");

        Document html = Jsoup.parse(new URL("https://search.jd.com/Search?keyword=%E5%9B%BA%E6%80%81%E7%A1%AC%E7%9B%98&wq=%E5%9B%BA%E6%80%81%E7%A1%AC%E7%9B%98&page=1"), 5000);
        Elements pricePage = html.select(".p-price i");
        Elements name = html.select(".p-name-type-2 em");

        List<String> prices = pricePage.eachText();
        List<String> nameArray = name.eachText();

//        String patternPrice = "<i data-price=\"(.*)\">(.*)</i>";
//        Pattern priceM = Pattern.compile(patternPrice);

        HSSFRow title = sheet.createRow(0);
        HSSFCell titleName = title.createCell(0);
        titleName.setCellValue("名称");
        HSSFCell titlePrice = title.createCell(1);
        titlePrice.setCellValue("价格");

        for (int i = 0; i < prices.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            HSSFCell tempName = row.createCell(0);
            HSSFCell tempPrice = row.createCell(1);

            tempPrice.setCellValue(prices.get(i));
            tempName.setCellValue(nameArray.get(i));
        }

        FileOutputStream output = new FileOutputStream("E:\\study_res\\menu.xls");
        wb.write(output);
        output.flush();
        System.out.println("成功执行");
    }

    /**
     * 可以爬取自定物品的名称和价格自定义页码
     * @param itemName
     * @param start
     * @param end
     * @throws IOException
     */

    public void get(String itemName, int start, int end) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(itemName + "价格列表");
        HSSFRow title = sheet.createRow(0);
        HSSFCell titleName = title.createCell(0);
        titleName.setCellValue("名称");
        HSSFCell titlePrice = title.createCell(1);
        titlePrice.setCellValue("价格");

        String URLName = URLEncoder.encode(itemName, "utf-8");
        URLName = "https://search.jd.com/Search?keyword=" + URLName + "&page=";

        int nowLocal = 0;

        for (int page = start; page <= end; page++) {
            Document html = Jsoup.parse(new URL(URLName + page), 5000);
            Elements pricePage = html.select(".p-price i");
            Elements name = html.select(".p-name-type-2 em");

            List<String> prices = pricePage.eachText();
            List<String> nameArray = name.eachText();

            for (int i = 0; i < prices.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1 + nowLocal);
                HSSFCell tempName = row.createCell(0);
                HSSFCell tempPrice = row.createCell(1);

                tempPrice.setCellValue(prices.get(i));
                tempName.setCellValue(nameArray.get(i));
            }
            nowLocal += prices.size();
        }
        FileOutputStream output = new FileOutputStream("E:\\study_res\\menu.xls");
        wb.write(output);
        output.flush();
        System.out.println("成功执行");
    }
}
