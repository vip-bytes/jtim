package cn.bytes.jtim.logic.mapper.extension.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 *
 */
public class NativeSelectForUpdateMethod extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, String.format(SQLDefine.SELECT_FOR_UPDATE.getSql(), this.sqlSelectColumns(tableInfo, true), tableInfo.getTableName(), this.sqlWhereEntityWrapper(true, tableInfo)), modelClass);
        return this.addSelectMappedStatement(mapperClass, SQLDefine.SELECT_FOR_UPDATE.getMethod(), sqlSource, modelClass, tableInfo);
    }
}

