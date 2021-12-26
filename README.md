# SelectAndOpenImage
Basic Android App for select an image and show it in a ImageView control


DESCRIPCION
Esta aplicacion enseña la forma más básica de seleccionar una foto
de la memoria del telefono, cargarla y mostrarla en un ImageView


//Para seleccionarla, se utiliza un INTENT.
// Su funcionamiento es algo complejo por la optimizacion de Memoria de Android


// El proceso gira en torno al hecho de que la app podría ser destruida
//  por android para ceder memoria al intent de seleccio de imagen
// que la propia app solicita,


// En el metodo onCreate() se registra un objeto MyLifecycleObserver
// que tupervisa el proceso
// En OnResume() se comprueba se la app se ha iniciado desde cero o desde
// el intent que la propia App ha invocado


// Para abrir el archivo, es necesario preparar el android manifest:



/<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" //>


// Para poder usar funciones obsoletas a partir de la version 29 de androir relacionadas con la conversion de
// rutas de archivo se ha de agregar esta linea al AndroidManifest
//
//      android:requestLegacyExternalStorage="true"
//
// Ademas ... si la version de Android es superior a 6.. se tiene que pedir permiso e
// en tiempo de ejecucion
//
//                ActivityCompat.requestPermissions
//                        (this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
//
//Puesto que dicho permiso se puede conceder y retirar durante to.do el
//tiempo de vida de la app, se debe comprobar cada vez que se intenta leer el archivo.
//
//            int permissionCheck = ContextCompat.checkSelfPermission(
//                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {}
//              }
//
//Hasta aquí el tema de los permisos... para lanzar un Intent de seleccion de imagen
//es necesario que la aplicacion pueda prever el caso en que el propio Intent de seleccion
//consuma tanta memoria que Android se vea obligado a destruir tu aplicacion para
//optimizar recursos y, una vez conseguida la ruta de acceso a la imagen,
//android vuelva a ejecutar la aplicacion desde el principio
//
//La forma de controlar todo este proceso es a traves de un objeto
// que deriva de DefaultLifecycleObserver.
//
// Este objeto se ha de crear en onCreate(). para que esté activo durante toda
// la vida de la Activity
// Despues se puede controlar la funcion onResume() para saber si la
// app ha arrancado desde una llamada al Intent selector de imagenes. Por ejemplo con
// una variable global.
//
//@Override
//public void onResume(){
//        super.onResume();
//        ...
//        Uri uri=SharingData.uri;
//        if(SharingData.uri!=null)
//        {
//        SharingData.uri=null;
//        ...
//
// esta variable global es cargada en la llamada callback que es invocada por el Intent
// selector de Imagenes:
//
//
//public void onActivityResult(Uri result) {
//        SharingData.uri=result;
//        }
//
//esta callback es declarada desde el registro del objeto DefaultLifecycleObserver
//
//public void onCreate(@NonNull LifecycleOwner owner){
//        mGetContent =mRegistry.register("key", owner, new ActivityResultContracts.GetContent(),
//        new ActivityResultCallback<Uri>() {
//              @Override
//              public void onActivityResult(Uri result) {
//                  SharingData.uri=result;
//              }
//        });
//     }
//
// Resumiendo...
//Primero pregunta/solicita permiso de lectura
// Despues llama al intent que selecciona la imagen
// el intent manda la app a segundo plano( o directamente la destruye)
// cuando la imagen ha sido seleccionada, la app vuelve a la vida y onResume() es invocado
// es aquí, en on Resume() donde se garga el imageView con la uri recivida por el intent,
//
