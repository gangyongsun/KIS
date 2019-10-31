package cn.com.goldwind.kis.shiro.cache.impl;

import org.apache.shiro.cache.Cache;

import cn.com.goldwind.kis.shiro.cache.JedisManager;
import cn.com.goldwind.kis.shiro.cache.JedisShiroCache;
import cn.com.goldwind.kis.shiro.cache.ShiroCacheManager;
import lombok.Data;

/**
 * JRedis管理
 * 
 * @author alvin
 *
 */
@Data
public class JedisShiroCacheManager implements ShiroCacheManager {
    private JedisManager jedisManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getJedisManager());
    }

    @Override
    public void destroy() {
    	//如果和其他系统，或者应用在一起就不能关闭
    	//getJedisManager().getJedis().shutdown();
    }
}
