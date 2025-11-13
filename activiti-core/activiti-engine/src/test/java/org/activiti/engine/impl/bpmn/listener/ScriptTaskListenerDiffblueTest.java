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
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ScriptTaskListenerDiffblueTest {
  /**
   * Test {@link ScriptTaskListener#validateParameters()}.
   *
   * <ul>
   *   <li>Given {@link ScriptTaskListener} (default constructor) Language is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTaskListener#validateParameters()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptTaskListener.validateParameters()"})
  public void testValidateParameters_givenScriptTaskListenerLanguageIsNull() {
    // Arrange
    ScriptTaskListener scriptTaskListener = new ScriptTaskListener();
    scriptTaskListener.setResultVariable(new FixedValue(JSONObject.NULL));
    scriptTaskListener.setScript(new FixedValue(JSONObject.NULL));
    scriptTaskListener.setLanguage(null);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> scriptTaskListener.validateParameters());
  }

  /**
   * Test {@link ScriptTaskListener#validateParameters()}.
   *
   * <ul>
   *   <li>Given {@link ScriptTaskListener} (default constructor).
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTaskListener#validateParameters()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptTaskListener.validateParameters()"})
  public void testValidateParameters_givenScriptTaskListener_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new ScriptTaskListener().validateParameters());
  }

  /**
   * Test {@link ScriptTaskListener#validateParameters()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTaskListener#validateParameters()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptTaskListener.validateParameters()"})
  public void testValidateParameters_thenDoesNotThrow() {
    // Arrange
    ScriptTaskListener scriptTaskListener = new ScriptTaskListener();
    scriptTaskListener.setResultVariable(new FixedValue(JSONObject.NULL));
    scriptTaskListener.setScript(new FixedValue(JSONObject.NULL));
    scriptTaskListener.setLanguage(new FixedValue(JSONObject.NULL));

    // Act and Assert
    scriptTaskListener.validateParameters();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ScriptTaskListener}
   *   <li>{@link ScriptTaskListener#setLanguage(Expression)}
   *   <li>{@link ScriptTaskListener#setResultVariable(Expression)}
   *   <li>{@link ScriptTaskListener#setScript(Expression)}
   *   <li>{@link ScriptTaskListener#setAutoStoreVariables(boolean)}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ScriptTaskListener.<init>()",
    "void ScriptTaskListener.setAutoStoreVariables(boolean)",
    "void ScriptTaskListener.setLanguage(Expression)",
    "void ScriptTaskListener.setResultVariable(Expression)",
    "void ScriptTaskListener.setScript(Expression)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ScriptTaskListener actualScriptTaskListener = new ScriptTaskListener();
    actualScriptTaskListener.setLanguage(new FixedValue(JSONObject.NULL));
    actualScriptTaskListener.setResultVariable(new FixedValue(JSONObject.NULL));
    actualScriptTaskListener.setScript(new FixedValue(JSONObject.NULL));
    actualScriptTaskListener.setAutoStoreVariables(true);

    // Assert
    Expression expression = actualScriptTaskListener.language;
    assertTrue(expression instanceof FixedValue);
    Expression expression2 = actualScriptTaskListener.resultVariable;
    assertTrue(expression2 instanceof FixedValue);
    Expression expression3 = actualScriptTaskListener.script;
    assertTrue(expression3 instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals("null", expression2.getExpressionText());
    assertEquals("null", expression3.getExpressionText());
    assertTrue(actualScriptTaskListener.autoStoreVariables);
  }
}
