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
package org.activiti.engine.impl.interceptor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.impl.agenda.DefaultActivitiEngineAgenda;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class CommandContextFactoryDiffblueTest {
  /**
   * Test {@link CommandContextFactory#createCommandContext(Command)}.
   *
   * <ul>
   *   <li>Then Agenda return {@link DefaultActivitiEngineAgenda}.
   * </ul>
   *
   * <p>Method under test: {@link CommandContextFactory#createCommandContext(Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CommandContext CommandContextFactory.createCommandContext(Command)"})
  public void testCreateCommandContext_thenAgendaReturnDefaultActivitiEngineAgenda() {
    // Arrange
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);
    DefaultActivitiEngineAgenda defaultActivitiEngineAgenda = new DefaultActivitiEngineAgenda(null);
    when(engineAgendaFactory.createAgenda(Mockito.<CommandContext>any()))
        .thenReturn(defaultActivitiEngineAgenda);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    CommandContextFactory commandContextFactory = new CommandContextFactory();
    commandContextFactory.setProcessEngineConfiguration(processEngineConfiguration);
    Command<Object> cmd = mock(Command.class);

    // Act
    CommandContext actualCreateCommandContextResult =
        commandContextFactory.createCommandContext(cmd);

    // Assert
    verify(engineAgendaFactory).createAgenda(isA(CommandContext.class));
    ActivitiEngineAgenda agenda = actualCreateCommandContextResult.getAgenda();
    assertTrue(agenda instanceof DefaultActivitiEngineAgenda);
    ProcessEngineConfigurationImpl processEngineConfiguration2 =
        actualCreateCommandContextResult.getProcessEngineConfiguration();
    assertTrue(processEngineConfiguration2 instanceof JtaProcessEngineConfiguration);
    assertNull(actualCreateCommandContextResult.getResult());
    assertNull(actualCreateCommandContextResult.getException());
    assertNull(actualCreateCommandContextResult.getCloseListeners());
    assertNull(actualCreateCommandContextResult.getSessionFactories());
    assertNull(actualCreateCommandContextResult.attributes);
    assertNull(actualCreateCommandContextResult.getEventDispatcher());
    assertNull(actualCreateCommandContextResult.getJobManager());
    assertNull(actualCreateCommandContextResult.getHistoryManager());
    assertNull(actualCreateCommandContextResult.getFailedJobCommandFactory());
    assertNull(actualCreateCommandContextResult.getAttachmentEntityManager());
    assertNull(actualCreateCommandContextResult.getByteArrayEntityManager());
    assertNull(actualCreateCommandContextResult.getCommentEntityManager());
    assertNull(actualCreateCommandContextResult.getDeadLetterJobEntityManager());
    assertNull(actualCreateCommandContextResult.getDeploymentEntityManager());
    assertNull(actualCreateCommandContextResult.getEventLogEntryEntityManager());
    assertNull(actualCreateCommandContextResult.getEventSubscriptionEntityManager());
    assertNull(actualCreateCommandContextResult.getExecutionEntityManager());
    assertNull(actualCreateCommandContextResult.getHistoricActivityInstanceEntityManager());
    assertNull(actualCreateCommandContextResult.getHistoricDetailEntityManager());
    assertNull(actualCreateCommandContextResult.getHistoricIdentityLinkEntityManager());
    assertNull(actualCreateCommandContextResult.getHistoricProcessInstanceEntityManager());
    assertNull(actualCreateCommandContextResult.getHistoricTaskInstanceEntityManager());
    assertNull(actualCreateCommandContextResult.getHistoricVariableInstanceEntityManager());
    assertNull(actualCreateCommandContextResult.getIdentityLinkEntityManager());
    assertNull(actualCreateCommandContextResult.getJobEntityManager());
    assertNull(actualCreateCommandContextResult.getModelEntityManager());
    assertNull(actualCreateCommandContextResult.getProcessDefinitionEntityManager());
    assertNull(actualCreateCommandContextResult.getProcessDefinitionInfoEntityManager());
    assertNull(actualCreateCommandContextResult.getPropertyEntityManager());
    assertNull(actualCreateCommandContextResult.getResourceEntityManager());
    assertNull(actualCreateCommandContextResult.getSuspendedJobEntityManager());
    assertNull(actualCreateCommandContextResult.getTableDataManager());
    assertNull(actualCreateCommandContextResult.getTaskEntityManager());
    assertNull(actualCreateCommandContextResult.getTimerJobEntityManager());
    assertNull(actualCreateCommandContextResult.getVariableInstanceEntityManager());
    assertFalse(actualCreateCommandContextResult.hasInvolvedExecutions());
    assertFalse(actualCreateCommandContextResult.isReused());
    assertTrue(actualCreateCommandContextResult.resultStack.isEmpty());
    assertTrue(actualCreateCommandContextResult.getInvolvedExecutions().isEmpty());
    assertTrue(actualCreateCommandContextResult.getSessions().isEmpty());
    assertTrue(actualCreateCommandContextResult.involvedExecutions.isEmpty());
    assertSame(defaultActivitiEngineAgenda, agenda);
    assertSame(processEngineConfiguration, processEngineConfiguration2);
    assertSame(cmd, actualCreateCommandContextResult.getCommand());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link CommandContextFactory}
   *   <li>{@link
   *       CommandContextFactory#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link CommandContextFactory#getProcessEngineConfiguration()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CommandContextFactory.<init>()",
    "ProcessEngineConfigurationImpl CommandContextFactory.getProcessEngineConfiguration()",
    "void CommandContextFactory.setProcessEngineConfiguration(ProcessEngineConfigurationImpl)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    CommandContextFactory actualCommandContextFactory = new CommandContextFactory();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    actualCommandContextFactory.setProcessEngineConfiguration(processEngineConfiguration);

    // Assert
    assertSame(
        processEngineConfiguration, actualCommandContextFactory.getProcessEngineConfiguration());
  }
}
