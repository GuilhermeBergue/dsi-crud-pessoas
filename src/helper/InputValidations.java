package helper;

public class InputValidations {

   public static String isCpfValido( String input ) {
      String retorno = TrataString.trim( input );
      if (retorno.length() == 11) {
         return retorno;
      }
      return "";
   }

   public static String isCnpjValido( String input ) {
      String retorno = TrataString.trim( input );
      if (retorno.length() == 14) {
         return retorno;
      }
      return "";
   }

}
