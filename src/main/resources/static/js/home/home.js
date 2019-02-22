$(document).ready(function () {
    //当前ip(⊙o⊙)…
    homeContext = new Object();

    homeContext.hostname = window.location.hostname;
    $("#tabContainer").tabs({
        data: [{
            id: 'index',
            text: '首页',
            url: "index"
        }],
        showIndex: 0,
        loadAll: true
    });

    tabContext = $("#tabContainer").data("tabs");


    //初始化侧边栏
    var $menu = $("#my-menu").mmenu({
        /*"extensions":[
            "position-bottom"
        ],
        autoHeight:true*/
        sidebar: {
            collapsed: "(min-width:200px)",
            expanded: "(min-width:100px)"
        },
        "iconbar": {
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
        }
    }, {
        offCanvas: {
            pageNodetype: "section"
        }
    })

    //设置展开动画
    $icon = $("#my-icon");
    menuContext = $menu.data("mmenu");
    //打开关闭
    $icon.on("click", function () {
        if ($icon.hasClass("is-active")) {
            menuContext.close();
        } else {
            menuContext.open()
        }
    });

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
                        console.log(content)
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
        if(url=="/"){
            $.alert({
                title:"警告",
                content:"为指定路径∑"
            })
            return false;
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
            $icon.addClass("is-active");
        }, 100);
    });
    /**
     * 关闭侧边栏
     */
    menuContext.bind("close:finish", function () {
        setTimeout(function () {
            $icon.removeClass("is-active");
        }, 100);
    });

    /**
     *
     */
        //单击次数
    var click_cnt = 0;
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
    };
})


$(window).scroll(function () {
    if ($(".navbar").offset().top > 50) {
        $(".navbar-fixed-top").addClass("top-nav");
    } else {
        $(".navbar-fixed-top").removeClass("top-nav");
    }
})