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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BaseDelegateEventListenerDiffblueTest {
  /**
   * Test {@link BaseDelegateEventListener#setEntityClass(Class)}.
   *
   * <p>Method under test: {@link BaseDelegateEventListener#setEntityClass(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseDelegateEventListener.setEntityClass(Class)"})
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
   *
   * <p>Method under test: {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseDelegateEventListener.isValidEvent(ActivitiEvent)"})
  public void testIsValidEvent() {
    // Arrange
    Class<Object> entityClass = Object.class;

    DelegateActivitiEventListener delegateActivitiEventListener =
        new DelegateActivitiEventListener("Class Name", entityClass);
    Class<Object> entityClass2 = Object.class;
    delegateActivitiEventListener.setEntityClass(entityClass2);

    // Act
    boolean actualIsValidEventResult =
        delegateActivitiEventListener.isValidEvent(
            new ActivitiEntityEventImpl(JSONObject.NULL, ActivitiEventType.ENTITY_CREATED));

    // Assert
    assertTrue(actualIsValidEventResult);
  }

  /**
   * Test {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ErrorThrowingEventListener} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseDelegateEventListener.isValidEvent(ActivitiEvent)"})
  public void testIsValidEvent_givenErrorThrowingEventListener_thenReturnTrue() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();

    // Act and Assert
    assertTrue(errorThrowingEventListener.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseDelegateEventListener#isValidEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseDelegateEventListener.isValidEvent(ActivitiEvent)"})
  public void testIsValidEvent_thenReturnFalse() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();
    Class<Object> entityClass = Object.class;
    errorThrowingEventListener.setEntityClass(entityClass);

    // Act and Assert
    assertFalse(errorThrowingEventListener.isValidEvent(new ActivitiActivityCancelledEventImpl()));
  }
}
