package eu.domibus.ebms3.receiver;

import eu.domibus.pki.CertificateService;
import eu.domibus.pki.PKIUtil;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.bind.JAXBContext;
import java.math.BigInteger;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Properties;

/**
 * @author idragusa
 * @since 3.3
 */
@RunWith(JMockit.class)
public class TrustSenderInterceptorTest {

    @Injectable
    Properties domibusProperties;

    @Injectable
    CertificateService certificateService;

    @Injectable
    protected JAXBContext jaxbContextEBMS;

    @Tested
    TrustSenderInterceptor trustSenderInterceptor;

    PKIUtil pkiUtil = new PKIUtil();

    @Before
    public void init() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Test
    public void testCheckCertificateValidityEnabled() throws Exception {
        final X509Certificate certificate = pkiUtil.createCertificate(BigInteger.ONE, null);
        final X509Certificate expiredCertificate = pkiUtil.createCertificate(BigInteger.ONE, new DateTime().minusDays(2).toDate(), new DateTime().minusDays(1).toDate(), null);

        new Expectations() {{
            domibusProperties.getProperty(TrustSenderInterceptor.DOMIBUS_SENDER_CERTIFICATE_VALIDATION_ONRECEIVING, "true");
            result = "true";
            certificateService.isCertificateValid(certificate);
            result = true;
            certificateService.isCertificateValid(expiredCertificate);
            result = false;

        }};

        Assert.assertTrue(trustSenderInterceptor.checkCertificateValidity(certificate, "test sender", false));
        Assert.assertFalse(trustSenderInterceptor.checkCertificateValidity(expiredCertificate, "test sender", false));
    }

    @Test
    public void testCheckCertificateValidityDisabled() throws Exception {
        final X509Certificate expiredCertificate = pkiUtil.createCertificate(BigInteger.ONE, new DateTime().minusDays(2).toDate(), new DateTime().minusDays(1).toDate(), null);

        new Expectations() {{
            domibusProperties.getProperty(TrustSenderInterceptor.DOMIBUS_SENDER_CERTIFICATE_VALIDATION_ONRECEIVING, "true");
            result = "false";
        }};
        Assert.assertTrue(trustSenderInterceptor.checkCertificateValidity(expiredCertificate, "test sender", false));
    }

    @Test
    public void testCheckSenderPartyTrust() throws Exception {
        final X509Certificate certificate = pkiUtil.createCertificate(BigInteger.ONE, null);

        new Expectations() {{
            domibusProperties.getProperty(TrustSenderInterceptor.DOMIBUS_SENDER_TRUST_VALIDATION_ONRECEIVING, "false");
            result = "true";
        }};

        Assert.assertTrue(trustSenderInterceptor.checkSenderPartyTrust(certificate, "GlobalSign", "messageID123", false));
        Assert.assertFalse(trustSenderInterceptor.checkSenderPartyTrust(certificate, "test sender", "messageID123", false));
    }

    @Test
    public void testCheckSenderPartyTrustDisabled() throws Exception {
        final X509Certificate certificate = pkiUtil.createCertificate(BigInteger.ONE, null);

        new Expectations() {{
            domibusProperties.getProperty(TrustSenderInterceptor.DOMIBUS_SENDER_TRUST_VALIDATION_ONRECEIVING, "false");
            result = "false";
        }};

        Assert.assertTrue(trustSenderInterceptor.checkSenderPartyTrust(certificate, "test sender", "messageID123", false));
    }
}