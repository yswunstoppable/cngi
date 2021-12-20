function searchByPara(){
	var account = $("#account").val();
	window.location.href = "/monitor-server/log/list?account="+escape(escape(account));
}

function add() {
	window.location.href = "/monitor-server/appInfo/edit";
}


function view(id) {
	window.location.href = "/monitor-server/appInfo/view?id="+id;
}

function edit(id){
	window.location.href = "/monitor-server/appInfo/edit?id="+id;
}

function del(id) {
	if(confirm('你确定要删除吗？')) {
		window.location.href = "/monitor-server/appInfo/del?id=" + id;
	}
}
function viewDate(id,dates){
	window.location.href = "/monitor-server/appInfo/view?id="+id+"&date="+dates;
}

function cancel(){
	history.back();
}
