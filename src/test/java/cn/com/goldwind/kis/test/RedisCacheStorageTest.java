package cn.com.goldwind.kis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.utils.redis.RedisCacheStorage;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheStorageTest {

	@Autowired
	@Qualifier("redisCacheStorage")
	private RedisCacheStorage<String, AccessSummary> redisCacheStorage;

	@Test
	public void testSet() throws Exception {
		System.out.print("开始执行测试");
		AccessSummary as = new AccessSummary();
		as.setClassification("分类1");
		as.setTotalAccess(100);

		redisCacheStorage.set("AccessSummary", as);
		AccessSummary as2 = (AccessSummary) redisCacheStorage.get("Akey9", new AccessSummary());

		System.out.println("=======" + as2.getClassification() + "=====" + as2.getTotalAccess());
	}
	
}