$(document).ready(function() {
    //Tabla
    var idProyecto = $('#proyectoId').data('id');
    var table = $('#terrenoTable').DataTable({
        ajax: {
            url: '/terrenos/data/' + idProyecto,
            dataSrc: 'data'
        },
        order: [[0, 'asc'],[1, 'asc'],[2, 'asc']],
        processing: true,
        serverSide: true,
        dom: "<'row w-100'<'col-sm-12 mb-4'B>>" +
             "<'row w-100'<'col-sm-6'l><'col-sm-6'f>>" +
             "<'row w-100'<'col-sm-12 my-4'tr>>" +
             "<'row w-100'<'col-sm-5'i><'col-sm-7'p>>",
        lengthMenu: [[5, 25, 50, 100, -1], [5, 25, 50, 100, 'Todos']],
        pageLength: 5,
        buttons: [
            {
                extend: 'copy',
                text: 'Copiar',
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5]
                }
            },
            {
                extend: 'excel',
                text: 'Exportar a Excel',
                title: 'Terrenos del proyecto',
                filename: 'Terrenos ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5]
                }
            },
            {
                extend: 'pdf',
                text: 'Exportar a PDF',
                title: 'Terrenos del proyecto',
                filename: 'Terrenos ' + getCurrentDateTime(),
                exportOptions: {
                  columns: [0, 1, 2, 3, 4, 5]
                },
                customize: function (doc) {
                  doc.content[1].table.widths = Array(doc.content[1].table.body[0].length + 1).join('*').split('');
                }
            }
        ],
        columns: [
            {
                data: null,
                title: "N°",
                sortable: false,
                searchable: false,
                render: function (data, type, row, meta) {
                    return meta.row + 1;
                },
                width: '10%'
            },
            { data: 'poligono', title:'Polígono', width: '10%' },
            { data: 'lote', title:'Lote', width: '10%' },
            { data: 'matricula', title:'Matrícula', width: '20%', sortable: false, searchable: false,},
            {
                data: 'areaMetros',
                width: '15%',
                title: "Área (m²)",
                sortable: false,
                searchable: false,
                render: function(data, type, row) {
                    if (type === 'display' || type === 'filter') {
                        return parseFloat(data).toFixed(2);
                    }
                    return data;
                }
            },
            {
                data: 'areaVaras',
                width: '15%',
                title: "Área (v²)",
                sortable: false,
                searchable: false,
                render: function(data, type, row) {
                    if (type === 'display' || type === 'filter') {
                        return parseFloat(data).toFixed(2);
                    }
                    return data;
                }
            },
            {
                data: null,
                title: 'Acciones',
                sortable: false,
                searchable: false,
                width: '20%',
                render: function (data, type, row) {
                    var actionsHtml = '';
                    if(hasPrivilegeVerTerreno === true){
                        actionsHtml = '<a title="Ver" type="button" class="btn btn-outline-secondary btn-sm" href="/Ventas/' + row.idTerreno + '">';
                        actionsHtml += '<i class="far fa-eye"></i></a>';
                    }
                    if(hasPrivilegeEditarTerreno === true){
                        actionsHtml += '<button  title="Editar" type="button" class="btn btn-outline-primary abrirModal-btn btn-sm" data-bs-toggle="modal" ';
                        actionsHtml += 'data-bs-target="#crearModal" data-tipo="editar" data-id="' + row.idTerreno + '" data-modo="actualizar">';
                        actionsHtml += '<i class="far fa-edit"></i></button>';
                    }
                    if(hasPrivilegeEliminarTerreno === true){
                        actionsHtml += '<button  title="Eliminar" type="button" class="btn btn-outline-danger eliminarModal-btn btn-sm" data-id="' + row.idTerreno + '" ';
                        actionsHtml += 'data-cod="' + row.idTerreno + '">';
                        actionsHtml += '<i class="far fa-trash-alt"></i></button>';
                    }
                    return actionsHtml || '';
                }
            }
        ],
        language: {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "Ningún dato disponible en esta tabla",
            "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered": "",
            "sInfoPostFix": "",
            "sSearch": "Buscar:",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            },
            "buttons": {
                "copy": "Copiar",
                "copyTitle": "Copiar al portapapeles",
                copySuccess: {
                  _: "%d filas copiadas al portapapeles",
                  1: "1 fila copiada al portapapeles"
                }
            }
        },
        search: {
            return: true
        },
        ordering: {
            return: true
        }
    });
    table.columns.adjust();
    $('#export-pdf').on('click', function() {
        table.button('.buttons-pdf').trigger();
    });
    $('#export-excel').on('click', function() {
        table.button('.buttons-excel').trigger();
    });
    $('#export-copy').on('click', function() {
        table.button('.buttons-copy').trigger();
    });
    // Función para obtener la fecha y hora actual en formato deseado
    function getCurrentDateTime() {
        var date = new Date();
        var year = date.getFullYear();
        var month = String(date.getMonth() + 1).padStart(2, '0');
        var day = String(date.getDate()).padStart(2, '0');
        var hours = String(date.getHours()).padStart(2, '0');
        var minutes = String(date.getMinutes()).padStart(2, '0');
        var seconds = String(date.getSeconds()).padStart(2, '0');

        return year + month + day + '_' + hours + minutes + seconds;
    }
    //Formulario de agregar
    document.getElementById("areaMetros").addEventListener("input", function() {
        const metros = parseFloat(document.getElementById('areaMetros').value);
        if (!isNaN(metros)) {
            const varas = metros * 1.4233213046;
            document.getElementById('areaVaras').value = varas.toFixed(2);
        } else {
            document.getElementById('areaVaras').value = '';
        }
    });
    $.validator.addMethod(
        "validarPoligono",
        function(value, element) {
            return this.optional(element) || /^[A-Za-z\s,]+$/.test(value);
        },
        "No se aceptan números ni caracteres especiales"
    );
    $.validator.addMethod(
        "validarNumero",
        function(value, element) {
            return this.optional(element) || /^\d+$/.test(value);
        },
        "Solo se aceptan números"
    );
    $.validator.addMethod(
        "validarSeccion",
        function(value, element) {
            return this.optional(element) || /^[A-Za-z\s,]*$/.test(value);
        },
        "No se aceptan números ni caracteres especiales"
    );
    $.validator.addMethod(
        "validarArea",
        function(value, element) {
            return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
        },
        "Ingrese un número válido"
    );
    var formGuardar = $('#formGuardar');
    var validator = $('#formGuardar').validate({
        rules: {
            matricula: {
                required: true,
                maxlength: 18
            },
            poligono: {
                required: true,
                validarPoligono: true,
                maxlength: 1
            },
            numero: {
                required: true,
                validarNumero: true,
                maxlength: 3
            },
            seccion:{
                validarSeccion: true,
                maxlength: 1
            },
            areaMetros: {
                required: true,
                validarArea: true,
                maxlength: 20
            },
            areaVaras:{
                maxlength: 20
            }          
        },
        messages:{
            matricula:{
                required: 'Este campo es requerido'
            },
            poligono:{
                required: 'Este campo es requerido'
            },
            numero:{
                required: 'Este campo es requerido'
            },
            seccion:{
                required: 'Este campo es requerido'
            },
            areaMetros:{
                required: 'Este campo es requerido'
            },
            areaVaras:{
                required: 'Este campo es requerido'
            }        
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "matricula" || element.attr("name") === "poligono" || element.attr("name") === "numero" || element.attr("name") === "seccion" || element.attr("name") === "areaMetros" || element.attr("name") === "areaVaras") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            var idTerreno = $('#idTerreno').val();
            var idProyecto = $('#proyecto').val();
            var formDataArray = formGuardar.serializeArray();
            var url;
            if (idTerreno) {
                url = '/ActualizarTerreno/'+idProyecto;
                formDataArray.push({name: 'idTerreno', value: idTerreno});
            } else {
                url = '/AgregarTerreno/'+idProyecto;
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: formDataArray,
                success: function (response) {
                    $('#crearModal').modal('hide');
                    table.ajax.reload(null, false);
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#crearModal').modal('hide');
                    var errorMessage = xhr.responseText || 'Error al actualizar el terreno.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Método para mostrar el modal segun sea si editar o nuevo registro
    $(document).on('click', '.abrirModal-btn', function () {
        var idTerreno = $(this).data('id');
        var idProyecto = $("#proyecto").val();
        var modal = $('#crearModal');
        var tituloModal = modal.find('.modal-title');
        var form = modal.find('form');
        validator.resetForm();
        formGuardar.find('.is-invalid').removeClass('is-invalid');
        if (idTerreno) {
            tituloModal.text('Editar Terreno');
            $.ajax({
                url: '/ObtenerTerreno/' + idTerreno,
                type: 'GET',
                success: function (response) {
                    $('#matricula').val(response.matricula);
                    $('#poligono').val(response.poligono);
                    $('#numero').val(response.numero);
                    $('#seccion').val(response.seccion);
                    $('#areaMetros').val(response.areaMetros);
                    $('#areaVaras').val(response.areaVaras);
                    $('#proyecto').val(response.proyecto.idProyecto);
                    $('#idTerreno').val(response.idTerreno);
                },
                error: function () {
                    alert('Error al obtener los datos del terreno.');
                }
            });
        } else {
            tituloModal.text('Agregar Terreno');
            form.attr('action', '/AgregarTerreno/'+idProyecto);
            $('#matricula').val('');
            $('#poligono').val('');
            $('#numero').val('');
            $('#seccion').val('');
            $('#areaMetros').val('');
            $('#areaVaras').val('');
            $('#idTerreno').val('');
        }
        modal.modal('show');
    });
    //Método para mostrar el modal de eliminación
    $(document).on('click', '.eliminarModal-btn', function () {
        var idTerreno = $(this).data('id');
        var modal = $('#confirmarEliminarModal');
        var eliminarBtn = modal.find('#eliminarTerrenoBtn');
        eliminarBtn.data('id', idTerreno);
        modal.modal('show');
    });
    //Método para enviar la solicitud de eliminar
    $(document).on('click', '#eliminarTerrenoBtn', function () {
        var idTerreno = $(this).data('id');
        $('#eliminarTerrenoForm').attr('action', '/EliminarTerreno/' + idTerreno);
        $.ajax({
            url: $('#eliminarTerrenoForm').attr('action'),
            type: 'POST',
            data: $('#eliminarTerrenoForm').serialize(),
            success: function (response) {
                $('#confirmarEliminarModal').modal('hide');
                table.ajax.reload();
                toastr.success(response);
            },
            error: function (xhr, status, error) {
                $('#confirmarEliminarModal').modal('hide');
                var errorMessage = xhr.responseText || 'Error al eliminar el terreno.';
                toastr.error(errorMessage);
            }
        });
    });
}); 

