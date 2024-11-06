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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentEntityManagerImplDiffblueTest {
  @Mock
  private AttachmentDataManager attachmentDataManager;

  @InjectMocks
  private AttachmentEntityManagerImpl attachmentEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link AttachmentEntityManagerImpl#AttachmentEntityManagerImpl(ProcessEngineConfigurationImpl, AttachmentDataManager)}
   *   <li>
   * {@link AttachmentEntityManagerImpl#setAttachmentDataManager(AttachmentDataManager)}
   *   <li>{@link AttachmentEntityManagerImpl#getAttachmentDataManager()}
   *   <li>{@link AttachmentEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    AttachmentEntityManagerImpl actualAttachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));
    MybatisAttachmentDataManager attachmentDataManager = new MybatisAttachmentDataManager(
        new JtaProcessEngineConfiguration());
    actualAttachmentEntityManagerImpl.setAttachmentDataManager(attachmentDataManager);
    AttachmentDataManager actualAttachmentDataManager = actualAttachmentEntityManagerImpl.getAttachmentDataManager();

    // Assert that nothing has changed
    assertSame(attachmentDataManager, actualAttachmentDataManager);
    assertSame(attachmentDataManager, actualAttachmentEntityManagerImpl.getDataManager());
  }

  /**
   * Test
   * {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindAttachmentsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> attachmentEntityManagerImpl.findAttachmentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test
   * {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindAttachmentsByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(attachmentDataManager.findAttachmentsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> attachmentEntityManagerImpl.findAttachmentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(attachmentDataManager).findAttachmentsByProcessInstanceId(eq("42"));
  }

  /**
   * Test
   * {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}
   */
  @Test
  public void testFindAttachmentsByProcessInstanceId_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(attachmentDataManager.findAttachmentsByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<AttachmentEntity> actualFindAttachmentsByProcessInstanceIdResult = attachmentEntityManagerImpl
        .findAttachmentsByProcessInstanceId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(attachmentDataManager).findAttachmentsByProcessInstanceId(eq("42"));
    assertTrue(actualFindAttachmentsByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}
   */
  @Test
  public void testFindAttachmentsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> attachmentEntityManagerImpl.findAttachmentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}
   */
  @Test
  public void testFindAttachmentsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(attachmentDataManager.findAttachmentsByTaskId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> attachmentEntityManagerImpl.findAttachmentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(attachmentDataManager).findAttachmentsByTaskId(eq("42"));
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}
   */
  @Test
  public void testFindAttachmentsByTaskId_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(attachmentDataManager.findAttachmentsByTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<AttachmentEntity> actualFindAttachmentsByTaskIdResult = attachmentEntityManagerImpl
        .findAttachmentsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(attachmentDataManager).findAttachmentsByTaskId(eq("42"));
    assertTrue(actualFindAttachmentsByTaskIdResult.isEmpty());
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#deleteAttachmentsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link AttachmentEntityManagerImpl#deleteAttachmentsByTaskId(String)}
   */
  @Test
  public void testDeleteAttachmentsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> attachmentEntityManagerImpl.deleteAttachmentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#deleteAttachmentsByTaskId(String)}.
   * <p>
   * Method under test:
   * {@link AttachmentEntityManagerImpl#deleteAttachmentsByTaskId(String)}
   */
  @Test
  public void testDeleteAttachmentsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(processEngineConfigurationImpl, HistoryLevel.ACTIVITY));
    when(attachmentDataManager.findAttachmentsByTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    attachmentEntityManagerImpl.deleteAttachmentsByTaskId("42");

    // Assert
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(processEngineConfigurationImpl, atLeast(1)).getHistoryManager();
    verify(attachmentDataManager).findAttachmentsByTaskId(eq("42"));
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#checkHistoryEnabled()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AttachmentEntityManagerImpl#checkHistoryEnabled()}
   */
  @Test
  public void testCheckHistoryEnabled_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()))).checkHistoryEnabled());
  }
}
