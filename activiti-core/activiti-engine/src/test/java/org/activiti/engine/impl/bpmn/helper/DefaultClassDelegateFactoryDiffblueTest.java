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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DefaultClassDelegateFactoryDiffblueTest {
  /**
   * Test {@link DefaultClassDelegateFactory#create(String, List)} with {@code className}, {@code
   * fieldDeclarations}.
   *
   * <ul>
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ClassDelegate DefaultClassDelegateFactory.create(String, List)"})
  public void testCreateWithClassNameFieldDeclarations_thenReturnFieldDeclarationsSizeIsOne() {
    // Arrange
    DefaultClassDelegateFactory defaultClassDelegateFactory = new DefaultClassDelegateFactory();

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    List<FieldDeclaration> fieldDeclarationList =
        defaultClassDelegateFactory.create("Class Name", fieldDeclarations).fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test {@link DefaultClassDelegateFactory#create(String, List)} with {@code className}, {@code
   * fieldDeclarations}.
   *
   * <ul>
   *   <li>Then return {@link ClassDelegate#fieldDeclarations} size is two.
   * </ul>
   *
   * <p>Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ClassDelegate DefaultClassDelegateFactory.create(String, List)"})
  public void testCreateWithClassNameFieldDeclarations_thenReturnFieldDeclarationsSizeIsTwo() {
    // Arrange
    DefaultClassDelegateFactory defaultClassDelegateFactory = new DefaultClassDelegateFactory();

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    List<FieldDeclaration> fieldDeclarationList =
        defaultClassDelegateFactory.create("Class Name", fieldDeclarations).fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test {@link DefaultClassDelegateFactory#create(String, List)} with {@code className}, {@code
   * fieldDeclarations}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code Class Name}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultClassDelegateFactory#create(String, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ClassDelegate DefaultClassDelegateFactory.create(String, List)"})
  public void testCreateWithClassNameFieldDeclarations_whenArrayList_thenReturnClassName() {
    // Arrange
    DefaultClassDelegateFactory defaultClassDelegateFactory = new DefaultClassDelegateFactory();

    // Act
    ClassDelegate actualCreateResult =
        defaultClassDelegateFactory.create("Class Name", new ArrayList<>());

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
   * Test {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)} with
   * {@code id}, {@code className}, {@code fieldDeclarations}, {@code skipExpression}, {@code
   * mapExceptions}.
   *
   * <p>Method under test: {@link DefaultClassDelegateFactory#create(String, String, List,
   * Expression, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ClassDelegate DefaultClassDelegateFactory.create(String, String, List, Expression, List)"
  })
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions() {
    // Arrange
    DefaultClassDelegateFactory defaultClassDelegateFactory = new DefaultClassDelegateFactory();
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    defaultClassDelegateFactory.create(
        "42", "Class Name", fieldDeclarations, skipExpression, mapExceptions);

    // Assert
    assertTrue(mapExceptions.isEmpty());
  }

  /**
   * Test {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)} with
   * {@code id}, {@code className}, {@code fieldDeclarations}, {@code skipExpression}, {@code
   * mapExceptions}.
   *
   * <p>Method under test: {@link DefaultClassDelegateFactory#create(String, String, List,
   * Expression, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ClassDelegate DefaultClassDelegateFactory.create(String, String, List, Expression, List)"
  })
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions2() {
    // Arrange
    DefaultClassDelegateFactory defaultClassDelegateFactory = new DefaultClassDelegateFactory();

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    List<FieldDeclaration> fieldDeclarationList =
        defaultClassDelegateFactory.create(
                "42", "Class Name", fieldDeclarations, skipExpression, new ArrayList<>())
            .fieldDeclarations;
    assertEquals(1, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(0));
  }

  /**
   * Test {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)} with
   * {@code id}, {@code className}, {@code fieldDeclarations}, {@code skipExpression}, {@code
   * mapExceptions}.
   *
   * <p>Method under test: {@link DefaultClassDelegateFactory#create(String, String, List,
   * Expression, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ClassDelegate DefaultClassDelegateFactory.create(String, String, List, Expression, List)"
  })
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions3() {
    // Arrange
    DefaultClassDelegateFactory defaultClassDelegateFactory = new DefaultClassDelegateFactory();

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    FieldDeclaration fieldDeclaration = new FieldDeclaration();
    fieldDeclarations.add(fieldDeclaration);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    List<FieldDeclaration> fieldDeclarationList =
        defaultClassDelegateFactory.create(
                "42", "Class Name", fieldDeclarations, skipExpression, new ArrayList<>())
            .fieldDeclarations;
    assertEquals(2, fieldDeclarationList.size());
    assertSame(fieldDeclaration, fieldDeclarationList.get(1));
  }

  /**
   * Test {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)} with
   * {@code id}, {@code className}, {@code fieldDeclarations}, {@code skipExpression}, {@code
   * mapExceptions}.
   *
   * <p>Method under test: {@link DefaultClassDelegateFactory#create(String, String, List,
   * Expression, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ClassDelegate DefaultClassDelegateFactory.create(String, String, List, Expression, List)"
  })
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions4() {
    // Arrange
    DefaultClassDelegateFactory defaultClassDelegateFactory = new DefaultClassDelegateFactory();
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList =
        defaultClassDelegateFactory.create(
                "42", "Class Name", fieldDeclarations, skipExpression, mapExceptions)
            .mapExceptions;
    assertEquals(1, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry, mapExceptionEntryList.get(0));
  }

  /**
   * Test {@link DefaultClassDelegateFactory#create(String, String, List, Expression, List)} with
   * {@code id}, {@code className}, {@code fieldDeclarations}, {@code skipExpression}, {@code
   * mapExceptions}.
   *
   * <p>Method under test: {@link DefaultClassDelegateFactory#create(String, String, List,
   * Expression, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ClassDelegate DefaultClassDelegateFactory.create(String, String, List, Expression, List)"
  })
  public void testCreateWithIdClassNameFieldDeclarationsSkipExpressionMapExceptions5() {
    // Arrange
    DefaultClassDelegateFactory defaultClassDelegateFactory = new DefaultClassDelegateFactory();
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry2);

    // Act and Assert
    List<MapExceptionEntry> mapExceptionEntryList =
        defaultClassDelegateFactory.create(
                "42", "Class Name", fieldDeclarations, skipExpression, mapExceptions)
            .mapExceptions;
    assertEquals(2, mapExceptionEntryList.size());
    assertSame(mapExceptionEntry2, mapExceptionEntryList.get(1));
  }
}
