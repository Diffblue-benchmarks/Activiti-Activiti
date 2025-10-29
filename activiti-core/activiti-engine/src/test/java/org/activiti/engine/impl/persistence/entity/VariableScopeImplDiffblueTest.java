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
import static org.mockito.Mockito.mock;
import jakarta.el.BeanNameELResolver;
import jakarta.el.BeanNameResolver;
import jakarta.el.ELContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.el.ParsingElContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VariableScopeImplDiffblueTest {
  @InjectMocks
  private ExecutionEntityImpl executionEntityImpl;

  /**
   * Method under test:
   * {@link VariableScopeImpl#ensureVariableInstancesInitialized()}
   */
  @Test
  public void testEnsureVariableInstancesInitialized() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).ensureVariableInstancesInitialized());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariables()}
   */
  @Test
  public void testGetVariables() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariables().isEmpty());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariables());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariables()}
   */
  @Test
  public void testGetVariables2() {
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
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  public void testGetVariables3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  public void testGetVariables4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariables(new ArrayList<>()));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  public void testGetVariables5() {
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
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  public void testGetVariables6() {
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
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  public void testGetVariables7() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  public void testGetVariables8() {
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
   * Method under test: {@link VariableScopeImpl#getVariables(Collection)}
   */
  @Test
  public void testGetVariables9() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables10() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables11() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariables(new ArrayList<>(), true));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables12() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables13() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables14() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariables(Collection, boolean)}
   */
  @Test
  public void testGetVariables15() {
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
   * Method under test: {@link VariableScopeImpl#getVariableInstances()}
   */
  @Test
  public void testGetVariableInstances() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstances().isEmpty());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstances());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstances()}
   */
  @Test
  public void testGetVariableInstances2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertEquals(createWithEmptyRelationshipCollectionsResult.transientVariabes,
        createWithEmptyRelationshipCollectionsResult.getVariableInstances());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstances(new ArrayList<>()));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances5() {
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
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances6() {
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
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances7() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances8() {
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
   * Method under test: {@link VariableScopeImpl#getVariableInstances(Collection)}
   */
  @Test
  public void testGetVariableInstances9() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances10() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances11() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstances(new ArrayList<>(), true));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances12() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances13() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances14() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstances(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstances15() {
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
   * Method under test: {@link VariableScopeImpl#collectVariables(HashMap)}
   */
  @Test
  public void testCollectVariables() {
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
    assertSame(variables, actualCollectVariablesResult);
  }

  /**
   * Method under test: {@link VariableScopeImpl#collectVariables(HashMap)}
   */
  @Test
  public void testCollectVariables2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.collectVariables(new HashMap<>()));
  }

  /**
   * Method under test: {@link VariableScopeImpl#collectVariables(HashMap)}
   */
  @Test
  public void testCollectVariables3() {
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
    assertEquals(1, actualCollectVariablesResult.size());
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(1, transientVariablesLocal.size());
    Map<String, Object> variables2 = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(1, variables2.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(1, variablesLocal.size());
    assertTrue(variables.containsKey("Variable Name"));
    assertTrue(actualCollectVariablesResult.containsKey("Variable Name"));
    assertTrue(transientVariables.containsKey("Variable Name"));
    assertTrue(transientVariablesLocal.containsKey("Variable Name"));
    assertTrue(variables2.containsKey("Variable Name"));
    assertTrue(variablesLocal.containsKey("Variable Name"));
    assertSame(variables, actualCollectVariablesResult);
  }

  /**
   * Method under test: {@link VariableScopeImpl#collectVariables(HashMap)}
   */
  @Test
  public void testCollectVariables4() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

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
    assertSame(variables, actualCollectVariablesResult);
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#collectVariableInstances(HashMap)}
   */
  @Test
  public void testCollectVariableInstances() {
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
    assertSame(variables, actualCollectVariableInstancesResult);
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#collectVariableInstances(HashMap)}
   */
  @Test
  public void testCollectVariableInstances2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.collectVariableInstances(new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#collectVariableInstances(HashMap)}
   */
  @Test
  public void testCollectVariableInstances3() {
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
    assertTrue(variables.containsKey("Variable Name"));
    assertSame(variables, actualCollectVariableInstancesResult);
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#collectVariableInstances(HashMap)}
   */
  @Test
  public void testCollectVariableInstances4() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, VariableInstance> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    Map<String, VariableInstance> actualCollectVariableInstancesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariableInstances(variables);

    // Assert
    assertTrue(variables.isEmpty());
    assertTrue(actualCollectVariableInstancesResult.isEmpty());
    assertSame(variables, actualCollectVariableInstancesResult);
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariable(String)}
   */
  @Test
  public void testGetVariable() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariable("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariable(String, Class)}
   */
  @Test
  public void testGetVariable2() {
    // Arrange
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariable("Variable Name", variableClass));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstance(String)}
   */
  @Test
  public void testGetVariableInstance() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstance("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String)}
   */
  @Test
  public void testGetVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableLocal("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableLocal(String, Class)}
   */
  @Test
  public void testGetVariableLocal2() {
    // Arrange
    Class<Object> variableClass = Object.class;

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableLocal("Variable Name", variableClass));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstanceLocal(String)}
   */
  @Test
  public void testGetVariableInstanceLocal() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstanceLocal("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#hasVariables()}
   */
  @Test
  public void testHasVariables() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().hasVariables());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).hasVariables());
  }

  /**
   * Method under test: {@link VariableScopeImpl#hasVariables()}
   */
  @Test
  public void testHasVariables2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.hasVariables());
  }

  /**
   * Method under test: {@link VariableScopeImpl#hasVariablesLocal()}
   */
  @Test
  public void testHasVariablesLocal() {
    // Arrange, Act and Assert
    assertFalse(ExecutionEntityImpl.createWithEmptyRelationshipCollections().hasVariablesLocal());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).hasVariablesLocal());
  }

  /**
   * Method under test: {@link VariableScopeImpl#hasVariablesLocal()}
   */
  @Test
  public void testHasVariablesLocal2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.hasVariablesLocal());
  }

  /**
   * Method under test: {@link VariableScopeImpl#hasVariable(String)}
   */
  @Test
  public void testHasVariable() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.hasVariable("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#hasVariableLocal(String)}
   */
  @Test
  public void testHasVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.hasVariableLocal("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  public void testCollectVariableNames() {
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
    assertSame(variableNames, actualCollectVariableNamesResult);
  }

  /**
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  public void testCollectVariableNames2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.collectVariableNames(new HashSet<>()));
  }

  /**
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  public void testCollectVariableNames3() {
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
    assertEquals(1, actualCollectVariableNamesResult.size());
    assertTrue(variableNames.contains("foo"));
    assertTrue(actualCollectVariableNamesResult.contains("foo"));
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
    assertSame(variableNames, actualCollectVariableNamesResult);
  }

  /**
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  public void testCollectVariableNames4() {
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
    assertTrue(variableNames.contains("foo"));
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
    assertSame(variableNames, actualCollectVariableNamesResult);
  }

  /**
   * Method under test: {@link VariableScopeImpl#collectVariableNames(Set)}
   */
  @Test
  public void testCollectVariableNames5() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);
    HashSet<String> variableNames = new HashSet<>();

    // Act
    Set<String> actualCollectVariableNamesResult = createWithEmptyRelationshipCollectionsResult
        .collectVariableNames(variableNames);

    // Assert
    assertEquals(1, variableNames.size());
    assertEquals(1, actualCollectVariableNamesResult.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableNames().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().size());
    assertTrue(variableNames.contains("Variable Name"));
    assertTrue(actualCollectVariableNamesResult.contains("Variable Name"));
    assertSame(variableNames, actualCollectVariableNamesResult);
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableNames()}
   */
  @Test
  public void testGetVariableNames() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableNames().isEmpty());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableNames());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableNames()}
   */
  @Test
  public void testGetVariableNames2() {
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
   * Method under test: {@link VariableScopeImpl#getVariablesLocal()}
   */
  @Test
  public void testGetVariablesLocal() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariablesLocal().isEmpty());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariablesLocal());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariablesLocal()}
   */
  @Test
  public void testGetVariablesLocal2() {
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
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariablesLocal(new ArrayList<>()));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal5() {
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
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal6() {
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
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal7() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal8() {
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
   * Method under test: {@link VariableScopeImpl#getVariablesLocal(Collection)}
   */
  @Test
  public void testGetVariablesLocal9() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal10() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal11() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariablesLocal(new ArrayList<>(), true));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal12() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal13() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal14() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal15() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariablesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariablesLocal16() {
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
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal()}
   */
  @Test
  public void testGetVariableInstancesLocal() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstancesLocal().isEmpty());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstancesLocal());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstancesLocal()}
   */
  @Test
  public void testGetVariableInstancesLocal2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertEquals(createWithEmptyRelationshipCollectionsResult.transientVariabes,
        createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal4() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstancesLocal(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal5() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal6() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal7() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal8() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection)}
   */
  @Test
  public void testGetVariableInstancesLocal9() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal10() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(
        createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal11() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.getVariableInstancesLocal(new ArrayList<>(), true));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal12() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal13() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal14() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act and Assert
    assertTrue(
        createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal(new ArrayList<>(), true).isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal15() {
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
   * Method under test:
   * {@link VariableScopeImpl#getVariableInstancesLocal(Collection, boolean)}
   */
  @Test
  public void testGetVariableInstancesLocal16() {
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
   * Method under test: {@link VariableScopeImpl#getVariableNamesLocal()}
   */
  @Test
  public void testGetVariableNamesLocal() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableNamesLocal().isEmpty());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableNamesLocal());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableNamesLocal()}
   */
  @Test
  public void testGetVariableNamesLocal2() {
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
   * Method under test: {@link VariableScopeImpl#getVariableInstanceEntities()}
   */
  @Test
  public void testGetVariableInstanceEntities() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getVariableInstanceEntities().isEmpty());
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).getVariableInstanceEntities());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getVariableInstanceEntities()}
   */
  @Test
  public void testGetVariableInstanceEntities2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getUsedVariablesCache()}
   */
  @Test
  public void testGetUsedVariablesCache() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    Map<String, VariableInstanceEntity> actualUsedVariablesCache = createWithEmptyRelationshipCollectionsResult
        .getUsedVariablesCache();

    // Assert
    assertTrue(actualUsedVariablesCache.isEmpty());
    assertSame(createWithEmptyRelationshipCollectionsResult.usedVariablesCache, actualUsedVariablesCache);
  }

  /**
   * Method under test: {@link VariableScopeImpl#getUsedVariablesCache()}
   */
  @Test
  public void testGetUsedVariablesCache2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(Date.class));

    // Act
    Map<String, VariableInstanceEntity> actualUsedVariablesCache = createWithEmptyRelationshipCollectionsResult
        .getUsedVariablesCache();

    // Assert
    assertTrue(actualUsedVariablesCache.isEmpty());
    assertSame(createWithEmptyRelationshipCollectionsResult.usedVariablesCache, actualUsedVariablesCache);
  }

  /**
   * Method under test: {@link VariableScopeImpl#createVariablesLocal(Map)}
   */
  @Test
  public void testCreateVariablesLocal() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.createVariablesLocal(variables));
  }

  /**
   * Method under test: {@link VariableScopeImpl#setVariables(Map)}
   */
  @Test
  public void testSetVariables() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariables(variables));
  }

  /**
   * Method under test: {@link VariableScopeImpl#setVariablesLocal(Map)}
   */
  @Test
  public void testSetVariablesLocal() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("foo", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariablesLocal(variables));
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeVariables()}
   */
  @Test
  public void testRemoveVariables() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).removeVariables());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeVariables(Collection)}
   */
  @Test
  public void testRemoveVariables2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.removeVariables(variableNames));
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeVariablesLocal()}
   */
  @Test
  public void testRemoveVariablesLocal() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ExecutionEntityImpl()).removeVariablesLocal());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeVariablesLocal(Collection)}
   */
  @Test
  public void testRemoveVariablesLocal2() {
    // Arrange
    ExecutionEntityImpl executionEntityImpl = new ExecutionEntityImpl();

    ArrayList<String> variableNames = new ArrayList<>();
    variableNames.add("foo");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.removeVariablesLocal(variableNames));
  }

  /**
   * Method under test: {@link VariableScopeImpl#setVariable(String, Object)}
   */
  @Test
  public void testSetVariable() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariable("Variable Name", JSONObject.NULL));
  }

  /**
   * Method under test: {@link VariableScopeImpl#setVariableLocal(String, Object)}
   */
  @Test
  public void testSetVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.setVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#createVariableLocal(String, Object)}
   */
  @Test
  public void testCreateVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> executionEntityImpl.createVariableLocal("Variable Name", JSONObject.NULL));
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeVariable(String)}
   */
  @Test
  public void testRemoveVariable() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.removeVariable("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeVariableLocal(String)}
   */
  @Test
  public void testRemoveVariableLocal() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> executionEntityImpl.removeVariableLocal("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#setTransientVariablesLocal(Map)}
   */
  @Test
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
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#setTransientVariablesLocal(Map)}
   */
  @Test
  public void testSetTransientVariablesLocal2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.put("foo", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariablesLocal(transientVariables);

    // Assert
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getTransientVariables().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariables().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariablesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    Set<String> variableNamesLocal = createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal();
    assertEquals(1, variableNamesLocal.size());
    assertTrue(variableNames.contains("foo"));
    assertTrue(variableNamesLocal.contains("foo"));
    Map<String, VariableInstance> expectedVariableInstances = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(expectedVariableInstances, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
  }

  /**
   * Method under test: {@link VariableScopeImpl#setTransientVariablesLocal(Map)}
   */
  @Test
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
    assertEquals(2, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(2, variableNames.size());
    assertTrue(variableNames.contains("42"));
    assertTrue(variableNames.contains("foo"));
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getTransientVariables());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariables());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariablesLocal());
    assertEquals(variableNames, createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(stringVariableInstanceMap, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
    assertEquals(stringVariableInstanceMap, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal());
  }

  /**
   * Method under test: {@link VariableScopeImpl#setTransientVariablesLocal(Map)}
   */
  @Test
  public void testSetTransientVariablesLocal4() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.computeIfPresent("foo", mock(BiFunction.class));
    transientVariables.put("foo", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariablesLocal(transientVariables);

    // Assert
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getTransientVariables().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariables().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariablesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    Set<String> variableNamesLocal = createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal();
    assertEquals(1, variableNamesLocal.size());
    assertTrue(variableNames.contains("foo"));
    assertTrue(variableNamesLocal.contains("foo"));
    Map<String, VariableInstance> expectedVariableInstances = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(expectedVariableInstances, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#setTransientVariableLocal(String, Object)}
   */
  @Test
  public void testSetTransientVariableLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    Object object = JSONObject.NULL;

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", object);

    // Assert
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(1, transientVariablesLocal.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().size());
    Map<String, Object> variables = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(1, variables.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(1, variablesLocal.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    Set<String> variableNamesLocal = createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal();
    assertEquals(1, variableNamesLocal.size());
    assertTrue(variableNames.contains("Variable Name"));
    assertTrue(variableNamesLocal.contains("Variable Name"));
    Map<String, VariableInstance> expectedVariableInstances = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(expectedVariableInstances, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
    assertSame(object, transientVariables.get("Variable Name"));
    assertSame(object, transientVariablesLocal.get("Variable Name"));
    assertSame(object, variables.get("Variable Name"));
    assertSame(object, variablesLocal.get("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#setTransientVariables(Map)}
   */
  @Test
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
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#setTransientVariables(Map)}
   */
  @Test
  public void testSetTransientVariables2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.put("foo", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariables(transientVariables);

    // Assert
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getTransientVariables().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariables().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariablesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    Set<String> variableNamesLocal = createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal();
    assertEquals(1, variableNamesLocal.size());
    assertTrue(variableNames.contains("foo"));
    assertTrue(variableNamesLocal.contains("foo"));
    Map<String, VariableInstance> expectedVariableInstances = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(expectedVariableInstances, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
  }

  /**
   * Method under test: {@link VariableScopeImpl#setTransientVariables(Map)}
   */
  @Test
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
    assertEquals(2, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(2, variableNames.size());
    assertTrue(variableNames.contains("42"));
    assertTrue(variableNames.contains("foo"));
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getTransientVariables());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariables());
    assertEquals(transientVariables, createWithEmptyRelationshipCollectionsResult.getVariablesLocal());
    assertEquals(variableNames, createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal());
    Map<String, VariableInstance> stringVariableInstanceMap = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(stringVariableInstanceMap, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
    assertEquals(stringVariableInstanceMap, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal());
  }

  /**
   * Method under test: {@link VariableScopeImpl#setTransientVariables(Map)}
   */
  @Test
  public void testSetTransientVariables4() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> transientVariables = new HashMap<>();
    transientVariables.computeIfPresent("foo", mock(BiFunction.class));
    transientVariables.put("foo", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariables(transientVariables);

    // Assert
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getTransientVariables().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariables().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariablesLocal().size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    Set<String> variableNamesLocal = createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal();
    assertEquals(1, variableNamesLocal.size());
    assertTrue(variableNames.contains("foo"));
    assertTrue(variableNamesLocal.contains("foo"));
    Map<String, VariableInstance> expectedVariableInstances = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(expectedVariableInstances, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#setTransientVariable(String, Object)}
   */
  @Test
  public void testSetTransientVariable() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    Object object = JSONObject.NULL;

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariable("Variable Name", object);

    // Assert
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(1, transientVariablesLocal.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().size());
    Map<String, Object> variables = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(1, variables.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(1, variablesLocal.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    Set<String> variableNamesLocal = createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal();
    assertEquals(1, variableNamesLocal.size());
    assertTrue(variableNames.contains("Variable Name"));
    assertTrue(variableNamesLocal.contains("Variable Name"));
    Map<String, VariableInstance> expectedVariableInstances = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(expectedVariableInstances, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
    assertSame(object, transientVariables.get("Variable Name"));
    assertSame(object, transientVariablesLocal.get("Variable Name"));
    assertSame(object, variables.get("Variable Name"));
    assertSame(object, variablesLocal.get("Variable Name"));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#setTransientVariable(String, Object)}
   */
  @Test
  public void testSetTransientVariable2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);
    Object object = JSONObject.NULL;

    // Act
    createWithEmptyRelationshipCollectionsResult.setTransientVariable("Variable Name", object);

    // Assert
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(1, transientVariablesLocal.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().size());
    Map<String, Object> variables = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(1, variables.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(1, variablesLocal.size());
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.transientVariabes.size());
    Set<String> variableNames = createWithEmptyRelationshipCollectionsResult.getVariableNames();
    assertEquals(1, variableNames.size());
    Set<String> variableNamesLocal = createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal();
    assertEquals(1, variableNamesLocal.size());
    assertTrue(variableNames.contains("Variable Name"));
    assertTrue(variableNamesLocal.contains("Variable Name"));
    Map<String, VariableInstance> expectedVariableInstances = createWithEmptyRelationshipCollectionsResult.transientVariabes;
    assertEquals(expectedVariableInstances, createWithEmptyRelationshipCollectionsResult.getVariableInstances());
    assertSame(object, transientVariables.get("Variable Name"));
    assertSame(object, transientVariablesLocal.get("Variable Name"));
    assertSame(object, variables.get("Variable Name"));
    assertSame(object, variablesLocal.get("Variable Name"));
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#getTransientVariableLocal(String)}
   */
  @Test
  public void testGetTransientVariableLocal() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTransientVariableLocal("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getTransientVariablesLocal()}
   */
  @Test
  public void testGetTransientVariablesLocal() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTransientVariablesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getTransientVariablesLocal()}
   */
  @Test
  public void testGetTransientVariablesLocal2() {
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
   * Method under test: {@link VariableScopeImpl#getTransientVariablesLocal()}
   */
  @Test
  public void testGetTransientVariablesLocal3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getTransientVariable(String)}
   */
  @Test
  public void testGetTransientVariable() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTransientVariable("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getTransientVariable(String)}
   */
  @Test
  public void testGetTransientVariable2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getTransientVariable("Variable Name"));
  }

  /**
   * Method under test: {@link VariableScopeImpl#getTransientVariables()}
   */
  @Test
  public void testGetTransientVariables() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getTransientVariables().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getTransientVariables()}
   */
  @Test
  public void testGetTransientVariables2() {
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
   * Method under test:
   * {@link VariableScopeImpl#collectTransientVariables(HashMap)}
   */
  @Test
  public void testCollectTransientVariables() {
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
    assertSame(variables, actualCollectTransientVariablesResult);
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#collectTransientVariables(HashMap)}
   */
  @Test
  public void testCollectTransientVariables2() {
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
    assertEquals(1, actualCollectTransientVariablesResult.size());
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
    Map<String, Object> transientVariablesLocal = createWithEmptyRelationshipCollectionsResult
        .getTransientVariablesLocal();
    assertEquals(1, transientVariablesLocal.size());
    Map<String, Object> variables2 = createWithEmptyRelationshipCollectionsResult.getVariables();
    assertEquals(1, variables2.size());
    Map<String, Object> variablesLocal = createWithEmptyRelationshipCollectionsResult.getVariablesLocal();
    assertEquals(1, variablesLocal.size());
    assertTrue(variables.containsKey("Variable Name"));
    assertTrue(actualCollectTransientVariablesResult.containsKey("Variable Name"));
    assertTrue(transientVariables.containsKey("Variable Name"));
    assertTrue(transientVariablesLocal.containsKey("Variable Name"));
    assertTrue(variables2.containsKey("Variable Name"));
    assertTrue(variablesLocal.containsKey("Variable Name"));
    assertSame(variables, actualCollectTransientVariablesResult);
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#collectTransientVariables(HashMap)}
   */
  @Test
  public void testCollectTransientVariables3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

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
    assertSame(variables, actualCollectTransientVariablesResult);
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#removeTransientVariableLocal(String)}
   */
  @Test
  public void testRemoveTransientVariableLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariableLocal("Variable Name");

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableScopeImpl#removeTransientVariableLocal(String)}
   */
  @Test
  public void testRemoveTransientVariableLocal2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariableLocal("Variable Name");

    // Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.transientVariabes.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeTransientVariablesLocal()}
   */
  @Test
  public void testRemoveTransientVariablesLocal() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariablesLocal();

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeTransientVariablesLocal()}
   */
  @Test
  public void testRemoveTransientVariablesLocal2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariablesLocal();

    // Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.transientVariabes.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeTransientVariablesLocal()}
   */
  @Test
  public void testRemoveTransientVariablesLocal3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(Date.class));

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariablesLocal();

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeTransientVariable(String)}
   */
  @Test
  public void testRemoveTransientVariable() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariable("Variable Name");

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeTransientVariable(String)}
   */
  @Test
  public void testRemoveTransientVariable2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariable("Variable Name");

    // Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.transientVariabes.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeTransientVariable(String)}
   */
  @Test
  public void testRemoveTransientVariable3() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal(null, JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariable("Variable Name");

    // Assert that nothing has changed
    Map<String, Object> transientVariables = createWithEmptyRelationshipCollectionsResult.getTransientVariables();
    assertEquals(1, transientVariables.size());
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
    assertEquals(1, createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().size());
    assertTrue(transientVariables.containsKey(null));
    assertTrue(transientVariablesLocal.containsKey(null));
    assertTrue(variableInstances.containsKey(null));
    assertTrue(variableInstancesLocal.containsKey(null));
    assertTrue(variables.containsKey(null));
    assertTrue(variablesLocal.containsKey(null));
    assertTrue(stringVariableInstanceMap.containsKey(null));
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeTransientVariables()}
   */
  @Test
  public void testRemoveTransientVariables() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariables();

    // Assert that nothing has changed
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#removeTransientVariables()}
   */
  @Test
  public void testRemoveTransientVariables2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setTransientVariableLocal("Variable Name", JSONObject.NULL);

    // Act
    createWithEmptyRelationshipCollectionsResult.removeTransientVariables();

    // Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.getProcessVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getTransientVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getUsedVariablesCache().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstanceEntities().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstances().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableInstancesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariables().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariablesLocal().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.transientVariabes.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.variableInstances.isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNames().isEmpty());
    assertTrue(createWithEmptyRelationshipCollectionsResult.getVariableNamesLocal().isEmpty());
  }

  /**
   * Method under test: {@link VariableScopeImpl#isActivityIdUsedForDetails()}
   */
  @Test
  public void testIsActivityIdUsedForDetails() {
    // Arrange, Act and Assert
    assertTrue(ExecutionEntityImpl.createWithEmptyRelationshipCollections().isActivityIdUsedForDetails());
  }

  /**
   * Method under test: {@link VariableScopeImpl#isActivityIdUsedForDetails()}
   */
  @Test
  public void testIsActivityIdUsedForDetails2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(Date.class));

    // Act and Assert
    assertTrue(createWithEmptyRelationshipCollectionsResult.isActivityIdUsedForDetails());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getCachedElContext()}
   */
  @Test
  public void testGetCachedElContext() {
    // Arrange, Act and Assert
    assertNull(ExecutionEntityImpl.createWithEmptyRelationshipCollections().getCachedElContext());
  }

  /**
   * Method under test: {@link VariableScopeImpl#getCachedElContext()}
   */
  @Test
  public void testGetCachedElContext2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setLockTime(mock(Date.class));

    // Act and Assert
    assertNull(createWithEmptyRelationshipCollectionsResult.getCachedElContext());
  }

  /**
   * Method under test: {@link VariableScopeImpl#setCachedElContext(ELContext)}
   */
  @Test
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

  /**
   * Method under test: {@link VariableScopeImpl#setCachedElContext(ELContext)}
   */
  @Test
  public void testSetCachedElContext2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();
    ActivitiElContext cachedElContext = new ActivitiElContext(new BeanNameELResolver(mock(BeanNameResolver.class)));

    // Act
    createWithEmptyRelationshipCollectionsResult.setCachedElContext(cachedElContext);

    // Assert
    assertSame(cachedElContext, createWithEmptyRelationshipCollectionsResult.getCachedElContext());
  }
}
