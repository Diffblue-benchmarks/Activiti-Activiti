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
import org.activiti.bpmn.model.BaseElement;
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
   * Test getters and setters.
   * <p>
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

  /**
   * Test {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)} with
   * {@code DeploymentEntity}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link DeploymentEntityImpl#getResources()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)}
   */
  @Test
  public void testInsertWithDeploymentEntity_givenHashMap_thenCallsGetResources() {
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
   * Test {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)} with
   * {@code DeploymentEntity}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)}
   */
  @Test
  public void testInsertWithDeploymentEntity_thenCallsGetEventDispatcher() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#deleteProcessDefinitionIdentityLinks(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteProcessDefinitionIdentityLinks(String)}
   */
  @Test
  public void testDeleteProcessDefinitionIdentityLinks_thenCallsGetIdentityLinkEntityManager() {
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
   * Test {@link DeploymentEntityManagerImpl#deleteEventSubscriptions(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteEventSubscriptions(String)}
   */
  @Test
  public void testDeleteEventSubscriptions_thenCallsGetEventSubscriptionEntityManager() {
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
   * Test {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   * <p>
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
   * Test {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  public void testDeleteProcessDefinitionInfo_thenCallsIsEnabled() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#deleteProcessDefinitionForDeployment(String)}.
   * <p>
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
   * Test
   * {@link DeploymentEntityManagerImpl#deleteProcessInstancesForProcessDefinitions(List)}.
   * <p>
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
   * Test
   * {@link DeploymentEntityManagerImpl#deleteProcessInstancesForProcessDefinitions(List)}.
   * <p>
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
   * Test
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}.
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveRelatedJobs() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveRelatedJobs_thenCallsDispatchEvent() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveRelatedJobs_thenCallsDispatchEvent2() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getJobEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveRelatedJobs_thenCallsGetJobEntityManager() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}.
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveTimerSuspendProcesDefJobs() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveTimerSuspendProcesDefJobs_thenCallsDispatchEvent() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveTimerSuspendProcesDefJobs_thenCallsDispatchEvent2() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getJobEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  public void testRemoveTimerSuspendProcesDefJobs_thenCallsGetJobEntityManager() {
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
   * Test {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}.
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  public void testRemoveTimerStartJobs() {
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
   * Test {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  public void testRemoveTimerStartJobs_thenCallsDispatchEvent() {
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
   * Test {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  public void testRemoveTimerStartJobs_thenCallsGetTimerJobEntityManager() {
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
   * Test {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  public void testRemoveTimerStartJobs_thenCallsIsEnabled() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   * <p>
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
   * Test
   * {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessDefinitionDataManager#findLatestProcessDefinitionByKey(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  public void testRestorePreviousStartEventsIfNeeded_thenCallsFindLatestProcessDefinitionByKey() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls {@link ProcessDefinitionEntityImpl#getVersion()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  public void testRestorePreviousStartEventsIfNeeded_thenCallsGetVersion() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}.
   * <p>
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
   * Test
   * {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreSignalStartEvent_givenActivitiEventDispatcherIsEnabledReturnFalse() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}.
   * <ul>
   *   <li>Then calls {@link BpmnModel#getSignal(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreSignalStartEvent_thenCallsGetSignal() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}.
   * <p>
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
   * Test
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent_givenActivitiEventDispatcherIsEnabledReturnFalse() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent_givenNull() {
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
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message buildResult = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(buildResult);
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
   * Test
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}.
   * <ul>
   *   <li>Then {@link MessageEventDefinition} (default constructor) MessageRef is
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent_thenMessageEventDefinitionMessageRefIsName() {
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
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message buildResult = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(buildResult);
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
   * Test
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}.
   * <ul>
   *   <li>When {@link StartEvent} {@link BaseElement#getId()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)}
   */
  @Test
  public void testRestoreMessageStartEvent_whenStartEventGetIdReturn42_thenCallsGetId() {
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
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message buildResult = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(buildResult);
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
   * Test
   * {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls
   * {@link ProcessDefinitionDataManager#findLatestProcessDefinitionByKey(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}
   */
  @Test
  public void testFindLatestProcessDefinition_thenCallsFindLatestProcessDefinitionByKey() {
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

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionResult = deploymentEntityManagerImpl
        .findLatestProcessDefinition(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey(isNull());
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionResult);
  }

  /**
   * Test
   * {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}.
   * <ul>
   *   <li>Then calls {@link ProcessDefinitionEntityImpl#getKey()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}
   */
  @Test
  public void testFindLatestProcessDefinition_thenCallsGetKey() {
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
   * Test {@link DeploymentEntityManagerImpl#findLatestDeploymentByName(String)}.
   * <p>
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
   * Test {@link DeploymentEntityManagerImpl#findDeploymentByVersion(Integer)}.
   * <ul>
   *   <li>Then return {@link DeploymentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentByVersion(Integer)}
   */
  @Test
  public void testFindDeploymentByVersion_thenReturnDeploymentEntityImpl() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#findDeploymentCountByQueryCriteria(DeploymentQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentCountByQueryCriteria(DeploymentQueryImpl)}
   */
  @Test
  public void testFindDeploymentCountByQueryCriteria_thenReturnThree() {
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
   * Test
   * {@link DeploymentEntityManagerImpl#findDeploymentsByQueryCriteria(DeploymentQueryImpl, Page)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentsByQueryCriteria(DeploymentQueryImpl, Page)}
   */
  @Test
  public void testFindDeploymentsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findDeploymentsByQueryCriteria(Mockito.<DeploymentQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager);
    DeploymentQueryImpl deploymentQuery = new DeploymentQueryImpl();

    // Act
    List<Deployment> actualFindDeploymentsByQueryCriteriaResult = deploymentEntityManagerImpl
        .findDeploymentsByQueryCriteria(deploymentQuery, new Page(1, 3));

    // Assert
    verify(deploymentDataManager).findDeploymentsByQueryCriteria(isA(DeploymentQueryImpl.class), isA(Page.class));
    assertTrue(actualFindDeploymentsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#getDeploymentResourceNames(String)}.
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#getDeploymentResourceNames(String)}
   */
  @Test
  public void testGetDeploymentResourceNames() {
    // Arrange
    when(deploymentDataManager.getDeploymentResourceNames(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<String> actualDeploymentResourceNames = deploymentEntityManagerImpl.getDeploymentResourceNames("42");

    // Assert
    verify(deploymentDataManager).getDeploymentResourceNames(eq("42"));
    assertTrue(actualDeploymentResourceNames.isEmpty());
  }

  /**
   * Test
   * {@link DeploymentEntityManagerImpl#findDeploymentsByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentsByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindDeploymentsByNativeQuery_thenReturnEmpty() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findDeploymentsByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl = new DeploymentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), deploymentDataManager);

    // Act
    List<Deployment> actualFindDeploymentsByNativeQueryResult = deploymentEntityManagerImpl
        .findDeploymentsByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(deploymentDataManager).findDeploymentsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindDeploymentsByNativeQueryResult.isEmpty());
  }

  /**
   * Test
   * {@link DeploymentEntityManagerImpl#findDeploymentCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeploymentEntityManagerImpl#findDeploymentCountByNativeQuery(Map)}
   */
  @Test
  public void testFindDeploymentCountByNativeQuery_thenReturnThree() {
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
}
