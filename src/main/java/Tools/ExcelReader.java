package Tools;

import beans.HQL;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelReader {


    /**
     * 获取当前excel所有的页
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static Workbook getReadWorkBookType(String filePath) throws Exception {
        //xls-2003, xlsx-2007
        FileInputStream is = null;

        try {
            is = new FileInputStream(filePath);
            if (filePath.toLowerCase().endsWith("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (filePath.toLowerCase().endsWith("xls")) {
                return new HSSFWorkbook(is);
            } else {
                //  抛出自定义的业务异常
                throw new RuntimeException("excel格式文件错误");
            }
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * @param cell
     * @return
     */
    public static String getCellStringVal(Cell cell) {
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            default:
                return "";
        }
    }

    public static List<HQL> readExcel(String sourceFilePath) throws Exception {
        Workbook workbook = null;
        List<HQL> hqlList = new ArrayList<>();
        try {
            workbook = getReadWorkBookType(sourceFilePath);

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                HQL hql = readSheet(sheet);
                if(hql != null){
                    hqlList.add(hql);
                }
            }
            return hqlList;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }


    public static HQL readSheet(Sheet sheet) {
        String sheetName = sheet.getSheetName();
        String regex = "^数据源_0[1-9]*$";
        HQL hql = new HQL();
        boolean isMatchingSheetName = StringUtils.isMatching(sheetName, regex);
        if(isMatchingSheetName == false){
            return null;
        }
        //读取前三行，获取配置信息
        for (int rowNum = 0; rowNum <= 2; rowNum++) {
            Row row = sheet.getRow(rowNum);
            for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                Cell cell = row.getCell(cellIndex);
                String cellText = getCellStringVal(cell).trim();
                if ("模式名".equals(cellText)) {
                    hql.setSchema(getCellStringVal(row.getCell(cellIndex + 1)).trim());
                }
                if ("表名".equals(cellText)) {
                    hql.setTableName(getCellStringVal(row.getCell(cellIndex + 1)).trim());
                }
                if ("追加/覆盖".equals(cellText)) {
                    hql.setSqlAction(getCellStringVal(row.getCell(cellIndex + 1)).trim());
                }
                if ("分区键".equals(cellText)) {
                    hql.setPartitionField(getCellStringVal(row.getCell(cellIndex + 1)).trim());
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select");
        stringBuilder.append("\n");
        //拼接sql
        for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++) {


            Row row = sheet.getRow(rowNum);
            //获取第一列内容，如果是1，则直接添加cell_7的字段内容，否则，+cell_7的字段内容
            Cell cell0 = row.getCell(0);
            String cell0Text = getCellStringVal(cell0).trim();
            if ("1".equals(cell0Text)) {
                String fieldText = getCellStringVal(row.getCell(7)).trim();
                stringBuilder.append("  " + fieldText + "\n");
            } else if (StringUtils.isNumeric(cell0Text)) {
                String fieldText = getCellStringVal(row.getCell(7)).trim();
                stringBuilder.append("  " + "," + fieldText);
                if (!"关联关系".equals(sheet.getRow(rowNum + 1).getCell(0))) {
                    stringBuilder.append("\n");
                }
            } else if ("FROM".equals(cell0Text)) {
                stringBuilder.append("FROM" + " " + getCellStringVal(row.getCell(1)).trim() + " " + getCellStringVal(row.getCell(2)).trim() + "\n");
            } else if ("LEFT JOIN".equals(cell0Text) || "RIGHT JOIN".equals(cell0Text)) {
                stringBuilder.append(cell0Text + " " + getCellStringVal(row.getCell(1)).trim() + " " + getCellStringVal(row.getCell(2)).trim() + "\n");
                //添加ON 和 条件
                stringBuilder.append(getCellStringVal(row.getCell(4)) + " " + getCellStringVal(row.getCell(5)) + "\n");
            } else if ("WHERE".equals(cell0Text)) {
                stringBuilder.append("WHERE" + " " + getCellStringVal(row.getCell(1)).trim() + " " + getCellStringVal(row.getCell(2)).trim() + "\n");
            }
        }
        stringBuilder.append(";");
        String sql = stringBuilder.toString();
        hql.setSql(sql);
        return hql;
    }


}
