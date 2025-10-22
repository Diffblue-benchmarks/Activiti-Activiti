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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.asyncexecutor.JobManager;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
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
   * Test {@link AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}.
   * <p>
   * Method under test: {@link AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractSetProcessDefinitionStateCmd.executeInternal(CommandContext, List)"})
  public void testExecuteInternal() {
    // Arrange
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd = new ActivateProcessDefinitionCmd("42",
        "Process Definition Key", false,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42");
    JobManager jobManager = mock(JobManager.class);
    doNothing().when(jobManager).scheduleTimerJob(Mockito.<TimerJobEntity>any());
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getJobManager()).thenReturn(jobManager);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    when(commandContext.getTimerJobEntityManager()).thenReturn(new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration())));
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");
    when(processDefinitionEntityImpl.getTenantId()).thenReturn("42");

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(processDefinitionEntityImpl);

    // Act
    activateProcessDefinitionCmd.executeInternal(commandContext, processDefinitions);

    // Assert
    verify(jobManager).scheduleTimerJob(isA(TimerJobEntity.class));
    verify(commandContext).getJobManager();
    verify(commandContext).getTimerJobEntityManager();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinitionEntityImpl, atLeast(1)).getTenantId();
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}.
   * <ul>
   *   <li>Given {@link ProcessDefinitionEntityImpl} {@link ProcessDefinitionEntityImpl#getTenantId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractSetProcessDefinitionStateCmd.executeInternal(CommandContext, List)"})
  public void testExecuteInternal_givenProcessDefinitionEntityImplGetTenantIdReturnNull() {
    // Arrange
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd = new ActivateProcessDefinitionCmd("42",
        "Process Definition Key", true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42");
    JobManager jobManager = mock(JobManager.class);
    doNothing().when(jobManager).scheduleTimerJob(Mockito.<TimerJobEntity>any());
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getJobManager()).thenReturn(jobManager);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    when(commandContext.getTimerJobEntityManager()).thenReturn(new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration())));
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");
    when(processDefinitionEntityImpl.getTenantId()).thenReturn(null);

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(processDefinitionEntityImpl);

    // Act
    activateProcessDefinitionCmd.executeInternal(commandContext, processDefinitions);

    // Assert
    verify(jobManager).scheduleTimerJob(isA(TimerJobEntity.class));
    verify(commandContext).getJobManager();
    verify(commandContext).getTimerJobEntityManager();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinitionEntityImpl).getTenantId();
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}.
   * <ul>
   *   <li>Then calls {@link JobManager#scheduleTimerJob(TimerJobEntity)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractSetProcessDefinitionStateCmd#executeInternal(CommandContext, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractSetProcessDefinitionStateCmd.executeInternal(CommandContext, List)"})
  public void testExecuteInternal_thenCallsScheduleTimerJob() {
    // Arrange
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd = new ActivateProcessDefinitionCmd("42",
        "Process Definition Key", true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42");
    JobManager jobManager = mock(JobManager.class);
    doNothing().when(jobManager).scheduleTimerJob(Mockito.<TimerJobEntity>any());
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getJobManager()).thenReturn(jobManager);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    when(commandContext.getTimerJobEntityManager()).thenReturn(new TimerJobEntityManagerImpl(processEngineConfiguration,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration())));
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");
    when(processDefinitionEntityImpl.getTenantId()).thenReturn("42");

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(processDefinitionEntityImpl);

    // Act
    activateProcessDefinitionCmd.executeInternal(commandContext, processDefinitions);

    // Assert
    verify(jobManager).scheduleTimerJob(isA(TimerJobEntity.class));
    verify(commandContext).getJobManager();
    verify(commandContext).getTimerJobEntityManager();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinitionEntityImpl, atLeast(1)).getTenantId();
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractSetProcessDefinitionStateCmd.findProcessDefinition(CommandContext)"})
  public void testFindProcessDefinition_thenReturnSizeIsOne() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd = new ActivateProcessDefinitionCmd(
        processDefinitionEntity, true,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42");

    // Act
    List<ProcessDefinitionEntity> actualFindProcessDefinitionResult = activateProcessDefinitionCmd
        .findProcessDefinition(null);

    // Assert
    assertEquals(1, actualFindProcessDefinitionResult.size());
    ProcessDefinitionEntity getResult = actualFindProcessDefinitionResult.get(0);
    assertTrue(getResult instanceof ProcessDefinitionEntityImpl);
    assertSame(activateProcessDefinitionCmd.processDefinitionEntity, getResult);
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractSetProcessDefinitionStateCmd#findProcessDefinition(CommandContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractSetProcessDefinitionStateCmd.findProcessDefinition(CommandContext)"})
  public void testFindProcessDefinition_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ActivateProcessDefinitionCmd(null, true,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42"))
            .findProcessDefinition(null));
  }

  /**
   * Test {@link AbstractSetProcessDefinitionStateCmd#changeProcessDefinitionState(CommandContext, List)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getDeploymentManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractSetProcessDefinitionStateCmd#changeProcessDefinitionState(CommandContext, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractSetProcessDefinitionStateCmd.changeProcessDefinitionState(CommandContext, List)"})
  public void testChangeProcessDefinitionState_thenCallsGetDeploymentManager() {
    // Arrange
    ProcessDefinitionEntityImpl processDefinitionEntity = new ProcessDefinitionEntityImpl();
    ActivateProcessDefinitionCmd activateProcessDefinitionCmd = new ActivateProcessDefinitionCmd(
        processDefinitionEntity, false,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42");
    DeploymentManager deploymentManager = mock(DeploymentManager.class);
    when(deploymentManager.getProcessDefinitionCache()).thenReturn(new DefaultDeploymentCache<>());
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfigurationImpl.getDeploymentManager()).thenReturn(deploymentManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationImpl);
    ProcessDefinitionEntityImpl processDefinitionEntityImpl = mock(ProcessDefinitionEntityImpl.class);
    when(processDefinitionEntityImpl.getId()).thenReturn("42");
    when(processDefinitionEntityImpl.getSuspensionState()).thenReturn(-1);
    doNothing().when(processDefinitionEntityImpl).setSuspensionState(anyInt());

    ArrayList<ProcessDefinitionEntity> processDefinitions = new ArrayList<>();
    processDefinitions.add(processDefinitionEntityImpl);

    // Act
    activateProcessDefinitionCmd.changeProcessDefinitionState(commandContext, processDefinitions);

    // Assert
    verify(processEngineConfigurationImpl).getDeploymentManager();
    verify(commandContext).getProcessEngineConfiguration();
    verify(deploymentManager).getProcessDefinitionCache();
    verify(processDefinitionEntityImpl).getId();
    verify(processDefinitionEntityImpl).getSuspensionState();
    verify(processDefinitionEntityImpl).setSuspensionState(eq(1));
  }
}
