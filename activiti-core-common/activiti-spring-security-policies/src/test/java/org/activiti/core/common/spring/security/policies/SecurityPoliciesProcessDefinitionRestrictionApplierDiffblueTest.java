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
import org.junit.jupiter.api.DisplayName;
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
   * Test
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   *   <li>Then return hasDefinitionKeys.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  @DisplayName("Test restrictToKeys(Set); given '42'; when HashSet() add '42'; then return hasDefinitionKeys")
  void testRestrictToKeys_given42_whenHashSetAdd42_thenReturnHasDefinitionKeys() {
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
   * Test
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.</li>
   *   <li>Then return hasDefinitionKeys.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  @DisplayName("Test restrictToKeys(Set); given 'foo'; when HashSet() add 'foo'; then return hasDefinitionKeys")
  void testRestrictToKeys_givenFoo_whenHashSetAddFoo_thenReturnHasDefinitionKeys() {
    // Arrange
    HashSet<String> keys = new HashSet<>();
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
   * Test
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return not hasDefinitionKeys.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#restrictToKeys(Set)}
   */
  @Test
  @DisplayName("Test restrictToKeys(Set); when HashSet(); then return not hasDefinitionKeys")
  void testRestrictToKeys_whenHashSet_thenReturnNotHasDefinitionKeys() {
    // Arrange and Act
    GetProcessDefinitionsPayload actualRestrictToKeysResult = securityPoliciesProcessDefinitionRestrictionApplier
        .restrictToKeys(new HashSet<>());

    // Assert
    assertNull(actualRestrictToKeysResult.getProcessDefinitionId());
    assertFalse(actualRestrictToKeysResult.hasDefinitionKeys());
    assertTrue(actualRestrictToKeysResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link SecurityPoliciesProcessDefinitionRestrictionApplier#denyAll()}.
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#denyAll()}
   */
  @Test
  @DisplayName("Test denyAll()")
  void testDenyAll() {
    // Arrange and Act
    GetProcessDefinitionsPayload actualDenyAllResult = securityPoliciesProcessDefinitionRestrictionApplier.denyAll();

    // Assert
    assertNull(actualDenyAllResult.getProcessDefinitionId());
    assertEquals(1, actualDenyAllResult.getProcessDefinitionKeys().size());
    assertTrue(actualDenyAllResult.hasDefinitionKeys());
  }

  /**
   * Test {@link SecurityPoliciesProcessDefinitionRestrictionApplier#allowAll()}.
   * <p>
   * Method under test:
   * {@link SecurityPoliciesProcessDefinitionRestrictionApplier#allowAll()}
   */
  @Test
  @DisplayName("Test allowAll()")
  void testAllowAll() {
    // Arrange and Act
    GetProcessDefinitionsPayload actualAllowAllResult = securityPoliciesProcessDefinitionRestrictionApplier.allowAll();

    // Assert
    assertNull(actualAllowAllResult.getProcessDefinitionId());
    assertFalse(actualAllowAllResult.hasDefinitionKeys());
    assertTrue(actualAllowAllResult.getProcessDefinitionKeys().isEmpty());
  }
}
