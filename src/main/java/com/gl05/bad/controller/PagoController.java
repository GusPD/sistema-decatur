package com.gl05.bad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.Empresa;
import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.domain.Venta;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CuentaBancariaService;
import com.gl05.bad.servicio.CuotaMantenimientoService;
import com.gl05.bad.servicio.EmpresaService;
import com.gl05.bad.servicio.InformacionMantenimientoService;
import com.gl05.bad.servicio.PagoService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.UserServiceImp;
import com.gl05.bad.servicio.VentaService;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private UserServiceImp usuarioService;
    
    //Función para redigir a la vista de pagos del proyecto
    @GetMapping("/Pagos/{idProyecto}")
    public String mostrarProyecto(Model model, @PathVariable("idProyecto") Long idProyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Pagos");
        Proyecto newProyecto = proyectoService.encontrar(idProyecto);
        Empresa newEmpresa = empresaService.encontrar(newProyecto.getEmpresa().getIdEmpresa());
        List<CuentaBancaria> cuentas = cuentaService.encontrarEmpresa(newEmpresa);
        List<Venta> ventas = ventaService.encontrarActivas(newProyecto);
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> listaProyectosAsignados = usuario.getProyectos();
        if(!listaProyectosAsignados.contains(newProyecto)){
            return "accesodenegado";
        }
        model.addAttribute("usuario", usuario);
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
    @GetMapping(value="/pagos/data/{idProyecto}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Pago> GetPagos(
        @Valid DataTablesInput input,  @PathVariable Long idProyecto, 
        @RequestParam("fecha_inicio") String fechaInicio, @RequestParam("fecha_fin") String fechaFin,
        @RequestParam("tipo") String tipo, @RequestParam("estado") String estado,
        @RequestParam("cuenta") Integer cuenta, @RequestParam("comprobante") String comprobante) throws ParseException {
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
        return pagoService.listarPagos(input, idProyecto, fechaInicioDate, fechaFinDate, tipo, estadoBoolean, cuenta, comprobante);
    }

    //Función para ver el pago
    @GetMapping("/Recibo/{idPago}")
    public String mostrarPago(Model model, @PathVariable("idPago") Long idPago, Authentication authentication) {
        model.addAttribute("pageTitle", "Recibo Pago");
        Pago newPago = pagoService.encontrar(idPago);
        Proyecto newProyecto = proyectoService.encontrar(newPago.getVenta().getTerreno().getProyecto().getIdProyecto());
        List<CuotaMantenimiento> listaCuotaMantenimientos = cuotaMantenimientoService.encontrarPago(newPago);
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> listaProyectosAsignados = usuario.getProyectos();
        if(!listaProyectosAsignados.contains(newProyecto)){
            return "accesodenegado";
        }
        model.addAttribute("listaCuotaMantenimientos", listaCuotaMantenimientos);
        model.addAttribute("pago", newPago);
        model.addAttribute("proyecto", newProyecto);
        return "/Pago/Recibo";
    }

    //Función para obtener el mantenimiento de la venta de la base de datos
    @GetMapping(value="/cuotaMantenimientoPago/data/{idPago}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<CuotaMantenimiento> GetMantenimientoVenta(@Valid DataTablesInput input,  @PathVariable Long idPago){
        return cuotaMantenimientoService.listarPago(input, idPago);
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
                    if(!pago.getComprobante().equals("Ticket")){
                        RegistroCuotaMantenimiento(pago);
                    }
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
    @GetMapping(value="/ObtenerPago/{id}", produces = "application/json; charset=UTF-8")
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
                if(pagoService.encontrarRecibo("Financiamiento", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==null || pagoService.encontrarRecibo("Prima", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante()).getIdPago()==pago.getIdPago()){
                    pagoService.actualizar(pago);
                }else{
                    String error = "Ocurrió un error al actualizar el pago, el recibo ya se encuentra registrado.";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                }
            }else if(pago.getTipo().equals("Mantenimiento")){
                if(pagoService.encontrarRecibo("Mantenimiento", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante())==null || pagoService.encontrarRecibo("Mantenimiento", pago.getRecibo(), pago.getVenta().getTerreno().getProyecto(), pago.getComprobante()).getIdPago()==pago.getIdPago()){
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

    //Función para verificar si el pago no se encuentra con el comprobante duplicado
    @GetMapping(value="/VerificarPago", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ObjectNode> VerificarPago(@RequestParam String tipo, @RequestParam String recibo, @RequestParam String idVenta, @RequestParam String comprobante, @RequestParam String idPago, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Boolean valor = false;
        Venta venta = ventaService.encontrar(Long.parseLong(idVenta));
        if(idPago.isEmpty()){
            if(tipo.equals("Prima")){
                if(!(pagoService.encontrarRecibo("Financiamiento", Integer.parseInt(recibo), venta.getTerreno().getProyecto(), comprobante)==null)){
                    valor = true;
                }
            }else if(tipo.equals("Mantenimiento")){
                if(!(pagoService.encontrarRecibo("Mantenimiento", Integer.parseInt(recibo), venta.getTerreno().getProyecto(), comprobante)==null)){
                    valor = true;
                }
            }
        }else{
            if(tipo.equals("Prima")){
                if(!(pagoService.encontrarRecibo("Financiamiento", Integer.parseInt(recibo), venta.getTerreno().getProyecto(), comprobante)==null || pagoService.encontrarRecibo(tipo, Integer.parseInt(recibo), venta.getTerreno().getProyecto(), comprobante).getIdPago()==Long.parseLong(idPago))){
                    valor = true;
                }
            }else if(tipo.equals("Mantenimiento")){
                if(!(pagoService.encontrarRecibo("Mantenimiento", Integer.parseInt(recibo), venta.getTerreno().getProyecto(), comprobante)==null || pagoService.encontrarRecibo(tipo, Integer.parseInt(recibo), venta.getTerreno().getProyecto(), comprobante).getIdPago()==Long.parseLong(idPago))){
                    valor = true;
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();
        responseJson.put("existe", valor);
        return ResponseEntity.ok(responseJson);
    }

    //Función para obtener el descuento disponible del pago
    @GetMapping(value="/ObtenerDescuento/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ObjectNode> ObtenerDescuentoPago(@PathVariable Long id, @RequestParam double monto, @RequestParam double otros, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) throws ParseException {
        Venta venta = ventaService.encontrar(id);
        CuotaMantenimiento ultimaCuota = cuotaMantenimientoService.encontrarUltimaCuota(venta);
        InformacionMantenimiento informacionCuota = InformacionCuotaMantenimiento(venta, ultimaCuota.getFechaCuota());
        Date fechaPago = fecha;
        Date fechaCorte = ultimaCuota.getFechaCuota();
        double descuentoCalculado = 0;

        //Obtener el excedente o lo pendiente de acuerdo al ajuste de las cuotas
        List<InformacionMantenimiento> listaInformacionMantenimiento = informacionMantenimientoService.encontrarVenta(venta);
        //Definicion de variables a utilizar
        Double excedenteCuotaAjuste = 0.0;
        Double pendienteCuotaAjuste = 0.0;
        Double excedenteRecargoAjuste = 0.0;
        Double pendienteRecargoAjuste = 0.0;
        Double valorCuotaEvaluada = 0.0;
        Double valorRecargoEvaluado = 0.0;
        Date fechaUltimaCuotaEvaluada = new Date();
        Date fechaCuotaEvaluada = new Date();
        Date fechaAnteriorEvaluada = null;
        for (InformacionMantenimiento informacionEvaluada : listaInformacionMantenimiento) {
            //Obtener la lista de cuotas para el periodo de aplicación de las cuotas de mantenimiento
            List<CuotaMantenimiento> cuotasMantenimientoAjuste = cuotaMantenimientoService.encontrarMayoresFechaCuotaVenta(informacionEvaluada.getFechaAplicacion(), venta);                   
            //Verificar si existen cuotas para la fecha de aplicación
            if(!cuotasMantenimientoAjuste.isEmpty()){
                //Obtener el rango de aplicación para la cuota
                fechaUltimaCuotaEvaluada = cuotasMantenimientoAjuste.get(cuotasMantenimientoAjuste.size()-1).getFechaCuota();
                fechaCuotaEvaluada = cuotasMantenimientoAjuste.get(0).getFechaCuota();
                if(fechaAnteriorEvaluada != null){
                    fechaUltimaCuotaEvaluada = fechaAnteriorEvaluada;
                    Calendar calendarFechaAnteriorEvaluada = Calendar.getInstance();
                    calendarFechaAnteriorEvaluada.setTime(fechaUltimaCuotaEvaluada);
                    calendarFechaAnteriorEvaluada.add(Calendar.DAY_OF_MONTH, -1);
                    fechaUltimaCuotaEvaluada = calendarFechaAnteriorEvaluada.getTime();
                }
                //Obtener el excedente o lo pendiente para cada cuota aplicada
                while (fechaCuotaEvaluada.before(fechaUltimaCuotaEvaluada) || fechaCuotaEvaluada.equals(fechaUltimaCuotaEvaluada)) {
                    List<CuotaMantenimiento> listaCuotasMes = cuotaMantenimientoService.encontrarFechaCuotaVenta(fechaCuotaEvaluada, venta);
                    for (CuotaMantenimiento cuotaMesEvaluada : listaCuotasMes) {
                        valorCuotaEvaluada += cuotaMesEvaluada.getCuota();
                        valorRecargoEvaluado += cuotaMesEvaluada.getRecargo();
                    }
                    if(valorCuotaEvaluada>informacionEvaluada.getCuota() && valorCuotaEvaluada>0){
                        excedenteCuotaAjuste += valorCuotaEvaluada-informacionEvaluada.getCuota();
                    }else if(valorCuotaEvaluada<informacionEvaluada.getCuota() && valorCuotaEvaluada>0){
                        pendienteCuotaAjuste += informacionEvaluada.getCuota()-valorCuotaEvaluada;
                    }
                    if(valorRecargoEvaluado>informacionEvaluada.getMulta() && valorRecargoEvaluado>0){
                        excedenteRecargoAjuste += valorRecargoEvaluado-informacionEvaluada.getMulta();
                    }else if(valorRecargoEvaluado<informacionEvaluada.getMulta() && valorRecargoEvaluado>0){
                        pendienteRecargoAjuste += informacionEvaluada.getMulta()-valorRecargoEvaluado;
                    }
                    valorCuotaEvaluada = 0.0;
                    valorRecargoEvaluado = 0.0;
                    fechaCuotaEvaluada = SiguienteMes(fechaCuotaEvaluada, venta, 1);
                }
            }
            //Actualizar rango para la fecha de aplicación de las cuotas de mantenimiento
            fechaAnteriorEvaluada = informacionEvaluada.getFechaAplicacion();
        }

        double montoAjuste = excedenteCuotaAjuste + excedenteRecargoAjuste - pendienteCuotaAjuste - pendienteRecargoAjuste;
        monto = monto - otros - montoAjuste;
        //Se utiliza la misma logica para registrar la cuota de mantenimiento
        //Control del cobro del recargo para el abono para las cuotas con mora o sin mora
        boolean cobroRecargo = false;
        //Registro de las cuotas
        if(monto >= informacionCuota.getCuota()){
            while (monto >= informacionCuota.getCuota()){
                informacionCuota = InformacionCuotaMantenimiento(venta, fechaCorte);
                if(fechaCorte.after(fechaPago) || fechaCorte.equals(fechaPago)){
                    descuentoCalculado +=0;
                    cobroRecargo = false;
                }else{
                    descuentoCalculado+=informacionCuota.getMulta();
                    cobroRecargo = true;
                    monto-=informacionCuota.getMulta();
                }
                if(monto>informacionCuota.getCuota()){
                    monto-=informacionCuota.getCuota();
                }else{
                    monto-=monto;
                }
                fechaCorte = SiguienteMes(fechaCorte, venta, 1);
            }
        }
        //Registro de abono a cuota en caso de que exista
        if(monto>0){
            informacionCuota = InformacionCuotaMantenimiento(venta, fechaCorte);
            if(cobroRecargo && fechaCorte.before(fechaPago)){
                if(monto>=informacionCuota.getMulta()){
                    descuentoCalculado+=informacionCuota.getMulta();
                    monto-=informacionCuota.getMulta();
                }else{
                    descuentoCalculado+=monto;
                    monto-=monto;
                }
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

        //Obtener la ultima cuota cancelada, la fecha de corte, la fecha de pago, y el monto para aplicar a las cuotas
        CuotaMantenimiento ultimaCuota = cuotaMantenimientoService.encontrarPenultimaCuota(venta);
        Date fechaCorte = SiguienteMes(ultimaCuota.getFechaCuota(), venta, 1);
        double monto = pago.getMonto() + pago.getDescuento() - pago.getOtros();
        double montoDescuento = pago.getDescuento();
        double montoOtros = pago.getOtros();
        Date fechaPago = pago.getFecha();
        
        //Obtener la información sobre el valor de la cuota a cancelar
        InformacionMantenimiento informacionCuota = InformacionCuotaMantenimiento(venta, fechaCorte);

        //Registrar cuota en concepto de otros pagos, en caso de que exista
        if(montoOtros>0){
            Date fechaOtros = new Date();
            CuotaMantenimiento cuotaOtros = new CuotaMantenimiento();
            cuotaOtros.setFechaRegistro(LocalDateTime.now());
            cuotaOtros.setFechaCuota(fechaOtros);
            cuotaOtros.setCuota(0.0);
            cuotaOtros.setSaldoCuota(ultimaCuota.getSaldoCuota());
            cuotaOtros.setRecargo( 0.0);
            cuotaOtros.setDescuento(0.0);
            cuotaOtros.setSaldoRecargo( ultimaCuota.getSaldoRecargo());
            cuotaOtros.setOtros(montoOtros);
            cuotaOtros.setPago(pago);
            cuotaMantenimientoService.agregar(cuotaOtros);
            montoOtros-=montoOtros;
        }
        if(monto>0){
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
                            cuotaMantenimientoService.agregar(cuota);
                            monto-=monto;
                        }
                    }
                }
            }
        }

        //Ajuste de cuotas en caso de que existan abonos adelantados en un cambio de fecha aplicación
        if(monto>0){
            List<InformacionMantenimiento> listaInformacionMantenimiento = informacionMantenimientoService.encontrarVenta(venta);
            //Definicion de variables a utilizar
            Double valorSumaAjusteAnterior = 0.0;
            Double excedenteAjuste = 0.0;
            Double pendienteAjuste = 0.0;
            Double valorCuotaEvaluada = 0.0;
            Date fechaUltimaCuotaEvaluada = new Date();
            Date fechaCuotaEvaluada = new Date();
            Date fechaAnteriorEvaluada = null;
            Date fechaInicioAjuste = new Date();
            Date fechaInicioAjusteAuxiliar = new Date();
            Date fechaFinalAjuste = null;
            Date fechaFinalAjusteAuxiliar = new Date();
            for (InformacionMantenimiento informacionEvaluada : listaInformacionMantenimiento) {
                //Obtener la lista de cuotas para el periodo de aplicación de las cuotas de mantenimiento
                List<CuotaMantenimiento> cuotasMantenimientoAjuste = cuotaMantenimientoService.encontrarMayoresFechaCuotaVenta(informacionEvaluada.getFechaAplicacion(), venta);                   
                //Verificar si existen cuotas para la fecha de aplicación
                if(!cuotasMantenimientoAjuste.isEmpty()){
                    //Obtener el rango de aplicación para la cuota
                    fechaUltimaCuotaEvaluada = cuotasMantenimientoAjuste.get(cuotasMantenimientoAjuste.size()-1).getFechaCuota();
                    fechaCuotaEvaluada = cuotasMantenimientoAjuste.get(0).getFechaCuota();
                    if(fechaAnteriorEvaluada != null){
                        fechaUltimaCuotaEvaluada = fechaAnteriorEvaluada;
                        Calendar calendarFechaAnteriorEvaluada = Calendar.getInstance();
                        calendarFechaAnteriorEvaluada.setTime(fechaUltimaCuotaEvaluada);
                        calendarFechaAnteriorEvaluada.add(Calendar.DAY_OF_MONTH, -1);
                        fechaUltimaCuotaEvaluada = calendarFechaAnteriorEvaluada.getTime();
                    }
                    //Obtener la ultima fecha del ajuste
                    if(fechaFinalAjuste == null){
                        fechaFinalAjusteAuxiliar = fechaUltimaCuotaEvaluada;
                    }
                    //Obtener la primera fecha del ajuste con una variable auxiliar, para verificar posteriormente si existe ajuste. 
                    fechaInicioAjusteAuxiliar = fechaCuotaEvaluada;
                    //Obtener el excedente o lo pendiente para cada cuota aplicada
                    while (fechaCuotaEvaluada.before(fechaUltimaCuotaEvaluada) || fechaCuotaEvaluada.equals(fechaUltimaCuotaEvaluada)) {
                        List<CuotaMantenimiento> listaCuotasMes = cuotaMantenimientoService.encontrarFechaCuotaVenta(fechaCuotaEvaluada, venta);
                        for (CuotaMantenimiento cuotaMesEvaluada : listaCuotasMes) {
                            valorCuotaEvaluada += cuotaMesEvaluada.getCuota();
                        }
                        if(valorCuotaEvaluada>informacionEvaluada.getCuota() && valorCuotaEvaluada>0.0){
                            excedenteAjuste += valorCuotaEvaluada-informacionEvaluada.getCuota();
                        }else if(valorCuotaEvaluada<informacionEvaluada.getCuota() && valorCuotaEvaluada>0.0){
                            pendienteAjuste += informacionEvaluada.getCuota()-valorCuotaEvaluada;
                        }
                        valorCuotaEvaluada = 0.0;
                        fechaCuotaEvaluada = SiguienteMes(fechaCuotaEvaluada, venta, 1);
                    }
                    //Obtener la primera fecha del ajuste
                    if((excedenteAjuste+pendienteAjuste)>valorSumaAjusteAnterior){
                        fechaInicioAjuste = fechaInicioAjusteAuxiliar;
                        fechaFinalAjuste = fechaFinalAjusteAuxiliar;
                    }
                    valorSumaAjusteAnterior = excedenteAjuste + pendienteAjuste;
                }
                //Actualizar rango para la fecha de aplicación de las cuotas de mantenimiento
                fechaAnteriorEvaluada = informacionEvaluada.getFechaAplicacion();
            }
            //Realizar el ajuste de las cuotas
            SimpleDateFormat sdf = new SimpleDateFormat("MMM/yy");
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            String observacionesAjuste = "";
            if((pendienteAjuste+excedenteAjuste)>0){
                observacionesAjuste = "Ajuste de cuotas de " + capitalize(sdf.format(fechaInicioAjuste)) + " a " + capitalize(sdf.format(fechaFinalAjuste));
            }
            if(pendienteAjuste>0){
                if(monto>=pendienteAjuste){
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(LocalDateTime.now());
                    cuota.setRecargo(0.0);
                    cuota.setDescuento(0.0);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setFechaCuota(fechaFinalAjuste);
                    cuota.setCuota(pendienteAjuste);
                    cuota.setSaldoCuota(0.0);
                    cuota.setPago(pago);
                    cuotaMantenimientoService.agregar(cuota);
                    monto-=pendienteAjuste;
                    //Actualizar la información del pago
                    observacionesAjuste +=".\nCon un monto pendiente de $ " + formato.format(pendienteAjuste);
                }else{
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(LocalDateTime.now());
                    cuota.setRecargo(0.0);
                    cuota.setDescuento(0.0);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setFechaCuota(fechaFinalAjuste);
                    cuota.setCuota(monto);
                    cuota.setSaldoCuota(pendienteAjuste-monto);
                    cuota.setPago(pago);
                    cuotaMantenimientoService.agregar(cuota);
                    monto-=monto;
                    //Actualizar la información del pago
                    observacionesAjuste +=".\nCon un monto pendiente de $ " + formato.format(pendienteAjuste);
                }
            }
            if(excedenteAjuste>0){
                monto += excedenteAjuste;
                //Actualizar la información del pago
                observacionesAjuste +=".\nCon un monto excedente de $ " + formato.format(excedenteAjuste);
            }
            if((excedenteAjuste+pendienteAjuste)>0){
                //Actualizar la información del pago
                observacionesAjuste +=".\n" + pago.getObservaciones();
                pago.setObservaciones(observacionesAjuste);
                pagoService.actualizar(pago);
            }
        }
        
        if(monto>0){
            //Verificar si se encuentra al día
            if(fechaCorte.after(fechaPago) || fechaCorte.equals(fechaPago)){
                //Registro de las cuotas
                if(monto >= informacionCuota.getCuota()){
                    while(monto>=informacionCuota.getCuota()){
                        informacionCuota = InformacionCuotaMantenimiento(venta, fechaCorte);
                        CuotaMantenimiento cuota = new CuotaMantenimiento();
                        cuota.setFechaRegistro(LocalDateTime.now());
                        cuota.setFechaCuota(fechaCorte);
                        cuota.setCuota(informacionCuota.getCuota());
                        cuota.setSaldoCuota(0.0);
                        cuota.setRecargo(0.0);
                        cuota.setDescuento(0.0);
                        cuota.setSaldoRecargo(0.0);
                        cuota.setPago(pago);
                        cuotaMantenimientoService.agregar(cuota);
                        monto-=informacionCuota.getCuota();
                        fechaCorte = SiguienteMes(fechaCorte, venta, 1);
                    }
                }
                //Registro de abono en caso de que exista
                if(monto>0){
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(LocalDateTime.now());
                    cuota.setFechaCuota(fechaCorte);
                    cuota.setCuota(monto);
                    cuota.setSaldoCuota(informacionCuota.getCuota()-monto);
                    cuota.setRecargo(0.0);
                    cuota.setDescuento(0.0);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setPago(pago);
                    cuotaMantenimientoService.agregar(cuota);
                    monto-=monto;
                    montoDescuento-=montoDescuento;
                }
            }else{ //Verificar si se encuentra en mora
                //Control del cobro del recargo para el abono para las cuotas con mora o sin mora
                boolean cobroRecargo = true;
                //Registro de las cuotas
                if(monto >= informacionCuota.getCuota()){
                    while (monto >= informacionCuota.getCuota()){
                        informacionCuota = InformacionCuotaMantenimiento(venta, fechaCorte);
                        CuotaMantenimiento cuota = new CuotaMantenimiento();
                        cuota.setFechaRegistro(LocalDateTime.now());
                        cuota.setFechaCuota(fechaCorte);
                        if(fechaCorte.after(fechaPago) || fechaCorte.equals(fechaPago)){
                            cuota.setRecargo(0.0);
                            cuota.setDescuento(0.0);
                            cuota.setSaldoRecargo(0.0);
                            cobroRecargo = false;
                        }else{
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
                            cobroRecargo = true;
                            monto-=informacionCuota.getMulta();
                        }
                        if(monto>informacionCuota.getCuota()){
                            cuota.setCuota(informacionCuota.getCuota());
                            cuota.setSaldoCuota(0.0);
                            monto-=informacionCuota.getCuota();
                        }else{
                            cuota.setCuota(monto);
                            cuota.setSaldoCuota(informacionCuota.getCuota()-monto);
                            monto-=monto;
                        }
                        cuota.setPago(pago);
                        cuotaMantenimientoService.agregar(cuota);
                        fechaCorte = SiguienteMes(fechaCorte, venta, 1);
                    }
                }
                //Registro de abono a cuota en caso de que exista
                if(monto>0){
                    informacionCuota = InformacionCuotaMantenimiento(venta, fechaCorte);
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(LocalDateTime.now());
                    cuota.setFechaCuota(fechaCorte);
                    if(cobroRecargo){
                        if(monto>=informacionCuota.getMulta()){
                            cuota.setRecargo(informacionCuota.getMulta());
                            cuota.setSaldoRecargo(0.0);
                            monto-=informacionCuota.getMulta();
                            //Aplicación del descuento a la cuota
                            if(montoDescuento>=informacionCuota.getMulta()){
                                cuota.setDescuento(informacionCuota.getMulta());
                                montoDescuento-=informacionCuota.getMulta();
                            }else{
                                cuota.setDescuento(montoDescuento);
                                montoDescuento-=montoDescuento;
                            }
                        }else{
                            cuota.setRecargo(monto);
                            cuota.setSaldoRecargo(informacionCuota.getMulta()-monto);
                            monto-=monto;
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
                    if(monto>=informacionCuota.getCuota()){
                        cuota.setCuota(informacionCuota.getCuota());
                        cuota.setSaldoCuota(0.0);
                        monto-=informacionCuota.getCuota();
                    }else{
                        cuota.setCuota(monto);
                        cuota.setSaldoCuota(informacionCuota.getCuota()-monto);
                        monto-=monto;
                    }
                    cuota.setPago(pago);
                    cuotaMantenimientoService.agregar(cuota);
                    montoDescuento-=montoDescuento;
                }
            }
        }
    }

    //Funcion que obtiene la informacion de la cuota de mantenimiento actual
    public InformacionMantenimiento InformacionCuotaMantenimiento(Venta venta, Date fechaActual) {
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(fechaActual);
        InformacionMantenimiento informacionCuota= new InformacionMantenimiento();
        List<InformacionMantenimiento> listaInformacion = informacionMantenimientoService.encontrarVenta(venta) ;
        for (InformacionMantenimiento informacionMantenimiento : listaInformacion) {
            Date fechaAplicacion = informacionMantenimiento.getFechaAplicacion();
            Calendar calendarAplicacion = Calendar.getInstance();
            calendarAplicacion.setTime(fechaAplicacion);
            if(calendarAplicacion.get(Calendar.YEAR) < calendarActual.get(Calendar.YEAR) ||
                (calendarAplicacion.get(Calendar.YEAR) == calendarActual.get(Calendar.YEAR) &&
                    calendarAplicacion.get(Calendar.MONTH) <= calendarActual.get(Calendar.MONTH))){
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

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }
}
