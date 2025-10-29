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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NoExecutionVariableScopeDiffblueTest {
  @InjectMocks
  private NoExecutionVariableScope noExecutionVariableScope;

  /**
   * Method under test: {@link NoExecutionVariableScope#getVariables()}
   */
  @Test
  public void testGetVariables() {
    // Arrange, Act and Assert
    assertTrue(NoExecutionVariableScope.getSharedInstance().getVariables().isEmpty());
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection)}
   */
  @Test
  public void testGetVariables2() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertTrue(sharedInstance.getVariables(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection)}
   */
  @Test
  public void testGetVariables3() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariables(variableNames).isEmpty());
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#getVariables(Collection)}
   */
  @Test
  public void testGetVariables4() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariables(variableNames).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables5() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertTrue(sharedInstance.getVariables(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables6() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariables(variableNames, true).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables7() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariables(variableNames, true).isEmpty());
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#getVariablesLocal()}
   */
  @Test
  public void testGetVariablesLocal() {
    // Arrange, Act and Assert
    assertTrue(NoExecutionVariableScope.getSharedInstance().getVariablesLocal().isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal2() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal3() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(variableNames).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal4() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(variableNames).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal5() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal6() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(variableNames, true).isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal7() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertTrue(sharedInstance.getVariablesLocal(variableNames, true).isEmpty());
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#getVariable(String)}
   */
  @Test
  public void testGetVariable() {
    // Arrange, Act and Assert
    assertNull(noExecutionVariableScope.getVariable("Variable Name"));
    assertNull(noExecutionVariableScope.getVariable("Variable Name", true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariable(String, Class)}
   */
  @Test
  public void testGetVariable2() {
    // Arrange
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertNull(noExecutionVariableScope.getVariable("Variable Name", variableClass));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#getVariableLocal(String)}
   */
  @Test
  public void testGetVariableLocal() {
    // Arrange, Act and Assert
    assertNull(noExecutionVariableScope.getVariableLocal("Variable Name"));
    assertNull(noExecutionVariableScope.getVariableLocal("Variable Name", true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableLocal(String, Class)}
   */
  @Test
  public void testGetVariableLocal2() {
    // Arrange
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertNull(noExecutionVariableScope.getVariableLocal("Variable Name", variableClass));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances2() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(variableNames));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances3() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(variableNames));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances4() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(new ArrayList<>(), true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances5() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(variableNames, true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances6() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstances(variableNames, true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal2() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(variableNames));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal3() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(variableNames));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal4() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(new ArrayList<>(), true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal5() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(variableNames, true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal6() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("42");
    variableNames.add("foo");

    // Act and Assert
    assertNull(sharedInstance.getVariableInstancesLocal(variableNames, true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstance(String)}
   */
  @Test
  public void testGetVariableInstance() {
    // Arrange, Act and Assert
    assertNull(noExecutionVariableScope.getVariableInstance("Variable Name"));
    assertNull(noExecutionVariableScope.getVariableInstance("Variable Name", true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getVariableInstanceLocal(String)}
   */
  @Test
  public void testGetVariableInstanceLocal() {
    // Arrange, Act and Assert
    assertNull(noExecutionVariableScope.getVariableInstanceLocal("Variable Name"));
    assertNull(noExecutionVariableScope.getVariableInstanceLocal("Variable Name", true));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#getVariableNames()}
   */
  @Test
  public void testGetVariableNames() {
    // Arrange, Act and Assert
    assertTrue(NoExecutionVariableScope.getSharedInstance().getVariableNames().isEmpty());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#setVariable(String, Object)}
   */
  @Test
  public void testSetVariable() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.setVariable("Variable Name", JSONObject.NULL));
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.setVariable("Variable Name", JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#setVariableLocal(String, Object)}
   */
  @Test
  public void testSetVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.setVariableLocal("Variable Name", JSONObject.NULL));
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.setVariableLocal("Variable Name", JSONObject.NULL, true));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#setVariables(Map)}
   */
  @Test
  public void testSetVariables() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.setVariables(new HashMap<>()));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#setVariablesLocal(Map)}
   */
  @Test
  public void testSetVariablesLocal() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.setVariablesLocal(new HashMap<>()));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#hasVariable(String)}
   */
  @Test
  public void testHasVariable() {
    // Arrange, Act and Assert
    assertFalse(noExecutionVariableScope.hasVariable("Variable Name"));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#hasVariableLocal(String)}
   */
  @Test
  public void testHasVariableLocal() {
    // Arrange, Act and Assert
    assertFalse(noExecutionVariableScope.hasVariableLocal("Variable Name"));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#createVariableLocal(String, Object)}
   */
  @Test
  public void testCreateVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.createVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#createVariablesLocal(Map)}
   */
  @Test
  public void testCreateVariablesLocal() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.createVariablesLocal(new HashMap<>()));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#removeVariable(String)}
   */
  @Test
  public void testRemoveVariable() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> noExecutionVariableScope.removeVariable("Variable Name"));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeVariableLocal(String)}
   */
  @Test
  public void testRemoveVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.removeVariableLocal("Variable Name"));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#removeVariables()}
   */
  @Test
  public void testRemoveVariables() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeVariables());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeVariables(Collection)}
   */
  @Test
  public void testRemoveVariables2() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariables(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeVariables(Collection)}
   */
  @Test
  public void testRemoveVariables3() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("No execution active, no variables can be removed");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariables(variableNames));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeVariables(Collection)}
   */
  @Test
  public void testRemoveVariables4() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");
    variableNames.add("No execution active, no variables can be removed");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariables(variableNames));
  }

  /**
   * Method under test: {@link NoExecutionVariableScope#removeVariablesLocal()}
   */
  @Test
  public void testRemoveVariablesLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeVariablesLocal());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeVariablesLocal(Collection)}
   */
  @Test
  public void testRemoveVariablesLocal2() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariablesLocal(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeVariablesLocal(Collection)}
   */
  @Test
  public void testRemoveVariablesLocal3() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("No execution active, no variables can be removed");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariablesLocal(variableNames));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeVariablesLocal(Collection)}
   */
  @Test
  public void testRemoveVariablesLocal4() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");
    variableNames.add("No execution active, no variables can be removed");

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.removeVariablesLocal(variableNames));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#setTransientVariablesLocal(Map)}
   */
  @Test
  public void testSetTransientVariablesLocal() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.setTransientVariablesLocal(new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#setTransientVariableLocal(String, Object)}
   */
  @Test
  public void testSetTransientVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.setTransientVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#setTransientVariables(Map)}
   */
  @Test
  public void testSetTransientVariables() {
    // Arrange
    NoExecutionVariableScope sharedInstance = NoExecutionVariableScope.getSharedInstance();

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> sharedInstance.setTransientVariables(new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#setTransientVariable(String, Object)}
   */
  @Test
  public void testSetTransientVariable() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.setTransientVariable("Variable Name", JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getTransientVariableLocal(String)}
   */
  @Test
  public void testGetTransientVariableLocal() {
    // Arrange, Act and Assert
    assertNull(noExecutionVariableScope.getTransientVariableLocal("Variable Name"));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#getTransientVariable(String)}
   */
  @Test
  public void testGetTransientVariable() {
    // Arrange, Act and Assert
    assertNull(noExecutionVariableScope.getTransientVariable("Variable Name"));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeTransientVariableLocal(String)}
   */
  @Test
  public void testRemoveTransientVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.removeTransientVariableLocal("Variable Name"));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeTransientVariablesLocal()}
   */
  @Test
  public void testRemoveTransientVariablesLocal() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeTransientVariablesLocal());
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeTransientVariable(String)}
   */
  @Test
  public void testRemoveTransientVariable() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> noExecutionVariableScope.removeTransientVariable("Variable Name"));
  }

  /**
   * Method under test:
   * {@link NoExecutionVariableScope#removeTransientVariables()}
   */
  @Test
  public void testRemoveTransientVariables() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> NoExecutionVariableScope.getSharedInstance().removeTransientVariables());
  }

  /**
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
