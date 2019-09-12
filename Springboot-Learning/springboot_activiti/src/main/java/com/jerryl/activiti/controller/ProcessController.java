package com.jerryl.activiti.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jerryl.model.ProcessModel;
import com.jerryl.util.*;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 对外提供流程Controller
 */
@RequestMapping("process")
@RestController
public class ProcessController {
    private  static final Logger logger = Logger.getLogger(ProcessController.class);
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    HistoryService historyService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    TaskService taskService;
    @Autowired
    ActivitiUtils activitiUtils;
    @Autowired
    IdGen idGen;
    @Autowired
    IdentityService identityService;
    @Autowired
    ObjectMapper objectMapper;


    /**
     *  查询可以启动的流程
     * @return
     */
    @PostMapping("qureystartProcessInstance")
    public JSONObject qureystartProcessInstance(){
        try {
            List<Map> result = new ArrayList<>();
            //查询最新版本的
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                    .latestVersion().orderByProcessDefinitionKey().asc();
            //分页
//            List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(), page.getMaxResults());
            List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
            for (ProcessDefinition processDefinition : processDefinitionList) {
                String deploymentId = processDefinition.getDeploymentId();
                Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
                Map pMap = new HashMap<>();
                pMap.put("id", processDefinition.getId());
                pMap.put("category", processDefinition.getCategory());
                pMap.put("key", processDefinition.getKey());
                pMap.put("name", processDefinition.getName());
                pMap.put("version","V:"+processDefinition.getVersion());
                pMap.put("resourceName", processDefinition.getResourceName());
                pMap.put("diagramResourceName", processDefinition.getDiagramResourceName());
                pMap.put("deploymentId", processDefinition.getDeploymentId());
                pMap.put("suspended", processDefinition.isSuspended());
                pMap.put("deploymentTime",deployment.getDeploymentTime());
                result.add(pMap);
            }
            return ResultUtils.returnJSONArray(HttpRequest.SUCCEED_CODE, "查询成功", result);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "查询失败", new JSONObject());
        }
    }


    /**
     * 启动流程
     * @return
     */
    @PostMapping("startProcessInstance")
    public JSONObject startProcessInstance(@RequestBody ProcessModel processModel){
        try {
            processEngine.getIdentityService().setAuthenticatedUserId(processModel.getUserId());
            processEngine.getRuntimeService()
                    .startProcessInstanceById(processModel.getProcess(),processModel.getPlanid());

//            pushServerEngineClient.messagePush()
            return ResultUtils.returnJSONObject(HttpRequest.SUCCEED_CODE, "启动流程成功", new JSONObject());
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "启动流程失败", new JSONObject());
        }
    }

    /**
     *  查询当前登录人所有的待办流程
     * @param userId 流程人工号
     * @return
     */
    @PostMapping("getTasks")
    public JSONObject getTasks(@RequestParam String userId){
        try {
            List<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
//            //组任务
//            List<Task> list = processEngine.getTaskService()//
//                    .createTaskQuery()//
//                    .taskCandidateUser(userId)// 参与者，组任务查询
//                    .list();
            //个人任务
            List<Task> todoList = taskService
                    .createTaskQuery()
                    .taskAssignee(userId)
                    .list();
            ProcessInstance pi = null;
            for (Task task : todoList) {
                //根据taskID查询业务ID
                pi = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(task.getProcessInstanceId())
                        .singleResult();
                HashMap map = new HashMap();
                map.put("assignee",task.getAssignee());
                map.put("id", task.getId());
                map.put("createTime", task.getCreateTime());
                map.put("name", task.getName());
//                map.put("task.executionId",task.getExecutionId());
                map.put("processDefinitionId", task.getProcessDefinitionId());
                map.put("processInstanceId", task.getProcessInstanceId());
                map.put("taskDefinitionKey", task.getTaskDefinitionKey());
                map.put("vars",task.getProcessVariables());
                map.put("BusinessKey",pi.getBusinessKey());
                result.add(map);
            }
            Map<String, Object> resultmap = new HashMap<>();
            resultmap.put("rows",result);
            resultmap.put("total",result.size());
            return ResultUtils.returnJSONObject(HttpRequest.SUCCEED_CODE, "查询流程成功", resultmap);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "查询流程失败", new JSONObject());
        }
    }

    /**
     *  通过流程
     * @param taskId 流程ID
     * @return
     */
    @PostMapping("updTasks")
    public JSONObject updTasks(@RequestParam String taskId,@RequestParam String opinion){
        try {

            Map<String,Object>variables = new HashMap<String, Object>();
            if (!StringUtils.isEmpty(opinion)) { //审核意见
                variables.put("通过意见", opinion);
            }
            taskService.complete(taskId,variables);
            //通知
            return ResultUtils.returnJSONObject(HttpRequest.SUCCEED_CODE, "通过流程成功",  new JSONObject());
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "通过流程失败", new JSONObject());
        }

    }

    /**
     *  驳回流程
     * @param taskId 流程ID
     * @return
     */
    @PostMapping("updReject")
    public JSONObject updReject(@RequestParam String taskId,@RequestParam String opinion){
        try {

            Map<String, Object> variables;
            // 取得当前任务
            HistoricTaskInstance currTask = historyService
                    .createHistoricTaskInstanceQuery().taskId(taskId)
                    .singleResult();
            // 取得流程实例
            ProcessInstance instance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(currTask.getProcessInstanceId())
                    .singleResult();
            if (instance == null) {
            }
            variables = instance.getProcessVariables();
            if (!StringUtils.isEmpty(opinion)) { //审核意见
                variables.put("通过意见", opinion);
            }
            // 取得流程定义
            ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                    .getDeployedProcessDefinition(currTask
                            .getProcessDefinitionId());
            if (definition == null) {
            }
            // 取得上一步活动
            ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                    .findActivity(currTask.getTaskDefinitionKey());
            List<PvmTransition> nextTransitionList = currActivity
                    .getIncomingTransitions();
            // 清除当前活动的出口
            List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
            List<PvmTransition> pvmTransitionList = currActivity
                    .getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitionList) {
                oriPvmTransitionList.add(pvmTransition);
            }
            pvmTransitionList.clear();

            // 建立新出口
            List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
            for (PvmTransition nextTransition : nextTransitionList) {
                PvmActivity nextActivity = nextTransition.getSource();
                ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
                        .findActivity(nextActivity.getId());
                TransitionImpl newTransition = currActivity
                        .createOutgoingTransition();
                newTransition.setDestination(nextActivityImpl);
                newTransitions.add(newTransition);
            }
            // 完成任务
            List<Task> tasks = taskService.createTaskQuery()
                    .processInstanceId(instance.getId())
                    .taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
            for (Task task : tasks) {
                taskService.complete(task.getId(), variables);
                historyService.deleteHistoricTaskInstance(task.getId());
            }
            // 恢复方向
            for (TransitionImpl transitionImpl : newTransitions) {
                currActivity.getOutgoingTransitions().remove(transitionImpl);
            }
            for (PvmTransition pvmTransition : oriPvmTransitionList) {
                pvmTransitionList.add(pvmTransition);
            }
            //成功
            return ResultUtils.returnJSONObject(HttpRequest.SUCCEED_CODE, "驳回流程成功",  new JSONObject());
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "驳回流程失败", new JSONObject());
        }
    }


    /**
     *  结束流程
     * @param taskId 流程ID
     * @return
     */
    @PostMapping("endTasks")
    public JSONObject endTasks(@RequestParam String taskId,@RequestParam String opinion){
        try {
            //根据taskId查询任务
            Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();

            ActivityImpl endActivity = activitiUtils.findActivitiImpl(taskId, "end");

            if(endActivity==null){
                return  ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "结束流程失败", new JSONObject());
            }

            // 获取当前活动节点ID
            //String activityId = task.getTaskDefinitionKey();

            Map<String,Object>variables = new HashMap<String, Object>();
            if (!StringUtils.isEmpty(opinion)) { //审核意见
                variables.put("驳回意见", opinion);
            }
            // 跳转节点为空，默认提交操作
            if (StringUtils.isEmpty(endActivity.getId())) {

                if(!StringUtils.isEmpty(task.getOwner())){
                    // 被委派人处理完成任务
                    processEngine.getTaskService().resolveTask(task.getId(),variables);
                }
                processEngine.getTaskService().complete(taskId, variables);
            } else {// 流程转向操作
                //turnTransition(taskId, activityId, variables);
                // 当前节点
                ActivityImpl currActivity = activitiUtils.findActivitiImpl(taskId, null);

                // 清空当前流向
                // 存储当前节点所有流向临时变量
                List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
                // 获取当前节点所有流向，存储到临时变量，然后清空
                List<PvmTransition> pvmTransitionList = currActivity
                        .getOutgoingTransitions();
                for (PvmTransition pvmTransition : pvmTransitionList) {
                    oriPvmTransitionList.add(pvmTransition);
                }
                pvmTransitionList.clear();

                // 创建新流向
                TransitionImpl newTransition = currActivity.createOutgoingTransition();
                // 目标节点
                ActivityImpl pointActivity = activitiUtils.findActivitiImpl(taskId, endActivity.getId());
                // 设置新流向的目标节点
                newTransition.setDestination(pointActivity);

                if(!StringUtils.isEmpty(task.getOwner())){
                    // 被委派人处理完成任务
                    processEngine.getTaskService().resolveTask(task.getId(),variables);
                }
                // 执行转向任务
                processEngine.getTaskService().complete(taskId, variables);
                // 删除目标节点新流入
                pointActivity.getIncomingTransitions().remove(newTransition);

                // 还原以前流向
                //restoreTransition(currActivity, oriPvmTransitionList);
                // 清空现有流向
                List<PvmTransition> pvmTransitionListC = currActivity.getOutgoingTransitions();
                pvmTransitionListC.clear();
                // 还原以前流向
                for (PvmTransition pvmTransition : oriPvmTransitionList) {
                    pvmTransitionListC.add(pvmTransition);
                }
            }

            return ResultUtils.returnJSONObject(HttpRequest.SUCCEED_CODE, "结束流程成功",  new JSONObject());
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "结束流程失败", new JSONObject());
        }
    }



    /**
     *  查询部署流程的流程图
     * @param taskId 流程ID
     * @return
     */
    @PostMapping("getdeplyomenImg")
    public void getdeplyomenImg(HttpServletResponse response, @RequestParam String taskId){

        InputStream fis = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            fis=activitiUtils.lookProcessPicture(taskId);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     *  查看已经完成的任务
     * @param
     * @return
     */
    @PostMapping("findHistoryTask")
    public JSONObject findHistoryTask(@RequestParam String userId){
        try {
            List<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
            //如果只想获取到已经执行完成的，那么就要加入completed这个过滤条件
            List<HistoricTaskInstance> historicTaskInstances1 = processEngine.getHistoryService()
                    .createHistoricTaskInstanceQuery()
                    .taskDeleteReason("completed")
                    .taskAssignee(userId)
                    .list();
            historicTaskInstances1.forEach(historicTaskInstance -> {
                //根据taskID查询业务ID
                HistoricProcessInstance pi= historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getExecutionId()).singleResult();
                HashMap map = new HashMap();
                map.put("assignee",historicTaskInstance.getAssignee());
                map.put("id", historicTaskInstance.getId());
                map.put("createTime", historicTaskInstance.getCreateTime());
                map.put("name", historicTaskInstance.getName());
//                map.put("task.executionId",task.getExecutionId());
                map.put("processDefinitionId", historicTaskInstance.getProcessDefinitionId());
                map.put("processInstanceId", historicTaskInstance.getProcessInstanceId());
                map.put("taskDefinitionKey", historicTaskInstance.getTaskDefinitionKey());
                map.put("vars",historicTaskInstance.getProcessVariables());
                map.put("BusinessKey",pi.getBusinessKey());
                result.add(map);
            });
            return ResultUtils.returnJSONArray(HttpRequest.SUCCEED_CODE, "查询历史成功",  result);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "查询历史失败", new JSONObject());
        }

    }
    /**
     * 某一次流程执行了多少步
     */
    @PostMapping("queryHistoricActivitiInstance")
    public JSONObject queryHistoricActivitiInstance(@RequestParam String processInstanceId) {
        try {
            List<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
            List<HistoricActivityInstance> list = processEngine.getHistoryService()
                    .createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc()
                    .list();
            list.forEach(his->{
                //根据taskID查询业务ID
                HistoricProcessInstance pi= historyService.createHistoricProcessInstanceQuery().processInstanceId(his.getExecutionId()).singleResult();
                HashMap map = new HashMap();
                map.put("assignee",his.getAssignee());
                map.put("id", his.getId());
                map.put("startTime", his.getStartTime());
                map.put("endTime", his.getEndTime());
                map.put("name", his.getActivityName());
                map.put("processDefinitionId", his.getProcessDefinitionId());
                map.put("processInstanceId", his.getProcessInstanceId());
                map.put("taskDefinitionKey", his.getTaskId());
                map.put("BusinessKey",pi==null?null:pi.getBusinessKey());
                result.add(map);
            });
            return ResultUtils.returnJSONArray(HttpRequest.SUCCEED_CODE, "查询历史成功",  result);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "查询历史失败", new JSONObject());
        }

    }
    /**
     * 获取流程图像，已执行节点和流程线高亮显示
     */
    @GetMapping("getActivitiProccessImage")
    public void getActivitiProccessImage(String pProcessInstanceId, HttpServletResponse response) throws Exception {
        logger.info("[开始]-获取流程图图像");
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(pProcessInstanceId).singleResult();

            if (historicProcessInstance == null) {
                return;
            } else {
                // 获取流程定义
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(pProcessInstanceId).orderByHistoricActivityInstanceId().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<String>();
                int index = 1;
                logger.info("获取已经执行的节点ID");
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());
                    logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
                    index++;
                }

                // 获取流程图图像字符流
                InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", executedActivityIdList);

                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
            logger.info("[完成]-获取流程图图像");
        } catch (Exception e) {
            logger.error("【异常】-获取流程图失败！" + e.getMessage());
        }
    }
    /**
     * 我的申请
     */
    @PostMapping("getHisActiviti")
    public JSONObject getHisActiviti(@RequestParam String userId){
        try {
            List<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
            //查询我的发起历史
            List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().startedBy(userId).list();
            historicProcessInstanceList.forEach(his->{
                HashMap map = new HashMap();
                map.put("assignee",his.getStartUserId());
                map.put("id", his.getId());
                map.put("startTime", his.getStartTime());
                map.put("endTime", his.getEndTime());
                map.put("name", his.getProcessDefinitionName());
                map.put("processDefinitionId", his.getProcessDefinitionId());
                map.put("processInstanceId", his.getProcessDefinitionId());
                map.put("taskDefinitionKey", his.getProcessDefinitionKey());
                map.put("BusinessKey",his.getBusinessKey());
                result.add(map);
            });
            return ResultUtils.returnJSONArray(HttpRequest.SUCCEED_CODE, "查询历史成功",  result);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONArray(HttpRequest.SUCCEED_CODE, "查询历史成功",  new JSONArray());
        }


    }


    /**
     *  查询当前所有人所有的待办流程
     * @param
     * @return
     */
    @PostMapping("getAllTasks")
    public JSONObject getAllTasks(){
        try {
            List<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
//            //组任务
//            List<Task> list = processEngine.getTaskService()//
//                    .createTaskQuery()//
//                    .taskCandidateUser(userId)// 参与者，组任务查询
//                    .list();
            //个人任务
            List<Task> todoList = taskService
                    .createTaskQuery()
                    .list();
            ProcessInstance pi = null;
            for (Task task : todoList) {
                //根据taskID查询业务ID
                pi = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(task.getProcessInstanceId())
                        .singleResult();
                HashMap map = new HashMap();
                map.put("assignee",task.getAssignee());
                map.put("id", task.getId());
                map.put("createTime", task.getCreateTime());
                map.put("name", task.getName());
//                map.put("task.executionId",task.getExecutionId());
                map.put("processDefinitionId", task.getProcessDefinitionId());
                map.put("processInstanceId", task.getProcessInstanceId());
                map.put("taskDefinitionKey", task.getTaskDefinitionKey());
                map.put("vars",task.getProcessVariables());
                map.put("BusinessKey",pi.getBusinessKey());
                result.add(map);
            }
            Map<String, Object> resultmap = new HashMap<>();
            resultmap.put("rows",result);
            resultmap.put("total",result.size());
            return ResultUtils.returnJSONObject(HttpRequest.SUCCEED_CODE, "查询流程成功", resultmap);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "查询流程失败", new JSONObject());
        }
    }

    /**
     * 删除人员通知管理员
     */
    @PostMapping("deletethenotice")
    public JSONObject deletethenotice(@RequestParam String userId,@RequestParam String name) {
        try {

            List<Model> models = processEngine.getRepositoryService().createModelQuery().list();
            String content="";
            for(Model model:models) {
                ObjectNode modelNode = null;
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put("name", model.getName());
                }
                modelNode.put("modelId", model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(processEngine.getRepositoryService().getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);
                String json = editorJsonNode.toString();
                if(json.contains(userId)){
                    content+=model.getName()+",";
                    logger.info("有,部署ID为:"+model.getName());

                }
                logger.info("没有");
            }
            if(!"".equals(content)){
                content = content.substring(0,content.length()-1);
            }

            return ResultUtils.returnJSONObject(HttpRequest.SUCCEED_CODE, "通知成功", new JSONObject());
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnJSONObject(HttpRequest.ERROR_CODE, "通知失败！", new JSONObject());
        }

    }
}
