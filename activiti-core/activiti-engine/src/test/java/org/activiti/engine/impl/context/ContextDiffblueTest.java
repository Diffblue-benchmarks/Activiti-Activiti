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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import org.activiti.engine.impl.context.Context.ResourceBundleControl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ContextDiffblueTest {
  /**
   * Test {@link Context#getCommandContext()}.
   *
   * <p>Method under test: {@link Context#getCommandContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.interceptor.CommandContext Context.getCommandContext()"
  })
  public void testGetCommandContext() {
    // Arrange, Act and Assert
    assertNull(Context.getCommandContext());
  }

  /**
   * Test {@link Context#getProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link Context#getProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl Context.getProcessEngineConfiguration()"
  })
  public void testGetProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull(Context.getProcessEngineConfiguration());
  }

  /**
   * Test {@link Context#getTransactionContext()}.
   *
   * <p>Method under test: {@link Context#getTransactionContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.cfg.TransactionContext Context.getTransactionContext()"
  })
  public void testGetTransactionContext() {
    // Arrange, Act and Assert
    assertNull(Context.getTransactionContext());
  }

  /**
   * Test {@link Context#getStack(ThreadLocal)}.
   *
   * <ul>
   *   <li>When {@link ThreadLocal} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link Context#getStack(ThreadLocal)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Stack Context.getStack(ThreadLocal)"})
  public void testGetStack_whenThreadLocal_thenReturnEmpty() {
    // Arrange and Act
    Stack<Object> actualStack = Context.getStack(new ThreadLocal<>());

    // Assert
    assertTrue(actualStack.isEmpty());
  }

  /**
   * Test {@link Context#getBpmnOverrideContext()}.
   *
   * <p>Method under test: {@link Context#getBpmnOverrideContext()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Map Context.getBpmnOverrideContext()"})
  public void testGetBpmnOverrideContext() {
    // Arrange and Act
    Map<String, ObjectNode> actualBpmnOverrideContext = Context.getBpmnOverrideContext();

    // Assert
    assertTrue(actualBpmnOverrideContext.isEmpty());
  }

  /**
   * Test {@link Context#getProcessDefinitionHelper()}.
   *
   * <p>Method under test: {@link Context#getProcessDefinitionHelper()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.ProcessDefinitionHelper Context.getProcessDefinitionHelper()"
  })
  public void testGetProcessDefinitionHelper() {
    // Arrange, Act and Assert
    assertNull(Context.getProcessDefinitionHelper());
  }

  /**
   * Test ResourceBundleControl {@link ResourceBundleControl#getCandidateLocales(String, Locale)}.
   *
   * <ul>
   *   <li>When Default.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link ResourceBundleControl#getCandidateLocales(String, Locale)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ResourceBundleControl.getCandidateLocales(String, Locale)"})
  public void testResourceBundleControlGetCandidateLocales_whenDefault_thenReturnSizeIsTwo() {
    // Arrange and Act
    List<Locale> actualCandidateLocales =
        new ResourceBundleControl().getCandidateLocales("en", Locale.getDefault());

    // Assert
    assertEquals(2, actualCandidateLocales.size());
    assertSame(Locale.ENGLISH, actualCandidateLocales.get(0));
    assertSame(Locale.ROOT, actualCandidateLocales.get(1));
  }
}
