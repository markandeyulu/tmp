<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:PDFConvertor="com.test.PDFConvertor">

	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simple"
					page-height="20cm" page-width="14.5cm" margin-left="0.2cm"
					margin-right="0.2cm">
					<fo:region-body margin-top="1cm" margin-bottom="1cm" />
					<fo:region-before extent="1cm" />
					<fo:region-after extent="1cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simple"
				initial-page-number="1">
				<fo:static-content flow-name="xsl-region-before">
					<fo:block font-size="3pt" text-align="center"></fo:block>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-after">
					<fo:block font-size="3pt" text-align="center">
						<fo:page-number />
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block text-align="left" font-size="5pt"
						space-after="2em">
						<xsl:text> Sub : IAUT Requirements Update. </xsl:text>
					</fo:block>
					<fo:block>
						<fo:block-container>
							<fo:block font-size="5pt" text-align="left"
								space-after="1em">
								<xsl:text>Hi All,</xsl:text>
							</fo:block>
							<fo:block font-size="5pt" space-after="1em">
								<xsl:text>The below  </xsl:text>
								<xsl:value-of select="PDFConvertor:getSize()" />
								<xsl:text space-after="1em">  Requirements are in the pending Status </xsl:text>
							</fo:block>
							<fo:block font-size="5pt" space-after="1em">
								<xsl:value-of
									select="PDFConvertor:getRequirements()" />
							</fo:block>
						</fo:block-container>
					</fo:block>
					<fo:block>
						<fo:block-container>
							<fo:block font-size="5pt" text-align="left">
								<xsl:text>suitable  profiles for the above mentioned requirements are needed.
													</xsl:text>
							</fo:block>

							<fo:block font-size="5pt" text-align="left"
								space-after="1em">
								<xsl:text>Please Do not reply, This is an auto generated mail.</xsl:text>
							</fo:block>
						</fo:block-container>
					</fo:block>
					<fo:block font-size="5pt" font-weight="bold"
						text-align="left" space-after="0.5em">
						<xsl:text>Regards,</xsl:text>
					</fo:block>
					<fo:block font-size="5pt" font-weight="bold"
						text-align="left">
						<xsl:text>IAUT.</xsl:text>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>