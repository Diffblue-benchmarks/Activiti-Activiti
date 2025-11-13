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
package org.activiti.spring.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessVariableHeaderMapperDiffblueTest {
  /**
   * Test {@link ProcessVariableHeaderMapper#ProcessVariableHeaderMapper(Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then return toHeaders {@code null} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariableHeaderMapper#ProcessVariableHeaderMapper(Set)}
   */
  @Test
  @DisplayName(
      "Test new ProcessVariableHeaderMapper(Set); when HashSet(); then return toHeaders 'null' Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessVariableHeaderMapper.<init>(Set)"})
  void testNewProcessVariableHeaderMapper_whenHashSet_thenReturnToHeadersNullEmpty() {
    // Arrange, Act and Assert
    assertTrue(new ProcessVariableHeaderMapper(new HashSet<>()).toHeaders(null).isEmpty());
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#toHeaders(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code Value}.
   *   <li>Then return {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  @DisplayName(
      "Test toHeaders(Map) with 'Map'; given 'foo'; when HashMap() 'foo' is 'Value'; then return HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessVariableHeaderMapper.toHeaders(Map)"})
  void testToHeadersWithMap_givenFoo_whenHashMapFooIsValue_thenReturnHashMap() {
    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("foo");
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(sync);

    HashMap<String, Object> source = new HashMap<>();
    source.put("foo", "Value");

    // Act
    Map<String, Object> actualToHeadersResult = processVariableHeaderMapper.toHeaders(source);

    // Assert
    assertEquals(source, actualToHeadersResult);
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#toHeaders(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given {@link HashSet#HashSet()} add {@code foo}.
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  @DisplayName(
      "Test toHeaders(Map) with 'Map'; given HashSet() add 'foo'; when HashMap(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessVariableHeaderMapper.toHeaders(Map)"})
  void testToHeadersWithMap_givenHashSetAddFoo_whenHashMap_thenReturnEmpty() {
    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("foo");
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(sync);

    // Act and Assert
    assertTrue(processVariableHeaderMapper.toHeaders(new HashMap<>()).isEmpty());
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#toHeaders(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  @DisplayName("Test toHeaders(Map) with 'Map'; when HashMap(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ProcessVariableHeaderMapper.toHeaders(Map)"})
  void testToHeadersWithMap_whenHashMap_thenReturnEmpty() {
    // Arrange
    ProcessVariableHeaderMapper processVariableHeaderMapper =
        new ProcessVariableHeaderMapper(new HashSet<>());

    // Act and Assert
    assertTrue(processVariableHeaderMapper.toHeaders(new HashMap<>()).isEmpty());
  }
}
