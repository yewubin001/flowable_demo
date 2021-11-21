package cn.magfin.flowabledemo.service;

import cn.magfin.flowabledemo.domain.User;
import org.springframework.stereotype.Service;

/**
 * @created: 11/9/21
 * @package: cn.magfin.flowabledemo.service
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/9/21
 * @IDETools: IDEA
 * @description:
 */
@Service
public class UserService {

    private User applyUser = new User("123", "张三");
    private User approveUser = new User("456", "李四");


    public User getApplyUser() {
        return applyUser;
    }

    public User getApproveUser() {
        return approveUser;
    }

    public String getUserName(String userId) {
        if (applyUser.getUserId().equals(userId)) {
            return applyUser.getUserName();
        } else {
            return approveUser.getUserName();
        }
    }
}
