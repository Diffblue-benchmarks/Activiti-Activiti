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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.DefaultHistoryManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.AttachmentEntity;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.ByteArrayDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DeleteAttachmentCmdDiffblueTest {
  /**
   * Test {@link DeleteAttachmentCmd#DeleteAttachmentCmd(String)}.
   * <p>
   * Method under test: {@link DeleteAttachmentCmd#DeleteAttachmentCmd(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeleteAttachmentCmd.<init>(String)"})
  public void testNewDeleteAttachmentCmd() {
    // Arrange, Act and Assert
    assertEquals("42", (new DeleteAttachmentCmd("42")).attachmentId);
  }

  /**
   * Test {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}.
   * <p>
   * Method under test: {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeleteAttachmentCmd.executeInternal(CommandContext, AttachmentEntity, String, String)"})
  public void testExecuteInternal() {
    // Arrange
    DeleteAttachmentCmd deleteAttachmentCmd = new DeleteAttachmentCmd("42");
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), attachmentDataManager);

    ProcessEngineConfigurationImpl processEngineConfigurationImpl = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationImpl);
    when(commandContext.getAttachmentEntityManager()).thenReturn(attachmentEntityManagerImpl);

    // Act
    deleteAttachmentCmd.executeInternal(commandContext, new AttachmentEntityImpl(), "42", "42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(commandContext).getAttachmentEntityManager();
    verify(commandContext, atLeast(1)).getProcessEngineConfiguration();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeleteAttachmentCmd.executeInternal(CommandContext, AttachmentEntity, String, String)"})
  public void testExecuteInternal_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    DeleteAttachmentCmd deleteAttachmentCmd = new DeleteAttachmentCmd("42");
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), attachmentDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationImpl);
    when(commandContext.getAttachmentEntityManager()).thenReturn(attachmentEntityManagerImpl);

    // Act
    deleteAttachmentCmd.executeInternal(commandContext, new AttachmentEntityImpl(), "42", "42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(commandContext).getAttachmentEntityManager();
    verify(commandContext).getProcessEngineConfiguration();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}.
   * <ul>
   *   <li>Given {@link DeleteAttachmentCmd#DeleteAttachmentCmd(String)} with {@code Attachment Id}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeleteAttachmentCmd.executeInternal(CommandContext, AttachmentEntity, String, String)"})
  public void testExecuteInternal_givenDeleteAttachmentCmdWithAttachmentId() {
    // Arrange
    DeleteAttachmentCmd deleteAttachmentCmd = new DeleteAttachmentCmd("Attachment Id");
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), attachmentDataManager);

    ProcessEngineConfigurationImpl processEngineConfigurationImpl = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationImpl);
    when(commandContext.getAttachmentEntityManager()).thenReturn(attachmentEntityManagerImpl);

    // Act
    deleteAttachmentCmd.executeInternal(commandContext, new AttachmentEntityImpl(), "42", "42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(commandContext).getAttachmentEntityManager();
    verify(commandContext, atLeast(1)).getProcessEngineConfiguration();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}.
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeleteAttachmentCmd.executeInternal(CommandContext, AttachmentEntity, String, String)"})
  public void testExecuteInternal_thenCallsDispatchEvent() {
    // Arrange
    DeleteAttachmentCmd deleteAttachmentCmd = new DeleteAttachmentCmd("42");
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), attachmentDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationImpl);
    when(commandContext.getAttachmentEntityManager()).thenReturn(attachmentEntityManagerImpl);

    // Act
    deleteAttachmentCmd.executeInternal(commandContext, new AttachmentEntityImpl(), "42", "42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(commandContext).getAttachmentEntityManager();
    verify(commandContext, atLeast(1)).getProcessEngineConfiguration();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}.
   * <ul>
   *   <li>Then calls {@link CommandContext#getByteArrayEntityManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeleteAttachmentCmd#executeInternal(CommandContext, AttachmentEntity, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DeleteAttachmentCmd.executeInternal(CommandContext, AttachmentEntity, String, String)"})
  public void testExecuteInternal_thenCallsGetByteArrayEntityManager() {
    // Arrange
    DeleteAttachmentCmd deleteAttachmentCmd = new DeleteAttachmentCmd("42");
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        new JtaProcessEngineConfiguration(), attachmentDataManager);

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    ByteArrayDataManager byteArrayDataManager = mock(ByteArrayDataManager.class);
    doNothing().when(byteArrayDataManager).deleteByteArrayNoRevisionCheck(Mockito.<String>any());
    ByteArrayEntityManagerImpl byteArrayEntityManagerImpl = new ByteArrayEntityManagerImpl(
        new JtaProcessEngineConfiguration(), byteArrayDataManager);

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getHistoryManager())
        .thenReturn(new DefaultHistoryManager(new JtaProcessEngineConfiguration(), HistoryLevel.NONE));
    when(commandContext.getByteArrayEntityManager()).thenReturn(byteArrayEntityManagerImpl);
    when(commandContext.getProcessEngineConfiguration()).thenReturn(processEngineConfigurationImpl);
    when(commandContext.getAttachmentEntityManager()).thenReturn(attachmentEntityManagerImpl);
    AttachmentEntityImpl attachment = mock(AttachmentEntityImpl.class);
    when(attachment.getName()).thenReturn("Name");
    when(attachment.getProcessInstanceId()).thenReturn("42");
    when(attachment.getContentId()).thenReturn("42");
    when(attachment.getTaskId()).thenReturn("42");

    // Act
    deleteAttachmentCmd.executeInternal(commandContext, attachment, "42", "42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(commandContext).getAttachmentEntityManager();
    verify(commandContext).getByteArrayEntityManager();
    verify(commandContext).getHistoryManager();
    verify(commandContext, atLeast(1)).getProcessEngineConfiguration();
    verify(attachment, atLeast(1)).getContentId();
    verify(attachment).getName();
    verify(attachment).getProcessInstanceId();
    verify(attachment, atLeast(1)).getTaskId();
    verify(byteArrayDataManager).deleteByteArrayNoRevisionCheck(eq("42"));
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }
}
