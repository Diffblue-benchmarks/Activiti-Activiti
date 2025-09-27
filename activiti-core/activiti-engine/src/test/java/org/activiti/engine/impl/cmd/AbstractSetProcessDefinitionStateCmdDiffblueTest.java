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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntity;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class AbstractSetProcessDefinitionStateCmdDiffblueTest {
  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#execute(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractSetProcessDefinitionStateCmd#execute(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Void AbstractSetProcessDefinitionStateCmd.execute(CommandContext)"})
  public void testExecute_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(null, true, executionDate, "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> activateProcessDefinitionCmd.execute(null));
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContext#getJobManager()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractSetProcessDefinitionStateCmd.executeInternal(CommandContext, List)"
  })
  public void testExecuteInternal_thenCallsGetJobManager() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(processDefinitionEntity, true, executionDate, "42");

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getJobManager())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    when(commandContext.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> activateProcessDefinitionCmd.executeInternal(commandContext, processDefinitions));
    verify(commandContext).getJobManager();
    verify(commandContext).getTimerJobEntityManager();
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}.
   *
   * <ul>
   *   <li>Then calls {@link CommandContext#getProcessEngineConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractSetProcessDefinitionStateCmd.executeInternal(CommandContext, List)"
  })
  public void testExecuteInternal_thenCallsGetProcessEngineConfiguration() {
    // Arrange
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(new ProcessDefinitionEntityImpl(), true, null, "42");

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getSuspensionState()).thenReturn(-1);
    doNothing().when(processDefinitionEntityImpl).setSuspensionState(anyInt());

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(processDefinitionEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> activateProcessDefinitionCmd.executeInternal(commandContext, processDefinitions));
    verify(commandContext).getProcessEngineConfiguration();
    verify(processDefinitionEntityImpl).getSuspensionState();
    verify(processDefinitionEntityImpl).setSuspensionState(1);
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List AbstractSetProcessDefinitionStateCmd.findProcessDefinition(CommandContext)"
  })
  public void testFindProcessDefinition_thenReturnSizeIsOne() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(processDefinitionEntity, true, executionDate, "42");

    // Act
    List<ProcessDefinitionEntity> actualFindProcessDefinitionResult =
        activateProcessDefinitionCmd.findProcessDefinition(null);

    // Assert
    assertEquals(1, actualFindProcessDefinitionResult.size());
    ProcessDefinitionEntity getResult = actualFindProcessDefinitionResult.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertSame(activateProcessDefinitionCmd.processDefinitionEntity, getResult);
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List AbstractSetProcessDefinitionStateCmd.findProcessDefinition(CommandContext)"
  })
  public void testFindProcessDefinition_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(null, true, executionDate, "42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> activateProcessDefinitionCmd.findProcessDefinition(null));
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#createTimerForDelayedExecution(CommandContext,
   * List)}.
   *
   * <p>Method under test: {@link
   * AbstractSetProcessDefinitionStateCmd#createTimerForDelayedExecution(CommandContext, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractSetProcessDefinitionStateCmd.createTimerForDelayedExecution(CommandContext, List)"
  })
  public void testCreateTimerForDelayedExecution() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(processDefinitionEntity, false, executionDate, "42");

    DefaultJobManager defaultJobManager = mock(DefaultJobManager.class);
    doNothing().when(defaultJobManager).scheduleTimerJob(Mockito.<TimerJobEntity>any());

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getJobManager()).thenReturn(defaultJobManager);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    when(commandContext.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);

    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");
    when(processDefinitionEntityImpl.getTenantId()).thenReturn("42");

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(processDefinitionEntityImpl);

    // Act
    activateProcessDefinitionCmd.createTimerForDelayedExecution(commandContext, processDefinitions);

    // Assert
    verify(defaultJobManager).scheduleTimerJob(isA(TimerJobEntity.class));
    verify(commandContext).getJobManager();
    verify(commandContext).getTimerJobEntityManager();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinitionEntityImpl, atLeast(1)).getTenantId();
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#createTimerForDelayedExecution(CommandContext,
   * List)}.
   *
   * <ul>
   *   <li>Then calls {@link DefaultJobManager#scheduleTimerJob(TimerJobEntity)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractSetProcessDefinitionStateCmd#createTimerForDelayedExecution(CommandContext, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractSetProcessDefinitionStateCmd.createTimerForDelayedExecution(CommandContext, List)"
  })
  public void testCreateTimerForDelayedExecution_thenCallsScheduleTimerJob() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(processDefinitionEntity, true, executionDate, "42");

    DefaultJobManager defaultJobManager = mock(DefaultJobManager.class);
    doNothing().when(defaultJobManager).scheduleTimerJob(Mockito.<TimerJobEntity>any());

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getJobManager()).thenReturn(defaultJobManager);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    when(commandContext.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);

    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");
    when(processDefinitionEntityImpl.getTenantId()).thenReturn("42");

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(processDefinitionEntityImpl);

    // Act
    activateProcessDefinitionCmd.createTimerForDelayedExecution(commandContext, processDefinitions);

    // Assert
    verify(defaultJobManager).scheduleTimerJob(isA(TimerJobEntity.class));
    verify(commandContext).getJobManager();
    verify(commandContext).getTimerJobEntityManager();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinitionEntityImpl, atLeast(1)).getTenantId();
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#createTimerForDelayedExecution(CommandContext,
   * List)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractSetProcessDefinitionStateCmd#createTimerForDelayedExecution(CommandContext, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractSetProcessDefinitionStateCmd.createTimerForDelayedExecution(CommandContext, List)"
  })
  public void testCreateTimerForDelayedExecution_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(processDefinitionEntity, true, executionDate, "42");

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getJobManager())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    when(commandContext.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(new ProcessDefinitionEntityImpl());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            activateProcessDefinitionCmd.createTimerForDelayedExecution(
                commandContext, processDefinitions));
    verify(commandContext).getJobManager();
    verify(commandContext).getTimerJobEntityManager();
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#changeProcessDefinitionState(CommandContext,
   * List)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractSetProcessDefinitionStateCmd#changeProcessDefinitionState(CommandContext, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractSetProcessDefinitionStateCmd.changeProcessDefinitionState(CommandContext, List)"
  })
  public void testChangeProcessDefinitionState_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    Date executionDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ActivateProcessDefinitionCmd activateProcessDefinitionCmd =
        new ActivateProcessDefinitionCmd(processDefinitionEntity, true, executionDate, "42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDeploymentManager(new DeploymentManager());

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(jtaProcessEngineConfiguration);

    ProcessDefinitionEntityImpl processDefinitionEntityImpl =
        mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(processDefinitionEntityImpl.getSuspensionState()).thenReturn(-1);
    doNothing().when(processDefinitionEntityImpl).setSuspensionState(anyInt());

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(processDefinitionEntityImpl);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            activateProcessDefinitionCmd.changeProcessDefinitionState(
                commandContext, processDefinitions));
    verify(commandContext).getProcessEngineConfiguration();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinitionEntityImpl).getSuspensionState();
    verify(processDefinitionEntityImpl).setSuspensionState(1);
  }
}
