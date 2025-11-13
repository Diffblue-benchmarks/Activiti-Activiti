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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ErrorThrowingEventListenerDiffblueTest {
  /**
   * Test {@link ErrorThrowingEventListener#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ErrorThrowingEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ErrorThrowingEventListener.onEvent(ActivitiEvent)"})
  public void testOnEvent_givenJavaLangObject_thenDoesNotThrow() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();
    Class<Object> entityClass = Object.class;
    errorThrowingEventListener.setEntityClass(entityClass);

    // Act and Assert
    errorThrowingEventListener.onEvent(new ActivitiActivityCancelledEventImpl());
  }

  /**
   * Test {@link ErrorThrowingEventListener#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default constructor).
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorThrowingEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ErrorThrowingEventListener.onEvent(ActivitiEvent)"})
  public void testOnEvent_whenActivitiActivityCancelledEventImpl_thenThrowActivitiException() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> errorThrowingEventListener.onEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test {@link ErrorThrowingEventListener#onEventInternal(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ErrorThrowingEventListener#onEventInternal(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ErrorThrowingEventListener.onEventInternal(ActivitiEvent)"})
  public void testOnEventInternal_thenThrowActivitiException() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> errorThrowingEventListener.onEventInternal(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ErrorThrowingEventListener}
   *   <li>{@link ErrorThrowingEventListener#setErrorCode(String)}
   *   <li>{@link ErrorThrowingEventListener#isFailOnException()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ErrorThrowingEventListener.<init>()",
    "boolean ErrorThrowingEventListener.isFailOnException()",
    "void ErrorThrowingEventListener.setErrorCode(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ErrorThrowingEventListener actualErrorThrowingEventListener = new ErrorThrowingEventListener();
    actualErrorThrowingEventListener.setErrorCode("An error occurred");

    // Assert
    assertTrue(actualErrorThrowingEventListener.isFailOnException());
  }
}
