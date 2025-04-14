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
package org.activiti.engine.impl.bpmn.parser.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.DefaultThrowMessageJavaDelegate;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.ThrowMessageDelegateFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.test.bpmn.event.message.MessageThrowCatchEventTest;
import org.activiti.engine.test.bpmn.event.message.MessageThrowCatchEventTest.TestThrowMessageDelegateFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class AbstractBehaviorFactoryDiffblueTest {
  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("not empty", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>Given {@link ExpressionManager#ExpressionManager()} CustomFunctionProviders is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_givenExpressionManagerCustomFunctionProvidersIsNull() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("not empty", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_givenFieldExtensionExpressionIsEmptyString() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_givenFieldExtensionExpressionIsNull() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_givenFieldExtension_thenReturnSizeIsTwo() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(new FieldExtension());
    fieldList.add(new FieldExtension());

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    assertEquals(2, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(1);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("not empty", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_thenCallsAddCustomFunctions2() {
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

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof JuelExpression);
    assertEquals("not empty", ((JuelExpression) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>Then return first Value ExpressionText is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_thenReturnFirstValueExpressionTextIsNull() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    FixedValue fixedValue = new FixedValue(JSONObject.NULL);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(fixedValue);
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    verify(expressionManager).createExpression(eq("not empty"));
    verify(expressionManager).setCustomFunctionProviders(isNull());
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    Object value = actualCreateFieldDeclarationsResult.get(0).getValue();
    assertTrue(value instanceof FixedValue);
    assertEquals("null", ((FixedValue) value).getExpressionText());
    assertSame(fixedValue, value);
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_whenArrayList_thenReturnEmpty() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createFieldDeclarations(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getExpressionManager()}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#getExpressionManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExpressionManager AbstractBehaviorFactory.getExpressionManager()"})
  public void testGetExpressionManager() {
    // Arrange, Act and Assert
    assertNull((new DefaultActivityBehaviorFactory()).getExpressionManager());
  }

  /**
   * Test {@link AbstractBehaviorFactory#setExpressionManager(ExpressionManager)}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#setExpressionManager(ExpressionManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractBehaviorFactory.setExpressionManager(ExpressionManager)"})
  public void testSetExpressionManager() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Assert
    assertSame(expressionManager, defaultActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getThrowMessageDelegateFactory()}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#getThrowMessageDelegateFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ThrowMessageDelegateFactory AbstractBehaviorFactory.getThrowMessageDelegateFactory()"})
  public void testGetThrowMessageDelegateFactory() {
    // Arrange, Act and Assert
    assertTrue((new DefaultActivityBehaviorFactory()).getThrowMessageDelegateFactory()
        .create() instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Test {@link AbstractBehaviorFactory#setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractBehaviorFactory.setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)"})
  public void testSetThrowMessageDelegateFactory() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    TestThrowMessageDelegateFactory throwMessageDelegateFactory = new TestThrowMessageDelegateFactory();

    // Act
    defaultActivityBehaviorFactory.setThrowMessageDelegateFactory(throwMessageDelegateFactory);

    // Assert
    assertSame(throwMessageDelegateFactory, defaultActivityBehaviorFactory.getThrowMessageDelegateFactory());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"})
  public void testGetMessagePayloadMappingProviderFactory() {
    // Arrange, Act and Assert
    assertTrue((new DefaultActivityBehaviorFactory())
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#setMessagePayloadMappingProviderFactory(MessagePayloadMappingProviderFactory)}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#setMessagePayloadMappingProviderFactory(MessagePayloadMappingProviderFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void AbstractBehaviorFactory.setMessagePayloadMappingProviderFactory(MessagePayloadMappingProviderFactory)"})
  public void testSetMessagePayloadMappingProviderFactory() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    MessagePayloadMappingProviderFactory messagePayloadMappingProviderFactory = mock(
        MessagePayloadMappingProviderFactory.class);

    // Act
    defaultActivityBehaviorFactory.setMessagePayloadMappingProviderFactory(messagePayloadMappingProviderFactory);

    // Assert
    assertSame(messagePayloadMappingProviderFactory,
        defaultActivityBehaviorFactory.getMessagePayloadMappingProviderFactory());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessageExecutionContextFactory()}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#getMessageExecutionContextFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"MessageExecutionContextFactory AbstractBehaviorFactory.getMessageExecutionContextFactory()"})
  public void testGetMessageExecutionContextFactory() {
    // Arrange, Act and Assert
    assertTrue((new DefaultActivityBehaviorFactory())
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#setMessageExecutionContextFactory(MessageExecutionContextFactory)}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#setMessageExecutionContextFactory(MessageExecutionContextFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractBehaviorFactory.setMessageExecutionContextFactory(MessageExecutionContextFactory)"})
  public void testSetMessageExecutionContextFactory() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    MessageExecutionContextFactory messageExecutionContextFactory = mock(MessageExecutionContextFactory.class);

    // Act
    defaultActivityBehaviorFactory.setMessageExecutionContextFactory(messageExecutionContextFactory);

    // Assert
    assertSame(messageExecutionContextFactory, defaultActivityBehaviorFactory.getMessageExecutionContextFactory());
  }
}
