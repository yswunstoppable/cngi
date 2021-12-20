
function view(id) {
    window.location.href = "/monitor-server/appInfo/view?id="+id;
}

function del(id) {
    window.location.href = "/monitor-server/appInfo/del?id="+id;
}

function cancel(){
    history.back();
}