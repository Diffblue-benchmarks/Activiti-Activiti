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
package org.activiti.core.common.spring.project.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ApplicationUpgradeContextAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
class ApplicationUpgradeContextAutoConfigurationDiffblueTest {
  @Autowired
  private ApplicationUpgradeContextAutoConfiguration applicationUpgradeContextAutoConfiguration;

  /**
   * Test {@link ApplicationUpgradeContextAutoConfiguration#objectMapper()}.
   *
   * <p>Method under test: {@link ApplicationUpgradeContextAutoConfiguration#objectMapper()}
   */
  @Test
  @DisplayName("Test objectMapper()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectMapper ApplicationUpgradeContextAutoConfiguration.objectMapper()"})
  void testObjectMapper() {
    // Arrange and Act
    ObjectMapper actualObjectMapperResult =
        applicationUpgradeContextAutoConfiguration.objectMapper();

    // Assert
    JsonFactory factory = actualObjectMapperResult.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(
        actualObjectMapperResult.getDeserializationContext()
            instanceof DefaultDeserializationContext.Impl);
    assertTrue(actualObjectMapperResult.getVisibilityChecker() instanceof Std);
    assertTrue(
        actualObjectMapperResult.getPolymorphicTypeValidator()
            instanceof LaissezFaireSubTypeValidator);
    assertTrue(actualObjectMapperResult.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(actualObjectMapperResult.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(actualObjectMapperResult.getSerializerProvider() instanceof Impl);
    assertTrue(actualObjectMapperResult.getSerializerProviderInstance() instanceof Impl);
    assertTrue(actualObjectMapperResult.getDateFormat() instanceof StdDateFormat);
    assertNull(actualObjectMapperResult.getInjectableValues());
    assertNull(actualObjectMapperResult.getPropertyNamingStrategy());
    assertTrue(actualObjectMapperResult.getRegisteredModuleIds().isEmpty());
    assertSame(factory, actualObjectMapperResult.getJsonFactory());
  }

  /**
   * Test {@link ApplicationUpgradeContextAutoConfiguration#applicationUpgradeContextService(String,
   * Integer, Boolean, ObjectMapper, ResourcePatternResolver)}.
   *
   * <p>Method under test: {@link
   * ApplicationUpgradeContextAutoConfiguration#applicationUpgradeContextService(String, Integer,
   * Boolean, ObjectMapper, ResourcePatternResolver)}
   */
  @Test
  @DisplayName(
      "Test applicationUpgradeContextService(String, Integer, Boolean, ObjectMapper, ResourcePatternResolver)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationUpgradeContextService ApplicationUpgradeContextAutoConfiguration.applicationUpgradeContextService(String, Integer, Boolean, ObjectMapper, ResourcePatternResolver)"
  })
  void testApplicationUpgradeContextService() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    ApplicationUpgradeContextService actualApplicationUpgradeContextServiceResult =
        applicationUpgradeContextAutoConfiguration.applicationUpgradeContextService(
            "Absolute Path",
            1,
            true,
            objectMapper,
            new AnnotationConfigReactiveWebApplicationContext());

    // Assert
    assertEquals(
        1, actualApplicationUpgradeContextServiceResult.getEnforcedAppVersion().intValue());
    assertTrue(actualApplicationUpgradeContextServiceResult.isRollbackDeployment());
  }
}
