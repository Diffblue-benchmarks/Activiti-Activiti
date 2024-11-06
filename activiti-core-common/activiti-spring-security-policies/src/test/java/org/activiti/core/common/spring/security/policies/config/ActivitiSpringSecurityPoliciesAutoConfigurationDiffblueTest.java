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
package org.activiti.core.common.spring.security.policies.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.activiti.api.runtime.shared.security.PrincipalGroupsProvider;
import org.activiti.api.runtime.shared.security.PrincipalRolesProvider;
import org.activiti.api.runtime.shared.security.SecurityContextPrincipalProvider;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.core.common.spring.security.AuthenticationPrincipalIdentityProvider;
import org.activiti.core.common.spring.security.LocalSpringSecurityManager;
import org.activiti.core.common.spring.security.policies.ProcessSecurityPoliciesManagerImpl;
import org.activiti.core.common.spring.security.policies.SecurityPoliciesProcessDefinitionRestrictionApplier;
import org.activiti.core.common.spring.security.policies.SecurityPoliciesProcessInstanceRestrictionApplier;
import org.activiti.core.common.spring.security.policies.SecurityPoliciesRestrictionApplier;
import org.activiti.core.common.spring.security.policies.conf.SecurityPoliciesProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivitiSpringSecurityPoliciesAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ActivitiSpringSecurityPoliciesAutoConfigurationDiffblueTest {
  @Autowired
  private ActivitiSpringSecurityPoliciesAutoConfiguration activitiSpringSecurityPoliciesAutoConfiguration;

  @MockBean
  private SecurityManager securityManager;

  /**
   * Test
   * {@link ActivitiSpringSecurityPoliciesAutoConfiguration#processSecurityPoliciesManager(SecurityManager, SecurityPoliciesProperties, SecurityPoliciesRestrictionApplier, SecurityPoliciesRestrictionApplier)}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityPoliciesAutoConfiguration#processSecurityPoliciesManager(SecurityManager, SecurityPoliciesProperties, SecurityPoliciesRestrictionApplier, SecurityPoliciesRestrictionApplier)}
   */
  @Test
  @DisplayName("Test processSecurityPoliciesManager(SecurityManager, SecurityPoliciesProperties, SecurityPoliciesRestrictionApplier, SecurityPoliciesRestrictionApplier)")
  void testProcessSecurityPoliciesManager() {
    // Arrange
    SecurityContextPrincipalProvider securityContextPrincipalProvider = mock(SecurityContextPrincipalProvider.class);
    LocalSpringSecurityManager securityManager2 = new LocalSpringSecurityManager(securityContextPrincipalProvider,
        new AuthenticationPrincipalIdentityProvider(), mock(PrincipalGroupsProvider.class),
        mock(PrincipalRolesProvider.class));

    SecurityPoliciesProperties securityPoliciesProperties = new SecurityPoliciesProperties();
    SecurityPoliciesProcessDefinitionRestrictionApplier processDefinitionRestrictionApplier = new SecurityPoliciesProcessDefinitionRestrictionApplier();

    // Act and Assert
    assertTrue(activitiSpringSecurityPoliciesAutoConfiguration.processSecurityPoliciesManager(securityManager2,
        securityPoliciesProperties, processDefinitionRestrictionApplier,
        new SecurityPoliciesProcessInstanceRestrictionApplier()) instanceof ProcessSecurityPoliciesManagerImpl);
  }

  /**
   * Test
   * {@link ActivitiSpringSecurityPoliciesAutoConfiguration#processInstanceRestrictionApplier()}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityPoliciesAutoConfiguration#processInstanceRestrictionApplier()}
   */
  @Test
  @DisplayName("Test processInstanceRestrictionApplier()")
  void testProcessInstanceRestrictionApplier() {
    // Arrange and Act
    SecurityPoliciesRestrictionApplier<GetProcessInstancesPayload> actualProcessInstanceRestrictionApplierResult = (new ActivitiSpringSecurityPoliciesAutoConfiguration())
        .processInstanceRestrictionApplier();

    // Assert
    assertTrue(
        actualProcessInstanceRestrictionApplierResult instanceof SecurityPoliciesProcessInstanceRestrictionApplier);
    GetProcessInstancesPayload allowAllResult = actualProcessInstanceRestrictionApplierResult.allowAll();
    assertNull(allowAllResult.getBusinessKey());
    GetProcessInstancesPayload denyAllResult = actualProcessInstanceRestrictionApplierResult.denyAll();
    assertNull(denyAllResult.getBusinessKey());
    assertNull(allowAllResult.getParentProcessInstanceId());
    assertNull(denyAllResult.getParentProcessInstanceId());
    assertEquals(1, denyAllResult.getProcessDefinitionKeys().size());
    assertFalse(allowAllResult.isActiveOnly());
    assertFalse(denyAllResult.isActiveOnly());
    assertFalse(allowAllResult.isSuspendedOnly());
    assertFalse(denyAllResult.isSuspendedOnly());
    assertTrue(allowAllResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test
   * {@link ActivitiSpringSecurityPoliciesAutoConfiguration#processDefinitionRestrictionApplier()}.
   * <p>
   * Method under test:
   * {@link ActivitiSpringSecurityPoliciesAutoConfiguration#processDefinitionRestrictionApplier()}
   */
  @Test
  @DisplayName("Test processDefinitionRestrictionApplier()")
  void testProcessDefinitionRestrictionApplier() {
    // Arrange and Act
    SecurityPoliciesRestrictionApplier<GetProcessDefinitionsPayload> actualProcessDefinitionRestrictionApplierResult = (new ActivitiSpringSecurityPoliciesAutoConfiguration())
        .processDefinitionRestrictionApplier();

    // Assert
    assertTrue(
        actualProcessDefinitionRestrictionApplierResult instanceof SecurityPoliciesProcessDefinitionRestrictionApplier);
    GetProcessDefinitionsPayload allowAllResult = actualProcessDefinitionRestrictionApplierResult.allowAll();
    assertNull(allowAllResult.getProcessDefinitionId());
    GetProcessDefinitionsPayload denyAllResult = actualProcessDefinitionRestrictionApplierResult.denyAll();
    assertNull(denyAllResult.getProcessDefinitionId());
    assertEquals(1, denyAllResult.getProcessDefinitionKeys().size());
    assertFalse(allowAllResult.hasDefinitionKeys());
    assertTrue(allowAllResult.getProcessDefinitionKeys().isEmpty());
    assertTrue(denyAllResult.hasDefinitionKeys());
  }
}
