package com.crsm.maker.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Session监听
 */
@Slf4j
public class SessionMonitor implements SessionListener {
    @Override
    public void onStart(Session session) {
        log.info("会话创建：【{}】",session.getId());
    }

    @Override
    public void onStop(Session session) {
        log.info("会话停止：【{}】",session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        log.info("会话超时：【{}】",session.getId());
    }
}
