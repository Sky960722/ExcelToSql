package Beans.ODS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ODSTB {

    Map<String, String> schemaNameToNums = new HashMap<String, String>() {{
        put("runlou_risk_decision_engine","10009");
        put("a4l_data_asset", "10008");
        put("runlou_saas_quant", "10007");
        put("runlou_data_risk", "10006");
        put("djt_quant", "10005");
        put("biz_loan_diversion_cjk", "10004");
        put("biz_loan_diversion", "10003");
        put("biz_loan_assistance_zb", "10002");
        put("biz_loan_assistance_cy", "10001");
        put("biz_loan_assistance", "10000");
    }};

    String schema;
    String tbName;
    String tbCommand;
    List<String> fields;
    List<String> fieldsType;
    List<String> fieldsCommand;
    List<String> primaryKey;
    String loadTypes;

    @Override
    public String toString() {
        return "ODSTB{" +
                "schemaNameToNums=" + schemaNameToNums +
                ", schema='" + schema + '\'' +
                ", tbName='" + tbName + '\'' +
                ", tbCommand='" + tbCommand + '\'' +
                ", fields=" + fields +
                ", fieldsType=" + fieldsType +
                ", fieldsCommand=" + fieldsCommand +
                ", primaryKey=" + primaryKey +
                ", loadTypes='" + loadTypes + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }

    String frequency;

    public String getTbCommand() {
        return tbCommand;
    }

    public void setTbCommand(String tbCommand) {
        this.tbCommand = tbCommand;
    }


    public String getLoadTypes() {
        return loadTypes;
    }

    public void setLoadTypes(String loadTypes) {
        this.loadTypes = loadTypes;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Map<String, String> getSchemaNameToNums() {
        return schemaNameToNums;
    }


    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }


    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getFieldsType() {
        return fieldsType;
    }

    public void setFieldsType(List<String> fieldsType) {
        this.fieldsType = fieldsType;
    }

    public List<String> getFieldsCommand() {
        return fieldsCommand;
    }

    public void setFieldsCommand(List<String> fieldsCommand) {
        this.fieldsCommand = fieldsCommand;
    }

    public List<String> getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(List<String> primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String generateODMddl() {
        StringBuilder stringBuilder = new StringBuilder();
        String sysNum = this.getSchemaNameToNums().get(this.getSchema());
        String odmTbName = "${hivevar:ods}.o_" + this.getLoadTypes().toLowerCase() + "_" + sysNum + "_" + this.getTbName();
        stringBuilder.append("drop table if exists " + odmTbName + ";");
        stringBuilder.append("\n");
        stringBuilder.append("CREATE TABLE " + odmTbName + " (");
        stringBuilder.append("\n");

        boolean needComma = true; // 初始化为 false，表示不需要添加逗号

        for (int i = 0; i < this.fields.size(); i++) {

            stringBuilder.append("  " + this.fields.get(i) + " ");
            stringBuilder.append(this.fieldsType.get(i) + " ");
            stringBuilder.append("COMMENT ");
            stringBuilder.append("'" + this.fieldsCommand.get(i) + "'");

            if (needComma) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");
            //最后一个字段需要判断是否加 ，
            if (this.fields.size() - 2 == i && this.primaryKey.size() == 0) {
                needComma = false;
            }
        }
        if (this.primaryKey.size() > 0) {
            stringBuilder.append("  PRIMARY KEY (" + String.join(",", this.primaryKey) + ") DISABLE NOVALIDATE");
            stringBuilder.append("\n");
        }
        stringBuilder.append(") COMMENT '" + this.tbCommand + "'");
        stringBuilder.append("\n");
        stringBuilder.append("STORED AS PARQUET\n" +
                "TBLPROPERTIES(\"parquet.compression\"=\"snappy\");");
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    public String generateSDMddl() {
        StringBuilder stringBuilder = new StringBuilder();
        String sysNum = this.getSchemaNameToNums().get(this.getSchema());
        String sdmTbName = "${hivevar:ods}.s_" + this.getLoadTypes().toLowerCase() + "_" +this.getFrequency().toLowerCase()+"_"+ sysNum + "_" + this.getTbName();
        stringBuilder.append("drop table if exists " + sdmTbName + ";");
        stringBuilder.append("\n");
        stringBuilder.append("CREATE TABLE " + sdmTbName + " (");
        stringBuilder.append("\n");

        boolean needComma = true; // 初始化为 false，表示不需要添加逗号

        for (int i = 0; i < this.fields.size(); i++) {

            stringBuilder.append("  " + this.fields.get(i) + " ");
            stringBuilder.append(this.fieldsType.get(i) + " ");
            stringBuilder.append("COMMENT ");
            stringBuilder.append("'" + this.fieldsCommand.get(i) + "'");

            if (needComma) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");
            //最后一个字段需要判断是否加 ，
            if (this.fields.size() - 2 == i && this.primaryKey.size() == 0) {
                needComma = false;
            }
        }
        if (this.primaryKey.size() > 0) {
            stringBuilder.append("  PRIMARY KEY (" + String.join(",", this.primaryKey) + ") DISABLE NOVALIDATE");
            stringBuilder.append("\n");
        }
        stringBuilder.append(") COMMENT '" + this.tbCommand + "'");
        stringBuilder.append("\n");
        stringBuilder.append("PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')\n" +
                "STORED AS PARQUET\n" +
                "TBLPROPERTIES(\"parquet.compression\"=\"snappy\");");
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    public String generateSQL(){
        String sysNum = this.getSchemaNameToNums().get(this.getSchema());
        String odmTbName = "${ods}.o_" + this.getLoadTypes().toLowerCase() + "_" + sysNum + "_" + this.getTbName();
        String sdmTbName = "${ods}.s_" + this.getLoadTypes().toLowerCase() + "_" +this.getFrequency().toLowerCase()+"_"+ sysNum + "_" + this.getTbName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SET hive.exec.dynamic.partition.mode=nonstrict;\n");
        stringBuilder.append("INSERT OVERWRITE TABLE "+sdmTbName+" PARTITION(etl_date)\n" +
                "SELECT\n" +
                "  *\n" +
                "  ,'${load_date}'\n" +
                "FROM "+odmTbName+";");
        return stringBuilder.toString();
    }
}
