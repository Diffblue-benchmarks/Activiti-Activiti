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
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.junit.Test;

public class ErrorThrowingEventListenerDiffblueTest {
  /**
   * Test {@link ErrorThrowingEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default
   * constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorThrowingEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_whenActivitiActivityCancelledEventImpl_thenThrowActivitiException() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> errorThrowingEventListener.onEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test {@link ErrorThrowingEventListener#onEventInternal(ActivitiEvent)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ErrorThrowingEventListener#onEventInternal(ActivitiEvent)}
   */
  @Test
  public void testOnEventInternal_thenThrowActivitiException() {
    // Arrange
    ErrorThrowingEventListener errorThrowingEventListener = new ErrorThrowingEventListener();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> errorThrowingEventListener.onEventInternal(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link ErrorThrowingEventListener}
   *   <li>{@link ErrorThrowingEventListener#setErrorCode(String)}
   *   <li>{@link ErrorThrowingEventListener#isFailOnException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ErrorThrowingEventListener actualErrorThrowingEventListener = new ErrorThrowingEventListener();
    actualErrorThrowingEventListener.setErrorCode("An error occurred");

    // Assert that nothing has changed
    assertTrue(actualErrorThrowingEventListener.isFailOnException());
  }
}
