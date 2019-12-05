<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <script>
        $(function () {
            function upload(bannerId) {
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/banner/upload",
                    datatype:"json",
                    type:"post",
                    data:{'bannerId':bannerId},
                    // 指定的上传input框的id
                    fileElementId:"url",
                    success:function (data) {
                        // 输出上传成功
                        // jqgrid重新载入
                        $("#banners").trigger("reloadGrid");
                    }
                })
            }
            $('#banners').jqGrid({
                styleUI:'Bootstrap',
                url:'${path}/banner/findAll',
                datatype:'json',
                mtype:'get',
                colNames:["编号","标题","图片","链接","创建日期","描述","状态"],
                colModel:[
                    {name:'id',align:'center',search:false,hidden:true,height:200},
                    {name:'title',align:'center',editable:true,height:200},
                    {name:'url',align:'center',editable:true,height:200,edittype:"file",editoptions:{enctype:"multipart/form-data"},formatter:function (data) {
                        return "<img style='width: 100%' src='"+data+"'>";
                        }},
                    {name:'href',align:'center',editable:true,height:200},
                    {name:'createDate',align:'center',height:200},
                    {name:'description',align:'center',editable:true,search:false,height:200},
                    {name:'status',align:'center',height:200,editable:true,formatter:function (data) {
                            if(data == 1){
                                return "使用";
                            }
                            return "未使用";
                        },edittype:"select",editoptions:{value:"1:展示;2:不展示"}}
                ],
                pager:"#pager",
                rowNum:5,
                rowList:[5,10,15],
                viewrecords:true,
                mtype : "post",
                caption:'轮播图',
                editurl:'${path}/banner/edit',
                autowidth:true,
                height: 400,
                multiselect:true
            }).navGrid('#pager',
                {edittext:"编辑",addtext:"添加",deltext:"删除"},   //参数2:开启工具栏编辑按钮
                {closeAfterEdit:true,reloadAfterSubmit:true,afterSubmit:function (response,postData) {
                        var bannerId = response.responseJSON.bannerId;
                        upload(bannerId);
                        return postData;
                    }/*beforeShowForm:function (frm) {
                        frm.find("#url").attr("disabled",true);
                    }*/},//编辑面板的配置
                {closeAfterAdd:true,afterSubmit:function (response,postData) {
                        var bannerId = response.responseJSON.bannerId;
                        upload(bannerId);
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
            <ul class="nav nav-tabs">
                <li class="active"><a>轮播图列表</a></li>
                <li><a href="javascript:void(0);" onclick="outPoi()">导出轮播图信息</a></li>
                <li><a href="javascript:void(0);" onclick="outPoiModel()">导出轮播图模板</a></li>
                <li><a href="javascript:void(0);" onclick="showMessage()">导入轮播图信息</a></li>
            </ul>
            <script>
                function outPoi() {
                    location.href = "${path}/banner/out";
                }

                function outPoiModel() {
                    location.href = "${path}/banner/outModel";
                }

                function showMessage() {
                    $("#form")[0].reset();
                    $("#kind").modal("show");
                }
                
                function inputMessage() {
                    $.ajaxFileUpload({
                        url:"${path}/banner/input",
                        type:"post",
                        datatype:"text",
                        fileElementId:"file",
                        success:function (data) {
                            $("#kind").modal("hide");
                        }
                    })
                }
            </script>
            <div class="panel">
                <table id="banners"></table>
                <div id="pager" style="height:30px">
            </div>
            </div>
        </div>
    </div>

<div class="modal fade" id="kind" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 750px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">导入轮播图数据</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="form" method="post" class="form-horizontal">
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="file" name="file" id="file">
                        </div>
                    </div>
                    <div class="pull-right">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>&nbsp;&nbsp;
                        <button type="button" class="btn btn-primary pull-right" onclick="inputMessage()">提交</button>
                    </div>
                </form>
            </div>
            <div class="modal-footer" id="modal_foot"></div>
        </div>
    </div>
</div>
