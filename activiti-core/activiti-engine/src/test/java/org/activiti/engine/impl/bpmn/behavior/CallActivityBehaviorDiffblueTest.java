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
import org.activiti.engine.impl.persistence.entity.VariableScopeImpl;
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
   * Test {@link CallActivityBehavior#CallActivityBehavior(String, List)}.
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List)}
   */
  @Test
  public void testNewCallActivityBehavior() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = (new CallActivityBehavior("Process Definition Key",
        mapExceptions)).mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Test
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}.
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior2() {
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
   * Test {@link CallActivityBehavior#CallActivityBehavior(String, List)}.
   * <ul>
   *   <li>Given {@link MapExceptionEntry}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List)}
   */
  @Test
  public void testNewCallActivityBehavior_givenMapExceptionEntry() {
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
   * Test {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}.
   * <ul>
   *   <li>Then {@link CallActivityBehavior#processDefinitionExpression} return
   * {@link FixedValue}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}
   */
  @Test
  public void testNewCallActivityBehavior_thenProcessDefinitionExpressionReturnFixedValue() {
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
   * Test
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}.
   * <ul>
   *   <li>Then {@link CallActivityBehavior#processDefinitionExpression} return
   * {@link FixedValue}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior_thenProcessDefinitionExpressionReturnFixedValue2() {
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
   * Test {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}.
   * <ul>
   *   <li>Then {@link CallActivityBehavior#processDefinitionExpression} return
   * {@link JuelExpression}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}
   */
  @Test
  public void testNewCallActivityBehavior_thenProcessDefinitionExpressionReturnJuelExpression() {
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
   * Test
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}.
   * <ul>
   *   <li>Then {@link CallActivityBehavior#processDefinitionExpression} return
   * {@link JuelExpression}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior_thenProcessDefinitionExpressionReturnJuelExpression2() {
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
   * Test {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}.
   * <ul>
   *   <li>Then return {@link CallActivityBehavior#mapExceptions} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}
   */
  @Test
  public void testNewCallActivityBehavior_thenReturnMapExceptionsSizeIsOne() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = (new CallActivityBehavior(processDefinitionExpression,
        mapExceptions)).mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Test
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}.
   * <ul>
   *   <li>Then return {@link CallActivityBehavior#mapExceptions} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior_thenReturnMapExceptionsSizeIsOne2() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = (new CallActivityBehavior(processDefinitionExpression,
        mapExceptions, new VariablesPropagator(new CopyVariablesCalculator()))).mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Test
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}.
   * <ul>
   *   <li>Then return {@link CallActivityBehavior#mapExceptions} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior_thenReturnMapExceptionsSizeIsOne3() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = (new CallActivityBehavior("Process Definition Key", mapExceptions,
        new VariablesPropagator(new CopyVariablesCalculator()))).mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Test {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}.
   * <ul>
   *   <li>Then return {@link CallActivityBehavior#mapExceptions} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List)}
   */
  @Test
  public void testNewCallActivityBehavior_thenReturnMapExceptionsSizeIsTwo() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = (new CallActivityBehavior(processDefinitionExpression,
        mapExceptions)).mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(1));
  }

  /**
   * Test
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}.
   * <ul>
   *   <li>Then return {@link CallActivityBehavior#mapExceptions} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(Expression, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior_thenReturnMapExceptionsSizeIsTwo2() {
    // Arrange
    FixedValue processDefinitionExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = (new CallActivityBehavior(processDefinitionExpression,
        mapExceptions, new VariablesPropagator(new CopyVariablesCalculator()))).mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(1));
  }

  /**
   * Test {@link CallActivityBehavior#CallActivityBehavior(String, List)}.
   * <ul>
   *   <li>Then return {@link CallActivityBehavior#mapExceptions} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List)}
   */
  @Test
  public void testNewCallActivityBehavior_thenReturnMapExceptionsSizeIsTwo3() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = (new CallActivityBehavior("Process Definition Key",
        mapExceptions)).mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(1));
  }

  /**
   * Test
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}.
   * <ul>
   *   <li>Then return {@link CallActivityBehavior#mapExceptions} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior_thenReturnMapExceptionsSizeIsTwo4() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = (new CallActivityBehavior("Process Definition Key", mapExceptions,
        new VariablesPropagator(new CopyVariablesCalculator()))).mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(1));
  }

  /**
   * Test {@link CallActivityBehavior#CallActivityBehavior(String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@link CallActivityBehavior#mapExceptions} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List)}
   */
  @Test
  public void testNewCallActivityBehavior_whenArrayList_thenReturnMapExceptionsEmpty() {
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
   * Test
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code Process Definition Key}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#CallActivityBehavior(String, List, VariablesPropagator)}
   */
  @Test
  public void testNewCallActivityBehavior_whenArrayList_thenReturnProcessDefinitionKey() {
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
   * Test getters and setters.
   * <p>
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
   * Test {@link CallActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_givenJavaLangObject_whenArrayList_thenReturnEmpty() {
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
   * Test {@link CallActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BooleanDataObject} (default
   * constructor).</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenArrayListAddBooleanDataObject_thenReturnSizeIsOne() {
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
   * Test {@link CallActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenArrayList_thenReturnEmpty() {
    // Arrange
    CallActivityBehavior callActivityBehavior = new CallActivityBehavior("Process Definition Key", new ArrayList<>());

    // Act and Assert
    assertTrue(callActivityBehavior.processDataObjects(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link CallActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link BooleanDataObject}
   * (default constructor).</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenLinkedHashSetAddBooleanDataObject_thenReturnSizeIsOne() {
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
   * Test {@link CallActivityBehavior#processDataObjects(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#processDataObjects(Collection)}
   */
  @Test
  public void testProcessDataObjects_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        (new CallActivityBehavior("Process Definition Key", new ArrayList<>())).processDataObjects(null).isEmpty());
  }

  /**
   * Test {@link CallActivityBehavior#initializeVariables(ExecutionEntity, Map)}.
   * <ul>
   *   <li>Then calls {@link VariableScopeImpl#setVariables(Map)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#initializeVariables(ExecutionEntity, Map)}
   */
  @Test
  public void testInitializeVariables_thenCallsSetVariables() {
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
   * Test
   * {@link CallActivityBehavior#calculateInboundVariables(DelegateExecution, ProcessDefinition)}.
   * <p>
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
   * Test
   * {@link CallActivityBehavior#calculateInboundVariables(DelegateExecution, ProcessDefinition)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#calculateInboundVariables(DelegateExecution, ProcessDefinition)}
   */
  @Test
  public void testCalculateInboundVariables_givenJavaLangObject() {
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
   * Test
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}
   */
  @Test
  public void testCopyProcessVariables_givenFoo_whenHashMapComputeIfPresentFooAndBiFunction() {
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
    assertTrue(actualCopyProcessVariablesResult.isEmpty());
  }

  /**
   * Test
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}.
   * <ul>
   *   <li>Given {@link IOParameter} (default constructor) SourceExpression is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}
   */
  @Test
  public void testCopyProcessVariables_givenIOParameterSourceExpressionIsEmptyString() {
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
    assertSame(variables, actualCopyProcessVariablesResult);
  }

  /**
   * Test
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}.
   * <ul>
   *   <li>Given {@link IOParameter} (default constructor) SourceExpression is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}
   */
  @Test
  public void testCopyProcessVariables_givenIOParameterSourceExpressionIsNull() {
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
    assertSame(variables, actualCopyProcessVariablesResult);
  }

  /**
   * Test
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}.
   * <ul>
   *   <li>When {@link CallActivity} (default constructor).</li>
   *   <li>Then {@link HashMap#HashMap()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityBehavior#copyProcessVariables(DelegateExecution, ExpressionManager, CallActivity, Map)}
   */
  @Test
  public void testCopyProcessVariables_whenCallActivity_thenHashMapEmpty() {
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
    assertTrue(actualCopyProcessVariablesResult.isEmpty());
  }
}
