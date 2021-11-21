package cn.magfin.flowabledemo.service.flow;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @created: 11/9/21
 * @package: cn.magfin.flowabledemo.service.flow
 * @version: v1.0
 * @author: xiaoboli
 * @date: 11/9/21
 * @IDETools: IDEA
 * @description:
 */
@Service("flowAuditFinalResultListener")
public class FlowAuditFinalResultListener implements ExecutionListener {
    private Logger log = LoggerFactory.getLogger(FlowAuditFinalResultListener.class);

    @Override
    public void notify(DelegateExecution execution) {
        execution.setVariable("handlerTrigger", "True");
    }
}
