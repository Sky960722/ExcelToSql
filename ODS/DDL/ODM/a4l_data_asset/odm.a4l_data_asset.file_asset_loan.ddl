drop table if exists ${hivevar:ods}.o_f_10008_file_asset_loan;
CREATE TABLE ${hivevar:ods}.o_f_10008_file_asset_loan (
  id bigint COMMENT '物理主键',
  fund_loan_no varchar(64) COMMENT '资金放款编号',
  apply_no varchar(64) COMMENT '客户编号',
  loan_no varchar(64) COMMENT '放款编号',
  business_no varchar(64) COMMENT '业务流水号',
  cont_no varchar(32) COMMENT '合同编号',
  apply_date varchar(8) COMMENT '贷款申请日期yyyymmdd',
  merchant_id bigint COMMENT '渠道id',
  loan_amount decimal(17,2) COMMENT '放款金额',
  period varchar(2) COMMENT '申请期限',
  interest_rate decimal(17,9) COMMENT '年化利率',
  loan_date varchar(8) COMMENT '放款时间 yyyyMMdd',
  cont_end_date varchar(8) COMMENT '合同到期日 yyyyMMdd',
  cust_name varchar(64) COMMENT '放款账户姓名',
  bank_name varchar(64) COMMENT '放款账户开户行',
  account varchar(64) COMMENT '放款账户开户行',
  asset_package_no varchar(20) COMMENT '资产池编号',
  status varchar(2) COMMENT '是否加入对账文件',
  file_date date COMMENT '文件日期',
  created_time timestamp COMMENT '创建时间',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '放款资产端对账文件拆分数据'
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

