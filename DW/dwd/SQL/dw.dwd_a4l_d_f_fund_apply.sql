set hive.exec.dynamic.partition.mode=nonstrict;
alter table dw.dwd_a4l_d_f_fund_apply drop partition(ETL_DATE = '${load_date}');
insert into table dw.dwd_a4l_d_f_fund_apply partition(etl_date)
select
  t1.id
  ,t1.apply_no
  ,t1.asset_package_no
  ,t1.business_no
  ,NULL
  ,T1.id_card
  ,NULL
  ,T1.open_id
  ,T1.created_time
  ,T1.risk_status
  ,case
  when T1.risk_status = 'F' then '未通过'
  when T1.risk_status = 'S' then '通过'
  when T1.risk_status = 'E' then '异常'
  else 'error_code'
end
  ,T1.risk_reason
  ,T1.apply_status
  ,case
  when T1.apply_status = 'P' then '处理中'
  when T1.apply_status = 'S' then '成功'
  when T1.apply_status = 'F' then '失败'
  when T1.apply_status = 'R' then '拒绝'
  when T1.apply_status = 'E' then '错误'
  else 'error_code'
end
  ,T1.apply_reason
  ,T1.credit_score
  ,CASE
  WHEN T1.risk_status = 'P' THEN T1.apply_status
  ELSE T1.risk_status
END
  ,CASE
  WHEN T1.risk_status = 'P' THEN T1.apply_reason
  ELSE T1.risk_reason
END
  ,T2.busi_amt
  ,T1.created_time
  ,NULL
  ,NULL
  ,NULL
  ,T1.create_time
  ,T1.update_time
  ,'01'
  ,'${load_date}'
FROM ODS.s_f_s_10000_div_user T1
LEFT JOIN ODS.s_f_s_10000_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '${load_date}'
WHERE t1.ETL_DATE = '${load_date}' ;


insert into table dw.dwd_a4l_d_f_fund_apply partition(etl_date)
select
  t1.id
  ,t1.apply_no
  ,t1.asset_package_no
  ,t1.business_no
  ,t1.user_no
  ,t1.id_no
  ,NULL
  ,T1.open_id
  ,T1.created_time
  ,T1.risk_status
  ,case
  when T1.risk_status = 'F' then '未通过'
  when T1.risk_status = 'S' then '通过'
  when T1.risk_status = 'E' then '异常'
  else 'error_code'
end
  ,T1.risk_reason
  ,T1.apply_status
  ,case
  when T1.apply_status = 'P' then '处理中'
  when T1.apply_status = 'S' then '成功'
  when T1.apply_status = 'F' then '失败'
  when T1.apply_status = 'R' then '拒绝'
  when T1.apply_status = 'E' then '错误'
  else 'error_code'
end
  ,T1.apply_reason
  ,T1.credit_score
  ,CASE
  WHEN T1.risk_status = 'P' THEN T1.apply_status
  ELSE T1.risk_status
END
  ,CASE
  WHEN T1.risk_status = 'P' THEN T1.apply_reason
  ELSE T1.risk_reason
END
  ,T2.busi_amt
  ,T1.created_time
  ,NULL
  ,NULL
  ,t1.cust_day_rate
  ,T1.create_time
  ,T1.update_time
  ,'02'
  ,'${load_date}'
FROM ODS.s_f_s_10001_div_user T1
LEFT JOIN ODS.s_f_s_10001_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '${load_date}'
WHERE t1.ETL_DATE = '${load_date}' ;


insert into table dw.dwd_a4l_d_f_fund_apply partition(etl_date)
select
  t1.id
  ,t1.apply_no
  ,t1.asset_package_no
  ,t1.business_no
  ,t1.user_no
  ,t1.id_card
  ,NULL
  ,T1.open_id
  ,t1.create_time
  ,T1.risk_status
  ,case
  when T1.risk_status = 'F' then '未通过'
  when T1.risk_status = 'S' then '通过'
  when T1.risk_status = 'E' then '异常'
  else 'error_code'
end
  ,T1.risk_reason
  ,T1.apply_status
  ,case
  when T1.apply_status = 'P' then '处理中'
  when T1.apply_status = 'S' then '成功'
  when T1.apply_status = 'F' then '失败'
  when T1.apply_status = 'R' then '拒绝'
  when T1.apply_status = 'E' then '错误'
  else 'error_code'
end
  ,T1.apply_reason
  ,T1.credit_score
  ,CASE
  WHEN T1.risk_status = 'P' THEN T1.apply_status
  ELSE T1.risk_status
END
  ,CASE
  WHEN T1.risk_status = 'P' THEN T1.apply_reason
  ELSE T1.risk_reason
END
  ,T2.credit_amount
  ,T1.created_start_date
  ,t1.credit_end_date
  ,NULL
  ,t1.extend_info
  ,T1.create_time
  ,T1.update_time
  ,'01'
  ,'${load_date}'
FROM ODS.s_f_s_10002_div_user T1
LEFT JOIN ODS.s_f_s_10002_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '${load_date}'
WHERE t1.ETL_DATE = '${load_date}' ;


insert into table dw.dwd_a4l_d_f_fund_apply partition(etl_date)
select
  t1.id
  ,t1.apply_no
  ,t1.asset_package_no
  ,t1.business_no
  ,t1.user_no
  ,t1.id_card
  ,NULL
  ,T1.open_id
  ,t1.create_time
  ,T1.risk_status
  ,null
  ,T1.risk_reason
  ,T1.apply_status
  ,case
  when T1.apply_status = 'P' then '处理中'
  when T1.apply_status = 'S' then '成功'
  when T1.apply_status = 'F' then '失败'
  when T1.apply_status = 'R' then '拒绝'
  when T1.apply_status = 'E' then '错误'
  else 'error_code'
end
  ,T1.apply_reason
  ,T3.div_user_risk_control
  ,CASE
  WHEN T1.risk_status = 'P' THEN T1.apply_status
  ELSE T1.risk_status
END
  ,CASE
  WHEN T1.risk_status = 'P' THEN T1.apply_reason
  ELSE T1.risk_reason
END
  ,T2.credit_amount
  ,T1.created_start_date
  ,null
  ,NULL
  ,'24'
  ,T1.create_time
  ,T1.update_time
  ,'01'
  ,'${load_date}'
FROM ODS.s_f_s_10003_div_user T1
LEFT JOIN ODS.s_f_s_10003_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '${load_date}'
LEFT JOIN ODS.s_f_s_10003_div_user_risk_control T3
ON T1.business_no = T3. business_no
AND T1.user_id = T3.user_id
AND T2.ETL_DATE = '${load_date}'
WHERE t1.ETL_DATE = '${load_date}' ;


insert into table dw.dwd_a4l_d_f_fund_apply partition(etl_date)
select
  t1.id
  ,t1.rent_plan_code
  ,t1.asset_package_no
  ,t1.rent_plan_code
  ,null
  ,t1.id_no_md5
  ,NULL
  ,T1.open_id
  ,t1.reconciliation_date
  ,T1.risk_status
  ,null
  ,null
  ,null
  ,null
  ,null
  ,null
  ,null
  ,null
  ,T2.contract_amount/100
  ,T1.created_start_date
  ,null
  ,NULL
  ,null
  ,T1.create_time
  ,T1.update_time
  ,'05'
  ,'${load_date}'
FROM ODS.s_f_s_10004_div_customer_apply T1
LEFT JOIN ODS.s_f_s_10004_div_customer_payment T2
ON T1.rent_plan_code = T2. rent_plan_code
AND T2.ETL_DATE = '${load_date}'
WHERE t1.ETL_DATE = '${load_date}' ;


