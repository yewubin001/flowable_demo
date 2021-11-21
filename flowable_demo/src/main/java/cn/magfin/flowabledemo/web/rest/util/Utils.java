package cn.magfin.flowabledemo.web.rest.util;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @created: 11/9/21
 * @package: cn.magfin.flowabledemo.web.rest.util
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/9/21
 * @IDETools: IDEA
 * @description:
 */
public class Utils {

    public static String dateFormat(Date date) {
        if (Objects.isNull(date)) {
            return "-";
        }
        return DateFormatUtils.ISO_DATETIME_FORMAT.format(date);
    }
}
