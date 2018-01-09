package com.dev.rxnetmodule.util;

/**
 * author: 王可可
 * Created on 2017/12/20.
 * description: 缓存模式类
 *
 * NOCACHE，不使用缓存，强制网络,
 * 此模式下isSave和forceRefresh设置无效
 *
 * CACHE_FIRST, 使用缓存，缓存优先，缓存过期或者无缓存使用网络，
 * 此模式下isSave推荐设置为true，forceRefresh设置为false
 *
 * NET_FIRST, 网络优先，有缓存先展示缓存，更优雅，
 * 此模式下isSave推荐设置为true，forceRefresh为false
 *
 */
public enum CacheMode {
    NO_CACHE,CACHE_FIRST,NET_FIRST
}
