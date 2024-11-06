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
package org.activiti.core.common.spring.security.policies;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.activiti.api.runtime.shared.security.PrincipalGroupsProvider;
import org.activiti.api.runtime.shared.security.PrincipalRolesProvider;
import org.activiti.api.runtime.shared.security.SecurityContextPrincipalProvider;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.core.common.spring.security.AuthenticationPrincipalIdentityProvider;
import org.activiti.core.common.spring.security.LocalSpringSecurityManager;
import org.activiti.core.common.spring.security.policies.conf.SecurityPoliciesProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessSecurityPoliciesManagerImpl.class, SecurityPoliciesProperties.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BaseSecurityPoliciesManagerImplDiffblueTest {
  @Autowired
  private BaseSecurityPoliciesManagerImpl baseSecurityPoliciesManagerImpl;

  @MockBean
  private SecurityManager securityManager;

  @Autowired
  private SecurityPoliciesProperties securityPoliciesProperties;

  @MockBean
  private SecurityPoliciesRestrictionApplier<GetProcessDefinitionsPayload> securityPoliciesRestrictionApplier;

  @MockBean
  private SecurityPoliciesRestrictionApplier<GetProcessInstancesPayload> securityPoliciesRestrictionApplier2;

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#arePoliciesDefined()}.
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#arePoliciesDefined()}
   */
  @Test
  @DisplayName("Test arePoliciesDefined()")
  void testArePoliciesDefined() {
    // Arrange, Act and Assert
    assertFalse(baseSecurityPoliciesManagerImpl.arePoliciesDefined());
  }

  /**
   * Test
   * {@link BaseSecurityPoliciesManagerImpl#getAllowedKeys(SecurityPolicyAccess[])}.
   * <ul>
   *   <li>Given {@link SecurityManager}
   * {@link SecurityManager#getAuthenticatedUserId()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#getAllowedKeys(SecurityPolicyAccess[])}
   */
  @Test
  @DisplayName("Test getAllowedKeys(SecurityPolicyAccess[]); given SecurityManager getAuthenticatedUserId() return 'null'")
  void testGetAllowedKeys_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act
    Map<String, Set<String>> actualAllowedKeys = baseSecurityPoliciesManagerImpl
        .getAllowedKeys(SecurityPolicyAccess.NONE);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    assertTrue(actualAllowedKeys.isEmpty());
  }

  /**
   * Test
   * {@link BaseSecurityPoliciesManagerImpl#getAllowedKeys(SecurityPolicyAccess[])}.
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserGroups()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#getAllowedKeys(SecurityPolicyAccess[])}
   */
  @Test
  @DisplayName("Test getAllowedKeys(SecurityPolicyAccess[]); then calls getAuthenticatedUserGroups()")
  void testGetAllowedKeys_thenCallsGetAuthenticatedUserGroups() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    Map<String, Set<String>> actualAllowedKeys = baseSecurityPoliciesManagerImpl
        .getAllowedKeys(SecurityPolicyAccess.NONE);

    // Assert
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    assertTrue(actualAllowedKeys.isEmpty());
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#canRead(String, String)} with
   * {@code processDefinitionKey}, {@code appName}.
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#canRead(String, String)}
   */
  @Test
  @DisplayName("Test canRead(String, String) with 'processDefinitionKey', 'appName'")
  void testCanReadWithProcessDefinitionKeyAppName() {
    // Arrange, Act and Assert
    assertTrue(baseSecurityPoliciesManagerImpl.canRead("Process Definition Key", "App Name"));
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#canWrite(String, String)} with
   * {@code processDefinitionKey}, {@code appName}.
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#canWrite(String, String)}
   */
  @Test
  @DisplayName("Test canWrite(String, String) with 'processDefinitionKey', 'appName'")
  void testCanWriteWithProcessDefinitionKeyAppName() {
    // Arrange, Act and Assert
    assertTrue(baseSecurityPoliciesManagerImpl.canWrite("Process Definition Key", "App Name"));
  }

  /**
   * Test
   * {@link BaseSecurityPoliciesManagerImpl#hasPermission(String, SecurityPolicyAccess, String)}.
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#hasPermission(String, SecurityPolicyAccess, String)}
   */
  @Test
  @DisplayName("Test hasPermission(String, SecurityPolicyAccess, String)")
  void testHasPermission() {
    // Arrange, Act and Assert
    assertTrue(
        baseSecurityPoliciesManagerImpl.hasPermission("Process Definition Key", SecurityPolicyAccess.NONE, "App Name"));
  }

  /**
   * Test
   * {@link BaseSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}
   */
  @Test
  @DisplayName("Test anEntryInSetStartsKey(Set, String); given '42'; when '42'; then return 'true'")
  void testAnEntryInSetStartsKey_given42_when42_thenReturnTrue() {
    // Arrange
    SecurityContextPrincipalProvider securityContextPrincipalProvider = mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager = new LocalSpringSecurityManager(securityContextPrincipalProvider,
        new AuthenticationPrincipalIdentityProvider(), mock(PrincipalGroupsProvider.class),
        mock(PrincipalRolesProvider.class));

    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier = new SecurityPoliciesProcessDefinitionRestrictionApplier();
    ProcessSecurityPoliciesManagerImpl processSecurityPoliciesManagerImpl = new ProcessSecurityPoliciesManagerImpl(
        securityManager, securityPoliciesProperties, processDefinitionRestrictionApplier,
        new SecurityPoliciesProcessInstanceRestrictionApplier());

    HashSet<String> keys = new HashSet<>();
    keys.add("42");
    keys.add("foo");

    // Act and Assert
    assertTrue(processSecurityPoliciesManagerImpl.anEntryInSetStartsKey(keys, "42"));
  }

  /**
   * Test
   * {@link BaseSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}
   */
  @Test
  @DisplayName("Test anEntryInSetStartsKey(Set, String); given '42'; when HashSet() add '42'; then return 'false'")
  void testAnEntryInSetStartsKey_given42_whenHashSetAdd42_thenReturnFalse() {
    // Arrange
    SecurityContextPrincipalProvider securityContextPrincipalProvider = mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager = new LocalSpringSecurityManager(securityContextPrincipalProvider,
        new AuthenticationPrincipalIdentityProvider(), mock(PrincipalGroupsProvider.class),
        mock(PrincipalRolesProvider.class));

    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier = new SecurityPoliciesProcessDefinitionRestrictionApplier();
    ProcessSecurityPoliciesManagerImpl processSecurityPoliciesManagerImpl = new ProcessSecurityPoliciesManagerImpl(
        securityManager, securityPoliciesProperties, processDefinitionRestrictionApplier,
        new SecurityPoliciesProcessInstanceRestrictionApplier());

    HashSet<String> keys = new HashSet<>();
    keys.add("42");
    keys.add("foo");

    // Act and Assert
    assertFalse(processSecurityPoliciesManagerImpl.anEntryInSetStartsKey(keys, "Process Definition Key"));
  }

  /**
   * Test
   * {@link BaseSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}.
   * <ul>
   *   <li>Given {@code Keys}.</li>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@code Keys}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}
   */
  @Test
  @DisplayName("Test anEntryInSetStartsKey(Set, String); given 'Keys'; when LinkedHashSet() add 'Keys'; then return 'false'")
  void testAnEntryInSetStartsKey_givenKeys_whenLinkedHashSetAddKeys_thenReturnFalse() {
    // Arrange
    SecurityContextPrincipalProvider securityContextPrincipalProvider = mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager = new LocalSpringSecurityManager(securityContextPrincipalProvider,
        new AuthenticationPrincipalIdentityProvider(), mock(PrincipalGroupsProvider.class),
        mock(PrincipalRolesProvider.class));

    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier = new SecurityPoliciesProcessDefinitionRestrictionApplier();
    ProcessSecurityPoliciesManagerImpl processSecurityPoliciesManagerImpl = new ProcessSecurityPoliciesManagerImpl(
        securityManager, securityPoliciesProperties, processDefinitionRestrictionApplier,
        new SecurityPoliciesProcessInstanceRestrictionApplier());

    LinkedHashSet<String> keys = new LinkedHashSet<>();
    keys.add("Keys");

    // Act and Assert
    assertFalse(processSecurityPoliciesManagerImpl.anEntryInSetStartsKey(keys, "Process Definition Key"));
  }

  /**
   * Test
   * {@link BaseSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}
   */
  @Test
  @DisplayName("Test anEntryInSetStartsKey(Set, String); when HashSet(); then return 'false'")
  void testAnEntryInSetStartsKey_whenHashSet_thenReturnFalse() {
    // Arrange
    SecurityContextPrincipalProvider securityContextPrincipalProvider = mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager = new LocalSpringSecurityManager(securityContextPrincipalProvider,
        new AuthenticationPrincipalIdentityProvider(), mock(PrincipalGroupsProvider.class),
        mock(PrincipalRolesProvider.class));

    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier = new SecurityPoliciesProcessDefinitionRestrictionApplier();
    ProcessSecurityPoliciesManagerImpl processSecurityPoliciesManagerImpl = new ProcessSecurityPoliciesManagerImpl(
        securityManager, securityPoliciesProperties, processDefinitionRestrictionApplier,
        new SecurityPoliciesProcessInstanceRestrictionApplier());

    // Act and Assert
    assertFalse(processSecurityPoliciesManagerImpl.anEntryInSetStartsKey(new HashSet<>(), "Process Definition Key"));
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#getSecurityPoliciesProperties()}.
   * <p>
   * Method under test:
   * {@link BaseSecurityPoliciesManagerImpl#getSecurityPoliciesProperties()}
   */
  @Test
  @DisplayName("Test getSecurityPoliciesProperties()")
  void testGetSecurityPoliciesProperties() {
    // Arrange, Act and Assert
    assertSame(baseSecurityPoliciesManagerImpl.securityPoliciesProperties,
        baseSecurityPoliciesManagerImpl.getSecurityPoliciesProperties());
  }
}
