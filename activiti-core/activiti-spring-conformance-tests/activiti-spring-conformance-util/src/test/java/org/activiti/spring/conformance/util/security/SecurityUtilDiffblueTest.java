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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.api.runtime.shared.security.SecurityManager;
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
  @Mock
  private SecurityManager securityManager;

  @InjectMocks
  private SecurityUtil securityUtil;

  @Mock
  private UserDetailsService userDetailsService;

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   * <p>
   * Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SecurityUtil.logInAs(String)"})
  void testLogInAs() throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> securityUtil.logInAs("janedoe"));
    verify(securityManager).getAuthenticatedUserId();
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
  }

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String); given SecurityManager getAuthenticatedUserId() return '42'; when '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SecurityUtil.logInAs(String)"})
  void testLogInAs_givenSecurityManagerGetAuthenticatedUserIdReturn42_when42() throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    securityUtil.logInAs("42");

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(userDetailsService).loadUserByUsername(eq("42"));
  }

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   * <ul>
   *   <li>Given {@link UserDetailsService} {@link UserDetailsService#loadUserByUsername(String)} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String); given UserDetailsService loadUserByUsername(String) return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SecurityUtil.logInAs(String)"})
  void testLogInAs_givenUserDetailsServiceLoadUserByUsernameReturnNull() throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> securityUtil.logInAs("janedoe"));
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
  }
}
