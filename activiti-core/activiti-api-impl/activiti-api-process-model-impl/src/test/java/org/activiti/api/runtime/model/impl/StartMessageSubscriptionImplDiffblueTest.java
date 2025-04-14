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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.runtime.model.impl.StartMessageSubscriptionImpl.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Builder.class})
@ExtendWith(SpringExtension.class)
class StartMessageSubscriptionImplDiffblueTest {
  @Autowired
  private Builder builder;

  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Builder#build()}
   *   <li>{@link Builder#withActivityId(String)}
   *   <li>{@link Builder#withConfiguration(String)}
   *   <li>{@link Builder#withCreated(Date)}
   *   <li>{@link Builder#withEventName(String)}
   *   <li>{@link Builder#withId(String)}
   *   <li>{@link Builder#withProcessDefinitionId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test Builder build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Builder.<init>()", "StartMessageSubscriptionImpl Builder.build()",
      "Builder Builder.withActivityId(String)", "Builder Builder.withConfiguration(String)",
      "Builder Builder.withCreated(Date)", "Builder Builder.withEventName(String)", "Builder Builder.withId(String)",
      "Builder Builder.withProcessDefinitionId(String)"})
  void testBuilderBuild() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    Date created = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    StartMessageSubscriptionImpl actualBuildResult = withConfigurationResult.withCreated(created)
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getActivityId());
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getProcessDefinitionId());
    assertEquals("Configuration", actualBuildResult.getConfiguration());
    assertEquals("Event Name", actualBuildResult.getEventName());
    assertSame(created, actualBuildResult.getCreated());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageSubscriptionImpl#StartMessageSubscriptionImpl()}
   *   <li>{@link StartMessageSubscriptionImpl#builderFrom(StartMessageSubscriptionImpl)}
   *   <li>{@link StartMessageSubscriptionImpl#toString()}
   *   <li>{@link StartMessageSubscriptionImpl#getActivityId()}
   *   <li>{@link StartMessageSubscriptionImpl#getConfiguration()}
   *   <li>{@link StartMessageSubscriptionImpl#getCreated()}
   *   <li>{@link StartMessageSubscriptionImpl#getEventName()}
   *   <li>{@link StartMessageSubscriptionImpl#getId()}
   *   <li>{@link StartMessageSubscriptionImpl#getProcessDefinitionId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartMessageSubscriptionImpl.<init>()",
      "Builder StartMessageSubscriptionImpl.builderFrom(StartMessageSubscriptionImpl)",
      "String StartMessageSubscriptionImpl.getActivityId()", "String StartMessageSubscriptionImpl.getConfiguration()",
      "Date StartMessageSubscriptionImpl.getCreated()", "String StartMessageSubscriptionImpl.getEventName()",
      "String StartMessageSubscriptionImpl.getId()", "String StartMessageSubscriptionImpl.getProcessDefinitionId()",
      "String StartMessageSubscriptionImpl.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    StartMessageSubscriptionImpl actualStartMessageSubscriptionImpl = new StartMessageSubscriptionImpl();
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl startMessageSubscriptionImpl = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();
    actualStartMessageSubscriptionImpl.builderFrom(startMessageSubscriptionImpl);
    String actualToStringResult = actualStartMessageSubscriptionImpl.toString();
    String actualActivityId = actualStartMessageSubscriptionImpl.getActivityId();
    String actualConfiguration = actualStartMessageSubscriptionImpl.getConfiguration();
    Date actualCreated = actualStartMessageSubscriptionImpl.getCreated();
    String actualEventName = actualStartMessageSubscriptionImpl.getEventName();
    String actualId = actualStartMessageSubscriptionImpl.getId();

    // Assert
    assertEquals("MessageEventSubscriptionImpl [id=null, eventName=null, processDefinitionId=null, configuration=null,"
        + " activityId=null, created=null]", actualToStringResult);
    assertNull(actualActivityId);
    assertNull(actualConfiguration);
    assertNull(actualEventName);
    assertNull(actualId);
    assertNull(actualStartMessageSubscriptionImpl.getProcessDefinitionId());
    assertNull(actualCreated);
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}, and {@link StartMessageSubscriptionImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageSubscriptionImpl#equals(Object)}
   *   <li>{@link StartMessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();
    Builder withConfigurationResult2 = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}, and {@link StartMessageSubscriptionImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessageSubscriptionImpl#equals(Object)}
   *   <li>{@link StartMessageSubscriptionImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("Activity Id")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();
    Builder withConfigurationResult2 = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration(null);
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();
    Builder withConfigurationResult2 = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();
    Builder withConfigurationResult2 = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName(null)
        .withId("42")
        .withProcessDefinitionId("42")
        .build();
    Builder withConfigurationResult2 = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("Id")
        .withProcessDefinitionId("42")
        .build();
    Builder withConfigurationResult2 = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("Process Definition Id")
        .build();
    Builder withConfigurationResult2 = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult2 = withConfigurationResult2
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Test {@link StartMessageSubscriptionImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageSubscriptionImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean StartMessageSubscriptionImpl.equals(Object)",
      "int StartMessageSubscriptionImpl.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Builder withConfigurationResult = StartMessageSubscriptionImpl.builder()
        .withActivityId("42")
        .withConfiguration("Configuration");
    StartMessageSubscriptionImpl buildResult = withConfigurationResult
        .withCreated(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withEventName("Event Name")
        .withId("42")
        .withProcessDefinitionId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to StartMessageSubscriptionImpl");
  }
}
