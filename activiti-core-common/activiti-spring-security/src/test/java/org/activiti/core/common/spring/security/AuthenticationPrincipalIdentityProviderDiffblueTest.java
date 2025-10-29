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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.sun.security.auth.UserPrincipal;
import java.security.Principal;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationPrincipalIdentityProvider.class})
@ExtendWith(SpringExtension.class)
class AuthenticationPrincipalIdentityProviderDiffblueTest {
  @Autowired
  private AuthenticationPrincipalIdentityProvider authenticationPrincipalIdentityProvider;

  /**
   * Method under test:
   * {@link AuthenticationPrincipalIdentityProvider#getUserId(Principal)}
   */
  @Test
  void testGetUserId() {
    // Arrange, Act and Assert
    assertThrows(SecurityException.class,
        () -> authenticationPrincipalIdentityProvider.getUserId(new UserPrincipal("principal")));
    assertEquals("Principal",
        authenticationPrincipalIdentityProvider.getUserId(new TestingAuthenticationToken("Principal", "Credentials")));
  }

  /**
   * Method under test:
   * {@link AuthenticationPrincipalIdentityProvider#getUserId(Principal)}
   */
  @Test
  void testGetUserId2() {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    Class<Authentication> originalAuthentication = Authentication.class;

    // Act and Assert
    assertEquals("Principal",
        authenticationPrincipalIdentityProvider
            .getUserId((Principal) new RunAsUserToken("Invalid principal authentication object instance", "Principal",
                "Credentials", authorities, originalAuthentication)));
  }

  /**
   * Method under test:
   * {@link AuthenticationPrincipalIdentityProvider#securityException()}
   */
  @Test
  void testSecurityException() {
    // Arrange and Act
    SecurityException actualSecurityExceptionResult = authenticationPrincipalIdentityProvider.securityException();

    // Assert
    assertEquals("Invalid principal authentication object instance",
        actualSecurityExceptionResult.getLocalizedMessage());
    assertEquals("Invalid principal authentication object instance", actualSecurityExceptionResult.getMessage());
    assertNull(actualSecurityExceptionResult.getCause());
    assertEquals(0, actualSecurityExceptionResult.getSuppressed().length);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link AuthenticationPrincipalIdentityProvider}
   *   <li>{@link AuthenticationPrincipalIdentityProvider#getAnonymousUserId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("", (new AuthenticationPrincipalIdentityProvider()).getAnonymousUserId());
  }
}
