package ms.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author maple
 * @create 2022-04-27 9:54
 */
@Component // 将当前类的对象创建使用维护 交由Spring容器维护
@Aspect // 将当前类标记为切面类
public class TimerAspect {
    // 环绕通知
    @Around("execution(* ms.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 先记录开始执行的系统时间
        long start = System.currentTimeMillis();

        Object result = pjp.proceed(); // 执行目标方法

        // 再记录执行结束的系统时间
        long end = System.currentTimeMillis();

        System.out.println("方法耗时：" + (end - start) + "ms.");
        return result;
    }
}
