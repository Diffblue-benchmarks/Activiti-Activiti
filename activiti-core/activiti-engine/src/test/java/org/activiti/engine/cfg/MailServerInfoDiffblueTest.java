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
package org.activiti.engine.cfg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MailServerInfoDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link MailServerInfo}
   *   <li>{@link MailServerInfo#setMailServerDefaultFrom(String)}
   *   <li>{@link MailServerInfo#setMailServerHost(String)}
   *   <li>{@link MailServerInfo#setMailServerPassword(String)}
   *   <li>{@link MailServerInfo#setMailServerPort(int)}
   *   <li>{@link MailServerInfo#setMailServerUseSSL(boolean)}
   *   <li>{@link MailServerInfo#setMailServerUseTLS(boolean)}
   *   <li>{@link MailServerInfo#setMailServerUsername(String)}
   *   <li>{@link MailServerInfo#getMailServerDefaultFrom()}
   *   <li>{@link MailServerInfo#getMailServerHost()}
   *   <li>{@link MailServerInfo#getMailServerPassword()}
   *   <li>{@link MailServerInfo#getMailServerPort()}
   *   <li>{@link MailServerInfo#getMailServerUsername()}
   *   <li>{@link MailServerInfo#isMailServerUseSSL()}
   *   <li>{@link MailServerInfo#isMailServerUseTLS()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    MailServerInfo actualMailServerInfo = new MailServerInfo();
    actualMailServerInfo.setMailServerDefaultFrom("jane.doe@example.org");
    actualMailServerInfo.setMailServerHost("localhost");
    actualMailServerInfo.setMailServerPassword("iloveyou");
    actualMailServerInfo.setMailServerPort(8080);
    actualMailServerInfo.setMailServerUseSSL(true);
    actualMailServerInfo.setMailServerUseTLS(true);
    actualMailServerInfo.setMailServerUsername("janedoe");
    String actualMailServerDefaultFrom = actualMailServerInfo.getMailServerDefaultFrom();
    String actualMailServerHost = actualMailServerInfo.getMailServerHost();
    String actualMailServerPassword = actualMailServerInfo.getMailServerPassword();
    int actualMailServerPort = actualMailServerInfo.getMailServerPort();
    String actualMailServerUsername = actualMailServerInfo.getMailServerUsername();
    boolean actualIsMailServerUseSSLResult = actualMailServerInfo.isMailServerUseSSL();

    // Assert that nothing has changed
    assertEquals("iloveyou", actualMailServerPassword);
    assertEquals("jane.doe@example.org", actualMailServerDefaultFrom);
    assertEquals("janedoe", actualMailServerUsername);
    assertEquals("localhost", actualMailServerHost);
    assertEquals(8080, actualMailServerPort);
    assertTrue(actualIsMailServerUseSSLResult);
    assertTrue(actualMailServerInfo.isMailServerUseTLS());
  }
}
