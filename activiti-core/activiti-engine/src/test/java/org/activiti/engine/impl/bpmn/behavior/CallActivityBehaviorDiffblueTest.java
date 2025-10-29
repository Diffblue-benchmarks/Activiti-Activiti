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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.IOParameter;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CallActivityBehaviorDiffblueTest {
  @InjectMocks
  private CallActivityBehavior callActivityBehavior;

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List)}
   */
  @Test
  public void testNewCallActivityBehavior9() {
    // Arrange and Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior("Process Definition Key",
        new ArrayList<>());

    // Assert
    assertEquals("Process Definition Key", actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.processDefinitionExpression);
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(actualCallActivityBehavior.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List)}
   */
  @Test
  public void testNewCallActivityBehavior10() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior("Process Definition Key", mapExceptions);

    // Assert
    assertEquals("Process Definition Key", actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.processDefinitionExpression);
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    List<MapExceptionEntry> mapExceptionEntryList = actualCallActivityBehavior.mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List)}
   */
  @Test
  public void testNewCallActivityBehavior11() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry2);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior("Process Definition Key", mapExceptions);

    // Assert
    assertEquals("Process Definition Key", actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.processDefinitionExpression);
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    List<MapExceptionEntry> mapExceptionEntryList = actualCallActivityBehavior.mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
    assertSame(mapExceptionEntry2, mapExceptionEntryList.get(1));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List)}
   */
  @Test
  public void testNewCallActivityBehavior12() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(mock(MapExceptionEntry.class));

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior("Process Definition Key", mapExceptions);

    // Assert
    assertEquals("Process Definition Key", actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.processDefinitionExpression);
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(1, actualCallActivityBehavior.mapExceptions.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior13() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior("Process Definition Key", mapExceptions,
        new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    assertEquals("Process Definition Key", actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.processDefinitionExpression);
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(actualCallActivityBehavior.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior14() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior("Process Definition Key", mapExceptions,
        new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    assertEquals("Process Definition Key", actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.processDefinitionExpression);
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    List<MapExceptionEntry> mapExceptionEntryList = actualCallActivityBehavior.mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior15() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry2);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior("Process Definition Key", mapExceptions,
        new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    assertEquals("Process Definition Key", actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.processDefinitionExpression);
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    List<MapExceptionEntry> mapExceptionEntryList = actualCallActivityBehavior.mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
    assertSame(mapExceptionEntry2, mapExceptionEntryList.get(1));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior16() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior("Process Definition Key", mapExceptions,
        new VariablesPropagator(mock(CopyVariablesCalculator.class)));

    // Assert
    assertEquals("Process Definition Key", actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.processDefinitionExpression);
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(actualCallActivityBehavior.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());

    // Act and Assert
    assertTrue(callActivityBehavior.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects2() {
    // Arrange, Act and Assert
    assertTrue(
        (new CallActivityBehavior("Process Definition Key", new ArrayList<>())).processDataObjects(null).isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects3() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());

    LinkedHashSet<ValuedDataObject> dataObjects = new LinkedHashSet<>();
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = callActivityBehavior.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects4() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(new BooleanDataObject());
    dataObjects.add(new BooleanDataObject());

    // Act
    Map<String, Object> actualProcessDataObjectsResult = callActivityBehavior.processDataObjects(dataObjects);

    // Assert
    assertEquals(1, actualProcessDataObjectsResult.size());
    assertNull(actualProcessDataObjectsResult.get(null));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects5() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression processDefinitionExpression = new JuelExpression(
        new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text");

    CallActivityBehavior callActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        new ArrayList<>());

    // Act and Assert
    assertTrue(callActivityBehavior.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#initializeVariables(ExecutionEntity, Map)}
   */
  @Test
  public void testInitializeVariables() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());
    ExecutionEntityImpl subProcessInstance = mock(ExecutionEntityImpl.class);
    doNothing().when(subProcessInstance).setVariables(Mockito.<Map<String, Object>>any());

    // Act
    callActivityBehavior.initializeVariables(subProcessInstance, new HashMap<>());

    // Assert that nothing has changed
    verify(subProcessInstance).setVariables(isA(Map.class));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#calculateInboundVariables(DelegateExecution, ProcessDefinition)}
   */
  @Test
  public void testCalculateInboundVariables() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(callActivityBehavior.calculateInboundVariables(execution, new ProcessDefinitionEntityImpl()).isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#calculateInboundVariables(DelegateExecution, ProcessDefinition)}
   */
  @Test
  public void testCalculateInboundVariables2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression processDefinitionExpression = new JuelExpression(
        new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text");

    CallActivityBehavior callActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(callActivityBehavior.calculateInboundVariables(execution, new ProcessDefinitionEntityImpl()).isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}
   */
  @Test
  public void testCopyProcessVariables() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExpressionManager expressionManager = new ExpressionManager();
    CallActivity callActivity = new CallActivity();
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    Map<String, Object> actualCopyProcessVariablesResult = callActivityBehavior.copyProcessVariables(execution,
        expressionManager, callActivity, variables);

    // Assert
    assertTrue(variables.isEmpty());
    assertTrue(callActivity.getInParameters().isEmpty());
    assertTrue(callActivity.getAttributes().isEmpty());
    assertTrue(callActivity.getExtensionElements().isEmpty());
    assertTrue(actualCopyProcessVariablesResult.isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertSame(variables, actualCopyProcessVariablesResult);
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}
   */
  @Test
  public void testCopyProcessVariables2() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    IOParameter ioParameter = new IOParameter();
    ioParameter.setSourceExpression(null);

    ArrayList<IOParameter> inParameters = new ArrayList<>();
    inParameters.add(ioParameter);

    CallActivity callActivity = new CallActivity();
    callActivity.setInParameters(inParameters);
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    Map<String, Object> actualCopyProcessVariablesResult = callActivityBehavior.copyProcessVariables(execution,
        expressionManager, callActivity, variables);

    // Assert
    assertEquals(1, variables.size());
    assertNull(variables.get(null));
    assertEquals(1, actualCopyProcessVariablesResult.size());
    assertNull(actualCopyProcessVariablesResult.get(null));
    assertTrue(callActivity.getAttributes().isEmpty());
    assertTrue(callActivity.getExtensionElements().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertSame(inParameters, callActivity.getInParameters());
    assertSame(variables, actualCopyProcessVariablesResult);
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}
   */
  @Test
  public void testCopyProcessVariables3() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ExpressionManager expressionManager = new ExpressionManager();
    CallActivity callActivity = new CallActivity();

    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    Map<String, Object> actualCopyProcessVariablesResult = callActivityBehavior.copyProcessVariables(execution,
        expressionManager, callActivity, variables);

    // Assert
    assertTrue(variables.isEmpty());
    assertTrue(callActivity.getInParameters().isEmpty());
    assertTrue(callActivity.getAttributes().isEmpty());
    assertTrue(callActivity.getExtensionElements().isEmpty());
    assertTrue(actualCopyProcessVariablesResult.isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertSame(variables, actualCopyProcessVariablesResult);
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}
   */
  @Test
  public void testCopyProcessVariables4() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    IOParameter ioParameter = new IOParameter();
    ioParameter.setSourceExpression("");

    ArrayList<IOParameter> inParameters = new ArrayList<>();
    inParameters.add(ioParameter);

    CallActivity callActivity = new CallActivity();
    callActivity.setInParameters(inParameters);
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    Map<String, Object> actualCopyProcessVariablesResult = callActivityBehavior.copyProcessVariables(execution,
        expressionManager, callActivity, variables);

    // Assert
    assertEquals(1, variables.size());
    assertNull(variables.get(null));
    assertEquals(1, actualCopyProcessVariablesResult.size());
    assertNull(actualCopyProcessVariablesResult.get(null));
    assertTrue(callActivity.getAttributes().isEmpty());
    assertTrue(callActivity.getExtensionElements().isEmpty());
    assertTrue(execution.getTransientVariables().isEmpty());
    assertTrue(execution.getTransientVariablesLocal().isEmpty());
    assertTrue(execution.getUsedVariablesCache().isEmpty());
    assertTrue(execution.getVariableInstanceEntities().isEmpty());
    assertTrue(execution.getVariableInstances().isEmpty());
    assertTrue(execution.getVariableInstancesLocal().isEmpty());
    assertTrue(execution.getVariables().isEmpty());
    assertTrue(execution.getVariablesLocal().isEmpty());
    assertSame(inParameters, callActivity.getInParameters());
    assertSame(variables, actualCopyProcessVariablesResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CallActivityBehavior#setProcessDefinitionKey(String)}
   *   <li>{@link CallActivityBehavior#getProcessDefinitionKey()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());

    // Act
    callActivityBehavior.setProcessDefinitionKey("Process Definition Key");

    // Assert that nothing has changed
    assertEquals("Process Definition Key", callActivityBehavior.getProcessDefinitionKey());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}
   */
  @Test
  public void testNewCallActivityBehavior() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        new ArrayList<>());

    // Assert
    Expression expression = actualCallActivityBehavior.processDefinitionExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(actualCallActivityBehavior.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}
   */
  @Test
  public void testNewCallActivityBehavior2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression processDefinitionExpression = new JuelExpression(
        new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text");

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        new ArrayList<>());

    // Assert
    Expression expression = actualCallActivityBehavior.processDefinitionExpression;
    assertTrue(expression instanceof JuelExpression);
    assertEquals("Expression Text", expression.getExpressionText());
    assertNull(actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(actualCallActivityBehavior.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}
   */
  @Test
  public void testNewCallActivityBehavior3() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        mapExceptions);

    // Assert
    Expression expression = actualCallActivityBehavior.processDefinitionExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    List<MapExceptionEntry> mapExceptionEntryList = actualCallActivityBehavior.mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}
   */
  @Test
  public void testNewCallActivityBehavior4() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry2);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        mapExceptions);

    // Assert
    Expression expression = actualCallActivityBehavior.processDefinitionExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    List<MapExceptionEntry> mapExceptionEntryList = actualCallActivityBehavior.mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
    assertSame(mapExceptionEntry2, mapExceptionEntryList.get(1));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior5() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        mapExceptions, new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    Expression expression = actualCallActivityBehavior.processDefinitionExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(actualCallActivityBehavior.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior6() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression processDefinitionExpression = new JuelExpression(
        new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text");

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        mapExceptions, new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    Expression expression = actualCallActivityBehavior.processDefinitionExpression;
    assertTrue(expression instanceof JuelExpression);
    assertEquals("Expression Text", expression.getExpressionText());
    assertNull(actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(actualCallActivityBehavior.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior7() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        mapExceptions, new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    Expression expression = actualCallActivityBehavior.processDefinitionExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    List<MapExceptionEntry> mapExceptionEntryList = actualCallActivityBehavior.mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior8() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry2);

    // Act
    CallActivityBehavior actualCallActivityBehavior = new CallActivityBehavior(processDefinitionExpression,
        mapExceptions, new VariablesPropagator(new CopyVariablesCalculator()));

    // Assert
    Expression expression = actualCallActivityBehavior.processDefinitionExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCallActivityBehavior.getProcessDefinitionKey());
    assertNull(actualCallActivityBehavior.getMultiInstanceActivityBehavior());
    List<MapExceptionEntry> mapExceptionEntryList = actualCallActivityBehavior.mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertFalse(actualCallActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualCallActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
    assertSame(mapExceptionEntry2, mapExceptionEntryList.get(1));
  }
}
