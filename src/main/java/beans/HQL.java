package beans;

public class HQL {
    private String schema;
    private String tableName;
    private String sqlAction;
    private String partitionField;
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
        StringBuilder stringBuilder = new StringBuilder();
        if ("overwrite".equals(sqlAction)) {
            stringBuilder.append("INSERT OVERWRITE TABLE ");
            stringBuilder.append(schema + tableName + " ");
        }

        if (partitionField.contains("#")) {
            stringBuilder.append("partition" + "(" + partitionField.replace("#", ",")+")");
        } else {
            stringBuilder.append("partition" + "(" + partitionField+")");
        }

        stringBuilder.append("\n");
        stringBuilder.append(sql);
        stringBuilder.append("\n" + "\n" + "\n");
        return stringBuilder.toString();
    }
}
