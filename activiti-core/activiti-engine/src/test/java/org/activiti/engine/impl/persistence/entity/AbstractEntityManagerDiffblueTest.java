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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.CountingExecutionEntity;
import org.activiti.engine.impl.persistence.entity.data.AttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractEntityManagerDiffblueTest {
  @Mock private AttachmentDataManager attachmentDataManager;

  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test {@link AbstractEntityManager#findById(String)}.
   *
   * <p>Method under test: {@link AbstractEntityManager#findById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.findById(String)"})
  public void testFindById() {
    // Arrange
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.findById(Mockito.<String>any())).thenReturn(attachmentEntityImpl);

    // Act
    AttachmentEntity actualFindByIdResult =
        new AttachmentEntityManagerImpl(processEngineConfigurationImpl, attachmentDataManager)
            .findById("42");

    // Assert
    verify(attachmentDataManager).findById("42");
    assertSame(attachmentEntityImpl, actualFindByIdResult);
  }

  /**
   * Test {@link AbstractEntityManager#create()}.
   *
   * <ul>
   *   <li>Then PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#create()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.create()"})
  public void testCreate_thenPersistentStateReturnMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act
    AttachmentEntity actualCreateResult = attachmentEntityManagerImpl.create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof AttachmentEntityImpl);
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
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("name"));
  }

  /**
   * Test {@link AbstractEntityManager#insert(Entity)} with {@code entity}.
   *
   * <p>Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.insert(Entity)"})
  public void testInsertWithEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl());

    // Assert
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#insert(Entity, boolean)} with {@code entity}, {@code
   * fireCreateEvent}.
   *
   * <p>Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.insert(Entity, boolean)"})
  public void testInsertWithEntityFireCreateEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl(), true);

    // Assert
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#insert(Entity, boolean)} with {@code entity}, {@code
   * fireCreateEvent}.
   *
   * <p>Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.insert(Entity, boolean)"})
  public void testInsertWithEntityFireCreateEvent2() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl(), true);

    // Assert
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#insert(Entity, boolean)} with {@code entity}, {@code
   * fireCreateEvent}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.insert(Entity, boolean)"})
  public void testInsertWithEntityFireCreateEvent_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl(), true);

    // Assert
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#insert(Entity, boolean)} with {@code entity}, {@code
   * fireCreateEvent}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then calls {@link AttachmentDataManager#insert(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#insert(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.insert(Entity, boolean)"})
  public void testInsertWithEntityFireCreateEvent_whenFalse_thenCallsInsert() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(mock(ActivitiEventDispatcher.class));

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl(), false);

    // Assert
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#insert(Entity)} with {@code entity}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.insert(Entity)"})
  public void testInsertWithEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl());

    // Assert
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#insert(Entity)} with {@code entity}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#insert(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.insert(Entity)"})
  public void testInsertWithEntity_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).insert(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.insert(new AttachmentEntityImpl());

    // Assert
    verify(eventDispatcher, atLeast(1)).dispatchEvent(Mockito.<ActivitiEvent>any());
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).insert(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#update(Entity)} with {@code entity}.
   *
   * <p>Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.update(Entity)"})
  public void testUpdateWithEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any()))
        .thenReturn(attachmentEntityImpl);

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult =
        attachmentEntityManagerImpl.update(new AttachmentEntityImpl());

    // Assert
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Test {@link AbstractEntityManager#update(Entity, boolean)} with {@code entity}, {@code
   * fireUpdateEvent}.
   *
   * <p>Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.update(Entity, boolean)"})
  public void testUpdateWithEntityFireUpdateEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any()))
        .thenReturn(attachmentEntityImpl);

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult =
        attachmentEntityManagerImpl.update(new AttachmentEntityImpl(), true);

    // Assert
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Test {@link AbstractEntityManager#update(Entity, boolean)} with {@code entity}, {@code
   * fireUpdateEvent}.
   *
   * <p>Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.update(Entity, boolean)"})
  public void testUpdateWithEntityFireUpdateEvent2() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any()))
        .thenReturn(attachmentEntityImpl);

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult =
        attachmentEntityManagerImpl.update(new AttachmentEntityImpl(), true);

    // Assert
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Test {@link AbstractEntityManager#update(Entity, boolean)} with {@code entity}, {@code
   * fireUpdateEvent}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.update(Entity, boolean)"})
  public void testUpdateWithEntityFireUpdateEvent_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any()))
        .thenReturn(attachmentEntityImpl);

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult =
        attachmentEntityManagerImpl.update(new AttachmentEntityImpl(), true);

    // Assert
    verify(eventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Test {@link AbstractEntityManager#update(Entity, boolean)} with {@code entity}, {@code
   * fireUpdateEvent}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then return {@link AttachmentEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#update(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.update(Entity, boolean)"})
  public void testUpdateWithEntityFireUpdateEvent_whenFalse_thenReturnAttachmentEntityImpl() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(mock(ActivitiEventDispatcher.class));

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any()))
        .thenReturn(attachmentEntityImpl);

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult =
        attachmentEntityManagerImpl.update(new AttachmentEntityImpl(), false);

    // Assert
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Test {@link AbstractEntityManager#update(Entity)} with {@code entity}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.update(Entity)"})
  public void testUpdateWithEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any()))
        .thenReturn(attachmentEntityImpl);

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult =
        attachmentEntityManagerImpl.update(new AttachmentEntityImpl());

    // Assert
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Test {@link AbstractEntityManager#update(Entity)} with {@code entity}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#update(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Entity AbstractEntityManager.update(Entity)"})
  public void testUpdateWithEntity_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    AttachmentEntityImpl attachmentEntityImpl = new AttachmentEntityImpl();
    when(attachmentDataManager.update(Mockito.<AttachmentEntity>any()))
        .thenReturn(attachmentEntityImpl);

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    AttachmentEntity actualUpdateResult =
        attachmentEntityManagerImpl.update(new AttachmentEntityImpl());

    // Assert
    verify(eventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).update(isA(AttachmentEntity.class));
    assertSame(attachmentEntityImpl, actualUpdateResult);
  }

  /**
   * Test {@link AbstractEntityManager#delete(Entity)} with {@code entity}.
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(Entity)"})
  public void testDeleteWithEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl());

    // Assert
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#delete(Entity, boolean)} with {@code entity}, {@code
   * fireDeleteEvent}.
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(Entity, boolean)"})
  public void testDeleteWithEntityFireDeleteEvent() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl(), true);

    // Assert
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#delete(Entity, boolean)} with {@code entity}, {@code
   * fireDeleteEvent}.
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(Entity, boolean)"})
  public void testDeleteWithEntityFireDeleteEvent2() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl(), true);

    // Assert
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#delete(Entity, boolean)} with {@code entity}, {@code
   * fireDeleteEvent}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(Entity, boolean)"})
  public void testDeleteWithEntityFireDeleteEvent_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl(), true);

    // Assert
    verify(eventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#delete(Entity, boolean)} with {@code entity}, {@code
   * fireDeleteEvent}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then calls {@link AttachmentDataManager#delete(Entity)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(Entity, boolean)"})
  public void testDeleteWithEntityFireDeleteEvent_whenFalse_thenCallsDelete() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(mock(ActivitiEventDispatcher.class));

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl(), false);

    // Assert
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#delete(Entity)} with {@code entity}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(Entity)"})
  public void testDeleteWithEntity_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    when(eventDispatcher.isEnabled()).thenReturn(false);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl());

    // Assert
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#delete(Entity)} with {@code entity}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(Entity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(Entity)"})
  public void testDeleteWithEntity_thenCallsDispatchEvent() {
    // Arrange
    ActivitiEventDispatcher eventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(eventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(eventDispatcher.isEnabled()).thenReturn(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(eventDispatcher);

    AttachmentDataManager attachmentDataManager = mock(AttachmentDataManager.class);
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(processEngineConfiguration, attachmentDataManager);

    // Act
    attachmentEntityManagerImpl.delete(new AttachmentEntityImpl());

    // Assert
    verify(eventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(eventDispatcher).isEnabled();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
  }

  /**
   * Test {@link AbstractEntityManager#delete(String)} with {@code id}.
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(String)"})
  public void testDeleteWithId() {
    // Arrange
    when(attachmentDataManager.findById(Mockito.<String>any()))
        .thenReturn(new AttachmentEntityImpl());
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());
    when(processEngineConfigurationImpl.getEventDispatcher())
        .thenReturn(new ActivitiEventDispatcherImpl());

    // Act
    new AttachmentEntityManagerImpl(processEngineConfigurationImpl, attachmentDataManager)
        .delete("42");

    // Assert
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
    verify(attachmentDataManager).findById("42");
  }

  /**
   * Test {@link AbstractEntityManager#delete(String)} with {@code id}.
   *
   * <ul>
   *   <li>Given {@link ActivitiEventDispatcher} {@link ActivitiEventDispatcher#isEnabled()} return
   *       {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(String)"})
  public void testDeleteWithId_givenActivitiEventDispatcherIsEnabledReturnFalse() {
    // Arrange
    when(attachmentDataManager.findById(Mockito.<String>any()))
        .thenReturn(new AttachmentEntityImpl());
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    when(activitiEventDispatcher.isEnabled()).thenReturn(false);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    // Act
    new AttachmentEntityManagerImpl(processEngineConfigurationImpl, attachmentDataManager)
        .delete("42");

    // Assert
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
    verify(attachmentDataManager).findById("42");
  }

  /**
   * Test {@link AbstractEntityManager#delete(String)} with {@code id}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiEventDispatcher#dispatchEvent(ActivitiEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractEntityManager#delete(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractEntityManager.delete(String)"})
  public void testDeleteWithId_thenCallsDispatchEvent() {
    // Arrange
    when(attachmentDataManager.findById(Mockito.<String>any()))
        .thenReturn(new AttachmentEntityImpl());
    doNothing().when(attachmentDataManager).delete(Mockito.<AttachmentEntity>any());

    ActivitiEventDispatcher activitiEventDispatcher = mock(ActivitiEventDispatcher.class);
    doNothing().when(activitiEventDispatcher).dispatchEvent(Mockito.<ActivitiEvent>any());
    when(activitiEventDispatcher.isEnabled()).thenReturn(true);
    when(processEngineConfigurationImpl.getEventDispatcher()).thenReturn(activitiEventDispatcher);

    // Act
    new AttachmentEntityManagerImpl(processEngineConfigurationImpl, attachmentDataManager)
        .delete("42");

    // Assert
    verify(activitiEventDispatcher).dispatchEvent(isA(ActivitiEvent.class));
    verify(activitiEventDispatcher).isEnabled();
    verify(processEngineConfigurationImpl, atLeast(1)).getEventDispatcher();
    verify(attachmentDataManager).delete(isA(AttachmentEntity.class));
    verify(attachmentDataManager).findById("42");
  }

  /**
   * Test {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabledGlobally()"
  })
  public void testIsExecutionRelatedEntityCountEnabledGlobally_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabledGlobally());
  }

  /**
   * Test {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabledGlobally()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabledGlobally()"
  })
  public void testIsExecutionRelatedEntityCountEnabledGlobally_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertTrue(attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabledGlobally());
  }

  /**
   * Test {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)} with
   * {@code CountingExecutionEntity}.
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)"
  })
  public void testIsExecutionRelatedEntityCountEnabledWithCountingExecutionEntity() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(
        attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
            (CountingExecutionEntity)
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)} with
   * {@code CountingExecutionEntity}.
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)"
  })
  public void testIsExecutionRelatedEntityCountEnabledWithCountingExecutionEntity2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(
        attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
            (CountingExecutionEntity)
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)} with
   * {@code CountingExecutionEntity}.
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabled(CountingExecutionEntity)"
  })
  public void testIsExecutionRelatedEntityCountEnabledWithCountingExecutionEntity3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    CountingExecutionEntity executionEntity = mock(CountingExecutionEntity.class);
    when(executionEntity.isCountEnabled()).thenReturn(true);

    // Act
    boolean actualIsExecutionRelatedEntityCountEnabledResult =
        attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(executionEntity);

    // Assert
    verify(executionEntity).isCountEnabled();
    assertTrue(actualIsExecutionRelatedEntityCountEnabledResult);
  }

  /**
   * Test {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)} with
   * {@code ExecutionEntity}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabled(ExecutionEntity)"
  })
  public void testIsExecutionRelatedEntityCountEnabledWithExecutionEntity_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(
        attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
            (ExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)} with
   * {@code ExecutionEntity}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabled(ExecutionEntity)"
  })
  public void testIsExecutionRelatedEntityCountEnabledWithExecutionEntity_thenReturnFalse2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(
        attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
            (ExecutionEntity) ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)} with
   * {@code ExecutionEntity}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabled(ExecutionEntity)"
  })
  public void testIsExecutionRelatedEntityCountEnabledWithExecutionEntity_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    ExecutionEntityImpl executionEntity =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.setCountEnabled(true);

    // Act and Assert
    assertTrue(
        attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled(
            (ExecutionEntity) executionEntity));
  }

  /**
   * Test {@link AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)} with
   * {@code ExecutionEntity}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractEntityManager#isExecutionRelatedEntityCountEnabled(ExecutionEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractEntityManager.isExecutionRelatedEntityCountEnabled(ExecutionEntity)"
  })
  public void testIsExecutionRelatedEntityCountEnabledWithExecutionEntity_whenNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);
    AttachmentEntityManagerImpl attachmentEntityManagerImpl =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    // Act and Assert
    assertFalse(
        attachmentEntityManagerImpl.isExecutionRelatedEntityCountEnabled((ExecutionEntity) null));
  }
}
