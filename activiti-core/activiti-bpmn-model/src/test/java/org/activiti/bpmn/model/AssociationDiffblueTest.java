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

public class AssociationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Association#setAssociationDirection(AssociationDirection)}
   *   <li>{@link Association#setSourceRef(String)}
   *   <li>{@link Association#setTargetRef(String)}
   *   <li>{@link Association#getAssociationDirection()}
   *   <li>{@link Association#getSourceRef()}
   *   <li>{@link Association#getTargetRef()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AssociationDirection Association.getAssociationDirection()", "String Association.getSourceRef()",
      "String Association.getTargetRef()", "void Association.setAssociationDirection(AssociationDirection)",
      "void Association.setSourceRef(String)", "void Association.setTargetRef(String)"})
  public void testGettersAndSetters() {
    // Arrange
    Association association = new Association();

    // Act
    association.setAssociationDirection(AssociationDirection.NONE);
    association.setSourceRef("Source Ref");
    association.setTargetRef("Target Ref");
    AssociationDirection actualAssociationDirection = association.getAssociationDirection();
    String actualSourceRef = association.getSourceRef();

    // Assert
    assertEquals("Source Ref", actualSourceRef);
    assertEquals("Target Ref", association.getTargetRef());
    assertEquals(AssociationDirection.NONE, actualAssociationDirection);
  }

  /**
   * Test {@link Association#clone()}.
   * <ul>
   *   <li>Given {@link Association} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Association#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Association Association.clone()"})
  public void testClone_givenAssociation() {
    // Arrange and Act
    Association actualCloneResult = (new Association()).clone();

    // Assert
    assertNull(actualCloneResult.getSourceRef());
    assertNull(actualCloneResult.getTargetRef());
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertEquals(AssociationDirection.NONE, actualCloneResult.getAssociationDirection());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Association#clone()}.
   * <ul>
   *   <li>Given {@link Association} (default constructor) AssociationDirection is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Association#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Association Association.clone()"})
  public void testClone_givenAssociationAssociationDirectionIsNull() {
    // Arrange
    Association association = new Association();
    association.setAssociationDirection(null);

    // Act
    Association actualCloneResult = association.clone();

    // Assert
    assertNull(actualCloneResult.getSourceRef());
    assertNull(actualCloneResult.getTargetRef());
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertEquals(AssociationDirection.NONE, actualCloneResult.getAssociationDirection());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link Association} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Association}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Association.<init>()"})
  public void testNewAssociation() {
    // Arrange and Act
    Association actualAssociation = new Association();

    // Assert
    assertNull(actualAssociation.getSourceRef());
    assertNull(actualAssociation.getTargetRef());
    assertNull(actualAssociation.getId());
    assertEquals(0, actualAssociation.getXmlColumnNumber());
    assertEquals(0, actualAssociation.getXmlRowNumber());
    assertEquals(AssociationDirection.NONE, actualAssociation.getAssociationDirection());
    assertTrue(actualAssociation.getAttributes().isEmpty());
    assertTrue(actualAssociation.getExtensionElements().isEmpty());
  }
}
