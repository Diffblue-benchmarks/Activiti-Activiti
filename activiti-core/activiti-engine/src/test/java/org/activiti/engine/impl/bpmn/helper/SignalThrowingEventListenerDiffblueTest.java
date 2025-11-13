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
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SignalThrowingEventListenerDiffblueTest {
  /**
   * Test {@link SignalThrowingEventListener#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link SignalThrowingEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalThrowingEventListener.onEvent(ActivitiEvent)"})
  public void testOnEvent_givenJavaLangObject_thenDoesNotThrow() {
    // Arrange
    SignalThrowingEventListener signalThrowingEventListener = new SignalThrowingEventListener();
    Class<Object> entityClass = Object.class;
    signalThrowingEventListener.setEntityClass(entityClass);

    // Act and Assert
    signalThrowingEventListener.onEvent(new ActivitiActivityCancelledEventImpl());
  }

  /**
   * Test {@link SignalThrowingEventListener#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link SignalThrowingEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalThrowingEventListener.onEvent(ActivitiEvent)"})
  public void testOnEvent_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    SignalThrowingEventListener signalThrowingEventListener = new SignalThrowingEventListener();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> signalThrowingEventListener.onEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link SignalThrowingEventListener}
   *   <li>{@link SignalThrowingEventListener#setProcessInstanceScope(boolean)}
   *   <li>{@link SignalThrowingEventListener#setSignalName(String)}
   *   <li>{@link SignalThrowingEventListener#isFailOnException()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalThrowingEventListener.<init>()",
    "boolean SignalThrowingEventListener.isFailOnException()",
    "void SignalThrowingEventListener.setProcessInstanceScope(boolean)",
    "void SignalThrowingEventListener.setSignalName(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    SignalThrowingEventListener actualSignalThrowingEventListener =
        new SignalThrowingEventListener();
    actualSignalThrowingEventListener.setProcessInstanceScope(true);
    actualSignalThrowingEventListener.setSignalName("Signal Name");

    // Assert
    assertTrue(actualSignalThrowingEventListener.isFailOnException());
  }
}
