SET hive.exec.dynamic.partition.mode=nonstrict;
INSERT OVERWRITE TABLE ${ods}.s_f_d_10008_credit_pre_check_apply PARTITION(etl_date)
SELECT
  *
  ,'${load_date}'
FROM ${ods}.o_f_10008_credit_pre_check_apply;