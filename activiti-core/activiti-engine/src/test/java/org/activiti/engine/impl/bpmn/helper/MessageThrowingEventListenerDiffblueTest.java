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
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.junit.Test;

public class MessageThrowingEventListenerDiffblueTest {
  /**
   * Test {@link MessageThrowingEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageThrowingEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    MessageThrowingEventListener messageThrowingEventListener = new MessageThrowingEventListener();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> messageThrowingEventListener.onEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link MessageThrowingEventListener}
   *   <li>{@link MessageThrowingEventListener#setMessageName(String)}
   *   <li>{@link MessageThrowingEventListener#isFailOnException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    MessageThrowingEventListener actualMessageThrowingEventListener = new MessageThrowingEventListener();
    actualMessageThrowingEventListener.setMessageName("Message Name");

    // Assert that nothing has changed
    assertTrue(actualMessageThrowingEventListener.isFailOnException());
  }
}
