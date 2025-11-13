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
package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AstBooleanDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstBoolean#AstBoolean(boolean)}
   *   <li>{@link AstBoolean#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBoolean.<init>(boolean)", "java.lang.String AstBoolean.toString()"})
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(Boolean.TRUE.toString(), new AstBoolean(true).toString());
  }

  /**
   * Test {@link AstBoolean#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstBoolean#AstBoolean(boolean)} with value is {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstBoolean#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstBoolean(boolean) with value is 'false'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBoolean.eval(Bindings, ELContext)"})
  void testEval_givenAstBooleanWithValueIsFalse_thenReturnFalse() {
    // Arrange
    AstBoolean astBoolean = new AstBoolean(false);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertFalse((Boolean) astBoolean.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBoolean#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstBoolean#AstBoolean(boolean)} with value is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstBoolean#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstBoolean(boolean) with value is 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBoolean.eval(Bindings, ELContext)"})
  void testEval_givenAstBooleanWithValueIsTrue_thenReturnTrue() {
    // Arrange
    AstBoolean astBoolean = new AstBoolean(true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertTrue((Boolean) astBoolean.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBoolean#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strtrue}.
   * </ul>
   *
   * <p>Method under test: {@link AstBoolean#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strtrue'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBoolean.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrtrue() {
    // Arrange
    AstBoolean astBoolean = new AstBoolean(true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBoolean.appendStructure(b, bindings);

    // Assert
    assertEquals("Strtrue", b.toString());
  }
}
