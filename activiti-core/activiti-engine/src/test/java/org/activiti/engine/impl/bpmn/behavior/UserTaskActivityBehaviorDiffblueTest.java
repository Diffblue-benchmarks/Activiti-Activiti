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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import org.activiti.bpmn.model.UserTask;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class UserTaskActivityBehaviorDiffblueTest {
  /**
   * Test {@link UserTaskActivityBehavior#UserTaskActivityBehavior(UserTask)}.
   * <p>
   * Method under test: {@link UserTaskActivityBehavior#UserTaskActivityBehavior(UserTask)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void UserTaskActivityBehavior.<init>(UserTask)"})
  public void testNewUserTaskActivityBehavior() {
    // Arrange and Act
    UserTaskActivityBehavior actualUserTaskActivityBehavior = new UserTaskActivityBehavior(new UserTask());

    // Assert
    assertNull(actualUserTaskActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualUserTaskActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualUserTaskActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test {@link UserTaskActivityBehavior#UserTaskActivityBehavior(UserTask, VariablesPropagator)}.
   * <p>
   * Method under test: {@link UserTaskActivityBehavior#UserTaskActivityBehavior(UserTask, VariablesPropagator)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void UserTaskActivityBehavior.<init>(UserTask, VariablesPropagator)"})
  public void testNewUserTaskActivityBehavior2() {
    // Arrange
    UserTask userTask = new UserTask();

    // Act
    UserTaskActivityBehavior actualUserTaskActivityBehavior = new UserTaskActivityBehavior(userTask,
        new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    assertNull(actualUserTaskActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualUserTaskActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualUserTaskActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test {@link UserTaskActivityBehavior#extractCandidates(String)}.
   * <p>
   * Method under test: {@link UserTaskActivityBehavior#extractCandidates(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List UserTaskActivityBehavior.extractCandidates(String)"})
  public void testExtractCandidates() {
    // Arrange and Act
    List<String> actualExtractCandidatesResult = (new UserTaskActivityBehavior(new UserTask()))
        .extractCandidates("Str");

    // Assert
    assertEquals(1, actualExtractCandidatesResult.size());
    assertEquals("Str", actualExtractCandidatesResult.get(0));
  }
}
