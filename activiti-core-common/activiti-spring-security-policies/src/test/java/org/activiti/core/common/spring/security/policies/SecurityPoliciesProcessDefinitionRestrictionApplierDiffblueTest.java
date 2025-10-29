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
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SecurityPoliciesProcessDefinitionRestrictionApplier.class})
@ExtendWith(SpringExtension.class)
class SecurityPoliciesProcessDefinitionRestrictionApplierDiffblueTest {
  @Autowired
  private SecurityPoliciesProcessDefinitionRestrictionApplier securityPoliciesProcessDefinitionRestrictionApplier;

  /**
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  void testRestrictToKeys() {
    // Arrange
    HashSet<String> keys = new HashSet<>();

    // Act
    GetProcessDefinitionsPayload actualRestrictToKeysResult = securityPoliciesProcessDefinitionRestrictionApplier
        .restrictToKeys(keys);

    // Assert
    assertNull(actualRestrictToKeysResult.getProcessDefinitionId());
    assertFalse(actualRestrictToKeysResult.hasDefinitionKeys());
    Set<String> processDefinitionKeys = actualRestrictToKeysResult.getProcessDefinitionKeys();
    assertTrue(processDefinitionKeys.isEmpty());
    assertSame(keys, processDefinitionKeys);
  }

  /**
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  void testRestrictToKeys2() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
    keys.add("foo");

    // Act
    GetProcessDefinitionsPayload actualRestrictToKeysResult = securityPoliciesProcessDefinitionRestrictionApplier
        .restrictToKeys(keys);

    // Assert
    assertNull(actualRestrictToKeysResult.getProcessDefinitionId());
    Set<String> processDefinitionKeys = actualRestrictToKeysResult.getProcessDefinitionKeys();
    assertEquals(1, processDefinitionKeys.size());
    assertTrue(processDefinitionKeys.contains("foo"));
    assertTrue(actualRestrictToKeysResult.hasDefinitionKeys());
    assertSame(keys, processDefinitionKeys);
  }

  /**
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  void testRestrictToKeys3() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
    keys.add("42");
    keys.add("foo");

    // Act
    GetProcessDefinitionsPayload actualRestrictToKeysResult = securityPoliciesProcessDefinitionRestrictionApplier
        .restrictToKeys(keys);

    // Assert
    assertNull(actualRestrictToKeysResult.getProcessDefinitionId());
    assertTrue(actualRestrictToKeysResult.hasDefinitionKeys());
    assertSame(keys, actualRestrictToKeysResult.getProcessDefinitionKeys());
  }

  /**
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#denyAll()}
   */
  @Test
  void testDenyAll() {
    // Arrange and Act
    GetProcessDefinitionsPayload actualDenyAllResult = securityPoliciesProcessDefinitionRestrictionApplier.denyAll();

    // Assert
    assertNull(actualDenyAllResult.getProcessDefinitionId());
    assertEquals(1, actualDenyAllResult.getProcessDefinitionKeys().size());
    assertTrue(actualDenyAllResult.hasDefinitionKeys());
  }

  /**
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#allowAll()}
   */
  @Test
  void testAllowAll() {
    // Arrange and Act
    GetProcessDefinitionsPayload actualAllowAllResult = securityPoliciesProcessDefinitionRestrictionApplier.allowAll();

    // Assert
    assertNull(actualAllowAllResult.getProcessDefinitionId());
    assertFalse(actualAllowAllResult.hasDefinitionKeys());
    assertTrue(actualAllowAllResult.getProcessDefinitionKeys().isEmpty());
  }
}
