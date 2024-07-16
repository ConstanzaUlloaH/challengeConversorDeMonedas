import currencyEnums.CurrencyCode;
import currencyEnums.NombrePaisDivisa;
import services.Cambio;
import services.ConsultaAPI;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        System.out.println("Bienvenido al Conversor de Divisas!\n");

        while(true) {
            System.out.println("Opciones de Divisas Disponibles:\n");

            //mostrar los tipos de divisa disponibles para cambiar
            for (CurrencyCode currencyCode : CurrencyCode.values()) {
                NombrePaisDivisa nombrePaisDivisa = NombrePaisDivisa.values()[currencyCode.ordinal()];
                System.out.println(currencyCode + " = " + nombrePaisDivisa);
            }
            System.out.println("\nPor favor, siga los siguientes pasos para realizar su conversión:\n" +
                    "\n" +
                    "1. Ingrese la divisa que desea convertir (Para salir ingrese 0):\n" +
                    "   (Por ejemplo, ARS)");


            //consulta API
            ConsultaAPI consulta = new ConsultaAPI();

            try {
                var divisa1 = lectura.nextLine().toUpperCase();

                if (divisa1.equals("0")){
                    break;
                }

                boolean divisa1IsValid = false;
                for (CurrencyCode currencyCode : CurrencyCode.values()) {
                    if (divisa1.trim().equals(currencyCode.name())){
                        divisa1IsValid = true;
                    };
                }

                if (!divisa1IsValid){
                    System.out.println("DIVISA NO VALIDA\n\n");
                    continue;
                }
                System.out.println("2. Ingrese la divisa a la que desea convertir:\n" +
                        "   (Por ejemplo, BOB)");


                var divisa2 = lectura.nextLine().toUpperCase();

                boolean divisa2IsValid = false;
                for (CurrencyCode currencyCode : CurrencyCode.values()) {
                    if (divisa2.trim().equals(currencyCode.name())){
                        divisa2IsValid = true;
                    };
                }

                if (!divisa2IsValid){
                    System.out.println("DIVISA NO VALIDA\n\n");
                    continue;
                }

                //muestra el resultado exitoso, codigo a cambiar, codigo a cambiar y valor
                Cambio cambio = consulta.seleccionDivisas(divisa1,divisa2);
                System.out.println("Usted desea cambiar de "+ cambio.base_code()+ " a "+cambio.target_code()+" la tasa de cambio es de "+ cambio.conversion_rate());

                System.out.println("Ingrese la cantidad que desea cambiar" +
                        " (Ingrese la cantidad numérica):");

                String montoCambioInput = lectura.nextLine();

                float montoCambio = 0;
                try {
                    montoCambio = Float.parseFloat(montoCambioInput);
                } catch (RuntimeException e){
                    System.out.println("Numero no valido\n\n");
                    continue;

                }
                float totalCambio= cambio.conversion_rate() * montoCambio;

                System.out.println("El valor de "+montoCambio + "["+divisa1+"] corresponde al valor final de =>" +totalCambio +"["+divisa2+"]\n\n\n");
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
