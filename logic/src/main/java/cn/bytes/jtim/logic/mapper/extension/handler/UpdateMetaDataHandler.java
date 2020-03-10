package cn.bytes.jtim.logic.mapper.extension.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 *
 */
@Component
@Slf4j
public class UpdateMetaDataHandler implements MetaObjectHandler {

    private final static String UPDATE_TIME_FIELD_NAME = "updateTime";

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {

    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("进入了全局的更新字段填充");
        Date updateDate = Date.from(Instant.now());
        this.setFieldValByName(UPDATE_TIME_FIELD_NAME, updateDate, metaObject);
        metaObject.setValue(UPDATE_TIME_FIELD_NAME, updateDate);
    }
}
