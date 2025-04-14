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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SignalEventReceivedCmdDiffblueTest {
  /**
   * Test {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, boolean, String)}.
   * <p>
   * Method under test: {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, boolean, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SignalEventReceivedCmd.<init>(String, String, boolean, String)"})
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
   * Test {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, Map, String)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link SignalEventReceivedCmd#payload} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SignalEventReceivedCmd.<init>(String, String, Map, String)"})
  public void testNewSignalEventReceivedCmd_whenHashMap_thenReturnPayloadEmpty() {
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

  /**
   * Test {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, Map, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link SignalEventReceivedCmd#payload} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalEventReceivedCmd#SignalEventReceivedCmd(String, String, Map, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SignalEventReceivedCmd.<init>(String, String, Map, String)"})
  public void testNewSignalEventReceivedCmd_whenNull_thenReturnPayloadIsNull() {
    // Arrange and Act
    SignalEventReceivedCmd actualSignalEventReceivedCmd = new SignalEventReceivedCmd("Event Name", "42", null, "42");

    // Assert
    assertEquals("42", actualSignalEventReceivedCmd.executionId);
    assertEquals("42", actualSignalEventReceivedCmd.tenantId);
    assertEquals("Event Name", actualSignalEventReceivedCmd.eventName);
    assertNull(actualSignalEventReceivedCmd.payload);
    assertFalse(actualSignalEventReceivedCmd.async);
  }
}
