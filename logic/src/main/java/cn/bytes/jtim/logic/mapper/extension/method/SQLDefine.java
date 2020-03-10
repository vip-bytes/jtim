package cn.bytes.jtim.logic.mapper.extension.method;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */

@AllArgsConstructor
@Getter
public enum SQLDefine {

    SELECT_FOR_UPDATE("selectForUpdate", "<script>\nSELECT %s FROM %s %s for update\n</script>"),

    INSERT_BATCH_IGNORE("insertBatchIgnore", "<script\ninsert ignore into %s %s values %s \n</script>"),

    ;

    private String method;

    private String sql;

}
