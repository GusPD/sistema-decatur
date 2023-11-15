package com.gl05.bad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.Empresa;
import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CuentaBancariaService;
import com.gl05.bad.servicio.CuotaMantenimientoService;
import com.gl05.bad.servicio.EmpresaService;
import com.gl05.bad.servicio.InformacionMantenimientoService;
import com.gl05.bad.servicio.PagoService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.VentaService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PagoController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private CuotaMantenimientoService cuotaMantenimientoService;

    @Autowired
    private InformacionMantenimientoService informacionMantenimientoService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private CuentaBancariaService cuentaService;
    
    @Autowired
    private VentaService ventaService;
    
    //Función para redigir a la vista de pagos del proyecto
    @GetMapping("/Pagos/{idProyecto}")
    public String mostrarProyecto(Model model, @PathVariable("idProyecto") Long idProyecto) {
        model.addAttribute("pageTitle", "Pagos");
        Proyecto newProyecto = proyectoService.encontrar(idProyecto);
        Empresa newEmpresa = empresaService.encontrar(newProyecto.getEmpresa().getIdEmpresa());
        List<CuentaBancaria> cuentas = cuentaService.encontrarEmpresa(newEmpresa);
        List<Venta> ventas = ventaService.encontrarActivas(newProyecto);
        model.addAttribute("ventas", ventas);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("proyecto", newProyecto);
        return "/Pago/GestionarPago";
    }
    
    //Función para obtener las lotes activos del proyecto de la base de datos con prima
    @GetMapping("/obtenerVentasPrima")
    public ResponseEntity<List<Venta>> obtenerVentasPrima(@RequestParam Long proyectoId) {
        Proyecto proyecto = proyectoService.encontrar(proyectoId);
        List<Venta> ventas = ventaService.encontrarProyectoPrima(proyecto);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    
    //Función para obtener las lotes activos del proyecto de la base de datos con financiamiento
    @GetMapping("/obtenerVentasFinanciamiento")
    public ResponseEntity<List<Venta>> obtenerVentasFinanciamiento(@RequestParam Long proyectoId) {
        Proyecto proyecto = proyectoService.encontrar(proyectoId);
        List<Venta> ventas = ventaService.encontrarProyectoFinanciamiento(proyecto);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    
    //Función para obtener las lotes activos del proyecto de la base de datos con mantenimiento
    @GetMapping("/obtenerVentasMantenimiento")
    public ResponseEntity<List<Venta>> obtenerVentasMantenimiento(@RequestParam Long proyectoId) {
        Proyecto proyecto = proyectoService.encontrar(proyectoId);
        List<Venta> ventas = ventaService.encontrarProyectoMantenimiento(proyecto);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    
    //Función para obtener las pagos del proyecto de la base de datos
    @GetMapping("/pagos/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<Pago> GetPagos(
        @Valid DataTablesInput input,  @PathVariable Long idProyecto, 
        @RequestParam("fecha_inicio") String fechaInicio, @RequestParam("fecha_fin") String fechaFin,
        @RequestParam("comprobante") String comprobante, @RequestParam("estado") String estado,
        @RequestParam("tipo_pago") Integer tipoPago, @RequestParam("lote") Long venta) throws ParseException {
        Date fechaInicioDate = null;
        if(!fechaInicio.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaInicioDate = sdf.parse(fechaInicio);
        }
        Date fechaFinDate = null;
        if(!fechaFin.equals("")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaFinDate = sdf.parse(fechaFin);
        }
        Boolean estadoBoolean = null;
        if(!estado.equals("")){
            estadoBoolean = Boolean.valueOf(estado);
        }
        Venta ventaObj = null;
        if(venta > 0){
            ventaObj = ventaService.encontrar(venta);
        }
        return pagoService.listarPagos(input, idProyecto, fechaInicioDate, fechaFinDate, comprobante, estadoBoolean, tipoPago, ventaObj);
    }

    //Función para ver el pago
    @GetMapping("/Recibo/{idPago}")
    public String mostrarPago(Model model, @PathVariable("idPago") Long idPago) {
        model.addAttribute("pageTitle", "Recibo Pago");
        Pago newPago = pagoService.encontrar(idPago);
        Proyecto newProyecto = proyectoService.encontrar(newPago.getVenta().getTerreno().getProyecto().getIdProyecto());
        List<CuotaMantenimiento> listaCuotaMantenimientos = cuotaMantenimientoService.encontrarPago(newPago);
        model.addAttribute("listaCuotaMantenimientos", listaCuotaMantenimientos);
        model.addAttribute("pago", newPago);
        model.addAttribute("proyecto", newProyecto);
        return "/Pago/Recibo";
    }
    
    //Función para agregar un pago en la base de datos
    @PostMapping("/AgregarPago")
    public ResponseEntity<String> AgregarPago(Pago pago, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            pago.setFechaRegistro(LocalDateTime.now());
            if(pago.getTipo().equals("Prima")){
                if(pagoService.encontrarRecibo("Prima", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==null && pagoService.encontrarRecibo("Financiamiento", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==null){
                    pagoService.agregar(pago);
                }else{
                    String error = "Ocurrió un error al agregar el pago, el recibo ya se encuentra registrado.";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                }
            }else if(pago.getTipo().equals("Mantenimiento")){
                if(pagoService.encontrarRecibo("Mantenimiento", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==null){
                    pagoService.agregar(pago);
                    RegistroCuotaMantenimiento(pago);
                }else{
                    String error = "Ocurrió un error al agregar el pago, el recibo ya se encuentra registrado.";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                }
            }
            String mensaje = "Se ha agregado un pago.";
            bitacoraService.registrarAccion("Agregar pago "+pago.getTipo().toLowerCase());
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el pago.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para eliminar un pago de la base de datos
    @PostMapping("/EliminarPago/{idPago}")
    public ResponseEntity<String> EliminarPago(@PathVariable Long idPago) {
        try {
            Pago newPago = pagoService.encontrar(idPago);
            pagoService.eliminar(newPago);
            String mensaje = "Se han eliminado los pagos correctamente.";
            bitacoraService.registrarAccion("Eliminar pago "+newPago.getTipo().toLowerCase());
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el pago.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para obtener un pago de la base de datos
    @GetMapping("/ObtenerPago/{id}")
    public ResponseEntity<Pago> ObtenerPago(@PathVariable Long id) {
        Pago pago = pagoService.encontrar(id);
        if (pago != null) {
            return ResponseEntity.ok(pago);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función para actualizar un pago de la base de datos
    @PostMapping("/ActualizarPago")
    public ResponseEntity<String> ActualizarPago(Pago pago, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Pago pagoSinActualizar = pagoService.encontrar(pago.getIdPago());
            pago.setFechaRegistro(pagoSinActualizar.getFechaRegistro());
            if(pago.getTipo().equals("Prima")){
                if(pagoService.encontrarRecibo("Financiamiento", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==null || pagoService.encontrarRecibo("Prima", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==pago){
                    pagoService.actualizar(pago);
                }else{
                    String error = "Ocurrió un error al actualizar el pago, el recibo ya se encuentra registrado.";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                }
            }else if(pago.getTipo().equals("Mantenimiento")){
                if(pagoService.encontrarRecibo("Mantenimiento", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==null || pagoService.encontrarRecibo("Mantenimiento", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==pago){
                    pagoService.actualizar(pago);
                }else{
                    String error = "Ocurrió un error al actualizar el pago, el recibo ya se encuentra registrado.";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                }
            }
            String mensaje = "Se ha actualizado el pago correctamente.";
            bitacoraService.registrarAccion("Actualizar pago "+pago.getTipo().toLowerCase());
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el pago.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para anular un recibo
    @GetMapping("/AnularRecibo")
    public ResponseEntity<String> AnularRecibo(@RequestParam Long idPago, @RequestParam boolean estado) {
        try {
            String mensaje = "";
            Pago pago = pagoService.encontrar(idPago);
            List<Pago> pagosPosteriores = pagoService.encontrarMayores("Mantenimiento", pago.getVenta(), pago.getFechaRegistro());
            if(pagosPosteriores.size()==0){
                pago.setEstado(estado);
                pagoService.actualizar(pago);
                if(estado==false){
                    mensaje = "Se ha anulado el recibo correctamente.";
                    bitacoraService.registrarAccion("Anular pago "+pago.getTipo().toLowerCase());
                }else{
                    mensaje = "Se ha desanulado el recibo correctamente.";
                    bitacoraService.registrarAccion("Desanular pago "+pago.getTipo().toLowerCase());
                }
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "";
                if(estado==false){
                    error = "Ha ocurrido un error al anular el recibo, existen pagos registrados posterior a este.";
                }else{
                    error = "Ha ocurrido un error al desanular el recibo, existen pagos registrados posterior a este.";
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            } 
        } catch (Exception e) {
            String error = "";
            if(estado==false){
                error = "Ha ocurrido un error al anular el recibo.";
            }else{
                error = "Ha ocurrido un error al desanular el recibo.";
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para obtener el descuento disponible del pago
    @GetMapping("/ObtenerDescuento/{id}")
    public ResponseEntity<ObjectNode> ObtenerDescuentoPago(@PathVariable Long id, @RequestParam double monto, @RequestParam double otros, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) throws ParseException {
        Venta venta = ventaService.encontrar(id);
        InformacionMantenimiento informacionCuota = InformacionCuotaMantenimiento(venta);
        CuotaMantenimiento ultimaCuota = cuotaMantenimientoService.encontrarUltimaCuota(venta);
        Date fechaPago = fecha;
        Date fechaCorte = ultimaCuota.getFechaCuota();
        double descuentoCalculado = 0;
        double montoMora = 0;
        //Validar si se encuentra en mora
        if(fechaPago.after(fechaCorte)){
            //Cálculo de las cantidad de meses en mora
            int cantidadCuotasMora = CantidadMeses(fechaCorte, fechaPago);
            Date fechaCorteEvaluacion = SiguienteMes(fechaCorte, venta, cantidadCuotasMora);
            if(fechaCorteEvaluacion.after(fechaPago) || fechaCorteEvaluacion.equals(fechaPago)){
                cantidadCuotasMora--;
            }
            if(monto>(ultimaCuota.getSaldoCuota() - ultimaCuota.getSaldoRecargo())){
                monto = monto - otros - ultimaCuota.getSaldoCuota() - ultimaCuota.getSaldoRecargo();
                montoMora = cantidadCuotasMora * (informacionCuota.getCuota()+informacionCuota.getMulta());
                if(monto <= montoMora){
                    montoMora = monto;
                    cantidadCuotasMora = (int) Math.ceil(montoMora / (informacionCuota.getCuota()+informacionCuota.getMulta()));
                }
                //Cálculo del descuento que se le puede aplicar según el abono.
                descuentoCalculado = cantidadCuotasMora * informacionCuota.getMulta();
            }
        }
        // Crear un objeto JSON para devolver al cliente
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();
        responseJson.put("descuentoCalculado", descuentoCalculado);
        return ResponseEntity.ok(responseJson);
    }

    //Función que registra las cuotas de mantenimiento del pago
    public void RegistroCuotaMantenimiento(Pago pago) {

        //Obtener la venta del pago
        Venta venta = ventaService.encontrar(pago.getVenta().getIdVenta());

        //Obtener la información sobre el valor de la cuota a cancelar
        InformacionMantenimiento informacionCuota = InformacionCuotaMantenimiento(venta);

        //Obtener la ultima cuota cancelada, la fecha de corte, la fecha de pago, y el monto para aplicar a las cuotas
        CuotaMantenimiento ultimaCuota = cuotaMantenimientoService.encontrarPenultimaCuota(venta);
        Date fechaCorte = SiguienteMes(ultimaCuota.getFechaCuota(), venta, 1);
        double monto = pago.getMonto() + pago.getDescuento() - pago.getOtros();
        double montoDescuento = pago.getDescuento();
        double montoOtros = pago.getOtros();
        Date fechaPago = pago.getFecha();
        
        //Registrar cuota en concepto de otros pagos, en caso de que exista
        if(montoOtros>0){
            Date fechaOtros = new Date();
            CuotaMantenimiento cuotaOtros = new CuotaMantenimiento();
            cuotaOtros.setFechaRegistro(LocalDateTime.now());
            cuotaOtros.setFechaCuota(fechaOtros);
            cuotaOtros.setCuota(0.0);
            cuotaOtros.setSaldoCuota(0.0);
            cuotaOtros.setRecargo(0.0);
            cuotaOtros.setDescuento(0.0);
            cuotaOtros.setSaldoRecargo(0.0);
            cuotaOtros.setOtros(montoOtros);
            cuotaOtros.setPago(pago);
            cuotaOtros.setInformacion(informacionCuota);
            cuotaMantenimientoService.agregar(cuotaOtros);
            montoOtros-=montoOtros;
        }
        //Verificación si existe un saldo que cancelar, para realizar el cobro
        if((ultimaCuota.getSaldoCuota() + ultimaCuota.getSaldoRecargo()) > 0){
            //Cobro en caso del que el monto sea mayor que el saldo
            if(monto > (ultimaCuota.getSaldoCuota() + ultimaCuota.getSaldoRecargo())){
                CuotaMantenimiento cuota = new CuotaMantenimiento();
                cuota.setFechaRegistro(LocalDateTime.now());
                cuota.setFechaCuota(ultimaCuota.getFechaCuota());
                cuota.setCuota(ultimaCuota.getSaldoCuota());
                cuota.setSaldoCuota(0.0);
                cuota.setRecargo(ultimaCuota.getSaldoRecargo());
                cuota.setDescuento(0.0);
                cuota.setSaldoRecargo(0.0);
                cuota.setPago(pago);
                cuota.setInformacion(informacionCuota);
                cuotaMantenimientoService.agregar(cuota);
                monto-=(ultimaCuota.getSaldoCuota()+ultimaCuota.getSaldoRecargo());
            }else{
                //Si existe saldo de recargo
                if(ultimaCuota.getSaldoRecargo() > 0){
                    //Cobro del recargo y un abono si alcanza
                    if(monto > ultimaCuota.getSaldoRecargo()){
                        CuotaMantenimiento cuota = new CuotaMantenimiento();
                        cuota.setFechaRegistro(LocalDateTime.now());
                        cuota.setRecargo(ultimaCuota.getSaldoRecargo());
                        cuota.setDescuento(0.0);
                        cuota.setSaldoRecargo(0.0);
                        cuota.setFechaCuota(ultimaCuota.getFechaCuota());
                        double valorCuota = monto-ultimaCuota.getSaldoRecargo();
                        double saldoCuota = ultimaCuota.getSaldoCuota() - valorCuota;
                        cuota.setCuota(valorCuota);
                        cuota.setSaldoCuota(saldoCuota);
                        cuota.setPago(pago);
                        cuota.setInformacion(informacionCuota);
                        cuotaMantenimientoService.agregar(cuota);
                        monto-=monto;
                    }else{//Cobro solo del recargo
                        CuotaMantenimiento cuota = new CuotaMantenimiento();
                        cuota.setFechaRegistro(LocalDateTime.now());
                        cuota.setRecargo(monto);
                        cuota.setDescuento(0.0);
                        cuota.setSaldoRecargo(ultimaCuota.getSaldoRecargo() - monto);
                        cuota.setFechaCuota(ultimaCuota.getFechaCuota());
                        cuota.setCuota(0.0);
                        cuota.setSaldoCuota(ultimaCuota.getSaldoCuota());
                        cuota.setPago(pago);
                        cuota.setInformacion(informacionCuota);
                        cuotaMantenimientoService.agregar(cuota);
                        monto-=monto;
                    }
                //Si existe saldo de cuota
                }else if(ultimaCuota.getSaldoCuota() > 0){
                    //Cobro del saldo de la cuota
                    if(monto >= ultimaCuota.getSaldoCuota()){
                        CuotaMantenimiento cuota = new CuotaMantenimiento();
                        cuota.setFechaRegistro(LocalDateTime.now());
                        cuota.setRecargo(0.0);
                        cuota.setDescuento(0.0);
                        cuota.setSaldoRecargo(0.0);
                        cuota.setFechaCuota(ultimaCuota.getFechaCuota());
                        cuota.setCuota(monto);
                        cuota.setSaldoCuota(0.0);
                        cuota.setPago(pago);
                        cuota.setInformacion(informacionCuota);
                        cuotaMantenimientoService.agregar(cuota);
                        monto-=monto;
                    }else{//Cobro solo de abono a la cuota
                        CuotaMantenimiento cuota = new CuotaMantenimiento();
                        cuota.setFechaRegistro(LocalDateTime.now());
                        cuota.setRecargo(0.0);
                        cuota.setDescuento(0.0);
                        cuota.setSaldoRecargo(0.0);
                        cuota.setFechaCuota(ultimaCuota.getFechaCuota());
                        cuota.setCuota(monto);
                        cuota.setSaldoCuota(ultimaCuota.getSaldoCuota()-monto);
                        cuota.setPago(pago);
                        cuota.setInformacion(informacionCuota);
                        cuotaMantenimientoService.agregar(cuota);
                        monto-=monto;
                    }
                }
            }
        }
        //Verificar si se encuentra al día
        if(fechaCorte.after(fechaPago) || fechaCorte.equals(fechaPago)){
            //Calculo de la cantidad de coutas a cancelar
            int cantidadCuotas = (int) Math.floor(monto / informacionCuota.getCuota());
            //Verificación si existira un abono en la cuota
            double existeAbono = monto % informacionCuota.getCuota();
            //Registro de las cuotas
            if(monto >= informacionCuota.getCuota()){
                for (int i=0;i<cantidadCuotas;i++){
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(LocalDateTime.now());
                    cuota.setFechaCuota(fechaCorte);
                    cuota.setCuota(informacionCuota.getCuota());
                    cuota.setSaldoCuota(0.0);
                    cuota.setRecargo(0.0);
                    cuota.setDescuento(0.0);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setPago(pago);
                    cuota.setInformacion(informacionCuota);
                    cuotaMantenimientoService.agregar(cuota);
                    monto-=informacionCuota.getCuota();
                    fechaCorte = SiguienteMes(fechaCorte, venta, 1);
                }
            }
            //Registro de abono en caso de que exista
            if(existeAbono>0){
                CuotaMantenimiento cuota = new CuotaMantenimiento();
                cuota.setFechaRegistro(LocalDateTime.now());
                cuota.setFechaCuota(fechaCorte);
                cuota.setCuota(monto);
                cuota.setSaldoCuota(informacionCuota.getCuota()-monto);
                cuota.setRecargo(0.0);
                cuota.setDescuento(0.0);
                cuota.setSaldoRecargo(0.0);
                cuota.setPago(pago);
                cuota.setInformacion(informacionCuota);
                cuotaMantenimientoService.agregar(cuota);
                monto-=monto;
                montoDescuento-=montoDescuento;
            }
        }else{ //Verificar si se encuentra en mora
            //Control del cobro del recargo para el abono para las cuotas con mora o sin mora
            boolean cobroRecargo = true;
            //Calculo de las cantidad de meses en mora y si existirá abono de cuota
            int cantidadCuotasMora = CantidadMeses(fechaCorte, fechaPago);
            Date fechaCorteEvaluacion = SiguienteMes(fechaCorte, venta, cantidadCuotasMora);
            if(fechaPago.after(fechaCorteEvaluacion)){
                cantidadCuotasMora++;
            }
            double montoMora = cantidadCuotasMora * (informacionCuota.getCuota()+informacionCuota.getMulta());
            double existeAbono = 0;
            if(monto>=montoMora){
                monto-=montoMora;
                existeAbono = monto % (informacionCuota.getCuota());
            }else{
                montoMora=monto;
                existeAbono = montoMora % (informacionCuota.getCuota()+informacionCuota.getMulta());
                cantidadCuotasMora = (int) Math.floor(montoMora / (informacionCuota.getCuota()+informacionCuota.getMulta()));
                monto=0;
            }
            //Calculo de la cantidad de coutas a cancelar sin mora
            int cantidadCuotas = (int) Math.floor(monto / (informacionCuota.getCuota())); 
            //Registro de las cuotas con mora
            if(montoMora >= (informacionCuota.getCuota()+informacionCuota.getMulta())){
                for (int i=0;i<cantidadCuotasMora;i++){
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(LocalDateTime.now());
                    cuota.setFechaCuota(fechaCorte);
                    cuota.setCuota(informacionCuota.getCuota());
                    cuota.setSaldoCuota(0.0);
                    cuota.setRecargo(informacionCuota.getMulta());
                    //Aplicación del descuento a la cuota
                    if(montoDescuento>=informacionCuota.getMulta()){
                        cuota.setDescuento(informacionCuota.getMulta());
                        montoDescuento-=informacionCuota.getMulta();
                    }else{
                        cuota.setDescuento(montoDescuento);
                        montoDescuento-=montoDescuento;
                    }
                    cuota.setSaldoRecargo(0.0);
                    cuota.setPago(pago);
                    cuota.setInformacion(informacionCuota);
                    cuotaMantenimientoService.agregar(cuota);
                    montoMora-=(informacionCuota.getCuota()+informacionCuota.getMulta());
                    fechaCorte = SiguienteMes(fechaCorte, venta, 1);
                    //Validar si en el abono se cobrará recargo
                    if(fechaPago.after(fechaCorte)){
                        cobroRecargo = true;
                    }else{
                        cobroRecargo = false;
                    }
                }
            }
            //Registro de las cuotas sin mora
            if(monto >= informacionCuota.getCuota()){
                for (int i=0;i<cantidadCuotas;i++){
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(LocalDateTime.now());
                    cuota.setFechaCuota(fechaCorte);
                    cuota.setCuota(informacionCuota.getCuota());
                    cuota.setSaldoCuota(0.0);
                    cuota.setRecargo(0.0);
                    cuota.setDescuento(0.0);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setPago(pago);
                    cuota.setInformacion(informacionCuota);
                    cuotaMantenimientoService.agregar(cuota);
                    monto-=informacionCuota.getCuota();
                    fechaCorte = SiguienteMes(fechaCorte, venta,1);
                    //Validar si en el abono se cobrará recargo
                    cobroRecargo = false;
                }
            }
            //Registro de abono a cuota en caso de que exista
            if(existeAbono>0){
                double montoEvaluar = monto + montoMora;
                CuotaMantenimiento cuota = new CuotaMantenimiento();
                cuota.setFechaRegistro(LocalDateTime.now());
                cuota.setFechaCuota(fechaCorte);
                if(cobroRecargo){
                    if(montoEvaluar>=informacionCuota.getMulta()){
                        cuota.setRecargo(informacionCuota.getMulta());
                        cuota.setSaldoRecargo(0.0);
                        montoEvaluar-=informacionCuota.getMulta();
                        //Aplicación del descuento a la cuota
                        if(montoDescuento>=informacionCuota.getMulta()){
                            cuota.setDescuento(informacionCuota.getMulta());
                            montoDescuento-=informacionCuota.getMulta();
                        }else{
                            cuota.setDescuento(montoDescuento);
                            montoDescuento-=montoDescuento;
                        }
                    }else{
                        cuota.setRecargo(montoEvaluar);
                        cuota.setSaldoRecargo(informacionCuota.getMulta()-montoEvaluar);
                        montoEvaluar-=montoEvaluar;
                        //Aplicación del descuento a la cuota
                        if(montoDescuento>=informacionCuota.getMulta()){
                            cuota.setDescuento(informacionCuota.getMulta());
                            montoDescuento-=informacionCuota.getMulta();
                        }else{
                            cuota.setDescuento(montoDescuento);
                            montoDescuento-=montoDescuento;
                        }
                    }
                }else{
                    cuota.setRecargo(0.0);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setDescuento(0.0);
                }
                if(montoEvaluar>=informacionCuota.getCuota()){
                    cuota.setCuota(informacionCuota.getCuota());
                    cuota.setSaldoCuota(0.0);
                    montoEvaluar-=informacionCuota.getCuota();
                }else{
                    cuota.setCuota(montoEvaluar);
                    cuota.setSaldoCuota(informacionCuota.getCuota()-montoEvaluar);
                    montoEvaluar-=montoEvaluar;
                }
                cuota.setPago(pago);
                cuota.setInformacion(informacionCuota);
                cuotaMantenimientoService.agregar(cuota);
                montoDescuento-=montoDescuento;
            }
        }
    }

    //Funcion que obtiene la informacion de la cuota de mantenimiento actual
    public InformacionMantenimiento InformacionCuotaMantenimiento(Venta venta) {
        //Obtener la fecha actual
        Date fechaActual = new Date();
        InformacionMantenimiento informacionCuota= new InformacionMantenimiento();
        List<InformacionMantenimiento> listaInformacion = informacionMantenimientoService.encontrarVenta(venta) ;
        for (InformacionMantenimiento informacionMantenimiento : listaInformacion) {
            Date fechaAplicacion = informacionMantenimiento.getFechaAplicacion();
            if(fechaAplicacion.before(fechaActual)){
                informacionCuota = informacionMantenimiento;
                break;
            }
        }
        return informacionCuota;
    }

    //Función que calcula la cantidad de meses entre dos fechas
    public int CantidadMeses(Date fechaInicio, Date fechaFin){
        Calendar inicio = new GregorianCalendar();
        Calendar fin = new GregorianCalendar();
        inicio.setTime(fechaInicio);
        fin.setTime(fechaFin);
        int cantidadMeses = (fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR)) * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH);
        return cantidadMeses;
    }

    //Función que obtiene la siguiente fecha de corte
    public Date SiguienteMes(Date fechaCorte, Venta venta, int cantidad) {
        Date fechaEscritura = venta.getFecha();

        // Convierte la fecha a Calendar
        Calendar calEscritura = Calendar.getInstance();
        calEscritura.setTime(fechaEscritura);

        Calendar calCorte = Calendar.getInstance();
        calCorte.setTime(fechaCorte);

        // Incrementa el mes en 1
        calCorte.add(Calendar.MONTH, cantidad);

        // Mantén el día si es posible, de lo contrario, establece en el último día del mes
        int diaActual = calCorte.get(Calendar.DAY_OF_MONTH);
        calCorte.set(Calendar.DAY_OF_MONTH, Math.min(diaActual, calCorte.getActualMaximum(Calendar.DAY_OF_MONTH)));
        
        // Verifica si el día de fechaCorte es diferente al día de fechaEscritura
        if (calCorte.get(Calendar.DAY_OF_MONTH) != calEscritura.get(Calendar.DAY_OF_MONTH)) {
            // Reemplaza el día de fechaCorte con el día de fechaEscritura solo si el día de fechaCorte no es el último día del mes
            if (calCorte.get(Calendar.DAY_OF_MONTH) != calCorte.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                calCorte.set(Calendar.DAY_OF_MONTH, calEscritura.get(Calendar.DAY_OF_MONTH));
            }
        }

        return calCorte.getTime();
    }
}
