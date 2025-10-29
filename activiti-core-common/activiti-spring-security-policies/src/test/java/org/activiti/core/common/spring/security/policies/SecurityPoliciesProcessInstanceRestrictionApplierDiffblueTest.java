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
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  void testRestrictToKeys() {
    // Arrange
    HashSet<String> keys = new HashSet<>();

    // Act
    GetProcessInstancesPayload actualRestrictToKeysResult = securityPoliciesProcessInstanceRestrictionApplier
        .restrictToKeys(keys);

    // Assert
    assertNull(actualRestrictToKeysResult.getBusinessKey());
    assertNull(actualRestrictToKeysResult.getParentProcessInstanceId());
    assertFalse(actualRestrictToKeysResult.isActiveOnly());
    assertFalse(actualRestrictToKeysResult.isSuspendedOnly());
    Set<String> processDefinitionKeys = actualRestrictToKeysResult.getProcessDefinitionKeys();
    assertTrue(processDefinitionKeys.isEmpty());
    assertSame(keys, processDefinitionKeys);
  }

  /**
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  void testRestrictToKeys2() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
    keys.add("foo");

    // Act
    GetProcessInstancesPayload actualRestrictToKeysResult = securityPoliciesProcessInstanceRestrictionApplier
        .restrictToKeys(keys);

    // Assert
    assertNull(actualRestrictToKeysResult.getBusinessKey());
    assertNull(actualRestrictToKeysResult.getParentProcessInstanceId());
    Set<String> processDefinitionKeys = actualRestrictToKeysResult.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys.size());
    assertFalse(actualRestrictToKeysResult.isActiveOnly());
    assertFalse(actualRestrictToKeysResult.isSuspendedOnly());
    assertTrue(processDefinitionKeys.contains("foo"));
    assertSame(keys, processDefinitionKeys);
  }

  /**
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  void testRestrictToKeys3() {
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
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#denyAll()}
   */
  @Test
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
   * Method under test:
   * {@link SecurityPoliciesProcessInstanceRestrictionApplier#allowAll()}
   */
  @Test
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
