drop table if exists ${hivevar:ods}.o_f_10008_apply_file;
CREATE TABLE ${hivevar:ods}.o_f_10008_apply_file (
  id BIGINT COMMENT '主键',
  file_no varchar(64) COMMENT '文件编号唯一',
  apply_no varchar(64) COMMENT '申请编号',
  asset_package_no varchar(64) COMMENT '资产池编号',
  business_no varchar(64) COMMENT '申请流水号',
  file_type varchar(20) COMMENT '文件类型',
  file_path varchar(256) COMMENT '文件路径',
  file_name varchar(128) COMMENT '文件名称',
  fund_file_id varchar(128) COMMENT '资金方文件唯一标识',
  stage varchar(2) COMMENT '阶段 1授信 2放款',
  source varchar(2) COMMENT '来源 1资产 2资金',
  channel varchar(32) COMMENT '渠道标识',
  contract_no varchar(64) COMMENT '合同编号',
  created_time timestamp COMMENT '',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '文件表'
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

