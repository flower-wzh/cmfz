<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $('#users').jqGrid({
            styleUI:'Bootstrap',
            url:'${path}/user/findAll',
            datatype:'json',
            mtype:'get',
            colNames:["编号","名字","性别","城市","注册时间","上次登录","状态","操作"],
            colModel:[
                {name:'id',align:'center',search:false,hidden:true},
                {name:'name',align:'center',editable:true},
                {name:'sex',align:'center',editable:true},
                {name:'location',align:'center',editable:true},
                {name:'rigestDate',align:'center',editable:true},
                {name:'lastLogin',align:'center',editable:true},
                {name:'status',align:'center',editable:true,formatter:function (data) {
                        if(data == 1){
                            return "使用";
                        }
                        return "冻结";
                    }},
                {name:'option',align:'center',editable:true,formatter:function (cellvalue, options, rowObject) {
                        return "<button class='btn btn-primary' onclick=\"changeStatus('" + rowObject.id + "','"+rowObject.status+"')\">修改状态</button>"
                    }},
            ],
            pager:"#pager",
            rowNum:5,
            rowList:[5,10,15],
            viewrecords:true,
            mtype : "post",
            caption:'用户列表',
            autowidth:true,
            multiselect:true
        }).navGrid('#pager',
            {
                sopt:['eq','ne','cn']
            }
        );
    });
    //更改状态
    function changeStatus(id,status) {
/*        console.log(id);
        console.log(status);*/
        $.ajax({
            url:'${path}/user/changeStatus',
            type:'GET',
            dateType:'text',
            data:{
                id:id,
                status:status
            },
            success:function () {
                $("#users").trigger("reloadGrid");
            }
        })
    }

</script>
<div class="row">
    <div class="col-sm-12">
        <table id="users"></table>
        <div id="pager" style="height:30px">
        </div>
    </div>
</div>


