package cn.com.goldwind.kis.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.bo.DailyAccessBo;
import cn.com.goldwind.kis.entity.WebAccessLog;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;

public interface WebAccessLogService {
	public TableSplitResult<WebAccessLog> findPage(ModelMap map, Integer pageNumber, Integer pageSize);

	public int insert(WebAccessLog webAccessLog);

	public int getTotalAccessCount();

	public int getTotalAccessEmp();

	public List<DailyAccessBo> getRecent30Tendency(int i);

	public List<DailyAccessBo> getCurrentSeasionTendency();

	public List<DailyAccessBo> getLastSeasionTendency();

	public List<DailyAccessBo> getCurrentYearTendency();

	public List<DailyAccessBo> getLastYearTendency();

	public List<DailyAccessBo> getTendency();

}
