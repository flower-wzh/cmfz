<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        function upload(guruId) {
            $.ajaxFileUpload({
                url:"${pageContext.request.contextPath}/guru/upload",
                datatype:"json",
                type:"post",
                data:{'guruId':guruId},
                // 指定的上传input框的id
                fileElementId:"photo",
                success:function (data) {
                    // 输出上传成功
                    // jqgrid重新载入
                    $("#gurus").trigger("reloadGrid");
                }
            })
        }
        $('#gurus').jqGrid({
            styleUI:'Bootstrap',
            url:'${path}/guru/show',
            datatype:'json',
            mtype:'get',
            colNames:["编号","名字","照片","状态","法号"],
            colModel:[
                {name:'id',align:'center',search:false,hidden:true},
                {name:'name',align:'center',editable:true},
                {name:'photo',align:'center',editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"},formatter:function (data) {
                        return "<img style='width: 100%' src='"+data+"'>";
                    }},
                {name:'status',align:'center',editable:true,formatter:function (data) {
                        if(data == 1){
                            return "使用";
                        }
                        return "未使用";
                    },edittype:"select",editoptions:{value:"1:展示;2:不展示"}},
                {name:'nickName',align:'center',editable:true},
            ],
            pager:"#pager",
            rowNum:5,
            rowList:[5,10,15],
            viewrecords:true,
            mtype : "post",
            caption:'轮播图',
            editurl:'${path}/guru/edit',
            autowidth:true,
            height: 400,
            multiselect:true
        }).navGrid('#pager',
            {edittext:"编辑",addtext:"添加",deltext:"删除"},   //参数2:开启工具栏编辑按钮
            {closeAfterEdit:true,reloadAfterSubmit:true,afterSubmit:function (response,postData) {
                    var guruId = response.responseJSON.guruId;
                    upload(guruId);
                    return postData;
                }/*beforeShowForm:function (frm) {
                        frm.find("#url").attr("disabled",true);
                    }*/},//编辑面板的配置
            {closeAfterAdd:true,afterSubmit:function (response,postData) {
                    var guruId = response.responseJSON.guruId;
                    upload(guruId);
                    return postData;
                }},//添加面板的配置
            {},//删除的配置
            {
                sopt:['eq','ne','cn']
            }
        );
    });
</script>
<div class="row">
    <div class="col-sm-12">
        <table id="gurus"></table>
        <div id="pager" style="height:30px">
        </div>
    </div>
</div>

