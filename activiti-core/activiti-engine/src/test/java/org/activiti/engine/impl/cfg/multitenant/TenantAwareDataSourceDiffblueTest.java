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
package org.activiti.engine.impl.cfg.multitenant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TenantAwareDataSourceDiffblueTest {
  @InjectMocks private TenantAwareDataSource tenantAwareDataSource;

  @Mock private TenantInfoHolder tenantInfoHolder;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TenantAwareDataSource#TenantAwareDataSource(TenantInfoHolder)}
   *   <li>{@link TenantAwareDataSource#setDataSources(Map)}
   *   <li>{@link TenantAwareDataSource#getDataSources()}
   *   <li>{@link TenantAwareDataSource#getLoginTimeout()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TenantAwareDataSource.<init>(TenantInfoHolder)",
    "Map TenantAwareDataSource.getDataSources()",
    "int TenantAwareDataSource.getLoginTimeout()",
    "void TenantAwareDataSource.setDataSources(Map)"
  })
  public void testGettersAndSetters() throws SQLException {
    // Arrange and Act
    TenantAwareDataSource actualTenantAwareDataSource =
        new TenantAwareDataSource(new DummyTenantInfoHolder());
    HashMap<Object, DataSource> dataSources = new HashMap<>();
    actualTenantAwareDataSource.setDataSources(dataSources);
    Map<Object, DataSource> actualDataSources = actualTenantAwareDataSource.getDataSources();

    // Assert
    assertEquals(0, actualTenantAwareDataSource.getLoginTimeout());
    assertTrue(actualDataSources.isEmpty());
    assertSame(dataSources, actualDataSources);
  }

  /**
   * Test {@link TenantAwareDataSource#addDataSource(Object, DataSource)}.
   *
   * <p>Method under test: {@link TenantAwareDataSource#addDataSource(Object, DataSource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TenantAwareDataSource.addDataSource(Object, DataSource)"})
  public void testAddDataSource() {
    // Arrange
    TenantAwareDataSource tenantAwareDataSource =
        new TenantAwareDataSource(new DummyTenantInfoHolder());

    // Act
    tenantAwareDataSource.addDataSource(JSONObject.NULL, mock(DataSource.class));

    // Assert
    assertEquals(1, tenantAwareDataSource.getDataSources().size());
  }

  /**
   * Test {@link TenantAwareDataSource#getConnection(String, String)} with {@code String}, {@code
   * String}.
   *
   * <p>Method under test: {@link TenantAwareDataSource#getConnection(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.sql.Connection TenantAwareDataSource.getConnection(String, String)"})
  public void testGetConnectionWithStringString() throws SQLException {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new TenantAwareDataSource(new DummyTenantInfoHolder())
                .getConnection("janedoe", "iloveyou"));
  }

  /**
   * Test {@link TenantAwareDataSource#getConnection(String, String)} with {@code String}, {@code
   * String}.
   *
   * <ul>
   *   <li>Then calls {@link TenantInfoHolder#getCurrentTenantId()}.
   * </ul>
   *
   * <p>Method under test: {@link TenantAwareDataSource#getConnection(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.sql.Connection TenantAwareDataSource.getConnection(String, String)"})
  public void testGetConnectionWithStringString_thenCallsGetCurrentTenantId() throws SQLException {
    // Arrange
    when(tenantInfoHolder.getCurrentTenantId())
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> tenantAwareDataSource.getConnection("janedoe", "iloveyou"));
    verify(tenantInfoHolder).getCurrentTenantId();
  }

  /**
   * Test {@link TenantAwareDataSource#getConnection()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TenantAwareDataSource#getConnection()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.sql.Connection TenantAwareDataSource.getConnection()"})
  public void testGetConnection_thenThrowActivitiException() throws SQLException {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new TenantAwareDataSource(new DummyTenantInfoHolder()).getConnection());
  }

  /**
   * Test {@link TenantAwareDataSource#getCurrentDataSource()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TenantAwareDataSource#getCurrentDataSource()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"DataSource TenantAwareDataSource.getCurrentDataSource()"})
  public void testGetCurrentDataSource_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new TenantAwareDataSource(new DummyTenantInfoHolder()).getCurrentDataSource());
  }

  /**
   * Test {@link TenantAwareDataSource#getParentLogger()}.
   *
   * <p>Method under test: {@link TenantAwareDataSource#getParentLogger()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Logger TenantAwareDataSource.getParentLogger()"})
  public void testGetParentLogger() throws SQLFeatureNotSupportedException {
    // Arrange, Act and Assert
    assertSame(
        Logger.global, new TenantAwareDataSource(new DummyTenantInfoHolder()).getParentLogger());
  }

  /**
   * Test {@link TenantAwareDataSource#unwrap(Class)}.
   *
   * <p>Method under test: {@link TenantAwareDataSource#unwrap(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TenantAwareDataSource.unwrap(Class)"})
  public void testUnwrap() throws SQLException {
    // Arrange
    TenantAwareDataSource tenantAwareDataSource =
        new TenantAwareDataSource(new DummyTenantInfoHolder());
    Class<Object> iface = Object.class;

    // Act
    Object actualUnwrapResult = tenantAwareDataSource.unwrap(iface);

    // Assert
    assertSame(tenantAwareDataSource, actualUnwrapResult);
  }

  /**
   * Test {@link TenantAwareDataSource#isWrapperFor(Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link TenantAwareDataSource#isWrapperFor(Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TenantAwareDataSource.isWrapperFor(Class)"})
  public void testIsWrapperFor_whenJavaLangObject_thenReturnTrue() throws SQLException {
    // Arrange
    TenantAwareDataSource tenantAwareDataSource =
        new TenantAwareDataSource(new DummyTenantInfoHolder());
    Class<Object> iface = Object.class;

    // Act and Assert
    assertTrue(tenantAwareDataSource.isWrapperFor(iface));
  }

  /**
   * Test {@link TenantAwareDataSource#getLogWriter()}.
   *
   * <p>Method under test: {@link TenantAwareDataSource#getLogWriter()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"PrintWriter TenantAwareDataSource.getLogWriter()"})
  public void testGetLogWriter() throws SQLException {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new TenantAwareDataSource(new DummyTenantInfoHolder()).getLogWriter());
  }

  /**
   * Test {@link TenantAwareDataSource#setLogWriter(PrintWriter)}.
   *
   * <p>Method under test: {@link TenantAwareDataSource#setLogWriter(PrintWriter)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TenantAwareDataSource.setLogWriter(PrintWriter)"})
  public void testSetLogWriter() throws SQLException {
    // Arrange
    TenantAwareDataSource tenantAwareDataSource =
        new TenantAwareDataSource(new DummyTenantInfoHolder());

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> tenantAwareDataSource.setLogWriter(new PrintWriter(new StringWriter())));
  }

  /**
   * Test {@link TenantAwareDataSource#setLoginTimeout(int)}.
   *
   * <p>Method under test: {@link TenantAwareDataSource#setLoginTimeout(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TenantAwareDataSource.setLoginTimeout(int)"})
  public void testSetLoginTimeout() throws SQLException {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new TenantAwareDataSource(new DummyTenantInfoHolder()).setLoginTimeout(1));
  }
}
