package cpiUtilties

import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.msglog.MessageLog
import com.sap.it.api.msglog.MessageLogFactory
import javax.mail.util.ByteArrayDataSource
import org.apache.camel.impl.DefaultAttachment

/*
    Utility Class for CPI message Processing
 */

class MessageHelper
{
    Message msg
    MessageLog messageLog
    MessageHelper(){}
    MessageHelper(Message msg, msgLog)
    {
        this.msg = msg
     //   this.messageLog = ((MessageLogFactory) messageLogFactory).getMessageLog(msg)
        this.messageLog = msgLog
    }



    String getHeaderValue(String headerName, String defaultValue) {
        def headerValue = this.msg.getHeaders()[headerName]
        if(headerValue)
            headerValue
        else if(defaultValue)
            defaultValue
        else
            throw new RuntimeException("Header $headerName do not exist")
    }

    String getHeaderValue(String headerName) {
        getHeaderValue(headerName, null)
    }

    String getParamValue(String paramName) {
        getParamValue(paramName, null)
    }

    String getParamValue(String paramName, String defaultValue) {
        def paramValue = this.msg.getProperties()[paramName]
        if(paramValue)
            paramValue
        else if(defaultValue)
            defaultValue
        else
            throw new RuntimeException("Header $paramValue do not exist")
    }
    void addAttachmentAsString(String logName, String content)
    {
        if (this.messageLog) {
            messageLog.addAttachmentAsString(logName, content, "text/plain")
        }
    }
    void addHeaderParam(String logName, Object content){
        if (this.messageLog) {
            messageLog.addCustomHeaderProperty(logName, content.toString())
        }
    }

    void addAttachment(String attachName, String contentBase64, String type = "application/pdf"){
        def pdfBytes = contentBase64.decodeBase64()
        def data = new ByteArrayDataSource(pdfBytes, type)
        def att = new DefaultAttachment(data)
        this.msg.addAttachmentObject(attachName, att)
    }

    static String addBom(String sIn){
        List bom = [0xEF,0xBB,0xBF].toList()
        byte[] bIn = sIn.getBytes("utf-8")
        bom.addAll(bIn.toList())
        byte[] total = bom.toArray()
        return new String(total, "utf-8")
    }

    static String addCrlf(String sIn){
        def crlf = [(byte)0x0D, (byte)0x0A].toArray() as byte[]
        return sIn + new String(crlf)
    }

    static String JDBCResultSetToCSV(String xmlContent){
        // Parse the XML from the String
        def xml = new XmlSlurper().parseText(xmlContent)

// Grab all the <row> elements under <select_response>
        def rows = xml.select_response.row

// Determine the full set of column names (unique child tags of any <row>)
        def headers = rows
                .collect { row -> row.children().collect { it.name() } }
                .flatten()
                .unique()

// Use StringWriter to accumulate CSV in-memory
        def writer = new StringWriter()
        def pw = new PrintWriter(writer)

// Write header line
        pw.println(headers.join(','))

// Write each row's values in header order
        rows.each { row ->
            def values = headers.collect { col ->
                // Pull text or empty if missing
                def v = row."$col"*.text().join('')
                // CSV-escape if needed
                if (v.contains('"') || v.contains(',')) {
                    v = '"' + v.replaceAll('"','""') + '"'
                }
                return v
            }
            pw.println(values.join(','))
        }

        pw.flush()
        return writer.toString()
    }

    void setHeader(String headerName, Object headerValue){
        this.msg.setHeader(headerName, headerValue)
    }
    void setProperty(String propertyName, Object propertyValue){
        this.msg.setProperty(propertyName, propertyValue)
    }
    void setBody(Object body){
        this.msg.setBody(body)
    }
    void getPropandMPLog(String prop){
        String sProp = getParamValue(prop, "notSet")
        this.messageLog.addCustomHeaderProperty(prop, sProp)
    }
    void getHeaderandMPLog(String header){
        String sHeader = getHeaderValue(header, "notSet")
        this.messageLog.addCustomHeaderProperty(header, sHeader)
    }
    void getPropandMPLog(List<String> prop){
        prop.each { String sProp ->
            getPropandMPLog(sProp)
        }
    }
    void getHeaderandMPLog(List<String> header){
        header.each { String sHeader ->
            getHeaderandMPLog(sHeader)
        }
    }
}
