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

public class SubProcessActivityBehaviorDiffblueTest {
  /**
   * Test {@link SubProcessActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link SubProcessActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_thenThrowActivitiException() {
    // Arrange
    SubProcessActivityBehavior subProcessActivityBehavior = new SubProcessActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            subProcessActivityBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link SubProcessActivityBehavior#getSubProcessFromExecution(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SubProcessActivityBehavior#getSubProcessFromExecution(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.bpmn.model.SubProcess SubProcessActivityBehavior.getSubProcessFromExecution(DelegateExecution)"
  })
  public void testGetSubProcessFromExecution_thenThrowActivitiException() {
    // Arrange
    SubProcessActivityBehavior subProcessActivityBehavior = new SubProcessActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            subProcessActivityBehavior.getSubProcessFromExecution(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link SubProcessActivityBehavior#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BooleanDataObject} (default constructor).
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcessActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map SubProcessActivityBehavior.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenArrayListAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    SubProcessActivityBehavior subProcessActivityBehavior = new SubProcessActivityBehavior();

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult =
        subProcessActivityBehavior.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test {@link SubProcessActivityBehavior#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link SubProcessActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map SubProcessActivityBehavior.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenArrayList_thenReturnEmpty() {
    // Arrange
    SubProcessActivityBehavior subProcessActivityBehavior = new SubProcessActivityBehavior();

    // Act and Assert
    assertTrue(subProcessActivityBehavior.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link SubProcessActivityBehavior#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link BooleanDataObject} (default
   *       constructor).
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link SubProcessActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map SubProcessActivityBehavior.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenLinkedHashSetAddBooleanDataObject_thenReturnSizeIsOne() {
    // Arrange
    SubProcessActivityBehavior subProcessActivityBehavior = new SubProcessActivityBehavior();

    LinkedHashSet<ValuedDataObject> dataObjects = new LinkedHashSet<>();
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult =
        subProcessActivityBehavior.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Test {@link SubProcessActivityBehavior#processDataObjects(Collection)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link SubProcessActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map SubProcessActivityBehavior.processDataObjects(Collection)"})
  public void testProcessDataObjects_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new SubProcessActivityBehavior().processDataObjects(null).isEmpty());
  }

  /**
   * Test new {@link SubProcessActivityBehavior} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * SubProcessActivityBehavior}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubProcessActivityBehavior.<init>()"})
  public void testNewSubProcessActivityBehavior() {
    // Arrange and Act
    SubProcessActivityBehavior actualSubProcessActivityBehavior = new SubProcessActivityBehavior();

    // Assert
    assertNull(actualSubProcessActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualSubProcessActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualSubProcessActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
