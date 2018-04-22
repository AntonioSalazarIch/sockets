import com.sun.org.apache.bcel.internal.generic.RETURN;
/* 
	by Antonio Salazar
*/
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorTCP {
	ServerSocket sServidor;
	Socket sCliente;
	int puerto;
	PrintStream salida;
	Scanner entrada;
	String mensajeSolicitud = "";
	String mensajeRespuesta = "";

	public static String verificaPar(String numero)
	{
		int num = Integer.parseInt(numero);
		String respuesta = "";

		if (num % 2 == 0)
		{
			respuesta = "el numero " + num + " es PAR";
		}
		else
		{
			respuesta = "el numero " + num + "es IMPAR";
		}
		return respuesta;
	}
	//funcion para verificar si es primo y devuelve una cadena que se enviara al cliente
	public String verificaPrimo(int numero)
	{

		int contador = 0;
		String cadena = "";

		for(int i= 1; i <= numero; i++)
		{
			if((numero % i) == 0)
			{
				contador++;
			}
		}
		//verificamos el contador para saber si el resultado del modulo es mayor a 2 para saber si es primo
		if(contador <= 2)
		{
			cadena = "El numero es primo";
		}else{
			cadena = "El numero no es primo";
		}
		//devolvemos el string cadena que sera la respuesta a enviar al cliente
		return cadena;
	}

	
	public ServidorTCP(int p)
	{
		puerto = p;
	}
	public void iniciar()
	{
		try {
			//creamos una variable de la clase ServerSocket para iniciar nuestro servidor
			sServidor = new ServerSocket(puerto);
			System.out.println("- SERVIDOR TCP INICIADO -");
			System.out.println("- Esperando Cliente -");
			while(true){
				//aceptamos la conexion con un cliente
				sCliente = sServidor.accept();				
				entrada = new Scanner(sCliente.getInputStream());
				salida = new PrintStream(sCliente.getOutputStream());
				//capturamos el mensaje del cliente en una variable string
				mensajeSolicitud = entrada.nextLine();
				System.out.println("NUMERO PARA VERIFICAR :"+mensajeSolicitud);
				//con este if validaremos si el mensaje es un espacio (" ") el
				//servidor se desconecta y si es un numero verificara si es primo o no
				if (mensajeSolicitud.equals(" "))
				{
					//enviamos el mensaje de desconexion y desconectamos el servidor con la palabra reservada return
					//el cual saldra del while infinito y se ejecutara la funcion finally
					mensajeRespuesta = "El servidor se ha desconectado";
					salida.println(mensajeRespuesta);
					return;
				}
				else
				{
					mensajeRespuesta = verificaPrimo(Integer.parseInt(mensajeSolicitud));
					salida.println(mensajeRespuesta);
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			finalizar();
		}
		finally{
			finalizar();
		}
	}
	public void finalizar()
	{
		try {
			salida.close();
			entrada.close();
			sServidor.close();
			sCliente.close();
			System.out.println("Conexion Finalizada...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int sumar(int a,int b)
	{
		return a+b;
	}
}
