package com.cpi.util.groovy

import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.msglog.MessageLogFactory

class MessageHelper
{
    Message msg
    MessageHelper(){}
    MessageHelper(Message msg)
    {
        this.msg = msg
    }

    MessageLogFactory logFactory

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
        def messageLog = this.logFactory.getMessageLog(this.msg)
        if (messageLog) {
            messageLog.addAttachmentAsString(logName, content, "text/plain")
        }
    }
}
