package kr.co.hulan.aas.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * Usage
 *
ExportExcelHelper helper = new ExportExcelHelper("TITLE", new String[]{ "columnHeader1", "columnHeader2", ... });
try{

   helper.initialize();

   List<ExcelDataRow[]> dataList = new ArrayList<ExcelDataRow[]>();
   dataList.add(new String[]{ "data1", "data2", ...});
   ...
   helper.addReport(dataList);

   helper.flushData(response);
}
finally{
   helper.close();
}

 */
public class ExportExcelHelper {

    private Logger logger = LoggerFactory.getLogger(ExportExcelHelper.class);

    private static int DEFAULT_FETCH_SIZE = 100000;

    private String title;
    private String[] headers;
    private int fetchSize = DEFAULT_FETCH_SIZE;
    private SXSSFWorkbook workbook;
    private Sheet worksheet;
    private CellStyle bodyCellStyle;
    private int rowOffsetIndex = 0;

    public ExportExcelHelper(String title, String[] headers){
        this.title = title;
        this.headers = headers;


    }


    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public void initialize(){
        // this.workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE /* 100 */);
        this.workbook = new SXSSFWorkbook(10000);
        workbook.setCompressTempFiles(true);
        this.worksheet = workbook.createSheet(title);
        this.initStyle();
        initializeColumnHeaders();
    }

    private void initStyle(){
        bodyCellStyle = worksheet.getWorkbook().createCellStyle();

        bodyCellStyle.setAlignment(HorizontalAlignment.LEFT);
        bodyCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyCellStyle.setBorderTop(BorderStyle.THIN);
        bodyCellStyle.setBorderRight(BorderStyle.THIN);
        bodyCellStyle.setBorderLeft(BorderStyle.THIN);
        bodyCellStyle.setBorderBottom(BorderStyle.THIN);

        bodyCellStyle.setWrapText(true);
    }

    public void addReport(List<Object[]> dataList) {
        // Create body
        for (Object[] reportRow : dataList) {
            addReport(reportRow);
        }//for
    }

    public void addReport(Object[] reportRow) {
        Row row = worksheet.createRow(rowOffsetIndex++);

        // Create a new Cell
        for (int colIndex=0; colIndex < Math.min(headers.length, reportRow.length) ; colIndex++) {

            // Retrieve the id value
            //Cell cell1 = row.createCell(startColIndex+colIndex);
            SXSSFCell cell1 = (SXSSFCell)row.createCell(colIndex);

            // 데이터가 없거나 컬럼 크기가 맞지 않을 경우
            if (colIndex >= reportRow.length || reportRow[colIndex] == null) {

                cell1.setCellType(CellType.STRING);
                cell1.setCellValue("");
                cell1.setCellStyle(bodyCellStyle);
            }
            else if (reportRow[colIndex] instanceof Integer || reportRow[colIndex] instanceof Double
                    || reportRow[colIndex] instanceof Long  || reportRow[colIndex] instanceof Float){

                Double d = Double.parseDouble("" + reportRow[colIndex]);

                cell1.setCellType(CellType.NUMERIC);
                cell1.setCellValue(d);
                cell1.setCellStyle(bodyCellStyle);
            } else {
                cell1.setCellType(CellType.STRING);
                cell1.setCellValue(reportRow[colIndex].toString());
                cell1.setCellStyle(bodyCellStyle);
            }
        }//for
    }


    public void flushData(HttpServletResponse response){

        // 6. Set the response properties
        String fileName = StringUtils.defaultString(title, "Report");

        try {
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
        }

        // JQuery Filedownload popup close
        response.setHeader("Set-Cookie" , "fileDownload=true; path=/");
        // Make sure to set the correct content type
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 7. Write to the output stream
        write(response);
    }


    public void close(){
        try{
            if( workbook != null ){
                workbook.dispose();
            }
        }
        catch(Exception e){
            logger.error("close error", e);
        }
    }



    private void initializeColumnHeaders() {
        // Create font style for the headers
        Font font = worksheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontName("맑은 고딕");

        // Create cell style for the headers
        CellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFont(font);

        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);

        // Create the column headers
        Row rowHeader = worksheet.createRow((short) rowOffsetIndex++);
        rowHeader.setHeight((short) 500);

        if (headers !=null && headers.length>0){
            for(int i=0; i < headers.length; i++){
                worksheet.setColumnWidth(i, 3000);
                Cell cell = rowHeader.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }
        }
    }


    public void write(HttpServletResponse response) {
        try {
            // Retrieve the output stream
            ServletOutputStream outputStream = response.getOutputStream();
            // Write to the output stream
            workbook.write(outputStream);
            // Flush the stream
            outputStream.flush();

        } catch (Exception e) {
            logger.error("Unable to write report to the output stream");
        }
    }
}
