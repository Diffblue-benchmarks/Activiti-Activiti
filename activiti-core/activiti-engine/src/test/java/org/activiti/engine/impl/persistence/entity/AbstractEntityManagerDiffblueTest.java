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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Map;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.CountingExecutionEntity;
import org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractEntityManagerDiffblueTest {
  @Mock
  private AttachmentDataManager attachmentDataManager;

  @InjectMocks
  private AttachmentEntityManagerImpl attachmentEntityManagerImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Method under test: {@link AbstractEntityManager#findById(String)}
   */
  @Test
  public void testFindById() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.findById(Mockito.<String>any())).thenReturn(attachmentEntityImpl);

    // Act
    AttachmentEntity actualFindByIdResult = attachmentEntityManagerImpl.findById("42");

    // Assert
    verify(attachmentDataManager).findById(eq("42"));
    assertSame(attachmentEntityImpl, actualFindByIdResult);
  }

  /**
   * Method under test: {@link AbstractEntityManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    AttachmentEntity actualCreateResult = (new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()))).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof AttachmentEntityImpl);
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("description"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getContentId());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUrl());
    assertNull(actualCreateResult.getTime());
    assertNull(actualCreateResult.getContent());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntityManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    AttachmentEntity actualCreateResult = (new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()))).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof AttachmentEntityImpl);
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("description"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getContentId());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUrl());
    assertNull(actualCreateResult.getTime());
    assertNull(actualCreateResult.getContent());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }

  /**
   * Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  public void testInsert() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl());

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  public void testInsert2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl());

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  public void testInsert3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  public void testInsert4() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl(), true);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  public void testInsert5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  public void testInsert6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  public void testInsert7() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(mock(ActivitiEventDispatcher.class));
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl(), false);

    // Assert
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  public void testUpdate() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any())).thenReturn(attachmentEntityImpl);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult = attachmentEntityManagerImpl.update(new AttachmentEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  public void testUpdate2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any())).thenReturn(attachmentEntityImpl);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult = attachmentEntityManagerImpl.update(new AttachmentEntityImpl());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  public void testUpdate3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any())).thenReturn(attachmentEntityImpl);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult = attachmentEntityManagerImpl.update(new AttachmentEntityImpl());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  public void testUpdate4() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any())).thenReturn(attachmentEntityImpl);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult = attachmentEntityManagerImpl.update(new AttachmentEntityImpl(), true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  public void testUpdate5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any())).thenReturn(attachmentEntityImpl);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult = attachmentEntityManagerImpl.update(new AttachmentEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  public void testUpdate6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any())).thenReturn(attachmentEntityImpl);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult = attachmentEntityManagerImpl.update(new AttachmentEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  public void testUpdate7() {
    // Arrange
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any())).thenReturn(attachmentEntityImpl);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        mock(ProcessEngineConfigurationImpl.class), attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult = attachmentEntityManagerImpl.update(new AttachmentEntityImpl(), false);

    // Assert
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  public void testDelete() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl());

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  public void testDelete2() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl());

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  public void testDelete3() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl());

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  public void testDelete4() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl(), true);

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  public void testDelete5() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  public void testDelete6() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl(), true);

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfiguration).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  public void testDelete7() {
    // Arrange
    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        mock(ProcessEngineConfigurationImpl.class), attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl(), false);

    // Assert
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  public void testDelete8() {
    // Arrange
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(attachmentDataManager.findById(Mockito.<String>any())).thenReturn(new AttachmentEntityImpl());
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    // Act
    attachmentEntityManagerImpl.delete("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
    verify(attachmentDataManager).findById(eq("42"));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  public void testDelete9() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(attachmentDataManager.findById(Mockito.<String>any())).thenReturn(new AttachmentEntityImpl());
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    // Act
    attachmentEntityManagerImpl.delete("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
    verify(attachmentDataManager).findById(eq("42"));
  }

  /**
   * Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  public void testDelete10() {
    // Arrange
    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);
    when(attachmentDataManager.findById(Mockito.<String>any())).thenReturn(new AttachmentEntityImpl());
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    // Act
    attachmentEntityManagerImpl.delete("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
    verify(attachmentDataManager).findById(eq("42"));
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledGlobally() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertFalse((new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())))
        .isExecutionRelatedEntityCountEnabledGlobally());
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledGlobally2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse((new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())))
        .isExecutionRelatedEntityCountEnabledGlobally());
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledGlobally3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue((new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())))
        .isExecutionRelatedEntityCountEnabledGlobally());
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabled() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
        (CountingExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabled2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
        (CountingExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabled3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
        (CountingExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabled4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
        (ExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabled5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
        (ExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabled6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
        (ExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabled7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertFalse((new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())))
        .isExecutionRelatedEntityCountEnabled((ExecutionEntity) null));
  }
}
