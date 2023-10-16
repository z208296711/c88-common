package com.c88.common.web.util;

import com.c88.common.web.exception.BizException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

import static com.c88.common.core.result.ResultCode.SYSTEM_RESOURCE_ERROR;

/**
 * Excel工具
 */
@Slf4j
public class ExcelExporter {

    /**
     * XLSX最大行數
     */
    public static final int MAX_EXCEL_COUNT_BY_XLSX = 1048576;

    /**
     * SXSSFWorkbook暫存筆數
     */
    public static final int SXSSF_DATA_CACGE = 1024;

    /**
     * 生成xls
     *
     * @param dataList    Excel資料
     * @param tableHeader Excel抬頭
     * @param request     請求
     * @param response    響應
     * @param <T>
     */
    public static <T> void getExcel(List<T> dataList,
                                    String[] tableHeader, //【2】 设置表头：对Excel每列命名
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            HSSFWorkbook workbook = null;
            // 代表一行记录
            HSSFCell cell = null;
            HSSFRow row = null;
            //String fileSuffix = ".xls";
            String fileName = "merchant" + "_" + (int) (Math.random() * 9000 + 1000) + ".xls";

            //【1】创建excel
            workbook = new HSSFWorkbook();
            // 设置表头的类型
            HSSFCellStyle style = ExcelExporter.setCellStyle(workbook);

            // 创建一个sheet
            HSSFSheet sheet = workbook.createSheet("sheet1");


            // 设置Excel表头
            row = ExcelExporter.getExcelRow(workbook, sheet, tableHeader);
            //通过反射，获取POJO对象。由于同一个dataList中，POJO相同，故定义在for循环外部，提高遍历性能
            Class cl = dataList.get(0).getClass();
            //获取类的所有字段
            Field[] fields = cl.getDeclaredFields();
            //写入每一行的记录
            for (int i = 0; i < dataList.size(); i++) {
                //创建新的一行，递增
                row = sheet.createRow(i + 1);
                // 设置行高
                row.setHeight((short) 400);
                for (int j = 0; j < fields.length; j++) {
                    //设置字段可见，否则会报错，禁止访问
                    fields[j].setAccessible(true);
                    //创建单元格
                    cell = row.createCell(j);

                    String cellValue = Objects.toString(fields[j].get(dataList.get(i)), "");
                    if (cellValue.startsWith("http")) {
                        Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
                        href.setAddress(cellValue);
                        cell.setHyperlink(href);
                        cell.setCellStyle(ExcelExporter.setCellHTTPStyle(workbook, style));
                    }
                    cell.setCellValue(cellValue);
                    cell.setCellStyle(style);
                }
            }
            //下载文件
            ExcelExporter.outputExcelStream(request, response, workbook, fileName);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    /**
     * 生成xlsx
     *
     * @param dataList    Excel資料
     * @param tableHeader Excel抬頭
     * @param request     請求
     * @param response    響應
     * @param <T>
     */
    public static <T> void getExcelBySXSSF(List<T> dataList,
                                           String[] tableHeader,
                                           HttpServletRequest request,
                                           HttpServletResponse response,
                                           String fileName) {
        // 判斷超過XLSX最大行數時拋出錯誤
        if (dataList.size() > MAX_EXCEL_COUNT_BY_XLSX) {
            throw new BizException(SYSTEM_RESOURCE_ERROR);
        }

        try {
            //初始化
            SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSF_DATA_CACGE);
            Cell cell = null;
            Row row = null;

            // 设置表头的类型
            CellStyle style = setCellStyleBySXSSF(workbook);

            // 创建一个sheet
            Sheet sheet = workbook.createSheet("sheet1");

            // 设置Excel表头
            row = ExcelExporter.getExcelRowBySXSSF(workbook, sheet, tableHeader);

            //通过反射，获取POJO对象。由于同一个dataList中，POJO相同，故定义在for循环外部，提高遍历性能
            if (!dataList.isEmpty()) {
                Class cl = dataList.get(0).getClass();

                //获取类的所有字段
                Field[] fields = cl.getDeclaredFields();

                //写入每一行的记录
                for (int i = 0; i < dataList.size(); i++) {
                    //创建新的一行，递增
                    row = sheet.createRow(i + 1);

                    // 设置行高
                    row.setHeight((short) 400);
                    for (int j = 0; j < fields.length; j++) {
                        if (fields[j].getAnnotation(JsonIgnore.class) != null) {
                            continue;
                        }
                        //设置字段可见，否则会报错，禁止访问
                        fields[j].setAccessible(true);

                        //创建单元格
                        cell = row.createCell(j);

                        String cellValue = Objects.toString(fields[j].get(dataList.get(i)), "");
                        if (cellValue.startsWith("http")) {
                            Hyperlink href = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
                            href.setAddress(cellValue);
                            cell.setHyperlink(href);
                            cell.setCellStyle(setCellHTTPStyleBySXSSF(workbook, style));
                        }
                        cell.setCellValue(cellValue);
                        cell.setCellStyle(style);
                    }
                }
            }
            //下载文件
            ExcelExporter.outputExcelStreamBySXSSF(request, response, workbook, fileName);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    public static HSSFRow getExcelRow(HSSFWorkbook workbook, HSSFSheet sheet, String[] tableHeader) {
        short cellNumber = (short) tableHeader.length;
// Excel的第一行表头
        HSSFRow row = sheet.createRow(0);
// 设置行高
        row.setHeight((short) 560);
// Excel的列
        HSSFCell cell = null;
        HSSFFont font = workbook.createFont();
// 粗体显示
        font.setBold(Boolean.TRUE);
        font.setFontHeightInPoints((short) 15);
// 设置单元格字体的颜色
        font.setColor(HSSFFont.COLOR_NORMAL);
// 样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
//设置边框样式
        style.setBorderTop(BorderStyle.DOTTED);
        style.setBorderBottom(BorderStyle.DOTTED);
        style.setBorderLeft(BorderStyle.DOTTED);
        style.setBorderRight(BorderStyle.DOTTED);
//设置边框颜色
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

        for (int k = 0; k < cellNumber; k++) {
            int i = 0;
            i += k;
            short b = 6000;
            cell = row.createCell(i); // 创建第0行第k列
            cell.setCellValue(tableHeader[k]); // 设置第0行第k列的值
            sheet.setColumnWidth(i, b); // 设置列的宽度
            style.setFont(font); // 设置字体风格
            cell.setCellStyle(style);
        }
        return row;
    }


    /**
     * @param workbook    Excel
     * @param sheet       Sheet
     * @param tableHeader 抬頭
     * @return
     */
    public static Row getExcelRowBySXSSF(Workbook workbook, Sheet sheet, String[] tableHeader) {
        //取得抬頭的數量
        short cellNumber = (short) tableHeader.length;

        // Excel的第一行表头
        Row row = sheet.createRow(0);

        // 设置行高
        row.setHeight((short) 560);

        // Excel的列
        Cell cell = null;
        Font font = workbook.createFont();

        // 粗体显示
        font.setBold(Boolean.TRUE);
        font.setFontHeightInPoints((short) 15);

        // 设置单元格字体的颜色
        font.setColor(HSSFFont.COLOR_NORMAL);

        // 样式
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //设置边框样式
        style.setBorderTop(BorderStyle.DOTTED);
        style.setBorderBottom(BorderStyle.DOTTED);
        style.setBorderLeft(BorderStyle.DOTTED);
        style.setBorderRight(BorderStyle.DOTTED);

        //设置边框颜色
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

        for (int k = 0; k < cellNumber; k++) {
            int i = 0;
            i += k;
            short b = 6000;
            // 创建第0行第k列
            cell = row.createCell(i);

            // 设置第0行第k列的值
            cell.setCellValue(tableHeader[k]);

            // 设置列的宽度
            sheet.setColumnWidth(i, b);

            // 设置字体风格
            style.setFont(font);

            //寫入樣式
            cell.setCellStyle(style);
        }
        return row;
    }

    /**
     * 设置Excel未获取数据提示行
     *
     * @param workbook
     * @param sheet
     * @return
     */
    public static HSSFRow getNotResultInfoRow(HSSFWorkbook workbook, HSSFSheet sheet, String message) {
// Excel的第一行表头
        HSSFRow row = sheet.createRow(0);
// Excel的列
        HSSFCell cell = null;
// 设置表头的类型
        HSSFCellStyle style = workbook.createCellStyle();
//设置自动换行
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);

// 没有查到数据
        row = sheet.createRow((short) (0)); // 创建第j+1行
        row.setHeight((short) 1000); // 设置行高
        sheet.setColumnWidth(0, 10000);
        cell = row.createCell(0); // 创建第i+1行第1列
        cell.setCellValue(message);//
        cell.setCellStyle(style); // 设置风格

        return row;
    }

    /**
     * 下载EXCEL,对不同浏览器做中文名称兼容<br/>
     * 如果不对文件名进行编码处理，那么有些浏览器无法识别下载文件
     *
     * @param request
     * @param response
     * @param workbook
     * @param fileName
     */
    public static void outputExcelStream(HttpServletRequest request, HttpServletResponse response, HSSFWorkbook workbook, String fileName) {
        OutputStream out = null;
        try {
            String excelFileName = fileName;// + "_" + (int) (Math.random() * 9000 + 1000) + ".xls";
            if (isMsBrowser(request)) {
// ie,edge 浏览器
                excelFileName = URLEncoder.encode(excelFileName, "UTF-8");
            } else {
//其他的浏览器
                excelFileName = new String(excelFileName.getBytes("UTF-8"), "iso-8859-1");
            }
//输出流对象
            out = response.getOutputStream();
// 设置文件头编码方式和文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + excelFileName);
// 设置类型
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            workbook.write(out);
            out.flush();
            workbook.write(out);
        } catch (IOException e) {
            log.error("下载EXCEL失败，", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("下载EXCEL关闭文件流失败，", e);
            }
        }
    }

    /**
     * 下载EXCEL,对不同浏览器做中文名称兼容
     * 如果不对文件名进行编码处理，那么有些浏览器无法识别下载文件
     * ByXlsx
     *
     * @param request  請求
     * @param response 響應
     * @param workbook Excel
     * @param fileName 檔案名稱
     */
    public static void outputExcelStreamBySXSSF(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Workbook workbook,
                                                String fileName) {
        //初始化輸出流
        OutputStream out = null;
        try {
            // 檔案名稱
            String excelFileName = "";

            // 判斷是否為微軟的瀏覽器，根據選擇的瀏覽器選擇檔案名稱編碼
            if (isMsBrowser(request)) {
                // ie,edge 浏览器
                excelFileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //其他的浏览器
                excelFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }

            //輸出流對象
            out = response.getOutputStream();

            // 設定文件名稱
            response.setHeader("Content-Disposition", "attachment;filename=" + excelFileName);

            // 設定文件類型
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            workbook.write(out);
        } catch (IOException e) {
            log.error("下载EXCEL失败，", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("下载EXCEL关闭文件流失败，", e);
            }
        }
    }

    //判断是否是IE浏览器
    private static boolean isMsBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isBlank(userAgent)) {
            return false;
        }
        String[] IEBrowserSignals = {"msie", "trident", "edge"};
        for (String signal : IEBrowserSignals) {
            if (userAgent.toLowerCase().contains(signal)) {
                return true;
            }
        }
        return false;
    }

    public static HSSFCellStyle setCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
//设置自动换行
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
//设置边框样式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
//设置边框颜色
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

        HSSFDataFormat format = workbook.createDataFormat();
// 设置文本格式
        style.setDataFormat(format.getFormat("@"));
// 设置字体
        HSSFFont font = workbook.createFont();
//        font.setColor(Font.COLOR_RED);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);// 12号字体
        style.setFont(font);
        return style;
    }


    /**
     * 設定表頭樣式
     *
     * @param workbook Excel
     * @return
     */
    public static CellStyle setCellStyleBySXSSF(SXSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //设置自动换行
        style.setWrapText(true);

        //置中
        style.setAlignment(HorizontalAlignment.CENTER);

        //设置边框样式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        //设置边框颜色
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

        //初始化日期樣式
        DataFormat format = workbook.createDataFormat();

        // 设置文本格式
        style.setDataFormat(format.getFormat("@"));

        // 初始化字体
        Font font = workbook.createFont();

        //字行名稱
        font.setFontName("宋体");

        //字體大小
        font.setFontHeightInPoints((short) 12);

        //寫入樣式
        style.setFont(font);
        return style;
    }

    public static HSSFCellStyle setCellHTTPStyle(HSSFWorkbook workbook, HSSFCellStyle style) {
        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        style.setFont(font);

        return style;
    }

    /**
     * 增加Https的字型樣式
     *
     * @param workbook Excel
     * @param style    樣式
     * @return
     */
    public static CellStyle setCellHTTPStyleBySXSSF(Workbook workbook, CellStyle style) {
        //初始化字型
        Font font = workbook.createFont();

        // 字型藍色
        font.setColor(IndexedColors.BLUE.getIndex());

        // 寫入樣式
        style.setFont(font);

        return style;
    }

}
