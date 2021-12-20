#sql("list")
select * from command
 where is_deleted = 0
 #if(notBlank(rq.name))
    and position(#para(rq.name) in name) > 0
 #end
 #if(rq.status != 0)
    and status = #para(rq.status)
 #end
#end