package com.tmp.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.trilogy.forddirect.util.ReloadableProperties;

public class TMPProperties {


	private static String PROPERTIES_FILE = "environment.properties";
	private ReloadableProperties nvoProperties = getProperties();

	private static TMPProperties singleInstance = new TMPProperties();
	private static final Logger LOG = Logger.getLogger(TMPProperties.class.getName());

    // Configurable Properties relevant to this utility class.
    public static final String DB_USER = "DB_USER";
    public static final String DB_PASSWORD= "DB_PASSWORD";
    public static final String DATA_SOURCE= "DATA_SOURCE";
    public static final String DB_USER_LOCAL= "DB_USER_LOCAL";
    public static final String DB_PASSWORD_LOCAL= "DB_PASSWORD_LOCAL";
    public static final String USE_DATA_SOURCE= "USE_DATA_SOURCE";
    public static final String DB_CONNECTION_URL = "DB_CONNECTION_URL";
    public static final String CACHE_REFRESH_INTERVAL_HOURS= "CACHE_REFRESH_INTERVAL_HOURS";
    public static final String DEFAULT_VEHICLE_IMAGE= "DEFAULT_VEHICLE_IMAGE";
    public static final String PDF_TEMPLATE= "PDF_TEMPLATE";
    public static final String EMAIL_TEMPLATE= "EMAIL_TEMPLATE";
    public static final String RESOURCE_HOST= "RESOURCE_HOST";
    public static final String EMAIL_FROM_FORD= "EMAIL_FROM_FORD";
    public static final String EMAIL_FROM_LINCOLN= "EMAIL_FROM_LINCOLN";
    public static final String EMAIL_HOST= "EMAIL_HOST";
    public static final String EMAIL_PORT= "EMAIL_PORT";
    public static final String EMAIL_FROMNAME_PREFIX = "EMAIL_FROMNAME_";
    public static final String NGP_SERVICE_PREFIX= "NGP_SERVICE_";
    public static final String VINX_SERVICE_PREFIX= "VINX_SERVICE_";
    public static final String DIG_SERVICE_PREFIX= "DIG_SERVICE_";
    public static final String VINDECODE_SERVICE= "VINDECODE_SERVICE";
    public static final String LINCOLN_PREF_SERVICE= "LINCOLN_PREF_SERVICE";
    public static final String COOKIE_NAME= "COOKIE_NAME";
    public static final String COOKIE_VALUE= "COOKIE_VALUE";
    public static final String COOKIEV2_VALUE= "COOKIEV2_VALUE";
    public static final String DOMAIN_MATCHES_PREFIX = "DOMAIN_MATCHES_";
    public static final String UDL_LINK_PREFIX= "UDL_LINK_";
    public static final String LPHONE_UDL_LINK_PREFIX= "LPHONE_UDL_LINK_";
    public static final String RESOURCE_HOST_CA= "RESOURCE_HOST_CA";
    public static final String CDSID_EXCLUDE_ENABLED= "CDSID_EXCLUDE_ENABLED";
    public static final String CACHE_REPLICATION= "CACHE_REPLICATION";
    public static final String HOST_PREFIX= "HOST_";
    public static final String PURGE_BEFORE_YEARS = "PURGE_BEFORE_YEARS";
    public static final String PUBLISH_DATE_MARGIN = "PUBLISH_DATE_MARGIN";
    public static final String UPDATE_DB = "UPDATE_DB";
    public static final String UPDATE_METRICS_EVENT = "UPDATE_METRICS_EVENT";
    public static final String LINCOLN_PREFRENCES_ENABLED = "LINCOLN_PREFRENCES_ENABLED";
    public static final String TRACK_PDF_EXCLUDED_CDSID = "TRACK_PDF_EXCLUDED_CDSID";
    public static final String FORDGID = "FORDGID";
    public static final String FORDGID_PASS = "FORDGIDPASS";
    public static final String LDAPURL = "LDAPURL";
    private static final String SEARCHINVENTORY_PREFIX = "SI_";
    public static final String OCA_FORD_EN = "OCA_FORD_EN";
    public static final String OCA_FORD_EN_CA = "OCA_FORD_EN_CA";
    public static final String OCA_FORD_FR_CA = "OCA_FORD_FR_CA";
    public static final String OCA_LINCOLN_EN = "OCA_LINCOLN_EN";
    public static final String OCA_LINCOLN_EN_CA = "OCA_LINCOLN_EN_CA";
    public static final String OCA_LINCOLN_FR_CA = "OCA_LINCOLN_FR_CA";
    
    public static final String VIBES_USERID = "VIBES_USERID";
    public static final String VIBES_USER_PASSWORD = "VIBES_USER_PASSWORD";
    public static final String VIBES_USER_SHORTCODE = "VIBES_USER_SHORTCODE";
    public static final String VIBES_USER_COMPANY_ID = "VIBES_USER_COMPANY_ID";
    public static final String VIBES_USER_EVENT_TYPE_US_EN = "VIBES_USER_EVENT_TYPE_US_EN";  
    public static final String VIBES_PROXY_HOST = "VIBES_PROXY_HOST";
    public static final String VIBES_PROXY_PORT = "VIBES_PROXY_PORT"; 
    public static final String VIBES_USER_EVENT_TYPE_CA_EN = "VIBES_USER_EVENT_TYPE_CA_EN";  
    public static final String VIBES_USER_EVENT_TYPE_CA_FR = "VIBES_USER_EVENT_TYPE_CA_FR";  
    
    private TMPProperties(){
	}

	public static TMPProperties getInstance(){
		return singleInstance;
	}

	/*public static ReloadableProperties getNVOProperties(){
		ReloadableProperties props = null;
        try {
        	props = PropertiesLoader.loadDetemplatizedProperties(PROPERTIES_FILE);
        	//LOG.log(Level.INFO, "Successfully loaded " + PROPERTIES_FILE);
        } catch(Exception e) {
        	//LOG.log(Level.SEVERE, "Could not load the propertes from the properties file " + PROPERTIES_FILE, e);
        	System.out.println("Could not load the propertes from the properties file " + PROPERTIES_FILE + " " + e.getMessage());
        }

	    return props;
	}
	*/
	public void setCacheSystemProperty() {
    	String replicationEnabled = getProperties().getProperty(CACHE_REPLICATION);
    	if("false".equalsIgnoreCase(replicationEnabled)) {
    		if(getProperties().getProperty(RESOURCE_HOST).contains("ngpdev08")) // identify for sandbox
            System.setProperty("DEPLOYED_SERVERS_FJF_CACHE_REPLICATION", "ngpdev08_1,localhost:1234;ngpdev08_2,localhost:2345");
              else 
    		System.setProperty("DEPLOYED_SERVERS_FJF_CACHE_REPLICATION", "ngpdev01_1,localhost:1234;ngpdev01_2,localhost:2345");
    	}

	}

	public static Properties getPropertiesFromClasspath(String propFileName){
	    Properties props = new Properties();
	    InputStream inputStream = TMPProperties.class.getResourceAsStream(propFileName);

	    if (inputStream == null) {
	       LOG.log(Level.SEVERE, propFileName + " not found in the classpath");
	    }
	    else{
	    	try {
				props.load(inputStream);
			} catch (IOException e) {
				LOG.log(Level.SEVERE, propFileName + " cannot be loaded from the classpath", e);
			}
	    }
	    return props;
	}

	public int getCacheRefreshRate(){
		String hours = getProperties().getProperty(CACHE_REFRESH_INTERVAL_HOURS);
		return Integer.parseInt(hours);
	}

	public String getDefaultVehicleImage(){
		String image = getProperties().getProperty(DEFAULT_VEHICLE_IMAGE);
		return image;
	}


	public ReloadableProperties getProperties() {
		return nvoProperties;
	}

	public String getDBUser(){
		return getProperties().getProperty(DB_USER);
	}

	public String getDBPassword(){
		return getProperties().getProperty(DB_PASSWORD);
	}

	public String getDBDataSource(){
		return getProperties().getProperty(DATA_SOURCE);
	}

	public boolean useDatasource(){
		String prop =  getProperties().getProperty(USE_DATA_SOURCE);
		return Boolean.parseBoolean(prop);
	}

	public String getDBConnectionURL(){
		return getProperties().getProperty(DB_CONNECTION_URL);
	}

	public String getLocalDBUser(){
		return getProperties().getProperty(DB_USER_LOCAL);
	}

	public String getLocalDBPassword(){
		return getProperties().getProperty(DB_PASSWORD_LOCAL);
	}


	public String getSmtpHost() {
		return getProperties().getProperty(EMAIL_HOST);
	}

	public String getSmtpPort() {
		return getProperties().getProperty(EMAIL_PORT);
	}

	public String getEmailFromFord() {
		return getProperties().getProperty(EMAIL_FROM_FORD);
	}

	public String getEmailFromLincoln() {
		return getProperties().getProperty(EMAIL_FROM_LINCOLN);
	}

	/*public String getResourceHost(Country country) {
		if(country.equals(Country.CA))
			return getProperties().getProperty(RESOURCE_HOST_CA);
		else
			return getProperties().getProperty(RESOURCE_HOST);
	}
	
	public String getHost(Country country, Brand brand, VehicleType vehicleType) {
		return getProperties().getProperty(HOST_PREFIX+vehicleType.name().toUpperCase()+"_"+brand.name().toUpperCase()
				+"_"+country.name().toUpperCase());
	}*/

	public String getLincolnPrefEndPoint() {
		return getProperties().getProperty(LINCOLN_PREF_SERVICE);
	}

	public String getCookieName() {
		return getProperties().getProperty(COOKIE_NAME);
	}

	public String getCookieValue() {
		String cookie = null;

		cookie = getProperties().getProperty(COOKIE_VALUE);

		LOG.info("Cookie Value: " + cookie);

		return cookie;
	}
	
	/**
	 * This method used to get SHA256 cookie value from property file
	 * @return CookieV2 Value
	 */
	public String getCookieV2Value(){
		String cookieV2 = "";
		
		if(null != getProperties().getProperty(COOKIEV2_VALUE))
			cookieV2 = getProperties().getProperty(COOKIEV2_VALUE);
		
		LOG.info("CookieV2 Value: " + cookieV2);
		
		return cookieV2;
	}	

	public String getVinDecodeEndpoint() {
		return getProperties().getProperty(VINDECODE_SERVICE);
	}

	/*public ServicesURL getVinXEndpoint(Country country) {
		String key = VINX_SERVICE_PREFIX + country.toString();
		return ServicesURL.valueOf(getProperties().getProperty(key));
	}

	public ServicesURL getNGPEndpoint(Country country) {
		String key = NGP_SERVICE_PREFIX + country.toString();
		return ServicesURL.valueOf(getProperties().getProperty(key));
	}

	public String getDIGEndpoint(Country country) {
		String key = DIG_SERVICE_PREFIX + country.toString();
		return getProperties().getProperty(key);
	}

	
	
	public String getEmailFromName(VehicleType vehicleType, Brand brand)  {
		String key = EMAIL_FROMNAME_PREFIX + vehicleType.toString()+"_"+brand.name().toUpperCase();
		return getProperties().getProperty(key);
	}*/

	
/*
	public String getDomainPattern(Country country) {
		String key = DOMAIN_MATCHES_PREFIX + country.toString();

		return getProperties().getProperty(key);
	}

	public Map<Country, String> getCountryDomainPatterns() {
		Map<Country, String> patterns = new HashMap<Country, String>();

		patterns.put(Country.CA, this.getDomainPattern(Country.CA));
		patterns.put(Country.US, this.getDomainPattern(Country.US));

		return patterns;
	}
	
	public String getDomainPattern(Brand brand) {
		String key = DOMAIN_MATCHES_PREFIX + brand.toString().toUpperCase();

		return getProperties().getProperty(key);
	}

	public Map<Brand, String> getBrandDomainPatterns() {
		Map<Brand, String> patterns = new HashMap<Brand, String>();
		patterns.put(Brand.Ford, this.getDomainPattern(Brand.Ford));
		patterns.put(Brand.Lincoln, this.getDomainPattern(Brand.Lincoln));
		return patterns;
	}

	public String getDomainPattern(VehicleType vehicleType) {
		String key = DOMAIN_MATCHES_PREFIX + vehicleType.toString();
		return getProperties().getProperty(key);
	}

	public Map<VehicleType, String> getVehicleTypeDomainPatterns() {
		Map<VehicleType, String> patterns = new HashMap<VehicleType, String>();
		patterns.put(VehicleType.NVO, this.getDomainPattern(VehicleType.NVO));
		patterns.put(VehicleType.CPO, this.getDomainPattern(VehicleType.CPO));
		patterns.put(VehicleType.NVC, this.getDomainPattern(VehicleType.NVC));
		return patterns;
	}

	public String getUDLEndpoint(Country country) {
		String key = UDL_LINK_PREFIX + country.toString();
		return getProperties().getProperty(key);
	}
	
	public String getLphoeUDLEndpoint(Country country) {
		String key = LPHONE_UDL_LINK_PREFIX + country.toString();
		return getProperties().getProperty(key);
	}
	*/
	public boolean isCdsidExcludeEnabled() {
		String cdsidExcludeEnabled =  getProperties().getProperty(CDSID_EXCLUDE_ENABLED);
		return Boolean.parseBoolean(cdsidExcludeEnabled);
	}
	
	public String getPurgeBeforeYears() {
		return getProperties().getProperty(PURGE_BEFORE_YEARS);
	}
	
	public String getPublishDateMargin() {
		return getProperties().getProperty(PUBLISH_DATE_MARGIN);
	}
	
	public String canUpdateDB() {
		return getProperties().getProperty(UPDATE_DB);
	}
	
	public String canUpdateMetricsEvent() {
		return getProperties().getProperty(UPDATE_METRICS_EVENT);
	}

	public boolean isLincolnPrefrencesEnabled() {
		return "true".equalsIgnoreCase(getProperties().getProperty(LINCOLN_PREFRENCES_ENABLED));
	}

	public boolean isTracKPDFMetricsForExcludedIds() {
		return "true".equalsIgnoreCase(getProperties().getProperty(TRACK_PDF_EXCLUDED_CDSID));
	}
	
	public boolean useTestEmail() {
		return "true".equalsIgnoreCase(getProperties().getProperty("USETESTEMAIL"));
	}

	public String getFordGID() {
		return getProperties().getProperty(FORDGID);
	}
	
	public String getFordGIDPass() {
		return getProperties().getProperty(FORDGID_PASS);
	}

	public String getLDAPURL() {
		return getProperties().getProperty(LDAPURL);
	}
	
	/*public String getSearchInventoryURL(Country country, Brand brand){
		return getProperties().getProperty(SEARCHINVENTORY_PREFIX+brand.name().toUpperCase()
				+"_"+country.name().toUpperCase());
	}*/
	
	public String getVibesUserID() {
		return getProperties().getProperty(VIBES_USERID);
	}
	
	public String getVibesUserPassword() {
		return getProperties().getProperty(VIBES_USER_PASSWORD);
	}
	
	public String getVibesUserShortCode() {
		return getProperties().getProperty(VIBES_USER_SHORTCODE);
	}

	 public  String getVibesUserCompanyId() {
	   return  getProperties().getProperty(VIBES_USER_COMPANY_ID);
	}

	public String getVibesProxyHost() {
		return  getProperties().getProperty(VIBES_PROXY_HOST);
		
	}

	public String getVibesProxyPort() {
		return getProperties().getProperty(VIBES_PROXY_PORT);
	}
	
	


	public String getOcaFordEnUrl() {
		return getProperties().getProperty(OCA_FORD_EN);
	}
	
	public String getOcaFordEnCaUrl() {
		return getProperties().getProperty(OCA_FORD_EN_CA);
	}
	
	public String getOcaFordFrCaUrl() {
		return getProperties().getProperty(OCA_FORD_FR_CA);
	}
	
	public String getOcaLincolnEnUrl() {
		return getProperties().getProperty(OCA_LINCOLN_EN);
	}
	
	public String getOcaLincolnEnCaUrl() {
		return getProperties().getProperty(OCA_LINCOLN_EN_CA);
	}
	
	public String getOcaLincolnFrCaUrl() {
		return getProperties().getProperty(OCA_LINCOLN_FR_CA);
	}

	public  String getVibesUserEventTypeUsEn() {
		return getProperties().getProperty(VIBES_USER_EVENT_TYPE_US_EN);
	}

	public  String getVibesUserEventTypeCaEn() {
		return getProperties().getProperty(VIBES_USER_EVENT_TYPE_CA_EN);
	}

	public  String getVibesUserEventTypeCaFr() {
		return getProperties().getProperty(VIBES_USER_EVENT_TYPE_CA_FR);
	}
	
	
}