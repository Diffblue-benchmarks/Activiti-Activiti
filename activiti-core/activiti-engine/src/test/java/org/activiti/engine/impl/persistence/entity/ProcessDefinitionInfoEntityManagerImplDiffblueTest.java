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

import static org.junit.Assert.assertSame;
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
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionInfoDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessDefinitionInfoEntityManagerImplDiffblueTest {
  @Mock private ProcessDefinitionInfoDataManager processDefinitionInfoDataManager;

  @InjectMocks
  private ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       ProcessDefinitionInfoEntityManagerImpl#ProcessDefinitionInfoEntityManagerImpl(ProcessEngineConfigurationImpl,
   *       ProcessDefinitionInfoDataManager)}
   *   <li>{@link ProcessDefinitionInfoEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, ProcessDefinitionInfoDataManager)",
    "org.activiti.engine.impl.persistence.entity.data.DataManager ProcessDefinitionInfoEntityManagerImpl.getDataManager()"
  })
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration());

    // Act
    ProcessDefinitionInfoEntityManagerImpl actualProcessDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration, processDefinitionInfoDataManager);

    // Assert
    assertSame(
        processDefinitionInfoDataManager,
        actualProcessDefinitionInfoEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link
   * ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)"
  })
  public void testInsertProcessDefinitionInfo() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    doNothing()
        .when(processDefinitionInfoDataManager)
        .insert(Mockito.<ProcessDefinitionInfoEntity>any());

    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(
        new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(processDefinitionInfoDataManager).insert(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link
   * ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)"
  })
  public void testInsertProcessDefinitionInfo_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    doNothing()
        .when(processDefinitionInfoDataManager)
        .insert(Mockito.<ProcessDefinitionInfoEntity>any());

    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(
        new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(eventDispatcher).isEnabled();
    verify(processDefinitionInfoDataManager).insert(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link
   * ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)"
  })
  public void testInsertProcessDefinitionInfo_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    doNothing()
        .when(processDefinitionInfoDataManager)
        .insert(Mockito.<ProcessDefinitionInfoEntity>any());

    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(
        new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(processDefinitionInfoDataManager).insert(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link
   * ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)"
  })
  public void testUpdateProcessDefinitionInfo() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(
        new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link
   * ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)"
  })
  public void testUpdateProcessDefinitionInfo_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(
        new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(eventDispatcher).isEnabled();
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link
   * ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)"
  })
  public void testUpdateProcessDefinitionInfo_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(
        new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(eventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(eventDispatcher).isEnabled();
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo(String)"
  })
  public void testDeleteProcessDefinitionInfo() {
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

    // Act
    processDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId("42");
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo(String)"
  })
  public void testDeleteProcessDefinitionInfo2() {
    // Arrange
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(
            Mockito.<String>any()))
        .thenReturn(null);
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId("42");
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo(String)"
  })
  public void testDeleteProcessDefinitionInfo_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    ProcessDefinitionInfoEntity processDefinitionInfoEntity =
        mock(ProcessDefinitionInfoEntity.class);
    when(processDefinitionInfoEntity.getInfoJsonId()).thenReturn(null);
    doNothing()
        .when(processDefinitionInfoDataManager)
        .delete(Mockito.<ProcessDefinitionInfoEntity>any());
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(
            Mockito.<String>any()))
        .thenReturn(processDefinitionInfoEntity);

    // Act
    processDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processDefinitionInfoEntity).getInfoJsonId();
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId("42");
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo(String)"
  })
  public void testDeleteProcessDefinitionInfo_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    ProcessDefinitionInfoEntity processDefinitionInfoEntity =
        mock(ProcessDefinitionInfoEntity.class);
    when(processDefinitionInfoEntity.getInfoJsonId()).thenReturn(null);
    doNothing()
        .when(processDefinitionInfoDataManager)
        .delete(Mockito.<ProcessDefinitionInfoEntity>any());
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(
            Mockito.<String>any()))
        .thenReturn(processDefinitionInfoEntity);

    // Act
    processDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processDefinitionInfoEntity).getInfoJsonId();
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId("42");
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String, byte[])}.
   *
   * <p>Method under test: {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String,
   * byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDefinitionInfoEntityManagerImpl.updateInfoJson(String, byte[])"})
  public void testUpdateInfoJson() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());
    when(processDefinitionInfoDataManager.findById(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    // Act
    processDefinitionInfoEntityManagerImpl.updateInfoJson("42", null);

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processDefinitionInfoDataManager).findById("42");
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String, byte[])}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String,
   * byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDefinitionInfoEntityManagerImpl.updateInfoJson(String, byte[])"})
  public void testUpdateInfoJson_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());
    when(processDefinitionInfoDataManager.findById(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    // Act
    processDefinitionInfoEntityManagerImpl.updateInfoJson("42", null);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processDefinitionInfoDataManager).findById("42");
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String, byte[])}.
   *
   * <ul>
   *   <li>Given {@link ProcessDefinitionInfoDataManager} {@link
   *       ProcessDefinitionInfoDataManager#findById(String)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String,
   * byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDefinitionInfoEntityManagerImpl.updateInfoJson(String, byte[])"})
  public void testUpdateInfoJson_givenProcessDefinitionInfoDataManagerFindByIdReturnNull()
      throws UnsupportedEncodingException {
    // Arrange
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    when(processDefinitionInfoDataManager.findById(Mockito.<String>any())).thenReturn(null);
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.updateInfoJson("42", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(processDefinitionInfoDataManager).findById("42");
  }

  /**
   * Test {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String, byte[])}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String,
   * byte[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDefinitionInfoEntityManagerImpl.updateInfoJson(String, byte[])"})
  public void testUpdateInfoJson_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());
    when(processDefinitionInfoDataManager.findById(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    // Act
    processDefinitionInfoEntityManagerImpl.updateInfoJson("42", null);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processDefinitionInfoDataManager).findById("42");
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Test {@link
   * ProcessDefinitionInfoEntityManagerImpl#findProcessDefinitionInfoByProcessDefinitionId(String)}.
   *
   * <p>Method under test: {@link
   * ProcessDefinitionInfoEntityManagerImpl#findProcessDefinitionInfoByProcessDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessDefinitionInfoEntity ProcessDefinitionInfoEntityManagerImpl.findProcessDefinitionInfoByProcessDefinitionId(String)"
  })
  public void testFindProcessDefinitionInfoByProcessDefinitionId() {
    // Arrange
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager =
        mock(ProcessDefinitionInfoDataManager.class);
    ProcessDefinitionInfoEntityImpl processDefinitionInfoEntityImpl =
        new ProcessDefinitionInfoEntityImpl();
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(
            Mockito.<String>any()))
        .thenReturn(processDefinitionInfoEntityImpl);
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl =
        new ProcessDefinitionInfoEntityManagerImpl(
            new JtaProcessEngineConfiguration(), processDefinitionInfoDataManager);

    // Act
    ProcessDefinitionInfoEntity actualFindProcessDefinitionInfoByProcessDefinitionIdResult =
        processDefinitionInfoEntityManagerImpl.findProcessDefinitionInfoByProcessDefinitionId("42");

    // Assert
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId("42");
    assertSame(
        processDefinitionInfoEntityImpl,
        actualFindProcessDefinitionInfoByProcessDefinitionIdResult);
  }
}
