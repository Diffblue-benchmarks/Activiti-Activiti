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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.el.ParsingElContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class VariableScopeImplDiffblueTest {
  /**
   * Test {@link VariableScopeImpl#ensureVariableInstancesInitialized()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#ensureVariableInstancesInitialized()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.ensureVariableInstancesInitialized()"})
  public void testEnsureVariableInstancesInitialized_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).ensureVariableInstancesInitialized());
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_given42_thenReturnSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariables = createWithEmptyRelationshipCollectionsResult.getVariables(variableNames,
        true);

    // Assert
    assertEquals(2, actualVariables.size());
    assertNull(actualVariables.get("42"));
    assertNull(actualVariables.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_givenFoo_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariables = createWithEmptyRelationshipCollectionsResult.getVariables(variableNames,
        true);

    // Assert
    assertEquals(1, actualVariables.size());
    assertNull(actualVariables.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_thenReturnEmpty2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariables = createWithEmptyRelationshipCollectionsResult.getVariables(variableNames,
        true);

    // Assert
    assertEquals(1, actualVariables.size());
    assertNull(actualVariables.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariables(new ArrayList<>(), true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_given42_whenArrayListAdd42_thenReturnSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariables = createWithEmptyRelationshipCollectionsResult.getVariables(variableNames);

    // Assert
    assertEquals(2, actualVariables.size());
    assertNull(actualVariables.get("42"));
    assertNull(actualVariables.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return {@code foo} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_givenFoo_thenReturnFooIsNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariables = createWithEmptyRelationshipCollectionsResult.getVariables(variableNames);

    // Assert
    assertEquals(1, actualVariables.size());
    assertNull(actualVariables.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return containsKey empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_thenReturnContainsKeyEmptyString() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("");

    // Act
    Map<String, Object> actualVariables = createWithEmptyRelationshipCollectionsResult.getVariables(variableNames);

    // Assert
    assertEquals(1, actualVariables.size());
    assertTrue(actualVariables.containsKey(""));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_thenReturnEmpty2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return empty string is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_thenReturnEmptyStringIsNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("");

    // Act
    Map<String, Object> actualVariables = createWithEmptyRelationshipCollectionsResult.getVariables(variableNames);

    // Assert
    assertEquals(1, actualVariables.size());
    assertNull(actualVariables.get(""));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariables(new ArrayList<>()));
  }

  /**
   * Test {@link VariableScopeImpl#getVariables()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables()"})
  public void testGetVariables_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariables().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariables()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables()"})
  public void testGetVariables_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariables());
  }

  /**
   * Test {@link VariableScopeImpl#getVariables()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariables()"})
  public void testGetVariables_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    Map<String, Object> actualVariables = createWithEmptyRelationshipCollectionsResult.getVariables();

    // Assert
    assertEquals(1, actualVariables.size());
    assertTrue(actualVariables.containsKey("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances()}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances()"})
  public void testGetVariableInstances() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertEquals(createWithEmptyRelationshipCollectionsResult.transientVariabes,
        createWithEmptyRelationshipCollectionsResult.getVariableInstances());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection)} with {@code variableNames}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("");

    // Act and Assert
    assertEquals(createWithEmptyRelationshipCollectionsResult.transientVariabes,
        createWithEmptyRelationshipCollectionsResult.getVariableInstances(variableNames));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstances(new ArrayList<>(), true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables_thenReturnEmpty2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances(variableNames, true);

    // Assert
    assertEquals(1, actualVariableInstances.size());
    assertNull(actualVariableInstances.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables_thenReturnSizeIsOne2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances(variableNames, true);

    // Assert
    assertEquals(1, actualVariableInstances.size());
    assertNull(actualVariableInstances.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables_thenReturnSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances(variableNames, true);

    // Assert
    assertEquals(2, actualVariableInstances.size());
    assertNull(actualVariableInstances.get("42"));
    assertNull(actualVariableInstances.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_given42_thenReturnSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances(variableNames);

    // Assert
    assertEquals(2, actualVariableInstances.size());
    assertNull(actualVariableInstances.get("42"));
    assertNull(actualVariableInstances.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_givenFoo_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances(variableNames);

    // Assert
    assertEquals(1, actualVariableInstances.size());
    assertNull(actualVariableInstances.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_thenReturnEmpty2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return empty string is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_thenReturnEmptyStringIsNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("");

    // Act
    Map<String, VariableInstance> actualVariableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances(variableNames);

    // Assert
    assertEquals(1, actualVariableInstances.size());
    assertNull(actualVariableInstances.get(""));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstances(new ArrayList<>()));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances()"})
  public void testGetVariableInstances_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstances());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstances()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstances()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstances()"})
  public void testGetVariableInstances_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstances().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#collectVariables(HashMap)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariables(HashMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.collectVariables(HashMap)"})
  public void testCollectVariables_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.collectVariables(new HashMap<>()));
  }

  /**
   * Test {@link VariableScopeImpl#collectVariables(HashMap)}.
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariables(HashMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.collectVariables(HashMap)"})
  public void testCollectVariables_thenHashMapEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    Map<String, Object> actualCollectVariablesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariables(variables);

    // Assert
    assertTrue(variables.isEmpty());
    assertTrue(actualCollectVariablesResult.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#collectVariables(HashMap)}.
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariables(HashMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.collectVariables(HashMap)"})
  public void testCollectVariables_thenHashMapSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    Map<String, Object> actualCollectVariablesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariables(variables);

    // Assert
    assertEquals(1, variables.size());
    assertTrue(variables.containsKey("Variable Name"));
    assertEquals(variables, createWithEmptyRelationshipCollectionsResult.getTransientVariables());
    assertEquals(variables, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal());
    assertEquals(variables, createWithEmptyRelationshipCollectionsResult.getVariables());
    assertEquals(variables, createWithEmptyRelationshipCollectionsResult.getVariablesLocal());
    assertSame(variables, actualCollectVariablesResult);
  }

  /**
   * Test {@link VariableScopeImpl#collectVariableInstances(HashMap)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariableInstances(HashMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.collectVariableInstances(HashMap)"})
  public void testCollectVariableInstances_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.collectVariableInstances(new HashMap<>()));
  }

  /**
   * Test {@link VariableScopeImpl#collectVariableInstances(HashMap)}.
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariableInstances(HashMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.collectVariableInstances(HashMap)"})
  public void testCollectVariableInstances_thenHashMapEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    HashMap<String, VariableInstance> variables = new HashMap<>();

    // Act
    Map<String, VariableInstance> actualCollectVariableInstancesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariableInstances(variables);

    // Assert
    assertTrue(variables.isEmpty());
    assertTrue(actualCollectVariableInstancesResult.isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#collectVariableInstances(HashMap)}.
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariableInstances(HashMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.collectVariableInstances(HashMap)"})
  public void testCollectVariableInstances_thenHashMapSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);
    HashMap<String, VariableInstance> variables = new HashMap<>();

    // Act
    Map<String, VariableInstance> actualCollectVariableInstancesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariableInstances(variables);

    // Assert
    assertEquals(1, variables.size());
    assertEquals(1, actualCollectVariableInstancesResult.size());
    assertTrue(actualCollectVariableInstancesResult.get("Variable Name") instanceof TransientVariableInstance);
    assertTrue(variables.containsKey("Variable Name"));
    assertSame(variables, actualCollectVariableInstancesResult);
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String)"})
  public void testGetVariableWithVariableName() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String, boolean)"})
  public void testGetVariableWithVariableNameFetchAllVariables() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariable("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String, boolean)"})
  public void testGetVariableWithVariableNameFetchAllVariables_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariable("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String, boolean)"})
  public void testGetVariableWithVariableNameFetchAllVariables_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariable("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String, Class)} with {@code variableName}, {@code variableClass}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String, Class)"})
  public void testGetVariableWithVariableNameVariableClass() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariable("Variable Name", variableClass));
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String, Class)} with {@code variableName}, {@code variableClass}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String, Class)"})
  public void testGetVariableWithVariableNameVariableClass_thenReturnNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariable("Variable Name", variableClass));
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String, Class)} with {@code variableName}, {@code variableClass}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String, Class)"})
  public void testGetVariableWithVariableNameVariableClass_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariable("Variable Name", variableClass));
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String)} with {@code variableName}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String)"})
  public void testGetVariableWithVariableName_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariable(String)} with {@code variableName}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariable(String)"})
  public void testGetVariableWithVariableName_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstance(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstance(String)"})
  public void testGetVariableInstanceWithVariableName() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstance("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstance(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstance(String)"})
  public void testGetVariableInstanceWithVariableName2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariableInstance("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstance(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstance(String, boolean)"})
  public void testGetVariableInstanceWithVariableNameFetchAllVariables() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstance("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstance(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstance(String, boolean)"})
  public void testGetVariableInstanceWithVariableNameFetchAllVariables2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    VariableInstance actualVariableInstance = createWithEmptyRelationshipCollectionsResult
        .getVariableInstance("Variable Name", true);

    // Assert
    assertTrue(actualVariableInstance instanceof TransientVariableInstance);
    assertEquals("Variable Name", actualVariableInstance.getName());
    assertEquals("transient", actualVariableInstance.getTypeName());
    assertNull(actualVariableInstance.getBytes());
    assertNull(actualVariableInstance.getDoubleValue());
    assertNull(actualVariableInstance.getLongValue());
    assertNull(actualVariableInstance.getPersistentState());
    assertNull(actualVariableInstance.getCachedValue());
    assertNull(actualVariableInstance.getId());
    assertNull(actualVariableInstance.getExecutionId());
    assertNull(actualVariableInstance.getProcessInstanceId());
    assertNull(actualVariableInstance.getTaskId());
    assertNull(actualVariableInstance.getTextValue());
    assertNull(actualVariableInstance.getTextValue2());
    assertEquals(0, actualVariableInstance.getRevision());
    assertEquals(0, actualVariableInstance.getRevisionNext());
    assertFalse(actualVariableInstance.isDeleted());
    assertFalse(actualVariableInstance.isInserted());
    assertFalse(actualVariableInstance.isUpdated());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstance(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstance(String, boolean)"})
  public void testGetVariableInstanceWithVariableNameFetchAllVariables3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariableInstance("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstance(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstance(String, boolean)"})
  public void testGetVariableInstanceWithVariableNameFetchAllVariables_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstance("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstance(String)} with {@code variableName}.
   * <ul>
   *   <li>Then return {@link TransientVariableInstance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstance(String)"})
  public void testGetVariableInstanceWithVariableName_thenReturnTransientVariableInstance() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    VariableInstance actualVariableInstance = createWithEmptyRelationshipCollectionsResult
        .getVariableInstance("Variable Name");

    // Assert
    assertTrue(actualVariableInstance instanceof TransientVariableInstance);
    assertEquals("Variable Name", actualVariableInstance.getName());
    assertEquals("transient", actualVariableInstance.getTypeName());
    assertNull(actualVariableInstance.getBytes());
    assertNull(actualVariableInstance.getDoubleValue());
    assertNull(actualVariableInstance.getLongValue());
    assertNull(actualVariableInstance.getPersistentState());
    assertNull(actualVariableInstance.getCachedValue());
    assertNull(actualVariableInstance.getId());
    assertNull(actualVariableInstance.getExecutionId());
    assertNull(actualVariableInstance.getProcessInstanceId());
    assertNull(actualVariableInstance.getTaskId());
    assertNull(actualVariableInstance.getTextValue());
    assertNull(actualVariableInstance.getTextValue2());
    assertEquals(0, actualVariableInstance.getRevision());
    assertEquals(0, actualVariableInstance.getRevisionNext());
    assertFalse(actualVariableInstance.isDeleted());
    assertFalse(actualVariableInstance.isInserted());
    assertFalse(actualVariableInstance.isUpdated());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstance(String)} with {@code variableName}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstance(String)"})
  public void testGetVariableInstanceWithVariableName_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstance("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String)"})
  public void testGetVariableLocalWithVariableName() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String, boolean)"})
  public void testGetVariableLocalWithVariableNameFetchAllVariables() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariableLocal("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String, boolean)"})
  public void testGetVariableLocalWithVariableNameFetchAllVariables_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableLocal("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String, boolean)"})
  public void testGetVariableLocalWithVariableNameFetchAllVariables_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableLocal("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String, Class)} with {@code variableName}, {@code variableClass}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String, Class)"})
  public void testGetVariableLocalWithVariableNameVariableClass() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariableLocal("Variable Name", variableClass));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String, Class)} with {@code variableName}, {@code variableClass}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String, Class)"})
  public void testGetVariableLocalWithVariableNameVariableClass_thenReturnNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariableLocal("Variable Name", variableClass));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String, Class)} with {@code variableName}, {@code variableClass}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String, Class)"})
  public void testGetVariableLocalWithVariableNameVariableClass_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableLocal("Variable Name", variableClass));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String)} with {@code variableName}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String)"})
  public void testGetVariableLocalWithVariableName_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableLocal(String)} with {@code variableName}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getVariableLocal(String)"})
  public void testGetVariableLocalWithVariableName_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceLocal(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstanceLocal(String)"})
  public void testGetVariableInstanceLocalWithVariableName() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstanceLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceLocal(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstanceLocal(String)"})
  public void testGetVariableInstanceLocalWithVariableName2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariableInstanceLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstanceLocal(String, boolean)"})
  public void testGetVariableInstanceLocalWithVariableNameFetchAllVariables() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new ExecutionEntityImpl()).getVariableInstanceLocal("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstanceLocal(String, boolean)"})
  public void testGetVariableInstanceLocalWithVariableNameFetchAllVariables2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    VariableInstance actualVariableInstanceLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstanceLocal("Variable Name", true);

    // Assert
    assertTrue(actualVariableInstanceLocal instanceof TransientVariableInstance);
    assertEquals("Variable Name", actualVariableInstanceLocal.getName());
    assertEquals("transient", actualVariableInstanceLocal.getTypeName());
    assertNull(actualVariableInstanceLocal.getBytes());
    assertNull(actualVariableInstanceLocal.getDoubleValue());
    assertNull(actualVariableInstanceLocal.getLongValue());
    assertNull(actualVariableInstanceLocal.getPersistentState());
    assertNull(actualVariableInstanceLocal.getCachedValue());
    assertNull(actualVariableInstanceLocal.getId());
    assertNull(actualVariableInstanceLocal.getExecutionId());
    assertNull(actualVariableInstanceLocal.getProcessInstanceId());
    assertNull(actualVariableInstanceLocal.getTaskId());
    assertNull(actualVariableInstanceLocal.getTextValue());
    assertNull(actualVariableInstanceLocal.getTextValue2());
    assertEquals(0, actualVariableInstanceLocal.getRevision());
    assertEquals(0, actualVariableInstanceLocal.getRevisionNext());
    assertFalse(actualVariableInstanceLocal.isDeleted());
    assertFalse(actualVariableInstanceLocal.isInserted());
    assertFalse(actualVariableInstanceLocal.isUpdated());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstanceLocal(String, boolean)"})
  public void testGetVariableInstanceLocalWithVariableNameFetchAllVariables3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getVariableInstanceLocal("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstanceLocal(String, boolean)"})
  public void testGetVariableInstanceLocalWithVariableNameFetchAllVariables_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstanceLocal("Variable Name", true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceLocal(String)} with {@code variableName}.
   * <ul>
   *   <li>Then return {@link TransientVariableInstance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstanceLocal(String)"})
  public void testGetVariableInstanceLocalWithVariableName_thenReturnTransientVariableInstance() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    VariableInstance actualVariableInstanceLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstanceLocal("Variable Name");

    // Assert
    assertTrue(actualVariableInstanceLocal instanceof TransientVariableInstance);
    assertEquals("Variable Name", actualVariableInstanceLocal.getName());
    assertEquals("transient", actualVariableInstanceLocal.getTypeName());
    assertNull(actualVariableInstanceLocal.getBytes());
    assertNull(actualVariableInstanceLocal.getDoubleValue());
    assertNull(actualVariableInstanceLocal.getLongValue());
    assertNull(actualVariableInstanceLocal.getPersistentState());
    assertNull(actualVariableInstanceLocal.getCachedValue());
    assertNull(actualVariableInstanceLocal.getId());
    assertNull(actualVariableInstanceLocal.getExecutionId());
    assertNull(actualVariableInstanceLocal.getProcessInstanceId());
    assertNull(actualVariableInstanceLocal.getTaskId());
    assertNull(actualVariableInstanceLocal.getTextValue());
    assertNull(actualVariableInstanceLocal.getTextValue2());
    assertEquals(0, actualVariableInstanceLocal.getRevision());
    assertEquals(0, actualVariableInstanceLocal.getRevisionNext());
    assertFalse(actualVariableInstanceLocal.isDeleted());
    assertFalse(actualVariableInstanceLocal.isInserted());
    assertFalse(actualVariableInstanceLocal.isUpdated());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceLocal(String)} with {@code variableName}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance VariableScopeImpl.getVariableInstanceLocal(String)"})
  public void testGetVariableInstanceLocalWithVariableName_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstanceLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#hasVariables()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariables()"})
  public void testHasVariables_givenCreateWithEmptyRelationshipCollections_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().hasVariables());
  }

  /**
   * Test {@link VariableScopeImpl#hasVariables()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariables()"})
  public void testHasVariables_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).hasVariables());
  }

  /**
   * Test {@link VariableScopeImpl#hasVariables()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariables()"})
  public void testHasVariables_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.hasVariables());
  }

  /**
   * Test {@link VariableScopeImpl#hasVariablesLocal()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariablesLocal()"})
  public void testHasVariablesLocal_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).hasVariablesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#hasVariablesLocal()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariablesLocal()"})
  public void testHasVariablesLocal_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().hasVariablesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#hasVariablesLocal()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariablesLocal()"})
  public void testHasVariablesLocal_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.hasVariablesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#hasVariable(String)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariable(String)"})
  public void testHasVariable() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertFalse(createWithEmptyRelationshipCollectionsResult.hasVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#hasVariable(String)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariable(String)"})
  public void testHasVariable_givenCreateWithEmptyRelationshipCollections_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().hasVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#hasVariable(String)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariable(String)"})
  public void testHasVariable_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).hasVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#hasVariable(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariable(String)"})
  public void testHasVariable_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.hasVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#hasVariableLocal(String)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariableLocal(String)"})
  public void testHasVariableLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertFalse(createWithEmptyRelationshipCollectionsResult.hasVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#hasVariableLocal(String)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariableLocal(String)"})
  public void testHasVariableLocal_givenCreateWithEmptyRelationshipCollections_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().hasVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#hasVariableLocal(String)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariableLocal(String)"})
  public void testHasVariableLocal_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).hasVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#hasVariableLocal(String)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#hasVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.hasVariableLocal(String)"})
  public void testHasVariableLocal_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.hasVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#collectVariableNames(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then {@link HashSet#HashSet()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.collectVariableNames(Set)"})
  public void testCollectVariableNames_given42_whenHashSetAdd42_thenHashSetSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashSet<String> variableNames = new HashSet<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Set<String> actualCollectVariableNamesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariableNames(variableNames);

    // Assert
    assertEquals(2, variableNames.size());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
    assertSame(variableNames, actualCollectVariableNamesResult);
  }

  /**
   * Test {@link VariableScopeImpl#collectVariableNames(Set)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.collectVariableNames(Set)"})
  public void testCollectVariableNames_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.collectVariableNames(new HashSet<>()));
  }

  /**
   * Test {@link VariableScopeImpl#collectVariableNames(Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   *   <li>Then {@link HashSet#HashSet()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.collectVariableNames(Set)"})
  public void testCollectVariableNames_givenFoo_whenHashSetAddFoo_thenHashSetSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashSet<String> variableNames = new HashSet<>();
    variableNames.add("foo");

    // Act
    Set<String> actualCollectVariableNamesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariableNames(variableNames);

    // Assert
    assertEquals(1, variableNames.size());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
    assertSame(variableNames, actualCollectVariableNamesResult);
  }

  /**
   * Test {@link VariableScopeImpl#collectVariableNames(Set)}.
   * <ul>
   *   <li>Then {@link HashSet#HashSet()} contains {@code Variable Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.collectVariableNames(Set)"})
  public void testCollectVariableNames_thenHashSetContainsVariableName() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);
    HashSet<String> variableNames = new HashSet<>();

    // Act
    createWithEmptyRelationshipCollectionsResult.collectVariableNames(variableNames);

    // Assert
    assertEquals(1, variableNames.size());
    assertTrue(variableNames.contains("Variable Name"));
    assertEquals(variableNames, createWithEmptyRelationshipCollectionsResult.getVariableNames());
    assertEquals(variableNames, createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#collectVariableNames(Set)}.
   * <ul>
   *   <li>Then {@link HashSet#HashSet()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.collectVariableNames(Set)"})
  public void testCollectVariableNames_thenHashSetEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    HashSet<String> variableNames = new HashSet<>();

    // Act
    Set<String> actualCollectVariableNamesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariableNames(variableNames);

    // Assert
    assertTrue(variableNames.isEmpty());
    assertTrue(actualCollectVariableNamesResult.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableNames()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableNames()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.getVariableNames()"})
  public void testGetVariableNames_givenCreateWithEmptyRelationshipCollections_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableNames().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableNames()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableNames()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.getVariableNames()"})
  public void testGetVariableNames_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableNames());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableNames()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableNames()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.getVariableNames()"})
  public void testGetVariableNames_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    Set<String> actualVariableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();

    // Assert
    assertEquals(1, actualVariableNames.size());
    assertTrue(actualVariableNames.contains("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariablesLocal(new ArrayList<>(), true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("");

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariablesLocal(variableNames, true);

    // Assert
    assertEquals(1, actualVariablesLocal.size());
    assertNull(actualVariablesLocal.get(""));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("");

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariablesLocal(variableNames, true);

    // Assert
    assertEquals(1, actualVariablesLocal.size());
    assertTrue(actualVariablesLocal.containsKey(""));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables_thenReturnEmpty2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return {@code foo} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables_thenReturnFooIsNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariablesLocal(variableNames, true);

    // Assert
    assertEquals(1, actualVariablesLocal.size());
    assertNull(actualVariablesLocal.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables_thenReturnSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariablesLocal(variableNames, true);

    // Assert
    assertEquals(2, actualVariablesLocal.size());
    assertNull(actualVariablesLocal.get("42"));
    assertNull(actualVariablesLocal.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return {@code foo} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_givenFoo_thenReturnFooIsNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariablesLocal(variableNames);

    // Assert
    assertEquals(1, actualVariablesLocal.size());
    assertNull(actualVariablesLocal.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_givenFoo_thenReturnSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariablesLocal(variableNames);

    // Assert
    assertEquals(2, actualVariablesLocal.size());
    assertNull(actualVariablesLocal.get("42"));
    assertNull(actualVariablesLocal.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return {@code 42} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_thenReturn42IsNull() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariablesLocal(variableNames);

    // Assert
    assertEquals(1, actualVariablesLocal.size());
    assertNull(actualVariablesLocal.get("42"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return containsKey {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_thenReturnContainsKey42() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("42", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariablesLocal(variableNames);

    // Assert
    assertEquals(1, actualVariablesLocal.size());
    assertTrue(actualVariablesLocal.containsKey("42"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_thenReturnEmpty2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariablesLocal(new ArrayList<>()));
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal()"})
  public void testGetVariablesLocal_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariablesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal()"})
  public void testGetVariablesLocal_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariablesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariablesLocal()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariablesLocal()"})
  public void testGetVariablesLocal_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    Map<String, Object> actualVariablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();

    // Assert
    assertEquals(1, actualVariablesLocal.size());
    assertTrue(actualVariablesLocal.containsKey("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal()}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal()"})
  public void testGetVariableInstancesLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertEquals(createWithEmptyRelationshipCollectionsResult.transientVariabes,
        createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("42", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");

    // Act and Assert
    assertEquals(createWithEmptyRelationshipCollectionsResult.transientVariabes,
        createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(variableNames));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstancesLocal(new ArrayList<>(), true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal(variableNames, true);

    // Assert
    assertEquals(1, actualVariableInstancesLocal.size());
    assertNull(actualVariableInstancesLocal.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal(variableNames, true);

    // Assert
    assertEquals(2, actualVariableInstancesLocal.size());
    assertNull(actualVariableInstancesLocal.get("42"));
    assertNull(actualVariableInstancesLocal.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables4() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("");

    // Act
    Map<String, VariableInstance> actualVariableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal(variableNames, true);

    // Assert
    assertEquals(1, actualVariableInstancesLocal.size());
    assertNull(actualVariableInstancesLocal.get(""));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables5() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("");

    // Act and Assert
    assertEquals(createWithEmptyRelationshipCollectionsResult.transientVariabes,
        createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(variableNames, true));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(
        createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables_thenReturnEmpty2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(
        createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_givenFoo_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal(variableNames);

    // Assert
    assertEquals(1, actualVariableInstancesLocal.size());
    assertNull(actualVariableInstancesLocal.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_givenFoo_thenReturnSizeIsTwo() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act
    Map<String, VariableInstance> actualVariableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal(variableNames);

    // Assert
    assertEquals(2, actualVariableInstancesLocal.size());
    assertNull(actualVariableInstancesLocal.get("42"));
    assertNull(actualVariableInstancesLocal.get("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_thenReturnEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_thenReturnEmpty2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");

    // Act
    Map<String, VariableInstance> actualVariableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal(variableNames);

    // Assert
    assertEquals(1, actualVariableInstancesLocal.size());
    assertNull(actualVariableInstancesLocal.get("42"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstancesLocal(new ArrayList<>()));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal()"})
  public void testGetVariableInstancesLocal_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstancesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstancesLocal()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstancesLocal()"})
  public void testGetVariableInstancesLocal_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstancesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableNamesLocal()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableNamesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.getVariableNamesLocal()"})
  public void testGetVariableNamesLocal_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableNamesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableNamesLocal()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableNamesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.getVariableNamesLocal()"})
  public void testGetVariableNamesLocal_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableNamesLocal()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableNamesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set VariableScopeImpl.getVariableNamesLocal()"})
  public void testGetVariableNamesLocal_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    Set<String> actualVariableNamesLocal = createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal();

    // Assert
    assertEquals(1, actualVariableNamesLocal.size());
    assertTrue(actualVariableNamesLocal.contains("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceEntities()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceEntities()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstanceEntities()"})
  public void testGetVariableInstanceEntities_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstanceEntities().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getVariableInstanceEntities()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getVariableInstanceEntities()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getVariableInstanceEntities()"})
  public void testGetVariableInstanceEntities_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstanceEntities());
  }

  /**
   * Test {@link VariableScopeImpl#getUsedVariablesCache()}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getUsedVariablesCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getUsedVariablesCache()"})
  public void testGetUsedVariablesCache() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getUsedVariablesCache().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#createVariablesLocal(Map)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#createVariablesLocal(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.createVariablesLocal(Map)"})
  public void testCreateVariablesLocal_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.createVariablesLocal(variables));
  }

  /**
   * Test {@link VariableScopeImpl#setVariables(Map)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariables(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariables(Map)"})
  public void testSetVariables_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariables(variables));
  }

  /**
   * Test {@link VariableScopeImpl#setVariablesLocal(Map)}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariablesLocal(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariablesLocal(Map)"})
  public void testSetVariablesLocal_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariablesLocal(variables));
  }

  /**
   * Test {@link VariableScopeImpl#removeVariables(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeVariables(Collection)"})
  public void testRemoveVariablesWithCollection_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.removeVariables(variableNames));
  }

  /**
   * Test {@link VariableScopeImpl#removeVariables()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeVariables()"})
  public void testRemoveVariables_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).removeVariables());
  }

  /**
   * Test {@link VariableScopeImpl#removeVariablesLocal(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeVariablesLocal(Collection)"})
  public void testRemoveVariablesLocalWithCollection_thenThrowActivitiException() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.removeVariablesLocal(variableNames));
  }

  /**
   * Test {@link VariableScopeImpl#removeVariablesLocal()}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeVariablesLocal()"})
  public void testRemoveVariablesLocal_givenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).removeVariablesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object)} with {@code variableName}, {@code value}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object)"})
  public void testSetVariableWithVariableNameValue() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();
    executionEntityImpl.setTransientVariableLocal("lazy loading outside command context", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariable("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object)} with {@code variableName}, {@code value}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object)"})
  public void testSetVariableWithVariableNameValue2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();
    executionEntityImpl.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariable("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object, boolean)} with {@code variableName}, {@code value}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object, boolean)"})
  public void testSetVariableWithVariableNameValueFetchAllVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();
    executionEntityImpl.setTransientVariableLocal("lazy loading outside command context", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> executionEntityImpl.setVariable("Variable Name", JSONObject.NULL, true));
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object, boolean)} with {@code variableName}, {@code value}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object, boolean)"})
  public void testSetVariableWithVariableNameValueFetchAllVariables2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();
    executionEntityImpl.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> executionEntityImpl.setVariable("Variable Name", JSONObject.NULL, true));
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object, boolean)} with {@code variableName}, {@code value}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object, boolean)"})
  public void testSetVariableWithVariableNameValueFetchAllVariables_givenExecutionEntityImpl() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new ExecutionEntityImpl()).setVariable("Variable Name", JSONObject.NULL, true));
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object, ExecutionEntity, boolean)} with {@code variableName}, {@code value}, {@code sourceExecution}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object, ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object, ExecutionEntity, boolean)"})
  public void testSetVariableWithVariableNameValueSourceExecutionFetchAllVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariable("Variable Name", JSONObject.NULL,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object, ExecutionEntity, boolean)} with {@code variableName}, {@code value}, {@code sourceExecution}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object, ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object, ExecutionEntity, boolean)"})
  public void testSetVariableWithVariableNameValueSourceExecutionFetchAllVariables2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();
    executionEntityImpl.setTransientVariableLocal("lazy loading outside command context", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariable("Variable Name", JSONObject.NULL,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object, ExecutionEntity, boolean)} with {@code variableName}, {@code value}, {@code sourceExecution}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object, ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object, ExecutionEntity, boolean)"})
  public void testSetVariableWithVariableNameValueSourceExecutionFetchAllVariables3() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();
    executionEntityImpl.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariable("Variable Name", JSONObject.NULL,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));
  }

  /**
   * Test {@link VariableScopeImpl#setVariable(String, Object)} with {@code variableName}, {@code value}.
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setVariable(String, Object)"})
  public void testSetVariableWithVariableNameValue_givenExecutionEntityImpl() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new ExecutionEntityImpl()).setVariable("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeImpl#setVariableLocal(String, Object, boolean)} with {@code variableName}, {@code value}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariableLocal(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.setVariableLocal(String, Object, boolean)"})
  public void testSetVariableLocalWithVariableNameValueFetchAllVariables() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new ExecutionEntityImpl()).setVariableLocal("Variable Name", JSONObject.NULL, true));
  }

  /**
   * Test {@link VariableScopeImpl#setVariableLocal(String, Object, ExecutionEntity, boolean)} with {@code variableName}, {@code value}, {@code sourceActivityExecution}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariableLocal(String, Object, ExecutionEntity, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.setVariableLocal(String, Object, ExecutionEntity, boolean)"})
  public void testSetVariableLocalWithVariableNameValueSourceActivityExecutionFetchAllVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariableLocal("Variable Name", JSONObject.NULL,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));
  }

  /**
   * Test {@link VariableScopeImpl#setVariableLocal(String, Object)} with {@code variableName}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#setVariableLocal(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.setVariableLocal(String, Object)"})
  public void testSetVariableLocalWithVariableNameValue_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new ExecutionEntityImpl()).setVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeImpl#createVariableLocal(String, Object, ExecutionEntity)} with {@code variableName}, {@code value}, {@code sourceActivityExecution}.
   * <p>
   * Method under test: {@link VariableScopeImpl#createVariableLocal(String, Object, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.createVariableLocal(String, Object, ExecutionEntity)"})
  public void testCreateVariableLocalWithVariableNameValueSourceActivityExecution() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.createVariableLocal("Variable Name",
        JSONObject.NULL, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link VariableScopeImpl#createVariableLocal(String, Object)} with {@code variableName}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#createVariableLocal(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.createVariableLocal(String, Object)"})
  public void testCreateVariableLocalWithVariableNameValue_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new ExecutionEntityImpl()).createVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link VariableScopeImpl#removeVariable(String, ExecutionEntity)} with {@code variableName}, {@code sourceActivityExecution}.
   * <p>
   * Method under test: {@link VariableScopeImpl#removeVariable(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeVariable(String, ExecutionEntity)"})
  public void testRemoveVariableWithVariableNameSourceActivityExecution() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.removeVariable("Variable Name",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link VariableScopeImpl#removeVariable(String)} with {@code variableName}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeVariable(String)"})
  public void testRemoveVariableWithVariableName_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).removeVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#removeVariableLocal(String, ExecutionEntity)} with {@code variableName}, {@code sourceActivityExecution}.
   * <p>
   * Method under test: {@link VariableScopeImpl#removeVariableLocal(String, ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeVariableLocal(String, ExecutionEntity)"})
  public void testRemoveVariableLocalWithVariableNameSourceActivityExecution() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.removeVariableLocal("Variable Name",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link VariableScopeImpl#removeVariableLocal(String)} with {@code variableName}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeVariableLocal(String)"})
  public void testRemoveVariableLocalWithVariableName_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).removeVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariablesLocal(Map)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariablesLocal(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariablesLocal(Map)"})
  public void testSetTransientVariablesLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariablesLocal(new HashMap<>());

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariablesLocal(Map)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariablesLocal(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariablesLocal(Map)"})
  public void testSetTransientVariablesLocal2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.put("foo", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariablesLocal(transientVariables);

    // Assert
    Map<String, Object> transientVariables2 = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables2.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(1, transientVariablesLocal.size());
    Map<String, VariableInstance> variableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances();
    assertEquals(1, variableInstances.size());
    Map<String, VariableInstance> variableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal();
    assertEquals(1, variableInstancesLocal.size());
    Map<String, Object> variables = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(1, variables.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(1, variablesLocal.size());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(1, stringVariableInstanceMap.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableNames().size());
    assertTrue(transientVariables2.containsKey("foo"));
    assertTrue(transientVariablesLocal.containsKey("foo"));
    assertTrue(variableInstances.containsKey("foo"));
    assertTrue(variableInstancesLocal.containsKey("foo"));
    assertTrue(variables.containsKey("foo"));
    assertTrue(variablesLocal.containsKey("foo"));
    assertTrue(stringVariableInstanceMap.containsKey("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariablesLocal(Map)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariablesLocal(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariablesLocal(Map)"})
  public void testSetTransientVariablesLocal3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.put("42", JSONObject.NULL);
    transientVariables.put("foo", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariablesLocal(transientVariables);

    // Assert
    Map<String, VariableInstance> variableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances();
    assertEquals(2, variableInstances.size());
    VariableInstance getResult = variableInstances.get("42");
    assertTrue(getResult instanceof TransientVariableInstance);
    Map<String, Object> transientVariables2 = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(2, transientVariables2.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(2, transientVariablesLocal.size());
    Map<String, VariableInstance> variableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal();
    assertEquals(2, variableInstancesLocal.size());
    Map<String, Object> variables = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(2, variables.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(2, variablesLocal.size());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(2, stringVariableInstanceMap.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(2, variableNames.size());
    assertTrue(transientVariables2.containsKey("42"));
    assertTrue(transientVariables2.containsKey("foo"));
    assertTrue(transientVariablesLocal.containsKey("42"));
    assertTrue(transientVariablesLocal.containsKey("foo"));
    assertTrue(variableInstances.containsKey("foo"));
    assertTrue(variableInstancesLocal.containsKey("foo"));
    assertTrue(variables.containsKey("42"));
    assertTrue(variables.containsKey("foo"));
    assertTrue(variablesLocal.containsKey("42"));
    assertTrue(variablesLocal.containsKey("foo"));
    assertTrue(stringVariableInstanceMap.containsKey("foo"));
    assertTrue(variableNames.contains("42"));
    assertSame(getResult, variableInstancesLocal.get("42"));
    assertSame(getResult, stringVariableInstanceMap.get("42"));
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariableLocal(String, Object)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariableLocal(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariableLocal(String, Object)"})
  public void testSetTransientVariableLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    Object object = JSONObject.NULL;

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", object);

    // Assert
    Map<String, VariableInstance> variableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances();
    assertEquals(1, variableInstances.size());
    VariableInstance getResult = variableInstances.get("Variable Name");
    assertTrue(getResult instanceof TransientVariableInstance);
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, VariableInstance> variableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal();
    assertEquals(1, variableInstancesLocal.size());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(1, stringVariableInstanceMap.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    assertTrue(variableNames.contains("Variable Name"));
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariables());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariablesLocal());
    assertSame(object, transientVariables.get("Variable Name"));
    assertSame(getResult, variableInstancesLocal.get("Variable Name"));
    assertSame(getResult, stringVariableInstanceMap.get("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariables(Map)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariables(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariables(Map)"})
  public void testSetTransientVariables() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariables(new HashMap<>());

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariables(Map)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariables(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariables(Map)"})
  public void testSetTransientVariables2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.put("foo", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariables(transientVariables);

    // Assert
    Map<String, Object> transientVariables2 = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables2.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(1, transientVariablesLocal.size());
    Map<String, VariableInstance> variableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances();
    assertEquals(1, variableInstances.size());
    Map<String, VariableInstance> variableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal();
    assertEquals(1, variableInstancesLocal.size());
    Map<String, Object> variables = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(1, variables.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(1, variablesLocal.size());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(1, stringVariableInstanceMap.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableNames().size());
    assertTrue(transientVariables2.containsKey("foo"));
    assertTrue(transientVariablesLocal.containsKey("foo"));
    assertTrue(variableInstances.containsKey("foo"));
    assertTrue(variableInstancesLocal.containsKey("foo"));
    assertTrue(variables.containsKey("foo"));
    assertTrue(variablesLocal.containsKey("foo"));
    assertTrue(stringVariableInstanceMap.containsKey("foo"));
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariables(Map)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariables(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariables(Map)"})
  public void testSetTransientVariables3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.put("42", JSONObject.NULL);
    transientVariables.put("foo", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariables(transientVariables);

    // Assert
    Map<String, VariableInstance> variableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances();
    assertEquals(2, variableInstances.size());
    VariableInstance getResult = variableInstances.get("42");
    assertTrue(getResult instanceof TransientVariableInstance);
    Map<String, Object> transientVariables2 = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(2, transientVariables2.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(2, transientVariablesLocal.size());
    Map<String, VariableInstance> variableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal();
    assertEquals(2, variableInstancesLocal.size());
    Map<String, Object> variables = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(2, variables.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(2, variablesLocal.size());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(2, stringVariableInstanceMap.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(2, variableNames.size());
    assertTrue(transientVariables2.containsKey("42"));
    assertTrue(transientVariables2.containsKey("foo"));
    assertTrue(transientVariablesLocal.containsKey("42"));
    assertTrue(transientVariablesLocal.containsKey("foo"));
    assertTrue(variableInstances.containsKey("foo"));
    assertTrue(variableInstancesLocal.containsKey("foo"));
    assertTrue(variables.containsKey("42"));
    assertTrue(variables.containsKey("foo"));
    assertTrue(variablesLocal.containsKey("42"));
    assertTrue(variablesLocal.containsKey("foo"));
    assertTrue(stringVariableInstanceMap.containsKey("foo"));
    assertTrue(variableNames.contains("42"));
    assertSame(getResult, variableInstancesLocal.get("42"));
    assertSame(getResult, stringVariableInstanceMap.get("42"));
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariable(String, Object)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariable(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariable(String, Object)"})
  public void testSetTransientVariable() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);
    Object object = JSONObject.NULL;

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariable("Variable Name", object);

    // Assert that nothing has changed
    Map<String, VariableInstance> variableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances();
    assertEquals(1, variableInstances.size());
    VariableInstance getResult = variableInstances.get("Variable Name");
    assertTrue(getResult instanceof TransientVariableInstance);
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, VariableInstance> variableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal();
    assertEquals(1, variableInstancesLocal.size());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(1, stringVariableInstanceMap.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    assertTrue(variableNames.contains("Variable Name"));
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariables());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariablesLocal());
    assertSame(object, transientVariables.get("Variable Name"));
    assertSame(getResult, variableInstancesLocal.get("Variable Name"));
    assertSame(getResult, stringVariableInstanceMap.get("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#setTransientVariable(String, Object)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#setTransientVariable(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setTransientVariable(String, Object)"})
  public void testSetTransientVariable_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    Object object = JSONObject.NULL;

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariable("Variable Name", object);

    // Assert
    Map<String, VariableInstance> variableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances();
    assertEquals(1, variableInstances.size());
    VariableInstance getResult = variableInstances.get("Variable Name");
    assertTrue(getResult instanceof TransientVariableInstance);
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, VariableInstance> variableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal();
    assertEquals(1, variableInstancesLocal.size());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(1, stringVariableInstanceMap.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    assertTrue(variableNames.contains("Variable Name"));
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariables());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariablesLocal());
    assertSame(object, transientVariables.get("Variable Name"));
    assertSame(getResult, variableInstancesLocal.get("Variable Name"));
    assertSame(getResult, stringVariableInstanceMap.get("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getTransientVariableLocal(String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getTransientVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getTransientVariableLocal(String)"})
  public void testGetTransientVariableLocal_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTransientVariableLocal("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getTransientVariablesLocal()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getTransientVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getTransientVariablesLocal()"})
  public void testGetTransientVariablesLocal_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTransientVariablesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getTransientVariablesLocal()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getTransientVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getTransientVariablesLocal()"})
  public void testGetTransientVariablesLocal_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    Map<String, Object> actualTransientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();

    // Assert
    assertEquals(1, actualTransientVariablesLocal.size());
    assertTrue(actualTransientVariablesLocal.containsKey("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getTransientVariable(String)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getTransientVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getTransientVariable(String)"})
  public void testGetTransientVariable() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getTransientVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getTransientVariable(String)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getTransientVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object VariableScopeImpl.getTransientVariable(String)"})
  public void testGetTransientVariable_givenCreateWithEmptyRelationshipCollections() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTransientVariable("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#getTransientVariables()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getTransientVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getTransientVariables()"})
  public void testGetTransientVariables_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTransientVariables().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#getTransientVariables()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#getTransientVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.getTransientVariables()"})
  public void testGetTransientVariables_thenReturnSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    Map<String, Object> actualTransientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();

    // Assert
    assertEquals(1, actualTransientVariables.size());
    assertTrue(actualTransientVariables.containsKey("Variable Name"));
  }

  /**
   * Test {@link VariableScopeImpl#collectTransientVariables(HashMap)}.
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectTransientVariables(HashMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.collectTransientVariables(HashMap)"})
  public void testCollectTransientVariables_thenHashMapEmpty() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    Map<String, Object> actualCollectTransientVariablesResult = createWithEmptyRelationshipCollectionsResult
        .collectTransientVariables(variables);

    // Assert
    assertTrue(variables.isEmpty());
    assertTrue(actualCollectTransientVariablesResult.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#collectTransientVariables(HashMap)}.
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#collectTransientVariables(HashMap)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map VariableScopeImpl.collectTransientVariables(HashMap)"})
  public void testCollectTransientVariables_thenHashMapSizeIsOne() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    Map<String, Object> actualCollectTransientVariablesResult = createWithEmptyRelationshipCollectionsResult
        .collectTransientVariables(variables);

    // Assert
    assertEquals(1, variables.size());
    assertTrue(variables.containsKey("Variable Name"));
    assertEquals(variables, createWithEmptyRelationshipCollectionsResult.getTransientVariables());
    assertEquals(variables, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal());
    assertEquals(variables, createWithEmptyRelationshipCollectionsResult.getVariables());
    assertEquals(variables, createWithEmptyRelationshipCollectionsResult.getVariablesLocal());
    assertSame(variables, actualCollectTransientVariablesResult);
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariableLocal(String)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariableLocal(String)"})
  public void testRemoveTransientVariableLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariableLocal("Variable Name");

    // Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.transientVariabes.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariableLocal(String)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariableLocal(String)"})
  public void testRemoveTransientVariableLocal_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariableLocal("Variable Name");

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariablesLocal()}.
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariablesLocal()"})
  public void testRemoveTransientVariablesLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariablesLocal();

    // Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.transientVariabes.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariablesLocal()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariablesLocal()"})
  public void testRemoveTransientVariablesLocal_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariablesLocal();

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariable(String)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariable(String)"})
  public void testRemoveTransientVariable() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariable("Variable Name");

    // Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.transientVariabes.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariable(String)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariable(String)"})
  public void testRemoveTransientVariable2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariable("Variable Name");

    // Assert that nothing has changed
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, VariableInstance> variableInstances = createWithEmptyRelationshipCollectionsResult
        .getVariableInstances();
    assertEquals(1, variableInstances.size());
    Map<String, VariableInstance> variableInstancesLocal = createWithEmptyRelationshipCollectionsResult
        .getVariableInstancesLocal();
    assertEquals(1, variableInstancesLocal.size());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(1, stringVariableInstanceMap.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    assertTrue(transientVariables.containsKey(null));
    assertTrue(variableInstances.containsKey(null));
    assertTrue(variableInstancesLocal.containsKey(null));
    assertTrue(stringVariableInstanceMap.containsKey(null));
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariables());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariablesLocal());
    assertEquals(variableNames, createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal());
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariable(String)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariable(String)"})
  public void testRemoveTransientVariable_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariable("Variable Name");

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariables()}.
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariables()"})
  public void testRemoveTransientVariables() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariables();

    // Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.transientVariabes.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#removeTransientVariables()}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test: {@link VariableScopeImpl#removeTransientVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.removeTransientVariables()"})
  public void testRemoveTransientVariables_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariables();

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Test {@link VariableScopeImpl#isActivityIdUsedForDetails()}.
   * <p>
   * Method under test: {@link VariableScopeImpl#isActivityIdUsedForDetails()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean VariableScopeImpl.isActivityIdUsedForDetails()"})
  public void testIsActivityIdUsedForDetails() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().isActivityIdUsedForDetails());
  }

  /**
   * Test {@link VariableScopeImpl#getCachedElContext()}.
   * <p>
   * Method under test: {@link VariableScopeImpl#getCachedElContext()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ELContext VariableScopeImpl.getCachedElContext()"})
  public void testGetCachedElContext() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getCachedElContext());
  }

  /**
   * Test {@link VariableScopeImpl#setCachedElContext(ELContext)}.
   * <p>
   * Method under test: {@link VariableScopeImpl#setCachedElContext(ELContext)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void VariableScopeImpl.setCachedElContext(ELContext)"})
  public void testSetCachedElContext() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    ParsingElContext cachedElContext = new ParsingElContext();

    // Act
    createWithEmptyRelationshipCollectionsResult.setCachedElContext(cachedElContext);

    // Assert
    assertSame(cachedElContext, createWithEmptyRelationshipCollectionsResult.getCachedElContext());
  }
}
