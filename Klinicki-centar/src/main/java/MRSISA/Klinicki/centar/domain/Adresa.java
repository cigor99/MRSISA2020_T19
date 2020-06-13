package MRSISA.Klinicki.centar.domain;

public class Adresa {
	
	private long place_id;
	private String licence;
	private String osm_type;
	private long osm_id;
	private String lat;
	private String lon;
	private String display_name;
	
	public Adresa() {
		super();
	}
	public Adresa(long place_id, String licence, String osm_type, long osm_id, String lat, String lon,
			String display_name) {
		super();
		this.place_id = place_id;
		this.licence = licence;
		this.osm_type = osm_type;
		this.osm_id = osm_id;
		this.lat = lat;
		this.lon = lon;
		this.display_name = display_name;
	}
	public long getPlace_id() {
		return place_id;
	}
	public void setPlace_id(long place_id) {
		this.place_id = place_id;
	}
	public String getLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	public String getOsm_type() {
		return osm_type;
	}
	public void setOsm_type(String osm_type) {
		this.osm_type = osm_type;
	}
	public long getOsm_id() {
		return osm_id;
	}
	public void setOsm_id(long osm_id) {
		this.osm_id = osm_id;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	
	

}
