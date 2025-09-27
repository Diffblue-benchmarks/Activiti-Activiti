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
package org.activiti.engine.impl.persistence;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.TableDataManagerImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractManagerDiffblueTest {
  /**
   * Test {@link AbstractManager#getCommandContext()}.
   *
   * <p>Method under test: {@link AbstractManager#getCommandContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.interceptor.CommandContext AbstractManager.getCommandContext()"
  })
  public void testGetCommandContext() {
    // Arrange, Act and Assert
    assertNull(new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getCommandContext());
  }

  /**
   * Test {@link AbstractManager#getCommandContext()}.
   *
   * <p>Method under test: {@link AbstractManager#getCommandContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.interceptor.CommandContext AbstractManager.getCommandContext()"
  })
  public void testGetCommandContext2() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new StandaloneInMemProcessEngineConfiguration())
            .getCommandContext());
  }

  /**
   * Test {@link AbstractManager#getProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link AbstractManager#getProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl AbstractManager.getProcessEngineConfiguration()"
  })
  public void testGetProcessEngineConfiguration() {
    // Arrange
    TableDataManagerImpl tableDataManagerImpl =
        new TableDataManagerImpl(new JtaProcessEngineConfiguration());

    // Act
    ProcessEngineConfigurationImpl actualProcessEngineConfiguration =
        tableDataManagerImpl.getProcessEngineConfiguration();

    // Assert
    assertSame(tableDataManagerImpl.processEngineConfiguration, actualProcessEngineConfiguration);
  }

  /**
   * Test {@link AbstractManager#getCommandExecutor()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getCommandExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.interceptor.CommandExecutor AbstractManager.getCommandExecutor()"
  })
  public void testGetCommandExecutor_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getCommandExecutor());
  }

  /**
   * Test {@link AbstractManager#getClock()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getClock()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.engine.runtime.Clock AbstractManager.getClock()"})
  public void testGetClock_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getClock());
  }

  /**
   * Test {@link AbstractManager#getAsyncExecutor()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.asyncexecutor.AsyncExecutor AbstractManager.getAsyncExecutor()"
  })
  public void testGetAsyncExecutor_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getAsyncExecutor());
  }

  /**
   * Test {@link AbstractManager#getEventDispatcher()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getEventDispatcher()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.delegate.event.ActivitiEventDispatcher AbstractManager.getEventDispatcher()"
  })
  public void testGetEventDispatcher_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getEventDispatcher());
  }

  /**
   * Test {@link AbstractManager#getHistoryManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getHistoryManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.history.HistoryManager AbstractManager.getHistoryManager()"
  })
  public void testGetHistoryManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getHistoryManager());
  }

  /**
   * Test {@link AbstractManager#getJobManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getJobManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.asyncexecutor.JobManager AbstractManager.getJobManager()"
  })
  public void testGetJobManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getJobManager());
  }

  /**
   * Test {@link AbstractManager#getDeploymentEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getDeploymentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.DeploymentEntityManager AbstractManager.getDeploymentEntityManager()"
  })
  public void testGetDeploymentEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getDeploymentEntityManager());
  }

  /**
   * Test {@link AbstractManager#getResourceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getResourceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ResourceEntityManager AbstractManager.getResourceEntityManager()"
  })
  public void testGetResourceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getResourceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getByteArrayEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getByteArrayEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ByteArrayEntityManager AbstractManager.getByteArrayEntityManager()"
  })
  public void testGetByteArrayEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getByteArrayEntityManager());
  }

  /**
   * Test {@link AbstractManager#getProcessDefinitionEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getProcessDefinitionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManager AbstractManager.getProcessDefinitionEntityManager()"
  })
  public void testGetProcessDefinitionEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getProcessDefinitionInfoEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManager AbstractManager.getProcessDefinitionInfoEntityManager()"
  })
  public void testGetProcessDefinitionInfoEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test {@link AbstractManager#getModelEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getModelEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ModelEntityManager AbstractManager.getModelEntityManager()"
  })
  public void testGetModelEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getModelEntityManager());
  }

  /**
   * Test {@link AbstractManager#getExecutionEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getExecutionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.ExecutionEntityManager AbstractManager.getExecutionEntityManager()"
  })
  public void testGetExecutionEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getExecutionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getTaskEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getTaskEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.TaskEntityManager AbstractManager.getTaskEntityManager()"
  })
  public void testGetTaskEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getTaskEntityManager());
  }

  /**
   * Test {@link AbstractManager#getIdentityLinkEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getIdentityLinkEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManager AbstractManager.getIdentityLinkEntityManager()"
  })
  public void testGetIdentityLinkEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getIdentityLinkEntityManager());
  }

  /**
   * Test {@link AbstractManager#getEventSubscriptionEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getEventSubscriptionEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityManager AbstractManager.getEventSubscriptionEntityManager()"
  })
  public void testGetEventSubscriptionEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getEventSubscriptionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getVariableInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getVariableInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManager AbstractManager.getVariableInstanceEntityManager()"
  })
  public void testGetVariableInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getVariableInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getJobEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.JobEntityManager AbstractManager.getJobEntityManager()"
  })
  public void testGetJobEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getTimerJobEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getTimerJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.TimerJobEntityManager AbstractManager.getTimerJobEntityManager()"
  })
  public void testGetTimerJobEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getTimerJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getSuspendedJobEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getSuspendedJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManager AbstractManager.getSuspendedJobEntityManager()"
  })
  public void testGetSuspendedJobEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getSuspendedJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getDeadLetterJobEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getDeadLetterJobEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManager AbstractManager.getDeadLetterJobEntityManager()"
  })
  public void testGetDeadLetterJobEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getDeadLetterJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricProcessInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager AbstractManager.getHistoricProcessInstanceEntityManager()"
  })
  public void testGetHistoricProcessInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricDetailEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getHistoricDetailEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricDetailEntityManager AbstractManager.getHistoricDetailEntityManager()"
  })
  public void testGetHistoricDetailEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getHistoricDetailEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricActivityInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManager AbstractManager.getHistoricActivityInstanceEntityManager()"
  })
  public void testGetHistoricActivityInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricVariableInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManager AbstractManager.getHistoricVariableInstanceEntityManager()"
  })
  public void testGetHistoricVariableInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricTaskInstanceEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager AbstractManager.getHistoricTaskInstanceEntityManager()"
  })
  public void testGetHistoricTaskInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricIdentityLinkEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager AbstractManager.getHistoricIdentityLinkEntityManager()"
  })
  public void testGetHistoricIdentityLinkEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration())
            .getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test {@link AbstractManager#getAttachmentEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getAttachmentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.AttachmentEntityManager AbstractManager.getAttachmentEntityManager()"
  })
  public void testGetAttachmentEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getAttachmentEntityManager());
  }

  /**
   * Test {@link AbstractManager#getCommentEntityManager()}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractManager#getCommentEntityManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.CommentEntityManager AbstractManager.getCommentEntityManager()"
  })
  public void testGetCommentEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()).getCommentEntityManager());
  }
}
