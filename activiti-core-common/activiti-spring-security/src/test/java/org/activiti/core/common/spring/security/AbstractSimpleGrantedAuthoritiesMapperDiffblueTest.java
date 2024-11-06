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
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class AbstractSimpleGrantedAuthoritiesMapperDiffblueTest {
  /**
   * Test
   * {@link AbstractSimpleGrantedAuthoritiesMapper#getAuthoritesFilteredByPrefix(Collection, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractSimpleGrantedAuthoritiesMapper#getAuthoritesFilteredByPrefix(Collection, String)}
   */
  @Test
  @DisplayName("Test getAuthoritesFilteredByPrefix(Collection, String); then return Empty")
  void testGetAuthoritesFilteredByPrefix_thenReturnEmpty() {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));

    // Act
    List<String> actualAuthoritesFilteredByPrefix = AbstractSimpleGrantedAuthoritiesMapper
        .getAuthoritesFilteredByPrefix(authorities, "Prefix");

    // Assert
    assertTrue(actualAuthoritesFilteredByPrefix.isEmpty());
  }

  /**
   * Test
   * {@link AbstractSimpleGrantedAuthoritiesMapper#getAuthoritesFilteredByPrefix(Collection, String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractSimpleGrantedAuthoritiesMapper#getAuthoritesFilteredByPrefix(Collection, String)}
   */
  @Test
  @DisplayName("Test getAuthoritesFilteredByPrefix(Collection, String); then return Empty")
  void testGetAuthoritesFilteredByPrefix_thenReturnEmpty2() {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));
    authorities.add(new SimpleGrantedAuthority("Role"));

    // Act
    List<String> actualAuthoritesFilteredByPrefix = AbstractSimpleGrantedAuthoritiesMapper
        .getAuthoritesFilteredByPrefix(authorities, "Prefix");

    // Assert
    assertTrue(actualAuthoritesFilteredByPrefix.isEmpty());
  }

  /**
   * Test
   * {@link AbstractSimpleGrantedAuthoritiesMapper#getAuthoritesFilteredByPrefix(Collection, String)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractSimpleGrantedAuthoritiesMapper#getAuthoritesFilteredByPrefix(Collection, String)}
   */
  @Test
  @DisplayName("Test getAuthoritesFilteredByPrefix(Collection, String); when ArrayList(); then return Empty")
  void testGetAuthoritesFilteredByPrefix_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    List<String> actualAuthoritesFilteredByPrefix = AbstractSimpleGrantedAuthoritiesMapper
        .getAuthoritesFilteredByPrefix(new ArrayList<>(), "Prefix");

    // Assert
    assertTrue(actualAuthoritesFilteredByPrefix.isEmpty());
  }

  /**
   * Test
   * {@link AbstractSimpleGrantedAuthoritiesMapper#getAuthoritesFilteredByPrefix(Collection, String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractSimpleGrantedAuthoritiesMapper#getAuthoritesFilteredByPrefix(Collection, String)}
   */
  @Test
  @DisplayName("Test getAuthoritesFilteredByPrefix(Collection, String); when empty string; then return size is one")
  void testGetAuthoritesFilteredByPrefix_whenEmptyString_thenReturnSizeIsOne() {
    // Arrange
    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("Role"));

    // Act
    List<String> actualAuthoritesFilteredByPrefix = AbstractSimpleGrantedAuthoritiesMapper
        .getAuthoritesFilteredByPrefix(authorities, "");

    // Assert
    assertEquals(1, actualAuthoritesFilteredByPrefix.size());
    assertEquals("Role", actualAuthoritesFilteredByPrefix.get(0));
  }
}
