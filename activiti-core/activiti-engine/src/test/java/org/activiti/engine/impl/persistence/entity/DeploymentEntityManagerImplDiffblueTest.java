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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
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
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeploymentEntityManagerImplDiffblueTest {
  @Mock private DeploymentDataManager deploymentDataManager;

  @InjectMocks private DeploymentEntityManagerImpl deploymentEntityManagerImpl;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       DeploymentEntityManagerImpl#DeploymentEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       DeploymentDataManager)}
   *   <li>{@link DeploymentEntityManagerImpl#setDeploymentDataManager(DeploymentDataManager)}
   *   <li>{@link DeploymentEntityManagerImpl#getDataManager()}
   *   <li>{@link DeploymentEntityManagerImpl#getDeploymentDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, DeploymentDataManager)",
    "DataManager DeploymentEntityManagerImpl.getDataManager()",
    "DeploymentDataManager DeploymentEntityManagerImpl.getDeploymentDataManager()",
    "void DeploymentEntityManagerImpl.setDeploymentDataManager(DeploymentDataManager)"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    DeploymentEntityManagerImpl actualDeploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    MybatisDeploymentDataManager deploymentDataManager =
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration());
    actualDeploymentEntityManagerImpl.setDeploymentDataManager(deploymentDataManager);
    DataManager<DeploymentEntity> actualDataManager =
        actualDeploymentEntityManagerImpl.getDataManager();

    // Assert
    assertSame(deploymentDataManager, actualDataManager);
    assertSame(deploymentDataManager, actualDeploymentEntityManagerImpl.getDeploymentDataManager());
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)} with {@code
   * DeploymentEntity}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link DeploymentEntityImpl#getResources()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.insert(DeploymentEntity)"})
  public void testInsertWithDeploymentEntity_givenHashMap_thenCallsGetResources() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    doNothing().when(deploymentDataManager).insert(Mockito.<DeploymentEntity>any());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(new JtaProcessEngineConfiguration(), deploymentDataManager);

    DeploymentEntityImpl deployment = mock(DeploymentEntityImpl.class);
    when(deployment.getResources()).thenReturn(new HashMap<>());

    // Act
    deploymentEntityManagerImpl.insert(deployment);

    // Assert
    verify(deployment).getResources();
    verify(deploymentDataManager).insert(isA(DeploymentEntity.class));
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)} with {@code
   * DeploymentEntity}.
   *
   * <ul>
   *   <li>Then calls {@link DeploymentEntityImpl#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#insert(DeploymentEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.insert(DeploymentEntity)"})
  public void testInsertWithDeploymentEntity_thenCallsGetId() {
    // Arrange
    ResourceEntityManager resourceEntityManager = mock(ResourceEntityManager.class);
    doNothing().when(resourceEntityManager).insert(Mockito.<ResourceEntity>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setResourceEntityManager(resourceEntityManager);

    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    doNothing().when(deploymentDataManager).insert(Mockito.<DeploymentEntity>any());

    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(processEngineConfiguration, deploymentDataManager);

    HashMap<String, ResourceEntity> stringResourceEntityMap = new HashMap<>();
    stringResourceEntityMap.put("foo", new ResourceEntityImpl());

    DeploymentEntityImpl deployment = mock(DeploymentEntityImpl.class);
    when(deployment.getId()).thenReturn("42");
    when(deployment.getResources()).thenReturn(stringResourceEntityMap);

    // Act
    deploymentEntityManagerImpl.insert(deployment);

    // Assert
    verify(deployment).getId();
    verify(deployment).getResources();
    verify(resourceEntityManager).insert(isA(ResourceEntity.class));
    verify(deploymentDataManager).insert(isA(DeploymentEntity.class));
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#deleteProcessDefinitionIdentityLinks(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getIdentityLinkEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#deleteProcessDefinitionIdentityLinks(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.deleteProcessDefinitionIdentityLinks(String)"
  })
  public void testDeleteProcessDefinitionIdentityLinks_thenCallsGetIdentityLinkEntityManager() {
    // Arrange
    IdentityLinkDataManager identityLinkDataManager = mock(IdentityLinkDataManager.class);
    doNothing().when(identityLinkDataManager).deleteIdentityLinksByProcDef(Mockito.<String>any());
    IdentityLinkEntityManagerImpl identityLinkEntityManagerImpl =
        new IdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), identityLinkDataManager);
    when(processEngineConfigurationImpl.getIdentityLinkEntityManager())
        .thenReturn(identityLinkEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteProcessDefinitionIdentityLinks("42");

    // Assert
    verify(processEngineConfigurationImpl).getIdentityLinkEntityManager();
    verify(identityLinkDataManager).deleteIdentityLinksByProcDef("42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#deleteEventSubscriptions(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getEventSubscriptionEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#deleteEventSubscriptions(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.deleteEventSubscriptions(String)"})
  public void testDeleteEventSubscriptions_thenCallsGetEventSubscriptionEntityManager() {
    // Arrange
    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing()
        .when(eventSubscriptionDataManager)
        .deleteEventSubscriptionsForProcessDefinition(Mockito.<String>any());
    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), eventSubscriptionDataManager);
    when(processEngineConfigurationImpl.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteEventSubscriptions("42");

    // Assert
    verify(processEngineConfigurationImpl).getEventSubscriptionEntityManager();
    verify(eventSubscriptionDataManager).deleteEventSubscriptionsForProcessDefinition("42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessDefinitionInfoDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.deleteProcessDefinitionInfo(String)"})
  public void testDeleteProcessDefinitionInfo_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    doNothing()
        .when(processDefinitionInfoDataManager)
        .delete(Mockito.<ProcessDefinitionInfoEntity>any());
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(
            Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration, processDefinitionInfoDataManager);
    when(processEngineConfigurationImpl.getProcessDefinitionInfoEntityManager())
        .thenReturn(processDefinitionInfoEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(processEngineConfigurationImpl).getProcessDefinitionInfoEntityManager();
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId("42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.deleteProcessDefinitionInfo(String)"})
  public void testDeleteProcessDefinitionInfo_thenCallsDeleteProcessDefinitionInfo() {
    // Arrange
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        mock(ProcessDefinitionInfoEntityManagerImpl.class);
    doNothing()
        .when(processDefinitionInfoEntityManagerImpl)
        .deleteProcessDefinitionInfo(Mockito.<String>any());
    when(processEngineConfigurationImpl.getProcessDefinitionInfoEntityManager())
        .thenReturn(processDefinitionInfoEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(processEngineConfigurationImpl).getProcessDefinitionInfoEntityManager();
    verify(processDefinitionInfoEntityManagerImpl).deleteProcessDefinitionInfo("42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#deleteProcessDefinitionForDeployment(String)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#deleteProcessDefinitionForDeployment(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.deleteProcessDefinitionForDeployment(String)"
  })
  public void testDeleteProcessDefinitionForDeployment() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    doNothing()
        .when(processDefinitionDataManager)
        .deleteProcessDefinitionsByDeploymentId(Mockito.<String>any());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);
    when(processEngineConfigurationImpl.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.deleteProcessDefinitionForDeployment("42");

    // Assert
    verify(processEngineConfigurationImpl).getProcessDefinitionEntityManager();
    verify(processDefinitionDataManager).deleteProcessDefinitionsByDeploymentId("42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#deleteProcessInstancesForProcessDefinitions(List)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#deleteProcessInstancesForProcessDefinitions(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.deleteProcessInstancesForProcessDefinitions(List)"
  })
  public void testDeleteProcessInstancesForProcessDefinitions() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    doNothing()
        .when(executionEntityManager)
        .deleteProcessInstancesByProcessDefinition(
            Mockito.<String>any(), Mockito.<String>any(), anyBoolean());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExecutionEntityManager(executionEntityManager);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ArrayList<ProcessDefinition> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act
    deploymentEntityManagerImpl.deleteProcessInstancesForProcessDefinitions(processDefinitions);

    // Assert
    verify(executionEntityManager)
        .deleteProcessInstancesByProcessDefinition(null, "deleted deployment", true);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.removeRelatedJobs(ProcessDefinition)"})
  public void testRemoveRelatedJobs_thenCallsDispatchEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ArrayList<JobEntity> jobEntityList = new ArrayList<>();
    jobEntityList.add(new JobEntityImpl());

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findJobsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(jobEntityList);

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeRelatedJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(jobDataManager).findJobsByProcessDefinitionId(null);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getJobEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#removeRelatedJobs(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.removeRelatedJobs(ProcessDefinition)"})
  public void testRemoveRelatedJobs_thenCallsGetJobEntityManager() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeRelatedJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processEngineConfiguration).getJobEntityManager();
    verify(jobDataManager).findJobsByProcessDefinitionId(null);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.removeTimerSuspendProcesDefJobs(ProcessDefinition)"
  })
  public void testRemoveTimerSuspendProcesDefJobs_thenCallsDispatchEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ArrayList<JobEntity> jobEntityList = new ArrayList<>();
    jobEntityList.add(new JobEntityImpl());

    JobDataManager jobDataManager = mock(JobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<JobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(jobEntityList);

    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(processEngineConfiguration, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration2.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeTimerSuspendProcesDefJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getJobEntityManager();
    verify(jobDataManager).delete(isA(JobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId("suspend-processdefinition", null);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getJobEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#removeTimerSuspendProcesDefJobs(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.removeTimerSuspendProcesDefJobs(ProcessDefinition)"
  })
  public void testRemoveTimerSuspendProcesDefJobs_thenCallsGetJobEntityManager() {
    // Arrange
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    deploymentEntityManagerImpl.removeTimerSuspendProcesDefJobs(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processEngineConfiguration).getJobEntityManager();
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId("suspend-processdefinition", null);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}.
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.removeTimerStartJobs(String)"})
  public void testRemoveTimerStartJobs() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    timerJobEntityList.add(new TimerJobEntityImpl());

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<TimerJobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(timerJobEntityList);

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfigurationImpl, jobDataManager);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getTimerJobEntityManager())
        .thenReturn(timerJobEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.removeTimerStartJobs("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getTimerJobEntityManager();
    verify(jobDataManager).delete(isA(TimerJobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId("timer-start-event", "42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.removeTimerStartJobs(String)"})
  public void testRemoveTimerStartJobs_thenCallsDispatchEvent() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    timerJobEntityList.add(new TimerJobEntityImpl());

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<TimerJobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(timerJobEntityList);

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfigurationImpl, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTimerJobEntityManager())
        .thenReturn(timerJobEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.removeTimerStartJobs("42");

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getTimerJobEntityManager();
    verify(jobDataManager).delete(isA(TimerJobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId("timer-start-event", "42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getTimerJobEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.removeTimerStartJobs(String)"})
  public void testRemoveTimerStartJobs_thenCallsGetTimerJobEntityManager() {
    // Arrange
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);
    when(processEngineConfigurationImpl.getTimerJobEntityManager())
        .thenReturn(timerJobEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.removeTimerStartJobs("42");

    // Assert
    verify(processEngineConfigurationImpl).getTimerJobEntityManager();
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId("timer-start-event", "42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#removeTimerStartJobs(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeploymentEntityManagerImpl.removeTimerStartJobs(String)"})
  public void testRemoveTimerStartJobs_thenCallsIsEnabled() {
    // Arrange
    ArrayList<TimerJobEntity> timerJobEntityList = new ArrayList<>();
    timerJobEntityList.add(new TimerJobEntityImpl());

    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    doNothing().when(jobDataManager).delete(Mockito.<TimerJobEntity>any());
    when(jobDataManager.findJobsByTypeAndProcessDefinitionId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(timerJobEntityList);

    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(processEngineConfigurationImpl, jobDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getTimerJobEntityManager())
        .thenReturn(timerJobEntityManagerImpl);

    // Act
    deploymentEntityManagerImpl.removeTimerStartJobs("42");

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getTimerJobEntityManager();
    verify(jobDataManager).delete(isA(TimerJobEntity.class));
    verify(jobDataManager).findJobsByTypeAndProcessDefinitionId("timer-start-event", "42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(ProcessDefinition)"
  })
  public void testRestorePreviousStartEventsIfNeeded() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

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
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId("Key", "42");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(ProcessDefinition)"
  })
  public void testRestorePreviousStartEventsIfNeeded2() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");

    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByQueryCriteria(
            Mockito.<ProcessDefinitionQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

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
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId("Key", "42");
    verify(processDefinitionDataManager)
        .findProcessDefinitionsByQueryCriteria(
            isA(ProcessDefinitionQueryImpl.class), isA(Page.class));
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(ProcessDefinition)"
  })
  public void testRestorePreviousStartEventsIfNeeded3() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

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
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey("Key");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(ProcessDefinition)"
  })
  public void testRestorePreviousStartEventsIfNeeded4() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");

    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByQueryCriteria(
            Mockito.<ProcessDefinitionQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getVersion()).thenReturn(1);
    when(processDefinition.getId()).thenReturn("42");
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn("");

    // Act
    deploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(processDefinition);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getProcessDefinitionEntityManager();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinition).getId();
    verify(processDefinition, atLeast(1)).getKey();
    verify(processDefinition, atLeast(1)).getTenantId();
    verify(processDefinition).getVersion();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey("Key");
    verify(processDefinitionDataManager)
        .findProcessDefinitionsByQueryCriteria(
            isA(ProcessDefinitionQueryImpl.class), isA(Page.class));
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(ProcessDefinition)"
  })
  public void testRestorePreviousStartEventsIfNeeded5() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");

    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByQueryCriteria(
            Mockito.<ProcessDefinitionQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getVersion()).thenReturn(1);
    when(processDefinition.getId()).thenReturn("42");
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn(null);

    // Act
    deploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(processDefinition);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getProcessDefinitionEntityManager();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinition).getId();
    verify(processDefinition, atLeast(1)).getKey();
    verify(processDefinition, atLeast(1)).getTenantId();
    verify(processDefinition).getVersion();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey("Key");
    verify(processDefinitionDataManager)
        .findProcessDefinitionsByQueryCriteria(
            isA(ProcessDefinitionQueryImpl.class), isA(Page.class));
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(ProcessDefinition)"
  })
  public void testRestorePreviousStartEventsIfNeeded_givenArrayListAddNull() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");

    ArrayList<ProcessDefinition> processDefinitionList = new ArrayList<>();
    processDefinitionList.add(null);

    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findProcessDefinitionsByQueryCriteria(
            Mockito.<ProcessDefinitionQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(processDefinitionList);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

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
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId("Key", "42");
    verify(processDefinitionDataManager)
        .findProcessDefinitionsByQueryCriteria(
            isA(ProcessDefinitionQueryImpl.class), isA(Page.class));
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restorePreviousStartEventsIfNeeded(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(ProcessDefinition)"
  })
  public void testRestorePreviousStartEventsIfNeeded_givenEmptyString() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getId()).thenReturn("42");
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn("");

    // Act
    deploymentEntityManagerImpl.restorePreviousStartEventsIfNeeded(processDefinition);

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinition).getId();
    verify(processDefinition).getKey();
    verify(processDefinition, atLeast(1)).getTenantId();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey("Key");
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreSignalStartEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    StartEvent startEvent = new StartEvent();

    // Act
    deploymentEntityManagerImpl.restoreSignalStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, new SignalEventDefinition());

    // Assert
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId(null);
    verify(signalEventSubscriptionEntity).setEventName(null);
    verify(signalEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(signalEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getSignal(String)} return {@code null}.
   *   <li>Then calls {@link BpmnModel#getSignal(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreSignalStartEvent_whenBpmnModelGetSignalReturnNull_thenCallsGetSignal() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getSignal(Mockito.<String>any())).thenReturn(null);
    StartEvent startEvent = new StartEvent();

    // Act
    deploymentEntityManagerImpl.restoreSignalStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, new SignalEventDefinition());

    // Assert
    verify(bpmnModel).getSignal(null);
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(previousProcessDefinition).getId();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId(null);
    verify(signalEventSubscriptionEntity).setEventName(null);
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getSignal(String)} return {@link
   *       Signal#Signal(String, String)} with id is {@code 42} and {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreSignalStartEvent_whenBpmnModelGetSignalReturnSignalWithIdIs42AndName() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getSignal(Mockito.<String>any())).thenReturn(new Signal("42", "Name"));
    StartEvent startEvent = new StartEvent();

    // Act
    deploymentEntityManagerImpl.restoreSignalStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, new SignalEventDefinition());

    // Assert
    verify(bpmnModel).getSignal(null);
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(previousProcessDefinition).getId();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId(null);
    verify(signalEventSubscriptionEntity).setEventName("Name");
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <ul>
   *   <li>When {@link StartEvent} {@link StartEvent#getId()} return {@code 42}.
   *   <li>Then calls {@link StartEvent#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreSignalStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreSignalStartEvent_whenStartEventGetIdReturn42_thenCallsGetId() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    SignalEventSubscriptionEntity signalEventSubscriptionEntity =
        mock(SignalEventSubscriptionEntity.class);
    when(signalEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(signalEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(signalEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createSignalEventSubscription())
        .thenReturn(signalEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getSignal(Mockito.<String>any())).thenReturn(null);

    StartEvent startEvent = mock(StartEvent.class);
    when(startEvent.getId()).thenReturn("42");

    // Act
    deploymentEntityManagerImpl.restoreSignalStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, new SignalEventDefinition());

    // Assert
    verify(startEvent).getId();
    verify(bpmnModel).getSignal(null);
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(previousProcessDefinition).getId();
    verify(signalEventSubscriptionEntity).getExecutionId();
    verify(signalEventSubscriptionEntity).setActivityId("42");
    verify(signalEventSubscriptionEntity).setEventName(null);
    verify(signalEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(signalEventSubscriptionEntity).setTenantId("42");
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createSignalEventSubscription();
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreMessageStartEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    ProcessDefinitionEntityImpl previousProcessDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    StartEvent startEvent = new StartEvent();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, new MessageEventDefinition());

    // Assert that nothing has changed
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setConfiguration(null);
    verify(messageEventSubscriptionEntity).setEventName(null);
    verify(messageEventSubscriptionEntity).setProcessDefinitionId(null);
    verify(messageEventSubscriptionEntity).setTenantId("");
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreMessageStartEvent2() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");

    BpmnModel bpmnModel = mock(BpmnModel.class);

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    when(bpmnModel.getMessage(Mockito.<String>any()))
        .thenReturn(
            attributesResult
                .extensionElements(new HashMap<>())
                .id("42")
                .itemRef("Item Ref")
                .name("Name")
                .xmlColumnNumber(10)
                .xmlRowNumber(10)
                .build());
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);

    StartEvent startEvent = mock(StartEvent.class);
    when(startEvent.getId()).thenReturn("42");
    MessageEventDefinition eventDefinition = new MessageEventDefinition();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, eventDefinition);

    // Assert
    verify(startEvent).getId();
    verify(bpmnModel).containsMessageId(null);
    verify(bpmnModel).getMessage(null);
    verify(eventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(previousProcessDefinition, atLeast(1)).getId();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId("42");
    verify(messageEventSubscriptionEntity).setConfiguration("42");
    verify(messageEventSubscriptionEntity).setEventName("Name");
    verify(messageEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(messageEventSubscriptionEntity).setTenantId("42");
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertEquals("Name", eventDefinition.getMessageRef());
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreMessageStartEvent_givenFalse() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(false);
    StartEvent startEvent = new StartEvent();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, new MessageEventDefinition());

    // Assert that nothing has changed
    verify(bpmnModel).containsMessageId(null);
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(previousProcessDefinition, atLeast(1)).getId();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setConfiguration("42");
    verify(messageEventSubscriptionEntity).setEventName(null);
    verify(messageEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(messageEventSubscriptionEntity).setTenantId("42");
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <ul>
   *   <li>Then {@link MessageEventDefinition} (default constructor) MessageRef is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreMessageStartEvent_thenMessageEventDefinitionMessageRefIsName() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");

    BpmnModel bpmnModel = mock(BpmnModel.class);

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    when(bpmnModel.getMessage(Mockito.<String>any()))
        .thenReturn(
            attributesResult
                .extensionElements(new HashMap<>())
                .id("42")
                .itemRef("Item Ref")
                .name("Name")
                .xmlColumnNumber(10)
                .xmlRowNumber(10)
                .build());
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);
    StartEvent startEvent = new StartEvent();
    MessageEventDefinition eventDefinition = new MessageEventDefinition();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, eventDefinition);

    // Assert
    verify(bpmnModel).containsMessageId(null);
    verify(bpmnModel).getMessage(null);
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(previousProcessDefinition, atLeast(1)).getId();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId(null);
    verify(messageEventSubscriptionEntity).setConfiguration("42");
    verify(messageEventSubscriptionEntity).setEventName("Name");
    verify(messageEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(messageEventSubscriptionEntity).setTenantId("42");
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertEquals("Name", eventDefinition.getMessageRef());
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel,
   * StartEvent, EventDefinition)}.
   *
   * <ul>
   *   <li>When {@link StartEvent} {@link StartEvent#getId()} return {@code 42}.
   *   <li>Then calls {@link StartEvent#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent,
   * EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeploymentEntityManagerImpl.restoreMessageStartEvent(ProcessDefinition, BpmnModel, StartEvent, EventDefinition)"
  })
  public void testRestoreMessageStartEvent_whenStartEventGetIdReturn42_thenCallsGetId() {
    // Arrange
    ActivitiEventDispatcherImpl eventDispatcher = mock(ActivitiEventDispatcherImpl.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    MessageEventSubscriptionEntity messageEventSubscriptionEntity =
        mock(MessageEventSubscriptionEntity.class);
    when(messageEventSubscriptionEntity.getExecutionId()).thenReturn("42");
    doNothing().when(messageEventSubscriptionEntity).setActivityId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setConfiguration(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setEventName(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setProcessDefinitionId(Mockito.<String>any());
    doNothing().when(messageEventSubscriptionEntity).setTenantId(Mockito.<String>any());

    EventSubscriptionDataManager eventSubscriptionDataManager =
        mock(EventSubscriptionDataManager.class);
    doNothing().when(eventSubscriptionDataManager).insert(Mockito.<EventSubscriptionEntity>any());
    when(eventSubscriptionDataManager.createMessageEventSubscription())
        .thenReturn(messageEventSubscriptionEntity);

    EventSubscriptionEntityManagerImpl eventSubscriptionEntityManagerImpl =
        new EventSubscriptionEntityManagerImpl(
            processEngineConfiguration, eventSubscriptionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getEventSubscriptionEntityManager())
        .thenReturn(eventSubscriptionEntityManagerImpl);
    doNothing().when(processEngineConfiguration2).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration2.addSessionFactory(new DbSqlSessionFactory());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl previousProcessDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(previousProcessDefinition.getId()).thenReturn("42");
    when(previousProcessDefinition.getTenantId()).thenReturn("42");

    BpmnModel bpmnModel = mock(BpmnModel.class);

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    when(bpmnModel.getMessage(Mockito.<String>any()))
        .thenReturn(
            attributesResult
                .extensionElements(new HashMap<>())
                .id("42")
                .itemRef("Item Ref")
                .name("Name")
                .xmlColumnNumber(10)
                .xmlRowNumber(10)
                .build());
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);

    StartEvent startEvent = mock(StartEvent.class);
    when(startEvent.getId()).thenReturn("42");
    MessageEventDefinition eventDefinition = new MessageEventDefinition();

    // Act
    deploymentEntityManagerImpl.restoreMessageStartEvent(
        previousProcessDefinition, bpmnModel, startEvent, eventDefinition);

    // Assert
    verify(startEvent).getId();
    verify(bpmnModel).containsMessageId(null);
    verify(bpmnModel).getMessage(null);
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(processEngineConfiguration2).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration2, atLeast(1)).getEventSubscriptionEntityManager();
    verify(previousProcessDefinition, atLeast(1)).getId();
    verify(messageEventSubscriptionEntity).getExecutionId();
    verify(messageEventSubscriptionEntity).setActivityId("42");
    verify(messageEventSubscriptionEntity).setConfiguration("42");
    verify(messageEventSubscriptionEntity).setEventName("Name");
    verify(messageEventSubscriptionEntity).setProcessDefinitionId("42");
    verify(messageEventSubscriptionEntity).setTenantId("42");
    verify(previousProcessDefinition, atLeast(1)).getTenantId();
    verify(eventSubscriptionDataManager).insert(isA(EventSubscriptionEntity.class));
    verify(eventSubscriptionDataManager).createMessageEventSubscription();
    assertEquals("Name", eventDefinition.getMessageRef());
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity DeploymentEntityManagerImpl.findLatestProcessDefinition(ProcessDefinition)"
  })
  public void testFindLatestProcessDefinition() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKeyAndTenantId(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn("42");

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionResult =
        deploymentEntityManagerImpl.findLatestProcessDefinition(processDefinition);

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinition).getKey();
    verify(processDefinition, atLeast(1)).getTenantId();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKeyAndTenantId("Key", "42");
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionResult);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}.
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity DeploymentEntityManagerImpl.findLatestProcessDefinition(ProcessDefinition)"
  })
  public void testFindLatestProcessDefinition2() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ProcessDefinitionEntityImpl processDefinition = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinition.getKey()).thenReturn("Key");
    when(processDefinition.getTenantId()).thenReturn(null);

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionResult =
        deploymentEntityManagerImpl.findLatestProcessDefinition(processDefinition);

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinition).getKey();
    verify(processDefinition).getTenantId();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey("Key");
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionResult);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessDefinitionDataManager#findLatestProcessDefinitionByKey(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#findLatestProcessDefinition(ProcessDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionEntity DeploymentEntityManagerImpl.findLatestProcessDefinition(ProcessDefinition)"
  })
  public void testFindLatestProcessDefinition_thenCallsFindLatestProcessDefinitionByKey() {
    // Arrange
    ProcessDefinitionDataManager processDefinitionDataManager =
        mock(ProcessDefinitionDataManager.class);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = new ProcessDefinitionEntityImpl();
    when(processDefinitionDataManager.findLatestProcessDefinitionByKey(Mockito.<String>any()))
        .thenReturn(processDefinitionEntityImpl);
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManagerImpl =
        new ProcessDefinitionEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getProcessDefinitionEntityManager())
        .thenReturn(processDefinitionEntityManagerImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    ProcessDefinitionEntity actualFindLatestProcessDefinitionResult =
        deploymentEntityManagerImpl.findLatestProcessDefinition(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processEngineConfiguration).getProcessDefinitionEntityManager();
    verify(processDefinitionDataManager).findLatestProcessDefinitionByKey(null);
    assertSame(processDefinitionEntityImpl, actualFindLatestProcessDefinitionResult);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#findLatestDeploymentByName(String)}.
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#findLatestDeploymentByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeploymentEntity DeploymentEntityManagerImpl.findLatestDeploymentByName(String)"
  })
  public void testFindLatestDeploymentByName() {
    // Arrange
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    when(deploymentDataManager.findLatestDeploymentByName(Mockito.<String>any()))
        .thenReturn(deploymentEntityImpl);

    // Act
    DeploymentEntity actualFindLatestDeploymentByNameResult =
        deploymentEntityManagerImpl.findLatestDeploymentByName("Deployment Name");

    // Assert
    verify(deploymentDataManager).findLatestDeploymentByName("Deployment Name");
    assertSame(deploymentEntityImpl, actualFindLatestDeploymentByNameResult);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#findDeploymentByVersion(Integer)}.
   *
   * <ul>
   *   <li>Then return {@link DeploymentEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#findDeploymentByVersion(Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeploymentEntity DeploymentEntityManagerImpl.findDeploymentByVersion(Integer)"
  })
  public void testFindDeploymentByVersion_thenReturnDeploymentEntityImpl() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    DeploymentEntityImpl deploymentEntityImpl = new DeploymentEntityImpl();
    when(deploymentDataManager.findDeploymentByVersion(Mockito.<Integer>any()))
        .thenReturn(deploymentEntityImpl);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(new JtaProcessEngineConfiguration(), deploymentDataManager);

    // Act
    DeploymentEntity actualFindDeploymentByVersionResult =
        deploymentEntityManagerImpl.findDeploymentByVersion(1);

    // Assert
    verify(deploymentDataManager).findDeploymentByVersion(1);
    assertSame(deploymentEntityImpl, actualFindDeploymentByVersionResult);
  }

  /**
   * Test {@link
   * DeploymentEntityManagerImpl#findDeploymentCountByQueryCriteria(DeploymentQueryImpl)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#findDeploymentCountByQueryCriteria(DeploymentQueryImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long DeploymentEntityManagerImpl.findDeploymentCountByQueryCriteria(DeploymentQueryImpl)"
  })
  public void testFindDeploymentCountByQueryCriteria_thenReturnThree() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findDeploymentCountByQueryCriteria(
            Mockito.<DeploymentQueryImpl>any()))
        .thenReturn(3L);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(new JtaProcessEngineConfiguration(), deploymentDataManager);

    // Act
    long actualFindDeploymentCountByQueryCriteriaResult =
        deploymentEntityManagerImpl.findDeploymentCountByQueryCriteria(new DeploymentQueryImpl());

    // Assert
    verify(deploymentDataManager)
        .findDeploymentCountByQueryCriteria(isA(DeploymentQueryImpl.class));
    assertEquals(3L, actualFindDeploymentCountByQueryCriteriaResult);
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#findDeploymentsByQueryCriteria(DeploymentQueryImpl,
   * Page)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * DeploymentEntityManagerImpl#findDeploymentsByQueryCriteria(DeploymentQueryImpl, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentEntityManagerImpl.findDeploymentsByQueryCriteria(DeploymentQueryImpl, Page)"
  })
  public void testFindDeploymentsByQueryCriteria_thenReturnEmpty() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findDeploymentsByQueryCriteria(
            Mockito.<DeploymentQueryImpl>any(), Mockito.<Page>any()))
        .thenReturn(new ArrayList<>());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(new JtaProcessEngineConfiguration(), deploymentDataManager);
    DeploymentQueryImpl deploymentQuery = new DeploymentQueryImpl();

    // Act
    List<Deployment> actualFindDeploymentsByQueryCriteriaResult =
        deploymentEntityManagerImpl.findDeploymentsByQueryCriteria(deploymentQuery, new Page(1, 3));

    // Assert
    verify(deploymentDataManager)
        .findDeploymentsByQueryCriteria(isA(DeploymentQueryImpl.class), isA(Page.class));
    assertTrue(actualFindDeploymentsByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#getDeploymentResourceNames(String)}.
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#getDeploymentResourceNames(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DeploymentEntityManagerImpl.getDeploymentResourceNames(String)"})
  public void testGetDeploymentResourceNames() {
    // Arrange
    when(deploymentDataManager.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<String> actualDeploymentResourceNames =
        deploymentEntityManagerImpl.getDeploymentResourceNames("42");

    // Assert
    verify(deploymentDataManager).getDeploymentResourceNames("42");
    assertTrue(actualDeploymentResourceNames.isEmpty());
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#findDeploymentsByNativeQuery(Map, int, int)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#findDeploymentsByNativeQuery(Map, int,
   * int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentEntityManagerImpl.findDeploymentsByNativeQuery(Map, int, int)"
  })
  public void testFindDeploymentsByNativeQuery_thenReturnEmpty() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findDeploymentsByNativeQuery(
            Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(new JtaProcessEngineConfiguration(), deploymentDataManager);

    // Act
    List<Deployment> actualFindDeploymentsByNativeQueryResult =
        deploymentEntityManagerImpl.findDeploymentsByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(deploymentDataManager).findDeploymentsByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindDeploymentsByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link DeploymentEntityManagerImpl#findDeploymentCountByNativeQuery(Map)}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentEntityManagerImpl#findDeploymentCountByNativeQuery(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long DeploymentEntityManagerImpl.findDeploymentCountByNativeQuery(Map)"})
  public void testFindDeploymentCountByNativeQuery_thenReturnThree() {
    // Arrange
    DeploymentDataManager deploymentDataManager = mock(DeploymentDataManager.class);
    when(deploymentDataManager.findDeploymentCountByNativeQuery(Mockito.<Map<String, Object>>any()))
        .thenReturn(3L);
    DeploymentEntityManagerImpl deploymentEntityManagerImpl =
        new DeploymentEntityManagerImpl(new JtaProcessEngineConfiguration(), deploymentDataManager);

    // Act
    long actualFindDeploymentCountByNativeQueryResult =
        deploymentEntityManagerImpl.findDeploymentCountByNativeQuery(new HashMap<>());

    // Assert
    verify(deploymentDataManager).findDeploymentCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindDeploymentCountByNativeQueryResult);
  }
}
