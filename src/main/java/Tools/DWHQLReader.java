package Tools;

import Beans.DW.HQL;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static Tools.ExcelReader.getCellStringVal;
import static Tools.ExcelReader.getReadWorkBookType;

public class DWHQLReader {

    private static Logger logger = LoggerFactory.getLogger(DWHQLReader.class);

    public static HQL sheetToHql(Sheet sheet) {
        String sheetName = sheet.getSheetName();
        String regex = "^数据源_[0-9][0-9]*$";
        HQL hql = new HQL();
        logger.info(sheetName+"检查当前sheet页是否满足正则：^数据源_0[1-9]*$  要求");
        boolean isMatchingSheetName = StringUtils.isMatching(sheetName, regex);
        if (isMatchingSheetName == false) {
            logger.error("当前数据页不符合格式，请检查-----"+sheetName);
            return null;
        }
        logger.info("当前sheet页满足正则表达式校验");
        logger.info("读取前三行，获取dw层的HQL配置信息");
        //读取前三行，获取配置信息
        for (int rowNum = 0; rowNum <= 2; rowNum++) {
            Row row = sheet.getRow(rowNum);
            for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                Cell cell = row.getCell(cellIndex);
                String cellText = getCellStringVal(cell).trim();
//                logger.info("cellText:"+cellText);
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
                if("是否删除分区".equals(cellText)){
                    hql.setIfDropPartition(getCellStringVal(row.getCell(cellIndex + 1)).trim());
                }
                if("删除分区字段逻辑".equals(cellText)){
                    hql.setDropPartitionFields(getCellStringVal(row.getCell(cellIndex + 1)).trim());
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select");
        stringBuilder.append("\n");

        logger.info("获取select部分所有的字段和表名");
        //拼接sql
        for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++) {


            Row row = sheet.getRow(rowNum);
            //获取第一列内容，如果是1，则直接添加cell_7的字段内容，否则，+cell_7的字段内容
            Cell cell0 = row.getCell(0);
            String cell0Text = getCellStringVal(cell0).trim();
            if("前置脚本".equals(cell0Text) && ExcelReader.getCellStringVal(row.getCell(1)).trim() != ""){
                hql.setFirstScript(ExcelReader.getCellStringVal(row.getCell(1)).trim());
            }
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
            } else if ("LEFT JOIN".equals(cell0Text) || "RIGHT JOIN".equals(cell0Text) || "INNER JOIN".equals(cell0Text)) {
                stringBuilder.append(cell0Text + " " + getCellStringVal(row.getCell(1)).trim() + " " + getCellStringVal(row.getCell(2)).trim() + "\n");
                //添加ON 和 条件
                stringBuilder.append(getCellStringVal(row.getCell(4)) + " " + getCellStringVal(row.getCell(5)) + "\n");
            } else if ("WHERE".equals(cell0Text)) {
                stringBuilder.append("WHERE" + " " + getCellStringVal(row.getCell(1)).trim() + " " + getCellStringVal(row.getCell(2)).trim() + "\n");
            }
        }
        // 检查是否存在换行符
        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '\n') {
            // 删除最后一个换行符
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append(";");
        String sql = stringBuilder.toString();
        hql.setSql(sql);
        logger.info("hql配置完成,返回配置好的hql");
        return hql;
    }


    public static List<HQL> excelToHalList(String sourceFilePath) throws Exception {
        Workbook workbook = null;
        List<HQL> hqlList = new ArrayList<>();
        try {
            workbook = getReadWorkBookType(sourceFilePath);

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                HQL hql = sheetToHql(sheet);
                if (hql != null) {
                    hqlList.add(hql);
                }
            }
            return hqlList;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }
}
