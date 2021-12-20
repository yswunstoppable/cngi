#sql("list")
select * from white_list
 where is_deleted = 0
 #if(notBlank(rq.address))
    and position(#para(rq.address) in address) > 0
 #end
 #if(rq.status != 0)
    and status = #para(rq.status)
 #end
#end

#sql("listByUsed")
select * from white_list where status = 1 and is_deleted = 0
#end