      *<<<<<<<<<<<<<<<<TASK EWT-Cobol Rutina de prestamos>>>>>>>>>>>>>>>
      *(****************************************************************        
      *                                                                *        
      *   ACCESO  A TABLA  DB2:                                        *        
      *                                                                *        
      *          - UGDTSMD                                             *        
      *)****************************************************************        
      *                      DESCRIPCION:       algo  se describe        
      *AQUI   HAY MUCHAS COSAS PARA LLENAR LA LINEA.        
      * OBTENCION DE SALARIO MINIMO  OTRO ADICIONAL CON      
      *EstoEsUnaPalabraMurGrandeQueDebeSerCortada>>>>>>>>>>>AQUI<<<<<<<*                                                               *        
      ******************************************************************        
      *----------------------------------------------------------------*        
      * DD/MMM/AA ]       AUTOR                                                
      * sigue aqui con mas cosas para ser varias lineas        
      * 10/AGOSTO/2000. DEMETRIO DOMINGUEZ.                           
      *(                                                                         
      *********************           **********************************        
      *  REQUERIMIENTO        FECHA          USUARIO     MARCA         *        
      * --------------- -------------------  -------  --------------   *        
      * REINGENIERIA DE     2009-03-06       IDAXJEM  ACME-RI-INI.     *        
      * PRESTAMOS                                     ACME-RI-FIN.     *        
      *                                                                *        
      *)SE AGREGA COPY PARA EL MANEJO DE ERRORES.                      *        
      * --------------- -------------------  -------  --------------   *        
      *(REINGENIERIA DE     2009-04-04       IDAXFAM  ACME-RI-INI.     *        
      * PRESTAMOS                                     ACME-RI-FIN.     *        
      *)                                                               *        
      * ESTA RUTINA ES UN CLON DE LA RUTINA UR9CSMD0, SE AGREGA COPY   *        
      * PARA EL MANEJO CORRECTO DE ERRORES.                            *        
      *                                                                *        
      *        
      *             IDENTIFICATION DIVISION
                                                                                
       IDENTIFICATION DIVISION.                                                 
       PROGRAM-ID. UR9CSMDE.                                                    
      *AUTHOR. DEMETRIO DOMINGUEZ.                                              
      *DATE-WRITTEN. 10-08-00.                                                  
                                                                                
      *============ ENVIRONMENT DIVISION ===============================        
                                                                                
       ENVIRONMENT DIVISION.                                                    
       CONFIGURATION SECTION.                                                   
       SOURCE-COMPUTER. IBM-370 WITH DEBUGGING MODE.                            
       OBJECT-COMPUTER. IBM-3090.                                               
      *SPECIAL-NAMES.                                                           
      *    DECIMAL-POINT IS COMMA.                                              
                                                                                
      *============ DATA DIVISION =====================================         
                                                                                
       DATA DIVISION.                                                           
       WORKING-STORAGE SECTION.                                                 
      *------------------------                                                 
      *                                                                         
           EXEC SQL                                                             
              INCLUDE UGTCSMD                                                   
           END-EXEC.                                                            
      *                                                                         
      ***  AREA SQLCA                                                           
      *                                                                         
           EXEC SQL INCLUDE SQLCA     END-EXEC.                                 
      *                                                                         
      ***  TABLA DB2 : UGDTSMD                                                  
      *                                                                         
           EXEC SQL INCLUDE UGGTSMD   END-EXEC.                                 
      *                                                                         
      *                                                                         
       LINKAGE SECTION.                                                         
      *----------------                                                         
           EXEC SQL                                                             
              INCLUDE URWCOSM                                                   
           END-EXEC.                                                            
      *                                                                         
      *ACME-RI-INI.                                                           
           COPY UGECMNE.                                                        
      *ACME-RI-FIN.                                                           
      *============ PROCEDURE DIVISION ================================         
                                                                                
       PROCEDURE DIVISION USING URWCOSM                                         
      *ACME-RI-INI.                                                           
                                UGECMNE.                                        
      *ACME-RI-FIN.                                                           
      *                                                                         
           PERFORM INICIO.                                                      
           PERFORM PROCESO.                                                     
           PERFORM FINAL-PROCES.                                                
      ******************************************************************        
      *                    INICIO                                      *        
      *                                                                *        
      *  SE INICIALIZAN LAS VARIABLES DE TRABAJO.                     *         
      ******************************************************************        
       INICIO.                                                                  
      *-------------                                                            
           INITIALIZE UGTCSMD.                                                  
                                                                                
                                                                                
      ******************************************************************        
      *                                                                *        
      *                    FIN                                         *        
      *                                                                *        
      *  RETORNA EL CONTROL AL PROGRAMA QUE LLAMA A LA RUTINA.         *        
      *                                                                *        
      ******************************************************************        
       FINAL-PROCES.                                                            
      *-------------                                                            
           GOBACK.                                                              
                                                                                
      *-----------                                                              
       PROCESO.                                                                 
      *ACME-RI-INI                                                            
      *    DISPLAY 'LA ZONA DE ENTRADA ES:  ', OSM-ZONA                         
      *    DISPLAY 'LA FECHA DE ENTRADA ES: ', OSM-FECHA                        
      *ACME-RI-FIN                                                            
           MOVE OSM-ZONA         TO SMD-ZONA                                    
           MOVE OSM-FECHA        TO SMD-FECOVALI                                
           EXEC SQL                                                             
              SELECT                                                            
                  IMPSALAR                                                      
              INTO                                                              
                 :SMD-IMPSALAR                                                  
              FROM UGDTSMD                                                      
              WHERE  ZONA     = :SMD-ZONA     AND                               
                     FECOVALI =(SELECT MAX( FECOVALI)                           
                                    FROM UGDTSMD                                
                                    WHERE  FECOVALI <=    :SMD-FECOVALI)        
           END-EXEC                                                             
      *ACME-RI-INI                                                            
      *       DISPLAY 'SQLCODE:  ' SQLCODE                                      
      *ACME-RI-FIN                                                            
                IF SQLCODE = 100                                                
                   MOVE '10' TO OSM-COD-RET                                     
                   MOVE SQLCODE TO OSM-SQLCODE                                  
                 ELSE                                                           
                   IF SQLCODE = 0                                               
                      MOVE SMD-IMPSALAR TO OSM-IMPORTE                          
                      MOVE '00'         TO OSM-COD-RET                          
                      MOVE SQLCODE TO OSM-SQLCODE                               
                    ELSE                                                        
                      MOVE '99'         TO OSM-COD-RET                          
                      MOVE SQLCODE      TO OSM-SQLCODE                          
      *ACME-RI-INI.                                                           
                      MOVE '99'         TO MNE-INDERROR                         
                      MOVE 'SELECT'     TO MNE-MISSATGE                         
                      MOVE 'UGDTSMD'    TO MNE-TAULAERR                         
                      MOVE 'UR9CSMDE'   TO MNE-MODULRES                         
                      MOVE SQLCODE      TO MNE-SQLCODE                          
      *ACME-RI-FIN.                                                           
                   END-IF                                                       
                END-IF.   
123456***********************************&&&&&&&

                                                      
