package com.gl05.bad.controller;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.InformeMantenimiento;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.VentaService;
import com.gl05.bad.servicio.CuotaMantenimientoService;
import com.gl05.bad.servicio.InformacionMantenimientoService;
import com.gl05.bad.servicio.InformeMantenimientoService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InformeMantenimientoController {
    
    @Autowired
    private InformeMantenimientoService informeMantenimientoService;
    
    @Autowired
    private InformacionMantenimientoService informacionMantenimientoService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private CuotaMantenimientoService cuotaMantenimientoService;
    
    //Función que redirige a la vista del informe mantenimiento del proyecto
    @GetMapping("/InformeMantenimiento/{idProyecto}")
    public String mostrarInformeMantenimientoProyecto(Model model, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Informe Mantenimiento");
        Proyecto proyectoEncontrado = proyectoService.encontrar(proyecto.getIdProyecto());
        InformeMantenmientoMora(proyectoEncontrado.getIdProyecto());
        model.addAttribute("proyecto", proyectoEncontrado);
        return "/Informes/InformeMantenimiento";
    }
    
    //Función que obtiene el informe mantenimiento del proyecto
    @GetMapping("/InformeMantenimiento/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<InformeMantenimiento> GetInformeMantenimiento(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return informeMantenimientoService.listarInforme(input, idProyecto);
    }

    //Función que actualiza la información del informe de mantenimiento
    public void InformeMantenmientoMora (Long idProyecto){
        Proyecto proyectoEncontrado = proyectoService.encontrar(idProyecto);
        List<Venta> listadoVentasProyecto = ventaService.encontrarActivas(proyectoEncontrado);
        List<InformeMantenimiento> listaRegistrosInforme = informeMantenimientoService.listaInformeMantenimientosProyecto(proyectoEncontrado);
        boolean registroEncontrado = false;
        for (InformeMantenimiento informeMantenimiento : listaRegistrosInforme) {
            for (Venta ventaEvaluada : listadoVentasProyecto) {
                if(informeMantenimiento.getVenta().getIdVenta().equals(ventaEvaluada.getIdVenta())){
                    registroEncontrado = true;
                    if(informacionMantenimientoService.encontrarVenta(ventaEvaluada).isEmpty()){
                        registroEncontrado = false;
                    }
                }
            }
            if(!registroEncontrado){
                informeMantenimientoService.eliminar(informeMantenimiento);
            }
        }
        //Establecer el primer día del siguiente mes
        Calendar calendarFechaSiguiente = Calendar.getInstance();
        calendarFechaSiguiente.add(Calendar.MONTH, 1);
        calendarFechaSiguiente.set(Calendar.DAY_OF_MONTH, 1);
        Date primerDiaSiguienteMes = calendarFechaSiguiente.getTime();
        //Establecer el primer día del mes actual
        Calendar calendarFechaAnterior = Calendar.getInstance();
        calendarFechaAnterior.set(Calendar.DAY_OF_MONTH, 1);
        Date primerDiaMesActual = calendarFechaAnterior.getTime();
        for (Venta venta : listadoVentasProyecto) {
            CuotaMantenimiento ultimaCuota = cuotaMantenimientoService.encontrarUltimaCuota(venta);
            List<InformacionMantenimiento> listInformacionMantenimiento = informacionMantenimientoService.encontrarVenta(venta);
            if(!listInformacionMantenimiento.isEmpty()){
                //Convertir la fecha de la cuota a Calendar
                Date ultimaCuotaDate = ultimaCuota.getFechaCuota();
                if(ultimaCuotaDate.after(primerDiaSiguienteMes)){
                    InformeMantenimiento registroInforme = informeMantenimientoService.encontrarVenta(venta);
                    if(registroInforme == null){
                        registroInforme = new InformeMantenimiento();
                    }                
                    registroInforme.setVenta(venta);
                    registroInforme.setLote(venta.getTerreno().getPoligono()+"-"+venta.getTerreno().getNumero()+venta.getTerreno().getSeccion());
                    if(ultimaCuota.getPago()!=null){
                        registroInforme.setFechaPago(ultimaCuota.getPago().getFecha());
                    }else{
                        registroInforme.setFechaPago(ultimaCuota.getFechaCuota());
                    }
                    registroInforme.setFechaCuota(ultimaCuota.getFechaCuota());
                    registroInforme.setEstado("Adelantado");
                    registroInforme.setCuota(cuotaMantenimientoService.montoAdelantado(venta));
                    registroInforme.setMulta(0.0);
                    informeMantenimientoService.agregar(registroInforme);
                }else if(ultimaCuotaDate.before(primerDiaMesActual)){
                    List<Double> montoMora = calculoMora(venta);
                    InformeMantenimiento registroInforme = informeMantenimientoService.encontrarVenta(venta);
                    if(registroInforme == null){
                        registroInforme = new InformeMantenimiento();
                    } 
                    registroInforme.setVenta(venta);
                    registroInforme.setLote(venta.getTerreno().getPoligono()+"-"+venta.getTerreno().getNumero()+venta.getTerreno().getSeccion());
                    if(ultimaCuota.getPago()!=null){
                        registroInforme.setFechaPago(ultimaCuota.getPago().getFecha());
                    }else{
                        registroInforme.setFechaPago(ultimaCuota.getFechaCuota());
                    }
                    registroInforme.setFechaCuota(ultimaCuota.getFechaCuota());
                    registroInforme.setEstado("Mora");
                    registroInforme.setCuota(montoMora.get(0));
                    registroInforme.setMulta(montoMora.get(1));
                    informeMantenimientoService.agregar(registroInforme);
                }else{
                    if((ultimaCuota.getSaldoCuota()+ultimaCuota.getSaldoRecargo())>0){
                        InformeMantenimiento registroInforme = informeMantenimientoService.encontrarVenta(venta);
                        if(registroInforme == null){
                            registroInforme = new InformeMantenimiento();
                        } 
                        registroInforme.setVenta(venta);
                        registroInforme.setLote(venta.getTerreno().getPoligono()+"-"+venta.getTerreno().getNumero()+venta.getTerreno().getSeccion());
                        if(ultimaCuota.getPago()!=null){
                            registroInforme.setFechaPago(ultimaCuota.getPago().getFecha());
                        }else{
                            registroInforme.setFechaPago(ultimaCuota.getFechaCuota());
                        }
                        registroInforme.setFechaCuota(ultimaCuota.getFechaCuota());
                        registroInforme.setEstado("Pendiente");
                        registroInforme.setCuota(ultimaCuota.getSaldoCuota());
                        registroInforme.setMulta(ultimaCuota.getSaldoRecargo());
                        informeMantenimientoService.agregar(registroInforme);
                    }else{
                        InformeMantenimiento registroInforme = informeMantenimientoService.encontrarVenta(venta);
                        if(registroInforme == null){
                            registroInforme = new InformeMantenimiento();
                        } 
                        registroInforme.setVenta(venta);
                        registroInforme.setLote(venta.getTerreno().getPoligono()+"-"+venta.getTerreno().getNumero()+venta.getTerreno().getSeccion());
                        if(ultimaCuota.getPago()!=null){
                            registroInforme.setFechaPago(ultimaCuota.getPago().getFecha());
                        }else{
                            registroInforme.setFechaPago(ultimaCuota.getFechaCuota());
                        }
                        registroInforme.setFechaCuota(ultimaCuota.getFechaCuota());
                        registroInforme.setEstado("Cancelado");
                        registroInforme.setCuota(0.0);
                        registroInforme.setMulta(0.0);
                        informeMantenimientoService.agregar(registroInforme);
                    }
                }
            }
        }
    }

    //Función que calcula el monto de la mora correspondiente
    public List<Double> calculoMora(Venta venta){
        List<Double> montoMora = new ArrayList<>();
        CuotaMantenimiento ultimaCuota = cuotaMantenimientoService.encontrarUltimaCuota(venta);
        //Convertir la fecha de la cuota a Calendar
        Calendar calendarFechaCuota = Calendar.getInstance();
        Date ultimaCuotaDate = SiguienteMes(ultimaCuota.getFechaCuota(), venta, 1);
        //Obtener la información para la cuota correspondiente
        InformacionMantenimiento informacionCuota = InformacionCuotaMantenimiento(venta,ultimaCuotaDate);
        //Agregar los saldos de la ultima cuota al monto de la mora
        double montoCuota = ultimaCuota.getSaldoCuota();
        double montoRecargo = ultimaCuota.getSaldoRecargo();
        //Establecer el primer día del siguiente mes
        Calendar calendarFechaSiguiente = Calendar.getInstance();
        calendarFechaSiguiente.add(Calendar.MONTH, 1);
        calendarFechaSiguiente.set(Calendar.DAY_OF_MONTH, 1);
        Date primerDiaSiguienteMes = calendarFechaSiguiente.getTime();
        while (ultimaCuotaDate.before(primerDiaSiguienteMes)) {
            montoCuota += informacionCuota.getCuota();
            montoRecargo += informacionCuota.getMulta();
            ultimaCuotaDate = SiguienteMes(ultimaCuotaDate, venta, 1);
            calendarFechaCuota.setTime(ultimaCuotaDate);
            informacionCuota = InformacionCuotaMantenimiento(venta, ultimaCuotaDate); 
        }
        montoMora.add(montoCuota);
        montoMora.add(montoRecargo);
        return montoMora;
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
