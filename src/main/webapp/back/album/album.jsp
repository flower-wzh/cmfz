<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <script>
        $(function () {

            function subTable(subgrid_id, row_id) {
                var subgridTableId = subgrid_id + "table";
                var pagerId = subgrid_id + "pager";
                $("#" + subgrid_id).html(
                    "<table id='" + subgridTableId
                    + "'></table><div style='height: 50px' id='"
                    + pagerId + "'></div>");
                $("#" + subgridTableId).jqGrid(
                    {
                        url : "${path}/chapter/findAll?albumId="+row_id,
                        datatype : "json",
                        colNames : [ '编号', '标题', '大小', '时长','上传时间',"音频","操作" ],
                        colModel : [
                            {name : "id",hidden : true},
                            {name : "title",align : "center",editable:true},
                            {name : "size",align : "center"},
                            {name : "time",align : "center"},
                            {name : "createDate",align : "center"},
                            {name : "oldName",align : "center",editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                            {name : "fileName",align : "center",formatter:function(data) {
                                    var result = "";
                                    result += "<a href='javascript:void(0)' onclick=\"playAudio('" + data + "')\" class='btn btn-lg' title='播放'><span class='glyphicon glyphicon-play-circle'></span></a>";
                                    result += "<a href='javascript:void(0)' onclick=\"downloadAudio('" + data + "')\" class='btn btn-lg' title='下载'><span class='glyphicon glyphicon-download'></span></a>";
                                    return result;
                                }},

                        ],
                        rowNum : 20,
                        pager : "#" + pagerId,
                        styleUI:'Bootstrap',
                        editurl: "${path}/chapter/edit?albumId="+row_id,
                        rowNum:5,
                        rowList:[5,10,15],
                        autowidth:true
                    });
                jQuery("#" + subgridTableId).jqGrid('navGrid',
                    "#" + pagerId, {
                        edit : true,
                        add : true,
                        del : true
                    },{
                        closeAfterEdit:true,reloadAfterSubmit:true,
                        beforeShowForm:function (frm) {
                            frm.find("#create_date").attr("readOnly",true);
                            frm.find("#url").attr("disabled",true);
                        },
                        afterSubmit:function (response,postData) {
                            var chapterId = response.responseJSON.chapterId;
                            $.ajaxFileUpload({
                                url:"${path}/chapter/upload",
                                type:"post",
                                datatype:"json",
                                data:{id:chapterId},
                                fileElementId:"oldName",
                                success:function (data) {
                                    $("#"+subgridTableId).trigger("reloadGrid");
                                }
                            })
                            return postData;
                        }
                    },{
                        closeAfterAdd:true,reloadAfterSubmit:true,
                        afterSubmit:function (response,postData) {
                            var chapterId = response.responseJSON.chapterId;
                            $.ajaxFileUpload({
                                url:"${path}/chapter/upload",
                                type:"post",
                                datatype:"json",
                                data:{id:chapterId},
                                fileElementId:"oldName",
                                success:function (data) {
                                    $("#"+subgridTableId).trigger("reloadGrid");
                                }
                            })
                            return postData;
                        }
                    },{

                    });
            }

            function upload(albumId) {
                $.ajaxFileUpload({
                    url:"${path}/album/upload",
                    datatype:"json",
                    type:"post",
                    data:{'id':albumId},
                    // 指定的上传input框的id
                    fileElementId:"cover",
                    success:function (data) {
                        // 输出上传成功
                        // jqgrid重新载入
                        $("#albums").trigger("reloadGrid");
                    }
                })
            }
            $('#albums').jqGrid({
                styleUI:'Bootstrap',
                url:'${path}/album/findAll',
                datatype:'json',
                colNames:["编号","标题","封面","分数","作者","播音员","专辑简介","章节数","状态","发行时间","上传时间"],
                colModel:[
                    {name:'id',align:'center',search:false,hidden:true},
                    {name:'title',align:'center',editable:true},
                    {name:'cover',align:'center',editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"},formatter:function (data) {
                            return "<img style='width: 100%' src='"+data+"'>";
                        }},
                    {name:'score',align:'center',editable:true},
                    {name:'author',align:'center',editable:true},
                    {name:'broadcast',align:'center',editable:true},
                    {name:'description',align:'center',editable:true},
                    {name:'count',align:'center'},
                    {name:'status',align:'center',editable:true,formatter:function (data) {
                            if(data == 1){
                                return "使用";
                            }
                            return "未使用";
                        },edittype:"select",editoptions:{value:"1:展示;2:不展示"}},
                    {name:'publishDate',align:'center',edittype:'date',editable:true},
                    {name:'createDate',align:'center'}
                ],

                pager:"#pager",
                rowNum:5,
                rowList:[5,10,15],
                viewrecords:true,
                mtype : "post",
                caption:'专辑列表',
                editurl:'${path}/album/edit',
                autowidth:true,
                multiselect:true,
                subGrid : true,
                height : '100%',
                autowidth: true,
                subGridRowExpanded : function(subgrid_id, row_id) {
                    subTable(subgrid_id, row_id);
                },
                subGridRowColapsed : function(subgrid_id, row_id) {
                }
            }).navGrid('#pager',
                {edittext:"编辑",addtext:"添加",deltext:"删除"},   //参数2:开启工具栏编辑按钮
                {closeAfterEdit:true,reloadAfterSubmit:true,afterSubmit:function (response,postData) {
                        var albumId = response.responseJSON.albumId;
                        upload(albumId);
                        return postData;
                    }/*beforeShowForm:function (frm) {
                        frm.find("#url").attr("disabled",true);
                    }*/},//编辑面板的配置
                {closeAfterAdd:true,afterSubmit:function (response,postData) {
                        var albumId = response.responseJSON.albumId;
                        upload(albumId);
                        return postData;
                    }},//添加面板的配置
                {},//删除的配置
                {
                    sopt:['eq','ne','cn']
                }
            );
        });

        function playAudio(data) {
            $('.modal-open').find('.modal-backdrop').remove();
            $("#musicDiv").modal("show");
            $("#playAudio").attr("src", "${path}/file/music/" + data);
        }

        function downloadAudio(data) {
            location.href = "${path}/chapter/download?fileName=" + data;
        }
    </script>
        <div class="col-sm-12">
            <table id="albums"></table>
            <div id="pager" style="height:30px">
            </div>
        </div>
        <div class="modal fade" id="musicDiv" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <audio id="playAudio" src="" controls></audio>
            </div>
        </div>


