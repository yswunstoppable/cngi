#sql("getByToken")
select id,user_id,session_id,expires_in,expires_time
from user_session
where session_id = #para(sessionId)
#end

#sql("deleteByAccount")
delete from user_session
where user_id = #para(accountId)
#end

#sql("getByAccount")
select id,session_id,expires_in,expires_time
from user_session
where user_id = #para(accountId)
order by id desc
#end

#sql("deleteByToken")
delete from user_session
where session_id = #para(token)
#end