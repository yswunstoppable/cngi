#sql("list")
select tcl.id,tcl.ip_address,tcl.is_white,tcl.is_success,tcl.command_content,tcl.command_result,tcl.err_msg,
tcl.created_time,b.ip_address as boundary
 from timer_command_log tcl
 join boundary b on tcl.boundary_id = b.id
  where tcl.is_deleted = 0
    #if(notBlank(ip))
        and position(#para(ip) in tcl.ip_address) > 0
    #end
  order by tcl.id desc
#end