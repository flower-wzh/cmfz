<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <script>
        $(function () {
            $('#articles').jqGrid({
                styleUI:'Bootstrap',
                url:'${path}/article/findAll',
                datatype:'json',
                mtype:'get',
                colNames:["编号","上师","标题","封面","内容","状态","上传时间","发布时间","操作"],
                colModel:[
                    {name:'id',align:'center',search:false,hidden:true},
                    {name:'guruId',align:'center',search:false,hidden:true},
                    {name:'title',align:'center',editable:true},
                    {name:'img',align:'center',editable:true,formatter:function (data) {
                        return "<img src='"+data+"' width='100%'>";
                        }},
                    {name:'content',align:'center',editable:true,hidden:true},
                    {name:'status',align:'center',editable:true},
                    {name:'createDate',align:'center'},
                    {name:'publishDate',align:'center'},
                    {name:'option',align:'center',formatter:function (cellvalue, options, rowObject) {
                            var result = '';
                            result += "<a href='javascript:void(0)' onclick=\"showModel('" + rowObject.id + "')\" class='btn btn-lg' title='查看详情'> " +
                                "<span class='glyphicon glyphicon-th-list'></span></a>";
                            return result;
                        }}
                ],
                pager:"#pager",
                rowNum:5,
                rowList:[5,10,15],
                viewrecords:true,
                mtype : "post",
                caption:'轮播图',
                editurl:'${path}/article/edit',
                autowidth:true,
                height: 400,
                multiselect:true
            }).navGrid('#pager',
                {edit:false,add:false,edittext:"编辑",addtext:"添加",deltext:"删除"},   //参数2:开启工具栏编辑按钮
                {closeAfterEdit:true,reloadAfterSubmit:true,afterSubmit:function (response,postData) {
                        var articleId = response.responseJSON.articleId;
                        //upload(articleId);
                        return postData;
                    }/*beforeShowForm:function (frm) {
                        frm.find("#url").attr("disabled",true);
                    }*/},//编辑面板的配置
                {closeAfterAdd:true,afterSubmit:function (response,postData) {
                        var articleId = response.responseJSON.articleId;
                        //upload(articleId);
                        return postData;
                    }},//添加面板的配置
                {},//删除的配置
                {
                    sopt:['eq','ne','cn']
                }
            );

        })
        function showModel(id) {
            var data = $("#articles").jqGrid("getRowData",id);
            $("#title").val(data.title);
            $("#status").val(data.status);
            $("#guru").val(data.guruId);
            //$("#guru [value='"+data.guruId+"']").attr("select",true);
            $("#id").val(data.id);
            KindEditor.html("#editor_id",data.content);
            $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
                "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateArticle()\">提交</button>")
            $("#kind").modal("show");
        }
        function addArticle() {
            $("#kindfrm")[0].reset();
            KindEditor.html("#editor_id","");
            $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
                "<button type=\"button\" class=\"btn btn-primary\" onclick=\"insertArticle()\">提交</button>")
            $("#kind").modal("show");
        }

        // 添加文章
        function insertArticle() {
            $.ajaxFileUpload({
                url:"${path}/article/add",
                type:"post",
                datatype:"json",
                fileElementId:"file",
                data:{
                    id:$("#id").val(),
                    title:$("#title").val(),
                    status:$("#status").val(),
                    guruId:$("#guru").val(),
                    content:$("#editor_id").val()
                },
                success:function (data) {
                    $("#kind").modal("hide");
                    $("#articles").trigger("reloadGrid");
                }
            })
        }
        function updateArticle() {
            $.ajaxFileUpload({
                url:"${path}/article/modify",
                type:"post",
                datatype:"json",
                fileElementId:"file",
                data:{
                    id:$("#id").val(),
                    title:$("#title").val(),
                    status:$("#status").val(),
                    guruId:$("#guru").val(),
                    content:$("#editor_id").val()
                },
                success:function (data) {
                    $("#kind").modal("hide");
                    $("#articles").trigger("reloadGrid");
                }
            })
        }
    </script>
<div class="col-sm-12">
    <div class="page-header">
        <h2><strong>文章管理</strong></h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a>文章列表</a></li>
        <li><a onclick="addArticle()">添加文章</a></li>
    </ul>
    <div class="panel">
        <table id="articles"></table>
        <div id="pager" style="height:30px"></div>
    </div>
</div>
