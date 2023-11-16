drop table if exists ${hivevar:ods}.o_f_10008_credit_pre_check_apply;
CREATE TABLE ${hivevar:ods}.o_f_10008_credit_pre_check_apply (
  id bigint COMMENT '',
  asset_package_no varchar(32) COMMENT '资产池编号',
  business_no varchar(64) COMMENT '业务流水号',
  user_name varchar(32) COMMENT '客户姓名',
  id_no_md5 varchar(32) COMMENT '身份证md5',
  phone_md5 varchar(32) COMMENT '电话号md5',
  fund_channel_no varchar(32) COMMENT '资金渠道编号',
  apply_status varchar(10) COMMENT '状态 01 可授信 02 本渠道已授信 03 其他渠道授信  00 不可授信',
  cause varchar(566) COMMENT '原因',
  created_time timestamp COMMENT '',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT ''
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

