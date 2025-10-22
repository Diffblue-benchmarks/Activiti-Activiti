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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StringToJsonNodeConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StringToJsonNodeConverterDiffblueTest {
  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private StringToJsonNodeConverter stringToJsonNodeConverter;

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString() throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<JsonNode>>any()))
        .thenReturn(MissingNode.getInstance());
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
    MissingNode instance = MissingNode.getInstance();

    // Act
    JsonNode actualConvertResult = stringToJsonNodeConverter.convert(objectMapper.writeValueAsString(instance));

    // Assert
    verify(objectMapper).readValue(eq("42"), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertSame(instance, actualConvertResult);
  }
}
