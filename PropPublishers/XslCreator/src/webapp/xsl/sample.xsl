<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:output method="text"/>

<xsl:variable name="crlf" select="'&#10;'"/>
<xsl:strip-space elements="*"/>
	<xsl:template match="bookstore">
		<xsl:apply-templates name="child::book"/>
	</xsl:template>
	
	<xsl:template match="book">
		<xsl:value-of select="current()/title"/>
		<xsl:value-of select="'|'"/>
		<xsl:value-of select="current()/category"/>
		<xsl:value-of select="'|'"/>
		<xsl:value-of select="current()/author"/>
		<xsl:value-of select="'|'"/>
		<xsl:value-of select="current()/price"/>
		<xsl:value-of select="'|'"/>
		
		<xsl:for-each select="current()/editions/edition">
			<xsl:value-of select="current()/editionNumber"/>
			<xsl:value-of select="'|'"/>
		</xsl:for-each>
		<xsl:value-of select="$crlf"/>
	</xsl:template>
</xsl:stylesheet>