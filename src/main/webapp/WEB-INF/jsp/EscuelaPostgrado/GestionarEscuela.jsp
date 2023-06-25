<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div align="center">
     <div class="titulo-Perfil"><h3>Escuelas de Posgrados</h3></div>
     <div id="container-datos">
         
         <c:if test="${not empty mensaje}">
            <div class="alert alert-success d-flex align-items-center alert-dismissible fade show" role="alert">
                <strong><i class="bi bi-check-circle"></i> Éxito!  </strong> ${mensaje}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                <strong><i class="bi bi-exclamation-triangle"></i> Error!  </strong> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <br>
        
        <div class="row col-sm-12 d-flex justify-content-end">
            <div class="col-sm-1">
                <button type="button" class="btn btn-outline-primary btn-sm abrirModal-btn" 
                        data-bs-toggle="modal" data-bs-target="#crearModal" 
                        data-action="agregar">Agregar</button>
            </div>
        </div>
        
         <br>
         
         <div class="row col-sm-12">
             <table class="table">
                 <thead class="table">
                     <tr>
                        <th scope="col">N°</th>
                        <th scope="col">Nombre Escuela</th>
                        <th scope="col">Facultad</th>
                        <th scope="Opciones">Opciones</th>
                    </tr>
                 </thead>
                 
                 <tbody>
                     <c:if test="${empty escuela}">
                        <tr>
                            <td colspan="5">No hay registros</td>
                        </tr>
                    </c:if>
                        
                    <c:if test="${not empty escuela}">
                        <c:forEach items="${escuela}" var="elemento" varStatus="status">
                            <tr>
                                <td width="20%">${status.index + 1}</td>
                                <td>${elemento.nombrePostgrado}</td>
                                <td>${elemento.idFacultad}</td>
                                <td>
                                    <a href="#" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#confirmarEliminar-${elemento.idPostgrado}">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                    <!-- Modal de confirmación de eliminación -->
                                    <div class="modal fade" id="confirmarEliminar-${elemento.idPostgrado}" tabindex="-1" aria-labelledby="confirmarEliminarLabel-${elemento.idPostgrado}" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="confirmarEliminarLabel-${elemento.idPostgrado}">Confirmar eliminación</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <strong>¿Estás seguro de eliminar la escuela de posgrado seleccionada?</strong>
                                                </div>
                                                <div class="modal-footer">
                                                    <a href="/EliminarEscuelaPosgrado/${elemento.idPostgrado}" class="btn btn-danger">Eliminar</a>
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="button" class="btn btn-outline-primary abrirModal-btn" data-bs-toggle="modal" 
                                            data-bs-target="#crearModal" data-tipo="editar" data-id="${elemento.idPostgrado}" 
                                            data-modo="actualizar">
                                        <i class="bi bi-pencil-square"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>       
                 </tbody>    
             </table>
         </div> 
     </div>
</div>

<!-- Modal para las escuelas de posgrados -->
<div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true" data-tipo="" data-modo=''>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    
                    <input type="hidden" id="EscuelaId">
                    
                    <div class="form-group">
                        <input type="text" class="form-control" id="nombrePostgrado" name="nombrePostgrado" placeholder="Nombre de la Escuela" required>
                    </div>
                    
                     <div class="form-group">
                         <select class="form-control" id="idFacultad" name="idFacultad" required>
                            <option value="">Seleccione una facultad</option>
                            <c:forEach items="${facultad}" var="elementoFacultad" varStatus="status">
                                <option value="${elementoFacultad.idFacultad}">${elementoFacultad.nombreFacultad}</option>
                            </c:forEach>
                            
                        </select>
                     </div>

                    <div class="modal-footer">
                        <button id='btnSumit' type="submit" class="btn btn-outline-success guardar-btn">Guardar</button>
                        <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">Cancelar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jspf"%>
                    
<script>
    $(document).ready(function () {
        $('#formGuardar').submit(function (event) {
            event.preventDefault();//detiene el evento del envio del form 
            var idPostgrado = $('#EscuelaId').val();//tomo la id

            var formDataArray = $(this).serializeArray();//tomo los datos del array

            var url;//valido el tipo de url si editar o crear
            if (idPostgrado) {
                url = '/ActualizarEscuelaPosgrado';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idPostgrado', value: idPostgrado});
            } else {
                url = '/AgregarEscuelaPosgrado';
            }
            // Convertir el arreglo en un objeto
            var formData = {};
            $.map(formDataArray, function (n, i) {
                formData[n['name']] = n['value'];
            });
            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formData,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    location.reload();  // Recarga la página
                },
                error: function (error) {
                    console.log(error);
                }
            });
        });
        // metodo para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '.abrirModal-btn', function () {
            var idPostgrado = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');

            if (idPostgrado) {
                tituloModal.text('Editar Escuela de Posgrado');//titulo del modal
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerEscuelaPosgrado/' + idPostgrado,
                    type: 'GET',
                    success: function (response) {
                        $('#nombrePostgrado').val(response.nombrePostgrado);
                        console.log(response.idFacultad.idFacultad);
                        $('#idFacultad').val(response.idFacultad.idFacultad);
                        $('#EscuelaId').val(idPostgrado);
                    },
                    error: function () {
                        alert('Error al obtener los datos de la escuela de posgrado.');
                    }
                });
            } else {
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Escuela de Posgrado');
                form.attr('action', '/AgregarEscuelaPosgrado');
                $('#nombrePostgrado').val('');
                $('#idFacultad').val('');
                $('#EscuelaId').val('');
            }
            modal.modal('show');
        });
    });

</script>

