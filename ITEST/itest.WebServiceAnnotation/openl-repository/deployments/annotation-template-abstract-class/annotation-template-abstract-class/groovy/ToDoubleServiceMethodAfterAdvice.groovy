import org.openl.rules.ruleservice.core.interceptors.AbstractServiceMethodAfterReturningAdvice

import java.lang.reflect.Method

class ToDoubleServiceMethodAfterAdvice extends AbstractServiceMethodAfterReturningAdvice<Double> {
    @Override
    Double afterReturning(Method interfaceMethod, Object result, Object... args) throws Exception {
        return ((Number) result).doubleValue();
    }
}
