drop table if exists ${hivevar:ods}.s_f_d_10008_asset_pay_channel_account;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_asset_pay_channel_account (
  id BIGINT COMMENT '',
  merchant_id varchar(64) COMMENT '商户id',
  merchant_name varchar(64) COMMENT '商户名',
  asset_package_no varchar(64) COMMENT '资产池编号',
  pay_channel_account varchar(64) COMMENT '支付渠道账户号',
  pay_channel_share_msg varchar(20) COMMENT '支付渠道分账信息',
  created_time varchar(256) COMMENT '',
  updated_time varchar(128) COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT ''
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

