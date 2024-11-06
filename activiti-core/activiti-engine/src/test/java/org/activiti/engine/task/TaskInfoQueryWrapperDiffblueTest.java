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
package org.activiti.engine.task;

import static org.junit.Assert.assertSame;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.junit.Test;

public class TaskInfoQueryWrapperDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskInfoQueryWrapper#TaskInfoQueryWrapper(TaskInfoQuery)}
   *   <li>{@link TaskInfoQueryWrapper#setTaskInfoQuery(TaskInfoQuery)}
   *   <li>{@link TaskInfoQueryWrapper#getTaskInfoQuery()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    TaskInfoQueryWrapper actualTaskInfoQueryWrapper = new TaskInfoQueryWrapper(new HistoricTaskInstanceQueryImpl());
    HistoricTaskInstanceQueryImpl taskInfoQuery = new HistoricTaskInstanceQueryImpl();
    actualTaskInfoQueryWrapper.setTaskInfoQuery(taskInfoQuery);

    // Assert that nothing has changed
    assertSame(taskInfoQuery, actualTaskInfoQueryWrapper.getTaskInfoQuery());
  }
}
