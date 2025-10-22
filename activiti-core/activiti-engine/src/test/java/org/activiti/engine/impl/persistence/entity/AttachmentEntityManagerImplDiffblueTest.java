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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentEntityManagerImplDiffblueTest {
  @InjectMocks
  private AttachmentEntityManagerImpl attachmentEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock
  private AttachmentDataManager attachmentDataManager;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AttachmentEntityManagerImpl#AttachmentEntityManagerImpl(ProcessEngineConfigurationImpl, AttachmentDataManager)}
   *   <li>{@link AttachmentEntityManagerImpl#setAttachmentDataManager(AttachmentDataManager)}
   *   <li>{@link AttachmentEntityManagerImpl#getAttachmentDataManager()}
   *   <li>{@link AttachmentEntityManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AttachmentEntityManagerImpl.<init>(ProcessEngineConfigurationImpl, AttachmentDataManager)",
      "AttachmentDataManager AttachmentEntityManagerImpl.getAttachmentDataManager()",
      "org.activiti.engine.impl.persistence.entity.data.DataManager AttachmentEntityManagerImpl.getDataManager()",
      "void AttachmentEntityManagerImpl.setAttachmentDataManager(AttachmentDataManager)"})
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

    // Assert
    assertSame(attachmentDataManager, actualAttachmentDataManager);
    assertSame(attachmentDataManager, actualAttachmentEntityManagerImpl.getDataManager());
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AttachmentEntityManagerImpl.findAttachmentsByProcessInstanceId(String)"})
  public void testFindAttachmentsByProcessInstanceId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> attachmentEntityManagerImpl.findAttachmentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}.
   * <p>
   * Method under test: {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AttachmentEntityManagerImpl.findAttachmentsByProcessInstanceId(String)"})
  public void testFindAttachmentsByProcessInstanceId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
    when(attachmentDataManager.findAttachmentsByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> attachmentEntityManagerImpl.findAttachmentsByProcessInstanceId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
    verify(attachmentDataManager).findAttachmentsByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AttachmentEntityManagerImpl#findAttachmentsByProcessInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AttachmentEntityManagerImpl.findAttachmentsByProcessInstanceId(String)"})
  public void testFindAttachmentsByProcessInstanceId_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
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
   * Method under test: {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AttachmentEntityManagerImpl.findAttachmentsByTaskId(String)"})
  public void testFindAttachmentsByTaskId() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> attachmentEntityManagerImpl.findAttachmentsByTaskId("42"));
    verify(processEngineConfigurationImpl).getHistoryManager();
  }

  /**
   * Test {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}.
   * <p>
   * Method under test: {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AttachmentEntityManagerImpl.findAttachmentsByTaskId(String)"})
  public void testFindAttachmentsByTaskId2() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
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
   * Method under test: {@link AttachmentEntityManagerImpl#findAttachmentsByTaskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AttachmentEntityManagerImpl.findAttachmentsByTaskId(String)"})
  public void testFindAttachmentsByTaskId_thenReturnEmpty() {
    // Arrange
    when(processEngineConfigurationImpl.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.ACTIVITY));
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
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AttachmentEntityManagerImpl#deleteAttachmentsByTaskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AttachmentEntityManagerImpl.deleteAttachmentsByTaskId(String)"})
  public void testDeleteAttachmentsByTaskId_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()))).deleteAttachmentsByTaskId("42"));
    verify(processEngineConfiguration).getHistoryManager();
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AttachmentEntityManagerImpl.checkHistoryEnabled()"})
  public void testCheckHistoryEnabled_thenThrowActivitiException() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration
        .setHistoryManager(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()))).checkHistoryEnabled());
  }
}
