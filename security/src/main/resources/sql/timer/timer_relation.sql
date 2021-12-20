#sql("listCommand")
select c.id,c.name,c.content
 from timer_relation tr
join command c on c.id = tr.target_id
where tr.timer_id = #para(timerId) and tr.type = 2 and tr.is_deleted = 0
#end

#sql("listBoundary")
select b.id,b.ip_address,b.username,b.password
 from timer_relation tr
join boundary b on b.id = tr.target_id
where tr.timer_id = #para(timerId) and tr.type = 1 and tr.is_deleted = 0
#end

#sql("listByTimer")
select tr.id,tr.type,tr.target_id from timer_relation tr
 where tr.timer_id = #para(timerId) and tr.is_deleted = 0
#end