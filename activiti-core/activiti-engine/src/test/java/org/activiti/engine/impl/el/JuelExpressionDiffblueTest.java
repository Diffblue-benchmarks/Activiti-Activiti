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
package org.activiti.engine.impl.el;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import jakarta.el.MethodNotFoundException;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.ValueExpression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.delegate.invocation.DelegateInvocation;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class JuelExpressionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link JuelExpression#JuelExpression(ValueExpression, String)}
   *   <li>{@link JuelExpression#getExpressionText()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Expression Text",
        (new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text"))
            .getExpressionText());
  }

  /**
   * Test
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   * with {@code expressionManager}, {@code delegateInterceptor},
   * {@code availableVariables}.
   * <p>
   * Method under test:
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   */
  @Test
  public void testGetValueWithExpressionManagerDelegateInterceptorAvailableVariables() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression juelExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");
    ExpressionManager expressionManager = new ExpressionManager();
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);
    doNothing().when(delegateInterceptor).handleInvocation(Mockito.<DelegateInvocation>any());

    // Act
    Object actualValue = juelExpression.getValue(expressionManager, delegateInterceptor, new HashMap<>());

    // Assert
    verify(delegateInterceptor).handleInvocation(isA(DelegateInvocation.class));
    assertNull(actualValue);
  }

  /**
   * Test
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   * with {@code expressionManager}, {@code delegateInterceptor},
   * {@code availableVariables}.
   * <p>
   * Method under test:
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   */
  @Test
  public void testGetValueWithExpressionManagerDelegateInterceptorAvailableVariables2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression juelExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);
    doNothing().when(delegateInterceptor).handleInvocation(Mockito.<DelegateInvocation>any());

    // Act
    Object actualValue = juelExpression.getValue(expressionManager, delegateInterceptor, new HashMap<>());

    // Assert
    verify(delegateInterceptor).handleInvocation(isA(DelegateInvocation.class));
    assertNull(actualValue);
  }

  /**
   * Test
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   * with {@code expressionManager}, {@code delegateInterceptor},
   * {@code availableVariables}.
   * <p>
   * Method under test:
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   */
  @Test
  public void testGetValueWithExpressionManagerDelegateInterceptorAvailableVariables3() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression juelExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");
    ExpressionManager expressionManager = new ExpressionManager();
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);
    doThrow(new ActivitiException("An error occurred")).when(delegateInterceptor)
        .handleInvocation(Mockito.<DelegateInvocation>any());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> juelExpression.getValue(expressionManager, delegateInterceptor, new HashMap<>()));
    verify(delegateInterceptor).handleInvocation(isA(DelegateInvocation.class));
  }

  /**
   * Test
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   * with {@code expressionManager}, {@code delegateInterceptor},
   * {@code availableVariables}.
   * <p>
   * Method under test:
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   */
  @Test
  public void testGetValueWithExpressionManagerDelegateInterceptorAvailableVariables4() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression juelExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");
    ExpressionManager expressionManager = new ExpressionManager();
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);
    doThrow(new PropertyNotFoundException("An error occurred")).when(delegateInterceptor)
        .handleInvocation(Mockito.<DelegateInvocation>any());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> juelExpression.getValue(expressionManager, delegateInterceptor, new HashMap<>()));
    verify(delegateInterceptor).handleInvocation(isA(DelegateInvocation.class));
  }

  /**
   * Test
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   * with {@code expressionManager}, {@code delegateInterceptor},
   * {@code availableVariables}.
   * <p>
   * Method under test:
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   */
  @Test
  public void testGetValueWithExpressionManagerDelegateInterceptorAvailableVariables5() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression juelExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");
    ExpressionManager expressionManager = new ExpressionManager();
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);
    doThrow(new MethodNotFoundException("An error occurred")).when(delegateInterceptor)
        .handleInvocation(Mockito.<DelegateInvocation>any());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> juelExpression.getValue(expressionManager, delegateInterceptor, new HashMap<>()));
    verify(delegateInterceptor).handleInvocation(isA(DelegateInvocation.class));
  }

  /**
   * Test
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   * with {@code expressionManager}, {@code delegateInterceptor},
   * {@code availableVariables}.
   * <p>
   * Method under test:
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   */
  @Test
  public void testGetValueWithExpressionManagerDelegateInterceptorAvailableVariables6() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression juelExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);
    doNothing().when(delegateInterceptor).handleInvocation(Mockito.<DelegateInvocation>any());

    // Act
    Object actualValue = juelExpression.getValue(expressionManager, delegateInterceptor, new HashMap<>());

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    verify(delegateInterceptor).handleInvocation(isA(DelegateInvocation.class));
    assertNull(actualValue);
  }

  /**
   * Test
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   * with {@code expressionManager}, {@code delegateInterceptor},
   * {@code availableVariables}.
   * <p>
   * Method under test:
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   */
  @Test
  public void testGetValueWithExpressionManagerDelegateInterceptorAvailableVariables7() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression juelExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);
    doNothing().when(delegateInterceptor).handleInvocation(Mockito.<DelegateInvocation>any());

    // Act
    Object actualValue = juelExpression.getValue(expressionManager, delegateInterceptor, new HashMap<>());

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    verify(delegateInterceptor).handleInvocation(isA(DelegateInvocation.class));
    assertNull(actualValue);
  }

  /**
   * Test
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   * with {@code expressionManager}, {@code delegateInterceptor},
   * {@code availableVariables}.
   * <p>
   * Method under test:
   * {@link JuelExpression#getValue(ExpressionManager, DelegateInterceptor, Map)}
   */
  @Test
  public void testGetValueWithExpressionManagerDelegateInterceptorAvailableVariables8() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression juelExpression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider3 = mock(CustomFunctionProvider.class);
    doThrow(new ActivitiException("An error occurred")).when(customFunctionProvider3)
        .addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider3);
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    DelegateInterceptor delegateInterceptor = mock(DelegateInterceptor.class);
    doNothing().when(delegateInterceptor).handleInvocation(Mockito.<DelegateInvocation>any());

    // Act
    Object actualValue = juelExpression.getValue(expressionManager, delegateInterceptor, new HashMap<>());

    // Assert
    verify(customFunctionProvider3).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    verify(delegateInterceptor).handleInvocation(isA(DelegateInvocation.class));
    assertNull(actualValue);
  }

  /**
   * Test {@link JuelExpression#toString()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JuelExpression#toString()}
   */
  @Test
  public void testToString_givenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull((new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "Expression Text"))
        .toString());
  }
}
