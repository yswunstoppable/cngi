#sql("login")
select * from user
 where account = #para(account) and password = #para(password) and is_deleted = 0
#end

#sql("get")
select * from user where id = #para(id)
#end

#sql("list")
select * from user
where id > 1 and is_deleted = 0
#if(notBlank(rq.account))
    and position(#para(rq.account) in account) > 0
#end
#if(notBlank(rq.name))
    and position(#para(rq.name) in name) > 0
#end
#end

#sql("resetPass")
update user set password = #para(password) where id = #para(id)
#end