
function view(id) {
	window.location.href = "/monitor-server/dbTable/edit?id="+id;
}


function add() {
	window.location.href = "/monitor-server/dbTable/edit";
}

function del(id) {
	if(confirm('你确定要删除吗？')) {
		window.location.href = "/monitor-server/dbTable/del?id=" + id;
	}
}
function viewChart(id){
	window.location.href = "/monitor-server/dbTable/viewChart?id="+id;
}

function cancel(){
	history.back();
}
