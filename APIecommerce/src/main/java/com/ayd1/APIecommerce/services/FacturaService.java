/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ayd1.APIecommerce.models.DatosFacturacion;
import com.ayd1.APIecommerce.models.Envio;
import com.ayd1.APIecommerce.models.EstadoEnvio;
import com.ayd1.APIecommerce.models.LineaVenta;
import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.TiendaConfig;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.Venta;
import com.ayd1.APIecommerce.models.request.ProductoVentaRequest;
import com.ayd1.APIecommerce.models.request.VentaRequest;
import com.ayd1.APIecommerce.repositories.DatosFacturacionRepository;
import com.ayd1.APIecommerce.repositories.EnvioRepository;
import com.ayd1.APIecommerce.repositories.EstadoEnvioRepository;
import com.ayd1.APIecommerce.repositories.LineaVentaRepository;
import com.ayd1.APIecommerce.repositories.ProductoRepository;
import com.ayd1.APIecommerce.repositories.VentaRepository;
import com.ayd1.APIecommerce.services.reportes.Reporte;
import com.ayd1.APIecommerce.services.reportes.imprimibles.FacturaImprimible;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class FacturaService extends Reporte {

    @Autowired
    private LineaVentaRepository lineaVentaRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DatosFacturacionRepository datosFacturacionRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private EstadoEnvioRepository estadoEnvioRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TiendaConfigService tiendaConfigService;

    @Autowired
    private FacturaImprimible facturaImprimible;

    public Venta getVenta(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Id invalido.");
        }

        Optional<Venta> ventaSearch = this.ventaRepository.findById(id);

        if (ventaSearch.isEmpty()) {
            throw new Exception("Venta no encontrada.");
        }

        return ventaSearch.get();
    }

    public List<Venta> getVentasByUser(Long idUsuario) throws Exception {
        if (idUsuario == null || idUsuario <= 0) {
            throw new Exception("Id invalido.");
        }
        // Verificamos que el usuario exista
        Usuario usuario = this.usuarioService.getUsuario(idUsuario);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado.");
        }
        return this.ventaRepository.findAllByDatosFacturacion_Usuario_Id(idUsuario);
    }

    public byte[] getFactura(Long ventId) throws Exception {
        Venta venta = this.getVenta(ventId);
        DatosFacturacion datosFacturacion = venta.getDatosFacturacion();
        List<LineaVenta> desgloce = venta.getLineaVentas();

        return this.facturaImprimible.init(venta,
                datosFacturacion,
                desgloce);
    }

    @Transactional(rollbackOn = Exception.class)
    public byte[] guardarVenta(VentaRequest ventaRequest) throws Exception {

        // validamos elobjeto
        this.validar(ventaRequest);
        /*
         * la venta volatil es la creacion de la venta con datos iniciales,
         * se utiiza para que exista una referencia en la bd y en spring a la cual
         * apuntar
         * con los distintos objetos que se crearan luego
         */
        Venta venta = new Venta(0.00, 0.00,
                ventaRequest.getProductos().size(), 0.00);
        /*
         * la venta volatil2 se utilizara para guardar el desgloce de la factura
         * y el valor total real de la factura
         */
        Venta save = this.ventaRepository.save(venta);

        // validar la creacion de la venta
        if (save.getId() <= 0) {
            throw new Exception("No se pudo generar la factura");
        }

        ArrayList<LineaVenta> desglose = this.crearDesgloce(save, ventaRequest);
        Double total = this.calcularTotal(desglose);
        Integer cantidadDeProductos = this.calcularCantidadProductos(desglose);
        Double totalImpuestos = this.calcularTotalDeImpuestoPagado(desglose);
        Double cuotaPagoContraEntrega = 0.00;

        // si el pago contra entrega esta activado entonces debemos subir a la cuota
        if (ventaRequest.getPagoContraEntrega() && !ventaRequest.getRetiroEnTienda()) {
            // traer las configs del sistema
            TiendaConfig tiendaConfig = this.tiendaConfigService.getTiendaConfig();
            cuotaPagoContraEntrega = tiendaConfig.getPrecioPagoContraEntrega();
        }

        // anadimos las propiedades de la venta
        save.setValorTotal(total + cuotaPagoContraEntrega);
        save.setLineaVentas(desglose);
        save.setCuotaPagContraEntrega(cuotaPagoContraEntrega);
        save.setTotalImpuestosPagados(totalImpuestos);
        save.setCantidadProductos(cantidadDeProductos);

        // actualizar
        this.ventaRepository.save(save);

        // hacemos las restas en el stock
        ArrayList<Producto> productos = this.hacerRestasEnStock(ventaRequest);
        // guardar las restas
        this.productoRepository.saveAll(productos);

        // crear la data de la facturacion
        DatosFacturacion datosFactuacion = this.crearDatosFacturacion(ventaRequest,
                save);
        // guardar los datos de facturacion
        this.datosFacturacionRepository.save(datosFactuacion);

        if (datosFactuacion.getId() <= 0) {
            throw new Exception("No se pudieron generar los datos de facturacion.");
        }

        // Crear el envio de ser necesario
        if (ventaRequest.getRetiroEnTienda() == false) {
            Envio envio = this.crearEnvio(save, ventaRequest.getDireccion());
            Envio saveEnvio = this.envioRepository.save(envio);
            if (saveEnvio.getId() <= 0) {
                throw new Exception("No se pudo generar el envio.");
            }
        }
        return this.facturaImprimible.init(save,
                datosFactuacion,
                desglose);
    }

    private ArrayList<LineaVenta> crearDesgloce(Venta venta, VentaRequest ventaRequest) throws Exception {
        ArrayList<LineaVenta> desglose = new ArrayList<>();
        // por cada uno de los productos realizar un calculo del total, crear las lineas
        // de ventas
        for (ProductoVentaRequest productosRequest : ventaRequest.getProductos()) {
            this.validar(productosRequest);
            // nos aseguramos que el producto exista
            Producto producto = this.productoService.getProducto(productosRequest.getId());

            if (producto.getDeletedAt() != null) {
                throw new Exception(String.format("El producto %s no existe", producto.getNombre()));
            }

            // creamos la linea de venta del producto
            LineaVenta lineaVenta = new LineaVenta(producto, venta,
                    producto.getPrecio(),
                    productosRequest.getCantidad().intValue(),
                    (producto.getPrecio() * (producto.getPorcentajeImpuesto() / 100)));
            // agregamos la linea de venta al array
            desglose.add(lineaVenta);
        }
        return desglose;
    }

    private ArrayList<Producto> hacerRestasEnStock(VentaRequest ventaRequest) throws Exception {
        ArrayList<Producto> productos = new ArrayList<>();

        // por cada uno de los productos realizar un calculo del total, crear las lineas
        // de ventas
        for (ProductoVentaRequest productosRequest : ventaRequest.getProductos()) {
            // nos aseguramos que el producto exista
            Producto producto = this.productoService.getProducto(productosRequest.getId());

            // verificamos la disponibilidad del producto, de no haber stock se rechaza la
            // venta
            if (producto.getStock() < productosRequest.getCantidad()) {
                throw new Exception("No hay suficiente Stock del producto \""
                        + producto.getNombre() + "\"");
            }

            // restamos el stock con la cantidad
            producto.setStock(producto.getStock()
                    - productosRequest.getCantidad().intValue());

            productos.add(producto);
        }
        return productos;
    }

    private Double calcularTotal(ArrayList<LineaVenta> desgloce) {
        Double total = 0.0;
        for (LineaVenta linea : desgloce) {
            // sumamos el total
            total += (linea.getPrecio() * linea.getCantidad());
        }
        return total;
    }

    private Integer calcularCantidadProductos(ArrayList<LineaVenta> desgloce) {
        Integer total = 0;
        for (LineaVenta linea : desgloce) {
            // sumamos el total
            total += linea.getCantidad();
        }
        return total;
    }

    private DatosFacturacion crearDatosFacturacion(VentaRequest ventaRequest,
            Venta venta) throws Exception {
        // traemos el usuario que va a comprar
        Usuario usuarioEncontrado = this.usuarioService.getUsuario(
                ventaRequest.getIdCompradador());

        String nit = "";

        if (ventaRequest.getConsumidorFinal()) {
            nit = "CF";
        } else {
            if (usuarioEncontrado.getNit() == null) {
                throw new Exception("No haz ingresado un nit.");
            }
            nit = usuarioEncontrado.getNit();
        }

        return new DatosFacturacion(
                nit,
                usuarioEncontrado.getNombres().trim()
                        + " "
                        + usuarioEncontrado.getApellidos().trim(),
                venta,
                usuarioEncontrado);
    }

    private Envio crearEnvio(Venta venta, String direccion) throws Exception {
        // mandamos a traer el estado "pendiente"
        Optional<EstadoEnvio> busquedaEstadoEnvio = this.estadoEnvioRepository.findOneByNombre("PENDIENTE");
        if (busquedaEstadoEnvio.isEmpty()) {
            throw new Exception("Error al asignar el estado del pedido");
        }
        EstadoEnvio estadoEnvio = busquedaEstadoEnvio.get();
        // mandamos a traer el estado del envio
        return new Envio(venta, direccion, estadoEnvio);
    }
}
