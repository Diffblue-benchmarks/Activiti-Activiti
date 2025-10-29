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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.function.BiFunction;
import org.junit.Test;

public class NeedsActiveExecutionCmdDiffblueTest {
  /**
   * Method under test:
   * {@link NeedsActiveExecutionCmd#getSuspendedExceptionMessage()}
   */
  @Test
  public void testGetSuspendedExceptionMessage() {
    // Arrange, Act and Assert
    assertEquals("Cannot execution operation because execution '42' is suspended",
        (new MessageEventReceivedCmd("Message Name", "42", true)).getSuspendedExceptionMessage());
  }

  /**
   * Method under test:
   * {@link NeedsActiveExecutionCmd#getSuspendedExceptionMessage()}
   */
  @Test
  public void testGetSuspendedExceptionMessage2() {
    // Arrange
    HashMap<String, Object> processVariables = new HashMap<>();
    processVariables.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertEquals("Cannot execution operation because execution '42' is suspended",
        (new MessageEventReceivedCmd("Message Name", "42", processVariables)).getSuspendedExceptionMessage());
  }
}
