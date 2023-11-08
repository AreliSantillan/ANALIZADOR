package analizador;
//importamos las librerias necesarias 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ANALIZADOR {
//Declaramos nuestras expresiones regulares para validad nuestros lenguaje, variables y constantes
//Variables    
    static int lineas;
    static int sig ; 
    static int varep ;
    public static int errs= 0;
    public static String lines [] = new String [100];
    public static Pattern patron; 
    public static Matcher mcher; 
    public static ArrayList<String> tabla_simbolosL = new ArrayList<String>(); //Definimos nuestro arreglo para a tabla de simbolos, sera donde agreguemos las variables   
    public static ArrayList<String> tabla_simbolosS = new ArrayList<String>();
      public static ArrayList<String> tabla_simbolosSe = new ArrayList<String>();
    public static ArrayList<String> enteros = new ArrayList<String>();
    public static ArrayList<String> flotantes = new ArrayList<String>();
    public static ArrayList<String> cadenas = new ArrayList<String>();
    public static String arr[] = new String [100];
    public static String arr1[] = new String [100];

//Inicio y fin 
    
    public static String inci = "begin";
    public static String fin = "end"; 
    
//conectores     
    public static String variables = "()";
    public static String variablesE = "()";
    public static String variablesF = "()";
    public static String variablesS = "()";
    public static String simb = "\\!|\\'|\\#|\\$|\\%|\\&|\\/|\\(|\\)|\\=|[\\^]|\\'|\\?|\\¿|\\;|\\:|\\::|\\,|\\¡";
    public static String oper = "(\\+|\\-|\\*|\\/|\\%|[\\^])+";
    public static String opers = "(\\<|\\>|\\=|\\==|\\!=|\\>=|\\<=)";
    public static String oped = "(\\|||\\&&)+";
    public static String espacio = "\\s";
    public static String letra = "[a-z]";
    public static String num = "[0-9]";
    public static String guion= "[-_]";
    public static String tipo = "((ent)|(floy)|(tring))";
    public static String entero = "ent";
    public static String flotante = "floy";
    public static String cadena = "tring";
    public static String var = "("+letra+"("+letra+"|"+num+"|"+guion+")*"+num+")";
    public static String ent = "[0-9]+";
    public static String floy = ent+"\\."+ent; 
    public static String tring1 = "[\\w\\s\\W]+";
    public static String tring = "([']" + tring1 + "['])";
    public static String Cad = "("+ent+")|("+floy+")|("+tring+")";
   
    
//sentencias 
    public static String vars = "("+var+")|("+ent+")|("+floy+")";
    public static String varwi = "("+ent+")|("+floy+")";
    public static String asig = "("+var+")|("+Cad+")"; 
    //public static String sal = "("+var+")|("+tring+")";
    public static String math = "("+var+")"+espacio+"\\::"+espacio+"("+vars+")"+espacio+oper+espacio+"("+vars+")"+ espacio +"\\;";//funciona
    public static String decl = tipo+espacio+var+espacio+"\\;";//funciona
    public static String asign = "("+var+")"+espacio+"\\::"+espacio+"("+asig+")"+espacio+"\\;";//funciona
    public static String leer = "(entr)"+ espacio + var + espacio +"\\;"; //funciona
    public static String imprime = "(print)"+ espacio +"\\::"+ espacio + "("+asig+")" + espacio +"\\:"+ espacio +"\\;";//funciona
    
//ciclos
    
    public static String conds = "\\::"+ espacio + "("+vars+")" + espacio + opers + espacio + "("+vars+")" +espacio+ "\\:";//funciona
    public static String condc = "("+conds+")"+espacio+oped+espacio+"("+conds+")";//funciona
    public static String condi = "("+conds+")+|("+condc+")+";
    public static String Si = "(wi)"+espacio+"("+condi+")";//funciona
  
    public static String Para = "(cicla)"+espacio+"\\::"+espacio+"("+var+")"+espacio+"(inicia)"+espacio+"("+vars+")"+espacio+"(termina)"+espacio+"("+vars+")"+espacio+"\\;";//funiona
    public static String Mientras = "(repet)"+ espacio + "("+condi+")";//funciona
    
    
//Fines    
    
    public static String finsi = "wo\\;";
    public static String finpara = "clo\\;";
    public static String finientras = "rop\\;";
    public static String fines = "end"+"|"+finsi+"|"+finpara +"|"+finientras;
    
//Para sintactico
    
    public static String All = decl+"|"+asign+"|"+leer+"|"+imprime+"|"+Si+"|"+Para+"|"+Mientras+"|"+fines+"|"+math;
    public static String lenguaje = "(entr|ent|floy|tring|print|wi|ont|cicla|repet|begin)|"+simb+"|"+oper+"|"+opers+"|"+fines;
 //Metodo para leer nuestro archivo de texto
public String Archivo (String ruta){
    lineas = 0; //Variable para contar nuestras lineas en el txt
    String Parrafo = "";
    
    //Iniciamos nuestro Try catch para tomar el archivo de txt
        try{
           BufferedReader rut = new BufferedReader (new FileReader(ruta));
           String txto="";String lee;
           while((lee=rut.readLine())!=null){//inicio de ciclo while
           //Hacemos salto de linea y guardamos en una variable temporal para evitar errores
           txto = txto+lee+"\n";lineas++;
           
           for(int x = 0; x<lineas; x++){//inicio de ciclo for
           String line = Files.readAllLines(Paths.get(ruta)).get(x);
           lines [x] = line; //aqui guardamos las lineas 
               }//fin de ciclo for
           
           
        }//fin de ciclo while
        
           Parrafo = txto; //igualamos nuestras variable
           
 }catch(Exception e){System.out.println("No se encontro el archivo");}
    return Parrafo;
   
}
//Metodo para separar nuestro txt y comparar nuestro arreglo con el lenguaje
 public void ordenar()
    {//inicamos el metodo
        
        
        
        
    try {//Iniciamos el try catch para nuestro analisis LEXICO
        
        for (int y = 0; y < lineas; y++){//Inicamos ciclo for para nuestras lineas
            
            String arr[] = lines[y].split(" ");//Separamos las lineas y las aÃ±adimos al arreglo
            
            for (int c = 0; c < arr.length; c++){//Iniciamos nuestro para recorrer los tokens y llevar un conteo
                
            System.out.println(arr[c]);// Imprimimos los tokens
            
               patron = Pattern.compile(lenguaje);//tomamos nuestro lenguaje
               mcher = patron.matcher(arr[c]);//comparamos que haga match
            
            if(!mcher.matches()){//if para lenguaje
               
               patron = Pattern.compile(var);//tomamos expresion de variables
               mcher = patron.matcher(arr[c]);//Comparamos si existe
               
            if(mcher.matches()){//if para si hace match
                   
                boolean rept = false;//Bandera para no duplicar variables
                
            for(int x=0; x<tabla_simbolosL.size(); x++){//Recorremos nuestra tabla de simbolos 
                   
            if(arr[c].equals(tabla_simbolosL.get(x))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
                rept=true;
                    }          
               }
               
            if(rept==false){//Si no esta repetida y cumple con la expresion la agrega a nuestra tabla de simbolos
                   tabla_simbolosL.add(arr[c]);
                   
                   System.out.println("Se detecta como ID y se aÃ±ade a la tabla de simbolos");
                 }//
               
               }//finalizamos validacion con variables
               else{
                patron = Pattern.compile(Cad);
                mcher = patron.matcher(arr[c]);
               
            if(!mcher.matches())
                System.out.println("Token "+(c+1)+" No es valido en el codigo en la Linea "+(y+1));  
               
               }  
               
            }//Finalizamos if para analizar nuestros tokens
          }//Finalizamos for de tokens
        }//Finalizamos for de lineas
    
        }catch (Exception e){
            System.out.println();
        }
 //Se termina Try
    
    
    }//finalza metodo
 
 
 //Iniciamos analizador sintactico
  public void sintactico (String txto) 
{
     
 //Declaramos varibles para nuestro analisis sintactico  
    int Linea =0;
    int err = 0; 
    int errf = 0;  
    int varr = 0; int exvar = 0; 
    if (lines[0].matches(inci))//Condicion para validar el inicio del programa
        
    {
          for (int y = 0; y < lineas; y++) {//Ciclo para recorrer nuestro archivo txt
            
            Linea++;//contador de lineas
            lines[0] = lines[0+1];
            if (lines[y].matches(All)) {//Condicion para realizar nuestro analisis sintactico con las expresiones  
                
                
                
                
                
            } else {//si no hace match con alguna expresion se muestra el error
                
                System.err.println("Error sintactico * " + lines[y] + " * En la linea " + Linea);//Si el usuario ingresa una cadena invalida, el programa le dira sobre el error
               
                err++;//contador de errores
                
            }
            
         
          if(lines[y].matches(fin)){errf++;}//Condicion para comprobar que exista un fin 
          
              
        }
          
          //Se verifica que por cada ciclo en el programa haya un fin
        
          if(errf < 1){System.err.println("Error sintactico de fin. Finaliza el programa. ");err++;}//se verifica que exista un fin
          if(errf == 1 && err==0){
           System.out.println("Programa escrito sintacticamente con exito!!\n");
           sig++;
          }
    }  
    else 
        
          System.err.println("\nError de inicio. Linea "+lines[0]);//Se muestra el error si no existe un inicio y no se analiza los demas errores 
   // System.out.println(rep);

}
  public void semantico (String txto){
  ANALIZADOR a = new ANALIZADOR();
                a.tabla();
   
   //Contadores para finalizacion de ciclos
    int s = 0; int fs = 0; 
    int m = 0; int fm = 0; 
    int p = 0; int fp = 0;
    int ids = 0, idm = 0, idp = 0;
 //Proceso para agregar variables a la tabla de simbolos para semantico
     for (int J = 0; J < lineas; J++){//Ciclo para recorrer el archivo txt
         
               patron = Pattern.compile(decl);//tomamos expresion regular de declaracion de variables
               mcher = patron.matcher(lines[J]);//Comparamos si hace match con alguna linea
               
               if(mcher.matches()){//Condicion para saber si cumple o no 
                   
               //if(lines[0].matches("ent")){}//identificamos las declaraciones enteras    
                   
               arr = lines[J].split(" ");//separamos las lineas en tokens
         
               //Filtramos el tipo de dato
               if (arr[0].matches(entero)){//identificamos los enteros 
 
               boolean rept = false;//Bandera para no duplicar variables
                
               
               //Validacion para no agregar variables repetidas
               for(int x=0; x<enteros.size(); x++){//Recorremos nuestra tabla de simbolos´para enteros
                   
               if(arr[1].equals(enteros.get(x))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;

                       }          
                   }
               for(int f=0; f<flotantes.size(); f++){//Recorremos nuestra tabla de simbolos´para enteros
                   
               if(arr[1].equals(flotantes.get(f))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;

                       }          
                   }
               for(int c=0; c<cadenas.size(); c++){//Recorremos nuestra tabla de simbolos´para enteros     
               if(arr[1].equals(cadenas.get(c))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;

                       }          
                   }//fin de validacion
               
               if(rept==false){//Si no esta repetida y cumple con la expresion la agrega a nuestra tabla de simbolos
              
               enteros.add(arr[1]);//se agregan a una tabla de simbolos
            
               if(variablesE.equals("()"))
                    variablesE = variablesE.replace(")", arr[1]+")");
               else
                    variablesE = variablesE.replace(")", "|"+arr[1]+")");//agregamos variables a un string   
                  }
    
               }//fin para los enteros
         
               
               //Validacion para no agregar variables repetidas
               if (arr[0].matches(flotante)){//identificamos los flotantes
             
               boolean rept = false;//Bandera para no duplicar variables
                
               for(int x=0; x<flotantes.size(); x++){//Recorremos nuestra tabla de simbolos para floy
                   
               if(arr[1].equals(flotantes.get(x))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;
                      }          
                 }
               for(int c=0; c<cadenas.size(); c++){//Recorremos nuestra tabla de simbolos´para enteros
                   
               if(arr[1].equals(cadenas.get(c))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;
                       }          
                   }
               for(int x=0; x<enteros.size(); x++){//Recorremos nuestra tabla de simbolos´para enteros      
               if(arr[1].equals(enteros.get(x))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;
                       }          
                   }//fin de validacion
                 
               
               if(rept==false){//Si no esta repetida y cumple con la expresion la agrega a nuestra tabla de simbolos
              
               flotantes.add(arr[1]);//agregamos las variables flotantes
            
               if(variablesF.equals("()"))
                    variablesF = variablesF.replace(")", arr[1]+")");
               else
                    variablesF = variablesF.replace(")", "|"+arr[1]+")");//agregamos variables a un string   
                             }
     
                }//fin para flotantes
         
               if (arr[0].matches(cadena)){
               boolean rept = false;//Bandera para no duplicar variables
                
               //Validacion para no agregar variables repetidas
               for(int x=0; x<cadenas.size(); x++){//Recorremos nuestra tabla de simbolos 
                   
               if(arr[1].equals(cadenas.get(x))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;
                         }          
                }
               for(int x=0; x<enteros.size(); x++){//Recorremos nuestra tabla de simbolos´para enteros
                   
               if(arr[1].equals(enteros.get(x))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;
                       }          
                   }
               for(int f=0; f<flotantes.size(); f++){//Recorremos nuestra tabla de simbolos´para enteros
                   
               if(arr[1].equals(flotantes.get(f))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
               rept=true;
                       }          
                   }//fin de validacion
               
               if(rept==false){//Si no esta repetida y cumple con la expresion la agrega a nuestra tabla de simbolos
              
               cadenas.add(arr[1]);
            
               if(variablesS.equals("()"))
                    variablesS = variablesS.replace(")", arr[1]+")");
               else
                    variablesS = variablesS.replace(")", "|"+arr[1]+")");//agregamos variables a un string   
                       }
               
              }//fin para cadena
         
         //Proceso para identificar variables repetidas
        for (int c = 0; c < arr.length; c++){//recorremos el arreglo con tokens
                
        patron = Pattern.compile(var);//tomamos expresion de variables
        mcher = patron.matcher(arr[c]);//Comparamos si existe
               
             
        if(mcher.matches()){ //if para si hace match con alguna variable
                                   
        boolean rept = false;//Bandera para no duplicar variables
                
            for(int x=0; x<tabla_simbolosSe.size(); x++){//Recorremos nuestra tabla de simbolos 
                   
        if(arr[c].equals(tabla_simbolosSe.get(x))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
        rept=true;
        
        System.err.println("Error semantico existe una variable repetida: "+arr[c]+" Linea: "+lines[J]+" "+(J+1));errs++;//Error para variables repetidas
                
                    }          
               }
               
        if(rept==false){//Si no esta repetida y cumple con la expresion la agrega a nuestra tabla de simbolos
                
                    tabla_simbolosSe.add(arr[c]);//se aÃ±ade a la tabla de simb
              }
        }
          }//Finaliza proceso de variables repetidas
        
            }//finaliza match con declaracion
              
               //Validacion de tipos para operaciones
              if(lines[J].matches(math)){arr1 = lines[J].split(" ");//identificamos expresion
              
              if(((arr1[0].matches(variablesE)||arr1[0].matches(ent))&&(arr1[2].matches(variablesE)||arr1[2].matches(ent))&&(arr1[4].matches(variablesE)||arr1[4].matches(ent)))||
              ((arr1[0].matches(variablesF)||arr1[0].matches(floy))&&(arr1[2].matches(variablesF)||arr1[2].matches(floy))&&(arr1[4].matches(variablesF)||arr1[4].matches(floy))))
              {}else{System.err.println("Error semantico los tipos no coinciden "+(J+1));errs++;}//Condicion para verificar que los tipos coincidan en cada bloque
                
              }
              //Validacion de tipos para ciclo Si
              if(lines[J].matches(Si)){arr1 = lines[J].split(" ");
              
              if(((arr1[2].matches(variablesE)||arr1[2].matches(ent))&&(arr1[4].matches(variablesE)||arr1[4].matches(ent)))||
              ((arr1[2].matches(variablesF)||arr1[2].matches(floy))&&(arr1[4].matches(variablesF)||arr1[4].matches(floy)))
               ){}else{System.err.println("Error semantico los tipos no coinciden "+(J+1));errs++;}
                
              }
               //Validacion de tipos para ciclo mientras
              if(lines[J].matches(Mientras)){arr1 = lines[J].split(" ");
              
              if(((arr1[2].matches(variablesE)||arr1[2].matches(ent))&&(arr1[4].matches(variablesE)||arr1[4].matches(ent)))||
              ((arr1[2].matches(variablesF)||arr1[2].matches(floy))&&(arr1[4].matches(variablesF)||arr1[4].matches(floy)))
               ){}else{System.err.println("Error semantico los tipos no coinciden "+(J+1));errs++;}
                
              }
               //Validacion de tipos para Ciclo Si
              if(lines[J].matches(Para)){arr1 = lines[J].split(" ");
              
              if(((arr1[2].matches(variablesE)||arr1[2].matches(ent))&&(arr1[4].matches(variablesE)||arr1[4].matches(ent))&&(arr1[6].matches(variablesE)||arr1[6].matches(ent)))
               ){}else{System.err.println("Error semantico los tipos no coinciden "+(J+1));errs++;}
                
              }
               //Validacion de tipos para las asignaciones
              if(lines[J].matches(asign)){arr1 = lines[J].split(" ");
              
              if(((arr1[0].matches(variablesE)||arr1[0].matches(ent))&&(arr1[2].matches(variablesE)||arr1[2].matches(ent)))||
              ((arr1[0].matches(variablesF)||arr1[0].matches(floy))&&(arr1[2].matches(variablesF)||arr1[2].matches(floy)))||
              ((arr1[0].matches(variablesS)||arr1[0].matches(tring))&&(arr1[2].matches(variablesS)||arr1[2].matches(tring)))
               ){}else{System.err.println("Error semantico los tipos no coinciden "+(J+1));errs++;}
                
              }
              
              
              
          if(lines[J].matches(Si)){s++; ids = J; }if(lines[J].matches(finsi)){fs++;}//Condiciones para comprobar que exista un Ciclo Wi Wo
          if(lines[J].matches(Mientras)){m++; idm = J; }if(lines[J].matches(finientras)){fm++;}//Condiciones para comprobar que exista un ciclo Repet rop
          if(lines[J].matches(Para)){p++; idp = J; }if(lines[J].matches(finpara)){fp++;}//Condiciones para comprobar que exista un ciclo cicla clo
          
          
              
              
     }//finalziza ciclo for que recorre el archivo
     
          //Se verifica que por cada ciclo en el programa haya un fin
          if(fs == s){}else {System.err.println("Error semantico en ciclo wi en linea "+(ids+1));errs++;}
          if(fm == m){}else {System.err.println("Error semantico en ciclo repet en "+(idm+1));errs++;}
          if(fp == p){}else {System.err.println("Error semantico en ciclo cicla en linea "+(idp+1));errs++;}
  }
 
  public void tabla (){
      for (int J = 0; J < lineas; J++){
          
      //variables que no existen  
      //validacion de variables
      
         //validar variables en math
                if(lines[J].matches(math)){arr1 = lines[J].split(" ");//filtramos la operacion que se haga para posteriormente separar en tokens y agregamos a un array
                if(!arr1[0].matches(variables)&&!arr1[0].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[0]+" Linea "+(J+1));errs++;}//tomamos el array y seleccionamos el token para validad si exite la variable en la tabla de simb
                if(!arr1[2].matches(variables)&&!arr1[2].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[2]+" Linea "+(J+1));errs++;}//repetimos para otra posicion
                if(!arr1[4].matches(variables)&&!arr1[4].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[4]+" Linea "+(J+1));errs++;}//repetimos para otra posicion
                }

                //validar variables en asignacion
                if(lines[J].matches(asign)){arr1 = lines[J].split(" ");
                if(!arr1[0].matches(variables)){System.err.println("Error semantico de la variable inexistente "+arr1[0]+" Linea "+(J+1));errs++;}
                }

                //validar variables en leer
                if(lines[J].matches(leer)){arr1 = lines[J].split(" ");
                if(!arr1[1].matches(variables)){System.err.println("Error semantico de la variable inexistente "+arr1[1]+" Linea "+(J+1));errs++;}
                }
                //validar variables en imprime
                if(lines[J].matches(imprime)){arr1 = lines[J].split(" ");
                if(arr1[2].matches(var)){
                if(!arr1[2].matches(variables)){System.err.println("Error semantico de la variable inexistente "+arr1[2]+" Linea "+(J+1));errs++;}
                 }
                }
                //validar variables en Si
                if(lines[J].matches(Si)){arr1 = lines[J].split(" ");
                if(arr1[2].matches(var)||arr1[4].matches(var)){
                if(!arr1[2].matches(variables)&&!arr1[2].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[2]+" Linea "+(J+1));errs++;}
                if(!arr1[4].matches(variables)&&!arr1[4].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[4]+" Linea "+(J+1));errs++;}
                 }
                }
                
                //validar variables en for
                if(lines[J].matches(Para)){arr1 = lines[J].split(" ");
                if(arr1[2].matches(var)||arr1[4].matches(var)||arr1[6].matches(var)){
                if(!arr1[2].matches(variables)&&!arr1[2].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[2]+" Linea "+(J+1));errs++;}
                if(!arr1[4].matches(variables)&&!arr1[4].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[4]+" Linea "+(J+1));errs++;}
                if(!arr1[6].matches(variables)&&!arr1[6].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[6]+" Linea "+(J+1));errs++;}
                }
                }
                
                //validar variables en Mientras
                if(lines[J].matches(Mientras)){arr1 = lines[J].split(" ");
                if(arr1[2].matches(var)||arr1[4].matches(var)){
                if(!arr1[2].matches(variables)&&!arr1[2].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[2]+" Linea "+(J+1));errs++;}
                if(!arr1[4].matches(variables)&&!arr1[4].matches(varwi)){System.err.println("Error semantico de la variable inexistente "+arr1[4]+" Linea "+(J+1));errs++;}
                 }
                } 
               patron = Pattern.compile(decl);//tomamos expresion regular de declaracion de variables
               mcher = patron.matcher(lines[J]);//Comparamos si hace match con alguna linea
               
               if(mcher.matches()){//Condicion para saber si cumple o no 
                   
                   
                   
         arr = lines[J].split(" ");//separamos las lineas en tokens
         
         
         for (int c = 0; c < arr.length; c++){//recorremos el arreglo con tokens
                
             patron = Pattern.compile(var);//tomamos expresion de variables
             mcher = patron.matcher(arr[c]);//Comparamos si existe
               
                
               if(mcher.matches()){ //if para si hace match con alguna variable
                   
                boolean rept = false;//Bandera para no duplicar variables
                
            for(int x=0; x<tabla_simbolosS.size(); x++){//Recorremos nuestra tabla de simbolos 
                   
            if(arr[c].equals(tabla_simbolosS.get(x))){//Comparamos si en nuestra tabla de simbolos existe una variable duplicada
                rept=true;
                //System.err.println("Error Semantico una variable repetida: "+arr[c]+" Linea: "+lines[J]+" "+J);//Error para variables repetidas
                varep++;
                    }          
               }
               
            if(rept==false){//Si no esta repetida y cumple con la expresion la agrega a nuestra tabla de simbolos
                 if(variables.equals("()"))
                    variables = variables.replace(")", arr[c]+")");
                else
                    variables = variables.replace(")", "|"+arr[c]+")");//agregamos variables a un string 
                
                   tabla_simbolosS.add(arr[c]);
                 
    
     }
        }
          }
            }
               
     }
      
      
  }
    
    public static void main(String[] args) {
       ANALIZADOR a = new ANALIZADOR ();
        /*System.out.println("\n****Se muestra el txt con el Codigo****\n");
        System.out.println(a.Archivo("archivo.txt"));//Invocamos nuestro metodo de lectura

        System.out.println("\nAnalizador lexico\n");*/
        a.ordenar();//incovamos metodo de ordenamiento y analisis lexico
        /*System.out.println("\nSe muestra que se agregan a la tabla de simbolos: "+tabla_simbolosL);*/
       
        //System.out.println("\nAnalizador sintactico\n");
        System.out.println("\n****Se muestra el txt con el Codigo****\n");
        System.out.println(a.Archivo("archivo.txt"));//Invocamos nuestro metodo de lectura
        a.sintactico("archivo.txt");//Incovamos metodo sintacticoSystem.out.println("\n****Se muestra el txt con el Codigo****\n");
        
        /*System.out.println("\nAnalizador Semantico\n");*/
        /*System.out.println("\n****Se muestra el txt con el Codigo****\n");*/
       if(sig>0){
            a.Archivo("archivo.txt");
        a.semantico("archivo.txt");
        if(errs==0){System.out.println("Programa escrito semanticamente con exito!!\n");}
        }
     
       if(errs==0 && sig > 0 ){a.ENSAMBLADOR();System.out.println("¡¡Se a generado codigo en ensamblador!!");}
       
    }
    
    
    //Variables para ensamblador
    ArrayList<String> codigo = new ArrayList();
    ArrayList<String> dato = new ArrayList();
    
    //con el stack almacenaremos el conteo de saltos que hagamos en los ciclos y asi poder cerrarlos
    Stack<String> jump = new Stack();
    Stack<String> jump2 = new Stack();
    Stack<String> ciclo = new Stack();
    Stack<String> jump3 = new Stack();
    Stack<String> jump4 = new Stack();
    Stack<String> poo = new Stack();
    Stack<String> poo2 = new Stack();
    boolean bandera = false;
    
    String opc = "";
    String es, es1;
    String ver = "";
    int c = 0;//verificar si se ingresa el final de las expresiones o programa
    String m = "", tx;//para tomar tokens especificos
     
    public void ENSAMBLADOR () {
        //Declaracion de variables para el ensamblador:
        int ls = 0, lm = 0, lf = 0;//Contadores de lineas
         String forr = "";
         int  num = 0;
         String salt = "";
           
        ANALIZADOR a = new ANALIZADOR();
       
        for (int i = 0; i < lineas ; i++) {
            
          String [] arr = lines[i].split(" ");
       
           //Codigo para obtener valores de variables
                    if (arr[0].equals("ent")) {
                        dato.add("  " + arr[1] + " DB 5,?,5 dup "+("("+24+"h"+")"));//obtiene los valores de variables
                    }
           //Codigo para mostrar los mensajes o impresiones en ensamblador
                    if (arr[0].equals("tring")) {
                        dato.add("  MSJ" + num + " db " + ver + " ,10,13,'$'");//muetra los mensajes que contenga el archivo
                    }
                    
           //Para cargar nuestro metodo de operaciones verficamos que haga match con una operacion o asignacion     
                    if (lines[i].matches(math) || lines[i].matches(asign)) {
                    tx = lines[i] ;
                    
                        operaciones();//manda llamar el codigo ensamblador de las operaciones
                    
                    }
            //for para recorer cada token   
             for (String part : arr) {
                    //En cada token buscamos las palabras en nuestro lenguaje
                    if (part.matches("(entr|ent|floy|tring|print|wi|ont|cicla|repet|begin|end|wo\\;|clo\\;|rop\\;)")) {
                        
                        //Opc tomara el valor de cad token
                        opc = part; 
                        
                        //Switch para detectar las palabras en el lenguaje con opc
                        switch (opc) {
                            case "begin":
                                codigo.add(".MODEL SMALL");//Define el tamaño de memoria a utilizar
                                codigo.add(".CODE\n");//Indica el inicio del programa
                                codigo.add("INICIO:");//Etiqueta de inicio obligatorio
                                codigo.add("    mov Ax, @Data");//Asignamos direccion 
                                codigo.add("    mov Ds, Ax \n");//al segmento de datos (DS)
                                
                                dato.add("    mov Ax, 4C00H");//Este ira por default siempre 
                                dato.add("    INT 21H\n");
                                dato.add(".Data\n");//declaracion de variables
                                dato.add("    Salto DB 10,13, '$'");//salto de linea
                                break;
          
                            case "end":
                                es = "\n.Stack";//Fin del programma
                                es1 = "\nEND INICIO";//fin de etiqueta inicio
                                break;
                                
                            case "print"://Imprimir = print :: 'Valor de A ' : ;
                                ver = "";
                                c = arr.length - 1;
                                if (arr[c].equals(";")) {
                                    
                                    m = arr[2];
                                    
                                    for (int c = 2; c < arr.length - 2; c++) {
                                        
                                        if (arr[c].matches("[0-9]+.[0-9]+||[0-9]+")) {//Tomamos todo lo que no sea string
                                            ver = "'" + arr[c] + "'";
                                        } else {
                                            ver = ver + " " + arr[c];
                                            
                                        }
                                    }//fin de ciclo for para obtener comillas
                                }
                               
                                if (arr[2].matches(var)) {//imprimir variables
                            
                                    m = arr[2];
                                            
                                    // el add y el sub funiconan para la asignacion. pero no para la lectura
                                    codigo.add(";imprimir variables " + arr[2]);//imprime variables 
                                    //codigo.add("    add " + arr[2] + ", 30H" );
                                    codigo.add("    mov Dx, Offset " + arr[2] + " +2");
                                    codigo.add("    mov Ah, 09h");
                                    codigo.add("    Int 21h");
                                    //codigo.add("    sub " + arr[2] + ", 30H" + "\n");
                                    salt();
                         
                                } else {//imprimir msj
                                    num++;
                                    dato.add("  MSJ" + num + " db " + ver + " ,10,13,'$'");
                                    codigo.add(";Impresion de constante MSJ" + num);
                                    codigo.add("    mov Dx, offset MSJ" + num);
                                    codigo.add("    mov ah, 09h");
                                    codigo.add("    int 21h\n");
                                    salt();
                                }
                                ver = "";

                                break;
                                //entr a1 ;
                                case "entr"://leer datos
                               
                                    m = arr[1];
                                    codigo.add(";Leer de variables " + arr[1]);
                                    codigo.add("   mov ah, 0Ah");
                                    codigo.add("   mov Dx, Offset " + arr[1]);
                                    codigo.add("   int 21h" + "\n");
                                    salt();
                               
                                break;
                                 case "wi"://ciclo si = wi :: c1 <= 5 : 
                                codigo.add(";condicional si");
                                codigo.add("    xor Ax, Ax");
                                codigo.add("    mov Si, offSet " + arr[2] + "+2");

                                if (arr[2].matches(ent)) {
                                    codigo.add("    mov Ah, " + arr[2]);
                                    codigo.add("    Add Ah, 30h");
                                } else {
                                    codigo.add("    mov Ah, byte ptr [Si]");
                                }
                                codigo.add("    mov Di, offset " + arr[4] + "+2");
                                if (arr[4].matches(ent)) {
                                    codigo.add("    mov Al, " + arr[4]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Di]");
                                }
                                codigo.add("    CMP Ah,Al");
                                jump.push("wi" + ls);
                                ls++;
                                switch (arr[3]) {
                                    case ">":
                                        codigo.add("JBE " + jump.peek() + ":");
                                        break;
                                        //codigo.add("JBE wi" + ls++; + ":");
                                    case "<":
                                        codigo.add("JAE " + jump.peek() + ":");
                                        break;
                                    case "==":
                                        codigo.add("JNE " + jump.peek() + ":");
                                        break;
                                    case "!=":
                                        codigo.add("JE " + jump.peek() + ":");
                                        break;
                                    case ">=":
                                        codigo.add("JB " + jump.peek() + ":");
                                        break;
                                    case "<=":
                                        codigo.add("JA " + jump.peek() + ":");
                                        break;
                                }
                                break;
                            case "wo;"://fin de ciclo si
                            
                                codigo.add(jump.peek() + ":");
                                //cont++
                                jump.pop();
                            
                                break;
                            case "repet"://repet :: c1 > 1 :
                                lm++;
                                codigo.add(";ciclo condicional mientras");
                                codigo.add("    Xor Ax,Ax");
                                codigo.add("repet" + lm + ":");
                                codigo.add("    mov Si, offSet " + arr[2] + "+2");
                                if (arr[2].matches(ent)) {
                                    codigo.add("    mov Ah, " + arr[2]);
                                    codigo.add("    Add Ah, 30h");
                                } else {
                                    codigo.add("    mov Ah, byte ptr [Si]");
                                }

                                codigo.add("    mov Di, offset " + arr[4] + "+2");

                                if (arr[4].matches(ent)) {
                                    codigo.add("    mov Al, " + arr[4]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Di]");
                                }
                                codigo.add("    cmp Al,Ah");
                                jump2.push("    rep" + lm);
                                jump4.push("repet" + lm );
                                switch (arr[3]) {
                                    case ">":
                                        codigo.add("JAE " + jump2.peek() + ":");
                                        break;
                                    case "<":
                                        codigo.add("JBE " + jump2.peek() + ":");
                                        break;
                                    case "==":
                                        codigo.add("JNE " + jump2.peek() + ":");
                                        break;
                                    case "!=":
                                        codigo.add("JE " + jump2.peek() + ":");
                                        break;
                                    case ">=":
                                        codigo.add("JA " + jump2.peek() + ":");
                                        break;
                                    case "<=":
                                        codigo.add("JB " + jump2.peek() + ":");
                                        break;
                                }
                                salt();
                           
                                break;
                            case "rop;"://cierre de ciclo while
                                
                                codigo.add("JMP " + jump4.peek() + ":");
                                jump4.pop();
                                codigo.add(jump2.peek() + ":");
                                jump2.pop();
                                
                                
                                break;
                              
                            case "cicla"://cicla :: a1 inicia 1 termina a1 ;
                                           
                                lf++;
                                String salto = "ciclo" + lf;
                                jump3.push(salto);
                                forr = arr[2];
                                ciclo.push(forr);
                                codigo.add(";ciclo for");
                                codigo.add("    mov Si, offset " + forr + "+2");
                                if (arr[4].matches(var)) {
                                    codigo.add("    mov Di, offset " + arr[4] + "+2");
                                    codigo.add("    mov Al, byte ptr [Di]");
                                    codigo.add("    mov byte ptr [Si], Al");
                                    codigo.add("    xor Bx, Bx");
                                    codigo.add("    mov Bl, Al");
                                } else {
                                    codigo.add("    mov byte ptr[Si], " + arr[4] + "+30h");
                                }
                                if (arr[6].matches(ent)) {
                                    codigo.add("    mov Cx, " + arr[6] + "");
                                    if (arr[4].matches(var)) {
                                        codigo.add("    mov Bh, 0");
                                        codigo.add("    mov Bl, byte ptr [Si]");
                                        codigo.add("    sub Bx, 30h");
                                        codigo.add("    sub Cx, Bx");
                                        codigo.add("    add Cx, 1");
                                    } else {
                                        codigo.add("    sub Cx, " + arr[4] + "-1");
                                    }
                                } else {
                                    codigo.add("    xor Cx, Cx");
                                    codigo.add("    mov Si, offset " + arr[6] + "+2");
                                    codigo.add("    mov Cl, byte ptr[Si]");
                                    if (arr[4].matches(ent)) {
                                        codigo.add("    sub Cl, 30h");
                                        codigo.add("    sub Cl, " + arr[4] + "-1");
                                    } else {
                                        codigo.add("    sub Cl, Bl");
                                        codigo.add("    add Cl, 1");
                                    }
                                }
                                codigo.add(salto + ":");
                                codigo.add("    push Cx\n");
                                salt();
                                break;
                            case "clo;"://cierre ciclo for
                                codigo.add("    mov Si, offset " + ciclo.pop() + "+2");
                                codigo.add("    Add byte ptr[Si],1");
                                codigo.add("    POP CX");
                                codigo.add("Loop " + jump3.pop());
                                //bandera = true;
                                break;
                            default:
                                break;
                           
        }
                        opc = "";
    }
      
}
        
        }
        try {
                //En este try se genera un documento para abrirlo en el ensablador con el codigo generado
                PrintWriter code = new PrintWriter("src\\analizador\\ensamblador.asm");
                for (int x = 0; x < codigo.size(); x++) {
                    code.println(codigo.get(x));
                }
                for (int y = 0; y < dato.size(); y++) {
                        code.println(dato.get(y));
                }
                code.println(es);
                code.println(es1);
                code.close();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        
    }
    
 public void salt() {//Metodo para saltar lineas entre msj
        codigo.add(";salto de linea");
        codigo.add("    mov Dx, offset Salto");
        codigo.add("    mov ah,09h");
        codigo.add("    int 21h\n");
    } 

        int po = 0;
  public void operaciones() {
      
        String[] arr = tx.split(" ");//sepatamos los tokens  
       
        if (arr[3].equals("+")) {
            codigo.add(";Suma");//operacion suma
            codigo.add("    xor Ax, Ax");
            codigo.add("    mov Si, offset " + arr[2] + "+2");
            if (arr[2].matches(ent)) {
                                    codigo.add("    mov Al, " + arr[2]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Si]");
                                }
            codigo.add("    xor Bx, Bx");
            codigo.add("    mov Si, offset " + arr[4] + "+2");
            if (arr[4].matches(ent)) {
                                    codigo.add("    mov Bl, " + arr[4]);
                                    codigo.add("    Add Bl, 30h");
                                } else {
                                    codigo.add("    mov Bl, byte ptr [Si]");
                                }
            codigo.add("    add Ax, Bx");
            codigo.add("    sub Al, 30h");
            codigo.add("    mov Si, offset " + arr[0] + "+2");
            codigo.add("    mov byte ptr [Si], Al");
            codigo.add("    mov byte ptr [Si+1], '$'\n");
        }
        if (arr[3].equals("-")) {
            codigo.add(";Resta");//operacion resta
            codigo.add("    xor Ax, Ax");
            codigo.add("    mov Si, offset " + arr[2] + "+2");
             if (arr[2].matches(ent)) {
                                    codigo.add("    mov Al, " + arr[2]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Si]");
                                }
            codigo.add("    xor Bx, Bx");
            codigo.add("    mov Si, offset " + arr[4] + "+2");
            if (arr[4].matches(ent)) {
                                    codigo.add("    mov Bl, " + arr[4]);
                                    codigo.add("    Add Bl, 30h");
                                } else {
                                    codigo.add("    mov Bl, byte ptr [Si]");
                                }
            codigo.add("    sub Ax, Bx");
            codigo.add("    add Al, 30h");
            codigo.add("    mov Si, offset " + arr[0] + "+2");
            codigo.add("    mov byte ptr [Si], Al");
            codigo.add("    mov byte ptr [Si+1], '$'\n");
        }
        if (arr[3].equals("*")) {
            codigo.add(";Multiplicacion");//operacion multiplicacion
            codigo.add("    mov Si, offset " + arr[2] + "+2");
            codigo.add("    mov Di, offset " + arr[4] + "+2");
            codigo.add("    mov Ah, 0");
             if (arr[2].matches(ent)) {
                                    codigo.add("    mov Al, " + arr[2]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Si]");
                                }
            codigo.add("    sub Ax, 30H");
            codigo.add("    mov Bh, 0");
            if (arr[4].matches(ent)) {
                                    codigo.add("    mov Bl, " + arr[4]);
                                    codigo.add("    Add Bl, 30h");
                                } else {
                                    codigo.add("    mov Bl, byte ptr [Di]");
                                }
            codigo.add("    sub Bx, 30H");
            codigo.add("    mul BL");
            codigo.add("    add ax, 30h");
            codigo.add("    mov " + arr[0] + ", Al");
            codigo.add("    mov Si, offset " + arr[0] + "+2");
            codigo.add("    mov byte ptr [Si], Al\n");
        }
        if (arr[3].equals("/")) {
            codigo.add(";Division");//operacion division
            codigo.add("    mov Si, offset " + arr[2] + "+2");
            codigo.add("    mov Di, offset " + arr[4] + "+2");
            codigo.add("    mov Ah, 0");
            if (arr[2].matches(ent)) {
                                    codigo.add("    mov Al, " + arr[2]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Si]");
                                }  codigo.add("    mov Al, " + arr[2]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Si]");
            codigo.add("    sub Ax, 30H");
            codigo.add("    mov Bh, 0");
            if (arr[4].matches(ent)) {
                                    codigo.add("    mov Bl, " + arr[4]);
                                    codigo.add("    Add Bl, 30h");
                                } else {
                                    codigo.add("    mov Bl, byte ptr [Di]");
                                }
            codigo.add("    sub Bx, 30H");
            codigo.add("    div BL");
            codigo.add("    add Al, 30h");
            codigo.add("    mov " + arr[0] + ", Al");
            codigo.add("    mov Si, offset " + arr[0] + "+2");
            codigo.add("    mov byte ptr [Si], AL\n");
        }
        if (arr[3].equals("%")) {
            codigo.add(";Modulo");//operacion modulo
            codigo.add("    xor Ax, Ax");
            codigo.add("    mov Si, offset " + arr[2] + "+2");
            if (arr[2].matches(ent)) {
                                    codigo.add("    mov Al, " + arr[2]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Si]");
                                }
            codigo.add("    xor Bx, Bx");
            codigo.add("    mov Bl, " + arr[4]);
            codigo.add("    div Bl");
            codigo.add("    add ah, 30h");
            codigo.add("    mov Si, offset " + arr[0] + "+2");
            codigo.add("    mov byte ptr [Si], Ah\n");
        }
        if (arr[3].equals("^")) {
            
           
            codigo.add(";Potencia");//potencia
            codigo.add("    mov Si, offset " + arr[2] + "+2");
            codigo.add("    mov Di, offset " + arr[4] + "+2");
            codigo.add("    mov Ah, 0");
            if (arr[2].matches(ent)) {
                                    codigo.add("    mov Al, " + arr[2]);
                                    codigo.add("    Add Al, 30h");
                                } else {
                                    codigo.add("    mov Al, byte ptr [Si]");
                                }
            codigo.add("    sub Ax, 30h");
            codigo.add("    mov Bh, 0");
            if (arr[4].matches(ent)) {
                                    codigo.add("    mov Bl, " + arr[4]);
                                    codigo.add("    Add Bl, 30h");
                                } else {
                                    codigo.add("    mov Bl, byte ptr [Di]");
                                }
            
            poo.push("elevar_0"+po);
            poo2.push("potencia"+po);
            po++;
            codigo.add("    sub Bx, 30h");
            codigo.add("    cmp bl, 0");//Condicion para cuando bl sea 0
            codigo.add("    je "+poo.peek());//si se cumple que es igual salta a elevar_0
             
            codigo.add("    mov Cx, Bx");//de lo contrario continua el proceso
            codigo.add("    xor Bx, Bx");
            codigo.add("    mov Bx, Ax");
            codigo.add("    xor Ax, Ax");
            codigo.add("    mov Al, 1");
   
            codigo.add(poo2.peek()+":");
            codigo.add("    mul bl\n");
            codigo.add("    loop "+poo2.peek());
            codigo.add("    add ax, 30h");
            codigo.add("    mov Si, offset " + arr[0] + "+2");
            codigo.add("    mov byte ptr [Si], Al\n");
            codigo.add("    jmp fin"+po);//realizamos un salto para que no le asigne en automatico 1 a bl
            
            codigo.add(poo.peek()+":");//elevar_0 se ejecuta 
            codigo.add("    mov dx, offset "+arr[0]+"+2");
            codigo.add("    mov si, dx");
            codigo.add("    mov [si], 1");//le asignamos un uno en caso de que sea 0
            codigo.add("    add [si], 30h");
            codigo.add("    jmp fin_elevar"+po);//hacemos un salto al final
            
            
            codigo.add("    fin_elevar"+po+":");
            codigo.add("    fin"+po+":");
        }
         
         if (arr[3].equals(";")) {//asignacion 
         
           // codigo.add("    mov "+ arr[0] + ", "+ arr[2]);
            codigo.add(";Asignacion");//potencia
            codigo.add("    mov  DX, offset "+ arr[0]+" +2");
            codigo.add("    mov SI, DX");
            codigo.add("    mov [SI], "+ arr[2] );
            codigo.add("    add [SI], 30h ");
            
            
        }
        
    }
  
}
