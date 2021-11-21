package cn.magfin.flowabledemo.domain.enums;

/**
 * @created: 11/9/21
 * @package: cn.magfin.flowabledemo.domain
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/9/21
 * @IDETools: IDEA
 * @description:
 */
public enum FlowBizTypeEnum {
    /**
     * 流程类型
     */
    PROD_ON_LINE("产品上架"),
    PROD_OFFLINE("产品下架"),
    BLACK_LIST_REG("不宜贷款户登记"),
    BLACK_LIST_REMOVE("不宜贷款户解除"),
    CUS_HAND_OVER("客户移交"),
    CUS_SHARE("客户共享"),
    BIZ_APPLY("业务申请"),
    ;

    String desc;

    FlowBizTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
