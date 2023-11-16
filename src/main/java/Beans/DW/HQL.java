package Beans.DW;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HQL {
    private static Logger logger = LoggerFactory.getLogger(HQL.class);

    private String schema;
    private String tableName;
    private String sqlAction;
    private String partitionField;
    private String ifDropPartition;
    private String dropPartitionFields;
    private String firstScript = "";

    public String getFirstScript() {
        return firstScript;
    }

    public void setFirstScript(String firstScript) {
        this.firstScript = firstScript;
    }

    public String getDropPartitionFields() {
        return dropPartitionFields;
    }

    public void setDropPartitionFields(String dropPartitionFields) {
        this.dropPartitionFields = dropPartitionFields;
    }

    private String sql;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSqlAction() {
        return sqlAction;
    }

    public void setSqlAction(String sqlAction) {
        this.sqlAction = sqlAction;
    }

    public String getPartitionField() {
        return partitionField;
    }

    public void setPartitionField(String partitionField) {
        this.partitionField = partitionField;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String toHQL() {
        logger.info("开始生成hql");
        StringBuilder stringBuilder = new StringBuilder();
        //添加动态分区不严格设置
        if (this.firstScript != "") {
            stringBuilder.append(firstScript + "\n");
        }
        //根据条件判断是否要加动态分区
        if ("y".equals(this.ifDropPartition)) {
            stringBuilder.append("alter table " + this.schema + "." + this.tableName + " drop if exists partition(" + this.dropPartitionFields + ");\n");
        }
        //判断当前是插入还是追加
        if ("overwrite".equals(sqlAction)) {
            stringBuilder.append("insert overwrite table ");
            stringBuilder.append(schema + "." + tableName + " ");
        } else if ("into".equals(sqlAction)) {
            stringBuilder.append("insert into table ");
            stringBuilder.append(schema + "." + tableName + " ");
        } else {
            logger.error("只有into或者overwrite两种模式，请检查对应的列");
            throw new RuntimeException("只有into或者overwrite两种模式，请检查对应的列");
        }

        if (partitionField.contains("#")) {
            stringBuilder.append("partition" + "(" + partitionField.replace("#", ",") + ")");
        } else {
            stringBuilder.append("partition" + "(" + partitionField + ")");
        }

        stringBuilder.append("\n");
        stringBuilder.append(sql);
        stringBuilder.append("\n" + "\n" + "\n");
        return stringBuilder.toString();
    }

    public String getIfDropPartition() {
        return ifDropPartition;
    }

    public void setIfDropPartition(String ifDropPartition) {
        this.ifDropPartition = ifDropPartition;
    }
}
