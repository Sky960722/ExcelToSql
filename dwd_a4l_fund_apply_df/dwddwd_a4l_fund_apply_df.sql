INSERT OVERWRITE TABLE dwddwd_a4l_fund_apply_df partition(ETL_DATE,SOURCE_NUM)
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
  ,T1.risk_reason
  ,T1.apply_status
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
  ,'${run_date}'
  ,'01'
FROM ODS.S_10000_div_user T1
LEFT JOIN ODS.S_10000_div_loan_apply T2
ON T1.business_no = T2. business_no
AND T2.ETL_DATE = '#{run_date}'
WHERE t1.ETL_DATE = '${run_date}' 



