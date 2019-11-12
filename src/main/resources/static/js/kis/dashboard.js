$(function() {
	// 请求饼图summary
	$.ajax({
		type : "POST",
		url : '/kis/summary',
		success : function(data) {
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
			init_pieChart(keyArray, keyValueMapJsonArray);//初始化饼图
		},
		error : function(data) {
			console.log("饼图初始化失败!");
		}
	});
	
	// 请求accessSummary
	$.ajax({
		type : "POST",
		url : '/kis/accessSummary',
		success : function(data) {
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
			init_funnel(keyArray, keyValueMapJsonArray);//初始化漏斗图
		},
		error : function(data) {
			console.log("漏斗图初始化失败!");
		}
	});
});


/**
 * 初始化饼图
 */
function init_pieChart(keyData, displayData) {
	var pieChart = echarts.init(document.getElementById("echarts-pie-chart"),"macarons");
	var pieoption = {
		title : {
			text : '术语比例',
			subtext : '点击查看各类术语列表',
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{d}%"
		},
		legend : {
			orient : 'vertical',
			x : 'left',
			data : keyData
		},
		calculable : true,
		series : [ {
			name : '比例',
			type : 'pie',
			radius : '45%',
			center : [ '50%', '60%' ],
			data : displayData
		} ]
	};
	pieChart.setOption(pieoption);
	$(window).resize(pieChart.resize);
	pieChart.on('click', function (param) {
		var obj = {value:param.name};
		searchTerminology(obj);
	});
}
	 
	
/**
 * 初始化漏斗图
 * @param keyData
 * @param displayData
 * @returns
 */
function init_funnel(keyData, displayData){
	var funnelChart = echarts.init(document.getElementById("echarts-funnel-chart"),"macarons");
    var funneloption = {
        title : {
            text: '术语点击量',
            subtext: '',
            x : 'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{c}"
        },
        legend: {
        	orient : 'vertical',
			x : 'right',
            data : keyData
        },
        calculable : true,
        series : [
//            {
//                name:'点击量',
//                type:'funnel',
//                width: '20%',
//                data: displayData
//            },
            {
                name:'点击量',
                type:'funnel',
                x : '30%',
                left: '10%',
	            width: '30%',
	            maxSize: '80%',
                sort : 'ascending',
                itemStyle: {
	                normal: {
	                    opacity: 08,           // 系列2图形透明度
	                    borderColor: '#fff',    // 图形边框颜色
	                    borderWidth: 3,          // 图形边框宽度大小
	                    label: {
                            position: 'left'
                        }
	                }
	            },
                data:displayData
            }
        ]
    };

    funnelChart.setOption(funneloption);
    $(window).resize(funnelChart.resize);
}