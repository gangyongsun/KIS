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
	var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
	var pieoption = {
		title : {
			text : '',
			subtext : '术语比例',
			x : 'center'
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
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
			radius : '55%',
			center : [ '50%', '60%' ],
			data : displayData
		} ]
	};
	pieChart.setOption(pieoption);
	$(window).resize(pieChart.resize);
}

/**
 * 初始化漏斗图
 * @param keyData
 * @param displayData
 * @returns
 */
function init_funnel(keyData, displayData){
	var funnelChart = echarts.init(document.getElementById("echarts-funnel-chart"));
    var funneloption = {
        title : {
            text: '',
            subtext: '各类术语点击量'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{b} : {c}"
        },
        legend: {
            data : keyData
        },
        calculable : true,
        series : [
            {
                name:'点击量',
                type:'funnel',
                width: '40%',
                data: displayData
            },
            {
                name:'点击量',
                type:'funnel',
                x : '50%',
                sort : 'ascending',
                itemStyle: {
                    normal: {
                        // color: 各异,
                        label: {
                            position: 'right'
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