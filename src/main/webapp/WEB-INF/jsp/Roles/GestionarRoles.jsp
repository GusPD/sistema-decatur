<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div align="center">
    <div class="titulo-Perfil"><h3>Roles</h3></div>
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
                <button type="button" class="btn btn-outline-primary btn-sm abrirModal-btn" data-bs-toggle="modal" data-bs-target="#crearModal">Agregar</button>
            </div>
        </div>

        <br>

        <div class="row col-sm-12">
            <table class="table">
                <thead class="table">
                    <tr>
                        <th scope="col">N°</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Permisos</th>
                        <th scope="Opciones">Opciones</th>
                    </tr>
                </thead>

                <tbody>
                    <c:if test="${empty Roles}">
                        <tr>
                            <td colspan="5">No hay registros</td>
                        </tr>
                    </c:if>

                    <c:if test="${not empty Roles}">
                        <c:forEach items="${Roles}" var="elemento" varStatus="status">
                            <tr>
                                <td width="20%">${status.index + 1}</td>
                                <td>${elemento.nombre}</td>
                                <td>${elemento.permisos}</td>
                                <td>

                                    <a href="#" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#confirmarEliminar-${elemento.idRol}">
                                        <i class="bi bi-trash"></i>
                                    </a>


                                    <!-- Modal de confirmación de eliminación -->
                                    <div class="modal fade" id="confirmarEliminar-${elemento.idRol}" tabindex="-1" aria-labelledby="confirmarEliminarLabel-${elemento.idRol}" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="confirmarEliminarLabel-${elemento.idRol}">Confirmar eliminación</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <strong>¿Estás seguro de eliminar este rol?</strong>
                                                    <p>Ten en cuenta que se eliminarán los datos relacionados a ${elemento.nombre} .</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                                    <a href="/EliminarRol/${elemento.idRol}" class="btn btn-danger">Eliminar</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <button type="button" class="btn btn-outline-primary abrirModal-btn" data-bs-toggle="modal" 
                                            data-bs-target="#crearModal" data-tipo="editar" data-id="${elemento.idRol}" 
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

<!-- Modal para roles -->
<div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="crearModalLabel">Agregar Rol</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id='formGuardar' accept-charset="UTF-8">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <input type="hidden" id="rolId">
                    <div class="form-group">
                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required>
                    </div>

                    <div class="form-group">
                        <c:forEach items="${Permisos}" var="elementoPermiso" varStatus="status">
                            <div>
                                <li>
                                    <input class="checkClean" type="checkbox" id="permiso${elementoPermiso.idPermiso}" name="permisos[]" value="${elementoPermiso.idPermiso}">
                                    <label for="permiso${elementoPermiso.idPermiso}">${elementoPermiso.nombre}</label>
                                </li>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="modal-footer">
                        <button id='btnSumit' type="submit" class="btn btn-outline-success">Guardar</button>
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
            var idRol = $('#rolId').val();//tomo la id

            var formDataArray = $(this).serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idRol) {
                url = '/ActualizarRol';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idRol', value: idRol});
            } else {
                url = '/AgregarRol';
            }
            //console.log(formDataArray);
            // Convertir el arreglo en un objeto
            //var formData = {};
            //$.map(formDataArray, function (n, i) {
            //    formData[n['name']] = n['value'];
            //});
            //console.log(formData);
            //realizo el guardado mediante ajax
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');  // Cierra el modal
                    location.reload();  // Recarga la página
                },
                error: function (error) {
                    //console.log(error);
                }
            });
        });

        // metodo para mostrar el modal segun sea si editar o nuevo registro
        $(document).on('click', '.abrirModal-btn', function () {
            var idRol = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');

            if (idRol) {
                tituloModal.text('Editar Rol');//titulo del modal
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerRol/' + idRol,
                    type: 'GET',
                    success: function (response) {

                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#nombre').val(response.nombre);
                        //$('#permisos').val(response.permisos);
                        $.each(response.permisos, function (index, valor) {
                            var miCheckbox = document.getElementById('permiso' + valor.idPermiso);
                            if (miCheckbox !== null) {
                                miCheckbox.checked = true;
                            } else {
                                console.log("El checkbox no se encontró en el documento.");
                            }
                        });
                        $('#rolId').val(idRol);


                    },
                    error: function () {
                        alert('Error al obtener los datos del rol.');
                    }
                });
            } else {
                var checkboxes = document.querySelectorAll(".checkClean");

                for (var i = 0; i < checkboxes.length; i++) {
                    checkboxes[i].checked = false;
                }
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Rol');
                form.attr('action', '/AgregarRol');
                $('#nombre').val('');
                $('#permisos').val('');
                $('#rolId').val('');
            }
            modal.modal('show');
        });
    });

</script>                        

