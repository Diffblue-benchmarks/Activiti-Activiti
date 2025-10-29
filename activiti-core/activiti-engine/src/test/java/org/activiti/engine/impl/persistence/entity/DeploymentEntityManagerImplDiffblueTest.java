/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DeploymentQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.ProcessDefinitionQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.DeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.EventSubscriptionDataManager;
import org.activiti.engine.impl.persistence.entity.data.IdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.JobDataManager;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.persistence.entity.data.TimerJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeploymentEntityManagerImplDiffblueTest {
  @Mock
  private DeploymentDataManager deploymentDataManager;

  @InjectMocks
  private DeploymentEntityManagerImpl deploymentEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)}
   */
  @Test
  public void testInsert() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    doNothing().when(deploymentDataManager).insert(Mockito.<DeploymentEntity>any());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager);
    DeploymentEntityImpl deployment = mock(DeploymentEntityImpl.class);
    when(deployment.getResources()).thenReturn(new HashMap<>());

    // Act
    deploymentEntityManagerImpl.insert(deployment);

    // Assert
    verify(deployment).getResources();
    verify(deploymentDataManager).insert(isA(DeploymentEntity.class));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)}
   */
  @Test
  public void testInsert2() {
    // Arrange
    ResourceEntityManager resourceEntityManager = mock(ResourceEntityManager.class);
    doNothing().when(resourceEntityManager).insert(Mockito.<ResourceEntity>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getResourceEntityManager()).thenReturn(resourceEntityManager);
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    doNothing().when(deploymentDataManager).insert(Mockito.<DeploymentEntity>any());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, deploymentDataManager);

    HashMap<String, ResourceEntity> stringResourceEntityMap = new HashMap<>();
    stringResourceEntityMap.put("foo", new ResourceEntityImpl());
    DeploymentEntityImpl deployment = mock(DeploymentEntityImpl.class);
    when(deployment.getId()).thenReturn("42");
    when(deployment.getResources()).thenReturn(stringResourceEntityMap);

    // Act
    deploymentEntityManagerImpl.insert(deployment);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration).getResourceEntityManager();
    verify(deployment).getId();
    verify(deployment).getResources();
    verify(resourceEntityManager).insert(isA(ResourceEntity.class));
    verify(deploymentDataManager).insert(isA(DeploymentEntity.class));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteProcessDefinitionIdentityLinks(String)}
   */
  @Test
  public void testDeleteProcessDefinitionIdentityLinks() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).deleteIdentityLinksByProcDef(Mockito.<String>any());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl = new IdentityLinkEntityManagerImpl(
        processEngineConfigurationImpl, identityLinkDataManager);

    when(processEngineConfigurationImpl.getIdentityLinkEntityManager()).thenReturn(identityLinkEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteProcessDefinitionIdentityLinks("42");

    // Assert
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(identityLinkDataManager).deleteIdentityLinksByProcDef(eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteEventSubscriptions(String)}
   */
  @Test
  public void testDeleteEventSubscriptions() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).deleteEventSubscriptionsForProcessDefinition(Mockito.<String>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl = new EventSubscriptionEntityManagerImpl(
        processEngineConfigurationImpl, eventSubscriptionDataManager);

    when(processEngineConfigurationImpl.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteEventSubscriptions("42");

    // Assert
    verify(processEngineConfigurationImpl).getEventSubscriptionEntityManager();
    verify(eventSubscriptionDataManager).deleteEventSubscriptionsForProcessDefinition(eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  public void testDeleteProcessDefinitionInfo() {
    // Arrange
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = mock(ProcessDefinitionInfoDataManager.class);
    doNothing().when(processDefinitionInfoDataManager).delete(Mockito.<ProcessDefinitionInfoEntity>any());
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfigurationImpl, processDefinitionInfoDataManager);

    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getProcessDefinitionInfoEntityManager())
        .thenReturn(processDefinitionInfoEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getProcessDefinitionInfoEntityManager();
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId(eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  public void testDeleteProcessDefinitionInfo2() {
    // Arrange
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = mock(ProcessDefinitionInfoDataManager.class);
    doNothing().when(processDefinitionInfoDataManager).delete(Mockito.<ProcessDefinitionInfoEntity>any());
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfigurationImpl, processDefinitionInfoDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getProcessDefinitionInfoEntityManager())
        .thenReturn(processDefinitionInfoEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getProcessDefinitionInfoEntityManager();
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId(eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteProcessDefinitionForDeployment(String)}
   */
  @Test
  public void testDeleteProcessDefinitionForDeployment() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    doNothing().when(processDefinitionDataManager).deleteProcessDefinitionsByDeploymentId(Mockito.<String>any());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl = new ProcessDefinitionEntityManagerImpl(
        processEngineConfigurationImpl, processDefinitionDataManager);

    when(processEngineConfigurationImpl.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteProcessDefinitionForDeployment("42");

    // Assert
    verify(processEngineConfigurationImpl).getProcessDefinitionEntityManager();
    verify(processDefinitionDataManager).deleteProcessDefinitionsByDeploymentId(eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteProcessInstancesForProcessDefinitions(List)}
   */
  @Test
  public void testDeleteProcessInstancesForProcessDefinitions() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    doNothing().when(executionEntityManager)
        .deleteProcessInstancesByProcessDefinition(Mockito.<String>any(), Mockito.<String>any(), anyBoolean());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ArrayList<ProcessDefinition> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    deploymentEntityManagerImpl.deleteProcessInstancesForProcessDefinitions(processDefinitions);

    // Assert
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(executionEntityManager).deleteProcessInstancesByProcessDefinition(isNull(), eq("deleted deployment"),
        eq(true));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteProcessInstancesForProcessDefinitions(List)}
   */
  @Test
  public void testDeleteProcessInstancesForProcessDefinitions2() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    doNothing().when(executionEntityManager)
        .deleteProcessInstancesByProcessDefinition(Mockito.<String>any(), Mockito.<String>any(), anyBoolean());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExecutionEntityManager()).thenReturn(executionEntityManager);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ArrayList<ProcessDefinition> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    deploymentEntityManagerImpl.deleteProcessInstancesForProcessDefinitions(processDefinitions);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getExecutionEntityManager();
    verify(executionEntityManager, atLeast(1)).deleteProcessInstancesByProcessDefinition(isNull(),
        eq("deleted deployment"), eq(true));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveRelatedJobs() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByProcessDefinitionId(Mockito.<String>any())).thenReturn(new ArrayList<>());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobEntityManager())
        .thenReturn(new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager));
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeRelatedJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processEngineConfiguration).getJobEntityManager();
    verify(jobDataManager).findJobsByProcessDefinitionId(isNull());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveRelatedJobs2() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<JobEntity> jobEntityList = new ArrayList<>();
    jobEntityList.add(new JobEntityImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findJobsByProcessDefinitionId(Mockito.<String>any())).thenReturn(jobEntityList);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeRelatedJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(jobDataManager).findJobsByProcessDefinitionId(isNull());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveRelatedJobs3() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<JobEntity> jobEntityList = new ArrayList<>();
    jobEntityList.add(new JobEntityImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findJobsByProcessDefinitionId(Mockito.<String>any())).thenReturn(jobEntityList);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeRelatedJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(jobDataManager).findJobsByProcessDefinitionId(isNull());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveRelatedJobs4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    ArrayList<JobEntity> jobEntityList = new ArrayList<>();
    jobEntityList.add(new JobEntityImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findJobsByProcessDefinitionId(Mockito.<String>any())).thenReturn(jobEntityList);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher2 = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher2).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher2.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher2);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeRelatedJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(activitiEventDispatcher2).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher2).isEnabled();
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration2, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(jobDataManager).findJobsByProcessDefinitionId(isNull());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveTimerSuspendProcesDefJobs() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobEntityManager())
        .thenReturn(new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager));
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeTimerSuspendProcesDefJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processEngineConfiguration).getJobEntityManager();
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("suspend-processdefinition"), isNull());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveTimerSuspendProcesDefJobs2() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<JobEntity> jobEntityList = new ArrayList<>();
    jobEntityList.add(new JobEntityImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(jobEntityList);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeTimerSuspendProcesDefJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("suspend-processdefinition"), isNull());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveTimerSuspendProcesDefJobs3() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());

    ArrayList<JobEntity> jobEntityList = new ArrayList<>();
    jobEntityList.add(new JobEntityImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(jobEntityList);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeTimerSuspendProcesDefJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("suspend-processdefinition"), isNull());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveTimerSuspendProcesDefJobs4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    ArrayList<JobEntity> jobEntityList = new ArrayList<>();
    jobEntityList.add(new JobEntityImpl());
    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(jobEntityList);
    JobEntityManagerImpl jobEntityManagerImpl = new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher2 = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher2).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher2.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher2);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeTimerSuspendProcesDefJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(activitiEventDispatcher2).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher2).isEnabled();
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfiguration2, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("suspend-processdefinition"), isNull());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  public void testRemoveTimerStartJobs() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfigurationImpl,
        jobDataManager);

    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.removeTimerStartJobs("42");

    // Assert
    verify(processEngineConfigurationImpl).getTimerJobEntityManager();
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("timer-start-event"), eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  public void testRemoveTimerStartJobs2() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    timerJobEntityList.add(new TimerJobEntityImpl());
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<TimerJobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(timerJobEntityList);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfigurationImpl,
        jobDataManager);

    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.removeTimerStartJobs("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getTimerJobEntityManager();
    verify(jobDataManager).delete(isA(TimerJobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("timer-start-event"), eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  public void testRemoveTimerStartJobs3() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    timerJobEntityList.add(new TimerJobEntityImpl());
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<TimerJobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(timerJobEntityList);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfigurationImpl,
        jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.removeTimerStartJobs("42");

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getTimerJobEntityManager();
    verify(jobDataManager).delete(isA(TimerJobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("timer-start-event"), eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  public void testRemoveTimerStartJobs4() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    timerJobEntityList.add(new TimerJobEntityImpl());
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<TimerJobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(timerJobEntityList);
    TimerJobEntityManagerImpl timerJobEntityManagerImpl = new TimerJobEntityManagerImpl(processEngineConfigurationImpl,
        jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.removeTimerStartJobs("42");

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getTimerJobEntityManager();
    verify(jobDataManager).delete(isA(TimerJobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId(eq("timer-start-event"), eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  public void testRestorePreviousStartEventsIfNeeded() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(new ProcessDefinitionEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager()).thenReturn(
        new ProcessDefinitionEntityManagerImpl(new JtaProcessEngineConfiguration(), processDefinitionDataManager));
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getId()).thenReturn("42");
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn("42");

    // Act
    deploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(processDefinition);

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinition).getId();
    verify(processDefinition).getKey();
    verify(processDefinition, atLeast(1)).getTenantId();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId(eq("Key"), eq("42"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  public void testRestorePreviousStartEventsIfNeeded2() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByQueryCriteria(Mockito.<ProcessDefinitionQueryImpl>any(),
        Mockito.<Page>any())).thenReturn(new ArrayList<>());
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(processDefinitionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager()).thenReturn(
        new ProcessDefinitionEntityManagerImpl(new JtaProcessEngineConfiguration(), processDefinitionDataManager));
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getVersion()).thenReturn(1);
    when(processDefinition.getId()).thenReturn("42");
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn("42");

    // Act
    deploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(processDefinition);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getProcessDefinitionEntityManager();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinition).getId();
    verify(processDefinition, atLeast(1)).getKey();
    verify(processDefinition, atLeast(1)).getTenantId();
    verify(processDefinition).getVersion();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId(eq("Key"), eq("42"));
    verify(processDefinitionDataManager).findProcessDefinitionsByQueryCriteria(isA(ProcessDefinitionQueryImpl.class),
        isA(Page.class));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  public void testRestorePreviousStartEventsIfNeeded3() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager()).thenReturn(
        new ProcessDefinitionEntityManagerImpl(new JtaProcessEngineConfiguration(), processDefinitionDataManager));
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getId()).thenReturn("42");
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn(null);

    // Act
    deploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(processDefinition);

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinition).getId();
    verify(processDefinition).getKey();
    verify(processDefinition).getTenantId();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey(eq("Key"));
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreSignalStartEvent() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(new EventSubscriptionEntityManagerImpl(processEngineConfiguration, eventSubscriptionDataManager));
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = new BpmnModel();
    StartEvent startEvent = new StartEvent();

    // Act
    deploymentEntityManagerImpl.restoreSignalStartEvent(previousProcessDefinition, bpmnModel, startEvent,
        new SignalEventDefinition());

    // Assert
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(isNull());
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreSignalStartEvent2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(new EventSubscriptionEntityManagerImpl(processEngineConfiguration, eventSubscriptionDataManager));
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = new BpmnModel();
    StartEvent startEvent = new StartEvent();

    // Act
    deploymentEntityManagerImpl.restoreSignalStartEvent(previousProcessDefinition, bpmnModel, startEvent,
        new SignalEventDefinition());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(isNull());
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreSignalStartEvent3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    SignalEventSubscriptionEntityImpl signalEventSubscriptionEntityImpl = mock(SignalEventSubscriptionEntityImpl.class);
    when(signalEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(signalEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription()).thenReturn(signalEventSubscriptionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(new EventSubscriptionEntityManagerImpl(processEngineConfiguration, eventSubscriptionDataManager));
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getSignal(Mockito.<String>any())).thenReturn(new Signal("42", "Name"));
    StartEvent startEvent = new StartEvent();

    // Act
    deploymentEntityManagerImpl.restoreSignalStartEvent(previousProcessDefinition, bpmnModel, startEvent,
        new SignalEventDefinition());

    // Assert
    verify(bpmnModel).getSignal(isNull());
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(previousProcessDefinition).getId();
    verify(signalEventSubscriptionEntityImpl).getExecution();
    verify(signalEventSubscriptionEntityImpl).getExecutionId();
    verify(signalEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(signalEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(signalEventSubscriptionEntityImpl).setProcessDefinitionId(eq("42"));
    verify(signalEventSubscriptionEntityImpl).setTenantId(eq("42"));
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(new EventSubscriptionEntityManagerImpl(processEngineConfiguration, eventSubscriptionDataManager));
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = new BpmnModel();
    StartEvent startEvent = new StartEvent();
    MessageEventDefinition eventDefinition = new MessageEventDefinition();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(previousProcessDefinition, bpmnModel, startEvent,
        eventDefinition);

    // Assert
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setConfiguration(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(isNull());
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertNull(eventDefinition.getMessageRef());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(new EventSubscriptionEntityManagerImpl(processEngineConfiguration, eventSubscriptionDataManager));
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = new BpmnModel();
    StartEvent startEvent = new StartEvent();
    MessageEventDefinition eventDefinition = new MessageEventDefinition();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(previousProcessDefinition, bpmnModel, startEvent,
        eventDefinition);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setConfiguration(isNull());
    verify(messageEventSubscriptionEntityImpl).setEventName(isNull());
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(isNull());
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq(""));
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertNull(eventDefinition.getMessageRef());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(new EventSubscriptionEntityManagerImpl(processEngineConfiguration, eventSubscriptionDataManager));
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(new Message("42", "Name", "Item Ref"));
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);
    StartEvent startEvent = new StartEvent();
    MessageEventDefinition eventDefinition = new MessageEventDefinition();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(previousProcessDefinition, bpmnModel, startEvent,
        eventDefinition);

    // Assert
    verify(bpmnModel).containsMessageId(isNull());
    verify(bpmnModel).getMessage(isNull());
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(previousProcessDefinition, atLeast(1)).getId();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setConfiguration(eq("42"));
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(eq("42"));
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq("42"));
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertEquals("Name", eventDefinition.getMessageRef());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(new EventSubscriptionEntityManagerImpl(processEngineConfiguration, eventSubscriptionDataManager));
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn(null);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(new Message("42", "Name", "Item Ref"));
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);
    StartEvent startEvent = new StartEvent();
    MessageEventDefinition eventDefinition = new MessageEventDefinition();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(previousProcessDefinition, bpmnModel, startEvent,
        eventDefinition);

    // Assert
    verify(bpmnModel).containsMessageId(isNull());
    verify(bpmnModel).getMessage(isNull());
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(previousProcessDefinition, atLeast(1)).getId();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(isNull());
    verify(messageEventSubscriptionEntityImpl).setConfiguration(eq("42"));
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(eq("42"));
    verify(previousProcessDefinition).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertEquals("Name", eventDefinition.getMessageRef());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getPerformanceSettings()).thenReturn(performanceSettings);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntityImpl = mock(
        MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntityImpl.getExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(messageEventSubscriptionEntityImpl.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntityImpl).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntityImpl).setTenantId(Mockito.<String>any());
    EventSubscriptionDataManager eventSubscriptionDataManager = mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription()).thenReturn(messageEventSubscriptionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(new EventSubscriptionEntityManagerImpl(processEngineConfiguration, eventSubscriptionDataManager));
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration2, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(new Message("42", "Name", "Item Ref"));
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);
    StartEvent startEvent = mock(StartEvent.class);
    when(startEvent.getId()).thenReturn("42");
    MessageEventDefinition eventDefinition = new MessageEventDefinition();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(previousProcessDefinition, bpmnModel, startEvent,
        eventDefinition);

    // Assert
    verify(startEvent).getId();
    verify(bpmnModel).containsMessageId(isNull());
    verify(bpmnModel).getMessage(isNull());
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getPerformanceSettings();
    verify(previousProcessDefinition, atLeast(1)).getId();
    verify(messageEventSubscriptionEntityImpl).getExecution();
    verify(messageEventSubscriptionEntityImpl).getExecutionId();
    verify(messageEventSubscriptionEntityImpl).setActivityId(eq("42"));
    verify(messageEventSubscriptionEntityImpl).setConfiguration(eq("42"));
    verify(messageEventSubscriptionEntityImpl).setEventName(eq("Name"));
    verify(messageEventSubscriptionEntityImpl).setProcessDefinitionId(eq("42"));
    verify(messageEventSubscriptionEntityImpl).setTenantId(eq("42"));
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertEquals("Name", eventDefinition.getMessageRef());
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}
   */
  @Test
  public void testFindLatestProcessDefinition() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager()).thenReturn(
        new ProcessDefinitionEntityManagerImpl(new JtaProcessEngineConfiguration(), processDefinitionDataManager));
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionResult = deploymentEntityManagerImpl
        .findLatestProcessDefinition(processDefinition);

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey(isNull());
    assertTrue(processDefinition.definitionIdentityLinkEntities.isEmpty());
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionResult);
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}
   */
  @Test
  public void testFindLatestProcessDefinition2() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager = mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(Mockito.<String>any(),
        Mockito.<String>any())).thenReturn(processDefinitionEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager()).thenReturn(
        new ProcessDefinitionEntityManagerImpl(new JtaProcessEngineConfiguration(), processDefinitionDataManager));
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn("42");

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionResult = deploymentEntityManagerImpl
        .findLatestProcessDefinition(processDefinition);

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinition).getKey();
    verify(processDefinition, atLeast(1)).getTenantId();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId(eq("Key"), eq("42"));
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionResult);
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findLatestDeploymentByName(String)}
   */
  @Test
  public void testFindLatestDeploymentByName() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    when(deploymentDataManager.findLatestDeploymentByName(Mockito.<String>any())).thenReturn(deploymentEntityImpl);

    // Act
    DeploymentEntity actualFindLatestDeploymentByNameResult = deploymentEntityManagerImpl
        .findLatestDeploymentByName("Deployment Name");

    // Assert
    verify(deploymentDataManager).findLatestDeploymentByName(eq("Deployment Name"));
    assertSame(deploymentEntityImpl, actualFindLatestDeploymentByNameResult);
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentByVersion(Integer)}
   */
  @Test
  public void testFindDeploymentByVersion() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    when(deploymentDataManager.findDeploymentByVersion(Mockito.<Integer>any())).thenReturn(deploymentEntityImpl);

    // Act
    DeploymentEntity actualFindDeploymentByVersionResult = (new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager)).findDeploymentByVersion(1);

    // Assert
    verify(deploymentDataManager).findDeploymentByVersion(eq(1));
    assertSame(deploymentEntityImpl, actualFindDeploymentByVersionResult);
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentCountByQueryCriteria(DeploymentQueryImpl)}
   */
  @Test
  public void testFindDeploymentCountByQueryCriteria() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findDeploymentCountByQueryCriteria(Mockito.<DeploymentQueryImpl>any())).thenReturn(3L);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager);

    // Act
    long actualFindDeploymentCountByQueryCriteriaResult = deploymentEntityManagerImpl
        .findDeploymentCountByQueryCriteria(new DeploymentQueryImpl());

    // Assert
    verify(deploymentDataManager).findDeploymentCountByQueryCriteria(isA(DeploymentQueryImpl.class));
    assertEquals(3L, actualFindDeploymentCountByQueryCriteriaResult);
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentsByQueryCriteria(DeploymentQueryImpl, Page)}
   */
  @Test
  public void testFindDeploymentsByQueryCriteria() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    ArrayList<Deployment> deploymentList = new ArrayList<>();
    when(deploymentDataManager.findDeploymentsByQueryCriteria(Mockito.<DeploymentQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(deploymentList);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager);
    DeploymentQueryImpl deploymentQuery = new DeploymentQueryImpl();

    // Act
    List<Deployment> actualFindDeploymentsByQueryCriteriaResult = deploymentEntityManagerImpl
        .findDeploymentsByQueryCriteria(deploymentQuery, new Page(1, 3));

    // Assert
    verify(deploymentDataManager).findDeploymentsByQueryCriteria(isA(DeploymentQueryImpl.class), isA(Page.class));
    assertTrue(actualFindDeploymentsByQueryCriteriaResult.isEmpty());
    assertSame(deploymentList, actualFindDeploymentsByQueryCriteriaResult);
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#getDeploymentResourceNames(String)}
   */
  @Test
  public void testGetDeploymentResourceNames() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    when(deploymentDataManager.getDeploymentResourceNames(Mockito.<String>any())).thenReturn(stringList);

    // Act
    List<String> actualDeploymentResourceNames = deploymentEntityManagerImpl.getDeploymentResourceNames("42");

    // Assert
    verify(deploymentDataManager).getDeploymentResourceNames(eq("42"));
    assertTrue(actualDeploymentResourceNames.isEmpty());
    assertSame(stringList, actualDeploymentResourceNames);
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentsByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindDeploymentsByNativeQuery() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    ArrayList<Deployment> deploymentList = new ArrayList<>();
    when(deploymentDataManager.findDeploymentsByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(deploymentList);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager);

    // Act
    List<Deployment> actualFindDeploymentsByNativeQueryResult = deploymentEntityManagerImpl
        .findDeploymentsByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(deploymentDataManager).findDeploymentsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindDeploymentsByNativeQueryResult.isEmpty());
    assertSame(deploymentList, actualFindDeploymentsByNativeQueryResult);
  }

  /**
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentCountByNativeQuery(Map)}
   */
  @Test
  public void testFindDeploymentCountByNativeQuery() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findDeploymentCountByNativeQuery(Mockito.<Map<String, Object>>any())).thenReturn(3L);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager);

    // Act
    long actualFindDeploymentCountByNativeQueryResult = deploymentEntityManagerImpl
        .findDeploymentCountByNativeQuery(new HashMap<>());

    // Assert
    verify(deploymentDataManager).findDeploymentCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindDeploymentCountByNativeQueryResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link DeploymentEntityManagerImpl#DeploymentEntityManagerImpl(ProcessEngineConfigurationImpl, DeploymentDataManager)}
   *   <li>
   * {@link DeploymentEntityManagerImpl#setDeploymentDataManager(DeploymentDataManager)}
   *   <li>{@link DeploymentEntityManagerImpl#getDataManager()}
   *   <li>{@link DeploymentEntityManagerImpl#getDeploymentDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    DeploymentEntityManagerImpl actualDeploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        processEngineConfiguration, new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    MybatisDeploymentDataManager deploymentDataManager = new MybatisDeploymentDataManager(
        new JtaProcessEngineConfiguration());
    actualDeploymentEntityManagerImpl.setDeploymentDataManager(deploymentDataManager);
    DataManager<DeploymentEntity> actualDataManager = actualDeploymentEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(deploymentDataManager, actualDataManager);
    assertSame(deploymentDataManager, actualDeploymentEntityManagerImpl.getDeploymentDataManager());
  }
}
