package spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class DynamicProxyTest {

    @Test
    public void classNamePointcutAdvisor(){

        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut(){

            public ClassFilter getClassFilter(){
                return new ClassFilter(){
                    public boolean matches(Class<?> clazz){
                        return clazz.getSimpleName().startsWith("HelloT");
                    }
                };

            }
        };

        classMethodPointcut.setMappedName("sayH*");

        checkAdviced(new HelloTarget(), classMethodPointcut, true);

        class HelloWorld extends HelloTarget {};
        checkAdviced(new HelloWorld(), classMethodPointcut, false);

        class HelloToby extends HelloTarget{};
        checkAdviced(new HelloToby(), classMethodPointcut, true);



    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced){

        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxiedHello = (Hello) pfBean.getObject();

        if(adviced){
            Assertions.assertThat(proxiedHello.sayHello("Toby")).isEqualTo("HELLO TOBY");
            Assertions.assertThat(proxiedHello.sayHi("Toby")).isEqualTo("HI TOBY");
            Assertions.assertThat(proxiedHello.sayThankYou("Toby")).isEqualTo("Thank You Toby");
            System.out.println("애즈펙트 적용");
            System.out.println(proxiedHello.sayHello("Toby"));
            System.out.println(proxiedHello.sayHi("Toby"));
            System.out.println(proxiedHello.sayThankYou("Toby"));
            System.out.println("--------------------------------------");
        }else {
            Assertions.assertThat(proxiedHello.sayHello("Toby")).isEqualTo("Hello Toby");
            Assertions.assertThat(proxiedHello.sayHi("Toby")).isEqualTo("Hi Toby");
            Assertions.assertThat(proxiedHello.sayThankYou("Toby")).isEqualTo("Thank You Toby");
            System.out.println("애즈펙트 적용 xxxx");
            System.out.println(proxiedHello.sayHello("Toby"));
            System.out.println(proxiedHello.sayHi("Toby"));
            System.out.println(proxiedHello.sayThankYou("Toby"));
            System.out.println("--------------------------------------");
        }



    }

    static interface Hello {
        String sayHello(String name);
        String sayHi(String name);
        String sayThankYou(String name);
    }

    static class HelloTarget implements Hello {
        public String sayHello(String name) {
            return "Hello " + name;
        }

        public String sayHi(String name) {
            return "Hi " + name;
        }

        public String sayThankYou(String name) {
            return "Thank You " + name;
        }
    }

    static class HelloUppercase implements Hello {
        Hello hello;

        public HelloUppercase(Hello hello) {
            this.hello = hello;
        }

        public String sayHello(String name) {
            return hello.sayHello(name).toUpperCase();
        }

        public String sayHi(String name) {
            return hello.sayHi(name).toUpperCase();
        }

        public String sayThankYou(String name) {
            return hello.sayThankYou(name).toUpperCase();
        }

    }

    static class UppercaseAdvice implements MethodInterceptor {
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String)invocation.proceed();
            return ret.toUpperCase();
        }
    }
}
