#sql("list")
select * from perception_source where is_deleted = 0
#end

#sql("get")
select * from perception_source where id = #para(id)
#end

#sql("listValidated")
select id,name from perception_source where is_validated = 1 and is_deleted = 0
#end