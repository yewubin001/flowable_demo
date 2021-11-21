package cn.magfin.flowabledemo.web.rest;

import cn.magfin.flowabledemo.domain.enums.FlowAuditEnum;
import cn.magfin.flowabledemo.service.DiagramService;
import cn.magfin.flowabledemo.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.magfin.flowabledemo.domain.FlowableVarName.*;
import static cn.magfin.flowabledemo.domain.enums.FlowBizTypeEnum.BLACK_LIST_REMOVE;
import static cn.magfin.flowabledemo.web.rest.util.Utils.dateFormat;
import static java.util.Comparator.comparing;

/**
 * @created: 11/5/21
 * @package: cn.magfin.flowabledemo.web.rest
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/5/21
 * @IDETools: IDEA
 * @description:
 */
@Controller
@RequestMapping("/black-list-remove")
public class BlackListRemoveAuditController {
    private Logger log = LoggerFactory.getLogger(BlackListRemoveAuditController.class);

    private static final String REDIRECT_URL = "redirect:/black-list-remove/";

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private DiagramService diagramService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("apply_list", list(userService.getApplyUser().getUserId()));
        model.addAttribute("audit_list", list(userService.getApproveUser().getUserId()));
        model.addAttribute("finished_list", finishedList());
        return "index";
    }


    @GetMapping("/add")
    public String add() {
        //启动流程
        String sequenceNo = "Q123456789001";
        String userId = userService.getApplyUser().getUserId();
        String userName = userService.getApplyUser().getUserName();
        String processDesc = "由".concat(userName).concat("创建的").concat(BLACK_LIST_REMOVE.getDesc());

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("userName", "刘某某");
        map.put("idCardNo", "37847873984932423");

        map.put(USER_ID, userId);
        map.put(TARGET_USER_ID, userId);
        map.put(STARTER_NAME, userName);
        map.put(BUSINESS_KEY, sequenceNo);
        map.put(BIZ_TYPE, BLACK_LIST_REMOVE.name());
        map.put(PROCESS_NAME, BLACK_LIST_REMOVE.getDesc());
        map.put(PROCESS_DESC, processDesc);

        Authentication.setAuthenticatedUserId(userId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("black_list_remove", sequenceNo, map);
        runtimeService.setProcessInstanceName(processInstance.getId(), processDesc);

        return REDIRECT_URL;
    }

    public Object list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();

        return tasks.stream().map(e -> {
            Map<String, Object> variables = runtimeService.getVariables(e.getExecutionId());

            Map<String, Object> item = Maps.newHashMap();
            item.put("taskId", e.getId());
            item.put("taskName", e.getName());
            item.put("desc", variables.get(PROCESS_DESC));
            item.put("processInsId", e.getProcessInstanceId());
            item.put("date", dateFormat(e.getCreateTime()));
            item.put("businessKey", variables.get(BUSINESS_KEY));
            item.put("processName", variables.get(PROCESS_NAME));
            item.put("currNodeName", variables.get(CURR_NODE_NAME));
            item.put("currNodeAssignee", variables.get(CURR_NODE_ASSIGNEE));
            item.put("currNodeStartDate", dateFormat((Date) variables.get(CURR_NODE_START_DATE)));
            item.put("previousNodeName", variables.get(PREVIOUS_NODE_NAME));
            item.put("previousNodeAssignee", variables.get(PREVIOUS_NODE_ASSIGNEE));
            return item;
        }).collect(Collectors.toList());
    }

    private Object finishedList() {
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("black_list_remove").finished().list();

        return historicProcessInstances.stream().map(e -> {
            Map<String, Object> variables = Maps.newHashMap();
            historyService.createHistoricVariableInstanceQuery().processInstanceId(e.getId()).list().forEach(v -> {
                variables.put(v.getVariableName(), v.getValue());
            });

            Map<String, Object> item = Maps.newHashMap();
            item.put("taskId", e.getId());
            item.put("taskName", e.getName());
            item.put("desc", variables.get(PROCESS_DESC));
            item.put("processInsId", e.getId());
            item.put("businessKey", variables.get(BUSINESS_KEY));
            item.put("processName", variables.get(PROCESS_NAME));
            item.put("starterName", variables.get(STARTER_NAME));
            item.put("startTime", dateFormat(e.getStartTime()));
            item.put("endTime", dateFormat(e.getEndTime()));
            item.put("durationInMillis", e.getDurationInMillis());
            item.put("currNodeName", variables.get(CURR_NODE_NAME));
            item.put("currNodeAssignee", variables.get(CURR_NODE_ASSIGNEE));
            item.put("currNodeStartDate", dateFormat((Date) variables.get(CURR_NODE_START_DATE)));
            item.put("handlerTrigger", variables.get("handlerTrigger"));
            return item;
        }).collect(Collectors.toList());
    }


    @GetMapping(value = "/apply")
    public String apply(String taskId, String action) {
        HashMap<String, Object> map = new HashMap<>();

        if (StringUtils.isNotBlank(action) && action.equals("apply")) {
            map.put(TARGET_USER_ID, userService.getApproveUser().getUserId());
            map.put(AUDIT_RESULT, FlowAuditEnum.APPLY.name());
            map.put(PROCESS_MEMO, FlowAuditEnum.APPLY.getDesc());
        } else {
            map.put(AUDIT_RESULT, FlowAuditEnum.APPROVED.name());
            map.put(PROCESS_MEMO, FlowAuditEnum.APPROVED.getDesc());
        }
        taskService.complete(taskId, map);
        return REDIRECT_URL;
    }

    @GetMapping(value = "/roll_back")
    public String rollBack(String taskId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(AUDIT_RESULT, FlowAuditEnum.ROLL_BACK.name());
        map.put(PROCESS_MEMO, FlowAuditEnum.ROLL_BACK.getDesc());
        taskService.complete(taskId, map);
        return REDIRECT_URL;
    }

    @GetMapping(value = "/diagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {

        OutputStream out = null;
        try {
            httpServletResponse.setHeader("content-type", "image/png");
            out = httpServletResponse.getOutputStream();
            byte[] image = diagramService.createImage(processId);
            out.write(image);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @GetMapping("/comment")
    public String comment(Model model, String processId) {
        List<Comment> comments = taskService.getProcessInstanceComments(processId);

        List<Map<String, Object>> infoList = Lists.newArrayList();
        comments
            .stream()
            .collect(Collectors.groupingBy(Comment::getTaskId))
            .values()
            .stream()
            .filter(CollectionUtils::isNotEmpty).sorted(comparing(e -> e.get(0).getTime()))
            .forEach(e -> {
                Map<String, Object> item = Maps.newHashMap();
                for (Comment comment : e) {
                    item.put(comment.getType(), comment.getFullMessage());
                }
                infoList.add(item);
            });

        model.addAttribute("comments", infoList);

        return "comments";
    }

    @GetMapping("/clean")
    public String cleanAllInstance() {
        historyService.createHistoricProcessInstanceQuery().processDefinitionKey("black_list_remove").finished().list().forEach(e -> {
            historyService.deleteHistoricProcessInstance(e.getId());
        });

        historyService.createHistoricTaskInstanceQuery().processDefinitionKey("black_list_remove").list().forEach(e -> {
            historyService.deleteHistoricTaskInstance(e.getId());
        });

        return REDIRECT_URL;
    }

}
