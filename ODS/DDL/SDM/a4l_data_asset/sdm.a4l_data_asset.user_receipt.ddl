drop table if exists ${hivevar:ods}.s_f_d_10008_user_receipt;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_user_receipt (
  id bigint COMMENT '主键',
  user_receipt_no varchar(32) COMMENT '借据编号',
  asset_package_no varchar(64) COMMENT '资产池编号',
  receipt_id bigint COMMENT '资金借据id',
  credit_apply_id bigint COMMENT '授信id',
  loan_id bigint COMMENT '放款id',
  loan_rate varchar(64) COMMENT '利率',
  period varchar(5) COMMENT '期数',
  repay_date date COMMENT '客户还款日期',
  over_due_days varchar(5) COMMENT '逾期天数',
  receipt_status varchar(5) COMMENT '借据状态 RP还款中 OD逾期 PF结清',
  pay_off_date date COMMENT '客户结清日期',
  repayment_amount decimal(19,2) COMMENT '应还总额',
  repaid_amount decimal(19,2) COMMENT '已还总额',
  unpaid_amount decimal(19,2) COMMENT '未还总额',
  repayment_principal decimal(19,2) COMMENT '应还总额',
  repaid_principal decimal(19,2) COMMENT '已还总额',
  unpaid_principal decimal(19,2) COMMENT '未还总额',
  repayment_interest decimal(19,2) COMMENT '应还总额',
  repaid_interest decimal(19,2) COMMENT '已还总额',
  unpaid_interest decimal(19,2) COMMENT '未还总额',
  repayment_default_interest decimal(19,2) COMMENT '应还总额',
  repaid_default_interest decimal(19,2) COMMENT '已还总额',
  unpaid_default_interest decimal(19,2) COMMENT '未还总额',
  repayment_overdue_interest decimal(19,2) COMMENT '应还总额',
  repaid_overdue_interest decimal(19,2) COMMENT '已还总额',
  unpaid_overdue_interest decimal(19,2) COMMENT '未还总额',
  repayment_fee decimal(19,2) COMMENT '应还总额',
  repaid_fee decimal(19,2) COMMENT '已还总额',
  unpaid_fee decimal(19,2) COMMENT '未还总额',
  created_time timestamp COMMENT '',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '底层资产借据信息表'
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

