package com.krest.rpc.starter.register;

import com.krest.rpc.annotation.RpcServerAnno;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterRpcBean implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 启动 RpcServer
        if (bean.getClass().isAnnotationPresent(RpcServerAnno.class)) {
            RpcServerAnno anno = bean.getClass().getAnnotation(RpcServerAnno.class);
            RunRpcServerRunnable serverRunnable = new RunRpcServerRunnable(bean, anno.port(), anno.threads());
            Thread thread = new Thread(serverRunnable);
            thread.start();
        }
        return bean;
    }
}