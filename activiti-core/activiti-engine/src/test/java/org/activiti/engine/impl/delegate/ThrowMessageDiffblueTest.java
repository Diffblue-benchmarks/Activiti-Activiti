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
package org.activiti.engine.impl.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import java.util.Optional;
import org.activiti.engine.impl.delegate.ThrowMessage.IBuildStage;
import org.activiti.engine.impl.delegate.ThrowMessage.INameStage;
import org.activiti.engine.impl.delegate.ThrowMessage.ThrowMessagBuilder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ThrowMessageDiffblueTest {
  /**
   * Test {@link ThrowMessage#ThrowMessage()}.
   *
   * <p>Method under test: {@link ThrowMessage#ThrowMessage()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ThrowMessage.<init>()"})
  public void testNewThrowMessage() {
    // Arrange and Act
    ThrowMessage actualThrowMessage = new ThrowMessage();

    // Assert
    assertNull(actualThrowMessage.getName());
    Optional<String> businessKey = actualThrowMessage.getBusinessKey();
    assertFalse(businessKey.isPresent());
    assertSame(businessKey, actualThrowMessage.getCorrelationKey());
    assertSame(businessKey, actualThrowMessage.getPayload());
  }

  /**
   * Test {@link ThrowMessage#ThrowMessage(String)}.
   *
   * <p>Method under test: {@link ThrowMessage#ThrowMessage(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ThrowMessage.<init>(String)"})
  public void testNewThrowMessage2() {
    // Arrange and Act
    ThrowMessage actualThrowMessage = new ThrowMessage("Name");

    // Assert
    assertEquals("Name", actualThrowMessage.getName());
    Optional<String> businessKey = actualThrowMessage.getBusinessKey();
    assertFalse(businessKey.isPresent());
    assertSame(businessKey, actualThrowMessage.getCorrelationKey());
    assertSame(businessKey, actualThrowMessage.getPayload());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ThrowMessage#getBusinessKey()}
   *   <li>{@link ThrowMessage#getCorrelationKey()}
   *   <li>{@link ThrowMessage#getName()}
   *   <li>{@link ThrowMessage#getPayload()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ThrowMessage.getBusinessKey()",
    "Optional ThrowMessage.getCorrelationKey()",
    "String ThrowMessage.getName()",
    "Optional ThrowMessage.getPayload()"
  })
  public void testGettersAndSetters() {
    // Arrange
    ThrowMessage throwMessage = new ThrowMessage();

    // Act
    Optional<String> actualBusinessKey = throwMessage.getBusinessKey();
    Optional<String> actualCorrelationKey = throwMessage.getCorrelationKey();
    String actualName = throwMessage.getName();
    Optional<Map<String, Object>> actualPayload = throwMessage.getPayload();

    // Assert
    assertNull(actualName);
    assertFalse(actualBusinessKey.isPresent());
    assertSame(actualBusinessKey, actualCorrelationKey);
    assertSame(actualBusinessKey, actualPayload);
  }

  /**
   * Test {@link ThrowMessage#builder()}.
   *
   * <p>Method under test: {@link ThrowMessage#builder()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"INameStage ThrowMessage.builder()"})
  public void testBuilder() {
    // Arrange and Act
    INameStage actualBuilderResult = ThrowMessage.builder();
    IBuildStage actualNameResult = actualBuilderResult.name("Name");

    // Assert
    assertTrue(actualBuilderResult instanceof ThrowMessagBuilder);
    ThrowMessage throwMessage = ((ThrowMessagBuilder) actualBuilderResult).build();
    assertEquals("Name", throwMessage.getName());
    Optional<String> businessKey = throwMessage.getBusinessKey();
    assertFalse(businessKey.isPresent());
    assertSame(businessKey, throwMessage.getCorrelationKey());
    assertSame(businessKey, throwMessage.getPayload());
    assertSame(actualBuilderResult, actualNameResult);
  }
}
