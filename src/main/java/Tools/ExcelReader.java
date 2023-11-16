package Tools;

import Beans.DW.HQL;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
     * 返回单元格内容
     * @param cell
     * @return
     */
    public static String getCellStringVal(Cell cell) {
        if(cell == null ){
            return "";
        }
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

    public static boolean hasBlackBorder(CellStyle cellStyle) {
        BorderStyle borderStyle = cellStyle.getBorderTop();
        short borderColor = cellStyle.getTopBorderColor();

        return borderStyle == BorderStyle.THIN && borderColor == IndexedColors.BLACK.getIndex();
    }









}
