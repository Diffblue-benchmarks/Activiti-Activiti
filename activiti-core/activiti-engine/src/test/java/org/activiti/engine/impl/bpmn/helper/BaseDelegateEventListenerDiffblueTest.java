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
package org.activiti.engine.impl.bpmn.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class BaseDelegateEventListenerDiffblueTest {
  /**
   * Test {@link BaseDelegateEventListener#setEntityClass(Class)}.
   * <p>
   * Method under test: {@link BaseDelegateEventListener#setEntityClass(Class)}
   */
  @Test
  public void testSetEntityClass() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();
    Class<Object> entityClass = Object.class;

    // Act
    errorThrowingEventListener.setEntityClass(entityClass);

    // Assert
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, errorThrowingEventListener.entityClass);
  }

  /**
   * Test {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  public void testIsValidEvent() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();
    Class<Object> entityClass = Object.class;
    errorThrowingEventListener.setEntityClass(entityClass);

    // Act and Assert
    assertTrue(errorThrowingEventListener
        .isValidEvent(new ActivitiEntityEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Test {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link ErrorThrowingEventListener} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  public void testIsValidEvent_givenErrorThrowingEventListener_thenReturnTrue() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();

    // Act and Assert
    assertTrue(errorThrowingEventListener.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  public void testIsValidEvent_givenJavaLangObject_thenReturnFalse() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();
    Class<Object> entityClass = Object.class;
    errorThrowingEventListener.setEntityClass(entityClass);

    // Act and Assert
    assertFalse(errorThrowingEventListener.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }
}
