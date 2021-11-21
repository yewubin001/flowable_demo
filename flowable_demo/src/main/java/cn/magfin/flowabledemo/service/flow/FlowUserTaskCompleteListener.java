package cn.magfin.flowabledemo.service.flow;

import cn.magfin.flowabledemo.domain.enums.FlowAuditEnum;
import cn.magfin.flowabledemo.service.UserService;
import com.google.common.collect.Maps;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static cn.magfin.flowabledemo.domain.FlowableCommentType.PROCESS_MEMO;
import static cn.magfin.flowabledemo.domain.FlowableCommentType.*;
import static cn.magfin.flowabledemo.domain.FlowableVarName.*;
import static cn.magfin.flowabledemo.web.rest.util.Utils.dateFormat;

/**
 * @created: 11/9/21
 * @package: cn.magfin.flowabledemo.service.flow
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/9/21
 * @IDETools: IDEA
 * @description:
 */
@Component("flowUserTaskCompleteListener")
public class FlowUserTaskCompleteListener implements TaskListener {
    private Logger log = LoggerFactory.getLogger(FlowUserTaskCompleteListener.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Override
    public void notify(DelegateTask delegateTask) {
        Map<String, Object> variables = Maps.newHashMap();
        String processUserName = userService.getUserName(delegateTask.getAssignee());
        variables.put(PREVIOUS_NODE_NAME, delegateTask.getName());
        variables.put(PREVIOUS_NODE_ASSIGNEE, processUserName);

        delegateTask.setVariables(variables);

        //add comment
        Map<String, String> comments = Maps.newHashMap();
        Map<String, Object> processVars = delegateTask.getVariables();

        comments.put(PROCESS_NODE, delegateTask.getName());
        comments.put(ASSIGNEE, processUserName);
        comments.put(START_TIME, dateFormat((Date) processVars.get(CURR_NODE_START_DATE)));
        comments.put(PROCESS_TIME, dateFormat(new Date()));
        comments.put(PROCESS_RESULT, Objects.toString(processVars.get(AUDIT_RESULT), ""));
        comments.put(PROCESS_MEMO, Objects.toString(processVars.get(PROCESS_MEMO), ""));

        comments.forEach((k, v) -> taskService.addComment(delegateTask.getId(), delegateTask.getProcessInstanceId(), k, v));

        if (delegateTask.getVariable(AUDIT_RESULT).equals(FlowAuditEnum.ROLL_BACK.name())) {
            delegateTask.setVariable(TARGET_USER_ID, delegateTask.getVariable(PREVIOUS_USER_ID));
        }

        delegateTask.setVariable(PREVIOUS_USER_ID, delegateTask.getVariable(USER_ID));
    }
}
