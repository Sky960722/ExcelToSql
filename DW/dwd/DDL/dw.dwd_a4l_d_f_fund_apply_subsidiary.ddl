drop table if exists dw.dwd_a4l_d_f_fund_apply_subsidiary;
CREATE TABLE dw.dwd_a4l_d_f_fund_apply_subsidiary (
  id varchar(32) COMMENT '主键',
  credit_apply_id varchar(64) COMMENT '授信申请id',
  asset_package_no varchar(32) COMMENT '资产池编号',
  guar_type varchar(32) COMMENT '担保类型',
  guar_name varchar(32) COMMENT '担保公司类型',
  guar_amount decimal(18,2) COMMENT '担保金额',
  guar_rate decimal(18,2) COMMENT '担保费率',
  guar_period int COMMENT '担保期数',
  register_time timestamp COMMENT '注册时间',
  apply_period int COMMENT '申请期数',
  if_reloan int COMMENT '是否复借',
  created_time timestamp COMMENT '创建时间',
  updated_time timestamp COMMENT '修改时间',
  SOURCE_NUM varchar(2) COMMENT '数据源',
  SOURCE_NUM_DESC varchar(20) COMMENT '数据源描述',
  ETL_DATE date COMMENT '数据加载日期'
) COMMENT '授信申请金额详情表'
PARTITIONED BY (ETL_DATE date COMMENT '数据加载日期')
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

