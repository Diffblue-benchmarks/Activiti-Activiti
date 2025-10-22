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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.MessageHeaders;

class ProcessVariableHeaderMapperDiffblueTest {
  /**
   * Test {@link ProcessVariableHeaderMapper#ProcessVariableHeaderMapper(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then return toHeaders {@code null} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#ProcessVariableHeaderMapper(Set)}
   */
  @Test
  @DisplayName("Test new ProcessVariableHeaderMapper(Set); when HashSet(); then return toHeaders 'null' Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessVariableHeaderMapper.<init>(Set)"})
  void testNewProcessVariableHeaderMapper_whenHashSet_thenReturnToHeadersNullEmpty() {
    // Arrange, Act and Assert
    assertTrue((new ProcessVariableHeaderMapper(new HashSet<>())).toHeaders(null).isEmpty());
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)} with {@code MessageHeaders}, {@code Map}.
   * <ul>
   *   <li>Given {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  @DisplayName("Test fromHeaders(MessageHeaders, Map) with 'MessageHeaders', 'Map'; given 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessVariableHeaderMapper.fromHeaders(MessageHeaders, Map)"})
  void testFromHeadersWithMessageHeadersMap_givenFalse() {
    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("foo");
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(sync);
    MessageHeaders headers = mock(MessageHeaders.class);
    when(headers.containsKey(Mockito.<Object>any())).thenReturn(false);
    HashMap<String, Object> target = new HashMap<>();

    // Act
    processVariableHeaderMapper.fromHeaders(headers, target);

    // Assert that nothing has changed
    verify(headers).containsKey(isA(Object.class));
    assertTrue(target.isEmpty());
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)} with {@code MessageHeaders}, {@code Map}.
   * <ul>
   *   <li>Given {@code Get}.</li>
   *   <li>Then {@link HashMap#HashMap()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  @DisplayName("Test fromHeaders(MessageHeaders, Map) with 'MessageHeaders', 'Map'; given 'Get'; then HashMap() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessVariableHeaderMapper.fromHeaders(MessageHeaders, Map)"})
  void testFromHeadersWithMessageHeadersMap_givenGet_thenHashMapSizeIsOne() {
    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("foo");
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(sync);
    MessageHeaders headers = mock(MessageHeaders.class);
    when(headers.get(Mockito.<Object>any())).thenReturn("Get");
    when(headers.containsKey(Mockito.<Object>any())).thenReturn(true);
    HashMap<String, Object> target = new HashMap<>();

    // Act
    processVariableHeaderMapper.fromHeaders(headers, target);

    // Assert
    verify(headers).containsKey(isA(Object.class));
    verify(headers).get(isA(Object.class));
    assertEquals(1, target.size());
    assertEquals("Get", target.get("foo"));
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)} with {@code MessageHeaders}, {@code Map}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link HashMap#HashMap()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  @DisplayName("Test fromHeaders(MessageHeaders, Map) with 'MessageHeaders', 'Map'; when 'null'; then HashMap() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessVariableHeaderMapper.fromHeaders(MessageHeaders, Map)"})
  void testFromHeadersWithMessageHeadersMap_whenNull_thenHashMapEmpty() {
    // Arrange
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(new HashSet<>());
    HashMap<String, Object> target = new HashMap<>();

    // Act
    processVariableHeaderMapper.fromHeaders(null, target);

    // Assert that nothing has changed
    assertTrue(target.isEmpty());
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#toHeaders(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  @DisplayName("Test toHeaders(Map) with 'Map'; given 'foo'; when HashMap() 'foo' is '42'; then return HashMap()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariableHeaderMapper.toHeaders(Map)"})
  void testToHeadersWithMap_givenFoo_whenHashMapFooIs42_thenReturnHashMap() {
    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("foo");
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(sync);

    HashMap<String, Object> source = new HashMap<>();
    source.put("foo", "42");

    // Act and Assert
    assertEquals(source, processVariableHeaderMapper.toHeaders(source));
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#toHeaders(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@link HashSet#HashSet()} add {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  @DisplayName("Test toHeaders(Map) with 'Map'; given HashSet() add 'foo'; when HashMap(); then return Empty")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  @DisplayName("Test toHeaders(Map) with 'Map'; when HashMap(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessVariableHeaderMapper.toHeaders(Map)"})
  void testToHeadersWithMap_whenHashMap_thenReturnEmpty() {
    // Arrange
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(new HashSet<>());

    // Act and Assert
    assertTrue(processVariableHeaderMapper.toHeaders(new HashMap<>()).isEmpty());
  }
}
