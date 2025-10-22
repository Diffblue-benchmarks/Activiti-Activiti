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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AssignmentDiffblueTest {
  /**
   * Test {@link Assignment#clone()}.
   * <p>
   * Method under test: {@link Assignment#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Assignment Assignment.clone()"})
  public void testClone() {
    // Arrange and Act
    Assignment actualCloneResult = (new Assignment()).clone();

    // Assert
    assertNull(actualCloneResult.getFrom());
    assertNull(actualCloneResult.getTo());
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Assignment}
   *   <li>{@link Assignment#setFrom(String)}
   *   <li>{@link Assignment#setTo(String)}
   *   <li>{@link Assignment#getFrom()}
   *   <li>{@link Assignment#getTo()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Assignment.<init>()", "String Assignment.getFrom()", "String Assignment.getTo()",
      "void Assignment.setFrom(String)", "void Assignment.setTo(String)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    Assignment actualAssignment = new Assignment();
    actualAssignment.setFrom("jane.doe@example.org");
    actualAssignment.setTo("alice.liddell@example.org");
    String actualFrom = actualAssignment.getFrom();

    // Assert
    assertEquals("alice.liddell@example.org", actualAssignment.getTo());
    assertEquals("jane.doe@example.org", actualFrom);
    assertNull(actualAssignment.getId());
    assertEquals(0, actualAssignment.getXmlColumnNumber());
    assertEquals(0, actualAssignment.getXmlRowNumber());
    assertTrue(actualAssignment.getAttributes().isEmpty());
    assertTrue(actualAssignment.getExtensionElements().isEmpty());
  }
}
