$(function() {
	$.ajax({
		type : "POST",
		url : '/kis/generateAutofillData',
		success : function(data) {
//			console.log(data[0]);
//			console.log(data[1]);
//			console.log(data[2]);
//			console.log(data[3]);
			var data = {
				english : data[0],
				chinese : data[1],
				abbreviationEN : data[2],
				abbreviationCN : data[3]
			};

			typeof $.typeahead === 'function' && $.typeahead({
				input : ".js-typeahead",
				minLength : 1,
				maxItem : 20,
				order : "asc",
				hint : true,
				group : {
					template : "{{group}} beers!"
				},
				maxItemPerGroup : 10,
				backdrop : {
					"background-color" : "#fff"
				},
				href : "/beers/{{group}}/{{display}}/",
				dropdownFilter : "所有类型",
				emptyTemplate : 'No result for "{{query}}"',
				source : {
					"中文" : {
						data : data.chinese
					},
					"中文简称" : {
						data : data.abbreviationCN
					},
					"English" : {
						data : data.english
					},
					"English Abbr" : {
						data : data.abbreviationEN
					}
				},
				callback : {
					onClickAfter : function(node, a, item, event) {
						event.preventDefault();
					}
				},
				debug : false
			});
		},
		error : function(data) {
			console.log("获取generateAutofillData失败！");
		}
	});

});