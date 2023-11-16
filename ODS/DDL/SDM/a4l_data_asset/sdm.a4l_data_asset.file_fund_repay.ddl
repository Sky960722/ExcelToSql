drop table if exists ${hivevar:ods}.s_f_d_10008_file_fund_repay;
CREATE TABLE ${hivevar:ods}.s_f_d_10008_file_fund_repay (
  id bigint COMMENT '物理主键',
  fund_loan_no varchar(64) COMMENT '资金方放款编号',
  merchant_id bigint COMMENT '渠道id',
  loan_no varchar(64) COMMENT '放款编号',
  business_no varchar(64) COMMENT '业务流水号',
  repay_no varchar(64) COMMENT '还款流水号',
  repay_date date COMMENT '还款日期',
  period varchar(32) COMMENT '还款期数 多个，号分隔',
  repay_type int COMMENT '还款种类：0 正常还当期\n1 逾期还当期\n2 全部结清 \n3 按金额还款 \n4 提前还当期 \n5 代偿当期\n6 代偿整笔借据（回购）',
  repay_time timestamp COMMENT '还款时间',
  repay_amount decimal(17,2) COMMENT '还款金额',
  repay_principal decimal(17,2) COMMENT '还款本金',
  repay_interest decimal(17,2) COMMENT '还款利息',
  repay_fee decimal(17,2) COMMENT '还款费用',
  repay_overdue_fee decimal(17,2) COMMENT '还款罚息',
  repay_compound_interest decimal(17,2) COMMENT '还款复利',
  reduce_amount decimal(17,2) COMMENT '优惠金额',
  collection_amount decimal(17,2) COMMENT '代收金额(融担费)',
  repay_status varchar(2) COMMENT '还款状态',
  repayment_results varchar(256) COMMENT '还款结果说明(S-成功；O-处理中；E-失败)',
  asset_package_no varchar(20) COMMENT '资产池编号',
  fund_channel_no varchar(64) COMMENT '资金渠道标识',
  payment_method varchar(2) COMMENT '还款方式 01 资金方扣款\r\n02 第三方支付扣款\r\n03 客户线下还款',
  status varchar(2) COMMENT '是否加入对账文件',
  file_date date COMMENT '文件日期',
  created_time timestamp COMMENT '创建时间',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '放款资金对账文件拆分数据'
PARTITIONED BY (ETL_DATE DATE COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

