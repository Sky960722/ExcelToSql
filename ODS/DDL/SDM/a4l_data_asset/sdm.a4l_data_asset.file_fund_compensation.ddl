drop table if exists ${hivevar:ods}.s_f_d_10008_file_fund_compensation;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_file_fund_compensation (
  id bigint COMMENT '物理主键',
  fund_loan_no varchar(64) COMMENT '资金方放款编号',
  loan_no varchar(64) COMMENT '放款编号',
  business_no varchar(64) COMMENT '业务流水号',
  repay_no varchar(64) COMMENT '还款编号',
  compensation_date date COMMENT '代偿日期',
  merchant_id bigint COMMENT '渠道id',
  period int COMMENT '代偿期数',
  return_amount decimal(17,2) COMMENT '归还金额',
  compensation_amount decimal(17,2) COMMENT '代偿金额',
  compensation_principal decimal(17,2) COMMENT '代偿本金',
  compensation_interest decimal(17,2) COMMENT '代偿利息',
  compensation_over_fee decimal(17,2) COMMENT '代偿罚息',
  compensation_compound_interest decimal(17,2) COMMENT '代偿复利',
  compensation_fee decimal(17,2) COMMENT '代偿费用',
  deal_status varchar(2) COMMENT '处理状态01 成功\n02 失败',
  compensation_type varchar(2) COMMENT '01 代偿 \n02 回购（包含主动回购）',
  asset_package_no varchar(20) COMMENT '资产池编号',
  fund_channel_no varchar(64) COMMENT '资金渠道标识',
  status varchar(2) COMMENT '是否加入对账文件',
  file_date date COMMENT '文件日期',
  created_time timestamp COMMENT '创建时间',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '代偿文件数据'
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

