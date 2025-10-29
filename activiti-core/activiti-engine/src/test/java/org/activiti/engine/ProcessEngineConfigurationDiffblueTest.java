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
import groovy.lang.GroovyClassLoader;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.cfg.MailServerInfo;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessEngineConfigurationDiffblueTest {
  @InjectMocks
  private JtaProcessEngineConfiguration jtaProcessEngineConfiguration;

  /**
   * Method under test: {@link ProcessEngineConfiguration#getProcessEngineName()}
   */
  @Test
  public void testGetProcessEngineName() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngines.NAME_DEFAULT, (new JtaProcessEngineConfiguration()).getProcessEngineName());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getProcessEngineName()}
   */
  @Test
  public void testGetProcessEngineName2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngines.NAME_DEFAULT, jtaProcessEngineConfiguration.getProcessEngineName());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setProcessEngineName(String)}
   */
  @Test
  public void testSetProcessEngineName() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetProcessEngineNameResult = jtaProcessEngineConfiguration
        .setProcessEngineName("Process Engine Name");

    // Assert
    assertEquals("Process Engine Name", jtaProcessEngineConfiguration.getProcessEngineName());
    assertSame(jtaProcessEngineConfiguration, actualSetProcessEngineNameResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getIdBlockSize()}
   */
  @Test
  public void testGetIdBlockSize() {
    // Arrange, Act and Assert
    assertEquals(2500, (new JtaProcessEngineConfiguration()).getIdBlockSize());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getIdBlockSize()}
   */
  @Test
  public void testGetIdBlockSize2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(2500, jtaProcessEngineConfiguration.getIdBlockSize());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setIdBlockSize(int)}
   */
  @Test
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
   * Method under test: {@link ProcessEngineConfiguration#setIdBlockSize(int)}
   */
  @Test
  public void testSetIdBlockSize2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetIdBlockSizeResult = jtaProcessEngineConfiguration.setIdBlockSize(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getIdBlockSize());
    assertSame(jtaProcessEngineConfiguration, actualSetIdBlockSizeResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getHistory()}
   */
  @Test
  public void testGetHistory() {
    // Arrange, Act and Assert
    assertEquals("audit", (new JtaProcessEngineConfiguration()).getHistory());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getHistory()}
   */
  @Test
  public void testGetHistory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("audit", jtaProcessEngineConfiguration.getHistory());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setHistory(String)}
   */
  @Test
  public void testSetHistory() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetHistoryResult = jtaProcessEngineConfiguration.setHistory("History");

    // Assert
    assertEquals("History", jtaProcessEngineConfiguration.getHistory());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerHost()}
   */
  @Test
  public void testGetMailServerHost() {
    // Arrange, Act and Assert
    assertEquals("localhost", (new JtaProcessEngineConfiguration()).getMailServerHost());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerHost()}
   */
  @Test
  public void testGetMailServerHost2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("localhost", jtaProcessEngineConfiguration.getMailServerHost());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerHost(String)}
   */
  @Test
  public void testSetMailServerHost() {
    // Arrange, Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailServerHost("localhost"));
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUsername()}
   */
  @Test
  public void testGetMailServerUsername() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailServerUsername());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUsername()}
   */
  @Test
  public void testGetMailServerUsername2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailServerUsername());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUsername(String)}
   */
  @Test
  public void testSetMailServerUsername() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetMailServerUsernameResult = jtaProcessEngineConfiguration
        .setMailServerUsername("janedoe");

    // Assert
    assertEquals("janedoe", jtaProcessEngineConfiguration.getMailServerUsername());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUsernameResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPassword()}
   */
  @Test
  public void testGetMailServerPassword() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailServerPassword());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPassword()}
   */
  @Test
  public void testGetMailServerPassword2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailServerPassword());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerPassword(String)}
   */
  @Test
  public void testSetMailServerPassword() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetMailServerPasswordResult = jtaProcessEngineConfiguration
        .setMailServerPassword("iloveyou");

    // Assert
    assertEquals("iloveyou", jtaProcessEngineConfiguration.getMailServerPassword());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPasswordResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionJndi()}
   */
  @Test
  public void testGetMailSessionJndi() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailSessionJndi());
    assertNull(jtaProcessEngineConfiguration.getMailSessionJndi("42"));
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionJndi()}
   */
  @Test
  public void testGetMailSessionJndi2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailSessionJndi());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailSessionJndi(String)}
   */
  @Test
  public void testSetMailSessionJndi() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetMailSessionJndiResult = jtaProcessEngineConfiguration
        .setMailSessionJndi("Mail Session Jndi");

    // Assert
    assertEquals("Mail Session Jndi", jtaProcessEngineConfiguration.getMailSessionJndi());
    assertSame(jtaProcessEngineConfiguration, actualSetMailSessionJndiResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionsJndi()}
   */
  @Test
  public void testGetMailSessionsJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    Map<String, String> actualMailSessionsJndi = jtaProcessEngineConfiguration.getMailSessionsJndi();

    // Assert
    assertTrue(actualMailSessionsJndi.isEmpty());
    assertSame(jtaProcessEngineConfiguration.mailSessionsJndi, actualMailSessionsJndi);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionsJndi()}
   */
  @Test
  public void testGetMailSessionsJndi2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    Map<String, String> actualMailSessionsJndi = jtaProcessEngineConfiguration.getMailSessionsJndi();

    // Assert
    assertTrue(actualMailSessionsJndi.isEmpty());
    assertSame(jtaProcessEngineConfiguration.mailSessionsJndi, actualMailSessionsJndi);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}
   */
  @Test
  public void testSetMailSessionsJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailSessionsJndi(new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}
   */
  @Test
  public void testSetMailSessionsJndi2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailSessionsJndi(new HashMap<>()));
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPort()}
   */
  @Test
  public void testGetMailServerPort() {
    // Arrange, Act and Assert
    assertEquals(25, (new JtaProcessEngineConfiguration()).getMailServerPort());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPort()}
   */
  @Test
  public void testGetMailServerPort2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(25, jtaProcessEngineConfiguration.getMailServerPort());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setMailServerPort(int)}
   */
  @Test
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
   * Method under test: {@link ProcessEngineConfiguration#setMailServerPort(int)}
   */
  @Test
  public void testSetMailServerPort2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetMailServerPortResult = jtaProcessEngineConfiguration.setMailServerPort(8080);

    // Assert
    assertEquals(8080, jtaProcessEngineConfiguration.getMailServerPort());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPortResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  public void testGetMailServerUseSSL() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).getMailServerUseSSL());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  public void testGetMailServerUseSSL2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.getMailServerUseSSL());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  public void testGetMailServerUseSSL3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerUseSSL(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}
   */
  @Test
  public void testSetMailServerUseSSL2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetMailServerUseSSLResult = jtaProcessEngineConfiguration
        .setMailServerUseSSL(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseSSLResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  public void testGetMailServerUseTLS() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).getMailServerUseTLS());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  public void testGetMailServerUseTLS2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.getMailServerUseTLS());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  public void testGetMailServerUseTLS3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerUseTLS(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}
   */
  @Test
  public void testSetMailServerUseTLS2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetMailServerUseTLSResult = jtaProcessEngineConfiguration
        .setMailServerUseTLS(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseTLSResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}
   */
  @Test
  public void testGetMailServerDefaultFrom() {
    // Arrange, Act and Assert
    assertEquals("activiti@localhost", (new JtaProcessEngineConfiguration()).getMailServerDefaultFrom());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}
   */
  @Test
  public void testGetMailServerDefaultFrom2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("activiti@localhost", jtaProcessEngineConfiguration.getMailServerDefaultFrom());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerDefaultFrom(String)}
   */
  @Test
  public void testSetMailServerDefaultFrom() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetMailServerDefaultFromResult = jtaProcessEngineConfiguration
        .setMailServerDefaultFrom("jane.doe@example.org");

    // Assert
    assertEquals("jane.doe@example.org", jtaProcessEngineConfiguration.getMailServerDefaultFrom());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerDefaultFromResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServer(String)}
   */
  @Test
  public void testGetMailServer() {
    // Arrange, Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailServer("42"));
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServers()}
   */
  @Test
  public void testGetMailServers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    Map<String, MailServerInfo> actualMailServers = jtaProcessEngineConfiguration.getMailServers();

    // Assert
    assertTrue(actualMailServers.isEmpty());
    assertSame(jtaProcessEngineConfiguration.mailServers, actualMailServers);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getMailServers()}
   */
  @Test
  public void testGetMailServers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    Map<String, MailServerInfo> actualMailServers = jtaProcessEngineConfiguration.getMailServers();

    // Assert
    assertTrue(actualMailServers.isEmpty());
    assertSame(jtaProcessEngineConfiguration.mailServers, actualMailServers);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setMailServers(Map)}
   */
  @Test
  public void testSetMailServers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailServers(new HashMap<>()));
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setMailServers(Map)}
   */
  @Test
  public void testSetMailServers2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailServers(new HashMap<>()));
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseType()}
   */
  @Test
  public void testGetDatabaseType() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseType());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseType()}
   */
  @Test
  public void testGetDatabaseType2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDatabaseType());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setDatabaseType(String)}
   */
  @Test
  public void testSetDatabaseType() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseTypeResult = jtaProcessEngineConfiguration
        .setDatabaseType("Database Type");

    // Assert
    assertEquals("Database Type", jtaProcessEngineConfiguration.getDatabaseType());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseTypeResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}
   */
  @Test
  public void testGetDatabaseSchemaUpdate() {
    // Arrange and Act
    String actualDatabaseSchemaUpdate = (new JtaProcessEngineConfiguration()).getDatabaseSchemaUpdate();

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualDatabaseSchemaUpdate);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}
   */
  @Test
  public void testGetDatabaseSchemaUpdate2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    String actualDatabaseSchemaUpdate = jtaProcessEngineConfiguration.getDatabaseSchemaUpdate();

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualDatabaseSchemaUpdate);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseSchemaUpdate(String)}
   */
  @Test
  public void testSetDatabaseSchemaUpdate() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseSchemaUpdateResult = jtaProcessEngineConfiguration
        .setDatabaseSchemaUpdate("2020-03-01");

    // Assert
    assertEquals("2020-03-01", jtaProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseSchemaUpdateResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDataSource()}
   */
  @Test
  public void testGetDataSource() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDataSource());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDataSource()}
   */
  @Test
  public void testGetDataSource2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDataSource());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDataSource(DataSource)}
   */
  @Test
  public void testSetDataSource() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DataSource dataSource = mock(DataSource.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDataSource(dataSource));
    assertSame(dataSource, jtaProcessEngineConfiguration.getDataSource());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcDriver()}
   */
  @Test
  public void testGetJdbcDriver() {
    // Arrange, Act and Assert
    assertEquals("org.h2.Driver", (new JtaProcessEngineConfiguration()).getJdbcDriver());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcDriver()}
   */
  @Test
  public void testGetJdbcDriver2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("org.h2.Driver", jtaProcessEngineConfiguration.getJdbcDriver());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setJdbcDriver(String)}
   */
  @Test
  public void testSetJdbcDriver() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcDriverResult = jtaProcessEngineConfiguration.setJdbcDriver("Jdbc Driver");

    // Assert
    assertEquals("Jdbc Driver", jtaProcessEngineConfiguration.getJdbcDriver());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDriverResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUrl()}
   */
  @Test
  public void testGetJdbcUrl() {
    // Arrange, Act and Assert
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", (new JtaProcessEngineConfiguration()).getJdbcUrl());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUrl()}
   */
  @Test
  public void testGetJdbcUrl2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", jtaProcessEngineConfiguration.getJdbcUrl());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setJdbcUrl(String)}
   */
  @Test
  public void testSetJdbcUrl() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcUrlResult = jtaProcessEngineConfiguration
        .setJdbcUrl("https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", jtaProcessEngineConfiguration.getJdbcUrl());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcUrlResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUsername()}
   */
  @Test
  public void testGetJdbcUsername() {
    // Arrange, Act and Assert
    assertEquals("sa", (new JtaProcessEngineConfiguration()).getJdbcUsername());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUsername()}
   */
  @Test
  public void testGetJdbcUsername2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("sa", jtaProcessEngineConfiguration.getJdbcUsername());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setJdbcUsername(String)}
   */
  @Test
  public void testSetJdbcUsername() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcUsernameResult = jtaProcessEngineConfiguration.setJdbcUsername("janedoe");

    // Assert
    assertEquals("janedoe", jtaProcessEngineConfiguration.getJdbcUsername());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcUsernameResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPassword()}
   */
  @Test
  public void testGetJdbcPassword() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, (new JtaProcessEngineConfiguration()).getJdbcPassword());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPassword()}
   */
  @Test
  public void testGetJdbcPassword2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, jtaProcessEngineConfiguration.getJdbcPassword());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setJdbcPassword(String)}
   */
  @Test
  public void testSetJdbcPassword() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcPasswordResult = jtaProcessEngineConfiguration.setJdbcPassword("iloveyou");

    // Assert
    assertEquals("iloveyou", jtaProcessEngineConfiguration.getJdbcPassword());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPasswordResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}
   */
  @Test
  public void testIsTransactionsExternallyManaged() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isTransactionsExternallyManaged());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}
   */
  @Test
  public void testIsTransactionsExternallyManaged2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isTransactionsExternallyManaged());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}
   */
  @Test
  public void testSetTransactionsExternallyManaged() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTransactionsExternallyManaged(true));
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}
   */
  @Test
  public void testSetTransactionsExternallyManaged2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTransactionsExternallyManaged(true));
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getHistoryLevel()}
   */
  @Test
  public void testGetHistoryLevel() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoryLevel());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getHistoryLevel()}
   */
  @Test
  public void testGetHistoryLevel2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoryLevel());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}
   */
  @Test
  public void testSetHistoryLevel2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetHistoryLevelResult = jtaProcessEngineConfiguration
        .setHistoryLevel(HistoryLevel.NONE);

    // Assert
    assertEquals(HistoryLevel.NONE, jtaProcessEngineConfiguration.getHistoryLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryLevelResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#isDbHistoryUsed()}
   */
  @Test
  public void testIsDbHistoryUsed() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isDbHistoryUsed());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#isDbHistoryUsed()}
   */
  @Test
  public void testIsDbHistoryUsed2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isDbHistoryUsed());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}
   */
  @Test
  public void testSetDbHistoryUsed() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDbHistoryUsed(true));
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}
   */
  @Test
  public void testSetDbHistoryUsed2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDbHistoryUsed(true));
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}
   */
  @Test
  public void testGetJdbcMaxActiveConnections() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxActiveConnections());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}
   */
  @Test
  public void testGetJdbcMaxActiveConnections2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}
   */
  @Test
  public void testSetJdbcMaxActiveConnections2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxActiveConnectionsResult = jtaProcessEngineConfiguration
        .setJdbcMaxActiveConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxActiveConnectionsResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}
   */
  @Test
  public void testGetJdbcMaxIdleConnections() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxIdleConnections());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}
   */
  @Test
  public void testGetJdbcMaxIdleConnections2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}
   */
  @Test
  public void testSetJdbcMaxIdleConnections2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxIdleConnectionsResult = jtaProcessEngineConfiguration
        .setJdbcMaxIdleConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxIdleConnectionsResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}
   */
  @Test
  public void testGetJdbcMaxCheckoutTime() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxCheckoutTime());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}
   */
  @Test
  public void testGetJdbcMaxCheckoutTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}
   */
  @Test
  public void testSetJdbcMaxCheckoutTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxCheckoutTimeResult = jtaProcessEngineConfiguration
        .setJdbcMaxCheckoutTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxCheckoutTimeResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}
   */
  @Test
  public void testGetJdbcMaxWaitTime() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxWaitTime());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}
   */
  @Test
  public void testGetJdbcMaxWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcMaxWaitTime());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}
   */
  @Test
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
   * Method under test: {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}
   */
  @Test
  public void testSetJdbcMaxWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxWaitTimeResult = jtaProcessEngineConfiguration.setJdbcMaxWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxWaitTimeResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  public void testIsJdbcPingEnabled() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJdbcPingEnabled());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  public void testIsJdbcPingEnabled2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isJdbcPingEnabled());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  public void testIsJdbcPingEnabled3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJdbcPingEnabled(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}
   */
  @Test
  public void testSetJdbcPingEnabled2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcPingEnabledResult = jtaProcessEngineConfiguration.setJdbcPingEnabled(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingEnabledResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPingQuery()}
   */
  @Test
  public void testGetJdbcPingQuery() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJdbcPingQuery());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPingQuery()}
   */
  @Test
  public void testGetJdbcPingQuery2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJdbcPingQuery());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingQuery(String)}
   */
  @Test
  public void testSetJdbcPingQuery() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcPingQueryResult = jtaProcessEngineConfiguration
        .setJdbcPingQuery("Jdbc Ping Query");

    // Assert
    assertEquals("Jdbc Ping Query", jtaProcessEngineConfiguration.getJdbcPingQuery());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingQueryResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}
   */
  @Test
  public void testGetJdbcPingConnectionNotUsedFor() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcPingConnectionNotUsedFor());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}
   */
  @Test
  public void testGetJdbcPingConnectionNotUsedFor2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}
   */
  @Test
  public void testSetJdbcPingConnectionNotUsedFor2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcPingConnectionNotUsedForResult = jtaProcessEngineConfiguration
        .setJdbcPingConnectionNotUsedFor(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingConnectionNotUsedForResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}
   */
  @Test
  public void testGetJdbcDefaultTransactionIsolationLevel() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcDefaultTransactionIsolationLevel());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}
   */
  @Test
  public void testGetJdbcDefaultTransactionIsolationLevel2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}
   */
  @Test
  public void testSetJdbcDefaultTransactionIsolationLevel2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcDefaultTransactionIsolationLevelResult = jtaProcessEngineConfiguration
        .setJdbcDefaultTransactionIsolationLevel(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDefaultTransactionIsolationLevelResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  public void testIsAsyncExecutorActivate() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isAsyncExecutorActivate());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  public void testIsAsyncExecutorActivate2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  public void testIsAsyncExecutorActivate3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorActivate(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}
   */
  @Test
  public void testSetAsyncExecutorActivate2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorActivateResult = jtaProcessEngineConfiguration
        .setAsyncExecutorActivate(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorActivateResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getClassLoader()}
   */
  @Test
  public void testGetClassLoader() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getClassLoader());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getClassLoader()}
   */
  @Test
  public void testGetClassLoader2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getClassLoader());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}
   */
  @Test
  public void testSetClassLoader2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    GroovyClassLoader classLoader = new GroovyClassLoader();

    // Act
    ProcessEngineConfiguration actualSetClassLoaderResult = jtaProcessEngineConfiguration.setClassLoader(classLoader);

    // Assert
    assertSame(classLoader, jtaProcessEngineConfiguration.getClassLoader());
    assertSame(jtaProcessEngineConfiguration, actualSetClassLoaderResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}
   */
  @Test
  public void testIsUseClassForNameClassLoading() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isUseClassForNameClassLoading());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}
   */
  @Test
  public void testIsUseClassForNameClassLoading2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isUseClassForNameClassLoading());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}
   */
  @Test
  public void testSetUseClassForNameClassLoading() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setUseClassForNameClassLoading(true));
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}
   */
  @Test
  public void testSetUseClassForNameClassLoading2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setUseClassForNameClassLoading(true));
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}
   */
  @Test
  public void testGetJpaEntityManagerFactory() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJpaEntityManagerFactory());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}
   */
  @Test
  public void testGetJpaEntityManagerFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJpaEntityManagerFactory());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}
   */
  @Test
  public void testSetJpaEntityManagerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    Object object = JSONObject.NULL;

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJpaEntityManagerFactory(object));
    assertSame(object, jtaProcessEngineConfiguration.getJpaEntityManagerFactory());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}
   */
  @Test
  public void testSetJpaEntityManagerFactory2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    Object object = JSONObject.NULL;

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJpaEntityManagerFactory(object));
    assertSame(object, jtaProcessEngineConfiguration.getJpaEntityManagerFactory());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  public void testIsJpaHandleTransaction() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJpaHandleTransaction());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  public void testIsJpaHandleTransaction2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isJpaHandleTransaction());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  public void testIsJpaHandleTransaction3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJpaHandleTransaction(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}
   */
  @Test
  public void testSetJpaHandleTransaction2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJpaHandleTransactionResult = jtaProcessEngineConfiguration
        .setJpaHandleTransaction(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaHandleTransactionResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  public void testIsJpaCloseEntityManager() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJpaCloseEntityManager());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  public void testIsJpaCloseEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  public void testIsJpaCloseEntityManager3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJpaCloseEntityManager(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}
   */
  @Test
  public void testSetJpaCloseEntityManager2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJpaCloseEntityManagerResult = jtaProcessEngineConfiguration
        .setJpaCloseEntityManager(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaCloseEntityManagerResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}
   */
  @Test
  public void testGetJpaPersistenceUnitName() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJpaPersistenceUnitName());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}
   */
  @Test
  public void testGetJpaPersistenceUnitName2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJpaPersistenceUnitName());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaPersistenceUnitName(String)}
   */
  @Test
  public void testSetJpaPersistenceUnitName() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJpaPersistenceUnitNameResult = jtaProcessEngineConfiguration
        .setJpaPersistenceUnitName("Jpa Persistence Unit Name");

    // Assert
    assertEquals("Jpa Persistence Unit Name", jtaProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaPersistenceUnitNameResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDataSourceJndiName()}
   */
  @Test
  public void testGetDataSourceJndiName() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDataSourceJndiName());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDataSourceJndiName()}
   */
  @Test
  public void testGetDataSourceJndiName2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDataSourceJndiName());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDataSourceJndiName(String)}
   */
  @Test
  public void testSetDataSourceJndiName() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDataSourceJndiNameResult = jtaProcessEngineConfiguration
        .setDataSourceJndiName("Data Source Jndi Name");

    // Assert
    assertEquals("Data Source Jndi Name", jtaProcessEngineConfiguration.getDataSourceJndiName());
    assertSame(jtaProcessEngineConfiguration, actualSetDataSourceJndiNameResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDefaultCamelContext()}
   */
  @Test
  public void testGetDefaultCamelContext() {
    // Arrange, Act and Assert
    assertEquals("camelContext", (new JtaProcessEngineConfiguration()).getDefaultCamelContext());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDefaultCamelContext()}
   */
  @Test
  public void testGetDefaultCamelContext2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("camelContext", jtaProcessEngineConfiguration.getDefaultCamelContext());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDefaultCamelContext(String)}
   */
  @Test
  public void testSetDefaultCamelContext() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDefaultCamelContextResult = jtaProcessEngineConfiguration
        .setDefaultCamelContext("Default Camel Context");

    // Assert
    assertEquals("Default Camel Context", jtaProcessEngineConfiguration.getDefaultCamelContext());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultCamelContextResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}
   */
  @Test
  public void testGetProcessEngineLifecycleListener() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessEngineLifecycleListener());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}
   */
  @Test
  public void testGetProcessEngineLifecycleListener2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessEngineLifecycleListener());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}
   */
  @Test
  public void testGetDatabaseTablePrefix() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID,
        (new JtaProcessEngineConfiguration()).getDatabaseTablePrefix());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}
   */
  @Test
  public void testGetDatabaseTablePrefix2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, jtaProcessEngineConfiguration.getDatabaseTablePrefix());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseTablePrefix(String)}
   */
  @Test
  public void testSetDatabaseTablePrefix() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseTablePrefixResult = jtaProcessEngineConfiguration
        .setDatabaseTablePrefix("Database Table Prefix");

    // Assert
    assertEquals("Database Table Prefix", jtaProcessEngineConfiguration.getDatabaseTablePrefix());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseTablePrefixResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}
   */
  @Test
  public void testSetTablePrefixIsSchema2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetTablePrefixIsSchemaResult = jtaProcessEngineConfiguration
        .setTablePrefixIsSchema(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetTablePrefixIsSchemaResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  public void testIsTablePrefixIsSchema() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isTablePrefixIsSchema());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  public void testIsTablePrefixIsSchema2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  public void testIsTablePrefixIsSchema3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTablePrefixIsSchema(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}
   */
  @Test
  public void testGetDatabaseWildcardEscapeCharacter() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseWildcardEscapeCharacter());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}
   */
  @Test
  public void testGetDatabaseWildcardEscapeCharacter2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseWildcardEscapeCharacter(String)}
   */
  @Test
  public void testSetDatabaseWildcardEscapeCharacter() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseWildcardEscapeCharacterResult = jtaProcessEngineConfiguration
        .setDatabaseWildcardEscapeCharacter("Database Wildcard Escape Character");

    // Assert
    assertEquals("Database Wildcard Escape Character",
        jtaProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseWildcardEscapeCharacterResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseCatalog()}
   */
  @Test
  public void testGetDatabaseCatalog() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, (new JtaProcessEngineConfiguration()).getDatabaseCatalog());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseCatalog()}
   */
  @Test
  public void testGetDatabaseCatalog2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, jtaProcessEngineConfiguration.getDatabaseCatalog());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseCatalog(String)}
   */
  @Test
  public void testSetDatabaseCatalog() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseCatalogResult = jtaProcessEngineConfiguration
        .setDatabaseCatalog("Database Catalog");

    // Assert
    assertEquals("Database Catalog", jtaProcessEngineConfiguration.getDatabaseCatalog());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseCatalogResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseSchema()}
   */
  @Test
  public void testGetDatabaseSchema() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseSchema());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseSchema()}
   */
  @Test
  public void testGetDatabaseSchema2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDatabaseSchema());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseSchema(String)}
   */
  @Test
  public void testSetDatabaseSchema() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseSchemaResult = jtaProcessEngineConfiguration
        .setDatabaseSchema("Database Schema");

    // Assert
    assertEquals("Database Schema", jtaProcessEngineConfiguration.getDatabaseSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseSchemaResult);
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getXmlEncoding()}
   */
  @Test
  public void testGetXmlEncoding() {
    // Arrange, Act and Assert
    assertEquals("UTF-8", (new JtaProcessEngineConfiguration()).getXmlEncoding());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getXmlEncoding()}
   */
  @Test
  public void testGetXmlEncoding2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("UTF-8", jtaProcessEngineConfiguration.getXmlEncoding());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setXmlEncoding(String)}
   */
  @Test
  public void testSetXmlEncoding() {
    // Arrange, Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setXmlEncoding("UTF-8"));
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getClock()}
   */
  @Test
  public void testGetClock() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getClock());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getClock()}
   */
  @Test
  public void testGetClock2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setClock(Clock)}
   */
  @Test
  public void testSetClock() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(clock));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#setClock(Clock)}
   */
  @Test
  public void testSetClock2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(clock));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAsyncExecutor());
  }

  /**
   * Method under test: {@link ProcessEngineConfiguration#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getAsyncExecutor());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}
   */
  @Test
  public void testSetAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorResult = jtaProcessEngineConfiguration
        .setAsyncExecutor(asyncExecutor);

    // Assert
    assertSame(asyncExecutor, jtaProcessEngineConfiguration.getAsyncExecutor());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}
   */
  @Test
  public void testGetLockTimeAsyncJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(60, (new JtaProcessEngineConfiguration()).getLockTimeAsyncJobWaitTime());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}
   */
  @Test
  public void testGetLockTimeAsyncJobWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(60, jtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}
   */
  @Test
  public void testSetLockTimeAsyncJobWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetLockTimeAsyncJobWaitTimeResult = jtaProcessEngineConfiguration
        .setLockTimeAsyncJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetLockTimeAsyncJobWaitTimeResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}
   */
  @Test
  public void testGetDefaultFailedJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10, (new JtaProcessEngineConfiguration()).getDefaultFailedJobWaitTime());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}
   */
  @Test
  public void testGetDefaultFailedJobWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(10, jtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}
   */
  @Test
  public void testSetDefaultFailedJobWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetDefaultFailedJobWaitTimeResult = jtaProcessEngineConfiguration
        .setDefaultFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultFailedJobWaitTimeResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}
   */
  @Test
  public void testGetAsyncFailedJobWaitTime() {
    // Arrange, Act and Assert
    assertEquals(10, (new JtaProcessEngineConfiguration()).getAsyncFailedJobWaitTime());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}
   */
  @Test
  public void testGetAsyncFailedJobWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(10, jtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}
   */
  @Test
  public void testSetAsyncFailedJobWaitTime2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetAsyncFailedJobWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncFailedJobWaitTimeResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  public void testIsEnableProcessDefinitionInfoCache() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableProcessDefinitionInfoCache());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  public void testIsEnableProcessDefinitionInfoCache2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  public void testIsEnableProcessDefinitionInfoCache3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableProcessDefinitionInfoCache(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}
   */
  @Test
  public void testSetEnableProcessDefinitionInfoCache2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetEnableProcessDefinitionInfoCacheResult = jtaProcessEngineConfiguration
        .setEnableProcessDefinitionInfoCache(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableProcessDefinitionInfoCacheResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}
   */
  @Test
  public void testSetCopyVariablesToLocalForTasks2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetCopyVariablesToLocalForTasksResult = jtaProcessEngineConfiguration
        .setCopyVariablesToLocalForTasks(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertSame(jtaProcessEngineConfiguration, actualSetCopyVariablesToLocalForTasksResult);
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  public void testIsCopyVariablesToLocalForTasks() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isCopyVariablesToLocalForTasks());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  public void testIsCopyVariablesToLocalForTasks2() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  public void testIsCopyVariablesToLocalForTasks3() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCopyVariablesToLocalForTasks(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
  }

  /**
   * Method under test:
   * {@link ProcessEngineConfiguration#setEngineAgendaFactory(ActivitiEngineAgendaFactory)}
   */
  @Test
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
