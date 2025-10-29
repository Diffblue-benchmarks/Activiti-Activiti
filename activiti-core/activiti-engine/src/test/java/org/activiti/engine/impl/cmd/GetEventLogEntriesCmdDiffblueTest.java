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
   * Method under test: {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd()}
   */
  @Test
  public void testNewGetEventLogEntriesCmd() {
    // Arrange and Act
    GetEventLogEntriesCmd actualGetEventLogEntriesCmd = new GetEventLogEntriesCmd();

    // Assert
    assertNull(actualGetEventLogEntriesCmd.pageSize);
    assertNull(actualGetEventLogEntriesCmd.startLogNr);
    assertNull(actualGetEventLogEntriesCmd.processInstanceId);
  }

  /**
   * Method under test:
   * {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd(Long, Long)}
   */
  @Test
  public void testNewGetEventLogEntriesCmd2() {
    // Arrange and Act
    GetEventLogEntriesCmd actualGetEventLogEntriesCmd = new GetEventLogEntriesCmd(1L, 3L);

    // Assert
    assertEquals(1L, actualGetEventLogEntriesCmd.startLogNr.longValue());
    assertEquals(3L, actualGetEventLogEntriesCmd.pageSize.longValue());
  }

  /**
   * Method under test:
   * {@link GetEventLogEntriesCmd#GetEventLogEntriesCmd(String)}
   */
  @Test
  public void testNewGetEventLogEntriesCmd3() {
    // Arrange and Act
    GetEventLogEntriesCmd actualGetEventLogEntriesCmd = new GetEventLogEntriesCmd("42");

    // Assert
    assertEquals("42", actualGetEventLogEntriesCmd.processInstanceId);
    assertNull(actualGetEventLogEntriesCmd.pageSize);
    assertNull(actualGetEventLogEntriesCmd.startLogNr);
  }
}
