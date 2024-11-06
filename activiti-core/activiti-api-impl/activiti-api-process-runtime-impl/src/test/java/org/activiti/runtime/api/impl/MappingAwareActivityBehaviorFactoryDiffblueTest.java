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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContextFactory;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.spring.process.ProcessVariablesInitiator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MappingAwareActivityBehaviorFactory.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MappingAwareActivityBehaviorFactoryDiffblueTest {
  @Autowired
  private MappingAwareActivityBehaviorFactory mappingAwareActivityBehaviorFactory;

  @MockBean
  private ProcessVariablesInitiator processVariablesInitiator;

  @MockBean
  private VariablesCalculator variablesCalculator;

  @MockBean
  private VariablesPropagator variablesPropagator;

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#MappingAwareActivityBehaviorFactory(VariablesCalculator, ProcessVariablesInitiator, VariablesPropagator)}.
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#MappingAwareActivityBehaviorFactory(VariablesCalculator, ProcessVariablesInitiator, VariablesPropagator)}
   */
  @Test
  @DisplayName("Test new MappingAwareActivityBehaviorFactory(VariablesCalculator, ProcessVariablesInitiator, VariablesPropagator)")
  void testNewMappingAwareActivityBehaviorFactory() {
    // Arrange and Act
    MappingAwareActivityBehaviorFactory actualMappingAwareActivityBehaviorFactory = new MappingAwareActivityBehaviorFactory(
        variablesCalculator, processVariablesInitiator, variablesPropagator);

    // Assert
    assertTrue(actualMappingAwareActivityBehaviorFactory
        .getMessageExecutionContextFactory() instanceof DefaultMessageExecutionContextFactory);
    assertTrue(actualMappingAwareActivityBehaviorFactory
        .getMessagePayloadMappingProviderFactory() instanceof JsonMessagePayloadMappingProviderFactory);
    assertNull(actualMappingAwareActivityBehaviorFactory.getExpressionManager());
  }

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   * with {@code calledElement}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  @DisplayName("Test createCallActivityBehavior(String, List) with 'calledElement', 'mapExceptions'")
  void testCreateCallActivityBehaviorWithCalledElementMapExceptions() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = mappingAwareActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", mapExceptions);

    // Assert
    assertTrue(actualCreateCallActivityBehaviorResult instanceof MappingAwareCallActivityBehavior);
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   * with {@code calledElement}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  @DisplayName("Test createCallActivityBehavior(String, List) with 'calledElement', 'mapExceptions'")
  void testCreateCallActivityBehaviorWithCalledElementMapExceptions2() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = mappingAwareActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", mapExceptions);

    // Assert
    assertTrue(actualCreateCallActivityBehaviorResult instanceof MappingAwareCallActivityBehavior);
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   * with {@code calledElement}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  @DisplayName("Test createCallActivityBehavior(String, List) with 'calledElement', 'mapExceptions'")
  void testCreateCallActivityBehaviorWithCalledElementMapExceptions3() {
    // Arrange
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(mock(MapExceptionEntry.class));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = mappingAwareActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", mapExceptions);

    // Assert
    assertTrue(actualCreateCallActivityBehaviorResult instanceof MappingAwareCallActivityBehavior);
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   * with {@code calledElement}, {@code mapExceptions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(String, List)}
   */
  @Test
  @DisplayName("Test createCallActivityBehavior(String, List) with 'calledElement', 'mapExceptions'; when ArrayList()")
  void testCreateCallActivityBehaviorWithCalledElementMapExceptions_whenArrayList() {
    // Arrange and Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = mappingAwareActivityBehaviorFactory
        .createCallActivityBehavior("Called Element", new ArrayList<>());

    // Assert
    assertTrue(actualCreateCallActivityBehaviorResult instanceof MappingAwareCallActivityBehavior);
    assertEquals("Called Element", actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   * with {@code expression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  @DisplayName("Test createCallActivityBehavior(Expression, List) with 'expression', 'mapExceptions'")
  void testCreateCallActivityBehaviorWithExpressionMapExceptions() {
    // Arrange
    FixedValue expression = new FixedValue("Value");

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = mappingAwareActivityBehaviorFactory
        .createCallActivityBehavior(expression, mapExceptions);

    // Assert
    assertTrue(actualCreateCallActivityBehaviorResult instanceof MappingAwareCallActivityBehavior);
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   * with {@code expression}, {@code mapExceptions}.
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  @DisplayName("Test createCallActivityBehavior(Expression, List) with 'expression', 'mapExceptions'")
  void testCreateCallActivityBehaviorWithExpressionMapExceptions2() {
    // Arrange
    FixedValue expression = new FixedValue("Value");

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = mappingAwareActivityBehaviorFactory
        .createCallActivityBehavior(expression, mapExceptions);

    // Assert
    assertTrue(actualCreateCallActivityBehaviorResult instanceof MappingAwareCallActivityBehavior);
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   * with {@code expression}, {@code mapExceptions}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  @DisplayName("Test createCallActivityBehavior(Expression, List) with 'expression', 'mapExceptions'; when ArrayList()")
  void testCreateCallActivityBehaviorWithExpressionMapExceptions_whenArrayList() {
    // Arrange
    FixedValue expression = new FixedValue("Value");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = mappingAwareActivityBehaviorFactory
        .createCallActivityBehavior(expression, new ArrayList<>());

    // Assert
    assertTrue(actualCreateCallActivityBehaviorResult instanceof MappingAwareCallActivityBehavior);
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }

  /**
   * Test
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   * with {@code expression}, {@code mapExceptions}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MappingAwareActivityBehaviorFactory#createCallActivityBehavior(Expression, List)}
   */
  @Test
  @DisplayName("Test createCallActivityBehavior(Expression, List) with 'expression', 'mapExceptions'; when 'java.lang.Object'")
  void testCreateCallActivityBehaviorWithExpressionMapExceptions_whenJavaLangObject() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression expression = new JuelExpression(new ObjectValueExpression(converter, "Object", type),
        "Expression Text");

    // Act
    CallActivityBehavior actualCreateCallActivityBehaviorResult = mappingAwareActivityBehaviorFactory
        .createCallActivityBehavior(expression, new ArrayList<>());

    // Assert
    assertTrue(actualCreateCallActivityBehaviorResult instanceof MappingAwareCallActivityBehavior);
    assertNull(actualCreateCallActivityBehaviorResult.getProcessDefinitionKey());
    assertNull(actualCreateCallActivityBehaviorResult.getMultiInstanceActivityBehavior());
  }
}
