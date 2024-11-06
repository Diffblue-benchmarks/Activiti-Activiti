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
import org.activiti.engine.impl.persistence.entity.data.DataManager;
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
   * Test {@link AbstractEntityManager#findById(String)}.
   * <p>
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
   * Test {@link AbstractEntityManager#create()}.
   * <p>
   * Method under test: {@link AbstractEntityManager#create()}
   */
  @Test
  public void testCreate() {
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
   * Test {@link AbstractEntityManager#create()}.
   * <ul>
   *   <li>Then PersistentState return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#create()}
   */
  @Test
  public void testCreate_thenPersistentStateReturnMap() {
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
   * Test {@link AbstractEntityManager#insert(Entity)} with {@code entity}.
   * <p>
   * Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  public void testInsertWithEntity() {
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
   * Test {@link AbstractEntityManager#insert(Entity, boolean)} with
   * {@code entity}, {@code fireCreateEvent}.
   * <p>
   * Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  public void testInsertWithEntityFireCreateEvent() {
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
   * Test {@link AbstractEntityManager#insert(Entity, boolean)} with
   * {@code entity}, {@code fireCreateEvent}.
   * <p>
   * Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  public void testInsertWithEntityFireCreateEvent2() {
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
   * Test {@link AbstractEntityManager#insert(Entity, boolean)} with
   * {@code entity}, {@code fireCreateEvent}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  public void testInsertWithEntityFireCreateEvent_thenCallsDispatchEvent() {
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
   * Test {@link AbstractEntityManager#insert(Entity, boolean)} with
   * {@code entity}, {@code fireCreateEvent}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getEventDispatcher()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  public void testInsertWithEntityFireCreateEvent_whenFalse_thenCallsGetEventDispatcher() {
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
   * Test {@link AbstractEntityManager#insert(Entity)} with {@code entity}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  public void testInsertWithEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
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
   * Test {@link AbstractEntityManager#insert(Entity)} with {@code entity}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  public void testInsertWithEntity_thenCallsDispatchEvent() {
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
   * Test {@link AbstractEntityManager#update(Entity)} with {@code entity}.
   * <p>
   * Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  public void testUpdateWithEntity() {
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
   * Test {@link AbstractEntityManager#update(Entity, boolean)} with
   * {@code entity}, {@code fireUpdateEvent}.
   * <p>
   * Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  public void testUpdateWithEntityFireUpdateEvent() {
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
   * Test {@link AbstractEntityManager#update(Entity, boolean)} with
   * {@code entity}, {@code fireUpdateEvent}.
   * <p>
   * Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  public void testUpdateWithEntityFireUpdateEvent2() {
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
   * Test {@link AbstractEntityManager#update(Entity, boolean)} with
   * {@code entity}, {@code fireUpdateEvent}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  public void testUpdateWithEntityFireUpdateEvent_thenCallsDispatchEvent() {
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
   * Test {@link AbstractEntityManager#update(Entity, boolean)} with
   * {@code entity}, {@code fireUpdateEvent}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@link AttachmentEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  public void testUpdateWithEntityFireUpdateEvent_whenFalse_thenReturnAttachmentEntityImpl() {
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
   * Test {@link AbstractEntityManager#update(Entity)} with {@code entity}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  public void testUpdateWithEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
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
   * Test {@link AbstractEntityManager#update(Entity)} with {@code entity}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  public void testUpdateWithEntity_thenCallsDispatchEvent() {
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
   * Test {@link AbstractEntityManager#delete(Entity)} with {@code entity}.
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  public void testDeleteWithEntity() {
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
   * Test {@link AbstractEntityManager#delete(Entity, boolean)} with
   * {@code entity}, {@code fireDeleteEvent}.
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  public void testDeleteWithEntityFireDeleteEvent() {
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
   * Test {@link AbstractEntityManager#delete(Entity, boolean)} with
   * {@code entity}, {@code fireDeleteEvent}.
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  public void testDeleteWithEntityFireDeleteEvent2() {
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
   * Test {@link AbstractEntityManager#delete(Entity, boolean)} with
   * {@code entity}, {@code fireDeleteEvent}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  public void testDeleteWithEntityFireDeleteEvent_thenCallsDispatchEvent() {
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
   * Test {@link AbstractEntityManager#delete(Entity, boolean)} with
   * {@code entity}, {@code fireDeleteEvent}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then calls {@link DataManager#delete(Entity)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  public void testDeleteWithEntityFireDeleteEvent_whenFalse_thenCallsDelete() {
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
   * Test {@link AbstractEntityManager#delete(Entity)} with {@code entity}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  public void testDeleteWithEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
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
   * Test {@link AbstractEntityManager#delete(Entity)} with {@code entity}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  public void testDeleteWithEntity_thenCallsDispatchEvent() {
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
   * Test {@link AbstractEntityManager#delete(String)} with {@code id}.
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  public void testDeleteWithId() {
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
   * Test {@link AbstractEntityManager#delete(String)} with {@code id}.
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher}
   * {@link ActivitiEventDispatcher#isEnabled()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  public void testDeleteWithId_givenActivitiEventDispatcherIsEnabledReturnFalse() {
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
   * Test {@link AbstractEntityManager#delete(String)} with {@code id}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  public void testDeleteWithId_thenCallsDispatchEvent() {
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
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledGlobally_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertFalse((new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())))
        .isExecutionRelatedEntityCountEnabledGlobally());
  }

  /**
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledGlobally_thenReturnFalse2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse((new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())))
        .isExecutionRelatedEntityCountEnabledGlobally());
  }

  /**
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledGlobally_thenReturnTrue() {
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
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   * with {@code CountingExecutionEntity}.
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledWithCountingExecutionEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
        (CountingExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   * with {@code CountingExecutionEntity}.
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledWithCountingExecutionEntity2() {
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
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   * with {@code CountingExecutionEntity}.
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledWithCountingExecutionEntity3() {
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
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledWithExecutionEntity() {
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
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledWithExecutionEntity2() {
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
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledWithExecutionEntity_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManagerImpl = new AttachmentEntityManagerImpl(
        processEngineConfiguration, new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
        (ExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   * with {@code ExecutionEntity}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  public void testIsExecutionRelatedEntityCountEnabledWithExecutionEntity_whenNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertFalse((new AttachmentEntityManagerImpl(processEngineConfiguration,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())))
        .isExecutionRelatedEntityCountEnabled((ExecutionEntity) null));
  }
}
