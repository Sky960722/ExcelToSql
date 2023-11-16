drop table if exists ${hivevar:ods}.o_f_10008_user_repayment_plan;
CREATE TABLE ${hivevar:ods}.o_f_10008_user_repayment_plan (
  id bigint COMMENT '主键',
  user_repayment_plan_no varchar(64) COMMENT '还款计划编号',
  asset_package_no varchar(64) COMMENT '资产池编号',
  credit_apply_id bigint COMMENT '授信id',
  loan_id bigint COMMENT '放款id',
  period varchar(5) COMMENT '期数',
  start_date date COMMENT '当期开始时间',
  due_date date COMMENT '当期到期日',
  grace_date date COMMENT '宽限期到期日',
  status varchar(5) COMMENT '当期状态 0正常 1结清 2逾期',
  settle_date date COMMENT '客户真实还款日期',
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
) COMMENT '还款计划表'
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

