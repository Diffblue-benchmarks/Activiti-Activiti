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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.activiti.core.common.spring.security.LocalSpringSecurityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class SecurityUtilDiffblueTest {
  @Mock private SecurityManager securityManager;

  @InjectMocks private SecurityUtil securityUtil;

  @Mock private UserDetailsService userDetailsService;

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   *
   * <ul>
   *   <li>Given {@link ExtendedInMemoryUserDetailsManager} {@link
   *       ExtendedInMemoryUserDetailsManager#loadUserByUsername(String)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName(
      "Test logInAs(String); given ExtendedInMemoryUserDetailsManager loadUserByUsername(String) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecurityUtil.logInAs(String)"})
  void testLogInAs_givenExtendedInMemoryUserDetailsManagerLoadUserByUsernameReturnNull()
      throws UsernameNotFoundException {
    // Arrange
    ExtendedInMemoryUserDetailsManager userDetailsService =
        mock(ExtendedInMemoryUserDetailsManager.class);
    when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(null);
    SecurityUtil securityUtil =
        new SecurityUtil(userDetailsService, mock(LocalSpringSecurityManager.class));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> securityUtil.logInAs("janedoe"));
    verify(userDetailsService).loadUserByUsername("janedoe");
  }

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   *
   * <ul>
   *   <li>Given {@link UserDetailsService} {@link UserDetailsService#loadUserByUsername(String)}
   *       throw {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName(
      "Test logInAs(String); given UserDetailsService loadUserByUsername(String) throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecurityUtil.logInAs(String)"})
  void testLogInAs_givenUserDetailsServiceLoadUserByUsernameThrowIllegalStateException()
      throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> securityUtil.logInAs("janedoe"));
    verify(userDetailsService).loadUserByUsername("janedoe");
  }

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   *
   * <ul>
   *   <li>Then calls {@link LocalSpringSecurityManager#getAuthenticatedUserId()}.
   * </ul>
   *
   * <p>Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String); then calls getAuthenticatedUserId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecurityUtil.logInAs(String)"})
  void testLogInAs_thenCallsGetAuthenticatedUserId() throws UsernameNotFoundException {
    // Arrange
    ExtendedInMemoryUserDetailsManager userDetailsService =
        mock(ExtendedInMemoryUserDetailsManager.class);
    User user = new User("janedoe", "iloveyou", new ArrayList<>());
    when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(user);

    LocalSpringSecurityManager securityManager = mock(LocalSpringSecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    SecurityUtil securityUtil = new SecurityUtil(userDetailsService, securityManager);

    // Act
    securityUtil.logInAs("42");

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(userDetailsService).loadUserByUsername("42");
  }

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserId()}.
   * </ul>
   *
   * <p>Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String); then calls getAuthenticatedUserId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SecurityUtil.logInAs(String)"})
  void testLogInAs_thenCallsGetAuthenticatedUserId2() throws UsernameNotFoundException {
    // Arrange
    User user = new User("janedoe", "iloveyou", new ArrayList<>());
    when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(user);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> securityUtil.logInAs("janedoe"));
    verify(securityManager).getAuthenticatedUserId();
    verify(userDetailsService).loadUserByUsername("janedoe");
  }
}
