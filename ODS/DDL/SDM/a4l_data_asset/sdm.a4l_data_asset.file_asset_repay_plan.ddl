drop table if exists ${hivevar:ods}.s_f_d_10008_file_asset_repay_plan;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_file_asset_repay_plan (
  id bigint COMMENT '物理主键',
  fund_loan_no varchar(64) COMMENT '资金方放款编号',
  repayment_plan_no varchar(64) COMMENT '还款计划编号',
  merchant_id bigint COMMENT '渠道id',
  loan_no varchar(64) COMMENT '放款编号',
  total_period varchar(2) COMMENT '贷款总期数',
  current_period varchar(2) COMMENT '当前期数',
  repay_date date COMMENT '应还日期',
  repay_amount decimal(17,2) COMMENT '应还金额',
  repaid_amount decimal(17,2) COMMENT '实还金额',
  repay_principal decimal(17,2) COMMENT '应还本金',
  repaid_principal decimal(17,2) COMMENT '实还本金',
  residue_principal decimal(17,2) COMMENT '剩余本金',
  repay_interest decimal(17,2) COMMENT '应还利息',
  repaid_interest decimal(17,2) COMMENT '实还利息',
  repay_principal_overdue_fee decimal(17,2) COMMENT '应还本金罚息',
  repaid_principal_overdue_fee decimal(17,2) COMMENT '实还本金罚息',
  repaid_interest_overdue_fee decimal(17,2) COMMENT '实还利息罚息',
  repay_interest_overdue_fee decimal(17,2) COMMENT '应还利息罚息',
  repay_fee decimal(17,2) COMMENT '应还费用',
  repaid_fee decimal(17,2) COMMENT '实还费用',
  repay_guarantee_fee decimal(17,2) COMMENT '应还担保费',
  repaid_guarantee_fee decimal(17,2) COMMENT '实还担保费',
  repay_plan_status varchar(2) COMMENT '还款计划状态',
  settle_date date COMMENT '结清日期',
  new_identifying varchar(2) COMMENT 'A0-放款后首次同步 B0-代偿后若对客真实还款计划有变更需再次同步',
  asset_package_no varchar(20) COMMENT '资产池编号',
  status varchar(2) COMMENT '是否加入对账文件',
  file_date date COMMENT '文件日期',
  created_time timestamp COMMENT '创建时间',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '还款计划资产端对账文件拆分数据'
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

