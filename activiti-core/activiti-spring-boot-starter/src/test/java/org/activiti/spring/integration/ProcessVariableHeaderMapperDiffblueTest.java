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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.MessageHeaders;

class ProcessVariableHeaderMapperDiffblueTest {
  /**
   * Method under test:
   * {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  void testFromHeaders() {
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
   * Method under test:
   * {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  void testFromHeaders2() {
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
   * Method under test:
   * {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  void testFromHeaders3() {
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
   * Method under test:
   * {@link ProcessVariableHeaderMapper#fromHeaders(MessageHeaders, Map)}
   */
  @Test
  void testFromHeaders4() {
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
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  void testToHeaders() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(new HashSet<>());

    // Act and Assert
    assertTrue(processVariableHeaderMapper.toHeaders(new HashMap<>()).isEmpty());
  }

  /**
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  void testToHeaders2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<String> sync = new HashSet<>();
    sync.add("foo");
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(sync);

    // Act and Assert
    assertTrue(processVariableHeaderMapper.toHeaders(new HashMap<>()).isEmpty());
  }

  /**
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  void testToHeaders3() {
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
   * Method under test: {@link ProcessVariableHeaderMapper#toHeaders(Map)}
   */
  @Test
  void testToHeaders4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessVariableHeaderMapper processVariableHeaderMapper = new ProcessVariableHeaderMapper(new HashSet<>());

    HashMap<String, Object> source = new HashMap<>();
    source.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertTrue(processVariableHeaderMapper.toHeaders(source).isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessVariableHeaderMapper#ProcessVariableHeaderMapper(Set)}
   */
  @Test
  void testNewProcessVariableHeaderMapper() {
    // Arrange, Act and Assert
    assertTrue((new ProcessVariableHeaderMapper(new HashSet<>())).toHeaders(null).isEmpty());
  }
}
