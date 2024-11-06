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
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class GetEventLogEntriesCmdDiffblueTest {
  /**
   * Test {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd()}.
   * <ul>
   *   <li>Then return {@link GetEventLogEntriesCmd#processInstanceId} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd()}
   */
  @Test
  public void testNewGetEventLogEntriesCmd_thenReturnProcessInstanceIdIsNull() {
    // Arrange and Act
    GetEventLogEntriesCmd actualGetEventLogEntriesCmd = new GetEventLogEntriesCmd();

    // Assert
    assertNull(actualGetEventLogEntriesCmd.pageSize);
    assertNull(actualGetEventLogEntriesCmd.startLogNr);
    assertNull(actualGetEventLogEntriesCmd.processInstanceId);
  }

  /**
   * Test {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@link GetEventLogEntriesCmd#processInstanceId} is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd(String)}
   */
  @Test
  public void testNewGetEventLogEntriesCmd_when42_thenReturnProcessInstanceIdIs42() {
    // Arrange and Act
    GetEventLogEntriesCmd actualGetEventLogEntriesCmd = new GetEventLogEntriesCmd("42");

    // Assert
    assertEquals("42", actualGetEventLogEntriesCmd.processInstanceId);
    assertNull(actualGetEventLogEntriesCmd.pageSize);
    assertNull(actualGetEventLogEntriesCmd.startLogNr);
  }

  /**
   * Test {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd(Long, Long)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link GetEventLogEntriesCmd#startLogNr} longValue is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd(Long, Long)}
   */
  @Test
  public void testNewGetEventLogEntriesCmd_whenOne_thenReturnStartLogNrLongValueIsOne() {
    // Arrange and Act
    GetEventLogEntriesCmd actualGetEventLogEntriesCmd = new GetEventLogEntriesCmd(1L, 3L);

    // Assert
    assertEquals(1L, actualGetEventLogEntriesCmd.startLogNr.longValue());
    assertEquals(3L, actualGetEventLogEntriesCmd.pageSize.longValue());
  }
}
