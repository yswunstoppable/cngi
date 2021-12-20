#sql("list")
select t.* from shreshold t
 where t.is_deleted = 0
#end

#sql("get")
select * from shreshold where id = #para(id)
and is_deleted = 0
#end

#sql("judge")
select id from shreshold
where concat("`",#para(field),"`") = #para(value) and is_deleted = 0
#if(id != 0)
and id != #para(id)
#end
#end 