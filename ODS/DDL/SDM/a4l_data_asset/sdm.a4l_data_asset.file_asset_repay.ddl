drop table if exists ${hivevar:ods}.s_f_d_10008_file_asset_repay;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_file_asset_repay (
  id bigint COMMENT '物理主键',
  fund_loan_no varchar(64) COMMENT '资金方放款编号',
  merchant_id bigint COMMENT '渠道id',
  loan_no varchar(64) COMMENT '放款编号',
  repay_no varchar(64) COMMENT '还款流水号',
  total_period varchar(2) COMMENT '贷款成功的总期数',
  current_period varchar(2) COMMENT '当前还款期数',
  loan_principal decimal(17,2) COMMENT '贷款本金',
  repay_date date COMMENT '应还日期',
  pre_fee decimal(17,2) COMMENT '前置费用',
  repaid_date date COMMENT '实还日期',
  repay_principal decimal(17,2) COMMENT '应还本金',
  repay_interest decimal(17,2) COMMENT '应还利息',
  repay_overdue_fee decimal(17,2) COMMENT '应还罚息',
  repay_fee decimal(17,2) COMMENT '应还费用',
  repay_guarantee_fee decimal(17,2) COMMENT '应还融担费',
  repay_amount decimal(17,2) COMMENT '应还总金额',
  repaid_principal decimal(17,2) COMMENT '实还本金',
  repaid_interest decimal(17,2) COMMENT '实还利息',
  repaid_overdue_fee decimal(17,2) COMMENT '实还罚息',
  repaid_fee decimal(17,2) COMMENT '实还费用',
  repaid_guarantee_fee decimal(17,2) COMMENT '实还融担费',
  repaid_amount decimal(17,2) COMMENT '实还总金额',
  residue_principal decimal(17,2) COMMENT '剩余应还本金',
  residue_interest decimal(17,2) COMMENT '剩余应还利息',
  residue_overdue_fee decimal(17,2) COMMENT '剩余应还罚息',
  residue_fee decimal(17,2) COMMENT '剩余应还费用',
  residue_guarantee_fee decimal(17,2) COMMENT '剩余应还融担费',
  residue_amount decimal(17,2) COMMENT '剩余应还总金额',
  reduction_principal decimal(17,2) COMMENT '减免本金',
  reduction_interest decimal(17,2) COMMENT '减免利息',
  reduction_overdue_fee decimal(17,2) COMMENT '减免罚息',
  reduction_fee decimal(17,2) COMMENT '减免费用',
  reduction_guarantee_fee decimal(17,2) COMMENT '减免融担费',
  reduction_amount decimal(17,2) COMMENT '减免总金额',
  overdue_days varchar(8) COMMENT '当期逾期天数',
  max_overdue_days varchar(8) COMMENT '历史最大逾期天数',
  overdue_count varchar(8) COMMENT '累计逾期次数',
  first_overdue_date varchar(8) COMMENT '首次逾期日期',
  repay_type varchar(2) COMMENT '还款状态',
  asset_package_no varchar(20) COMMENT '资产池编号',
  status varchar(2) COMMENT '是否加入对账文件',
  file_date date COMMENT '文件日期',
  created_time timestamp COMMENT '创建时间',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '还款资产端对账文件拆分数据'
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");
