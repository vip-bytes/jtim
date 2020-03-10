package cn.bytes.jtim.logic.mapper.extension;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 *
 **/
public interface ExpansionBaseMapper<T> extends BaseMapper<T> {

    /**
     * 插入忽略
     *
     * @param batch
     * @return
     */
    int insertBatchIgnore(@Param("list") Collection<T> batch);

    /**
     * 获取单条添加锁
     *
     * @param wrapper
     * @return
     */
    T selectForUpdate(@org.apache.ibatis.annotations.Param("ew") Wrapper<T> wrapper);


}
