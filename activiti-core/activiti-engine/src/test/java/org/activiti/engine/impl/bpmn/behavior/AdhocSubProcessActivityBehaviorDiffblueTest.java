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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AdhocSubProcessActivityBehaviorDiffblueTest {
  /**
   * Test {@link AdhocSubProcessActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link AdhocSubProcessActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AdhocSubProcessActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_thenThrowActivitiException() {
    // Arrange
    AdhocSubProcessActivityBehavior adhocSubProcessActivityBehavior =
        new AdhocSubProcessActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            adhocSubProcessActivityBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link AdhocSubProcessActivityBehavior#getSubProcessFromExecution(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AdhocSubProcessActivityBehavior#getSubProcessFromExecution(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.SubProcess AdhocSubProcessActivityBehavior.getSubProcessFromExecution(DelegateExecution)"
  })
  public void testGetSubProcessFromExecution_thenThrowActivitiException() {
    // Arrange
    AdhocSubProcessActivityBehavior adhocSubProcessActivityBehavior =
        new AdhocSubProcessActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            adhocSubProcessActivityBehavior.getSubProcessFromExecution(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link AdhocSubProcessActivityBehavior#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BooleanDataObject} (default constructor).
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link AdhocSubProcessActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map AdhocSubProcessActivityBehavior.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenArrayListAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    AdhocSubProcessActivityBehavior adhocSubProcessActivityBehavior =
        new AdhocSubProcessActivityBehavior();

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult =
        adhocSubProcessActivityBehavior.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test {@link AdhocSubProcessActivityBehavior#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link AdhocSubProcessActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map AdhocSubProcessActivityBehavior.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenArrayList_thenReturnEmpty() {
    // Arrange
    AdhocSubProcessActivityBehavior adhocSubProcessActivityBehavior =
        new AdhocSubProcessActivityBehavior();

    // Act and Assert
    assertTrue(adhocSubProcessActivityBehavior.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AdhocSubProcessActivityBehavior#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link BooleanDataObject} (default
   *       constructor).
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link AdhocSubProcessActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map AdhocSubProcessActivityBehavior.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenLinkedHashSetAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    AdhocSubProcessActivityBehavior adhocSubProcessActivityBehavior =
        new AdhocSubProcessActivityBehavior();

    LinkedHashSet<ValuedDataObject> dataObjects = new LinkedHashSet<>();
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult =
        adhocSubProcessActivityBehavior.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test {@link AdhocSubProcessActivityBehavior#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link AdhocSubProcessActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map AdhocSubProcessActivityBehavior.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new AdhocSubProcessActivityBehavior().processDataObjects(null).isEmpty());
  }

  /**
   * Test new {@link AdhocSubProcessActivityBehavior} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * AdhocSubProcessActivityBehavior}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AdhocSubProcessActivityBehavior.<init>()"})
  public void testNewAdhocSubProcessActivityBehavior() {
    // Arrange and Act
    AdhocSubProcessActivityBehavior actualAdhocSubProcessActivityBehavior =
        new AdhocSubProcessActivityBehavior();

    // Assert
    assertNull(actualAdhocSubProcessActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualAdhocSubProcessActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualAdhocSubProcessActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
