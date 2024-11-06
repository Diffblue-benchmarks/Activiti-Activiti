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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.junit.Test;

public class DelegateActivitiEventListenerDiffblueTest {
  /**
   * Test
   * {@link DelegateActivitiEventListener#DelegateActivitiEventListener(String, Class)}.
   * <p>
   * Method under test:
   * {@link DelegateActivitiEventListener#DelegateActivitiEventListener(String, Class)}
   */
  @Test
  public void testNewDelegateActivitiEventListener() {
    // Arrange
    Class<Object> entityClass = Object.class;

    // Act
    DelegateActivitiEventListener actualDelegateActivitiEventListener = new DelegateActivitiEventListener("Class Name",
        entityClass);

    // Assert
    assertEquals("Class Name", actualDelegateActivitiEventListener.className);
    assertNull(actualDelegateActivitiEventListener.delegateInstance);
    assertFalse(actualDelegateActivitiEventListener.isFailOnException());
    assertFalse(actualDelegateActivitiEventListener.failOnException);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, actualDelegateActivitiEventListener.entityClass);
  }

  /**
   * Test {@link DelegateActivitiEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateActivitiEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Class<Object> entityClass = Object.class;
    DelegateActivitiEventListener delegateActivitiEventListener = new DelegateActivitiEventListener("Class Name",
        entityClass);
    ActivitiEntityEventImpl event = mock(ActivitiEntityEventImpl.class);
    when(event.getEntity()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> delegateActivitiEventListener.onEvent(event));
    verify(event).getEntity();
  }

  /**
   * Test {@link DelegateActivitiEventListener#isFailOnException()}.
   * <p>
   * Method under test: {@link DelegateActivitiEventListener#isFailOnException()}
   */
  @Test
  public void testIsFailOnException() {
    // Arrange
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertFalse((new DelegateActivitiEventListener("Class Name", entityClass)).isFailOnException());
  }
}
