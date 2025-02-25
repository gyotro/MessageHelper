package cpiUtilties

import java.util.jar.JarInputStream
import java.util.zip.ZipEntry

class ConverterUtil {
    static InputStream getInputStreamFromByte(byte[] myByte)
    {
        InputStream myInputStream = new ByteArrayInputStream(myByte)
        return myInputStream
    }
    static InputStream getInputStreamFromString(String myString) throws UnsupportedEncodingException
    {
        InputStream myInputStream = new ByteArrayInputStream(myString.getBytes("UTF-8"))
        return myInputStream
    }

    static InputStream getInputStreamFromString(String myString, String sEncoding) throws UnsupportedEncodingException
    {
        InputStream myInputStream = new ByteArrayInputStream(myString.getBytes(sEncoding))
        return myInputStream
    }
    static InputStream byteToInputStream(byte[] bIn) {
        InputStream myInputStream = new ByteArrayInputStream(bIn)
        return (myInputStream)
    }
    static byte[] InputStreamToByteConverter(InputStream is)
            throws IOException {
        /**
         * Per fare questa conversione si potrebbe anche utilizzare il package
         * Apache: commons-io, Classe IOUtils:
         *
         * InputStream is; byte[] bytes = IOUtils.toByteArray(is);
         *
         */
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] byteRes = new byte[16384];
        int nRead;
        while ((nRead = is.read(byteRes, 0, byteRes.length)) != -1) {
            buffer.write(byteRes, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }

    static OutputStream ConvertByteToOutputStream(byte[] bPDF,
                                                         OutputStream outputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            baos.write(bPDF);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            baos.writeTo(outputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return outputStream;
    }

    static OutputStream Base64ToFile(InputStream input, OutputStream output) {
        String sBase64In = inputStream2StringConverter(input);
        byte[] bDecoded = convertAndDecodeBase64StringToByte(sBase64In);
        try {
            output = ConvertByteToOutputStream(bDecoded, output)
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output
    }

    static Vector<OutputStream> Unzip(InputStream input) {

        OutputStream out = null;
        Vector<OutputStream> vOut = new Vector<OutputStream>()
        JarInputStream newJar;
        int BUFFER = 2048;
        try {
            newJar = new JarInputStream(input)
            ZipEntry ze = newJar.getNextEntry()

            // FileOutputStream ots = new FileOutputStream(ze.getName());
            byte[] bOut = new byte[BUFFER]
            // BufferedOutputStream bo = new BufferedOutputStream();
            // out = new BufferedOutputStream(ots);
            long size = ze.getSize()
            int n = 0;
            long count = 0
            while ((count = newJar.read(bOut, 0, BUFFER)) != -1)
            // {
                out.write(bOut, 0, n)
            count += n
            // }

            out.flush()
            out.close()

            vOut.add(out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return vOut

    }
    static byte[] OutputStreamtoByteConverter(OutputStream outputStream){
        if (outputStream instanceof ByteArrayOutputStream) {
            return ((ByteArrayOutputStream) outputStream).toByteArray()
        } else {
            // Handle case where OutputStream is not ByteArrayOutputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            outputStream.flush(); // Ensure all data is written
            ((ByteArrayOutputStream) outputStream).writeTo(byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }
    }
    static String convertByte2Base64String(byte[] dataIn) {
        return Base64.encoder.encodeToString(dataIn)
    }
    static byte[] convertAndDecodeBase64StringToByte(String sBase64String) {
        return Base64.decoder.decode(sBase64String)
    }
    static String GUIDgenerate(boolean bHyphenMantain) {
        // Se il booleano bHyphenMantain ï¿½ false il risultato conterrï¿½ i segni
        // '-' tipici di un GUID in SAP PI, altrimenti NO

        String sGUID = "";
        UUID idOne = UUID.randomUUID()

        if (bHyphenMantain)
            sGUID = idOne.toString().toUpperCase()
        else
            sGUID = idOne.toString().replaceAll("-", "").toUpperCase()

        return sGUID
    }
    static String inputStream2StringConverter(InputStream is, String encoding = "ISO-8859-1") {
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    encoding));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line)
            }
            br.close()
            return sb.toString()
        } catch (Exception e) {
            return e.getMessage()
        } finally {
            try {
                is.close()
            } catch (Exception e) {
                return e.getMessage()
            }
        }
    }
}
