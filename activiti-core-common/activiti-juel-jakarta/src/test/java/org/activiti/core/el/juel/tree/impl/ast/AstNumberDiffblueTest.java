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
import static org.junit.jupiter.api.Assertions.assertSame;
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

class AstNumberDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstNumber#AstNumber(Number)}
   *   <li>{@link AstNumber#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstNumber.<init>(Number)", "java.lang.String AstNumber.toString()"})
  void testGettersAndSetters() {
    // Arrange
    Integer value = Integer.valueOf(1);

    // Act and Assert
    assertEquals("1", new AstNumber(value).toString());
  }

  /**
   * Test {@link AstNumber#eval(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstNumber#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstNumber.eval(Bindings, ELContext)"})
  void testEval() {
    // Arrange
    Integer value = Integer.valueOf(1);
    AstNumber astNumber = new AstNumber(value);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object actualEvalResult = astNumber.eval(bindings, new SimpleContext());

    // Assert
    assertEquals(1, ((Integer) actualEvalResult).intValue());
    assertSame(value, actualEvalResult);
  }

  /**
   * Test {@link AstNumber#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo1}.
   * </ul>
   *
   * <p>Method under test: {@link AstNumber#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo1'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstNumber.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoo1() {
    // Arrange
    Integer value = Integer.valueOf(1);
    AstNumber astNumber = new AstNumber(value);
    StringBuilder b = new StringBuilder("foo");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astNumber.appendStructure(b, bindings);

    // Assert
    assertEquals("foo1", b.toString());
  }
}
