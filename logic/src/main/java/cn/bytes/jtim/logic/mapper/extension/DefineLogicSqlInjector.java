package cn.bytes.jtim.logic.mapper.extension;

import cn.bytes.jtim.logic.mapper.extension.method.NativeInsertIgnoreMethod;
import cn.bytes.jtim.logic.mapper.extension.method.NativeSelectForUpdateMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 *
 **/
public class DefineLogicSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList() {

        List<AbstractMethod> defaultMethods = super.getMethodList();

        defaultMethods.add(new NativeInsertIgnoreMethod());
        defaultMethods.add(new NativeSelectForUpdateMethod());

        return defaultMethods;

    }
}

