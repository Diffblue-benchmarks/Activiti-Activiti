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
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class ScriptExecutionListenerDiffblueTest {
  /**
   * Test {@link ScriptExecutionListener#notify(DelegateExecution)} with
   * {@code DelegateExecution}.
   * <ul>
   *   <li>Given {@link ScriptExecutionListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptExecutionListener#notify(DelegateExecution)}
   */
  @Test
  public void testNotifyWithDelegateExecution_givenScriptExecutionListener() {
    // Arrange
    ScriptExecutionListener scriptExecutionListener = new ScriptExecutionListener();

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> scriptExecutionListener.notify(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ScriptExecutionListener#notify(DelegateExecution)} with
   * {@code DelegateExecution}.
   * <ul>
   *   <li>Given {@link ScriptExecutionListener} (default constructor) Language is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptExecutionListener#notify(DelegateExecution)}
   */
  @Test
  public void testNotifyWithDelegateExecution_givenScriptExecutionListenerLanguageIsNull() {
    // Arrange
    ScriptExecutionListener scriptExecutionListener = new ScriptExecutionListener();
    scriptExecutionListener.setResultVariable(null);
    scriptExecutionListener.setLanguage(null);
    scriptExecutionListener.setScript(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> scriptExecutionListener.notify(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ScriptExecutionListener#validateParameters()}.
   * <ul>
   *   <li>Given {@link ScriptExecutionListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptExecutionListener#validateParameters()}
   */
  @Test
  public void testValidateParameters_givenScriptExecutionListener() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> (new ScriptExecutionListener()).validateParameters());
  }

  /**
   * Test {@link ScriptExecutionListener#validateParameters()}.
   * <ul>
   *   <li>Given {@link ScriptExecutionListener} (default constructor) Language is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptExecutionListener#validateParameters()}
   */
  @Test
  public void testValidateParameters_givenScriptExecutionListenerLanguageIsNull() {
    // Arrange
    ScriptExecutionListener scriptExecutionListener = new ScriptExecutionListener();
    scriptExecutionListener.setResultVariable(new FixedValue(JSONObject.NULL));
    scriptExecutionListener.setScript(new FixedValue(JSONObject.NULL));
    scriptExecutionListener.setLanguage(null);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> scriptExecutionListener.validateParameters());
  }

  /**
   * Test {@link ScriptExecutionListener#validateParameters()}.
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptExecutionListener#validateParameters()}
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
            "The field 'language' should be set on the ExecutionListener", type),
        "The field 'language' should be set on the ExecutionListener");

    ScriptExecutionListener scriptExecutionListener = new ScriptExecutionListener();
    scriptExecutionListener.setResultVariable(resultVariable);
    scriptExecutionListener.setScript(new FixedValue(JSONObject.NULL));
    scriptExecutionListener.setLanguage(new FixedValue(JSONObject.NULL));

    // Act
    scriptExecutionListener.validateParameters();

    // Assert that nothing has changed
    verify(builder).build(eq("The field 'language' should be set on the ExecutionListener"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ScriptExecutionListener}
   *   <li>{@link ScriptExecutionListener#setLanguage(Expression)}
   *   <li>{@link ScriptExecutionListener#setResultVariable(Expression)}
   *   <li>{@link ScriptExecutionListener#setScript(Expression)}
   * </ul>
   */
  @Test
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
