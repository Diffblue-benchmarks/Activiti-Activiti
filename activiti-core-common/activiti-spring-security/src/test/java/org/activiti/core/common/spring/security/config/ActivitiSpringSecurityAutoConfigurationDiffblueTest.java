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
package org.activiti.core.common.spring.security.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import org.activiti.api.runtime.shared.security.PrincipalGroupsProvider;
import org.activiti.api.runtime.shared.security.PrincipalIdentityProvider;
import org.activiti.api.runtime.shared.security.PrincipalRolesProvider;
import org.activiti.api.runtime.shared.security.SecurityContextPrincipalProvider;
import org.activiti.core.common.spring.security.AuthenticationPrincipalGroupsProvider;
import org.activiti.core.common.spring.security.AuthenticationPrincipalIdentityProvider;
import org.activiti.core.common.spring.security.AuthenticationPrincipalRolesProvider;
import org.activiti.core.common.spring.security.GrantedAuthoritiesGroupsMapper;
import org.activiti.core.common.spring.security.GrantedAuthoritiesResolver;
import org.activiti.core.common.spring.security.GrantedAuthoritiesRolesMapper;
import org.activiti.core.common.spring.security.LocalSpringSecurityContextPrincipalProvider;
import org.activiti.core.common.spring.security.LocalSpringSecurityManager;
import org.activiti.core.common.spring.security.SimpleGrantedAuthoritiesGroupsMapper;
import org.activiti.core.common.spring.security.SimpleGrantedAuthoritiesRolesMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivitiSpringSecurityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
class ActivitiSpringSecurityAutoConfigurationDiffblueTest {
  @Autowired
  private ActivitiSpringSecurityAutoConfiguration activitiSpringSecurityAutoConfiguration;

  /**
   * Test
   * {@link ActivitiSpringSecurityAutoConfiguration#grantedAuthoritiesGroupsMapper()}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityAutoConfiguration#grantedAuthoritiesGroupsMapper()}
   */
  @Test
  @DisplayName("Test grantedAuthoritiesGroupsMapper()")
  void testGrantedAuthoritiesGroupsMapper() {
    // Arrange and Act
    GrantedAuthoritiesGroupsMapper actualGrantedAuthoritiesGroupsMapperResult = (new ActivitiSpringSecurityAutoConfiguration())
        .grantedAuthoritiesGroupsMapper();

    // Assert
    assertTrue(actualGrantedAuthoritiesGroupsMapperResult instanceof SimpleGrantedAuthoritiesGroupsMapper);
    assertTrue(actualGrantedAuthoritiesGroupsMapperResult.getGroups(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link ActivitiSpringSecurityAutoConfiguration#grantedAuthoritiesRolesMapper()}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityAutoConfiguration#grantedAuthoritiesRolesMapper()}
   */
  @Test
  @DisplayName("Test grantedAuthoritiesRolesMapper()")
  void testGrantedAuthoritiesRolesMapper() {
    // Arrange and Act
    GrantedAuthoritiesRolesMapper actualGrantedAuthoritiesRolesMapperResult = (new ActivitiSpringSecurityAutoConfiguration())
        .grantedAuthoritiesRolesMapper();

    // Assert
    assertTrue(actualGrantedAuthoritiesRolesMapperResult instanceof SimpleGrantedAuthoritiesRolesMapper);
    assertTrue(actualGrantedAuthoritiesRolesMapperResult.getRoles(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link ActivitiSpringSecurityAutoConfiguration#securityContextPrincipalProvider()}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityAutoConfiguration#securityContextPrincipalProvider()}
   */
  @Test
  @DisplayName("Test securityContextPrincipalProvider()")
  void testSecurityContextPrincipalProvider() {
    // Arrange and Act
    SecurityContextPrincipalProvider actualSecurityContextPrincipalProviderResult = (new ActivitiSpringSecurityAutoConfiguration())
        .securityContextPrincipalProvider();
    Optional<Principal> actualCurrentPrincipal = actualSecurityContextPrincipalProviderResult.getCurrentPrincipal();

    // Assert
    assertTrue(actualSecurityContextPrincipalProviderResult instanceof LocalSpringSecurityContextPrincipalProvider);
    Optional<Principal> currentPrincipal = actualSecurityContextPrincipalProviderResult.getCurrentPrincipal();
    assertFalse(currentPrincipal.isPresent());
    assertSame(currentPrincipal, actualCurrentPrincipal);
  }

  /**
   * Test
   * {@link ActivitiSpringSecurityAutoConfiguration#principalIdentityProvider()}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityAutoConfiguration#principalIdentityProvider()}
   */
  @Test
  @DisplayName("Test principalIdentityProvider()")
  void testPrincipalIdentityProvider() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiSpringSecurityAutoConfiguration())
        .principalIdentityProvider() instanceof AuthenticationPrincipalIdentityProvider);
  }

  /**
   * Test
   * {@link ActivitiSpringSecurityAutoConfiguration#principalGroupsProvider(GrantedAuthoritiesResolver, GrantedAuthoritiesGroupsMapper)}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityAutoConfiguration#principalGroupsProvider(GrantedAuthoritiesResolver, GrantedAuthoritiesGroupsMapper)}
   */
  @Test
  @DisplayName("Test principalGroupsProvider(GrantedAuthoritiesResolver, GrantedAuthoritiesGroupsMapper)")
  void testPrincipalGroupsProvider() {
    // Arrange, Act and Assert
    assertTrue(activitiSpringSecurityAutoConfiguration.principalGroupsProvider(mock(GrantedAuthoritiesResolver.class),
        mock(GrantedAuthoritiesGroupsMapper.class)) instanceof AuthenticationPrincipalGroupsProvider);
  }

  /**
   * Test
   * {@link ActivitiSpringSecurityAutoConfiguration#principalRolessProvider(GrantedAuthoritiesResolver, GrantedAuthoritiesRolesMapper)}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityAutoConfiguration#principalRolessProvider(GrantedAuthoritiesResolver, GrantedAuthoritiesRolesMapper)}
   */
  @Test
  @DisplayName("Test principalRolessProvider(GrantedAuthoritiesResolver, GrantedAuthoritiesRolesMapper)")
  void testPrincipalRolessProvider() {
    // Arrange, Act and Assert
    assertTrue(activitiSpringSecurityAutoConfiguration.principalRolessProvider(mock(GrantedAuthoritiesResolver.class),
        mock(GrantedAuthoritiesRolesMapper.class)) instanceof AuthenticationPrincipalRolesProvider);
  }

  /**
   * Test
   * {@link ActivitiSpringSecurityAutoConfiguration#securityManager(SecurityContextPrincipalProvider, PrincipalIdentityProvider, PrincipalGroupsProvider, PrincipalRolesProvider)}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityAutoConfiguration#securityManager(SecurityContextPrincipalProvider, PrincipalIdentityProvider, PrincipalGroupsProvider, PrincipalRolesProvider)}
   */
  @Test
  @DisplayName("Test securityManager(SecurityContextPrincipalProvider, PrincipalIdentityProvider, PrincipalGroupsProvider, PrincipalRolesProvider)")
  void testSecurityManager() {
    // Arrange
    SecurityContextPrincipalProvider securityContextPrincipalProvider = mock(SecurityContextPrincipalProvider.class);

    // Act and Assert
    assertTrue(activitiSpringSecurityAutoConfiguration.securityManager(securityContextPrincipalProvider,
        new AuthenticationPrincipalIdentityProvider(), mock(PrincipalGroupsProvider.class),
        mock(PrincipalRolesProvider.class)) instanceof LocalSpringSecurityManager);
  }
}
