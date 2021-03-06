$(document).ready(function () {

    baseHome = new Object();

    baseHome = {
        hostname: window.location.hostname,
        staticResourcePort: 8090,
        settingStaticLoadURL:"undefined",
        staticImgURLstaticImgURL: function () {
            if(this.settingStaticLoadURL!="undefined"){
                return this.settingStaticLoadURL;
            }
            if (this.hostname == "localhost" || this.hostname == "192.168.0.196") {
                return "http://192.168.0.112"
            }
            return "";
        },
        musicData:[],
        iconSwitch: $("#my-icon"),
        aValidator:function (callback) {
                if (typeof callback != "function") {
                    console.error("参数错误")
                    return;
                }
                $.ajax({
                    url: "/getAuthenicatedResult",
                    method: "get",
                    dataType: "json",
                    async: true,
                    success: callback
                })
            },
        logout: function () {
            var self = this;
            $.ajax({
                url: "/user/logout",
                method: "get",
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result.isSuccess) {
                        self.successPrompt("退出登录")
                        logoutSetting()
                    }
                }
            })
        },
        getCurrentUseInfo: function (callback) {
            $.ajax({
                url: "/getPrincipal",
                method: "get",
                dataType: "json",
                async: true,
                success: callback
            })
        },
        promptTitle: "信息",
        successPrompt:function () {
                if (arguments.length == 0) {
                    console.error("Error! params not norm")
                    return;
                }
                toastr.success(arguments[0], this.promptTitle)
            },
        errorPrompt: function () {
            if (arguments.length == 0) {
                console.error("Error! params not norm")
                return;
            }
            toastr.error(arguments[0], this.promptTitle)
        } ,
        warningPrompt: function () {
            if (arguments.length == 0) {
                console.error("Error! params not norm")
                return;
            }
            toastr.warning(arguments[0], this.promptTitle)
        }
        ,
        autoCloseTime: 4000
    }

    var registerWebSocketEvent = function () {
        socket.onmessage = function (event) {
            console.log(typeof event.data)
            if (typeof event.data == "string") {
                baseHome.successPrompt(event.data)
            }
            console.log(event.data)
        }
        socket.onopen = function (event) {
            console.log(socket.extensions)
            console.log("打开通道")
            console.log(baseHome.staticImgURL())

        }
        socket.onclose = function (event) {
            console.log("关闭通道")
        }
        socket.onerror = function (event) {
            console.log("产生错误")
        }

    }


    $.get("/fileAudio/getAllMusicInfo",function(result){
        if(result.isSuccess){
            var musicData=result.data
            for (key in musicData){
                baseHome.musicData.push({
                    name: musicData[key].audioName,
                    artist: musicData[key].audioArtist,
                    url: baseHome.staticImgURLstaticImgURL()+musicData[key].audioPath,
                    cover: baseHome.staticImgURLstaticImgURL()+musicData[key].coverPath,
                    lrc: baseHome.staticImgURLstaticImgURL()+musicData[key].lrcPath
                })
            }
            const ap = new APlayer({
                container: document.getElementById('aplayer'),
                fixed: true,
                lrcType: 3,
                audio:baseHome.musicData
            });
        }else{
            baseHome.errorPrompt("音乐初始化失败")
        }
    },"json")


   /* [{
        name: 'Light',
        artist: 'San Holo',
        url: 'http://192.168.0.112:8090/home/resource/systemResource/audio/San%20Holo%20-%20Light.mp3',
        cover: 'http://192.168.0.112:8090/home/resource/systemResource/img/San%20Holo%20-%20Light.jpg',
        lrc: "http://192.168.0.112:8090/home/resource/systemResource/lrc/San%20Holo%20-%20Light.lrc"
    }]*/


    //消息设置
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": true,
        "positionClass": "toast-bottom-right",
        "preventDuplicates": false,
        "showDuration": baseHome.autoCloseTime,
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }

    /**
     * 页面初始化
     */
    $("#tabContainer").tabs({
        data: [{
            id: '11',
            text: '首页',
            url: "index".format()
        }],
        showIndex: 0,
        loadAll: true
    });

    $(document).ajaxError(function (evt, request, settings) {
        var content = request.responseText;
        var errorMsg = $(content).find("span").text();
        toastr.error(errorMsg, "请求错误")
    });

    tabContext = $("#tabContainer").data("tabs");

    var menuConfig={
        wrappers: ["bootstrap3"],
        extensions:[
            "fx-menu-fade",
            "fx-listitems-slide",
            /*"fullscreen"*/
        ]
        /*sidebar: {
            collapsed: "(min-width:200px)",
            expanded: "(min-width:100px)"
        },*/
        /*"iconbar": {
            "add": true,
            "top": [
                "<a href='#/'><i class='glyphicon glyphicon-globe'></i></a>",
                "<a href='#/'><i class='glyphicon glyphicon-wrench'></i></a>"
            ],
            "bottom": [
                "<a href='#/'><i class='glyphicon glyphicon-tasks'></i></a>",
                "<a href='#/'><i class='glyphicon glyphicon-filter'></i></a>",
                "<a href='#/'><i class='glyphicon glyphicon-briefcase'></i></a>"
            ]
        },*/
       /* offCanvas: {
            pageNodetype: "section"
        }*/
    };

    /**
     * 初始化侧边栏
     * @type {*|jQuery}
     */
    var $menu = $("#my-menu").mmenu(menuConfig)



    /**
     * 是否登录验证
     */
    baseHome.aValidator(function (result) {
        var self = this;
        if (result.data) {
            baseHome.getCurrentUseInfo(function (userinfo) {
                loginSuccessSetting(userinfo.data)
            })
        }
    })

    /**
     * 登录后的操作
     */
    var loginSuccessSetting = function () {
        if (arguments.length == 0) {
            return;
        }
        var data = arguments[0];
        //重新登录
        if (typeof socket == "object") {
            socket.close();
        }
        socket = new WebSocket("ws://{0}:10010/websocket?sessionId={1}".format(baseHome.hostname, data));
        registerWebSocketEvent();
        $("#login").parent().stop().animate({top: -52, opacity: 0}, 1000, 'easeOutQuad').hide("1000")
        $("#login_success_userInfo").stop().show().animate({
            opacity: 1,
            top: -57
        }, 1000, 'easeOutQuad');
    }

    /**
     * 退出登录
     */
    var logoutSetting = function () {
        $("#login").parent().stop().show().animate({top: 1, opacity: 1}, 1000, 'easeOutQuad')
        $("#login_success_userInfo").stop().animate({
            opacity: 0,
            top: 40
        }, 1000, 'easeOutQuad').hide("1000");

    };

    /**
     * 打开登陆页面
     */
    $("#login").on("click", function () {
        var self = this;
        $.confirm({
            title: "暗号内容",
            content: "url:/static/LoginTemplate",
            //theme: 'supervan',
            draggable: false,
            buttons: {
                ok: {
                    text: "登录",
                    action: function () {
                        var form = this.$content.find("#home_login");
                        var validatorContext = form.data("bootstrapValidator")
                        validatorContext.validate();
                        //验证未通过
                        if (!validatorContext.isValid()) {
                            return false;
                        }
                        var loginValidator = false;
                        $.ajax({
                            url: "/user/login",
                            async: false,
                            method: "post",
                            data: form.serialize(),
                            dataType: "json",
                            success: function (result) {
                                if (result.isSuccess) {
                                    loginSuccessSetting(result.data)
                                    loginValidator = true;
                                }
                            }
                        })
                        if (!loginValidator) {
                            console.log("登录失败")
                            return false;
                        }
                    }
                },
                cancel: {
                    text: "取消",
                    action: function () {

                    }
                }
            },
            onContentReady: function () {
                this.$content.find("#home_login").bootstrapValidator({
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        userName: {
                            message: "请输入正确的用户名",
                            validators: {
                                notEmpty: "请输入用户名",
                                stringLength: {
                                    min: 1,
                                    max: 8,
                                    message: "用户名最小长度为1，最大长度为8"
                                }
                            }
                        },
                        userPassword: {
                            message: "请输入正确的密码",
                            validators: {
                                notEmpty: "请输入密码",
                                stringLength: {
                                    min: 1,
                                    max: 8,
                                    message: "密码名最小长度为1，最大长度为8"
                                }
                            }
                        }
                    }
                })
            }
        })
    });

    menuContext = $menu.data("mmenu");
    //打开关闭
    baseHome.iconSwitch.on("click", function () {
        if ($(this).hasClass("is-active")) {
            menuContext.close();
        } else {
            menuContext.open()
        }
    });

    /**
     * 加载可用列表
     */
    setTimeout(function () {
        $.ajax({
            url: "/getMenuData",
            type: "get",
            dataType: "json",
            success: function (result) {
                if (result.isSuccess) {
                    var data = result.data;
                    var structure = "<li id='menu-No{0}'><a href='{1}' data-skip='{2}' id='menu-href{3}'>{4}</a></li>";
                    var fUl = "<ul id='{0}'>{1}</ul>";
                    for (var key in data) {
                        var content = structure.format(data[key].rmsId,
                            "javaScript:void(0)",
                            data[key].sysRms.rmsUrl,
                            data[key].rmsId,
                            data[key].sysRms.rmsName,
                            data[key].sysRms.rmsUrl);
                        if (data[key].fRmsId == 0) {
                            $("#menu-No" + data[key].fRmsId).find(".mm-listview").append(content);
                        } else {
                            if ($("#menu-son{0}".format(data[key].fRmsId))[0]) {
                                $("#menu-son" + data[key].fRmsId).append(content);
                            } else {
                                content = fUl.format("menu-son" + data[key].fRmsId, content);
                                $("#menu-No" + data[key].fRmsId).append(content);
                            }

                        }
                    }
                    menuContext.initPanels();
                }
            }
        })
    }, 200)

    /**
     * 添加单击事件，展开页面
     */
    $(document).on("click", 'li[id^="menu-No"] a[id^="menu-href"]', function () {
        var url = $(this).attr("data-skip");
        var title = $(this).text();
        var id = $(this).attr("id").replace("menu-href", "");
        if (url == "/") {
            baseHome.warningPrompt("未指定路径∑");
            return;
        }
        tabContext.addTab({
            id: id,
            text: title,
            closeable: true,
            url: url
        });
    });

    /**
     * 打开侧边栏
     */
    menuContext.bind("open:finish", function () {
        setTimeout(function () {
            baseHome.iconSwitch.addClass("is-active");
        }, 100);
    });

    /**
     * 关闭侧边栏
     */
    menuContext.bind("close:finish", function () {
        setTimeout(function () {
            baseHome.iconSwitch.removeClass("is-active");
        }, 100);
    });

    /**
     *  单击浮动效果
     */
   /* var click_cnt = 0;
    //获取HTML
    var $html = document.getElementsByTagName("html")[0];
    //获取BODY
    var $body = document.getElementsByTagName("body")[0];
    $html.onclick = function (e) {
        //创建动画元素
        var $elem = document.createElement("i");
        $elem.style.color = "#ff6384";
        $elem.style.zIndex = 9999;
        //$elem.className="fab fa-alipay"
        $elem.style.position = "absolute";
        $elem.style.select = "none";
        $elem.style.userSelect = "none";
        var x = e.pageX;
        var y = e.pageY;
        $elem.style.left = (x - 10) + "px";
        $elem.style.top = (y - 20) + "px";
        clearInterval(anim);
        switch (++click_cnt) {
            case 10:
                $elem.innerText = "OωO";
                break;
            case 20:
                $elem.innerText = "(๑•́ ∀ •̀๑)";
                break;
            case 30:
                $elem.innerText = "(๑•́ ₃ •̀๑)";
                break;
            case 40:
                $elem.innerText = "(๑•̀_•́๑)";
                break;
            case 50:
                $elem.innerText = "（￣へ￣）";
                break;
            case 60:
                $elem.innerText = "(╯°口°)╯(┴—┴";
                break;
            case 70:
                $elem.innerText = "૮( ᵒ̌皿ᵒ̌ )ა";
                break;
            case 80:
                $elem.innerText = "╮(｡>口<｡)╭";
                break;
            case 90:
                $elem.innerText = "( ง ᵒ̌皿ᵒ̌)ง⁼³₌₃";
                break;
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
                $elem.innerText = "(ꐦ°᷄д°᷅)";
                //重置，循环
                click_cnt = 0;
                break;
            default:
                $elem.innerText = "❤";
                break;
        }
        //随机大小
        $elem.style.fontSize = Math.random() * 10 + 8 + "px";
        var increase = 0;
        var anim;
        setTimeout(function () {
            //往上漂浮
            anim = setInterval(function () {
                if (++increase == 150) {
                    clearInterval(anim);
                    $body.removeChild($elem);
                }
                $elem.style.top = y - 20 - increase + "px";
                $elem.style.opacity = (150 - increase) / 120;
            }, 8);
        }, 70);
        $body.appendChild($elem);
    };*/
})

function handler(e) {
    //doSomething(); // do something here
}

document.addEventListener('mousewheel', handler, {passive: true});


$(window).scroll(function () {
    if ($(".navbar").offset().top > 50) {
        $(".navbar-fixed-top").addClass("top-nav");
    } else {
        $(".navbar-fixed-top").removeClass("top-nav");
    }
})