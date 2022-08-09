package mp.domain;

public enum EnumData {
	EXPIRATION_PRESTACIONES(604800000L); // 7 * 24 * 60 * 60 * 1000 == 7 dias
	
	public final Long data;	
	
	private EnumData(Long data) {			
		this.data = data;
	}
}
