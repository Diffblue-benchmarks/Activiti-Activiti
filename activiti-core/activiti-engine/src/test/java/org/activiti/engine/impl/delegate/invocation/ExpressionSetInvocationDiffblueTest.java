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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ValueExpression;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.impl.el.ParsingElContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ExpressionSetInvocationDiffblueTest {
  /**
   * Test {@link ExpressionSetInvocation#ExpressionSetInvocation(ValueExpression, ELContext,
   * Object)}.
   *
   * <p>Method under test: {@link ExpressionSetInvocation#ExpressionSetInvocation(ValueExpression,
   * ELContext, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExpressionSetInvocation.<init>(ValueExpression, ELContext, Object)"})
  public void testNewExpressionSetInvocation() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression valueExpression =
        new ObjectValueExpression(converter, JSONObject.NULL, type);

    // Act
    ExpressionSetInvocation actualExpressionSetInvocation =
        new ExpressionSetInvocation(valueExpression, new ParsingElContext(), JSONObject.NULL);

    // Assert
    ELContext elContext = actualExpressionSetInvocation.elContext;
    assertTrue(elContext instanceof ParsingElContext);
    assertNull(elContext.getELResolver());
    assertNull(elContext.getFunctionMapper());
    assertNull(elContext.getVariableMapper());
    assertNull(actualExpressionSetInvocation.getInvocationResult());
    assertNull(elContext.getEvaluationListeners());
    assertNull(elContext.getLocale());
    Object[] invocationParameters = actualExpressionSetInvocation.getInvocationParameters();
    assertEquals(1, invocationParameters.length);
    assertFalse(elContext.isPropertyResolved());
    assertSame(valueExpression, actualExpressionSetInvocation.getTarget());
    assertSame(actualExpressionSetInvocation.value, invocationParameters[0]);
  }

  /**
   * Test {@link ExpressionSetInvocation#invoke()}.
   *
   * <ul>
   *   <li>Given {@link ObjectValueExpression} {@link ObjectValueExpression#setValue(ELContext,
   *       Object)} does nothing.
   *   <li>Then calls {@link ObjectValueExpression#setValue(ELContext, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionSetInvocation#invoke()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExpressionSetInvocation.invoke()"})
  public void testInvoke_givenObjectValueExpressionSetValueDoesNothing_thenCallsSetValue() {
    // Arrange
    ObjectValueExpression valueExpression = mock(ObjectValueExpression.class);
    doNothing().when(valueExpression).setValue(Mockito.<ELContext>any(), Mockito.<Object>any());
    ExpressionSetInvocation expressionSetInvocation =
        new ExpressionSetInvocation(valueExpression, new ParsingElContext(), JSONObject.NULL);

    // Act
    expressionSetInvocation.invoke();

    // Assert
    verify(valueExpression).setValue(isA(ELContext.class), isA(Object.class));
  }
}
