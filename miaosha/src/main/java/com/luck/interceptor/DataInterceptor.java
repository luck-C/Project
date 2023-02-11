package com.luck.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
@Component
@Intercepts(value = {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class DataInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = statement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        if(Objects.isNull(parameter)){
            invocation.proceed();
        }

        //sql类型为插入
        if(Objects.equals(sqlCommandType,SqlCommandType.INSERT)){
            Field[] declaredFields = parameter.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                try {
                    String fieldName = field.getName();
                    if(Objects.equals(fieldName,"creationDate")||Objects.equals(fieldName,"lastUpdateDate")){
                        field.setAccessible(true);
                        field.set(parameter,new Date());
                        field.setAccessible(false);
                    }
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        if(Objects.equals(sqlCommandType,SqlCommandType.UPDATE)){
            Field[] fields = parameter.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    if(Objects.equals(field.getName(),"lastUpdateDate")){
                        field.setAccessible(true);
                        field.set(parameter,new Date());
                        field.setAccessible(false);
                    }
                } catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        //sql类型为更新
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
