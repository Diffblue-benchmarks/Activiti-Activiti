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
   * Test {@link DefaultClassDelegateFactory#create(String, List)} with
   * {@code className}, {@code fieldDeclarations}.
   * <p>
   * Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  public void testCreateWithClassNameFieldDeclarations() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    List<FieldDeclaration> fieldDeclarationList = defaultClassDelegateFactory.create("Class Name",
        fieldDeclarations).fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test {@link DefaultClassDelegateFactory#create(String, List)} with
   * {@code className}, {@code fieldDeclarations}.
   * <ul>
   *   <li>Given {@link FieldDeclaration}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  public void testCreateWithClassNameFieldDeclarations_givenFieldDeclaration() {
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

  /**
   * Test {@link DefaultClassDelegateFactory#create(String, List)} with
   * {@code className}, {@code fieldDeclarations}.
   * <ul>
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  public void testCreateWithClassNameFieldDeclarations_thenReturnFieldDeclarationsEmpty() {
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
   * Test {@link DefaultClassDelegateFactory#create(String, List)} with
   * {@code className}, {@code fieldDeclarations}.
   * <ul>
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  public void testCreateWithClassNameFieldDeclarations_thenReturnFieldDeclarationsSizeIsTwo() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    List<FieldDeclaration> fieldDeclarationList = defaultClassDelegateFactory.create("Class Name",
        fieldDeclarations).fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   * with {@code id}, {@code className}, {@code fieldDeclarations},
   * {@code skipExpression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    defaultClassDelegateFactory.create("42", "Class Name", fieldDeclarations, skipExpression, mapExceptions);

    // Assert
    assertTrue(mapExceptions.isEmpty());
  }

  /**
   * Test
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   * with {@code id}, {@code className}, {@code fieldDeclarations},
   * {@code skipExpression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions2() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    List<FieldDeclaration> fieldDeclarationList = defaultClassDelegateFactory.create("42", "Class Name",
        fieldDeclarations, skipExpression, new ArrayList<>()).fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   * with {@code id}, {@code className}, {@code fieldDeclarations},
   * {@code skipExpression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions3() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    List<FieldDeclaration> fieldDeclarationList = defaultClassDelegateFactory.create("42", "Class Name",
        fieldDeclarations, skipExpression, new ArrayList<>()).fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   * with {@code id}, {@code className}, {@code fieldDeclarations},
   * {@code skipExpression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions4() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression skipExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act and Assert
    Expression expression = defaultClassDelegateFactory.create("42", "Class Name", fieldDeclarations, skipExpression,
        mapExceptions).skipExpression;
    assertTrue(expression instanceof JuelExpression);
    assertEquals("Expression Text", expression.getExpressionText());
    assertTrue(mapExceptions.isEmpty());
  }

  /**
   * Test
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   * with {@code id}, {@code className}, {@code fieldDeclarations},
   * {@code skipExpression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions5() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = defaultClassDelegateFactory.create("42", "Class Name",
        fieldDeclarations, skipExpression, mapExceptions).mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Test
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   * with {@code id}, {@code className}, {@code fieldDeclarations},
   * {@code skipExpression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)}
   */
  @Test
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions6() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    MapExceptionEntry mapExceptionEntry = new MapExceptionEntry("An error occurred", "Class Name", true);

    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList = defaultClassDelegateFactory.create("42", "Class Name",
        fieldDeclarations, skipExpression, mapExceptions).mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(1));
  }
}
