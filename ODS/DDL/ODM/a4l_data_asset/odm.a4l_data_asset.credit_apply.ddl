drop table if exists ${hivevar:ods}.o_f_10008_credit_apply;
CREATE TABLE ${hivevar:ods}.o_f_10008_credit_apply (
  id BIGINT COMMENT '主键',
  credit_apply_no varchar(64) COMMENT '授信唯一标识',
  asset_package_no varchar(64) COMMENT '资产池编号',
  business_no varchar(64) COMMENT '业务流水号',
  user_no varchar(32) COMMENT '用户编号',
  id_no varchar(64) COMMENT '用户身份证号md5',
  merchant_id varchar(64) COMMENT '商户Id',
  fund_open_id varchar(64) COMMENT '资金授信唯一标识',
  risk_status varchar(2) COMMENT '风控结果 S 通过 R 拒绝 P 处理中',
  risk_reason varchar(64) COMMENT '风控原因',
  apply_status varchar(2) COMMENT '授信状态S 通过 F 失败 R 拒绝',
  apply_reason String COMMENT '授信失败原因',
  risk_score int COMMENT '风控得分',
  risk_level varchar(64) COMMENT '风控等级',
  fund_status varchar(64) COMMENT '资方结果 S 通过 R 拒绝 P 处理中',
  fund_reason String COMMENT '资方申请失败原因',
  credit_amount decimal(19,2) COMMENT '授信额度',
  credit_start_date date COMMENT '授信有效期开始时间',
  credit_end_date date COMMENT '授信有效期结束时间',
  bind_type varchar(2) COMMENT '签约方式 1 支付渠道签约 2 共享协议码签约',
  due_day_mathod varchar(2) COMMENT '还款日规则 1放款日对日 2 固定日',
  due_day varchar(2) COMMENT '还款日',
  cooperator_mdel varchar(2) COMMENT '业务模式 1融担 2自营 3联合助贷',
  limit_type varchar(2) COMMENT '额度类型 01循环额度 02 单笔单批 03 结清可循环',
  credit_status varchar(5) COMMENT '额度状态',
  contract_no varchar(32) COMMENT '合同编号',
  avaliable_amount decimal(19,2) COMMENT '可用额度',
  min_loan_amount decimal(19,2) COMMENT '单笔最小金额',
  step_amount varchar(5) COMMENT '最小步长',
  pay_method varchar(2) COMMENT '还款方式',
  term_month varchar(5) COMMENT '放款期数',
  rate_type varchar(2) COMMENT '利率类型 D日息 M月息 Y年息',
  rate decimal(19,6) COMMENT '利率',
  fund_channel_no varchar(64) COMMENT '资金渠道标识',
  device_id varchar(30) COMMENT '设备号',
  terminal varchar(2) COMMENT '终端类型',
  ip_address varchar(30) COMMENT 'ip',
  gps_address varchar(30) COMMENT 'gps',
  created_time timestamp COMMENT '',
  updated_time timestamp COMMENT '',
  PRIMARY KEY (id) DISABLE NOVALIDATE
) COMMENT '授信申请表'
STORED AS PARQUET
TBLPROPERTIES("parquet.compression"="snappy");

