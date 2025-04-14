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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.delegate.DelegateExecution;
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
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManager;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricScopeInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.CommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.HistoricActivityInstanceDataManager;
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
  @InjectMocks
  private DefaultHistoryManager defaultHistoryManager;

  @Mock
  private HistoryLevel historyLevel;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DefaultHistoryManager#DefaultHistoryManager(ProcessEngineConfigurationImpl, HistoryLevel)}
   *   <li>{@link DefaultHistoryManager#setHistoryLevel(HistoryLevel)}
   *   <li>{@link DefaultHistoryManager#getHistoryLevel()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.<init>(ProcessEngineConfigurationImpl, HistoryLevel)",
      "HistoryLevel DefaultHistoryManager.getHistoryLevel()",
      "void DefaultHistoryManager.setHistoryLevel(HistoryLevel)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    DefaultHistoryManager actualDefaultHistoryManager = new DefaultHistoryManager(new JtaProcessEngineConfiguration(),
        HistoryLevel.NONE);
    actualDefaultHistoryManager.setHistoryLevel(HistoryLevel.NONE);

    // Assert
    assertEquals(HistoryLevel.NONE, actualDefaultHistoryManager.getHistoryLevel());
  }

  /**
   * Test {@link DefaultHistoryManager#isHistoryLevelAtLeast(HistoryLevel)}.
   * <ul>
   *   <li>When {@code ACTIVITY}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#isHistoryLevelAtLeast(HistoryLevel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DefaultHistoryManager.isHistoryLevelAtLeast(HistoryLevel)"})
  public void testIsHistoryLevelAtLeast_whenActivity_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE))
        .isHistoryLevelAtLeast(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#isHistoryLevelAtLeast(HistoryLevel)}.
   * <ul>
   *   <li>When {@code NONE}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#isHistoryLevelAtLeast(HistoryLevel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DefaultHistoryManager.isHistoryLevelAtLeast(HistoryLevel)"})
  public void testIsHistoryLevelAtLeast_whenNone_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE))
        .isHistoryLevelAtLeast(HistoryLevel.NONE));
  }

  /**
   * Test {@link DefaultHistoryManager#isHistoryEnabled()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#isHistoryEnabled()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DefaultHistoryManager.isHistoryEnabled()"})
  public void testIsHistoryEnabled_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE)).isHistoryEnabled());
  }

  /**
   * Test {@link DefaultHistoryManager#isHistoryEnabled()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#isHistoryEnabled()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DefaultHistoryManager.isHistoryEnabled()"})
  public void testIsHistoryEnabled_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        (new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY)).isHistoryEnabled());
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd2() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl = mock(HistoricProcessInstanceEntityImpl.class);
    doNothing().when(historicProcessInstanceEntityImpl).setEndActivityId(Mockito.<String>any());
    doNothing().when(historicProcessInstanceEntityImpl).markEnded(Mockito.<String>any());
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceEntityImpl).setEndActivityId(eq("42"));
    verify(historicProcessInstanceEntityImpl).markEnded(eq("Just cause"));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd3() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl = mock(HistoricProcessInstanceEntityImpl.class);
    doNothing().when(historicProcessInstanceEntityImpl).setEndActivityId(Mockito.<String>any());
    doNothing().when(historicProcessInstanceEntityImpl).markEnded(Mockito.<String>any());
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
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
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceEntityImpl).setEndActivityId(eq("42"));
    verify(historicProcessInstanceEntityImpl).markEnded(eq("Just cause"));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd4() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY)).recordProcessInstanceEnd("42",
        "Just cause", "42");

    // Assert
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl = mock(HistoricProcessInstanceEntityImpl.class);
    doNothing().when(historicProcessInstanceEntityImpl).setEndActivityId(Mockito.<String>any());
    doNothing().when(historicProcessInstanceEntityImpl).markEnded(Mockito.<String>any());
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
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
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceEntityImpl).setEndActivityId(eq("42"));
    verify(historicProcessInstanceEntityImpl).markEnded(eq("Just cause"));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordProcessInstanceEnd("42", "Just cause", "42");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceEnd(String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceEnd(String, String, String)"})
  public void testRecordProcessInstanceEnd_thenCallsDispatchEvent() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl = mock(HistoricProcessInstanceEntityImpl.class);
    doNothing().when(historicProcessInstanceEntityImpl).setEndActivityId(Mockito.<String>any());
    doNothing().when(historicProcessInstanceEntityImpl).markEnded(Mockito.<String>any());
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
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
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceEntityImpl).setEndActivityId(eq("42"));
    verify(historicProcessInstanceEntityImpl).markEnded(eq("Just cause"));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceNameChange(String, String)"})
  public void testRecordProcessInstanceNameChange() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceNameChange("42", "New Name");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceNameChange(String, String)"})
  public void testRecordProcessInstanceNameChange2() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessInstanceNameChange("42", "New Name");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceNameChange(String, String)"})
  public void testRecordProcessInstanceNameChange3() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY)).recordProcessInstanceNameChange("42",
        "New Name");

    // Assert
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceNameChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceNameChange(String, String)"})
  public void testRecordProcessInstanceNameChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordProcessInstanceNameChange("42", "New Name");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceStart(ExecutionEntity, FlowElement)"})
  public void testRecordProcessInstanceStart() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

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
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceStart(ExecutionEntity, FlowElement)"})
  public void testRecordProcessInstanceStart2() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(null);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

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
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceStart(ExecutionEntity, FlowElement)"})
  public void testRecordProcessInstanceStart_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

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
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessInstanceStart(ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessInstanceStart(ExecutionEntity, FlowElement)"})
  public void testRecordProcessInstanceStart_thenCallsDispatchEvent() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl processInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

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
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"})
  public void testRecordSubProcessInstanceStart() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"})
  public void testRecordSubProcessInstanceStart2() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(null);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"})
  public void testRecordSubProcessInstanceStart3() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"})
  public void testRecordSubProcessInstanceStart_thenCallsDispatchEvent() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}.
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getCurrentFlowElement()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"})
  public void testRecordSubProcessInstanceStart_thenCallsGetCurrentFlowElement() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl = mock(HistoricProcessInstanceEntityImpl.class);
    when(historicProcessInstanceEntityImpl.getStartActivityId()).thenReturn("42");
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution = mock(ExecutionEntityImpl.class);
    when(parentExecution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    ExecutionEntityImpl subProcessInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(parentExecution, atLeast(1)).getCurrentFlowElement();
    verify(historicProcessInstanceEntityImpl).getStartActivityId();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}.
   * <ul>
   *   <li>Then calls {@link HistoricProcessInstanceEntityImpl#getStartActivityId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.recordSubProcessInstanceStart(ExecutionEntity, ExecutionEntity, FlowElement)"})
  public void testRecordSubProcessInstanceStart_thenCallsGetStartActivityId() {
    // Arrange
    HistoricProcessInstanceEntityImpl historicProcessInstanceEntityImpl = mock(HistoricProcessInstanceEntityImpl.class);
    when(historicProcessInstanceEntityImpl.getStartActivityId()).thenReturn("42");
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    doNothing().when(historicProcessInstanceDataManager).insert(Mockito.<HistoricProcessInstanceEntity>any());
    when(historicProcessInstanceDataManager.create(Mockito.<ExecutionEntity>any()))
        .thenReturn(historicProcessInstanceEntityImpl);
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManagerImpl = new HistoricProcessInstanceEntityManagerImpl(
        new JtaProcessEngineConfiguration(), historicProcessInstanceDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(historicProcessInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntityImpl parentExecution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExecutionEntityImpl subProcessInstance = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    defaultHistoryManager.recordSubProcessInstanceStart(parentExecution, subProcessInstance, new AdhocSubProcess());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceEntityImpl).getStartActivityId();
    verify(historicProcessInstanceDataManager).insert(isA(HistoricProcessInstanceEntity.class));
    verify(historicProcessInstanceDataManager).create(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityStart(ExecutionEntity)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then calls {@link ExecutionEntityImpl#getActivityId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordActivityStart(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityStart(ExecutionEntity)"})
  public void testRecordActivityStart_givenNull_thenCallsGetActivityId() {
    // Arrange
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(new JtaProcessEngineConfiguration(),
        HistoryLevel.ACTIVITY);
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
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityEnd(ExecutionEntity, String)"})
  public void testRecordActivityEnd() {
    // Arrange
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(new JtaProcessEngineConfiguration(),
        HistoryLevel.ACTIVITY);
    ExecutionEntity executionEntity = mock(ExecutionEntity.class);
    when(executionEntity.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    defaultHistoryManager.recordActivityEnd(executionEntity, "Just cause");

    // Assert
    verify(executionEntity, atLeast(1)).getCurrentFlowElement();
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then calls {@link DelegateExecution#getCurrentFlowElement()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@link HistoryLevel} {@link HistoryLevel#isAtLeast(HistoryLevel)} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityEnd(ExecutionEntity, String)"})
  public void testRecordActivityEnd_givenHistoryLevelIsAtLeastReturnFalse() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordActivityEnd(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordActivityEnd(ExecutionEntity, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordActivityEnd(ExecutionEntity, String)"})
  public void testRecordActivityEnd_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordActivityEnd(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#findActivityInstance(ExecutionEntity, String, boolean, boolean)} with {@code execution}, {@code activityId}, {@code createOnNotFound}, {@code endTimeMustBeNull}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#findActivityInstance(ExecutionEntity, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricActivityInstanceEntity DefaultHistoryManager.findActivityInstance(ExecutionEntity, String, boolean, boolean)"})
  public void testFindActivityInstanceWithExecutionActivityIdCreateOnNotFoundEndTimeMustBeNull() {
    // Arrange, Act and Assert
    assertNull(defaultHistoryManager.findActivityInstance(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        null, true, true));
  }

  /**
   * Test {@link DefaultHistoryManager#findActivityInstance(ExecutionEntity, boolean, boolean)} with {@code execution}, {@code createOnNotFound}, {@code endTimeMustBeNull}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#findActivityInstance(ExecutionEntity, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricActivityInstanceEntity DefaultHistoryManager.findActivityInstance(ExecutionEntity, boolean, boolean)"})
  public void testFindActivityInstanceWithExecutionCreateOnNotFoundEndTimeMustBeNull() {
    // Arrange
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(new JtaProcessEngineConfiguration(),
        HistoryLevel.NONE);

    // Act and Assert
    assertNull(defaultHistoryManager.findActivityInstance(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        true, true));
  }

  /**
   * Test {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricActivityInstanceEntity DefaultHistoryManager.createHistoricActivityInstanceEntity(ExecutionEntity)"})
  public void testCreateHistoricActivityInstanceEntity() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    doNothing().when(historicActivityInstanceDataManager).insert(Mockito.<HistoricActivityInstanceEntity>any());
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    when(historicActivityInstanceDataManager.create()).thenReturn(historicActivityInstanceEntityImpl);
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfiguration, historicActivityInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration2.getIdGenerator()).thenReturn(idGenerator);
    when(processEngineConfiguration2.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration2,
        HistoryLevel.NONE);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    HistoricActivityInstanceEntity actualCreateHistoricActivityInstanceEntityResult = defaultHistoryManager
        .createHistoricActivityInstanceEntity(execution);

    // Assert
    verify(processEngineConfiguration2).getClock();
    verify(idGenerator).getNextId();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getHistoricActivityInstanceEntityManager();
    verify(processEngineConfiguration2).getIdGenerator();
    verify(execution).getId();
    verify(execution).getActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    verify(execution, atLeast(1)).getTenantId();
    verify(historicActivityInstanceDataManager).create();
    verify(historicActivityInstanceDataManager).insert(isA(HistoricActivityInstanceEntity.class));
    assertSame(historicActivityInstanceEntityImpl, actualCreateHistoricActivityInstanceEntityResult);
  }

  /**
   * Test {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricActivityInstanceEntity DefaultHistoryManager.createHistoricActivityInstanceEntity(ExecutionEntity)"})
  public void testCreateHistoricActivityInstanceEntity_givenNull() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    doNothing().when(historicActivityInstanceDataManager).insert(Mockito.<HistoricActivityInstanceEntity>any());
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    when(historicActivityInstanceDataManager.create()).thenReturn(historicActivityInstanceEntityImpl);
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfiguration, historicActivityInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration2.getIdGenerator()).thenReturn(idGenerator);
    when(processEngineConfiguration2.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration2,
        HistoryLevel.NONE);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn(null);
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    HistoricActivityInstanceEntity actualCreateHistoricActivityInstanceEntityResult = defaultHistoryManager
        .createHistoricActivityInstanceEntity(execution);

    // Assert
    verify(processEngineConfiguration2).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(idGenerator).getNextId();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getHistoricActivityInstanceEntityManager();
    verify(processEngineConfiguration2).getIdGenerator();
    verify(execution).getId();
    verify(execution).getActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    verify(execution).getTenantId();
    verify(historicActivityInstanceDataManager).create();
    verify(historicActivityInstanceDataManager).insert(isA(HistoricActivityInstanceEntity.class));
    assertSame(historicActivityInstanceEntityImpl, actualCreateHistoricActivityInstanceEntityResult);
  }

  /**
   * Test {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createHistoricActivityInstanceEntity(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricActivityInstanceEntity DefaultHistoryManager.createHistoricActivityInstanceEntity(ExecutionEntity)"})
  public void testCreateHistoricActivityInstanceEntity_thenCallsIsEnabled() {
    // Arrange
    IdGenerator idGenerator = mock(IdGenerator.class);
    when(idGenerator.getNextId()).thenReturn("42");
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    HistoricActivityInstanceDataManager historicActivityInstanceDataManager = mock(
        HistoricActivityInstanceDataManager.class);
    doNothing().when(historicActivityInstanceDataManager).insert(Mockito.<HistoricActivityInstanceEntity>any());
    HistoricActivityInstanceEntityImpl historicActivityInstanceEntityImpl = new HistoricActivityInstanceEntityImpl();
    when(historicActivityInstanceDataManager.create()).thenReturn(historicActivityInstanceEntityImpl);
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManagerImpl = new HistoricActivityInstanceEntityManagerImpl(
        processEngineConfiguration, historicActivityInstanceDataManager);

    JtaProcessEngineConfiguration processEngineConfiguration2 = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration2.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration2.getIdGenerator()).thenReturn(idGenerator);
    when(processEngineConfiguration2.getHistoricActivityInstanceEntityManager())
        .thenReturn(historicActivityInstanceEntityManagerImpl);
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration2,
        HistoryLevel.NONE);
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getId()).thenReturn("42");
    when(execution.getActivityId()).thenReturn("42");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessInstanceId()).thenReturn("42");
    when(execution.getTenantId()).thenReturn("42");
    when(execution.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    // Act
    HistoricActivityInstanceEntity actualCreateHistoricActivityInstanceEntityResult = defaultHistoryManager
        .createHistoricActivityInstanceEntity(execution);

    // Assert
    verify(processEngineConfiguration2).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(idGenerator).getNextId();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processEngineConfiguration2, atLeast(1)).getHistoricActivityInstanceEntityManager();
    verify(processEngineConfiguration2).getIdGenerator();
    verify(execution).getId();
    verify(execution).getActivityId();
    verify(execution, atLeast(1)).getCurrentFlowElement();
    verify(execution).getProcessDefinitionId();
    verify(execution).getProcessInstanceId();
    verify(execution, atLeast(1)).getTenantId();
    verify(historicActivityInstanceDataManager).create();
    verify(historicActivityInstanceDataManager).insert(isA(HistoricActivityInstanceEntity.class));
    assertSame(historicActivityInstanceEntityImpl, actualCreateHistoricActivityInstanceEntityResult);
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessDefinitionChange(String, String)"})
  public void testRecordProcessDefinitionChange() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessDefinitionChange(String, String)"})
  public void testRecordProcessDefinitionChange2() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricProcessInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessDefinitionChange(String, String)"})
  public void testRecordProcessDefinitionChange3() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY)).recordProcessDefinitionChange("42",
        "42");

    // Assert
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordProcessDefinitionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordProcessDefinitionChange(String, String)"})
  public void testRecordProcessDefinitionChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordProcessDefinitionChange("42", "42");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskEnd(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskEnd(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskEnd(String, String)"})
  public void testRecordTaskEnd() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskEnd("42", "Just cause");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskEnd(String, String)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceDataManager} {@link DataManager#findById(String)} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskEnd(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskEnd(String, String)"})
  public void testRecordTaskEnd_givenHistoricTaskInstanceDataManagerFindByIdReturnNull() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskEnd("42", "Just cause");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskEnd(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   *   <li>Then calls {@link HistoryLevel#isAtLeast(HistoryLevel)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskEnd(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskEnd(String, String)"})
  public void testRecordTaskEnd_givenProcessEngineConfigurationImpl_thenCallsIsAtLeast() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskEnd("42", "Just cause");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskEnd(String, String)}.
   * <ul>
   *   <li>Then calls {@link HistoricScopeInstanceEntityImpl#markEnded(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskEnd(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskEnd(String, String)"})
  public void testRecordTaskEnd_thenCallsMarkEnded() {
    // Arrange
    HistoricTaskInstanceEntityImpl historicTaskInstanceEntityImpl = mock(HistoricTaskInstanceEntityImpl.class);
    doNothing().when(historicTaskInstanceEntityImpl).markEnded(Mockito.<String>any());
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(historicTaskInstanceEntityImpl);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskEnd("42", "Just cause");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceEntityImpl).markEnded(eq("Just cause"));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskAssigneeChange(String, String)"})
  public void testRecordTaskAssigneeChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskAssigneeChange("42", "Assignee");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskAssigneeChange(String, String)"})
  public void testRecordTaskAssigneeChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskAssigneeChange("42", "Assignee");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskAssigneeChange(String, String)"})
  public void testRecordTaskAssigneeChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskAssigneeChange("42",
        "Assignee");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskAssigneeChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskAssigneeChange(String, String)"})
  public void testRecordTaskAssigneeChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskAssigneeChange("42", "Assignee");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskOwnerChange(String, String)"})
  public void testRecordTaskOwnerChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskOwnerChange("42", "Owner");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskOwnerChange(String, String)"})
  public void testRecordTaskOwnerChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskOwnerChange("42", "Owner");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceDataManager} {@link DataManager#findById(String)} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskOwnerChange(String, String)"})
  public void testRecordTaskOwnerChange_givenHistoricTaskInstanceDataManagerFindByIdReturnNull() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskOwnerChange("42", "Owner");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   *   <li>Then calls {@link HistoryLevel#isAtLeast(HistoryLevel)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskOwnerChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskOwnerChange(String, String)"})
  public void testRecordTaskOwnerChange_givenProcessEngineConfigurationImpl_thenCallsIsAtLeast() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskOwnerChange("42", "Owner");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskNameChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskNameChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskNameChange(String, String)"})
  public void testRecordTaskNameChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskNameChange("42", "Task Name");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskNameChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskNameChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskNameChange(String, String)"})
  public void testRecordTaskNameChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskNameChange("42", "Task Name");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskNameChange(String, String)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceDataManager} {@link DataManager#findById(String)} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskNameChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskNameChange(String, String)"})
  public void testRecordTaskNameChange_givenHistoricTaskInstanceDataManagerFindByIdReturnNull() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskNameChange("42", "Task Name");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskNameChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   *   <li>Then calls {@link HistoryLevel#isAtLeast(HistoryLevel)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskNameChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskNameChange(String, String)"})
  public void testRecordTaskNameChange_givenProcessEngineConfigurationImpl_thenCallsIsAtLeast() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskNameChange("42", "Task Name");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDescriptionChange(String, String)"})
  public void testRecordTaskDescriptionChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDescriptionChange("42", "The characteristics of someone or something");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDescriptionChange(String, String)"})
  public void testRecordTaskDescriptionChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDescriptionChange("42", "The characteristics of someone or something");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDescriptionChange(String, String)"})
  public void testRecordTaskDescriptionChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskDescriptionChange("42",
        "The characteristics of someone or something");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDescriptionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDescriptionChange(String, String)"})
  public void testRecordTaskDescriptionChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskDescriptionChange("42", "The characteristics of someone or something");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDueDateChange(String, Date)"})
  public void testRecordTaskDueDateChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDueDateChange("42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDueDateChange(String, Date)"})
  public void testRecordTaskDueDateChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDueDateChange("42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDueDateChange(String, Date)"})
  public void testRecordTaskDueDateChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.AUDIT);

    // Act
    defaultHistoryManager.recordTaskDueDateChange("42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDueDateChange(String, Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDueDateChange(String, Date)"})
  public void testRecordTaskDueDateChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskDueDateChange("42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskPriorityChange(String, int)"})
  public void testRecordTaskPriorityChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskPriorityChange("42", 1);

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskPriorityChange(String, int)"})
  public void testRecordTaskPriorityChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskPriorityChange("42", 1);

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskPriorityChange(String, int)"})
  public void testRecordTaskPriorityChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskPriorityChange("42", 1);

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskPriorityChange(String, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskPriorityChange(String, int)"})
  public void testRecordTaskPriorityChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskPriorityChange("42", 1);

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskCategoryChange(String, String)"})
  public void testRecordTaskCategoryChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskCategoryChange("42", "Category");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskCategoryChange(String, String)"})
  public void testRecordTaskCategoryChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskCategoryChange("42", "Category");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskCategoryChange(String, String)"})
  public void testRecordTaskCategoryChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskCategoryChange("42",
        "Category");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskCategoryChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskCategoryChange(String, String)"})
  public void testRecordTaskCategoryChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskCategoryChange("42", "Category");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskFormKeyChange(String, String)"})
  public void testRecordTaskFormKeyChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskFormKeyChange("42", "Form Key");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskFormKeyChange(String, String)"})
  public void testRecordTaskFormKeyChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskFormKeyChange("42", "Form Key");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskFormKeyChange(String, String)"})
  public void testRecordTaskFormKeyChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskFormKeyChange("42",
        "Form Key");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskFormKeyChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskFormKeyChange(String, String)"})
  public void testRecordTaskFormKeyChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskFormKeyChange("42", "Form Key");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskParentTaskIdChange(String, String)"})
  public void testRecordTaskParentTaskIdChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskParentTaskIdChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskParentTaskIdChange(String, String)"})
  public void testRecordTaskParentTaskIdChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskParentTaskIdChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskParentTaskIdChange(String, String)"})
  public void testRecordTaskParentTaskIdChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskParentTaskIdChange("42",
        "42");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskParentTaskIdChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskParentTaskIdChange(String, String)"})
  public void testRecordTaskParentTaskIdChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskParentTaskIdChange("42", "42");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskExecutionIdChange(String, String)"})
  public void testRecordTaskExecutionIdChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskExecutionIdChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskExecutionIdChange(String, String)"})
  public void testRecordTaskExecutionIdChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskExecutionIdChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskExecutionIdChange(String, String)"})
  public void testRecordTaskExecutionIdChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskExecutionIdChange("42", "42");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskExecutionIdChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskExecutionIdChange(String, String)"})
  public void testRecordTaskExecutionIdChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskExecutionIdChange("42", "42");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDefinitionKeyChange(String, String)"})
  public void testRecordTaskDefinitionKeyChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDefinitionKeyChange("42", "Task Definition Key");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDefinitionKeyChange(String, String)"})
  public void testRecordTaskDefinitionKeyChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskDefinitionKeyChange("42", "Task Definition Key");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDefinitionKeyChange(String, String)"})
  public void testRecordTaskDefinitionKeyChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).recordTaskDefinitionKeyChange("42",
        "Task Definition Key");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskDefinitionKeyChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskDefinitionKeyChange(String, String)"})
  public void testRecordTaskDefinitionKeyChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskDefinitionKeyChange("42", "Task Definition Key");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskProcessDefinitionChange(String, String)"})
  public void testRecordTaskProcessDefinitionChange() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskProcessDefinitionChange(String, String)"})
  public void testRecordTaskProcessDefinitionChange2() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any())).thenReturn(null);
    when(processEngineConfigurationImpl.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.recordTaskProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricTaskInstanceEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskProcessDefinitionChange(String, String)"})
  public void testRecordTaskProcessDefinitionChange3() {
    // Arrange
    HistoricTaskInstanceDataManager historicTaskInstanceDataManager = mock(HistoricTaskInstanceDataManager.class);
    when(historicTaskInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricTaskInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricTaskInstanceEntityManager())
        .thenReturn(new HistoricTaskInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicTaskInstanceDataManager));

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.ACTIVITY))
        .recordTaskProcessDefinitionChange("42", "42");

    // Assert
    verify(processEngineConfiguration).getHistoricTaskInstanceEntityManager();
    verify(historicTaskInstanceDataManager).findById(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordTaskProcessDefinitionChange(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordTaskProcessDefinitionChange(String, String)"})
  public void testRecordTaskProcessDefinitionChange_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.recordTaskProcessDefinitionChange("42", "42");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code groupId}, {@code type}, {@code create}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createIdentityLinkComment(String, String, String, String, boolean)"})
  public void testCreateIdentityLinkCommentWithTaskIdUserIdGroupIdTypeCreate() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createIdentityLinkComment("42", "42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code groupId}, {@code type}, {@code create}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createIdentityLinkComment(String, String, String, String, boolean)"})
  public void testCreateIdentityLinkCommentWithTaskIdUserIdGroupIdTypeCreate2() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createIdentityLinkComment("42", "42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code groupId}, {@code type}, {@code create}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createIdentityLinkComment(String, String, String, String, boolean)"})
  public void testCreateIdentityLinkCommentWithTaskIdUserIdGroupIdTypeCreate3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createIdentityLinkComment("42", "42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean, boolean)} with {@code taskId}, {@code userId}, {@code groupId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createIdentityLinkComment(String, String, String, String, boolean, boolean)"})
  public void testCreateIdentityLinkCommentWithTaskIdUserIdGroupIdTypeCreateForceNullUserId() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoryManager()).thenReturn(historyManager);
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        commentDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration2.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);

    // Act
    (new DefaultHistoryManager(processEngineConfiguration2, HistoryLevel.ACTIVITY)).createIdentityLinkComment("42",
        "42", "42", "Type", true, true);

    // Assert
    verify(processEngineConfiguration2).getClock();
    verify(processEngineConfiguration2, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean, boolean)} with {@code taskId}, {@code userId}, {@code groupId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createIdentityLinkComment(String, String, String, String, boolean, boolean)"})
  public void testCreateIdentityLinkCommentWithTaskIdUserIdGroupIdTypeCreateForceNullUserId2() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfiguration.getHistoryManager()).thenReturn(historyManager);
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        commentDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration2.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);

    // Act
    (new DefaultHistoryManager(processEngineConfiguration2, HistoryLevel.ACTIVITY)).createIdentityLinkComment("42",
        null, "42", "Type", false, false);

    // Assert
    verify(processEngineConfiguration2).getClock();
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration2, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code groupId}, {@code type}, {@code create}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createIdentityLinkComment(String, String, String, String, boolean)"})
  public void testCreateIdentityLinkCommentWithTaskIdUserIdGroupIdTypeCreate_whenNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createIdentityLinkComment("42", null, "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code groupId}, {@code type}, {@code create}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createIdentityLinkComment(String, String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createIdentityLinkComment(String, String, String, String, boolean)"})
  public void testCreateIdentityLinkCommentWithTaskIdUserIdGroupIdTypeCreate_whenNull2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createIdentityLinkComment("42", null, "42", "Type", true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreate() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreate2() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreateForceNullUserId() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", "42", "Type", false, true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreateForceNullUserId2() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", "42", "Type", false, true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreateForceNullUserId3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", "42", "Type", false, true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreateForceNullUserId4() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", null, "Type", false, true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreateForceNullUserId5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", null, "Type", false, false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreateForceNullUserId6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", null, "Type", true, false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreate_thenCallsIsEnabled() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreate_whenNull() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", null, "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)} with {@code taskId}, {@code userId}, {@code type}, {@code create}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createUserIdentityLinkComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createUserIdentityLinkComment(String, String, String, boolean)"})
  public void testCreateUserIdentityLinkCommentWithTaskIdUserIdTypeCreate_whenNull2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createUserIdentityLinkComment("42", null, "Type", true);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createProcessInstanceIdentityLinkComment(String, String, String, String, boolean)} with {@code processInstanceId}, {@code userId}, {@code groupId}, {@code type}, {@code create}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createProcessInstanceIdentityLinkComment(String, String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createProcessInstanceIdentityLinkComment(String, String, String, String, boolean)"})
  public void testCreateProcessInstanceIdentityLinkCommentWithProcessInstanceIdUserIdGroupIdTypeCreate() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoryManager()).thenReturn(historyManager);
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        commentDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration2.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);

    // Act
    (new DefaultHistoryManager(processEngineConfiguration2, HistoryLevel.ACTIVITY))
        .createProcessInstanceIdentityLinkComment("42", "42", "42", "Type", false);

    // Assert
    verify(processEngineConfiguration2).getClock();
    verify(processEngineConfiguration2, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(executionDataManager).findById(eq("42"));
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createProcessInstanceIdentityLinkComment(String, String, String, String, boolean, boolean)} with {@code processInstanceId}, {@code userId}, {@code groupId}, {@code type}, {@code create}, {@code forceNullUserId}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createProcessInstanceIdentityLinkComment(String, String, String, String, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultHistoryManager.createProcessInstanceIdentityLinkComment(String, String, String, String, boolean, boolean)"})
  public void testCreateProcessInstanceIdentityLinkCommentWithProcessInstanceIdUserIdGroupIdTypeCreateForceNullUserId() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoryManager()).thenReturn(historyManager);
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        commentDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration2.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);

    // Act
    (new DefaultHistoryManager(processEngineConfiguration2, HistoryLevel.ACTIVITY))
        .createProcessInstanceIdentityLinkComment("42", "42", "42", "Type", false, true);

    // Assert
    verify(processEngineConfiguration2).getClock();
    verify(processEngineConfiguration2, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(executionDataManager).findById(eq("42"));
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createGroupIdentityLinkComment(String, String, String, boolean)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#createGroupIdentityLinkComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createGroupIdentityLinkComment(String, String, String, boolean)"})
  public void testCreateGroupIdentityLinkComment() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createGroupIdentityLinkComment("42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createGroupIdentityLinkComment(String, String, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#isEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createGroupIdentityLinkComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createGroupIdentityLinkComment(String, String, String, boolean)"})
  public void testCreateGroupIdentityLinkComment_thenCallsIsEnabled() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createGroupIdentityLinkComment("42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createGroupIdentityLinkComment(String, String, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link HistoryManager#isHistoryEnabled()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createGroupIdentityLinkComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createGroupIdentityLinkComment(String, String, String, boolean)"})
  public void testCreateGroupIdentityLinkComment_thenCallsIsHistoryEnabled() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(mock(HistoryManager.class));
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getHistoryManager()).thenReturn(historyManager);
    when(processEngineConfigurationImpl.getClock()).thenReturn(new DefaultClockImpl());
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    when(processEngineConfigurationImpl.getCommentEntityManager())
        .thenReturn(new CommentEntityManagerImpl(processEngineConfigurationImpl, commentDataManager));

    // Act
    defaultHistoryManager.createGroupIdentityLinkComment("42", "42", "Type", false);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getClock();
    verify(processEngineConfigurationImpl, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#createAttachmentComment(String, String, String, boolean)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#createAttachmentComment(String, String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.createAttachmentComment(String, String, String, boolean)"})
  public void testCreateAttachmentComment_thenCallsGetEventDispatcher() {
    // Arrange
    HistoryManager historyManager = mock(HistoryManager.class);
    when(historyManager.isHistoryEnabled()).thenReturn(true);
    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getExecutionEntityManager())
        .thenReturn(new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager));
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfiguration.getHistoryManager()).thenReturn(historyManager);
    CommentDataManager commentDataManager = mock(CommentDataManager.class);
    doNothing().when(commentDataManager).insert(Mockito.<CommentEntity>any());
    when(commentDataManager.create()).thenReturn(new CommentEntityImpl());
    CommentEntityManagerImpl commentEntityManagerImpl = new CommentEntityManagerImpl(processEngineConfiguration,
        commentDataManager);

    ProcessEngineConfigurationImpl processEngineConfiguration2 = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration2.getClock()).thenReturn(new DefaultClockImpl());
    when(processEngineConfiguration2.getCommentEntityManager()).thenReturn(commentEntityManagerImpl);

    // Act
    (new DefaultHistoryManager(processEngineConfiguration2, HistoryLevel.ACTIVITY)).createAttachmentComment("42", "42",
        "Attachment Name", false);

    // Assert
    verify(processEngineConfiguration2).getClock();
    verify(processEngineConfiguration2, atLeast(1)).getCommentEntityManager();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processEngineConfiguration).getExecutionEntityManager();
    verify(processEngineConfiguration).getHistoryManager();
    verify(historyManager).isHistoryEnabled();
    verify(commentDataManager).create();
    verify(executionDataManager).findById(eq("42"));
    verify(commentDataManager).insert(isA(CommentEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordIdentityLinkCreated(IdentityLinkEntity)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordIdentityLinkCreated(IdentityLinkEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordIdentityLinkCreated(IdentityLinkEntity)"})
  public void testRecordIdentityLinkCreated_givenNull() throws UnsupportedEncodingException {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl = mock(HistoricIdentityLinkEntityImpl.class);
    doNothing().when(historicIdentityLinkEntityImpl).setId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setDetails(Mockito.<byte[]>any());
    doNothing().when(historicIdentityLinkEntityImpl).setGroupId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setTaskId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setType(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setUserId(Mockito.<String>any());
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    doNothing().when(historicIdentityLinkDataManager).insert(Mockito.<HistoricIdentityLinkEntity>any());
    when(historicIdentityLinkDataManager.create()).thenReturn(historicIdentityLinkEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricIdentityLinkEntityManager())
        .thenReturn(new HistoricIdentityLinkEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicIdentityLinkDataManager));
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.AUDIT);
    IdentityLinkEntityImpl identityLink = mock(IdentityLinkEntityImpl.class);
    when(identityLink.getDetails()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(identityLink.getId()).thenReturn("42");
    when(identityLink.getGroupId()).thenReturn("42");
    when(identityLink.getType()).thenReturn("Type");
    when(identityLink.getUserId()).thenReturn("42");
    when(identityLink.getProcessInstanceId()).thenReturn(null);
    when(identityLink.getTaskId()).thenReturn("42");

    // Act
    defaultHistoryManager.recordIdentityLinkCreated(identityLink);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getHistoricIdentityLinkEntityManager();
    verify(identityLink).getId();
    verify(historicIdentityLinkEntityImpl).setId(eq("42"));
    verify(historicIdentityLinkEntityImpl).setDetails(isA(byte[].class));
    verify(historicIdentityLinkEntityImpl).setGroupId(eq("42"));
    verify(historicIdentityLinkEntityImpl).setProcessInstanceId(isNull());
    verify(historicIdentityLinkEntityImpl).setTaskId(eq("42"));
    verify(historicIdentityLinkEntityImpl).setType(eq("Type"));
    verify(historicIdentityLinkEntityImpl).setUserId(eq("42"));
    verify(identityLink).getDetails();
    verify(identityLink).getGroupId();
    verify(identityLink, atLeast(1)).getProcessInstanceId();
    verify(identityLink, atLeast(1)).getTaskId();
    verify(identityLink).getType();
    verify(identityLink).getUserId();
    verify(historicIdentityLinkDataManager).create();
    verify(historicIdentityLinkDataManager).insert(isA(HistoricIdentityLinkEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#recordIdentityLinkCreated(IdentityLinkEntity)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getHistoricIdentityLinkEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#recordIdentityLinkCreated(IdentityLinkEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.recordIdentityLinkCreated(IdentityLinkEntity)"})
  public void testRecordIdentityLinkCreated_thenCallsGetHistoricIdentityLinkEntityManager()
      throws UnsupportedEncodingException {
    // Arrange
    HistoricIdentityLinkEntityImpl historicIdentityLinkEntityImpl = mock(HistoricIdentityLinkEntityImpl.class);
    doNothing().when(historicIdentityLinkEntityImpl).setId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setDetails(Mockito.<byte[]>any());
    doNothing().when(historicIdentityLinkEntityImpl).setGroupId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setProcessInstanceId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setTaskId(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setType(Mockito.<String>any());
    doNothing().when(historicIdentityLinkEntityImpl).setUserId(Mockito.<String>any());
    HistoricIdentityLinkDataManager historicIdentityLinkDataManager = mock(HistoricIdentityLinkDataManager.class);
    doNothing().when(historicIdentityLinkDataManager).insert(Mockito.<HistoricIdentityLinkEntity>any());
    when(historicIdentityLinkDataManager.create()).thenReturn(historicIdentityLinkEntityImpl);
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricIdentityLinkEntityManager())
        .thenReturn(new HistoricIdentityLinkEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicIdentityLinkDataManager));
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.AUDIT);
    IdentityLinkEntityImpl identityLink = mock(IdentityLinkEntityImpl.class);
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
    verify(processEngineConfiguration, atLeast(1)).getHistoricIdentityLinkEntityManager();
    verify(identityLink).getId();
    verify(historicIdentityLinkEntityImpl).setId(eq("42"));
    verify(historicIdentityLinkEntityImpl).setDetails(isA(byte[].class));
    verify(historicIdentityLinkEntityImpl).setGroupId(eq("42"));
    verify(historicIdentityLinkEntityImpl).setProcessInstanceId(eq("42"));
    verify(historicIdentityLinkEntityImpl).setTaskId(eq("42"));
    verify(historicIdentityLinkEntityImpl).setType(eq("Type"));
    verify(historicIdentityLinkEntityImpl).setUserId(eq("42"));
    verify(identityLink).getDetails();
    verify(identityLink).getGroupId();
    verify(identityLink, atLeast(1)).getProcessInstanceId();
    verify(identityLink).getTaskId();
    verify(identityLink).getType();
    verify(identityLink).getUserId();
    verify(historicIdentityLinkDataManager).create();
    verify(historicIdentityLinkDataManager).insert(isA(HistoricIdentityLinkEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.deleteHistoricIdentityLink(String)"})
  public void testDeleteHistoricIdentityLink() {
    // Arrange
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = mock(HistoricIdentityLinkEntityManager.class);
    doNothing().when(historicIdentityLinkEntityManager).delete(Mockito.<String>any());
    when(processEngineConfigurationImpl.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManager);
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(true);

    // Act
    defaultHistoryManager.deleteHistoricIdentityLink("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoricIdentityLinkEntityManager();
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
    verify(historicIdentityLinkEntityManager).delete(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.deleteHistoricIdentityLink(String)"})
  public void testDeleteHistoricIdentityLink2() {
    // Arrange
    HistoricIdentityLinkEntityManager historicIdentityLinkEntityManager = mock(HistoricIdentityLinkEntityManager.class);
    doNothing().when(historicIdentityLinkEntityManager).delete(Mockito.<String>any());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricIdentityLinkEntityManager())
        .thenReturn(historicIdentityLinkEntityManager);

    // Act
    (new DefaultHistoryManager(processEngineConfiguration, HistoryLevel.AUDIT)).deleteHistoricIdentityLink("42");

    // Assert
    verify(processEngineConfiguration).getHistoricIdentityLinkEntityManager();
    verify(historicIdentityLinkEntityManager).delete(eq("42"));
  }

  /**
   * Test {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}.
   * <ul>
   *   <li>Given {@link ProcessEngineConfigurationImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#deleteHistoricIdentityLink(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.deleteHistoricIdentityLink(String)"})
  public void testDeleteHistoricIdentityLink_givenProcessEngineConfigurationImpl() {
    // Arrange
    when(historyLevel.isAtLeast(Mockito.<HistoryLevel>any())).thenReturn(false);

    // Act
    defaultHistoryManager.deleteHistoricIdentityLink("42");

    // Assert
    verify(historyLevel).isAtLeast(eq(HistoryLevel.AUDIT));
  }

  /**
   * Test {@link DefaultHistoryManager#updateProcessBusinessKeyInHistory(ExecutionEntity)}.
   * <p>
   * Method under test: {@link DefaultHistoryManager#updateProcessBusinessKeyInHistory(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.updateProcessBusinessKeyInHistory(ExecutionEntity)"})
  public void testUpdateProcessBusinessKeyInHistory() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(processEngineConfiguration2,
            new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration())));
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);

    // Act
    defaultHistoryManager
        .updateProcessBusinessKeyInHistory(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(processEngineConfiguration).getHistoricProcessInstanceEntityManager();
  }

  /**
   * Test {@link DefaultHistoryManager#updateProcessBusinessKeyInHistory(ExecutionEntity)}.
   * <ul>
   *   <li>Then calls {@link DelegateExecution#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#updateProcessBusinessKeyInHistory(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultHistoryManager.updateProcessBusinessKeyInHistory(ExecutionEntity)"})
  public void testUpdateProcessBusinessKeyInHistory_thenCallsGetId() {
    // Arrange
    HistoricProcessInstanceDataManager historicProcessInstanceDataManager = mock(
        HistoricProcessInstanceDataManager.class);
    when(historicProcessInstanceDataManager.update(Mockito.<HistoricProcessInstanceEntity>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    when(historicProcessInstanceDataManager.findById(Mockito.<String>any()))
        .thenReturn(new HistoricProcessInstanceEntityImpl());
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoricProcessInstanceEntityManager())
        .thenReturn(new HistoricProcessInstanceEntityManagerImpl(new JtaProcessEngineConfiguration(),
            historicProcessInstanceDataManager));
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(processEngineConfiguration,
        HistoryLevel.ACTIVITY);
    ExecutionEntity processInstance = mock(ExecutionEntity.class);
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");

    // Act
    defaultHistoryManager.updateProcessBusinessKeyInHistory(processInstance);

    // Assert
    verify(processInstance).getId();
    verify(processInstance).getProcessInstanceBusinessKey();
    verify(processEngineConfiguration, atLeast(1)).getHistoricProcessInstanceEntityManager();
    verify(historicProcessInstanceDataManager).findById(eq("42"));
    verify(historicProcessInstanceDataManager).update(isA(HistoricProcessInstanceEntity.class));
  }

  /**
   * Test {@link DefaultHistoryManager#parseActivityType(FlowElement)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code adhocSubProcess}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultHistoryManager#parseActivityType(FlowElement)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DefaultHistoryManager.parseActivityType(FlowElement)"})
  public void testParseActivityType_whenAdhocSubProcess_thenReturnAdhocSubProcess() {
    // Arrange
    DefaultHistoryManager defaultHistoryManager = new DefaultHistoryManager(new JtaProcessEngineConfiguration(),
        HistoryLevel.NONE);

    // Act and Assert
    assertEquals("adhocSubProcess", defaultHistoryManager.parseActivityType(new AdhocSubProcess()));
  }
}
