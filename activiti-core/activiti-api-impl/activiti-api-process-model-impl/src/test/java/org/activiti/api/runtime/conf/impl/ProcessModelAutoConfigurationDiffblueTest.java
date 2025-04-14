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
package org.activiti.api.runtime.conf.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.activiti.api.runtime.model.impl.DateToStringConverter;
import org.activiti.api.runtime.model.impl.JsonNodeToStringConverter;
import org.activiti.api.runtime.model.impl.ListToStringConverter;
import org.activiti.api.runtime.model.impl.LocalDateTimeToStringConverter;
import org.activiti.api.runtime.model.impl.LocalDateToStringConverter;
import org.activiti.api.runtime.model.impl.MapToStringConverter;
import org.activiti.api.runtime.model.impl.ObjectValue;
import org.activiti.api.runtime.model.impl.ObjectValueToStringConverter;
import org.activiti.api.runtime.model.impl.SetToStringConverter;
import org.activiti.api.runtime.model.impl.StringToJsonNodeConverter;
import org.activiti.api.runtime.model.impl.StringToObjectValueConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessModelAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcessModelAutoConfigurationDiffblueTest {
  @MockBean
  private Converter<Object, Object> converter;

  @Autowired
  private ProcessModelAutoConfiguration processModelAutoConfiguration;

  @Autowired
  private Set<Converter<Object, Object>> set;

  /**
   * Test {@link ProcessModelAutoConfiguration#conversionService()}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#conversionService()}
   */
  @Test
  @DisplayName("Test conversionService()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.format.support.FormattingConversionService ProcessModelAutoConfiguration.conversionService()"})
  void testConversionService() {
    // Arrange, Act and Assert
    assertTrue(processModelAutoConfiguration.conversionService() instanceof ApplicationConversionService);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test mapToStringConverter(ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"MapToStringConverter ProcessModelAutoConfiguration.mapToStringConverter(ObjectMapper)"})
  void testMapToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    MapToStringConverter actualMapToStringConverterResult = processModelAutoConfiguration
        .mapToStringConverter(JsonMapper.builder().findAndAddModules().build());

    // Assert
    assertEquals("{}", actualMapToStringConverterResult.convert(new HashMap<>()));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToJsonNodeConverter(ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"StringToJsonNodeConverter ProcessModelAutoConfiguration.stringToJsonNodeConverter(ObjectMapper)"})
  void testStringToJsonNodeConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));

    // Act
    StringToJsonNodeConverter actualStringToJsonNodeConverterResult = processModelAutoConfiguration
        .stringToJsonNodeConverter(builderResult.findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();
    JsonNode actualConvertResult = actualStringToJsonNodeConverterResult
        .convert(buildResult.writeValueAsString(MissingNode.getInstance()));

    // Assert
    assertSame(((NullNode) actualConvertResult).instance, actualConvertResult);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}.
   * <ul>
   *   <li>When builder findAndAddModules build.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToJsonNodeConverter(ObjectMapper); when builder findAndAddModules build")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"StringToJsonNodeConverter ProcessModelAutoConfiguration.stringToJsonNodeConverter(ObjectMapper)"})
  void testStringToJsonNodeConverter_whenBuilderFindAndAddModulesBuild() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    StringToJsonNodeConverter actualStringToJsonNodeConverterResult = processModelAutoConfiguration
        .stringToJsonNodeConverter(JsonMapper.builder().findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();
    JsonNode actualConvertResult = actualStringToJsonNodeConverterResult
        .convert(buildResult.writeValueAsString(MissingNode.getInstance()));

    // Assert
    assertSame(((NullNode) actualConvertResult).instance, actualConvertResult);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test jsonNodeToStringConverter(ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNodeToStringConverter ProcessModelAutoConfiguration.jsonNodeToStringConverter(ObjectMapper)"})
  void testJsonNodeToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    JsonNodeToStringConverter actualJsonNodeToStringConverterResult = processModelAutoConfiguration
        .jsonNodeToStringConverter(JsonMapper.builder().findAndAddModules().build());
    MissingNode source = MissingNode.getInstance();
    String actualConvertResult = actualJsonNodeToStringConverterResult.convert(source);

    // Assert
    assertTrue(source.traverse() instanceof TreeTraversingParser);
    assertEquals("null", actualConvertResult);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#dateToStringConverter()}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#dateToStringConverter()}
   */
  @Test
  @DisplayName("Test dateToStringConverter()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"DateToStringConverter ProcessModelAutoConfiguration.dateToStringConverter()"})
  void testDateToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    DateToStringConverter actualDateToStringConverterResult = (new ProcessModelAutoConfiguration())
        .dateToStringConverter();

    // Assert
    assertEquals("1970-01-01T00:00:00Z", actualDateToStringConverterResult
        .convert(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#localDateTimeToStringConverter()}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#localDateTimeToStringConverter()}
   */
  @Test
  @DisplayName("Test localDateTimeToStringConverter()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"LocalDateTimeToStringConverter ProcessModelAutoConfiguration.localDateTimeToStringConverter()"})
  void testLocalDateTimeToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LocalDateTimeToStringConverter actualLocalDateTimeToStringConverterResult = (new ProcessModelAutoConfiguration())
        .localDateTimeToStringConverter();

    // Assert
    assertEquals("1970-01-01T00:00:00",
        actualLocalDateTimeToStringConverterResult.convert(LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#localDateToStringConverter()}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#localDateToStringConverter()}
   */
  @Test
  @DisplayName("Test localDateToStringConverter()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"LocalDateToStringConverter ProcessModelAutoConfiguration.localDateToStringConverter()"})
  void testLocalDateToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    LocalDateToStringConverter actualLocalDateToStringConverterResult = (new ProcessModelAutoConfiguration())
        .localDateToStringConverter();

    // Assert
    assertEquals("1970-01-01", actualLocalDateToStringConverterResult.convert(LocalDate.of(1970, 1, 1)));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test listToStringConverter(ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ListToStringConverter ProcessModelAutoConfiguration.listToStringConverter(ObjectMapper)"})
  void testListToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    ListToStringConverter actualListToStringConverterResult = processModelAutoConfiguration
        .listToStringConverter(JsonMapper.builder().findAndAddModules().build());

    // Assert
    assertEquals("[]", actualListToStringConverterResult.convert(new ArrayList<>()));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test setToStringConverter(ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"SetToStringConverter ProcessModelAutoConfiguration.setToStringConverter(ObjectMapper)"})
  void testSetToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    SetToStringConverter actualSetToStringConverterResult = processModelAutoConfiguration
        .setToStringConverter(JsonMapper.builder().findAndAddModules().build());

    // Assert
    assertEquals("[]", actualSetToStringConverterResult.convert(new HashSet<>()));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToObjectValueConverter(ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "StringToObjectValueConverter ProcessModelAutoConfiguration.stringToObjectValueConverter(ObjectMapper)"})
  void testStringToObjectValueConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    StringToObjectValueConverter actualStringToObjectValueConverterResult = processModelAutoConfiguration
        .stringToObjectValueConverter(JsonMapper.builder().findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();

    // Assert
    assertEquals("Object",
        actualStringToObjectValueConverterResult.convert(buildResult.writeValueAsString(new ObjectValue("Object")))
            .getObject());
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test objectValueToStringConverter(ObjectMapper)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ObjectValueToStringConverter ProcessModelAutoConfiguration.objectValueToStringConverter(ObjectMapper)"})
  void testObjectValueToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    ObjectValueToStringConverter actualObjectValueToStringConverterResult = processModelAutoConfiguration
        .objectValueToStringConverter(JsonMapper.builder().findAndAddModules().build());

    // Assert
    assertEquals("{\"object\":\"Object\"}",
        actualObjectValueToStringConverterResult.convert(new ObjectValue("Object")));
  }

  /**
   * Test new {@link ProcessModelAutoConfiguration} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ProcessModelAutoConfiguration}
   */
  @Test
  @DisplayName("Test new ProcessModelAutoConfiguration (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessModelAutoConfiguration.<init>()"})
  void testNewProcessModelAutoConfiguration() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange, Act and Assert
    assertTrue((new ProcessModelAutoConfiguration()).conversionService() instanceof ApplicationConversionService);
  }
}
