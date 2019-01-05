(function($, window, document, undefined) {
	'user strict';

	//使用标签
	var pluginName = 'tabs';

	/**
	 * @options 获取配置参数
	 */
	$.fn[pluginName] = function(options) {
		var self = $(this);
		if(this == null) {
			return null;
		}
		var data = this.data(pluginName);
		if(!data) {
			data = new BaseTab(this, options);
		}
		return data;
	};

	//构造对象
	var BaseTab = function(element, options) {
		this.$element = $(element);
		this.options = $.extend(true, {}, this.default, options);
		this.init();
	}

	//默认配置
	BaseTab.prototype.default = {
		showIndex: 0, // 默认显示页索引
		loadAll: true //true 一次全部加载页面， false 只在家showIndex指定的页面，其他点击加载，提高响应速度
	}

	BaseTab.prototype.template = {
		ul_nav: '<ul  class="nav nav-tabs"></ul>',
		ul_li: '<li><a href="#{0}" data-toggle="tab"><span>{1}</span></a></li>',
		ul_li_close: '<i class="fa fa-remove closeable" title="关闭"></i>',
		div_content: '<div  class="tab-content"></div>',
		div_content_panel: '<div class="tab-pane fade" id="{0}"></div>'
	};

	BaseTab.prototype.init = function() {
		if(!this.options.data || this.options.data.length == 0) {
			console.error("请指定tab页数据")
			return;
		}
		//当前显示的页面是否超出索引
		if(this.options.showIndex < 0 || this.options.showIndex > this.options.data.length - 1) {
			console.err("showIndex超出了范围")
			//指定为默认值
			this.options.showIndex = this.default.showIndex;
		}
		//清除原来的tab页
		this.$element.html("");
		this.builder(this.options.data);
	};

	//使用模板搭建页面结构
	BaseTab.prototype.builder = function(data) {
		var ul_nav = $(this.template.ul_nav);
		var div_content = $(this.template.div_content);

		for(var i = 0; i < data.length; i++) {
			var ul_li = $(this.template.ul_li.format(data[i].id, data[i].text))
			//如果可关闭，插入关闭图标，并绑定关闭事件
			if(data[i].closeable) {
				var ul_li_close = $(this.template.ul_li_close)

				ul_li.find("a").append(ul_li_close);
				ul_li.find("a").append("&nbsp;");
			}
			ul_nav.append(ul_li);

			//div-content
			var div_contetn_panel = $(this.template.div_content_panel.format(data[i].id));

			div_content.append(div_contetn_panel);
		}
		this.$element.append(ul_nav);
		this.$element.append(div_content);
		this.loadData();
		this.$element.find(".nav-tabs li:eq(" + this.options.showIndex + ") a").tab("show");

	}

	BaseTab.prototype.loadData = function() {
		var self = this;

		//tab点击即加载事件
		//设置一个值，记录每个tab是否加载过
		this.stateObj = {};
		var data = this.options.data;
		//如果是当前页面或者配置了一次性全部加载，否则点击tab页是加载
		for(var i = 0; i < data.length; i++) {
			if(this.options.loadAll || this.options.showIndex == i) {
				if(data[i].url) {
					$("#" + data[i]).load(data[i].url);
					this.stateObj[data[i].id] = true;
				} else {
					console.error("id=" + data[i].id + "的tab页未指定url");
					this.stateObj[data[i].id] = false;
				}
			} else {
				this.stateObj[data[i].id] = false;
				(function(id, url) {
					self.$element.find(".nav-tabs a[hre='#" + id + "']").on('show.bs.tab', function() {
						if(!self.stateObj[id]) {
							$("#" + id).load(url);
						}
					});
				}(data[i].id, data[i].url))
			}
		}
		//关闭tab事件
		this.$element.find(".nav-tabs li a i.closeable").each(function(index, item) {
			$(item).click(function() {
				var href = $(this).parents("a").attr("href").substr(1);
				$(this).parents("li"), remove();
				$("#" + href).parent().remove();
			})
		});
	}
	
	//在字符串对象下加入format函数
	String.prototype.format = function () {
        if (arguments.length == 0) return this;
        for (var s = this, i = 0; i < arguments.length; i++)
            s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
        return s;
    };

})(jQuery, window, document)