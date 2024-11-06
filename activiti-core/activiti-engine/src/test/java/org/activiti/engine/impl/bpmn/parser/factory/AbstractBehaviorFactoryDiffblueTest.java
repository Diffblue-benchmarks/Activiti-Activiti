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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.DefaultThrowMessageJavaDelegate;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProviderFactory;
import org.activiti.engine.impl.delegate.ThrowMessageDelegateFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.test.bpmn.event.message.MessageThrowCatchEventTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractBehaviorFactoryDiffblueTest {
  @InjectMocks
  private DefaultActivityBehaviorFactory defaultActivityBehaviorFactory;

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   *   <li>Then return first Name is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  public void testCreateFieldDeclarations_givenFieldExtension_thenReturnFirstNameIsNull() {
    // Arrange
    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(new FieldExtension());

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
   * Method under test:
   * {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  public void testCreateFieldDeclarations_givenFieldExtension_thenReturnSizeIsTwo() {
    // Arrange
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
   *   <li>Then return first Value ExpressionText is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  public void testCreateFieldDeclarations_thenReturnFirstValueExpressionTextIs42() {
    // Arrange
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.getExpression()).thenReturn("");
    when(fieldExtension.getFieldName()).thenReturn("Field Name");
    when(fieldExtension.getStringValue()).thenReturn("42");

    ArrayList<FieldExtension> fieldList = new ArrayList<>();
    fieldList.add(fieldExtension);

    // Act
    List<FieldDeclaration> actualCreateFieldDeclarationsResult = defaultActivityBehaviorFactory
        .createFieldDeclarations(fieldList);

    // Assert
    verify(fieldExtension).getExpression();
    verify(fieldExtension).getFieldName();
    verify(fieldExtension).getStringValue();
    assertEquals(1, actualCreateFieldDeclarationsResult.size());
    FieldDeclaration getResult = actualCreateFieldDeclarationsResult.get(0);
    Object value = getResult.getValue();
    assertTrue(value instanceof FixedValue);
    assertEquals("42", ((FixedValue) value).getExpressionText());
    assertEquals("Field Name", getResult.getName());
    assertEquals("org.activiti.engine.delegate.Expression", getResult.getType());
  }

  /**
   * Test {@link AbstractBehaviorFactory#createFieldDeclarations(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#createFieldDeclarations(List)}
   */
  @Test
  public void testCreateFieldDeclarations_whenArrayList_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(defaultActivityBehaviorFactory.createFieldDeclarations(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getExpressionManager()}.
   * <p>
   * Method under test: {@link AbstractBehaviorFactory#getExpressionManager()}
   */
  @Test
  public void testGetExpressionManager() {
    // Arrange, Act and Assert
    assertNull(defaultActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test {@link AbstractBehaviorFactory#setExpressionManager(ExpressionManager)}.
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#setExpressionManager(ExpressionManager)}
   */
  @Test
  public void testSetExpressionManager() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();

    // Act
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Assert
    assertSame(expressionManager, defaultActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test {@link AbstractBehaviorFactory#setExpressionManager(ExpressionManager)}.
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#setExpressionManager(ExpressionManager)}
   */
  @Test
  public void testSetExpressionManager2() {
    // Arrange
    ExpressionManager expressionManager = mock(ExpressionManager.class);

    // Act
    defaultActivityBehaviorFactory.setExpressionManager(expressionManager);

    // Assert
    assertSame(expressionManager, defaultActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test {@link AbstractBehaviorFactory#getThrowMessageDelegateFactory()}.
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#getThrowMessageDelegateFactory()}
   */
  @Test
  public void testGetThrowMessageDelegateFactory() {
    // Arrange
    DefaultActivityBehaviorFactory defaultActivityBehaviorFactory = new DefaultActivityBehaviorFactory();
    defaultActivityBehaviorFactory
        .setMessagePayloadMappingProviderFactory(mock(MessagePayloadMappingProviderFactory.class));

    // Act and Assert
    assertTrue(defaultActivityBehaviorFactory.getThrowMessageDelegateFactory()
        .create() instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Test {@link AbstractBehaviorFactory#getThrowMessageDelegateFactory()}.
   * <ul>
   *   <li>Given
   * {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#getThrowMessageDelegateFactory()}
   */
  @Test
  public void testGetThrowMessageDelegateFactory_givenDefaultActivityBehaviorFactory() {
    // Arrange, Act and Assert
    assertTrue((new DefaultActivityBehaviorFactory()).getThrowMessageDelegateFactory()
        .create() instanceof DefaultThrowMessageJavaDelegate);
  }

  /**
   * Test
   * {@link AbstractBehaviorFactory#setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)}.
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)}
   */
  @Test
  public void testSetThrowMessageDelegateFactory() {
    // Arrange
    MessageThrowCatchEventTest.TestThrowMessageDelegateFactory throwMessageDelegateFactory = new MessageThrowCatchEventTest.TestThrowMessageDelegateFactory();

    // Act
    defaultActivityBehaviorFactory.setThrowMessageDelegateFactory(throwMessageDelegateFactory);

    // Assert
    assertSame(throwMessageDelegateFactory, defaultActivityBehaviorFactory.getThrowMessageDelegateFactory());
  }

  /**
   * Test
   * {@link AbstractBehaviorFactory#setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)}.
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#setThrowMessageDelegateFactory(ThrowMessageDelegateFactory)}
   */
  @Test
  public void testSetThrowMessageDelegateFactory2() {
    // Arrange
    MessageThrowCatchEventTest.TestThrowMessageDelegateFactory throwMessageDelegateFactory = mock(
        MessageThrowCatchEventTest.TestThrowMessageDelegateFactory.class);

    // Act
    defaultActivityBehaviorFactory.setThrowMessageDelegateFactory(throwMessageDelegateFactory);

    // Assert
    assertSame(throwMessageDelegateFactory, defaultActivityBehaviorFactory.getThrowMessageDelegateFactory());
  }

  /**
   * Test
   * {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}.
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#getMessagePayloadMappingProviderFactory()}
   */
  @Test
  public void testGetMessagePayloadMappingProviderFactory() {
    // Arrange, Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof BpmnMessagePayloadMappingProviderFactory);
  }

  /**
   * Test
   * {@link AbstractBehaviorFactory#setMessagePayloadMappingProviderFactory(MessagePayloadMappingProviderFactory)}.
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#setMessagePayloadMappingProviderFactory(MessagePayloadMappingProviderFactory)}
   */
  @Test
  public void testSetMessagePayloadMappingProviderFactory() {
    // Arrange
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
   * Method under test:
   * {@link AbstractBehaviorFactory#getMessageExecutionContextFactory()}
   */
  @Test
  public void testGetMessageExecutionContextFactory() {
    // Arrange, Act and Assert
    assertTrue(defaultActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
  }

  /**
   * Test
   * {@link AbstractBehaviorFactory#setMessageExecutionContextFactory(MessageExecutionContextFactory)}.
   * <p>
   * Method under test:
   * {@link AbstractBehaviorFactory#setMessageExecutionContextFactory(MessageExecutionContextFactory)}
   */
  @Test
  public void testSetMessageExecutionContextFactory() {
    // Arrange
    MessageExecutionContextFactory messageExecutionContextFactory = mock(MessageExecutionContextFactory.class);

    // Act
    defaultActivityBehaviorFactory.setMessageExecutionContextFactory(messageExecutionContextFactory);

    // Assert
    assertSame(messageExecutionContextFactory, defaultActivityBehaviorFactory.getMessageExecutionContextFactory());
  }
}
