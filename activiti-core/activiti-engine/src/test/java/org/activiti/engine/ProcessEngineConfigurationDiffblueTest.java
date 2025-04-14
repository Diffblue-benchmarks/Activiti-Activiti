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
package org.activiti.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import groovy.lang.GroovyClassLoader;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessEngineConfigurationDiffblueTest {
  /**
   * Test {@link ProcessEngineConfiguration#getProcessEngineName()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getProcessEngineName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getProcessEngineName()"})
  public void testGetProcessEngineName() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngines.NAME_DEFAULT, (new JtaProcessEngineConfiguration()).getProcessEngineName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setProcessEngineName(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setProcessEngineName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setProcessEngineName(String)"})
  public void testSetProcessEngineName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetProcessEngineNameResult = jtaProcessEngineConfiguration
        .setProcessEngineName("Process Engine Name");

    // Assert
    assertEquals("Process Engine Name", jtaProcessEngineConfiguration.getProcessEngineName());
    assertSame(jtaProcessEngineConfiguration, actualSetProcessEngineNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getIdBlockSize()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getIdBlockSize()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getIdBlockSize()"})
  public void testGetIdBlockSize() {
    // Arrange, Act and Assert
    assertEquals(2500, (new JtaProcessEngineConfiguration()).getIdBlockSize());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setIdBlockSize(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setIdBlockSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setIdBlockSize(int)"})
  public void testSetIdBlockSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetIdBlockSizeResult = jtaProcessEngineConfiguration.setIdBlockSize(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getIdBlockSize());
    assertSame(jtaProcessEngineConfiguration, actualSetIdBlockSizeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getHistory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getHistory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getHistory()"})
  public void testGetHistory() {
    // Arrange, Act and Assert
    assertEquals("audit", (new JtaProcessEngineConfiguration()).getHistory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setHistory(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setHistory(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setHistory(String)"})
  public void testSetHistory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetHistoryResult = jtaProcessEngineConfiguration.setHistory("History");

    // Assert
    assertEquals("History", jtaProcessEngineConfiguration.getHistory());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerHost()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerHost()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailServerHost()"})
  public void testGetMailServerHost() {
    // Arrange, Act and Assert
    assertEquals("localhost", (new JtaProcessEngineConfiguration()).getMailServerHost());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerHost(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerHost(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerHost(String)"})
  public void testSetMailServerHost() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailServerHost("localhost"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUsername()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUsername()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailServerUsername()"})
  public void testGetMailServerUsername() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailServerUsername());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUsername(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerUsername(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerUsername(String)"})
  public void testSetMailServerUsername() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerUsernameResult = jtaProcessEngineConfiguration
        .setMailServerUsername("janedoe");

    // Assert
    assertEquals("janedoe", jtaProcessEngineConfiguration.getMailServerUsername());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUsernameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerPassword()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPassword()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailServerPassword()"})
  public void testGetMailServerPassword() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailServerPassword());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerPassword(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerPassword(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerPassword(String)"})
  public void testSetMailServerPassword() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerPasswordResult = jtaProcessEngineConfiguration
        .setMailServerPassword("iloveyou");

    // Assert
    assertEquals("iloveyou", jtaProcessEngineConfiguration.getMailServerPassword());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPasswordResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionJndi()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionJndi()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailSessionJndi()"})
  public void testGetMailSessionJndi() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailSessionJndi());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionJndi(String)} with {@code String}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionJndi(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailSessionJndi(String)"})
  public void testGetMailSessionJndiWithString() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailSessionJndi("42"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailSessionJndi(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailSessionJndi(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailSessionJndi(String)"})
  public void testSetMailSessionJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailSessionJndiResult = jtaProcessEngineConfiguration
        .setMailSessionJndi("Mail Session Jndi");

    // Assert
    assertEquals("Mail Session Jndi", jtaProcessEngineConfiguration.getMailSessionJndi());
    assertSame(jtaProcessEngineConfiguration, actualSetMailSessionJndiResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionsJndi()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionsJndi()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ProcessEngineConfiguration.getMailSessionsJndi()"})
  public void testGetMailSessionsJndi() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).getMailSessionsJndi().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailSessionsJndi(Map)"})
  public void testSetMailSessionsJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailSessionsJndi(new HashMap<>()));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerPort()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPort()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getMailServerPort()"})
  public void testGetMailServerPort() {
    // Arrange, Act and Assert
    assertEquals(25, (new JtaProcessEngineConfiguration()).getMailServerPort());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerPort(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerPort(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerPort(int)"})
  public void testSetMailServerPort() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerPortResult = jtaProcessEngineConfiguration.setMailServerPort(8080);

    // Assert
    assertEquals(8080, jtaProcessEngineConfiguration.getMailServerPort());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPortResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseSSL()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.getMailServerUseSSL()"})
  public void testGetMailServerUseSSL_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).getMailServerUseSSL());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseSSL()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.getMailServerUseSSL()"})
  public void testGetMailServerUseSSL_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerUseSSL(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerUseSSL(boolean)"})
  public void testSetMailServerUseSSL() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerUseSSLResult = jtaProcessEngineConfiguration
        .setMailServerUseSSL(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseSSLResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseTLS()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.getMailServerUseTLS()"})
  public void testGetMailServerUseTLS_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).getMailServerUseTLS());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseTLS()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.getMailServerUseTLS()"})
  public void testGetMailServerUseTLS_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerUseTLS(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerUseTLS(boolean)"})
  public void testSetMailServerUseTLS() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerUseTLSResult = jtaProcessEngineConfiguration
        .setMailServerUseTLS(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseTLSResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getMailServerDefaultFrom()"})
  public void testGetMailServerDefaultFrom() {
    // Arrange, Act and Assert
    assertEquals("activiti@localhost", (new JtaProcessEngineConfiguration()).getMailServerDefaultFrom());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerDefaultFrom(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerDefaultFrom(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServerDefaultFrom(String)"})
  public void testSetMailServerDefaultFrom() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerDefaultFromResult = jtaProcessEngineConfiguration
        .setMailServerDefaultFrom("jane.doe@example.org");

    // Assert
    assertEquals("jane.doe@example.org", jtaProcessEngineConfiguration.getMailServerDefaultFrom());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerDefaultFromResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServer(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServer(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.cfg.MailServerInfo ProcessEngineConfiguration.getMailServer(String)"})
  public void testGetMailServer() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailServer("42"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map ProcessEngineConfiguration.getMailServers()"})
  public void testGetMailServers() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).getMailServers().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServers(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServers(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setMailServers(Map)"})
  public void testSetMailServers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailServers(new HashMap<>()));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseType()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseType()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseType()"})
  public void testGetDatabaseType() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseType());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseType(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDatabaseType(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseType(String)"})
  public void testSetDatabaseType() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseTypeResult = jtaProcessEngineConfiguration
        .setDatabaseType("Database Type");

    // Assert
    assertEquals("Database Type", jtaProcessEngineConfiguration.getDatabaseType());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseTypeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseSchemaUpdate()"})
  public void testGetDatabaseSchemaUpdate() {
    // Arrange and Act
    String actualDatabaseSchemaUpdate = (new JtaProcessEngineConfiguration()).getDatabaseSchemaUpdate();

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualDatabaseSchemaUpdate);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseSchemaUpdate(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDatabaseSchemaUpdate(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseSchemaUpdate(String)"})
  public void testSetDatabaseSchemaUpdate() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseSchemaUpdateResult = jtaProcessEngineConfiguration
        .setDatabaseSchemaUpdate("2020-03-01");

    // Assert
    assertEquals("2020-03-01", jtaProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseSchemaUpdateResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDataSource()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDataSource()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DataSource ProcessEngineConfiguration.getDataSource()"})
  public void testGetDataSource() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDataSource());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDataSource(DataSource)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDataSource(DataSource)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDataSource(DataSource)"})
  public void testSetDataSource() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DataSource dataSource = mock(DataSource.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDataSource(dataSource));
    assertSame(dataSource, jtaProcessEngineConfiguration.getDataSource());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcDriver()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcDriver()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcDriver()"})
  public void testGetJdbcDriver() {
    // Arrange, Act and Assert
    assertEquals("org.h2.Driver", (new JtaProcessEngineConfiguration()).getJdbcDriver());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcDriver(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcDriver(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcDriver(String)"})
  public void testSetJdbcDriver() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcDriverResult = jtaProcessEngineConfiguration.setJdbcDriver("Jdbc Driver");

    // Assert
    assertEquals("Jdbc Driver", jtaProcessEngineConfiguration.getJdbcDriver());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDriverResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcUrl()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUrl()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcUrl()"})
  public void testGetJdbcUrl() {
    // Arrange, Act and Assert
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", (new JtaProcessEngineConfiguration()).getJdbcUrl());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcUrl(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcUrl(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcUrl(String)"})
  public void testSetJdbcUrl() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcUrlResult = jtaProcessEngineConfiguration
        .setJdbcUrl("https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", jtaProcessEngineConfiguration.getJdbcUrl());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcUrlResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcUsername()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUsername()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcUsername()"})
  public void testGetJdbcUsername() {
    // Arrange, Act and Assert
    assertEquals("sa", (new JtaProcessEngineConfiguration()).getJdbcUsername());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcUsername(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcUsername(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcUsername(String)"})
  public void testSetJdbcUsername() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcUsernameResult = jtaProcessEngineConfiguration.setJdbcUsername("janedoe");

    // Assert
    assertEquals("janedoe", jtaProcessEngineConfiguration.getJdbcUsername());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcUsernameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPassword()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPassword()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcPassword()"})
  public void testGetJdbcPassword() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, (new JtaProcessEngineConfiguration()).getJdbcPassword());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPassword(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcPassword(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcPassword(String)"})
  public void testSetJdbcPassword() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPasswordResult = jtaProcessEngineConfiguration.setJdbcPassword("iloveyou");

    // Assert
    assertEquals("iloveyou", jtaProcessEngineConfiguration.getJdbcPassword());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPasswordResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isTransactionsExternallyManaged()"})
  public void testIsTransactionsExternallyManaged() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isTransactionsExternallyManaged());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setTransactionsExternallyManaged(boolean)"})
  public void testSetTransactionsExternallyManaged() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTransactionsExternallyManaged(true));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getHistoryLevel()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getHistoryLevel()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoryLevel ProcessEngineConfiguration.getHistoryLevel()"})
  public void testGetHistoryLevel() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setHistoryLevel(HistoryLevel)"})
  public void testSetHistoryLevel() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetHistoryLevelResult = jtaProcessEngineConfiguration
        .setHistoryLevel(HistoryLevel.NONE);

    // Assert
    assertEquals(HistoryLevel.NONE, jtaProcessEngineConfiguration.getHistoryLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryLevelResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isDbHistoryUsed()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isDbHistoryUsed()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isDbHistoryUsed()"})
  public void testIsDbHistoryUsed() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isDbHistoryUsed());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDbHistoryUsed(boolean)"})
  public void testSetDbHistoryUsed() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDbHistoryUsed(true));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcMaxActiveConnections()"})
  public void testGetJdbcMaxActiveConnections() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxActiveConnections());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcMaxActiveConnections(int)"})
  public void testSetJdbcMaxActiveConnections() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxActiveConnectionsResult = jtaProcessEngineConfiguration
        .setJdbcMaxActiveConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxActiveConnectionsResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcMaxIdleConnections()"})
  public void testGetJdbcMaxIdleConnections() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxIdleConnections());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcMaxIdleConnections(int)"})
  public void testSetJdbcMaxIdleConnections() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxIdleConnectionsResult = jtaProcessEngineConfiguration
        .setJdbcMaxIdleConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxIdleConnectionsResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcMaxCheckoutTime()"})
  public void testGetJdbcMaxCheckoutTime() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxCheckoutTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcMaxCheckoutTime(int)"})
  public void testSetJdbcMaxCheckoutTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxCheckoutTimeResult = jtaProcessEngineConfiguration
        .setJdbcMaxCheckoutTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxCheckoutTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcMaxWaitTime()"})
  public void testGetJdbcMaxWaitTime() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcMaxWaitTime(int)"})
  public void testSetJdbcMaxWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxWaitTimeResult = jtaProcessEngineConfiguration.setJdbcMaxWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJdbcPingEnabled()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJdbcPingEnabled()"})
  public void testIsJdbcPingEnabled_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJdbcPingEnabled());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJdbcPingEnabled()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJdbcPingEnabled()"})
  public void testIsJdbcPingEnabled_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJdbcPingEnabled(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcPingEnabled(boolean)"})
  public void testSetJdbcPingEnabled() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPingEnabledResult = jtaProcessEngineConfiguration.setJdbcPingEnabled(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingEnabledResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPingQuery()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPingQuery()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJdbcPingQuery()"})
  public void testGetJdbcPingQuery() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJdbcPingQuery());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingQuery(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcPingQuery(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcPingQuery(String)"})
  public void testSetJdbcPingQuery() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPingQueryResult = jtaProcessEngineConfiguration
        .setJdbcPingQuery("Jdbc Ping Query");

    // Assert
    assertEquals("Jdbc Ping Query", jtaProcessEngineConfiguration.getJdbcPingQuery());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingQueryResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor()"})
  public void testGetJdbcPingConnectionNotUsedFor() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcPingConnectionNotUsedFor());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcPingConnectionNotUsedFor(int)"})
  public void testSetJdbcPingConnectionNotUsedFor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPingConnectionNotUsedForResult = jtaProcessEngineConfiguration
        .setJdbcPingConnectionNotUsedFor(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingConnectionNotUsedForResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel()"})
  public void testGetJdbcDefaultTransactionIsolationLevel() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcDefaultTransactionIsolationLevel());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfiguration ProcessEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(int)"})
  public void testSetJdbcDefaultTransactionIsolationLevel() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcDefaultTransactionIsolationLevelResult = jtaProcessEngineConfiguration
        .setJdbcDefaultTransactionIsolationLevel(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDefaultTransactionIsolationLevelResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isAsyncExecutorActivate()"})
  public void testIsAsyncExecutorActivate_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isAsyncExecutorActivate());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isAsyncExecutorActivate()"})
  public void testIsAsyncExecutorActivate_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorActivate(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setAsyncExecutorActivate(boolean)"})
  public void testSetAsyncExecutorActivate() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorActivateResult = jtaProcessEngineConfiguration
        .setAsyncExecutorActivate(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorActivateResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getClassLoader()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getClassLoader()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ClassLoader ProcessEngineConfiguration.getClassLoader()"})
  public void testGetClassLoader() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getClassLoader());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setClassLoader(ClassLoader)"})
  public void testSetClassLoader() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    GroovyClassLoader classLoader = new GroovyClassLoader();

    // Act
    ProcessEngineConfiguration actualSetClassLoaderResult = jtaProcessEngineConfiguration.setClassLoader(classLoader);

    // Assert
    assertSame(classLoader, jtaProcessEngineConfiguration.getClassLoader());
    assertSame(jtaProcessEngineConfiguration, actualSetClassLoaderResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isUseClassForNameClassLoading()"})
  public void testIsUseClassForNameClassLoading() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isUseClassForNameClassLoading());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setUseClassForNameClassLoading(boolean)"})
  public void testSetUseClassForNameClassLoading() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setUseClassForNameClassLoading(true));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ProcessEngineConfiguration.getJpaEntityManagerFactory()"})
  public void testGetJpaEntityManagerFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJpaEntityManagerFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJpaEntityManagerFactory(Object)"})
  public void testSetJpaEntityManagerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    Object object = JSONObject.NULL;

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJpaEntityManagerFactory(object));
    assertSame(object, jtaProcessEngineConfiguration.getJpaEntityManagerFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaHandleTransaction()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJpaHandleTransaction()"})
  public void testIsJpaHandleTransaction_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJpaHandleTransaction());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaHandleTransaction()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJpaHandleTransaction()"})
  public void testIsJpaHandleTransaction_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJpaHandleTransaction(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJpaHandleTransaction(boolean)"})
  public void testSetJpaHandleTransaction() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJpaHandleTransactionResult = jtaProcessEngineConfiguration
        .setJpaHandleTransaction(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaHandleTransactionResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJpaCloseEntityManager()"})
  public void testIsJpaCloseEntityManager_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJpaCloseEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isJpaCloseEntityManager()"})
  public void testIsJpaCloseEntityManager_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJpaCloseEntityManager(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJpaCloseEntityManager(boolean)"})
  public void testSetJpaCloseEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJpaCloseEntityManagerResult = jtaProcessEngineConfiguration
        .setJpaCloseEntityManager(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaCloseEntityManagerResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getJpaPersistenceUnitName()"})
  public void testGetJpaPersistenceUnitName() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJpaPersistenceUnitName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaPersistenceUnitName(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJpaPersistenceUnitName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setJpaPersistenceUnitName(String)"})
  public void testSetJpaPersistenceUnitName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJpaPersistenceUnitNameResult = jtaProcessEngineConfiguration
        .setJpaPersistenceUnitName("Jpa Persistence Unit Name");

    // Assert
    assertEquals("Jpa Persistence Unit Name", jtaProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaPersistenceUnitNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDataSourceJndiName()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDataSourceJndiName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDataSourceJndiName()"})
  public void testGetDataSourceJndiName() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDataSourceJndiName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDataSourceJndiName(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDataSourceJndiName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDataSourceJndiName(String)"})
  public void testSetDataSourceJndiName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDataSourceJndiNameResult = jtaProcessEngineConfiguration
        .setDataSourceJndiName("Data Source Jndi Name");

    // Assert
    assertEquals("Data Source Jndi Name", jtaProcessEngineConfiguration.getDataSourceJndiName());
    assertSame(jtaProcessEngineConfiguration, actualSetDataSourceJndiNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDefaultCamelContext()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDefaultCamelContext()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDefaultCamelContext()"})
  public void testGetDefaultCamelContext() {
    // Arrange, Act and Assert
    assertEquals("camelContext", (new JtaProcessEngineConfiguration()).getDefaultCamelContext());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDefaultCamelContext(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDefaultCamelContext(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDefaultCamelContext(String)"})
  public void testSetDefaultCamelContext() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDefaultCamelContextResult = jtaProcessEngineConfiguration
        .setDefaultCamelContext("Default Camel Context");

    // Assert
    assertEquals("Default Camel Context", jtaProcessEngineConfiguration.getDefaultCamelContext());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultCamelContextResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfiguration ProcessEngineConfiguration.setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)"})
  public void testSetProcessEngineLifecycleListener() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessEngineLifecycleListener(processEngineLifecycleListener));
    assertSame(processEngineLifecycleListener, jtaProcessEngineConfiguration.getProcessEngineLifecycleListener());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineLifecycleListener ProcessEngineConfiguration.getProcessEngineLifecycleListener()"})
  public void testGetProcessEngineLifecycleListener() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessEngineLifecycleListener());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseTablePrefix()"})
  public void testGetDatabaseTablePrefix() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID,
        (new JtaProcessEngineConfiguration()).getDatabaseTablePrefix());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseTablePrefix(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDatabaseTablePrefix(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseTablePrefix(String)"})
  public void testSetDatabaseTablePrefix() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseTablePrefixResult = jtaProcessEngineConfiguration
        .setDatabaseTablePrefix("Database Table Prefix");

    // Assert
    assertEquals("Database Table Prefix", jtaProcessEngineConfiguration.getDatabaseTablePrefix());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseTablePrefixResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setTablePrefixIsSchema(boolean)"})
  public void testSetTablePrefixIsSchema() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetTablePrefixIsSchemaResult = jtaProcessEngineConfiguration
        .setTablePrefixIsSchema(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetTablePrefixIsSchemaResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isTablePrefixIsSchema()"})
  public void testIsTablePrefixIsSchema_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isTablePrefixIsSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isTablePrefixIsSchema()"})
  public void testIsTablePrefixIsSchema_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTablePrefixIsSchema(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter()"})
  public void testGetDatabaseWildcardEscapeCharacter() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseWildcardEscapeCharacter());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseWildcardEscapeCharacter(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDatabaseWildcardEscapeCharacter(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseWildcardEscapeCharacter(String)"})
  public void testSetDatabaseWildcardEscapeCharacter() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseWildcardEscapeCharacterResult = jtaProcessEngineConfiguration
        .setDatabaseWildcardEscapeCharacter("Database Wildcard Escape Character");

    // Assert
    assertEquals("Database Wildcard Escape Character",
        jtaProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseWildcardEscapeCharacterResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseCatalog()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseCatalog()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseCatalog()"})
  public void testGetDatabaseCatalog() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, (new JtaProcessEngineConfiguration()).getDatabaseCatalog());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseCatalog(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDatabaseCatalog(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseCatalog(String)"})
  public void testSetDatabaseCatalog() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseCatalogResult = jtaProcessEngineConfiguration
        .setDatabaseCatalog("Database Catalog");

    // Assert
    assertEquals("Database Catalog", jtaProcessEngineConfiguration.getDatabaseCatalog());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseCatalogResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseSchema()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseSchema()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getDatabaseSchema()"})
  public void testGetDatabaseSchema() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseSchema(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDatabaseSchema(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDatabaseSchema(String)"})
  public void testSetDatabaseSchema() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDatabaseSchemaResult = jtaProcessEngineConfiguration
        .setDatabaseSchema("Database Schema");

    // Assert
    assertEquals("Database Schema", jtaProcessEngineConfiguration.getDatabaseSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseSchemaResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getXmlEncoding()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getXmlEncoding()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessEngineConfiguration.getXmlEncoding()"})
  public void testGetXmlEncoding() {
    // Arrange, Act and Assert
    assertEquals("UTF-8", (new JtaProcessEngineConfiguration()).getXmlEncoding());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setXmlEncoding(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setXmlEncoding(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setXmlEncoding(String)"})
  public void testSetXmlEncoding() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setXmlEncoding("UTF-8"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getClock()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getClock()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Clock ProcessEngineConfiguration.getClock()"})
  public void testGetClock() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getClock());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setClock(Clock)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setClock(Clock)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setClock(Clock)"})
  public void testSetClock() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(clock));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ProcessEngineConfiguration.getAsyncExecutor()"})
  public void testGetAsyncExecutor() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAsyncExecutor());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setAsyncExecutor(AsyncExecutor)"})
  public void testSetAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorResult = jtaProcessEngineConfiguration
        .setAsyncExecutor(asyncExecutor);

    // Assert
    assertSame(asyncExecutor, jtaProcessEngineConfiguration.getAsyncExecutor());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getLockTimeAsyncJobWaitTime()"})
  public void testGetLockTimeAsyncJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(60, (new JtaProcessEngineConfiguration()).getLockTimeAsyncJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setLockTimeAsyncJobWaitTime(int)"})
  public void testSetLockTimeAsyncJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetLockTimeAsyncJobWaitTimeResult = jtaProcessEngineConfiguration
        .setLockTimeAsyncJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetLockTimeAsyncJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getDefaultFailedJobWaitTime()"})
  public void testGetDefaultFailedJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10, (new JtaProcessEngineConfiguration()).getDefaultFailedJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setDefaultFailedJobWaitTime(int)"})
  public void testSetDefaultFailedJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDefaultFailedJobWaitTimeResult = jtaProcessEngineConfiguration
        .setDefaultFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultFailedJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ProcessEngineConfiguration.getAsyncFailedJobWaitTime()"})
  public void testGetAsyncFailedJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10, (new JtaProcessEngineConfiguration()).getAsyncFailedJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setAsyncFailedJobWaitTime(int)"})
  public void testSetAsyncFailedJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetAsyncFailedJobWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncFailedJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isEnableProcessDefinitionInfoCache()"})
  public void testIsEnableProcessDefinitionInfoCache_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableProcessDefinitionInfoCache());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isEnableProcessDefinitionInfoCache()"})
  public void testIsEnableProcessDefinitionInfoCache_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableProcessDefinitionInfoCache(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "ProcessEngineConfiguration ProcessEngineConfiguration.setEnableProcessDefinitionInfoCache(boolean)"})
  public void testSetEnableProcessDefinitionInfoCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetEnableProcessDefinitionInfoCacheResult = jtaProcessEngineConfiguration
        .setEnableProcessDefinitionInfoCache(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableProcessDefinitionInfoCacheResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfiguration ProcessEngineConfiguration.setCopyVariablesToLocalForTasks(boolean)"})
  public void testSetCopyVariablesToLocalForTasks() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetCopyVariablesToLocalForTasksResult = jtaProcessEngineConfiguration
        .setCopyVariablesToLocalForTasks(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertSame(jtaProcessEngineConfiguration, actualSetCopyVariablesToLocalForTasksResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isCopyVariablesToLocalForTasks()"})
  public void testIsCopyVariablesToLocalForTasks_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isCopyVariablesToLocalForTasks());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessEngineConfiguration.isCopyVariablesToLocalForTasks()"})
  public void testIsCopyVariablesToLocalForTasks_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCopyVariablesToLocalForTasks(true);

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setEngineAgendaFactory(ActivitiEngineAgendaFactory)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setEngineAgendaFactory(ActivitiEngineAgendaFactory)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessEngineConfiguration.setEngineAgendaFactory(ActivitiEngineAgendaFactory)"})
  public void testSetEngineAgendaFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);

    // Act
    jtaProcessEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Assert
    assertSame(engineAgendaFactory, jtaProcessEngineConfiguration.getEngineAgendaFactory());
  }
}
