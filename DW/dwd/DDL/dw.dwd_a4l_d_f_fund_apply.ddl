drop table if exists dw.dwd_a4l_d_f_fund_apply;
CREATE TABLE dw.dwd_a4l_d_f_fund_apply (
  id varchar(32) COMMENT '客户号',
  credit_apply_no varchar(64) COMMENT '授信唯一标识',
  asset_package_no varchar(32) COMMENT '资产池编号',
  asset_package_desc varchar(60) COMMENT '资产池编号描述',
  business_no varchar(64) COMMENT '业务流水号',
  user_no varchar(64) COMMENT '资产用户编号',
  id_card varchar(128) COMMENT '用户身份证号md5',
  merchant_id VARCHAR(30) COMMENT '商户Id',
  merchant_nm varchar(60) COMMENT '商户名称',
  fund_open_id varchar(64) COMMENT '资金授信唯一标识',
  apply_time timestamp COMMENT '授信申请时间',
  risk_status varchar(2) COMMENT '风控结果',
  risk_status_desc VARCHAR(30) COMMENT '风控结果描述',
  risk_reason varchar(255) COMMENT '风控原因',
  apply_status varchar(2) COMMENT '授信状态',
  apply_status_desc VARCHAR(30) COMMENT '授信状态描述',
  apply_reason STRING COMMENT '授信失败原因',
  risk_score int COMMENT '风控得分',
  fund_status varchar(2) COMMENT '资方结果',
  fund_reason STRING COMMENT '资方申请失败原因',
  credit_amount decimal(19,2) COMMENT '授信金额',
  credit_start_date timestamp COMMENT '授信有效期开始时间',
  credit_end_date timestamp COMMENT '授信有效期结束时间',
  limit_type varchar(20) COMMENT '额度类型',
  limit_type_desc varchar(20) COMMENT '额度类型描述',
  rate_type varchar(2) COMMENT '利率类型',
  rate_type_desc varchar(20) COMMENT '利率类型描述',
  credit_rate decimal(19,6) COMMENT '资金方利率',
  business_risk_price decimal(19,6) COMMENT '资产方利率',
  guar_rate decimal(19,7) COMMENT '担保利率',
  created_time timestamp COMMENT '创建时间',
  updated_time timestamp COMMENT '修改时间',
  SOURCE_NUM varchar(2) COMMENT '数据源',
  SOURCE_NUM_desc varchar(20) COMMENT '数据源描述',
  ETL_DATE varchar(2) COMMENT '数据加载日期'
) COMMENT '授信申请信息表'
PARTITIONED BY (ETL_DATE varchar(2) COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

