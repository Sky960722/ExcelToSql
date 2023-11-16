package Beans.DW;

import java.util.ArrayList;
import java.util.List;

public class DWTB {

    String schema;
    String tbName;
    String tbCommand;
    List<String> fields;
    List<String> fieldsType;
    List<String> fieldsCommand;
    List<String> primaryKey;
    List<String> partitonFields;

    @Override
    public String toString() {
        return "DWTB{" +
                "schema='" + schema + '\'' +
                ", tbName='" + tbName + '\'' +
                ", tbCommand='" + tbCommand + '\'' +
                ", fields=" + fields +
                ", fieldsType=" + fieldsType +
                ", fieldsCommand=" + fieldsCommand +
                ", primaryKey=" + primaryKey +
                ", partitonFields=" + partitonFields +
                '}';
    }

    public List<String> getPartitonFields() {
        return partitonFields;
    }

    public void setPartitonFields(List<String> partitonFields) {
        this.partitonFields = partitonFields;
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

    public String getTbCommand() {
        return tbCommand;
    }

    public void setTbCommand(String tbCommand) {
        this.tbCommand = tbCommand;
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


    public String generateDDL() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("drop table if exists " + "${hivevar:dw}"  + "." + this.tbName + ";");
        stringBuilder.append("\n");
        stringBuilder.append("CREATE TABLE " + "${hivevar:dw}" + "." + this.tbName + " (");
        stringBuilder.append("\n");

        boolean needComma = true; // 初始化为 false，表示不需要添加逗号

        for (int i = 0; i < this.fields.size(); i++) {
            //最后一个字段需要判断是否加 ，
            if (this.fields.size() - 2 - this.partitonFields.size() == i && this.primaryKey.size() == 0) {
                needComma = false;
            }

            if (!this.partitonFields.contains(this.fields.get(i))) {
                stringBuilder.append("  " + this.fields.get(i) + " ");
                stringBuilder.append(this.fieldsType.get(i) + " ");
                stringBuilder.append("COMMENT ");
                stringBuilder.append("'" + this.fieldsCommand.get(i) + "'");
                if (needComma) {
                    stringBuilder.append(",");
                }
                stringBuilder.append("\n");

            }


        }
        if (this.primaryKey.size() > 0) {
            stringBuilder.append("  PRIMARY KEY (" + String.join(",", this.primaryKey) + ") DISABLE NOVALIDATE");
            stringBuilder.append("\n");
        }
        stringBuilder.append(") COMMENT '" + this.tbCommand + "'");
        stringBuilder.append("\n");
        //判断当前fields 是否为空和长度不为0，有分区字段，则开始拼接
        if (partitonFields != null && partitonFields.size() > 0) {
            List<String> res = new ArrayList<>();
            //对分区字段做拼接
            for (String partitionField : this.partitonFields) {
                int index = fields.indexOf(partitionField);
                if (index >= 0 && index < fieldsType.size() && index < fieldsCommand.size()) {
                    String fieldType = fieldsType.get(index);
                    String fieldCommand = fieldsCommand.get(index);
                    String concatenatedField = partitionField + " " + fieldType + " COMMENT '" + fieldCommand + "'";
                    res.add(concatenatedField);
                }
            }
            String finalRes = String.join(",", res);

            stringBuilder.append("PARTITIONED BY (" + finalRes + ")\n");
        }
        stringBuilder.append("STORED AS PARQUET\n" +
                "TBLPROPERTIES(\"parquet.compression\"=\"snappy\");");
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
