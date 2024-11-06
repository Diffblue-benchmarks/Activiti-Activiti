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
package org.activiti.engine.impl.delegate.invocation;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ValueExpression;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.impl.el.ParsingElContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class ExpressionGetInvocationDiffblueTest {
  /**
   * Test
   * {@link ExpressionGetInvocation#ExpressionGetInvocation(ValueExpression, ELContext)}.
   * <p>
   * Method under test:
   * {@link ExpressionGetInvocation#ExpressionGetInvocation(ValueExpression, ELContext)}
   */
  @Test
  public void testNewExpressionGetInvocation() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression valueExpression = new ObjectValueExpression(converter, JSONObject.NULL, type);

    // Act
    ExpressionGetInvocation actualExpressionGetInvocation = new ExpressionGetInvocation(valueExpression,
        new ParsingElContext());

    // Assert
    assertNull(actualExpressionGetInvocation.getInvocationParameters());
    assertNull(actualExpressionGetInvocation.getInvocationResult());
    assertSame(valueExpression, actualExpressionGetInvocation.getTarget());
  }

  /**
   * Test {@link ExpressionGetInvocation#invoke()}.
   * <ul>
   *   <li>Given {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@link JSONObject#NULL}.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionGetInvocation#invoke()}
   */
  @Test
  public void testInvoke_givenTypeConverterConvertReturnNull_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(JSONObject.NULL);
    Class<Object> type = Object.class;
    ObjectValueExpression valueExpression = new ObjectValueExpression(converter, JSONObject.NULL, type);

    // Act
    (new ExpressionGetInvocation(valueExpression, new ParsingElContext())).invoke();

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }
}
