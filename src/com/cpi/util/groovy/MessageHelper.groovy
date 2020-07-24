package com.cpi.util.groovy

import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.msglog.MessageLogFactory

class MessageHelper {

        Message msg
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

            void logToMPL(String logName, String content) {
            def messageLog = this.logFactory.getMessageLog(this.msg)
            if (messageLog) {
                messageLog.addAttachmentAsString(logName, content, "text/plain")
            }
        }

}
