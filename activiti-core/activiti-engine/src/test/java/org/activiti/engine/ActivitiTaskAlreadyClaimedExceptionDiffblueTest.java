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
package org.activiti.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiTaskAlreadyClaimedExceptionDiffblueTest {
  @InjectMocks
  private ActivitiTaskAlreadyClaimedException activitiTaskAlreadyClaimedException;

  @InjectMocks
  private String string;

  /**
   * Test
   * {@link ActivitiTaskAlreadyClaimedException#ActivitiTaskAlreadyClaimedException(String, String)}.
   * <p>
   * Method under test:
   * {@link ActivitiTaskAlreadyClaimedException#ActivitiTaskAlreadyClaimedException(String, String)}
   */
  @Test
  public void testNewActivitiTaskAlreadyClaimedException() {
    // Arrange and Act
    ActivitiTaskAlreadyClaimedException actualActivitiTaskAlreadyClaimedException = new ActivitiTaskAlreadyClaimedException(
        "42", "Task Assignee");

    // Assert
    assertEquals("42", actualActivitiTaskAlreadyClaimedException.getTaskId());
    assertEquals("Task '42' is already claimed by someone else.",
        actualActivitiTaskAlreadyClaimedException.getLocalizedMessage());
    assertEquals("Task '42' is already claimed by someone else.",
        actualActivitiTaskAlreadyClaimedException.getMessage());
    assertEquals("Task Assignee", actualActivitiTaskAlreadyClaimedException.getTaskAssignee());
    assertNull(actualActivitiTaskAlreadyClaimedException.getCause());
    assertEquals(0, actualActivitiTaskAlreadyClaimedException.getSuppressed().length);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiTaskAlreadyClaimedException#getTaskAssignee()}
   *   <li>{@link ActivitiTaskAlreadyClaimedException#getTaskId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ActivitiTaskAlreadyClaimedException activitiTaskAlreadyClaimedException = new ActivitiTaskAlreadyClaimedException(
        "42", "Task Assignee");

    // Act
    String actualTaskAssignee = activitiTaskAlreadyClaimedException.getTaskAssignee();

    // Assert
    assertEquals("42", activitiTaskAlreadyClaimedException.getTaskId());
    assertEquals("Task Assignee", actualTaskAssignee);
  }
}
