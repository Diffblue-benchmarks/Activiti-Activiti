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
package org.activiti.engine.impl.bpmn.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ScriptExecutionListenerDiffblueTest {
  /**
   * Test {@link ScriptExecutionListener#notify(DelegateExecution)} with {@code DelegateExecution}.
   *
   * <ul>
   *   <li>Given {@link ScriptExecutionListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ScriptExecutionListener#notify(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptExecutionListener.notify(DelegateExecution)"})
  public void testNotifyWithDelegateExecution_givenScriptExecutionListener() {
    // Arrange
    ScriptExecutionListener scriptExecutionListener = new ScriptExecutionListener();

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            scriptExecutionListener.notify(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ScriptExecutionListener#notify(DelegateExecution)} with {@code DelegateExecution}.
   *
   * <ul>
   *   <li>Given {@link ScriptExecutionListener} (default constructor) Language is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptExecutionListener#notify(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptExecutionListener.notify(DelegateExecution)"})
  public void testNotifyWithDelegateExecution_givenScriptExecutionListenerLanguageIsNull() {
    // Arrange
    ScriptExecutionListener scriptExecutionListener = new ScriptExecutionListener();
    scriptExecutionListener.setResultVariable(null);
    scriptExecutionListener.setScript(new FixedValue(JSONObject.NULL));
    scriptExecutionListener.setLanguage(null);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            scriptExecutionListener.notify(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ScriptExecutionListener#validateParameters()}.
   *
   * <ul>
   *   <li>Given {@link ScriptExecutionListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ScriptExecutionListener#validateParameters()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptExecutionListener.validateParameters()"})
  public void testValidateParameters_givenScriptExecutionListener() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new ScriptExecutionListener().validateParameters());
  }

  /**
   * Test {@link ScriptExecutionListener#validateParameters()}.
   *
   * <ul>
   *   <li>Given {@link ScriptExecutionListener} (default constructor) Language is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptExecutionListener#validateParameters()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptExecutionListener.validateParameters()"})
  public void testValidateParameters_givenScriptExecutionListenerLanguageIsNull() {
    // Arrange
    ScriptExecutionListener scriptExecutionListener = new ScriptExecutionListener();
    scriptExecutionListener.setResultVariable(new FixedValue(JSONObject.NULL));
    scriptExecutionListener.setScript(new FixedValue(JSONObject.NULL));
    scriptExecutionListener.setLanguage(null);

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> scriptExecutionListener.validateParameters());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ScriptExecutionListener}
   *   <li>{@link ScriptExecutionListener#setLanguage(Expression)}
   *   <li>{@link ScriptExecutionListener#setResultVariable(Expression)}
   *   <li>{@link ScriptExecutionListener#setScript(Expression)}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ScriptExecutionListener.<init>()",
    "void ScriptExecutionListener.setLanguage(Expression)",
    "void ScriptExecutionListener.setResultVariable(Expression)",
    "void ScriptExecutionListener.setScript(Expression)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ScriptExecutionListener actualScriptExecutionListener = new ScriptExecutionListener();
    actualScriptExecutionListener.setLanguage(new FixedValue(JSONObject.NULL));
    actualScriptExecutionListener.setResultVariable(new FixedValue(JSONObject.NULL));
    actualScriptExecutionListener.setScript(new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualScriptExecutionListener.language;
    assertTrue(expression instanceof FixedValue);
    Expression expression2 = actualScriptExecutionListener.resultVariable;
    assertTrue(expression2 instanceof FixedValue);
    Expression expression3 = actualScriptExecutionListener.script;
    assertTrue(expression3 instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals("null", expression2.getExpressionText());
    assertEquals("null", expression3.getExpressionText());
  }
}
