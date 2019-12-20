$(function() {
	noneSearchPage();
	$("#sel_exportoption").change(function () {
		console.log($(this).val());
        //刷新参数设置
        $('#noneSearchTable').bootstrapTable('refreshOptions', {
            exportDataType: $(this).val()
        });
    });
})

function noneSearchPage() {
	var t = $("#noneSearchTable");
	t.bootstrapTable('destroy');
	// 获取搜索关键词
	//var findContentVal = $("#findContent").val();
	var t = t.bootstrapTable({
		url : '/console/searchLogList',
		method : 'get',
		dataType : "json",
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		undefinedText : "空",// 当数据为undefined时显示的字符
		showRefresh:true,//显示刷新按钮
        showToggle: true,//是否显示切换试图（table/card）按钮
        showColumns: true,//是否显示内容列下拉框
        minimumCountColumns: 2,//最少允许的列数
        showExport:true,
        exportDataType:'basic',
        exportTypes:['json', 'xml', 'csv', 'txt', 'excel'],  //导出文件类型
        Icons:'glyphicon-export',
		pagination : true, // 启用分页
		pageNumber : 1,// 分页首页页码
		pageSize : 10,// 分页页面数据条数
		pageList : [ 10, 20, 30, 50, 100 ],// 设置可供选择的页面数据条数；设置为All则显示所有记录
		paginationFirstText : "首页",// 指定“首页”按钮的图标或文字
		paginationPreText : '上一页',// 指定“上一页”按钮的图标或文字
		paginationNextText : '下一页',// 指定“下一页”按钮的图标或文字
		paginationLastText : "末页",// 指定“末页”按钮的图标或文字
		paginationLoop:false,//不循环
		data_local : "zh-US",// 表格汉化
		sidePagination : "server", // 服务端处理分页
		queryParamsType : '',// 默认值为'limit',在默认情况下传给服务端的参数为：offset,limit,sort;设置为'',在这种情况下传给服务器的参数为：pageSize,pageNumber
//		queryParams : function(params) {
//			// 这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
//			var temp = {
//				pageNumber : params.pageNumber,// 当前页(开始页)
//				pageSize : params.pageSize,// 每页的数量
//				findContent : findContentVal
//			};
//			return temp;
//		},
		idField : "id",// 指定主键列
		columns : [  {
            checkbox:true,
        },{
            visible:false,
            field: 'id',//json数据中rows数组中的属性名
            align: 'center'//水平居中
        },{
			title : '查询内容',
			field : 'findContent',
			align : 'left',
			valign : 'middle'
		}, {
			title : '查询次数',
			field : 'searchCounts',
			align : 'left',
			valign : 'middle'
		},
		{
            title: '操作',
            align: 'center',
            width:300,
            clickToSelect:false,
            formatter: function (value, row, index) {//自定义显示，也可以写标签
            	var add2db=
            		'<shiro:hasPermission name="user/deactive">'+
					'<a class="btn btn-outline btn-info btn-sm" th:id="'+row.id+'" onclick="add2db()"><span class="fa fa-save"></span> 添加到术语库</a>'+
					'</shiro:hasPermission>';
                return add2db;
            }
		}]
	});
	t.on('load-success.bs.table', function(data) {// table加载成功后的监听函数
		console.log("load console success");
		$(".pull-right").css("display", "block");
	});
}
