package cn.com.goldwind.kis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.entity.KeyInfoNonSearch;
import cn.com.goldwind.kis.entity.WebResponse;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.service.NonSearchService;
import cn.com.goldwind.kis.service.WebResponseService;

@Controller
@RequestMapping("/console")
public class NonSearchController {
	@Autowired
	NonSearchService nonSearchService;
	
	@Autowired
	WebResponseService webResponseService;

	/**
	 * noneSearchIndex初始页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "noneSearchIndex")
	public ModelAndView noneSearchIndex(ModelMap map) {
		return new ModelAndView("console/noneSearch");
	}

	/**
	 * webResponseIndex初始页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "webResponseIndex")
	public ModelAndView webResponseIndex(ModelMap map) {
		return new ModelAndView("console/webResponse");
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "noneSearchList")
	@ResponseBody
	public TableSplitResult<KeyInfoNonSearch> noneSearchList(ModelMap map, String findContent, Integer pageSize, Integer pageNumber) {
		map.put("findContent", findContent);
		TableSplitResult<KeyInfoNonSearch> page = nonSearchService.findNoneSearchListPage(map, pageNumber, pageSize);
		return page;
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "webResponseList")
	@ResponseBody
	public TableSplitResult<WebResponse> webResponseList(ModelMap map, String findContent, Integer pageSize, Integer pageNumber) {
		map.put("findContent", findContent);
		TableSplitResult<WebResponse> page = webResponseService.findWebResponseListPage(map, pageNumber, pageSize);
		return page;
	}

}
