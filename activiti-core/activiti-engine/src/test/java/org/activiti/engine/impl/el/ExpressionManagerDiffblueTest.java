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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.el.ExpressionFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.ActivitiFunctionMapper;
import org.activiti.core.el.ActivitiVariablesMapper;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.core.el.juel.ExpressionFactoryImpl;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.test.mock.MockExpressionManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionManagerDiffblueTest {
  @InjectMocks
  private ExpressionManager expressionManager;

  /**
   * Test {@link ExpressionManager#ExpressionManager()}.
   * <p>
   * Method under test: {@link ExpressionManager#ExpressionManager()}
   */
  @Test
  public void testNewExpressionManager() {
    // Arrange and Act
    ExpressionManager actualExpressionManager = new ExpressionManager();

    // Assert
    ExpressionFactory expressionFactory = actualExpressionManager.expressionFactory;
    assertTrue(expressionFactory instanceof ExpressionFactoryImpl);
    assertNull(expressionFactory.getStreamELResolver());
    assertNull(actualExpressionManager.getCustomFunctionProviders());
    assertNull(actualExpressionManager.getBeans());
    assertNull(expressionFactory.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionManager#ExpressionManager(Map)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#ExpressionManager(Map)}
   */
  @Test
  public void testNewExpressionManager_givenNull_whenHashMapComputeIfPresentNullAndBiFunction() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    beans.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    // Act
    ExpressionManager actualExpressionManager = new ExpressionManager(beans);

    // Assert
    ExpressionFactory expressionFactory = actualExpressionManager.expressionFactory;
    assertTrue(expressionFactory instanceof ExpressionFactoryImpl);
    assertNull(expressionFactory.getStreamELResolver());
    assertNull(actualExpressionManager.getCustomFunctionProviders());
    assertNull(expressionFactory.getInitFunctionMap());
    assertTrue(actualExpressionManager.getBeans().isEmpty());
  }

  /**
   * Test {@link ExpressionManager#ExpressionManager(Map, boolean)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#ExpressionManager(Map, boolean)}
   */
  @Test
  public void testNewExpressionManager_givenNull_whenHashMapComputeIfPresentNullAndBiFunction2() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    beans.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    // Act
    ExpressionManager actualExpressionManager = new ExpressionManager(beans, true);

    // Assert
    ExpressionFactory expressionFactory = actualExpressionManager.expressionFactory;
    assertTrue(expressionFactory instanceof ExpressionFactoryImpl);
    assertNull(expressionFactory.getStreamELResolver());
    assertNull(actualExpressionManager.getCustomFunctionProviders());
    assertNull(expressionFactory.getInitFunctionMap());
    assertTrue(actualExpressionManager.getBeans().isEmpty());
  }

  /**
   * Test {@link ExpressionManager#ExpressionManager(Map, boolean)}.
   * <ul>
   *   <li>Then {@link ExpressionManager#expressionFactory} return
   * {@link ExpressionFactoryImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#ExpressionManager(Map, boolean)}
   */
  @Test
  public void testNewExpressionManager_thenExpressionFactoryReturnExpressionFactoryImpl() {
    // Arrange and Act
    ExpressionManager actualExpressionManager = new ExpressionManager(new HashMap<>(), true);

    // Assert
    ExpressionFactory expressionFactory = actualExpressionManager.expressionFactory;
    assertTrue(expressionFactory instanceof ExpressionFactoryImpl);
    assertNull(expressionFactory.getStreamELResolver());
    assertNull(actualExpressionManager.getCustomFunctionProviders());
    assertNull(expressionFactory.getInitFunctionMap());
    assertTrue(actualExpressionManager.getBeans().isEmpty());
  }

  /**
   * Test {@link ExpressionManager#ExpressionManager(boolean)}.
   * <ul>
   *   <li>Then {@link ExpressionManager#expressionFactory} return
   * {@link ExpressionFactoryImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#ExpressionManager(boolean)}
   */
  @Test
  public void testNewExpressionManager_thenExpressionFactoryReturnExpressionFactoryImpl2() {
    // Arrange and Act
    ExpressionManager actualExpressionManager = new ExpressionManager(true);

    // Assert
    ExpressionFactory expressionFactory = actualExpressionManager.expressionFactory;
    assertTrue(expressionFactory instanceof ExpressionFactoryImpl);
    assertNull(expressionFactory.getStreamELResolver());
    assertNull(actualExpressionManager.getCustomFunctionProviders());
    assertNull(actualExpressionManager.getBeans());
    assertNull(expressionFactory.getInitFunctionMap());
  }

  /**
   * Test {@link ExpressionManager#ExpressionManager(Map, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@link ExpressionManager#expressionFactory} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#ExpressionManager(Map, boolean)}
   */
  @Test
  public void testNewExpressionManager_whenFalse_thenReturnExpressionFactoryIsNull() {
    // Arrange and Act
    ExpressionManager actualExpressionManager = new ExpressionManager(new HashMap<>(), false);

    // Assert
    assertNull(actualExpressionManager.expressionFactory);
    assertNull(actualExpressionManager.getCustomFunctionProviders());
    assertTrue(actualExpressionManager.getBeans().isEmpty());
  }

  /**
   * Test {@link ExpressionManager#ExpressionManager(boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@link ExpressionManager#expressionFactory} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#ExpressionManager(boolean)}
   */
  @Test
  public void testNewExpressionManager_whenFalse_thenReturnExpressionFactoryIsNull2() {
    // Arrange and Act
    ExpressionManager actualExpressionManager = new ExpressionManager(false);

    // Assert
    assertNull(actualExpressionManager.expressionFactory);
    assertNull(actualExpressionManager.getCustomFunctionProviders());
    assertNull(actualExpressionManager.getBeans());
  }

  /**
   * Test {@link ExpressionManager#ExpressionManager(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#ExpressionManager(Map)}
   */
  @Test
  public void testNewExpressionManager_whenHashMap() {
    // Arrange and Act
    ExpressionManager actualExpressionManager = new ExpressionManager(new HashMap<>());

    // Assert
    ExpressionFactory expressionFactory = actualExpressionManager.expressionFactory;
    assertTrue(expressionFactory instanceof ExpressionFactoryImpl);
    assertNull(expressionFactory.getStreamELResolver());
    assertNull(actualExpressionManager.getCustomFunctionProviders());
    assertNull(expressionFactory.getInitFunctionMap());
    assertTrue(actualExpressionManager.getBeans().isEmpty());
  }

  /**
   * Test {@link ExpressionManager#createExpression(String)}.
   * <p>
   * Method under test: {@link ExpressionManager#createExpression(String)}
   */
  @Test
  public void testCreateExpression() {
    // Arrange and Act
    Expression actualCreateExpressionResult = expressionManager.createExpression("Expression");

    // Assert
    assertTrue(actualCreateExpressionResult instanceof JuelExpression);
    assertEquals("Expression", actualCreateExpressionResult.getExpressionText());
  }

  /**
   * Test {@link ExpressionManager#getElContext(Map)} with
   * {@code availableVariables}.
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(Map)}
   */
  @Test
  public void testGetElContextWithAvailableVariables() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    // Act
    ELContext actualElContext = expressionManager.getElContext(new HashMap<>());

    // Assert
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualElContext.getEvaluationListeners());
    assertNull(actualElContext.getLocale());
    assertFalse(actualElContext.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#getElContext(Map)} with
   * {@code availableVariables}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()}.</li>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(Map)}
   */
  @Test
  public void testGetElContextWithAvailableVariables_givenExpressionManager_whenHashMap() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    ELContext actualElContext = expressionManager.getElContext(new HashMap<>());

    // Assert
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualElContext.getEvaluationListeners());
    assertNull(actualElContext.getLocale());
    assertFalse(actualElContext.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#getElContext(Map)} with
   * {@code availableVariables}.
   * <ul>
   *   <li>Given {@code getFieldValue}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(Map)}
   */
  @Test
  public void testGetElContextWithAvailableVariables_givenGetFieldValue() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();

    HashMap<String, Object> availableVariables = new HashMap<>();
    availableVariables.computeIfPresent("getFieldValue", mock(BiFunction.class));

    // Act
    ELContext actualElContext = expressionManager.getElContext(availableVariables);

    // Assert
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualElContext.getEvaluationListeners());
    assertNull(actualElContext.getLocale());
    assertFalse(actualElContext.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#getElContext(Map)} with
   * {@code availableVariables}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(Map)}
   */
  @Test
  public void testGetElContextWithAvailableVariables_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    ELContext actualElContext = expressionManager.getElContext(new HashMap<>());

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualElContext.getEvaluationListeners());
    assertNull(actualElContext.getLocale());
    assertFalse(actualElContext.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#getElContext(Map)} with
   * {@code availableVariables}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(Map)}
   */
  @Test
  public void testGetElContextWithAvailableVariables_thenCallsAddCustomFunctions2() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    ELContext actualElContext = expressionManager.getElContext(new HashMap<>());

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualElContext.getEvaluationListeners());
    assertNull(actualElContext.getLocale());
    assertFalse(actualElContext.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#getElContext(VariableScope)} with
   * {@code variableScope}.
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(VariableScope)}
   */
  @Test
  public void testGetElContextWithVariableScope() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(null);
    expressionManager.setCustomFunctionProviders(null);
    ExecutionEntityImpl variableScope = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    variableScope.setCachedElContext(null);

    // Act
    ELContext actualElContext = expressionManager.getElContext(variableScope);

    // Assert
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
  }

  /**
   * Test {@link ExpressionManager#getElContext(VariableScope)} with
   * {@code variableScope}.
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(VariableScope)}
   */
  @Test
  public void testGetElContextWithVariableScope2() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(null);
    expressionManager.setCustomFunctionProviders(new ArrayList<>());
    ExecutionEntityImpl variableScope = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    variableScope.setCachedElContext(null);

    // Act
    ELContext actualElContext = expressionManager.getElContext(variableScope);

    // Assert
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
  }

  /**
   * Test {@link ExpressionManager#getElContext(VariableScope)} with
   * {@code variableScope}.
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(VariableScope)}
   */
  @Test
  public void testGetElContextWithVariableScope3() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    MockExpressionManager mockExpressionManager = new MockExpressionManager();
    mockExpressionManager.addBeansResolver(new CompositeELResolver());
    mockExpressionManager.setBeans(null);
    mockExpressionManager.setCustomFunctionProviders(customFunctionProviders);
    ExecutionEntityImpl variableScope = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    variableScope.setCachedElContext(null);

    // Act
    ELContext actualElContext = mockExpressionManager.getElContext(variableScope);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
  }

  /**
   * Test {@link ExpressionManager#getElContext(VariableScope)} with
   * {@code variableScope}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()} Beans is
   * {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(VariableScope)}
   */
  @Test
  public void testGetElContextWithVariableScope_givenExpressionManagerBeansIsHashMap() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(new HashMap<>());
    expressionManager.setCustomFunctionProviders(null);
    ExecutionEntityImpl variableScope = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    variableScope.setCachedElContext(null);

    // Act
    ELContext actualElContext = expressionManager.getElContext(variableScope);

    // Assert
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
  }

  /**
   * Test {@link ExpressionManager#getElContext(VariableScope)} with
   * {@code variableScope}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()}.</li>
   *   <li>When SharedInstance.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(VariableScope)}
   */
  @Test
  public void testGetElContextWithVariableScope_givenExpressionManager_whenSharedInstance() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    ELContext actualElContext = expressionManager.getElContext(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
  }

  /**
   * Test {@link ExpressionManager#getElContext(VariableScope)} with
   * {@code variableScope}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(VariableScope)}
   */
  @Test
  public void testGetElContextWithVariableScope_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(null);
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    ExecutionEntityImpl variableScope = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    variableScope.setCachedElContext(null);

    // Act
    ELContext actualElContext = expressionManager.getElContext(variableScope);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
  }

  /**
   * Test {@link ExpressionManager#getElContext(VariableScope)} with
   * {@code variableScope}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(VariableScope)}
   */
  @Test
  public void testGetElContextWithVariableScope_thenCallsAddCustomFunctions2() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(null);
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    ExecutionEntityImpl variableScope = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    variableScope.setCachedElContext(null);

    // Act
    ELContext actualElContext = expressionManager.getElContext(variableScope);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualElContext.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualElContext instanceof ActivitiElContext);
    assertTrue(actualElContext.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
  }

  /**
   * Test {@link ExpressionManager#getElContext(VariableScope)} with
   * {@code variableScope}.
   * <ul>
   *   <li>Then return {@link ParsingElContext} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#getElContext(VariableScope)}
   */
  @Test
  public void testGetElContextWithVariableScope_thenReturnParsingElContext() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(null);
    expressionManager.setCustomFunctionProviders(null);
    ExecutionEntityImpl variableScope = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ParsingElContext cachedElContext = new ParsingElContext();
    variableScope.setCachedElContext(cachedElContext);

    // Act and Assert
    assertSame(cachedElContext, expressionManager.getElContext(variableScope));
  }

  /**
   * Test {@link ExpressionManager#createElContext(VariableScope)}.
   * <p>
   * Method under test: {@link ExpressionManager#createElContext(VariableScope)}
   */
  @Test
  public void testCreateElContext() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    MockExpressionManager mockExpressionManager = new MockExpressionManager();
    mockExpressionManager.addBeansResolver(new CompositeELResolver());
    mockExpressionManager.setBeans(null);
    mockExpressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    ActivitiElContext actualCreateElContextResult = mockExpressionManager
        .createElContext(NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateElContextResult.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualCreateElContextResult.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualCreateElContextResult.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualCreateElContextResult.getEvaluationListeners());
    assertNull(actualCreateElContextResult.getLocale());
    assertFalse(actualCreateElContextResult.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#createElContext(VariableScope)}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElContext(VariableScope)}
   */
  @Test
  public void testCreateElContext_givenExpressionManager() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    ActivitiElContext actualCreateElContextResult = expressionManager
        .createElContext(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualCreateElContextResult.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualCreateElContextResult.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualCreateElContextResult.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualCreateElContextResult.getEvaluationListeners());
    assertNull(actualCreateElContextResult.getLocale());
    assertFalse(actualCreateElContextResult.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#createElContext(VariableScope)}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()} Beans is
   * {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElContext(VariableScope)}
   */
  @Test
  public void testCreateElContext_givenExpressionManagerBeansIsHashMap() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(new HashMap<>());
    expressionManager.setCustomFunctionProviders(null);

    // Act
    ActivitiElContext actualCreateElContextResult = expressionManager
        .createElContext(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualCreateElContextResult.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualCreateElContextResult.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualCreateElContextResult.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualCreateElContextResult.getEvaluationListeners());
    assertNull(actualCreateElContextResult.getLocale());
    assertFalse(actualCreateElContextResult.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#createElContext(VariableScope)}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()} Beans is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElContext(VariableScope)}
   */
  @Test
  public void testCreateElContext_givenExpressionManagerBeansIsNull() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(null);
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    // Act
    ActivitiElContext actualCreateElContextResult = expressionManager
        .createElContext(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualCreateElContextResult.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualCreateElContextResult.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualCreateElContextResult.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualCreateElContextResult.getEvaluationListeners());
    assertNull(actualCreateElContextResult.getLocale());
    assertFalse(actualCreateElContextResult.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#createElContext(VariableScope)}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElContext(VariableScope)}
   */
  @Test
  public void testCreateElContext_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(null);
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    ActivitiElContext actualCreateElContextResult = expressionManager
        .createElContext(NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateElContextResult.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualCreateElContextResult.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualCreateElContextResult.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualCreateElContextResult.getEvaluationListeners());
    assertNull(actualCreateElContextResult.getLocale());
    assertFalse(actualCreateElContextResult.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#createElContext(VariableScope)}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElContext(VariableScope)}
   */
  @Test
  public void testCreateElContext_thenCallsAddCustomFunctions2() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(null);
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    // Act
    ActivitiElContext actualCreateElContextResult = expressionManager
        .createElContext(NoExecutionVariableScope.getSharedInstance());

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateElContextResult.getELResolver() instanceof CompositeELResolver);
    assertTrue(actualCreateElContextResult.getFunctionMapper() instanceof ActivitiFunctionMapper);
    assertTrue(actualCreateElContextResult.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualCreateElContextResult.getEvaluationListeners());
    assertNull(actualCreateElContextResult.getLocale());
    assertFalse(actualCreateElContextResult.isPropertyResolved());
  }

  /**
   * Test {@link ExpressionManager#createElResolver(VariableScope)}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElResolver(VariableScope)}
   */
  @Test
  public void testCreateElResolver_givenExpressionManager() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    ELResolver actualCreateElResolverResult = expressionManager
        .createElResolver(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualCreateElResolverResult instanceof CompositeELResolver);
    assertFalse(actualCreateElResolverResult.getFeatureDescriptors(null, null).hasNext());
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCreateElResolverResult.getCommonPropertyType(null, null));
  }

  /**
   * Test {@link ExpressionManager#createElResolver(VariableScope)}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()} Beans is
   * {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElResolver(VariableScope)}
   */
  @Test
  public void testCreateElResolver_givenExpressionManagerBeansIsHashMap() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(new HashMap<>());

    // Act
    ELResolver actualCreateElResolverResult = expressionManager
        .createElResolver(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualCreateElResolverResult instanceof CompositeELResolver);
    assertFalse(actualCreateElResolverResult.getFeatureDescriptors(null, null).hasNext());
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCreateElResolverResult.getCommonPropertyType(null, null));
  }

  /**
   * Test {@link ExpressionManager#createElResolver(VariableScope)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElResolver(VariableScope)}
   */
  @Test
  public void testCreateElResolver_givenHashMapComputeIfPresentNullAndBiFunction() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    beans.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(beans);

    // Act
    ELResolver actualCreateElResolverResult = expressionManager
        .createElResolver(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualCreateElResolverResult instanceof CompositeELResolver);
    assertFalse(actualCreateElResolverResult.getFeatureDescriptors(null, null).hasNext());
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCreateElResolverResult.getCommonPropertyType(null, null));
  }

  /**
   * Test {@link ExpressionManager#createElResolver(VariableScope)}.
   * <ul>
   *   <li>Given {@link MockExpressionManager} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionManager#createElResolver(VariableScope)}
   */
  @Test
  public void testCreateElResolver_givenMockExpressionManager() {
    // Arrange and Act
    ELResolver actualCreateElResolverResult = ((ExpressionManager) new MockExpressionManager())
        .createElResolver(NoExecutionVariableScope.getSharedInstance());

    // Assert
    assertTrue(actualCreateElResolverResult instanceof CompositeELResolver);
    assertFalse(actualCreateElResolverResult.getFeatureDescriptors(null, null).hasNext());
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCreateElResolverResult.getCommonPropertyType(null, null));
  }

  /**
   * Test {@link ExpressionManager#addBeansResolver(CompositeELResolver)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver_givenHashMapComputeIfPresentNullAndBiFunction() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    beans.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(beans);
    CompositeELResolver elResolver = new CompositeELResolver();

    // Act
    expressionManager.addBeansResolver(elResolver);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, elResolver.getCommonPropertyType(null, null));
  }

  /**
   * Test {@link ExpressionManager#addBeansResolver(CompositeELResolver)}.
   * <ul>
   *   <li>Then {@link CompositeELResolver} (default constructor) CommonPropertyType
   * {@code null} is {@code null} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver_thenCompositeELResolverCommonPropertyTypeNullIsNullIsNull() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    CompositeELResolver elResolver = new CompositeELResolver();

    // Act
    expressionManager.addBeansResolver(elResolver);

    // Assert that nothing has changed
    assertNull(elResolver.getCommonPropertyType(null, null));
  }

  /**
   * Test {@link ExpressionManager#addBeansResolver(CompositeELResolver)}.
   * <ul>
   *   <li>Then {@link CompositeELResolver} (default constructor) CommonPropertyType
   * {@code null} is {@code null} is {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionManager#addBeansResolver(CompositeELResolver)}
   */
  @Test
  public void testAddBeansResolver_thenCompositeELResolverCommonPropertyTypeNullIsNullIsObject() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setBeans(new HashMap<>());
    CompositeELResolver elResolver = new CompositeELResolver();

    // Act
    expressionManager.addBeansResolver(elResolver);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, elResolver.getCommonPropertyType(null, null));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExpressionManager#setBeans(Map)}
   *   <li>{@link ExpressionManager#setCustomFunctionProviders(List)}
   *   <li>{@link ExpressionManager#setExpressionFactory(ExpressionFactory)}
   *   <li>{@link ExpressionManager#getBeans()}
   *   <li>{@link ExpressionManager#getCustomFunctionProviders()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    HashMap<Object, Object> beans = new HashMap<>();

    // Act
    expressionManager.setBeans(beans);
    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    expressionManager.setExpressionFactory(new ExpressionFactoryImpl());
    Map<Object, Object> actualBeans = expressionManager.getBeans();
    List<CustomFunctionProvider> actualCustomFunctionProviders = expressionManager.getCustomFunctionProviders();

    // Assert that nothing has changed
    assertTrue(actualCustomFunctionProviders.isEmpty());
    assertTrue(actualBeans.isEmpty());
    assertSame(customFunctionProviders, actualCustomFunctionProviders);
    assertSame(beans, actualBeans);
  }
}
