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
import static org.mockito.Mockito.mock;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.TableDataManagerImpl;
import org.junit.Test;

public class AbstractManagerDiffblueTest {
  /**
   * Method under test: {@link AbstractManager#getCommandContext()}
   */
  @Test
  public void testGetCommandContext() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getCommandContext());
  }

  /**
   * Method under test: {@link AbstractManager#getCommandContext()}
   */
  @Test
  public void testGetCommandContext2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getCommandContext());
  }

  /**
   * Method under test: {@link AbstractManager#getProcessEngineConfiguration()}
   */
  @Test
  public void testGetProcessEngineConfiguration() {
    // Arrange
    TableDataManagerImpl tableDataManagerImpl = new TableDataManagerImpl(new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(tableDataManagerImpl.processEngineConfiguration, tableDataManagerImpl.getProcessEngineConfiguration());
  }

  /**
   * Method under test: {@link AbstractManager#getProcessEngineConfiguration()}
   */
  @Test
  public void testGetProcessEngineConfiguration2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    TableDataManagerImpl tableDataManagerImpl = new TableDataManagerImpl(processEngineConfiguration);

    // Act and Assert
    assertSame(tableDataManagerImpl.processEngineConfiguration, tableDataManagerImpl.getProcessEngineConfiguration());
  }

  /**
   * Method under test: {@link AbstractManager#getCommandExecutor()}
   */
  @Test
  public void testGetCommandExecutor() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getCommandExecutor());
  }

  /**
   * Method under test: {@link AbstractManager#getCommandExecutor()}
   */
  @Test
  public void testGetCommandExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getCommandExecutor());
  }

  /**
   * Method under test: {@link AbstractManager#getClock()}
   */
  @Test
  public void testGetClock() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getClock());
  }

  /**
   * Method under test: {@link AbstractManager#getClock()}
   */
  @Test
  public void testGetClock2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getClock());
  }

  /**
   * Method under test: {@link AbstractManager#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getAsyncExecutor());
  }

  /**
   * Method under test: {@link AbstractManager#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getAsyncExecutor());
  }

  /**
   * Method under test: {@link AbstractManager#getEventDispatcher()}
   */
  @Test
  public void testGetEventDispatcher() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getEventDispatcher());
  }

  /**
   * Method under test: {@link AbstractManager#getEventDispatcher()}
   */
  @Test
  public void testGetEventDispatcher2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getEventDispatcher());
  }

  /**
   * Method under test: {@link AbstractManager#getHistoryManager()}
   */
  @Test
  public void testGetHistoryManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoryManager());
  }

  /**
   * Method under test: {@link AbstractManager#getHistoryManager()}
   */
  @Test
  public void testGetHistoryManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoryManager());
  }

  /**
   * Method under test: {@link AbstractManager#getJobManager()}
   */
  @Test
  public void testGetJobManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getJobManager());
  }

  /**
   * Method under test: {@link AbstractManager#getJobManager()}
   */
  @Test
  public void testGetJobManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getJobManager());
  }

  /**
   * Method under test: {@link AbstractManager#getDeploymentEntityManager()}
   */
  @Test
  public void testGetDeploymentEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getDeploymentEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getDeploymentEntityManager()}
   */
  @Test
  public void testGetDeploymentEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getDeploymentEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getResourceEntityManager()}
   */
  @Test
  public void testGetResourceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getResourceEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getResourceEntityManager()}
   */
  @Test
  public void testGetResourceEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getResourceEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getByteArrayEntityManager()}
   */
  @Test
  public void testGetByteArrayEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getByteArrayEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getByteArrayEntityManager()}
   */
  @Test
  public void testGetByteArrayEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getByteArrayEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getProcessDefinitionEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getProcessDefinitionEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getProcessDefinitionEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getProcessDefinitionEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getProcessDefinitionInfoEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getProcessDefinitionInfoEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getModelEntityManager()}
   */
  @Test
  public void testGetModelEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getModelEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getModelEntityManager()}
   */
  @Test
  public void testGetModelEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getModelEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getExecutionEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getExecutionEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getTaskEntityManager()}
   */
  @Test
  public void testGetTaskEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getTaskEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getTaskEntityManager()}
   */
  @Test
  public void testGetTaskEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getTaskEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getIdentityLinkEntityManager()}
   */
  @Test
  public void testGetIdentityLinkEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getIdentityLinkEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getIdentityLinkEntityManager()}
   */
  @Test
  public void testGetIdentityLinkEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getIdentityLinkEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getEventSubscriptionEntityManager()}
   */
  @Test
  public void testGetEventSubscriptionEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getEventSubscriptionEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getEventSubscriptionEntityManager()}
   */
  @Test
  public void testGetEventSubscriptionEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getEventSubscriptionEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getVariableInstanceEntityManager()}
   */
  @Test
  public void testGetVariableInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getVariableInstanceEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getVariableInstanceEntityManager()}
   */
  @Test
  public void testGetVariableInstanceEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getVariableInstanceEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getJobEntityManager()}
   */
  @Test
  public void testGetJobEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getJobEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getJobEntityManager()}
   */
  @Test
  public void testGetJobEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getJobEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getTimerJobEntityManager()}
   */
  @Test
  public void testGetTimerJobEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getTimerJobEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getTimerJobEntityManager()}
   */
  @Test
  public void testGetTimerJobEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getTimerJobEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getSuspendedJobEntityManager()}
   */
  @Test
  public void testGetSuspendedJobEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getSuspendedJobEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getSuspendedJobEntityManager()}
   */
  @Test
  public void testGetSuspendedJobEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getSuspendedJobEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getDeadLetterJobEntityManager()}
   */
  @Test
  public void testGetDeadLetterJobEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getDeadLetterJobEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getDeadLetterJobEntityManager()}
   */
  @Test
  public void testGetDeadLetterJobEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getDeadLetterJobEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull(
        (new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricProcessInstanceEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricProcessInstanceEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getHistoricDetailEntityManager()}
   */
  @Test
  public void testGetHistoricDetailEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricDetailEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getHistoricDetailEntityManager()}
   */
  @Test
  public void testGetHistoricDetailEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricDetailEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull(
        (new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricActivityInstanceEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricActivityInstanceEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull(
        (new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricVariableInstanceEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricVariableInstanceEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricTaskInstanceEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricTaskInstanceEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricIdentityLinkEntityManager());
  }

  /**
   * Method under test:
   * {@link AbstractManager#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricIdentityLinkEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getAttachmentEntityManager()}
   */
  @Test
  public void testGetAttachmentEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getAttachmentEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getAttachmentEntityManager()}
   */
  @Test
  public void testGetAttachmentEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getAttachmentEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getCommentEntityManager()}
   */
  @Test
  public void testGetCommentEntityManager() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getCommentEntityManager());
  }

  /**
   * Method under test: {@link AbstractManager#getCommentEntityManager()}
   */
  @Test
  public void testGetCommentEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getCommentEntityManager());
  }
}
