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
   * Test {@link AbstractManager#getCommandContext()}.
   * <p>
   * Method under test: {@link AbstractManager#getCommandContext()}
   */
  @Test
  public void testGetCommandContext() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getCommandContext());
  }

  /**
   * Test {@link AbstractManager#getCommandContext()}.
   * <p>
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
   * Test {@link AbstractManager#getProcessEngineConfiguration()}.
   * <p>
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
   * Test {@link AbstractManager#getProcessEngineConfiguration()}.
   * <p>
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
   * Test {@link AbstractManager#getCommandExecutor()}.
   * <p>
   * Method under test: {@link AbstractManager#getCommandExecutor()}
   */
  @Test
  public void testGetCommandExecutor() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getCommandExecutor());
  }

  /**
   * Test {@link AbstractManager#getCommandExecutor()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getCommandExecutor()}
   */
  @Test
  public void testGetCommandExecutor_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getCommandExecutor());
  }

  /**
   * Test {@link AbstractManager#getClock()}.
   * <p>
   * Method under test: {@link AbstractManager#getClock()}
   */
  @Test
  public void testGetClock() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getClock());
  }

  /**
   * Test {@link AbstractManager#getClock()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getClock()}
   */
  @Test
  public void testGetClock_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getClock());
  }

  /**
   * Test {@link AbstractManager#getAsyncExecutor()}.
   * <p>
   * Method under test: {@link AbstractManager#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getAsyncExecutor());
  }

  /**
   * Test {@link AbstractManager#getAsyncExecutor()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getAsyncExecutor());
  }

  /**
   * Test {@link AbstractManager#getEventDispatcher()}.
   * <p>
   * Method under test: {@link AbstractManager#getEventDispatcher()}
   */
  @Test
  public void testGetEventDispatcher() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getEventDispatcher());
  }

  /**
   * Test {@link AbstractManager#getEventDispatcher()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getEventDispatcher()}
   */
  @Test
  public void testGetEventDispatcher_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getEventDispatcher());
  }

  /**
   * Test {@link AbstractManager#getHistoryManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getHistoryManager()}
   */
  @Test
  public void testGetHistoryManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoryManager());
  }

  /**
   * Test {@link AbstractManager#getHistoryManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getHistoryManager()}
   */
  @Test
  public void testGetHistoryManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoryManager());
  }

  /**
   * Test {@link AbstractManager#getJobManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getJobManager()}
   */
  @Test
  public void testGetJobManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getJobManager());
  }

  /**
   * Test {@link AbstractManager#getJobManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getJobManager()}
   */
  @Test
  public void testGetJobManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getJobManager());
  }

  /**
   * Test {@link AbstractManager#getDeploymentEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getDeploymentEntityManager()}
   */
  @Test
  public void testGetDeploymentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getDeploymentEntityManager());
  }

  /**
   * Test {@link AbstractManager#getDeploymentEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getDeploymentEntityManager()}
   */
  @Test
  public void testGetDeploymentEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getDeploymentEntityManager());
  }

  /**
   * Test {@link AbstractManager#getResourceEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getResourceEntityManager()}
   */
  @Test
  public void testGetResourceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getResourceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getResourceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getResourceEntityManager()}
   */
  @Test
  public void testGetResourceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getResourceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getByteArrayEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getByteArrayEntityManager()}
   */
  @Test
  public void testGetByteArrayEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getByteArrayEntityManager());
  }

  /**
   * Test {@link AbstractManager#getByteArrayEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getByteArrayEntityManager()}
   */
  @Test
  public void testGetByteArrayEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getByteArrayEntityManager());
  }

  /**
   * Test {@link AbstractManager#getProcessDefinitionEntityManager()}.
   * <p>
   * Method under test:
   * {@link AbstractManager#getProcessDefinitionEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getProcessDefinitionEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractManager#getProcessDefinitionEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getProcessDefinitionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getProcessDefinitionInfoEntityManager()}.
   * <p>
   * Method under test:
   * {@link AbstractManager#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test {@link AbstractManager#getProcessDefinitionInfoEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractManager#getProcessDefinitionInfoEntityManager()}
   */
  @Test
  public void testGetProcessDefinitionInfoEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getProcessDefinitionInfoEntityManager());
  }

  /**
   * Test {@link AbstractManager#getModelEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getModelEntityManager()}
   */
  @Test
  public void testGetModelEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getModelEntityManager());
  }

  /**
   * Test {@link AbstractManager#getModelEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getModelEntityManager()}
   */
  @Test
  public void testGetModelEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getModelEntityManager());
  }

  /**
   * Test {@link AbstractManager#getExecutionEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getExecutionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getExecutionEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getExecutionEntityManager()}
   */
  @Test
  public void testGetExecutionEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getExecutionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getTaskEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getTaskEntityManager()}
   */
  @Test
  public void testGetTaskEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getTaskEntityManager());
  }

  /**
   * Test {@link AbstractManager#getTaskEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getTaskEntityManager()}
   */
  @Test
  public void testGetTaskEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getTaskEntityManager());
  }

  /**
   * Test {@link AbstractManager#getIdentityLinkEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getIdentityLinkEntityManager()}
   */
  @Test
  public void testGetIdentityLinkEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getIdentityLinkEntityManager());
  }

  /**
   * Test {@link AbstractManager#getIdentityLinkEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getIdentityLinkEntityManager()}
   */
  @Test
  public void testGetIdentityLinkEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getIdentityLinkEntityManager());
  }

  /**
   * Test {@link AbstractManager#getEventSubscriptionEntityManager()}.
   * <p>
   * Method under test:
   * {@link AbstractManager#getEventSubscriptionEntityManager()}
   */
  @Test
  public void testGetEventSubscriptionEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getEventSubscriptionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getEventSubscriptionEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractManager#getEventSubscriptionEntityManager()}
   */
  @Test
  public void testGetEventSubscriptionEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getEventSubscriptionEntityManager());
  }

  /**
   * Test {@link AbstractManager#getVariableInstanceEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getVariableInstanceEntityManager()}
   */
  @Test
  public void testGetVariableInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getVariableInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getVariableInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getVariableInstanceEntityManager()}
   */
  @Test
  public void testGetVariableInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getVariableInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getJobEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getJobEntityManager()}
   */
  @Test
  public void testGetJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getJobEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getJobEntityManager()}
   */
  @Test
  public void testGetJobEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getTimerJobEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getTimerJobEntityManager()}
   */
  @Test
  public void testGetTimerJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getTimerJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getTimerJobEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getTimerJobEntityManager()}
   */
  @Test
  public void testGetTimerJobEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getTimerJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getSuspendedJobEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getSuspendedJobEntityManager()}
   */
  @Test
  public void testGetSuspendedJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getSuspendedJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getSuspendedJobEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getSuspendedJobEntityManager()}
   */
  @Test
  public void testGetSuspendedJobEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getSuspendedJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getDeadLetterJobEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getDeadLetterJobEntityManager()}
   */
  @Test
  public void testGetDeadLetterJobEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getDeadLetterJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getDeadLetterJobEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getDeadLetterJobEntityManager()}
   */
  @Test
  public void testGetDeadLetterJobEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getDeadLetterJobEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricProcessInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricProcessInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricProcessInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricProcessInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        (new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricProcessInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricDetailEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getHistoricDetailEntityManager()}
   */
  @Test
  public void testGetHistoricDetailEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricDetailEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricDetailEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getHistoricDetailEntityManager()}
   */
  @Test
  public void testGetHistoricDetailEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricDetailEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricActivityInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricActivityInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricActivityInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricActivityInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        (new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricActivityInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricVariableInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricVariableInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricVariableInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricVariableInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        (new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricVariableInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricTaskInstanceEntityManager()}.
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricTaskInstanceEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricTaskInstanceEntityManager()}
   */
  @Test
  public void testGetHistoricTaskInstanceEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricTaskInstanceEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricIdentityLinkEntityManager()}.
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test {@link AbstractManager#getHistoricIdentityLinkEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractManager#getHistoricIdentityLinkEntityManager()}
   */
  @Test
  public void testGetHistoricIdentityLinkEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getHistoricIdentityLinkEntityManager());
  }

  /**
   * Test {@link AbstractManager#getAttachmentEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getAttachmentEntityManager()}
   */
  @Test
  public void testGetAttachmentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getAttachmentEntityManager());
  }

  /**
   * Test {@link AbstractManager#getAttachmentEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getAttachmentEntityManager()}
   */
  @Test
  public void testGetAttachmentEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getAttachmentEntityManager());
  }

  /**
   * Test {@link AbstractManager#getCommentEntityManager()}.
   * <p>
   * Method under test: {@link AbstractManager#getCommentEntityManager()}
   */
  @Test
  public void testGetCommentEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new TableDataManagerImpl(processEngineConfiguration)).getCommentEntityManager());
  }

  /**
   * Test {@link AbstractManager#getCommentEntityManager()}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractManager#getCommentEntityManager()}
   */
  @Test
  public void testGetCommentEntityManager_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new TableDataManagerImpl(new JtaProcessEngineConfiguration())).getCommentEntityManager());
  }
}
