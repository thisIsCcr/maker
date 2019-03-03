$(document).ready(function() {
	$('#table').bootstrapTable({
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
	});

	$("#uploadFile").fileinput({
        language: 'zh',//中文
		maxFileCount:10,//最大同时上传个数
        uploadUrl:"/file/multipartFile",//文件上传地址
		enctype:"multipart/form-data"//编码格式
	});

    var $usager=$("#usager");
	//cpu使用率监控
	/*setInterval(function() {
		$.ajax({
			type: "get",
			url: "/actuator/metrics/system.cpu.usage",
			async: true,
			success: function(result) {
				var usage=parseInt((result.measurements[0].value * 100));
				$("#CPUusager").html(usage+"%");
				$usager.prop("aria-valuenow",usage);
				$usager.css("width",usage+"%")
			}
		});
	}, 1300)*/



})