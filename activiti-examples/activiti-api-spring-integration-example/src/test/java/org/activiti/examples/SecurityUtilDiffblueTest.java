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
package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SecurityUtil.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SecurityUtilDiffblueTest {
  @Autowired
  private SecurityUtil securityUtil;

  @MockBean
  private UserDetailsService userDetailsService;

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   * <p>
   * Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String)")
  void testLogInAs() throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any()))
        .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));

    // Act
    securityUtil.logInAs("janedoe");

    // Assert
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
  }

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   * <p>
   * Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String)")
  void testLogInAs2() throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> securityUtil.logInAs("janedoe"));
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
  }

  /**
   * Test {@link SecurityUtil#logInAs(String)}.
   * <ul>
   *   <li>Given {@link UserDetailsService}
   * {@link UserDetailsService#loadUserByUsername(String)} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SecurityUtil#logInAs(String)}
   */
  @Test
  @DisplayName("Test logInAs(String); given UserDetailsService loadUserByUsername(String) return 'null'")
  void testLogInAs_givenUserDetailsServiceLoadUserByUsernameReturnNull() throws UsernameNotFoundException {
    // Arrange
    when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> securityUtil.logInAs("janedoe"));
    verify(userDetailsService).loadUserByUsername(eq("janedoe"));
  }
}
