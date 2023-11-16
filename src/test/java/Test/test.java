package Test;

import Beans.DW.DWTB;
import Beans.ODS.ODSTB;
import Tools.DWDDLReader;
import Tools.DWHQLReader;
import Tools.ExcelReader;
import Beans.DW.HQL;
import Tools.ODSReader;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static Tools.DWHQLReader.excelToHalList;
import static Tools.DWHQLReader.sheetToHql;

public class test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);

    @Test
    public void sheetToDWMetadata() throws Exception{
        String currentDirectory = System.getProperty("user.dir");
        System.setProperty("logDirectory", currentDirectory);

        logger.info("开始读取");
        String path = "D:\\共享文件夹\\dwd层模型.xlsx";
        Workbook workbook = ExcelReader.getReadWorkBookType(path);
        List<DWTB> dwtbs = DWDDLReader.readTbConfg(workbook);
        for (DWTB dwtb : dwtbs) {
            System.out.println(dwtb);
        }
        Sheet sheet = workbook.getSheetAt(2);
        DWDDLReader.fillTbFields(dwtbs,sheet);
        for (DWTB dwtb : dwtbs) {
            System.out.println(dwtb);
            System.out.println(dwtb.generateDDL());
        }



    }


    @Test
    public void  excelToHql()throws Exception{
        String currentDirectory = System.getProperty("user.dir");
        System.setProperty("logDirectory", currentDirectory);

        logger.info("开始读取");
        String path = "C:\\Users\\14481\\Desktop\\test\\dwd层-dwd_a4l_d_f_fund_apply-授信申请信息表.xlsx";
        Workbook workbook = ExcelReader.getReadWorkBookType(path);
        Sheet sheet = workbook.getSheetAt(2);
        HQL hql = sheetToHql(sheet);
        String toHQL = hql.toHQL();
        System.out.println(toHQL);

    }

    @Test
    public void excelToSQL() throws Exception {

        String currentDirectory = System.getProperty("user.dir");
        System.setProperty("tool.root", currentDirectory);

        logger.debug("test");
        String path = "D:\\共享文件夹\\dwd层-dwd_a4l_d_f_rd_data_apply-渠道申请信息.xlsx";
        List<HQL> hqlList = excelToHalList(path);
        for (HQL hql : hqlList) {
            String sql = hql.toHQL();
            System.out.println(sql);
        }
    }


    @Test
    public void readTBname() throws Exception {
        String path = "C:\\Users\\14481\\Desktop\\djt_quant-ODS层模型.xlsx";


        Workbook workbook = ExcelReader.getReadWorkBookType(path);
        //获取当前第一页中所有表的跑批配置和频率
        List<ODSTB> odstbs = ODSReader.readTbConfg(workbook);
        //根据List里面的ODSTB 循环遍历 2页，获取所有表的字段，主键
        //分区键默认用的是etl_date
        Sheet sheet = workbook.getSheetAt(2);
        ODSReader.fillTbFields(odstbs,sheet);


        for (ODSTB odstb : odstbs) {
            System.out.println(odstb);
            System.out.println(odstb.generateODMddl());
            System.out.println(odstb.generateSDMddl());
            System.out.println(odstb.generateSQL());
        }

    }
}
