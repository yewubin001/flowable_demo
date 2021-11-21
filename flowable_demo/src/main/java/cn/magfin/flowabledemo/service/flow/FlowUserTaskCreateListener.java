package cn.magfin.flowabledemo.service.flow;

import cn.magfin.flowabledemo.service.UserService;
import com.google.common.collect.Maps;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static cn.magfin.flowabledemo.domain.FlowableVarName.*;

/**
 * @created: 11/9/21
 * @package: cn.magfin.flowabledemo.service.flow
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/9/21
 * @IDETools: IDEA
 * @description:
 */
@Component("flowUserTaskCreateListener")
public class FlowUserTaskCreateListener implements TaskListener {
    private Logger log = LoggerFactory.getLogger(FlowUserTaskCreateListener.class);

    @Autowired
    private UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> variables = Maps.newHashMap();
        variables.put(CURR_NODE_NAME, delegateTask.getName());
        variables.put(CURR_NODE_ASSIGNEE, userService.getUserName(delegateTask.getAssignee()));
        variables.put(CURR_NODE_START_DATE, delegateTask.getCreateTime());

        delegateTask.setVariables(variables);
        String userId = Objects.toString(delegateTask.getVariable(TARGET_USER_ID), "");
        delegateTask.setAssignee(userId);
        delegateTask.setVariable(USER_ID, userId);

    }
}
