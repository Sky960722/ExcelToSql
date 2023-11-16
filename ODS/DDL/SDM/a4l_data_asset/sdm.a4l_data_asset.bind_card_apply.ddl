drop table if exists ${hivevar:ods}.s_f_d_10008_bind_card_apply;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_bind_card_apply (
  id BIGINT COMMENT '主键',
  bind_no varchar(64) COMMENT '绑卡签约编号',
  business_no varchar(64) COMMENT '业务流水号',
  asset_package_no varchar(64) COMMENT '资产池编号',
  credit_apply_id bigint COMMENT '授信id',
  credit_apply_no varchar(64) COMMENT '授信唯一标识',
  user_no varchar(32) COMMENT '资产用户编号',
  bind_type varchar(2) COMMENT '签约方式 1支付渠道签约 2共享协议码签约',
  cnt_user_id varchar(64) COMMENT '协议共享用户身份证号',
  cnt_no varchar(64) COMMENT '协议共享码',
  loan_business_no varchar(64) COMMENT '放款流水号',
  cert_type varchar(2) COMMENT '开户人证件类型 1身份证',
  id_no varchar(32) COMMENT '证件号码',
  phone varchar(64) COMMENT '预留手机号',
  account varchar(64) COMMENT '账号',
  account_name varchar(64) COMMENT '账号开户名',
  bank_no varchar(64) COMMENT '银行行号',
  priority varchar(2) COMMENT '优先级',
  apply_status varchar(64) COMMENT 'S成功 F失败',
  apply_cause String COMMENT '失败原因',
  valid_status varchar(64) COMMENT 'S成功 F失败',
  valid_cause String COMMENT '失败原因',
  fund_channel_no varchar(64) COMMENT '资金渠道标识',
  agreement_no varchar(64) COMMENT '签约协议号',
  device_id varchar(30) COMMENT '设备号',
  terminal varchar(2) COMMENT '终端类型',
  ip_address varchar(30) COMMENT 'ip',
  gps_address varchar(30) COMMENT 'gps',
  created_time timestamp COMMENT '',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '绑卡申请表'
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

