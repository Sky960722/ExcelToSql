package Tools;

import Beans.ODS.ODSTB;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/*
这个类是将读取的页转换为对应的 ods 配置
 */
public class ODSReader {

    private static Logger logger = LoggerFactory.getLogger(ODSReader.class);
    /**
     * 根据索引页数据，填充ODS
     * @param workbook
     * @return
     */
    public static List<ODSTB> readTbConfg(Workbook workbook){
        logger.info("读取索引页数据，填充odstb的配置");
        logger.info(workbook.toString());
        Sheet sheet = workbook.getSheetAt(1);

        List<ODSTB> odstbs = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            ODSTB odstb = new ODSTB();
            Row row = sheet.getRow(i);
            String schemaName = ExcelReader.getCellStringVal(row.getCell(1)).trim();
            String tbName = ExcelReader.getCellStringVal(row.getCell(2)).trim();
            String loadType = ExcelReader.getCellStringVal(row.getCell(3)).trim();
            String fre = ExcelReader.getCellStringVal(row.getCell(4)).trim();
            String tbCommand = ExcelReader.getCellStringVal(row.getCell(5)).trim();
            odstb.setSchema(schemaName);
            odstb.setTbName(tbName);
            odstb.setLoadTypes(loadType);
            odstb.setFrequency(fre);
            odstb.setTbCommand(tbCommand);
            odstbs.add(odstb);
        }
        logger.info("将配置完的集合 odstb 返回");
        return odstbs;
    }

    /**
     * 传入从 配置页 读取的 odstbs，odstb表结构所在的页，进行配置
     * @param odstbs
     * @param sheet
     */
    public static void fillTbFields(List<ODSTB> odstbs,Sheet sheet){
        logger.info("将集合odstb进一步填充字段，主键等信息");
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            int regionFirstRow = region.getFirstRow();
            String cellText =
                    ExcelReader.getCellStringVal(sheet.getRow(region.getFirstRow()).getCell(region.getFirstColumn())).trim();

            //如果和配置页返回的odstb匹配上，就把字段填入odstb中
            for (ODSTB odstb : odstbs) {
                if(cellText.equals(odstb.getTbName())){
                    List<String> fields = new ArrayList<>();
                    List<String> fieldsCommand = new ArrayList<>();
                    List<String> fieldsType = new ArrayList<>();
                    List<String> primaryKey = new ArrayList<>();
                    for (int j = regionFirstRow + 2 ; j <= sheet.getLastRowNum(); j++) {
                        //空行跳过，说明也到分界线了
                        if(sheet.getRow(j) == null || sheet.getRow(j).getCell(0) == null ){
                            break;
                        }
                        //通过序号判断 如果不是 数字 就退出
                        if(!StringUtils.isNumeric(ExcelReader.getCellStringVal(sheet.getRow(j).getCell(0)))){
                            break;
                        }
                        //判断当前主键是否是Y 是Y 就加入 primaryKey
                        if("Y".equals(ExcelReader.getCellStringVal(sheet.getRow(j).getCell(5)))){
                            primaryKey.add(ExcelReader.getCellStringVal(sheet.getRow(j).getCell(1)));
                        }
                        fields.add(ExcelReader.getCellStringVal(sheet.getRow(j).getCell(1)));
                        fieldsType.add(ExcelReader.getCellStringVal(sheet.getRow(j).getCell(2)));
                        fieldsCommand.add(ExcelReader.getCellStringVal(sheet.getRow(j).getCell(3)));
                    }
                    odstb.setFields(fields);
                    odstb.setFieldsType(fieldsType);
                    odstb.setFieldsCommand(fieldsCommand);
                    odstb.setPrimaryKey(primaryKey);
                }
            }
        }
        logger.info("所有的配置信息均填充完毕");
    }
}
