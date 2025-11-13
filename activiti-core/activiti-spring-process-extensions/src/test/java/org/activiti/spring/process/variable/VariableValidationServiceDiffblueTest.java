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
package org.activiti.spring.process.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.spring.process.model.VariableDefinition;
import org.activiti.spring.process.variable.types.BigDecimalVariableType;
import org.activiti.spring.process.variable.types.DateVariableType;
import org.activiti.spring.process.variable.types.JsonObjectVariableType;
import org.activiti.spring.process.variable.types.VariableType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableValidationService.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class VariableValidationServiceDiffblueTest {
  @Autowired private Map<String, VariableType> map;

  @MockBean private VariableType variableType;

  @Autowired private VariableValidationService variableValidationService;

  /**
   * Test {@link VariableValidationService#validate(Object, VariableDefinition)}.
   *
   * <p>Method under test: {@link VariableValidationService#validate(Object, VariableDefinition)}
   */
  @Test
  @DisplayName("Test validate(Object, VariableDefinition)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableValidationService.validate(Object, VariableDefinition)"})
  void testValidate() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    variableTypeMap.put("json", new JsonObjectVariableType(objectMapper));
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act and Assert
    assertTrue(variableValidationService.validate("Var", variableDefinition));
  }

  /**
   * Test {@link VariableValidationService#validate(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code json} is {@link BigDecimalVariableType} (default
   *       constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validate(Object, VariableDefinition)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, VariableDefinition); given HashMap() 'json' is BigDecimalVariableType (default constructor); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableValidationService.validate(Object, VariableDefinition)"})
  void testValidate_givenHashMapJsonIsBigDecimalVariableType_thenReturnFalse() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    variableTypeMap.put("json", new BigDecimalVariableType());
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act and Assert
    assertFalse(variableValidationService.validate("Var", variableDefinition));
  }

  /**
   * Test {@link VariableValidationService#validate(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validate(Object, VariableDefinition)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, VariableDefinition); given 'java.lang.Object'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableValidationService.validate(Object, VariableDefinition)"})
  void testValidate_givenJavaLangObject_thenReturnFalse() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType =
        new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    variableTypeMap.put("json", dateVariableType);
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act and Assert
    assertFalse(variableValidationService.validate("Var", variableDefinition));
  }

  /**
   * Test {@link VariableValidationService#validate(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>When one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validate(Object, VariableDefinition)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, VariableDefinition); given 'java.lang.Object'; when one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableValidationService.validate(Object, VariableDefinition)"})
  void testValidate_givenJavaLangObject_whenOne_thenReturnFalse() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType =
        new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    variableTypeMap.put("json", dateVariableType);
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act and Assert
    assertFalse(variableValidationService.validate(1, variableDefinition));
  }

  /**
   * Test {@link VariableValidationService#validate(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>When {@code ${UU}}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validate(Object, VariableDefinition)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, VariableDefinition); given 'java.lang.Object'; when '${UU}'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableValidationService.validate(Object, VariableDefinition)"})
  void testValidate_givenJavaLangObject_whenUu_thenReturnTrue() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType =
        new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    variableTypeMap.put("json", dateVariableType);
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act and Assert
    assertTrue(variableValidationService.validate("${UU}", variableDefinition));
  }

  /**
   * Test {@link VariableValidationService#validate(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Given {@code json}.
   *   <li>When {@link VariableDefinition#VariableDefinition(String, Object)} with {@code Type} and
   *       {@code Value} Type is {@code json}.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validate(Object, VariableDefinition)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, VariableDefinition); given 'json'; when VariableDefinition(String, Object) with 'Type' and 'Value' Type is 'json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableValidationService.validate(Object, VariableDefinition)"})
  void testValidate_givenJson_whenVariableDefinitionWithTypeAndValueTypeIsJson() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    variableTypeMap.put("json", new BigDecimalVariableType());
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("json");

    // Act and Assert
    assertFalse(variableValidationService.validate("Var", variableDefinition));
  }

  /**
   * Test {@link VariableValidationService#validate(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Given {@link VariableType}.
   *   <li>When {@link VariableDefinition#VariableDefinition()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validate(Object, VariableDefinition)}
   */
  @Test
  @DisplayName(
      "Test validate(Object, VariableDefinition); given VariableType; when VariableDefinition(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableValidationService.validate(Object, VariableDefinition)"})
  void testValidate_givenVariableType_whenVariableDefinition_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(variableValidationService.validate("Var", new VariableDefinition()));
  }

  /**
   * Test {@link VariableValidationService#validateWithErrors(Object, VariableDefinition)}.
   *
   * <p>Method under test: {@link VariableValidationService#validateWithErrors(Object,
   * VariableDefinition)}
   */
  @Test
  @DisplayName("Test validateWithErrors(Object, VariableDefinition)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableValidationService.validateWithErrors(Object, VariableDefinition)"
  })
  void testValidateWithErrors() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    variableTypeMap.put("json", new BigDecimalVariableType());
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act
    List<ActivitiException> actualValidateWithErrorsResult =
        variableValidationService.validateWithErrors("Var", variableDefinition);

    // Assert
    assertEquals(1, actualValidateWithErrorsResult.size());
    ActivitiException getResult = actualValidateWithErrorsResult.get(0);
    assertEquals("class java.lang.String is not a numeric type", getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not a numeric type", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Test {@link VariableValidationService#validateWithErrors(Object, VariableDefinition)}.
   *
   * <p>Method under test: {@link VariableValidationService#validateWithErrors(Object,
   * VariableDefinition)}
   */
  @Test
  @DisplayName("Test validateWithErrors(Object, VariableDefinition)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableValidationService.validateWithErrors(Object, VariableDefinition)"
  })
  void testValidateWithErrors2() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType =
        new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    variableTypeMap.put("json", dateVariableType);
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act
    List<ActivitiException> actualValidateWithErrorsResult =
        variableValidationService.validateWithErrors("Var", variableDefinition);

    // Assert
    assertEquals(1, actualValidateWithErrorsResult.size());
    ActivitiException getResult = actualValidateWithErrorsResult.get(0);
    assertEquals(
        "class java.lang.String is not assignable from class java.lang.Object",
        getResult.getLocalizedMessage());
    assertEquals(
        "class java.lang.String is not assignable from class java.lang.Object",
        getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Test {@link VariableValidationService#validateWithErrors(Object, VariableDefinition)}.
   *
   * <p>Method under test: {@link VariableValidationService#validateWithErrors(Object,
   * VariableDefinition)}
   */
  @Test
  @DisplayName("Test validateWithErrors(Object, VariableDefinition)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableValidationService.validateWithErrors(Object, VariableDefinition)"
  })
  void testValidateWithErrors3() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType =
        new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    variableTypeMap.put("json", dateVariableType);
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act
    List<ActivitiException> actualValidateWithErrorsResult =
        variableValidationService.validateWithErrors(1, variableDefinition);

    // Assert
    assertEquals(1, actualValidateWithErrorsResult.size());
    ActivitiException getResult = actualValidateWithErrorsResult.get(0);
    assertEquals(
        "class java.lang.Integer is not assignable from class java.lang.Object",
        getResult.getLocalizedMessage());
    assertEquals(
        "class java.lang.Integer is not assignable from class java.lang.Object",
        getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Test {@link VariableValidationService#validateWithErrors(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>When {@code ${UU}}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validateWithErrors(Object,
   * VariableDefinition)}
   */
  @Test
  @DisplayName(
      "Test validateWithErrors(Object, VariableDefinition); given 'java.lang.Object'; when '${UU}'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableValidationService.validateWithErrors(Object, VariableDefinition)"
  })
  void testValidateWithErrors_givenJavaLangObject_whenUu_thenReturnEmpty() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    Class<Object> clazz = Object.class;
    DateVariableType dateVariableType =
        new DateVariableType(clazz, new DateFormatterProvider("2020-03-01"));
    variableTypeMap.put("json", dateVariableType);
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("Variable Definition");

    // Act and Assert
    assertTrue(variableValidationService.validateWithErrors("${UU}", variableDefinition).isEmpty());
  }

  /**
   * Test {@link VariableValidationService#validateWithErrors(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Given {@code json}.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validateWithErrors(Object,
   * VariableDefinition)}
   */
  @Test
  @DisplayName("Test validateWithErrors(Object, VariableDefinition); given 'json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableValidationService.validateWithErrors(Object, VariableDefinition)"
  })
  void testValidateWithErrors_givenJson() {
    // Arrange
    HashMap<String, VariableType> variableTypeMap = new HashMap<>();
    variableTypeMap.put("json", new BigDecimalVariableType());
    VariableValidationService variableValidationService =
        new VariableValidationService(variableTypeMap);

    VariableDefinition variableDefinition = new VariableDefinition("Type", "Value");
    variableDefinition.setType("json");

    // Act
    List<ActivitiException> actualValidateWithErrorsResult =
        variableValidationService.validateWithErrors("Var", variableDefinition);

    // Assert
    assertEquals(1, actualValidateWithErrorsResult.size());
    ActivitiException getResult = actualValidateWithErrorsResult.get(0);
    assertEquals("class java.lang.String is not a numeric type", getResult.getLocalizedMessage());
    assertEquals("class java.lang.String is not a numeric type", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }

  /**
   * Test {@link VariableValidationService#validateWithErrors(Object, VariableDefinition)}.
   *
   * <ul>
   *   <li>Then return first LocalizedMessage is {@code null has no type}.
   * </ul>
   *
   * <p>Method under test: {@link VariableValidationService#validateWithErrors(Object,
   * VariableDefinition)}
   */
  @Test
  @DisplayName(
      "Test validateWithErrors(Object, VariableDefinition); then return first LocalizedMessage is 'null has no type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableValidationService.validateWithErrors(Object, VariableDefinition)"
  })
  void testValidateWithErrors_thenReturnFirstLocalizedMessageIsNullHasNoType() {
    // Arrange and Act
    List<ActivitiException> actualValidateWithErrorsResult =
        variableValidationService.validateWithErrors("Var", new VariableDefinition());

    // Assert
    assertEquals(1, actualValidateWithErrorsResult.size());
    ActivitiException getResult = actualValidateWithErrorsResult.get(0);
    assertEquals("null has no type", getResult.getLocalizedMessage());
    assertEquals("null has no type", getResult.getMessage());
    assertNull(getResult.getCause());
    assertEquals(0, getResult.getSuppressed().length);
  }
}
