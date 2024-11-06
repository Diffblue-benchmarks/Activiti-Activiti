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
package org.activiti.engine.impl.persistence.entity.data.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.CompensateEventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.MessageEventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.SignalEventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.EventSubscriptionsByExecutionAndTypeMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.EventSubscriptionsByExecutionIdMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.EventSubscriptionsByNameMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.EventSubscriptionsByProcInstTypeAndActivityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.MessageEventSubscriptionsByProcInstAndEventNameMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.SignalEventSubscriptionByEventNameMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.SignalEventSubscriptionByNameAndExecutionMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.SignalEventSubscriptionByProcInstAndEventNameMatcher;
import org.junit.Test;

public class MybatisEventSubscriptionDataManagerDiffblueTest {
  /**
   * Test
   * {@link MybatisEventSubscriptionDataManager#MybatisEventSubscriptionDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisEventSubscriptionDataManager#MybatisEventSubscriptionDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisEventSubscriptionDataManager_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    MybatisEventSubscriptionDataManager actualMybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        processEngineConfiguration);

    // Assert
    assertTrue(
        actualMybatisEventSubscriptionDataManager.eventSubscriptionsByExecutionAndTypeMatcher instanceof EventSubscriptionsByExecutionAndTypeMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.eventSubscritionsByExecutionIdMatcher instanceof EventSubscriptionsByExecutionIdMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.eventSubscriptionsByNameMatcher instanceof EventSubscriptionsByNameMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.eventSubscriptionsByProcInstTypeAndActivityMatcher instanceof EventSubscriptionsByProcInstTypeAndActivityMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.messageEventSubscriptionsByProcInstAndEventNameMatcher instanceof MessageEventSubscriptionsByProcInstAndEventNameMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.signalEventSubscriptionByEventNameMatcher instanceof SignalEventSubscriptionByEventNameMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.signalEventSubscriptionByNameAndExecutionMatcher instanceof SignalEventSubscriptionByNameAndExecutionMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.signalEventSubscriptionByProcInstAndEventNameMatcher instanceof SignalEventSubscriptionByProcInstAndEventNameMatcher);
    List<Class<? extends EventSubscriptionEntity>> managedEntitySubClasses = actualMybatisEventSubscriptionDataManager
        .getManagedEntitySubClasses();
    assertEquals(3, managedEntitySubClasses.size());
    Class<CompensateEventSubscriptionEntityImpl> expectedGetResult = CompensateEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult, managedEntitySubClasses.get(2));
    Class<EventSubscriptionEntityImpl> expectedManagedEntityClass = EventSubscriptionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisEventSubscriptionDataManager.getManagedEntityClass());
    Class<MessageEventSubscriptionEntityImpl> expectedGetResult2 = MessageEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult2, managedEntitySubClasses.get(0));
    Class<SignalEventSubscriptionEntityImpl> expectedGetResult3 = SignalEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult3, managedEntitySubClasses.get(1));
  }

  /**
   * Test
   * {@link MybatisEventSubscriptionDataManager#MybatisEventSubscriptionDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisEventSubscriptionDataManager#MybatisEventSubscriptionDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisEventSubscriptionDataManager_whenJtaProcessEngineConfiguration() {
    // Arrange and Act
    MybatisEventSubscriptionDataManager actualMybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        actualMybatisEventSubscriptionDataManager.eventSubscriptionsByExecutionAndTypeMatcher instanceof EventSubscriptionsByExecutionAndTypeMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.eventSubscritionsByExecutionIdMatcher instanceof EventSubscriptionsByExecutionIdMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.eventSubscriptionsByNameMatcher instanceof EventSubscriptionsByNameMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.eventSubscriptionsByProcInstTypeAndActivityMatcher instanceof EventSubscriptionsByProcInstTypeAndActivityMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.messageEventSubscriptionsByProcInstAndEventNameMatcher instanceof MessageEventSubscriptionsByProcInstAndEventNameMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.signalEventSubscriptionByEventNameMatcher instanceof SignalEventSubscriptionByEventNameMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.signalEventSubscriptionByNameAndExecutionMatcher instanceof SignalEventSubscriptionByNameAndExecutionMatcher);
    assertTrue(
        actualMybatisEventSubscriptionDataManager.signalEventSubscriptionByProcInstAndEventNameMatcher instanceof SignalEventSubscriptionByProcInstAndEventNameMatcher);
    List<Class<? extends EventSubscriptionEntity>> managedEntitySubClasses = actualMybatisEventSubscriptionDataManager
        .getManagedEntitySubClasses();
    assertEquals(3, managedEntitySubClasses.size());
    Class<CompensateEventSubscriptionEntityImpl> expectedGetResult = CompensateEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult, managedEntitySubClasses.get(2));
    Class<EventSubscriptionEntityImpl> expectedManagedEntityClass = EventSubscriptionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisEventSubscriptionDataManager.getManagedEntityClass());
    Class<MessageEventSubscriptionEntityImpl> expectedGetResult2 = MessageEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult2, managedEntitySubClasses.get(0));
    Class<SignalEventSubscriptionEntityImpl> expectedGetResult3 = SignalEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult3, managedEntitySubClasses.get(1));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MybatisEventSubscriptionDataManager#getManagedEntityClass()}
   *   <li>{@link MybatisEventSubscriptionDataManager#getManagedEntitySubClasses()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    MybatisEventSubscriptionDataManager mybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());

    // Act
    Class<? extends EventSubscriptionEntity> actualManagedEntityClass = mybatisEventSubscriptionDataManager
        .getManagedEntityClass();
    List<Class<? extends EventSubscriptionEntity>> actualManagedEntitySubClasses = mybatisEventSubscriptionDataManager
        .getManagedEntitySubClasses();

    // Assert
    assertEquals(3, actualManagedEntitySubClasses.size());
    Class<CompensateEventSubscriptionEntityImpl> expectedGetResult = CompensateEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult, actualManagedEntitySubClasses.get(2));
    Class<EventSubscriptionEntityImpl> expectedManagedEntityClass = EventSubscriptionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
    Class<MessageEventSubscriptionEntityImpl> expectedGetResult2 = MessageEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult2, actualManagedEntitySubClasses.get(0));
    Class<SignalEventSubscriptionEntityImpl> expectedGetResult3 = SignalEventSubscriptionEntityImpl.class;
    assertEquals(expectedGetResult3, actualManagedEntitySubClasses.get(1));
  }

  /**
   * Test {@link MybatisEventSubscriptionDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisEventSubscriptionDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> (new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration())).create());
  }

  /**
   * Test
   * {@link MybatisEventSubscriptionDataManager#toSignalEventSubscriptionEntityList(List)}.
   * <p>
   * Method under test:
   * {@link MybatisEventSubscriptionDataManager#toSignalEventSubscriptionEntityList(List)}
   */
  @Test
  public void testToSignalEventSubscriptionEntityList() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisEventSubscriptionDataManager mybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        processEngineConfiguration);

    // Act and Assert
    assertTrue(mybatisEventSubscriptionDataManager.toSignalEventSubscriptionEntityList(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link MybatisEventSubscriptionDataManager#toSignalEventSubscriptionEntityList(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisEventSubscriptionDataManager#toSignalEventSubscriptionEntityList(List)}
   */
  @Test
  public void testToSignalEventSubscriptionEntityList_givenNull_thenReturnArrayList() {
    // Arrange
    MybatisEventSubscriptionDataManager mybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());

    ArrayList<EventSubscriptionEntity> result = new ArrayList<>();
    result.add(null);

    // Act and Assert
    assertEquals(result, mybatisEventSubscriptionDataManager.toSignalEventSubscriptionEntityList(result));
  }

  /**
   * Test
   * {@link MybatisEventSubscriptionDataManager#toSignalEventSubscriptionEntityList(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisEventSubscriptionDataManager#toSignalEventSubscriptionEntityList(List)}
   */
  @Test
  public void testToSignalEventSubscriptionEntityList_whenArrayList_thenReturnEmpty() {
    // Arrange
    MybatisEventSubscriptionDataManager mybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertTrue(mybatisEventSubscriptionDataManager.toSignalEventSubscriptionEntityList(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link MybatisEventSubscriptionDataManager#toMessageEventSubscriptionEntityList(List)}.
   * <p>
   * Method under test:
   * {@link MybatisEventSubscriptionDataManager#toMessageEventSubscriptionEntityList(List)}
   */
  @Test
  public void testToMessageEventSubscriptionEntityList() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    MybatisEventSubscriptionDataManager mybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        processEngineConfiguration);

    // Act and Assert
    assertTrue(mybatisEventSubscriptionDataManager.toMessageEventSubscriptionEntityList(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link MybatisEventSubscriptionDataManager#toMessageEventSubscriptionEntityList(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisEventSubscriptionDataManager#toMessageEventSubscriptionEntityList(List)}
   */
  @Test
  public void testToMessageEventSubscriptionEntityList_givenNull_thenReturnArrayList() {
    // Arrange
    MybatisEventSubscriptionDataManager mybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());

    ArrayList<EventSubscriptionEntity> result = new ArrayList<>();
    result.add(null);

    // Act and Assert
    assertEquals(result, mybatisEventSubscriptionDataManager.toMessageEventSubscriptionEntityList(result));
  }

  /**
   * Test
   * {@link MybatisEventSubscriptionDataManager#toMessageEventSubscriptionEntityList(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisEventSubscriptionDataManager#toMessageEventSubscriptionEntityList(List)}
   */
  @Test
  public void testToMessageEventSubscriptionEntityList_whenArrayList_thenReturnEmpty() {
    // Arrange
    MybatisEventSubscriptionDataManager mybatisEventSubscriptionDataManager = new MybatisEventSubscriptionDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertTrue(mybatisEventSubscriptionDataManager.toMessageEventSubscriptionEntityList(new ArrayList<>()).isEmpty());
  }
}
