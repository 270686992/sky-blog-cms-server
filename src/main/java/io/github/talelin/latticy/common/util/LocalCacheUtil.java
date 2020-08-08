package io.github.talelin.latticy.common.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.talelin.autoconfigure.exception.FailedException;
import io.github.talelin.latticy.common.constant.CodeMessageConstant;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 本地缓存工具类
 * </p>
 *
 * @author 踏雪彡寻梅
 * @version 1.0
 * @date 2020/8/8 - 17:33
 * @since JDK1.8
 */
public class LocalCacheUtil {

    /**
     * 本地缓存统一前缀
     */
    public static final String CACHE_PREFIX = "local_";

    /**
     * 保存键值对的本地缓存
     * LRU 算法
     */
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                // 默认的数据加载实现,当调用 get 取 Token 值的时候,如果 key 没有对应的值,就调用这个方法进行加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    /**
     * 添加键值对到本地缓存中
     *
     * @param key   添加的键
     * @param value 添加的值
     */
    public static void setLocalCache(String key, String value) {
        localCache.put(key, value);
    }

    /**
     * 获取本地缓存中 key 对应的的值
     *
     * @param key 键
     * @return 返回键 key 对应的值
     */
    public static String getLocalCache(String key) {
        String value = null;

        try {
            value = localCache.get(key);

            if ("null".equals(value)) {
                return null;
            }

            return value;
        } catch (ExecutionException e) {
            throw new FailedException(CodeMessageConstant.SERVER_ERROR);
        }
    }

}