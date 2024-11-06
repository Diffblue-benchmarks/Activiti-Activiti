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
package org.activiti.spring.conformance.util.security;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.api.runtime.shared.security.PrincipalGroupsProvider;
import org.activiti.api.runtime.shared.security.PrincipalRolesProvider;
import org.activiti.api.runtime.shared.security.SecurityContextPrincipalProvider;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.activiti.core.common.spring.security.AuthenticationPrincipalIdentityProvider;
import org.activiti.core.common.spring.security.LocalSpringSecurityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class SecurityUtilDiffblueTest {
  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String); then calls getAuthenticatedUserId()")
  void testLogInAs_thenCallsGetAuthenticatedUserId() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    (new SecurityUtil(userDetailsService, securityManager)).logInAs("42");

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(userDetailsService).loadUserByUsername(eq("42"));
  }

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String); then throw IllegalStateException")
  void testLogInAs_thenThrowIllegalStateException() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ExtendedInMemoryUserDetailsManager userDetailsService = mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(null);
    SecurityContextPrincipalProvider securityContextPrincipalProvider = mock(SecurityContextPrincipalProvider.class);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> (new SecurityUtil(userDetailsService,
            new LocalSpringSecurityManager(securityContextPrincipalProvider,
                new AuthenticationPrincipalIdentityProvider(), mock(PrincipalGroupsProvider.class),
                mock(PrincipalRolesProvider.class))))
            .logInAs("janedoe"));
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
  }
}
