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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.core.common.spring.security.policies.conf.SecurityPoliciesProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
    classes = {ProcessSecurityPoliciesManagerImpl.class, SecurityPoliciesProperties.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class BaseSecurityPoliciesManagerImplDiffblueTest {
  @Autowired private BaseSecurityPoliciesManagerImpl baseSecurityPoliciesManagerImpl;

  @MockBean private SecurityManager securityManager;

  @Autowired private SecurityPoliciesProperties securityPoliciesProperties;

  @MockBean
  private SecurityPoliciesRestrictionApplier<GetProcessDefinitionsPayload>
      securityPoliciesRestrictionApplier;

  @MockBean
  private SecurityPoliciesRestrictionApplier<GetProcessInstancesPayload>
      securityPoliciesRestrictionApplier2;

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#arePoliciesDefined()}.
   *
   * <p>Method under test: {@link BaseSecurityPoliciesManagerImpl#arePoliciesDefined()}
   */
  @Test
  @DisplayName("Test arePoliciesDefined()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseSecurityPoliciesManagerImpl.arePoliciesDefined()"})
  void testArePoliciesDefined() {
    // Arrange, Act and Assert
    assertFalse(baseSecurityPoliciesManagerImpl.arePoliciesDefined());
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#getAllowedKeys(SecurityPolicyAccess[])}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BaseSecurityPoliciesManagerImpl#getAllowedKeys(SecurityPolicyAccess[])}
   */
  @Test
  @DisplayName(
      "Test getAllowedKeys(SecurityPolicyAccess[]); given SecurityManager getAuthenticatedUserId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map BaseSecurityPoliciesManagerImpl.getAllowedKeys(SecurityPolicyAccess[])"})
  void testGetAllowedKeys_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act
    Map<String, Set<String>> actualAllowedKeys =
        baseSecurityPoliciesManagerImpl.getAllowedKeys(SecurityPolicyAccess.NONE);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    assertTrue(actualAllowedKeys.isEmpty());
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#getAllowedKeys(SecurityPolicyAccess[])}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserGroups()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BaseSecurityPoliciesManagerImpl#getAllowedKeys(SecurityPolicyAccess[])}
   */
  @Test
  @DisplayName(
      "Test getAllowedKeys(SecurityPolicyAccess[]); then calls getAuthenticatedUserGroups()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map BaseSecurityPoliciesManagerImpl.getAllowedKeys(SecurityPolicyAccess[])"})
  void testGetAllowedKeys_thenCallsGetAuthenticatedUserGroups() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act
    Map<String, Set<String>> actualAllowedKeys =
        baseSecurityPoliciesManagerImpl.getAllowedKeys(SecurityPolicyAccess.NONE);

    // Assert
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    assertTrue(actualAllowedKeys.isEmpty());
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#canRead(String, String)} with {@code
   * processDefinitionKey}, {@code appName}.
   *
   * <p>Method under test: {@link BaseSecurityPoliciesManagerImpl#canRead(String, String)}
   */
  @Test
  @DisplayName("Test canRead(String, String) with 'processDefinitionKey', 'appName'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseSecurityPoliciesManagerImpl.canRead(String, String)"})
  void testCanReadWithProcessDefinitionKeyAppName() {
    // Arrange, Act and Assert
    assertTrue(baseSecurityPoliciesManagerImpl.canRead("Process Definition Key", "App Name"));
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#canWrite(String, String)} with {@code
   * processDefinitionKey}, {@code appName}.
   *
   * <p>Method under test: {@link BaseSecurityPoliciesManagerImpl#canWrite(String, String)}
   */
  @Test
  @DisplayName("Test canWrite(String, String) with 'processDefinitionKey', 'appName'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseSecurityPoliciesManagerImpl.canWrite(String, String)"})
  void testCanWriteWithProcessDefinitionKeyAppName() {
    // Arrange, Act and Assert
    assertTrue(baseSecurityPoliciesManagerImpl.canWrite("Process Definition Key", "App Name"));
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#hasPermission(String, SecurityPolicyAccess,
   * String)}.
   *
   * <p>Method under test: {@link BaseSecurityPoliciesManagerImpl#hasPermission(String,
   * SecurityPolicyAccess, String)}
   */
  @Test
  @DisplayName("Test hasPermission(String, SecurityPolicyAccess, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseSecurityPoliciesManagerImpl.hasPermission(String, SecurityPolicyAccess, String)"
  })
  void testHasPermission() {
    // Arrange, Act and Assert
    assertTrue(
        baseSecurityPoliciesManagerImpl.hasPermission(
            "Process Definition Key", SecurityPolicyAccess.NONE, "App Name"));
  }

  /**
   * Test {@link BaseSecurityPoliciesManagerImpl#getSecurityPoliciesProperties()}.
   *
   * <p>Method under test: {@link BaseSecurityPoliciesManagerImpl#getSecurityPoliciesProperties()}
   */
  @Test
  @DisplayName("Test getSecurityPoliciesProperties()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SecurityPoliciesProperties BaseSecurityPoliciesManagerImpl.getSecurityPoliciesProperties()"
  })
  void testGetSecurityPoliciesProperties() {
    // Arrange and Act
    SecurityPoliciesProperties actualSecurityPoliciesProperties =
        baseSecurityPoliciesManagerImpl.getSecurityPoliciesProperties();

    // Assert
    assertSame(
        baseSecurityPoliciesManagerImpl.securityPoliciesProperties,
        actualSecurityPoliciesProperties);
  }
}
