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
package org.activiti.engine.impl.el;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class NoExecutionVariableScopeDiffblueTest {
  /**
   * Test {@link NoExecutionVariableScope#getVariables()}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariables()"})
  public void testGetVariables() {
    // Arrange, Act and Assert
    assertTrue(NoExecutionVariableScope.getSharedInstance().getVariables().isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_given42_whenArrayListAdd42() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariables(variableNames, true).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_givenFoo_whenArrayListAddFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariables(variableNames, true).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariables(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariables(Collection, boolean)"})
  public void testGetVariablesWithVariableNamesFetchAllVariables_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertTrue(sharedInstance.getVariables(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_given42_whenArrayListAdd42() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariables(variableNames).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_givenFoo_whenArrayListAddFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariables(variableNames).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariables(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariables(Collection)"})
  public void testGetVariablesWithVariableNames_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertTrue(sharedInstance.getVariables(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariablesLocal()}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariablesLocal()"})
  public void testGetVariablesLocal() {
    // Arrange, Act and Assert
    assertTrue(NoExecutionVariableScope.getSharedInstance().getVariablesLocal().isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables_given42() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(variableNames, true).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables_givenFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(variableNames, true).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariablesLocal(Collection, boolean)"})
  public void testGetVariablesLocalWithVariableNamesFetchAllVariables_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_given42_whenArrayListAdd42() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(variableNames).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_givenFoo_whenArrayListAddFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(variableNames).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariablesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariablesLocal(Collection)"})
  public void testGetVariablesLocalWithVariableNames_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariable(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.getVariable(String)"})
  public void testGetVariableWithVariableName() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getVariable("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariable(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariable(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.getVariable(String, boolean)"})
  public void testGetVariableWithVariableNameFetchAllVariables() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getVariable("Variable Name", true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariable(String, Class)} with {@code variableName}, {@code variableClass}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariable(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.getVariable(String, Class)"})
  public void testGetVariableWithVariableNameVariableClass() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertNull(sharedInstance.getVariable("Variable Name", variableClass));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableLocal(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.getVariableLocal(String)"})
  public void testGetVariableLocalWithVariableName() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getVariableLocal("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.getVariableLocal(String, boolean)"})
  public void testGetVariableLocalWithVariableNameFetchAllVariables() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getVariableLocal("Variable Name", true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableLocal(String, Class)} with {@code variableName}, {@code variableClass}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableLocal(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.getVariableLocal(String, Class)"})
  public void testGetVariableLocalWithVariableNameVariableClass() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertNull(sharedInstance.getVariableLocal("Variable Name", variableClass));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables_given42() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(variableNames, true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables_givenFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(variableNames, true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstances(Collection, boolean)"})
  public void testGetVariableInstancesWithVariableNamesFetchAllVariables_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(new ArrayList<>(), true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_given42_whenArrayListAdd42() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(variableNames));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_givenFoo_whenArrayListAddFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(variableNames));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstances(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstances(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstances(Collection)"})
  public void testGetVariableInstancesWithVariableNames_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(new ArrayList<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables_given42() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(variableNames, true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables_givenFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(variableNames, true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)} with {@code variableNames}, {@code fetchAllVariables}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstancesLocal(Collection, boolean)"})
  public void testGetVariableInstancesLocalWithVariableNamesFetchAllVariables_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(new ArrayList<>(), true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_given42_whenArrayListAdd42() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(variableNames));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_givenFoo_whenArrayListAddFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(variableNames));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)} with {@code variableNames}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map NoExecutionVariableScope.getVariableInstancesLocal(Collection)"})
  public void testGetVariableInstancesLocalWithVariableNames_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(new ArrayList<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstance(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstance(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance NoExecutionVariableScope.getVariableInstance(String)"})
  public void testGetVariableInstanceWithVariableName() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getVariableInstance("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstance(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstance(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance NoExecutionVariableScope.getVariableInstance(String, boolean)"})
  public void testGetVariableInstanceWithVariableNameFetchAllVariables() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getVariableInstance("Variable Name", true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstanceLocal(String)} with {@code variableName}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstanceLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance NoExecutionVariableScope.getVariableInstanceLocal(String)"})
  public void testGetVariableInstanceLocalWithVariableName() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getVariableInstanceLocal("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableInstanceLocal(String, boolean)} with {@code variableName}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableInstanceLocal(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableInstance NoExecutionVariableScope.getVariableInstanceLocal(String, boolean)"})
  public void testGetVariableInstanceLocalWithVariableNameFetchAllVariables() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getVariableInstanceLocal("Variable Name", true));
  }

  /**
   * Test {@link NoExecutionVariableScope#getVariableNames()}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getVariableNames()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set NoExecutionVariableScope.getVariableNames()"})
  public void testGetVariableNames() {
    // Arrange, Act and Assert
    assertTrue(NoExecutionVariableScope.getSharedInstance().getVariableNames().isEmpty());
  }

  /**
   * Test {@link NoExecutionVariableScope#setVariable(String, Object)} with {@code variableName}, {@code value}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setVariable(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.setVariable(String, Object)"})
  public void testSetVariableWithVariableNameValue() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().setVariable("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link NoExecutionVariableScope#setVariable(String, Object, boolean)} with {@code variableName}, {@code value}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setVariable(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.setVariable(String, Object, boolean)"})
  public void testSetVariableWithVariableNameValueFetchAllVariables() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().setVariable("Variable Name", JSONObject.NULL, true));
  }

  /**
   * Test {@link NoExecutionVariableScope#setVariableLocal(String, Object)} with {@code variableName}, {@code value}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setVariableLocal(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.setVariableLocal(String, Object)"})
  public void testSetVariableLocalWithVariableNameValue() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().setVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link NoExecutionVariableScope#setVariableLocal(String, Object, boolean)} with {@code variableName}, {@code value}, {@code fetchAllVariables}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setVariableLocal(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.setVariableLocal(String, Object, boolean)"})
  public void testSetVariableLocalWithVariableNameValueFetchAllVariables() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().setVariableLocal("Variable Name", JSONObject.NULL, true));
  }

  /**
   * Test {@link NoExecutionVariableScope#setVariables(Map)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setVariables(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.setVariables(Map)"})
  public void testSetVariables() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.setVariables(new HashMap<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#setVariablesLocal(Map)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setVariablesLocal(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.setVariablesLocal(Map)"})
  public void testSetVariablesLocal() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.setVariablesLocal(new HashMap<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#hasVariable(String)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#hasVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NoExecutionVariableScope.hasVariable(String)"})
  public void testHasVariable() {
    // Arrange, Act and Assert
    assertFalse(NoExecutionVariableScope.getSharedInstance().hasVariable("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#hasVariableLocal(String)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#hasVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean NoExecutionVariableScope.hasVariableLocal(String)"})
  public void testHasVariableLocal() {
    // Arrange, Act and Assert
    assertFalse(NoExecutionVariableScope.getSharedInstance().hasVariableLocal("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#createVariableLocal(String, Object)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#createVariableLocal(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.createVariableLocal(String, Object)"})
  public void testCreateVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().createVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link NoExecutionVariableScope#createVariablesLocal(Map)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#createVariablesLocal(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.createVariablesLocal(Map)"})
  public void testCreateVariablesLocal() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.createVariablesLocal(new HashMap<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariable(String)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariable(String)"})
  public void testRemoveVariable() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeVariable("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariableLocal(String)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariableLocal(String)"})
  public void testRemoveVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeVariableLocal("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariables()}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariables()"})
  public void testRemoveVariables() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeVariables());
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariables(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariables(Collection)"})
  public void testRemoveVariablesWithCollection_givenFoo_whenArrayListAddFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");
    variableNames.add("No execution active, no variables can be removed");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariables(variableNames));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariables(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@code No execution active, no variables can be removed}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariables(Collection)"})
  public void testRemoveVariablesWithCollection_givenNoExecutionActiveNoVariablesCanBeRemoved() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("No execution active, no variables can be removed");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariables(variableNames));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariables(Collection)} with {@code Collection}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariables(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariables(Collection)"})
  public void testRemoveVariablesWithCollection_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariables(new ArrayList<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariablesLocal()}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariablesLocal()"})
  public void testRemoveVariablesLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeVariablesLocal());
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariablesLocal(Collection)} with {@code Collection}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariablesLocal(Collection)"})
  public void testRemoveVariablesLocalWithCollection() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("No execution active, no variables can be removed");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariablesLocal(variableNames));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariablesLocal(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariablesLocal(Collection)"})
  public void testRemoveVariablesLocalWithCollection_givenFoo_whenArrayListAddFoo() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");
    variableNames.add("No execution active, no variables can be removed");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariablesLocal(variableNames));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeVariablesLocal(Collection)} with {@code Collection}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeVariablesLocal(Collection)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeVariablesLocal(Collection)"})
  public void testRemoveVariablesLocalWithCollection_whenArrayList() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariablesLocal(new ArrayList<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#setTransientVariablesLocal(Map)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setTransientVariablesLocal(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.setTransientVariablesLocal(Map)"})
  public void testSetTransientVariablesLocal() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.setTransientVariablesLocal(new HashMap<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#setTransientVariableLocal(String, Object)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setTransientVariableLocal(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.setTransientVariableLocal(String, Object)"})
  public void testSetTransientVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().setTransientVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link NoExecutionVariableScope#setTransientVariables(Map)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setTransientVariables(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.setTransientVariables(Map)"})
  public void testSetTransientVariables() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.setTransientVariables(new HashMap<>()));
  }

  /**
   * Test {@link NoExecutionVariableScope#setTransientVariable(String, Object)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#setTransientVariable(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.setTransientVariable(String, Object)"})
  public void testSetTransientVariable() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().setTransientVariable("Variable Name", JSONObject.NULL));
  }

  /**
   * Test {@link NoExecutionVariableScope#getTransientVariableLocal(String)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getTransientVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.getTransientVariableLocal(String)"})
  public void testGetTransientVariableLocal() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getTransientVariableLocal("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#getTransientVariable(String)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#getTransientVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object NoExecutionVariableScope.getTransientVariable(String)"})
  public void testGetTransientVariable() {
    // Arrange, Act and Assert
    assertNull(NoExecutionVariableScope.getSharedInstance().getTransientVariable("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeTransientVariableLocal(String)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeTransientVariableLocal(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeTransientVariableLocal(String)"})
  public void testRemoveTransientVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeTransientVariableLocal("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeTransientVariablesLocal()}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeTransientVariablesLocal()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeTransientVariablesLocal()"})
  public void testRemoveTransientVariablesLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeTransientVariablesLocal());
  }

  /**
   * Test {@link NoExecutionVariableScope#removeTransientVariable(String)}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeTransientVariable(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeTransientVariable(String)"})
  public void testRemoveTransientVariable() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeTransientVariable("Variable Name"));
  }

  /**
   * Test {@link NoExecutionVariableScope#removeTransientVariables()}.
   * <p>
   * Method under test: {@link NoExecutionVariableScope#removeTransientVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.removeTransientVariables()"})
  public void testRemoveTransientVariables() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeTransientVariables());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NoExecutionVariableScope}
   *   <li>{@link NoExecutionVariableScope#getSharedInstance()}
   *   <li>{@link NoExecutionVariableScope#getTransientVariables()}
   *   <li>{@link NoExecutionVariableScope#getTransientVariablesLocal()}
   *   <li>{@link NoExecutionVariableScope#getVariableInstances()}
   *   <li>{@link NoExecutionVariableScope#getVariableInstancesLocal()}
   *   <li>{@link NoExecutionVariableScope#getVariableNamesLocal()}
   *   <li>{@link NoExecutionVariableScope#hasVariables()}
   *   <li>{@link NoExecutionVariableScope#hasVariablesLocal()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void NoExecutionVariableScope.<init>()",
      "NoExecutionVariableScope NoExecutionVariableScope.getSharedInstance()",
      "Map NoExecutionVariableScope.getTransientVariables()",
      "Map NoExecutionVariableScope.getTransientVariablesLocal()",
      "Map NoExecutionVariableScope.getVariableInstances()", "Map NoExecutionVariableScope.getVariableInstancesLocal()",
      "Set NoExecutionVariableScope.getVariableNamesLocal()", "boolean NoExecutionVariableScope.hasVariables()",
      "boolean NoExecutionVariableScope.hasVariablesLocal()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    NoExecutionVariableScope actualNoExecutionVariableScope = new NoExecutionVariableScope();
    NoExecutionVariableScope actualSharedInstance = actualNoExecutionVariableScope.getSharedInstance();
    Map<String, Object> actualTransientVariables = actualNoExecutionVariableScope.getTransientVariables();
    Map<String, Object> actualTransientVariablesLocal = actualNoExecutionVariableScope.getTransientVariablesLocal();
    Map<String, VariableInstance> actualVariableInstances = actualNoExecutionVariableScope.getVariableInstances();
    Map<String, VariableInstance> actualVariableInstancesLocal = actualNoExecutionVariableScope
        .getVariableInstancesLocal();
    Set<String> actualVariableNamesLocal = actualNoExecutionVariableScope.getVariableNamesLocal();
    boolean actualHasVariablesResult = actualNoExecutionVariableScope.hasVariables();

    // Assert
    assertNull(actualTransientVariables);
    assertNull(actualTransientVariablesLocal);
    assertNull(actualVariableInstances);
    assertNull(actualVariableInstancesLocal);
    assertNull(actualVariableNamesLocal);
    assertFalse(actualHasVariablesResult);
    assertFalse(actualNoExecutionVariableScope.hasVariablesLocal());
    Set<String> expectedVariableNames = actualNoExecutionVariableScope.getVariableNames();
    assertSame(expectedVariableNames, actualSharedInstance.getVariableNames());
    Map<String, Object> variables = actualNoExecutionVariableScope.getVariables();
    assertSame(variables, actualSharedInstance.getVariables());
    assertSame(variables, actualSharedInstance.getVariablesLocal());
  }
}
