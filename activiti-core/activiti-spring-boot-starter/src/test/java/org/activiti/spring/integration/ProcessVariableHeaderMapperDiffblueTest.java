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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
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
   * Method under test:
   * {@link ProcessVariableHeaderMapper#ProcessVariableHeaderMapper(Set)}
   */
  @Test
  @DisplayName("Test new ProcessVariableHeaderMapper(Set); when HashSet(); then return toHeaders 'null' Empty")
  void testNewProcessVariableHeaderMapper_whenHashSet_thenReturnToHeadersNullEmpty() {
    // Arrange, Act and Assert
    assertTrue((new ProcessVariableHeaderMapper(new HashSet<>())).toHeaders(null).isEmpty());
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   * with {@code MessageHeaders}, {@code Map}.
   * <ul>
   *   <li>Given {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  @DisplayName("Test fromHeaders(MessageHeaders, Map) with 'MessageHeaders', 'Map'; given 'false'")
  void testFromHeadersWithMessageHeadersMap_givenFalse() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("");
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
   * Test {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   * with {@code MessageHeaders}, {@code Map}.
   * <ul>
   *   <li>Given {@code Get}.</li>
   *   <li>Then {@link HashMap#HashMap()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  @DisplayName("Test fromHeaders(MessageHeaders, Map) with 'MessageHeaders', 'Map'; given 'Get'; then HashMap() size is one")
  void testFromHeadersWithMessageHeadersMap_givenGet_thenHashMapSizeIsOne() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("");
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
    assertEquals("Get", target.get(""));
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   * with {@code MessageHeaders}, {@code Map}.
   * <ul>
   *   <li>When {@link MessageHeaders}.</li>
   *   <li>Then {@link HashMap#HashMap()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  @DisplayName("Test fromHeaders(MessageHeaders, Map) with 'MessageHeaders', 'Map'; when MessageHeaders; then HashMap() Empty")
  void testFromHeadersWithMessageHeadersMap_whenMessageHeaders_thenHashMapEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(new HashSet<>());
    MessageHeaders headers = mock(MessageHeaders.class);
    HashMap<String, Object> target = new HashMap<>();

    // Act
    processVariableHeaderMapper.fromHeaders(headers, target);

    // Assert that nothing has changed
    assertTrue(target.isEmpty());
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   * with {@code MessageHeaders}, {@code Map}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link HashMap#HashMap()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  @DisplayName("Test fromHeaders(MessageHeaders, Map) with 'MessageHeaders', 'Map'; when 'null'; then HashMap() Empty")
  void testFromHeadersWithMessageHeadersMap_whenNull_thenHashMapEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  @DisplayName("Test toHeaders(Map) with 'Map'; given '42'; when HashMap() 'foo' is '42'; then return size is one")
  void testToHeadersWithMap_given42_whenHashMapFooIs42_thenReturnSizeIsOne() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("foo");
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(sync);

    HashMap<String, Object> source = new HashMap<>();
    source.put("foo", "42");

    // Act
    Map<String, Object> actualToHeadersResult = processVariableHeaderMapper.toHeaders(source);

    // Assert
    assertEquals(1, actualToHeadersResult.size());
    assertEquals("42", actualToHeadersResult.get("foo"));
  }

  /**
   * Test {@link ProcessVariableHeaderMapper#toHeaders(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@link BiFunction}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  @DisplayName("Test toHeaders(Map) with 'Map'; given BiFunction; when HashMap() computeIfPresent 'foo' and BiFunction")
  void testToHeadersWithMap_givenBiFunction_whenHashMapComputeIfPresentFooAndBiFunction() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(new HashSet<>());

    HashMap<String, Object> source = new HashMap<>();
    source.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertTrue(processVariableHeaderMapper.toHeaders(source).isEmpty());
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
  void testToHeadersWithMap_givenHashSetAddFoo_whenHashMap_thenReturnEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
  void testToHeadersWithMap_whenHashMap_thenReturnEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(new HashSet<>());

    // Act and Assert
    assertTrue(processVariableHeaderMapper.toHeaders(new HashMap<>()).isEmpty());
  }
}
