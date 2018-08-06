package cn.edu.cqu.wheel.framework.proxy;

/**
 * 代理接口
 * @author hxc
 */
public interface Proxy {

    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}