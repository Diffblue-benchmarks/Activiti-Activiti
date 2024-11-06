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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Set;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SecurityPoliciesProcessInstanceRestrictionApplier.class})
@ExtendWith(SpringExtension.class)
class SecurityPoliciesProcessInstanceRestrictionApplierDiffblueTest {
  @Autowired
  private SecurityPoliciesProcessInstanceRestrictionApplier securityPoliciesProcessInstanceRestrictionApplier;

  /**
   * Test
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  @DisplayName("Test restrictToKeys(Set); given '42'; when HashSet() add '42'")
  void testRestrictToKeys_given42_whenHashSetAdd42() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
    keys.add("42");
    keys.add("foo");

    // Act
    GetProcessInstancesPayload actualRestrictToKeysResult = securityPoliciesProcessInstanceRestrictionApplier
        .restrictToKeys(keys);

    // Assert
    assertNull(actualRestrictToKeysResult.getBusinessKey());
    assertNull(actualRestrictToKeysResult.getParentProcessInstanceId());
    assertFalse(actualRestrictToKeysResult.isActiveOnly());
    assertFalse(actualRestrictToKeysResult.isSuspendedOnly());
    assertSame(keys, actualRestrictToKeysResult.getProcessDefinitionKeys());
  }

  /**
   * Test
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return ProcessDefinitionKeys is {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  @DisplayName("Test restrictToKeys(Set); given 'foo'; then return ProcessDefinitionKeys is HashSet()")
  void testRestrictToKeys_givenFoo_thenReturnProcessDefinitionKeysIsHashSet() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
    keys.add("foo");

    // Act
    GetProcessInstancesPayload actualRestrictToKeysResult = securityPoliciesProcessInstanceRestrictionApplier
        .restrictToKeys(keys);

    // Assert
    assertNull(actualRestrictToKeysResult.getBusinessKey());
    assertNull(actualRestrictToKeysResult.getParentProcessInstanceId());
    assertFalse(actualRestrictToKeysResult.isActiveOnly());
    assertFalse(actualRestrictToKeysResult.isSuspendedOnly());
    assertSame(keys, actualRestrictToKeysResult.getProcessDefinitionKeys());
  }

  /**
   * Test
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return ProcessDefinitionKeys Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  @DisplayName("Test restrictToKeys(Set); when HashSet(); then return ProcessDefinitionKeys Empty")
  void testRestrictToKeys_whenHashSet_thenReturnProcessDefinitionKeysEmpty() {
    // Arrange and Act
    GetProcessInstancesPayload actualRestrictToKeysResult = securityPoliciesProcessInstanceRestrictionApplier
        .restrictToKeys(new HashSet<>());

    // Assert
    assertNull(actualRestrictToKeysResult.getBusinessKey());
    assertNull(actualRestrictToKeysResult.getParentProcessInstanceId());
    assertFalse(actualRestrictToKeysResult.isActiveOnly());
    assertFalse(actualRestrictToKeysResult.isSuspendedOnly());
    assertTrue(actualRestrictToKeysResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link SecurityPoliciesProcessInstanceRestrictionApplier#denyAll()}.
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#denyAll()}
   */
  @Test
  @DisplayName("Test denyAll()")
  void testDenyAll() {
    // Arrange and Act
    GetProcessInstancesPayload actualDenyAllResult = securityPoliciesProcessInstanceRestrictionApplier.denyAll();

    // Assert
    assertNull(actualDenyAllResult.getBusinessKey());
    assertNull(actualDenyAllResult.getParentProcessInstanceId());
    assertEquals(1, actualDenyAllResult.getProcessDefinitionKeys().size());
    assertFalse(actualDenyAllResult.isActiveOnly());
    assertFalse(actualDenyAllResult.isSuspendedOnly());
  }

  /**
   * Test {@link SecurityPoliciesProcessInstanceRestrictionApplier#allowAll()}.
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#allowAll()}
   */
  @Test
  @DisplayName("Test allowAll()")
  void testAllowAll() {
    // Arrange and Act
    GetProcessInstancesPayload actualAllowAllResult = securityPoliciesProcessInstanceRestrictionApplier.allowAll();

    // Assert
    assertNull(actualAllowAllResult.getBusinessKey());
    assertNull(actualAllowAllResult.getParentProcessInstanceId());
    assertFalse(actualAllowAllResult.isActiveOnly());
    assertFalse(actualAllowAllResult.isSuspendedOnly());
    assertTrue(actualAllowAllResult.getProcessDefinitionKeys().isEmpty());
  }
}
