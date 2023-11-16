drop table if exists ${hivevar:ods}.s_f_d_10008_loan_repayment_plan;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_loan_repayment_plan (
  id bigint COMMENT '主键',
  repayment_plan_no varchar(64) COMMENT '还款计划编号',
  asset_package_no varchar(64) COMMENT '资产池编号',
  credit_apply_id bigint COMMENT '授信Id',
  loan_id bigint COMMENT '放款Id',
  period int COMMENT '期数',
  start_date date COMMENT '当期开始日期',
  due_date date COMMENT '当期到期日',
  grace_date date COMMENT '宽限期到期日',
  status varchar(5) COMMENT '当期状态 0正常 1结清 2逾期',
  compensatory varchar(5) COMMENT '是否代偿 0否1是',
  settle_date date COMMENT '当前结清日期',
  repayment_amount decimal(19,2) COMMENT '应还总额',
  repaid_amount decimal(19,2) COMMENT '已还总额',
  unpaid_amount decimal(19,2) COMMENT '未还总额',
  repayment_principal decimal(19,2) COMMENT '应还本金',
  repaid_principal decimal(19,2) COMMENT '已还本金',
  unpaid_principal decimal(19,2) COMMENT '未还本金',
  repayment_interest decimal(19,2) COMMENT '应还利息',
  repaid_interest decimal(19,2) COMMENT '已还利息',
  unpaid_interest decimal(19,2) COMMENT '未还利息',
  repayment_default_interest decimal(19,2) COMMENT '应还罚息',
  repaid_default_interest decimal(19,2) COMMENT '已还罚息',
  unpaid_default_interest decimal(19,2) COMMENT '未还罚息',
  repayment_overdue_interest decimal(19,2) COMMENT '应还复利',
  repaid_overdue_interest decimal(19,2) COMMENT '已还复利',
  unpaid_overdue_interest decimal(19,2) COMMENT '未还复利',
  repayment_fee decimal(19,2) COMMENT '应还费用',
  repaid_fee decimal(19,2) COMMENT '已还费用',
  unpaid_fee decimal(19,2) COMMENT '未还费用',
  created_time timestamp COMMENT '',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT ''
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

