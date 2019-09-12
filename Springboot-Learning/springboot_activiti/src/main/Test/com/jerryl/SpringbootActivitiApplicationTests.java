package com.jerryl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jerryl.util.IdGen;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootActivitiApplicationTests {

    @Autowired
    ProcessEngine processEngine;
    @Autowired
    IdGen idGen;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 1：部署一个Activiti流程
     * 运行成功后，查看之前的数据库表，就会发现多了很多内容
     */
    @Test
    public void creatActivitiTask(){
        //加载的那两个内容就是我们之前已经弄好的基础内容哦。
        //得到了流程引擎
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("processes/shenqing.bpmn")
                .addClasspathResource("processes/shenqing.png")
                .deploy();
    }
    /**
     * 2：启动流程实例
     */
    @Test
    public void testStartProcessInstance(){
        /**
         * 流程变量
         *   给<userTask id="请假申请" name="请假申请" activiti:assignee="#{student}"></userTask>
         *     的student赋值
         */
        String business_key="shengqing"+idGen.getNextId();
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("user", "小明");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("process:2:3b8875d9024140d1a0da1d650b42d179",business_key);

//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        processEngine.getRuntimeService()
//                .startProcessInstanceById("process:1:566d013f3d124f1f90b21a2cbe5ce22f");  //这个是查看数据库中act_re_procdef表
    }
    /**
     * 完成请假申请
     */
    @Test
    public void testQingjia(){
        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("admin", "班主任");
        String [] candidateUsers={"班主任1","班主任2","班主任3"};
        variables.put("admins",  Arrays.asList(candidateUsers));
        processEngine.getTaskService()
                .complete("296dfe9619634559acc33bc993b4e9ea", variables); //完成任务的同时设置流程变量

//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        processEngine.getTaskService()
//                .complete("871367b477a045d5980b4c925abf90f5"); //查看act_ru_task表
    }


    /**
     *
     *
     *<p>Description:流程驳回</p>
     *
     * @author：SongJia
     *
     * @date: 2017-3-24下午2:57:08
     *
     */
    @Test
    public void processReject(){
        String taskId="b8c6502ec23b48ebb1c57cbee50a7cd4";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        TaskService taskService = processEngine.getTaskService();
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
        } catch (Exception e) {
            //失败
        }
    }

    /**
     * 小明学习的班主任小毛查询当前正在执行任务
     */
    @Test
    public void testQueryTask(){
        //下面代码中的小毛，就是我们之前设计那个流程图中添加的班主任内容
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskAssignee("班主任")
                .list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }
    }
    /**查询组任务*/
    @Test
    public void findGroupTaskList() {
        // 任务办理人
        String candidateUser = "班主任2";
        List<Task> list = processEngine.getTaskService()//
                .createTaskQuery()//
                .taskCandidateUser(candidateUser)// 参与者，组任务查询
                .list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID：" + task.getId());
                System.out.println("任务的办理人：" + task.getAssignee());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务的创建时间：" + task.getCreateTime());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("#######################################");
            }
        }
    }


    /**
     * 完成任务
     */
    @Test
    public void testFinishTask_manager(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
//        engine.getTaskService().claim("32bc7fb1ca6a4208917bd1eadcf01890","小李");
//                //查看act_ru_task数据表
        processEngine.getTaskService().complete("32bc7fb1ca6a4208917bd1eadcf01890");
    }

    /**
     * 教务处的大毛完成的任务
     */
    @Test
    public void testFinishTask_Boss(){
        processEngine.getTaskService().complete("4232de9af81e4535a008477861f850fc");  //查看act_ru_task数据表
    }


     /**
       *taskId:任务id
       *opinion：意见
       *
       */
  @Test
  public void endTask(){
        String taskId="203ad3462f9244a39b7572718271fbde";
        String opinion="结束";
  //根据taskID查询任务   
  Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
    
  ActivityImpl endActivity = findActivitiImpl(taskId, "end");
  
  if(endActivity==null){
   return; 
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
      ActivityImpl currActivity = findActivitiImpl(taskId, null); 
     
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
      ActivityImpl pointActivity = findActivitiImpl(taskId, endActivity.getId()); 
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
   
 }
    /**
       * 根据任务ID和节点ID获取活动节点 <br>
       *
       * @param taskId
       *      任务ID
       * @param activityId
       *      活动节点ID <br>
       *      如果为null或""，则默认查询当前活动节点 <br>
       *      如果为"end"，则查询结束节点 <br>
       *
       * @return
       * @throws Exception
       */
    private ActivityImpl findActivitiImpl(String taskId, String activityId){
   TaskEntity task = (TaskEntity) processEngine.getTaskService().createTaskQuery().taskId(
                        taskId).singleResult();
    if (task == null) {
     return null;
    }
    // 取得流程定义
    ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) processEngine.getRepositoryService())
        .getDeployedProcessDefinition(task.getProcessDefinitionId());

    if (processDefinition == null) {
      //throw new Exception("流程定义未找到!");
     return null;
    }

    // 获取当前活动节点ID
    if (StringUtils.isEmpty(activityId)) {
      activityId = task.getTaskDefinitionKey();
    }

    // 根据流程定义，获取该流程实例的结束节点
    if (activityId.toUpperCase().equals("END")) {
      for (ActivityImpl activityImpl : processDefinition.getActivities()) {
        List<PvmTransition> pvmTransitionList = activityImpl
            .getOutgoingTransitions();
        if (pvmTransitionList.isEmpty()) {
          return activityImpl;
        }
      }
    }

    // 根据节点ID，获取对应的活动节点
    ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
        .findActivity(activityId);

    return activityImpl;
  }



    /**
     * 2：启动流程实例
     */
    @Test
    public void test1(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceById("process:1:d230b53fc3004feface9e173353c7ecc");  //这个是查看数据库中act_re_procdef表
    }

    @Test
    public void test2(){
        ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery().active().orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.list();

    }
    /*
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition1(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义查询
                /*指定查询条件,where条件*/
                //.deploymentId("process:2:3b8875d9024140d1a0da1d650b42d179")//使用部署对象ID查询
                .processDefinitionId("process:2:3b8875d9024140d1a0da1d650b42d179")//使用流程定义ID查询
                //.processDefinitionKey("3b8875d9024140d1a0da1d650b42d179")//使用流程定义的KEY查询
                //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
                /*排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                .list();//返回一个集合列表，封装流程定义
        //.singleResult();//返回唯一结果集
        //.count();//返回结果集数量
        //.listPage(firstResult, maxResults)//分页查询

        if(list != null && list.size()>0){
            for(ProcessDefinition processDefinition:list){
                System.out.println("流程定义ID:"+processDefinition.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义名称:"+processDefinition.getName());//对应HelloWorld.bpmn文件中的name属性值
                System.out.println("流程定义的key:"+processDefinition.getKey());//对应HelloWorld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+processDefinition.getVersion());//当流程定义的key值相同的情况下，版本升级，默认从1开始
                System.out.println("资源名称bpmn文件:"+processDefinition.getResourceName());
                System.out.println("资源名称png文件:"+processDefinition.getDiagramResourceName());
                System.out.println("部署对象ID:"+processDefinition.getDeploymentId());
                System.out.println("################################");
            }
        }

    }


    /*
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition(){
        String modelId="159acf043a674e9785360988bf1e1956";
        ObjectNode modelNode = null;

        Model model = processEngine.getRepositoryService().getModel(modelId);

        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
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
               System.out.println(json);
                JsonNode jsonNode = editorJsonNode.get("childShapes");
                System.out.println(jsonNode);

            } catch (Exception e) {
                System.out.println("Error creating model JSON");
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
    }

}
