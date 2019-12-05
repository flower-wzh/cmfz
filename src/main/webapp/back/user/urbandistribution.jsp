<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <script src="${path}/static/echarts/echarts.min.js"></script>
    <script src="${path}/static/echarts/china.js"></script>
    <script>
        var myChart = echarts.init(document.getElementById('userMap'));
    </script>
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例

            var option = {
                title: {
                    text: '用户分布图',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['男', '女']
                },
                visualMap: {
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [

                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            $.get("${pageContext.request.contextPath}/user/urbanDistribution",function (data) {
                myChart.setOption({
                    series:[
                        {
                            name: '男',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data.man
                        },
                        {
                            name: '女',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data.women
                        }
                    ]
                })
            },"json")
            myChart.setOption(option);
        })

        goEasy.subscribe({
            channel: "urband", //替换为您自己的channel
            onMessage: function (message) {
                let data = JSON.parse(message.content);
                console.log(data);
                myChart.setOption({
                    series:[
                        {
                            name: '男',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data.man
                        },
                        {
                            name: '女',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: data.women
                        }
                    ]
                });
            } //替换为您想要发送的消息内容
        });
    </script>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="userMap" style="width: 600px;height:400px;"></div>
