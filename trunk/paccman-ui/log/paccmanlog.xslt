<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
    <xsl:template match="/">
        <html>
            <body>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th align="left">Date</th>
                        <th align="left">Logger</th>
                        <th align="left">Sequence</th>
                        <th align="left">Level</th>
                        <th align="left">Class</th>
                        <th align="left">Method</th>
                        <th align="left">Thread</th>
                    </tr>
                    <xsl:for-each select="log/record">
                        <tr>
                            <xsl:choose>
                                <xsl:when test="level = 'SEVERE'">
                                    <xsl:attribute name="bgcolor">red</xsl:attribute>
                                </xsl:when>
                                <xsl:when test="level = 'INFO'">
                                    <xsl:attribute name="bgcolor">lightgreen</xsl:attribute>
                                </xsl:when>
                                <xsl:when test="level = 'FINE'">
                                    <xsl:attribute name="bgcolor">lightgreen</xsl:attribute>
                                </xsl:when>
                                <xsl:when test="level = 'FINER'">
                                    <xsl:attribute name="bgcolor">lightgreen</xsl:attribute>
                                </xsl:when>
                                <xsl:when test="level = 'FINEST'">
                                    <xsl:attribute name="bgcolor">lightgreen</xsl:attribute>
                                </xsl:when>
                            </xsl:choose>
                            <td><xsl:value-of select="concat(substring(date, 0, 11), ' ', substring(date, 12, 18), '.', substring(millis, string-length(millis)-2, 3))"/></td>
                            <td><xsl:value-of select="logger"/></td>
                            <td><xsl:value-of select="sequence"/></td>
                            <td><xsl:value-of select="level"/></td>
                            <td><xsl:value-of select="class"/></td>
                            <td><xsl:value-of select="method"/></td>
                            <td><xsl:value-of select="thread"/></td>
                        </tr>
                        <tr>
                            <td colspan="7"><xsl:value-of select="message"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
    
</xsl:stylesheet>

