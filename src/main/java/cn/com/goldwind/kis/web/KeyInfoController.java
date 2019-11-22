package cn.com.goldwind.kis.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.entity.KeyInfoNonSearch;
import cn.com.goldwind.kis.entity.WebResponse;
import cn.com.goldwind.kis.mmseg.interfa.MMsegInterface;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.service.KeyInfoService;
import cn.com.goldwind.kis.service.NonSearchService;
import cn.com.goldwind.kis.service.WebResponseService;
import cn.com.goldwind.kis.shiro.controller.BaseController;

@Controller
@RequestMapping("/kis")
public class KeyInfoController extends BaseController {
	@Autowired
	KeyInfoService keyInfoService;

	@Autowired
	NonSearchService nonSearchService;
	
	@Autowired
	WebResponseService webResponseService;

	/**
	 * 初始页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(ModelMap map, HttpServletRequest request) {
		// 从HTTP头获取empNO
		String empNO = request.getHeader("empNO");
//		System.out.println("从HTTP头获取empNO:"+empNO);

		HttpSession session = request.getSession();

		if (null != empNO && !"".equals(empNO)) {
			// 将HTTP头中的empNO设置到Session
			Object obj = session.getAttribute("empNO");
			if (null != obj) {
//				System.out.println("session中已经存在empNO："+obj.toString());
			} else {
				session.setAttribute("empNO", empNO);
//				System.out.println("设置empNO到session："+session.getAttribute("empNO"));
			}
		} else {
			// 这段代码测试用，之后要去掉的
			session.setAttribute("empNO", "50508");
//			System.out.println("设置默认empNO到session："+session.getAttribute("empNO"));
		}
//		System.out.println("sessionID:" + session.getId());

		// 热词展示
		List<String> hotKeyInfoList = keyInfoService.findHotTerms(8);
		if (null != hotKeyInfoList && hotKeyInfoList.size() > 0) {
			map.put("hotKeyInfoList", hotKeyInfoList);
		}
		return new ModelAndView("search");
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public TableSplitResult<KeyInfo> search(ModelMap map, String termType, String findContent, Integer pageSize, Integer pageNumber, HttpServletRequest request) {
		// System.out.println(request.getSession().getAttribute("empNO"));
		TableSplitResult<KeyInfo> page = null;
		if (null != findContent && !"".equals(findContent)||null != termType && !"".equals(termType)) {
			//首先根据关键词或类型进行精确查询
			map.put("findContent", findContent.trim());
			map.put("termType", termType);
			page = keyInfoService.findPagedTermByKeyInfo(map, pageNumber, pageSize);
			
			//如果精确匹配查不到，则分词查询
			if (null == page || page.getTotal() == 0) {
				//分词
				MMsegInterface mmInterface = MMsegInterface.getInstance();
				List<String> findContentList=mmInterface.textWordSegToList(findContent.trim());
				findContentList.removeAll(Collections.singleton(null));
//				System.out.println(findContentList);
				map.put("findContentList", findContentList);
				map.remove("findContent");
				map.remove("termType");
				page = keyInfoService.findPagedTermByKeyInfo(map, pageNumber, pageSize);
			}
			//如果还是查不到则做记录
			if (null == page || page.getTotal() == 0) {
				// 根据内容查询
				KeyInfoNonSearch keyInfoNonSearch = new KeyInfoNonSearch();
				keyInfoNonSearch.setSearchContent(findContent);
				KeyInfoNonSearch result = nonSearchService.findBySearchContent(keyInfoNonSearch);
				if (null == result) {
					// 查不到，则添加
					keyInfoNonSearch.setSearchNumber(1);
					nonSearchService.insert(keyInfoNonSearch);
				} else {
					// 查得到，则查询次数加1
					result.setSearchNumber(result.getSearchNumber() + 1);
					nonSearchService.update(result);
				}
			}
		}

		return page;
	}

	/**
	 * 查询术语详情
	 * 
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "showDetail")
	public ModelAndView showDetail(ModelMap map, Integer id) {
		KeyInfo keyInfo = keyInfoService.findTermById(id);
		if (null != keyInfo) {
			map.put("keyInfo", keyInfo);
			keyInfo.setTotalClick(keyInfo.getTotalClick() + 1);
			keyInfoService.updateTerm(keyInfo);
		}
		return new ModelAndView("detail");
	}


	/**
	 * 查询术语类型对应的数量
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "summary")
	@ResponseBody
	public Map<String, Integer> summary() {
		// 查询所有术语类型
		List<String> keyInfoTypeList = keyInfoService.findTermTypes();

		Map<String, Integer> termTypeNumMap = new HashMap<String, Integer>();

		if (null != keyInfoTypeList && keyInfoTypeList.size() > 0) {
			for (String keyInfoType : keyInfoTypeList) {
				int num = keyInfoService.getNumByType(keyInfoType);
				termTypeNumMap.put(keyInfoType, num);
			}
		}
		return termTypeNumMap;
	}

	/**
	 * 查询各术语类型对应的点击量
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "accessSummary")
	@ResponseBody
	public Map<String, Integer> accessSummary() {
		List<AccessSummary> accessSummaryList = keyInfoService.findAccessSummary();
		Map<String, Integer> accessSummaryMap = new HashMap<String, Integer>();

		for (AccessSummary accessSummary : accessSummaryList) {
			accessSummaryMap.put(accessSummary.getClassification(), accessSummary.getTotalAccess());
		}
		return accessSummaryMap;
	}

	/**
	 * 查询更多热词
	 * 
	 * @param number
	 * @return
	 */
	@RequestMapping(value = "showMoreHotTerms")
	public ModelAndView showMoreHotTerms(ModelMap map, Integer number) {
		List<String> hotKeyInfoList = keyInfoService.findHotTerms(number);
		if (null != hotKeyInfoList && hotKeyInfoList.size() > 0) {
			map.put("hotKeyInfoList", hotKeyInfoList);
		}
		return new ModelAndView("hotTerms");
	}

	
	/**
	 * 收集用户反馈信息
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "webresponseLog")
	@ResponseBody
	public Map<String, Object> webresponseLog(String content) {
		WebResponse webResponse=new WebResponse();
		webResponse.setContent(content);
		webResponseService.saveWebresponseLog(webResponse);
		resultMap.put("status", 200);
		resultMap.put("message", "感谢您宝贵的建议！");
		return resultMap;
	}

}
