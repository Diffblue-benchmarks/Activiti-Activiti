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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import jakarta.el.ELContext;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AstNodeDiffblueTest {
  /**
   * Test {@link AstNode#getValue(Bindings, ELContext, Class)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNode#getValue(Bindings, ELContext, Class)}
   */
  @Test
  @DisplayName("Test getValue(Bindings, ELContext, Class); then return 'null'")
  void testGetValue_thenReturnNull() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNull(astNull.getValue(bindings, context, type2));
  }

  /**
   * Test {@link AstNode#getValue(Bindings, ELContext, Class)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNode#getValue(Bindings, ELContext, Class)}
   */
  @Test
  @DisplayName("Test getValue(Bindings, ELContext, Class); then return 'null'")
  void testGetValue_thenReturnNull2() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astNull.getValue(bindings, new SimpleContext(), null));
  }

  /**
   * Test {@link AstNode#getStructuralId(Bindings)}.
   * <p>
   * Method under test: {@link AstNode#getStructuralId(Bindings)}
   */
  @Test
  @DisplayName("Test getStructuralId(Bindings)")
  void testGetStructuralId() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("null", astNull.getStructuralId(
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)})));
  }

  /**
   * Test {@link AstNode#findAccessibleMethod(Method)}.
   * <ul>
   *   <li>Given {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNode#findAccessibleMethod(Method)}
   */
  @Test
  @DisplayName("Test findAccessibleMethod(Method); given AstNull (default constructor)")
  void testFindAccessibleMethod_givenAstNull() {
    // Arrange, Act and Assert
    assertNull((new AstNull()).findAccessibleMethod(null));
  }

  /**
   * Test {@link AstNode#findAccessibleMethod(Method)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNode#findAccessibleMethod(Method)}
   */
  @Test
  @DisplayName("Test findAccessibleMethod(Method); given 'java.lang.Object'")
  void testFindAccessibleMethod_givenJavaLangObject() {
    // Arrange
    AstNull astNull = new AstNull();
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    astNull.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertNull(astNull.findAccessibleMethod(null));
  }
}
