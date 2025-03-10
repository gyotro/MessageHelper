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
    void addHeaderParam(String logName, String content){
        if (this.messageLog) {
            messageLog.addCustomHeaderProperty(logName, content)
        }
    }

    void addAttachment(String attachName, String contentBase64, String type = "application/pdf"){
        def pdfBytes = contentBase64.decodeBase64()
        def data = new ByteArrayDataSource(pdfBytes, type)
        def att = new DefaultAttachment(data)
        this.msg.addAttachmentObject(attachName, att)
    }
}
