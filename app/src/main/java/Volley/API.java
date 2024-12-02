package Volley;

public interface API {
        public String URL ="https://slateblue-squid-754504.hostingersite.com/Administrador/";
        public String URL2 ="https://slateblue-squid-754504.hostingersite.com/Docente/";

    //Comienza el CRUD para ASIG
   public String READASIG=URL + "ApiA.php?api=readAsig";
   public String CREATEASIG=URL + "ApiA.php?api=createAsig";
   public String UPDATEASIG=URL + "ApiA.php?api=updateAsig";
   public String DELETEASIG=URL + "ApiA.php?api=deleteAsig";

        //Comienza el CRUD para CARRERA
    public String CREATECARRERA=URL + "ApiC.php?api=createCarrera";
    public String READCARRERA=URL + "ApiC.php?api=readCarrera";
    public String UPDATECARRERA=URL + "ApiC.php?api=updateCarrera";
    public String DELETECARRERA=URL + "ApiC.php?api=deleteCarrera";


    //Comienza el CRUD para PERIODO
    public String CREATEPER=URL + "ApiP.php?api=createPeriodos";
    public String READPER=URL + "ApiP.php?api=readPeriodos";
    public String UPDATEPER=URL + "ApiP.php?api=updatePeriodos";
    public String DELETEPER=URL + "ApiP.php?api=deletePeriodos";

    //Operaciones con docentes
    public String READDOC=URL + "ApiD.php?api=readDoc";
    public String DELETEDOC=URL + "ApiD.php?api=deleteDoc";


    //Operaciones con estudiantes

    public String READESTU=URL + "ApiE.php?api=readEstu";
    public String DELETEESTU=URL + "ApiE.php?api=deleteEstu";

    //LOGIN
    public String BUSCARDOC = URL2 + "ApiD.php?api=listarDoc";


    public String BUSCARCORREO = URL2 + "ApiD.php?api=listarCorreo";
    public  String RECUPERA_CONTRA = URL2 + "ApiD.php?api=recuperaContrasenia";

}
