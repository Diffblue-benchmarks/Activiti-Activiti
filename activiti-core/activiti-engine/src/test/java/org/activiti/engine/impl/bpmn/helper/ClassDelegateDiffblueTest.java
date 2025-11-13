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
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.bpmn.model.Task;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ClassDelegateDiffblueTest {
  /**
   * Test {@link ClassDelegate#ClassDelegate(Class, List)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is one.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(Class, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(Class, List)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsOne() {
    // Arrange
    Class<Object> clazz = Object.class;

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(Class, List, Expression)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is one.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(Class, List, Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(Class, List, Expression)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsOne2() {
    // Arrange
    Class<Object> clazz = Object.class;

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate(clazz, fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is one.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, String, List, Expression,
   * List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, String, List, Expression, List)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsOne3() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression, new ArrayList<>());

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, List)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is one.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, List)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsOne4() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations);

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, List, Expression)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is one.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, List, Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, List, Expression)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsOne5() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate("Class Name", fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(Class, List)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(Class, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(Class, List)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsTwo() {
    // Arrange
    Class<Object> clazz = Object.class;

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(Class, List, Expression)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(Class, List, Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(Class, List, Expression)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsTwo2() {
    // Arrange
    Class<Object> clazz = Object.class;

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate(clazz, fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, String, List, Expression,
   * List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, String, List, Expression, List)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsTwo3() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression, new ArrayList<>());

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, List)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, List)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsTwo4() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate = new ClassDelegate("Class Name", fieldDeclarations);

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, List, Expression)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, List, Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, List, Expression)"})
  public void testNewClassDelegate_givenFieldDeclaration_thenReturnFieldDeclarationsSizeIsTwo5() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate("Class Name", fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    List<FieldDeclaration> fieldDeclarationList = actualClassDelegate.fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}.
   *
   * <ul>
   *   <li>Then return {@link ClassDelegate#mapExceptions} size is one.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, String, List, Expression,
   * List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, String, List, Expression, List)"})
  public void testNewClassDelegate_thenReturnMapExceptionsSizeIsOne() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry);

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression, mapExceptions);

    // Assert
    List<MapExceptionEntry> mapExceptionEntryList = actualClassDelegate.mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}.
   *
   * <ul>
   *   <li>Then return {@link ClassDelegate#mapExceptions} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, String, List, Expression,
   * List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, String, List, Expression, List)"})
  public void testNewClassDelegate_thenReturnMapExceptionsSizeIsTwo() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry2);

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression, mapExceptions);

    // Assert
    List<MapExceptionEntry> mapExceptionEntryList = actualClassDelegate.mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry2, mapExceptionEntryList.get(1));
  }

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code Class Name}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, List)"})
  public void testNewClassDelegate_whenArrayList_thenReturnClassName() {
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
   * Test {@link ClassDelegate#ClassDelegate(Class, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return ClassName is {@code Object}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(Class, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(Class, List)"})
  public void testNewClassDelegate_whenArrayList_thenReturnClassNameIsJavaLangObject() {
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
   * Test {@link ClassDelegate#ClassDelegate(Class, List, Expression)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ClassDelegate#skipExpression} return {@link FixedValue}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(Class, List, Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(Class, List, Expression)"})
  public void testNewClassDelegate_whenArrayList_thenSkipExpressionReturnFixedValue() {
    // Arrange
    Class<Object> clazz = Object.class;
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate(clazz, fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    assertTrue(actualClassDelegate.skipExpression instanceof FixedValue);
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

  /**
   * Test {@link ClassDelegate#ClassDelegate(String, List, Expression)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ClassDelegate#skipExpression} return {@link FixedValue}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, List, Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, List, Expression)"})
  public void testNewClassDelegate_whenArrayList_thenSkipExpressionReturnFixedValue2() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();

    // Act
    ClassDelegate actualClassDelegate =
        new ClassDelegate("Class Name", fieldDeclarations, new FixedValue(JSONObject.NULL));

    // Assert
    assertTrue(actualClassDelegate.skipExpression instanceof FixedValue);
    assertEquals("Class Name", actualClassDelegate.getClassName());
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
   * Test {@link ClassDelegate#ClassDelegate(String, String, List, Expression, List)}.
   *
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is {@link JSONObject#NULL}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#ClassDelegate(String, String, List, Expression,
   * List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.<init>(String, String, List, Expression, List)"})
  public void testNewClassDelegate_whenFixedValueWithValueIsNull_thenArrayListEmpty() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    new ClassDelegate("42", "Class Name", fieldDeclarations, skipExpression, mapExceptions);

    // Assert
    assertTrue(mapExceptions.isEmpty());
  }

  /**
   * Test {@link ClassDelegate#notify(DelegateExecution)} with {@code execution}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#notify(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.notify(DelegateExecution)"})
  public void testNotifyWithExecution_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ClassDelegate#notify(DelegateExecution)} with {@code execution}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#notify(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.notify(DelegateExecution)"})
  public void testNotifyWithExecution_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    FieldDeclaration fieldDeclaration =
        new FieldDeclaration(
            "Trying to load class with current thread context classloader: {}",
            "Trying to load class with current thread context classloader: {}",
            JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;

    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ClassDelegate#notify(String, String, FlowElement, Map, Map)} with {@code
   * processInstanceId}, {@code executionId}, {@code flowElement}, {@code executionVariables},
   * {@code customPropertiesMap}.
   *
   * <p>Method under test: {@link ClassDelegate#notify(String, String, FlowElement, Map, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.notify(String, String, FlowElement, Map, Map)"})
  public void
      testNotifyWithProcessInstanceIdExecutionIdFlowElementExecutionVariablesCustomPropertiesMap() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    AdhocSubProcess flowElement = new AdhocSubProcess();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify("42", "42", flowElement, executionVariables, new HashMap<>()));
  }

  /**
   * Test {@link ClassDelegate#notify(String, String, FlowElement, Map, Map)} with {@code
   * processInstanceId}, {@code executionId}, {@code flowElement}, {@code executionVariables},
   * {@code customPropertiesMap}.
   *
   * <p>Method under test: {@link ClassDelegate#notify(String, String, FlowElement, Map, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.notify(String, String, FlowElement, Map, Map)"})
  public void
      testNotifyWithProcessInstanceIdExecutionIdFlowElementExecutionVariablesCustomPropertiesMap2() {
    // Arrange
    FieldDeclaration fieldDeclaration =
        new FieldDeclaration(
            "Trying to load class with current thread context classloader: {}",
            "Trying to load class with current thread context classloader: {}",
            JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;

    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);
    AdhocSubProcess flowElement = new AdhocSubProcess();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify("42", "42", flowElement, executionVariables, new HashMap<>()));
  }

  /**
   * Test {@link ClassDelegate#notify(String, String, Task, Map, Map)} with {@code
   * processInstanceId}, {@code executionId}, {@code task}, {@code executionVariables}, {@code
   * customPropertiesMap}.
   *
   * <p>Method under test: {@link ClassDelegate#notify(String, String, Task, Map, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.notify(String, String, Task, Map, Map)"})
  public void
      testNotifyWithProcessInstanceIdExecutionIdTaskExecutionVariablesCustomPropertiesMap() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify("42", "42", task, executionVariables, new HashMap<>()));
  }

  /**
   * Test {@link ClassDelegate#notify(String, String, Task, Map, Map)} with {@code
   * processInstanceId}, {@code executionId}, {@code task}, {@code executionVariables}, {@code
   * customPropertiesMap}.
   *
   * <p>Method under test: {@link ClassDelegate#notify(String, String, Task, Map, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.notify(String, String, Task, Map, Map)"})
  public void
      testNotifyWithProcessInstanceIdExecutionIdTaskExecutionVariablesCustomPropertiesMap2() {
    // Arrange
    FieldDeclaration fieldDeclaration =
        new FieldDeclaration(
            "Trying to load class with current thread context classloader: {}",
            "Trying to load class with current thread context classloader: {}",
            JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;

    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> classDelegate.notify("42", "42", task, executionVariables, new HashMap<>()));
  }

  /**
   * Test {@link ClassDelegate#trigger(DelegateExecution, String, Object)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#trigger(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.trigger(DelegateExecution, String, Object)"})
  public void testTrigger_givenJavaLangObject_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            classDelegate.trigger(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
                "Signal Name",
                JSONObject.NULL));
  }

  /**
   * Test {@link ClassDelegate#trigger(DelegateExecution, String, Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#trigger(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.trigger(DelegateExecution, String, Object)"})
  public void testTrigger_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    FieldDeclaration fieldDeclaration =
        new FieldDeclaration(
            "Trying to load class with current thread context classloader: {}",
            "Trying to load class with current thread context classloader: {}",
            JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;

    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            classDelegate.trigger(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
                "Signal Name",
                JSONObject.NULL));
  }

  /**
   * Test {@link ClassDelegate#getCustomPropertiesMap(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ClassDelegate.getCustomPropertiesMap(DelegateExecution)"})
  public void testGetCustomPropertiesMap_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            classDelegate.getCustomPropertiesMap(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ClassDelegate#getCustomPropertiesMap(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ClassDelegate.getCustomPropertiesMap(DelegateExecution)"})
  public void testGetCustomPropertiesMap_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    FieldDeclaration fieldDeclaration =
        new FieldDeclaration(
            "Trying to load class with current thread context classloader: {}",
            "Trying to load class with current thread context classloader: {}",
            JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;

    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            classDelegate.getCustomPropertiesMap(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ClassDelegate#completing(DelegateExecution, DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#completing(DelegateExecution, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.completing(DelegateExecution, DelegateExecution)"})
  public void testCompleting_givenJavaLangObject_thenThrowActivitiIllegalArgumentException()
      throws Exception {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            classDelegate.completing(
                execution, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ClassDelegate#completing(DelegateExecution, DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#completing(DelegateExecution, DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.completing(DelegateExecution, DelegateExecution)"})
  public void testCompleting_thenThrowActivitiIllegalArgumentException() throws Exception {
    // Arrange
    FieldDeclaration fieldDeclaration =
        new FieldDeclaration(
            "Trying to load class with current thread context classloader: {}",
            "Trying to load class with current thread context classloader: {}",
            JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;

    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            classDelegate.completing(
                execution, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ClassDelegate#completed(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#completed(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.completed(DelegateExecution)"})
  public void testCompleted_givenJavaLangObject_thenThrowActivitiIllegalArgumentException()
      throws Exception {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            classDelegate.completed(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ClassDelegate#completed(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#completed(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.completed(DelegateExecution)"})
  public void testCompleted_thenThrowActivitiIllegalArgumentException() throws Exception {
    // Arrange
    FieldDeclaration fieldDeclaration =
        new FieldDeclaration(
            "Trying to load class with current thread context classloader: {}",
            "Trying to load class with current thread context classloader: {}",
            JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);
    Class<Object> clazz = Object.class;

    ClassDelegate classDelegate = new ClassDelegate(clazz, fieldDeclarations);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            classDelegate.completed(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ClassDelegate#determineBehaviour(ActivityBehavior)}.
   *
   * <p>Method under test: {@link ClassDelegate#determineBehaviour(ActivityBehavior)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivityBehavior ClassDelegate.determineBehaviour(ActivityBehavior)"})
  public void testDetermineBehaviour() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    ActivityBehavior delegateInstance = mock(ActivityBehavior.class);

    // Act
    ActivityBehavior actualDetermineBehaviourResult =
        classDelegate.determineBehaviour(delegateInstance);

    // Assert
    assertNull(classDelegate.getMultiInstanceActivityBehavior());
    assertSame(delegateInstance, actualDetermineBehaviourResult);
  }

  /**
   * Test {@link ClassDelegate#determineBehaviour(ActivityBehavior)}.
   *
   * <p>Method under test: {@link ClassDelegate#determineBehaviour(ActivityBehavior)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivityBehavior ClassDelegate.determineBehaviour(ActivityBehavior)"})
  public void testDetermineBehaviour2() {
    // Arrange
    Class<Object> clazz = Object.class;

    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior multiInstanceActivityBehavior =
        new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior());
    classDelegate.setMultiInstanceActivityBehavior(multiInstanceActivityBehavior);
    Class<Object> clazz2 = Object.class;
    ClassDelegate delegateInstance = new ClassDelegate(clazz2, new ArrayList<>());

    // Act
    ActivityBehavior actualDetermineBehaviourResult =
        classDelegate.determineBehaviour(delegateInstance);

    // Assert
    assertSame(multiInstanceActivityBehavior, delegateInstance.getMultiInstanceActivityBehavior());
    assertSame(multiInstanceActivityBehavior, actualDetermineBehaviourResult);
  }

  /**
   * Test {@link ClassDelegate#defaultInstantiateDelegate(Class, List)} with {@code clazz}, {@code
   * fieldDeclarations}.
   *
   * <p>Method under test: {@link ClassDelegate#defaultInstantiateDelegate(Class, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ClassDelegate.defaultInstantiateDelegate(Class, List)"})
  public void testDefaultInstantiateDelegateWithClazzFieldDeclarations() {
    // Arrange
    Class<Object> clazz = Object.class;

    FieldDeclaration fieldDeclaration =
        new FieldDeclaration(
            "Trying to load class with current thread context classloader: {}",
            "Trying to load class with current thread context classloader: {}",
            JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.defaultInstantiateDelegate(clazz, fieldDeclarations));
  }

  /**
   * Test {@link ClassDelegate#defaultInstantiateDelegate(Class, List)} with {@code clazz}, {@code
   * fieldDeclarations}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#defaultInstantiateDelegate(Class, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ClassDelegate.defaultInstantiateDelegate(Class, List)"})
  public void testDefaultInstantiateDelegateWithClazzFieldDeclarations_thenDoesNotThrow() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    ClassDelegate.defaultInstantiateDelegate(clazz, new ArrayList<>());
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object)} with {@code
   * declaration}, {@code target}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(FieldDeclaration, Object)"})
  public void testApplyFieldDeclarationWithDeclarationTarget() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    declaration.setValue(42);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(declaration, JSONObject.NULL));
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object, boolean)} with {@code
   * declaration}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(FieldDeclaration, Object, boolean)"})
  public void testApplyFieldDeclarationWithDeclarationTargetThrowExceptionOnMissingField() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);

    // Act and Assert
    ClassDelegate.applyFieldDeclaration(declaration, JSONObject.NULL, false);
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object, boolean)} with {@code
   * declaration}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(FieldDeclaration, Object, boolean)"})
  public void testApplyFieldDeclarationWithDeclarationTargetThrowExceptionOnMissingField2() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    declaration.setValue(42);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(declaration, JSONObject.NULL, true));
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object, boolean)} with {@code
   * declaration}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(FieldDeclaration, Object, boolean)"})
  public void testApplyFieldDeclarationWithDeclarationTargetThrowExceptionOnMissingField3() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    declaration.setValue(42);
    FieldDeclaration fieldDeclaration = new FieldDeclaration("equals", "equals", JSONObject.NULL);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(declaration, fieldDeclaration, true));
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object, boolean)} with {@code
   * declaration}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(FieldDeclaration, Object,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(FieldDeclaration, Object, boolean)"})
  public void testApplyFieldDeclarationWithDeclarationTargetThrowExceptionOnMissingField4() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    declaration.setValue("42");
    FieldDeclaration fieldDeclaration = new FieldDeclaration("equals", "equals", JSONObject.NULL);

    // Act
    ClassDelegate.applyFieldDeclaration(declaration, fieldDeclaration, true);

    // Assert
    assertEquals("42", fieldDeclaration.getName());
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(List, Object)} with {@code fieldDeclarations},
   * {@code target}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(List, Object)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTarget() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(fieldDeclarations, JSONObject.NULL));
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)} with {@code
   * fieldDeclarations}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(List, Object, boolean)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTargetThrowExceptionOnMissingField() {
    // Arrange, Act and Assert
    ClassDelegate.applyFieldDeclaration(new ArrayList<>(), JSONObject.NULL, true);
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)} with {@code
   * fieldDeclarations}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(List, Object, boolean)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTargetThrowExceptionOnMissingField2() {
    // Arrange, Act and Assert
    ClassDelegate.applyFieldDeclaration((List<FieldDeclaration>) null, JSONObject.NULL, false);
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)} with {@code
   * fieldDeclarations}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(List, Object, boolean)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTargetThrowExceptionOnMissingField3() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    ClassDelegate.applyFieldDeclaration(fieldDeclarations, JSONObject.NULL, false);
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)} with {@code
   * fieldDeclarations}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(List, Object, boolean)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTargetThrowExceptionOnMissingField4() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(fieldDeclarations, JSONObject.NULL, true));
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)} with {@code
   * fieldDeclarations}, {@code target}, {@code throwExceptionOnMissingField}.
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(List, Object, boolean)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTargetThrowExceptionOnMissingField5() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclarations.add(fieldDeclaration);
    fieldDeclarations.add(new FieldDeclaration());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegate.applyFieldDeclaration(fieldDeclarations, new FieldDeclaration(), true));
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(List, Object)} with {@code fieldDeclarations},
   * {@code target}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(List, Object)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTarget_whenArrayList() {
    // Arrange, Act and Assert
    ClassDelegate.applyFieldDeclaration(new ArrayList<>(), JSONObject.NULL);
  }

  /**
   * Test {@link ClassDelegate#applyFieldDeclaration(List, Object)} with {@code fieldDeclarations},
   * {@code target}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#applyFieldDeclaration(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegate.applyFieldDeclaration(List, Object)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTarget_whenNull_thenDoesNotThrow() {
    // Arrange, Act and Assert
    ClassDelegate.applyFieldDeclaration((List<FieldDeclaration>) null, JSONObject.NULL);
  }

  /**
   * Test {@link ClassDelegate#fieldTypeCompatible(FieldDeclaration, Field)}.
   *
   * <ul>
   *   <li>When {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegate#fieldTypeCompatible(FieldDeclaration, Field)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ClassDelegate.fieldTypeCompatible(FieldDeclaration, Field)"})
  public void testFieldTypeCompatible_whenFieldDeclaration_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(ClassDelegate.fieldTypeCompatible(new FieldDeclaration(), null));
  }

  /**
   * Test {@link ClassDelegate#getClassName()}.
   *
   * <p>Method under test: {@link ClassDelegate#getClassName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ClassDelegate.getClassName()"})
  public void testGetClassName() {
    // Arrange
    Class<Object> clazz = Object.class;
    ClassDelegate classDelegate = new ClassDelegate(clazz, new ArrayList<>());

    // Act and Assert
    assertEquals("java.lang.Object", classDelegate.getClassName());
  }
}
