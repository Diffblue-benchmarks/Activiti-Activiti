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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.sun.security.auth.UserPrincipal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SimpleGrantedAuthoritiesResolver.class})
@ExtendWith(SpringExtension.class)
class SimpleGrantedAuthoritiesResolverDiffblueTest {
  @Autowired
  private SimpleGrantedAuthoritiesResolver simpleGrantedAuthoritiesResolver;

  /**
   * Test {@link SimpleGrantedAuthoritiesResolver#getAuthorities(Authentication)}
   * with {@code authentication}.
   * <ul>
   *   <li>Then return {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesResolver#getAuthorities(Authentication)}
   */
  @Test
  @DisplayName("Test getAuthorities(Authentication) with 'authentication'; then return List")
  void testGetAuthoritiesWithAuthentication_thenReturnList() {
    // Arrange
    SimpleGrantedAuthoritiesResolver simpleGrantedAuthoritiesResolver2 = new SimpleGrantedAuthoritiesResolver();

    // Act
    Collection<? extends GrantedAuthority> actualAuthorities = simpleGrantedAuthoritiesResolver2
        .getAuthorities(new TestingAuthenticationToken("Principal", "Credentials"));

    // Assert
    assertTrue(actualAuthorities instanceof List);
    assertTrue(actualAuthorities.isEmpty());
  }

  /**
   * Test {@link SimpleGrantedAuthoritiesResolver#getAuthorities(Principal)} with
   * {@code principal}.
   * <ul>
   *   <li>Then return {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesResolver#getAuthorities(Principal)}
   */
  @Test
  @DisplayName("Test getAuthorities(Principal) with 'principal'; then return List")
  void testGetAuthoritiesWithPrincipal_thenReturnList() {
    // Arrange
    SimpleGrantedAuthoritiesResolver simpleGrantedAuthoritiesResolver2 = new SimpleGrantedAuthoritiesResolver();
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    Class<Authentication> originalAuthentication = Authentication.class;

    // Act
    Collection<? extends GrantedAuthority> actualAuthorities = simpleGrantedAuthoritiesResolver2
        .getAuthorities(new RunAsUserToken("Invalid principal authorities", "Principal", "Credentials", authorities,
            originalAuthentication));

    // Assert
    assertTrue(actualAuthorities instanceof List);
    assertTrue(actualAuthorities.isEmpty());
  }

  /**
   * Test {@link SimpleGrantedAuthoritiesResolver#getAuthorities(Principal)} with
   * {@code principal}.
   * <ul>
   *   <li>Then throw {@link SecurityException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesResolver#getAuthorities(Principal)}
   */
  @Test
  @DisplayName("Test getAuthorities(Principal) with 'principal'; then throw SecurityException")
  void testGetAuthoritiesWithPrincipal_thenThrowSecurityException() {
    // Arrange
    SimpleGrantedAuthoritiesResolver simpleGrantedAuthoritiesResolver2 = new SimpleGrantedAuthoritiesResolver();

    // Act and Assert
    assertThrows(SecurityException.class,
        () -> simpleGrantedAuthoritiesResolver2.getAuthorities(new UserPrincipal("principal")));
  }

  /**
   * Test {@link SimpleGrantedAuthoritiesResolver#securityException()}.
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesResolver#securityException()}
   */
  @Test
  @DisplayName("Test securityException()")
  void testSecurityException() {
    // Arrange and Act
    SecurityException actualSecurityExceptionResult = (new SimpleGrantedAuthoritiesResolver()).securityException();

    // Assert
    assertEquals("Invalid principal authorities", actualSecurityExceptionResult.getLocalizedMessage());
    assertEquals("Invalid principal authorities", actualSecurityExceptionResult.getMessage());
    assertNull(actualSecurityExceptionResult.getCause());
    assertEquals(0, actualSecurityExceptionResult.getSuppressed().length);
  }

  /**
   * Test {@link SimpleGrantedAuthoritiesResolver#emptyAuthorities()}.
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesResolver#emptyAuthorities()}
   */
  @Test
  @DisplayName("Test emptyAuthorities()")
  void testEmptyAuthorities() {
    // Arrange and Act
    Collection<Object> actualEmptyAuthoritiesResult = (new SimpleGrantedAuthoritiesResolver()).emptyAuthorities();

    // Assert
    assertTrue(actualEmptyAuthoritiesResult instanceof List);
    assertTrue(actualEmptyAuthoritiesResult.isEmpty());
  }

  /**
   * Test
   * {@link SimpleGrantedAuthoritiesResolver#isSupportedPrincipal(Principal)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesResolver#isSupportedPrincipal(Principal)}
   */
  @Test
  @DisplayName("Test isSupportedPrincipal(Principal); then return 'true'")
  void testIsSupportedPrincipal_thenReturnTrue() {
    // Arrange
    SimpleGrantedAuthoritiesResolver simpleGrantedAuthoritiesResolver2 = new SimpleGrantedAuthoritiesResolver();
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    Class<Authentication> originalAuthentication = Authentication.class;

    // Act and Assert
    assertTrue(simpleGrantedAuthoritiesResolver2.isSupportedPrincipal(
        new RunAsUserToken("Key", "Principal", "Credentials", authorities, originalAuthentication)));
  }

  /**
   * Test
   * {@link SimpleGrantedAuthoritiesResolver#isSupportedPrincipal(Principal)}.
   * <ul>
   *   <li>When {@link UserPrincipal#UserPrincipal(String)} with name is
   * {@code principal}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleGrantedAuthoritiesResolver#isSupportedPrincipal(Principal)}
   */
  @Test
  @DisplayName("Test isSupportedPrincipal(Principal); when UserPrincipal(String) with name is 'principal'; then return 'false'")
  void testIsSupportedPrincipal_whenUserPrincipalWithNameIsPrincipal_thenReturnFalse() {
    // Arrange
    SimpleGrantedAuthoritiesResolver simpleGrantedAuthoritiesResolver2 = new SimpleGrantedAuthoritiesResolver();

    // Act and Assert
    assertFalse(simpleGrantedAuthoritiesResolver2.isSupportedPrincipal(new UserPrincipal("principal")));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link SimpleGrantedAuthoritiesResolver}
   *   <li>{@link SimpleGrantedAuthoritiesResolver#getPrincipalClass()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends Authentication> actualPrincipalClass = (new SimpleGrantedAuthoritiesResolver()).getPrincipalClass();

    // Assert
    Class<Authentication> expectedPrincipalClass = Authentication.class;
    assertEquals(expectedPrincipalClass, actualPrincipalClass);
  }
}
