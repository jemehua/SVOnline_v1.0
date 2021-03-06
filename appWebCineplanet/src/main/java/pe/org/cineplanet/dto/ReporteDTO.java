package pe.org.cineplanet.dto;

import java.util.Date;

public class ReporteDTO {

	private Integer contador;

	private Date fechaPedido;
	private String tipoDocumento;
	private String serie;
	private String numero;
	private String tipoPago;
	private String idEmpresa;
	private String nomEmpresa;
	private String idAgencia;
	private String nomAgencia;
	private String nombres;
	private Integer cantEntrada17;
	private Integer cantEntrada10;
	private Integer cantEntrada9;
	private Integer cantEntrada7;

	private Integer cantCombo11;
	private Integer cantCombo10;
	private Integer cantCombo9;
	private Integer cantCombo7;

	private Double totalVenta;

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(String idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Integer getCantEntrada17() {
		return cantEntrada17;
	}

	public void setCantEntrada17(Integer cantEntrada17) {
		this.cantEntrada17 = cantEntrada17;
	}

	public Integer getCantEntrada10() {
		return cantEntrada10;
	}

	public void setCantEntrada10(Integer cantEntrada10) {
		this.cantEntrada10 = cantEntrada10;
	}

	public Integer getCantEntrada9() {
		return cantEntrada9;
	}

	public void setCantEntrada9(Integer cantEntrada9) {
		this.cantEntrada9 = cantEntrada9;
	}

	public Integer getCantEntrada7() {
		return cantEntrada7;
	}

	public void setCantEntrada7(Integer cantEntrada7) {
		this.cantEntrada7 = cantEntrada7;
	}

	public Integer getCantCombo11() {
		return cantCombo11;
	}

	public void setCantCombo11(Integer cantCombo11) {
		this.cantCombo11 = cantCombo11;
	}

	public Integer getCantCombo10() {
		return cantCombo10;
	}

	public void setCantCombo10(Integer cantCombo10) {
		this.cantCombo10 = cantCombo10;
	}

	public Integer getCantCombo9() {
		return cantCombo9;
	}

	public void setCantCombo9(Integer cantCombo9) {
		this.cantCombo9 = cantCombo9;
	}

	public Integer getCantCombo7() {
		return cantCombo7;
	}

	public void setCantCombo7(Integer cantCombo7) {
		this.cantCombo7 = cantCombo7;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public Double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNomAgencia() {
		return nomAgencia;
	}

	public void setNomAgencia(String nomAgencia) {
		this.nomAgencia = nomAgencia;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNomEmpresa() {
		return nomEmpresa;
	}

	public void setNomEmpresa(String nomEmpresa) {
		this.nomEmpresa = nomEmpresa;
	}

}
