package com.gl05.bad.controller;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Venta;
import com.gl05.bad.servicio.AsignacionPropietarioService;
import com.gl05.bad.servicio.CuotaMantenimientoService;
import com.gl05.bad.servicio.InformacionMantenimientoService;
import com.gl05.bad.servicio.PagoService;
import com.gl05.bad.servicio.VentaService;

@Controller
public class ReporteController {

    @Autowired
    private CuotaMantenimientoService cuotaMantenimientoService;

    @Autowired
    private InformacionMantenimientoService informacionMantenimientoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private AsignacionPropietarioService asignacionPropietarioService;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/EstadoCuentaMantenimiento/{idVenta}")
    public  String EstadoCuentaMantenimiento(@PathVariable Long idVenta, Model model) {
        Venta venta = ventaService.encontrar(idVenta);
        List<CuotaMantenimiento> cuotas = cuotaMantenimientoService.listaCuotaMantenimientos(venta);
        List<AsignacionPropietario> listaPropietarios = asignacionPropietarioService.listaAsignacion(venta);
        model.addAttribute("cuotas", cuotas);
        model.addAttribute("venta", venta);
        model.addAttribute("propietarios", listaPropietarios);
        return "Reportes/EstadoCuentaMantenimiento";
    }

    @GetMapping("/ComprobantePago/{idPago}")
    public  String ReciboMantenimiento(@PathVariable Long idPago, Model model) {
        String propietario = "";
        String cuota = "";
        String recargo = "";
        String estado = "Estatus: ";
        String pendiente = "Saldo de ";
        double montoMantenimiento=0.0;
        double montoRecargo=0.0;
        double montoPendiente=0.0;
        Pago pago = pagoService.encontrar(idPago);
        List<CuotaMantenimiento> listaCuotaMantenimientos = cuotaMantenimientoService.encontrarPago(pago);
        Venta venta = ventaService.encontrar(pago.getVenta().getIdVenta());
        List<AsignacionPropietario> listaPropietarios = asignacionPropietarioService.listaAsignacionPropietarioSeleccionado(venta);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yy");
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        //Obtener el nombre del propietario
        if(!listaPropietarios.isEmpty()){
            AsignacionPropietario asignacionPropietario = listaPropietarios.get(0);
            //String[] nombres = asignacionPropietario.getPropietario().getPersona().getNombre().split(" ");
            //String[] apellidos = asignacionPropietario.getPropietario().getPersona().getApellido().split(" ");
            //propietario += nombres[0] + " " + apellidos[0];
            propietario += asignacionPropietario.getPropietario().getPersona().getNombre() + " " + asignacionPropietario.getPropietario().getPersona().getApellido();
            if(listaPropietarios.size()>1){
                 propietario += " y otros.";
            }
        }
        //Obtener el concepto de las cuotas canceladas
        int variableControl = 0;
        boolean primeraPosicion = false;
        boolean segundaPosicion = false;
        CuotaMantenimiento cuotaMantenimientoAnterior = new CuotaMantenimiento();
        if(!listaCuotaMantenimientos.isEmpty()){
            for (CuotaMantenimiento cuotaMantenimiento : listaCuotaMantenimientos) {
                InformacionMantenimiento informacionCuota = InformacionCuotaMantenimiento(venta, cuotaMantenimiento.getFechaCuota());
                //Solo existe una cuota registrada
                if(variableControl==0){
                    if(cuotaMantenimiento.getCuota()<informacionCuota.getCuota() && cuotaMantenimiento.getSaldoCuota()>0){
                        cuota = "Ab. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        segundaPosicion = true;
                    }else if(cuotaMantenimiento.getCuota()<informacionCuota.getCuota() && cuotaMantenimiento.getSaldoCuota()==0){
                        cuota = "Compl. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        segundaPosicion = true;
                    }else{
                        cuota = capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        primeraPosicion = true;
                    }
                }
                //Solo existe dos cuotas registradas
                if(listaCuotaMantenimientos.size()==2 && variableControl==1){
                    if(cuotaMantenimiento.getCuota()<informacionCuota.getCuota() && cuotaMantenimiento.getSaldoCuota()>0){
                        cuota += ", Ab. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                    }else{
                        if(primeraPosicion){
                            cuota += " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        }else{
                            cuota += ", " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        }
                    }
                }
                //Solo existe 3 cuotas registradas
                if(listaCuotaMantenimientos.size()>=3 && variableControl==1){
                    if(listaCuotaMantenimientos.size()==3){
                        if(primeraPosicion){
                            cuota += " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        }else if(segundaPosicion){
                            cuota += ", " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        }
                    }else{
                        if(segundaPosicion){
                            cuota += ", " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        }                        
                    }
                }
                if(listaCuotaMantenimientos.size()==3 && variableControl==2){
                    if(cuotaMantenimiento.getCuota()<informacionCuota.getCuota() && cuotaMantenimiento.getSaldoCuota()>0){
                        cuota += ", Ab. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                    }else{
                        cuota +=  " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                    }
                }
                //Solo existen mas de 4 cuotas registradas
                if(listaCuotaMantenimientos.size()>=4 && variableControl==(listaCuotaMantenimientos.size()-1)){
                    if(cuotaMantenimiento.getCuota()<informacionCuota.getCuota() && cuotaMantenimiento.getSaldoCuota()>0){
                        cuota += " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                        cuota += ", Ab. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                    }else{
                        cuota += " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                    }
                }
                if((listaCuotaMantenimientos.size()-1)==variableControl){
                    montoPendiente = cuotaMantenimiento.getSaldoCuota() + cuotaMantenimiento.getSaldoRecargo();
                    pendiente += capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()))+ " $" + formato.format(montoPendiente) + ".";
                    if(pago.getFecha().after(cuotaMantenimiento.getFechaCuota())){
                        estado += "Mora";
                    }else{
                        estado += "Solvente";
                    }                  
                }
                cuotaMantenimientoAnterior = cuotaMantenimiento;
                montoMantenimiento+=cuotaMantenimiento.getCuota();
                montoRecargo+=cuotaMantenimiento.getRecargo();
                variableControl++;
            }
        }
        //Obtener el concepto de los recargos cancelados
        if(montoRecargo>0){
            variableControl = 0;
            primeraPosicion = false;
            segundaPosicion = false;
            cuotaMantenimientoAnterior = new CuotaMantenimiento();
            if(!listaCuotaMantenimientos.isEmpty()){
                for (CuotaMantenimiento cuotaMantenimiento : listaCuotaMantenimientos) {
                    if(cuotaMantenimiento.getRecargo()>0){
                        InformacionMantenimiento informacionCuota = InformacionCuotaMantenimiento(venta, cuotaMantenimiento.getFechaCuota());
                        //Solo existe una cuota registrada
                        if(variableControl==0){
                            if(cuotaMantenimiento.getRecargo()<informacionCuota.getMulta() && cuotaMantenimiento.getSaldoRecargo()>0){
                                recargo = "Ab. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                segundaPosicion = true;
                            }else if(cuotaMantenimiento.getRecargo()<informacionCuota.getMulta() && cuotaMantenimiento.getSaldoRecargo()==0){
                                recargo = "Compl. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                segundaPosicion = true;
                            }else{
                                recargo = capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                primeraPosicion = true;
                            }
                        }
                        //Solo existe dos cuotas registradas
                        if(listaCuotaMantenimientos.size()==2 && variableControl==1){
                            if(cuotaMantenimiento.getRecargo()<informacionCuota.getMulta() && cuotaMantenimiento.getSaldoRecargo()>0){
                                recargo += ", Ab. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                            }else{
                                if(primeraPosicion){
                                    recargo += " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                }else{
                                    recargo += ", " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                }
                            }
                        }
                        //Solo existe 3 cuotas registradas
                        if(listaCuotaMantenimientos.size()>=3 && variableControl==1){
                            if(listaCuotaMantenimientos.size()==3){
                                if(primeraPosicion){
                                    recargo += " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                }else if(segundaPosicion){
                                    recargo += ", " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                }
                            }else{
                                if(segundaPosicion){
                                    recargo += ", " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                }                        
                            }
                        }
                        if(listaCuotaMantenimientos.size()==3 && variableControl==2){
                            if(cuotaMantenimiento.getRecargo()<informacionCuota.getMulta() && cuotaMantenimiento.getSaldoRecargo()>0){
                                recargo += ", Ab. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                            }else{
                                recargo +=  " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                            }
                        }
                        //Solo existen mas de 4 cuotas registradas
                        if(listaCuotaMantenimientos.size()>=4 && variableControl==(listaCuotaMantenimientos.size()-1)){
                            if(cuotaMantenimiento.getRecargo()<informacionCuota.getMulta() && cuotaMantenimiento.getSaldoRecargo()>0){
                                recargo += " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                                recargo += ", Ab. " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                            }else{
                                recargo += " a " + capitalize(sdf.format(cuotaMantenimiento.getFechaCuota()));
                            }
                        }
                        cuotaMantenimientoAnterior = cuotaMantenimiento;
                        variableControl++;
                    }
                }
            }
        }
        model.addAttribute("montoMantenimiento", montoMantenimiento);
        model.addAttribute("montoRecargo", montoRecargo);
        model.addAttribute("pendiente", pendiente);
        model.addAttribute("montoPendiente", montoPendiente);
        model.addAttribute("pago", pago);
        model.addAttribute("cuota", cuota);
        model.addAttribute("cuotaMantenimientoAnterior", cuotaMantenimientoAnterior);
        model.addAttribute("recargo", recargo);
        model.addAttribute("cuotas", listaCuotaMantenimientos);
        model.addAttribute("venta", venta);
        model.addAttribute("estado", estado);
        model.addAttribute("propietario", propietario);
        model.addAttribute("propietarios", listaPropietarios);
        if(pago.getComprobante().equals("Factura")){
            return "Reportes/ReciboMantenimiento";
        }else{
            return "Reportes/CreditoFiscalMantenimiento";
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

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }
}