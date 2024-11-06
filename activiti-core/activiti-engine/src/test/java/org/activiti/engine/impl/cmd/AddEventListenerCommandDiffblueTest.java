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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.junit.Test;

public class AddEventListenerCommandDiffblueTest {
  /**
   * Test
   * {@link AddEventListenerCommand#AddEventListenerCommand(ActivitiEventListener, ActivitiEventType[])}.
   * <p>
   * Method under test:
   * {@link AddEventListenerCommand#AddEventListenerCommand(ActivitiEventListener, ActivitiEventType[])}
   */
  @Test
  public void testNewAddEventListenerCommand() {
    // Arrange and Act
    AddEventListenerCommand actualAddEventListenerCommand = new AddEventListenerCommand(
        new BaseEntityEventListener(true), new ActivitiEventType[]{ActivitiEventType.ENTITY_CREATED});

    // Assert
    ActivitiEventListener activitiEventListener = actualAddEventListenerCommand.listener;
    assertTrue(activitiEventListener instanceof BaseEntityEventListener);
    assertTrue(activitiEventListener.isFailOnException());
    assertArrayEquals(new ActivitiEventType[]{ActivitiEventType.ENTITY_CREATED}, actualAddEventListenerCommand.types);
  }

  /**
   * Test
   * {@link AddEventListenerCommand#AddEventListenerCommand(ActivitiEventListener)}.
   * <ul>
   *   <li>Then return {@link AddEventListenerCommand#types} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AddEventListenerCommand#AddEventListenerCommand(ActivitiEventListener)}
   */
  @Test
  public void testNewAddEventListenerCommand_thenReturnTypesIsNull() {
    // Arrange and Act
    AddEventListenerCommand actualAddEventListenerCommand = new AddEventListenerCommand(
        new BaseEntityEventListener(true));

    // Assert
    ActivitiEventListener activitiEventListener = actualAddEventListenerCommand.listener;
    assertTrue(activitiEventListener instanceof BaseEntityEventListener);
    assertNull(actualAddEventListenerCommand.types);
    assertTrue(activitiEventListener.isFailOnException());
  }
}
