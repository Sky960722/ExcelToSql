drop table if exists ${hivevar:ods}.s_f_d_10008_loan_apply;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_loan_apply (
  id bigint COMMENT '主键',
  loan_no varchar(64) COMMENT '放款编号',
  business_no varchar(64) COMMENT '业务流水号',
  asset_package_no varchar(64) COMMENT '资产池编号',
  fund_loan_no varchar(64) COMMENT '资金放款编号',
  credit_apply_id bigint COMMENT '授信id',
  credit_apply_no varchar(64) COMMENT '授信唯一标识',
  merchant_id varchar(64) COMMENT '商户Id',
  loan_amount decimal(19,2) COMMENT '申请金额',
  period varchar(2) COMMENT '期数',
  rate decimal(19,6) COMMENT '利率',
  loan_date date COMMENT '放款日期',
  loan_time timestamp COMMENT '放款时间',
  first_repay_date date COMMENT '首期还款日',
  last_repay_date date COMMENT '最后还款日',
  repay_date varchar(20) COMMENT '还款日',
  contract_no varchar(64) COMMENT '合同编号',
  fund_channel_no varchar(64) COMMENT '资金渠道标识',
  apply_status varchar(32) COMMENT '放款申请状态 S成功 F失败 P处理中 R拒绝',
  cause String COMMENT '失败原因',
  device_id varchar(30) COMMENT '设备号',
  terminal varchar(2) COMMENT '终端类型',
  ip_address varchar(30) COMMENT 'ip',
  gps_address varchar(30) COMMENT 'gps',
  created_time timestamp COMMENT '',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '用信申请表'
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

