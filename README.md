# SAP Cloud Integration Utility Library

A comprehensive utility library for SAP Cloud Platform Integration (CPI) that simplifies common integration tasks and operations.

## Table of Contents
- [Installation](#installation)
- [Features](#features)
- [Usage](#usage)
  - [MessageHelper](#messagehelper)
  - [DateHelper](#datehelper)
  - [JsonHelper](#jsonhelper)
  - [ConverterUtil](#converterutil)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)

## Installation

1. Clone this repository
2. Build the project using Maven:
   ```
   mvn clean install
   ```
3. Add the generated JAR to your SAP CPI project's dependencies

## Features

- **MessageHelper**: Simplify message processing and manipulation in SAP CPI
- **DateHelper**: Comprehensive date and time manipulation utilities
- **JsonHelper**: Streamlined JSON parsing and manipulation
- **ConverterUtil**: Type conversion utilities for common data formats

## Usage

### MessageHelper

Simplify message processing in SAP CPI flows with these utility methods:

```groovy
def messageHelper = new MessageHelper(message, messageLog)

// Get header values with optional defaults
String headerValue = messageHelper.getHeaderValue("headerName", "defaultValue")

// Get property values with optional defaults
String paramValue = messageHelper.getParamValue("paramName", "defaultValue")

// Add attachments
messageHelper.addAttachment("document.pdf", base64Content, "application/pdf")

// Add string content as attachment
messageHelper.addAttachmentAsString("log.txt", "Log content")

// Add custom header parameters
messageHelper.addHeaderParam("customHeader", "value")

// Convert JDBC result set to CSV
String csv = MessageHelper.JDBCResultSetToCSV(xmlContent)
```

### DateHelper

Handle date and time operations with ease:

```groovy
// Get current date/time
String currentDate = DateHelper.getActualDate.get()
String currentDateTime = DateHelper.getActualDateTime.get()

// Format dates
String formattedDate = DateHelper.parseLocalDate("2023-01-15", "yyyy-MM-dd", "dd/MM/yyyy")

// Time zone conversions
String zonedTime = DateHelper.getTimeOfZoneWithZoneId("Europe/Rome")

// Date differences
LocalDate startDate = LocalDate.of(2023, 1, 1)
LocalDate endDate = LocalDate.of(2023, 1, 15)
String daysBetween = DateHelper.diffPeriodDays.apply(startDate, endDate)
```

### JsonHelper

Easily work with JSON data:

```groovy
// Parse JSON and extract values
String jsonValue = JsonHelper.lookUpValueFieldFromJson(jsonString, "fieldName")

// Extract array of values
Object[] values = JsonHelper.lookUpValuesFieldFromJson(jsonArrayString, "fieldName")

// Convert epoch time to formatted date
String formattedDate = JsonHelper.convertEpoch_to_date(1675200000000, "yyyy-MM-dd HH:mm:ss")
```

### ConverterUtil

Convert between different data types and handle streams:

```groovy
// Convert String to InputStream
InputStream inputStream = ConverterUtil.getInputStreamFromString("content")

// Convert byte array to InputStream
byte[] bytes = ...
InputStream stream = ConverterUtil.getInputStreamFromByte(bytes)

// Convert InputStream to byte array
byte[] data = ConverterUtil.InputStreamToByteConverter(inputStream)

// Handle Base64 content
OutputStream output = ConverterUtil.Base64ToFile(inputStream, outputStream)
```

## Examples

### Processing a Message with Attachments

```groovy
def messageHelper = new MessageHelper(message, messageLog)

try {
    // Get content from message
    String content = message.getBody(String.class)
    
    // Process content...
    
    // Add result as attachment
    messageHelper.addAttachmentAsString("result.txt", processedContent)
    
    // Add processing timestamp
    messageHelper.addHeaderParam("processedAt", DateHelper.getActualDateTime.get())
    
} catch (Exception e) {
    messageHelper.addAttachmentAsString("error.log", "Error: ${e.message}")
    throw e
}
```

### Handling Date Operations

```groovy
// Get first day of next month
String firstDayNextMonth = DateHelper.getFirstDayOfNextMonth.get()

// Format a date string from one format to another
String reformattedDate = DateHelper.parseLocalDate(
    "15-01-2023", 
    "dd-MM-yyyy", 
    "yyyy/MM/dd"
)
```

