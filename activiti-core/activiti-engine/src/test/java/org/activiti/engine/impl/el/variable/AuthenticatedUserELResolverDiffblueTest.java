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
package org.activiti.engine.impl.el.variable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AuthenticatedUserELResolverDiffblueTest {
  /**
   * Test {@link AuthenticatedUserELResolver#canResolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>When {@code authenticatedUserId}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AuthenticatedUserELResolver#canResolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AuthenticatedUserELResolver.canResolve(String, VariableScope)"})
  public void testCanResolve_whenAuthenticatedUserId_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        new AuthenticatedUserELResolver()
            .canResolve("authenticatedUserId", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link AuthenticatedUserELResolver#canResolve(String, VariableScope)}.
   *
   * <ul>
   *   <li>When {@code Property}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AuthenticatedUserELResolver#canResolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AuthenticatedUserELResolver.canResolve(String, VariableScope)"})
  public void testCanResolve_whenProperty_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        new AuthenticatedUserELResolver()
            .canResolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link AuthenticatedUserELResolver#resolve(String, VariableScope)}.
   *
   * <p>Method under test: {@link AuthenticatedUserELResolver#resolve(String, VariableScope)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object AuthenticatedUserELResolver.resolve(String, VariableScope)"})
  public void testResolve() {
    // Arrange, Act and Assert
    assertNull(
        new AuthenticatedUserELResolver()
            .resolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }
}
