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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.core.el.ActivitiFunctionMapper;
import org.activiti.core.el.ActivitiVariablesMapper;
import org.activiti.core.el.juel.TreeValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilder;
import org.activiti.core.el.juel.tree.TreeBuilderException;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class ScriptTaskListenerDiffblueTest {
  /**
   * Test {@link ScriptTaskListener#validateParameters()}.
   * <ul>
   *   <li>Given {@link ScriptTaskListener} (default constructor) Language is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTaskListener#validateParameters()}
   */
  @Test
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
   * <ul>
   *   <li>Given {@link ScriptTaskListener} (default constructor).</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTaskListener#validateParameters()}
   */
  @Test
  public void testValidateParameters_givenScriptTaskListener_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new ScriptTaskListener()).validateParameters());
  }

  /**
   * Test {@link ScriptTaskListener#validateParameters()}.
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTaskListener#validateParameters()}
   */
  @Test
  public void testValidateParameters_thenCallsBuild() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    ActivitiFunctionMapper functions2 = new ActivitiFunctionMapper();
    ActivitiVariablesMapper variables = new ActivitiVariablesMapper();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression resultVariable = new JuelExpression(
        new TreeValueExpression(store, functions2, variables, converter,
            "The field 'language' should be set on the TaskListener", type),
        "The field 'language' should be set on the TaskListener");

    ScriptTaskListener scriptTaskListener = new ScriptTaskListener();
    scriptTaskListener.setResultVariable(resultVariable);
    scriptTaskListener.setScript(new FixedValue(JSONObject.NULL));
    scriptTaskListener.setLanguage(new FixedValue(JSONObject.NULL));

    // Act
    scriptTaskListener.validateParameters();

    // Assert that nothing has changed
    verify(builder).build(eq("The field 'language' should be set on the TaskListener"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ScriptTaskListener}
   *   <li>{@link ScriptTaskListener#setLanguage(Expression)}
   *   <li>{@link ScriptTaskListener#setResultVariable(Expression)}
   *   <li>{@link ScriptTaskListener#setScript(Expression)}
   *   <li>{@link ScriptTaskListener#setAutoStoreVariables(boolean)}
   * </ul>
   */
  @Test
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
