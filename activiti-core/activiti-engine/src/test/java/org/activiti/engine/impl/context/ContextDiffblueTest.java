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
package org.activiti.engine.impl.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContextDiffblueTest {
  @InjectMocks
  private Context.ResourceBundleControl resourceBundleControl;

  /**
   * Method under test: {@link Context#getCommandContext()}
   */
  @Test
  public void testGetCommandContext() {
    // Arrange, Act and Assert
    assertNull(Context.getCommandContext());
  }

  /**
   * Method under test: {@link Context#getProcessEngineConfiguration()}
   */
  @Test
  public void testGetProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull(Context.getProcessEngineConfiguration());
  }

  /**
   * Method under test: {@link Context#getTransactionContext()}
   */
  @Test
  public void testGetTransactionContext() {
    // Arrange, Act and Assert
    assertNull(Context.getTransactionContext());
  }

  /**
   * Method under test: {@link Context#getStack(ThreadLocal)}
   */
  @Test
  public void testGetStack() {
    // Arrange and Act
    Stack<Object> actualStack = Context.getStack(new ThreadLocal<>());

    // Assert
    assertTrue(actualStack.isEmpty());
  }

  /**
   * Method under test: {@link Context#getBpmnOverrideContext()}
   */
  @Test
  public void testGetBpmnOverrideContext() {
    // Arrange and Act
    Map<String, ObjectNode> actualBpmnOverrideContext = Context.getBpmnOverrideContext();

    // Assert
    assertTrue(actualBpmnOverrideContext.isEmpty());
  }

  /**
   * Method under test: {@link Context#getProcessDefinitionHelper()}
   */
  @Test
  public void testGetProcessDefinitionHelper() {
    // Arrange, Act and Assert
    assertNull(Context.getProcessDefinitionHelper());
  }

  /**
   * Method under test:
   * {@link Context.ResourceBundleControl#getCandidateLocales(String, Locale)}
   */
  @Test
  public void testResourceBundleControlGetCandidateLocales() {
    // Arrange
    Locale locale = Locale.getDefault();

    // Act
    List<Locale> actualCandidateLocales = resourceBundleControl.getCandidateLocales("en", locale);

    // Assert
    assertEquals(2, actualCandidateLocales.size());
    assertSame(locale.ENGLISH, actualCandidateLocales.get(0));
    assertSame(locale.ROOT, actualCandidateLocales.get(1));
  }
}
