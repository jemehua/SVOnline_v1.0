package pe.org.cineplanet.dto;

public class VentaDTO {

	private String idCodigo;
	private String otorgado;
	private String fecValidez;
	private String nombreVale;
	private String descVale;
	private String fecVenta;
	private String diasValidos;
	private Integer tipoVale;

	public String getIdCodigo() {
		return idCodigo;
	}

	public void setIdCodigo(String idCodigo) {
		this.idCodigo = idCodigo;
	}

	public String getOtorgado() {
		return otorgado;
	}

	public void setOtorgado(String otorgado) {
		this.otorgado = otorgado;
	}

	public String getFecValidez() {
		return fecValidez;
	}

	public void setFecValidez(String fecValidez) {
		this.fecValidez = fecValidez;
	}

	public String getNombreVale() {
		return nombreVale;
	}

	public void setNombreVale(String nombreVale) {
		this.nombreVale = nombreVale;
	}

	public String getDescVale() {
		return descVale;
	}

	public void setDescVale(String descVale) {
		this.descVale = descVale;
	}

	public String getFecVenta() {
		return fecVenta;
	}

	public void setFecVenta(String fecVenta) {
		this.fecVenta = fecVenta;
	}

	public String getDiasValidos() {
		return diasValidos;
	}

	public void setDiasValidos(String diasValidos) {
		this.diasValidos = diasValidos;
	}

	public Integer getTipoVale() {
		return tipoVale;
	}

	public void setTipoVale(Integer tipoVale) {
		this.tipoVale = tipoVale;
	}

}
