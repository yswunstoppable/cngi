#sql("list")
select * from attack_source_log where is_deleted = 0
 #if(notBlank(ip))
    and position(#para(ip) in attack_ip) > 0
 #end
 order by id desc
#end

#sql("getLast")
select distinct asl.attack_ip as name,asl.attack_number as total,asl.attack_type
 from attack_source_log asl
 where asl.is_deleted = 0 and asl.timer_log_id = (select max(t.timer_log_id) from attack_source_log t where t.attack_type = asl.attack_type and t.is_deleted = 0)
order by asl.attack_number desc, asl.id
#end

#sql("listByAddress")
select attack_address as item,count(id) as count from attack_source_log where is_deleted = 0
group by attack_address
#end

#sql("listStatics")
select attack_ip,attack_address,sum(attack_number) as attackNumber
from attack_source_log
where is_deleted = 0
group by attack_ip,attack_address
order by attackNumber desc
#end

#sql("staticsByMonth")
SELECT t.month, ifnull(sum(asl.attack_number),0) as number
FROM(
	SELECT 1 AS MONTH
	UNION SELECT 2 AS MONTH
	UNION SELECT 3 AS MONTH
	UNION SELECT 4 AS MONTH
	UNION SELECT 5 AS MONTH
	UNION SELECT 6 AS MONTH
	UNION SELECT 7 AS MONTH
	UNION SELECT 8 AS MONTH
	UNION SELECT 9 AS MONTH
	UNION SELECT 10 AS MONTH
	UNION SELECT 11 AS MONTH
	UNION SELECT 12 AS MONTH
) as t
LEFT JOIN attack_source_log asl on t.month = MONTH(asl.created_time) and YEAR(asl.created_time) = YEAR(NOW())
GROUP BY t.month
order by t.month
#end