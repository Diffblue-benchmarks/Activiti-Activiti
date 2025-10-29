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
package org.activiti.engine.impl.bpmn.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.bpmn.model.Task;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClassDelegateDiffblueTest {
  @InjectMocks
  private ClassDelegate classDelegate;

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}
   */
  @Test
  public void testNewClassDelegate8() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression,
        mapExceptions);

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualClassDelegate.serviceTaskId);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertTrue(mapExceptions.isEmpty());
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
    assertTrue(actualClassDelegate.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}
   */
  @Test
  public void testNewClassDelegate9() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression,
        mapExceptions);

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualClassDelegate.serviceTaskId);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertTrue(mapExceptions.isEmpty());
    assertTrue(actualClassDelegate.mapExceptions.isEmpty());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}
   */
  @Test
  public void testNewClassDelegate10() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration2);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression,
        mapExceptions);

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualClassDelegate.serviceTaskId);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertTrue(mapExceptions.isEmpty());
    assertTrue(actualClassDelegate.mapExceptions.isEmpty());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
    assertSame(fieldDeclaration2, fieldDeclarationList.get(1));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}
   */
  @Test
  public void testNewClassDelegate11() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression skipExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression,
        mapExceptions);

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof JuelExpression);
    assertEquals("42", actualClassDelegate.serviceTaskId);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("Expression Text", expression.getExpressionText());
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertTrue(mapExceptions.isEmpty());
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
    assertTrue(actualClassDelegate.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}
   */
  @Test
  public void testNewClassDelegate12() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression,
        mapExceptions);

    // Assert
    assertEquals(1, mapExceptions.size());
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualClassDelegate.serviceTaskId);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<MapExceptionEntry> mapExceptionEntryList = actualClassDelegate.mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}
   */
  @Test
  public void testNewClassDelegate13() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry2);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression,
        mapExceptions);

    // Assert
    assertEquals(2, mapExceptions.size());
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualClassDelegate.serviceTaskId);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<MapExceptionEntry> mapExceptionEntryList = actualClassDelegate.mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
    assertSame(mapExceptionEntry2, mapExceptionEntryList.get(1));
  }

  /**
   * Method under test: {@link ClassDelegate#ClassDelegate(String, List)}
   */
  @Test
  public void testNewClassDelegate14() {
    // Arrange and Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", new ArrayList<>());

    // Assert
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.skipExpression);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
  }

  /**
   * Method under test: {@link ClassDelegate#ClassDelegate(String, List)}
   */
  @Test
  public void testNewClassDelegate15() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations);

    // Assert
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.skipExpression);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Method under test: {@link ClassDelegate#ClassDelegate(String, List)}
   */
  @Test
  public void testNewClassDelegate16() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration2);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations);

    // Assert
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.skipExpression);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
    assertSame(fieldDeclaration2, fieldDeclarationList.get(1));
  }

  /**
   * Method under test: {@link ClassDelegate#ClassDelegate(String, List)}
   */
  @Test
  public void testNewClassDelegate17() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(mock(FieldDeclaration.class));

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations);

    // Assert
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.skipExpression);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertEquals(1, actualClassDelegate.fieldDeclarations.size());
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, List, Expression)}
   */
  @Test
  public void testNewClassDelegate18() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations,
        new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, List, Expression)}
   */
  @Test
  public void testNewClassDelegate19() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations,
        new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, List, Expression)}
   */
  @Test
  public void testNewClassDelegate20() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration2);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations,
        new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
    assertSame(fieldDeclaration2, fieldDeclarationList.get(1));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(String, List, Expression)}
   */
  @Test
  public void testNewClassDelegate21() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations,
        new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text"));

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof JuelExpression);
    assertEquals("Class Name", actualClassDelegate.getClassName());
    assertEquals("Expression Text", expression.getExpressionText());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
  }

  /**
   * Method under test: {@link ClassDelegate#notify(DelegateExecution)}
   */
  @Test
  public void testNotify() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test: {@link ClassDelegate#notify(DelegateExecution)}
   */
  @Test
  public void testNotify2() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration(
        "Trying to load class with current thread context classloader: {}",
        "Trying to load class with current thread context classloader: {}", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#notify(String, String, FlowElement, Map, Map)}
   */
  @Test
  public void testNotify3() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    AdhocSubProcess flowElement = new AdhocSubProcess();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify("42", "42", flowElement, executionVariables, new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#notify(String, String, FlowElement, Map, Map)}
   */
  @Test
  public void testNotify4() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration(
        "Trying to load class with current thread context classloader: {}",
        "Trying to load class with current thread context classloader: {}", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);
    AdhocSubProcess flowElement = new AdhocSubProcess();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify("42", "42", flowElement, executionVariables, new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#notify(String, String, Task, Map, Map)}
   */
  @Test
  public void testNotify5() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify("42", "42", task, executionVariables, new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#notify(String, String, Task, Map, Map)}
   */
  @Test
  public void testNotify6() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration(
        "Trying to load class with current thread context classloader: {}",
        "Trying to load class with current thread context classloader: {}", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify("42", "42", task, executionVariables, new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap2() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration(
        "Trying to load class with current thread context classloader: {}",
        "Trying to load class with current thread context classloader: {}", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test: {@link ClassDelegate#execute(DelegateExecution)}
   */
  @Test
  public void testExecute() {
    // Arrange
    Class<Object> clazz = Object.class;
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations, new FixedValue(true));
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn(true);

    // Act
    classDelegate.execute(execution);

    // Assert
    verify(execution).getVariable(eq("_ACTIVITI_SKIP_EXPRESSION_ENABLED"));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#trigger(DelegateExecution, String, Object)}
   */
  @Test
  public void testTrigger() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> classDelegate
        .trigger(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Signal Name", JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#trigger(DelegateExecution, String, Object)}
   */
  @Test
  public void testTrigger2() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration(
        "Trying to load class with current thread context classloader: {}",
        "Trying to load class with current thread context classloader: {}", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> classDelegate
        .trigger(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Signal Name", JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#completing(DelegateExecution, DelegateExecution)}
   */
  @Test
  public void testCompleting() throws Exception {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.completing(execution, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#completing(DelegateExecution, DelegateExecution)}
   */
  @Test
  public void testCompleting2() throws Exception {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration(
        "Trying to load class with current thread context classloader: {}",
        "Trying to load class with current thread context classloader: {}", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.completing(execution, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test: {@link ClassDelegate#completed(DelegateExecution)}
   */
  @Test
  public void testCompleted() throws Exception {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.completed(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test: {@link ClassDelegate#completed(DelegateExecution)}
   */
  @Test
  public void testCompleted2() throws Exception {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration(
        "Trying to load class with current thread context classloader: {}",
        "Trying to load class with current thread context classloader: {}", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> classDelegate.completed(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test: {@link ClassDelegate#determineBehaviour(ActivityBehavior)}
   */
  @Test
  public void testDetermineBehaviour() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    ActivityBehavior delegateInstance = mock(ActivityBehavior.class);
    doNothing().when(delegateInstance).execute(Mockito.<DelegateExecution>any());

    // Act
    ActivityBehavior actualDetermineBehaviourResult = classDelegate.determineBehaviour(delegateInstance);
    actualDetermineBehaviourResult.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(delegateInstance).execute(isA(DelegateExecution.class));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#defaultInstantiateDelegate(Class, List)}
   */
  @Test
  public void testDefaultInstantiateDelegate() {
    // Arrange
    Class<Object> clazz = Object.class;

    FieldDeclaration fieldDeclaration = new FieldDeclaration(
        "Trying to load class with current thread context classloader: {}",
        "Trying to load class with current thread context classloader: {}", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.defaultInstantiateDelegate(clazz, fieldDeclarations));
  }

  /**
   * Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object)}
   */
  @Test
  public void testApplyFieldDeclaration() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(fieldDeclarations, JSONObject.NULL));
  }

  /**
   * Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object)}
   */
  @Test
  public void testApplyFieldDeclaration2() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(fieldDeclarations, new FieldDeclaration()));
  }

  /**
   * Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object)}
   */
  @Test
  public void testApplyFieldDeclaration3() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue("42");

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();

    // Act
    ClassDelegate.applyFieldDeclaration(fieldDeclarations, fieldDeclaration2);

    // Assert
    assertEquals("42", fieldDeclaration2.getName());
  }

  /**
   * Method under test:
   * {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)}
   */
  @Test
  public void testApplyFieldDeclaration4() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(fieldDeclarations, JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)}
   */
  @Test
  public void testApplyFieldDeclaration5() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(fieldDeclarations, new FieldDeclaration(), true));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)}
   */
  @Test
  public void testApplyFieldDeclaration6() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue("42");

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();

    // Act
    ClassDelegate.applyFieldDeclaration(fieldDeclarations, fieldDeclaration2, true);

    // Assert
    assertEquals("42", fieldDeclaration2.getName());
  }

  /**
   * Method under test:
   * {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object)}
   */
  @Test
  public void testApplyFieldDeclaration7() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    declaration.setValue(42);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(declaration, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object, boolean)}
   */
  @Test
  public void testApplyFieldDeclaration8() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    declaration.setValue(42);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(declaration, JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#fieldTypeCompatible(FieldDeclaration, Field)}
   */
  @Test
  public void testFieldTypeCompatible() {
    // Arrange, Act and Assert
    assertTrue(ClassDelegate.fieldTypeCompatible(new FieldDeclaration(), null));
  }

  /**
   * Method under test: {@link ClassDelegate#getClassName()}
   */
  @Test
  public void testGetClassName() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals("java.lang.Object", (new ClassDelegate(clazz, new ArrayList<>())).getClassName());
  }

  /**
   * Method under test: {@link ClassDelegate#ClassDelegate(Class, List)}
   */
  @Test
  public void testNewClassDelegate() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Assert
    assertEquals("java.lang.Object", actualClassDelegate.getClassName());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.skipExpression);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
  }

  /**
   * Method under test: {@link ClassDelegate#ClassDelegate(Class, List)}
   */
  @Test
  public void testNewClassDelegate2() {
    // Arrange
    Class<Object> clazz = Object.class;

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Assert
    assertEquals("java.lang.Object", actualClassDelegate.getClassName());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.skipExpression);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Method under test: {@link ClassDelegate#ClassDelegate(Class, List)}
   */
  @Test
  public void testNewClassDelegate3() {
    // Arrange
    Class<Object> clazz = Object.class;

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration2);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Assert
    assertEquals("java.lang.Object", actualClassDelegate.getClassName());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.skipExpression);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
    assertSame(fieldDeclaration2, fieldDeclarationList.get(1));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(Class, List, Expression)}
   */
  @Test
  public void testNewClassDelegate4() {
    // Arrange
    Class<Object> clazz = Object.class;
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("java.lang.Object", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(Class, List, Expression)}
   */
  @Test
  public void testNewClassDelegate5() {
    // Arrange
    Class<Object> clazz = Object.class;

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("java.lang.Object", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(Class, List, Expression)}
   */
  @Test
  public void testNewClassDelegate6() {
    // Arrange
    Class<Object> clazz = Object.class;

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration2);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("java.lang.Object", actualClassDelegate.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
    assertSame(fieldDeclaration2, fieldDeclarationList.get(1));
  }

  /**
   * Method under test:
   * {@link ClassDelegate#ClassDelegate(Class, List, Expression)}
   */
  @Test
  public void testNewClassDelegate7() {
    // Arrange
    Class<Object> clazz = Object.class;
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, fieldDeclarations,
        new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text"));

    // Assert
    Expression expression = actualClassDelegate.skipExpression;
    assertTrue(expression instanceof JuelExpression);
    assertEquals("Expression Text", expression.getExpressionText());
    assertEquals("java.lang.Object", actualClassDelegate.getClassName());
    assertNull(actualClassDelegate.serviceTaskId);
    assertNull(actualClassDelegate.mapExceptions);
    assertNull(actualClassDelegate.customPropertiesResolverInstance);
    assertNull(actualClassDelegate.executionListenerInstance);
    assertNull(actualClassDelegate.taskListenerInstance);
    assertNull(actualClassDelegate.transactionDependentExecutionListenerInstance);
    assertNull(actualClassDelegate.transactionDependentTaskListenerInstance);
    assertNull(actualClassDelegate.getMultiInstanceActivityBehavior());
    assertNull(actualClassDelegate.activityBehaviorInstance);
    assertTrue(actualClassDelegate.fieldDeclarations.isEmpty());
  }
}
