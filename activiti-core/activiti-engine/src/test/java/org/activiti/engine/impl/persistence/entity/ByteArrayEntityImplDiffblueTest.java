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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ByteArrayEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ByteArrayEntityImpl}
   *   <li>{@link ByteArrayEntityImpl#setBytes(byte[])}
   *   <li>{@link ByteArrayEntityImpl#setDeploymentId(String)}
   *   <li>{@link ByteArrayEntityImpl#setName(String)}
   *   <li>{@link ByteArrayEntityImpl#getBytes()}
   *   <li>{@link ByteArrayEntityImpl#getDeploymentId()}
   *   <li>{@link ByteArrayEntityImpl#getName()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ByteArrayEntityImpl.<init>()",
    "byte[] ByteArrayEntityImpl.getBytes()",
    "String ByteArrayEntityImpl.getDeploymentId()",
    "String ByteArrayEntityImpl.getName()",
    "void ByteArrayEntityImpl.setBytes(byte[])",
    "void ByteArrayEntityImpl.setDeploymentId(String)",
    "void ByteArrayEntityImpl.setName(String)"
  })
  public void testGettersAndSetters() throws UnsupportedEncodingException {
    // Arrange and Act
    ByteArrayEntityImpl actualByteArrayEntityImpl = new ByteArrayEntityImpl();
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");
    actualByteArrayEntityImpl.setBytes(bytes);
    actualByteArrayEntityImpl.setDeploymentId("42");
    actualByteArrayEntityImpl.setName("Name");
    byte[] actualBytes = actualByteArrayEntityImpl.getBytes();
    String actualDeploymentId = actualByteArrayEntityImpl.getDeploymentId();

    // Assert
    assertEquals("42", actualDeploymentId);
    assertEquals("Name", actualByteArrayEntityImpl.getName());
    assertNull(actualByteArrayEntityImpl.getId());
    assertEquals(1, actualByteArrayEntityImpl.getRevision());
    assertFalse(actualByteArrayEntityImpl.isDeleted());
    assertFalse(actualByteArrayEntityImpl.isInserted());
    assertFalse(actualByteArrayEntityImpl.isUpdated());
    assertSame(bytes, actualBytes);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualBytes);
  }

  /**
   * Test {@link ByteArrayEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code ByteArrayEntity[id=42, name=Name, size=8]}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ByteArrayEntityImpl.toString()"})
  public void testToString_thenReturnByteArrayEntityId42NameNameSize8()
      throws UnsupportedEncodingException {
    // Arrange
    ByteArrayEntityImpl byteArrayEntityImpl = new ByteArrayEntityImpl();
    byteArrayEntityImpl.setDeleted(true);
    byteArrayEntityImpl.setDeploymentId("42");
    byteArrayEntityImpl.setId("42");
    byteArrayEntityImpl.setInserted(true);
    byteArrayEntityImpl.setName("Name");
    byteArrayEntityImpl.setRevision(1);
    byteArrayEntityImpl.setUpdated(true);
    byteArrayEntityImpl.setBytes("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertEquals("ByteArrayEntity[id=42, name=Name, size=8]", byteArrayEntityImpl.toString());
  }

  /**
   * Test {@link ByteArrayEntityImpl#toString()}.
   *
   * <ul>
   *   <li>Then return {@code ByteArrayEntity[id=null, name=null, size=0]}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayEntityImpl#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ByteArrayEntityImpl.toString()"})
  public void testToString_thenReturnByteArrayEntityIdNullNameNullSize0() {
    // Arrange, Act and Assert
    assertEquals(
        "ByteArrayEntity[id=null, name=null, size=0]", new ByteArrayEntityImpl().toString());
  }
}
