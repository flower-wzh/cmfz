<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <script src="${path}/static/echarts/echarts.min.js"></script>
    <script src="${path}/static/echarts/china.js"></script>
<script>
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
</script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1天","7天","30天","1年"]
        },
        yAxis: {},
        series: [],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    // Ajax异步数据回显
    $.get("${pageContext.request.contextPath}/user/registDistribution",function (data) {
        myChart.setOption({
            series:[
                {
                    name: '男',
                    type: 'bar',
                    data: data.man,
                },{
                    name: '女',
                    type: 'bar',
                    data: data.woman,
                }
            ]
        })
    },"json")
</script>
<script>
    goEasy.subscribe({
        channel: "regist", //替换为您自己的channel
        onMessage: function (message) {
            // 手动将 字符串类型转换为 Json类型
            let data = JSON.parse(message.content);
            console.log(data);
            myChart.setOption({
                series:[
                    {
                        name: '男',
                        type: 'bar',
                        data: data.man,
                    },{
                        name: '女',
                        type: 'bar',
                        data: data.woman,
                    }
                ]
            })
        }
    });
</script>