package cn.com.goldwind.kis.test;

import org.junit.Test;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.mmseg.interfa.MMsegInterface;
import cn.com.goldwind.kis.mmseg.similarity.SimWordInterface;
import cn.com.goldwind.kis.utils.ProtoBuffUtil;

public class MyTest {
	@Test
	public void testSerialize() {
		AccessSummary as = new AccessSummary();
		as.setClassification("分类1");
		as.setTotalAccess(100);
		System.out.println("序列化");
		byte[] data = ProtoBuffUtil.serialize(as, AccessSummary.class);
		for (byte b : data) {
			System.out.print(b);
		}
		System.out.println();
		System.out.println("反序列化");
		AccessSummary as2 = ProtoBuffUtil.deSerialize(data, AccessSummary.class);
		System.out.println(as2);
	}

	@Test
	public void testSet() throws Exception {
		MMsegInterface testex = MMsegInterface.getInstance();
		String[] textList = { "变流器", "变桨系统", "鸡蛋", "金风风电场" };

		for (String text : textList) {
			System.out.println(testex.textWordSegToList(text));
		}
	}

	@Test
	public void newTestSplitWords() {
		SimWordInterface sim_cal = SimWordInterface.getInstance();
		// 生成离线内容
		// sim_cal.getOfflineSimDict("/Users/adolf/eclipse-workspace/KeyInformationSystem/data/text_res.txt",
		// "/Users/adolf/eclipse-workspace/KeyInformationSystem/data/sim_dict.txt");
		System.out.println("中文术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("电缆塔"));
		System.out.println("中文不在术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("幽闭恐惧症"));
		System.out.println("中文不在术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("金风科技"));
		System.out.println(sim_cal.getSimilaryTopWords("机组动力电缆"));
		System.out.println("英文术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("cable marker"));
		System.out.println("英文不在术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("Permanent magnet wind turbine"));

		System.out.println(sim_cal.getSimilaryTopWords("双馈Generation"));
		System.out.println(sim_cal.getSimilaryTopWords("鸡蛋"));

	}
}
