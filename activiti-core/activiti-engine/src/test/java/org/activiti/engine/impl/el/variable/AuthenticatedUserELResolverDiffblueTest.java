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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AuthenticatedUserELResolverDiffblueTest {
  /**
   * Test {@link AuthenticatedUserELResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>When {@code authenticatedUserId}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AuthenticatedUserELResolver#canResolve(String, VariableScope)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AuthenticatedUserELResolver.canResolve(String, VariableScope)"})
  public void testCanResolve_whenAuthenticatedUserId_thenReturnTrue() {
    // Arrange
    AuthenticatedUserELResolver authenticatedUserELResolver = new AuthenticatedUserELResolver();

    // Act and Assert
    assertTrue(
        authenticatedUserELResolver.canResolve("authenticatedUserId", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link AuthenticatedUserELResolver#canResolve(String, VariableScope)}.
   * <ul>
   *   <li>When {@code Property}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AuthenticatedUserELResolver#canResolve(String, VariableScope)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AuthenticatedUserELResolver.canResolve(String, VariableScope)"})
  public void testCanResolve_whenProperty_thenReturnFalse() {
    // Arrange
    AuthenticatedUserELResolver authenticatedUserELResolver = new AuthenticatedUserELResolver();

    // Act and Assert
    assertFalse(authenticatedUserELResolver.canResolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }

  /**
   * Test {@link AuthenticatedUserELResolver#resolve(String, VariableScope)}.
   * <p>
   * Method under test: {@link AuthenticatedUserELResolver#resolve(String, VariableScope)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object AuthenticatedUserELResolver.resolve(String, VariableScope)"})
  public void testResolve() {
    // Arrange
    AuthenticatedUserELResolver authenticatedUserELResolver = new AuthenticatedUserELResolver();

    // Act and Assert
    assertNull(authenticatedUserELResolver.resolve("Property", NoExecutionVariableScope.getSharedInstance()));
  }
}
