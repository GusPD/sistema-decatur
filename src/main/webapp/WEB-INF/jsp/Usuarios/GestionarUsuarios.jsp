<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div align="center">
<div class="titulo-Perfil"><h3>Usuarios</h3></div>
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
                    <th scope="col">Nombre de Usuario</th>
                    <th scope="col">Correo</th>
                    <th scope="col">Habilitado</th>
                    <th scope="col">Bloqueado</th>
                    <th scope="Opciones">Opciones</th>
                </tr>
            </thead>

            <tbody>
                <c:if test="${empty usuarios}">
                    <tr>
                        <td colspan="5">No hay registros</td>
                    </tr>
                </c:if>

                <c:if test="${not empty usuarios}">
                    <c:forEach items="${usuarios}" var="elemento" varStatus="status">
                        <tr>
                            <td width="20%">${status.index + 1}</td>
                            <td>${elemento.username}</td>
                            <td>${elemento.email}</td>
                            
                             <c:if test="${elemento.enabled == true}">
                                    <td>Si</td>
                             </c:if>
                                    
                             <c:if test="${elemento.enabled == false}">
                                    <td>No</td>
                             </c:if>       
                            
                            <c:if test="${elemento.usuarioBloqueado == 0}">
                                <td>No</td>
                             </c:if>
                                
                             <c:if test="${elemento.usuarioBloqueado == 1}">
                                <td>Si</td>
                             </c:if>
                                
                            <td>

                                <a href="#" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#confirmarEliminar-${elemento.idUsuario}">
                                    <i class="bi bi-trash"></i>
                                </a>

                                <!-- Modal de confirmación de eliminación -->
                                <div class="modal fade" id="confirmarEliminar-${elemento.idUsuario}" tabindex="-1" aria-labelledby="confirmarEliminarLabel-${elemento.idUsuario}" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="confirmarEliminarLabel-${elemento.idUsuario}">Confirmar eliminación</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <strong>¿Estás seguro de eliminar este usuario?</strong>
                                                <p>Ten en cuenta que se eliminarán los datos relacionados a ${elemento.username} .</p>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                                <a href="/EliminarUsuario/${elemento.idUsuario}" class="btn btn-danger">Eliminar</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>    

                                <button type="button" class="btn btn-outline-primary abrirModal-btn" data-bs-toggle="modal" 
                                        data-bs-target="#crearModal" data-tipo="editar" data-id="${elemento.idUsuario}" 
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

    <!-- Modal para Usuarios -->
    <div class="modal fade" id="crearModal" tabindex="-1" aria-labelledby="crearModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="crearModalLabel">Agregar Usuario</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id='formGuardar' accept-charset="UTF-8">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <input type="hidden" id="UsuarioId">

                        <div class="form-group">
                            <input type="text" class="form-control" id="username" name="username" placeholder="Nombre de Usuario" required>
                        </div>

                        <div class="form-group">
                            <input type="text" class="form-control" id="email" name="email" placeholder="Correo" required>
                        </div>

                        <div class="form-group">
                            <input type="text" class="form-control" id="password" name="password" placeholder="Contraseña">
                        </div>
                        
                        <input type="hidden" name="numerointentos" value="0">


                        <div class="form-group oculto" hidden>
                            <select class="form-control" id="enabled" name="enabled" required>
                                <option value="1">Habilitado</option>
                                <option value="0">Deshabilitado</option>
                            </select>
                        </div>
                        
                        <div class="form-group oculto" hidden>
                            <select class="form-control" id="usuarioBloqueado" name="usuarioBloqueado" required>
                                <option value="0" >Desbloqueado</option>
                                <option value="1" >Bloqueado</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <c:forEach items="${roles}" var="elementoRol" varStatus="status" >
                                <div>
                                    <li>
                                        <input class="checkClean" type="checkbox" id="rol${elementoRol.idRol}" name="roles[]" value="${elementoRol.idRol}">
                                        <label for="rol${elementoRol.idRol}">${elementoRol.nombre}</label>
                                    </li>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-outline-success">Guardar</button>
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
            var idUsuario = $('#UsuarioId').val();//tomo la id

            var formDataArray = $(this).serializeArray();//tomo los datos del array

            console.log(formDataArray);
            var url;//valido el tipo de url si editar o crear
            if (idUsuario) {
                url = '/ActualizarUsuario';
                //meto la id en el campo de envio
                formDataArray.push({name: 'idUsuario', value: idUsuario});
            } else {
                url = '/AgregarUsuario';
            }

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
            var idUsuario = $(this).data('id');
            var modal = $('#crearModal');
            var tituloModal = modal.find('.modal-title');
            var form = modal.find('form');
            var btnSumit = document.getElementById('btnSumit');
            

            if (idUsuario) {
                tituloModal.text('Editar Usuario');//titulo del modal
                //con esto quito los campos de oculto para que muestre para habilitar/deshabilitar
                // y bloquear/desbloquear
                $('.oculto').removeAttr('hidden');
                
                $.ajax({//utilizo ajax para obtener los datos
                    url: '/ObtenerUsuario/' + idUsuario,
                    type: 'GET',
                    success: function (response) {
                       
                        var checkboxes = document.querySelectorAll(".checkClean");

                        for (var i = 0; i < checkboxes.length; i++) {
                            checkboxes[i].checked = false;
                        }
                        $('#username').val(response.username);
                        $('#email').val(response.email);
                        //$('#password').val(response.password);

                        $.each(response.roles, function (index, valor) {
                            var miCheckbox = document.getElementById('rol' + valor.idRol);
                            if (miCheckbox !== null) {
                                miCheckbox.checked = true;
                            } else {
                                console.log("El checkbox no se encontró en el documento.");
                            }
                        });
                       
                       //esto es para habilitado/desabilitado
                       if(response.enabled === true){
                           $('#enabled').val(1);
                       }else{
                           $('#enabled').val(0);
                       }
                       
                       //console.log(response.enabled);
                       $('#usuarioBloqueado').val(response.usuarioBloqueado);
                       //console.log(response.usuarioBloqueado);
                       $('#UsuarioId').val(idUsuario);

                    },
                    error: function () {
                        alert('Error al obtener los datos del usuario.');
                    }
                });
            } else {
                var checkboxes = document.querySelectorAll(".checkClean");
                $('.oculto').attr('hidden', true);

                for (var i = 0; i < checkboxes.length; i++) {
                    checkboxes[i].checked = false;
                }
                // en caso de presionar el boton de nuevo solo se abrira el modal
                tituloModal.text('Agregar Usuario');
                form.attr('action', '/AgregarUsuario');
                $('#username').val('');
                $('#email').val('');
                $('#password').val('');
                $('#UsuarioId').val('');

            }
            modal.modal('show');
        });
    });

</script>                             


