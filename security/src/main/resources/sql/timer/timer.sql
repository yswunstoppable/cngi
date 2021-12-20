#sql("list")
select t.*,ps.name as sourceName from timer t
 join perception_source ps on t.source_id = ps.id
 where t.is_deleted = 0
#end

#sql("get")
select * from timer where id = #para(id)
#end

#sql("listUsed")
select t.* from timer t where status = 1 and is_deleted = 0
#end