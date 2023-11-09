package com.gl05.bad.controller;

import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.Empresa;
import com.gl05.bad.domain.InformacionFinanciamiento;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
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
    public DataTablesOutput<Pago> GetPagos(@Valid DataTablesInput input,  @PathVariable Long idProyecto) {
        return pagoService.listarPagos(input, idProyecto);
    }
    
    //Función para agregar un pago en la base de datos
    @PostMapping("/AgregarPago")
    public ResponseEntity<String> AgregarPago(Pago pago, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Date fechaActual = new Date();
            pago.setFechaRegistro(fechaActual);
            if(pago.getTipo()=="Prima"){
                pagoService.agregar(pago);
            }else if(pago.getTipo()=="Mantenimiento"){
                pagoService.agregar(pago);
                RegistroCuotaMantenimiento(pago);
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
            String mensaje = "Se ha eliminado un pago correctamente.";
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
            pagoService.actualizar(pago);
            String mensaje = "Se ha actualizado el pago correctamente.";
            bitacoraService.registrarAccion("Actualizar pago "+pago.getTipo().toLowerCase());
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el pago.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que registra las cuotas de mantenimiento del pago
    public void RegistroCuotaMantenimiento(Pago pago) {

        //Obtener la fecha actual
        Date fechaActual = new Date();

        //Obtener la información sobre el valor de la cuota a cancelar
        InformacionMantenimiento informacionCuota= InformacionCuotaMantenimiento(pago.getVenta()); 

        //Obtener la ultima cuota cancelada, la fecha de corte, la fecha de pago, y el monto para aplicar a las cuotas
        CuotaMantenimiento ultimaCuota = cuotaMantenimientoService.encontrarUltimaCuota(pago.getVenta());
        Date fechaCorteCuota = SiguienteMes(ultimaCuota.getFechaCuota(), pago.getVenta());
        Date fechaCorteMulta = SiguienteMes(ultimaCuota.getFechaRecargo(), pago.getVenta());
        Date fechaPago = pago.getFecha();
        double monto = pago.getMonto() + pago.getDescuento() - pago.getOtros();
        double montoDescuento = pago.getDescuento();
        
        //Verificación si existe un saldo que cancelar, para realizar el cobro
        if((ultimaCuota.getSaldoCuota() + ultimaCuota.getSaldoRecargo()) > 0){
            CuotaMantenimiento cuota = new CuotaMantenimiento();
            cuota.setFechaRegistro(fechaActual);
            cuota.setFechaCuota(ultimaCuota.getFechaCuota());
            cuota.setCuota(ultimaCuota.getSaldoCuota());
            cuota.setSaldoCuota(0.0);
            cuota.setFechaRecargo(ultimaCuota.getFechaRecargo());
            cuota.setRecargo(ultimaCuota.getSaldoRecargo());
            cuota.setDescuento(0.0);
            cuota.setSaldoRecargo(0.0);
            cuota.setPago(pago);
            monto-=(ultimaCuota.getSaldoCuota()+ultimaCuota.getSaldoRecargo());
        }

        //Verificar si se encuentra al día
        if(!fechaPago.before(fechaActual)){
            //Calculo de la cantidad de coutas a cancelar
            int cantidadCuotas = (int) Math.floor(monto / informacionCuota.getCuota());
            //Calculo del descuento para cada cuota
            double descuento = pago.getDescuento() / cantidadCuotas;
            //Verificación si existira un abono en la cuota
            double existeAbono = monto % informacionCuota.getCuota();
            //Registro de las cuotas
            if(monto >= informacionCuota.getCuota()){
                for (int i=0;i<cantidadCuotas;i++){
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(fechaActual);
                    cuota.setFechaCuota(fechaCorteCuota);
                    cuota.setCuota(informacionCuota.getCuota());
                    cuota.setSaldoCuota(0.0);
                    cuota.setFechaRecargo(fechaCorteMulta);
                    cuota.setRecargo(0.0);
                    cuota.setDescuento(descuento);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setPago(pago);
                    monto-=informacionCuota.getCuota();
                    montoDescuento-=descuento;
                    fechaCorteCuota = SiguienteMes(fechaCorteCuota, pago.getVenta());
                    fechaCorteMulta = SiguienteMes(fechaCorteMulta, pago.getVenta());
                }
            }
            //Registro de abono en caso de que exista
            if(existeAbono>0){
                CuotaMantenimiento cuota = new CuotaMantenimiento();
                cuota.setFechaRegistro(fechaActual);
                cuota.setFechaCuota(fechaCorteCuota);
                cuota.setCuota(monto);
                cuota.setSaldoCuota(informacionCuota.getCuota()-monto);
                cuota.setFechaRecargo(fechaCorteMulta);
                cuota.setRecargo(0.0);
                cuota.setDescuento(montoDescuento);
                cuota.setSaldoRecargo(0.0);
                cuota.setPago(pago);
                monto-=monto;
                montoDescuento-=montoDescuento;
            }
        }else if(!fechaPago.after(fechaActual)){ //Verificar si se encuentra en mora
            //Calculo de las cantidad de meses en mora
            int cantidadCuotasMora = CantidadMeses(fechaCorteCuota, fechaPago);
            double montoMora = cantidadCuotasMora * (informacionCuota.getCuota()+informacionCuota.getMulta());
            if(monto>=montoMora){
                monto-=montoMora;
            }else{
                montoMora=monto;
                monto=0;
            }
            //Calculo del descuento para cada cuota en mora
            double descuento = 0;
            if(cantidadCuotasMora>0){
                descuento = pago.getDescuento() / cantidadCuotasMora;
            }
            //Calculo de la cantidad de coutas a cancelar sin mora
            int cantidadCuotas = (int) Math.floor(monto / (informacionCuota.getCuota()));
            //Verificación si existira un abono en la cuota
            double existeAbono = 0;
            if(monto>=montoMora){
                existeAbono = monto % (informacionCuota.getCuota());
            }else{
                existeAbono = montoMora % (informacionCuota.getCuota()+informacionCuota.getMulta());
            }
            //Registro de las cuotas con mora
            if(montoMora >= (informacionCuota.getCuota()+informacionCuota.getMulta())){
                for (int i=0;i<cantidadCuotasMora;i++){
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(fechaActual);
                    cuota.setFechaCuota(fechaCorteCuota);
                    cuota.setCuota(informacionCuota.getCuota());
                    cuota.setSaldoCuota(0.0);
                    cuota.setFechaRecargo(fechaCorteMulta);
                    cuota.setRecargo(informacionCuota.getMulta());
                    cuota.setDescuento(descuento);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setPago(pago);
                    montoMora-=(informacionCuota.getCuota()+informacionCuota.getMulta());
                    montoDescuento-=descuento;
                    fechaCorteCuota = SiguienteMes(fechaCorteCuota, pago.getVenta());
                    fechaCorteMulta = SiguienteMes(fechaCorteMulta, pago.getVenta());
                }
            }
            //Registro de las cuotas sin mora
            if(monto >= informacionCuota.getCuota()){
                for (int i=0;i<cantidadCuotas;i++){
                    CuotaMantenimiento cuota = new CuotaMantenimiento();
                    cuota.setFechaRegistro(fechaActual);
                    cuota.setFechaCuota(fechaCorteCuota);
                    cuota.setCuota(informacionCuota.getCuota());
                    cuota.setSaldoCuota(0.0);
                    cuota.setFechaRecargo(fechaCorteMulta);
                    cuota.setRecargo(0.0);
                    cuota.setDescuento(0.0);
                    cuota.setSaldoRecargo(0.0);
                    cuota.setPago(pago);
                    monto-=informacionCuota.getCuota();
                    fechaCorteCuota = SiguienteMes(fechaCorteCuota, pago.getVenta());
                    fechaCorteMulta = SiguienteMes(fechaCorteMulta, pago.getVenta());
                }
            }
            //Registro de abono a cuota en caso de que exista
            if(existeAbono>0){
                double montoEvaluar = monto + montoMora;
                CuotaMantenimiento cuota = new CuotaMantenimiento();
                cuota.setFechaRegistro(fechaActual);
                cuota.setFechaRecargo(fechaCorteMulta);
                cuota.setDescuento(montoDescuento);
                if(montoEvaluar>=informacionCuota.getMulta()){
                    cuota.setRecargo(informacionCuota.getMulta());
                    cuota.setSaldoRecargo(0.0);
                    montoEvaluar-=informacionCuota.getMulta();
                }else{
                    cuota.setRecargo(montoEvaluar);
                    cuota.setSaldoRecargo(informacionCuota.getMulta()-montoEvaluar);
                    montoEvaluar-=montoEvaluar;
                }
                cuota.setFechaCuota(fechaCorteCuota);
                if(montoEvaluar>=informacionCuota.getCuota()){
                    cuota.setCuota(informacionCuota.getCuota());
                    cuota.setSaldoCuota(0.0);
                    montoEvaluar-=informacionCuota.getCuota();
                }else{
                    cuota.setCuota(monto);
                    cuota.setSaldoCuota(informacionCuota.getCuota()-montoEvaluar);
                    montoEvaluar-=montoEvaluar;
                }
                cuota.setPago(pago);
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
    public Date SiguienteMes(Date fechaCorte, Venta venta) {
        Date fechaEscritura = venta.getFecha();

        // Convierte la fecha a Calendar
        Calendar calEscritura = Calendar.getInstance();
        calEscritura.setTime(fechaEscritura);

        Calendar calCorte = Calendar.getInstance();
        calCorte.setTime(fechaCorte);

        // Incrementa el mes en 1
        calCorte.add(Calendar.MONTH, 1);

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
