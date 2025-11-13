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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DataAssociationDiffblueTest {
  /**
   * Test {@link DataAssociation#clone()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Assignment} (default constructor).
   *   <li>Then return Assignments size is one.
   * </ul>
   *
   * <p>Method under test: {@link DataAssociation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DataAssociation DataAssociation.clone()"})
  public void testClone_givenArrayListAddAssignment_thenReturnAssignmentsSizeIsOne() {
    // Arrange
    ArrayList<Assignment> assignments = new ArrayList<>();
    assignments.add(new Assignment());

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setAssignments(assignments);

    // Act and Assert
    List<Assignment> assignments2 = dataAssociation.clone().getAssignments();
    assertEquals(1, assignments2.size());
    Assignment getResult = assignments2.get(0);
    assertNull(getResult.getFrom());
    assertNull(getResult.getTo());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataAssociation#clone()}.
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) Assignments is {@code null}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DataAssociation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DataAssociation DataAssociation.clone()"})
  public void testClone_givenDataAssociationAssignmentsIsNull_thenReturnIdIsNull() {
    // Arrange
    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setAssignments(null);

    // Act
    DataAssociation actualCloneResult = dataAssociation.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSourceRef());
    assertNull(actualCloneResult.getTargetRef());
    assertNull(actualCloneResult.getTransformation());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAssignments().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataAssociation#clone()}.
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DataAssociation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DataAssociation DataAssociation.clone()"})
  public void testClone_givenDataAssociation_thenReturnIdIsNull() {
    // Arrange and Act
    DataAssociation actualCloneResult = new DataAssociation().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSourceRef());
    assertNull(actualCloneResult.getTargetRef());
    assertNull(actualCloneResult.getTransformation());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAssignments().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataAssociation#setValues(DataAssociation)} with {@code otherAssociation}.
   *
   * <ul>
   *   <li>Then calls {@link Assignment#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link DataAssociation#setValues(DataAssociation)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DataAssociation.setValues(DataAssociation)"})
  public void testSetValuesWithOtherAssociation_thenCallsClone() {
    // Arrange
    DataAssociation dataAssociation = new DataAssociation();

    Assignment assignment = mock(Assignment.class);
    when(assignment.clone()).thenReturn(new Assignment());

    ArrayList<Assignment> assignments = new ArrayList<>();
    assignments.add(assignment);

    DataAssociation otherAssociation = new DataAssociation();
    otherAssociation.setAssignments(assignments);

    // Act
    dataAssociation.setValues(otherAssociation);

    // Assert
    verify(assignment).clone();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link DataAssociation}
   *   <li>{@link DataAssociation#setAssignments(List)}
   *   <li>{@link DataAssociation#setSourceRef(String)}
   *   <li>{@link DataAssociation#setTargetRef(String)}
   *   <li>{@link DataAssociation#setTransformation(String)}
   *   <li>{@link DataAssociation#getAssignments()}
   *   <li>{@link DataAssociation#getSourceRef()}
   *   <li>{@link DataAssociation#getTargetRef()}
   *   <li>{@link DataAssociation#getTransformation()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DataAssociation.<init>()",
    "List DataAssociation.getAssignments()",
    "String DataAssociation.getSourceRef()",
    "String DataAssociation.getTargetRef()",
    "String DataAssociation.getTransformation()",
    "void DataAssociation.setAssignments(List)",
    "void DataAssociation.setSourceRef(String)",
    "void DataAssociation.setTargetRef(String)",
    "void DataAssociation.setTransformation(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    DataAssociation actualDataAssociation = new DataAssociation();
    ArrayList<Assignment> assignments = new ArrayList<>();
    actualDataAssociation.setAssignments(assignments);
    actualDataAssociation.setSourceRef("Source Ref");
    actualDataAssociation.setTargetRef("Target Ref");
    actualDataAssociation.setTransformation("Transformation");
    List<Assignment> actualAssignments = actualDataAssociation.getAssignments();
    String actualSourceRef = actualDataAssociation.getSourceRef();
    String actualTargetRef = actualDataAssociation.getTargetRef();

    // Assert
    assertEquals("Source Ref", actualSourceRef);
    assertEquals("Target Ref", actualTargetRef);
    assertEquals("Transformation", actualDataAssociation.getTransformation());
    assertNull(actualDataAssociation.getId());
    assertEquals(0, actualDataAssociation.getXmlColumnNumber());
    assertEquals(0, actualDataAssociation.getXmlRowNumber());
    assertTrue(actualAssignments.isEmpty());
    assertTrue(actualDataAssociation.getAttributes().isEmpty());
    assertTrue(actualDataAssociation.getExtensionElements().isEmpty());
    assertSame(assignments, actualAssignments);
  }
}
