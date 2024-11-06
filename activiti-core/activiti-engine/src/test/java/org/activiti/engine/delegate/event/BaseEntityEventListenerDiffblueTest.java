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
package org.activiti.engine.delegate.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityExceptionEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.api.event.TestBaseEntityEventListener;
import org.junit.Test;

public class BaseEntityEventListenerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BaseEntityEventListener#BaseEntityEventListener()}
   *   <li>{@link BaseEntityEventListener#onCreate(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onDelete(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onEntityEvent(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onInitialized(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onUpdate(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#isFailOnException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BaseEntityEventListener actualBaseEntityEventListener = new BaseEntityEventListener();
    actualBaseEntityEventListener.onCreate(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onDelete(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onEntityEvent(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onInitialized(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onUpdate(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    assertTrue(actualBaseEntityEventListener.isFailOnException());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BaseEntityEventListener#BaseEntityEventListener(boolean, Class)}
   *   <li>{@link BaseEntityEventListener#onCreate(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onDelete(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onEntityEvent(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onInitialized(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onUpdate(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#isFailOnException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenJavaLangObject() {
    // Arrange
    Class<Object> entityClass = Object.class;

    // Act
    BaseEntityEventListener actualBaseEntityEventListener = new BaseEntityEventListener(true, entityClass);
    actualBaseEntityEventListener.onCreate(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onDelete(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onEntityEvent(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onInitialized(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onUpdate(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    assertTrue(actualBaseEntityEventListener.isFailOnException());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link BaseEntityEventListener#BaseEntityEventListener(boolean)}
   *   <li>{@link BaseEntityEventListener#onCreate(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onDelete(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onEntityEvent(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onInitialized(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#onUpdate(ActivitiEvent)}
   *   <li>{@link BaseEntityEventListener#isFailOnException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenTrue() {
    // Arrange and Act
    BaseEntityEventListener actualBaseEntityEventListener = new BaseEntityEventListener(true);
    actualBaseEntityEventListener.onCreate(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onDelete(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onEntityEvent(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onInitialized(new ActivitiActivityCancelledEventImpl());
    actualBaseEntityEventListener.onUpdate(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    assertTrue(actualBaseEntityEventListener.isFailOnException());
  }

  /**
   * Test {@link BaseEntityEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then {@link TestBaseEntityEventListener#TestBaseEntityEventListener()}
   * CreateReceived.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseEntityEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_thenTestBaseEntityEventListenerCreateReceived() {
    // Arrange
    TestBaseEntityEventListener testBaseEntityEventListener = new TestBaseEntityEventListener();

    // Act
    testBaseEntityEventListener.onEvent(
        new ActivitiEntityExceptionEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED, new Throwable()));

    // Assert
    assertFalse(testBaseEntityEventListener.isCustomReceived());
    assertFalse(testBaseEntityEventListener.isDeleteReceived());
    assertFalse(testBaseEntityEventListener.isInitializeReceived());
    assertFalse(testBaseEntityEventListener.isUpdateReceived());
    assertTrue(testBaseEntityEventListener.isCreateReceived());
  }

  /**
   * Test {@link BaseEntityEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then {@link TestBaseEntityEventListener#TestBaseEntityEventListener()}
   * CustomReceived.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseEntityEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_thenTestBaseEntityEventListenerCustomReceived() {
    // Arrange
    TestBaseEntityEventListener testBaseEntityEventListener = new TestBaseEntityEventListener();

    // Act
    testBaseEntityEventListener
        .onEvent(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    assertFalse(testBaseEntityEventListener.isCreateReceived());
    assertFalse(testBaseEntityEventListener.isDeleteReceived());
    assertFalse(testBaseEntityEventListener.isInitializeReceived());
    assertFalse(testBaseEntityEventListener.isUpdateReceived());
    assertTrue(testBaseEntityEventListener.isCustomReceived());
  }

  /**
   * Test {@link BaseEntityEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then {@link TestBaseEntityEventListener#TestBaseEntityEventListener()}
   * DeleteReceived.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseEntityEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_thenTestBaseEntityEventListenerDeleteReceived() {
    // Arrange
    TestBaseEntityEventListener testBaseEntityEventListener = new TestBaseEntityEventListener();

    // Act
    testBaseEntityEventListener.onEvent(
        new ActivitiEntityExceptionEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_DELETED, new Throwable()));

    // Assert
    assertFalse(testBaseEntityEventListener.isCreateReceived());
    assertFalse(testBaseEntityEventListener.isCustomReceived());
    assertFalse(testBaseEntityEventListener.isInitializeReceived());
    assertFalse(testBaseEntityEventListener.isUpdateReceived());
    assertTrue(testBaseEntityEventListener.isDeleteReceived());
  }

  /**
   * Test {@link BaseEntityEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then {@link TestBaseEntityEventListener#TestBaseEntityEventListener()}
   * InitializeReceived.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseEntityEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_thenTestBaseEntityEventListenerInitializeReceived() {
    // Arrange
    TestBaseEntityEventListener testBaseEntityEventListener = new TestBaseEntityEventListener();

    // Act
    testBaseEntityEventListener.onEvent(
        new ActivitiEntityExceptionEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_INITIALIZED, new Throwable()));

    // Assert
    assertFalse(testBaseEntityEventListener.isCreateReceived());
    assertFalse(testBaseEntityEventListener.isCustomReceived());
    assertFalse(testBaseEntityEventListener.isDeleteReceived());
    assertFalse(testBaseEntityEventListener.isUpdateReceived());
    assertTrue(testBaseEntityEventListener.isInitializeReceived());
  }

  /**
   * Test {@link BaseEntityEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then {@link TestBaseEntityEventListener#TestBaseEntityEventListener()}
   * UpdateReceived.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseEntityEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_thenTestBaseEntityEventListenerUpdateReceived() {
    // Arrange
    TestBaseEntityEventListener testBaseEntityEventListener = new TestBaseEntityEventListener();

    // Act
    testBaseEntityEventListener.onEvent(
        new ActivitiEntityExceptionEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_UPDATED, new Throwable()));

    // Assert
    assertFalse(testBaseEntityEventListener.isCreateReceived());
    assertFalse(testBaseEntityEventListener.isCustomReceived());
    assertFalse(testBaseEntityEventListener.isDeleteReceived());
    assertFalse(testBaseEntityEventListener.isInitializeReceived());
    assertTrue(testBaseEntityEventListener.isUpdateReceived());
  }

  /**
   * Test {@link BaseEntityEventListener#isValidEvent(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link BaseEntityEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  public void testIsValidEvent() {
    // Arrange
    BaseEntityEventListener baseEntityEventListener = new BaseEntityEventListener(true);

    // Act and Assert
    assertTrue(baseEntityEventListener
        .isValidEvent(new ActivitiEntityEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Test {@link BaseEntityEventListener#isValidEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseEntityEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  public void testIsValidEvent_givenJavaLangObject() {
    // Arrange
    Class<Object> entityClass = Object.class;
    BaseEntityEventListener baseEntityEventListener = new BaseEntityEventListener(true, entityClass);

    // Act and Assert
    assertTrue(baseEntityEventListener
        .isValidEvent(new ActivitiEntityEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Test {@link BaseEntityEventListener#isValidEvent(ActivitiEvent)}.
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default
   * constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseEntityEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  public void testIsValidEvent_whenActivitiActivityCancelledEventImpl_thenReturnFalse() {
    // Arrange
    BaseEntityEventListener baseEntityEventListener = new BaseEntityEventListener(true);

    // Act and Assert
    assertFalse(baseEntityEventListener.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test {@link BaseEntityEventListener#isValidEvent(ActivitiEvent)}.
   * <ul>
   *   <li>When
   * {@link ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}
   * with {@link ProcessInstance}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseEntityEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  public void testIsValidEvent_whenActivitiProcessCancelledEventImplWithProcessInstance() {
    // Arrange
    BaseEntityEventListener baseEntityEventListener = new BaseEntityEventListener(true);

    // Act and Assert
    assertTrue(
        baseEntityEventListener.isValidEvent(new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class))));
  }
}
