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
import java.util.HashSet;
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
class ProcessSecurityPoliciesManagerImplDiffblueTest {
  @Autowired
  private ProcessSecurityPoliciesManagerImpl processSecurityPoliciesManagerImpl;

  @MockBean
  private SecurityManager securityManager;

  @Autowired
  private SecurityPoliciesProperties securityPoliciesProperties;

  @MockBean
  private SecurityPoliciesRestrictionApplier<GetProcessDefinitionsPayload> securityPoliciesRestrictionApplier;

  @MockBean
  private SecurityPoliciesRestrictionApplier<GetProcessInstancesPayload> securityPoliciesRestrictionApplier2;

  /**
   * Test
   * {@link ProcessSecurityPoliciesManagerImpl#ProcessSecurityPoliciesManagerImpl(SecurityManager, SecurityPoliciesProperties, SecurityPoliciesRestrictionApplier, SecurityPoliciesRestrictionApplier)}.
   * <p>
   * Method under test:
   * {@link ProcessSecurityPoliciesManagerImpl#ProcessSecurityPoliciesManagerImpl(SecurityManager, SecurityPoliciesProperties, SecurityPoliciesRestrictionApplier, SecurityPoliciesRestrictionApplier)}
   */
  @Test
  @DisplayName("Test new ProcessSecurityPoliciesManagerImpl(SecurityManager, SecurityPoliciesProperties, SecurityPoliciesRestrictionApplier, SecurityPoliciesRestrictionApplier)")
  void testNewProcessSecurityPoliciesManagerImpl() {
    // Arrange
    SecurityContextPrincipalProvider securityContextPrincipalProvider = mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager = new LocalSpringSecurityManager(securityContextPrincipalProvider,
        new AuthenticationPrincipalIdentityProvider(), mock(PrincipalGroupsProvider.class),
        mock(PrincipalRolesProvider.class));

    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier = new SecurityPoliciesProcessDefinitionRestrictionApplier();

    // Act and Assert
    assertSame(securityPoliciesProperties,
        (new ProcessSecurityPoliciesManagerImpl(securityManager, securityPoliciesProperties,
            processDefinitionRestrictionApplier, new SecurityPoliciesProcessInstanceRestrictionApplier()))
            .getSecurityPoliciesProperties());
  }

  /**
   * Test
   * {@link ProcessSecurityPoliciesManagerImpl#restrictProcessDefQuery(SecurityPolicyAccess)}.
   * <p>
   * Method under test:
   * {@link ProcessSecurityPoliciesManagerImpl#restrictProcessDefQuery(SecurityPolicyAccess)}
   */
  @Test
  @DisplayName("Test restrictProcessDefQuery(SecurityPolicyAccess)")
  void testRestrictProcessDefQuery() {
    // Arrange
    GetProcessDefinitionsPayload getProcessDefinitionsPayload = new GetProcessDefinitionsPayload();
    when(securityPoliciesRestrictionApplier.allowAll()).thenReturn(getProcessDefinitionsPayload);

    // Act
    GetProcessDefinitionsPayload actualRestrictProcessDefQueryResult = processSecurityPoliciesManagerImpl
        .restrictProcessDefQuery(SecurityPolicyAccess.NONE);

    // Assert
    verify(securityPoliciesRestrictionApplier).allowAll();
    assertSame(getProcessDefinitionsPayload, actualRestrictProcessDefQueryResult);
  }

  /**
   * Test
   * {@link ProcessSecurityPoliciesManagerImpl#restrictProcessInstQuery(SecurityPolicyAccess)}.
   * <p>
   * Method under test:
   * {@link ProcessSecurityPoliciesManagerImpl#restrictProcessInstQuery(SecurityPolicyAccess)}
   */
  @Test
  @DisplayName("Test restrictProcessInstQuery(SecurityPolicyAccess)")
  void testRestrictProcessInstQuery() {
    // Arrange
    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    getProcessInstancesPayload.setProcessDefinitionKeys(new HashSet<>());
    getProcessInstancesPayload.setSuspendedOnly(true);
    when(securityPoliciesRestrictionApplier2.allowAll()).thenReturn(getProcessInstancesPayload);

    // Act
    GetProcessInstancesPayload actualRestrictProcessInstQueryResult = processSecurityPoliciesManagerImpl
        .restrictProcessInstQuery(SecurityPolicyAccess.NONE);

    // Assert
    verify(securityPoliciesRestrictionApplier2).allowAll();
    assertSame(getProcessInstancesPayload, actualRestrictProcessInstQueryResult);
  }

  /**
   * Test {@link ProcessSecurityPoliciesManagerImpl#canWrite(String)} with
   * {@code processDefinitionKey}.
   * <p>
   * Method under test:
   * {@link ProcessSecurityPoliciesManagerImpl#canWrite(String)}
   */
  @Test
  @DisplayName("Test canWrite(String) with 'processDefinitionKey'")
  void testCanWriteWithProcessDefinitionKey() {
    // Arrange, Act and Assert
    assertTrue(processSecurityPoliciesManagerImpl.canWrite("Process Definition Key"));
  }

  /**
   * Test {@link ProcessSecurityPoliciesManagerImpl#canRead(String)} with
   * {@code processDefinitionKey}.
   * <p>
   * Method under test: {@link ProcessSecurityPoliciesManagerImpl#canRead(String)}
   */
  @Test
  @DisplayName("Test canRead(String) with 'processDefinitionKey'")
  void testCanReadWithProcessDefinitionKey() {
    // Arrange, Act and Assert
    assertTrue(processSecurityPoliciesManagerImpl.canRead("Process Definition Key"));
  }

  /**
   * Test
   * {@link ProcessSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}
   */
  @Test
  @DisplayName("Test anEntryInSetStartsKey(Set, String); given '42'; when '42'; then return 'true'")
  void testAnEntryInSetStartsKey_given42_when42_thenReturnTrue() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
    keys.add("42");
    keys.add("foo");

    // Act and Assert
    assertTrue(processSecurityPoliciesManagerImpl.anEntryInSetStartsKey(keys, "42"));
  }

  /**
   * Test
   * {@link ProcessSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}
   */
  @Test
  @DisplayName("Test anEntryInSetStartsKey(Set, String); given '42'; when HashSet() add '42'; then return 'false'")
  void testAnEntryInSetStartsKey_given42_whenHashSetAdd42_thenReturnFalse() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
    keys.add("42");
    keys.add("foo");

    // Act and Assert
    assertFalse(processSecurityPoliciesManagerImpl.anEntryInSetStartsKey(keys, "Process Definition Key"));
  }

  /**
   * Test
   * {@link ProcessSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}
   */
  @Test
  @DisplayName("Test anEntryInSetStartsKey(Set, String); given 'foo'; when HashSet() add 'foo'; then return 'false'")
  void testAnEntryInSetStartsKey_givenFoo_whenHashSetAddFoo_thenReturnFalse() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
    keys.add("foo");

    // Act and Assert
    assertFalse(processSecurityPoliciesManagerImpl.anEntryInSetStartsKey(keys, "Process Definition Key"));
  }

  /**
   * Test
   * {@link ProcessSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessSecurityPoliciesManagerImpl#anEntryInSetStartsKey(Set, String)}
   */
  @Test
  @DisplayName("Test anEntryInSetStartsKey(Set, String); when HashSet(); then return 'false'")
  void testAnEntryInSetStartsKey_whenHashSet_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(processSecurityPoliciesManagerImpl.anEntryInSetStartsKey(new HashSet<>(), "Process Definition Key"));
  }
}
