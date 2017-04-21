package mkl.testarea.verapdf.signature;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.verapdf.core.EncryptedPdfException;
import org.verapdf.core.ModelParsingException;
import org.verapdf.core.ValidationException;
import org.verapdf.pdfa.Foundries;
import org.verapdf.pdfa.PDFAParser;
import org.verapdf.pdfa.PDFAValidator;
import org.verapdf.pdfa.PdfBoxFoundryProvider;
import org.verapdf.pdfa.results.ValidationResult;

/**
 * @author mkl
 */
public class ValidateSignatureByteRange
{
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    /**
     * <a href="http://stackoverflow.com/questions/43522415/itext-digital-signature-corrupts-pdf-a-2b">
     * iText digital signature corrupts PDF/A 2b
     * </a>
     * <br/>
     * <a href="https://www.docdroid.net/Id5kxvO/samplepdfadocsigned.pdf.html">
     * samplePdfaDoc.signed.pdf
     * </a>
     * <p>
     * Indeed, veraPDF (version 1.5.4 based on PDFBox) indicates a validation failure.
     * But this failure is due to a completely wrong veraPDF implementation of the
     * signature byte ranges test.
     * </p>
     */
    @Test
    public void testSamplePdfaDocByPeterVeselinovic() throws IOException, ModelParsingException, EncryptedPdfException, ValidationException
    {
        PdfBoxFoundryProvider.initialise();

        try (   InputStream resource = getClass().getResourceAsStream("samplePdfaDoc.signed.pdf");
                PDFAParser parser = Foundries.defaultInstance().createParser(resource)  )
        {
            PDFAValidator validator = Foundries.defaultInstance().createValidator(parser.getFlavour(), false);
            ValidationResult result = validator.validate(parser);

            Assert.assertTrue("Result has been mis-analysed as not compliant.", result.isCompliant());
        }
    }

}
