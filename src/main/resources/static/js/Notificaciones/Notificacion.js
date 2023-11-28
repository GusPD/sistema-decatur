$(document).ready(function() {
    //Plugin editor de texto
    tinymce.init({
        selector: '#mensaje',
        content_css: '/recursosOnline/css/styleTinymce.css',
        plugins: 'advlist autolink lists link image charmap preview anchor searchreplace visualblocks fullscreen insertdatetime media table wordcount',
        toolbar: 'undo redo save | fontfamily fontsize | bold italic underline strikethrough | removeformat | forecolor backcolor | align indent outdent lineheight | bullist numlist| subscript superscript',
        menubar: true,
        statusbar: false,
        language: 'es',
        font_formats: 'Arial=arial,helvetica,sans-serif',
        spellchecker_languages: 'Spanish=es',
        paste_as_text: true,
    });
    FilePond.registerPlugin(
        FilePondPluginFileValidateSize
    );
    //Plugin de carga de archivos
    var totalFileSize = 0;
    var filePondField = FilePond.create(document.getElementById('adjuntos'), {
        allowMultiple: true,
        maxTotalFileSize: '20MB',
        onaddfile: function(error, file) {
            if (!error) {
            totalFileSize += file.fileSize;
                if (totalFileSize > 20 * 1024 * 1024) {
                    document.getElementById('adjuntos').classList.add('filepond--item-error');
                }
            }
        },
        onremovefile: function(error, file) {
            totalFileSize -= file.fileSize;
            if (totalFileSize <= 20 * 1024 * 1024) {
            document.getElementById('adjuntos').classList.remove('filepond--item-error');
            }
        },
    });
    $.validator.addMethod(
        "validarCorreos",
        function(value, element) {
            if (value.trim() === "") {
                return true;
            }
            var correos = value.split(',');
            for (var i = 0; i < correos.length; i++) {
                if (!/^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$/.test(correos[i].trim())) {
                return false;
                }
            }
            return true;
        },
        "Ingrese uno o más correos electrónicos válidos separados por comas"
    );
    var formEnviar = $('#formEnviar');
    var validator = $('#formEnviar').validate({
        rules: {
            remitente: {
                required: true
            },
            destinatario: {
                validarCorreos: true
            },
            cc: {
                validarCorreos: true
            },
            cco: {
                validarCorreos: true
            },
            asunto: {
                required: true
            },
            mensaje: {
                required: true
            }
        },
        messages:{
            remitente:{
                required: 'Este campo es requerido'
            },
            propietarioIndividual:{
                required: 'Este campo es requerido'
            },
            destinatario:{
                required: 'Este campo es requerido'
            },
            cc:{
                required: 'Este campo es requerido'
            },
            cco:{
                required: 'Este campo es requerido'
            },
            asunto:{
                required: 'Este campo es requerido'
            },
            mensaje:{
                required: 'Este campo es requerido'
            }       
        },
        highlight: function(element) {
            $(element).addClass('is-invalid');
            var select2ChoiceElement = document.querySelector('#propietarioIndividual');
            if (select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-propietarios-error").removeClass('d-none');
                $('#venta').addClass('is-invalid');
                $("#propietarioIndividual-error").addClass('d-none');
            }
        },
        unhighlight: function(element) {
            $(element).removeClass('is-invalid');
            var select2ChoiceElement = document.querySelector('#propietarioIndividual');
            if (!select2ChoiceElement.classList.contains('is-invalid')) {
                $("#span-propietarios-error").addClass('d-none');
                $('#propietarioIndividual').removeClass('is-invalid');
            }
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") === "remitente" || element.attr("name") === "destinatario" || element.attr("name") === "cc" || element.attr("name") === "cco" || element.attr("name") === "asunto" || element.attr("name") === "mensaje") {
                error.insertAfter(element);
            }        
        },
        errorElement: 'div',
        errorClass: 'invalid-feedback',
        submitHandler: function(form) {
            event.preventDefault();
            validator.resetForm();
            var idProyecto = $("#idProyecto").val();
            var mensajeEditor = tinymce.get('mensaje');
            var mensaje = mensajeEditor ? mensajeEditor.getContent() : '';
            var formDataArray = new FormData(formEnviar[0]);
            formDataArray.delete('mensaje');
            formDataArray.delete('adjuntos');
            formDataArray.append('mensaje', mensaje);
            var filePondFiles = filePondField.getFiles();
            filePondFiles.forEach(function (file) {
                formDataArray.append('adjuntos', file.file);
            });
            formDataArray.append('idProyecto', idProyecto);
            $('#loadingOverlay').show();
            $.ajax({
                url: "/EnviarCorreo",
                type: 'POST',
                data: formDataArray,
                contentType: false,
                processData: false,
                success: function (response) {
                    $('#loadingOverlay').hide();
                    toastr.success(response);
                },
                error: function (xhr, status, error) {
                    $('#loadingOverlay').hide();
                    var errorMessage = xhr.responseText || 'Error al enviar el correo.';
                    toastr.error(errorMessage);
                }
            });
        }
    });
    //Función que habilita el destinatario individual
    $('#destino').change(function () {
        var selectedOption = $(this).val();
        if (selectedOption === 'Individual') {
            $('#form-group-destinatario').show();
            $('#form-group-seleccionar').hide();
            $("#destinatario").val('');
            $("#propietarioIndividual").val(null).trigger('change');
            document.getElementById("destinatario").setAttribute("required", "required");
            document.getElementById("propietarioIndividual").removeAttribute("required");
        } else if(selectedOption === 'Propietarios'){
            $('#form-group-destinatario').hide();
            $('#form-group-seleccionar').hide();
            $("#destinatario").val('');
            $("#propietarioIndividual").val(null).trigger('change');
            document.getElementById("destinatario").removeAttribute("required");
            document.getElementById("propietarioIndividual").removeAttribute("required");
        } else if(selectedOption === 'SeleccionarPropietarios'){
            $('#form-group-destinatario').hide();
            $('#form-group-seleccionar').show();
            $("#destinatario").val('');
            $("#propietarioIndividual").val(null).trigger('change');
            document.getElementById("destinatario").removeAttribute("required");
            document.getElementById("propietarioIndividual").setAttribute("required", "required");
        } else if(selectedOption === 'PropietariosIndividual'){
            $('#form-group-destinatario').show();
            $('#form-group-seleccionar').hide();
            $("#destinatario").val('');
            $("#propietarioIndividual").val(null).trigger('change');
            document.getElementById("destinatario").setAttribute("required", "required");
            document.getElementById("propietarioIndividual").removeAttribute("required");
        } else if(selectedOption === 'SeleccionarPropietariosIndividual'){
            $('#form-group-destinatario').show();
            $('#form-group-seleccionar').show();
            $("#destinatario").val('');
            $("#propietarioIndividual").val(null).trigger('change');
            document.getElementById("destinatario").setAttribute("required", "required");
            document.getElementById("propietarioIndividual").setAttribute("required", "required");
        } else {
            $('#form-group-destinatario').hide();
            $('#form-group-seleccionar').hide();
            $("#destinatario").val('');
            $("#propietarioIndividual").val(null).trigger('change');
            document.getElementById("destinatario").removeAttribute("required");
            document.getElementById("propietarioIndividual").removeAttribute("required");
        }
    });
    //Función para definir el uso de la libreria selec2
    var $select = $( '#propietarioIndividual' ).select2( {
        theme: "bootstrap-5",
        width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
        placeholder: $( this ).data( 'placeholder' ),
        closeOnSelect: false
    } );
    $select.on('change', function() {
        $(this).trigger('blur');
    });
});