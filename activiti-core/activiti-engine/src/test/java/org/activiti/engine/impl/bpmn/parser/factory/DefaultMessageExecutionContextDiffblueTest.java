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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.ThrowMessage;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultMessageExecutionContextDiffblueTest {
  @InjectMocks
  private DefaultMessageExecutionContext defaultMessageExecutionContext;

  @Mock
  private ExpressionManager expressionManager;

  @Mock
  private MessageEventDefinition messageEventDefinition;

  @Mock
  private MessagePayloadMappingProvider messagePayloadMappingProvider;

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#getMessageName(DelegateExecution)}
   */
  @Test
  public void testGetMessageName() {
    // Arrange
    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getMessageRef()).thenReturn("Message Ref");

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    DefaultMessageExecutionContext defaultMessageExecutionContext = new DefaultMessageExecutionContext(
        messageEventDefinition, expressionManager, mock(MessagePayloadMappingProvider.class));

    // Act
    String actualMessageName = defaultMessageExecutionContext
        .getMessageName(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(messageEventDefinition).getMessageRef();
    verify(expressionManager).createExpression(eq("Message Ref"));
    verify(expressionManager).setCustomFunctionProviders(isA(List.class));
    assertEquals("null", actualMessageName);
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#getMessageName(DelegateExecution)}
   */
  @Test
  public void testGetMessageName2() {
    // Arrange
    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getMessageRef()).thenReturn("Message Ref");

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(null);
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    DefaultMessageExecutionContext defaultMessageExecutionContext = new DefaultMessageExecutionContext(
        messageEventDefinition, expressionManager, mock(MessagePayloadMappingProvider.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultMessageExecutionContext
        .getMessageName(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(messageEventDefinition).getMessageRef();
    verify(expressionManager).createExpression(eq("Message Ref"));
    verify(expressionManager).setCustomFunctionProviders(isA(List.class));
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#getCorrelationKey(DelegateExecution)}
   */
  @Test
  public void testGetCorrelationKey() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    DefaultMessageExecutionContext defaultMessageExecutionContext = new DefaultMessageExecutionContext(
        messageEventDefinition, new ExpressionManager(), mock(MessagePayloadMappingProvider.class));

    // Act and Assert
    assertFalse(
        defaultMessageExecutionContext.getCorrelationKey(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .isPresent());
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#getMessagePayload(DelegateExecution)}
   */
  @Test
  public void testGetMessagePayload() {
    // Arrange
    MessagePayloadMappingProvider messagePayloadMappingProvider = mock(MessagePayloadMappingProvider.class);
    Optional<Map<String, Object>> ofResult = Optional.of(new HashMap<>());
    when(messagePayloadMappingProvider.getMessagePayload(Mockito.<DelegateExecution>any())).thenReturn(ofResult);
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    DefaultMessageExecutionContext defaultMessageExecutionContext = new DefaultMessageExecutionContext(
        messageEventDefinition, new ExpressionManager(), messagePayloadMappingProvider);

    // Act
    Optional<Map<String, Object>> actualMessagePayload = defaultMessageExecutionContext
        .getMessagePayload(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(messagePayloadMappingProvider).getMessagePayload(isA(DelegateExecution.class));
    assertSame(ofResult, actualMessagePayload);
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#getMessagePayload(DelegateExecution)}
   */
  @Test
  public void testGetMessagePayload2() {
    // Arrange
    MessagePayloadMappingProvider messagePayloadMappingProvider = mock(MessagePayloadMappingProvider.class);
    when(messagePayloadMappingProvider.getMessagePayload(Mockito.<DelegateExecution>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    DefaultMessageExecutionContext defaultMessageExecutionContext = new DefaultMessageExecutionContext(
        messageEventDefinition, new ExpressionManager(), messagePayloadMappingProvider);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultMessageExecutionContext
        .getMessagePayload(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(messagePayloadMappingProvider).getMessagePayload(isA(DelegateExecution.class));
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#createThrowMessage(DelegateExecution)}
   */
  @Test
  public void testCreateThrowMessage() {
    // Arrange
    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getMessageRef()).thenReturn("Message Ref");

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(null);
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    DefaultMessageExecutionContext defaultMessageExecutionContext = new DefaultMessageExecutionContext(
        messageEventDefinition, expressionManager, mock(MessagePayloadMappingProvider.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultMessageExecutionContext
        .createThrowMessage(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(messageEventDefinition).getMessageRef();
    verify(expressionManager).createExpression(eq("Message Ref"));
    verify(expressionManager).setCustomFunctionProviders(isA(List.class));
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#createThrowMessage(DelegateExecution)}
   */
  @Test
  public void testCreateThrowMessage2() {
    // Arrange
    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageEventDefinition.getMessageRef()).thenReturn("Message Ref");

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(mock(CustomFunctionProvider.class));
    ExpressionManager expressionManager = mock(ExpressionManager.class);
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));
    doNothing().when(expressionManager).setCustomFunctionProviders(Mockito.<List<CustomFunctionProvider>>any());
    expressionManager.setCustomFunctionProviders(customFunctionProviders);
    MessagePayloadMappingProvider messagePayloadMappingProvider = mock(MessagePayloadMappingProvider.class);
    Optional<Map<String, Object>> ofResult = Optional.of(new HashMap<>());
    when(messagePayloadMappingProvider.getMessagePayload(Mockito.<DelegateExecution>any())).thenReturn(ofResult);
    DefaultMessageExecutionContext defaultMessageExecutionContext = new DefaultMessageExecutionContext(
        messageEventDefinition, expressionManager, messagePayloadMappingProvider);
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getProcessInstanceBusinessKey()).thenReturn("Process Instance Business Key");

    // Act
    ThrowMessage actualCreateThrowMessageResult = defaultMessageExecutionContext.createThrowMessage(execution);

    // Assert
    verify(messageEventDefinition, atLeast(1)).getCorrelationKey();
    verify(messageEventDefinition).getMessageRef();
    verify(execution).getProcessInstanceBusinessKey();
    verify(messagePayloadMappingProvider).getMessagePayload(isA(DelegateExecution.class));
    verify(expressionManager, atLeast(1)).createExpression(Mockito.<String>any());
    verify(expressionManager).setCustomFunctionProviders(isA(List.class));
    Optional<String> businessKey = actualCreateThrowMessageResult.getBusinessKey();
    assertEquals("Process Instance Business Key", businessKey.get());
    Optional<String> correlationKey = actualCreateThrowMessageResult.getCorrelationKey();
    assertEquals("null", correlationKey.get());
    assertEquals("null", actualCreateThrowMessageResult.getName());
    assertTrue(businessKey.isPresent());
    assertTrue(correlationKey.isPresent());
    assertSame(ofResult, actualCreateThrowMessageResult.getPayload());
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#evaluateExpression(String, DelegateExecution)}
   */
  @Test
  public void testEvaluateExpression() {
    // Arrange
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue(JSONObject.NULL));

    // Act
    String actualEvaluateExpressionResult = defaultMessageExecutionContext.evaluateExpression("Expression",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expressionManager).createExpression(eq("Expression"));
    assertEquals("null", actualEvaluateExpressionResult);
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#evaluateExpression(String, DelegateExecution)}
   */
  @Test
  public void testEvaluateExpression2() {
    // Arrange
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultMessageExecutionContext
        .evaluateExpression("Expression", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(expressionManager).createExpression(eq("Expression"));
  }

  /**
   * Method under test:
   * {@link DefaultMessageExecutionContext#evaluateExpression(String, DelegateExecution)}
   */
  @Test
  public void testEvaluateExpression3() {
    // Arrange
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> defaultMessageExecutionContext
        .evaluateExpression("Expression", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(expressionManager).createExpression(eq("Expression"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link DefaultMessageExecutionContext#DefaultMessageExecutionContext(MessageEventDefinition, ExpressionManager, MessagePayloadMappingProvider)}
   *   <li>{@link DefaultMessageExecutionContext#getExpressionManager()}
   *   <li>{@link DefaultMessageExecutionContext#getMessagePayloadMappingProvider()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ExpressionManager expressionManager = new ExpressionManager();
    MessagePayloadMappingProvider messagePayloadMappingProvider = mock(MessagePayloadMappingProvider.class);

    // Act
    DefaultMessageExecutionContext actualDefaultMessageExecutionContext = new DefaultMessageExecutionContext(
        messageEventDefinition, expressionManager, messagePayloadMappingProvider);
    ExpressionManager actualExpressionManager = actualDefaultMessageExecutionContext.getExpressionManager();

    // Assert
    assertSame(expressionManager, actualExpressionManager);
    assertSame(messagePayloadMappingProvider, actualDefaultMessageExecutionContext.getMessagePayloadMappingProvider());
  }
}
