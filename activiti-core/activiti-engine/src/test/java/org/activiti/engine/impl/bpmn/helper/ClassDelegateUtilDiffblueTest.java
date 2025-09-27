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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ClassDelegateUtilDiffblueTest {
  /**
   * Test {@link ClassDelegateUtil#instantiateDelegate(Class, List)} with {@code clazz}, {@code
   * fieldDeclarations}.
   *
   * <p>Method under test: {@link ClassDelegateUtil#instantiateDelegate(Class, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ClassDelegateUtil.instantiateDelegate(Class, List)"})
  public void testInstantiateDelegateWithClazzFieldDeclarations() {
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
        () -> ClassDelegateUtil.instantiateDelegate(clazz, fieldDeclarations));
  }

  /**
   * Test {@link ClassDelegateUtil#applyFieldDeclaration(FieldDeclaration, Object)} with {@code
   * declaration}, {@code target}.
   *
   * <p>Method under test: {@link ClassDelegateUtil#applyFieldDeclaration(FieldDeclaration, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegateUtil.applyFieldDeclaration(FieldDeclaration, Object)"})
  public void testApplyFieldDeclarationWithDeclarationTarget() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegateUtil.applyFieldDeclaration(declaration, JSONObject.NULL));
  }

  /**
   * Test {@link ClassDelegateUtil#applyFieldDeclaration(FieldDeclaration, Object)} with {@code
   * declaration}, {@code target}.
   *
   * <p>Method under test: {@link ClassDelegateUtil#applyFieldDeclaration(FieldDeclaration, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegateUtil.applyFieldDeclaration(FieldDeclaration, Object)"})
  public void testApplyFieldDeclarationWithDeclarationTarget2() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", null);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegateUtil.applyFieldDeclaration(declaration, fieldDeclaration));
  }

  /**
   * Test {@link ClassDelegateUtil#applyFieldDeclaration(FieldDeclaration, Object)} with {@code
   * declaration}, {@code target}.
   *
   * <p>Method under test: {@link ClassDelegateUtil#applyFieldDeclaration(FieldDeclaration, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegateUtil.applyFieldDeclaration(FieldDeclaration, Object)"})
  public void testApplyFieldDeclarationWithDeclarationTarget3() {
    // Arrange
    FieldDeclaration declaration = new FieldDeclaration("Name", "Type", "42");
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", null);

    // Act
    ClassDelegateUtil.applyFieldDeclaration(declaration, fieldDeclaration);

    // Assert
    assertEquals("42", fieldDeclaration.getName());
  }

  /**
   * Test {@link ClassDelegateUtil#applyFieldDeclaration(List, Object)} with {@code
   * fieldDeclarations}, {@code target}.
   *
   * <p>Method under test: {@link ClassDelegateUtil#applyFieldDeclaration(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegateUtil.applyFieldDeclaration(List, Object)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTarget() {
    // Arrange
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclaration.setValue(42);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegateUtil.applyFieldDeclaration(fieldDeclarations, JSONObject.NULL));
  }

  /**
   * Test {@link ClassDelegateUtil#applyFieldDeclaration(List, Object)} with {@code
   * fieldDeclarations}, {@code target}.
   *
   * <p>Method under test: {@link ClassDelegateUtil#applyFieldDeclaration(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegateUtil.applyFieldDeclaration(List, Object)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTarget2() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", JSONObject.NULL);
    fieldDeclarations.add(fieldDeclaration);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ClassDelegateUtil.applyFieldDeclaration(fieldDeclarations, new FieldDeclaration()));
  }

  /**
   * Test {@link ClassDelegateUtil#applyFieldDeclaration(List, Object)} with {@code
   * fieldDeclarations}, {@code target}.
   *
   * <p>Method under test: {@link ClassDelegateUtil#applyFieldDeclaration(List, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ClassDelegateUtil.applyFieldDeclaration(List, Object)"})
  public void testApplyFieldDeclarationWithFieldDeclarationsTarget3() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    FieldDeclaration fieldDeclaration = new FieldDeclaration("Name", "Type", "42");
    fieldDeclarations.add(fieldDeclaration);
    FieldDeclaration fieldDeclaration2 = new FieldDeclaration();

    // Act
    ClassDelegateUtil.applyFieldDeclaration(fieldDeclarations, fieldDeclaration2);

    // Assert
    assertEquals("42", fieldDeclaration2.getName());
  }

  /**
   * Test {@link ClassDelegateUtil#fieldTypeCompatible(FieldDeclaration, Field)}.
   *
   * <ul>
   *   <li>When {@link FieldDeclaration#FieldDeclaration()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ClassDelegateUtil#fieldTypeCompatible(FieldDeclaration, Field)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ClassDelegateUtil.fieldTypeCompatible(FieldDeclaration, Field)"})
  public void testFieldTypeCompatible_whenFieldDeclaration_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(ClassDelegateUtil.fieldTypeCompatible(new FieldDeclaration(), null));
  }
}
