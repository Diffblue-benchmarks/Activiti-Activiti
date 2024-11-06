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
public class SignalEventReceivedCmdDiffblueTest {
  @InjectMocks
  private SignalEventReceivedCmd signalEventReceivedCmd;

  /**
   * Test
   * {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, boolean, String)}.
   * <p>
   * Method under test:
   * {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, boolean, String)}
   */
  @Test
  public void testNewSignalEventReceivedCmd() {
    // Arrange and Act
    SignalEventReceivedCmd actualSignalEventReceivedCmd = new SignalEventReceivedCmd("Event Name", "42", true, "42");

    // Assert
    assertEquals("42", actualSignalEventReceivedCmd.executionId);
    assertEquals("42", actualSignalEventReceivedCmd.tenantId);
    assertEquals("Event Name", actualSignalEventReceivedCmd.eventName);
    assertNull(actualSignalEventReceivedCmd.payload);
    assertTrue(actualSignalEventReceivedCmd.async);
  }

  /**
   * Test
   * {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, Map, String)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, Map, String)}
   */
  @Test
  public void testNewSignalEventReceivedCmd_givenFoo() {
    // Arrange
    HashMap<String, Object> processVariables = new HashMap<>();
    processVariables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    SignalEventReceivedCmd actualSignalEventReceivedCmd = new SignalEventReceivedCmd("Event Name", "42",
        processVariables, "42");

    // Assert
    assertEquals("42", actualSignalEventReceivedCmd.executionId);
    assertEquals("42", actualSignalEventReceivedCmd.tenantId);
    assertEquals("Event Name", actualSignalEventReceivedCmd.eventName);
    assertFalse(actualSignalEventReceivedCmd.async);
    assertTrue(actualSignalEventReceivedCmd.payload.isEmpty());
  }

  /**
   * Test
   * {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, Map, String)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, Map, String)}
   */
  @Test
  public void testNewSignalEventReceivedCmd_whenHashMap() {
    // Arrange and Act
    SignalEventReceivedCmd actualSignalEventReceivedCmd = new SignalEventReceivedCmd("Event Name", "42",
        new HashMap<>(), "42");

    // Assert
    assertEquals("42", actualSignalEventReceivedCmd.executionId);
    assertEquals("42", actualSignalEventReceivedCmd.tenantId);
    assertEquals("Event Name", actualSignalEventReceivedCmd.eventName);
    assertFalse(actualSignalEventReceivedCmd.async);
    assertTrue(actualSignalEventReceivedCmd.payload.isEmpty());
  }
}
