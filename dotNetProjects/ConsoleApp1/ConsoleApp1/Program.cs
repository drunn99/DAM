using System.Globalization;
using System.Net;

namespace ConsoleApp1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hola, ¿Cómo te llamas?");
            string name = Console.ReadLine();

            int input = -1;

            while (input != 3 ) {
                Console.WriteLine($"\nHola {name}. Escoge una opción: \n1. Calcular letra DNI\n2.Día de la semana para una fecha\n3.Salir");
                input = int.Parse(Console.ReadLine());
                switch (input) {
                    case 1:
                        Console.WriteLine($"\nIntroduce tu número de DNI");
                        string dni = Console.ReadLine();
                        mostrarLetraDNI(dni);
                        break;
                    case 2:
                        Console.WriteLine("\nIntroduce una fecha (dd/mm/aaaa)");
                        string fechaString = Console.ReadLine();
                        DateTime fecha  = DateTime.ParseExact(fechaString, "dd/MM/yyyy", CultureInfo.InvariantCulture);
                        diaDeLaSemana(fecha);
                        break;
                    case 3:
                        Console.WriteLine("Adiós!");
                        break;
                }

            }
        }

        static void mostrarLetraDNI(string numerosDNI) {
            int numeroDNI = int.Parse(numerosDNI);
            char [] listaLetras = new char[] {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
            string dniCompleto = numerosDNI + listaLetras[numeroDNI % 23];
            Console.WriteLine($"Su DNI completo es: {dniCompleto}");
        }

        static void diaDeLaSemana(DateTime fecha) { 
            Console.WriteLine($"Ese día es: {fecha.ToString("dddd")}");
        }
    }
}
