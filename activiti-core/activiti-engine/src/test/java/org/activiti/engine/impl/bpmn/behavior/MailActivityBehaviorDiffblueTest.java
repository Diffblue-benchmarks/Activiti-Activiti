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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.NamingException;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class MailActivityBehaviorDiffblueTest {
  /**
   * Test {@link MailActivityBehavior#execute(DelegateExecution)}.
   *
   * <p>Method under test: {@link MailActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.execute(DelegateExecution)"})
  public void testExecute() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            mailActivityBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenEmptyString_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> new MailActivityBehavior().createEmail(null, "", false));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenEmptyString_thenThrowActivitiException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> new MailActivityBehavior().createEmail("", null, false));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenFalse_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new MailActivityBehavior().createEmail(null, null, false));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code <html><body>HTML Content</body></html>}.
   *   <li>Then return {@link HtmlEmail}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenHtmlBodyHtmlContentBodyHtml_thenReturnHtmlEmail() {
    // Arrange and Act
    Email actualCreateEmailResult =
        new MailActivityBehavior()
            .createEmail(null, "<html><body>HTML Content</body></html>", false);

    // Assert
    assertTrue(actualCreateEmailResult instanceof HtmlEmail);
    assertEquals("25", actualCreateEmailResult.getSmtpPort());
    assertEquals("465", actualCreateEmailResult.getSslSmtpPort());
    assertNull(actualCreateEmailResult.getBounceAddress());
    assertNull(actualCreateEmailResult.getHostName());
    assertNull(actualCreateEmailResult.getSubject());
    assertNull(((HtmlEmail) actualCreateEmailResult).getSubType());
    assertNull(actualCreateEmailResult.getFromAddress());
    assertNull(actualCreateEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateEmailResult.getSocketTimeout());
    assertFalse(actualCreateEmailResult.isSSL());
    assertFalse(actualCreateEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateEmailResult.isSSLOnConnect());
    assertFalse(actualCreateEmailResult.isSendPartial());
    assertFalse(actualCreateEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateEmailResult.isStartTLSRequired());
    assertFalse(actualCreateEmailResult.isTLS());
    assertFalse(((HtmlEmail) actualCreateEmailResult).isBoolHasAttachments());
    assertTrue(actualCreateEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code <html><body>HTML Content</body></html>}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenHtmlBodyHtmlContentBodyHtml_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new MailActivityBehavior()
                .createEmail("", "<html><body>HTML Content</body></html>", false));
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code Text}.
   *   <li>Then return {@link HtmlEmail}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenText_thenReturnHtmlEmail() {
    // Arrange and Act
    Email actualCreateEmailResult =
        new MailActivityBehavior()
            .createEmail("Text", "<html><body>HTML Content</body></html>", true);

    // Assert
    assertTrue(actualCreateEmailResult instanceof HtmlEmail);
    assertEquals("25", actualCreateEmailResult.getSmtpPort());
    assertEquals("465", actualCreateEmailResult.getSslSmtpPort());
    assertNull(actualCreateEmailResult.getBounceAddress());
    assertNull(actualCreateEmailResult.getHostName());
    assertNull(actualCreateEmailResult.getSubject());
    assertNull(((HtmlEmail) actualCreateEmailResult).getSubType());
    assertNull(actualCreateEmailResult.getFromAddress());
    assertNull(actualCreateEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateEmailResult.getSocketTimeout());
    assertFalse(actualCreateEmailResult.isSSL());
    assertFalse(actualCreateEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateEmailResult.isSSLOnConnect());
    assertFalse(actualCreateEmailResult.isSendPartial());
    assertFalse(actualCreateEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateEmailResult.isStartTLSRequired());
    assertFalse(actualCreateEmailResult.isTLS());
    assertFalse(((HtmlEmail) actualCreateEmailResult).isBoolHasAttachments());
    assertTrue(actualCreateEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code Text}.
   *   <li>Then return {@link MultiPartEmail}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenText_thenReturnMultiPartEmail() {
    // Arrange and Act
    Email actualCreateEmailResult = new MailActivityBehavior().createEmail("Text", null, true);

    // Assert
    assertTrue(actualCreateEmailResult instanceof MultiPartEmail);
    assertEquals("25", actualCreateEmailResult.getSmtpPort());
    assertEquals("465", actualCreateEmailResult.getSslSmtpPort());
    assertNull(actualCreateEmailResult.getBounceAddress());
    assertNull(actualCreateEmailResult.getHostName());
    assertNull(actualCreateEmailResult.getSubject());
    assertNull(((MultiPartEmail) actualCreateEmailResult).getSubType());
    assertNull(actualCreateEmailResult.getFromAddress());
    assertNull(actualCreateEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateEmailResult.getSocketTimeout());
    assertFalse(actualCreateEmailResult.isSSL());
    assertFalse(actualCreateEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateEmailResult.isSSLOnConnect());
    assertFalse(actualCreateEmailResult.isSendPartial());
    assertFalse(actualCreateEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateEmailResult.isStartTLSRequired());
    assertFalse(actualCreateEmailResult.isTLS());
    assertFalse(((MultiPartEmail) actualCreateEmailResult).isBoolHasAttachments());
    assertTrue(actualCreateEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code Text}.
   *   <li>Then return {@link SimpleEmail}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenText_thenReturnSimpleEmail() {
    // Arrange and Act
    Email actualCreateEmailResult = new MailActivityBehavior().createEmail("Text", null, false);

    // Assert
    assertTrue(actualCreateEmailResult instanceof SimpleEmail);
    assertEquals("25", actualCreateEmailResult.getSmtpPort());
    assertEquals("465", actualCreateEmailResult.getSslSmtpPort());
    assertNull(actualCreateEmailResult.getBounceAddress());
    assertNull(actualCreateEmailResult.getHostName());
    assertNull(actualCreateEmailResult.getSubject());
    assertNull(actualCreateEmailResult.getFromAddress());
    assertNull(actualCreateEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateEmailResult.getSocketTimeout());
    assertFalse(actualCreateEmailResult.isSSL());
    assertFalse(actualCreateEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateEmailResult.isSSLOnConnect());
    assertFalse(actualCreateEmailResult.isSendPartial());
    assertFalse(actualCreateEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateEmailResult.isStartTLSRequired());
    assertFalse(actualCreateEmailResult.isTLS());
    assertTrue(actualCreateEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateEmailResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#createEmail(String, String, boolean)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createEmail(String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Email MailActivityBehavior.createEmail(String, String, boolean)"})
  public void testCreateEmail_whenTrue_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> new MailActivityBehavior().createEmail("", null, true));
  }

  /**
   * Test {@link MailActivityBehavior#createHtmlEmail(String, String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HtmlEmail MailActivityBehavior.createHtmlEmail(String, String)"})
  public void testCreateHtmlEmail_whenEmptyString_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new MailActivityBehavior()
                .createHtmlEmail("", "<html><body>HTML Content</body></html>"));
  }

  /**
   * Test {@link MailActivityBehavior#createHtmlEmail(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return SmtpPort is {@code 25}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HtmlEmail MailActivityBehavior.createHtmlEmail(String, String)"})
  public void testCreateHtmlEmail_whenNull_thenReturnSmtpPortIs25() {
    // Arrange and Act
    HtmlEmail actualCreateHtmlEmailResult =
        new MailActivityBehavior().createHtmlEmail(null, "<html><body>HTML Content</body></html>");

    // Assert
    assertEquals("25", actualCreateHtmlEmailResult.getSmtpPort());
    assertEquals("465", actualCreateHtmlEmailResult.getSslSmtpPort());
    assertNull(actualCreateHtmlEmailResult.getBounceAddress());
    assertNull(actualCreateHtmlEmailResult.getHostName());
    assertNull(actualCreateHtmlEmailResult.getSubject());
    assertNull(actualCreateHtmlEmailResult.getSubType());
    assertNull(actualCreateHtmlEmailResult.getFromAddress());
    assertNull(actualCreateHtmlEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateHtmlEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateHtmlEmailResult.getSocketTimeout());
    assertFalse(actualCreateHtmlEmailResult.isSSL());
    assertFalse(actualCreateHtmlEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateHtmlEmailResult.isSSLOnConnect());
    assertFalse(actualCreateHtmlEmailResult.isSendPartial());
    assertFalse(actualCreateHtmlEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateHtmlEmailResult.isStartTLSRequired());
    assertFalse(actualCreateHtmlEmailResult.isTLS());
    assertFalse(actualCreateHtmlEmailResult.isBoolHasAttachments());
    assertTrue(actualCreateHtmlEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#createHtmlEmail(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HtmlEmail MailActivityBehavior.createHtmlEmail(String, String)"})
  public void testCreateHtmlEmail_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> new MailActivityBehavior().createHtmlEmail(null, null));
  }

  /**
   * Test {@link MailActivityBehavior#createHtmlEmail(String, String)}.
   *
   * <ul>
   *   <li>When {@code Text}.
   *   <li>Then return SmtpPort is {@code 25}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createHtmlEmail(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HtmlEmail MailActivityBehavior.createHtmlEmail(String, String)"})
  public void testCreateHtmlEmail_whenText_thenReturnSmtpPortIs25() {
    // Arrange and Act
    HtmlEmail actualCreateHtmlEmailResult =
        new MailActivityBehavior()
            .createHtmlEmail("Text", "<html><body>HTML Content</body></html>");

    // Assert
    assertEquals("25", actualCreateHtmlEmailResult.getSmtpPort());
    assertEquals("465", actualCreateHtmlEmailResult.getSslSmtpPort());
    assertNull(actualCreateHtmlEmailResult.getBounceAddress());
    assertNull(actualCreateHtmlEmailResult.getHostName());
    assertNull(actualCreateHtmlEmailResult.getSubject());
    assertNull(actualCreateHtmlEmailResult.getSubType());
    assertNull(actualCreateHtmlEmailResult.getFromAddress());
    assertNull(actualCreateHtmlEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateHtmlEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateHtmlEmailResult.getSocketTimeout());
    assertFalse(actualCreateHtmlEmailResult.isSSL());
    assertFalse(actualCreateHtmlEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateHtmlEmailResult.isSSLOnConnect());
    assertFalse(actualCreateHtmlEmailResult.isSendPartial());
    assertFalse(actualCreateHtmlEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateHtmlEmailResult.isStartTLSRequired());
    assertFalse(actualCreateHtmlEmailResult.isTLS());
    assertFalse(actualCreateHtmlEmailResult.isBoolHasAttachments());
    assertTrue(actualCreateHtmlEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateHtmlEmailResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#createTextOnlyEmail(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createTextOnlyEmail(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SimpleEmail MailActivityBehavior.createTextOnlyEmail(String)"})
  public void testCreateTextOnlyEmail_whenEmptyString_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> new MailActivityBehavior().createTextOnlyEmail(""));
  }

  /**
   * Test {@link MailActivityBehavior#createTextOnlyEmail(String)}.
   *
   * <ul>
   *   <li>When {@code Text}.
   *   <li>Then return SmtpPort is {@code 25}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createTextOnlyEmail(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SimpleEmail MailActivityBehavior.createTextOnlyEmail(String)"})
  public void testCreateTextOnlyEmail_whenText_thenReturnSmtpPortIs25() {
    // Arrange and Act
    SimpleEmail actualCreateTextOnlyEmailResult =
        new MailActivityBehavior().createTextOnlyEmail("Text");

    // Assert
    assertEquals("25", actualCreateTextOnlyEmailResult.getSmtpPort());
    assertEquals("465", actualCreateTextOnlyEmailResult.getSslSmtpPort());
    assertNull(actualCreateTextOnlyEmailResult.getBounceAddress());
    assertNull(actualCreateTextOnlyEmailResult.getHostName());
    assertNull(actualCreateTextOnlyEmailResult.getSubject());
    assertNull(actualCreateTextOnlyEmailResult.getFromAddress());
    assertNull(actualCreateTextOnlyEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateTextOnlyEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateTextOnlyEmailResult.getSocketTimeout());
    assertFalse(actualCreateTextOnlyEmailResult.isSSL());
    assertFalse(actualCreateTextOnlyEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateTextOnlyEmailResult.isSSLOnConnect());
    assertFalse(actualCreateTextOnlyEmailResult.isSendPartial());
    assertFalse(actualCreateTextOnlyEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateTextOnlyEmailResult.isStartTLSRequired());
    assertFalse(actualCreateTextOnlyEmailResult.isTLS());
    assertTrue(actualCreateTextOnlyEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateTextOnlyEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateTextOnlyEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateTextOnlyEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateTextOnlyEmailResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#createMultiPartEmail(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createMultiPartEmail(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"MultiPartEmail MailActivityBehavior.createMultiPartEmail(String)"})
  public void testCreateMultiPartEmail_whenEmptyString_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> new MailActivityBehavior().createMultiPartEmail(""));
  }

  /**
   * Test {@link MailActivityBehavior#createMultiPartEmail(String)}.
   *
   * <ul>
   *   <li>When {@code Text}.
   *   <li>Then return SmtpPort is {@code 25}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#createMultiPartEmail(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"MultiPartEmail MailActivityBehavior.createMultiPartEmail(String)"})
  public void testCreateMultiPartEmail_whenText_thenReturnSmtpPortIs25() {
    // Arrange and Act
    MultiPartEmail actualCreateMultiPartEmailResult =
        new MailActivityBehavior().createMultiPartEmail("Text");

    // Assert
    assertEquals("25", actualCreateMultiPartEmailResult.getSmtpPort());
    assertEquals("465", actualCreateMultiPartEmailResult.getSslSmtpPort());
    assertNull(actualCreateMultiPartEmailResult.getBounceAddress());
    assertNull(actualCreateMultiPartEmailResult.getHostName());
    assertNull(actualCreateMultiPartEmailResult.getSubject());
    assertNull(actualCreateMultiPartEmailResult.getSubType());
    assertNull(actualCreateMultiPartEmailResult.getFromAddress());
    assertNull(actualCreateMultiPartEmailResult.getMimeMessage());
    assertEquals(60000, actualCreateMultiPartEmailResult.getSocketConnectionTimeout());
    assertEquals(60000, actualCreateMultiPartEmailResult.getSocketTimeout());
    assertFalse(actualCreateMultiPartEmailResult.isSSL());
    assertFalse(actualCreateMultiPartEmailResult.isSSLCheckServerIdentity());
    assertFalse(actualCreateMultiPartEmailResult.isSSLOnConnect());
    assertFalse(actualCreateMultiPartEmailResult.isSendPartial());
    assertFalse(actualCreateMultiPartEmailResult.isStartTLSEnabled());
    assertFalse(actualCreateMultiPartEmailResult.isStartTLSRequired());
    assertFalse(actualCreateMultiPartEmailResult.isTLS());
    assertFalse(actualCreateMultiPartEmailResult.isBoolHasAttachments());
    assertTrue(actualCreateMultiPartEmailResult.getBccAddresses().isEmpty());
    assertTrue(actualCreateMultiPartEmailResult.getCcAddresses().isEmpty());
    assertTrue(actualCreateMultiPartEmailResult.getReplyToAddresses().isEmpty());
    assertTrue(actualCreateMultiPartEmailResult.getToAddresses().isEmpty());
    assertTrue(actualCreateMultiPartEmailResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   *
   * <ul>
   *   <li>Given {@link EmailException#EmailException(String)} with msg is {@code ,}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_givenEmailExceptionWithMsgIsComma() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addTo(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> mailActivityBehavior.addTo(email, "alice.liddell@example.org"));
    verify(email).addTo("alice.liddell@example.org");
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   *
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).
   *   <li>When {@link HtmlEmail} {@link HtmlEmail#addTo(String)} return {@link HtmlEmail} (default
   *       constructor).
   *   <li>Then calls {@link HtmlEmail#addTo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_givenHtmlEmail_whenHtmlEmailAddToReturnHtmlEmail_thenCallsAddTo()
      throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addTo(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addTo(email, "alice.liddell@example.org");

    // Assert
    verify(email).addTo("alice.liddell@example.org");
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_whenEmptyString_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addTo(new HtmlEmail(), ""));
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   *
   * <ul>
   *   <li>When {@code foo,bar}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_whenFooBar_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> mailActivityBehavior.addTo(new HtmlEmail(), "foo,bar"));
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).
   *   <li>Then {@link HtmlEmail} (default constructor) ToAddresses size is one.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_whenHtmlEmail_thenHtmlEmailToAddressesSizeIsOne() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addTo(email, "alice.liddell@example.org");

    // Assert
    List<InternetAddress> toAddresses = email.getToAddresses();
    assertEquals(1, toAddresses.size());
    InternetAddress getResult = toAddresses.get(0);
    assertEquals("alice.liddell@example.org", getResult.getAddress());
    assertEquals("rfc822", getResult.getType());
    assertNull(getResult.getPersonal());
  }

  /**
   * Test {@link MailActivityBehavior#addTo(Email, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addTo(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addTo(Email, String)"})
  public void testAddTo_whenNull_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addTo(new HtmlEmail(), null));
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   *
   * <ul>
   *   <li>Given {@link EmailException#EmailException(String)} with {@code Msg}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_givenEmailExceptionWithMsg() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setFrom(Mockito.<String>any())).thenThrow(new EmailException("Msg"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> mailActivityBehavior.setFrom(email, "jane.doe@example.org", "42"));
    verify(email).setFrom("jane.doe@example.org");
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   *
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).
   *   <li>When {@link HtmlEmail} {@link HtmlEmail#setFrom(String)} return {@link HtmlEmail}
   *       (default constructor).
   *   <li>Then calls {@link HtmlEmail#setFrom(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_givenHtmlEmail_whenHtmlEmailSetFromReturnHtmlEmail_thenCallsSetFrom()
      throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setFrom(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.setFrom(email, "jane.doe@example.org", "42");

    // Assert
    verify(email).setFrom("jane.doe@example.org");
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   *
   * <ul>
   *   <li>Given {@code UTF-8}.
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_givenUtf8_whenEmptyString_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = new HtmlEmail();
    email.setCharset("UTF-8");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setFrom(email, "", null));
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   *
   * <ul>
   *   <li>Given {@code UTF-8}.
   *   <li>When {@code From}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_givenUtf8_whenFrom_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = new HtmlEmail();
    email.setCharset("UTF-8");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.setFrom(email, "From", null));
  }

  /**
   * Test {@link MailActivityBehavior#setFrom(Email, String, String)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).
   *   <li>Then {@link HtmlEmail} (default constructor) FromAddress Address is {@code
   *       jane.doe@example.org}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setFrom(Email, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setFrom(Email, String, String)"})
  public void testSetFrom_whenHtmlEmail_thenHtmlEmailFromAddressAddressIsJaneDoeExampleOrg() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.setFrom(email, "jane.doe@example.org", "42");

    // Assert
    InternetAddress fromAddress = email.getFromAddress();
    assertEquals("jane.doe@example.org", fromAddress.getAddress());
    assertEquals("rfc822", fromAddress.getType());
    assertNull(fromAddress.getPersonal());
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   *
   * <ul>
   *   <li>Given {@link EmailException#EmailException(String)} with msg is {@code ,}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_givenEmailExceptionWithMsgIsComma() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addCc(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> mailActivityBehavior.addCc(email, "ada.lovelace@example.org"));
    verify(email).addCc("ada.lovelace@example.org");
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   *
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).
   *   <li>When {@link HtmlEmail} {@link HtmlEmail#addCc(String)} return {@link HtmlEmail} (default
   *       constructor).
   *   <li>Then calls {@link HtmlEmail#addCc(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_givenHtmlEmail_whenHtmlEmailAddCcReturnHtmlEmail_thenCallsAddCc()
      throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addCc(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addCc(email, "ada.lovelace@example.org");

    // Assert
    verify(email).addCc("ada.lovelace@example.org");
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_whenEmptyString_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addCc(new HtmlEmail(), ""));
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   *
   * <ul>
   *   <li>When {@code foo,bar}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_whenFooBar_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> mailActivityBehavior.addCc(new HtmlEmail(), "foo,bar"));
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).
   *   <li>Then {@link HtmlEmail} (default constructor) CcAddresses size is one.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_whenHtmlEmail_thenHtmlEmailCcAddressesSizeIsOne() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addCc(email, "ada.lovelace@example.org");

    // Assert
    List<InternetAddress> ccAddresses = email.getCcAddresses();
    assertEquals(1, ccAddresses.size());
    InternetAddress getResult = ccAddresses.get(0);
    assertEquals("ada.lovelace@example.org", getResult.getAddress());
    assertEquals("rfc822", getResult.getType());
    assertNull(getResult.getPersonal());
  }

  /**
   * Test {@link MailActivityBehavior#addCc(Email, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link HtmlEmail} (default constructor) CcAddresses Empty.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addCc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addCc(Email, String)"})
  public void testAddCc_whenNull_thenHtmlEmailCcAddressesEmpty() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addCc(email, null);

    // Assert that nothing has changed
    assertTrue(email.getCcAddresses().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   *
   * <ul>
   *   <li>Given {@link EmailException#EmailException(String)} with msg is {@code ,}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_givenEmailExceptionWithMsgIsComma() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addBcc(Mockito.<String>any())).thenThrow(new EmailException(","));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> mailActivityBehavior.addBcc(email, "ada.lovelace@example.org"));
    verify(email).addBcc("ada.lovelace@example.org");
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   *
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).
   *   <li>When {@link HtmlEmail} {@link HtmlEmail#addBcc(String)} return {@link HtmlEmail} (default
   *       constructor).
   *   <li>Then calls {@link HtmlEmail#addBcc(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_givenHtmlEmail_whenHtmlEmailAddBccReturnHtmlEmail_thenCallsAddBcc()
      throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.addBcc(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.addBcc(email, "ada.lovelace@example.org");

    // Assert
    verify(email).addBcc("ada.lovelace@example.org");
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_whenEmptyString_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> mailActivityBehavior.addBcc(new HtmlEmail(), ""));
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   *
   * <ul>
   *   <li>When {@code foo,bar}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_whenFooBar_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> mailActivityBehavior.addBcc(new HtmlEmail(), "foo,bar"));
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).
   *   <li>Then {@link HtmlEmail} (default constructor) BccAddresses size is one.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_whenHtmlEmail_thenHtmlEmailBccAddressesSizeIsOne() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addBcc(email, "ada.lovelace@example.org");

    // Assert
    List<InternetAddress> bccAddresses = email.getBccAddresses();
    assertEquals(1, bccAddresses.size());
    InternetAddress getResult = bccAddresses.get(0);
    assertEquals("ada.lovelace@example.org", getResult.getAddress());
    assertEquals("rfc822", getResult.getType());
    assertNull(getResult.getPersonal());
  }

  /**
   * Test {@link MailActivityBehavior#addBcc(Email, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link HtmlEmail} (default constructor) BccAddresses Empty.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#addBcc(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.addBcc(Email, String)"})
  public void testAddBcc_whenNull_thenHtmlEmailBccAddressesEmpty() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.addBcc(email, null);

    // Assert that nothing has changed
    assertTrue(email.getBccAddresses().isEmpty());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   *
   * <ul>
   *   <li>Given {@link ByteArrayDataSource#ByteArrayDataSource(String, String)} with {@code Data}
   *       and {@code Type}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
  public void testAttach_givenByteArrayDataSourceWithDataAndType()
      throws IOException, EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();
    ArrayList<File> files = new ArrayList<>();

    ArrayList<DataSource> dataSources = new ArrayList<>();
    dataSources.add(new ByteArrayDataSource("Data", "Type"));

    // Act
    mailActivityBehavior.attach(email, files, dataSources);

    // Assert
    assertTrue(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   *
   * <ul>
   *   <li>Given {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
  public void testAttach_givenNotAllWhoWanderAreLost() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = new HtmlEmail();
    email.addPart("Not all who wander are lost", "text/plain");

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());

    // Act
    mailActivityBehavior.attach(email, files, new ArrayList<>());

    // Assert
    assertTrue(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then not {@link HtmlEmail} (default constructor) BoolHasAttachments.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
  public void testAttach_givenNull_whenArrayListAddNull_thenNotHtmlEmailBoolHasAttachments()
      throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();
    ArrayList<File> files = new ArrayList<>();

    ArrayList<DataSource> dataSources = new ArrayList<>();
    dataSources.add(null);

    // Act
    mailActivityBehavior.attach(email, files, dataSources);

    // Assert that nothing has changed
    assertFalse(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   *
   * <ul>
   *   <li>Then {@link HtmlEmail} (default constructor) BoolHasAttachments.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
  public void testAttach_thenHtmlEmailBoolHasAttachments() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    ArrayList<File> files = new ArrayList<>();
    files.add(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());

    // Act
    mailActivityBehavior.attach(email, files, new ArrayList<>());

    // Assert
    assertTrue(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#attach(Email, List, List)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).
   *   <li>Then not {@link HtmlEmail} (default constructor) BoolHasAttachments.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#attach(Email, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.attach(Email, List, List)"})
  public void testAttach_whenHtmlEmail_thenNotHtmlEmailBoolHasAttachments() throws EmailException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();
    ArrayList<File> files = new ArrayList<>();

    // Act
    mailActivityBehavior.attach(email, files, new ArrayList<>());

    // Assert that nothing has changed
    assertFalse(email.isBoolHasAttachments());
  }

  /**
   * Test {@link MailActivityBehavior#setSubject(Email, String)}.
   *
   * <ul>
   *   <li>Given {@link HtmlEmail} (default constructor).
   *   <li>Then calls {@link HtmlEmail#setSubject(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setSubject(Email, String)"})
  public void testSetSubject_givenHtmlEmail_thenCallsSetSubject() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    when(email.setSubject(Mockito.<String>any())).thenReturn(new HtmlEmail());

    // Act
    mailActivityBehavior.setSubject(email, "Hello from the Dreaming Spires");

    // Assert
    verify(email).setSubject("Hello from the Dreaming Spires");
  }

  /**
   * Test {@link MailActivityBehavior#setSubject(Email, String)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).
   *   <li>Then {@link HtmlEmail} (default constructor) Subject is empty string.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setSubject(Email, String)"})
  public void testSetSubject_whenHtmlEmail_thenHtmlEmailSubjectIsEmptyString() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.setSubject(email, null);

    // Assert
    assertEquals("", email.getSubject());
  }

  /**
   * Test {@link MailActivityBehavior#setSubject(Email, String)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).
   *   <li>Then {@link HtmlEmail} (default constructor) Subject is {@code Hello from the Dreaming
   *       Spires}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setSubject(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setSubject(Email, String)"})
  public void testSetSubject_whenHtmlEmail_thenHtmlEmailSubjectIsHelloFromTheDreamingSpires() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    HtmlEmail email = new HtmlEmail();

    // Act
    mailActivityBehavior.setSubject(email, "Hello from the Dreaming Spires");

    // Assert
    assertEquals("Hello from the Dreaming Spires", email.getSubject());
  }

  /**
   * Test {@link MailActivityBehavior#setEmailSession(Email, String)}.
   *
   * <ul>
   *   <li>Given {@link NamingException#NamingException()}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setEmailSession(Email, String)"})
  public void testSetEmailSession_givenNamingException() throws NamingException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    doThrow(new NamingException()).when(email).setMailSessionFromJNDI(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> mailActivityBehavior.setEmailSession(email, "Mail Session Jndi"));
    verify(email).setMailSessionFromJNDI("Mail Session Jndi");
  }

  /**
   * Test {@link MailActivityBehavior#setEmailSession(Email, String)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} {@link HtmlEmail#setMailSessionFromJNDI(String)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setEmailSession(Email, String)"})
  public void testSetEmailSession_whenHtmlEmailSetMailSessionFromJNDIDoesNothing()
      throws NamingException {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    HtmlEmail email = mock(HtmlEmail.class);
    doNothing().when(email).setMailSessionFromJNDI(Mockito.<String>any());

    // Act
    mailActivityBehavior.setEmailSession(email, "Mail Session Jndi");

    // Assert
    verify(email).setMailSessionFromJNDI("Mail Session Jndi");
  }

  /**
   * Test {@link MailActivityBehavior#setEmailSession(Email, String)}.
   *
   * <ul>
   *   <li>When {@link HtmlEmail} (default constructor).
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setEmailSession(Email, String)"})
  public void testSetEmailSession_whenHtmlEmail_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> mailActivityBehavior.setEmailSession(new HtmlEmail(), "Mail Session Jndi"));
  }

  /**
   * Test {@link MailActivityBehavior#setEmailSession(Email, String)}.
   *
   * <ul>
   *   <li>When {@code java:}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#setEmailSession(Email, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.setEmailSession(Email, String)"})
  public void testSetEmailSession_whenJava_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> mailActivityBehavior.setEmailSession(new HtmlEmail(), "java:"));
  }

  /**
   * Test {@link MailActivityBehavior#splitAndTrim(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#splitAndTrim(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] MailActivityBehavior.splitAndTrim(String)"})
  public void testSplitAndTrim_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new MailActivityBehavior().splitAndTrim(null));
  }

  /**
   * Test {@link MailActivityBehavior#splitAndTrim(String)}.
   *
   * <ul>
   *   <li>When {@code Str}.
   *   <li>Then return array of {@link String} with {@code Str}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#splitAndTrim(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] MailActivityBehavior.splitAndTrim(String)"})
  public void testSplitAndTrim_whenStr_thenReturnArrayOfStringWithStr() {
    // Arrange, Act and Assert
    assertArrayEquals(new String[] {"Str"}, new MailActivityBehavior().splitAndTrim("Str"));
  }

  /**
   * Test {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is {@link JSONObject#NULL}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#getStringFromField(Expression,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String MailActivityBehavior.getStringFromField(Expression, DelegateExecution)"
  })
  public void testGetStringFromField_whenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "null",
        mailActivityBehavior.getStringFromField(
            expression, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with value is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#getStringFromField(Expression,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String MailActivityBehavior.getStringFromField(Expression, DelegateExecution)"
  })
  public void testGetStringFromField_whenFixedValueWithValueIsNull_thenReturnNull2() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    FixedValue expression = new FixedValue(null);

    // Act and Assert
    assertNull(
        mailActivityBehavior.getStringFromField(
            expression, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#getStringFromField(Expression, DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#getStringFromField(Expression,
   * DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String MailActivityBehavior.getStringFromField(Expression, DelegateExecution)"
  })
  public void testGetStringFromField_whenNull_thenReturnNull() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    // Act and Assert
    assertNull(
        mailActivityBehavior.getStringFromField(
            null, ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link MailActivityBehavior#fileExists(File)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MailActivityBehavior.fileExists(File)"})
  public void testFileExists_whenNull() {
    // Arrange, Act and Assert
    assertFalse(new MailActivityBehavior().fileExists(null));
  }

  /**
   * Test {@link MailActivityBehavior#fileExists(File)}.
   *
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is empty string toFile.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MailActivityBehavior.fileExists(File)"})
  public void testFileExists_whenPropertyIsJavaIoTmpdirIsEmptyStringToFile() {
    // Arrange and Act
    boolean actualFileExistsResult =
        new MailActivityBehavior()
            .fileExists(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile());

    // Assert
    assertFalse(actualFileExistsResult);
  }

  /**
   * Test {@link MailActivityBehavior#fileExists(File)}.
   *
   * <ul>
   *   <li>When Property is {@code java.io.tmpdir} is {@code test.txt} toFile.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#fileExists(File)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MailActivityBehavior.fileExists(File)"})
  public void testFileExists_whenPropertyIsJavaIoTmpdirIsTestTxtToFile() {
    // Arrange and Act
    boolean actualFileExistsResult =
        new MailActivityBehavior()
            .fileExists(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());

    // Assert
    assertFalse(actualFileExistsResult);
  }

  /**
   * Test {@link MailActivityBehavior#handleException(DelegateExecution, String, Exception, boolean,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link DelegateExecution#setVariable(String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#handleException(DelegateExecution, String,
   * Exception, boolean, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MailActivityBehavior.handleException(DelegateExecution, String, Exception, boolean, String)"
  })
  public void testHandleException_thenCallsSetVariable() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();

    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).setVariable(Mockito.<String>any(), Mockito.<Object>any());

    // Act
    mailActivityBehavior.handleException(
        execution, "Msg", new Exception(), true, "Exception Variable");

    // Assert
    verify(execution).setVariable(eq("Exception Variable"), isA(Object.class));
  }

  /**
   * Test {@link MailActivityBehavior#handleException(DelegateExecution, String, Exception, boolean,
   * String)}.
   *
   * <ul>
   *   <li>When {@link DelegateExecution}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MailActivityBehavior#handleException(DelegateExecution, String,
   * Exception, boolean, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MailActivityBehavior.handleException(DelegateExecution, String, Exception, boolean, String)"
  })
  public void testHandleException_whenDelegateExecution_thenThrowActivitiException() {
    // Arrange
    MailActivityBehavior mailActivityBehavior = new MailActivityBehavior();
    DelegateExecution execution = mock(DelegateExecution.class);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            mailActivityBehavior.handleException(
                execution, "Msg", new Exception(), false, "Exception Variable"));
  }

  /**
   * Test new {@link MailActivityBehavior} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link MailActivityBehavior}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailActivityBehavior.<init>()"})
  public void testNewMailActivityBehavior() {
    // Arrange and Act
    MailActivityBehavior actualMailActivityBehavior = new MailActivityBehavior();

    // Assert
    assertNull(actualMailActivityBehavior.attachments);
    assertNull(actualMailActivityBehavior.bcc);
    assertNull(actualMailActivityBehavior.cc);
    assertNull(actualMailActivityBehavior.charset);
    assertNull(actualMailActivityBehavior.exceptionVariableName);
    assertNull(actualMailActivityBehavior.from);
    assertNull(actualMailActivityBehavior.html);
    assertNull(actualMailActivityBehavior.htmlVar);
    assertNull(actualMailActivityBehavior.ignoreException);
    assertNull(actualMailActivityBehavior.subject);
    assertNull(actualMailActivityBehavior.text);
    assertNull(actualMailActivityBehavior.textVar);
    assertNull(actualMailActivityBehavior.to);
    assertNull(actualMailActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualMailActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualMailActivityBehavior.hasMultiInstanceCharacteristics());
  }
}
