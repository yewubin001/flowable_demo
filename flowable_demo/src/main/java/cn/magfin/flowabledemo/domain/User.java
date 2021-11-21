package cn.magfin.flowabledemo.domain;

/**
 * @created: 11/9/21
 * @package: cn.magfin.flowabledemo.domain
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/9/21
 * @IDETools: IDEA
 * @description:
 */
public class User {

    private String userId;

    private String userName;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId='" + userId + '\'' +
            ", userName='" + userName + '\'' +
            '}';
    }
}
