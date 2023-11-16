set hive.exec.dynamic.partition.mode=nonstrict;
alter table dw.dwd_a4l_d_f_fund_apply_subsidiary drop partition(ETL_DATE = '${load_date}');
insert into table dw.dwd_a4l_d_f_fund_apply_subsidiary partition(ETL_DATE)
select
  T1.id
  ,t1.apply_no
  ,NULL
  ,'2'
  ,NULL
  ,NULL
  ,cast(t2.guarantee_consult_rate as decimal(10,4))+cast(t2.guarantee_rate as decimal(10,4))
  ,t2.term
  ,t2.extend_info
  ,t2.term
  ,t2.extend_info
  ,T1.create_time
  ,T1.update_time
  ,'01'
  ,'苏宁二期'
  ,'${load_date}'
FROM ODS.s_f_s_10000_div_user T1
LEFT JOIN ODS.s_f_s_10000_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '${load_date}'
WHERE t1..ETL_DATE = '${load_date}' ;


insert into table dw.dwd_a4l_d_f_fund_apply_subsidiary partition(ETL_DATE)
select
  T1.id
  ,t1.apply_no
  ,NULL
  ,'-99'
  ,NULL
  ,NULL
  ,'-99'
  ,'-99'
  ,t2.credit_risk2_msg
  ,t2.term
  ,t2.credit_risk2_msg
  ,T1.create_time
  ,T1.update_time
  ,'02'
  ,‘’苏宁1，不涉及担保'
  ,'${load_date}'
FROM ODS.s_f_s_10003_div_user T1
LEFT JOIN ODS.s_f_s_10003_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '${load_date}'
WHERE t1..ETL_DATE = '${load_date}' ;


insert into table dw.dwd_a4l_d_f_fund_apply_subsidiary partition(ETL_DATE)
select
  T1.id
  ,t1.apply_no
  ,NULL
  ,'2'
  ,NULL
  ,T2.fee_amt
  ,T2.fee_rate
  ,T2.tnr
  ,NULL
  ,t2.apply_tnr
  ,NULL
  ,T1.create_time
  ,T1.update_time
  ,'03'
  ,‘’长银'
  ,'${load_date}'
FROM ODS.s_f_s_10001_div_user T1
LEFT JOIN ODS.s_f_s_10001_file_eod_guarantee_daily T2
ON T1.business_no = T2. appl_seq
AND T2.ETL_DATE = '${load_date}'
WHERE t1..ETL_DATE = '${load_date}' ;


insert into table dw.dwd_a4l_d_f_fund_apply_subsidiary partition(ETL_DATE)
select
  T1.id
  ,t1.apply_no
  ,NULL
  ,'2'
  ,NULL
  ,NULL
  ,T2.fee_rate/T2.loan_amt*12/T2.term
  ,T2.term
  ,t2.create_time
  ,t2.apply_tnr
  ,T2.credit_risk_msg
  ,T1.create_time
  ,T1.update_time
  ,'04'
  ,‘’众邦'
  ,'${load_date}'
FROM ODS.s_f_s_10002_div_user T1
LEFT JOIN ODS.s_f_s_10002_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '${load_date}'
WHERE t1..ETL_DATE = '${load_date}' ;


insert into table dw.dwd_a4l_d_f_fund_apply_subsidiary partition(ETL_DATE)
select
  T1.id
  ,t1.apply_no
  ,NULL
  ,'-99'
  ,NULL
  ,'-99'
  ,'-99'
  ,'-99'
  ,t2.loan_date
  ,t2.pay_date
  ,'-99'
  ,T1.create_time
  ,T1.update_time
  ,'05'
  ,‘’车金矿'
  ,'${load_date}'
FROM ODS.s_f_s_10004_div_user T1
LEFT JOIN ODS.s_f_s_10004_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '${load_date}'
WHERE t1..ETL_DATE = '${load_date}' ;


