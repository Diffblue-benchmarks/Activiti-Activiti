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
import org.junit.jupiter.api.Test;

class AstNodeDiffblueTest {
  /**
   * Method under test: {@link AstNode#getValue(Bindings, ELContext, Class)}
   */
  @Test
  void testGetValue() {
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
   * Method under test: {@link AstNode#getValue(Bindings, ELContext, Class)}
   */
  @Test
  void testGetValue2() {
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
   * Method under test: {@link AstNode#getStructuralId(Bindings)}
   */
  @Test
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
   * Method under test: {@link AstNode#findAccessibleMethod(Method)}
   */
  @Test
  void testFindAccessibleMethod() {
    // Arrange, Act and Assert
    assertNull((new AstNull()).findAccessibleMethod(null));
  }

  /**
   * Method under test: {@link AstNode#findAccessibleMethod(Method)}
   */
  @Test
  void testFindAccessibleMethod2() {
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
