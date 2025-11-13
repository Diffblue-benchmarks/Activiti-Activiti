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
package org.activiti.core.common.spring.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LocalSpringSecurityContextPrincipalProvider.class})
@ExtendWith(SpringExtension.class)
class LocalSpringSecurityContextPrincipalProviderDiffblueTest {
  @Autowired
  private LocalSpringSecurityContextPrincipalProvider localSpringSecurityContextPrincipalProvider;

  /**
   * Test {@link LocalSpringSecurityContextPrincipalProvider#getCurrentPrincipal()}.
   *
   * <p>Method under test: {@link LocalSpringSecurityContextPrincipalProvider#getCurrentPrincipal()}
   */
  @Test
  @DisplayName("Test getCurrentPrincipal()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional LocalSpringSecurityContextPrincipalProvider.getCurrentPrincipal()"
  })
  void testGetCurrentPrincipal() {
    // Arrange, Act and Assert
    assertFalse(localSpringSecurityContextPrincipalProvider.getCurrentPrincipal().isPresent());
  }

  /**
   * Test new {@link LocalSpringSecurityContextPrincipalProvider} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * LocalSpringSecurityContextPrincipalProvider}
   */
  @Test
  @DisplayName("Test new LocalSpringSecurityContextPrincipalProvider (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LocalSpringSecurityContextPrincipalProvider.<init>()"})
  void testNewLocalSpringSecurityContextPrincipalProvider() {
    // Arrange, Act and Assert
    assertFalse(
        new LocalSpringSecurityContextPrincipalProvider().getCurrentPrincipal().isPresent());
  }
}
