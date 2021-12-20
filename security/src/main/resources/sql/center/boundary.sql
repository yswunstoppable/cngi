#sql("list")
select * from boundary
 where is_deleted = 0
 #if(notBlank(rq.address))
    and position(#para(rq.address) in address) > 0
 #end
 #if(rq.status != 0)
    and status = #para(rq.status)
 #end
#end