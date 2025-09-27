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
package org.activiti.engine.impl.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.CommentEntityImpl;
import org.activiti.engine.impl.persistence.entity.CommentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.data.CommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricProcessInstanceDataManager;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultHistoryManagerDiffblueTest {
  @InjectMocks private DefaultHistoryManager defaultHistoryManager;

  @Mock private HistoryLevel historyLevel;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DefaultHistoryManager#DefaultHistoryManager(ProcessEngineConfigurationImpl,
   *       HistoryLevel)}
   *   <li>{@link DefaultHistoryManager#setHistoryLevel(HistoryLevel)}
   *   <li>{@link DefaultHistoryManager#getHistoryLevel()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.<init>(ProcessEngineConfigurationImpl, HistoryLevel)",
    "HistoryLevel DefaultHistoryManager.getHistoryLevel()",
    "void DefaultHistoryManager.setHistoryLevel(HistoryLevel)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    DefaultHistoryManager actualDefaultHistoryManager =
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE);
    actualDefaultHistoryManager.setHistoryLevel(HistoryLevel.NONE);

    // Assert
    assertEquals(HistoryLevel.NONE, actualDefaultHistoryManager.getHistoryLevel());
  }

  /**
   * Test {@link DefaultHistoryManager#isHistoryLevelAtLeast(HistoryLevel)}.
   *
   * <ul>
   *   <li>When {@code ACTIVITY}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#isHistoryLevelAtLeast(HistoryLevel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultHistoryManager.isHistoryLevelAtLeast(HistoryLevel)"})
  public void testIsHistoryLevelAtLeast_whenActivity_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE)
            .isHistoryLevelAtLeast(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#isHistoryLevelAtLeast(HistoryLevel)}.
   *
   * <ul>
   *   <li>When {@code NONE}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#isHistoryLevelAtLeast(HistoryLevel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultHistoryManager.isHistoryLevelAtLeast(HistoryLevel)"})
  public void testIsHistoryLevelAtLeast_whenNone_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE)
            .isHistoryLevelAtLeast(HistoryLevel.NONE));
  }

  /**
   * Test {@link DefaultHistoryManager#isHistoryEnabled()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#isHistoryEnabled()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultHistoryManager.isHistoryEnabled()"})
  public void testIsHistoryEnabled_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE)
            .isHistoryEnabled());
  }

  /**
   * Test {@link DefaultHistoryManager#isHistoryEnabled()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#isHistoryEnabled()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultHistoryManager.isHistoryEnabled()"})
  public void testIsHistoryEnabled_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY)
            .isHistoryEnabled());
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd2() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        mock(HistoricProcessInstanceEntityImpl.class);
    doNothing().when(historicProcessInstanceEntityImpl).setEndActivityId(Mockito.<String>any());
    doNothing().when(historicProcessInstanceEntityImpl).markEnded(Mockito.<String>any());

    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceEntityImpl).setEndActivityId("42");
    verify(historicProcessInstanceEntityImpl).markEnded("Just cause");
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd3() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        mock(HistoricProcessInstanceEntityImpl.class);
    doNothing().when(historicProcessInstanceEntityImpl).setEndActivityId(Mockito.<String>any());
    doNothing().when(historicProcessInstanceEntityImpl).markEnded(Mockito.<String>any());

    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceEntityImpl).setEndActivityId("42");
    verify(historicProcessInstanceEntityImpl).markEnded("Just cause");
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        mock(HistoricProcessInstanceEntityImpl.class);
    doNothing().when(historicProcessInstanceEntityImpl).setEndActivityId(Mockito.<String>any());
    doNothing().when(historicProcessInstanceEntityImpl).markEnded(Mockito.<String>any());

    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceEntityImpl).setEndActivityId("42");
    verify(historicProcessInstanceEntityImpl).markEnded("Just cause");
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd_thenCallsDispatchEvent() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        mock(HistoricProcessInstanceEntityImpl.class);
    doNothing().when(historicProcessInstanceEntityImpl).setEndActivityId(Mockito.<String>any());
    doNothing().when(historicProcessInstanceEntityImpl).markEnded(Mockito.<String>any());

    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceEntityImpl).setEndActivityId("42");
    verify(historicProcessInstanceEntityImpl).markEnded("Just cause");
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       JtaProcessEngineConfiguration#getHistoricProcessInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd_thenCallsGetHistoricProcessInstanceEntityManager() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY)
        .recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceNameChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceNameChange(String, String)"})
  public void testRecordProcessInstanceNameChange() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceNameChange("42", "New Name");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceNameChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceNameChange(String, String)"})
  public void testRecordProcessInstanceNameChange2() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY)
        .recordProcessInstanceNameChange("42", "New Name");

    // Assert
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceNameChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceNameChange(String, String)"})
  public void testRecordProcessInstanceNameChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordProcessInstanceNameChange("42", "New Name");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricProcessInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceNameChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceNameChange(String, String)"})
  public void testRecordProcessInstanceNameChange_thenCallsFindById() {
    // Arrange
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager =
        mock(HistoricProcessInstanceEntityManager.class);
    when(historicProcessInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceNameChange("42", "New Name");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity,
   * FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordProcessInstanceStart(ExecutionEntity, FlowElement)"
  })
  public void testRecordProcessInstanceStart() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    doNothing()
        .when(historicProcessInstanceDataManager)
        .insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordProcessInstanceStart(processInstance, new AdhocSubProcess());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity,
   * FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordProcessInstanceStart(ExecutionEntity, FlowElement)"
  })
  public void testRecordProcessInstanceStart2() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    doNothing()
        .when(historicProcessInstanceDataManager)
        .insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(null);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordProcessInstanceStart(processInstance, new AdhocSubProcess());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity,
   * FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordProcessInstanceStart(ExecutionEntity, FlowElement)"
  })
  public void testRecordProcessInstanceStart_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    doNothing()
        .when(historicProcessInstanceDataManager)
        .insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordProcessInstanceStart(processInstance, new AdhocSubProcess());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity,
   * FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordProcessInstanceStart(ExecutionEntity, FlowElement)"
  })
  public void testRecordProcessInstanceStart_thenCallsDispatchEvent() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    doNothing()
        .when(historicProcessInstanceDataManager)
        .insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);
    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordProcessInstanceStart(processInstance, new AdhocSubProcess());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity,
   * ExecutionEntity, FlowElement)}.
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity,
   * FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"
  })
  public void testRecordSubProcessInstanceStart() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    doNothing()
        .when(historicProcessInstanceDataManager)
        .insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(
        parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity,
   * ExecutionEntity, FlowElement)}.
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity,
   * FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"
  })
  public void testRecordSubProcessInstanceStart2() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    doNothing()
        .when(historicProcessInstanceDataManager)
        .insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(null);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(
        parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity,
   * ExecutionEntity, FlowElement)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getCurrentFlowElement()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity,
   * FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"
  })
  public void testRecordSubProcessInstanceStart_thenCallsGetCurrentFlowElement() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        mock(HistoricProcessInstanceEntityImpl.class);
    when(historicProcessInstanceEntityImpl.getStartActivityId()).thenReturn("42");

    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    doNothing()
        .when(historicProcessInstanceDataManager)
        .insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);

    ExecutionEntityImpl parentExecution = mock(ExecutionEntityImpl.class);
    when(parentExecution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    ExecutionEntityImpl subProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(
        parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(parentExecution, atLeast(1)).getCurrentFlowElement();
    verify(historicProcessInstanceEntityImpl).getStartActivityId();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity,
   * ExecutionEntity, FlowElement)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricProcessInstanceEntityImpl#getStartActivityId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity,
   * FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"
  })
  public void testRecordSubProcessInstanceStart_thenCallsGetStartActivityId() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl =
        mock(HistoricProcessInstanceEntityImpl.class);
    when(historicProcessInstanceEntityImpl.getStartActivityId()).thenReturn("42");

    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    doNothing()
        .when(historicProcessInstanceDataManager)
        .insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(
        parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceEntityImpl).getStartActivityId();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityStart(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then calls {@link ExecutionEntityImpl#getActivityId()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordActivityStart(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityStart(ExecutionEntity)"})
  public void testRecordActivityStart_givenNull_thenCallsGetActivityId() {
    // Arrange
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY);

    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(null);
    when(executionEntity.getActivityId()).thenReturn("42");

    // Act
    defaultHistoryManager.recordActivityStart(executionEntity);

    // Assert
    verify(executionEntity).getActivityId();
    verify(executionEntity).getCurrentFlowElement();
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityEnd(ExecutionEntity, String)"})
  public void testRecordActivityEnd() {
    // Arrange
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY);

    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    defaultHistoryManager.recordActivityEnd(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then calls {@link ExecutionEntity#getCurrentFlowElement()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityEnd(ExecutionEntity, String)"})
  public void testRecordActivityEnd_givenAdhocSubProcess_thenCallsGetCurrentFlowElement() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    defaultHistoryManager.recordActivityEnd(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   *   <li>When {@link ExecutionEntity}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityEnd(ExecutionEntity, String)"})
  public void testRecordActivityEnd_givenHistoryLevelIsAtLeastReturnFalse_whenExecutionEntity() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordActivityEnd(mock(ExecutionEntity.class), "Just cause");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityEnd(ExecutionEntity, String)"})
  public void testRecordActivityEnd_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordActivityEnd(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
  }

  /**
   * Test {@link DefaultHistoryManager#findActivityInstance(ExecutionEntity, String, boolean,
   * boolean)} with {@code execution}, {@code activityId}, {@code createOnNotFound}, {@code
   * endTimeMustBeNull}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#findActivityInstance(ExecutionEntity,
   * String, boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceEntity DefaultHistoryManager.findActivityInstance(ExecutionEntity, String, boolean, boolean)"
  })
  public void testFindActivityInstanceWithExecutionActivityIdCreateOnNotFoundEndTimeMustBeNull() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setInserted(false);
    execution.setParentId(null);
    execution.setTenantId(null);

    // Act and Assert
    assertNull(defaultHistoryManager.findActivityInstance(execution, null, false, false));
  }

  /**
   * Test {@link DefaultHistoryManager#findActivityInstance(ExecutionEntity, boolean, boolean)} with
   * {@code execution}, {@code createOnNotFound}, {@code endTimeMustBeNull}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#findActivityInstance(ExecutionEntity,
   * boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceEntity DefaultHistoryManager.findActivityInstance(ExecutionEntity, boolean, boolean)"
  })
  public void testFindActivityInstanceWithExecutionCreateOnNotFoundEndTimeMustBeNull() {
    // Arrange
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE);

    // Act and Assert
    assertNull(
        defaultHistoryManager.findActivityInstance(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true, true));
  }

  /**
   * Test {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}.
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceEntity DefaultHistoryManager.createHistoricActivityInstanceEntity(ExecutionEntity)"
  })
  public void testCreateHistoricActivityInstanceEntity() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");

    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        mock(HistoricActivityInstanceEntityManagerImpl.class);
    doNothing()
        .when(historicActivityInstanceEntityManagerImpl)
        .insert(Mockito.<HistoricActivityInstanceEntity>any());
    when(historicActivityInstanceEntityManagerImpl.create())
        .thenReturn(new HistoricActivityInstanceEntityImpl());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration.getIdGenerator()).thenReturn(idGenerator);
    when(processEngineConfiguration.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.NONE);

    // Act
    HistoricActivityInstanceEntity actualCreateHistoricActivityInstanceEntityResult =
        defaultHistoryManager.createHistoricActivityInstanceEntity(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(idGenerator).getNextId();
    verify(processEngineConfiguration, atLeast(1)).getHistoricActivityInstanceEntityManager();
    verify(processEngineConfiguration).getIdGenerator();
    verify(historicActivityInstanceEntityManagerImpl).create();
    verify(historicActivityInstanceEntityManagerImpl)
        .insert(isA(HistoricActivityInstanceEntity.class));
    Object persistentState = actualCreateHistoricActivityInstanceEntityResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(
        actualCreateHistoricActivityInstanceEntityResult
            instanceof HistoricActivityInstanceEntityImpl);
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("executionId"));
    assertNull(actualCreateHistoricActivityInstanceEntityResult.getActivityId());
    assertNull(actualCreateHistoricActivityInstanceEntityResult.getActivityType());
    assertNull(actualCreateHistoricActivityInstanceEntityResult.getExecutionId());
    assertNull(actualCreateHistoricActivityInstanceEntityResult.getProcessDefinitionId());
    assertNull(actualCreateHistoricActivityInstanceEntityResult.getProcessInstanceId());
    assertTrue(((Map<String, Object>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("endTime"));
  }

  /**
   * Test {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then return TenantId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceEntity DefaultHistoryManager.createHistoricActivityInstanceEntity(ExecutionEntity)"
  })
  public void testCreateHistoricActivityInstanceEntity_thenReturnTenantIdIs42() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");

    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        mock(HistoricActivityInstanceEntityManagerImpl.class);
    doNothing()
        .when(historicActivityInstanceEntityManagerImpl)
        .insert(Mockito.<HistoricActivityInstanceEntity>any());
    when(historicActivityInstanceEntityManagerImpl.create())
        .thenReturn(new HistoricActivityInstanceEntityImpl());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration.getIdGenerator()).thenReturn(idGenerator);
    when(processEngineConfiguration.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.NONE);

    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    HistoricActivityInstanceEntity actualCreateHistoricActivityInstanceEntityResult =
        defaultHistoryManager.createHistoricActivityInstanceEntity(execution);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    verify(execution, atLeast(1)).getTenantId();
    verify(idGenerator).getNextId();
    verify(processEngineConfiguration, atLeast(1)).getHistoricActivityInstanceEntityManager();
    verify(processEngineConfiguration).getIdGenerator();
    verify(historicActivityInstanceEntityManagerImpl).create();
    verify(historicActivityInstanceEntityManagerImpl)
        .insert(isA(HistoricActivityInstanceEntity.class));
    verify(execution).getActivityId();
    Object persistentState = actualCreateHistoricActivityInstanceEntityResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(
        actualCreateHistoricActivityInstanceEntityResult
            instanceof HistoricActivityInstanceEntityImpl);
    assertEquals(5, ((Map<String, String>) persistentState).size());
    assertEquals("42", ((Map<String, String>) persistentState).get("executionId"));
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getActivityId());
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getExecutionId());
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getProcessDefinitionId());
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getProcessInstanceId());
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getTenantId());
    assertEquals(
        "adhocSubProcess", actualCreateHistoricActivityInstanceEntityResult.getActivityType());
    assertTrue(((Map<String, String>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, String>) persistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, String>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, String>) persistentState).containsKey("endTime"));
  }

  /**
   * Test {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then return TenantId is empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceEntity DefaultHistoryManager.createHistoricActivityInstanceEntity(ExecutionEntity)"
  })
  public void testCreateHistoricActivityInstanceEntity_thenReturnTenantIdIsEmptyString() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");

    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl =
        mock(HistoricActivityInstanceEntityManagerImpl.class);
    doNothing()
        .when(historicActivityInstanceEntityManagerImpl)
        .insert(Mockito.<HistoricActivityInstanceEntity>any());
    when(historicActivityInstanceEntityManagerImpl.create())
        .thenReturn(new HistoricActivityInstanceEntityImpl());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration.getIdGenerator()).thenReturn(idGenerator);
    when(processEngineConfiguration.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.NONE);

    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn(null);
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    HistoricActivityInstanceEntity actualCreateHistoricActivityInstanceEntityResult =
        defaultHistoryManager.createHistoricActivityInstanceEntity(execution);

    // Assert
    verify(processEngineConfiguration).getClock();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getId();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    verify(execution).getTenantId();
    verify(idGenerator).getNextId();
    verify(processEngineConfiguration, atLeast(1)).getHistoricActivityInstanceEntityManager();
    verify(processEngineConfiguration).getIdGenerator();
    verify(historicActivityInstanceEntityManagerImpl).create();
    verify(historicActivityInstanceEntityManagerImpl)
        .insert(isA(HistoricActivityInstanceEntity.class));
    verify(execution).getActivityId();
    Object persistentState = actualCreateHistoricActivityInstanceEntityResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(
        actualCreateHistoricActivityInstanceEntityResult
            instanceof HistoricActivityInstanceEntityImpl);
    assertEquals("", actualCreateHistoricActivityInstanceEntityResult.getTenantId());
    assertEquals(5, ((Map<String, String>) persistentState).size());
    assertEquals("42", ((Map<String, String>) persistentState).get("executionId"));
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getActivityId());
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getExecutionId());
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getProcessDefinitionId());
    assertEquals("42", actualCreateHistoricActivityInstanceEntityResult.getProcessInstanceId());
    assertEquals(
        "adhocSubProcess", actualCreateHistoricActivityInstanceEntityResult.getActivityType());
    assertTrue(((Map<String, String>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, String>) persistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, String>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, String>) persistentState).containsKey("endTime"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessDefinitionChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessDefinitionChange(String, String)"})
  public void testRecordProcessDefinitionChange() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessDefinitionChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessDefinitionChange(String, String)"})
  public void testRecordProcessDefinitionChange2() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY)
        .recordProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessDefinitionChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessDefinitionChange(String, String)"})
  public void testRecordProcessDefinitionChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordProcessDefinitionChange("42", "42");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricProcessInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordProcessDefinitionChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessDefinitionChange(String, String)"})
  public void testRecordProcessDefinitionChange_thenCallsFindById() {
    // Arrange
    HistoricProcessInstanceEntityManager historicProcessInstanceEntityManager =
        mock(HistoricProcessInstanceEntityManager.class);
    when(historicProcessInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicProcessInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskEnd(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   *   <li>Then calls {@link HistoryLevel#isAtLeast(HistoryLevel)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskEnd(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskEnd(String, String)"})
  public void testRecordTaskEnd_givenHistoryLevelIsAtLeastReturnFalse_thenCallsIsAtLeast() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskEnd("42", "Just cause");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskEnd(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskEnd(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskEnd(String, String)"})
  public void testRecordTaskEnd_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskEnd("42", "Just cause");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskEnd(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskEnd(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskEnd(String, String)"})
  public void testRecordTaskEnd_thenCallsGetHistoricTaskInstanceEntityManager2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskEnd("42", "Just cause");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskEnd(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityImpl#markEnded(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskEnd(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskEnd(String, String)"})
  public void testRecordTaskEnd_thenCallsMarkEnded() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl =
        mock(HistoricTaskInstanceEntityImpl.class);
    doNothing().when(historicTaskInstanceEntityImpl).markEnded(Mockito.<String>any());

    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicTaskInstanceEntityImpl);
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskEnd("42", "Just cause");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityImpl).markEnded("Just cause");
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskAssigneeChange(String, String)"})
  public void testRecordTaskAssigneeChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskAssigneeChange("42", "Assignee");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskAssigneeChange(String, String)"})
  public void testRecordTaskAssigneeChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskAssigneeChange("42", "Assignee");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskAssigneeChange(String, String)"})
  public void testRecordTaskAssigneeChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskAssigneeChange("42", "Assignee");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskAssigneeChange(String, String)"})
  public void testRecordTaskAssigneeChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskAssigneeChange("42", "Assignee");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskOwnerChange(String, String)"})
  public void testRecordTaskOwnerChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskOwnerChange("42", "Owner");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskOwnerChange(String, String)"})
  public void testRecordTaskOwnerChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskOwnerChange("42", "Owner");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskOwnerChange(String, String)"})
  public void testRecordTaskOwnerChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskOwnerChange("42", "Owner");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskOwnerChange(String, String)"})
  public void testRecordTaskOwnerChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskOwnerChange("42", "Owner");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskNameChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskNameChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskNameChange(String, String)"})
  public void testRecordTaskNameChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskNameChange("42", "Task Name");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskNameChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskNameChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskNameChange(String, String)"})
  public void testRecordTaskNameChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskNameChange("42", "Task Name");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskNameChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskNameChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskNameChange(String, String)"})
  public void testRecordTaskNameChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskNameChange("42", "Task Name");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskNameChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskNameChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskNameChange(String, String)"})
  public void testRecordTaskNameChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskNameChange("42", "Task Name");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDescriptionChange(String, String)"})
  public void testRecordTaskDescriptionChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDescriptionChange(
        "42", "The characteristics of someone or something");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDescriptionChange(String, String)"})
  public void testRecordTaskDescriptionChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskDescriptionChange(
        "42", "The characteristics of someone or something");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDescriptionChange(String, String)"})
  public void testRecordTaskDescriptionChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDescriptionChange(
        "42", "The characteristics of someone or something");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDescriptionChange(String, String)"})
  public void testRecordTaskDescriptionChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskDescriptionChange("42", "The characteristics of someone or something");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDueDateChange(String, Date)"})
  public void testRecordTaskDueDateChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDueDateChange(
        "42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDueDateChange(String, Date)"})
  public void testRecordTaskDueDateChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskDueDateChange(
        "42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDueDateChange(String, Date)"})
  public void testRecordTaskDueDateChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDueDateChange(
        "42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDueDateChange(String, Date)"})
  public void testRecordTaskDueDateChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT);

    // Act
    defaultHistoryManager.recordTaskDueDateChange(
        "42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskPriorityChange(String, int)"})
  public void testRecordTaskPriorityChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskPriorityChange("42", 1);

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskPriorityChange(String, int)"})
  public void testRecordTaskPriorityChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskPriorityChange("42", 1);

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskPriorityChange(String, int)"})
  public void testRecordTaskPriorityChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskPriorityChange("42", 1);

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskPriorityChange(String, int)"})
  public void testRecordTaskPriorityChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskPriorityChange("42", 1);

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskCategoryChange(String, String)"})
  public void testRecordTaskCategoryChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskCategoryChange("42", "Category");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskCategoryChange(String, String)"})
  public void testRecordTaskCategoryChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskCategoryChange("42", "Category");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskCategoryChange(String, String)"})
  public void testRecordTaskCategoryChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskCategoryChange("42", "Category");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskCategoryChange(String, String)"})
  public void testRecordTaskCategoryChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskCategoryChange("42", "Category");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskFormKeyChange(String, String)"})
  public void testRecordTaskFormKeyChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskFormKeyChange("42", "Form Key");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskFormKeyChange(String, String)"})
  public void testRecordTaskFormKeyChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskFormKeyChange("42", "Form Key");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskFormKeyChange(String, String)"})
  public void testRecordTaskFormKeyChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskFormKeyChange("42", "Form Key");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskFormKeyChange(String, String)"})
  public void testRecordTaskFormKeyChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskFormKeyChange("42", "Form Key");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskParentTaskIdChange(String, String)"})
  public void testRecordTaskParentTaskIdChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskParentTaskIdChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskParentTaskIdChange(String, String)"})
  public void testRecordTaskParentTaskIdChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskParentTaskIdChange("42", "42");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskParentTaskIdChange(String, String)"})
  public void testRecordTaskParentTaskIdChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskParentTaskIdChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskParentTaskIdChange(String, String)"})
  public void testRecordTaskParentTaskIdChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskParentTaskIdChange("42", "42");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskExecutionIdChange(String, String)"})
  public void testRecordTaskExecutionIdChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskExecutionIdChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskExecutionIdChange(String, String)"})
  public void testRecordTaskExecutionIdChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskExecutionIdChange("42", "42");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskExecutionIdChange(String, String)"})
  public void testRecordTaskExecutionIdChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskExecutionIdChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskExecutionIdChange(String, String)"})
  public void testRecordTaskExecutionIdChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskExecutionIdChange("42", "42");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDefinitionKeyChange(String, String)"})
  public void testRecordTaskDefinitionKeyChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDefinitionKeyChange("42", "Task Definition Key");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDefinitionKeyChange(String, String)"})
  public void testRecordTaskDefinitionKeyChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskDefinitionKeyChange("42", "Task Definition Key");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDefinitionKeyChange(String, String)"})
  public void testRecordTaskDefinitionKeyChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDefinitionKeyChange("42", "Task Definition Key");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricTaskInstanceEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDefinitionKeyChange(String, String)"})
  public void testRecordTaskDefinitionKeyChange_thenCallsGetHistoricTaskInstanceEntityManager() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .recordTaskDefinitionKeyChange("42", "Task Definition Key");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordTaskProcessDefinitionChange(String, String)"
  })
  public void testRecordTaskProcessDefinitionChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordTaskProcessDefinitionChange(String, String)"
  })
  public void testRecordTaskProcessDefinitionChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager =
        mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManagerImpl =
        new HistoricTaskInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicTaskInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManagerImpl);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY)
        .recordTaskProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordTaskProcessDefinitionChange(String, String)"
  })
  public void testRecordTaskProcessDefinitionChange_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskProcessDefinitionChange("42", "42");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoricTaskInstanceEntityManager#findById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.recordTaskProcessDefinitionChange(String, String)"
  })
  public void testRecordTaskProcessDefinitionChange_thenCallsFindById() {
    // Arrange
    HistoricTaskInstanceEntityManager historicTaskInstanceEntityManager =
        mock(HistoricTaskInstanceEntityManager.class);
    when(historicTaskInstanceEntityManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(historicTaskInstanceEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.ACTIVITY);
    verify(historicTaskInstanceEntityManager).findById("42");
  }

  /**
   * Test {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String,
   * boolean)} with {@code taskId}, {@code userId}, {@code groupId}, {@code type}, {@code create}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#createIdentityLinkComment(String, String,
   * String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.createIdentityLinkComment(String, String, String, String, boolean)"
  })
  public void testCreateIdentityLinkCommentWithTaskIdUserIdGroupIdTypeCreate() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);

    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());

    CommentEntityManagerImpl commentEntityManagerImpl =
        new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(commentEntityManagerImpl);

    // Act
    defaultHistoryManager.createIdentityLinkComment("42", "42", "42", "Type", true);

    // Assert
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String,
   * boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String,
   * String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean)"
  })
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreate() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);

    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());

    CommentEntityManagerImpl commentEntityManagerImpl =
        new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(commentEntityManagerImpl);

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", "42", "Type", true);

    // Assert
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String,
   * boolean, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}, {@code
   * forceNullUserId}.
   *
   * <p>Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String,
   * String, String, boolean, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean, boolean)"
  })
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreateForceNullUserId() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);

    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());

    CommentEntityManagerImpl commentEntityManagerImpl =
        new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(commentEntityManagerImpl);

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", "42", "Type", true, true);

    // Assert
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createGroupIdentityLinkComment(String, String, String,
   * boolean)}.
   *
   * <ul>
   *   <li>Then calls {@link HistoryManager#isHistoryEnabled()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#createGroupIdentityLinkComment(String,
   * String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.createGroupIdentityLinkComment(String, String, String, boolean)"
  })
  public void testCreateGroupIdentityLinkComment_thenCallsIsHistoryEnabled() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);

    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());

    CommentEntityManagerImpl commentEntityManagerImpl =
        new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(commentEntityManagerImpl);

    // Act
    defaultHistoryManager.createGroupIdentityLinkComment("42", "42", "Type", true);

    // Assert
    verify(processEngineConfigurationImpl).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordIdentityLinkCreated(IdentityLinkEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link IdentityLinkEntity#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#recordIdentityLinkCreated(IdentityLinkEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.recordIdentityLinkCreated(IdentityLinkEntity)"})
  public void testRecordIdentityLinkCreated_thenCallsGetId() throws UnsupportedEncodingException {
    // Arrange
    HistoricIdentityLinkEntity historicIdentityLinkEntity = mock(HistoricIdentityLinkEntity.class);
    doNothing().when(historicIdentityLinkEntity).setId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntity).setDetails(Mockito.<byte[]>any());
    doNothing().when(historicIdentityLinkEntity).setGroupId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntity).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntity).setTaskId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntity).setType(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntity).setUserId(Mockito.<String>any());

    HistoricIdentityLinkDataManager historicIdentityLinkDataManager =
        mock(HistoricIdentityLinkDataManager.class);
    doNothing()
        .when(historicIdentityLinkDataManager)
        .insert(Mockito.<HistoricIdentityLinkEntity>any());
    when(historicIdentityLinkDataManager.create()).thenReturn(historicIdentityLinkEntity);
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManager =
        new HistoricIdentityLinkEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicIdentityLinkDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricIdentityLinkEntityManager(
        historicIdentityLinkEntityManager);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT);

    IdentityLinkEntity identityLink = mock(IdentityLinkEntity.class);
    when(identityLink.getDetails()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(identityLink.getId()).thenReturn("42");
    when(identityLink.getGroupId()).thenReturn("42");
    when(identityLink.getType()).thenReturn("Type");
    when(identityLink.getUserId()).thenReturn("42");
    when(identityLink.getProcessInstanceId()).thenReturn("42");
    when(identityLink.getTaskId()).thenReturn("42");

    // Act
    defaultHistoryManager.recordIdentityLinkCreated(identityLink);

    // Assert
    verify(identityLink).getId();
    verify(historicIdentityLinkEntity).setId("42");
    verify(historicIdentityLinkEntity).setDetails(isA(byte[].class));
    verify(historicIdentityLinkEntity).setGroupId("42");
    verify(historicIdentityLinkEntity).setProcessInstanceId("42");
    verify(historicIdentityLinkEntity).setTaskId("42");
    verify(historicIdentityLinkEntity).setType("Type");
    verify(historicIdentityLinkEntity).setUserId("42");
    verify(historicIdentityLinkDataManager).create();
    verify(historicIdentityLinkDataManager).insert(isA(HistoricIdentityLinkEntity.class));
    verify(identityLink).getDetails();
    verify(identityLink).getGroupId();
    verify(identityLink, atLeast(1)).getProcessInstanceId();
    verify(identityLink).getTaskId();
    verify(identityLink).getType();
    verify(identityLink).getUserId();
  }

  /**
   * Test {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}.
   *
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code
   *       false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.deleteHistoricIdentityLink(String)"})
  public void testDeleteHistoricIdentityLink_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.deleteHistoricIdentityLink("42");

    // Assert
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
  }

  /**
   * Test {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.deleteHistoricIdentityLink(String)"})
  public void testDeleteHistoricIdentityLink_thenCallsGetHistoricIdentityLinkEntityManager() {
    // Arrange
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager =
        mock(HistoricIdentityLinkEntityManager.class);
    doNothing().when(historicIdentityLinkEntityManager).delete(Mockito.<String>any());
    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.deleteHistoricIdentityLink("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricIdentityLinkEntityManager();
    verify(historyLevel).isAtLeast(HistoryLevel.AUDIT);
    verify(historicIdentityLinkEntityManager).delete("42");
  }

  /**
   * Test {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}.
   *
   * <ul>
   *   <li>Then calls {@link JtaProcessEngineConfiguration#getHistoricIdentityLinkEntityManager()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultHistoryManager.deleteHistoricIdentityLink(String)"})
  public void testDeleteHistoricIdentityLink_thenCallsGetHistoricIdentityLinkEntityManager2() {
    // Arrange
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager =
        mock(HistoricIdentityLinkEntityManager.class);
    doNothing().when(historicIdentityLinkEntityManager).delete(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManager);

    // Act
    new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)
        .deleteHistoricIdentityLink("42");

    // Assert
    verify(processEngineConfiguration).getHistoricIdentityLinkEntityManager();
    verify(historicIdentityLinkEntityManager).delete("42");
  }

  /**
   * Test {@link DefaultHistoryManager#updateProcessBusinessKeyInHistory(ExecutionEntity)}.
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#updateProcessBusinessKeyInHistory(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.updateProcessBusinessKeyInHistory(ExecutionEntity)"
  })
  public void testUpdateProcessBusinessKeyInHistory() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);

    // Act
    defaultHistoryManager.updateProcessBusinessKeyInHistory(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
  }

  /**
   * Test {@link DefaultHistoryManager#updateProcessBusinessKeyInHistory(ExecutionEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntity#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultHistoryManager#updateProcessBusinessKeyInHistory(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultHistoryManager.updateProcessBusinessKeyInHistory(ExecutionEntity)"
  })
  public void testUpdateProcessBusinessKeyInHistory_thenCallsGetId() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager =
        mock(HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.update(Mockito.<HistoricProcessInstanceEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl =
        new HistoricProcessInstanceEntityManagerImpl(
            new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY);

    ExecutionEntity processInstance = mock(ExecutionEntity.class);
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getProcessInstanceBusinessKey())
        .thenReturn("Process Instance Business Key");

    // Act
    defaultHistoryManager.updateProcessBusinessKeyInHistory(processInstance);

    // Assert
    verify(processInstance).getId();
    verify(processInstance).getProcessInstanceBusinessKey();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).findById("42");
    verify(historicProcessInstanceDataManager).update(isA(HistoricProcessInstanceEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#parseActivityType(FlowElement)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code adhocSubProcess}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultHistoryManager#parseActivityType(FlowElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String DefaultHistoryManager.parseActivityType(FlowElement)"})
  public void testParseActivityType_whenAdhocSubProcess_thenReturnAdhocSubProcess() {
    // Arrange
    DefaultHistoryManager defaultHistoryManager =
        new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE);

    // Act and Assert
    assertEquals("adhocSubProcess", defaultHistoryManager.parseActivityType(new AdhocSubProcess()));
  }
}
