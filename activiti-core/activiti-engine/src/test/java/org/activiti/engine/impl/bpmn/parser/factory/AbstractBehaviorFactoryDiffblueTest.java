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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.bpmn.helper.DefaultClassDelegateFactory;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.DefaultThrowMessageJavaDelegate;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AbstractBehaviorFactoryDiffblueTest {
  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager(true);
    expressionManager.setCustomFunctionProviders(null);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        defaultActivityBehaviorFactory.createFieldDeclarations(fieldList);

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
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is empty string.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_givenFieldExtensionExpressionIsEmptyString() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager(true);
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        defaultActivityBehaviorFactory.createFieldDeclarations(fieldList);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) Expression is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_givenFieldExtensionExpressionIsNull() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager(true);
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression(null);

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        defaultActivityBehaviorFactory.createFieldDeclarations(fieldList);

    // Assert
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_givenFieldExtension_thenReturnSizeIsTwo() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory();

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(new FieldExtension());
    fieldList.add(new FieldExtension());

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        defaultActivityBehaviorFactory.createFieldDeclarations(fieldList);

    // Assert
    assertEquals(2, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(1);
    assertTrue(getResult.getValue() instanceof FixedValue);
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   *
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);

    ExpressionManager expressionManager = new ExpressionManager(true);
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        defaultActivityBehaviorFactory.createFieldDeclarations(fieldList);

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
   *
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
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

    ExpressionManager expressionManager = new ExpressionManager(true);
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        defaultActivityBehaviorFactory.createFieldDeclarations(fieldList);

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
   *
   * <ul>
   *   <li>Then first Value return {@link JuelExpression}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_thenFirstValueReturnJuelExpression() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager(true);
    expressionManager.setCustomFunctionProviders(new ArrayList<>());

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        defaultActivityBehaviorFactory.createFieldDeclarations(fieldList);

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
   *
   * <ul>
   *   <li>Then return first Value ExpressionText is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_thenReturnFirstValueExpressionTextIsNull() {
    // Arrange
    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));
    customFunctionProviders.add(mock(CustomFunctionProvider.class));

    ExpressionManager expressionManager = mock(ExpressionManager.class);
    FixedValue fixedValue = new FixedValue(JSONObject.NULL);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(fixedValue);
    doNothing()
        .when(expressionManager)
        .setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);

    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory());
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setExpression("not empty");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult =
        defaultActivityBehaviorFactory.createFieldDeclarations(fieldList);

    // Assert
    verify(expressionManager).createExpression("not empty");
    verify(expressionManager).setCustomFunctionProviders(isA(List.class));
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof FixedValue);
    assertEquals("null", ((FixedValue) value).getExpressionText());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
    assertNull(getResult.getName());
    assertSame(fixedValue, value);
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List AbstractBehaviorFactory.createFieldDeclarations(List)"})
  public void testCreateFieldDeclarations_whenArrayList_thenReturnEmpty() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory();

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createFieldDeclarations(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getExpressionManager()}.
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getExpressionManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExpressionManager AbstractBehaviorFactory.getExpressionManager()"})
  public void testGetExpressionManager() {
    // Arrange, Act and Assert
    assertNull(new DefaultActivityBehaviorFactory().getExpressionManager());
  }

  /**
   * Test {@link AbstractBehaviorFactory#setExpressionManager(ExpressionManager)}.
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#setExpressionManager(ExpressionManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBehaviorFactory.setExpressionManager(ExpressionManager)"})
  public void testSetExpressionManager() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory();
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Assert
    assertSame(expressionManager, defaultActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getThrowMessageDelegateFactory()}.
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getThrowMessageDelegateFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ThrowMessageDelegateFactory AbstractBehaviorFactory.getThrowMessageDelegateFactory()"
  })
  public void testGetThrowMessageDelegateFactory() {
    // Arrange, Act and Assert
    assertTrue(
        new DefaultActivityBehaviorFactory().getThrowMessageDelegateFactory().create()
            instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Test {@link
   * AbstractBehaviorFactory#setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)}.
   *
   * <p>Method under test: {@link
   * AbstractBehaviorFactory#setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractBehaviorFactory.setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)"
  })
  public void testSetThrowMessageDelegateFactory() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory();
    TestThrowMessageDelegateFactory throwMessageDelegateFactory =
        new TestThrowMessageDelegateFactory();

    // Act
    defaultActivityBehaviorFactory.setThrowMessageDelegateFactory(throwMessageDelegateFactory);

    // Assert
    assertSame(
        throwMessageDelegateFactory,
        defaultActivityBehaviorFactory.getThrowMessageDelegateFactory());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"
  })
  public void testGetMessagePayloadMappingProviderFactory() {
    // Arrange and Act
    MessagePayloadMappingProviderFactory actualMessagePayloadMappingProviderFactory =
        new DefaultActivityBehaviorFactory().getMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();

    // Assert
    assertTrue(
        actualMessagePayloadMappingProviderFactory.create(
                bpmnEvent, messageEventDefinition, new ExpressionManager())
            instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualMessagePayloadMappingProviderFactory
            instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"
  })
  public void testGetMessagePayloadMappingProviderFactory2() {
    // Arrange and Act
    MessagePayloadMappingProviderFactory actualMessagePayloadMappingProviderFactory =
        new DefaultActivityBehaviorFactory().getMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    // Assert
    assertTrue(
        actualMessagePayloadMappingProviderFactory.create(
                bpmnEvent, messageEventDefinition, new ExpressionManager())
            instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualMessagePayloadMappingProviderFactory
            instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"
  })
  public void testGetMessagePayloadMappingProviderFactory3() {
    // Arrange and Act
    MessagePayloadMappingProviderFactory actualMessagePayloadMappingProviderFactory =
        new DefaultActivityBehaviorFactory().getMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    fieldExtensions.add(new FieldExtension());
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);

    // Assert
    assertTrue(
        actualMessagePayloadMappingProviderFactory.create(
                bpmnEvent, messageEventDefinition, new ExpressionManager())
            instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualMessagePayloadMappingProviderFactory
            instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   *
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"
  })
  public void testGetMessagePayloadMappingProviderFactory_thenCallsAddCustomFunctions() {
    // Arrange and Act
    MessagePayloadMappingProviderFactory actualMessagePayloadMappingProviderFactory =
        new DefaultActivityBehaviorFactory().getMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    MessagePayloadMappingProvider actualCreateResult =
        actualMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, expressionManager);

    // Assert
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualMessagePayloadMappingProviderFactory
            instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   *
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"
  })
  public void testGetMessagePayloadMappingProviderFactory_thenCallsAddCustomFunctions2() {
    // Arrange and Act
    MessagePayloadMappingProviderFactory actualMessagePayloadMappingProviderFactory =
        new DefaultActivityBehaviorFactory().getMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());
    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    MessagePayloadMappingProvider actualCreateResult =
        actualMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, expressionManager);

    // Assert
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualMessagePayloadMappingProviderFactory
            instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   *
   * <ul>
   *   <li>Then calls {@link FieldExtension#getExpression()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"
  })
  public void testGetMessagePayloadMappingProviderFactory_thenCallsGetExpression() {
    // Arrange and Act
    MessagePayloadMappingProviderFactory actualMessagePayloadMappingProviderFactory =
        new DefaultActivityBehaviorFactory().getMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);
    MessagePayloadMappingProvider actualCreateResult =
        actualMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, new ExpressionManager());

    // Assert
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualMessagePayloadMappingProviderFactory
            instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   *
   * <ul>
   *   <li>Then calls {@link FieldExtension#getExpression()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"
  })
  public void testGetMessagePayloadMappingProviderFactory_thenCallsGetExpression2() {
    // Arrange and Act
    MessagePayloadMappingProviderFactory actualMessagePayloadMappingProviderFactory =
        new DefaultActivityBehaviorFactory().getMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("Expression");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);
    ExpressionManager expressionManager = new ExpressionManager();
    expressionManager.setCustomFunctionProviders(new ArrayList<>());
    MessagePayloadMappingProvider actualCreateResult =
        actualMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, expressionManager);

    // Assert
    verify(fieldExtension, atLeast(1)).getExpression();
    verify(fieldExtension).getFieldName();
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualMessagePayloadMappingProviderFactory
            instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   *
   * <ul>
   *   <li>Then calls {@link FieldExtension#getStringValue()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessagePayloadMappingProviderFactory AbstractBehaviorFactory.getMessagePayloadMappingProviderFactory()"
  })
  public void testGetMessagePayloadMappingProviderFactory_thenCallsGetStringValue() {
    // Arrange and Act
    MessagePayloadMappingProviderFactory actualMessagePayloadMappingProviderFactory =
        new DefaultActivityBehaviorFactory().getMessagePayloadMappingProviderFactory();
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");
    when(fieldExtension.getStringValue()).thenReturn("42");
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setFieldExtensions(fieldExtensions);
    MessagePayloadMappingProvider actualCreateResult =
        actualMessagePayloadMappingProviderFactory.create(
            bpmnEvent, messageEventDefinition, new ExpressionManager());

    // Assert
    verify(fieldExtension).getExpression();
    verify(fieldExtension).getFieldName();
    verify(fieldExtension).getStringValue();
    assertTrue(actualCreateResult instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        actualMessagePayloadMappingProviderFactory
            instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test {@link
   * AbstractBehaviorFactory#setMessagePayloadMappingProviderFactory(MessagePayloadMappingProviderFactory)}.
   *
   * <p>Method under test: {@link
   * AbstractBehaviorFactory#setMessagePayloadMappingProviderFactory(MessagePayloadMappingProviderFactory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractBehaviorFactory.setMessagePayloadMappingProviderFactory(MessagePayloadMappingProviderFactory)"
  })
  public void testSetMessagePayloadMappingProviderFactory() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory();
    MessagePayloadMappingProviderFactory messagePayloadMappingProviderFactory =
        mock(MessagePayloadMappingProviderFactory.class);

    // Act
    defaultActivityBehaviorFactory.setMessagePayloadMappingProviderFactory(
        messagePayloadMappingProviderFactory);

    // Assert
    assertSame(
        messagePayloadMappingProviderFactory,
        defaultActivityBehaviorFactory.getMessagePayloadMappingProviderFactory());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getMessageExecutionContextFactory()}.
   *
   * <p>Method under test: {@link AbstractBehaviorFactory#getMessageExecutionContextFactory()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageExecutionContextFactory AbstractBehaviorFactory.getMessageExecutionContextFactory()"
  })
  public void testGetMessageExecutionContextFactory() {
    // Arrange and Act
    MessageExecutionContextFactory actualMessageExecutionContextFactory =
        new DefaultActivityBehaviorFactory().getMessageExecutionContextFactory();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessagePayloadMappingProvider messagePayloadMappingProvider =
        mock(MessagePayloadMappingProvider.class);
    ExpressionManager expressionManager = new ExpressionManager();
    MessageExecutionContext actualCreateResult =
        actualMessageExecutionContextFactory.create(
            messageEventDefinition, messagePayloadMappingProvider, expressionManager);

    // Assert
    assertTrue(actualCreateResult instanceof DefaultMessageExecutionContext);
    assertTrue(
        actualMessageExecutionContextFactory instanceof DefaultMessageExecutionContextFactory);
    assertSame(
        expressionManager,
        ((DefaultMessageExecutionContext) actualCreateResult).getExpressionManager());
    assertSame(
        messagePayloadMappingProvider,
        ((DefaultMessageExecutionContext) actualCreateResult).getMessagePayloadMappingProvider());
  }

  /**
   * Test {@link
   * AbstractBehaviorFactory#setMessageExecutionContextFactory(MessageExecutionContextFactory)}.
   *
   * <p>Method under test: {@link
   * AbstractBehaviorFactory#setMessageExecutionContextFactory(MessageExecutionContextFactory)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractBehaviorFactory.setMessageExecutionContextFactory(MessageExecutionContextFactory)"
  })
  public void testSetMessageExecutionContextFactory() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory =
        new DefaultActivityBehaviorFactory();
    MessageExecutionContextFactory messageExecutionContextFactory =
        mock(MessageExecutionContextFactory.class);

    // Act
    defaultActivityBehaviorFactory.setMessageExecutionContextFactory(
        messageExecutionContextFactory);

    // Assert
    assertSame(
        messageExecutionContextFactory,
        defaultActivityBehaviorFactory.getMessageExecutionContextFactory());
  }
}
