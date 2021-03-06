$(document).ready(function () {
    /*$('#table').bootstrapTable({
        url: "/getAllUser",
        cache: false,
        search: true,
        columns: [{
            field: 'id',
            title: 'id',
            align: "center"
        }, {
            field: 'usrName',
            title: '用户名',
            align: "center"
        }, {
            field: 'usrAccount',
            title: '昵称',
            align: "center"
        }, {
            field: 'usrEmail',
            title: '邮箱',
            align: "center"
        }, {
            field: 'usrAge',
            title: '年龄',
            align: "center"
        }, {
            field: 'usrSex',
            title: '性别',
            formatter: function(value) {
                if(value == 1) {
                    return "男";
                }
                return "女";
            }
        }, {
            field: 'isFreeze',
            title: '是否禁用',
            formatter: function(value) {
                if(value != 1) {
                    return "正常";
                }
                return "禁用";
            }
        }, {
            field: 'updateDate',
            title: '更新时间',
            align: "center"
        }, {
            field: 'cartData',
            title: '创建时间',
            align: "center"
        }]
    });*/

    $("#uploadFile").fileinput({
        language: 'zh',//中文
        maxFileCount: 10,//最大同时上传个数
        uploadUrl: "/file/multipartFile",//文件上传地址
        enctype: "multipart/form-data"//编码格式
        //uploadExtraData:{}//添加额外数据
    });


    $("#submit_url").on("click", function () {
        var value = $("#setting_static_loadURL").val();
        console.log("settingValu:" + value)
        baseHome.settingStaticLoadURL = value;
        console.log(baseHome.staticImgURL())
    })

    var ctx = document.getElementById('Chart').getContext('2d');

    var chart = new Chart(ctx, {
        type: 'line',
        data: {
            datasets: [{
                label: 'CPU使用率',
                borderColor: 'rgb(54, 162, 235)',
                backgroundColor: 'rgba(54, 162, 235, 0.5)',
                fill: false,
                cubicInterpolationMode: 'monotone',
            }]
        },
        options: {
            scales: {
                xAxes: [{
                    type: 'realtime',
                    realtime: {
                        delay: 2000,
                        duration: 20000,
                        refresh: 1000,
                        onRefresh: function (chart) {
                            $.get("/actuator/metrics/system.cpu.usage",function(result){
                                var usage=parseInt((result.measurements[0].value * 100));
                                chart.data.datasets.forEach(function (dataset) {
                                    dataset.data.push({
                                        x: Date.now(),
                                        y: usage
                                    });
                                });
                            })

                        }
                    }
                }]
            }
        }
    });

})
