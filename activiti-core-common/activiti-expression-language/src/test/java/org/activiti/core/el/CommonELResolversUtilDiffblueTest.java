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
package org.activiti.core.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import jakarta.el.ArrayELResolver;
import jakarta.el.ELResolver;
import jakarta.el.ListELResolver;
import jakarta.el.MapELResolver;
import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CommonELResolversUtilDiffblueTest {
  /**
   * Test {@link CommonELResolversUtil#arrayResolver()}.
   * <p>
   * Method under test: {@link CommonELResolversUtil#arrayResolver()}
   */
  @Test
  @DisplayName("Test arrayResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ELResolver CommonELResolversUtil.arrayResolver()"})
  void testArrayResolver() {
    // Arrange and Act
    ELResolver actualArrayResolverResult = CommonELResolversUtil.arrayResolver();

    // Assert
    assertTrue(actualArrayResolverResult instanceof ArrayELResolver);
    assertNull(actualArrayResolverResult.getCommonPropertyType(null, "Base"));
    assertNull(actualArrayResolverResult.getFeatureDescriptors(null, "Base"));
  }

  /**
   * Test {@link CommonELResolversUtil#listResolver()}.
   * <p>
   * Method under test: {@link CommonELResolversUtil#listResolver()}
   */
  @Test
  @DisplayName("Test listResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ELResolver CommonELResolversUtil.listResolver()"})
  void testListResolver() {
    // Arrange and Act
    ELResolver actualListResolverResult = CommonELResolversUtil.listResolver();

    // Assert
    assertTrue(actualListResolverResult instanceof ListELResolver);
    assertNull(actualListResolverResult.getCommonPropertyType(null, "Base"));
    assertNull(actualListResolverResult.getFeatureDescriptors(null, "Base"));
  }

  /**
   * Test {@link CommonELResolversUtil#mapResolver()}.
   * <p>
   * Method under test: {@link CommonELResolversUtil#mapResolver()}
   */
  @Test
  @DisplayName("Test mapResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ELResolver CommonELResolversUtil.mapResolver()"})
  void testMapResolver() {
    // Arrange and Act
    ELResolver actualMapResolverResult = CommonELResolversUtil.mapResolver();

    // Assert
    assertTrue(actualMapResolverResult instanceof MapELResolver);
    assertNull(actualMapResolverResult.getCommonPropertyType(null, "Base"));
    assertNull(actualMapResolverResult.getFeatureDescriptors(null, "Base"));
  }

  /**
   * Test {@link CommonELResolversUtil#jsonNodeResolver()}.
   * <p>
   * Method under test: {@link CommonELResolversUtil#jsonNodeResolver()}
   */
  @Test
  @DisplayName("Test jsonNodeResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ELResolver CommonELResolversUtil.jsonNodeResolver()"})
  void testJsonNodeResolver() {
    // Arrange and Act
    ELResolver actualJsonNodeResolverResult = CommonELResolversUtil.jsonNodeResolver();

    // Assert
    ObjectMapper objectMapper = ((JsonNodeELResolver) actualJsonNodeResolverResult).getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(objectMapper.getDeserializationContext() instanceof DefaultDeserializationContext.Impl);
    assertTrue(objectMapper.getVisibilityChecker() instanceof Std);
    assertTrue(objectMapper.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(objectMapper.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(objectMapper.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(objectMapper.getSerializerProvider() instanceof Impl);
    assertTrue(objectMapper.getSerializerProviderInstance() instanceof Impl);
    assertTrue(objectMapper.getDateFormat() instanceof StdDateFormat);
    assertTrue(actualJsonNodeResolverResult instanceof JsonNodeELResolver);
    assertNull(objectMapper.getInjectableValues());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertTrue(objectMapper.getRegisteredModuleIds().isEmpty());
    assertSame(factory, objectMapper.getJsonFactory());
  }

  /**
   * Test {@link CommonELResolversUtil#beanResolver()}.
   * <p>
   * Method under test: {@link CommonELResolversUtil#beanResolver()}
   */
  @Test
  @DisplayName("Test beanResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ELResolver CommonELResolversUtil.beanResolver()"})
  void testBeanResolver() {
    // Arrange and Act
    ELResolver actualBeanResolverResult = CommonELResolversUtil.beanResolver();

    // Assert
    Iterator<FeatureDescriptor> featureDescriptors = actualBeanResolverResult.getFeatureDescriptors(null, "Base");
    assertTrue(featureDescriptors.next() instanceof PropertyDescriptor);
    assertTrue(featureDescriptors.next() instanceof PropertyDescriptor);
    assertTrue(featureDescriptors.next() instanceof PropertyDescriptor);
    assertTrue(featureDescriptors.next() instanceof PropertyDescriptor);
    assertTrue(actualBeanResolverResult instanceof ELResolverReflectionBlockerDecorator);
    assertFalse(featureDescriptors.hasNext());
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualBeanResolverResult.getCommonPropertyType(null, "Base"));
  }
}
