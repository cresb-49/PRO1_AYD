/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.DatosFacturacion;
import com.ayd1.APIecommerce.models.Envio;
import com.ayd1.APIecommerce.models.EstadoEnvio;
import com.ayd1.APIecommerce.models.LineaVenta;
import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.Venta;
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import com.ayd1.APIecommerce.models.request.ProductoVentaRequest;
import com.ayd1.APIecommerce.models.request.VentaRequest;
import com.ayd1.APIecommerce.repositories.DatosFacturacionRepository;
import com.ayd1.APIecommerce.repositories.EnvioRepository;
import com.ayd1.APIecommerce.repositories.EstadoEnvioRepository;
import com.ayd1.APIecommerce.repositories.LineaVentaRepository;
import com.ayd1.APIecommerce.repositories.ProductoRepository;
import com.ayd1.APIecommerce.repositories.VentaRepository;
import com.ayd1.APIecommerce.services.reportes.imprimibles.FacturaImprimible;
import com.ayd1.APIecommerce.tools.mappers.ProductoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class FacturaService extends Service {
    
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
    
    @Transactional
    public byte[] guardarVenta(VentaRequest ventaRequest) throws Exception {

        //validamos elobjeto
        this.validar(ventaRequest);

        /*la venta volatil es la creacion de la venta con datos iniciales, 
        se utiiza para que exista una referencia en la bd y en spring a la cual apuntar
        con los distintos objetos que se crearan luego
         */
        Venta venta = new Venta(0.00,
                ventaRequest.getProductos().size());
        /*la venta volatil2 se utilizara para guardar el desgloce de la factura
        y el valor total real de la factura
         */
        Venta save = this.ventaRepository.save(venta);
        
        System.out.println(save);
        //validar la creacion de la venta 
        /*
        if (ventaSave.getId() <= 0) {
        throw new Exception("No se pudo generar la factura");
        }*/
        
        ArrayList<LineaVenta> desglose = this.crearDesgloce(save, ventaRequest);
        Double total = this.calcularTotal(desglose);
        //anadimos las propiedades de la venta
        save.setValorTotal(total);
        save.setLineaVentas(desglose);

        //actualizar
        this.ventaRepository.save(save);

        //hacemos las restas en el stock
        ArrayList<Producto> productos = this.hacerRestasEnStock(ventaRequest);
        //guardar las restas
        this.productoRepository.saveAll(productos);

        //crear la data de la facturacion
        DatosFacturacion datosFactuacion = this.crearDatosFacturacion(ventaRequest,
                save);
        //guardar los datos de facturacion
        this.datosFacturacionRepository.save(datosFactuacion);
        
        if (datosFactuacion.getId() <= 0) {
            throw new Exception("No se pudieron generar los datos de facturacion.");
        }

        //Crear el envio de ser necesario 
        if (ventaRequest.getRetiroEnTienda() == false) {
            Envio envio = this.crearEnvio(save);
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
        //por cada uno de los productos realizar un calculo del total, crear las lineas de ventas
        for (ProductoVentaRequest productosRequest : ventaRequest.getProductos()) {
            this.validar(productosRequest);
            //nos aseguramos que el producto exista
            Producto producto = this.productoService.getProducto(productosRequest.getId());
            //creamos la linea de venta del producto
            LineaVenta lineaVenta = new LineaVenta(producto, venta,
                    producto.getPrecio(),
                    productosRequest.getCantidad().intValue());
            //agregamos la linea de venta al array
            desglose.add(lineaVenta);
        }
        return desglose;
    }
    
    private ArrayList<Producto> hacerRestasEnStock(VentaRequest ventaRequest) throws Exception {
        ArrayList<Producto> productos = new ArrayList<>();

        //por cada uno de los productos realizar un calculo del total, crear las lineas de ventas
        for (ProductoVentaRequest productosRequest : ventaRequest.getProductos()) {
            //nos aseguramos que el producto exista
            Producto producto = this.productoService.getProducto(productosRequest.getId());

            //verificamos la disponibilidad del producto, de no haber stock se rechaza la venta
            if (producto.getStock() < productosRequest.getCantidad()) {
                throw new Exception("No hay suficiente Stock del producto \""
                        + producto.getNombre() + "\"");
            }

            //restamos el stock con la cantidad
            producto.setStock(producto.getStock()
                    - productosRequest.getCantidad().intValue());
            
            productos.add(producto);
        }
        return productos;
    }
    
    private Double calcularTotal(ArrayList<LineaVenta> desgloce) {
        Double total = 0.0;
        for (LineaVenta linea : desgloce) {
            //sumamos el total
            total += (linea.getPrecio() * linea.getCantidad());
        }
        return total;
    }
    
    private DatosFacturacion crearDatosFacturacion(VentaRequest ventaRequest,
            Venta venta) throws Exception {
        //traemos el usuario que va a comprar
        Usuario usuarioEncontrado = this.usuarioService.getUsuario(
                ventaRequest.getIdCompradador()
        );

        //verificar si se requiere nit
        String nit = ventaRequest.getConsumidorFinal() ? "CF" : usuarioEncontrado.getNit();
        
        return new DatosFacturacion(
                nit,
                usuarioEncontrado.getNombres().trim()
                + " "
                + usuarioEncontrado.getApellidos().trim(),
                venta,
                usuarioEncontrado);
    }
    
    private Envio crearEnvio(Venta venta) throws Exception {
        //mandamos a traer el estado "pendiente"
        Optional<EstadoEnvio> busquedaEstadoEnvio
                = this.estadoEnvioRepository.findOneByNombre("PENDIENTE");
        if (busquedaEstadoEnvio.isEmpty()) {
            throw new Exception("Error al asignar el estado del pedido");
        }
        EstadoEnvio estadoEnvio = busquedaEstadoEnvio.get();
        //mandamos a traer el estado del envio
        return new Envio(venta, estadoEnvio, null);
    }
}
