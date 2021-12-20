
function add() {
	window.location.href = "/monitor-server/heathMonitor/edit";
}

function view(id) {
	window.location.href = "/monitor-server/heathMonitor/view?id="+id;
}

function del(id) {
	if(confirm('你确定要删除吗？')) {
		window.location.href = "/monitor-server/heathMonitor/del?id=" + id;
	}
}
function edit(id){
	window.location.href = "/monitor-server/heathMonitor/edit?id="+id;
}

function cancel(){
	history.back();
}
