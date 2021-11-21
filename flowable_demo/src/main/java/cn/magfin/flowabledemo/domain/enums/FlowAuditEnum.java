package cn.magfin.flowabledemo.domain.enums;

/**
 * @created: 11/9/21
 * @package: cn.magfin.flowabledemo.domain.enums
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/9/21
 * @IDETools: IDEA
 * @description:
 */
public enum FlowAuditEnum {
    /**
     * 审批结论
     */
    APPLY("申请"),
    APPROVED("通过"),
    ROLL_BACK("退回"),
    REJECTED("拒绝"),
    ;

    String desc;

    FlowAuditEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
