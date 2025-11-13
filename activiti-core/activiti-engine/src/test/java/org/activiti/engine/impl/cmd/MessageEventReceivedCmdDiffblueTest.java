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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MessageEventReceivedCmdDiffblueTest {
  /**
   * Test {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, boolean)}.
   *
   * <p>Method under test: {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageEventReceivedCmd.<init>(String, String, boolean)"})
  public void testNewMessageEventReceivedCmd() {
    // Arrange and Act
    MessageEventReceivedCmd actualMessageEventReceivedCmd =
        new MessageEventReceivedCmd("Message Name", "42", true);

    // Assert
    assertEquals("42", actualMessageEventReceivedCmd.executionId);
    assertEquals(
        "Cannot execution operation because execution '42' is suspended",
        actualMessageEventReceivedCmd.getSuspendedExceptionMessage());
    assertEquals("Message Name", actualMessageEventReceivedCmd.messageName);
    assertNull(actualMessageEventReceivedCmd.payload);
    assertTrue(actualMessageEventReceivedCmd.async);
  }

  /**
   * Test {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@link MessageEventReceivedCmd#payload} Empty.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String,
   * Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageEventReceivedCmd.<init>(String, String, Map)"})
  public void testNewMessageEventReceivedCmd_whenHashMap_thenReturnPayloadEmpty() {
    // Arrange and Act
    MessageEventReceivedCmd actualMessageEventReceivedCmd =
        new MessageEventReceivedCmd("Message Name", "42", new HashMap<>());

    // Assert
    assertEquals("42", actualMessageEventReceivedCmd.executionId);
    assertEquals(
        "Cannot execution operation because execution '42' is suspended",
        actualMessageEventReceivedCmd.getSuspendedExceptionMessage());
    assertEquals("Message Name", actualMessageEventReceivedCmd.messageName);
    assertFalse(actualMessageEventReceivedCmd.async);
    assertTrue(actualMessageEventReceivedCmd.payload.isEmpty());
  }

  /**
   * Test {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String, Map)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link MessageEventReceivedCmd#payload} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventReceivedCmd#MessageEventReceivedCmd(String, String,
   * Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageEventReceivedCmd.<init>(String, String, Map)"})
  public void testNewMessageEventReceivedCmd_whenNull_thenReturnPayloadIsNull() {
    // Arrange and Act
    MessageEventReceivedCmd actualMessageEventReceivedCmd =
        new MessageEventReceivedCmd("Message Name", "42", null);

    // Assert
    assertEquals("42", actualMessageEventReceivedCmd.executionId);
    assertEquals(
        "Cannot execution operation because execution '42' is suspended",
        actualMessageEventReceivedCmd.getSuspendedExceptionMessage());
    assertEquals("Message Name", actualMessageEventReceivedCmd.messageName);
    assertNull(actualMessageEventReceivedCmd.payload);
    assertFalse(actualMessageEventReceivedCmd.async);
  }
}
