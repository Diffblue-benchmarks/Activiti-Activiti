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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.Test;

public class SetTaskDueDateCmdDiffblueTest {
  /**
   * Test {@link SetTaskDueDateCmd#SetTaskDueDateCmd(String, Date)}.
   * <p>
   * Method under test: {@link SetTaskDueDateCmd#SetTaskDueDateCmd(String, Date)}
   */
  @Test
  public void testNewSetTaskDueDateCmd() {
    // Arrange and Act
    SetTaskDueDateCmd actualSetTaskDueDateCmd = new SetTaskDueDateCmd("42",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    assertEquals("42", actualSetTaskDueDateCmd.taskId);
    assertEquals("Cannot execute operation: task is suspended", actualSetTaskDueDateCmd.getSuspendedTaskException());
  }
}
