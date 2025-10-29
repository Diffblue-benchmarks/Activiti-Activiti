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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultClassDelegateFactoryDiffblueTest {
  @InjectMocks
  private DefaultClassDelegateFactory defaultClassDelegateFactory;

  /**
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreate() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("42", "Class Name", fieldDeclarations,
        skipExpression, mapExceptions);

    // Assert
    Expression expression = actualCreateResult.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualCreateResult.serviceTaskId);
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    assertTrue(mapExceptions.isEmpty());
    assertTrue(actualCreateResult.fieldDeclarations.isEmpty());
    assertTrue(actualCreateResult.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreate2() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("42", "Class Name", fieldDeclarations,
        skipExpression, mapExceptions);

    // Assert
    Expression expression = actualCreateResult.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualCreateResult.serviceTaskId);
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualCreateResult.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertTrue(mapExceptions.isEmpty());
    assertTrue(actualCreateResult.mapExceptions.isEmpty());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreate3() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration2);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("42", "Class Name", fieldDeclarations,
        skipExpression, mapExceptions);

    // Assert
    Expression expression = actualCreateResult.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualCreateResult.serviceTaskId);
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualCreateResult.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertTrue(mapExceptions.isEmpty());
    assertTrue(actualCreateResult.mapExceptions.isEmpty());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
    assertSame(fieldDeclaration2, fieldDeclarationList.get(1));
  }

  /**
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreate4() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression skipExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("42", "Class Name", fieldDeclarations,
        skipExpression, mapExceptions);

    // Assert
    Expression expression = actualCreateResult.skipExpression;
    assertTrue(expression instanceof JuelExpression);
    assertEquals("42", actualCreateResult.serviceTaskId);
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertEquals("Expression Text", expression.getExpressionText());
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    assertTrue(mapExceptions.isEmpty());
    assertTrue(actualCreateResult.fieldDeclarations.isEmpty());
    assertTrue(actualCreateResult.mapExceptions.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreate5() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("42", "Class Name", fieldDeclarations,
        skipExpression, mapExceptions);

    // Assert
    assertEquals(1, mapExceptions.size());
    Expression expression = actualCreateResult.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualCreateResult.serviceTaskId);
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    List<MapExceptionEntry> mapExceptionEntryList = actualCreateResult.mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertTrue(actualCreateResult.fieldDeclarations.isEmpty());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreate6() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry2);

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("42", "Class Name", fieldDeclarations,
        skipExpression, mapExceptions);

    // Assert
    assertEquals(2, mapExceptions.size());
    Expression expression = actualCreateResult.skipExpression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("42", actualCreateResult.serviceTaskId);
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertEquals("null", expression.getExpressionText());
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    List<MapExceptionEntry> mapExceptionEntryList = actualCreateResult.mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertTrue(actualCreateResult.fieldDeclarations.isEmpty());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
    assertSame(mapExceptionEntry2, mapExceptionEntryList.get(1));
  }

  /**
   * Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  public void testCreate7() {
    // Arrange and Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("Class Name", new ArrayList<>());

    // Assert
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertNull(actualCreateResult.serviceTaskId);
    assertNull(actualCreateResult.mapExceptions);
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.skipExpression);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    assertTrue(actualCreateResult.fieldDeclarations.isEmpty());
  }

  /**
   * Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  public void testCreate8() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("Class Name", fieldDeclarations);

    // Assert
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertNull(actualCreateResult.serviceTaskId);
    assertNull(actualCreateResult.mapExceptions);
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.skipExpression);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualCreateResult.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  public void testCreate9() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration2);

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("Class Name", fieldDeclarations);

    // Assert
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertNull(actualCreateResult.serviceTaskId);
    assertNull(actualCreateResult.mapExceptions);
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.skipExpression);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    List<FieldDeclaration> fieldDeclarationList = actualCreateResult.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
    assertSame(fieldDeclaration2, fieldDeclarationList.get(1));
  }

  /**
   * Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  public void testCreate10() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(mock(FieldDeclaration.class));

    // Act
    ClassDelegate actualCreateResult = defaultClassDelegateFactory.create("Class Name", fieldDeclarations);

    // Assert
    assertEquals("Class Name", actualCreateResult.getClassName());
    assertNull(actualCreateResult.serviceTaskId);
    assertNull(actualCreateResult.mapExceptions);
    assertNull(actualCreateResult.customPropertiesResolverInstance);
    assertNull(actualCreateResult.executionListenerInstance);
    assertNull(actualCreateResult.skipExpression);
    assertNull(actualCreateResult.taskListenerInstance);
    assertNull(actualCreateResult.transactionDependentExecutionListenerInstance);
    assertNull(actualCreateResult.transactionDependentTaskListenerInstance);
    assertNull(actualCreateResult.getMultiInstanceActivityBehavior());
    assertNull(actualCreateResult.activityBehaviorInstance);
    assertEquals(1, actualCreateResult.fieldDeclarations.size());
  }
}
