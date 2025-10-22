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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CompensateEventDefinitionDiffblueTest {
  /**
   * Test {@link CompensateEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link CompensateEventDefinition} (default constructor).</li>
   *   <li>Then return WaitForCompletion.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompensateEventDefinition#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CompensateEventDefinition CompensateEventDefinition.clone()"})
  public void testClone_givenCompensateEventDefinition_thenReturnWaitForCompletion() {
    // Arrange and Act
    CompensateEventDefinition actualCloneResult = (new CompensateEventDefinition()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getActivityRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isWaitForCompletion());
  }

  /**
   * Test {@link CompensateEventDefinition#clone()}.
   * <ul>
   *   <li>Then return not WaitForCompletion.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompensateEventDefinition#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"CompensateEventDefinition CompensateEventDefinition.clone()"})
  public void testClone_thenReturnNotWaitForCompletion() {
    // Arrange
    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();
    compensateEventDefinition.setWaitForCompletion(false);

    // Act
    CompensateEventDefinition actualCloneResult = compensateEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getActivityRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isWaitForCompletion());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CompensateEventDefinition}
   *   <li>{@link CompensateEventDefinition#setActivityRef(String)}
   *   <li>{@link CompensateEventDefinition#setWaitForCompletion(boolean)}
   *   <li>{@link CompensateEventDefinition#getActivityRef()}
   *   <li>{@link CompensateEventDefinition#isWaitForCompletion()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CompensateEventDefinition.<init>()", "String CompensateEventDefinition.getActivityRef()",
      "boolean CompensateEventDefinition.isWaitForCompletion()",
      "void CompensateEventDefinition.setActivityRef(String)",
      "void CompensateEventDefinition.setWaitForCompletion(boolean)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    CompensateEventDefinition actualCompensateEventDefinition = new CompensateEventDefinition();
    actualCompensateEventDefinition.setActivityRef("Activity Ref");
    actualCompensateEventDefinition.setWaitForCompletion(true);
    String actualActivityRef = actualCompensateEventDefinition.getActivityRef();
    boolean actualIsWaitForCompletionResult = actualCompensateEventDefinition.isWaitForCompletion();

    // Assert
    assertEquals("Activity Ref", actualActivityRef);
    assertNull(actualCompensateEventDefinition.getId());
    assertEquals(0, actualCompensateEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualCompensateEventDefinition.getXmlRowNumber());
    assertTrue(actualCompensateEventDefinition.getAttributes().isEmpty());
    assertTrue(actualCompensateEventDefinition.getExtensionElements().isEmpty());
    assertTrue(actualIsWaitForCompletionResult);
  }
}
