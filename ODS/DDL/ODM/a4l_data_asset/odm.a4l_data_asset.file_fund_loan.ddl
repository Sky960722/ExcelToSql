drop table if exists ${hivevar:ods}.o_f_10008_file_fund_loan;
CREATE TABLE ${hivevar:ods}.o_f_10008_file_fund_loan (
  id bigint COMMENT '物理主键',
  fund_loan_no varchar(64) COMMENT '资金方放款编号',
  loan_no varchar(64) COMMENT '放款编号',
  business_no varchar(64) COMMENT '业务流水号',
  loan_date date COMMENT '放款日期',
  merchant_id bigint COMMENT '渠道id',
  period int COMMENT '放款期数',
  loan_amount decimal(17,2) COMMENT '放款金额',
  loan_status varchar(2) COMMENT '放款状态',
  asset_package_no varchar(20) COMMENT '资产池编号',
  fund_channel_no varchar(64) COMMENT '资金渠道标识',
  status varchar(2) COMMENT '是否加入对账文件',
  file_date date COMMENT '文件日期',
  created_time timestamp COMMENT '创建时间',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '放款资金对账文件拆分数据'
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

