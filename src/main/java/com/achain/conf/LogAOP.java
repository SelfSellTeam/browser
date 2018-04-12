package com.achain.conf;

import com.achain.interceptor.LogInterceptor;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author: yaoxiaohui
 * @date: 2018/03/07
 * @description: 配置切面, 配置日志 和请求参数打印
 */
@Aspect
//@Order(-99) // 控制多个Aspect的执行顺序，越小越先执行,为了要在Spring的事务之后执行,所以给他设置99
@Configuration
public class LogAOP {
    private static final Logger logger = LoggerFactory.getLogger(LogAOP.class);

    /**
     * 定义切点Pointcut
     * 第一个*号：表示返回类型， *号表示所有的类型
     * 第二个*号：表示类名，*号表示所有的类
     * 第三个*号：表示方法名，*号表示所有的方法
     * 后面括弧里面表示方法的参数，两个句点表示任何参数
     */
    @Pointcut("execution(* com.achain.controller..*.*(..))")
    public void executionService() {

    }

    /**
     * 环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around("executionService()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        //获取目标方法的参数信息
        Object[] obj = proceedingJoinPoint.getArgs();
        //AOP代理类的信息
        proceedingJoinPoint.getThis();
        //代理的目标对象
        proceedingJoinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = proceedingJoinPoint.getSignature();
//        //代理的是哪一个方法
//        System.out.println(signature.getName());
//        //AOP代理类的名字
//        System.out.println(signature.getDeclaringTypeName());
//        //AOP代理类的类（class）信息
//        System.out.println(signature.getDeclaringType());
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //如果要获取Session信息的话，可以这样写：
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        Enumeration<String> enumeration = request.getParameterNames();
        StringBuilder paramSb = new StringBuilder();
        while (enumeration.hasMoreElements()) {
            String paramkey = enumeration.nextElement();
            paramSb.append(paramkey).append("=").append(request.getParameter(paramkey)).append("&");
        }
        String reqParamStr = paramSb.toString();
        reqParamStr = reqParamStr.length() > 0 ? reqParamStr.substring(0, reqParamStr.length() - 1) : "";
        logger.info("class:{}|method:{}|param:{}", signature.getDeclaringTypeName(), signature.getName(), reqParamStr);
        try {
            Object resultObj = proceedingJoinPoint.proceed();
            logger.info("resp:{}", JSON.toJSONString(resultObj));
            return resultObj;
        } catch (Throwable throwable) {
//            throwable.printStackTrace();
            logger.error("exception:{}", throwable);
            Map<String, Object> failRespRsult = getWebResultFail();
            logger.error("resp:{}", failRespRsult);
            return failRespRsult;
        }
    }

    private Map<String, Object> getWebResultFail() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", 1001);
        map.put("msg", "系统错误，请稍后再试");
        return map;
    }
}
