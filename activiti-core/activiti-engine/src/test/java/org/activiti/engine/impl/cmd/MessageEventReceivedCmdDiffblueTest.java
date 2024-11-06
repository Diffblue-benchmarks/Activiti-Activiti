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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageEventReceivedCmdDiffblueTest {
  @InjectMocks
  private MessageEventReceivedCmd messageEventReceivedCmd;

  /**
   * Test
   * {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, boolean)}.
   * <p>
   * Method under test:
   * {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, boolean)}
   */
  @Test
  public void testNewMessageEventReceivedCmd() {
    // Arrange and Act
    MessageEventReceivedCmd actualMessageEventReceivedCmd = new MessageEventReceivedCmd("Message Name", "42", true);

    // Assert
    assertEquals("42", actualMessageEventReceivedCmd.executionId);
    assertEquals("Cannot execution operation because execution '42' is suspended",
        actualMessageEventReceivedCmd.getSuspendedExceptionMessage());
    assertEquals("Message Name", actualMessageEventReceivedCmd.messageName);
    assertNull(actualMessageEventReceivedCmd.payload);
    assertTrue(actualMessageEventReceivedCmd.async);
  }

  /**
   * Test
   * {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, Map)}
   */
  @Test
  public void testNewMessageEventReceivedCmd_givenFoo() {
    // Arrange
    HashMap<String, Object> processVariables = new HashMap<>();
    processVariables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    MessageEventReceivedCmd actualMessageEventReceivedCmd = new MessageEventReceivedCmd("Message Name", "42",
        processVariables);

    // Assert
    assertEquals("42", actualMessageEventReceivedCmd.executionId);
    assertEquals("Cannot execution operation because execution '42' is suspended",
        actualMessageEventReceivedCmd.getSuspendedExceptionMessage());
    assertEquals("Message Name", actualMessageEventReceivedCmd.messageName);
    assertFalse(actualMessageEventReceivedCmd.async);
    assertTrue(actualMessageEventReceivedCmd.payload.isEmpty());
  }

  /**
   * Test
   * {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, Map)}
   */
  @Test
  public void testNewMessageEventReceivedCmd_whenHashMap() {
    // Arrange and Act
    MessageEventReceivedCmd actualMessageEventReceivedCmd = new MessageEventReceivedCmd("Message Name", "42",
        new HashMap<>());

    // Assert
    assertEquals("42", actualMessageEventReceivedCmd.executionId);
    assertEquals("Cannot execution operation because execution '42' is suspended",
        actualMessageEventReceivedCmd.getSuspendedExceptionMessage());
    assertEquals("Message Name", actualMessageEventReceivedCmd.messageName);
    assertFalse(actualMessageEventReceivedCmd.async);
    assertTrue(actualMessageEventReceivedCmd.payload.isEmpty());
  }
}
