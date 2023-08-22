package Test;

import Tools.ExcelReader;
import TransSQL.readExcel;
import beans.HQL;
import org.junit.jupiter.api.Test;

import java.util.List;

public class test {

    @Test
    public void excelToSQL() throws Exception {
        String path = "C:\\Users\\14481\\Desktop\\test\\dwd_a4l_fund_apply_df.xlsx";
        List<HQL> hqlList = ExcelReader.readExcel(path);
        for (HQL hql : hqlList) {
            String sql = hql.toHQL();
            System.out.println(sql);
        }
    }
}
