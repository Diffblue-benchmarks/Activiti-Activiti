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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.ProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionInfoDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessDefinitionInfoEntityManagerImplDiffblueTest {
  @Mock
  private ProcessDefinitionInfoDataManager processDefinitionInfoDataManager;

  @InjectMocks
  private ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessDefinitionInfoEntityManagerImpl#ProcessDefinitionInfoEntityManagerImpl(ProcessEngineConfigurationImpl, ProcessDefinitionInfoDataManager)}
   *   <li>{@link ProcessDefinitionInfoEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisProcessDefinitionInfoDataManager processDefinitionInfoDataManager = new MybatisProcessDefinitionInfoDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(processDefinitionInfoDataManager,
        (new ProcessDefinitionInfoEntityManagerImpl(processEngineConfiguration, processDefinitionInfoDataManager))
            .getDataManager());
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  public void testInsertProcessDefinitionInfo() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = mock(ProcessDefinitionInfoDataManager.class);
    doNothing().when(processDefinitionInfoDataManager).insert(Mockito.<ProcessDefinitionInfoEntity>any());
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processDefinitionInfoDataManager).insert(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  public void testInsertProcessDefinitionInfo2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = mock(ProcessDefinitionInfoDataManager.class);
    doNothing().when(processDefinitionInfoDataManager).insert(Mockito.<ProcessDefinitionInfoEntity>any());
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processDefinitionInfoDataManager).insert(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#insertProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  public void testInsertProcessDefinitionInfo3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = mock(ProcessDefinitionInfoDataManager.class);
    doNothing().when(processDefinitionInfoDataManager).insert(Mockito.<ProcessDefinitionInfoEntity>any());
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.insertProcessDefinitionInfo(new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processDefinitionInfoDataManager).insert(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  public void testUpdateProcessDefinitionInfo() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = mock(ProcessDefinitionInfoDataManager.class);
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  public void testUpdateProcessDefinitionInfo2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = mock(ProcessDefinitionInfoDataManager.class);
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#updateProcessDefinitionInfo(ProcessDefinitionInfoEntity)}
   */
  @Test
  public void testUpdateProcessDefinitionInfo3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ProcessDefinitionInfoDataManager processDefinitionInfoDataManager = mock(ProcessDefinitionInfoDataManager.class);
    when(processDefinitionInfoDataManager.update(Mockito.<ProcessDefinitionInfoEntity>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManagerImpl = new ProcessDefinitionInfoEntityManagerImpl(
        processEngineConfiguration, processDefinitionInfoDataManager);

    // Act
    processDefinitionInfoEntityManagerImpl.updateProcessDefinitionInfo(new ProcessDefinitionInfoEntityImpl());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(processDefinitionInfoDataManager).update(isA(ProcessDefinitionInfoEntity.class));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  public void testDeleteProcessDefinitionInfo() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    doNothing().when(processDefinitionInfoDataManager).delete(Mockito.<ProcessDefinitionInfoEntity>any());
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    // Act
    processDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId(eq("42"));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  public void testDeleteProcessDefinitionInfo2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    doNothing().when(processDefinitionInfoDataManager).delete(Mockito.<ProcessDefinitionInfoEntity>any());
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    // Act
    processDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId(eq("42"));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  public void testDeleteProcessDefinitionInfo3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    doNothing().when(processDefinitionInfoDataManager).delete(Mockito.<ProcessDefinitionInfoEntity>any());
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionInfoEntityImpl());

    // Act
    processDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processDefinitionInfoDataManager).delete(isA(ProcessDefinitionInfoEntity.class));
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId(eq("42"));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#deleteProcessDefinitionInfo(String)}
   */
  @Test
  public void testDeleteProcessDefinitionInfo4() {
    // Arrange
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(null);

    // Act
    processDefinitionInfoEntityManagerImpl.deleteProcessDefinitionInfo("42");

    // Assert
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId(eq("42"));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#updateInfoJson(String, byte[])}
   */
  @Test
  public void testUpdateInfoJson() throws UnsupportedEncodingException {
    // Arrange
    when(processDefinitionInfoDataManager.findById(Mockito.<String>any())).thenReturn(null);

    // Act
    processDefinitionInfoEntityManagerImpl.updateInfoJson("42", "AXAXAXAX".getBytes("UTF-8"));

    // Assert
    verify(processDefinitionInfoDataManager).findById(eq("42"));
  }

  /**
   * Method under test:
   * {@link ProcessDefinitionInfoEntityManagerImpl#findProcessDefinitionInfoByProcessDefinitionId(String)}
   */
  @Test
  public void testFindProcessDefinitionInfoByProcessDefinitionId() {
    // Arrange
    ProcessDefinitionInfoEntityImpl processDefinitionInfoEntityImpl = new ProcessDefinitionInfoEntityImpl();
    when(processDefinitionInfoDataManager.findProcessDefinitionInfoByProcessDefinitionId(Mockito.<String>any()))
        .thenReturn(processDefinitionInfoEntityImpl);

    // Act
    ProcessDefinitionInfoEntity actualFindProcessDefinitionInfoByProcessDefinitionIdResult = processDefinitionInfoEntityManagerImpl
        .findProcessDefinitionInfoByProcessDefinitionId("42");

    // Assert
    verify(processDefinitionInfoDataManager).findProcessDefinitionInfoByProcessDefinitionId(eq("42"));
    assertSame(processDefinitionInfoEntityImpl, actualFindProcessDefinitionInfoByProcessDefinitionIdResult);
  }
}
