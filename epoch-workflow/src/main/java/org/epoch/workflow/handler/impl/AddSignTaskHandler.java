//package org.epoch.workflow.handler.impl;
//
//import org.epoch.core.component.SessionContext;
//import org.epoch.workflow.constant.ActivitiConstant;
//import org.epoch.workflow.entity.TaskActionRequestExt;
//import org.epoch.workflow.exception.TaskHandleException;
//import org.epoch.workflow.handler.TaskHandler;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.impl.identity.Authentication;
//import org.activiti.engine.task.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @auth Marshal
// * @date 2019-07-05 15:22
// * @desc 任务加签处理器
// */
//public class AddSignTaskHandler implements TaskHandler {
//
//    @Autowired
//    private TaskService taskService;
//
//    @Override
//    public String getAction() {
//        return ActivitiConstant.ACTION_ADD_SIGN;
//    }
//
//    @Override
//    public void process(SessionContext sessionContext, Task taskEntity, TaskActionRequestExt actionRequest) throws TaskHandleException {
//        String taskId = taskEntity.getId();
//
//        Authentication.setAuthenticatedUserId(sessionContext.getEmployeeCode());
//
//        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), ActivitiConstant.APPROVE_ACTION, actionRequest.getAction());
//        taskService.addComment(taskId, taskEntity.getProcessInstanceId(), ActivitiConstant.APPROVE_COMMENT, actionRequest.getComment());
//
//        taskService.delegateTask(taskId, actionRequest.getAssignee());
//
//    }
//}
