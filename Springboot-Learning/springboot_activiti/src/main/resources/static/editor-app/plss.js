function openUserSelectDialog(id) {
    var iWidth=1000; //弹出窗口的宽度;
    var iHeight=550; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
     window.open('../model/userSelect.html?id='+id,"选择用户","height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
}

/**
 * 生成分页按钮
 */
function page(sum) {
    var s=Math.ceil(sum/10);
    var buttons="<button  onclick=\"pageQuery("+1+")\" title=\"首页\">首页</button>";
    for (var i=1;i<=s;i++){
        buttons+="<button id="+"sum"+i+" onclick=\"pageQuery("+i+")\" title="+"第"+i+"页"+">第"+i+"页</button>"
    }
    buttons+="<button  onclick=\"pageQuery("+s+")\" title=\"尾页\">尾页</button>";
    document.getElementById("nexts").innerHTML=buttons;

}
/**
 * 删除table所有行 除去表头
 */
function deleteTableRows() {
    var tb = document.getElementById('tb');
    var rowNum=tb.rows.length;
    for (i=1;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
}
/**
 * 查询
 */
function aQuery(currentPage,name) {
    //父窗口参数
    var locationpro = location.search.substring(1).split("=")[1];
    var inputtype = "";
    if("assigneeField"==locationpro){
        inputtype="radio";
    }else {
        inputtype="checkbox";
    }
    if(currentPage!=null){pageq=currentPage;}else {
        pageq=1;
    }


    $.ajax({
        type:"post",
        url:"http://218.247.12.42:8196/outsourcedController/getStaffListByOfficeOrTeam",
        data:{
            "unitCode":"bjscsygj",
            "hospitalCode":"zxyshj",
            "currentPage":pageq,
            "pageSize":"10",
            "name":name

        },
        async: false,
        success:function (data) {
            //删除table所有行
            deleteTableRows();
            var  shhuzu=data.data.staffList;
            if(shhuzu.length>10){
                document.getElementById("conut").innerHTML="显示第 1 到第 10 条记录，总共 "+data.data.sum+" 条记录"
            }else {
                document.getElementById("conut").innerHTML="显示第 1 到第 "+shhuzu.length+" 条记录，总共 "+data.data.sum+" 条记录"
            }
            //生成分页按钮
            page(data.data.sum);
            for(var i=0;i < shhuzu.length;i++){
                var x=document.getElementById('tb').insertRow();
                for(var j=0;j <6 ; j++ ){
                    var cell=x.insertCell();
                    if(j==0){
                        cell.innerHTML="<input type='"+inputtype+"' value="+data.data.staffList[i].id+" name=\"addPrice\" />";
                    }else if(j==1){
                        cell.innerHTML=data.data.staffList[i].userName;
                    }else if(j==2){
                        cell.innerHTML=data.data.staffList[i].name;
                    }else if(j==3){
                        cell.innerHTML=data.data.staffList[i].officeName;
                    }else if(j==4){
                        cell.innerHTML=data.data.staffList[i].sex;
                    }else if(j==5){
                        var mobile=data.data.staffList[i].mobile;
                        if(mobile==null){  cell.innerHTML="未录入";}else {cell.innerHTML=data.data.staffList[i].mobile;}
                    }
                }

            }
        }
    });
}

/**
 * 查询方法
 */
function buttonQuery() {
    var els =document.getElementById("loginName").value;
    if (els==null){
        alert("请填写姓名")
        return;
    }
    aQuery(null,els)
}


/**
 * 分页查询
 * @param pageSize
 */
function pageQuery(pageSize) {
    var els =document.getElementById("loginName").value;
    aQuery(pageSize,els)
}

/**
 * 确定方法
 */
function queding() {
    var els =document.getElementsByName("addPrice");
    var str="";
    for (var i = 0, j = els.length; i < j; i++){
        if(els[i].checked){
            str+=els[i].value+",";
        }
    }
    if(str==null){
        alert("请选中")
    }else {
        str=str.substr(0,str.length-1);
        var query = location.search.substring(1).split("=")[1];
        if("assigneeField"==query){
            window.opener.document.getElementById("assigneeField").value = str;
            window.opener.document.getElementById("userField").value = "";
        }else {
            window.opener.document.getElementById("userField").value = str;
            window.opener.document.getElementById("assigneeField").value = "";
        }

        window.close();
    }
}

/**
 * 关闭方法
 */
function buttonOff() {
    window.close();
}

/**
 * 重置方法
 */
function buttonClear() {
    document.getElementById("loginName").value="";
}
/*动态显示表格内容*/
window.onload=function(){
    aQuery(null,null);
}