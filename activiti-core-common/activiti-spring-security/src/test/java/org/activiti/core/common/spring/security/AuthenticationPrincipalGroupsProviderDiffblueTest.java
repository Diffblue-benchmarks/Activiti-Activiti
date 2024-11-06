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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.sun.security.auth.UserPrincipal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationPrincipalGroupsProvider.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthenticationPrincipalGroupsProviderDiffblueTest {
  @Autowired
  private AuthenticationPrincipalGroupsProvider authenticationPrincipalGroupsProvider;

  @MockBean
  private GrantedAuthoritiesGroupsMapper grantedAuthoritiesGroupsMapper;

  @MockBean
  private GrantedAuthoritiesResolver grantedAuthoritiesResolver;

  /**
   * Test {@link AuthenticationPrincipalGroupsProvider#getGroups(Principal)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AuthenticationPrincipalGroupsProvider#getGroups(Principal)}
   */
  @Test
  @DisplayName("Test getGroups(Principal); then return Empty")
  void testGetGroups_thenReturnEmpty() {
    // Arrange
    Mockito
        .<Collection<? extends GrantedAuthority>>when(
            grantedAuthoritiesResolver.getAuthorities(Mockito.<Principal>any()))
        .thenReturn(new ArrayList<>());
    when(grantedAuthoritiesGroupsMapper.getGroups(Mockito.<Collection<GrantedAuthority>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<String> actualGroups = authenticationPrincipalGroupsProvider.getGroups(new UserPrincipal("principal"));

    // Assert
    verify(grantedAuthoritiesGroupsMapper).getGroups(isA(Collection.class));
    verify(grantedAuthoritiesResolver).getAuthorities(isA(Principal.class));
    assertTrue(actualGroups.isEmpty());
  }

  /**
   * Test {@link AuthenticationPrincipalGroupsProvider#securityException()}.
   * <p>
   * Method under test:
   * {@link AuthenticationPrincipalGroupsProvider#securityException()}
   */
  @Test
  @DisplayName("Test securityException()")
  void testSecurityException() {
    // Arrange and Act
    SecurityException actualSecurityExceptionResult = authenticationPrincipalGroupsProvider.securityException();

    // Assert
    assertEquals("Invalid principal groups", actualSecurityExceptionResult.getLocalizedMessage());
    assertEquals("Invalid principal groups", actualSecurityExceptionResult.getMessage());
    assertNull(actualSecurityExceptionResult.getCause());
    assertEquals(0, actualSecurityExceptionResult.getSuppressed().length);
  }
}
