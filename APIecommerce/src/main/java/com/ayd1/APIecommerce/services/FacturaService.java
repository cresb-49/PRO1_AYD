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

    @Transactional
    public String guardarVenta(VentaRequest ventaRequest) throws Exception {

        //validamos elobjeto
        this.validar(ventaRequest);

        //construir la venta
        Venta venta = this.construirVenta(ventaRequest);
        Venta save2 = this.ventaRepository.save(venta);

        //validar la creacion de la venta
        if (save2.getId() <= 0) {
            throw new Exception("No se pudo generar la venta");
        }
        
        //hacemos las restas en el stock
        ArrayList<Producto> productos = this.hacerRestasEnStock(ventaRequest);
        //guardar las restas

        //crear la data de la facturacion
        DatosFacturacion datosFactuacion = this.crearDatosFacturacion(ventaRequest,
                venta);

        //guardar los datos de facturacion
        this.datosFacturacionRepository.save(datosFactuacion);

        if (datosFactuacion.getId() <= 0) {
            throw new Exception("No se pudieron generar los datos de facturacion.");
        }

        //Crear el envio de ser necesario
        if (ventaRequest.getRetiroEnTienda() != true) {
            Envio envio = this.crearEnvio(venta);
            Envio saveEnvio = this.envioRepository.save(envio);
            if (saveEnvio.getId() <= 0) {
                throw new Exception("No se pudo generar el envio.");
            }
        }

        /* Envio envio = this.guardarEnvio(venta, ventaRequest);
        //mandamos a crear el documento de la factura*/
        return "Se creo la venta con exito.";
    }

    private Venta construirVenta(VentaRequest ventaRequest) throws Exception {
        //creamos la instancia de a venta
        Venta venta = new Venta();

        ArrayList<LineaVenta> desglose = this.crearDesgloce(venta, ventaRequest);
        Double total = this.calcularTotal(desglose);

        //anadimos las propiedades de la venta
        venta.setCantidadProductos(ventaRequest.getProductos().size());
        venta.setValorTotal(total);
        venta.setLineaVentas(desglose);

        return venta;
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
                throw new Exception("No hay suficiente Stock del producto " + producto.getNombre());
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
