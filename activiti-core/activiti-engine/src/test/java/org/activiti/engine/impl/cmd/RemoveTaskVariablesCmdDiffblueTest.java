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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class RemoveTaskVariablesCmdDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link RemoveTaskVariablesCmd#RemoveTaskVariablesCmd(String, Collection, boolean)}
   *   <li>{@link RemoveTaskVariablesCmd#getSuspendedTaskException()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RemoveTaskVariablesCmd.<init>(String, Collection, boolean)",
    "String RemoveTaskVariablesCmd.getSuspendedTaskException()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    RemoveTaskVariablesCmd actualRemoveTaskVariablesCmd =
        new RemoveTaskVariablesCmd("42", new ArrayList<>(), true);

    // Assert
    assertEquals(
        "Cannot remove variables from a suspended task.",
        actualRemoveTaskVariablesCmd.getSuspendedTaskException());
  }
}
