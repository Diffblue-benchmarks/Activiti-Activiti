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
import org.junit.Test;

public class AddCommentCmdDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddCommentCmd#AddCommentCmd(String, String, String)}
   *   <li>{@link AddCommentCmd#getSuspendedExceptionMessage()}
   *   <li>{@link AddCommentCmd#getSuspendedTaskException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenNotAllWhoWanderAreLost() {
    // Arrange and Act
    AddCommentCmd actualAddCommentCmd = new AddCommentCmd("42", "42", "Not all who wander are lost");
    String actualSuspendedExceptionMessage = actualAddCommentCmd.getSuspendedExceptionMessage();

    // Assert
    assertEquals("Cannot add a comment to a suspended execution", actualSuspendedExceptionMessage);
    assertEquals("Cannot add a comment to a suspended task", actualAddCommentCmd.getSuspendedTaskException());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Type}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddCommentCmd#AddCommentCmd(String, String, String, String)}
   *   <li>{@link AddCommentCmd#getSuspendedExceptionMessage()}
   *   <li>{@link AddCommentCmd#getSuspendedTaskException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenType() {
    // Arrange and Act
    AddCommentCmd actualAddCommentCmd = new AddCommentCmd("42", "42", "Type", "Not all who wander are lost");
    String actualSuspendedExceptionMessage = actualAddCommentCmd.getSuspendedExceptionMessage();

    // Assert
    assertEquals("Cannot add a comment to a suspended execution", actualSuspendedExceptionMessage);
    assertEquals("Cannot add a comment to a suspended task", actualAddCommentCmd.getSuspendedTaskException());
  }
}
