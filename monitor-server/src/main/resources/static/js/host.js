function searchByPara(){
	var account = $("#account").val();
	window.location.href = "/monitor-server/log/list?account="+escape(escape(account));
}

function viewDashView(id) {
	window.location.href = "/monitor-server/dash/detail?dashView=1&id="+id;
}
function viewChartDashView(id) {
	window.location.href = "/monitor-server/dash/chart?dashView=1&id="+id;
}
function viewDatetDashView(id,dates){
	window.location.href = "/monitor-server/dash/chart?dashView=1&id="+id+"&date="+dates;
}
function view(id) {
	window.location.href = "/monitor-server/dash/detail?id="+id;
}
function viewChart(id) {
	window.location.href = "/monitor-server/dash/chart?id="+id;
}
function flowChart(id) {
	window.location.href = "/monitor-server/dash/chart?id="+id+"&flow=1";
}
function carriageChart(id) {
	window.location.href = "/monitor-server/dash/chart?id="+id+"&carriage=1";
}
function tempChart(id) {
	window.location.href = "/monitor-server/dash/chart?id="+id+"&temp=1";
}
function humidityChart(id) {
	window.location.href = "/monitor-server/dash/chart?id="+id+"&humidity=1";
}

function del(id) {
	if(confirm('你确定要删除吗？')) {
		window.location.href = "/monitor-server/dash/del?id=" + id;
	}
}


function viewDate(id,dates,flag){
	let url = "/monitor-server/dash/chart?id="+id+"&date="+dates
	if (flag){
		url = url + "&"+flag+"=1"
	}
	window.location.href = url
}

function viewApps(hostname){
	window.location.href = "/monitor-server/appInfo/list?hostname="+hostname;
}

function ajaxSaveRemark() {
	$("#form2").ajaxSubmit(function(message) {
		window.location.href = window.location.href;
	});
}

function setHostRemark(hostname,hostId,hostRemark) {
	$("#id").val(hostId);
	$("#ip").val(hostname);
	$("#remark").val(hostRemark);
}
function cancel(){
	history.back();
}
