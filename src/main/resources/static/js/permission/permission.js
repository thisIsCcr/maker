$(function () {
    var obutton = new Object();
    var botsTable = $('#rsm-table');
    botsTable.bootstrapTable({
        url: "/user/getAllrmsData",
        cache: false, //禁用缓存
        search: true, //启用搜索
        clickToSelect: true,
        queryParams: function (params) { //请求数据附加参数
            var tmp = {
                fid: 0
            }
            return tmp;
        },
        responseHandler: function (res) {       //加载服务器数据之前的处理程序，可以用来格式化数据。
            return res.data;
        },
        onExpandRow: expandSubist, //展开子table事件
        detailView: true,
        toolbar: '#toolbar',                //工具按钮用哪个容器
        pagination: true,                   //是否显示分页（*）
        striped: true,                       //隔行变色
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        uniqueId: "id",                      //设置主键
        showToggle: true,
        singleSelect: false,
        onPostBody: function () {
            console.log("初始化完成")
        },
        columns: [{
            checkbox: true
        }, {
            field: 'id',
            title: 'id',
            align: "center"
        }, {
            field: 'rmsName',
            title: '名称',
            align: "center"
        }, {
            field: 'rmsIocn',
            title: '图标',
            align: "center"
        }, {
            field: 'rmsUrl',
            title: '拦截路径',
            align: "center"
        }]
    });

    /**
     * 注册加载子表的事件。
     * @param index
     * @param row
     * @param $detail
     */
    function expandSubist(index, row, $detail) {
        var current = $detail.html("<table></table>").find("table");
        current.bootstrapTable({
            url: "/user/getAllrmsData",
            detailView: true,
            responseHandler: function (res) {       //加载服务器数据之前的处理程序，可以用来格式化数据。
                return res.data;
            },
            showHeader: false, //隐藏列头
            onExpandRow: expandSubist,
            queryParams: function (params) {
                var tmp = {
                    fid: row.id
                }
                return tmp;
            },
            columns: [{
                checkbox: true
            }, {
                field: 'id',
                title: 'id',
                align: "center"
            }, {
                field: 'rmsName',
                title: '名称',
                align: "center"
            }, {
                field: 'rmsIocn',
                title: '图标',
                align: "center"
            }, {
                field: 'rmsUrl',
                title: '拦截路径',
                align: "center"
            }]
        })
        //current.find("tr:first").remove();
    }

    /**
     * 添加数据
     */
    obutton.addAuthenaction = function () {
        $.confirm({
            title: "添加权限",
            content: "url:static/EditTemplate",
            buttons: {
                ok: {
                    text: "保存",
                    action: function () {
                        var addform = this.$content.find("#addform").data('bootstrapValidator')
                        addform.validate();
                        if (!addform.isValid()) {
                            return false;
                        }
                        var data = this.$content.find("#addform").serialize();
                        $.post("/user/addPermission", data, function (result) {
                            baseHome.successPrompt(result.msg)
                        }, 'json');
                    }
                },
                cancel: {
                    text: "取消",
                    action: function () {

                    }
                }
            },
            onContentReady: function () {
                var self = this;
                //加载角色
                $.ajax({
                    url: "/user/getAllrole",
                    method: "get",
                    dataType: "json",
                    success: function (result) {
                        if (result.isSuccess) {
                            var DomOption = "";
                            var option = "<option value='{id}'>{roleName}</option>";
                            var rolelis = result.data;
                            for (key in rolelis) {
                                DomOption += option.format(rolelis[key])
                            }
                            self.$content.find("#role").append(DomOption)
                        }
                    }
                })
                //加载目录数据
                $.ajax({
                    url: "/getMenuData",
                    method: "get",
                    dataType: "json",
                    success: function (result) {
                        if (result.isSuccess) {
                            var DomOption = "";
                            var option = "<option value='{0}'>{1}</option>";
                            var menu = result.data;
                            for (key in menu) {
                                DomOption += option.format(menu[key].rmsId, menu[key].sysRms.rmsName)
                            }
                            self.$content.find("#uplevelMenu").append(DomOption)
                        }
                    }
                })
                //初始化图标选择器
                this.$content.find("#rmsIocn").iconpicker({
                    placement: "bottom"
                });
                //初始化bootstrapValidator校验
                this.$content.find("#addform").bootstrapValidator({
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        rmsName: {
                            message: "请输入正确的权限名称",
                            validators: {
                                notEmpty: {
                                    message: "请输入权限名称"
                                },
                                stringLength: {
                                    min: 1,
                                    max: 50,
                                    message: "名称最少输入一个字符并且不超过五十个字符"
                                }
                            }
                        },
                        rmsUrl: {
                            message: "请输入正确的路径",
                            validators: {
                                notEmpty: {
                                    message: "请输入权限名称"
                                }
                            }
                        },
                        rmsIocn: {
                            message: "请输入正确的图标",
                            validators: {
                                notEmpty: {
                                    message: "请输入图标"
                                }
                            }
                        },
                        type: {
                            message: "请选择类型",
                            validators: {
                                notEmpty: {
                                    message: "请选择类型"
                                }
                            }
                        },
                        fRmsId: {
                            message: "请选择父级",
                            validators: {
                                notEmpty: {
                                    message: "请选择父级"
                                }
                            }
                        },
                        roleId: {
                            message: "请选择角色",
                            validators: {
                                notEmpty: {
                                    message: "请选择角色"
                                }
                            }
                        }
                    }
                })
            }
        })
    }

    obutton.removePermission = function () {
        var getSelections = $("table").bootstrapTable("getSelections")
        if (getSelections.length <= 0) {
            baseHome.warningPrompt("请选择需要操作的行");
            return false;
        }
        $.get("/user/delePermission/{0}".format(getSelections[0].id),function(result){
           baseHome.successPrompt(result.msg);
        },"json")
    }


    $("#toolbar_permission>button").click(function () {
        var editContent = $(this).attr("id");
        switch (editContent) {
            case "btn_add":
                obutton.addAuthenaction()
                break;
            case "btn_delete":
                obutton.removePermission()
                break;
            default:
                $.alert({
                    title: "操作",
                    content: editContent
                })
                break;
        }
    })
})