package cl.pedidos360.pedidos.dto;

public class PedidoEventDTO {

    private Long pedidoId;
    private String tipoEvento;
    private String fechaEvento;

    public PedidoEventDTO() {
    }

    public PedidoEventDTO(Long pedidoId, String tipoEvento, String fechaEvento) {
        this.pedidoId = pedidoId;
        this.tipoEvento = tipoEvento;
        this.fechaEvento = fechaEvento;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
}