drop table if exists ${hivevar:ods}.o_f_10008_credit_apply_subsidiary;
CREATE TABLE ${hivevar:ods}.o_f_10008_credit_apply_subsidiary (
  id bigint COMMENT '主键',
  credit_apply_id bigint COMMENT '授信申请id',
  apply_amount decimal(19,2) COMMENT '授信申请金额',
  apply_time timestamp COMMENT '授信申时间 yyyyMMddHHmmss',
  account varchar(32) COMMENT '账号',
  account_name varchar(256) COMMENT '持卡人姓名',
  account_phone varchar(20) COMMENT '银行卡预留手机号',
  guar_type varchar(2) COMMENT '担保类型',
  guar_name varchar(256) COMMENT '担保公司类型',
  guar_amount decimal(19,2) COMMENT '担保金额',
  guar_rate decimal(19,6) COMMENT '担保费率',
  guar_period varchar(2) COMMENT '担保期数',
  channel_type varchar(5) COMMENT '渠道类型',
  is_new_customer varchar(2) COMMENT '是否新客户 1是 2否',
  register_time timestamp COMMENT '注册时间 yyyyMMddHHmmss',
  apply_period varchar(5) COMMENT '申请期数',
  credit_rate decimal(19,6) COMMENT '授信利率',
  business_risk_price decimal(19,6) COMMENT '对客利率',
  credit_score varchar(6) COMMENT '客户风险评分',
  credit_level varchar(6) COMMENT '客户风险等级',
  if_reloan varchar(6) COMMENT '是否复借',
  purpose varchar(5) COMMENT '借款用途',
  three_elements_auth_result varchar(2) COMMENT '三要素鉴权结果 Y通过 N不通过',
  four_elements_auth_result varchar(2) COMMENT '四要素鉴权结果 Y通过 N不通过',
  created_time timestamp COMMENT '',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '授信申请附属表'
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

