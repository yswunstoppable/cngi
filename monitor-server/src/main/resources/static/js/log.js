function searchByPara(){
	var account = $("#account").val();
	window.location.href = "/monitor-server/log/list?account="+escape(escape(account));
}

function view(id) {
	window.location.href = "/monitor-server/log/view?id="+id;
}

function cancel(){
	history.back();
}
